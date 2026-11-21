package a.b.c;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/* JADX INFO: loaded from: classes.dex */
public final class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final Set<File> f34a = new HashSet();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final boolean f35b;

    /* JADX INFO: renamed from: a.b.c.a$a, reason: collision with other inner class name */
    public static final class C0002a {
        public static void a(Closeable closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                Log.w("MultiDex", "Failed to close resource", e);
            }
        }

        public static void b(ZipFile zipFile, ZipEntry zipEntry, File file, String str) throws IOException {
            InputStream inputStream = zipFile.getInputStream(zipEntry);
            File fileCreateTempFile = File.createTempFile("tmp-" + str, ".zip", file.getParentFile());
            StringBuilder sbB = c.a.a.a.a.b("Extracting ");
            sbB.append(fileCreateTempFile.getPath());
            Log.i("MultiDex", sbB.toString());
            try {
                ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(fileCreateTempFile)));
                try {
                    ZipEntry zipEntry2 = new ZipEntry("classes.dex");
                    zipEntry2.setTime(zipEntry.getTime());
                    zipOutputStream.putNextEntry(zipEntry2);
                    byte[] bArr = new byte[16384];
                    while (true) {
                        int i = inputStream.read(bArr);
                        if (i == -1) {
                            break;
                        } else {
                            zipOutputStream.write(bArr, 0, i);
                        }
                    }
                    zipOutputStream.closeEntry();
                    zipOutputStream.close();
                    if (!fileCreateTempFile.setReadOnly()) {
                        throw new IOException("Failed to mark readonly \"" + fileCreateTempFile.getAbsolutePath() + "\" (tmp of \"" + file.getAbsolutePath() + "\")");
                    }
                    Log.i("MultiDex", "Renaming to " + file.getPath());
                    if (fileCreateTempFile.renameTo(file)) {
                        a(inputStream);
                        fileCreateTempFile.delete();
                        return;
                    }
                    throw new IOException("Failed to rename \"" + fileCreateTempFile.getAbsolutePath() + "\" to \"" + file.getAbsolutePath() + "\"");
                } catch (Throwable th) {
                    zipOutputStream.close();
                    throw th;
                }
            } catch (Throwable th2) {
                a(inputStream);
                fileCreateTempFile.delete();
                throw th2;
            }
        }

        public static d c(RandomAccessFile randomAccessFile) throws IOException {
            long length = randomAccessFile.length() - 22;
            if (length < 0) {
                StringBuilder sbB = c.a.a.a.a.b("File too short to be a zip file: ");
                sbB.append(randomAccessFile.length());
                throw new ZipException(sbB.toString());
            }
            long j = length - 65536;
            long j2 = j >= 0 ? j : 0L;
            int iReverseBytes = Integer.reverseBytes(101010256);
            do {
                randomAccessFile.seek(length);
                if (randomAccessFile.readInt() == iReverseBytes) {
                    randomAccessFile.skipBytes(2);
                    randomAccessFile.skipBytes(2);
                    randomAccessFile.skipBytes(2);
                    randomAccessFile.skipBytes(2);
                    d dVar = new d();
                    dVar.f39b = ((long) Integer.reverseBytes(randomAccessFile.readInt())) & 4294967295L;
                    dVar.f38a = ((long) Integer.reverseBytes(randomAccessFile.readInt())) & 4294967295L;
                    return dVar;
                }
                length--;
            } while (length >= j2);
            throw new ZipException("End Of Central Directory signature not found");
        }

        public static long d(File file) {
            long jLastModified = file.lastModified();
            return jLastModified == -1 ? jLastModified - 1 : jLastModified;
        }

        public static long e(File file) throws IOException {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            try {
                d dVarC = c(randomAccessFile);
                CRC32 crc32 = new CRC32();
                long j = dVarC.f39b;
                randomAccessFile.seek(dVarC.f38a);
                int iMin = (int) Math.min(16384L, j);
                byte[] bArr = new byte[16384];
                while (true) {
                    int i = randomAccessFile.read(bArr, 0, iMin);
                    if (i == -1) {
                        break;
                    }
                    crc32.update(bArr, 0, i);
                    j -= (long) i;
                    if (j == 0) {
                        break;
                    }
                    iMin = (int) Math.min(16384L, j);
                }
                long value = crc32.getValue();
                randomAccessFile.close();
                return value == -1 ? value - 1 : value;
            } catch (Throwable th) {
                randomAccessFile.close();
                throw th;
            }
        }

        public static boolean f(Context context, File file, long j, String str) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("multidex.version", 4);
            if (sharedPreferences.getLong(str + "timestamp", -1L) == d(file)) {
                if (sharedPreferences.getLong(str + "crc", -1L) == j) {
                    return false;
                }
            }
            return true;
        }

        /* JADX WARN: Code duplicated, block: B:26:0x00d0  */
        /* JADX WARN: Code duplicated, block: B:28:0x00ea  */
        /* JADX WARN: Code duplicated, block: B:48:0x00b0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        public static List<? extends File> g(Context context, File file, File file2, String str, boolean z) throws Throwable {
            FileChannel channel;
            List<c> listI;
            List<c> list;
            StringBuilder sbB = c.a.a.a.a.b("MultiDexExtractor.load(");
            sbB.append(file.getPath());
            sbB.append(", ");
            sbB.append(z);
            sbB.append(", ");
            sbB.append(str);
            sbB.append(")");
            Log.i("MultiDex", sbB.toString());
            long jE = e(file);
            File file3 = new File(file2, "MultiDex.lock");
            RandomAccessFile randomAccessFile = new RandomAccessFile(file3, "rw");
            FileLock fileLock = null;
            iOException = null;
            IOException iOException = null;
            fileLock = null;
            try {
                channel = randomAccessFile.getChannel();
                try {
                    Log.i("MultiDex", "Blocking on lock " + file3.getPath());
                    FileLock fileLockLock = channel.lock();
                    try {
                        Log.i("MultiDex", file3.getPath() + " locked");
                        if (!z && !f(context, file, jE, str)) {
                            try {
                                listI = h(context, file, file2, str);
                            } catch (IOException e) {
                                Log.w("MultiDex", "Failed to reload existing extracted secondary dex files, falling back to fresh extraction", e);
                                listI = i(file, file2);
                                j(context, str, d(file), jE, listI);
                            }
                            list = listI;
                            if (fileLockLock != null) {
                                try {
                                    fileLockLock.release();
                                } catch (IOException e2) {
                                    StringBuilder sbB2 = c.a.a.a.a.b("Failed to release lock on ");
                                    sbB2.append(file3.getPath());
                                    Log.e("MultiDex", sbB2.toString());
                                    iOException = e2;
                                }
                            }
                            a(channel);
                            a(randomAccessFile);
                            if (iOException == null) {
                                throw iOException;
                            }
                            StringBuilder sbB3 = c.a.a.a.a.b("load found ");
                            sbB3.append(list.size());
                            sbB3.append(" secondary dex files");
                            Log.i("MultiDex", sbB3.toString());
                            return list;
                        }
                        Log.i("MultiDex", "Detected that extraction must be performed.");
                        listI = i(file, file2);
                        j(context, str, d(file), jE, listI);
                        list = listI;
                        if (fileLockLock != null) {
                            fileLockLock.release();
                        }
                        a(channel);
                        a(randomAccessFile);
                        if (iOException == null) {
                            throw iOException;
                        }
                        StringBuilder sbB4 = c.a.a.a.a.b("load found ");
                        sbB4.append(list.size());
                        sbB4.append(" secondary dex files");
                        Log.i("MultiDex", sbB4.toString());
                        return list;
                    } catch (Throwable th) {
                        th = th;
                        fileLock = fileLockLock;
                        if (fileLock != null) {
                            try {
                                fileLock.release();
                            } catch (IOException unused) {
                                StringBuilder sbB5 = c.a.a.a.a.b("Failed to release lock on ");
                                sbB5.append(file3.getPath());
                                Log.e("MultiDex", sbB5.toString());
                            }
                        }
                        if (channel != null) {
                            a(channel);
                        }
                        a(randomAccessFile);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                channel = null;
            }
        }

        public static List<c> h(Context context, File file, File file2, String str) throws IOException {
            Log.i("MultiDex", "loading existing secondary dex files");
            String str2 = file.getName() + ".classes";
            SharedPreferences sharedPreferences = context.getSharedPreferences("multidex.version", 4);
            int i = sharedPreferences.getInt(str + "dex.number", 1);
            ArrayList arrayList = new ArrayList(i + (-1));
            int i2 = 2;
            while (i2 <= i) {
                c cVar = new c(file2, str2 + i2 + ".zip");
                if (!cVar.isFile()) {
                    StringBuilder sbB = c.a.a.a.a.b("Missing extracted secondary dex file '");
                    sbB.append(cVar.getPath());
                    sbB.append("'");
                    throw new IOException(sbB.toString());
                }
                cVar.f37a = e(cVar);
                long j = sharedPreferences.getLong(str + "dex.crc." + i2, -1L);
                long j2 = sharedPreferences.getLong(str + "dex.time." + i2, -1L);
                long jLastModified = cVar.lastModified();
                if (j2 == jLastModified) {
                    String str3 = str2;
                    SharedPreferences sharedPreferences2 = sharedPreferences;
                    if (j == cVar.f37a) {
                        arrayList.add(cVar);
                        i2++;
                        sharedPreferences = sharedPreferences2;
                        str2 = str3;
                    }
                }
                throw new IOException("Invalid extracted dex: " + cVar + " (key \"" + str + "\"), expected modification time: " + j2 + ", modification time: " + jLastModified + ", expected crc: " + j + ", file crc: " + cVar.f37a);
            }
            return arrayList;
        }

        public static List<c> i(File file, File file2) {
            String str = file.getName() + ".classes";
            File[] fileArrListFiles = file2.listFiles(new b(str));
            if (fileArrListFiles == null) {
                StringBuilder sbB = c.a.a.a.a.b("Failed to list secondary dex dir content (");
                sbB.append(file2.getPath());
                sbB.append(").");
                Log.w("MultiDex", sbB.toString());
            } else {
                for (File file3 : fileArrListFiles) {
                    StringBuilder sbB2 = c.a.a.a.a.b("Trying to delete old file ");
                    sbB2.append(file3.getPath());
                    sbB2.append(" of size ");
                    sbB2.append(file3.length());
                    Log.i("MultiDex", sbB2.toString());
                    if (file3.delete()) {
                        StringBuilder sbB3 = c.a.a.a.a.b("Deleted old file ");
                        sbB3.append(file3.getPath());
                        Log.i("MultiDex", sbB3.toString());
                    } else {
                        StringBuilder sbB4 = c.a.a.a.a.b("Failed to delete old file ");
                        sbB4.append(file3.getPath());
                        Log.w("MultiDex", sbB4.toString());
                    }
                }
            }
            ArrayList arrayList = new ArrayList();
            ZipFile zipFile = new ZipFile(file);
            int i = 2;
            String str2 = "classes2.dex";
            while (true) {
                try {
                    ZipEntry entry = zipFile.getEntry(str2);
                    if (entry == null) {
                        try {
                            zipFile.close();
                        } catch (IOException e) {
                            Log.w("MultiDex", "Failed to close resource", e);
                        }
                        return arrayList;
                    }
                    c cVar = new c(file2, str + i + ".zip");
                    arrayList.add(cVar);
                    Log.i("MultiDex", "Extraction is needed for file " + cVar);
                    int i2 = 0;
                    boolean z = false;
                    while (i2 < 3 && !z) {
                        i2++;
                        b(zipFile, entry, cVar, str);
                        try {
                            cVar.f37a = e(cVar);
                            z = true;
                        } catch (IOException e2) {
                            Log.w("MultiDex", "Failed to read crc from " + cVar.getAbsolutePath(), e2);
                            z = false;
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append("Extraction ");
                        sb.append(z ? "succeeded" : "failed");
                        sb.append(" - length ");
                        sb.append(cVar.getAbsolutePath());
                        sb.append(": ");
                        sb.append(cVar.length());
                        sb.append(" - crc: ");
                        sb.append(cVar.f37a);
                        Log.i("MultiDex", sb.toString());
                        if (!z) {
                            cVar.delete();
                            if (cVar.exists()) {
                                Log.w("MultiDex", "Failed to delete corrupted secondary dex '" + cVar.getPath() + "'");
                            }
                        }
                    }
                    if (!z) {
                        throw new IOException("Could not create zip file " + cVar.getAbsolutePath() + " for secondary dex (" + i + ")");
                    }
                    i++;
                    str2 = "classes" + i + ".dex";
                } catch (Throwable th) {
                    try {
                        zipFile.close();
                    } catch (IOException e3) {
                        Log.w("MultiDex", "Failed to close resource", e3);
                    }
                    throw th;
                }
            }
        }

        public static void j(Context context, String str, long j, long j2, List<c> list) {
            SharedPreferences.Editor editorEdit = context.getSharedPreferences("multidex.version", 4).edit();
            editorEdit.putLong(str + "timestamp", j);
            editorEdit.putLong(str + "crc", j2);
            editorEdit.putInt(str + "dex.number", list.size() + 1);
            int i = 2;
            for (c cVar : list) {
                editorEdit.putLong(str + "dex.crc." + i, cVar.f37a);
                editorEdit.putLong(str + "dex.time." + i, cVar.lastModified());
                i++;
            }
            editorEdit.commit();
        }
    }

    static {
        String property = System.getProperty("java.vm.version");
        boolean z = false;
        if (property != null) {
            Matcher matcher = Pattern.compile("(\\d+)\\.(\\d+)(\\.\\d+)?").matcher(property);
            if (matcher.matches()) {
                try {
                    int i = Integer.parseInt(matcher.group(1));
                    int i2 = Integer.parseInt(matcher.group(2));
                    if (i > 2 || (i == 2 && i2 >= 1)) {
                        z = true;
                    }
                } catch (NumberFormatException unused) {
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("VM with version ");
        sb.append(property);
        sb.append(z ? " has multidex support" : " does not have multidex support");
        Log.i("MultiDex", sb.toString());
        f35b = z;
    }

    public static void a(Object obj, String str, Object[] objArr) throws IllegalAccessException, NoSuchFieldException {
        Field fieldE = e(obj, str);
        Object[] objArr2 = (Object[]) fieldE.get(obj);
        Object[] objArr3 = (Object[]) Array.newInstance(objArr2.getClass().getComponentType(), objArr2.length + objArr.length);
        System.arraycopy(objArr2, 0, objArr3, 0, objArr2.length);
        System.arraycopy(objArr, 0, objArr3, objArr2.length, objArr.length);
        fieldE.set(obj, objArr3);
    }

    public static Method b(Object obj, String str, Class[] clsArr) throws NoSuchMethodException {
        for (Class<?> superclass = obj.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
            try {
                Method declaredMethod = superclass.getDeclaredMethod(str, clsArr);
                if (!declaredMethod.isAccessible()) {
                    declaredMethod.setAccessible(true);
                }
                return declaredMethod;
            } catch (NoSuchMethodException unused) {
            }
        }
        throw new NoSuchMethodException("Method " + str + " with parameters " + Arrays.asList(clsArr) + " not found in " + obj.getClass());
    }

    public static void c(Context context) {
        File file = new File(context.getFilesDir(), "secondary-dexes");
        if (file.isDirectory()) {
            StringBuilder sbB = c.a.a.a.a.b("Clearing old secondary dex dir (");
            sbB.append(file.getPath());
            sbB.append(").");
            Log.i("MultiDex", sbB.toString());
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles == null) {
                StringBuilder sbB2 = c.a.a.a.a.b("Failed to list secondary dex dir content (");
                sbB2.append(file.getPath());
                sbB2.append(").");
                Log.w("MultiDex", sbB2.toString());
                return;
            }
            for (File file2 : fileArrListFiles) {
                StringBuilder sbB3 = c.a.a.a.a.b("Trying to delete old file ");
                sbB3.append(file2.getPath());
                sbB3.append(" of size ");
                sbB3.append(file2.length());
                Log.i("MultiDex", sbB3.toString());
                if (file2.delete()) {
                    StringBuilder sbB4 = c.a.a.a.a.b("Deleted old file ");
                    sbB4.append(file2.getPath());
                    Log.i("MultiDex", sbB4.toString());
                } else {
                    StringBuilder sbB5 = c.a.a.a.a.b("Failed to delete old file ");
                    sbB5.append(file2.getPath());
                    Log.w("MultiDex", sbB5.toString());
                }
            }
            if (file.delete()) {
                StringBuilder sbB6 = c.a.a.a.a.b("Deleted old secondary dex dir ");
                sbB6.append(file.getPath());
                Log.i("MultiDex", sbB6.toString());
            } else {
                StringBuilder sbB7 = c.a.a.a.a.b("Failed to delete secondary dex dir ");
                sbB7.append(file.getPath());
                Log.w("MultiDex", sbB7.toString());
            }
        }
    }

    public static void d(Context context, File file, File file2, String str, String str2) {
        Set<File> set = f34a;
        synchronized (set) {
            if (set.contains(file)) {
                return;
            }
            set.add(file);
            int i = Build.VERSION.SDK_INT;
            if (i > 20) {
                Log.w("MultiDex", "MultiDex is not guaranteed to work in SDK version " + i + ": SDK version higher than 20 should be backed by runtime with built-in multidex capabilty but it's not the case here: java.vm.version=\"" + System.getProperty("java.vm.version") + "\"");
            }
            try {
                ClassLoader classLoader = context.getClassLoader();
                if (classLoader == null) {
                    Log.e("MultiDex", "Context class loader is null. Must be running in test mode. Skip patching.");
                    return;
                }
                try {
                    c(context);
                } catch (Throwable th) {
                    Log.w("MultiDex", "Something went wrong when trying to clear old MultiDex extraction, continuing without cleaning.", th);
                }
                File file3 = new File(file2, "code_cache");
                try {
                    g(file3);
                } catch (IOException unused) {
                    file3 = new File(context.getFilesDir(), "code_cache");
                    g(file3);
                }
                File file4 = new File(file3, str);
                g(file4);
                f(classLoader, file4, C0002a.g(context, file, file4, str2, false));
            } catch (RuntimeException e) {
                Log.w("MultiDex", "Failure while trying to obtain Context class loader. Must be running in test mode. Skip patching.", e);
            }
        }
    }

    public static Field e(Object obj, String str) throws NoSuchFieldException {
        for (Class<?> superclass = obj.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
            try {
                Field declaredField = superclass.getDeclaredField(str);
                if (!declaredField.isAccessible()) {
                    declaredField.setAccessible(true);
                }
                return declaredField;
            } catch (NoSuchFieldException unused) {
            }
        }
        throw new NoSuchFieldException("Field " + str + " not found in " + obj.getClass());
    }

    public static void f(ClassLoader classLoader, File file, List<? extends File> list) throws IllegalAccessException, NoSuchFieldException {
        IOException[] iOExceptionArr;
        if (list.isEmpty()) {
            return;
        }
        Object obj = e(classLoader, "pathList").get(classLoader);
        ArrayList arrayList = new ArrayList();
        a(obj, "dexElements", (Object[]) b(obj, "makeDexElements", new Class[]{ArrayList.class, File.class, ArrayList.class}).invoke(obj, new ArrayList(list), file, arrayList));
        if (arrayList.size() > 0) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Log.w("MultiDex", "Exception in makeDexElement", (IOException) it.next());
            }
            Field fieldE = e(obj, "dexElementsSuppressedExceptions");
            IOException[] iOExceptionArr2 = (IOException[]) fieldE.get(obj);
            if (iOExceptionArr2 == null) {
                iOExceptionArr = (IOException[]) arrayList.toArray(new IOException[arrayList.size()]);
            } else {
                IOException[] iOExceptionArr3 = new IOException[arrayList.size() + iOExceptionArr2.length];
                arrayList.toArray(iOExceptionArr3);
                System.arraycopy(iOExceptionArr2, 0, iOExceptionArr3, arrayList.size(), iOExceptionArr2.length);
                iOExceptionArr = iOExceptionArr3;
            }
            fieldE.set(obj, iOExceptionArr);
        }
    }

    public static void g(File file) throws IOException {
        String string;
        file.mkdir();
        if (file.isDirectory()) {
            return;
        }
        File parentFile = file.getParentFile();
        if (parentFile == null) {
            StringBuilder sbB = c.a.a.a.a.b("Failed to create dir ");
            sbB.append(file.getPath());
            sbB.append(". Parent file is null.");
            string = sbB.toString();
        } else {
            StringBuilder sbB2 = c.a.a.a.a.b("Failed to create dir ");
            sbB2.append(file.getPath());
            sbB2.append(". parent file is a dir ");
            sbB2.append(parentFile.isDirectory());
            sbB2.append(", a file ");
            sbB2.append(parentFile.isFile());
            sbB2.append(", exists ");
            sbB2.append(parentFile.exists());
            sbB2.append(", readable ");
            sbB2.append(parentFile.canRead());
            sbB2.append(", writable ");
            sbB2.append(parentFile.canWrite());
            string = sbB2.toString();
        }
        Log.e("MultiDex", string);
        StringBuilder sbB3 = c.a.a.a.a.b("Failed to create directory ");
        sbB3.append(file.getPath());
        throw new IOException(sbB3.toString());
    }
}
