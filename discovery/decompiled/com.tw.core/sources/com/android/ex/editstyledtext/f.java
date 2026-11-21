package com.android.ex.editstyledtext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.style.ImageSpan;
import android.util.Log;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class f extends ImageSpan {
    private final int kV;
    Uri kW;
    private Context kX;
    private Drawable kY;
    public int kZ;
    public int la;

    private void qf(Drawable drawable) {
        Log.d("EditStyledTextSpan", "--- rescaleBigImage:");
        if (this.kV < 0) {
            return;
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        Log.d("EditStyledTextSpan", "--- rescaleBigImage:" + intrinsicWidth + "," + intrinsicHeight + "," + this.kV);
        if (intrinsicWidth > this.kV) {
            intrinsicWidth = this.kV;
            intrinsicHeight = (intrinsicHeight * this.kV) / intrinsicWidth;
        }
        drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
    }

    @Override // android.text.style.ImageSpan, android.text.style.DynamicDrawableSpan
    public Drawable getDrawable() {
        Bitmap bitmapDecodeStream;
        if (this.kY != null) {
            return this.kY;
        }
        if (this.kW != null) {
            System.gc();
            try {
                InputStream inputStreamOpenInputStream = this.kX.getContentResolver().openInputStream(this.kW);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(inputStreamOpenInputStream, null, options);
                inputStreamOpenInputStream.close();
                InputStream inputStreamOpenInputStream2 = this.kX.getContentResolver().openInputStream(this.kW);
                int i = options.outWidth;
                int i2 = options.outHeight;
                this.la = i;
                this.kZ = i2;
                if (options.outWidth > this.kV) {
                    i = this.kV;
                    i2 = (i2 * this.kV) / options.outWidth;
                    bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpenInputStream2, new Rect(0, 0, i, i2), null);
                } else {
                    bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpenInputStream2);
                }
                this.kY = new BitmapDrawable(this.kX.getResources(), bitmapDecodeStream);
                this.kY.setBounds(0, 0, i, i2);
                inputStreamOpenInputStream2.close();
            } catch (Exception e) {
                Log.e("EditStyledTextSpan", "Failed to loaded content " + this.kW, e);
                return null;
            } catch (OutOfMemoryError e2) {
                Log.e("EditStyledTextSpan", "OutOfMemoryError");
                return null;
            }
        } else {
            this.kY = super.getDrawable();
            qf(this.kY);
            this.la = this.kY.getIntrinsicWidth();
            this.kZ = this.kY.getIntrinsicHeight();
        }
        return this.kY;
    }
}
