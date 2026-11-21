package com.tw.core.a;

import android.net.TrafficStats;
import com.tw.core.utils.C0065s;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/* JADX INFO: loaded from: classes.dex */
public class c {
    private int bO;
    private long bP = 0;

    public c(int i) {
        this.bO = 0;
        this.bO = i;
    }

    public static long eu() {
        return TrafficStats.getMobileRxBytes();
    }

    public static long ev() {
        return eu() + ew();
    }

    public static long ew() {
        return TrafficStats.getMobileTxBytes();
    }

    public static long ex() {
        return TrafficStats.getTotalRxBytes();
    }

    public static long ey() {
        return ez() + ex();
    }

    public static long ez() {
        return TrafficStats.getTotalTxBytes();
    }

    /* JADX WARN: Code duplicated, block: B:55:0x00f8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v19 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v20 */
    /* JADX WARN: Type inference failed for: r1v21 */
    /* JADX WARN: Type inference failed for: r1v22 */
    /* JADX WARN: Type inference failed for: r1v23 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.io.RandomAccessFile] */
    public long eA() throws Throwable {
        RandomAccessFile randomAccessFile;
        long j;
        RandomAccessFile randomAccessFile2 = null;
        long uidRxBytes = TrafficStats.getUidRxBytes(this.bO);
        if (uidRxBytes == -1) {
            return -1L;
        }
        ?? r1 = "/tcp_rcv";
        try {
            try {
                randomAccessFile = new RandomAccessFile("/proc/uid_stat/" + this.bO + "/tcp_rcv", "r");
                try {
                    uidRxBytes = Long.parseLong(randomAccessFile.readLine());
                    r1 = randomAccessFile;
                    if (randomAccessFile != null) {
                        try {
                            randomAccessFile.close();
                            r1 = randomAccessFile;
                        } catch (IOException e) {
                            C0065s.bK("TrafficInfo", "Close RandomAccessFile exception: " + e.getMessage());
                            r1 = "TrafficInfo";
                        }
                    }
                } catch (FileNotFoundException e2) {
                    e = e2;
                    randomAccessFile2 = randomAccessFile;
                    try {
                        C0065s.bK("TrafficInfo", "FileNotFoundException: " + e.getMessage());
                        if (randomAccessFile2 != null) {
                            try {
                                randomAccessFile2.close();
                            } catch (IOException e3) {
                                C0065s.bK("TrafficInfo", "Close RandomAccessFile exception: " + e3.getMessage());
                                j = -1;
                                return j;
                            }
                        }
                        return -1L;
                    } catch (Throwable th) {
                        th = th;
                        r1 = randomAccessFile2;
                        if (r1 != 0) {
                            try {
                                r1.close();
                            } catch (IOException e4) {
                                C0065s.bK("TrafficInfo", "Close RandomAccessFile exception: " + e4.getMessage());
                            }
                        }
                        throw th;
                    }
                } catch (IOException e5) {
                    e = e5;
                    C0065s.bK("TrafficInfo", "IOException: " + e.getMessage());
                    e.printStackTrace();
                    r1 = randomAccessFile;
                    if (randomAccessFile != null) {
                        try {
                            randomAccessFile.close();
                            r1 = randomAccessFile;
                        } catch (IOException e6) {
                            C0065s.bK("TrafficInfo", "Close RandomAccessFile exception: " + e6.getMessage());
                            j = uidRxBytes;
                            return j;
                        }
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (r1 != 0) {
                    r1.close();
                }
                throw th;
            }
        } catch (FileNotFoundException e7) {
            e = e7;
        } catch (IOException e8) {
            e = e8;
            randomAccessFile = null;
        } catch (Throwable th3) {
            th = th3;
            r1 = 0;
            if (r1 != 0) {
                r1.close();
            }
            throw th;
        }
        return uidRxBytes;
    }

    /* JADX WARN: Code duplicated, block: B:44:0x00af A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v18 */
    /* JADX WARN: Type inference failed for: r1v19 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v20 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.io.RandomAccessFile] */
    public long eB() throws Throwable {
        RandomAccessFile randomAccessFile;
        long j;
        RandomAccessFile randomAccessFile2 = null;
        long uidTxBytes = TrafficStats.getUidTxBytes(this.bO);
        if (uidTxBytes == -1) {
            return -1L;
        }
        ?? r1 = "/tcp_snd";
        try {
            try {
                randomAccessFile = new RandomAccessFile("/proc/uid_stat/" + this.bO + "/tcp_snd", "r");
                try {
                    uidTxBytes = Long.parseLong(randomAccessFile.readLine());
                    r1 = randomAccessFile;
                    if (randomAccessFile != null) {
                        try {
                            randomAccessFile.close();
                            r1 = randomAccessFile;
                        } catch (IOException e) {
                            C0065s.bL("TrafficInfo", e);
                            r1 = "TrafficInfo";
                        }
                    }
                } catch (FileNotFoundException e2) {
                    e = e2;
                    randomAccessFile2 = randomAccessFile;
                    try {
                        C0065s.bK("TrafficInfo", "FileNotFoundException: " + e.getMessage());
                        if (randomAccessFile2 != null) {
                            try {
                                randomAccessFile2.close();
                            } catch (IOException e3) {
                                C0065s.bL("TrafficInfo", e3);
                                j = -1;
                                return j;
                            }
                        }
                        return -1L;
                    } catch (Throwable th) {
                        th = th;
                        r1 = randomAccessFile2;
                        if (r1 != 0) {
                            try {
                                r1.close();
                            } catch (IOException e4) {
                                C0065s.bL("TrafficInfo", e4);
                            }
                        }
                        throw th;
                    }
                } catch (IOException e5) {
                    e = e5;
                    C0065s.bK("TrafficInfo", "IOException: " + e.getMessage());
                    e.printStackTrace();
                    r1 = randomAccessFile;
                    if (randomAccessFile != null) {
                        try {
                            randomAccessFile.close();
                            r1 = randomAccessFile;
                        } catch (IOException e6) {
                            C0065s.bL("TrafficInfo", e6);
                            j = uidTxBytes;
                            return j;
                        }
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (r1 != 0) {
                    r1.close();
                }
                throw th;
            }
        } catch (FileNotFoundException e7) {
            e = e7;
        } catch (IOException e8) {
            e = e8;
            randomAccessFile = null;
        } catch (Throwable th3) {
            th = th3;
            r1 = 0;
            if (r1 != 0) {
                r1.close();
            }
            throw th;
        }
        return uidTxBytes;
    }

    public long eC() throws Throwable {
        long jEA = eA();
        long jEB = eB();
        if (jEA == -1 || jEB == -1) {
            return -1L;
        }
        return jEA + jEB;
    }
}
