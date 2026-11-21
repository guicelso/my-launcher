package com.tw.core.utils;

import android.os.Environment;
import android.text.TextUtils;
import com.tw.core.app.DoFunApplication;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class E {
    private static final String aw = E.class.getSimpleName();
    private String av = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;

    public static boolean cA(String str) {
        File file = new File(str);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    public static long cB(File file) {
        if (file != null && file.exists() && file.isFile()) {
            Long.valueOf(0L);
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                Long lValueOf = Long.valueOf(fileInputStream.available());
                fileInputStream.close();
                return lValueOf.longValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0L;
    }

    public static boolean cC(File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        return file.isDirectory();
    }

    public static void cD(String str, String str2, boolean z) throws Throwable {
        OutputStreamWriter outputStreamWriter;
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        try {
            fileOutputStream = new FileOutputStream(cM(str), z);
            try {
                outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                try {
                    outputStreamWriter.write(str2 + "\n");
                    outputStreamWriter.flush();
                    fileOutputStream.getFD().sync();
                    C0062p.bb(fileOutputStream);
                    C0062p.bb(outputStreamWriter);
                } catch (IOException e) {
                    e = e;
                    fileOutputStream2 = fileOutputStream;
                    try {
                        e.printStackTrace();
                        C0062p.bb(fileOutputStream2);
                        C0062p.bb(outputStreamWriter);
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream = fileOutputStream2;
                        C0062p.bb(fileOutputStream);
                        C0062p.bb(outputStreamWriter);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    C0062p.bb(fileOutputStream);
                    C0062p.bb(outputStreamWriter);
                    throw th;
                }
            } catch (IOException e2) {
                e = e2;
                outputStreamWriter = null;
                fileOutputStream2 = fileOutputStream;
            } catch (Throwable th3) {
                th = th3;
                outputStreamWriter = null;
            }
        } catch (IOException e3) {
            e = e3;
            outputStreamWriter = null;
        } catch (Throwable th4) {
            th = th4;
            outputStreamWriter = null;
            fileOutputStream = null;
        }
    }

    public static long cE(String str) throws Throwable {
        FileInputStream fileInputStream;
        if (cF(str)) {
            Long.valueOf(0L);
            try {
                try {
                    fileInputStream = new FileInputStream(new File(str));
                    try {
                        long jLongValue = Long.valueOf(fileInputStream.available()).longValue();
                        C0062p.bb(fileInputStream);
                        return jLongValue;
                    } catch (Exception e) {
                        e = e;
                        e.printStackTrace();
                        C0062p.bb(fileInputStream);
                        return 0L;
                    }
                } catch (Throwable th) {
                    th = th;
                    C0062p.bb(null);
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
                fileInputStream = null;
            } catch (Throwable th2) {
                th = th2;
                C0062p.bb(null);
                throw th;
            }
        }
        return 0L;
    }

    public static boolean cF(String str) {
        return cN(new File(str));
    }

    public static File cG(String str, String str2) throws IOException {
        File file = new File(cO(str), str2);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public static List cH(File file, FileFilter fileFilter) {
        File[] fileArrListFiles;
        ArrayList arrayList = new ArrayList();
        if (cC(file) && (fileArrListFiles = file.listFiles()) != null && fileArrListFiles.length > 0) {
            for (File file2 : fileArrListFiles) {
                if (fileFilter == null || fileFilter.accept(file2)) {
                    arrayList.add(file2);
                }
            }
        }
        return arrayList;
    }

    public static void cI(File file) {
        if (file.exists()) {
            if (!file.isDirectory()) {
                if (file.isFile()) {
                    file.delete();
                    return;
                }
                return;
            }
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles != null && fileArrListFiles.length > 0) {
                for (File file2 : fileArrListFiles) {
                    cI(file2);
                }
            }
            file.delete();
        }
    }

    public static boolean cJ(File file, String str) {
        if (file == null || !file.exists()) {
            return false;
        }
        if (str.equals(file.getName())) {
            return true;
        }
        File file2 = new File(file.getParent() + File.separator + str);
        if (file2.exists()) {
            return false;
        }
        return file.renameTo(file2);
    }

    public static boolean cK(File file) {
        if (file != null) {
            return file.exists() ? file.isDirectory() : file.mkdirs();
        }
        return false;
    }

    public static String cL(String str) throws Throwable {
        BufferedReader bufferedReader;
        FileInputStream fileInputStream;
        StringBuffer stringBuffer;
        StringBuffer stringBuffer2 = null;
        FileInputStream fileInputStream2 = null;
        stringBuffer2 = null;
        File file = new File(str);
        if (file.exists() && file.isFile()) {
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                    try {
                        try {
                            StringBuffer stringBuffer3 = new StringBuffer();
                            while (true) {
                                try {
                                    String line = bufferedReader.readLine();
                                    if (line == null) {
                                        break;
                                    }
                                    stringBuffer3.append(line + "\n");
                                } catch (Exception e) {
                                    e = e;
                                    fileInputStream2 = fileInputStream;
                                    stringBuffer = stringBuffer3;
                                    try {
                                        e.printStackTrace();
                                        C0062p.bb(fileInputStream2);
                                        C0062p.bb(bufferedReader);
                                        stringBuffer2 = stringBuffer;
                                    } catch (Throwable th) {
                                        th = th;
                                        fileInputStream = fileInputStream2;
                                        C0062p.bb(fileInputStream);
                                        C0062p.bb(bufferedReader);
                                        throw th;
                                    }
                                }
                            }
                            C0062p.bb(fileInputStream);
                            C0062p.bb(bufferedReader);
                            stringBuffer2 = stringBuffer3;
                        } catch (Throwable th2) {
                            th = th2;
                            C0062p.bb(fileInputStream);
                            C0062p.bb(bufferedReader);
                            throw th;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        stringBuffer = null;
                        fileInputStream2 = fileInputStream;
                    }
                } catch (Exception e3) {
                    e = e3;
                    bufferedReader = null;
                    stringBuffer = null;
                    fileInputStream2 = fileInputStream;
                } catch (Throwable th3) {
                    th = th3;
                    bufferedReader = null;
                }
            } catch (Exception e4) {
                e = e4;
                bufferedReader = null;
                stringBuffer = null;
            } catch (Throwable th4) {
                th = th4;
                bufferedReader = null;
                fileInputStream = null;
            }
        }
        return TextUtils.isEmpty(stringBuffer2) ? "" : stringBuffer2.toString();
    }

    public static File cM(String str) throws IOException {
        File file = new File(str);
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            file.createNewFile();
        }
        return file;
    }

    public static boolean cN(File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        return file.isFile();
    }

    public static File cO(String str) {
        File file;
        try {
            file = new File(cz() + str);
            try {
                if (!file.exists()) {
                    file.mkdirs();
                }
            } catch (Exception e) {
                e = e;
                C0065s.bL(aw, e);
            }
        } catch (Exception e2) {
            e = e2;
            file = null;
        }
        return file;
    }

    public static String cz() {
        File externalCacheDir;
        String path = null;
        if (("mounted".equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) && (externalCacheDir = DoFunApplication.nu().getExternalCacheDir()) != null) {
            path = externalCacheDir.getPath();
        }
        return path == null ? DoFunApplication.nu().getFilesDir().getPath() : path;
    }
}
