package com.tw.core.c;

import android.text.TextUtils;
import com.tw.core.app.DoFunApplication;
import com.tw.core.utils.C0062p;
import com.tw.core.utils.C0064r;
import com.tw.core.utils.C0065s;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: com.tw.core.c.j, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0030j {
    private final Object er = new Object();

    public abstract String jL(Object obj);

    public abstract boolean jM(Object obj);

    protected long jT() {
        return 26214400L;
    }

    protected boolean jV() {
        return false;
    }

    public abstract String jd();

    public abstract Object jw(String str);

    protected void kk(String str) throws Throwable {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        long jCE = com.tw.core.utils.E.cE(str);
        C0065s.bM("BaseDataManager", "deleteBigData fileSize = " + jCE);
        if (jCE > jT()) {
            com.tw.core.utils.E.cA(str);
        }
    }

    public void kl(List list) {
        km(list, jd());
    }

    public void km(List list, String str) {
        BufferedReader bufferedReader;
        FileReader fileReader;
        int i;
        FileReader fileReader2 = null;
        int i2 = 0;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        synchronized (this.er) {
            if (C0065s.aa) {
                C0065s.bK("BaseDataManager", "deleteData data=" + list);
            }
            if (list != null && list.size() > 0) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (com.tw.core.utils.E.cF(str)) {
                    ArrayList arrayList = new ArrayList();
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        String strJL = jL(it.next());
                        com.tw.core.utils.B.ci("BaseDataManager", "temps add json %s", strJL);
                        arrayList.add(strJL);
                    }
                    StringBuilder sb = new StringBuilder();
                    try {
                        fileReader = new FileReader(new File(str));
                        try {
                            bufferedReader = new BufferedReader(fileReader);
                            try {
                                try {
                                    String str2 = str + ".temp";
                                    i = 0;
                                    while (true) {
                                        try {
                                            String line = bufferedReader.readLine();
                                            if (line == null) {
                                                break;
                                            }
                                            boolean zJM = jM(jw(line));
                                            boolean zContains = arrayList.contains(line);
                                            if (zJM && !zContains) {
                                                sb.append(line);
                                                sb.append("\n");
                                                if (sb.length() >= 65536) {
                                                    com.tw.core.utils.E.cD(str2, sb.toString(), true);
                                                    sb.delete(0, sb.length());
                                                }
                                                i++;
                                            }
                                        } catch (Exception e) {
                                            fileReader2 = fileReader;
                                            int i3 = i;
                                            e = e;
                                            i2 = i3;
                                            try {
                                                e.printStackTrace();
                                                C0062p.bb(fileReader2);
                                                C0062p.bb(bufferedReader);
                                                i = i2;
                                            } catch (Throwable th) {
                                                th = th;
                                                fileReader = fileReader2;
                                                C0062p.bb(fileReader);
                                                C0062p.bb(bufferedReader);
                                                throw th;
                                            }
                                        }
                                    }
                                    if (com.tw.core.utils.E.cF(str2)) {
                                        if (sb.length() > 0) {
                                            com.tw.core.utils.E.cD(str2, sb.toString(), true);
                                            sb.delete(0, sb.length());
                                        }
                                        com.tw.core.utils.B.ci("BaseDataManager", "delete file %s", str);
                                        com.tw.core.utils.E.cA(str);
                                        new File(str2).renameTo(new File(str));
                                    } else {
                                        com.tw.core.utils.B.ci("BaseDataManager", "无有效缓存数据 %s", sb.toString());
                                        com.tw.core.utils.E.cD(str, sb.toString(), false);
                                    }
                                    C0062p.bb(fileReader);
                                    C0062p.bb(bufferedReader);
                                } catch (Throwable th2) {
                                    th = th2;
                                    C0062p.bb(fileReader);
                                    C0062p.bb(bufferedReader);
                                    throw th;
                                }
                            } catch (Exception e2) {
                                e = e2;
                                fileReader2 = fileReader;
                            }
                        } catch (Exception e3) {
                            e = e3;
                            bufferedReader = null;
                            fileReader2 = fileReader;
                        } catch (Throwable th3) {
                            th = th3;
                            bufferedReader = null;
                        }
                    } catch (Exception e4) {
                        e = e4;
                        bufferedReader = null;
                    } catch (Throwable th4) {
                        th = th4;
                        bufferedReader = null;
                        fileReader = null;
                    }
                    C0065s.bK("BaseDataManager", "deleteData wirte number=" + i);
                }
                if (C0065s.aa) {
                    C0065s.bK("BaseDataManager", "deleteGpsData handle time=" + (System.currentTimeMillis() - jCurrentTimeMillis));
                }
            }
        }
    }

    protected void kn() throws Throwable {
        ko(jd());
    }

    protected void ko(String str) throws Throwable {
        BufferedReader bufferedReader;
        FileReader fileReader;
        FileReader fileReader2 = null;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        String strDL = com.tw.core.utils.Q.dL(str);
        long jBE = C0064r.bE(DoFunApplication.nu(), strDL, 0L);
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (Math.abs(jCurrentTimeMillis - jBE) < 86400000) {
            C0065s.bK("BaseDataManager", "deleteExpiredData time not support");
            return;
        }
        if (com.tw.core.utils.E.cF(str)) {
            C0064r.bH(DoFunApplication.nu(), strDL, jCurrentTimeMillis);
            StringBuilder sb = new StringBuilder();
            try {
                fileReader = new FileReader(new File(str));
                try {
                    bufferedReader = new BufferedReader(fileReader);
                    try {
                        String str2 = str + ".extemp";
                        while (true) {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                break;
                            }
                            if (jM(jw(line))) {
                                com.tw.core.utils.B.ci("BaseDataManager", "deleteExpiredData %s", line);
                                sb.append(line);
                                sb.append("\n");
                                if (sb.length() >= 65536) {
                                    com.tw.core.utils.E.cD(str2, sb.toString(), true);
                                    sb.delete(0, sb.length());
                                }
                            }
                        }
                        if (com.tw.core.utils.E.cF(str2)) {
                            if (sb.length() > 0) {
                                com.tw.core.utils.E.cD(str2, sb.toString(), true);
                                sb.delete(0, sb.length());
                            }
                            com.tw.core.utils.E.cA(str);
                            new File(str2).renameTo(new File(str));
                        } else {
                            com.tw.core.utils.E.cD(str, sb.toString(), false);
                        }
                        C0062p.bb(fileReader);
                        C0062p.bb(bufferedReader);
                    } catch (Exception e) {
                        e = e;
                        fileReader2 = fileReader;
                        try {
                            e.printStackTrace();
                            C0062p.bb(fileReader2);
                            C0062p.bb(bufferedReader);
                        } catch (Throwable th) {
                            th = th;
                            fileReader = fileReader2;
                            C0062p.bb(fileReader);
                            C0062p.bb(bufferedReader);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        C0062p.bb(fileReader);
                        C0062p.bb(bufferedReader);
                        throw th;
                    }
                } catch (Exception e2) {
                    e = e2;
                    bufferedReader = null;
                    fileReader2 = fileReader;
                } catch (Throwable th3) {
                    th = th3;
                    bufferedReader = null;
                }
            } catch (Exception e3) {
                e = e3;
                bufferedReader = null;
            } catch (Throwable th4) {
                th = th4;
                bufferedReader = null;
                fileReader = null;
            }
        }
        if (C0065s.aa) {
            C0065s.bK("BaseDataManager", "deleteExpiredData handle time=" + (System.currentTimeMillis() - jCurrentTimeMillis));
        }
    }

    public List kp(int i) {
        return kq(i, jd());
    }

    public List kq(int i, String str) {
        BufferedReader bufferedReader;
        FileReader fileReader;
        FileReader fileReader2 = null;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        synchronized (this.er) {
            if (!com.tw.core.utils.E.cF(str)) {
                return null;
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            try {
                fileReader = new FileReader(new File(str));
                try {
                    bufferedReader = new BufferedReader(fileReader);
                    int i2 = 0;
                    do {
                        try {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                break;
                            }
                            Object objJw = jw(line);
                            if (objJw != null && jM(objJw) && !arrayList.contains(line)) {
                                arrayList.add(line);
                                arrayList2.add(objJw);
                                i2++;
                            }
                        } catch (Exception e) {
                            e = e;
                            fileReader2 = fileReader;
                            try {
                                e.printStackTrace();
                                C0062p.bb(fileReader2);
                                C0062p.bb(bufferedReader);
                            } catch (Throwable th) {
                                th = th;
                                fileReader = fileReader2;
                                C0062p.bb(fileReader);
                                C0062p.bb(bufferedReader);
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            C0062p.bb(fileReader);
                            C0062p.bb(bufferedReader);
                            throw th;
                        }
                    } while (i2 < i);
                    C0065s.bK("BaseDataManager", "readData count=" + i2);
                    C0062p.bb(fileReader);
                    C0062p.bb(bufferedReader);
                } catch (Exception e2) {
                    e = e2;
                    bufferedReader = null;
                    fileReader2 = fileReader;
                } catch (Throwable th3) {
                    th = th3;
                    bufferedReader = null;
                }
            } catch (Exception e3) {
                e = e3;
                bufferedReader = null;
            } catch (Throwable th4) {
                th = th4;
                bufferedReader = null;
                fileReader = null;
            }
            arrayList.clear();
            if (C0065s.aa) {
                C0065s.bK("BaseDataManager", "readData handle time=" + (System.currentTimeMillis() - jCurrentTimeMillis));
            }
            return arrayList2;
        }
    }

    public void kr(List list) {
        ks(list, jd());
    }

    public void ks(List list, String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        synchronized (this.er) {
            kk(str);
            if (jV()) {
                kn();
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (list != null && list.size() > 0) {
                if (C0065s.aa) {
                    C0065s.bK("BaseDataManager", "saveData :" + list);
                }
                int size = list.size();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < size; i++) {
                    sb.append(jL(list.get(i)));
                    if (i < size - 1) {
                        sb.append("\n");
                    }
                }
                com.tw.core.utils.E.cD(str, sb.toString(), true);
            }
            if (C0065s.aa) {
                C0065s.bK("BaseDataManager", "saveData handle time=" + (System.currentTimeMillis() - jCurrentTimeMillis));
            }
        }
    }
}
