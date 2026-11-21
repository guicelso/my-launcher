package com.dofun.carsetting.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.dofun.carsetting.R;
import com.dofun.carsetting.f.u;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/* JADX INFO: loaded from: classes.dex */
public class LogoActivity extends Activity {
    private boolean b;
    private String c;
    private RecyclerView e;
    private ImageView f;
    private HorizontalTagAdapter k;
    private int l;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private com.dofun.carsetting.b.a f37a = com.dofun.carsetting.b.a.a();
    private boolean d = true;
    private int g = 0;
    private int h = 0;
    private Handler i = new Handler() { // from class: com.dofun.carsetting.activity.LogoActivity.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                c cVar = (c) LogoActivity.this.m.get(message.arg1);
                if (cVar.f == null) {
                    new b().execute(cVar);
                }
            } catch (Exception unused) {
            }
        }
    };
    private u j = null;
    private ArrayList<c> m = new ArrayList<>();

    public interface d {
        void a(View view, int i);
    }

    /* JADX WARN: Code duplicated, block: B:26:0x009e  */
    /* JADX WARN: Code duplicated, block: B:27:0x00af  */
    private static Bitmap a(Matrix matrix, Bitmap bitmap, int i, int i2, boolean z) {
        Matrix matrix2;
        Bitmap bitmapCreateBitmap;
        Bitmap bitmapCreateBitmap2;
        Matrix matrix3 = matrix;
        int width = bitmap.getWidth() - i;
        int height = bitmap.getHeight() - i2;
        if (!z && (width < 0 || height < 0)) {
            Bitmap bitmapCreateBitmap3 = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap3);
            int iMax = Math.max(0, width / 2);
            int iMax2 = Math.max(0, height / 2);
            Rect rect = new Rect(iMax, iMax2, Math.min(i, bitmap.getWidth()) + iMax, Math.min(i2, bitmap.getHeight()) + iMax2);
            int iWidth = (i - rect.width()) / 2;
            int iHeight = (i2 - rect.height()) / 2;
            canvas.drawBitmap(bitmap, rect, new Rect(iWidth, iHeight, i - iWidth, i2 - iHeight), (Paint) null);
            canvas.setBitmap(null);
            return bitmapCreateBitmap3;
        }
        float width2 = bitmap.getWidth();
        float height2 = bitmap.getHeight();
        float f = i;
        float f2 = i2;
        if (width2 / height2 <= f / f2) {
            float f3 = f / width2;
            if (f3 < 0.9f || f3 > 1.0f) {
                matrix.setScale(f3, f3);
            } else {
                matrix2 = null;
            }
            if (matrix2 != null) {
                bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix2, true);
            } else {
                bitmapCreateBitmap = bitmap;
            }
            bitmapCreateBitmap2 = Bitmap.createBitmap(bitmapCreateBitmap, Math.max(0, bitmapCreateBitmap.getWidth() - i) / 2, Math.max(0, bitmapCreateBitmap.getHeight() - i2) / 2, i, i2);
            if (bitmapCreateBitmap2 != bitmapCreateBitmap && bitmapCreateBitmap != bitmap) {
                bitmapCreateBitmap.recycle();
            }
            return bitmapCreateBitmap2;
        }
        float f4 = f2 / height2;
        if (f4 < 0.9f || f4 > 1.0f) {
            matrix.setScale(f4, f4);
        } else {
            matrix3 = null;
        }
        matrix2 = matrix3;
        if (matrix2 != null) {
            bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix2, true);
        } else {
            bitmapCreateBitmap = bitmap;
        }
        bitmapCreateBitmap2 = Bitmap.createBitmap(bitmapCreateBitmap, Math.max(0, bitmapCreateBitmap.getWidth() - i) / 2, Math.max(0, bitmapCreateBitmap.getHeight() - i2) / 2, i, i2);
        if (bitmapCreateBitmap2 != bitmapCreateBitmap) {
            bitmapCreateBitmap.recycle();
        }
        return bitmapCreateBitmap2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap a(Bitmap bitmap, int i, int i2) {
        float height;
        if (bitmap.getWidth() < bitmap.getHeight()) {
            height = i / bitmap.getWidth();
        } else {
            height = i2 / bitmap.getHeight();
        }
        Matrix matrix = new Matrix();
        matrix.setScale(height, height);
        return a(matrix, bitmap, i, i2, true);
    }

    private void a(String str, String str2) {
        new a().execute(str, str2);
    }

    private void a(String str) {
        File file = new File(str);
        if (file.canRead() && file.isDirectory()) {
            try {
                String[] list = file.list(new FilenameFilter() { // from class: com.dofun.carsetting.activity.LogoActivity.1
                    @Override // java.io.FilenameFilter
                    public boolean accept(File file2, String str2) {
                        String upperCase = str2.toUpperCase(Locale.US);
                        if (upperCase.startsWith(".") || "wallpaper.png".equals(upperCase)) {
                            return false;
                        }
                        if (com.dofun.carsetting.f.b.F()) {
                            return upperCase.endsWith(".BMP");
                        }
                        return upperCase.endsWith(".BMP") || upperCase.endsWith(".PNG") || upperCase.endsWith(".JPG");
                    }
                });
                if (list != null) {
                    for (String str2 : list) {
                        a(str, str2);
                    }
                }
            } catch (Exception unused) {
            }
        }
    }

    private void b(String str) throws Throwable {
        File file = new File(str);
        if (!file.canRead() || !file.isFile()) {
            return;
        }
        ZipInputStream zipInputStream = null;
        try {
            try {
                ZipInputStream zipInputStream2 = new ZipInputStream(new BufferedInputStream(new FileInputStream(file)));
                while (true) {
                    try {
                        ZipEntry nextEntry = zipInputStream2.getNextEntry();
                        if (nextEntry != null) {
                            a(str, nextEntry.getName());
                        } else {
                            zipInputStream2.close();
                            return;
                        }
                    } catch (Exception unused) {
                        zipInputStream = zipInputStream2;
                        if (zipInputStream != null) {
                            zipInputStream.close();
                            return;
                        }
                        return;
                    } catch (Throwable th) {
                        th = th;
                        zipInputStream = zipInputStream2;
                        if (zipInputStream != null) {
                            zipInputStream.close();
                        }
                        throw th;
                    }
                }
            } catch (Exception unused2) {
            }
        } catch (Exception unused3) {
        } catch (Throwable th2) {
            th = th2;
        }
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) throws Throwable {
        String[] strArrSplit;
        super.onCreate(bundle);
        this.j = u.a();
        this.b = com.dofun.carsetting.f.b.H();
        this.c = SystemProperties.get("ro.tw.version");
        if (!TextUtils.isEmpty(this.c) && (strArrSplit = this.c.split("_")) != null && strArrSplit.length > 1) {
            try {
                if (Integer.parseInt(strArrSplit[1].split("\\.")[0]) < 20240509) {
                    this.d = false;
                }
                Log.d("LogoActivity", "onClick: systemOS date=" + strArrSplit[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.d("LogoActivity", "onClick: isNewLogoSet_t919=" + this.d);
        if (com.dofun.carsetting.f.b.L()) {
            this.j.write(40730, 1, 0, "mount -t vfat /dev/block/by-name/bootloader /bootloader");
        }
        a("/data/tw", "bootlogo.bmp");
        if (new File("/data/tw/bootlogo.bmp").exists()) {
            this.j.write(40730, 3, 0, "chmod 666 /data/tw/bootlogo.bmp");
        }
        int i = SystemProperties.getInt("ro.sf.swrotation", 0);
        this.h = SystemProperties.getInt("persist.sf.autorotation", -1);
        Log.d("LogoActivity", "onCreate:swrotation:" + i + " autoRotation:" + this.h);
        final int i2 = getResources().getDisplayMetrics().widthPixels;
        final int i3 = getResources().getDisplayMetrics().heightPixels;
        if (this.h == -1) {
            if (i == 90) {
                a("/sdcard/logov90");
                this.g = 270;
            } else if (i == 270) {
                a("/sdcard/logov270");
                this.g = 90;
            } else if (i == 0 || i == 180) {
                if (SystemProperties.getInt("persist.sf.forcerotation", 2) != 1) {
                    b(this.f37a.S + "logo.zip");
                } else if (i == 180) {
                    a("/sdcard/logov180");
                    this.g = 180;
                } else {
                    a("/sdcard/logov");
                }
            }
        } else if (this.h == 0) {
            a("/sdcard/logov");
        } else if (this.h == 1) {
            a("/sdcard/logov90");
        }
        a("/sdcard/.Tcfg");
        a("/sdcard/.Tcfg/logo");
        a("/sdcard");
        a("/sdcard/logo");
        for (int i4 = 0; i4 < this.f37a.A.length; i4++) {
            a("/" + this.f37a.A[i4]);
            a("/" + this.f37a.A[i4] + "/logo");
        }
        for (int i5 = 0; i5 < this.f37a.B.length; i5++) {
            a("/" + this.f37a.B[i5]);
            a("/" + this.f37a.A[i5] + "/logo");
        }
        setContentView(R.layout.tw_logo_chooser);
        this.f = (ImageView) findViewById(R.id.logo);
        if (this.g != 0) {
            this.f.setRotation(this.g);
        }
        this.e = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(0);
        this.e.setLayoutManager(linearLayoutManager);
        this.k = new HorizontalTagAdapter(this);
        this.e.setAdapter(this.k);
        this.k.a(new d() { // from class: com.dofun.carsetting.activity.LogoActivity.3
            @Override // com.dofun.carsetting.activity.LogoActivity.d
            public void a(View view, int i6) {
                LogoActivity.this.l = i6;
                c cVar = (c) LogoActivity.this.m.get(i6);
                Bitmap bitmapA = cVar.f;
                if (bitmapA == null && (bitmapA = LogoActivity.this.a(cVar.b, cVar)) == null) {
                    bitmapA = cVar.e;
                }
                if (bitmapA != null) {
                    LogoActivity.this.f.setImageBitmap(bitmapA);
                } else {
                    LogoActivity.this.i.removeMessages(0);
                    LogoActivity.this.i.sendMessageDelayed(LogoActivity.this.i.obtainMessage(0, i6, 0), 100L);
                }
            }
        });
        findViewById(R.id.set).setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.activity.LogoActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) throws Throwable {
                try {
                    Bitmap bitmap = ((c) LogoActivity.this.m.get(LogoActivity.this.l)).f;
                    if (com.dofun.carsetting.f.b.L()) {
                        LogoActivity.this.a(bitmap, 24);
                        if (LogoActivity.this.h == 0) {
                            LogoActivity.this.a(bitmap, 24, "/data/tw/bootlogo_h.bmp");
                        } else if (LogoActivity.this.h == 1) {
                            LogoActivity.this.a(bitmap, 24, "/data/tw/bootlogo_w.bmp");
                        }
                        LogoActivity.this.j.write(40730, 3, 0, "dd if=/data/tw/bootlogo.bmp of=/dev/block/by-name/logo bs=512");
                        Toast.makeText(LogoActivity.this.getApplicationContext(), R.string.logo_instructions_complete, 0).show();
                        return;
                    }
                    if (LogoActivity.this.b) {
                        LogoActivity.this.a(bitmap, 24);
                        String str = ((c) LogoActivity.this.m.get(LogoActivity.this.l)).b;
                        String str2 = ((c) LogoActivity.this.m.get(LogoActivity.this.l)).c;
                        String str3 = ((c) LogoActivity.this.m.get(LogoActivity.this.l)).d;
                        Log.d("LogoActivity", "onClick: " + str + "," + str2 + "," + str3);
                        int width = bitmap.getWidth();
                        int height = bitmap.getHeight();
                        Log.d("LogoActivity", "onClick: w=" + width + ",h=" + height + ",screenWidth=" + i2 + ",screenHeight=" + i3);
                        if (LogoActivity.this.d) {
                            if (LogoActivity.this.g == 90 || LogoActivity.this.g == 270) {
                                if (height <= i2) {
                                    if (width > i3) {
                                    }
                                }
                                Toast.makeText(LogoActivity.this.getApplicationContext(), "Logo size error", 0).show();
                                return;
                            } else if (width > i2 || height > i3) {
                                Toast.makeText(LogoActivity.this.getApplicationContext(), "Logo size error", 0).show();
                                return;
                            }
                            LogoActivity.this.j.write(40730, 3, 0, "mkfs.fat /dev/block/by-name/splash");
                            LogoActivity.this.j.write(40730, 3, 0, "mkdir -p /data/tw/logo");
                            LogoActivity.this.j.write(40730, 3, 0, "chmod 755 /data/tw/logo");
                            LogoActivity.this.j.write(40730, 1, 0, "mount -t vfat /dev/block/by-name/splash /data/tw/logo");
                            LogoActivity.this.i.postDelayed(new Runnable() { // from class: com.dofun.carsetting.activity.LogoActivity.4.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    LogoActivity.this.j.write(40730, 3, 0, "cp -rf /data/tw/bootlogo.bmp /data/tw/logo_custom.bmp");
                                    LogoActivity.this.j.write(40730, 3, 0, "cp -rf /data/tw/logo_custom.bmp /data/tw/logo/");
                                    LogoActivity.this.j.write(40730, 3, 0, "umount /data/tw/logo");
                                    LogoActivity.this.j.write(40730, 1, 0, "sync");
                                    Toast.makeText(LogoActivity.this.getApplicationContext(), R.string.logo_instructions_complete, 0).show();
                                }
                            }, 300L);
                            return;
                        }
                        if (new File(str + "/" + str3).exists()) {
                            LogoActivity.this.j.write(40730, 3, 0, "cp -rf " + str + "/" + str3 + " data/tw/bootlogo.elf");
                            LogoActivity.this.j.write(40730, 3, 0, "chown system.system /data/tw/bootlogo.elf");
                            LogoActivity.this.j.write(40730, 3, 0, "chmod 666 /data/tw/bootlogo.elf");
                        }
                        LogoActivity.this.i.postDelayed(new Runnable() { // from class: com.dofun.carsetting.activity.LogoActivity.4.2
                            @Override // java.lang.Runnable
                            public void run() {
                                if (new File("/data/tw/bootlogo.elf").exists()) {
                                    LogoActivity.this.j.write(40730, 3, 0, "dd if=/data/tw/bootlogo.elf of=/dev/block/by-name/imagefv_a");
                                    LogoActivity.this.j.write(40730, 3, 0, "dd if=/data/tw/bootlogo.elf of=/dev/block/by-name/imagefv_b");
                                    Toast.makeText(LogoActivity.this.getApplicationContext(), R.string.logo_instructions_complete, 0).show();
                                    return;
                                }
                                Toast.makeText(LogoActivity.this.getApplicationContext(), "Logo does not exist", 0).show();
                            }
                        }, 200L);
                        return;
                    }
                    if (com.dofun.carsetting.f.b.G()) {
                        LogoActivity.this.j.write(40730, 3, 0, "rm /twdataconfig/bootlogo.bmp");
                        LogoActivity.this.j.write(40730, 3, 0, "rm /twdataconfig/bootlogo_720_1280.bmp");
                        LogoActivity.this.a(bitmap, 24);
                        LogoActivity.this.i.postDelayed(new Runnable() { // from class: com.dofun.carsetting.activity.LogoActivity.4.3
                            @Override // java.lang.Runnable
                            public void run() {
                                LogoActivity.this.j.write(40730, 3, 0, "cp /data/tw/bootlogo.bmp /twdataconfig/");
                                LogoActivity.this.j.write(40730, 1, 0, "sync");
                                Toast.makeText(LogoActivity.this.getApplicationContext(), R.string.logo_instructions_complete, 0).show();
                            }
                        }, 300L);
                        return;
                    }
                    LogoActivity.this.j.write(40730, 1, 0, "mount -t vfat /dev/block/by-name/bootloader /bootloader");
                    if (com.dofun.carsetting.f.b.F()) {
                        LogoActivity.this.j.write(40730, 3, 0, "rm /bootloader/bootlogo.bmp");
                        LogoActivity.this.j.write(40730, 3, 0, "rm /bootloader/bootlogo_720_1280.bmp");
                        LogoActivity.this.j.write(40730, 3, 0, "cp -rf " + ((c) LogoActivity.this.m.get(LogoActivity.this.l)).b + "/" + ((c) LogoActivity.this.m.get(LogoActivity.this.l)).c + " /data/tw/bootlogo.bmp");
                    } else {
                        LogoActivity.this.a(bitmap, 32);
                    }
                    LogoActivity.this.i.postDelayed(new Runnable() { // from class: com.dofun.carsetting.activity.LogoActivity.4.4
                        @Override // java.lang.Runnable
                        public void run() {
                            LogoActivity.this.j.write(40730, 3, 0, "cp /data/tw/bootlogo.bmp /bootloader/");
                            LogoActivity.this.j.write(40730, 1, 0, "sync");
                            Toast.makeText(LogoActivity.this.getApplicationContext(), R.string.logo_instructions_complete, 0).show();
                        }
                    }, 300L);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
        this.f.setImageBitmap(a(BitmapFactory.decodeFile("/data/tw/bootlogo.bmp")));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap a(String str, c cVar) {
        Bitmap bitmapDecodeFile;
        if (TextUtils.isEmpty(str) || cVar == null) {
            return null;
        }
        try {
            String str2 = cVar.c;
            if (str2 != null && str2.length() > 0 && str.endsWith(".zip")) {
                ZipFile zipFile = new ZipFile(str);
                bitmapDecodeFile = BitmapFactory.decodeStream(zipFile.getInputStream(zipFile.getEntry(str2)));
            } else {
                bitmapDecodeFile = BitmapFactory.decodeFile(str + "/" + str2);
            }
            if (bitmapDecodeFile == null) {
                Log.d("LogoActivity", "resetBitmap bitmap=null");
            }
            Bitmap bitmapA = a(bitmapDecodeFile);
            cVar.f = bitmapA;
            return bitmapA;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap a(Bitmap bitmap) {
        try {
            getWindowManager().getDefaultDisplay();
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            new Canvas(bitmapCreateBitmap).drawBitmap(bitmap, 0.0f, 0.0f, new Paint());
            return bitmapCreateBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public class HorizontalTagAdapter extends RecyclerView.Adapter<a> {
        private Context b;
        private d c;

        public HorizontalTagAdapter(Context context) {
            this.b = context;
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public int getItemCount() {
            return LogoActivity.this.m.size();
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public a onCreateViewHolder(ViewGroup viewGroup, int i) {
            View viewInflate = View.inflate(viewGroup.getContext(), R.layout.tw_logo_item, null);
            if (LogoActivity.this.g != 0) {
                viewInflate.setRotation(LogoActivity.this.g);
            }
            return new a(viewInflate);
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onBindViewHolder(a aVar, final int i) {
            aVar.f48a.setImageBitmap(((c) LogoActivity.this.m.get(i)).e);
            if (this.c != null) {
                final View view = aVar.itemView;
                aVar.f48a.setOnClickListener(new View.OnClickListener() { // from class: com.dofun.carsetting.activity.LogoActivity.HorizontalTagAdapter.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        HorizontalTagAdapter.this.c.a(view, i);
                    }
                });
            }
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onBindViewHolder(a aVar, int i, List<Object> list) {
            super.onBindViewHolder(aVar, i, list);
        }

        class a extends RecyclerView.ViewHolder {

            /* JADX INFO: renamed from: a, reason: collision with root package name */
            ImageView f48a;

            public a(View view) {
                super(view);
                this.f48a = (ImageView) view;
            }
        }

        public void a(d dVar) {
            this.c = dVar;
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        this.j.write(40730, 1, 0, "umount /bootloader");
        this.j.close();
        this.j = null;
        this.i.removeMessages(0);
        for (c cVar : this.m) {
            if (cVar.f != null) {
                cVar.f.recycle();
                cVar.f = null;
            }
            if (cVar.e != null) {
                cVar.e.recycle();
                cVar.e = null;
            }
        }
        super.onDestroy();
    }

    private class c {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        int f51a;
        String b;
        String c;
        String d;
        Bitmap e;
        Bitmap f;

        private c() {
        }
    }

    private class a extends AsyncTask<String, Void, c> {
        private a() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public c doInBackground(String... strArr) {
            Bitmap bitmapDecodeFile;
            try {
                String str = strArr[0];
                String str2 = strArr[1];
                String strSubstring = str2.substring(0, str2.lastIndexOf("."));
                File file = new File(str + "/" + strSubstring + ".elf");
                if (LogoActivity.this.b && !file.exists()) {
                    Log.d("LogoActivity", "doInBackground:tq919 no .elf file-> " + str + "," + str2);
                    if (!LogoActivity.this.d) {
                        return null;
                    }
                }
                if (str2 != null && str2.length() > 0 && str.endsWith(".zip")) {
                    ZipFile zipFile = new ZipFile(str);
                    bitmapDecodeFile = BitmapFactory.decodeStream(zipFile.getInputStream(zipFile.getEntry(str2)));
                    zipFile.close();
                } else {
                    bitmapDecodeFile = BitmapFactory.decodeFile(str + "/" + str2);
                }
                if (bitmapDecodeFile != null) {
                    c cVar = new c();
                    cVar.f51a = LogoActivity.this.m.size();
                    cVar.b = str;
                    if (str2 != null && str2.length() > 0) {
                        cVar.c = str2;
                    }
                    if (!LogoActivity.this.d) {
                        cVar.d = strSubstring + ".elf";
                    }
                    cVar.e = LogoActivity.this.a(bitmapDecodeFile, 100, 100);
                    cVar.f = LogoActivity.this.a(bitmapDecodeFile);
                    bitmapDecodeFile.recycle();
                    return cVar;
                }
            } catch (Exception e) {
                Log.e("LogoActivity", "doInBackground: " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onPostExecute(c cVar) {
            if (cVar == null) {
                return;
            }
            LogoActivity.this.m.add(cVar);
            if (Math.abs(cVar.f51a - LogoActivity.this.l) > 1 && cVar.f != null) {
                cVar.f.recycle();
                cVar.f = null;
            }
            LogoActivity.this.k.notifyDataSetChanged();
        }
    }

    private class b extends AsyncTask<c, Void, c> {
        private b() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public c doInBackground(c... cVarArr) {
            Bitmap bitmapDecodeFile;
            try {
                c cVar = cVarArr[0];
                if (cVar.c != null && cVar.b.endsWith(".zip")) {
                    ZipFile zipFile = new ZipFile(cVar.b);
                    bitmapDecodeFile = BitmapFactory.decodeStream(zipFile.getInputStream(zipFile.getEntry(cVar.c)));
                    zipFile.close();
                } else {
                    bitmapDecodeFile = BitmapFactory.decodeFile(cVar.b + "/" + cVar.c);
                }
                if (bitmapDecodeFile == null) {
                    return null;
                }
                cVar.f = LogoActivity.this.a(bitmapDecodeFile);
                bitmapDecodeFile.recycle();
                return cVar;
            } catch (Exception unused) {
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onPostExecute(c cVar) {
            if (cVar == null) {
                return;
            }
            int i = LogoActivity.this.l;
            if (cVar.f51a == i) {
                LogoActivity.this.f.setImageBitmap(cVar.f);
                return;
            }
            for (int i2 = 0; i2 < i - 1; i2++) {
                c cVar2 = (c) LogoActivity.this.m.get(i2);
                if (cVar2.f != null) {
                    cVar2.f.recycle();
                    cVar2.f = null;
                }
            }
            int size = LogoActivity.this.m.size();
            for (int i3 = i + 2; i3 < size; i3++) {
                c cVar3 = (c) LogoActivity.this.m.get(i3);
                if (cVar3.f != null) {
                    cVar3.f.recycle();
                    cVar3.f = null;
                }
            }
        }
    }

    private byte[] a(int[] iArr, int i, int i2, int i3) {
        if (i3 % 8 != 0) {
            Log.e("LogoActivity", "toRGB: invalid bit argument!!!");
            return null;
        }
        int i4 = i3 / 8;
        byte[] bArr = new byte[i2 * i * 4];
        int length = iArr.length - 1;
        int i5 = 0;
        while (length >= i) {
            int i6 = length - i;
            int i7 = i6 + 1;
            while (i7 <= length) {
                int i8 = i5;
                int i9 = 0;
                while (i9 < i4) {
                    bArr[i8] = (byte) (iArr[i7] >> (i9 * 8));
                    i9++;
                    i8++;
                }
                i7++;
                i5 = i8;
            }
            length = i6;
        }
        return bArr;
    }

    private byte[] a(int i) {
        return new byte[]{66, 77, (byte) i, (byte) (i >> 8), (byte) (i >> 16), (byte) (i >> 24), 0, 0, 0, 0, 54, 0, 0, 0};
    }

    private byte[] a(int i, int i2, int i3, int i4) {
        return new byte[]{40, 0, 0, 0, (byte) i, (byte) (i >> 8), (byte) (i >> 16), (byte) (i >> 24), (byte) i2, (byte) (i2 >> 8), (byte) (i2 >> 16), (byte) (i2 >> 24), 1, 0, (byte) ((i4 != 24 && i4 == 32) ? 32 : 24), 0, 0, 0, 0, 0, (byte) i3, (byte) (i3 >> 8), (byte) (i3 >> 16), (byte) (i3 >> 24), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v7 */
    public void a(Bitmap bitmap, int i) throws Throwable {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        byte[] bArrA = a(iArr, width, height, i);
        byte[] bArrA2 = a(bArrA.length + 54);
        byte[] bArrA3 = a(width, height, bArrA.length, i);
        ?? r1 = 0;
        FileOutputStream fileOutputStream = null;
        try {
            try {
                try {
                    FileOutputStream fileOutputStream2 = new FileOutputStream("/data/tw/bootlogo.bmp");
                    try {
                        fileOutputStream2.write(bArrA2);
                        fileOutputStream2.write(bArrA3);
                        fileOutputStream2.write(bArrA);
                        fileOutputStream2.flush();
                        fileOutputStream2.close();
                    } catch (Exception unused) {
                        fileOutputStream = fileOutputStream2;
                        new File("/data/tw/bootlogo.bmp").delete();
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        r1 = fileOutputStream2;
                        if (r1 != 0) {
                            r1.close();
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception unused2) {
            }
            r1 = "chmod 666 /data/tw/bootlogo.bmp";
            this.j.write(40730, 3, 0, "chmod 666 /data/tw/bootlogo.bmp");
        } catch (Exception unused3) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r1v7 */
    public void a(Bitmap bitmap, int i, String str) throws Throwable {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        byte[] bArrA = a(iArr, width, height, i);
        byte[] bArrA2 = a(bArrA.length + 54);
        byte[] bArrA3 = a(width, height, bArrA.length, i);
        ?? sb = 0;
        FileOutputStream fileOutputStream = null;
        try {
            try {
                try {
                    FileOutputStream fileOutputStream2 = new FileOutputStream(str);
                    try {
                        fileOutputStream2.write(bArrA2);
                        fileOutputStream2.write(bArrA3);
                        fileOutputStream2.write(bArrA);
                        fileOutputStream2.flush();
                        fileOutputStream2.close();
                    } catch (Exception unused) {
                        fileOutputStream = fileOutputStream2;
                        new File(str).delete();
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        sb = fileOutputStream2;
                        if (sb != 0) {
                            sb.close();
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception unused2) {
            }
            u uVar = this.j;
            sb = new StringBuilder();
            sb.append("chmod 666 ");
            sb.append(str);
            uVar.write(40730, 3, 0, sb.toString());
        } catch (Exception unused3) {
        }
    }
}
