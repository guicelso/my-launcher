package a.b.d.c;

import android.content.Context;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class d extends i {
    /* JADX WARN: Code duplicated, block: B:32:0x0055 A[Catch: all -> 0x0059, Throwable -> 0x005c, TryCatch #2 {Throwable -> 0x005c, blocks: (B:7:0x0016, B:9:0x001c, B:12:0x0023, B:16:0x002d, B:18:0x003a, B:33:0x0058, B:32:0x0055, B:31:0x0051), top: B:51:0x0016 }] */
    /* JADX WARN: Code duplicated, block: B:41:0x0064 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:46:0x006f A[Catch: IOException -> 0x0073, TryCatch #4 {IOException -> 0x0073, blocks: (B:6:0x000e, B:14:0x0029, B:19:0x003d, B:46:0x006f, B:45:0x006b, B:47:0x0072, B:42:0x0066), top: B:54:0x000e, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:49:0x0066 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:55:0x004c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:63:? A[Catch: IOException -> 0x0073, SYNTHETIC, TRY_LEAVE, TryCatch #4 {IOException -> 0x0073, blocks: (B:6:0x000e, B:14:0x0029, B:19:0x003d, B:46:0x006f, B:45:0x006b, B:47:0x0072, B:42:0x0066), top: B:54:0x000e, inners: #0 }] */
    @Override // a.b.d.c.i
    public Typeface a(Context context, CancellationSignal cancellationSignal, a.b.d.e.f.b[] bVarArr, int i) throws Throwable {
        Throwable th;
        Throwable th2;
        Throwable th3;
        if (bVarArr.length < 1) {
            return null;
        }
        try {
            ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = context.getContentResolver().openFileDescriptor(a(bVarArr, i).f99a, "r", cancellationSignal);
            try {
                try {
                    File fileA = a(parcelFileDescriptorOpenFileDescriptor);
                    if (fileA != null && fileA.canRead()) {
                        Typeface typefaceCreateFromFile = Typeface.createFromFile(fileA);
                        if (parcelFileDescriptorOpenFileDescriptor != null) {
                            parcelFileDescriptorOpenFileDescriptor.close();
                        }
                        return typefaceCreateFromFile;
                    }
                    FileInputStream fileInputStream = new FileInputStream(parcelFileDescriptorOpenFileDescriptor.getFileDescriptor());
                    try {
                        Typeface typefaceA = super.a(context, fileInputStream);
                        fileInputStream.close();
                        parcelFileDescriptorOpenFileDescriptor.close();
                        return typefaceA;
                    } catch (Throwable th4) {
                        try {
                            throw th4;
                        } catch (Throwable th5) {
                            th2 = th4;
                            th3 = th5;
                            if (th2 == null) {
                                fileInputStream.close();
                                throw th3;
                            }
                            fileInputStream.close();
                            throw th3;
                        }
                    }
                } catch (Throwable th6) {
                    try {
                        throw th6;
                    } catch (Throwable th7) {
                        th = th6;
                        th = th7;
                        if (parcelFileDescriptorOpenFileDescriptor != null) {
                            throw th;
                        }
                        if (th == null) {
                            parcelFileDescriptorOpenFileDescriptor.close();
                            throw th;
                        }
                        try {
                            parcelFileDescriptorOpenFileDescriptor.close();
                            throw th;
                        } catch (Throwable th8) {
                            th.addSuppressed(th8);
                            throw th;
                        }
                    }
                }
            } catch (Throwable th9) {
                th = th9;
                th = null;
                if (parcelFileDescriptorOpenFileDescriptor != null) {
                    throw th;
                }
                if (th == null) {
                    parcelFileDescriptorOpenFileDescriptor.close();
                    throw th;
                }
                parcelFileDescriptorOpenFileDescriptor.close();
                throw th;
            }
        } catch (IOException unused) {
            return null;
        }
    }

    public final File a(ParcelFileDescriptor parcelFileDescriptor) {
        try {
            String str = Os.readlink("/proc/self/fd/" + parcelFileDescriptor.getFd());
            if (OsConstants.S_ISREG(Os.stat(str).st_mode)) {
                return new File(str);
            }
        } catch (ErrnoException unused) {
        }
        return null;
    }
}
