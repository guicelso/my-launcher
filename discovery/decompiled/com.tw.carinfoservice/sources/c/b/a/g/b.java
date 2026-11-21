package c.b.a.g;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import c.b.a.d;
import com.tw.carinfoservice.R;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public Context f407a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public RemoteCallbackList<d> f408b = new RemoteCallbackList<>();

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public boolean[] f409c = {true, true, true, true};
    public float d = 0.0f;
    public int e = 0;

    /* JADX INFO: renamed from: c.b.a.g.b$b, reason: collision with other inner class name */
    public static class C0015b {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public static b f410a = new b(null);
    }

    public b(a aVar) {
    }

    public static b a() {
        return C0015b.f410a;
    }

    public final boolean[] b(float f, String str, int i) {
        boolean[] zArr = new boolean[1];
        ContentResolver contentResolver = this.f407a.getContentResolver();
        if (i == -1) {
            i = 100;
        }
        int i2 = Settings.System.getInt(contentResolver, "TEMP_UP", i);
        if (str != null) {
            zArr[0] = f > ((float) i2);
        } else if (str.equalsIgnoreCase("℉")) {
            zArr[0] = f > ((float) (((i2 * 9) / 5) + 32));
        } else {
            zArr[0] = f > ((float) i2);
        }
        return zArr;
    }

    public final boolean[] c(float f, String str, int i, int i2) {
        boolean[] zArr = new boolean[2];
        ContentResolver contentResolver = this.f407a.getContentResolver();
        if (i2 == -1) {
            i2 = 180;
        }
        int i3 = Settings.System.getInt(contentResolver, "TPMS_LOW", i2);
        ContentResolver contentResolver2 = this.f407a.getContentResolver();
        if (i == -1) {
            i = 300;
        }
        int i4 = Settings.System.getInt(contentResolver2, "TPMS_UP", i);
        if (str == null) {
            zArr[0] = f < ((float) i3);
            zArr[1] = f > ((float) i4);
        } else if (str.equalsIgnoreCase("Bar")) {
            float f2 = f * 100.0f;
            zArr[0] = f2 < ((float) i3);
            zArr[1] = f2 > ((float) i4);
        } else if (str.equalsIgnoreCase("psi")) {
            double d = f;
            Double.isNaN(d);
            double d2 = d * 6.896d;
            zArr[0] = d2 < ((double) i3);
            zArr[1] = d2 > ((double) i4);
        } else {
            zArr[0] = f < ((float) i3);
            zArr[1] = f > ((float) i4);
        }
        return zArr;
    }

    public void d(float f, String str) {
        try {
            RemoteCallbackList<d> remoteCallbackList = this.f408b;
            if (remoteCallbackList != null) {
                int iBeginBroadcast = remoteCallbackList.beginBroadcast();
                for (int i = 0; i < iBeginBroadcast; i++) {
                    if (this.f408b.getBroadcastItem(i) != null) {
                        try {
                            ((d) this.f408b.getBroadcastItem(i)).d0(null);
                            ((d) this.f408b.getBroadcastItem(i)).r(f, str);
                        } catch (Exception unused) {
                            RemoteCallbackList<d> remoteCallbackList2 = this.f408b;
                            remoteCallbackList2.unregister(remoteCallbackList2.getBroadcastItem(i));
                            this.f408b.finishBroadcast();
                        }
                    }
                }
                this.f408b.finishBroadcast();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void e(int i, int i2, byte[] bArr) {
        try {
            Bundle bundle = new Bundle();
            bundle.putString("action", "car_info_recieve");
            bundle.putInt("what", i);
            bundle.putInt("arg1", i2);
            bundle.putByteArray("obj", bArr);
            RemoteCallbackList<d> remoteCallbackList = this.f408b;
            if (remoteCallbackList != null) {
                int iBeginBroadcast = remoteCallbackList.beginBroadcast();
                for (int i3 = 0; i3 < iBeginBroadcast; i3++) {
                    if (this.f408b.getBroadcastItem(i3) != null) {
                        try {
                            ((d) this.f408b.getBroadcastItem(i3)).d0(null);
                            ((d) this.f408b.getBroadcastItem(i3)).k(bundle);
                        } catch (Exception unused) {
                            RemoteCallbackList<d> remoteCallbackList2 = this.f408b;
                            remoteCallbackList2.unregister(remoteCallbackList2.getBroadcastItem(i3));
                            this.f408b.finishBroadcast();
                        }
                    }
                }
                this.f408b.finishBroadcast();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:163:0x0439 A[Catch: Exception -> 0x0c5d, TryCatch #1 {Exception -> 0x0c5d, blocks: (B:30:0x0072, B:32:0x0076, B:34:0x007e, B:404:0x0c45, B:41:0x00a0, B:42:0x00a4, B:44:0x00aa, B:46:0x00b6, B:48:0x00be, B:52:0x00c9, B:55:0x00d1, B:70:0x00f4, B:72:0x00fa, B:76:0x0113, B:77:0x0117, B:81:0x012b, B:83:0x0131, B:87:0x014a, B:88:0x014e, B:92:0x0162, B:93:0x0177, B:95:0x017d, B:99:0x0196, B:100:0x019b, B:104:0x01af, B:106:0x01cf, B:107:0x01e1, B:109:0x0201, B:110:0x0213, B:112:0x0233, B:113:0x0246, B:115:0x0266, B:116:0x0278, B:117:0x0296, B:118:0x02b5, B:119:0x02d4, B:120:0x02f2, B:122:0x02fc, B:124:0x0303, B:126:0x0314, B:125:0x0310, B:127:0x0322, B:129:0x0328, B:133:0x0342, B:134:0x0347, B:138:0x035c, B:142:0x0379, B:144:0x0393, B:146:0x0399, B:148:0x039f, B:150:0x03b3, B:151:0x03c3, B:154:0x03d8, B:155:0x03e8, B:160:0x0412, B:156:0x03ed, B:161:0x0429, B:163:0x0439, B:165:0x0472, B:166:0x048c, B:168:0x0491, B:169:0x04ae, B:171:0x04b3, B:172:0x04d0, B:173:0x04df, B:174:0x04f5, B:175:0x050b, B:176:0x0521, B:177:0x0537, B:178:0x054d, B:180:0x0578, B:181:0x0589, B:183:0x059a, B:184:0x05af, B:186:0x05da, B:187:0x05eb, B:189:0x05fc, B:190:0x0610, B:192:0x063a, B:193:0x064a, B:195:0x065b, B:196:0x066e, B:198:0x0699, B:199:0x06a9, B:201:0x06ba, B:202:0x06ce, B:204:0x06d5, B:208:0x06f0, B:209:0x06f5, B:213:0x070b, B:215:0x0712, B:219:0x072d, B:220:0x0732, B:224:0x0748, B:225:0x075d, B:226:0x0773, B:227:0x0789, B:229:0x0794, B:231:0x079b, B:233:0x07ac, B:232:0x07a8, B:234:0x07bb, B:236:0x07c6, B:238:0x07cd, B:240:0x07de, B:239:0x07da, B:241:0x07ed, B:242:0x0803, B:244:0x080a, B:248:0x0825, B:249:0x082a, B:253:0x0840, B:255:0x0847, B:259:0x0862, B:260:0x0867, B:264:0x087d, B:266:0x0884, B:270:0x089f, B:271:0x08a4, B:275:0x08ba, B:277:0x08c1, B:281:0x08dc, B:282:0x08e1, B:286:0x08f7, B:288:0x0902, B:290:0x0909, B:292:0x091a, B:291:0x0916, B:293:0x0927, B:294:0x093c, B:295:0x094e, B:297:0x0955, B:301:0x0970, B:302:0x0975, B:306:0x098b, B:308:0x0992, B:312:0x09ad, B:313:0x09b2, B:317:0x09c8, B:319:0x09cf, B:323:0x09ea, B:324:0x09ef, B:328:0x0a05, B:330:0x0a0c, B:331:0x0a21, B:332:0x0a26, B:333:0x0a36, B:334:0x0a4c, B:335:0x0a82, B:337:0x0a8d, B:339:0x0a94, B:341:0x0aa5, B:340:0x0aa1, B:342:0x0ab2, B:344:0x0abd, B:346:0x0ac4, B:353:0x0af5, B:355:0x0afb, B:356:0x0afd, B:348:0x0adc, B:350:0x0ae8, B:357:0x0b17, B:359:0x0b1f, B:363:0x0b3a, B:364:0x0b3f, B:368:0x0b55, B:370:0x0b61, B:372:0x0b68, B:374:0x0b79, B:373:0x0b75, B:375:0x0b88, B:377:0x0ba6, B:379:0x0bad, B:382:0x0bbf, B:381:0x0bbb, B:385:0x0bd7, B:387:0x0bdc, B:403:0x0c42, B:397:0x0c04, B:398:0x0c11, B:399:0x0c1f, B:400:0x0c2e, B:405:0x0c4d, B:406:0x0c57, B:38:0x008c), top: B:413:0x0072, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:165:0x0472 A[Catch: Exception -> 0x0c5d, TryCatch #1 {Exception -> 0x0c5d, blocks: (B:30:0x0072, B:32:0x0076, B:34:0x007e, B:404:0x0c45, B:41:0x00a0, B:42:0x00a4, B:44:0x00aa, B:46:0x00b6, B:48:0x00be, B:52:0x00c9, B:55:0x00d1, B:70:0x00f4, B:72:0x00fa, B:76:0x0113, B:77:0x0117, B:81:0x012b, B:83:0x0131, B:87:0x014a, B:88:0x014e, B:92:0x0162, B:93:0x0177, B:95:0x017d, B:99:0x0196, B:100:0x019b, B:104:0x01af, B:106:0x01cf, B:107:0x01e1, B:109:0x0201, B:110:0x0213, B:112:0x0233, B:113:0x0246, B:115:0x0266, B:116:0x0278, B:117:0x0296, B:118:0x02b5, B:119:0x02d4, B:120:0x02f2, B:122:0x02fc, B:124:0x0303, B:126:0x0314, B:125:0x0310, B:127:0x0322, B:129:0x0328, B:133:0x0342, B:134:0x0347, B:138:0x035c, B:142:0x0379, B:144:0x0393, B:146:0x0399, B:148:0x039f, B:150:0x03b3, B:151:0x03c3, B:154:0x03d8, B:155:0x03e8, B:160:0x0412, B:156:0x03ed, B:161:0x0429, B:163:0x0439, B:165:0x0472, B:166:0x048c, B:168:0x0491, B:169:0x04ae, B:171:0x04b3, B:172:0x04d0, B:173:0x04df, B:174:0x04f5, B:175:0x050b, B:176:0x0521, B:177:0x0537, B:178:0x054d, B:180:0x0578, B:181:0x0589, B:183:0x059a, B:184:0x05af, B:186:0x05da, B:187:0x05eb, B:189:0x05fc, B:190:0x0610, B:192:0x063a, B:193:0x064a, B:195:0x065b, B:196:0x066e, B:198:0x0699, B:199:0x06a9, B:201:0x06ba, B:202:0x06ce, B:204:0x06d5, B:208:0x06f0, B:209:0x06f5, B:213:0x070b, B:215:0x0712, B:219:0x072d, B:220:0x0732, B:224:0x0748, B:225:0x075d, B:226:0x0773, B:227:0x0789, B:229:0x0794, B:231:0x079b, B:233:0x07ac, B:232:0x07a8, B:234:0x07bb, B:236:0x07c6, B:238:0x07cd, B:240:0x07de, B:239:0x07da, B:241:0x07ed, B:242:0x0803, B:244:0x080a, B:248:0x0825, B:249:0x082a, B:253:0x0840, B:255:0x0847, B:259:0x0862, B:260:0x0867, B:264:0x087d, B:266:0x0884, B:270:0x089f, B:271:0x08a4, B:275:0x08ba, B:277:0x08c1, B:281:0x08dc, B:282:0x08e1, B:286:0x08f7, B:288:0x0902, B:290:0x0909, B:292:0x091a, B:291:0x0916, B:293:0x0927, B:294:0x093c, B:295:0x094e, B:297:0x0955, B:301:0x0970, B:302:0x0975, B:306:0x098b, B:308:0x0992, B:312:0x09ad, B:313:0x09b2, B:317:0x09c8, B:319:0x09cf, B:323:0x09ea, B:324:0x09ef, B:328:0x0a05, B:330:0x0a0c, B:331:0x0a21, B:332:0x0a26, B:333:0x0a36, B:334:0x0a4c, B:335:0x0a82, B:337:0x0a8d, B:339:0x0a94, B:341:0x0aa5, B:340:0x0aa1, B:342:0x0ab2, B:344:0x0abd, B:346:0x0ac4, B:353:0x0af5, B:355:0x0afb, B:356:0x0afd, B:348:0x0adc, B:350:0x0ae8, B:357:0x0b17, B:359:0x0b1f, B:363:0x0b3a, B:364:0x0b3f, B:368:0x0b55, B:370:0x0b61, B:372:0x0b68, B:374:0x0b79, B:373:0x0b75, B:375:0x0b88, B:377:0x0ba6, B:379:0x0bad, B:382:0x0bbf, B:381:0x0bbb, B:385:0x0bd7, B:387:0x0bdc, B:403:0x0c42, B:397:0x0c04, B:398:0x0c11, B:399:0x0c1f, B:400:0x0c2e, B:405:0x0c4d, B:406:0x0c57, B:38:0x008c), top: B:413:0x0072, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:168:0x0491 A[Catch: Exception -> 0x0c5d, TryCatch #1 {Exception -> 0x0c5d, blocks: (B:30:0x0072, B:32:0x0076, B:34:0x007e, B:404:0x0c45, B:41:0x00a0, B:42:0x00a4, B:44:0x00aa, B:46:0x00b6, B:48:0x00be, B:52:0x00c9, B:55:0x00d1, B:70:0x00f4, B:72:0x00fa, B:76:0x0113, B:77:0x0117, B:81:0x012b, B:83:0x0131, B:87:0x014a, B:88:0x014e, B:92:0x0162, B:93:0x0177, B:95:0x017d, B:99:0x0196, B:100:0x019b, B:104:0x01af, B:106:0x01cf, B:107:0x01e1, B:109:0x0201, B:110:0x0213, B:112:0x0233, B:113:0x0246, B:115:0x0266, B:116:0x0278, B:117:0x0296, B:118:0x02b5, B:119:0x02d4, B:120:0x02f2, B:122:0x02fc, B:124:0x0303, B:126:0x0314, B:125:0x0310, B:127:0x0322, B:129:0x0328, B:133:0x0342, B:134:0x0347, B:138:0x035c, B:142:0x0379, B:144:0x0393, B:146:0x0399, B:148:0x039f, B:150:0x03b3, B:151:0x03c3, B:154:0x03d8, B:155:0x03e8, B:160:0x0412, B:156:0x03ed, B:161:0x0429, B:163:0x0439, B:165:0x0472, B:166:0x048c, B:168:0x0491, B:169:0x04ae, B:171:0x04b3, B:172:0x04d0, B:173:0x04df, B:174:0x04f5, B:175:0x050b, B:176:0x0521, B:177:0x0537, B:178:0x054d, B:180:0x0578, B:181:0x0589, B:183:0x059a, B:184:0x05af, B:186:0x05da, B:187:0x05eb, B:189:0x05fc, B:190:0x0610, B:192:0x063a, B:193:0x064a, B:195:0x065b, B:196:0x066e, B:198:0x0699, B:199:0x06a9, B:201:0x06ba, B:202:0x06ce, B:204:0x06d5, B:208:0x06f0, B:209:0x06f5, B:213:0x070b, B:215:0x0712, B:219:0x072d, B:220:0x0732, B:224:0x0748, B:225:0x075d, B:226:0x0773, B:227:0x0789, B:229:0x0794, B:231:0x079b, B:233:0x07ac, B:232:0x07a8, B:234:0x07bb, B:236:0x07c6, B:238:0x07cd, B:240:0x07de, B:239:0x07da, B:241:0x07ed, B:242:0x0803, B:244:0x080a, B:248:0x0825, B:249:0x082a, B:253:0x0840, B:255:0x0847, B:259:0x0862, B:260:0x0867, B:264:0x087d, B:266:0x0884, B:270:0x089f, B:271:0x08a4, B:275:0x08ba, B:277:0x08c1, B:281:0x08dc, B:282:0x08e1, B:286:0x08f7, B:288:0x0902, B:290:0x0909, B:292:0x091a, B:291:0x0916, B:293:0x0927, B:294:0x093c, B:295:0x094e, B:297:0x0955, B:301:0x0970, B:302:0x0975, B:306:0x098b, B:308:0x0992, B:312:0x09ad, B:313:0x09b2, B:317:0x09c8, B:319:0x09cf, B:323:0x09ea, B:324:0x09ef, B:328:0x0a05, B:330:0x0a0c, B:331:0x0a21, B:332:0x0a26, B:333:0x0a36, B:334:0x0a4c, B:335:0x0a82, B:337:0x0a8d, B:339:0x0a94, B:341:0x0aa5, B:340:0x0aa1, B:342:0x0ab2, B:344:0x0abd, B:346:0x0ac4, B:353:0x0af5, B:355:0x0afb, B:356:0x0afd, B:348:0x0adc, B:350:0x0ae8, B:357:0x0b17, B:359:0x0b1f, B:363:0x0b3a, B:364:0x0b3f, B:368:0x0b55, B:370:0x0b61, B:372:0x0b68, B:374:0x0b79, B:373:0x0b75, B:375:0x0b88, B:377:0x0ba6, B:379:0x0bad, B:382:0x0bbf, B:381:0x0bbb, B:385:0x0bd7, B:387:0x0bdc, B:403:0x0c42, B:397:0x0c04, B:398:0x0c11, B:399:0x0c1f, B:400:0x0c2e, B:405:0x0c4d, B:406:0x0c57, B:38:0x008c), top: B:413:0x0072, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:171:0x04b3 A[Catch: Exception -> 0x0c5d, TryCatch #1 {Exception -> 0x0c5d, blocks: (B:30:0x0072, B:32:0x0076, B:34:0x007e, B:404:0x0c45, B:41:0x00a0, B:42:0x00a4, B:44:0x00aa, B:46:0x00b6, B:48:0x00be, B:52:0x00c9, B:55:0x00d1, B:70:0x00f4, B:72:0x00fa, B:76:0x0113, B:77:0x0117, B:81:0x012b, B:83:0x0131, B:87:0x014a, B:88:0x014e, B:92:0x0162, B:93:0x0177, B:95:0x017d, B:99:0x0196, B:100:0x019b, B:104:0x01af, B:106:0x01cf, B:107:0x01e1, B:109:0x0201, B:110:0x0213, B:112:0x0233, B:113:0x0246, B:115:0x0266, B:116:0x0278, B:117:0x0296, B:118:0x02b5, B:119:0x02d4, B:120:0x02f2, B:122:0x02fc, B:124:0x0303, B:126:0x0314, B:125:0x0310, B:127:0x0322, B:129:0x0328, B:133:0x0342, B:134:0x0347, B:138:0x035c, B:142:0x0379, B:144:0x0393, B:146:0x0399, B:148:0x039f, B:150:0x03b3, B:151:0x03c3, B:154:0x03d8, B:155:0x03e8, B:160:0x0412, B:156:0x03ed, B:161:0x0429, B:163:0x0439, B:165:0x0472, B:166:0x048c, B:168:0x0491, B:169:0x04ae, B:171:0x04b3, B:172:0x04d0, B:173:0x04df, B:174:0x04f5, B:175:0x050b, B:176:0x0521, B:177:0x0537, B:178:0x054d, B:180:0x0578, B:181:0x0589, B:183:0x059a, B:184:0x05af, B:186:0x05da, B:187:0x05eb, B:189:0x05fc, B:190:0x0610, B:192:0x063a, B:193:0x064a, B:195:0x065b, B:196:0x066e, B:198:0x0699, B:199:0x06a9, B:201:0x06ba, B:202:0x06ce, B:204:0x06d5, B:208:0x06f0, B:209:0x06f5, B:213:0x070b, B:215:0x0712, B:219:0x072d, B:220:0x0732, B:224:0x0748, B:225:0x075d, B:226:0x0773, B:227:0x0789, B:229:0x0794, B:231:0x079b, B:233:0x07ac, B:232:0x07a8, B:234:0x07bb, B:236:0x07c6, B:238:0x07cd, B:240:0x07de, B:239:0x07da, B:241:0x07ed, B:242:0x0803, B:244:0x080a, B:248:0x0825, B:249:0x082a, B:253:0x0840, B:255:0x0847, B:259:0x0862, B:260:0x0867, B:264:0x087d, B:266:0x0884, B:270:0x089f, B:271:0x08a4, B:275:0x08ba, B:277:0x08c1, B:281:0x08dc, B:282:0x08e1, B:286:0x08f7, B:288:0x0902, B:290:0x0909, B:292:0x091a, B:291:0x0916, B:293:0x0927, B:294:0x093c, B:295:0x094e, B:297:0x0955, B:301:0x0970, B:302:0x0975, B:306:0x098b, B:308:0x0992, B:312:0x09ad, B:313:0x09b2, B:317:0x09c8, B:319:0x09cf, B:323:0x09ea, B:324:0x09ef, B:328:0x0a05, B:330:0x0a0c, B:331:0x0a21, B:332:0x0a26, B:333:0x0a36, B:334:0x0a4c, B:335:0x0a82, B:337:0x0a8d, B:339:0x0a94, B:341:0x0aa5, B:340:0x0aa1, B:342:0x0ab2, B:344:0x0abd, B:346:0x0ac4, B:353:0x0af5, B:355:0x0afb, B:356:0x0afd, B:348:0x0adc, B:350:0x0ae8, B:357:0x0b17, B:359:0x0b1f, B:363:0x0b3a, B:364:0x0b3f, B:368:0x0b55, B:370:0x0b61, B:372:0x0b68, B:374:0x0b79, B:373:0x0b75, B:375:0x0b88, B:377:0x0ba6, B:379:0x0bad, B:382:0x0bbf, B:381:0x0bbb, B:385:0x0bd7, B:387:0x0bdc, B:403:0x0c42, B:397:0x0c04, B:398:0x0c11, B:399:0x0c1f, B:400:0x0c2e, B:405:0x0c4d, B:406:0x0c57, B:38:0x008c), top: B:413:0x0072, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:67:0x00ef  */
    /* JADX WARN: Instruction removed from duplicated block: B:163:0x0439, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:165:0x0472, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:168:0x0491, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:171:0x04b3, please report this as an issue */
    public void f(ArrayList<c.b.a.e.a> arrayList, int i) {
        int i2;
        int i3;
        String strN;
        String strN2;
        d dVar;
        boolean z;
        String strN3;
        float f;
        String strN4;
        d dVar2;
        int i4;
        d dVar3;
        boolean z2;
        d dVar4;
        boolean z3;
        d dVar5;
        boolean z4;
        String strN5;
        d dVar6;
        boolean z5;
        d dVar7;
        boolean z6;
        d dVar8;
        boolean z7;
        d dVar9;
        boolean z8;
        String strN6;
        String strN7;
        d dVar10;
        boolean z9;
        d dVar11;
        boolean z10;
        int[] iArrK;
        String str;
        float f2;
        float f3;
        d dVar12;
        boolean z11;
        String strN8;
        d dVar13;
        boolean z12;
        d dVar14;
        boolean z13;
        d dVar15;
        boolean z14;
        int i5 = -1;
        if (i == 0 && arrayList != null && this.d == 0.0f) {
            for (c.b.a.e.a aVar : arrayList) {
                if (aVar.b() == i) {
                    float f4 = a.b.e.b.a.f(aVar);
                    if (aVar.d() == null && aVar.e() == -1) {
                        f4 = a.b.e.b.a.e(f4, this.f407a);
                        this.d = a.b.e.b.a.e(this.d, this.f407a);
                    } else if (this.e != aVar.e()) {
                        this.e = aVar.e();
                        this.d = 0.0f;
                    }
                    if (f4 >= 0.0f && this.d == 0.0f) {
                        this.d = f4;
                    }
                    if (f4 >= 0.0f && this.d == 0.0f) {
                        this.d = f4;
                    }
                }
            }
        }
        try {
            RemoteCallbackList<d> remoteCallbackList = this.f408b;
            if (remoteCallbackList != null) {
                int iBeginBroadcast = remoteCallbackList.beginBroadcast();
                boolean z15 = false;
                int i6 = 0;
                while (i6 < iBeginBroadcast) {
                    if (this.f408b.getBroadcastItem(i6) == null) {
                        i2 = i6;
                    } else {
                        try {
                            ((d) this.f408b.getBroadcastItem(i6)).d0(null);
                            char c2 = 3;
                            int i7 = 1;
                            if (arrayList != null) {
                                for (c.b.a.e.a aVar2 : arrayList) {
                                    if ((aVar2.g() != null || aVar2.b() == 76 || aVar2.b() == 77) && (i == i5 || i == aVar2.b())) {
                                        int iB = aVar2.b();
                                        if (iB == 12) {
                                            i3 = i6;
                                            float f5 = a.b.e.b.a.f(aVar2);
                                            Log.e("LS123", "sendAidlCarServiceList: " + f5);
                                            if (aVar2.d() == null && aVar2.e() == -1) {
                                                f5 = a.b.e.b.a.i(f5, this.f407a);
                                                strN = a.b.e.b.a.j(this.f407a);
                                            } else {
                                                strN = a.b.e.b.a.n(aVar2);
                                            }
                                            ((d) this.f408b.getBroadcastItem(i3)).f((int) f5, strN);
                                        } else if (iB == 14) {
                                            i3 = i6;
                                            float f6 = a.b.e.b.a.f(aVar2);
                                            if (aVar2.d() == null && aVar2.e() == -1) {
                                                f6 = a.b.e.b.a.i(f6, this.f407a);
                                                strN2 = a.b.e.b.a.j(this.f407a);
                                            } else {
                                                strN2 = a.b.e.b.a.n(aVar2);
                                            }
                                            ((d) this.f408b.getBroadcastItem(i3)).G((int) f6, strN2);
                                        } else if (iB != 20) {
                                            switch (iB) {
                                                case 0:
                                                    i3 = i6;
                                                    float f7 = a.b.e.b.a.f(aVar2);
                                                    if (aVar2.d() == null && aVar2.e() == -1) {
                                                        f7 = a.b.e.b.a.e(f7, this.f407a);
                                                        strN3 = a.b.e.b.a.d(this.f407a);
                                                        this.d = a.b.e.b.a.e(this.d, this.f407a);
                                                    } else {
                                                        strN3 = a.b.e.b.a.n(aVar2);
                                                        if (this.e != aVar2.e()) {
                                                            this.e = aVar2.e();
                                                            f = 0.0f;
                                                            this.d = 0.0f;
                                                        }
                                                        if (f7 >= f && this.d == f) {
                                                            this.d = f7;
                                                        }
                                                        ((d) this.f408b.getBroadcastItem(i3)).H(f7, strN3);
                                                        ((d) this.f408b.getBroadcastItem(i3)).J(this.d, strN3);
                                                    }
                                                    f = 0.0f;
                                                    if (f7 >= f) {
                                                        this.d = f7;
                                                    }
                                                    ((d) this.f408b.getBroadcastItem(i3)).H(f7, strN3);
                                                    ((d) this.f408b.getBroadcastItem(i3)).J(this.d, strN3);
                                                    break;
                                                case 1:
                                                    i3 = i6;
                                                    float f8 = a.b.e.b.a.f(aVar2);
                                                    if (aVar2.d() == null && aVar2.e() == -1) {
                                                        f8 = a.b.e.b.a.e(f8, this.f407a);
                                                        strN4 = a.b.e.b.a.d(this.f407a);
                                                    } else {
                                                        strN4 = a.b.e.b.a.n(aVar2);
                                                    }
                                                    ((d) this.f408b.getBroadcastItem(i3)).L(f8, strN4);
                                                    break;
                                                case 2:
                                                    i3 = i6;
                                                    float f9 = a.b.e.b.a.f(aVar2);
                                                    Log.e("CarServiceCallback", "sendAidlCarServiceList: " + f9 + "   " + a.b.e.b.a.n(aVar2));
                                                    ((d) this.f408b.getBroadcastItem(i3)).j(f9, a.b.e.b.a.n(aVar2));
                                                    break;
                                                case 3:
                                                    i3 = i6;
                                                    ((d) this.f408b.getBroadcastItem(i3)).q(a.b.e.b.a.f(aVar2), a.b.e.b.a.n(aVar2));
                                                    break;
                                                case 4:
                                                    i3 = i6;
                                                    if (aVar2.a() != null) {
                                                        dVar2 = (d) this.f408b.getBroadcastItem(i3);
                                                        i4 = aVar2.a()[aVar2.g()[0]];
                                                    } else {
                                                        dVar2 = (d) this.f408b.getBroadcastItem(i3);
                                                        i4 = aVar2.g()[0];
                                                    }
                                                    dVar2.I(i4);
                                                    break;
                                                case 5:
                                                    i3 = i6;
                                                    if (aVar2.a() != null) {
                                                        dVar3 = (d) this.f408b.getBroadcastItem(i3);
                                                        z2 = aVar2.a()[aVar2.g()[0]] == 1;
                                                    } else {
                                                        dVar3 = (d) this.f408b.getBroadcastItem(i3);
                                                        z2 = aVar2.g()[0] == 1;
                                                    }
                                                    dVar3.m(z2);
                                                    break;
                                                case 6:
                                                    i3 = i6;
                                                    if (aVar2.a() != null) {
                                                        dVar4 = (d) this.f408b.getBroadcastItem(i3);
                                                        z3 = aVar2.a()[aVar2.g()[0]] == 1;
                                                    } else {
                                                        dVar4 = (d) this.f408b.getBroadcastItem(i3);
                                                        z3 = aVar2.g()[0] == 1;
                                                    }
                                                    dVar4.K(z3);
                                                    break;
                                                case 7:
                                                    i3 = i6;
                                                    if (aVar2.a() != null) {
                                                        dVar5 = (d) this.f408b.getBroadcastItem(i3);
                                                        z4 = aVar2.a()[aVar2.g()[0]] == 1;
                                                    } else {
                                                        dVar5 = (d) this.f408b.getBroadcastItem(i3);
                                                        z4 = aVar2.g()[0] == 1;
                                                    }
                                                    dVar5.k0(z4);
                                                    break;
                                                default:
                                                    switch (iB) {
                                                        case 16:
                                                            i3 = i6;
                                                            ((d) this.f408b.getBroadcastItem(i3)).y(a.b.e.b.a.f(aVar2));
                                                            break;
                                                        case 17:
                                                            i3 = i6;
                                                            ((d) this.f408b.getBroadcastItem(i3)).b0(aVar2.g()[0]);
                                                            break;
                                                        case 18:
                                                            i3 = i6;
                                                            float f10 = a.b.e.b.a.f(aVar2);
                                                            if (aVar2.d() == null && aVar2.e() == -1) {
                                                                f10 = a.b.e.b.a.b(f10, this.f407a);
                                                                strN5 = a.b.e.b.a.c(this.f407a);
                                                            } else {
                                                                strN5 = a.b.e.b.a.n(aVar2);
                                                            }
                                                            ((d) this.f408b.getBroadcastItem(i3)).F(f10, strN5);
                                                            break;
                                                        default:
                                                            switch (iB) {
                                                                case 54:
                                                                    i3 = i6;
                                                                    if (aVar2.a() != null) {
                                                                        dVar6 = (d) this.f408b.getBroadcastItem(i3);
                                                                        z5 = aVar2.a()[aVar2.g()[0]] == 1;
                                                                    } else {
                                                                        dVar6 = (d) this.f408b.getBroadcastItem(i3);
                                                                        z5 = aVar2.g()[0] == 1;
                                                                    }
                                                                    dVar6.v(z5);
                                                                    break;
                                                                case 55:
                                                                    i3 = i6;
                                                                    if (aVar2.a() != null) {
                                                                        dVar7 = (d) this.f408b.getBroadcastItem(i3);
                                                                        z6 = aVar2.a()[aVar2.g()[0]] == 1;
                                                                    } else {
                                                                        dVar7 = (d) this.f408b.getBroadcastItem(i3);
                                                                        z6 = aVar2.g()[0] == 1;
                                                                    }
                                                                    dVar7.z(z6);
                                                                    break;
                                                                case 56:
                                                                    i3 = i6;
                                                                    if (aVar2.a() != null) {
                                                                        dVar8 = (d) this.f408b.getBroadcastItem(i3);
                                                                        z7 = aVar2.a()[aVar2.g()[0]] == 1;
                                                                    } else {
                                                                        dVar8 = (d) this.f408b.getBroadcastItem(i3);
                                                                        z7 = aVar2.g()[0] == 1;
                                                                    }
                                                                    dVar8.r0(z7);
                                                                    break;
                                                                case 57:
                                                                    i3 = i6;
                                                                    if (aVar2.a() != null) {
                                                                        dVar9 = (d) this.f408b.getBroadcastItem(i3);
                                                                        z8 = aVar2.a()[aVar2.g()[0]] == 1;
                                                                    } else {
                                                                        dVar9 = (d) this.f408b.getBroadcastItem(i3);
                                                                        z8 = aVar2.g()[0] == 1;
                                                                    }
                                                                    dVar9.W(z8);
                                                                    break;
                                                                default:
                                                                    switch (iB) {
                                                                        case 59:
                                                                            i3 = i6;
                                                                            ((d) this.f408b.getBroadcastItem(i3)).P(a.b.e.b.a.f(aVar2), a.b.e.b.a.n(aVar2));
                                                                            break;
                                                                        case 60:
                                                                            i3 = i6;
                                                                            float f11 = a.b.e.b.a.f(aVar2);
                                                                            if (aVar2.d() == null && aVar2.e() == -1) {
                                                                                f11 = a.b.e.b.a.i(f11, this.f407a);
                                                                                strN6 = a.b.e.b.a.j(this.f407a);
                                                                            } else {
                                                                                strN6 = a.b.e.b.a.n(aVar2);
                                                                            }
                                                                            ((d) this.f408b.getBroadcastItem(i3)).n((int) f11, strN6);
                                                                            break;
                                                                        case 61:
                                                                            i3 = i6;
                                                                            float f12 = a.b.e.b.a.f(aVar2);
                                                                            if (aVar2.d() == null && aVar2.e() == -1) {
                                                                                f12 = a.b.e.b.a.i(f12, this.f407a);
                                                                                strN7 = a.b.e.b.a.j(this.f407a);
                                                                            } else {
                                                                                strN7 = a.b.e.b.a.n(aVar2);
                                                                            }
                                                                            ((d) this.f408b.getBroadcastItem(i3)).l((int) f12, strN7);
                                                                            break;
                                                                        case 62:
                                                                            i3 = i6;
                                                                            ((d) this.f408b.getBroadcastItem(i3)).N(a.b.e.b.a.f(aVar2), a.b.e.b.a.n(aVar2));
                                                                            break;
                                                                        case 63:
                                                                            i3 = i6;
                                                                            ((d) this.f408b.getBroadcastItem(i3)).T(a.b.e.b.a.f(aVar2), a.b.e.b.a.n(aVar2));
                                                                            break;
                                                                        case 64:
                                                                            i3 = i6;
                                                                            ((d) this.f408b.getBroadcastItem(i3)).e(aVar2.g()[0]);
                                                                            break;
                                                                        case 65:
                                                                            i3 = i6;
                                                                            if (aVar2.a() != null) {
                                                                                dVar10 = (d) this.f408b.getBroadcastItem(i3);
                                                                                z9 = aVar2.a()[aVar2.g()[0]] == 1;
                                                                            } else {
                                                                                dVar10 = (d) this.f408b.getBroadcastItem(i3);
                                                                                z9 = aVar2.g()[0] == 1;
                                                                            }
                                                                            dVar10.p0(z9);
                                                                            break;
                                                                        case 66:
                                                                            i3 = i6;
                                                                            if (aVar2.a() != null) {
                                                                                dVar11 = (d) this.f408b.getBroadcastItem(i3);
                                                                                z10 = aVar2.a()[aVar2.g()[0]] == 1;
                                                                            } else {
                                                                                dVar11 = (d) this.f408b.getBroadcastItem(i3);
                                                                                z10 = aVar2.g()[0] == 1;
                                                                            }
                                                                            dVar11.i0(z10);
                                                                            break;
                                                                        case 67:
                                                                            i3 = i6;
                                                                            float f13 = a.b.e.b.a.f(aVar2);
                                                                            String strN9 = a.b.e.b.a.n(aVar2);
                                                                            ((d) this.f408b.getBroadcastItem(i3)).s(f13, strN9);
                                                                            this.f409c[0] = true;
                                                                            if (c(f13, strN9, aVar2.f(), aVar2.c())[0]) {
                                                                                this.f409c[0] = false;
                                                                                ((d) this.f408b.getBroadcastItem(i3)).x(1, 1);
                                                                            }
                                                                            if (c(f13, strN9, aVar2.f(), aVar2.c())[1]) {
                                                                                this.f409c[0] = false;
                                                                                ((d) this.f408b.getBroadcastItem(i3)).x(1, 2);
                                                                            }
                                                                            break;
                                                                        case 68:
                                                                            i3 = i6;
                                                                            float f14 = a.b.e.b.a.f(aVar2);
                                                                            String strN10 = a.b.e.b.a.n(aVar2);
                                                                            ((d) this.f408b.getBroadcastItem(i3)).p(f14, strN10);
                                                                            this.f409c[1] = true;
                                                                            if (c(f14, strN10, aVar2.f(), aVar2.c())[0]) {
                                                                                this.f409c[1] = false;
                                                                                ((d) this.f408b.getBroadcastItem(i3)).D(1, 1);
                                                                            }
                                                                            if (c(f14, strN10, aVar2.f(), aVar2.c())[1]) {
                                                                                this.f409c[1] = false;
                                                                                ((d) this.f408b.getBroadcastItem(i3)).D(1, 2);
                                                                            }
                                                                            break;
                                                                        case 69:
                                                                            i3 = i6;
                                                                            float f15 = a.b.e.b.a.f(aVar2);
                                                                            String strN11 = a.b.e.b.a.n(aVar2);
                                                                            ((d) this.f408b.getBroadcastItem(i3)).i(f15, strN11);
                                                                            this.f409c[2] = true;
                                                                            if (c(f15, strN11, aVar2.f(), aVar2.c())[0]) {
                                                                                this.f409c[2] = false;
                                                                                ((d) this.f408b.getBroadcastItem(i3)).R(1, 1);
                                                                            }
                                                                            if (c(f15, strN11, aVar2.f(), aVar2.c())[1]) {
                                                                                this.f409c[2] = false;
                                                                                ((d) this.f408b.getBroadcastItem(i3)).R(1, 2);
                                                                            }
                                                                            break;
                                                                        case 70:
                                                                            i3 = i6;
                                                                            float f16 = a.b.e.b.a.f(aVar2);
                                                                            String strN12 = a.b.e.b.a.n(aVar2);
                                                                            ((d) this.f408b.getBroadcastItem(i3)).l0(f16, strN12);
                                                                            this.f409c[3] = true;
                                                                            if (c(f16, strN12, aVar2.f(), aVar2.c())[0]) {
                                                                                this.f409c[3] = false;
                                                                                ((d) this.f408b.getBroadcastItem(i3)).n0(1, 1);
                                                                            }
                                                                            if (c(f16, strN12, aVar2.f(), aVar2.c())[1]) {
                                                                                this.f409c[3] = false;
                                                                                ((d) this.f408b.getBroadcastItem(i3)).n0(1, 2);
                                                                            }
                                                                            break;
                                                                        case 71:
                                                                            i3 = i6;
                                                                            ((d) this.f408b.getBroadcastItem(i3)).C(a.b.e.b.a.f(aVar2), a.b.e.b.a.n(aVar2));
                                                                            break;
                                                                        case 72:
                                                                            i3 = i6;
                                                                            ((d) this.f408b.getBroadcastItem(i3)).B(a.b.e.b.a.f(aVar2), a.b.e.b.a.n(aVar2));
                                                                            break;
                                                                        case 73:
                                                                            i3 = i6;
                                                                            ((d) this.f408b.getBroadcastItem(i3)).f0(a.b.e.b.a.f(aVar2), a.b.e.b.a.n(aVar2));
                                                                            break;
                                                                        case 74:
                                                                            i3 = i6;
                                                                            ((d) this.f408b.getBroadcastItem(i3)).h(a.b.e.b.a.f(aVar2), a.b.e.b.a.n(aVar2));
                                                                            break;
                                                                        case 75:
                                                                            i3 = i6;
                                                                            ((d) this.f408b.getBroadcastItem(i3)).c0(a.b.e.b.a.f(aVar2), a.b.e.b.a.n(aVar2));
                                                                            break;
                                                                        case 76:
                                                                            StringBuilder sb = new StringBuilder();
                                                                            sb.append("Car_Maintenance_mileage: ");
                                                                            sb.append(Settings.System.getInt(this.f407a.getApplicationContext().getContentResolver(), "MA_SWITCH", z15 ? 1 : 0) != 0);
                                                                            Log.e("LS123", sb.toString());
                                                                            if (Settings.System.getInt(this.f407a.getApplicationContext().getContentResolver(), "MA_SWITCH", z15 ? 1 : 0) != 0) {
                                                                                float f17 = aVar2.g() != null ? a.b.e.b.a.f(aVar2) : 0.0f;
                                                                                if (f17 < Settings.System.getInt(this.f407a.getApplicationContext().getContentResolver(), "MA_OPEN_CURRENT_MILEAGE", -1)) {
                                                                                    f17 = Settings.System.getInt(this.f407a.getApplicationContext().getContentResolver(), "MA_OPEN_CURRENT_MILEAGE", -1);
                                                                                }
                                                                                if (Settings.System.getInt(this.f407a.getApplicationContext().getContentResolver(), "MA_OPEN_CURRENT_MILEAGE", -1) == -1) {
                                                                                    f2 = Settings.System.getInt(this.f407a.getApplicationContext().getContentResolver(), "MA_LEFT_MILEAGE", 10000);
                                                                                    f3 = 0.0f;
                                                                                } else {
                                                                                    f2 = Settings.System.getInt(this.f407a.getApplicationContext().getContentResolver(), "MA_LEFT_MILEAGE", 10000);
                                                                                    f3 = Settings.System.getInt(this.f407a.getApplicationContext().getContentResolver(), "MA_OPEN_CURRENT_MILEAGE", -1);
                                                                                }
                                                                                float f18 = f2 - (f17 - f3);
                                                                                if (f18 < 0.0f) {
                                                                                    f18 = 0.0f;
                                                                                }
                                                                                ((d) this.f408b.getBroadcastItem(i6)).m0(a.b.e.b.a.e(f18, this.f407a), a.b.e.b.a.d(this.f407a));
                                                                            }
                                                                            if (Settings.System.getInt(this.f407a.getApplicationContext().getContentResolver(), "MA_SWITCH", z15 ? 1 : 0) != 0) {
                                                                                int i8 = i6;
                                                                                iArrK = a.b.e.b.a.k((((((a.b.e.b.a.g(this.f407a, "MA_LEFT_TIME", 6L) * 30) * 24) * 3600) * 1000) + a.b.e.b.a.g(this.f407a, "MA_OPEN_CURRENT_TIME", System.currentTimeMillis())) - System.currentTimeMillis());
                                                                                str = iArrK[2] != 0 ? iArrK[2] + this.f407a.getString(R.string.unit_year) : "";
                                                                                if (iArrK[1] != 0) {
                                                                                    str = str + iArrK[1] + this.f407a.getString(R.string.unit_month);
                                                                                }
                                                                                if (iArrK[0] != 0) {
                                                                                    str = str + iArrK[0] + this.f407a.getString(R.string.unit_day);
                                                                                }
                                                                                i3 = i8;
                                                                                ((d) this.f408b.getBroadcastItem(i3)).E(str);
                                                                            } else {
                                                                                i3 = i6;
                                                                            }
                                                                            break;
                                                                        case 77:
                                                                            if (Settings.System.getInt(this.f407a.getApplicationContext().getContentResolver(), "MA_SWITCH", z15 ? 1 : 0) != 0) {
                                                                                int i9 = i6;
                                                                                iArrK = a.b.e.b.a.k((((((a.b.e.b.a.g(this.f407a, "MA_LEFT_TIME", 6L) * 30) * 24) * 3600) * 1000) + a.b.e.b.a.g(this.f407a, "MA_OPEN_CURRENT_TIME", System.currentTimeMillis())) - System.currentTimeMillis());
                                                                                if (iArrK[2] != 0) {
                                                                                }
                                                                                if (iArrK[1] != 0) {
                                                                                    str = str + iArrK[1] + this.f407a.getString(R.string.unit_month);
                                                                                }
                                                                                if (iArrK[0] != 0) {
                                                                                    str = str + iArrK[0] + this.f407a.getString(R.string.unit_day);
                                                                                }
                                                                                i3 = i9;
                                                                                ((d) this.f408b.getBroadcastItem(i3)).E(str);
                                                                            } else {
                                                                                i3 = i6;
                                                                            }
                                                                            break;
                                                                        case 78:
                                                                            if (aVar2.a() != null) {
                                                                                dVar12 = (d) this.f408b.getBroadcastItem(i6);
                                                                                z11 = aVar2.a()[aVar2.g()[z15 ? 1 : 0]] == 1;
                                                                            } else {
                                                                                dVar12 = (d) this.f408b.getBroadcastItem(i6);
                                                                                z11 = aVar2.g()[z15 ? 1 : 0] == 1;
                                                                            }
                                                                            dVar12.a(z11);
                                                                            i3 = i6;
                                                                            break;
                                                                        case 79:
                                                                            float f19 = a.b.e.b.a.f(aVar2);
                                                                            if (aVar2.d() == null && aVar2.e() == -1) {
                                                                                f19 = a.b.e.b.a.e(f19, this.f407a);
                                                                                strN8 = a.b.e.b.a.d(this.f407a);
                                                                            } else {
                                                                                strN8 = a.b.e.b.a.n(aVar2);
                                                                            }
                                                                            ((d) this.f408b.getBroadcastItem(i6)).h0((int) f19, strN8);
                                                                            i3 = i6;
                                                                            break;
                                                                        case 80:
                                                                            this.f409c[z15 ? 1 : 0] = z15;
                                                                            ((d) this.f408b.getBroadcastItem(i6)).x(a.b.e.b.a.l(aVar2)[z15 ? 1 : 0], a.b.e.b.a.l(aVar2)[1]);
                                                                            i3 = i6;
                                                                            break;
                                                                        case 81:
                                                                            this.f409c[1] = z15;
                                                                            ((d) this.f408b.getBroadcastItem(i6)).D(a.b.e.b.a.l(aVar2)[z15 ? 1 : 0], a.b.e.b.a.l(aVar2)[1]);
                                                                            i3 = i6;
                                                                            break;
                                                                        case 82:
                                                                            this.f409c[2] = z15;
                                                                            ((d) this.f408b.getBroadcastItem(i6)).R(a.b.e.b.a.l(aVar2)[z15 ? 1 : 0], a.b.e.b.a.l(aVar2)[1]);
                                                                            i3 = i6;
                                                                            break;
                                                                        case 83:
                                                                            this.f409c[c2] = z15;
                                                                            ((d) this.f408b.getBroadcastItem(i6)).n0(a.b.e.b.a.l(aVar2)[z15 ? 1 : 0], a.b.e.b.a.l(aVar2)[1]);
                                                                            i3 = i6;
                                                                            break;
                                                                        case 84:
                                                                            String strN13 = a.b.e.b.a.n(aVar2);
                                                                            float f20 = a.b.e.b.a.f(aVar2);
                                                                            ((d) this.f408b.getBroadcastItem(i6)).S((int) f20, strN13);
                                                                            if (b(f20, strN13, aVar2.f())[z15 ? 1 : 0]) {
                                                                                this.f409c[z15 ? 1 : 0] = z15;
                                                                                ((d) this.f408b.getBroadcastItem(i6)).x(2, 2);
                                                                            }
                                                                            i3 = i6;
                                                                            break;
                                                                        case 85:
                                                                            String strN14 = a.b.e.b.a.n(aVar2);
                                                                            float f21 = a.b.e.b.a.f(aVar2);
                                                                            ((d) this.f408b.getBroadcastItem(i6)).g0((int) f21, strN14);
                                                                            if (b(f21, strN14, aVar2.f())[z15 ? 1 : 0]) {
                                                                                this.f409c[1] = z15;
                                                                                ((d) this.f408b.getBroadcastItem(i6)).D(2, 2);
                                                                            }
                                                                            i3 = i6;
                                                                            break;
                                                                        case 86:
                                                                            String strN15 = a.b.e.b.a.n(aVar2);
                                                                            float f22 = a.b.e.b.a.f(aVar2);
                                                                            ((d) this.f408b.getBroadcastItem(i6)).o((int) f22, strN15);
                                                                            if (b(f22, strN15, aVar2.f())[z15 ? 1 : 0]) {
                                                                                this.f409c[2] = z15;
                                                                                ((d) this.f408b.getBroadcastItem(i6)).R(2, 2);
                                                                            }
                                                                            i3 = i6;
                                                                            break;
                                                                        case 87:
                                                                            String strN16 = a.b.e.b.a.n(aVar2);
                                                                            float f23 = a.b.e.b.a.f(aVar2);
                                                                            ((d) this.f408b.getBroadcastItem(i6)).O((int) f23, strN16);
                                                                            if (b(f23, strN16, aVar2.f())[z15 ? 1 : 0]) {
                                                                                this.f409c[c2] = z15;
                                                                                ((d) this.f408b.getBroadcastItem(i6)).n0(2, 2);
                                                                            }
                                                                            i3 = i6;
                                                                            break;
                                                                        case 88:
                                                                            if (aVar2.a() != null) {
                                                                                dVar13 = (d) this.f408b.getBroadcastItem(i6);
                                                                                z12 = aVar2.a()[aVar2.g()[z15 ? 1 : 0]] == i7;
                                                                            } else {
                                                                                dVar13 = (d) this.f408b.getBroadcastItem(i6);
                                                                                z12 = aVar2.g()[z15 ? 1 : 0] == i7;
                                                                            }
                                                                            dVar13.V(z12);
                                                                            i3 = i6;
                                                                            break;
                                                                        case 89:
                                                                            ((d) this.f408b.getBroadcastItem(i6)).a0(a.b.e.b.a.f(aVar2), a.b.e.b.a.n(aVar2));
                                                                            i3 = i6;
                                                                            break;
                                                                        case 90:
                                                                            if (aVar2.a() != null) {
                                                                                dVar14 = (d) this.f408b.getBroadcastItem(i6);
                                                                                z13 = aVar2.a()[aVar2.g()[z15 ? 1 : 0]] == i7;
                                                                            } else {
                                                                                dVar14 = (d) this.f408b.getBroadcastItem(i6);
                                                                                z13 = aVar2.g()[z15 ? 1 : 0] == i7;
                                                                            }
                                                                            dVar14.c(z13);
                                                                            i3 = i6;
                                                                            break;
                                                                        case 91:
                                                                            if (aVar2.a() != null) {
                                                                                dVar15 = (d) this.f408b.getBroadcastItem(i6);
                                                                                z14 = aVar2.a()[aVar2.g()[z15 ? 1 : 0]] == i7;
                                                                            } else {
                                                                                dVar15 = (d) this.f408b.getBroadcastItem(i6);
                                                                                z14 = aVar2.g()[z15 ? 1 : 0] == i7;
                                                                            }
                                                                            dVar15.U(z14);
                                                                            i3 = i6;
                                                                            break;
                                                                        default:
                                                                            i3 = i6;
                                                                            break;
                                                                    }
                                                                    break;
                                                            }
                                                            break;
                                                    }
                                                    break;
                                            }
                                        } else {
                                            i3 = i6;
                                            if (aVar2.a() != null) {
                                                dVar = (d) this.f408b.getBroadcastItem(i3);
                                                z = aVar2.a()[aVar2.g()[0]] == 1;
                                            } else {
                                                dVar = (d) this.f408b.getBroadcastItem(i3);
                                                z = aVar2.g()[0] == 1;
                                            }
                                            dVar.q0(z);
                                        }
                                        i6 = i3;
                                        i5 = -1;
                                        z15 = false;
                                        c2 = 3;
                                        i7 = 1;
                                    }
                                }
                            }
                            i2 = i6;
                            for (int i10 = 0; i10 < this.f409c.length; i10++) {
                                Log.e("LS123", "sendAidlCarServiceList: " + this.f409c[i10]);
                                if (this.f409c[i10]) {
                                    if (i10 == 0) {
                                        ((d) this.f408b.getBroadcastItem(i2)).x(0, 0);
                                    } else if (i10 == 1) {
                                        ((d) this.f408b.getBroadcastItem(i2)).D(0, 0);
                                    } else if (i10 == 2) {
                                        ((d) this.f408b.getBroadcastItem(i2)).R(0, 0);
                                    } else if (i10 == 3) {
                                        ((d) this.f408b.getBroadcastItem(i2)).n0(0, 0);
                                    }
                                }
                            }
                        } catch (Exception unused) {
                            RemoteCallbackList<d> remoteCallbackList2 = this.f408b;
                            remoteCallbackList2.unregister(remoteCallbackList2.getBroadcastItem(i6));
                            this.f408b.finishBroadcast();
                        }
                    }
                    i6 = i2 + 1;
                    i5 = -1;
                    z15 = false;
                }
                this.f408b.finishBroadcast();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void g(float f) {
        try {
            RemoteCallbackList<d> remoteCallbackList = this.f408b;
            if (remoteCallbackList != null) {
                int iBeginBroadcast = remoteCallbackList.beginBroadcast();
                for (int i = 0; i < iBeginBroadcast; i++) {
                    if (this.f408b.getBroadcastItem(i) != null) {
                        try {
                            ((d) this.f408b.getBroadcastItem(i)).d0(null);
                            ((d) this.f408b.getBroadcastItem(i)).b(f, "V");
                        } catch (Exception unused) {
                            RemoteCallbackList<d> remoteCallbackList2 = this.f408b;
                            remoteCallbackList2.unregister(remoteCallbackList2.getBroadcastItem(i));
                            this.f408b.finishBroadcast();
                        }
                    }
                }
                this.f408b.finishBroadcast();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void h(boolean z, int i) {
        try {
            RemoteCallbackList<d> remoteCallbackList = this.f408b;
            if (remoteCallbackList != null) {
                int iBeginBroadcast = remoteCallbackList.beginBroadcast();
                for (int i2 = 0; i2 < iBeginBroadcast; i2++) {
                    if (this.f408b.getBroadcastItem(i2) != null) {
                        try {
                            ((d) this.f408b.getBroadcastItem(i2)).d0(null);
                            if (z) {
                                Log.e("LS123", "sendAidlEngineSpeedl: " + i);
                                ((d) this.f408b.getBroadcastItem(i2)).Y(i);
                            }
                        } catch (Exception unused) {
                            RemoteCallbackList<d> remoteCallbackList2 = this.f408b;
                            remoteCallbackList2.unregister(remoteCallbackList2.getBroadcastItem(i2));
                            this.f408b.finishBroadcast();
                        }
                    }
                }
                this.f408b.finishBroadcast();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void i(boolean z, boolean z2, ArrayList<c.b.a.e.a> arrayList) {
        try {
            RemoteCallbackList<d> remoteCallbackList = this.f408b;
            if (remoteCallbackList != null) {
                int iBeginBroadcast = remoteCallbackList.beginBroadcast();
                for (int i = 0; i < iBeginBroadcast; i++) {
                    if (this.f408b.getBroadcastItem(i) != null) {
                        try {
                            ((d) this.f408b.getBroadcastItem(i)).d0(null);
                            if (z) {
                                ((d) this.f408b.getBroadcastItem(i)).k0(z2);
                            } else if (arrayList != null) {
                                for (c.b.a.e.a aVar : arrayList) {
                                    if (aVar.f397b == 7 && aVar.f != null) {
                                        boolean z3 = true;
                                        if (aVar.U == 1) {
                                            d dVar = (d) this.f408b.getBroadcastItem(i);
                                            if (aVar.f[0] != 1) {
                                                z3 = false;
                                            }
                                            dVar.k0(z3);
                                        }
                                    }
                                }
                            }
                        } catch (Exception unused) {
                            RemoteCallbackList<d> remoteCallbackList2 = this.f408b;
                            remoteCallbackList2.unregister(remoteCallbackList2.getBroadcastItem(i));
                            this.f408b.finishBroadcast();
                        }
                    }
                }
            }
            this.f408b.finishBroadcast();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void j(boolean z) {
        try {
            RemoteCallbackList<d> remoteCallbackList = this.f408b;
            if (remoteCallbackList != null) {
                int iBeginBroadcast = remoteCallbackList.beginBroadcast();
                for (int i = 0; i < iBeginBroadcast; i++) {
                    if (this.f408b.getBroadcastItem(i) != null) {
                        try {
                            ((d) this.f408b.getBroadcastItem(i)).d0(null);
                            ((d) this.f408b.getBroadcastItem(i)).g(z);
                        } catch (Exception unused) {
                            RemoteCallbackList<d> remoteCallbackList2 = this.f408b;
                            remoteCallbackList2.unregister(remoteCallbackList2.getBroadcastItem(i));
                            this.f408b.finishBroadcast();
                        }
                    }
                }
                this.f408b.finishBroadcast();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void k(int i) {
        try {
            RemoteCallbackList<d> remoteCallbackList = this.f408b;
            if (remoteCallbackList != null) {
                int iBeginBroadcast = remoteCallbackList.beginBroadcast();
                for (int i2 = 0; i2 < iBeginBroadcast; i2++) {
                    if (this.f408b.getBroadcastItem(i2) != null) {
                        try {
                            ((d) this.f408b.getBroadcastItem(i2)).d0(null);
                            ((d) this.f408b.getBroadcastItem(i2)).A(i);
                        } catch (Exception unused) {
                            RemoteCallbackList<d> remoteCallbackList2 = this.f408b;
                            remoteCallbackList2.unregister(remoteCallbackList2.getBroadcastItem(i2));
                            this.f408b.finishBroadcast();
                        }
                    }
                }
                this.f408b.finishBroadcast();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void l(int i) {
        try {
            RemoteCallbackList<d> remoteCallbackList = this.f408b;
            if (remoteCallbackList != null) {
                int iBeginBroadcast = remoteCallbackList.beginBroadcast();
                for (int i2 = 0; i2 < iBeginBroadcast; i2++) {
                    if (this.f408b.getBroadcastItem(i2) != null) {
                        try {
                            ((d) this.f408b.getBroadcastItem(i2)).d0(null);
                            ((d) this.f408b.getBroadcastItem(i2)).t(i);
                        } catch (Exception unused) {
                            RemoteCallbackList<d> remoteCallbackList2 = this.f408b;
                            remoteCallbackList2.unregister(remoteCallbackList2.getBroadcastItem(i2));
                            this.f408b.finishBroadcast();
                        }
                    }
                }
                this.f408b.finishBroadcast();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void m(int i, int i2) {
        try {
            RemoteCallbackList<d> remoteCallbackList = this.f408b;
            if (remoteCallbackList != null) {
                int iBeginBroadcast = remoteCallbackList.beginBroadcast();
                for (int i3 = 0; i3 < iBeginBroadcast; i3++) {
                    if (this.f408b.getBroadcastItem(i3) != null) {
                        try {
                            ((d) this.f408b.getBroadcastItem(i3)).d0(null);
                            boolean z = true;
                            if (i == 8) {
                                d dVar = (d) this.f408b.getBroadcastItem(i3);
                                if (i2 != 2) {
                                    z = false;
                                }
                                dVar.w(z);
                            } else if (i == 9) {
                                d dVar2 = (d) this.f408b.getBroadcastItem(i3);
                                if (i2 != 1) {
                                    z = false;
                                }
                                dVar2.o0(z);
                            } else if (i == 15) {
                                d dVar3 = (d) this.f408b.getBroadcastItem(i3);
                                if (i2 != 3) {
                                    z = false;
                                }
                                dVar3.u(z);
                            }
                        } catch (Exception unused) {
                            RemoteCallbackList<d> remoteCallbackList2 = this.f408b;
                            remoteCallbackList2.unregister(remoteCallbackList2.getBroadcastItem(i3));
                            this.f408b.finishBroadcast();
                        }
                    }
                }
                this.f408b.finishBroadcast();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
