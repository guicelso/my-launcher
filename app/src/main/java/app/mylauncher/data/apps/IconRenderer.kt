package app.mylauncher.data.apps

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.Drawable

/**
 * Ícones redondos, como no Android Auto, independentes da máscara do firmware
 * (na TopWay ela é quadrada e deixava cantos retos aparecendo).
 */
object IconRenderer {
    /** Fração do ícone legado dentro do círculo. */
    private const val LEGACY_SCALE = 0.72f

    fun circular(drawable: Drawable, size: Int): Bitmap {
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
        return mask(content, size)
    }

    /** Recorte circular com antialiasing (clipPath não suaviza a borda). */
    private fun mask(content: Bitmap, size: Int): Bitmap {
        val out = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(out)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        canvas.drawCircle(size / 2f, size / 2f, size / 2f, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(content, 0f, 0f, paint)
        content.recycle()
        return out
    }
}
