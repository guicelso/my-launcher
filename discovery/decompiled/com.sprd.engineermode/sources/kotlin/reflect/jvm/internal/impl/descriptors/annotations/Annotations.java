package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: Annotations.kt */
/* JADX INFO: loaded from: classes4.dex */
public interface Annotations extends Iterable<AnnotationDescriptor>, KMappedMarker {
    public static final Companion Companion = Companion.$$INSTANCE;

    @Nullable
    /* JADX INFO: renamed from: findAnnotation */
    AnnotationDescriptor mo374findAnnotation(@NotNull FqName fqName);

    boolean hasAnnotation(@NotNull FqName fqName);

    boolean isEmpty();

    /* JADX INFO: compiled from: Annotations.kt */
    public static final class DefaultImpls {
        @Nullable
        public static AnnotationDescriptor findAnnotation(Annotations $this, @NotNull FqName fqName) {
            Object element$iv;
            Intrinsics.checkParameterIsNotNull(fqName, "fqName");
            Annotations $receiver$iv = $this;
            Iterator<AnnotationDescriptor> it = $receiver$iv.iterator();
            while (it.hasNext()) {
                element$iv = it.next();
                AnnotationDescriptor it2 = (AnnotationDescriptor) element$iv;
                if (Intrinsics.areEqual(it2.getFqName(), fqName)) {
                    return (AnnotationDescriptor) element$iv;
                }
            }
            element$iv = null;
            return (AnnotationDescriptor) element$iv;
        }

        public static boolean hasAnnotation(Annotations $this, @NotNull FqName fqName) {
            Intrinsics.checkParameterIsNotNull(fqName, "fqName");
            return $this.mo374findAnnotation(fqName) != null;
        }
    }

    /* JADX INFO: compiled from: Annotations.kt */
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        @NotNull
        private static final Annotations EMPTY = new Annotations() { // from class: kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations$Companion$EMPTY$1
            @Override // kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations
            /* JADX INFO: renamed from: findAnnotation, reason: collision with other method in class */
            public /* bridge */ /* synthetic */ AnnotationDescriptor mo374findAnnotation(FqName fqName) {
                return (AnnotationDescriptor) findAnnotation(fqName);
            }

            @Override // kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations
            public boolean hasAnnotation(@NotNull FqName fqName) {
                Intrinsics.checkParameterIsNotNull(fqName, "fqName");
                return Annotations.DefaultImpls.hasAnnotation(this, fqName);
            }

            @Override // kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations
            public boolean isEmpty() {
                return true;
            }

            @Nullable
            public Void findAnnotation(@NotNull FqName fqName) {
                Intrinsics.checkParameterIsNotNull(fqName, "fqName");
                return null;
            }

            @Override // java.lang.Iterable
            @NotNull
            public Iterator<AnnotationDescriptor> iterator() {
                return CollectionsKt.emptyList().iterator();
            }

            @NotNull
            public String toString() {
                return "EMPTY";
            }
        };

        private Companion() {
        }

        @NotNull
        public final Annotations getEMPTY() {
            return EMPTY;
        }

        @NotNull
        public final Annotations create(@NotNull List<? extends AnnotationDescriptor> annotations) {
            Intrinsics.checkParameterIsNotNull(annotations, "annotations");
            return annotations.isEmpty() ? EMPTY : new AnnotationsImpl(annotations);
        }
    }
}
