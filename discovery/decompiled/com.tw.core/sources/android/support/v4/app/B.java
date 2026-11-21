package android.support.v4.app;

import android.app.Notification;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class B {
    private static Field ml;
    private static boolean mm;
    private static final Object mn = new Object();
    private static final Object mk = new Object();

    B() {
    }

    public static void qX(InterfaceC0019t interfaceC0019t, CharSequence charSequence, boolean z, CharSequence charSequence2, Bitmap bitmap, Bitmap bitmap2, boolean z2) {
        Notification.BigPictureStyle bigPictureStyleBigPicture = new Notification.BigPictureStyle(interfaceC0019t.qR()).setBigContentTitle(charSequence).bigPicture(bitmap);
        if (z2) {
            bigPictureStyleBigPicture.bigLargeIcon(bitmap2);
        }
        if (z) {
            bigPictureStyleBigPicture.setSummaryText(charSequence2);
        }
    }

    public static void qY(InterfaceC0019t interfaceC0019t, CharSequence charSequence, boolean z, CharSequence charSequence2, CharSequence charSequence3) {
        Notification.BigTextStyle bigTextStyleBigText = new Notification.BigTextStyle(interfaceC0019t.qR()).setBigContentTitle(charSequence).bigText(charSequence3);
        if (z) {
            bigTextStyleBigText.setSummaryText(charSequence2);
        }
    }

    public static void qZ(InterfaceC0019t interfaceC0019t, CharSequence charSequence, boolean z, CharSequence charSequence2, ArrayList arrayList) {
        Notification.InboxStyle bigContentTitle = new Notification.InboxStyle(interfaceC0019t.qR()).setBigContentTitle(charSequence);
        if (z) {
            bigContentTitle.setSummaryText(charSequence2);
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            bigContentTitle.addLine((CharSequence) it.next());
        }
    }

    public static SparseArray ra(List list) {
        SparseArray sparseArray = null;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Bundle bundle = (Bundle) list.get(i);
            if (bundle != null) {
                if (sparseArray == null) {
                    sparseArray = new SparseArray();
                }
                sparseArray.put(i, bundle);
            }
        }
        return sparseArray;
    }

    public static Bundle rb(Notification notification) {
        synchronized (mn) {
            if (mm) {
                return null;
            }
            try {
                if (ml == null) {
                    Field declaredField = Notification.class.getDeclaredField("extras");
                    if (!Bundle.class.isAssignableFrom(declaredField.getType())) {
                        Log.e("NotificationCompat", "Notification.extras field is not of type Bundle");
                        mm = true;
                        return null;
                    }
                    declaredField.setAccessible(true);
                    ml = declaredField;
                }
                Bundle bundle = (Bundle) ml.get(notification);
                if (bundle == null) {
                    bundle = new Bundle();
                    ml.set(notification, bundle);
                }
                return bundle;
            } catch (IllegalAccessException e) {
                Log.e("NotificationCompat", "Unable to access notification extras", e);
                mm = true;
                return null;
            } catch (NoSuchFieldException e2) {
                Log.e("NotificationCompat", "Unable to access notification extras", e2);
                mm = true;
                return null;
            }
        }
    }

    public static Bundle rc(Notification.Builder builder, v vVar) {
        builder.addAction(vVar.qM(), vVar.qO(), vVar.qK());
        Bundle bundle = new Bundle(vVar.qL());
        if (vVar.qN() != null) {
            bundle.putParcelableArray("android.support.remoteInputs", N.rh(vVar.qN()));
        }
        return bundle;
    }
}
