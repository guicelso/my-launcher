package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.reflect.jvm.internal.impl.utils.FunctionsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: NewCapturedType.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class NewCapturedTypeKt {
    @Nullable
    public static /* synthetic */ SimpleType captureFromArguments$default(SimpleType simpleType, CaptureStatus captureStatus, Function2 function2, int i, Object obj) {
        if ((i & 4) != 0) {
            function2 = FunctionsKt.getDO_NOTHING_2();
        }
        return captureFromArguments(simpleType, captureStatus, function2);
    }

    @Nullable
    public static final SimpleType captureFromArguments(@NotNull SimpleType type, @NotNull CaptureStatus status, @NotNull Function2<? super Integer, ? super NewCapturedType, Unit> acceptNewCapturedType) {
        List<TypeProjection> list;
        UnwrappedType lowerType;
        Intrinsics.checkParameterIsNotNull(type, "type");
        Intrinsics.checkParameterIsNotNull(status, "status");
        Intrinsics.checkParameterIsNotNull(acceptNewCapturedType, "acceptNewCapturedType");
        if (type.getArguments().size() != type.getConstructor().getParameters().size()) {
            return null;
        }
        List<TypeProjection> arguments = type.getArguments();
        List<TypeProjection> $receiver$iv = arguments;
        boolean z = true;
        if (!($receiver$iv instanceof Collection) || !$receiver$iv.isEmpty()) {
            for (Object element$iv : $receiver$iv) {
                TypeProjection it = (TypeProjection) element$iv;
                if (!(it.getProjectionKind() == Variance.INVARIANT)) {
                    z = false;
                    break;
                }
            }
        }
        if (z) {
            return null;
        }
        List<TypeProjection> $receiver$iv2 = arguments;
        int i = 10;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv2, 10));
        for (Object item$iv$iv : $receiver$iv2) {
            TypeProjection projection = (TypeProjection) item$iv$iv;
            if (projection.getProjectionKind() != Variance.INVARIANT) {
                if (!projection.isStarProjection() && projection.getProjectionKind() == Variance.IN_VARIANCE) {
                    lowerType = projection.getType().unwrap();
                } else {
                    lowerType = null;
                }
                projection = TypeUtilsKt.asTypeProjection(new NewCapturedType(status, lowerType, projection));
            }
            destination$iv$iv.add(projection);
        }
        List newArguments = (List) destination$iv$iv;
        TypeSubstitutor substitutor = TypeConstructorSubstitution.Companion.create(type.getConstructor(), newArguments).buildSubstitutor();
        int size = arguments.size();
        int index = 0;
        boolean z2 = false;
        while (index < size) {
            TypeProjection oldProjection = arguments.get(index);
            TypeProjection newProjection = (TypeProjection) newArguments.get(index);
            if (oldProjection.getProjectionKind() == Variance.INVARIANT) {
                list = arguments;
            } else {
                TypeParameterDescriptor typeParameterDescriptor = type.getConstructor().getParameters().get(index);
                Intrinsics.checkExpressionValueIsNotNull(typeParameterDescriptor, "type.constructor.parameters[index]");
                Iterable upperBounds = typeParameterDescriptor.getUpperBounds();
                Intrinsics.checkExpressionValueIsNotNull(upperBounds, "type.constructor.parameters[index].upperBounds");
                Iterable $receiver$iv3 = upperBounds;
                boolean z3 = z2;
                Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv3, i));
                for (Object item$iv$iv2 : $receiver$iv3) {
                    KotlinType it2 = (KotlinType) item$iv$iv2;
                    destination$iv$iv2.add(NewKotlinTypeChecker.INSTANCE.transformToNewType(substitutor.safeSubstitute(it2, Variance.INVARIANT).unwrap()));
                    arguments = arguments;
                }
                list = arguments;
                List<? extends UnwrappedType> listPlus = (List) destination$iv$iv2;
                if (!oldProjection.isStarProjection() && oldProjection.getProjectionKind() == Variance.OUT_VARIANCE) {
                    listPlus = CollectionsKt.plus((Collection<? extends UnwrappedType>) listPlus, NewKotlinTypeChecker.INSTANCE.transformToNewType(oldProjection.getType().unwrap()));
                }
                KotlinType type2 = newProjection.getType();
                if (type2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.checker.NewCapturedType");
                }
                NewCapturedType capturedType = (NewCapturedType) type2;
                capturedType.getConstructor().initializeSupertypes(listPlus);
                acceptNewCapturedType.invoke(Integer.valueOf(index), capturedType);
                z2 = z3;
            }
            index++;
            i = 10;
            arguments = list;
        }
        return KotlinTypeFactory.simpleType(type.getAnnotations(), type.getConstructor(), newArguments, type.isMarkedNullable());
    }
}
