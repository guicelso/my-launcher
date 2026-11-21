package com.tw.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: renamed from: com.tw.service.b, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0038b {
    private static Bitmap a(View view, InputStream inputStream) {
        int iRound;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byte[] bArr = new byte[1024];
            while (true) {
                int i = inputStream.read(bArr);
                if (i <= -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, i);
            }
            byteArrayOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(byteArrayInputStream, null, options);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int[] iArr = {layoutParams.width, layoutParams.height};
        int i2 = options.outWidth;
        int i3 = options.outHeight;
        if (i2 > iArr[0] || i3 > iArr[1]) {
            iRound = Math.round((i2 * 1.0f) / iArr[0]);
            int iRound2 = Math.round((i3 * 1.0f) / iArr[1]);
            if (iArr[0] <= iArr[1]) {
                iRound = iRound2;
            }
        } else {
            iRound = 1;
        }
        options.inSampleSize = iRound;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(byteArrayInputStream2, null, options);
    }

    public static boolean a(ImageView imageView, String str) {
        try {
            Bitmap bitmapA = a(imageView, new FileInputStream(str));
            if (bitmapA == null) {
                return false;
            }
            imageView.setImageBitmap(bitmapA);
            return true;
        } catch (FileNotFoundException unused) {
            Log.e("BitmapUtils", "setImageViewBitmap: FileNotFoundException:" + str);
            return false;
        }
    }
}
