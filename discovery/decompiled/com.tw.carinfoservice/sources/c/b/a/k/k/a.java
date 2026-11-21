package c.b.a.k.k;

import android.content.Context;
import android.util.Log;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static int f444a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static int f445b;
    public static String g;
    public static String h;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static HashMap<Integer, int[]> f446c = new HashMap<>();
    public static HashMap<Integer, Integer> d = new HashMap<>();
    public static int e = 0;
    public static int f = 0;
    public static int i = 0;
    public static int j = -1;
    public static int k = 0;
    public static int l = 0;
    public static int m = 0;

    public static c.b.a.e.a a(XmlPullParser xmlPullParser, Context context) throws XmlPullParserException, IOException {
        c.b.a.e.a aVar = new c.b.a.e.a();
        if (d.size() == 0) {
            d.put(0, Integer.valueOf(aVar.f398c));
        } else {
            aVar.f398c = d.get(0).intValue();
        }
        aVar.z = f444a;
        if (xmlPullParser.getAttributeCount() >= 1) {
            for (int i2 = 0; i2 < xmlPullParser.getAttributeCount(); i2++) {
                if ("Features".equals(xmlPullParser.getAttributeName(i2))) {
                    aVar.f397b = Integer.parseInt(xmlPullParser.getAttributeValue(i2));
                } else if (xmlPullParser.getAttributeName(i2).equals("dataType-receive")) {
                    aVar.f396a = c.b.a.l.a.a(xmlPullParser.getAttributeValue(i2));
                } else if (xmlPullParser.getAttributeName(i2).equals("dataType-send")) {
                    aVar.z = c.b.a.l.a.a(xmlPullParser.getAttributeValue(i2));
                } else if (xmlPullParser.getAttributeName(i2).equals("KeyDownType")) {
                    aVar.C = c.b.a.l.a.h(xmlPullParser.getAttributeValue(i2).split(";"));
                } else if (xmlPullParser.getAttributeName(i2).equals("KeyUpType")) {
                    aVar.D = c.b.a.l.a.h(xmlPullParser.getAttributeValue(i2).split(";"));
                } else if (xmlPullParser.getAttributeName(i2).equals("SendData")) {
                    aVar.A = c.b.a.l.a.h(xmlPullParser.getAttributeValue(i2).split(";"));
                } else if (xmlPullParser.getAttributeName(i2).equals("SendType")) {
                    aVar.B = c.b.a.l.a.g(xmlPullParser.getAttributeValue(i2).split(";"));
                } else if (xmlPullParser.getAttributeName(i2).equals("package")) {
                    aVar.E = xmlPullParser.getAttributeValue(i2);
                } else if (xmlPullParser.getAttributeName(i2).equals("activity")) {
                    aVar.F = xmlPullParser.getAttributeValue(i2);
                } else if ("Bundle_key".equals(xmlPullParser.getAttributeName(i2))) {
                    aVar.J = xmlPullParser.getAttributeValue(i2);
                } else if ("Bundle_int_key".equals(xmlPullParser.getAttributeName(i2))) {
                    aVar.K = xmlPullParser.getAttributeValue(i2).split(";");
                } else if ("Bundle_string_value".equals(xmlPullParser.getAttributeName(i2))) {
                    aVar.M = xmlPullParser.getAttributeValue(i2).split(";");
                } else if ("Bundle_int_value".equals(xmlPullParser.getAttributeName(i2))) {
                    aVar.L = c.b.a.l.a.g(xmlPullParser.getAttributeValue(i2).split(";"));
                } else if ("VoiceControlId".equals(xmlPullParser.getAttributeName(i2))) {
                    aVar.I = c.b.a.l.a.g(xmlPullParser.getAttributeValue(i2).split(";"));
                }
            }
        }
        xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                return aVar;
            }
            if (next != 2) {
                if (next == 3 && "item".equals(xmlPullParser.getName())) {
                    StringBuilder sbB = c.a.a.a.a.b("END_TAG parserItemInfo:");
                    sbB.append(aVar.toString());
                    Log.d("LS123", sbB.toString());
                    return aVar;
                }
            } else if (xmlPullParser.getName().contains("Data")) {
                String name = xmlPullParser.getName();
                xmlPullParser.next();
                if (xmlPullParser.getText().contains("0x")) {
                    aVar.x = c.b.a.l.a.a(xmlPullParser.getText());
                    aVar.y = Integer.parseInt(name.substring(4));
                } else {
                    int[] iArrE = c.b.a.l.a.e(xmlPullParser.getText());
                    int i3 = Integer.parseInt(name.substring(4));
                    int[] iArr = aVar.d;
                    if (iArr == null) {
                        aVar.d = new int[1];
                    } else {
                        aVar.d = new int[iArr.length + 1];
                        for (int i4 = 0; i4 < iArr.length; i4++) {
                            aVar.d[i4] = iArr[i4];
                        }
                    }
                    int[] iArr2 = aVar.d;
                    iArr2[iArr2.length - 1] = i3;
                    aVar.e.put(Integer.valueOf(Integer.parseInt(name.substring(4))), iArrE);
                }
            } else if (xmlPullParser.getName().equals("Calculation")) {
                xmlPullParser.next();
                aVar.w = xmlPullParser.getText().split(",");
            } else if (xmlPullParser.getName().equals("IsEffective")) {
                xmlPullParser.next();
                Integer.valueOf(xmlPullParser.getText()).intValue();
            } else if (xmlPullParser.getName().contains("Effective")) {
                String name2 = xmlPullParser.getName();
                xmlPullParser.next();
                c.b.a.l.a.e(xmlPullParser.getText());
                Integer.parseInt(name2.substring(9));
            } else if (xmlPullParser.getName().equals("Unit")) {
                xmlPullParser.next();
                aVar.m = xmlPullParser.getText();
            } else if (xmlPullParser.getName().equals("Start")) {
                xmlPullParser.next();
                aVar.j = Double.valueOf(xmlPullParser.getText()).doubleValue();
            } else {
                if (!xmlPullParser.getName().equals("Step")) {
                    if (xmlPullParser.getName().equals("Convert")) {
                        xmlPullParser.next();
                        aVar.l = Integer.valueOf(xmlPullParser.getText()).intValue();
                    } else if (!xmlPullParser.getName().equals("Step")) {
                        if ("entries".equals(xmlPullParser.getName())) {
                            xmlPullParser.next();
                            Log.e("LS123", "parserItemInfo: " + xmlPullParser.getText());
                            a.b.e.b.a.h(context, xmlPullParser.getText());
                        } else if ("Units".equals(xmlPullParser.getName())) {
                            xmlPullParser.next();
                            aVar.p = a.b.e.b.a.h(context, xmlPullParser.getText());
                        } else if ("Starts".equals(xmlPullParser.getName())) {
                            xmlPullParser.next();
                            String[] strArrH = a.b.e.b.a.h(context, xmlPullParser.getText());
                            double[] dArr = new double[strArrH.length];
                            for (int i5 = 0; i5 < strArrH.length; i5++) {
                                dArr[i5] = Double.valueOf(strArrH[i5]).doubleValue();
                            }
                            aVar.n = dArr;
                        } else if ("Steps".equals(xmlPullParser.getName())) {
                            xmlPullParser.next();
                            String[] strArrH2 = a.b.e.b.a.h(context, xmlPullParser.getText());
                            double[] dArr2 = new double[strArrH2.length];
                            for (int i6 = 0; i6 < strArrH2.length; i6++) {
                                dArr2[i6] = Double.valueOf(strArrH2[i6]).doubleValue();
                            }
                            aVar.o = dArr2;
                        } else if ("Converts".equals(xmlPullParser.getName())) {
                            xmlPullParser.next();
                            String[] strArrH3 = a.b.e.b.a.h(context, xmlPullParser.getText());
                            int[] iArr3 = new int[strArrH3.length];
                            for (int i7 = 0; i7 < strArrH3.length; i7++) {
                                iArr3[i7] = Integer.valueOf(strArrH3[i7]).intValue();
                            }
                            aVar.q = iArr3;
                        } else if (xmlPullParser.getName().contains("unit")) {
                            String name3 = xmlPullParser.getName();
                            xmlPullParser.next();
                            if (xmlPullParser.getText().contains("0x")) {
                                aVar.t = c.b.a.l.a.a(xmlPullParser.getText());
                                aVar.u = Integer.parseInt(name3.substring(4));
                            } else {
                                aVar.r = Integer.parseInt(name3.substring(4));
                                aVar.s = c.b.a.l.a.e(xmlPullParser.getText());
                            }
                        } else if ("entryValues".equals(xmlPullParser.getName())) {
                            xmlPullParser.next();
                            String[] strArrH4 = a.b.e.b.a.h(context, xmlPullParser.getText());
                            int length = strArrH4.length;
                            int[] iArr4 = new int[length];
                            for (int i8 = 0; i8 < length; i8++) {
                                if (strArrH4[i8].equals("")) {
                                    iArr4 = new int[]{Integer.parseInt(strArrH4[1])};
                                    break;
                                }
                                iArr4[i8] = Integer.parseInt(strArrH4[i8]);
                            }
                            aVar.g = iArr4;
                        } else if ("stateValues".equals(xmlPullParser.getName())) {
                            xmlPullParser.next();
                            aVar.h = a.b.e.b.a.h(context, xmlPullParser.getText());
                        } else if ("UnitValues".equals(xmlPullParser.getName())) {
                            xmlPullParser.next();
                            String[] strArrH5 = a.b.e.b.a.h(context, xmlPullParser.getText());
                            int length2 = strArrH5.length;
                            int[] iArr5 = new int[length2];
                            for (int i9 = 0; i9 < length2; i9++) {
                                if (strArrH5[i9].equals("")) {
                                    iArr5 = new int[]{Integer.parseInt(strArrH5[1])};
                                    break;
                                }
                                iArr5[i9] = Integer.parseInt(strArrH5[i9]);
                            }
                            aVar.i = iArr5;
                        } else if ("type".equals(xmlPullParser.getName())) {
                            xmlPullParser.next();
                            aVar.G = Integer.parseInt(xmlPullParser.getText());
                        } else if ("UpperLimit".equals(xmlPullParser.getName())) {
                            xmlPullParser.next();
                            aVar.N = Integer.parseInt(xmlPullParser.getText());
                        } else if ("LowerLimit".equals(xmlPullParser.getName())) {
                            xmlPullParser.next();
                            aVar.O = Integer.parseInt(xmlPullParser.getText());
                        } else if ("datatype".equals(xmlPullParser.getName())) {
                            xmlPullParser.next();
                            aVar.H = Integer.parseInt(xmlPullParser.getText());
                        } else if ("Model".equals(xmlPullParser.getName())) {
                            xmlPullParser.next();
                            aVar.P = c.b.a.l.a.h(xmlPullParser.getText().split(","));
                        } else if ("Supplier".equals(xmlPullParser.getName())) {
                            xmlPullParser.next();
                            aVar.Q = c.b.a.l.a.h(xmlPullParser.getText().split(","));
                        } else if ("Ohter".equals(xmlPullParser.getName())) {
                            xmlPullParser.next();
                            String[] strArrSplit = xmlPullParser.getText().split(",");
                            int i10 = c.b.a.l.a.f447a;
                            String[] strArr = new String[strArrSplit.length];
                            for (int i11 = 0; i11 < strArrSplit.length; i11++) {
                                try {
                                    strArr[i11] = strArrSplit[i11].substring(0, strArrSplit[i11].length());
                                } catch (Exception unused) {
                                    strArr = new String[0];
                                }
                            }
                            aVar.R = strArr;
                        } else if ("isbnr".equals(xmlPullParser.getName())) {
                            xmlPullParser.next();
                            aVar.S = Integer.parseInt(xmlPullParser.getText());
                        } else if ("vehicle".equals(xmlPullParser.getName())) {
                            xmlPullParser.next();
                            aVar.T = c.b.a.l.a.h(xmlPullParser.getText().split(","));
                        } else if ("visibility".equals(xmlPullParser.getName())) {
                            xmlPullParser.next();
                            aVar.U = Integer.parseInt(xmlPullParser.getText());
                            aVar.V = Integer.parseInt(xmlPullParser.getText());
                        }
                    }
                }
                xmlPullParser.next();
                aVar.k = Double.valueOf(xmlPullParser.getText()).doubleValue();
            }
        }
    }
}
