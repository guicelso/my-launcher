package app.mylauncher.data.apps

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.Drawable
import app.mylauncher.data.prefs.IconShape

/**
 * Ícones no formato escolhido (redondo por padrão, como no Android Auto), independentes da
 * máscara do firmware (na TopWay ela é quadrada e deixava cantos retos aparecendo).
 */
object IconRenderer {
    /** Fração do ícone legado dentro do círculo. */
    private const val LEGACY_SCALE = 0.72f
    private const val SQUIRCLE_RADIUS = 0.24f

    /** Renderiza no formato escolhido (spec 011, R11.5). */
    fun render(drawable: Drawable, size: Int, shape: IconShape): Bitmap = when (shape) {
        IconShape.ROUND -> circular(drawable, size)
        IconShape.SQUIRCLE -> masked(drawable, size, round = false)
        IconShape.ORIGINAL -> original(drawable, size)
    }

    /** Desenha como está (pacotes de ícones já trazem o próprio formato; ou máscara do sistema). */
    fun original(drawable: Drawable, size: Int): Bitmap {
        val out = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        drawable.setBounds(0, 0, size, size)
        drawable.draw(Canvas(out))
        return out
    }

    fun circular(drawable: Drawable, size: Int): Bitmap = masked(drawable, size, round = true)

    private fun masked(drawable: Drawable, size: Int, round: Boolean): Bitmap {
        val content = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(content)
        if (drawable is AdaptiveIconDrawable) {
            // Camadas de 108 dp com área visível de 72 dp: estende 1/4 do tamanho para cada lado.
            val ext = size / 4
            drawable.background?.apply { setBounds(-ext, -ext, size + ext, size + ext); draw(canvas) }
            drawable.foreground?.apply { setBounds(-ext, -ext, size + ext, size + ext); draw(canvas) }
        } else {
            canvas.drawColor(Color.WHITE)
            val inset = ((size * (1 - LEGACY_SCALE)) / 2).toInt()
            drawable.setBounds(inset, inset, size - inset, size - inset)
            drawable.draw(canvas)
        }
        return mask(content, size, round)
    }

    /** Recorte com antialiasing (clipPath não suaviza a borda). */
    private fun mask(content: Bitmap, size: Int, round: Boolean): Bitmap {
        val out = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(out)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        if (round) {
            canvas.drawCircle(size / 2f, size / 2f, size / 2f, paint)
        } else {
            val r = size * SQUIRCLE_RADIUS
            canvas.drawRoundRect(0f, 0f, size.toFloat(), size.toFloat(), r, r, paint)
        }
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(content, 0f, 0f, paint)
        content.recycle()
        return out
    }
}
