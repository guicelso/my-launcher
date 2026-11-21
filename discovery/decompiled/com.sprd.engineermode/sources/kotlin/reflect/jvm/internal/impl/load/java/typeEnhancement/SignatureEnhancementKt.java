package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: signatureEnhancement.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class SignatureEnhancementKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final JavaTypeQualifiers createJavaTypeQualifiers(NullabilityQualifier nullability, MutabilityQualifier mutability, boolean forWarning, boolean isAnyNonNullTypeParameter) {
        if (!isAnyNonNullTypeParameter || nullability != NullabilityQualifier.NOT_NULL) {
            return new JavaTypeQualifiers(nullability, mutability, false, forWarning);
        }
        return new JavaTypeQualifiers(nullability, mutability, true, forWarning);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <T> T select(@NotNull Set<? extends T> set, T t, T t2, T t3, boolean z) {
        Set<? extends T> set2;
        T t4;
        if (z) {
            if (set.contains(t)) {
                t4 = t;
            } else {
                t4 = set.contains(t2) ? t2 : null;
            }
            if (Intrinsics.areEqual(t4, t) && Intrinsics.areEqual(t3, t2)) {
                return null;
            }
            return t3 != null ? t3 : t4;
        }
        if (t3 == null || (set2 = CollectionsKt.toSet(SetsKt.plus(set, t3))) == null) {
            set2 = set;
        }
        return (T) CollectionsKt.singleOrNull(set2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final NullabilityQualifier select(@NotNull Set<? extends NullabilityQualifier> set, NullabilityQualifier own, boolean isCovariant) {
        if (own == NullabilityQualifier.FORCE_FLEXIBILITY) {
            return NullabilityQualifier.FORCE_FLEXIBILITY;
        }
        return (NullabilityQualifier) select(set, NullabilityQualifier.NOT_NULL, NullabilityQualifier.NULLABLE, own, isCovariant);
    }
}
