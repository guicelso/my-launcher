package d;

import android.os.Environment;
import android.text.TextUtils;
import com.tw.tdservice.app.CoreApplication;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/* JADX INFO: loaded from: classes.dex */
public final class d {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final /* synthetic */ int f38a = 0;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final /* synthetic */ int f39b = 0;

    public static File a(String str) throws IOException {
        File file;
        try {
            file = new File(b() + "/download/");
            try {
                if (!file.exists()) {
                    file.mkdirs();
                }
            } catch (Exception e) {
                e = e;
                c.d("d", e);
            }
        } catch (Exception e2) {
            e = e2;
            file = null;
        }
        File file2 = new File(file, str);
        if (!file2.exists()) {
            file2.createNewFile();
        }
        return file2;
    }

    public static String b() {
        File externalCacheDir;
        String path = (("mounted".equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) && (externalCacheDir = CoreApplication.f30a.getExternalCacheDir()) != null) ? externalCacheDir.getPath() : null;
        return path == null ? CoreApplication.f30a.getFilesDir().getPath() : path;
    }

    public static String c(String str) {
        File externalFilesDir;
        String absolutePath = (("mounted".equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) && (externalFilesDir = CoreApplication.f30a.getExternalFilesDir(str)) != null) ? externalFilesDir.getAbsolutePath() : null;
        return absolutePath == null ? CoreApplication.f30a.getFilesDir().getPath() : absolutePath;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v12, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r5v13, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r5v5, types: [boolean] */
    /* JADX WARN: Type inference failed for: r5v6, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r5v8 */
    public static String d(String str) throws Throwable {
        BufferedReader bufferedReader;
        StringBuffer stringBuffer;
        Exception e;
        ?? file = new File(str);
        StringBuffer stringBuffer2 = null;
        if (file.exists()) {
            ?? IsFile = file.isFile();
            try {
                if (IsFile != 0) {
                    try {
                        IsFile = new FileInputStream((File) file);
                        try {
                            bufferedReader = new BufferedReader(new InputStreamReader(IsFile));
                            try {
                                stringBuffer = new StringBuffer();
                                while (true) {
                                    try {
                                        String line = bufferedReader.readLine();
                                        if (line == null) {
                                            break;
                                        }
                                        stringBuffer.append(line + "\n");
                                    } catch (Exception e2) {
                                        e = e2;
                                        e.printStackTrace();
                                    }
                                }
                            } catch (Exception e3) {
                                stringBuffer = null;
                                e = e3;
                            }
                        } catch (Exception e4) {
                            stringBuffer = null;
                            e = e4;
                            bufferedReader = null;
                        } catch (Throwable th) {
                            th = th;
                            file = 0;
                            m.e(IsFile);
                            m.e(file);
                            throw th;
                        }
                    } catch (Exception e5) {
                        bufferedReader = null;
                        stringBuffer = null;
                        e = e5;
                        IsFile = 0;
                    } catch (Throwable th2) {
                        file = 0;
                        th = th2;
                        IsFile = 0;
                    }
                    m.e(IsFile);
                    m.e(bufferedReader);
                    stringBuffer2 = stringBuffer;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        }
        return TextUtils.isEmpty(stringBuffer2) ? "" : stringBuffer2.toString();
    }

    public static void e(ZipOutputStream zipOutputStream, String str, File file) {
        try {
            if (file.isDirectory()) {
                File[] fileArrListFiles = file.listFiles();
                String str2 = str + "/";
                zipOutputStream.putNextEntry(new ZipEntry(str2));
                for (File file2 : fileArrListFiles) {
                    e(zipOutputStream, str2 + file2.getName(), file2);
                }
                return;
            }
            zipOutputStream.putNextEntry(new ZipEntry(str));
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bArr = new byte[1024];
            while (true) {
                int i = fileInputStream.read(bArr);
                if (i == -1) {
                    fileInputStream.close();
                    return;
                }
                zipOutputStream.write(bArr, 0, i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
