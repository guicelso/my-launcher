package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.TypeCastException;
import kotlin._Assertions;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: TypeSubstitution.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class IndexedParametersSubstitution extends TypeSubstitution {
    private final boolean approximateCapturedTypes;

    @NotNull
    private final TypeProjection[] arguments;

    @NotNull
    private final TypeParameterDescriptor[] parameters;

    public IndexedParametersSubstitution(@NotNull TypeParameterDescriptor[] parameters, @NotNull TypeProjection[] arguments, boolean approximateCapturedTypes) {
        Intrinsics.checkParameterIsNotNull(parameters, "parameters");
        Intrinsics.checkParameterIsNotNull(arguments, "arguments");
        this.parameters = parameters;
        this.arguments = arguments;
        this.approximateCapturedTypes = approximateCapturedTypes;
        boolean z = this.parameters.length <= this.arguments.length;
        if (!_Assertions.ENABLED || z) {
            return;
        }
        throw new AssertionError("Number of arguments should not be less then number of parameters, but: parameters=" + this.parameters.length + ", args=" + this.arguments.length);
    }

    @NotNull
    public final TypeParameterDescriptor[] getParameters() {
        return this.parameters;
    }

    @NotNull
    public final TypeProjection[] getArguments() {
        return this.arguments;
    }

    public /* synthetic */ IndexedParametersSubstitution(TypeParameterDescriptor[] typeParameterDescriptorArr, TypeProjection[] typeProjectionArr, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(typeParameterDescriptorArr, typeProjectionArr, (i & 4) != 0 ? false : z);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public IndexedParametersSubstitution(@NotNull List<? extends TypeParameterDescriptor> parameters, @NotNull List<? extends TypeProjection> argumentsList) {
        Intrinsics.checkParameterIsNotNull(parameters, "parameters");
        Intrinsics.checkParameterIsNotNull(argumentsList, "argumentsList");
        List<? extends TypeParameterDescriptor> $receiver$iv = parameters;
        Object[] array = $receiver$iv.toArray(new TypeParameterDescriptor[0]);
        if (array != null) {
            TypeParameterDescriptor[] typeParameterDescriptorArr = (TypeParameterDescriptor[]) array;
            List<? extends TypeProjection> $receiver$iv2 = argumentsList;
            Object[] array2 = $receiver$iv2.toArray(new TypeProjection[0]);
            if (array2 != null) {
                this(typeParameterDescriptorArr, (TypeProjection[]) array2, false, 4, null);
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSubstitution
    public boolean isEmpty() {
        return this.arguments.length == 0;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSubstitution
    public boolean approximateContravariantCapturedTypes() {
        return this.approximateCapturedTypes;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeSubstitution
    @Nullable
    /* JADX INFO: renamed from: get */
    public TypeProjection mo384get(@NotNull KotlinType key) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        ClassifierDescriptor classifierDescriptorMo381getDeclarationDescriptor = key.getConstructor().mo381getDeclarationDescriptor();
        if (!(classifierDescriptorMo381getDeclarationDescriptor instanceof TypeParameterDescriptor)) {
            classifierDescriptorMo381getDeclarationDescriptor = null;
        }
        TypeParameterDescriptor parameter = (TypeParameterDescriptor) classifierDescriptorMo381getDeclarationDescriptor;
        if (parameter == null) {
            return null;
        }
        int index = parameter.getIndex();
        TypeParameterDescriptor[] typeParameterDescriptorArr = this.parameters;
        if (index >= typeParameterDescriptorArr.length || !Intrinsics.areEqual(typeParameterDescriptorArr[index].getTypeConstructor(), parameter.getTypeConstructor())) {
            return null;
        }
        return this.arguments[index];
    }
}
