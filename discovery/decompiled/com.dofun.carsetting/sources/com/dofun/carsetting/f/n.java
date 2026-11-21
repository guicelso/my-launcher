package com.dofun.carsetting.f;

import android.os.FileUtils;
import android.util.AtomicFile;
import android.util.Log;
import android.util.Xml;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: compiled from: ResumeAutoPlayManager.java */
/* JADX INFO: loaded from: classes.dex */
public class n {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private AtomicFile f228a;
    private List<String> b = new ArrayList();
    private a c;
    private String d;

    /* JADX INFO: compiled from: ResumeAutoPlayManager.java */
    public interface a {
        void a(List<String> list);
    }

    public n(a aVar) {
        this.c = aVar;
        if (new File("/twdataconfig/config/config_resume_auto_play.xml").canWrite()) {
            this.d = "/twdataconfig/config/config_resume_auto_play.xml";
        } else {
            this.d = "/data/tw/config_resume_auto_play.xml";
        }
        this.f228a = new AtomicFile(new File(this.d));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.util.List, java.util.List<java.lang.String>] */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r0v9, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v33, types: [org.xmlpull.v1.XmlPullParser] */
    /* JADX WARN: Type inference failed for: r6v0, types: [com.dofun.carsetting.f.n] */
    public void a() {
        String str;
        StringBuilder sb;
        String str2;
        StringBuilder sb2;
        int next;
        Log.d("ResumeAutoPlayManager", "loadAutoPlayAppList: start");
        ?? OpenRead = this.b;
        OpenRead.clear();
        try {
            try {
                OpenRead = this.f228a.openRead();
                try {
                    try {
                        try {
                            try {
                                try {
                                    ?? NewPullParser = Xml.newPullParser();
                                    NewPullParser.setInput(OpenRead, "UTF-8");
                                    do {
                                        next = NewPullParser.next();
                                        if (next == 2) {
                                            break;
                                        }
                                    } while (next != 1);
                                    if (next != 2) {
                                        Log.e("ResumeAutoPlayManager", ">>>No start tag found");
                                    }
                                    if (!NewPullParser.getName().equals("config")) {
                                        Log.e("ResumeAutoPlayManager", "Unexpected start tag found :" + NewPullParser.getName() + ", expected 'config'");
                                    }
                                    while (true) {
                                        z.b(NewPullParser);
                                        if (NewPullParser.getEventType() == 1) {
                                            try {
                                                break;
                                            } catch (IOException e) {
                                                e = e;
                                                str2 = "ResumeAutoPlayManager";
                                                sb2 = new StringBuilder();
                                                sb2.append("Fail to close stream ");
                                                sb2.append(e);
                                                Log.e(str2, sb2.toString());
                                            } catch (Exception e2) {
                                                e = e2;
                                                str = "ResumeAutoPlayManager";
                                                sb = new StringBuilder();
                                                sb.append("exception at last,e: ");
                                                sb.append(e);
                                                Log.e(str, sb.toString());
                                            }
                                        } else if (!"item".equals(NewPullParser.getName())) {
                                            z.a(NewPullParser);
                                        } else if ("ResumeAutoPlayList".equals(NewPullParser.getAttributeValue(null, "name"))) {
                                            a(NewPullParser, this.b);
                                        } else {
                                            z.a(NewPullParser);
                                        }
                                    }
                                    OpenRead.close();
                                } catch (NullPointerException e3) {
                                    Log.e("ResumeAutoPlayManager", "Failed parsing 2: " + e3);
                                    try {
                                        OpenRead.close();
                                    } catch (IOException e4) {
                                        e = e4;
                                        str2 = "ResumeAutoPlayManager";
                                        sb2 = new StringBuilder();
                                        sb2.append("Fail to close stream ");
                                        sb2.append(e);
                                        Log.e(str2, sb2.toString());
                                    } catch (Exception e5) {
                                        e = e5;
                                        str = "ResumeAutoPlayManager";
                                        sb = new StringBuilder();
                                        sb.append("exception at last,e: ");
                                        sb.append(e);
                                        Log.e(str, sb.toString());
                                    }
                                }
                            } catch (NumberFormatException e6) {
                                Log.e("ResumeAutoPlayManager", "Failed parsing 3:" + e6);
                                try {
                                    OpenRead.close();
                                } catch (IOException e7) {
                                    e = e7;
                                    str2 = "ResumeAutoPlayManager";
                                    sb2 = new StringBuilder();
                                    sb2.append("Fail to close stream ");
                                    sb2.append(e);
                                    Log.e(str2, sb2.toString());
                                } catch (Exception e8) {
                                    e = e8;
                                    str = "ResumeAutoPlayManager";
                                    sb = new StringBuilder();
                                    sb.append("exception at last,e: ");
                                    sb.append(e);
                                    Log.e(str, sb.toString());
                                }
                            }
                        } catch (IndexOutOfBoundsException e9) {
                            Log.e("ResumeAutoPlayManager", "Failed parsing 6:" + e9);
                            try {
                                OpenRead.close();
                            } catch (IOException e10) {
                                e = e10;
                                str2 = "ResumeAutoPlayManager";
                                sb2 = new StringBuilder();
                                sb2.append("Fail to close stream ");
                                sb2.append(e);
                                Log.e(str2, sb2.toString());
                            } catch (Exception e11) {
                                e = e11;
                                str = "ResumeAutoPlayManager";
                                sb = new StringBuilder();
                                sb.append("exception at last,e: ");
                                sb.append(e);
                                Log.e(str, sb.toString());
                            }
                        }
                    } catch (XmlPullParserException e12) {
                        Log.e("ResumeAutoPlayManager", "Failed parsing 4:" + e12);
                        try {
                            OpenRead.close();
                        } catch (IOException e13) {
                            e = e13;
                            str2 = "ResumeAutoPlayManager";
                            sb2 = new StringBuilder();
                            sb2.append("Fail to close stream ");
                            sb2.append(e);
                            Log.e(str2, sb2.toString());
                        } catch (Exception e14) {
                            e = e14;
                            str = "ResumeAutoPlayManager";
                            sb = new StringBuilder();
                            sb.append("exception at last,e: ");
                            sb.append(e);
                            Log.e(str, sb.toString());
                        }
                    }
                } catch (IOException e15) {
                    Log.e("ResumeAutoPlayManager", "Failed parsing 5:" + e15);
                    try {
                        OpenRead.close();
                    } catch (IOException e16) {
                        e = e16;
                        str2 = "ResumeAutoPlayManager";
                        sb2 = new StringBuilder();
                        sb2.append("Fail to close stream ");
                        sb2.append(e);
                        Log.e(str2, sb2.toString());
                    } catch (Exception e17) {
                        e = e17;
                        str = "ResumeAutoPlayManager";
                        sb = new StringBuilder();
                        sb.append("exception at last,e: ");
                        sb.append(e);
                        Log.e(str, sb.toString());
                    }
                } catch (IllegalStateException e18) {
                    Log.e("ResumeAutoPlayManager", "Failed parsing 1: " + e18);
                    try {
                        OpenRead.close();
                    } catch (IOException e19) {
                        e = e19;
                        str2 = "ResumeAutoPlayManager";
                        sb2 = new StringBuilder();
                        sb2.append("Fail to close stream ");
                        sb2.append(e);
                        Log.e(str2, sb2.toString());
                    } catch (Exception e20) {
                        e = e20;
                        str = "ResumeAutoPlayManager";
                        sb = new StringBuilder();
                        sb.append("exception at last,e: ");
                        sb.append(e);
                        Log.e(str, sb.toString());
                    }
                }
                Log.d("ResumeAutoPlayManager", "loadConfig: end");
                if (this.c != null) {
                    this.c.a(this.b);
                }
            } catch (FileNotFoundException e21) {
                Log.e("ResumeAutoPlayManager", ">>>file not found," + e21);
            }
        } catch (Throwable th) {
            try {
                OpenRead.close();
            } catch (IOException e22) {
                Log.e("ResumeAutoPlayManager", "Fail to close stream " + e22);
            } catch (Exception e23) {
                Log.e("ResumeAutoPlayManager", "exception at last,e: " + e23);
            }
            throw th;
        }
    }

    private void a(XmlPullParser xmlPullParser, List<String> list) throws XmlPullParserException, IOException {
        if (xmlPullParser == null || list == null) {
            Log.e("ResumeAutoPlayManager", "readAppList Error: null");
            return;
        }
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && xmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4 && "packageName".equals(xmlPullParser.getName())) {
                String strNextText = xmlPullParser.nextText();
                if (strNextText != null) {
                    if (list.indexOf(strNextText) < 0) {
                        list.add(strNextText);
                    }
                } else {
                    Log.w("ResumeAutoPlayManager", "<XML_PACKAGE_NAME_TAG> without text at " + xmlPullParser.getPositionDescription());
                }
            }
        }
    }

    public void a(List<String> list) {
        FileOutputStream fileOutputStreamStartWrite;
        this.b = list;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            synchronized (this) {
                XmlSerializer dVar = new d();
                dVar.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                dVar.setOutput(byteArrayOutputStream, StandardCharsets.UTF_8.name());
                a(dVar);
            }
        } catch (IOException unused) {
        }
        synchronized (this.f228a) {
            try {
                fileOutputStreamStartWrite = this.f228a.startWrite();
                try {
                    byteArrayOutputStream.writeTo(fileOutputStreamStartWrite);
                    fileOutputStreamStartWrite.flush();
                    FileUtils.sync(fileOutputStreamStartWrite);
                    fileOutputStreamStartWrite.close();
                    this.f228a.finishWrite(fileOutputStreamStartWrite);
                } catch (IOException e) {
                    e = e;
                    Log.w("ResumeAutoPlayManager", "Error writing config file", e);
                    this.f228a.failWrite(fileOutputStreamStartWrite);
                }
            } catch (IOException e2) {
                e = e2;
                fileOutputStreamStartWrite = null;
            }
        }
        FileUtils.setPermissions(this.d, 438, 1000, -1);
    }

    private void a(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.startDocument(null, true);
        xmlSerializer.startTag(null, "config");
        xmlSerializer.startTag(null, "item");
        xmlSerializer.attribute(null, "name", "ResumeAutoPlayList");
        for (int i = 0; i < this.b.size(); i++) {
            String str = this.b.get(i);
            Log.d("ResumeAutoPlayManager", "writeConfigFileLocked: pkgName:" + str);
            xmlSerializer.startTag(null, "packageName");
            xmlSerializer.text(str);
            xmlSerializer.endTag(null, "packageName");
        }
        xmlSerializer.endTag(null, "item");
        xmlSerializer.endTag(null, "config");
        xmlSerializer.endDocument();
    }
}
