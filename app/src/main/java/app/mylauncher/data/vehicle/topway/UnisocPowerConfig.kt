package app.mylauncher.data.vehicle.topway

import android.os.IBinder
import android.os.Parcel
import app.mylauncher.core.FileLog

/**
 * Gerenciamento de energia da Unisoc (`power_ex`, `android.os.sprdpower.IPowerManagerEx`).
 *
 * O `PowerController` decide se um app de terceiros pode ser iniciado sem ação do usuário. Com o
 * launcher em "otimizar" (o padrão), o sistema não conseguia conectar o listener de notificações
 * no boot ("non-UserActivity denyed"): o acesso continuava concedido, mas o card pedia a permissão
 * e ficava sem sessões de mídia (spec 016). "Não otimizar" nos tipos abaixo libera isso, como a
 * tela de gerenciamento de energia do próprio sistema faria.
 */
object UnisocPowerConfig {
    private const val SERVICE = "power_ex"
    private const val DESCRIPTOR = "android.os.sprdpower.IPowerManagerEx"
    private const val TX_GET = 28 // getAppPowerSaveConfigWithType(String, int): int
    private const val TX_SET = 30 // setAppPowerSaveConfigWithType(String, int, int): boolean

    /** AppPowerSaveConfig.ConfigType */
    const val TYPE_AUTOLAUNCH = 4
    const val TYPE_SECONDARYLAUNCH = 5
    const val TYPE_LOCKSCREENCLEANUP = 6

    /** AppPowerSaveConfig: 0 = automático, 1 = otimizar (bloqueia), 2 = não otimizar (permite). */
    const val VALUE_NO_OPTIMIZE = 2

    private val TYPES = listOf(TYPE_AUTOLAUNCH, TYPE_SECONDARYLAUNCH, TYPE_LOCKSCREENCLEANUP)

    /** Só existe em aparelhos Unisoc. */
    private fun binder(): IBinder? = runCatching {
        Class.forName("android.os.ServiceManager").getMethod("getService", String::class.java).invoke(null, SERVICE) as IBinder?
    }.getOrNull()

    /** Garante "não otimizar" para [pkg]. @return true se alguma configuração foi alterada. */
    fun ensureNoOptimize(pkg: String): Boolean {
        val b = binder() ?: return false
        var changed = false
        TYPES.forEach { type ->
            val current = get(b, pkg, type) ?: return@forEach
            if (current != VALUE_NO_OPTIMIZE && set(b, pkg, type, VALUE_NO_OPTIMIZE)) {
                FileLog.i("Energia Unisoc: $pkg tipo $type $current → $VALUE_NO_OPTIMIZE (não otimizar)")
                changed = true
            }
        }
        return changed
    }

    private fun get(b: IBinder, pkg: String, type: Int): Int? = call(b, TX_GET) { writeString(pkg); writeInt(type) }

    private fun set(b: IBinder, pkg: String, type: Int, value: Int): Boolean =
        call(b, TX_SET) { writeString(pkg); writeInt(type); writeInt(value) } == 1

    private inline fun call(b: IBinder, code: Int, args: Parcel.() -> Unit): Int? {
        val data = Parcel.obtain()
        val reply = Parcel.obtain()
        return try {
            data.writeInterfaceToken(DESCRIPTOR)
            data.args()
            b.transact(code, data, reply, 0)
            reply.readException()
            reply.readInt()
        } catch (t: Throwable) {
            FileLog.w("Energia Unisoc: falha na transação $code: ${t.message}")
            null
        } finally {
            data.recycle()
            reply.recycle()
        }
    }
}
