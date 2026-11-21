package a.b.c;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
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
    public static final Set<File> f33a = new HashSet();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final boolean f34b;

    /* JADX INFO: renamed from: a.b.c.a$a, reason: collision with other inner class name */
    private static final class C0000a {
        public static long a(File file) {
            long jLastModified = file.lastModified();
            return jLastModified == -1 ? jLastModified - 1 : jLastModified;
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        public static d a(RandomAccessFile randomAccessFile) throws IOException {
            long length = randomAccessFile.length() - 22;
            if (length < 0) {
                StringBuilder sbA = c.a.a.a.a.a("File too short to be a zip file: ");
                sbA.append(randomAccessFile.length());
                throw new ZipException(sbA.toString());
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
                    dVar.f38b = ((long) Integer.reverseBytes(randomAccessFile.readInt())) & 4294967295L;
                    dVar.f37a = ((long) Integer.reverseBytes(randomAccessFile.readInt())) & 4294967295L;
                    return dVar;
                }
                length--;
            } while (length >= j2);
            throw new ZipException("End Of Central Directory signature not found");
        }

        public static SharedPreferences a(Context context) {
            int i = Build.VERSION.SDK_INT;
            return context.getSharedPreferences("multidex.version", 4);
        }

        public static List<c> a(Context context, File file, File file2, String str) throws IOException {
            Log.i("MultiDex", "loading existing secondary dex files");
            String str2 = file.getName() + ".classes";
            SharedPreferences sharedPreferencesA = a(context);
            int i = sharedPreferencesA.getInt(str + "dex.number", 1);
            ArrayList arrayList = new ArrayList(i + (-1));
            int i2 = 2;
            while (i2 <= i) {
                c cVar = new c(file2, str2 + i2 + ".zip");
                if (!cVar.isFile()) {
                    StringBuilder sbA = c.a.a.a.a.a("Missing extracted secondary dex file '");
                    sbA.append(cVar.getPath());
                    sbA.append("'");
                    throw new IOException(sbA.toString());
                }
                cVar.f36a = b(cVar);
                long j = sharedPreferencesA.getLong(str + "dex.crc." + i2, -1L);
                long j2 = sharedPreferencesA.getLong(str + "dex.time." + i2, -1L);
                long jLastModified = cVar.lastModified();
                if (j2 == jLastModified) {
                    String str3 = str2;
                    SharedPreferences sharedPreferences = sharedPreferencesA;
                    if (j == cVar.f36a) {
                        arrayList.add(cVar);
                        i2++;
                        sharedPreferencesA = sharedPreferences;
                        str2 = str3;
                    }
                }
                throw new IOException("Invalid extracted dex: " + cVar + " (key \"" + str + "\"), expected modification time: " + j2 + ", modification time: " + jLastModified + ", expected crc: " + j + ", file crc: " + cVar.f36a);
            }
            return arrayList;
        }

        /* JADX WARN: Code duplicated, block: B:26:0x00d4  */
        /* JADX WARN: Code duplicated, block: B:28:0x00ee  */
        /* JADX WARN: Code duplicated, block: B:46:0x00b4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        public static List<? extends File> a(Context context, File file, File file2, String str, boolean z) throws Throwable {
            FileChannel channel;
            List<c> listA;
            List<c> list;
            StringBuilder sbA = c.a.a.a.a.a("MultiDexExtractor.load(");
            sbA.append(file.getPath());
            sbA.append(", ");
            sbA.append(z);
            sbA.append(", ");
            sbA.append(str);
            sbA.append(")");
            Log.i("MultiDex", sbA.toString());
            long jB = b(file);
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
                        if (!z && !a(context, file, jB, str)) {
                            try {
                                listA = a(context, file, file2, str);
                            } catch (IOException e) {
                                Log.w("MultiDex", "Failed to reload existing extracted secondary dex files, falling back to fresh extraction", e);
                                listA = a(file, file2);
                                a(context, str, a(file), jB, listA);
                            }
                            list = listA;
                            if (fileLockLock != null) {
                                try {
                                    fileLockLock.release();
                                } catch (IOException e2) {
                                    StringBuilder sbA2 = c.a.a.a.a.a("Failed to release lock on ");
                                    sbA2.append(file3.getPath());
                                    Log.e("MultiDex", sbA2.toString());
                                    iOException = e2;
                                }
                            }
                            a(channel);
                            a((Closeable) randomAccessFile);
                            if (iOException == null) {
                                throw iOException;
                            }
                            StringBuilder sbA3 = c.a.a.a.a.a("load found ");
                            sbA3.append(list.size());
                            sbA3.append(" secondary dex files");
                            Log.i("MultiDex", sbA3.toString());
                            return list;
                        }
                        Log.i("MultiDex", "Detected that extraction must be performed.");
                        listA = a(file, file2);
                        a(context, str, a(file), jB, listA);
                        list = listA;
                        if (fileLockLock != null) {
                            fileLockLock.release();
                        }
                        a(channel);
                        a((Closeable) randomAccessFile);
                        if (iOException == null) {
                            throw iOException;
                        }
                        StringBuilder sbA4 = c.a.a.a.a.a("load found ");
                        sbA4.append(list.size());
                        sbA4.append(" secondary dex files");
                        Log.i("MultiDex", sbA4.toString());
                        return list;
                    } catch (Throwable th) {
                        th = th;
                        fileLock = fileLockLock;
                        if (fileLock != null) {
                            try {
                                fileLock.release();
                            } catch (IOException unused) {
                                StringBuilder sbA5 = c.a.a.a.a.a("Failed to release lock on ");
                                sbA5.append(file3.getPath());
                                Log.e("MultiDex", sbA5.toString());
                            }
                        }
                        if (channel != null) {
                            a(channel);
                        }
                        a((Closeable) randomAccessFile);
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

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        public static List<c> a(File file, File file2) {
            String str = file.getName() + ".classes";
            File[] fileArrListFiles = file2.listFiles(new b(str));
            if (fileArrListFiles == null) {
                StringBuilder sbA = c.a.a.a.a.a("Failed to list secondary dex dir content (");
                sbA.append(file2.getPath());
                sbA.append(").");
                Log.w("MultiDex", sbA.toString());
            } else {
                for (File file3 : fileArrListFiles) {
                    StringBuilder sbA2 = c.a.a.a.a.a("Trying to delete old file ");
                    sbA2.append(file3.getPath());
                    sbA2.append(" of size ");
                    sbA2.append(file3.length());
                    Log.i("MultiDex", sbA2.toString());
                    if (file3.delete()) {
                        StringBuilder sbA3 = c.a.a.a.a.a("Deleted old file ");
                        sbA3.append(file3.getPath());
                        Log.i("MultiDex", sbA3.toString());
                    } else {
                        StringBuilder sbA4 = c.a.a.a.a.a("Failed to delete old file ");
                        sbA4.append(file3.getPath());
                        Log.w("MultiDex", sbA4.toString());
                    }
                }
            }
            ArrayList arrayList = new ArrayList();
            ZipFile zipFile = new ZipFile(file);
            try {
                ZipEntry entry = zipFile.getEntry("classes2.dex");
                int i = 2;
                while (entry != null) {
                    c cVar = new c(file2, str + i + ".zip");
                    arrayList.add(cVar);
                    Log.i("MultiDex", "Extraction is needed for file " + cVar);
                    int i2 = 0;
                    boolean z = false;
                    while (i2 < 3 && !z) {
                        i2++;
                        a(zipFile, entry, cVar, str);
                        try {
                            cVar.f36a = b(cVar);
                            z = true;
                        } catch (IOException e) {
                            Log.w("MultiDex", "Failed to read crc from " + cVar.getAbsolutePath(), e);
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
                        sb.append(cVar.f36a);
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
                    entry = zipFile.getEntry("classes" + i + ".dex");
                }
                try {
                    zipFile.close();
                } catch (IOException e2) {
                    Log.w("MultiDex", "Failed to close resource", e2);
                }
                return arrayList;
            } catch (Throwable th) {
                try {
                    zipFile.close();
                } catch (IOException e3) {
                    Log.w("MultiDex", "Failed to close resource", e3);
                }
                throw th;
            }
        }

        public static void a(Context context, String str, long j, long j2, List<c> list) {
            SharedPreferences.Editor editorEdit = a(context).edit();
            editorEdit.putLong(str + "timestamp", j);
            editorEdit.putLong(str + "crc", j2);
            editorEdit.putInt(str + "dex.number", list.size() + 1);
            int i = 2;
            for (c cVar : list) {
                editorEdit.putLong(str + "dex.crc." + i, cVar.f36a);
                editorEdit.putLong(str + "dex.time." + i, cVar.lastModified());
                i++;
            }
            editorEdit.commit();
        }

        public static void a(Closeable closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                Log.w("MultiDex", "Failed to close resource", e);
            }
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        public static void a(ZipFile zipFile, ZipEntry zipEntry, File file, String str) throws IOException {
            InputStream inputStream = zipFile.getInputStream(zipEntry);
            File fileCreateTempFile = File.createTempFile("tmp-" + str, ".zip", file.getParentFile());
            StringBuilder sbA = c.a.a.a.a.a("Extracting ");
            sbA.append(fileCreateTempFile.getPath());
            Log.i("MultiDex", sbA.toString());
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

        public static boolean a(Context context, File file, long j, String str) {
            SharedPreferences sharedPreferencesA = a(context);
            if (sharedPreferencesA.getLong(str + "timestamp", -1L) == a(file)) {
                if (sharedPreferencesA.getLong(str + "crc", -1L) == j) {
                    return false;
                }
            }
            return true;
        }

        /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
        public static long b(File file) throws IOException {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            try {
                d dVarA = a(randomAccessFile);
                CRC32 crc32 = new CRC32();
                long j = dVarA.f38b;
                randomAccessFile.seek(dVarA.f37a);
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
        f34b = z;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static Field a(Object obj, String str) throws NoSuchFieldException {
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

    public static void a(Context context) {
        File file = new File(context.getFilesDir(), "secondary-dexes");
        if (file.isDirectory()) {
            StringBuilder sbA = c.a.a.a.a.a("Clearing old secondary dex dir (");
            sbA.append(file.getPath());
            sbA.append(").");
            Log.i("MultiDex", sbA.toString());
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles == null) {
                StringBuilder sbA2 = c.a.a.a.a.a("Failed to list secondary dex dir content (");
                sbA2.append(file.getPath());
                sbA2.append(").");
                Log.w("MultiDex", sbA2.toString());
                return;
            }
            for (File file2 : fileArrListFiles) {
                StringBuilder sbA3 = c.a.a.a.a.a("Trying to delete old file ");
                sbA3.append(file2.getPath());
                sbA3.append(" of size ");
                sbA3.append(file2.length());
                Log.i("MultiDex", sbA3.toString());
                if (file2.delete()) {
                    StringBuilder sbA4 = c.a.a.a.a.a("Deleted old file ");
                    sbA4.append(file2.getPath());
                    Log.i("MultiDex", sbA4.toString());
                } else {
                    StringBuilder sbA5 = c.a.a.a.a.a("Failed to delete old file ");
                    sbA5.append(file2.getPath());
                    Log.w("MultiDex", sbA5.toString());
                }
            }
            if (file.delete()) {
                StringBuilder sbA6 = c.a.a.a.a.a("Deleted old secondary dex dir ");
                sbA6.append(file.getPath());
                Log.i("MultiDex", sbA6.toString());
            } else {
                StringBuilder sbA7 = c.a.a.a.a.a("Failed to delete secondary dex dir ");
                sbA7.append(file.getPath());
                Log.w("MultiDex", sbA7.toString());
            }
        }
    }

    public static void a(File file) throws IOException {
        String string;
        file.mkdir();
        if (file.isDirectory()) {
            return;
        }
        File parentFile = file.getParentFile();
        if (parentFile == null) {
            StringBuilder sbA = c.a.a.a.a.a("Failed to create dir ");
            sbA.append(file.getPath());
            sbA.append(". Parent file is null.");
            string = sbA.toString();
        } else {
            StringBuilder sbA2 = c.a.a.a.a.a("Failed to create dir ");
            sbA2.append(file.getPath());
            sbA2.append(". parent file is a dir ");
            sbA2.append(parentFile.isDirectory());
            sbA2.append(", a file ");
            sbA2.append(parentFile.isFile());
            sbA2.append(", exists ");
            sbA2.append(parentFile.exists());
            sbA2.append(", readable ");
            sbA2.append(parentFile.canRead());
            sbA2.append(", writable ");
            sbA2.append(parentFile.canWrite());
            string = sbA2.toString();
        }
        Log.e("MultiDex", string);
        StringBuilder sbA3 = c.a.a.a.a.a("Failed to create directory ");
        sbA3.append(file.getPath());
        throw new IOException(sbA3.toString());
    }

    public static /* synthetic */ void a(Object obj, String str, Object[] objArr) throws IllegalAccessException, NoSuchFieldException {
        Field fieldA = a(obj, str);
        Object[] objArr2 = (Object[]) fieldA.get(obj);
        Object[] objArr3 = (Object[]) Array.newInstance(objArr2.getClass().getComponentType(), objArr2.length + objArr.length);
        System.arraycopy(objArr2, 0, objArr3, 0, objArr2.length);
        System.arraycopy(objArr, 0, objArr3, objArr2.length, objArr.length);
        fieldA.set(obj, objArr3);
    }

    public static void b(Context context) {
        ApplicationInfo applicationInfo;
        String str;
        Log.i("MultiDex", "Installing application");
        if (f34b) {
            str = "VM has multidex support, MultiDex support library is disabled.";
        } else {
            int i = Build.VERSION.SDK_INT;
            try {
                try {
                    applicationInfo = context.getApplicationInfo();
                } catch (RuntimeException e) {
                    Log.w("MultiDex", "Failure while trying to obtain ApplicationInfo from Context. Must be running in test mode. Skip patching.", e);
                    applicationInfo = null;
                }
                if (applicationInfo == null) {
                    Log.i("MultiDex", "No ApplicationInfo available, i.e. running on a test Context: MultiDex support library is disabled.");
                    return;
                } else {
                    a(context, new File(applicationInfo.sourceDir), new File(applicationInfo.dataDir), "secondary-dexes", "");
                    str = "install done";
                }
            } catch (Exception e2) {
                Log.e("MultiDex", "MultiDex installation failure", e2);
                StringBuilder sbA = c.a.a.a.a.a("MultiDex installation failed (");
                sbA.append(e2.getMessage());
                sbA.append(").");
                throw new RuntimeException(sbA.toString());
            }
        }
        Log.i("MultiDex", str);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static /* synthetic */ Method a(Object obj, String str, Class[] clsArr) throws NoSuchMethodException {
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

    public static void a(Context context, File file, File file2, String str, String str2) {
        synchronized (f33a) {
            if (f33a.contains(file)) {
                return;
            }
            f33a.add(file);
            if (Build.VERSION.SDK_INT > 20) {
                Log.w("MultiDex", "MultiDex is not guaranteed to work in SDK version " + Build.VERSION.SDK_INT + ": SDK version higher than 20 should be backed by runtime with built-in multidex capabilty but it's not the case here: java.vm.version=\"" + System.getProperty("java.vm.version") + "\"");
            }
            try {
                ClassLoader classLoader = context.getClassLoader();
                if (classLoader == null) {
                    Log.e("MultiDex", "Context class loader is null. Must be running in test mode. Skip patching.");
                    return;
                }
                try {
                    a(context);
                } catch (Throwable th) {
                    Log.w("MultiDex", "Something went wrong when trying to clear old MultiDex extraction, continuing without cleaning.", th);
                }
                File file3 = new File(file2, "code_cache");
                try {
                    a(file3);
                } catch (IOException unused) {
                    file3 = new File(context.getFilesDir(), "code_cache");
                    a(file3);
                }
                File file4 = new File(file3, str);
                a(file4);
                a(classLoader, file4, C0000a.a(context, file, file4, str2, false));
            } catch (RuntimeException e) {
                Log.w("MultiDex", "Failure while trying to obtain Context class loader. Must be running in test mode. Skip patching.", e);
            }
        }
    }

    public static void a(ClassLoader classLoader, File file, List<? extends File> list) throws IllegalAccessException, NoSuchFieldException {
        IOException[] iOExceptionArr;
        if (list.isEmpty()) {
            return;
        }
        int i = Build.VERSION.SDK_INT;
        Object obj = a(classLoader, "pathList").get(classLoader);
        ArrayList arrayList = new ArrayList();
        a(obj, "dexElements", (Object[]) a(obj, "makeDexElements", new Class[]{ArrayList.class, File.class, ArrayList.class}).invoke(obj, new ArrayList(list), file, arrayList));
        if (arrayList.size() > 0) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Log.w("MultiDex", "Exception in makeDexElement", (IOException) it.next());
            }
            Field fieldA = a(obj, "dexElementsSuppressedExceptions");
            IOException[] iOExceptionArr2 = (IOException[]) fieldA.get(obj);
            if (iOExceptionArr2 == null) {
                iOExceptionArr = (IOException[]) arrayList.toArray(new IOException[arrayList.size()]);
            } else {
                IOException[] iOExceptionArr3 = new IOException[arrayList.size() + iOExceptionArr2.length];
                arrayList.toArray(iOExceptionArr3);
                System.arraycopy(iOExceptionArr2, 0, iOExceptionArr3, arrayList.size(), iOExceptionArr2.length);
                iOExceptionArr = iOExceptionArr3;
            }
            fieldA.set(obj, iOExceptionArr);
        }
    }
}
