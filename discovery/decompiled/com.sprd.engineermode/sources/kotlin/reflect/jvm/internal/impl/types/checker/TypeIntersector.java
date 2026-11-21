package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.IntersectionTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.SpecialTypesKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: IntersectionType.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class TypeIntersector {
    public static final TypeIntersector INSTANCE = new TypeIntersector();

    private TypeIntersector() {
    }

    @NotNull
    public final SimpleType intersectTypes$descriptors(@NotNull List<? extends SimpleType> types) {
        Intrinsics.checkParameterIsNotNull(types, "types");
        boolean z = types.size() > 1;
        if (_Assertions.ENABLED && !z) {
            throw new AssertionError("Size should be at least 2, but it is " + types.size());
        }
        ArrayList inputTypes = new ArrayList();
        for (SimpleType type : types) {
            if (type.getConstructor() instanceof IntersectionTypeConstructor) {
                Iterable supertypes = type.getConstructor().getSupertypes();
                Intrinsics.checkExpressionValueIsNotNull(supertypes, "type.constructor.supertypes");
                Iterable $receiver$iv = supertypes;
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
                for (Object item$iv$iv : $receiver$iv) {
                    KotlinType it = (KotlinType) item$iv$iv;
                    Intrinsics.checkExpressionValueIsNotNull(it, "it");
                    SimpleType it2 = FlexibleTypesKt.upperIfFlexible(it);
                    if (type.isMarkedNullable()) {
                        it2 = it2.makeNullableAsSpecified(true);
                    }
                    destination$iv$iv.add(it2);
                }
                inputTypes.addAll((List) destination$iv$iv);
            } else {
                inputTypes.add(type);
            }
        }
        ArrayList $receiver$iv2 = inputTypes;
        ResultNullability resultNullabilityCombine = ResultNullability.START;
        for (Object element$iv : $receiver$iv2) {
            UnwrappedType p2 = (UnwrappedType) element$iv;
            ResultNullability p1 = resultNullabilityCombine;
            resultNullabilityCombine = p1.combine(p2);
        }
        ResultNullability resultNullability = resultNullabilityCombine;
        ArrayList $receiver$iv3 = inputTypes;
        Collection destination$iv = new LinkedHashSet();
        for (Object item$iv : $receiver$iv3) {
            SimpleType it3 = (SimpleType) item$iv;
            if (resultNullability == ResultNullability.NOT_NULL) {
                it3 = SpecialTypesKt.makeSimpleTypeDefinitelyNotNullOrNotNull(it3);
            }
            destination$iv.add(it3);
        }
        LinkedHashSet correctNullability = (LinkedHashSet) destination$iv;
        return intersectTypesWithoutIntersectionType(correctNullability);
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0080  */
    private final SimpleType intersectTypesWithoutIntersectionType(Set<? extends SimpleType> set) {
        boolean strictSupertypeOrHasEqual;
        boolean z;
        if (set.size() == 1) {
            return (SimpleType) CollectionsKt.single(set);
        }
        ArrayList filteredSuperAndEqualTypes = new ArrayList(set);
        Iterator iterator = filteredSuperAndEqualTypes.iterator();
        Intrinsics.checkExpressionValueIsNotNull(iterator, "filteredSuperAndEqualTypes.iterator()");
        boolean z2 = false;
        boolean z3 = false;
        while (iterator.hasNext()) {
            SimpleType upper = (SimpleType) iterator.next();
            ArrayList $receiver$iv = filteredSuperAndEqualTypes;
            if (!($receiver$iv instanceof Collection) || !$receiver$iv.isEmpty()) {
                Iterator it = $receiver$iv.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        strictSupertypeOrHasEqual = false;
                        break;
                    }
                    Object element$iv = it.next();
                    SimpleType lower = (SimpleType) element$iv;
                    if (lower != upper) {
                        TypeIntersector typeIntersector = INSTANCE;
                        Intrinsics.checkExpressionValueIsNotNull(lower, "lower");
                        Intrinsics.checkExpressionValueIsNotNull(upper, "upper");
                        if (typeIntersector.isStrictSupertype(lower, upper) || NewKotlinTypeChecker.INSTANCE.equalTypes(lower, upper)) {
                            z = true;
                        } else {
                            z = false;
                        }
                    } else {
                        z = false;
                    }
                    if (z) {
                        strictSupertypeOrHasEqual = true;
                        break;
                    }
                }
            } else {
                strictSupertypeOrHasEqual = false;
            }
            if (strictSupertypeOrHasEqual) {
                iterator.remove();
            }
            z2 = strictSupertypeOrHasEqual;
            z3 = z3;
        }
        boolean zIsEmpty = true ^ filteredSuperAndEqualTypes.isEmpty();
        if (_Assertions.ENABLED && !zIsEmpty) {
            throw new AssertionError("This collections cannot be empty! input types: " + CollectionsKt.joinToString$default(set, null, null, null, 0, null, null, 63, null));
        }
        if (filteredSuperAndEqualTypes.size() >= 2) {
            IntersectionTypeConstructor constructor = new IntersectionTypeConstructor(set);
            return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(Annotations.Companion.getEMPTY(), constructor, CollectionsKt.emptyList(), false, constructor.createScopeForKotlinType());
        }
        Object objSingle = CollectionsKt.single((List<? extends Object>) filteredSuperAndEqualTypes);
        Intrinsics.checkExpressionValueIsNotNull(objSingle, "filteredSuperAndEqualTypes.single()");
        return (SimpleType) objSingle;
    }

    private final boolean isStrictSupertype(KotlinType subtype, KotlinType supertype) {
        NewKotlinTypeChecker $receiver = NewKotlinTypeChecker.INSTANCE;
        return $receiver.isSubtypeOf(subtype, supertype) && !$receiver.isSubtypeOf(supertype, subtype);
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* JADX INFO: compiled from: IntersectionType.kt */
    private static final class ResultNullability {
        private static final /* synthetic */ ResultNullability[] $VALUES;
        public static final ResultNullability ACCEPT_NULL;
        public static final ResultNullability NOT_NULL;
        public static final ResultNullability START;
        public static final ResultNullability UNKNOWN;

        static {
            START start = new START("START", 0);
            START = start;
            ACCEPT_NULL accept_null = new ACCEPT_NULL("ACCEPT_NULL", 1);
            ACCEPT_NULL = accept_null;
            UNKNOWN unknown = new UNKNOWN("UNKNOWN", 2);
            UNKNOWN = unknown;
            NOT_NULL not_null = new NOT_NULL("NOT_NULL", 3);
            NOT_NULL = not_null;
            $VALUES = new ResultNullability[]{start, accept_null, unknown, not_null};
        }

        public static ResultNullability valueOf(String str) {
            return (ResultNullability) Enum.valueOf(ResultNullability.class, str);
        }

        public static ResultNullability[] values() {
            return (ResultNullability[]) $VALUES.clone();
        }

        @NotNull
        public abstract ResultNullability combine(@NotNull UnwrappedType unwrappedType);

        /* JADX INFO: compiled from: IntersectionType.kt */
        static final class START extends ResultNullability {
            START(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1) {
                super($enum_name_or_ordinal$0, $enum_name_or_ordinal$1, null);
            }

            @Override // kotlin.reflect.jvm.internal.impl.types.checker.TypeIntersector.ResultNullability
            @NotNull
            public ResultNullability combine(@NotNull UnwrappedType nextType) {
                Intrinsics.checkParameterIsNotNull(nextType, "nextType");
                return getResultNullability(nextType);
            }
        }

        private ResultNullability(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1) {
            super($enum_name_or_ordinal$0, $enum_name_or_ordinal$1);
        }

        public /* synthetic */ ResultNullability(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1, DefaultConstructorMarker $constructor_marker) {
            this($enum_name_or_ordinal$0, $enum_name_or_ordinal$1);
        }

        /* JADX INFO: compiled from: IntersectionType.kt */
        static final class ACCEPT_NULL extends ResultNullability {
            ACCEPT_NULL(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1) {
                super($enum_name_or_ordinal$0, $enum_name_or_ordinal$1, null);
            }

            @Override // kotlin.reflect.jvm.internal.impl.types.checker.TypeIntersector.ResultNullability
            @NotNull
            public ResultNullability combine(@NotNull UnwrappedType nextType) {
                Intrinsics.checkParameterIsNotNull(nextType, "nextType");
                return getResultNullability(nextType);
            }
        }

        /* JADX INFO: compiled from: IntersectionType.kt */
        static final class UNKNOWN extends ResultNullability {
            UNKNOWN(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1) {
                super($enum_name_or_ordinal$0, $enum_name_or_ordinal$1, null);
            }

            @Override // kotlin.reflect.jvm.internal.impl.types.checker.TypeIntersector.ResultNullability
            @NotNull
            public ResultNullability combine(@NotNull UnwrappedType nextType) {
                Intrinsics.checkParameterIsNotNull(nextType, "nextType");
                ResultNullability it = getResultNullability(nextType);
                return it == ResultNullability.ACCEPT_NULL ? this : it;
            }
        }

        /* JADX INFO: compiled from: IntersectionType.kt */
        static final class NOT_NULL extends ResultNullability {
            NOT_NULL(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1) {
                super($enum_name_or_ordinal$0, $enum_name_or_ordinal$1, null);
            }

            @Override // kotlin.reflect.jvm.internal.impl.types.checker.TypeIntersector.ResultNullability
            @NotNull
            public NOT_NULL combine(@NotNull UnwrappedType nextType) {
                Intrinsics.checkParameterIsNotNull(nextType, "nextType");
                return this;
            }
        }

        @NotNull
        protected final ResultNullability getResultNullability(@NotNull UnwrappedType receiver$0) {
            Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
            if (receiver$0.isMarkedNullable()) {
                return ACCEPT_NULL;
            }
            return NullabilityChecker.INSTANCE.isSubtypeOfAny(receiver$0) ? NOT_NULL : UNKNOWN;
        }
    }
}
