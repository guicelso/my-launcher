package kotlin.reflect.jvm.internal.impl.resolve.calls.inference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.DelegatedTypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.IndexedParametersSubstitution;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.LazyWrappedType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: CapturedTypeConstructor.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class CapturedTypeConstructorKt {
    @NotNull
    public static final KotlinType createCapturedType(@NotNull TypeProjection typeProjection) {
        Intrinsics.checkParameterIsNotNull(typeProjection, "typeProjection");
        return new CapturedType(typeProjection, null, false, null, 14, null);
    }

    public static final boolean isCaptured(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return receiver$0.getConstructor() instanceof CapturedTypeConstructor;
    }

    @NotNull
    public static /* synthetic */ TypeSubstitution wrapWithCapturingSubstitution$default(TypeSubstitution typeSubstitution, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        return wrapWithCapturingSubstitution(typeSubstitution, z);
    }

    @NotNull
    public static final TypeSubstitution wrapWithCapturingSubstitution(@NotNull final TypeSubstitution receiver$0, final boolean needApproximation) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        if (receiver$0 instanceof IndexedParametersSubstitution) {
            TypeParameterDescriptor[] parameters = ((IndexedParametersSubstitution) receiver$0).getParameters();
            Iterable $receiver$iv = ArraysKt.zip(((IndexedParametersSubstitution) receiver$0).getArguments(), ((IndexedParametersSubstitution) receiver$0).getParameters());
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
            for (Object item$iv$iv : $receiver$iv) {
                Pair it = (Pair) item$iv$iv;
                destination$iv$iv.add(createCapturedIfNeeded((TypeProjection) it.getFirst(), (TypeParameterDescriptor) it.getSecond()));
            }
            Object[] array = ((List) destination$iv$iv).toArray(new TypeProjection[0]);
            if (array != null) {
                return new IndexedParametersSubstitution(parameters, (TypeProjection[]) array, needApproximation);
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        return new DelegatedTypeSubstitution(receiver$0) { // from class: kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructorKt.wrapWithCapturingSubstitution.2
            @Override // kotlin.reflect.jvm.internal.impl.types.DelegatedTypeSubstitution, kotlin.reflect.jvm.internal.impl.types.TypeSubstitution
            public boolean approximateContravariantCapturedTypes() {
                return needApproximation;
            }

            @Override // kotlin.reflect.jvm.internal.impl.types.DelegatedTypeSubstitution, kotlin.reflect.jvm.internal.impl.types.TypeSubstitution
            @Nullable
            /* JADX INFO: renamed from: get */
            public TypeProjection mo384get(@NotNull KotlinType key) {
                Intrinsics.checkParameterIsNotNull(key, "key");
                TypeProjection typeProjectionMo384get = super.mo384get(key);
                if (typeProjectionMo384get == null) {
                    return null;
                }
                ClassifierDescriptor classifierDescriptorMo381getDeclarationDescriptor = key.getConstructor().mo381getDeclarationDescriptor();
                return CapturedTypeConstructorKt.createCapturedIfNeeded(typeProjectionMo384get, (TypeParameterDescriptor) (classifierDescriptorMo381getDeclarationDescriptor instanceof TypeParameterDescriptor ? classifierDescriptorMo381getDeclarationDescriptor : null));
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final TypeProjection createCapturedIfNeeded(@NotNull final TypeProjection $receiver, TypeParameterDescriptor typeParameterDescriptor) {
        if (typeParameterDescriptor == null || $receiver.getProjectionKind() == Variance.INVARIANT) {
            return $receiver;
        }
        if (typeParameterDescriptor.getVariance() == $receiver.getProjectionKind()) {
            if ($receiver.isStarProjection()) {
                StorageManager storageManager = LockBasedStorageManager.NO_LOCKS;
                Intrinsics.checkExpressionValueIsNotNull(storageManager, "LockBasedStorageManager.NO_LOCKS");
                return new TypeProjectionImpl(new LazyWrappedType(storageManager, new Function0<KotlinType>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructorKt.createCapturedIfNeeded.1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    @NotNull
                    public final KotlinType invoke() {
                        KotlinType type = $receiver.getType();
                        Intrinsics.checkExpressionValueIsNotNull(type, "this@createCapturedIfNeeded.type");
                        return type;
                    }
                }));
            }
            return new TypeProjectionImpl($receiver.getType());
        }
        return new TypeProjectionImpl(createCapturedType($receiver));
    }
}
