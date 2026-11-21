package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.LongCompanionObject;
import kotlin.random.Random;
import kotlin.random.RandomKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: _Ranges.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000n\n\u0002\b\u0002\n\u0002\u0010\u000f\n\u0002\b\u0002\n\u0002\u0010\u0005\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\u0010\n\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\f\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0016\u001a'\u0010\u0000\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\u0006\u0010\u0003\u001a\u0002H\u0001¢\u0006\u0002\u0010\u0004\u001a\u0012\u0010\u0000\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0005\u001a\u0012\u0010\u0000\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0006\u001a\u0012\u0010\u0000\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0007\u001a\u0012\u0010\u0000\u001a\u00020\b*\u00020\b2\u0006\u0010\u0003\u001a\u00020\b\u001a\u0012\u0010\u0000\u001a\u00020\t*\u00020\t2\u0006\u0010\u0003\u001a\u00020\t\u001a\u0012\u0010\u0000\u001a\u00020\n*\u00020\n2\u0006\u0010\u0003\u001a\u00020\n\u001a'\u0010\u000b\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\u0006\u0010\f\u001a\u0002H\u0001¢\u0006\u0002\u0010\u0004\u001a\u0012\u0010\u000b\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005\u001a\u0012\u0010\u000b\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006\u001a\u0012\u0010\u000b\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007\u001a\u0012\u0010\u000b\u001a\u00020\b*\u00020\b2\u0006\u0010\f\u001a\u00020\b\u001a\u0012\u0010\u000b\u001a\u00020\t*\u00020\t2\u0006\u0010\f\u001a\u00020\t\u001a\u0012\u0010\u000b\u001a\u00020\n*\u00020\n2\u0006\u0010\f\u001a\u00020\n\u001a3\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\b\u0010\u0003\u001a\u0004\u0018\u0001H\u00012\b\u0010\f\u001a\u0004\u0018\u0001H\u0001¢\u0006\u0002\u0010\u000e\u001a/\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0010H\u0007¢\u0006\u0002\u0010\u0011\u001a-\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0012¢\u0006\u0002\u0010\u0013\u001a\u001a\u0010\r\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005\u001a\u001a\u0010\r\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006\u001a\u001a\u0010\r\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007\u001a\u001a\u0010\r\u001a\u00020\b*\u00020\b2\u0006\u0010\u0003\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\b\u001a\u0018\u0010\r\u001a\u00020\b*\u00020\b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\b0\u0012\u001a\u001a\u0010\r\u001a\u00020\t*\u00020\t2\u0006\u0010\u0003\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\t\u001a\u0018\u0010\r\u001a\u00020\t*\u00020\t2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\t0\u0012\u001a\u001a\u0010\r\u001a\u00020\n*\u00020\n2\u0006\u0010\u0003\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n\u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0087\n¢\u0006\u0002\u0010\u0019\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0087\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0087\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0087\u0002¢\u0006\u0002\b \u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020!2\b\u0010\u0017\u001a\u0004\u0018\u00010\bH\u0087\n¢\u0006\u0002\u0010\"\u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020#2\b\u0010\u0017\u001a\u0004\u0018\u00010\tH\u0087\n¢\u0006\u0002\u0010$\u001a\u0015\u0010%\u001a\u00020&*\u00020\u00052\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\u00052\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\u00052\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\u00052\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020)*\u00020\u00182\u0006\u0010'\u001a\u00020\u0018H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\b2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\b2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\b2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\b2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\n2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\n2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\n2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\n2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\r\u0010*\u001a\u00020\u0018*\u00020\u0016H\u0087\b\u001a\u0014\u0010*\u001a\u00020\u0018*\u00020\u00162\u0006\u0010*\u001a\u00020+H\u0007\u001a\r\u0010*\u001a\u00020\b*\u00020!H\u0087\b\u001a\u0014\u0010*\u001a\u00020\b*\u00020!2\u0006\u0010*\u001a\u00020+H\u0007\u001a\r\u0010*\u001a\u00020\t*\u00020#H\u0087\b\u001a\u0014\u0010*\u001a\u00020\t*\u00020#2\u0006\u0010*\u001a\u00020+H\u0007\u001a\n\u0010,\u001a\u00020)*\u00020)\u001a\n\u0010,\u001a\u00020&*\u00020&\u001a\n\u0010,\u001a\u00020(*\u00020(\u001a\u0015\u0010-\u001a\u00020)*\u00020)2\u0006\u0010-\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010-\u001a\u00020&*\u00020&2\u0006\u0010-\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010-\u001a\u00020(*\u00020(2\u0006\u0010-\u001a\u00020\tH\u0086\u0004\u001a\u0013\u0010.\u001a\u0004\u0018\u00010\u0005*\u00020\u0006H\u0000¢\u0006\u0002\u0010/\u001a\u0013\u0010.\u001a\u0004\u0018\u00010\u0005*\u00020\u0007H\u0000¢\u0006\u0002\u00100\u001a\u0013\u0010.\u001a\u0004\u0018\u00010\u0005*\u00020\bH\u0000¢\u0006\u0002\u00101\u001a\u0013\u0010.\u001a\u0004\u0018\u00010\u0005*\u00020\tH\u0000¢\u0006\u0002\u00102\u001a\u0013\u0010.\u001a\u0004\u0018\u00010\u0005*\u00020\nH\u0000¢\u0006\u0002\u00103\u001a\u0013\u00104\u001a\u0004\u0018\u00010\b*\u00020\u0006H\u0000¢\u0006\u0002\u00105\u001a\u0013\u00104\u001a\u0004\u0018\u00010\b*\u00020\u0007H\u0000¢\u0006\u0002\u00106\u001a\u0013\u00104\u001a\u0004\u0018\u00010\b*\u00020\tH\u0000¢\u0006\u0002\u00107\u001a\u0013\u00108\u001a\u0004\u0018\u00010\t*\u00020\u0006H\u0000¢\u0006\u0002\u00109\u001a\u0013\u00108\u001a\u0004\u0018\u00010\t*\u00020\u0007H\u0000¢\u0006\u0002\u0010:\u001a\u0013\u0010;\u001a\u0004\u0018\u00010\n*\u00020\u0006H\u0000¢\u0006\u0002\u0010<\u001a\u0013\u0010;\u001a\u0004\u0018\u00010\n*\u00020\u0007H\u0000¢\u0006\u0002\u0010=\u001a\u0013\u0010;\u001a\u0004\u0018\u00010\n*\u00020\bH\u0000¢\u0006\u0002\u0010>\u001a\u0013\u0010;\u001a\u0004\u0018\u00010\n*\u00020\tH\u0000¢\u0006\u0002\u0010?\u001a\u0015\u0010@\u001a\u00020!*\u00020\u00052\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\u00052\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\u00052\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\u00052\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020\u0016*\u00020\u00182\u0006\u0010'\u001a\u00020\u0018H\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\b2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\b2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\b2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\b2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\nH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\n2\u0006\u0010'\u001a\u00020\u0005H\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\n2\u0006\u0010'\u001a\u00020\bH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020#*\u00020\n2\u0006\u0010'\u001a\u00020\tH\u0086\u0004\u001a\u0015\u0010@\u001a\u00020!*\u00020\n2\u0006\u0010'\u001a\u00020\nH\u0086\u0004¨\u0006A"}, d2 = {"coerceAtLeast", "T", "", "minimumValue", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "", "", "", "", "", "", "coerceAtMost", "maximumValue", "coerceIn", "(Ljava/lang/Comparable;Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "range", "Lkotlin/ranges/ClosedFloatingPointRange;", "(Ljava/lang/Comparable;Lkotlin/ranges/ClosedFloatingPointRange;)Ljava/lang/Comparable;", "Lkotlin/ranges/ClosedRange;", "(Ljava/lang/Comparable;Lkotlin/ranges/ClosedRange;)Ljava/lang/Comparable;", "contains", "", "Lkotlin/ranges/CharRange;", "element", "", "(Lkotlin/ranges/CharRange;Ljava/lang/Character;)Z", "value", "byteRangeContains", "doubleRangeContains", "floatRangeContains", "intRangeContains", "longRangeContains", "shortRangeContains", "Lkotlin/ranges/IntRange;", "(Lkotlin/ranges/IntRange;Ljava/lang/Integer;)Z", "Lkotlin/ranges/LongRange;", "(Lkotlin/ranges/LongRange;Ljava/lang/Long;)Z", "downTo", "Lkotlin/ranges/IntProgression;", "to", "Lkotlin/ranges/LongProgression;", "Lkotlin/ranges/CharProgression;", "random", "Lkotlin/random/Random;", "reversed", "step", "toByteExactOrNull", "(D)Ljava/lang/Byte;", "(F)Ljava/lang/Byte;", "(I)Ljava/lang/Byte;", "(J)Ljava/lang/Byte;", "(S)Ljava/lang/Byte;", "toIntExactOrNull", "(D)Ljava/lang/Integer;", "(F)Ljava/lang/Integer;", "(J)Ljava/lang/Integer;", "toLongExactOrNull", "(D)Ljava/lang/Long;", "(F)Ljava/lang/Long;", "toShortExactOrNull", "(D)Ljava/lang/Short;", "(F)Ljava/lang/Short;", "(I)Ljava/lang/Short;", "(J)Ljava/lang/Short;", "until", "kotlin-stdlib"}, k = 5, mv = {1, 1, 13}, xi = 1, xs = "kotlin/ranges/RangesKt")
public class RangesKt___RangesKt extends RangesKt__RangesKt {
    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final int random(@NotNull IntRange $receiver) {
        return RangesKt.random($receiver, Random.INSTANCE);
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final long random(@NotNull LongRange $receiver) {
        return RangesKt.random($receiver, Random.INSTANCE);
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final char random(@NotNull CharRange $receiver) {
        return RangesKt.random($receiver, Random.INSTANCE);
    }

    @SinceKotlin(version = "1.3")
    public static final int random(@NotNull IntRange receiver$0, @NotNull Random random) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(random, "random");
        try {
            return RandomKt.nextInt(random, receiver$0);
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    @SinceKotlin(version = "1.3")
    public static final long random(@NotNull LongRange receiver$0, @NotNull Random random) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(random, "random");
        try {
            return RandomKt.nextLong(random, receiver$0);
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    @SinceKotlin(version = "1.3")
    public static final char random(@NotNull CharRange receiver$0, @NotNull Random random) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(random, "random");
        try {
            return (char) random.nextInt(receiver$0.getFirst(), receiver$0.getLast() + 1);
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final boolean contains(@NotNull IntRange receiver$0, Integer element) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return element != null && receiver$0.contains(element.intValue());
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final boolean contains(@NotNull LongRange receiver$0, Long element) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return element != null && receiver$0.contains(element.longValue());
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final boolean contains(@NotNull CharRange receiver$0, Character element) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return element != null && receiver$0.contains(element.charValue());
    }

    @JvmName(name = "intRangeContains")
    public static final boolean intRangeContains(@NotNull ClosedRange<Integer> receiver$0, byte value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return receiver$0.contains(Integer.valueOf(value));
    }

    @JvmName(name = "longRangeContains")
    public static final boolean longRangeContains(@NotNull ClosedRange<Long> receiver$0, byte value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return receiver$0.contains(Long.valueOf(value));
    }

    @JvmName(name = "shortRangeContains")
    public static final boolean shortRangeContains(@NotNull ClosedRange<Short> receiver$0, byte value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return receiver$0.contains(Short.valueOf(value));
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @JvmName(name = "doubleRangeContains")
    public static final boolean doubleRangeContains(@NotNull ClosedRange<Double> receiver$0, byte value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return receiver$0.contains(Double.valueOf(value));
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @JvmName(name = "floatRangeContains")
    public static final boolean floatRangeContains(@NotNull ClosedRange<Float> receiver$0, byte value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return receiver$0.contains(Float.valueOf(value));
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @JvmName(name = "intRangeContains")
    public static final boolean intRangeContains(@NotNull ClosedRange<Integer> receiver$0, double value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Integer it = RangesKt.toIntExactOrNull(value);
        if (it != null) {
            return receiver$0.contains(it);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @JvmName(name = "longRangeContains")
    public static final boolean longRangeContains(@NotNull ClosedRange<Long> receiver$0, double value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Long it = RangesKt.toLongExactOrNull(value);
        if (it != null) {
            return receiver$0.contains(it);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @JvmName(name = "byteRangeContains")
    public static final boolean byteRangeContains(@NotNull ClosedRange<Byte> receiver$0, double value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Byte it = RangesKt.toByteExactOrNull(value);
        if (it != null) {
            return receiver$0.contains(it);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @JvmName(name = "shortRangeContains")
    public static final boolean shortRangeContains(@NotNull ClosedRange<Short> receiver$0, double value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Short it = RangesKt.toShortExactOrNull(value);
        if (it != null) {
            return receiver$0.contains(it);
        }
        return false;
    }

    @JvmName(name = "floatRangeContains")
    public static final boolean floatRangeContains(@NotNull ClosedRange<Float> receiver$0, double value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return receiver$0.contains(Float.valueOf((float) value));
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @JvmName(name = "intRangeContains")
    public static final boolean intRangeContains(@NotNull ClosedRange<Integer> receiver$0, float value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Integer it = RangesKt.toIntExactOrNull(value);
        if (it != null) {
            return receiver$0.contains(it);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @JvmName(name = "longRangeContains")
    public static final boolean longRangeContains(@NotNull ClosedRange<Long> receiver$0, float value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Long it = RangesKt.toLongExactOrNull(value);
        if (it != null) {
            return receiver$0.contains(it);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @JvmName(name = "byteRangeContains")
    public static final boolean byteRangeContains(@NotNull ClosedRange<Byte> receiver$0, float value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Byte it = RangesKt.toByteExactOrNull(value);
        if (it != null) {
            return receiver$0.contains(it);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @JvmName(name = "shortRangeContains")
    public static final boolean shortRangeContains(@NotNull ClosedRange<Short> receiver$0, float value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Short it = RangesKt.toShortExactOrNull(value);
        if (it != null) {
            return receiver$0.contains(it);
        }
        return false;
    }

    @JvmName(name = "doubleRangeContains")
    public static final boolean doubleRangeContains(@NotNull ClosedRange<Double> receiver$0, float value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return receiver$0.contains(Double.valueOf(value));
    }

    @JvmName(name = "longRangeContains")
    public static final boolean longRangeContains(@NotNull ClosedRange<Long> receiver$0, int value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return receiver$0.contains(Long.valueOf(value));
    }

    @JvmName(name = "byteRangeContains")
    public static final boolean byteRangeContains(@NotNull ClosedRange<Byte> receiver$0, int value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Byte it = RangesKt.toByteExactOrNull(value);
        if (it != null) {
            return receiver$0.contains(it);
        }
        return false;
    }

    @JvmName(name = "shortRangeContains")
    public static final boolean shortRangeContains(@NotNull ClosedRange<Short> receiver$0, int value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Short it = RangesKt.toShortExactOrNull(value);
        if (it != null) {
            return receiver$0.contains(it);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @JvmName(name = "doubleRangeContains")
    public static final boolean doubleRangeContains(@NotNull ClosedRange<Double> receiver$0, int value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return receiver$0.contains(Double.valueOf(value));
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @JvmName(name = "floatRangeContains")
    public static final boolean floatRangeContains(@NotNull ClosedRange<Float> receiver$0, int value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return receiver$0.contains(Float.valueOf(value));
    }

    @JvmName(name = "intRangeContains")
    public static final boolean intRangeContains(@NotNull ClosedRange<Integer> receiver$0, long value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Integer it = RangesKt.toIntExactOrNull(value);
        if (it != null) {
            return receiver$0.contains(it);
        }
        return false;
    }

    @JvmName(name = "byteRangeContains")
    public static final boolean byteRangeContains(@NotNull ClosedRange<Byte> receiver$0, long value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Byte it = RangesKt.toByteExactOrNull(value);
        if (it != null) {
            return receiver$0.contains(it);
        }
        return false;
    }

    @JvmName(name = "shortRangeContains")
    public static final boolean shortRangeContains(@NotNull ClosedRange<Short> receiver$0, long value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Short it = RangesKt.toShortExactOrNull(value);
        if (it != null) {
            return receiver$0.contains(it);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @JvmName(name = "doubleRangeContains")
    public static final boolean doubleRangeContains(@NotNull ClosedRange<Double> receiver$0, long value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return receiver$0.contains(Double.valueOf(value));
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @JvmName(name = "floatRangeContains")
    public static final boolean floatRangeContains(@NotNull ClosedRange<Float> receiver$0, long value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return receiver$0.contains(Float.valueOf(value));
    }

    @JvmName(name = "intRangeContains")
    public static final boolean intRangeContains(@NotNull ClosedRange<Integer> receiver$0, short value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return receiver$0.contains(Integer.valueOf(value));
    }

    @JvmName(name = "longRangeContains")
    public static final boolean longRangeContains(@NotNull ClosedRange<Long> receiver$0, short value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return receiver$0.contains(Long.valueOf(value));
    }

    @JvmName(name = "byteRangeContains")
    public static final boolean byteRangeContains(@NotNull ClosedRange<Byte> receiver$0, short value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Byte it = RangesKt.toByteExactOrNull(value);
        if (it != null) {
            return receiver$0.contains(it);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @JvmName(name = "doubleRangeContains")
    public static final boolean doubleRangeContains(@NotNull ClosedRange<Double> receiver$0, short value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return receiver$0.contains(Double.valueOf(value));
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @JvmName(name = "floatRangeContains")
    public static final boolean floatRangeContains(@NotNull ClosedRange<Float> receiver$0, short value) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return receiver$0.contains(Float.valueOf(value));
    }

    @NotNull
    public static final IntProgression downTo(int $receiver, byte to) {
        return IntProgression.INSTANCE.fromClosedRange($receiver, to, -1);
    }

    @NotNull
    public static final LongProgression downTo(long $receiver, byte to) {
        return LongProgression.INSTANCE.fromClosedRange($receiver, to, -1L);
    }

    @NotNull
    public static final IntProgression downTo(byte $receiver, byte to) {
        return IntProgression.INSTANCE.fromClosedRange($receiver, to, -1);
    }

    @NotNull
    public static final IntProgression downTo(short $receiver, byte to) {
        return IntProgression.INSTANCE.fromClosedRange($receiver, to, -1);
    }

    @NotNull
    public static final CharProgression downTo(char $receiver, char to) {
        return CharProgression.INSTANCE.fromClosedRange($receiver, to, -1);
    }

    @NotNull
    public static final IntProgression downTo(int $receiver, int to) {
        return IntProgression.INSTANCE.fromClosedRange($receiver, to, -1);
    }

    @NotNull
    public static final LongProgression downTo(long $receiver, int to) {
        return LongProgression.INSTANCE.fromClosedRange($receiver, to, -1L);
    }

    @NotNull
    public static final IntProgression downTo(byte $receiver, int to) {
        return IntProgression.INSTANCE.fromClosedRange($receiver, to, -1);
    }

    @NotNull
    public static final IntProgression downTo(short $receiver, int to) {
        return IntProgression.INSTANCE.fromClosedRange($receiver, to, -1);
    }

    @NotNull
    public static final LongProgression downTo(int $receiver, long to) {
        return LongProgression.INSTANCE.fromClosedRange($receiver, to, -1L);
    }

    @NotNull
    public static final LongProgression downTo(long $receiver, long to) {
        return LongProgression.INSTANCE.fromClosedRange($receiver, to, -1L);
    }

    @NotNull
    public static final LongProgression downTo(byte $receiver, long to) {
        return LongProgression.INSTANCE.fromClosedRange($receiver, to, -1L);
    }

    @NotNull
    public static final LongProgression downTo(short $receiver, long to) {
        return LongProgression.INSTANCE.fromClosedRange($receiver, to, -1L);
    }

    @NotNull
    public static final IntProgression downTo(int $receiver, short to) {
        return IntProgression.INSTANCE.fromClosedRange($receiver, to, -1);
    }

    @NotNull
    public static final LongProgression downTo(long $receiver, short to) {
        return LongProgression.INSTANCE.fromClosedRange($receiver, to, -1L);
    }

    @NotNull
    public static final IntProgression downTo(byte $receiver, short to) {
        return IntProgression.INSTANCE.fromClosedRange($receiver, to, -1);
    }

    @NotNull
    public static final IntProgression downTo(short $receiver, short to) {
        return IntProgression.INSTANCE.fromClosedRange($receiver, to, -1);
    }

    @NotNull
    public static final IntProgression reversed(@NotNull IntProgression receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return IntProgression.INSTANCE.fromClosedRange(receiver$0.getLast(), receiver$0.getFirst(), -receiver$0.getStep());
    }

    @NotNull
    public static final LongProgression reversed(@NotNull LongProgression receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return LongProgression.INSTANCE.fromClosedRange(receiver$0.getLast(), receiver$0.getFirst(), -receiver$0.getStep());
    }

    @NotNull
    public static final CharProgression reversed(@NotNull CharProgression receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return CharProgression.INSTANCE.fromClosedRange(receiver$0.getLast(), receiver$0.getFirst(), -receiver$0.getStep());
    }

    @NotNull
    public static final IntProgression step(@NotNull IntProgression receiver$0, int step) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        RangesKt.checkStepIsPositive(step > 0, Integer.valueOf(step));
        return IntProgression.INSTANCE.fromClosedRange(receiver$0.getFirst(), receiver$0.getLast(), receiver$0.getStep() > 0 ? step : -step);
    }

    @NotNull
    public static final LongProgression step(@NotNull LongProgression receiver$0, long step) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        RangesKt.checkStepIsPositive(step > 0, Long.valueOf(step));
        return LongProgression.INSTANCE.fromClosedRange(receiver$0.getFirst(), receiver$0.getLast(), receiver$0.getStep() > 0 ? step : -step);
    }

    @NotNull
    public static final CharProgression step(@NotNull CharProgression receiver$0, int step) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        RangesKt.checkStepIsPositive(step > 0, Integer.valueOf(step));
        return CharProgression.INSTANCE.fromClosedRange(receiver$0.getFirst(), receiver$0.getLast(), receiver$0.getStep() > 0 ? step : -step);
    }

    @Nullable
    public static final Byte toByteExactOrNull(int $receiver) {
        if (-128 <= $receiver && 127 >= $receiver) {
            return Byte.valueOf((byte) $receiver);
        }
        return null;
    }

    @Nullable
    public static final Byte toByteExactOrNull(long $receiver) {
        long j = 127;
        if (-128 <= $receiver && j >= $receiver) {
            return Byte.valueOf((byte) $receiver);
        }
        return null;
    }

    @Nullable
    public static final Byte toByteExactOrNull(short $receiver) {
        short s = (short) 127;
        if (((short) (-128)) <= $receiver && s >= $receiver) {
            return Byte.valueOf((byte) $receiver);
        }
        return null;
    }

    @Nullable
    public static final Byte toByteExactOrNull(double $receiver) {
        double d = 127;
        if ($receiver < -128 || $receiver > d) {
            return null;
        }
        return Byte.valueOf((byte) $receiver);
    }

    @Nullable
    public static final Byte toByteExactOrNull(float $receiver) {
        float f = 127;
        if ($receiver < -128 || $receiver > f) {
            return null;
        }
        return Byte.valueOf((byte) $receiver);
    }

    @Nullable
    public static final Integer toIntExactOrNull(long $receiver) {
        long j = Integer.MAX_VALUE;
        if (Integer.MIN_VALUE <= $receiver && j >= $receiver) {
            return Integer.valueOf((int) $receiver);
        }
        return null;
    }

    @Nullable
    public static final Integer toIntExactOrNull(double $receiver) {
        double d = Integer.MAX_VALUE;
        if ($receiver < Integer.MIN_VALUE || $receiver > d) {
            return null;
        }
        return Integer.valueOf((int) $receiver);
    }

    @Nullable
    public static final Integer toIntExactOrNull(float $receiver) {
        float f = Integer.MAX_VALUE;
        if ($receiver < Integer.MIN_VALUE || $receiver > f) {
            return null;
        }
        return Integer.valueOf((int) $receiver);
    }

    @Nullable
    public static final Long toLongExactOrNull(double $receiver) {
        double d = Long.MIN_VALUE;
        double d2 = LongCompanionObject.MAX_VALUE;
        if ($receiver < d || $receiver > d2) {
            return null;
        }
        return Long.valueOf((long) $receiver);
    }

    @Nullable
    public static final Long toLongExactOrNull(float $receiver) {
        float f = Long.MIN_VALUE;
        float f2 = LongCompanionObject.MAX_VALUE;
        if ($receiver < f || $receiver > f2) {
            return null;
        }
        return Long.valueOf((long) $receiver);
    }

    @Nullable
    public static final Short toShortExactOrNull(int $receiver) {
        if (-32768 <= $receiver && 32767 >= $receiver) {
            return Short.valueOf((short) $receiver);
        }
        return null;
    }

    @Nullable
    public static final Short toShortExactOrNull(long $receiver) {
        long j = 32767;
        if (-32768 <= $receiver && j >= $receiver) {
            return Short.valueOf((short) $receiver);
        }
        return null;
    }

    @Nullable
    public static final Short toShortExactOrNull(double $receiver) {
        double d = 32767;
        if ($receiver < -32768 || $receiver > d) {
            return null;
        }
        return Short.valueOf((short) $receiver);
    }

    @Nullable
    public static final Short toShortExactOrNull(float $receiver) {
        float f = 32767;
        if ($receiver < -32768 || $receiver > f) {
            return null;
        }
        return Short.valueOf((short) $receiver);
    }

    @NotNull
    public static final IntRange until(int $receiver, byte to) {
        return new IntRange($receiver, to - 1);
    }

    @NotNull
    public static final LongRange until(long $receiver, byte to) {
        return new LongRange($receiver, ((long) to) - 1);
    }

    @NotNull
    public static final IntRange until(byte $receiver, byte to) {
        return new IntRange($receiver, to - 1);
    }

    @NotNull
    public static final IntRange until(short $receiver, byte to) {
        return new IntRange($receiver, to - 1);
    }

    @NotNull
    public static final CharRange until(char $receiver, char to) {
        return to <= 0 ? CharRange.INSTANCE.getEMPTY() : new CharRange($receiver, (char) (to - 1));
    }

    @NotNull
    public static final IntRange until(int $receiver, int to) {
        return to <= Integer.MIN_VALUE ? IntRange.INSTANCE.getEMPTY() : new IntRange($receiver, to - 1);
    }

    @NotNull
    public static final LongRange until(long $receiver, int to) {
        return new LongRange($receiver, ((long) to) - 1);
    }

    @NotNull
    public static final IntRange until(byte $receiver, int to) {
        return to <= Integer.MIN_VALUE ? IntRange.INSTANCE.getEMPTY() : new IntRange($receiver, to - 1);
    }

    @NotNull
    public static final IntRange until(short $receiver, int to) {
        return to <= Integer.MIN_VALUE ? IntRange.INSTANCE.getEMPTY() : new IntRange($receiver, to - 1);
    }

    @NotNull
    public static final LongRange until(int $receiver, long to) {
        return to <= Long.MIN_VALUE ? LongRange.INSTANCE.getEMPTY() : new LongRange($receiver, to - 1);
    }

    @NotNull
    public static final LongRange until(long $receiver, long to) {
        return to <= Long.MIN_VALUE ? LongRange.INSTANCE.getEMPTY() : new LongRange($receiver, to - 1);
    }

    @NotNull
    public static final LongRange until(byte $receiver, long to) {
        return to <= Long.MIN_VALUE ? LongRange.INSTANCE.getEMPTY() : new LongRange($receiver, to - 1);
    }

    @NotNull
    public static final LongRange until(short $receiver, long to) {
        return to <= Long.MIN_VALUE ? LongRange.INSTANCE.getEMPTY() : new LongRange($receiver, to - 1);
    }

    @NotNull
    public static final IntRange until(int $receiver, short to) {
        return new IntRange($receiver, to - 1);
    }

    @NotNull
    public static final LongRange until(long $receiver, short to) {
        return new LongRange($receiver, ((long) to) - 1);
    }

    @NotNull
    public static final IntRange until(byte $receiver, short to) {
        return new IntRange($receiver, to - 1);
    }

    @NotNull
    public static final IntRange until(short $receiver, short to) {
        return new IntRange($receiver, to - 1);
    }

    @NotNull
    public static final <T extends Comparable<? super T>> T coerceAtLeast(@NotNull T receiver$0, @NotNull T minimumValue) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(minimumValue, "minimumValue");
        return receiver$0.compareTo(minimumValue) < 0 ? minimumValue : receiver$0;
    }

    public static final byte coerceAtLeast(byte $receiver, byte minimumValue) {
        return $receiver < minimumValue ? minimumValue : $receiver;
    }

    public static final short coerceAtLeast(short $receiver, short minimumValue) {
        return $receiver < minimumValue ? minimumValue : $receiver;
    }

    public static final int coerceAtLeast(int $receiver, int minimumValue) {
        return $receiver < minimumValue ? minimumValue : $receiver;
    }

    public static final long coerceAtLeast(long $receiver, long minimumValue) {
        return $receiver < minimumValue ? minimumValue : $receiver;
    }

    public static final float coerceAtLeast(float $receiver, float minimumValue) {
        return $receiver < minimumValue ? minimumValue : $receiver;
    }

    public static final double coerceAtLeast(double $receiver, double minimumValue) {
        return $receiver < minimumValue ? minimumValue : $receiver;
    }

    @NotNull
    public static final <T extends Comparable<? super T>> T coerceAtMost(@NotNull T receiver$0, @NotNull T maximumValue) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(maximumValue, "maximumValue");
        return receiver$0.compareTo(maximumValue) > 0 ? maximumValue : receiver$0;
    }

    public static final byte coerceAtMost(byte $receiver, byte maximumValue) {
        return $receiver > maximumValue ? maximumValue : $receiver;
    }

    public static final short coerceAtMost(short $receiver, short maximumValue) {
        return $receiver > maximumValue ? maximumValue : $receiver;
    }

    public static final int coerceAtMost(int $receiver, int maximumValue) {
        return $receiver > maximumValue ? maximumValue : $receiver;
    }

    public static final long coerceAtMost(long $receiver, long maximumValue) {
        return $receiver > maximumValue ? maximumValue : $receiver;
    }

    public static final float coerceAtMost(float $receiver, float maximumValue) {
        return $receiver > maximumValue ? maximumValue : $receiver;
    }

    public static final double coerceAtMost(double $receiver, double maximumValue) {
        return $receiver > maximumValue ? maximumValue : $receiver;
    }

    @NotNull
    public static final <T extends Comparable<? super T>> T coerceIn(@NotNull T receiver$0, @Nullable T t, @Nullable T t2) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        if (t != null && t2 != null) {
            if (t.compareTo(t2) > 0) {
                throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + t2 + " is less than minimum " + t + '.');
            }
            if (receiver$0.compareTo(t) < 0) {
                return t;
            }
            if (receiver$0.compareTo(t2) > 0) {
                return t2;
            }
        } else {
            if (t != null && receiver$0.compareTo(t) < 0) {
                return t;
            }
            if (t2 != null && receiver$0.compareTo(t2) > 0) {
                return t2;
            }
        }
        return receiver$0;
    }

    public static final byte coerceIn(byte $receiver, byte minimumValue, byte maximumValue) {
        if (minimumValue > maximumValue) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + ((int) maximumValue) + " is less than minimum " + ((int) minimumValue) + '.');
        }
        if ($receiver < minimumValue) {
            return minimumValue;
        }
        return $receiver > maximumValue ? maximumValue : $receiver;
    }

    public static final short coerceIn(short $receiver, short minimumValue, short maximumValue) {
        if (minimumValue > maximumValue) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + ((int) maximumValue) + " is less than minimum " + ((int) minimumValue) + '.');
        }
        if ($receiver < minimumValue) {
            return minimumValue;
        }
        return $receiver > maximumValue ? maximumValue : $receiver;
    }

    public static final int coerceIn(int $receiver, int minimumValue, int maximumValue) {
        if (minimumValue > maximumValue) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.');
        }
        if ($receiver < minimumValue) {
            return minimumValue;
        }
        return $receiver > maximumValue ? maximumValue : $receiver;
    }

    public static final long coerceIn(long $receiver, long minimumValue, long maximumValue) {
        if (minimumValue <= maximumValue) {
            if ($receiver < minimumValue) {
                return minimumValue;
            }
            return $receiver > maximumValue ? maximumValue : $receiver;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.');
    }

    public static final float coerceIn(float $receiver, float minimumValue, float maximumValue) {
        if (minimumValue <= maximumValue) {
            if ($receiver < minimumValue) {
                return minimumValue;
            }
            return $receiver > maximumValue ? maximumValue : $receiver;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.');
    }

    public static final double coerceIn(double $receiver, double minimumValue, double maximumValue) {
        if (minimumValue <= maximumValue) {
            if ($receiver < minimumValue) {
                return minimumValue;
            }
            return $receiver > maximumValue ? maximumValue : $receiver;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.');
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final <T extends Comparable<? super T>> T coerceIn(@NotNull T receiver$0, @NotNull ClosedFloatingPointRange<T> range) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(range, "range");
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
        }
        if (!range.lessThanOrEquals(receiver$0, range.getStart()) || range.lessThanOrEquals(range.getStart(), receiver$0)) {
            return (!range.lessThanOrEquals(range.getEndInclusive(), receiver$0) || range.lessThanOrEquals(receiver$0, range.getEndInclusive())) ? receiver$0 : range.getEndInclusive();
        }
        return range.getStart();
    }

    @NotNull
    public static final <T extends Comparable<? super T>> T coerceIn(@NotNull T receiver$0, @NotNull ClosedRange<T> range) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(range, "range");
        if (range instanceof ClosedFloatingPointRange) {
            return (T) RangesKt.coerceIn((Comparable) receiver$0, (ClosedFloatingPointRange) range);
        }
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
        }
        if (receiver$0.compareTo(range.getStart()) < 0) {
            return (T) range.getStart();
        }
        return receiver$0.compareTo(range.getEndInclusive()) > 0 ? (T) range.getEndInclusive() : receiver$0;
    }

    public static final int coerceIn(int $receiver, @NotNull ClosedRange<Integer> range) {
        Intrinsics.checkParameterIsNotNull(range, "range");
        if (range instanceof ClosedFloatingPointRange) {
            return ((Number) RangesKt.coerceIn(Integer.valueOf($receiver), (ClosedFloatingPointRange<Integer>) range)).intValue();
        }
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
        }
        if ($receiver < ((Number) range.getStart()).intValue()) {
            return ((Number) range.getStart()).intValue();
        }
        return $receiver > ((Number) range.getEndInclusive()).intValue() ? ((Number) range.getEndInclusive()).intValue() : $receiver;
    }

    public static final long coerceIn(long $receiver, @NotNull ClosedRange<Long> range) {
        Intrinsics.checkParameterIsNotNull(range, "range");
        if (range instanceof ClosedFloatingPointRange) {
            return ((Number) RangesKt.coerceIn(Long.valueOf($receiver), (ClosedFloatingPointRange<Long>) range)).longValue();
        }
        if (!range.isEmpty()) {
            if ($receiver < ((Number) range.getStart()).longValue()) {
                return ((Number) range.getStart()).longValue();
            }
            return $receiver > ((Number) range.getEndInclusive()).longValue() ? ((Number) range.getEndInclusive()).longValue() : $receiver;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
    }
}
