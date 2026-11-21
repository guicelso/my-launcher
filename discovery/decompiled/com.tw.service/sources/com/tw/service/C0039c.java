package com.tw.service;

import android.os.FileUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

/* JADX INFO: renamed from: com.tw.service.c, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0039c {
    private final ArrayList pk = new ArrayList();
    private final ArrayList qk = new ArrayList();

    private void j(String str, String str2) {
        File[] fileArrListFiles;
        File file = new File(b.a.a.a.a.b(str, "/", str2));
        if (!file.isDirectory() || new File(file, ".nomedia").exists() || (fileArrListFiles = file.listFiles()) == null) {
            return;
        }
        boolean z = false;
        boolean z2 = false;
        for (File file2 : fileArrListFiles) {
            if (file2.canRead()) {
                String upperCase = file2.getName().toUpperCase(Locale.ENGLISH);
                if (!upperCase.startsWith(".")) {
                    if (!file2.isDirectory()) {
                        if (!z && (upperCase.endsWith(".AVI") || upperCase.endsWith(".ASF") || upperCase.endsWith(".WMV") || upperCase.endsWith(".M2T") || upperCase.endsWith(".MTS") || upperCase.endsWith(".TS") || upperCase.endsWith(".MPG") || upperCase.endsWith(".M2P") || upperCase.endsWith(".MP4") || upperCase.endsWith(".FLV") || upperCase.endsWith(".SWF") || upperCase.endsWith(".VOB") || upperCase.endsWith(".MKV") || upperCase.endsWith(".DIVX") || upperCase.endsWith(".XVID") || upperCase.endsWith(".MOV") || upperCase.endsWith(".RMVB") || upperCase.endsWith(".RV") || upperCase.endsWith(".3GP") || upperCase.endsWith(".PMP") || upperCase.endsWith(".TP") || upperCase.endsWith(".TRP") || upperCase.endsWith(".RM") || upperCase.endsWith(".WEBM") || upperCase.endsWith(".M2TS") || upperCase.endsWith(".SSIF") || upperCase.endsWith(".MPEG") || upperCase.endsWith(".MPE") || upperCase.endsWith(".M3U8") || upperCase.endsWith(".M4V") || upperCase.endsWith(".3G2") || upperCase.endsWith(".F4V") || upperCase.endsWith(".3GPP"))) {
                            z = true;
                        }
                        if (!z2 && (upperCase.endsWith(".MP3") || upperCase.endsWith(".WMA") || upperCase.endsWith(".AAC") || upperCase.endsWith(".OGG") || upperCase.endsWith(".PCM") || upperCase.endsWith(".M4A") || upperCase.endsWith(".AC3") || upperCase.endsWith(".EC3") || upperCase.endsWith(".DTSHD") || upperCase.endsWith(".MKA") || upperCase.endsWith(".RA") || upperCase.endsWith(".WAV") || upperCase.endsWith(".CD") || upperCase.endsWith(".AMR") || upperCase.endsWith(".MP2") || upperCase.endsWith(".APE") || upperCase.endsWith(".DTS") || upperCase.endsWith(".FLAC") || upperCase.endsWith(".MIDI") || upperCase.endsWith(".MID") || upperCase.endsWith(".MPC") || upperCase.endsWith(".TTA") || upperCase.endsWith(".ASX") || upperCase.endsWith(".AIFF") || upperCase.endsWith(".AU"))) {
                            z2 = true;
                        }
                    } else if (!upperCase.startsWith("PROGRAM") && !upperCase.startsWith("SYSTEM") && !upperCase.startsWith("IGO") && !upperCase.equals("SYGIC") && !upperCase.equals("AURA") && !upperCase.equals("PAPAGO") && !upperCase.equals("TOMTOM") && !upperCase.equals("$RECYCLE.BIN") && !upperCase.equals("WINDOWS") && !upperCase.equals("BOOT") && !upperCase.equals("LOST.DIR") && !upperCase.equals("DCIM") && !upperCase.contains("NAVI")) {
                        j(str, str2 + "/" + file2.getName());
                    }
                }
            }
        }
        if (z) {
            this.pk.add(str2);
        }
        if (z2) {
            this.qk.add(str2);
        }
    }

    /* JADX WARN: Code duplicated, block: B:39:0x00f6 A[Catch: all -> 0x010a, Exception -> 0x010d, LOOP:1: B:37:0x00f0->B:39:0x00f6, LOOP_END, TryCatch #12 {Exception -> 0x010d, all -> 0x010a, blocks: (B:36:0x00ea, B:37:0x00f0, B:39:0x00f6, B:40:0x0103), top: B:65:0x00ea }] */
    public void g(String str) throws Throwable {
        String string;
        BufferedWriter bufferedWriter;
        BufferedWriter bufferedWriter2;
        Iterator it;
        StringBuilder sb;
        String strSubstring;
        this.pk.clear();
        this.qk.clear();
        BufferedWriter bufferedWriter3 = null;
        try {
            j(str, ".");
            if (str.startsWith("/storage/usb") || str.startsWith("/storage/extsd")) {
                sb = new StringBuilder();
                sb.append("/data/tw/");
                strSubstring = str.substring(9);
            } else {
                sb = new StringBuilder();
                sb.append(str);
                strSubstring = "/DCIM";
            }
            sb.append(strSubstring);
            string = sb.toString();
            try {
                new File(string).mkdir();
                FileUtils.setPermissions(string, 493, -1, -1);
            } catch (Exception unused) {
            }
        } catch (Exception unused2) {
            string = null;
        }
        new File(b.a.a.a.a.a(string, "/.video")).delete();
        try {
            try {
                bufferedWriter = new BufferedWriter(new FileWriter(string + "/.video"));
                try {
                    Iterator it2 = this.pk.iterator();
                    while (it2.hasNext()) {
                        bufferedWriter.write((String) it2.next());
                        bufferedWriter.write(10);
                    }
                    bufferedWriter.flush();
                } catch (Exception unused3) {
                    if (bufferedWriter != null) {
                    }
                    FileUtils.setPermissions(string + "/.video", 438, -1, -1);
                    this.pk.clear();
                    new File(b.a.a.a.a.a(string, "/.music")).delete();
                    try {
                        bufferedWriter2 = new BufferedWriter(new FileWriter(string + "/.music"));
                        try {
                            it = this.qk.iterator();
                            while (it.hasNext()) {
                                bufferedWriter2.write((String) it.next());
                                bufferedWriter2.write(10);
                            }
                            bufferedWriter2.flush();
                            bufferedWriter2.close();
                        } catch (Exception unused4) {
                            bufferedWriter3 = bufferedWriter2;
                            if (bufferedWriter3 != null) {
                                bufferedWriter3.close();
                            }
                        } catch (Throwable th) {
                            th = th;
                            bufferedWriter3 = bufferedWriter2;
                            if (bufferedWriter3 != null) {
                                bufferedWriter3.close();
                            }
                            throw th;
                        }
                        FileUtils.setPermissions(string + "/.music", 438, -1, -1);
                    } catch (Exception unused5) {
                    }
                    this.qk.clear();
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                    throw th;
                }
            } catch (Exception unused6) {
            }
        } catch (Exception unused7) {
            bufferedWriter = null;
        } catch (Throwable th3) {
            th = th3;
            bufferedWriter = null;
        }
        bufferedWriter.close();
        FileUtils.setPermissions(string + "/.video", 438, -1, -1);
        this.pk.clear();
        new File(b.a.a.a.a.a(string, "/.music")).delete();
        try {
            bufferedWriter2 = new BufferedWriter(new FileWriter(string + "/.music"));
            it = this.qk.iterator();
            while (it.hasNext()) {
                bufferedWriter2.write((String) it.next());
                bufferedWriter2.write(10);
            }
            bufferedWriter2.flush();
            bufferedWriter2.close();
        } catch (Exception unused8) {
        } catch (Throwable th4) {
            th = th4;
        }
        FileUtils.setPermissions(string + "/.music", 438, -1, -1);
        this.qk.clear();
    }
}
