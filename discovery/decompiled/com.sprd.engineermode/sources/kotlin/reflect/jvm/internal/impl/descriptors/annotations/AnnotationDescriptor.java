package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import java.util.Map;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: AnnotationDescriptor.kt */
/* JADX INFO: loaded from: classes4.dex */
public interface AnnotationDescriptor {
    @NotNull
    Map<Name, ConstantValue<?>> getAllValueArguments();

    @Nullable
    FqName getFqName();

    @NotNull
    SourceElement getSource();

    @NotNull
    KotlinType getType();

    /* JADX INFO: compiled from: AnnotationDescriptor.kt */
    public static final class DefaultImpls {
        @Nullable
        public static FqName getFqName(AnnotationDescriptor $this) {
            DeclarationDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass($this);
            if (annotationClass == null) {
                return null;
            }
            DeclarationDescriptor p1 = annotationClass;
            if (ErrorUtils.isError(p1)) {
                annotationClass = null;
            }
            if (annotationClass != null) {
                return DescriptorUtilsKt.fqNameOrNull(annotationClass);
            }
            return null;
        }
    }
}
