package kotlin.reflect.full;

import java.lang.annotation.Annotation;
import java.util.List;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KAnnotatedElement;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: KAnnotatedElements.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0002\b\u0002\n\u0002\u0010\u001b\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a \u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u0003H\u0087\b¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"findAnnotation", "T", "", "Lkotlin/reflect/KAnnotatedElement;", "(Lkotlin/reflect/KAnnotatedElement;)Ljava/lang/annotation/Annotation;", "kotlin-reflect-api"}, k = 2, mv = {1, 1, 13})
@JvmName(name = "KAnnotatedElements")
public final class KAnnotatedElements {
    @SinceKotlin(version = "1.1")
    private static final <T extends Annotation> T findAnnotation(@NotNull KAnnotatedElement $receiver) {
        List<Annotation> $receiver$iv = $receiver.getAnnotations();
        for (Object element$iv : $receiver$iv) {
            Annotation it = (Annotation) element$iv;
            Intrinsics.reifiedOperationMarker(3, "T");
            if (it instanceof Annotation) {
                Intrinsics.reifiedOperationMarker(1, "T?");
                return (T) element$iv;
            }
        }
        element$iv = null;
        Intrinsics.reifiedOperationMarker(1, "T?");
        return (T) element$iv;
    }
}
