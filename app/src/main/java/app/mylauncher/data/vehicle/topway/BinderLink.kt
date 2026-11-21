package app.mylauncher.data.vehicle.topway

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import android.os.Parcel
import app.mylauncher.core.FileLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class LinkState { DISCONNECTED, CONNECTING, CONNECTED, UNAVAILABLE }

data class LinkStatus(val state: LinkState = LinkState.DISCONNECTED, val detail: String = "", val at: Long = 0)

/** Backoff de religação (R6.3): 1 s, 2 s, 5 s, 10 s, 30 s… */
object Backoff {
    private val steps = longArrayOf(1_000, 2_000, 5_000, 10_000, 30_000)
    fun delayFor(attempt: Int): Long = steps[attempt.coerceIn(0, steps.lastIndex)]
}

/**
 * Conexão com um serviço exportado da TopWay, com religação automática quando o serviço
 * não existe, cai ou o processo dele morre (spec 006, R6.3).
 */
class BinderLink(
    private val context: Context,
    private val name: String,
    private val action: String,
    private val packageName: String,
    private val scope: CoroutineScope,
    private val onConnected: (IBinder) -> Unit,
    private val onDisconnected: () -> Unit,
) {
    private val _status = MutableStateFlow(LinkStatus())
    val status: StateFlow<LinkStatus> = _status.asStateFlow()

    @Volatile var binder: IBinder? = null
        private set

    private var attempt = 0
    private var retryJob: Job? = null
    private var bound = false
    private var stopped = true

    private val deathRecipient = IBinder.DeathRecipient {
        FileLog.w("$name: processo do serviço morreu")
        scope.launch { lost("serviço morreu") }
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(cn: ComponentName, service: IBinder) {
            binder = service
            attempt = 0
            runCatching { service.linkToDeath(deathRecipient, 0) }
            set(LinkState.CONNECTED, cn.flattenToShortString())
            FileLog.i("$name: conectado")
            runCatching { onConnected(service) }.onFailure { FileLog.e("$name: falha ao registrar", it) }
        }

        override fun onServiceDisconnected(cn: ComponentName) = lost("desconectado")

        override fun onNullBinding(cn: ComponentName) {
            set(LinkState.UNAVAILABLE, "onBind retornou null")
        }
    }

    fun start() {
        stopped = false
        bind()
    }

    fun stop() {
        stopped = true
        retryJob?.cancel()
        unbind()
        set(LinkState.DISCONNECTED, "parado")
    }

    private fun bind() {
        if (stopped) return
        set(LinkState.CONNECTING, "tentativa ${attempt + 1}")
        val intent = Intent(action).setPackage(packageName)
        val ok = runCatching { context.bindService(intent, connection, Context.BIND_AUTO_CREATE) }
            .onFailure { FileLog.w("$name: bindService falhou", it) }
            .getOrDefault(false)
        if (ok) {
            bound = true
        } else {
            set(LinkState.UNAVAILABLE, "serviço não encontrado")
            scheduleRetry()
        }
    }

    private fun lost(reason: String) {
        binder?.let { b -> runCatching { b.unlinkToDeath(deathRecipient, 0) } }
        binder = null
        set(LinkState.DISCONNECTED, reason)
        runCatching { onDisconnected() }
        unbind()
        scheduleRetry()
    }

    private fun unbind() {
        if (bound) runCatching { context.unbindService(connection) }
        bound = false
    }

    private fun scheduleRetry() {
        if (stopped) return
        retryJob?.cancel()
        val wait = Backoff.delayFor(attempt++)
        retryJob = scope.launch {
            delay(wait)
            bind()
        }
    }

    private fun set(state: LinkState, detail: String) {
        _status.value = LinkStatus(state, detail, System.currentTimeMillis())
    }
}

/** Chamada síncrona crua (sempre fora da thread principal). */
fun <T> IBinder.call(
    descriptor: String,
    code: Int,
    write: Parcel.() -> Unit = {},
    read: (Parcel) -> T,
): T {
    val data = Parcel.obtain()
    val reply = Parcel.obtain()
    try {
        data.writeInterfaceToken(descriptor)
        data.write()
        transact(code, data, reply, 0)
        reply.readException()
        return read(reply)
    } finally {
        data.recycle()
        reply.recycle()
    }
}

/** Callback implementado por nós: responde sempre sem exceção para não ser desregistrado. */
class CallbackBinder(
    private val descriptor: String,
    private val name: String,
    private val handler: (code: Int, data: Parcel) -> Unit,
) : Binder() {
    init {
        attachInterface(null, descriptor)
    }

    override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
        if (code == INTERFACE_TRANSACTION) return super.onTransact(code, data, reply, flags)
        runCatching {
            data.enforceInterface(descriptor)
            handler(code, data)
        }.onFailure { FileLog.w("$name: callback $code ilegível", it) }
        reply?.writeNoException()
        return true
    }
}
