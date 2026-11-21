package c.b.a;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.tw.car.EQActivity;
import com.tw.car.R;

/* JADX INFO: loaded from: classes.dex */
public class f extends Handler {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ EQActivity f449a;

    public f(EQActivity eQActivity) {
        this.f449a = eQActivity;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0103 A[Catch: Exception -> 0x0329, TryCatch #0 {Exception -> 0x0329, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:11:0x0086, B:12:0x00a3, B:19:0x00e1, B:21:0x0103, B:22:0x0124, B:29:0x0162, B:32:0x0186, B:33:0x01a1, B:40:0x01e1, B:42:0x0203, B:43:0x021d, B:50:0x025d, B:54:0x0282, B:58:0x029d, B:64:0x02fa, B:65:0x0303, B:66:0x030b, B:67:0x0315, B:68:0x031f, B:44:0x0225, B:46:0x022d, B:47:0x024a, B:49:0x0252, B:34:0x01a9, B:36:0x01b1, B:37:0x01ce, B:39:0x01d6, B:23:0x0128, B:25:0x0130, B:26:0x014f, B:28:0x0157, B:13:0x00ab, B:15:0x00b3, B:16:0x00ce, B:18:0x00d6), top: B:72:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:23:0x0128 A[Catch: Exception -> 0x0329, TryCatch #0 {Exception -> 0x0329, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:11:0x0086, B:12:0x00a3, B:19:0x00e1, B:21:0x0103, B:22:0x0124, B:29:0x0162, B:32:0x0186, B:33:0x01a1, B:40:0x01e1, B:42:0x0203, B:43:0x021d, B:50:0x025d, B:54:0x0282, B:58:0x029d, B:64:0x02fa, B:65:0x0303, B:66:0x030b, B:67:0x0315, B:68:0x031f, B:44:0x0225, B:46:0x022d, B:47:0x024a, B:49:0x0252, B:34:0x01a9, B:36:0x01b1, B:37:0x01ce, B:39:0x01d6, B:23:0x0128, B:25:0x0130, B:26:0x014f, B:28:0x0157, B:13:0x00ab, B:15:0x00b3, B:16:0x00ce, B:18:0x00d6), top: B:72:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:25:0x0130 A[Catch: Exception -> 0x0329, TryCatch #0 {Exception -> 0x0329, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:11:0x0086, B:12:0x00a3, B:19:0x00e1, B:21:0x0103, B:22:0x0124, B:29:0x0162, B:32:0x0186, B:33:0x01a1, B:40:0x01e1, B:42:0x0203, B:43:0x021d, B:50:0x025d, B:54:0x0282, B:58:0x029d, B:64:0x02fa, B:65:0x0303, B:66:0x030b, B:67:0x0315, B:68:0x031f, B:44:0x0225, B:46:0x022d, B:47:0x024a, B:49:0x0252, B:34:0x01a9, B:36:0x01b1, B:37:0x01ce, B:39:0x01d6, B:23:0x0128, B:25:0x0130, B:26:0x014f, B:28:0x0157, B:13:0x00ab, B:15:0x00b3, B:16:0x00ce, B:18:0x00d6), top: B:72:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:26:0x014f A[Catch: Exception -> 0x0329, TryCatch #0 {Exception -> 0x0329, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:11:0x0086, B:12:0x00a3, B:19:0x00e1, B:21:0x0103, B:22:0x0124, B:29:0x0162, B:32:0x0186, B:33:0x01a1, B:40:0x01e1, B:42:0x0203, B:43:0x021d, B:50:0x025d, B:54:0x0282, B:58:0x029d, B:64:0x02fa, B:65:0x0303, B:66:0x030b, B:67:0x0315, B:68:0x031f, B:44:0x0225, B:46:0x022d, B:47:0x024a, B:49:0x0252, B:34:0x01a9, B:36:0x01b1, B:37:0x01ce, B:39:0x01d6, B:23:0x0128, B:25:0x0130, B:26:0x014f, B:28:0x0157, B:13:0x00ab, B:15:0x00b3, B:16:0x00ce, B:18:0x00d6), top: B:72:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:28:0x0157 A[Catch: Exception -> 0x0329, TryCatch #0 {Exception -> 0x0329, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:11:0x0086, B:12:0x00a3, B:19:0x00e1, B:21:0x0103, B:22:0x0124, B:29:0x0162, B:32:0x0186, B:33:0x01a1, B:40:0x01e1, B:42:0x0203, B:43:0x021d, B:50:0x025d, B:54:0x0282, B:58:0x029d, B:64:0x02fa, B:65:0x0303, B:66:0x030b, B:67:0x0315, B:68:0x031f, B:44:0x0225, B:46:0x022d, B:47:0x024a, B:49:0x0252, B:34:0x01a9, B:36:0x01b1, B:37:0x01ce, B:39:0x01d6, B:23:0x0128, B:25:0x0130, B:26:0x014f, B:28:0x0157, B:13:0x00ab, B:15:0x00b3, B:16:0x00ce, B:18:0x00d6), top: B:72:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:32:0x0186 A[Catch: Exception -> 0x0329, TRY_ENTER, TryCatch #0 {Exception -> 0x0329, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:11:0x0086, B:12:0x00a3, B:19:0x00e1, B:21:0x0103, B:22:0x0124, B:29:0x0162, B:32:0x0186, B:33:0x01a1, B:40:0x01e1, B:42:0x0203, B:43:0x021d, B:50:0x025d, B:54:0x0282, B:58:0x029d, B:64:0x02fa, B:65:0x0303, B:66:0x030b, B:67:0x0315, B:68:0x031f, B:44:0x0225, B:46:0x022d, B:47:0x024a, B:49:0x0252, B:34:0x01a9, B:36:0x01b1, B:37:0x01ce, B:39:0x01d6, B:23:0x0128, B:25:0x0130, B:26:0x014f, B:28:0x0157, B:13:0x00ab, B:15:0x00b3, B:16:0x00ce, B:18:0x00d6), top: B:72:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:34:0x01a9 A[Catch: Exception -> 0x0329, TryCatch #0 {Exception -> 0x0329, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:11:0x0086, B:12:0x00a3, B:19:0x00e1, B:21:0x0103, B:22:0x0124, B:29:0x0162, B:32:0x0186, B:33:0x01a1, B:40:0x01e1, B:42:0x0203, B:43:0x021d, B:50:0x025d, B:54:0x0282, B:58:0x029d, B:64:0x02fa, B:65:0x0303, B:66:0x030b, B:67:0x0315, B:68:0x031f, B:44:0x0225, B:46:0x022d, B:47:0x024a, B:49:0x0252, B:34:0x01a9, B:36:0x01b1, B:37:0x01ce, B:39:0x01d6, B:23:0x0128, B:25:0x0130, B:26:0x014f, B:28:0x0157, B:13:0x00ab, B:15:0x00b3, B:16:0x00ce, B:18:0x00d6), top: B:72:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:36:0x01b1 A[Catch: Exception -> 0x0329, TryCatch #0 {Exception -> 0x0329, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:11:0x0086, B:12:0x00a3, B:19:0x00e1, B:21:0x0103, B:22:0x0124, B:29:0x0162, B:32:0x0186, B:33:0x01a1, B:40:0x01e1, B:42:0x0203, B:43:0x021d, B:50:0x025d, B:54:0x0282, B:58:0x029d, B:64:0x02fa, B:65:0x0303, B:66:0x030b, B:67:0x0315, B:68:0x031f, B:44:0x0225, B:46:0x022d, B:47:0x024a, B:49:0x0252, B:34:0x01a9, B:36:0x01b1, B:37:0x01ce, B:39:0x01d6, B:23:0x0128, B:25:0x0130, B:26:0x014f, B:28:0x0157, B:13:0x00ab, B:15:0x00b3, B:16:0x00ce, B:18:0x00d6), top: B:72:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:37:0x01ce A[Catch: Exception -> 0x0329, TryCatch #0 {Exception -> 0x0329, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:11:0x0086, B:12:0x00a3, B:19:0x00e1, B:21:0x0103, B:22:0x0124, B:29:0x0162, B:32:0x0186, B:33:0x01a1, B:40:0x01e1, B:42:0x0203, B:43:0x021d, B:50:0x025d, B:54:0x0282, B:58:0x029d, B:64:0x02fa, B:65:0x0303, B:66:0x030b, B:67:0x0315, B:68:0x031f, B:44:0x0225, B:46:0x022d, B:47:0x024a, B:49:0x0252, B:34:0x01a9, B:36:0x01b1, B:37:0x01ce, B:39:0x01d6, B:23:0x0128, B:25:0x0130, B:26:0x014f, B:28:0x0157, B:13:0x00ab, B:15:0x00b3, B:16:0x00ce, B:18:0x00d6), top: B:72:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:39:0x01d6 A[Catch: Exception -> 0x0329, TryCatch #0 {Exception -> 0x0329, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:11:0x0086, B:12:0x00a3, B:19:0x00e1, B:21:0x0103, B:22:0x0124, B:29:0x0162, B:32:0x0186, B:33:0x01a1, B:40:0x01e1, B:42:0x0203, B:43:0x021d, B:50:0x025d, B:54:0x0282, B:58:0x029d, B:64:0x02fa, B:65:0x0303, B:66:0x030b, B:67:0x0315, B:68:0x031f, B:44:0x0225, B:46:0x022d, B:47:0x024a, B:49:0x0252, B:34:0x01a9, B:36:0x01b1, B:37:0x01ce, B:39:0x01d6, B:23:0x0128, B:25:0x0130, B:26:0x014f, B:28:0x0157, B:13:0x00ab, B:15:0x00b3, B:16:0x00ce, B:18:0x00d6), top: B:72:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:42:0x0203 A[Catch: Exception -> 0x0329, TryCatch #0 {Exception -> 0x0329, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:11:0x0086, B:12:0x00a3, B:19:0x00e1, B:21:0x0103, B:22:0x0124, B:29:0x0162, B:32:0x0186, B:33:0x01a1, B:40:0x01e1, B:42:0x0203, B:43:0x021d, B:50:0x025d, B:54:0x0282, B:58:0x029d, B:64:0x02fa, B:65:0x0303, B:66:0x030b, B:67:0x0315, B:68:0x031f, B:44:0x0225, B:46:0x022d, B:47:0x024a, B:49:0x0252, B:34:0x01a9, B:36:0x01b1, B:37:0x01ce, B:39:0x01d6, B:23:0x0128, B:25:0x0130, B:26:0x014f, B:28:0x0157, B:13:0x00ab, B:15:0x00b3, B:16:0x00ce, B:18:0x00d6), top: B:72:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:44:0x0225 A[Catch: Exception -> 0x0329, TryCatch #0 {Exception -> 0x0329, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:11:0x0086, B:12:0x00a3, B:19:0x00e1, B:21:0x0103, B:22:0x0124, B:29:0x0162, B:32:0x0186, B:33:0x01a1, B:40:0x01e1, B:42:0x0203, B:43:0x021d, B:50:0x025d, B:54:0x0282, B:58:0x029d, B:64:0x02fa, B:65:0x0303, B:66:0x030b, B:67:0x0315, B:68:0x031f, B:44:0x0225, B:46:0x022d, B:47:0x024a, B:49:0x0252, B:34:0x01a9, B:36:0x01b1, B:37:0x01ce, B:39:0x01d6, B:23:0x0128, B:25:0x0130, B:26:0x014f, B:28:0x0157, B:13:0x00ab, B:15:0x00b3, B:16:0x00ce, B:18:0x00d6), top: B:72:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:46:0x022d A[Catch: Exception -> 0x0329, TryCatch #0 {Exception -> 0x0329, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:11:0x0086, B:12:0x00a3, B:19:0x00e1, B:21:0x0103, B:22:0x0124, B:29:0x0162, B:32:0x0186, B:33:0x01a1, B:40:0x01e1, B:42:0x0203, B:43:0x021d, B:50:0x025d, B:54:0x0282, B:58:0x029d, B:64:0x02fa, B:65:0x0303, B:66:0x030b, B:67:0x0315, B:68:0x031f, B:44:0x0225, B:46:0x022d, B:47:0x024a, B:49:0x0252, B:34:0x01a9, B:36:0x01b1, B:37:0x01ce, B:39:0x01d6, B:23:0x0128, B:25:0x0130, B:26:0x014f, B:28:0x0157, B:13:0x00ab, B:15:0x00b3, B:16:0x00ce, B:18:0x00d6), top: B:72:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:47:0x024a A[Catch: Exception -> 0x0329, TryCatch #0 {Exception -> 0x0329, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:11:0x0086, B:12:0x00a3, B:19:0x00e1, B:21:0x0103, B:22:0x0124, B:29:0x0162, B:32:0x0186, B:33:0x01a1, B:40:0x01e1, B:42:0x0203, B:43:0x021d, B:50:0x025d, B:54:0x0282, B:58:0x029d, B:64:0x02fa, B:65:0x0303, B:66:0x030b, B:67:0x0315, B:68:0x031f, B:44:0x0225, B:46:0x022d, B:47:0x024a, B:49:0x0252, B:34:0x01a9, B:36:0x01b1, B:37:0x01ce, B:39:0x01d6, B:23:0x0128, B:25:0x0130, B:26:0x014f, B:28:0x0157, B:13:0x00ab, B:15:0x00b3, B:16:0x00ce, B:18:0x00d6), top: B:72:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:49:0x0252 A[Catch: Exception -> 0x0329, TryCatch #0 {Exception -> 0x0329, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:11:0x0086, B:12:0x00a3, B:19:0x00e1, B:21:0x0103, B:22:0x0124, B:29:0x0162, B:32:0x0186, B:33:0x01a1, B:40:0x01e1, B:42:0x0203, B:43:0x021d, B:50:0x025d, B:54:0x0282, B:58:0x029d, B:64:0x02fa, B:65:0x0303, B:66:0x030b, B:67:0x0315, B:68:0x031f, B:44:0x0225, B:46:0x022d, B:47:0x024a, B:49:0x0252, B:34:0x01a9, B:36:0x01b1, B:37:0x01ce, B:39:0x01d6, B:23:0x0128, B:25:0x0130, B:26:0x014f, B:28:0x0157, B:13:0x00ab, B:15:0x00b3, B:16:0x00ce, B:18:0x00d6), top: B:72:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:52:0x027f  */
    /* JADX WARN: Code duplicated, block: B:53:0x0281  */
    /* JADX WARN: Code duplicated, block: B:56:0x029a  */
    /* JADX WARN: Code duplicated, block: B:57:0x029c  */
    /* JADX WARN: Code duplicated, block: B:60:0x02f3 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:61:0x02f5 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:62:0x02f7 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:63:0x02f9  */
    /* JADX WARN: Code duplicated, block: B:64:0x02fa A[Catch: Exception -> 0x0329, TryCatch #0 {Exception -> 0x0329, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:11:0x0086, B:12:0x00a3, B:19:0x00e1, B:21:0x0103, B:22:0x0124, B:29:0x0162, B:32:0x0186, B:33:0x01a1, B:40:0x01e1, B:42:0x0203, B:43:0x021d, B:50:0x025d, B:54:0x0282, B:58:0x029d, B:64:0x02fa, B:65:0x0303, B:66:0x030b, B:67:0x0315, B:68:0x031f, B:44:0x0225, B:46:0x022d, B:47:0x024a, B:49:0x0252, B:34:0x01a9, B:36:0x01b1, B:37:0x01ce, B:39:0x01d6, B:23:0x0128, B:25:0x0130, B:26:0x014f, B:28:0x0157, B:13:0x00ab, B:15:0x00b3, B:16:0x00ce, B:18:0x00d6), top: B:72:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:66:0x030b A[Catch: Exception -> 0x0329, TryCatch #0 {Exception -> 0x0329, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:11:0x0086, B:12:0x00a3, B:19:0x00e1, B:21:0x0103, B:22:0x0124, B:29:0x0162, B:32:0x0186, B:33:0x01a1, B:40:0x01e1, B:42:0x0203, B:43:0x021d, B:50:0x025d, B:54:0x0282, B:58:0x029d, B:64:0x02fa, B:65:0x0303, B:66:0x030b, B:67:0x0315, B:68:0x031f, B:44:0x0225, B:46:0x022d, B:47:0x024a, B:49:0x0252, B:34:0x01a9, B:36:0x01b1, B:37:0x01ce, B:39:0x01d6, B:23:0x0128, B:25:0x0130, B:26:0x014f, B:28:0x0157, B:13:0x00ab, B:15:0x00b3, B:16:0x00ce, B:18:0x00d6), top: B:72:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:67:0x0315 A[Catch: Exception -> 0x0329, TryCatch #0 {Exception -> 0x0329, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:11:0x0086, B:12:0x00a3, B:19:0x00e1, B:21:0x0103, B:22:0x0124, B:29:0x0162, B:32:0x0186, B:33:0x01a1, B:40:0x01e1, B:42:0x0203, B:43:0x021d, B:50:0x025d, B:54:0x0282, B:58:0x029d, B:64:0x02fa, B:65:0x0303, B:66:0x030b, B:67:0x0315, B:68:0x031f, B:44:0x0225, B:46:0x022d, B:47:0x024a, B:49:0x0252, B:34:0x01a9, B:36:0x01b1, B:37:0x01ce, B:39:0x01d6, B:23:0x0128, B:25:0x0130, B:26:0x014f, B:28:0x0157, B:13:0x00ab, B:15:0x00b3, B:16:0x00ce, B:18:0x00d6), top: B:72:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:68:0x031f A[Catch: Exception -> 0x0329, TRY_LEAVE, TryCatch #0 {Exception -> 0x0329, blocks: (B:2:0x0000, B:5:0x0008, B:8:0x0014, B:11:0x0086, B:12:0x00a3, B:19:0x00e1, B:21:0x0103, B:22:0x0124, B:29:0x0162, B:32:0x0186, B:33:0x01a1, B:40:0x01e1, B:42:0x0203, B:43:0x021d, B:50:0x025d, B:54:0x0282, B:58:0x029d, B:64:0x02fa, B:65:0x0303, B:66:0x030b, B:67:0x0315, B:68:0x031f, B:44:0x0225, B:46:0x022d, B:47:0x024a, B:49:0x0252, B:34:0x01a9, B:36:0x01b1, B:37:0x01ce, B:39:0x01d6, B:23:0x0128, B:25:0x0130, B:26:0x014f, B:28:0x0157, B:13:0x00ab, B:15:0x00b3, B:16:0x00ce, B:18:0x00d6), top: B:72:0x0000 }] */
    /* JADX WARN: Instruction removed from duplicated block: B:21:0x0103, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:25:0x0130, please report this as an issue */
    @Override // android.os.Handler
    public void handleMessage(Message message) {
        TextView textView;
        StringBuilder sb;
        TextView textView2;
        String str;
        TextView textView3;
        StringBuilder sb2;
        TextView textView4;
        StringBuilder sb3;
        int i;
        int i2;
        int i3;
        View viewFindViewById;
        try {
            if (message.what != 1281) {
                return;
            }
            byte[] bArr = (byte[]) message.obj;
            if (message.arg1 != 23) {
                return;
            }
            this.f449a.f467b = bArr[1] & 255;
            ProgressBar progressBar = (ProgressBar) this.f449a.findViewById(R.id.volume_progress);
            progressBar.setMax(32);
            progressBar.setProgress(this.f449a.f467b);
            ((TextView) this.f449a.findViewById(R.id.volume)).setText(this.f449a.f467b + "");
            this.f449a.f = bArr[2];
            EQActivity.a(this.f449a, R.id.fader_progress, this.f449a.f - 1, 18);
            if (this.f449a.f >= 10) {
                if (this.f449a.f > 10) {
                    textView = (TextView) this.f449a.findViewById(R.id.fader);
                    sb = new StringBuilder();
                    sb.append("R");
                    sb.append(this.f449a.f - 10);
                } else if (this.f449a.f == 10) {
                    ((TextView) this.f449a.findViewById(R.id.fader)).setText("0");
                }
                this.f449a.e = bArr[3];
                EQActivity.a(this.f449a, R.id.balance_progress, this.f449a.e - 1, 18);
                if (this.f449a.e < 10) {
                    if (this.f449a.e > 10) {
                        textView2 = (TextView) this.f449a.findViewById(R.id.balance);
                        str = "R" + (this.f449a.e - 10);
                    } else if (this.f449a.e == 10) {
                        ((TextView) this.f449a.findViewById(R.id.balance)).setText("0");
                    }
                    this.f449a.f468c = bArr[4];
                    EQActivity.a(this.f449a, R.id.bass_progress, this.f449a.f468c - 1, 18);
                    if (this.f449a.f468c < 10) {
                        if (this.f449a.f468c > 10) {
                            textView3 = (TextView) this.f449a.findViewById(R.id.bass);
                            sb2 = new StringBuilder();
                            sb2.append("+");
                            sb2.append(this.f449a.f468c - 10);
                        } else if (this.f449a.f468c == 10) {
                            ((TextView) this.f449a.findViewById(R.id.bass)).setText("0");
                        }
                        this.f449a.d = bArr[5];
                        EQActivity.a(this.f449a, R.id.treblr_progress, this.f449a.d - 1, 18);
                        if (this.f449a.d < 10) {
                            if (this.f449a.d > 10) {
                                textView4 = (TextView) this.f449a.findViewById(R.id.treblr);
                                sb3 = new StringBuilder();
                                sb3.append("+");
                                sb3.append(this.f449a.d - 10);
                            } else if (this.f449a.d == 10) {
                                ((TextView) this.f449a.findViewById(R.id.treblr)).setText("0");
                            }
                            this.f449a.g = h.a(bArr[7], 7);
                            Drawable background = this.f449a.findViewById(R.id.sound_eq_have_off).getBackground();
                            if (this.f449a.g) {
                                i = 0;
                            } else {
                                i = 1;
                            }
                            background.setLevel(i);
                            Drawable background2 = this.f449a.findViewById(R.id.sound_eq_have_on).getBackground();
                            if (this.f449a.g) {
                                i2 = 1;
                            } else {
                                i2 = 0;
                            }
                            background2.setLevel(i2);
                            this.f449a.h = h.a(bArr[7], 4, 2);
                            this.f449a.findViewById(R.id.sound_b_f_mode_0).getBackground().setLevel(0);
                            this.f449a.findViewById(R.id.sound_b_f_mode_1).getBackground().setLevel(0);
                            this.f449a.findViewById(R.id.sound_b_f_mode_2).getBackground().setLevel(0);
                            this.f449a.findViewById(R.id.sound_b_f_mode_3).getBackground().setLevel(0);
                            i3 = this.f449a.h;
                            if (i3 != 0) {
                                viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_0);
                            } else if (i3 != 1) {
                                viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_1);
                            } else if (i3 != 2) {
                                viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_2);
                            } else if (i3 != 3) {
                                return;
                            } else {
                                viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_3);
                            }
                            viewFindViewById.getBackground().setLevel(1);
                        }
                        textView4 = (TextView) this.f449a.findViewById(R.id.treblr);
                        sb3 = new StringBuilder();
                        sb3.append("-");
                        sb3.append(10 - this.f449a.d);
                        textView4.setText(sb3.toString());
                        this.f449a.g = h.a(bArr[7], 7);
                        Drawable background3 = this.f449a.findViewById(R.id.sound_eq_have_off).getBackground();
                        if (this.f449a.g) {
                            i = 0;
                        } else {
                            i = 1;
                        }
                        background3.setLevel(i);
                        Drawable background4 = this.f449a.findViewById(R.id.sound_eq_have_on).getBackground();
                        if (this.f449a.g) {
                            i2 = 1;
                        } else {
                            i2 = 0;
                        }
                        background4.setLevel(i2);
                        this.f449a.h = h.a(bArr[7], 4, 2);
                        this.f449a.findViewById(R.id.sound_b_f_mode_0).getBackground().setLevel(0);
                        this.f449a.findViewById(R.id.sound_b_f_mode_1).getBackground().setLevel(0);
                        this.f449a.findViewById(R.id.sound_b_f_mode_2).getBackground().setLevel(0);
                        this.f449a.findViewById(R.id.sound_b_f_mode_3).getBackground().setLevel(0);
                        i3 = this.f449a.h;
                        if (i3 != 0) {
                            viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_0);
                        } else if (i3 != 1) {
                            viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_1);
                        } else if (i3 != 2) {
                            viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_2);
                        } else if (i3 != 3) {
                            return;
                        } else {
                            viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_3);
                        }
                        viewFindViewById.getBackground().setLevel(1);
                    }
                    textView3 = (TextView) this.f449a.findViewById(R.id.bass);
                    sb2 = new StringBuilder();
                    sb2.append("-");
                    sb2.append(10 - this.f449a.f468c);
                    textView3.setText(sb2.toString());
                    this.f449a.d = bArr[5];
                    EQActivity.a(this.f449a, R.id.treblr_progress, this.f449a.d - 1, 18);
                    if (this.f449a.d < 10) {
                        if (this.f449a.d > 10) {
                            textView4 = (TextView) this.f449a.findViewById(R.id.treblr);
                            sb3 = new StringBuilder();
                            sb3.append("+");
                            sb3.append(this.f449a.d - 10);
                        } else if (this.f449a.d == 10) {
                            ((TextView) this.f449a.findViewById(R.id.treblr)).setText("0");
                        }
                        this.f449a.g = h.a(bArr[7], 7);
                        Drawable background5 = this.f449a.findViewById(R.id.sound_eq_have_off).getBackground();
                        if (this.f449a.g) {
                            i = 0;
                        } else {
                            i = 1;
                        }
                        background5.setLevel(i);
                        Drawable background6 = this.f449a.findViewById(R.id.sound_eq_have_on).getBackground();
                        if (this.f449a.g) {
                            i2 = 1;
                        } else {
                            i2 = 0;
                        }
                        background6.setLevel(i2);
                        this.f449a.h = h.a(bArr[7], 4, 2);
                        this.f449a.findViewById(R.id.sound_b_f_mode_0).getBackground().setLevel(0);
                        this.f449a.findViewById(R.id.sound_b_f_mode_1).getBackground().setLevel(0);
                        this.f449a.findViewById(R.id.sound_b_f_mode_2).getBackground().setLevel(0);
                        this.f449a.findViewById(R.id.sound_b_f_mode_3).getBackground().setLevel(0);
                        i3 = this.f449a.h;
                        if (i3 != 0) {
                            viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_0);
                        } else if (i3 != 1) {
                            viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_1);
                        } else if (i3 != 2) {
                            viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_2);
                        } else if (i3 != 3) {
                            return;
                        } else {
                            viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_3);
                        }
                        viewFindViewById.getBackground().setLevel(1);
                    }
                    textView4 = (TextView) this.f449a.findViewById(R.id.treblr);
                    sb3 = new StringBuilder();
                    sb3.append("-");
                    sb3.append(10 - this.f449a.d);
                    textView4.setText(sb3.toString());
                    this.f449a.g = h.a(bArr[7], 7);
                    Drawable background7 = this.f449a.findViewById(R.id.sound_eq_have_off).getBackground();
                    if (this.f449a.g) {
                        i = 0;
                    } else {
                        i = 1;
                    }
                    background7.setLevel(i);
                    Drawable background8 = this.f449a.findViewById(R.id.sound_eq_have_on).getBackground();
                    if (this.f449a.g) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    background8.setLevel(i2);
                    this.f449a.h = h.a(bArr[7], 4, 2);
                    this.f449a.findViewById(R.id.sound_b_f_mode_0).getBackground().setLevel(0);
                    this.f449a.findViewById(R.id.sound_b_f_mode_1).getBackground().setLevel(0);
                    this.f449a.findViewById(R.id.sound_b_f_mode_2).getBackground().setLevel(0);
                    this.f449a.findViewById(R.id.sound_b_f_mode_3).getBackground().setLevel(0);
                    i3 = this.f449a.h;
                    if (i3 != 0) {
                        viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_0);
                    } else if (i3 != 1) {
                        viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_1);
                    } else if (i3 != 2) {
                        viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_2);
                    } else if (i3 != 3) {
                        return;
                    } else {
                        viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_3);
                    }
                    viewFindViewById.getBackground().setLevel(1);
                }
                textView2 = (TextView) this.f449a.findViewById(R.id.balance);
                str = "L" + (10 - this.f449a.e);
                textView2.setText(str);
                this.f449a.f468c = bArr[4];
                EQActivity.a(this.f449a, R.id.bass_progress, this.f449a.f468c - 1, 18);
                if (this.f449a.f468c < 10) {
                    if (this.f449a.f468c > 10) {
                        textView3 = (TextView) this.f449a.findViewById(R.id.bass);
                        sb2 = new StringBuilder();
                        sb2.append("+");
                        sb2.append(this.f449a.f468c - 10);
                    } else if (this.f449a.f468c == 10) {
                        ((TextView) this.f449a.findViewById(R.id.bass)).setText("0");
                    }
                    this.f449a.d = bArr[5];
                    EQActivity.a(this.f449a, R.id.treblr_progress, this.f449a.d - 1, 18);
                    if (this.f449a.d < 10) {
                        if (this.f449a.d > 10) {
                            textView4 = (TextView) this.f449a.findViewById(R.id.treblr);
                            sb3 = new StringBuilder();
                            sb3.append("+");
                            sb3.append(this.f449a.d - 10);
                        } else if (this.f449a.d == 10) {
                            ((TextView) this.f449a.findViewById(R.id.treblr)).setText("0");
                        }
                        this.f449a.g = h.a(bArr[7], 7);
                        Drawable background9 = this.f449a.findViewById(R.id.sound_eq_have_off).getBackground();
                        if (this.f449a.g) {
                            i = 0;
                        } else {
                            i = 1;
                        }
                        background9.setLevel(i);
                        Drawable background10 = this.f449a.findViewById(R.id.sound_eq_have_on).getBackground();
                        if (this.f449a.g) {
                            i2 = 1;
                        } else {
                            i2 = 0;
                        }
                        background10.setLevel(i2);
                        this.f449a.h = h.a(bArr[7], 4, 2);
                        this.f449a.findViewById(R.id.sound_b_f_mode_0).getBackground().setLevel(0);
                        this.f449a.findViewById(R.id.sound_b_f_mode_1).getBackground().setLevel(0);
                        this.f449a.findViewById(R.id.sound_b_f_mode_2).getBackground().setLevel(0);
                        this.f449a.findViewById(R.id.sound_b_f_mode_3).getBackground().setLevel(0);
                        i3 = this.f449a.h;
                        if (i3 != 0) {
                            viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_0);
                        } else if (i3 != 1) {
                            viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_1);
                        } else if (i3 != 2) {
                            viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_2);
                        } else if (i3 != 3) {
                            return;
                        } else {
                            viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_3);
                        }
                        viewFindViewById.getBackground().setLevel(1);
                    }
                    textView4 = (TextView) this.f449a.findViewById(R.id.treblr);
                    sb3 = new StringBuilder();
                    sb3.append("-");
                    sb3.append(10 - this.f449a.d);
                    textView4.setText(sb3.toString());
                    this.f449a.g = h.a(bArr[7], 7);
                    Drawable background11 = this.f449a.findViewById(R.id.sound_eq_have_off).getBackground();
                    if (this.f449a.g) {
                        i = 0;
                    } else {
                        i = 1;
                    }
                    background11.setLevel(i);
                    Drawable background12 = this.f449a.findViewById(R.id.sound_eq_have_on).getBackground();
                    if (this.f449a.g) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    background12.setLevel(i2);
                    this.f449a.h = h.a(bArr[7], 4, 2);
                    this.f449a.findViewById(R.id.sound_b_f_mode_0).getBackground().setLevel(0);
                    this.f449a.findViewById(R.id.sound_b_f_mode_1).getBackground().setLevel(0);
                    this.f449a.findViewById(R.id.sound_b_f_mode_2).getBackground().setLevel(0);
                    this.f449a.findViewById(R.id.sound_b_f_mode_3).getBackground().setLevel(0);
                    i3 = this.f449a.h;
                    if (i3 != 0) {
                        viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_0);
                    } else if (i3 != 1) {
                        viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_1);
                    } else if (i3 != 2) {
                        viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_2);
                    } else if (i3 != 3) {
                        return;
                    } else {
                        viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_3);
                    }
                    viewFindViewById.getBackground().setLevel(1);
                }
                textView3 = (TextView) this.f449a.findViewById(R.id.bass);
                sb2 = new StringBuilder();
                sb2.append("-");
                sb2.append(10 - this.f449a.f468c);
                textView3.setText(sb2.toString());
                this.f449a.d = bArr[5];
                EQActivity.a(this.f449a, R.id.treblr_progress, this.f449a.d - 1, 18);
                if (this.f449a.d < 10) {
                    if (this.f449a.d > 10) {
                        textView4 = (TextView) this.f449a.findViewById(R.id.treblr);
                        sb3 = new StringBuilder();
                        sb3.append("+");
                        sb3.append(this.f449a.d - 10);
                    } else if (this.f449a.d == 10) {
                        ((TextView) this.f449a.findViewById(R.id.treblr)).setText("0");
                    }
                    this.f449a.g = h.a(bArr[7], 7);
                    Drawable background13 = this.f449a.findViewById(R.id.sound_eq_have_off).getBackground();
                    if (this.f449a.g) {
                        i = 0;
                    } else {
                        i = 1;
                    }
                    background13.setLevel(i);
                    Drawable background14 = this.f449a.findViewById(R.id.sound_eq_have_on).getBackground();
                    if (this.f449a.g) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    background14.setLevel(i2);
                    this.f449a.h = h.a(bArr[7], 4, 2);
                    this.f449a.findViewById(R.id.sound_b_f_mode_0).getBackground().setLevel(0);
                    this.f449a.findViewById(R.id.sound_b_f_mode_1).getBackground().setLevel(0);
                    this.f449a.findViewById(R.id.sound_b_f_mode_2).getBackground().setLevel(0);
                    this.f449a.findViewById(R.id.sound_b_f_mode_3).getBackground().setLevel(0);
                    i3 = this.f449a.h;
                    if (i3 != 0) {
                        viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_0);
                    } else if (i3 != 1) {
                        viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_1);
                    } else if (i3 != 2) {
                        viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_2);
                    } else if (i3 != 3) {
                        return;
                    } else {
                        viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_3);
                    }
                    viewFindViewById.getBackground().setLevel(1);
                }
                textView4 = (TextView) this.f449a.findViewById(R.id.treblr);
                sb3 = new StringBuilder();
                sb3.append("-");
                sb3.append(10 - this.f449a.d);
                textView4.setText(sb3.toString());
                this.f449a.g = h.a(bArr[7], 7);
                Drawable background15 = this.f449a.findViewById(R.id.sound_eq_have_off).getBackground();
                if (this.f449a.g) {
                    i = 0;
                } else {
                    i = 1;
                }
                background15.setLevel(i);
                Drawable background16 = this.f449a.findViewById(R.id.sound_eq_have_on).getBackground();
                if (this.f449a.g) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                background16.setLevel(i2);
                this.f449a.h = h.a(bArr[7], 4, 2);
                this.f449a.findViewById(R.id.sound_b_f_mode_0).getBackground().setLevel(0);
                this.f449a.findViewById(R.id.sound_b_f_mode_1).getBackground().setLevel(0);
                this.f449a.findViewById(R.id.sound_b_f_mode_2).getBackground().setLevel(0);
                this.f449a.findViewById(R.id.sound_b_f_mode_3).getBackground().setLevel(0);
                i3 = this.f449a.h;
                if (i3 != 0) {
                    viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_0);
                } else if (i3 != 1) {
                    viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_1);
                } else if (i3 != 2) {
                    viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_2);
                } else if (i3 != 3) {
                    return;
                } else {
                    viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_3);
                }
                viewFindViewById.getBackground().setLevel(1);
            }
            textView = (TextView) this.f449a.findViewById(R.id.fader);
            sb = new StringBuilder();
            sb.append("F");
            sb.append(10 - this.f449a.f);
            textView.setText(sb.toString());
            this.f449a.e = bArr[3];
            EQActivity.a(this.f449a, R.id.balance_progress, this.f449a.e - 1, 18);
            if (this.f449a.e < 10) {
                if (this.f449a.e > 10) {
                    textView2 = (TextView) this.f449a.findViewById(R.id.balance);
                    str = "R" + (this.f449a.e - 10);
                } else if (this.f449a.e == 10) {
                    ((TextView) this.f449a.findViewById(R.id.balance)).setText("0");
                }
                this.f449a.f468c = bArr[4];
                EQActivity.a(this.f449a, R.id.bass_progress, this.f449a.f468c - 1, 18);
                if (this.f449a.f468c < 10) {
                    if (this.f449a.f468c > 10) {
                        textView3 = (TextView) this.f449a.findViewById(R.id.bass);
                        sb2 = new StringBuilder();
                        sb2.append("+");
                        sb2.append(this.f449a.f468c - 10);
                    } else if (this.f449a.f468c == 10) {
                        ((TextView) this.f449a.findViewById(R.id.bass)).setText("0");
                    }
                    this.f449a.d = bArr[5];
                    EQActivity.a(this.f449a, R.id.treblr_progress, this.f449a.d - 1, 18);
                    if (this.f449a.d < 10) {
                        if (this.f449a.d > 10) {
                            textView4 = (TextView) this.f449a.findViewById(R.id.treblr);
                            sb3 = new StringBuilder();
                            sb3.append("+");
                            sb3.append(this.f449a.d - 10);
                        } else if (this.f449a.d == 10) {
                            ((TextView) this.f449a.findViewById(R.id.treblr)).setText("0");
                        }
                        this.f449a.g = h.a(bArr[7], 7);
                        Drawable background17 = this.f449a.findViewById(R.id.sound_eq_have_off).getBackground();
                        if (this.f449a.g) {
                            i = 0;
                        } else {
                            i = 1;
                        }
                        background17.setLevel(i);
                        Drawable background18 = this.f449a.findViewById(R.id.sound_eq_have_on).getBackground();
                        if (this.f449a.g) {
                            i2 = 1;
                        } else {
                            i2 = 0;
                        }
                        background18.setLevel(i2);
                        this.f449a.h = h.a(bArr[7], 4, 2);
                        this.f449a.findViewById(R.id.sound_b_f_mode_0).getBackground().setLevel(0);
                        this.f449a.findViewById(R.id.sound_b_f_mode_1).getBackground().setLevel(0);
                        this.f449a.findViewById(R.id.sound_b_f_mode_2).getBackground().setLevel(0);
                        this.f449a.findViewById(R.id.sound_b_f_mode_3).getBackground().setLevel(0);
                        i3 = this.f449a.h;
                        if (i3 != 0) {
                            viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_0);
                        } else if (i3 != 1) {
                            viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_1);
                        } else if (i3 != 2) {
                            viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_2);
                        } else if (i3 != 3) {
                            return;
                        } else {
                            viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_3);
                        }
                        viewFindViewById.getBackground().setLevel(1);
                    }
                    textView4 = (TextView) this.f449a.findViewById(R.id.treblr);
                    sb3 = new StringBuilder();
                    sb3.append("-");
                    sb3.append(10 - this.f449a.d);
                    textView4.setText(sb3.toString());
                    this.f449a.g = h.a(bArr[7], 7);
                    Drawable background19 = this.f449a.findViewById(R.id.sound_eq_have_off).getBackground();
                    if (this.f449a.g) {
                        i = 0;
                    } else {
                        i = 1;
                    }
                    background19.setLevel(i);
                    Drawable background110 = this.f449a.findViewById(R.id.sound_eq_have_on).getBackground();
                    if (this.f449a.g) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    background110.setLevel(i2);
                    this.f449a.h = h.a(bArr[7], 4, 2);
                    this.f449a.findViewById(R.id.sound_b_f_mode_0).getBackground().setLevel(0);
                    this.f449a.findViewById(R.id.sound_b_f_mode_1).getBackground().setLevel(0);
                    this.f449a.findViewById(R.id.sound_b_f_mode_2).getBackground().setLevel(0);
                    this.f449a.findViewById(R.id.sound_b_f_mode_3).getBackground().setLevel(0);
                    i3 = this.f449a.h;
                    if (i3 != 0) {
                        viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_0);
                    } else if (i3 != 1) {
                        viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_1);
                    } else if (i3 != 2) {
                        viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_2);
                    } else if (i3 != 3) {
                        return;
                    } else {
                        viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_3);
                    }
                    viewFindViewById.getBackground().setLevel(1);
                }
                textView3 = (TextView) this.f449a.findViewById(R.id.bass);
                sb2 = new StringBuilder();
                sb2.append("-");
                sb2.append(10 - this.f449a.f468c);
                textView3.setText(sb2.toString());
                this.f449a.d = bArr[5];
                EQActivity.a(this.f449a, R.id.treblr_progress, this.f449a.d - 1, 18);
                if (this.f449a.d < 10) {
                    if (this.f449a.d > 10) {
                        textView4 = (TextView) this.f449a.findViewById(R.id.treblr);
                        sb3 = new StringBuilder();
                        sb3.append("+");
                        sb3.append(this.f449a.d - 10);
                    } else if (this.f449a.d == 10) {
                        ((TextView) this.f449a.findViewById(R.id.treblr)).setText("0");
                    }
                    this.f449a.g = h.a(bArr[7], 7);
                    Drawable background111 = this.f449a.findViewById(R.id.sound_eq_have_off).getBackground();
                    if (this.f449a.g) {
                        i = 0;
                    } else {
                        i = 1;
                    }
                    background111.setLevel(i);
                    Drawable background112 = this.f449a.findViewById(R.id.sound_eq_have_on).getBackground();
                    if (this.f449a.g) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    background112.setLevel(i2);
                    this.f449a.h = h.a(bArr[7], 4, 2);
                    this.f449a.findViewById(R.id.sound_b_f_mode_0).getBackground().setLevel(0);
                    this.f449a.findViewById(R.id.sound_b_f_mode_1).getBackground().setLevel(0);
                    this.f449a.findViewById(R.id.sound_b_f_mode_2).getBackground().setLevel(0);
                    this.f449a.findViewById(R.id.sound_b_f_mode_3).getBackground().setLevel(0);
                    i3 = this.f449a.h;
                    if (i3 != 0) {
                        viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_0);
                    } else if (i3 != 1) {
                        viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_1);
                    } else if (i3 != 2) {
                        viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_2);
                    } else if (i3 != 3) {
                        return;
                    } else {
                        viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_3);
                    }
                    viewFindViewById.getBackground().setLevel(1);
                }
                textView4 = (TextView) this.f449a.findViewById(R.id.treblr);
                sb3 = new StringBuilder();
                sb3.append("-");
                sb3.append(10 - this.f449a.d);
                textView4.setText(sb3.toString());
                this.f449a.g = h.a(bArr[7], 7);
                Drawable background113 = this.f449a.findViewById(R.id.sound_eq_have_off).getBackground();
                if (this.f449a.g) {
                    i = 0;
                } else {
                    i = 1;
                }
                background113.setLevel(i);
                Drawable background114 = this.f449a.findViewById(R.id.sound_eq_have_on).getBackground();
                if (this.f449a.g) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                background114.setLevel(i2);
                this.f449a.h = h.a(bArr[7], 4, 2);
                this.f449a.findViewById(R.id.sound_b_f_mode_0).getBackground().setLevel(0);
                this.f449a.findViewById(R.id.sound_b_f_mode_1).getBackground().setLevel(0);
                this.f449a.findViewById(R.id.sound_b_f_mode_2).getBackground().setLevel(0);
                this.f449a.findViewById(R.id.sound_b_f_mode_3).getBackground().setLevel(0);
                i3 = this.f449a.h;
                if (i3 != 0) {
                    viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_0);
                } else if (i3 != 1) {
                    viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_1);
                } else if (i3 != 2) {
                    viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_2);
                } else if (i3 != 3) {
                    return;
                } else {
                    viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_3);
                }
                viewFindViewById.getBackground().setLevel(1);
            }
            textView2 = (TextView) this.f449a.findViewById(R.id.balance);
            str = "L" + (10 - this.f449a.e);
            textView2.setText(str);
            this.f449a.f468c = bArr[4];
            EQActivity.a(this.f449a, R.id.bass_progress, this.f449a.f468c - 1, 18);
            if (this.f449a.f468c < 10) {
                if (this.f449a.f468c > 10) {
                    textView3 = (TextView) this.f449a.findViewById(R.id.bass);
                    sb2 = new StringBuilder();
                    sb2.append("+");
                    sb2.append(this.f449a.f468c - 10);
                } else if (this.f449a.f468c == 10) {
                    ((TextView) this.f449a.findViewById(R.id.bass)).setText("0");
                }
                this.f449a.d = bArr[5];
                EQActivity.a(this.f449a, R.id.treblr_progress, this.f449a.d - 1, 18);
                if (this.f449a.d < 10) {
                    if (this.f449a.d > 10) {
                        textView4 = (TextView) this.f449a.findViewById(R.id.treblr);
                        sb3 = new StringBuilder();
                        sb3.append("+");
                        sb3.append(this.f449a.d - 10);
                    } else if (this.f449a.d == 10) {
                        ((TextView) this.f449a.findViewById(R.id.treblr)).setText("0");
                    }
                    this.f449a.g = h.a(bArr[7], 7);
                    Drawable background115 = this.f449a.findViewById(R.id.sound_eq_have_off).getBackground();
                    if (this.f449a.g) {
                        i = 0;
                    } else {
                        i = 1;
                    }
                    background115.setLevel(i);
                    Drawable background116 = this.f449a.findViewById(R.id.sound_eq_have_on).getBackground();
                    if (this.f449a.g) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    background116.setLevel(i2);
                    this.f449a.h = h.a(bArr[7], 4, 2);
                    this.f449a.findViewById(R.id.sound_b_f_mode_0).getBackground().setLevel(0);
                    this.f449a.findViewById(R.id.sound_b_f_mode_1).getBackground().setLevel(0);
                    this.f449a.findViewById(R.id.sound_b_f_mode_2).getBackground().setLevel(0);
                    this.f449a.findViewById(R.id.sound_b_f_mode_3).getBackground().setLevel(0);
                    i3 = this.f449a.h;
                    if (i3 != 0) {
                        viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_0);
                    } else if (i3 != 1) {
                        viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_1);
                    } else if (i3 != 2) {
                        viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_2);
                    } else if (i3 != 3) {
                        return;
                    } else {
                        viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_3);
                    }
                    viewFindViewById.getBackground().setLevel(1);
                }
                textView4 = (TextView) this.f449a.findViewById(R.id.treblr);
                sb3 = new StringBuilder();
                sb3.append("-");
                sb3.append(10 - this.f449a.d);
                textView4.setText(sb3.toString());
                this.f449a.g = h.a(bArr[7], 7);
                Drawable background117 = this.f449a.findViewById(R.id.sound_eq_have_off).getBackground();
                if (this.f449a.g) {
                    i = 0;
                } else {
                    i = 1;
                }
                background117.setLevel(i);
                Drawable background118 = this.f449a.findViewById(R.id.sound_eq_have_on).getBackground();
                if (this.f449a.g) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                background118.setLevel(i2);
                this.f449a.h = h.a(bArr[7], 4, 2);
                this.f449a.findViewById(R.id.sound_b_f_mode_0).getBackground().setLevel(0);
                this.f449a.findViewById(R.id.sound_b_f_mode_1).getBackground().setLevel(0);
                this.f449a.findViewById(R.id.sound_b_f_mode_2).getBackground().setLevel(0);
                this.f449a.findViewById(R.id.sound_b_f_mode_3).getBackground().setLevel(0);
                i3 = this.f449a.h;
                if (i3 != 0) {
                    viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_0);
                } else if (i3 != 1) {
                    viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_1);
                } else if (i3 != 2) {
                    viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_2);
                } else if (i3 != 3) {
                    return;
                } else {
                    viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_3);
                }
                viewFindViewById.getBackground().setLevel(1);
            }
            textView3 = (TextView) this.f449a.findViewById(R.id.bass);
            sb2 = new StringBuilder();
            sb2.append("-");
            sb2.append(10 - this.f449a.f468c);
            textView3.setText(sb2.toString());
            this.f449a.d = bArr[5];
            EQActivity.a(this.f449a, R.id.treblr_progress, this.f449a.d - 1, 18);
            if (this.f449a.d < 10) {
                if (this.f449a.d > 10) {
                    textView4 = (TextView) this.f449a.findViewById(R.id.treblr);
                    sb3 = new StringBuilder();
                    sb3.append("+");
                    sb3.append(this.f449a.d - 10);
                } else if (this.f449a.d == 10) {
                    ((TextView) this.f449a.findViewById(R.id.treblr)).setText("0");
                }
                this.f449a.g = h.a(bArr[7], 7);
                Drawable background119 = this.f449a.findViewById(R.id.sound_eq_have_off).getBackground();
                if (this.f449a.g) {
                    i = 0;
                } else {
                    i = 1;
                }
                background119.setLevel(i);
                Drawable background1110 = this.f449a.findViewById(R.id.sound_eq_have_on).getBackground();
                if (this.f449a.g) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                background1110.setLevel(i2);
                this.f449a.h = h.a(bArr[7], 4, 2);
                this.f449a.findViewById(R.id.sound_b_f_mode_0).getBackground().setLevel(0);
                this.f449a.findViewById(R.id.sound_b_f_mode_1).getBackground().setLevel(0);
                this.f449a.findViewById(R.id.sound_b_f_mode_2).getBackground().setLevel(0);
                this.f449a.findViewById(R.id.sound_b_f_mode_3).getBackground().setLevel(0);
                i3 = this.f449a.h;
                if (i3 != 0) {
                    viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_0);
                } else if (i3 != 1) {
                    viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_1);
                } else if (i3 != 2) {
                    viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_2);
                } else if (i3 != 3) {
                    return;
                } else {
                    viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_3);
                }
                viewFindViewById.getBackground().setLevel(1);
            }
            textView4 = (TextView) this.f449a.findViewById(R.id.treblr);
            sb3 = new StringBuilder();
            sb3.append("-");
            sb3.append(10 - this.f449a.d);
            textView4.setText(sb3.toString());
            this.f449a.g = h.a(bArr[7], 7);
            Drawable background1111 = this.f449a.findViewById(R.id.sound_eq_have_off).getBackground();
            if (this.f449a.g) {
                i = 0;
            } else {
                i = 1;
            }
            background1111.setLevel(i);
            Drawable background1112 = this.f449a.findViewById(R.id.sound_eq_have_on).getBackground();
            if (this.f449a.g) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            background1112.setLevel(i2);
            this.f449a.h = h.a(bArr[7], 4, 2);
            this.f449a.findViewById(R.id.sound_b_f_mode_0).getBackground().setLevel(0);
            this.f449a.findViewById(R.id.sound_b_f_mode_1).getBackground().setLevel(0);
            this.f449a.findViewById(R.id.sound_b_f_mode_2).getBackground().setLevel(0);
            this.f449a.findViewById(R.id.sound_b_f_mode_3).getBackground().setLevel(0);
            i3 = this.f449a.h;
            if (i3 != 0) {
                viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_0);
            } else if (i3 != 1) {
                viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_1);
            } else if (i3 != 2) {
                viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_2);
            } else if (i3 != 3) {
                return;
            } else {
                viewFindViewById = this.f449a.findViewById(R.id.sound_b_f_mode_3);
            }
            viewFindViewById.getBackground().setLevel(1);
        } catch (Exception unused) {
        }
    }
}
