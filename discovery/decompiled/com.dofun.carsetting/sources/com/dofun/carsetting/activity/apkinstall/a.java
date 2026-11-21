package com.dofun.carsetting.activity.apkinstall;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.AtomicFile;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: compiled from: EventResultPersister.java */
/* JADX INFO: loaded from: classes.dex */
class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static final String f97a = "a";
    private final AtomicFile b;
    private final Object c = new Object();
    private final SparseArray<C0010a> d = new SparseArray<>();
    private final SparseArray<b> e = new SparseArray<>();
    private int f;
    private boolean g;
    private boolean h;

    /* JADX INFO: compiled from: EventResultPersister.java */
    interface b {
        void a(int i, int i2, String str);
    }

    private static void a(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int next;
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                return;
            }
        } while (next != 1);
    }

    private static int a(XmlPullParser xmlPullParser, String str) {
        return Integer.parseInt(xmlPullParser.getAttributeValue(null, str));
    }

    private static String b(XmlPullParser xmlPullParser, String str) {
        return xmlPullParser.getAttributeValue(null, str);
    }

    a(File file) {
        this.b = new AtomicFile(file);
        this.f = -2147483647;
        try {
            FileInputStream fileInputStreamOpenRead = this.b.openRead();
            Throwable th = null;
            try {
                XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
                xmlPullParserNewPullParser.setInput(fileInputStreamOpenRead, StandardCharsets.UTF_8.name());
                a(xmlPullParserNewPullParser);
                while (xmlPullParserNewPullParser.getEventType() != 1) {
                    String name = xmlPullParserNewPullParser.getName();
                    if ("results".equals(name)) {
                        this.f = a(xmlPullParserNewPullParser, "counter");
                    } else if ("result".equals(name)) {
                        int iA = a(xmlPullParserNewPullParser, "id");
                        int iA2 = a(xmlPullParserNewPullParser, NotificationCompat.CATEGORY_STATUS);
                        int iA3 = a(xmlPullParserNewPullParser, "legacyStatus");
                        String strB = b(xmlPullParserNewPullParser, "statusMessage");
                        if (this.d.get(iA) != null) {
                            throw new Exception("id " + iA + " has two results");
                        }
                        this.d.put(iA, new C0010a(iA2, iA3, strB));
                    } else {
                        throw new Exception("unexpected tag");
                    }
                    a(xmlPullParserNewPullParser);
                }
                if (fileInputStreamOpenRead != null) {
                    fileInputStreamOpenRead.close();
                }
            } catch (Throwable th2) {
                if (fileInputStreamOpenRead != null) {
                    if (0 != 0) {
                        try {
                            fileInputStreamOpenRead.close();
                        } catch (Throwable th3) {
                            th.addSuppressed(th3);
                        }
                    } else {
                        fileInputStreamOpenRead.close();
                    }
                }
                throw th2;
            }
        } catch (Exception unused) {
            this.d.clear();
            b();
        }
    }

    void a(Context context, Intent intent) {
        int intExtra = intent.getIntExtra("android.content.pm.extra.STATUS", 0);
        if (intExtra == -1) {
            return;
        }
        int intExtra2 = intent.getIntExtra("EventResultPersister.EXTRA_ID", 0);
        String stringExtra = intent.getStringExtra("android.content.pm.extra.STATUS_MESSAGE");
        int intExtra3 = intent.getIntExtra("android.content.pm.extra.LEGACY_STATUS", 0);
        b bVarValueAt = null;
        synchronized (this.c) {
            int size = this.e.size();
            for (int i = 0; i < size; i++) {
                if (this.e.keyAt(i) == intExtra2) {
                    bVarValueAt = this.e.valueAt(i);
                    this.e.removeAt(i);
                    break;
                }
            }
            if (bVarValueAt != null) {
                bVarValueAt.a(intExtra, intExtra3, stringExtra);
            } else {
                this.d.put(intExtra2, new C0010a(intExtra, intExtra3, stringExtra));
                b();
            }
        }
    }

    private void b() {
        synchronized (this.c) {
            this.h = false;
            if (!this.g) {
                this.g = true;
                AsyncTask.execute(new Runnable() { // from class: com.dofun.carsetting.activity.apkinstall.a.1
                    @Override // java.lang.Runnable
                    public void run() {
                        int i;
                        SparseArray sparseArrayClone;
                        FileOutputStream fileOutputStreamStartWrite;
                        while (true) {
                            synchronized (a.this.c) {
                                i = a.this.f;
                                sparseArrayClone = a.this.d.clone();
                                a.this.h = true;
                            }
                            try {
                                fileOutputStreamStartWrite = a.this.b.startWrite();
                                try {
                                    XmlSerializer xmlSerializerNewSerializer = Xml.newSerializer();
                                    xmlSerializerNewSerializer.setOutput(fileOutputStreamStartWrite, StandardCharsets.UTF_8.name());
                                    xmlSerializerNewSerializer.startDocument(null, true);
                                    xmlSerializerNewSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                                    xmlSerializerNewSerializer.startTag(null, "results");
                                    xmlSerializerNewSerializer.attribute(null, "counter", Integer.toString(i));
                                    int size = sparseArrayClone.size();
                                    for (int i2 = 0; i2 < size; i2++) {
                                        xmlSerializerNewSerializer.startTag(null, "result");
                                        xmlSerializerNewSerializer.attribute(null, "id", Integer.toString(sparseArrayClone.keyAt(i2)));
                                        xmlSerializerNewSerializer.attribute(null, NotificationCompat.CATEGORY_STATUS, Integer.toString(((C0010a) sparseArrayClone.valueAt(i2)).f99a));
                                        xmlSerializerNewSerializer.attribute(null, "legacyStatus", Integer.toString(((C0010a) sparseArrayClone.valueAt(i2)).b));
                                        if (((C0010a) sparseArrayClone.valueAt(i2)).c != null) {
                                            xmlSerializerNewSerializer.attribute(null, "statusMessage", ((C0010a) sparseArrayClone.valueAt(i2)).c);
                                        }
                                        xmlSerializerNewSerializer.endTag(null, "result");
                                    }
                                    xmlSerializerNewSerializer.endTag(null, "results");
                                    xmlSerializerNewSerializer.endDocument();
                                    a.this.b.finishWrite(fileOutputStreamStartWrite);
                                } catch (IOException e) {
                                    e = e;
                                    if (fileOutputStreamStartWrite != null) {
                                        a.this.b.failWrite(fileOutputStreamStartWrite);
                                    }
                                    Log.e(a.f97a, "error writing results", e);
                                    a.this.b.delete();
                                }
                            } catch (IOException e2) {
                                e = e2;
                                fileOutputStreamStartWrite = null;
                            }
                            synchronized (a.this.c) {
                                if (a.this.h) {
                                    a.this.g = false;
                                    return;
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    /* JADX INFO: renamed from: com.dofun.carsetting.activity.apkinstall.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: EventResultPersister.java */
    private class C0010a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final int f99a;
        public final int b;
        public final String c;

        private C0010a(int i, int i2, String str) {
            this.f99a = i;
            this.b = i2;
            this.c = str;
        }
    }
}
