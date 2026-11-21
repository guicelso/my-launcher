package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayDeque;
import java.util.Set;
import kotlin._Assertions;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutionKt;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.utils.SmartSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: TypeCheckerContext.kt */
/* JADX INFO: loaded from: classes4.dex */
public class TypeCheckerContext {
    private final boolean allowedTypeVariable;
    private int argumentsDepth;
    private final boolean errorTypeEqualsToAnything;
    private ArrayDeque<SimpleType> supertypesDeque;
    private boolean supertypesLocked;
    private Set<SimpleType> supertypesSet;

    /* JADX INFO: compiled from: TypeCheckerContext.kt */
    public enum LowerCapturedTypePolicy {
        CHECK_ONLY_LOWER,
        CHECK_SUBTYPE_AND_LOWER,
        SKIP_LOWER
    }

    /* JADX INFO: compiled from: TypeCheckerContext.kt */
    public enum SeveralSupertypesWithSameConstructorPolicy {
        TAKE_FIRST_FOR_SUBTYPING,
        FORCE_NOT_SUBTYPE,
        CHECK_ANY_OF_THEM,
        INTERSECT_ARGUMENTS_AND_CHECK_AGAIN
    }

    public TypeCheckerContext(boolean errorTypeEqualsToAnything, boolean allowedTypeVariable) {
        this.errorTypeEqualsToAnything = errorTypeEqualsToAnything;
        this.allowedTypeVariable = allowedTypeVariable;
    }

    public /* synthetic */ TypeCheckerContext(boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, (i & 2) != 0 ? true : z2);
    }

    public final boolean getErrorTypeEqualsToAnything() {
        return this.errorTypeEqualsToAnything;
    }

    @Nullable
    public Boolean addSubtypeConstraint(@NotNull UnwrappedType subType, @NotNull UnwrappedType superType) {
        Intrinsics.checkParameterIsNotNull(subType, "subType");
        Intrinsics.checkParameterIsNotNull(superType, "superType");
        return null;
    }

    public boolean areEqualTypeConstructors(@NotNull TypeConstructor a, @NotNull TypeConstructor b) {
        Intrinsics.checkParameterIsNotNull(a, "a");
        Intrinsics.checkParameterIsNotNull(b, "b");
        return Intrinsics.areEqual(a, b);
    }

    @NotNull
    public LowerCapturedTypePolicy getLowerCapturedTypePolicy(@NotNull SimpleType subType, @NotNull NewCapturedType superType) {
        Intrinsics.checkParameterIsNotNull(subType, "subType");
        Intrinsics.checkParameterIsNotNull(superType, "superType");
        return LowerCapturedTypePolicy.CHECK_SUBTYPE_AND_LOWER;
    }

    @NotNull
    public SeveralSupertypesWithSameConstructorPolicy getSameConstructorPolicy() {
        return SeveralSupertypesWithSameConstructorPolicy.INTERSECT_ARGUMENTS_AND_CHECK_AGAIN;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initialize() {
        boolean z = !this.supertypesLocked;
        if (_Assertions.ENABLED && !z) {
            throw new AssertionError("Assertion failed");
        }
        this.supertypesLocked = true;
        if (this.supertypesDeque == null) {
            this.supertypesDeque = new ArrayDeque<>(4);
        }
        if (this.supertypesSet == null) {
            this.supertypesSet = SmartSet.Companion.create();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void clear() {
        ArrayDeque<SimpleType> arrayDeque = this.supertypesDeque;
        if (arrayDeque == null) {
            Intrinsics.throwNpe();
        }
        arrayDeque.clear();
        Set<SimpleType> set = this.supertypesSet;
        if (set == null) {
            Intrinsics.throwNpe();
        }
        set.clear();
        this.supertypesLocked = false;
    }

    /* JADX INFO: compiled from: TypeCheckerContext.kt */
    public static abstract class SupertypesPolicy {
        @NotNull
        /* JADX INFO: renamed from: transformType */
        public abstract SimpleType mo385transformType(@NotNull KotlinType kotlinType);

        private SupertypesPolicy() {
        }

        public /* synthetic */ SupertypesPolicy(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* JADX INFO: compiled from: TypeCheckerContext.kt */
        public static final class None extends SupertypesPolicy {
            public static final None INSTANCE = new None();

            private None() {
                super(null);
            }

            @Override // kotlin.reflect.jvm.internal.impl.types.checker.TypeCheckerContext.SupertypesPolicy
            /* JADX INFO: renamed from: transformType, reason: collision with other method in class */
            public /* bridge */ /* synthetic */ SimpleType mo385transformType(KotlinType kotlinType) {
                return (SimpleType) transformType(kotlinType);
            }

            @NotNull
            public Void transformType(@NotNull KotlinType type) {
                Intrinsics.checkParameterIsNotNull(type, "type");
                throw new UnsupportedOperationException("Should not be called");
            }
        }

        /* JADX INFO: compiled from: TypeCheckerContext.kt */
        public static final class UpperIfFlexible extends SupertypesPolicy {
            public static final UpperIfFlexible INSTANCE = new UpperIfFlexible();

            private UpperIfFlexible() {
                super(null);
            }

            @Override // kotlin.reflect.jvm.internal.impl.types.checker.TypeCheckerContext.SupertypesPolicy
            @NotNull
            /* JADX INFO: renamed from: transformType */
            public SimpleType mo385transformType(@NotNull KotlinType type) {
                Intrinsics.checkParameterIsNotNull(type, "type");
                return FlexibleTypesKt.upperIfFlexible(type);
            }
        }

        /* JADX INFO: compiled from: TypeCheckerContext.kt */
        public static final class LowerIfFlexible extends SupertypesPolicy {
            public static final LowerIfFlexible INSTANCE = new LowerIfFlexible();

            private LowerIfFlexible() {
                super(null);
            }

            @Override // kotlin.reflect.jvm.internal.impl.types.checker.TypeCheckerContext.SupertypesPolicy
            @NotNull
            /* JADX INFO: renamed from: transformType */
            public SimpleType mo385transformType(@NotNull KotlinType type) {
                Intrinsics.checkParameterIsNotNull(type, "type");
                return FlexibleTypesKt.lowerIfFlexible(type);
            }
        }

        /* JADX INFO: compiled from: TypeCheckerContext.kt */
        public static final class LowerIfFlexibleWithCustomSubstitutor extends SupertypesPolicy {

            @NotNull
            private final TypeSubstitutor substitutor;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public LowerIfFlexibleWithCustomSubstitutor(@NotNull TypeSubstitutor substitutor) {
                super(null);
                Intrinsics.checkParameterIsNotNull(substitutor, "substitutor");
                this.substitutor = substitutor;
            }

            @Override // kotlin.reflect.jvm.internal.impl.types.checker.TypeCheckerContext.SupertypesPolicy
            @NotNull
            /* JADX INFO: renamed from: transformType */
            public SimpleType mo385transformType(@NotNull KotlinType type) {
                Intrinsics.checkParameterIsNotNull(type, "type");
                KotlinType kotlinTypeSafeSubstitute = this.substitutor.safeSubstitute(FlexibleTypesKt.lowerIfFlexible(type), Variance.INVARIANT);
                Intrinsics.checkExpressionValueIsNotNull(kotlinTypeSafeSubstitute, "substitutor.safeSubstitu…le(), Variance.INVARIANT)");
                return TypeSubstitutionKt.asSimpleType(kotlinTypeSafeSubstitute);
            }
        }
    }

    public final boolean isAllowedTypeVariable(@NotNull UnwrappedType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return this.allowedTypeVariable && (receiver$0.getConstructor() instanceof NewTypeVariableConstructor);
    }
}
