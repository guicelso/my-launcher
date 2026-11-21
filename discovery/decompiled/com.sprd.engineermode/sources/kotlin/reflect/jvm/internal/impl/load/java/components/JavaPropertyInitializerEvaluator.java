package kotlin.reflect.jvm.internal.impl.load.java.components;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaField;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: JavaPropertyInitializerEvaluator.kt */
/* JADX INFO: loaded from: classes4.dex */
public interface JavaPropertyInitializerEvaluator {
    @Nullable
    /* JADX INFO: renamed from: getInitializerConstant */
    ConstantValue<?> mo376getInitializerConstant(@NotNull JavaField javaField, @NotNull PropertyDescriptor propertyDescriptor);

    /* JADX INFO: compiled from: JavaPropertyInitializerEvaluator.kt */
    public static final class DoNothing implements JavaPropertyInitializerEvaluator {
        public static final DoNothing INSTANCE = new DoNothing();

        private DoNothing() {
        }

        @Override // kotlin.reflect.jvm.internal.impl.load.java.components.JavaPropertyInitializerEvaluator
        /* JADX INFO: renamed from: getInitializerConstant, reason: collision with other method in class */
        public /* bridge */ /* synthetic */ ConstantValue mo376getInitializerConstant(JavaField javaField, PropertyDescriptor propertyDescriptor) {
            return (ConstantValue) getInitializerConstant(javaField, propertyDescriptor);
        }

        @Nullable
        public Void getInitializerConstant(@NotNull JavaField field, @NotNull PropertyDescriptor descriptor) {
            Intrinsics.checkParameterIsNotNull(field, "field");
            Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
            return null;
        }
    }
}
