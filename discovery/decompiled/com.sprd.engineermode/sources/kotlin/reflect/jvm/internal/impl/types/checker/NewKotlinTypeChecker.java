package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ModalityKt;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructor;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntegerValueTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.DynamicTypesKt;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.IntersectionTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.SpecialTypesKt;
import kotlin.reflect.jvm.internal.impl.types.StubType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.reflect.jvm.internal.impl.utils.SmartList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: NewKotlinTypeChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class NewKotlinTypeChecker implements KotlinTypeChecker {
    public static final NewKotlinTypeChecker INSTANCE = new NewKotlinTypeChecker();

    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0 = new int[TypeCheckerContext.LowerCapturedTypePolicy.values().length];
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;

        static {
            $EnumSwitchMapping$0[TypeCheckerContext.LowerCapturedTypePolicy.CHECK_ONLY_LOWER.ordinal()] = 1;
            $EnumSwitchMapping$0[TypeCheckerContext.LowerCapturedTypePolicy.CHECK_SUBTYPE_AND_LOWER.ordinal()] = 2;
            $EnumSwitchMapping$0[TypeCheckerContext.LowerCapturedTypePolicy.SKIP_LOWER.ordinal()] = 3;
            $EnumSwitchMapping$1 = new int[TypeCheckerContext.SeveralSupertypesWithSameConstructorPolicy.values().length];
            $EnumSwitchMapping$1[TypeCheckerContext.SeveralSupertypesWithSameConstructorPolicy.FORCE_NOT_SUBTYPE.ordinal()] = 1;
            $EnumSwitchMapping$1[TypeCheckerContext.SeveralSupertypesWithSameConstructorPolicy.TAKE_FIRST_FOR_SUBTYPING.ordinal()] = 2;
            $EnumSwitchMapping$1[TypeCheckerContext.SeveralSupertypesWithSameConstructorPolicy.CHECK_ANY_OF_THEM.ordinal()] = 3;
            $EnumSwitchMapping$1[TypeCheckerContext.SeveralSupertypesWithSameConstructorPolicy.INTERSECT_ARGUMENTS_AND_CHECK_AGAIN.ordinal()] = 4;
            $EnumSwitchMapping$2 = new int[Variance.values().length];
            $EnumSwitchMapping$2[Variance.INVARIANT.ordinal()] = 1;
            $EnumSwitchMapping$2[Variance.OUT_VARIANCE.ordinal()] = 2;
            $EnumSwitchMapping$2[Variance.IN_VARIANCE.ordinal()] = 3;
        }
    }

    private NewKotlinTypeChecker() {
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker
    public boolean isSubtypeOf(@NotNull KotlinType subtype, @NotNull KotlinType supertype) {
        Intrinsics.checkParameterIsNotNull(subtype, "subtype");
        Intrinsics.checkParameterIsNotNull(supertype, "supertype");
        return isSubtypeOf(new TypeCheckerContext(true, false, 2, null), subtype.unwrap(), supertype.unwrap());
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker
    public boolean equalTypes(@NotNull KotlinType a, @NotNull KotlinType b) {
        Intrinsics.checkParameterIsNotNull(a, "a");
        Intrinsics.checkParameterIsNotNull(b, "b");
        boolean z = false;
        return equalTypes(new TypeCheckerContext(z, z, 2, null), a.unwrap(), b.unwrap());
    }

    public final boolean equalTypes(@NotNull TypeCheckerContext receiver$0, @NotNull UnwrappedType a, @NotNull UnwrappedType b) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(a, "a");
        Intrinsics.checkParameterIsNotNull(b, "b");
        if (a == b) {
            return true;
        }
        if (isCommonDenotableType(a) && isCommonDenotableType(b)) {
            if (!receiver$0.areEqualTypeConstructors(a.getConstructor(), b.getConstructor())) {
                return false;
            }
            if (a.getArguments().isEmpty()) {
                return hasFlexibleNullability(a) || hasFlexibleNullability(b) || a.isMarkedNullable() == b.isMarkedNullable();
            }
        }
        return isSubtypeOf(receiver$0, a, b) && isSubtypeOf(receiver$0, b, a);
    }

    private final boolean hasFlexibleNullability(@NotNull KotlinType $receiver) {
        return FlexibleTypesKt.lowerIfFlexible($receiver).isMarkedNullable() != FlexibleTypesKt.upperIfFlexible($receiver).isMarkedNullable();
    }

    private final boolean isCommonDenotableType(KotlinType type) {
        return type.getConstructor().isDenotable() && !DynamicTypesKt.isDynamic(type) && !SpecialTypesKt.isDefinitelyNotNullType(type) && Intrinsics.areEqual(FlexibleTypesKt.lowerIfFlexible(type).getConstructor(), FlexibleTypesKt.upperIfFlexible(type).getConstructor());
    }

    public final boolean isSubtypeOf(@NotNull TypeCheckerContext receiver$0, @NotNull UnwrappedType subType, @NotNull UnwrappedType superType) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(subType, "subType");
        Intrinsics.checkParameterIsNotNull(superType, "superType");
        if (subType == superType) {
            return true;
        }
        UnwrappedType newSubType = transformToNewType(subType);
        UnwrappedType newSuperType = transformToNewType(superType);
        Boolean boolCheckSubtypeForSpecialCases = checkSubtypeForSpecialCases(receiver$0, FlexibleTypesKt.lowerIfFlexible(newSubType), FlexibleTypesKt.upperIfFlexible(newSuperType));
        if (boolCheckSubtypeForSpecialCases != null) {
            boolean it = boolCheckSubtypeForSpecialCases.booleanValue();
            receiver$0.addSubtypeConstraint(newSubType, newSuperType);
            return it;
        }
        Boolean boolAddSubtypeConstraint = receiver$0.addSubtypeConstraint(newSubType, newSuperType);
        if (boolAddSubtypeConstraint == null) {
            return isSubtypeOfForSingleClassifierType(receiver$0, FlexibleTypesKt.lowerIfFlexible(newSubType), FlexibleTypesKt.upperIfFlexible(newSuperType));
        }
        boolean it2 = boolAddSubtypeConstraint.booleanValue();
        return it2;
    }

    @NotNull
    public final SimpleType transformToNewType(@NotNull SimpleType type) {
        KotlinType type2;
        Intrinsics.checkParameterIsNotNull(type, "type");
        TypeConstructor constructor = type.getConstructor();
        UnwrappedType unwrappedTypeUnwrap = null;
        if (constructor instanceof CapturedTypeConstructor) {
            TypeProjection it = ((CapturedTypeConstructor) constructor).getTypeProjection();
            if (!(it.getProjectionKind() == Variance.IN_VARIANCE)) {
                it = null;
            }
            if (it != null && (type2 = it.getType()) != null) {
                unwrappedTypeUnwrap = type2.unwrap();
            }
            UnwrappedType lowerType = unwrappedTypeUnwrap;
            if (((CapturedTypeConstructor) constructor).getNewTypeConstructor() == null) {
                CapturedTypeConstructor capturedTypeConstructor = (CapturedTypeConstructor) constructor;
                TypeProjection typeProjection = ((CapturedTypeConstructor) constructor).getTypeProjection();
                Iterable $receiver$iv = ((CapturedTypeConstructor) constructor).getSupertypes();
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
                for (Object item$iv$iv : $receiver$iv) {
                    destination$iv$iv.add(((KotlinType) item$iv$iv).unwrap());
                }
                capturedTypeConstructor.setNewTypeConstructor(new NewCapturedTypeConstructor(typeProjection, (List) destination$iv$iv));
            }
            CaptureStatus captureStatus = CaptureStatus.FOR_SUBTYPING;
            NewCapturedTypeConstructor newTypeConstructor = ((CapturedTypeConstructor) constructor).getNewTypeConstructor();
            if (newTypeConstructor == null) {
                Intrinsics.throwNpe();
            }
            return new NewCapturedType(captureStatus, newTypeConstructor, lowerType, type.getAnnotations(), type.isMarkedNullable());
        }
        if (constructor instanceof IntegerValueTypeConstructor) {
            Iterable $receiver$iv2 = ((IntegerValueTypeConstructor) constructor).getSupertypes();
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv2, 10));
            for (Object item$iv$iv2 : $receiver$iv2) {
                destination$iv$iv2.add(TypeUtils.makeNullableAsSpecified((KotlinType) item$iv$iv2, type.isMarkedNullable()));
            }
            return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(type.getAnnotations(), new IntersectionTypeConstructor((Collection) ((List) destination$iv$iv2)), CollectionsKt.emptyList(), false, type.getMemberScope());
        }
        if (!(constructor instanceof IntersectionTypeConstructor) || !type.isMarkedNullable()) {
            return type;
        }
        boolean changed$iv = false;
        Iterable $receiver$iv$iv = ((IntersectionTypeConstructor) constructor).getSupertypes();
        Collection destination$iv$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv$iv, 10));
        for (Object item$iv$iv$iv : $receiver$iv$iv) {
            KotlinType it$iv = (KotlinType) item$iv$iv$iv;
            changed$iv = true;
            destination$iv$iv$iv.add(TypeUtilsKt.makeNullable(it$iv));
        }
        List newSupertypes$iv = (List) destination$iv$iv$iv;
        IntersectionTypeConstructor intersectionTypeConstructor = !changed$iv ? null : new IntersectionTypeConstructor(newSupertypes$iv);
        if (intersectionTypeConstructor == null) {
            intersectionTypeConstructor = (IntersectionTypeConstructor) constructor;
        }
        IntersectionTypeConstructor newConstructor = intersectionTypeConstructor;
        return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(type.getAnnotations(), newConstructor, CollectionsKt.emptyList(), false, newConstructor.createScopeForKotlinType());
    }

    @NotNull
    public final UnwrappedType transformToNewType(@NotNull UnwrappedType type) {
        SimpleType simpleTypeFlexibleType;
        Intrinsics.checkParameterIsNotNull(type, "type");
        if (type instanceof SimpleType) {
            simpleTypeFlexibleType = transformToNewType((SimpleType) type);
        } else if (type instanceof FlexibleType) {
            SimpleType newLower = transformToNewType(((FlexibleType) type).getLowerBound());
            SimpleType newUpper = transformToNewType(((FlexibleType) type).getUpperBound());
            if (newLower == ((FlexibleType) type).getLowerBound() && newUpper == ((FlexibleType) type).getUpperBound()) {
                simpleTypeFlexibleType = type;
            } else {
                simpleTypeFlexibleType = KotlinTypeFactory.flexibleType(newLower, newUpper);
            }
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return TypeWithEnhancementKt.inheritEnhancement(simpleTypeFlexibleType, type);
    }

    private final Boolean checkSubtypeForSpecialCases(@NotNull TypeCheckerContext $receiver, SimpleType subType, SimpleType superType) {
        boolean z = true;
        if (KotlinTypeKt.isError(subType) || KotlinTypeKt.isError(superType)) {
            if ($receiver.getErrorTypeEqualsToAnything()) {
                return true;
            }
            if (!subType.isMarkedNullable() || superType.isMarkedNullable()) {
                return Boolean.valueOf(StrictEqualityTypeChecker.INSTANCE.strictEqualTypes(subType.makeNullableAsSpecified(false), superType.makeNullableAsSpecified(false)));
            }
            return false;
        }
        if ((subType instanceof StubType) || (superType instanceof StubType)) {
            return true;
        }
        if ((superType instanceof NewCapturedType) && ((NewCapturedType) superType).getLowerType() != null) {
            int i = WhenMappings.$EnumSwitchMapping$0[$receiver.getLowerCapturedTypePolicy(subType, (NewCapturedType) superType).ordinal()];
            if (i == 1) {
                return Boolean.valueOf(isSubtypeOf($receiver, subType, ((NewCapturedType) superType).getLowerType()));
            }
            if (i == 2 && isSubtypeOf($receiver, subType, ((NewCapturedType) superType).getLowerType())) {
                return true;
            }
        }
        TypeConstructor constructor = superType.getConstructor();
        if (!(constructor instanceof IntersectionTypeConstructor)) {
            constructor = null;
        }
        IntersectionTypeConstructor it = (IntersectionTypeConstructor) constructor;
        if (it == null) {
            return null;
        }
        boolean z2 = !superType.isMarkedNullable();
        if (_Assertions.ENABLED && !z2) {
            throw new AssertionError("Intersection type should not be marked nullable!: " + superType);
        }
        Iterable $receiver$iv = it.getSupertypes();
        if (!($receiver$iv instanceof Collection) || !((Collection) $receiver$iv).isEmpty()) {
            for (Object element$iv : $receiver$iv) {
                if (!INSTANCE.isSubtypeOf($receiver, subType, ((KotlinType) element$iv).unwrap())) {
                    z = false;
                    break;
                }
            }
        }
        return Boolean.valueOf(z);
    }

    private final boolean hasNothingSupertype(@NotNull TypeCheckerContext $receiver, SimpleType type) {
        TypeCheckerContext.SupertypesPolicy.LowerIfFlexible lowerIfFlexible;
        SimpleType p1 = type;
        if (KotlinBuiltIns.isNothingOrNullableNothing(p1)) {
            return true;
        }
        $receiver.initialize();
        ArrayDeque deque$iv = $receiver.supertypesDeque;
        if (deque$iv == null) {
            Intrinsics.throwNpe();
        }
        Set visitedSupertypes$iv = $receiver.supertypesSet;
        if (visitedSupertypes$iv == null) {
            Intrinsics.throwNpe();
        }
        deque$iv.push(type);
        while (!deque$iv.isEmpty()) {
            if (visitedSupertypes$iv.size() > 1000) {
                throw new IllegalStateException(("Too many supertypes for type: " + type + ". Supertypes = " + CollectionsKt.joinToString$default(visitedSupertypes$iv, null, null, null, 0, null, null, 63, null)).toString());
            }
            SimpleType current = (SimpleType) deque$iv.pop();
            Intrinsics.checkExpressionValueIsNotNull(current, "current");
            if (visitedSupertypes$iv.add(current)) {
                if (NewKotlinTypeCheckerKt.isClassType(current)) {
                    lowerIfFlexible = TypeCheckerContext.SupertypesPolicy.None.INSTANCE;
                } else {
                    lowerIfFlexible = TypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE;
                }
                TypeCheckerContext.SupertypesPolicy it$iv = lowerIfFlexible;
                if (!(!Intrinsics.areEqual(it$iv, TypeCheckerContext.SupertypesPolicy.None.INSTANCE))) {
                    lowerIfFlexible = null;
                }
                if (lowerIfFlexible != null) {
                    TypeCheckerContext.SupertypesPolicy policy$iv = lowerIfFlexible;
                    for (KotlinType supertype : current.getConstructor().getSupertypes()) {
                        Intrinsics.checkExpressionValueIsNotNull(supertype, "supertype");
                        SimpleType newType$iv = policy$iv.mo385transformType(supertype);
                        SimpleType p2 = newType$iv;
                        if (KotlinBuiltIns.isNothingOrNullableNothing(p2)) {
                            $receiver.clear();
                            return true;
                        }
                        deque$iv.add(newType$iv);
                    }
                } else {
                    continue;
                }
            }
        }
        $receiver.clear();
        return false;
    }

    private final boolean isSubtypeOfForSingleClassifierType(@NotNull TypeCheckerContext $receiver, SimpleType subType, SimpleType superType) {
        boolean z;
        KotlinType type;
        UnwrappedType unwrappedTypeUnwrap;
        boolean z2 = NewKotlinTypeCheckerKt.isSingleClassifierType(subType) || NewKotlinTypeCheckerKt.isIntersectionType(subType) || $receiver.isAllowedTypeVariable(subType);
        if (_Assertions.ENABLED && !z2) {
            throw new AssertionError("Not singleClassifierType and not intersection subType: " + subType);
        }
        boolean z3 = NewKotlinTypeCheckerKt.isSingleClassifierType(superType) || $receiver.isAllowedTypeVariable(superType);
        if (_Assertions.ENABLED && !z3) {
            throw new AssertionError("Not singleClassifierType superType: " + superType);
        }
        if (!NullabilityChecker.INSTANCE.isPossibleSubtype($receiver, subType, superType)) {
            return false;
        }
        TypeConstructor superConstructor = superType.getConstructor();
        if ((Intrinsics.areEqual(subType.getConstructor(), superConstructor) && superConstructor.getParameters().isEmpty()) || TypeUtilsKt.isAnyOrNullableAny(superType)) {
            return true;
        }
        List<SimpleType> listFindCorrespondingSupertypes = findCorrespondingSupertypes($receiver, subType, superConstructor);
        int size = listFindCorrespondingSupertypes.size();
        if (size == 0) {
            return hasNothingSupertype($receiver, subType);
        }
        if (size == 1) {
            return isSubtypeForSameConstructor($receiver, ((SimpleType) CollectionsKt.first((List) listFindCorrespondingSupertypes)).getArguments(), superType);
        }
        int i = WhenMappings.$EnumSwitchMapping$1[$receiver.getSameConstructorPolicy().ordinal()];
        if (i == 1) {
            return false;
        }
        if (i == 2) {
            return isSubtypeForSameConstructor($receiver, ((SimpleType) CollectionsKt.first((List) listFindCorrespondingSupertypes)).getArguments(), superType);
        }
        if (i == 3 || i == 4) {
            List<SimpleType> $receiver$iv = listFindCorrespondingSupertypes;
            if (!($receiver$iv instanceof Collection) || !$receiver$iv.isEmpty()) {
                Iterator it = $receiver$iv.iterator();
                while (true) {
                    if (it.hasNext()) {
                        Object element$iv = it.next();
                        if (INSTANCE.isSubtypeForSameConstructor($receiver, ((SimpleType) element$iv).getArguments(), superType)) {
                            z = true;
                            break;
                        }
                    } else {
                        z = false;
                        break;
                    }
                }
            } else {
                z = false;
            }
            if (z) {
                return true;
            }
        }
        if ($receiver.getSameConstructorPolicy() != TypeCheckerContext.SeveralSupertypesWithSameConstructorPolicy.INTERSECT_ARGUMENTS_AND_CHECK_AGAIN) {
            return false;
        }
        Iterable parameters = superConstructor.getParameters();
        Intrinsics.checkExpressionValueIsNotNull(parameters, "superConstructor.parameters");
        Iterable $receiver$iv2 = parameters;
        int i2 = 10;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv2, 10));
        int index = 0;
        for (Object item$iv$iv : $receiver$iv2) {
            int index$iv$iv = index + 1;
            if (index < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            List<SimpleType> $receiver$iv3 = listFindCorrespondingSupertypes;
            TypeConstructor superConstructor2 = superConstructor;
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv3, i2));
            Iterable $receiver$iv$iv = $receiver$iv3;
            for (Object item$iv$iv2 : $receiver$iv$iv) {
                SimpleType it2 = (SimpleType) item$iv$iv2;
                Iterable $receiver$iv4 = $receiver$iv3;
                TypeProjection it3 = (TypeProjection) CollectionsKt.getOrNull(it2.getArguments(), index);
                if (it3 != null) {
                    TypeProjection typeProjection = it3;
                    Iterable $receiver$iv$iv2 = $receiver$iv$iv;
                    if (!(it3.getProjectionKind() == Variance.INVARIANT)) {
                        typeProjection = null;
                    }
                    if (typeProjection != null && (type = typeProjection.getType()) != null && (unwrappedTypeUnwrap = type.unwrap()) != null) {
                        destination$iv$iv2.add(unwrappedTypeUnwrap);
                        $receiver$iv3 = $receiver$iv4;
                        $receiver$iv$iv = $receiver$iv$iv2;
                    }
                }
                throw new IllegalStateException(("Incorrect type: " + it2 + ", subType: " + subType + ", superType: " + superType).toString());
            }
            List allProjections = (List) destination$iv$iv2;
            destination$iv$iv.add(TypeUtilsKt.asTypeProjection(IntersectionTypeKt.intersectTypes(allProjections)));
            index = index$iv$iv;
            superConstructor = superConstructor2;
            i2 = 10;
        }
        List newArguments = (List) destination$iv$iv;
        return isSubtypeForSameConstructor($receiver, newArguments, superType);
    }

    private final List<SimpleType> collectAndFilter(@NotNull TypeCheckerContext $receiver, SimpleType classType, TypeConstructor constructor) {
        return selectOnlyPureKotlinSupertypes(collectAllSupertypesWithGivenTypeConstructor($receiver, classType, constructor));
    }

    @NotNull
    public final List<SimpleType> findCorrespondingSupertypes(@NotNull TypeCheckerContext receiver$0, @NotNull SimpleType baseType, @NotNull TypeConstructor constructor) {
        TypeCheckerContext.SupertypesPolicy.LowerIfFlexible lowerIfFlexible;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(baseType, "baseType");
        Intrinsics.checkParameterIsNotNull(constructor, "constructor");
        if (NewKotlinTypeCheckerKt.isClassType(baseType)) {
            return collectAndFilter(receiver$0, baseType, constructor);
        }
        if (!(constructor.mo381getDeclarationDescriptor() instanceof ClassDescriptor)) {
            return collectAllSupertypesWithGivenTypeConstructor(receiver$0, baseType, constructor);
        }
        SmartList classTypeSupertypes = new SmartList();
        receiver$0.initialize();
        ArrayDeque deque$iv = receiver$0.supertypesDeque;
        if (deque$iv == null) {
            Intrinsics.throwNpe();
        }
        Set visitedSupertypes$iv = receiver$0.supertypesSet;
        if (visitedSupertypes$iv == null) {
            Intrinsics.throwNpe();
        }
        deque$iv.push(baseType);
        while (!deque$iv.isEmpty()) {
            if (visitedSupertypes$iv.size() > 1000) {
                throw new IllegalStateException(("Too many supertypes for type: " + baseType + ". Supertypes = " + CollectionsKt.joinToString$default(visitedSupertypes$iv, null, null, null, 0, null, null, 63, null)).toString());
            }
            SimpleType current = (SimpleType) deque$iv.pop();
            Intrinsics.checkExpressionValueIsNotNull(current, "current");
            if (visitedSupertypes$iv.add(current)) {
                if (NewKotlinTypeCheckerKt.isClassType(current)) {
                    classTypeSupertypes.add(current);
                    lowerIfFlexible = TypeCheckerContext.SupertypesPolicy.None.INSTANCE;
                } else {
                    lowerIfFlexible = TypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE;
                }
                TypeCheckerContext.SupertypesPolicy it$iv = lowerIfFlexible;
                if (!(!Intrinsics.areEqual(it$iv, TypeCheckerContext.SupertypesPolicy.None.INSTANCE))) {
                    lowerIfFlexible = null;
                }
                if (lowerIfFlexible != null) {
                    TypeCheckerContext.SupertypesPolicy policy$iv = lowerIfFlexible;
                    for (KotlinType supertype : current.getConstructor().getSupertypes()) {
                        Intrinsics.checkExpressionValueIsNotNull(supertype, "supertype");
                        SimpleType newType$iv = policy$iv.mo385transformType(supertype);
                        deque$iv.add(newType$iv);
                    }
                }
            }
        }
        receiver$0.clear();
        SmartList $receiver$iv = classTypeSupertypes;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $receiver$iv) {
            SimpleType it = (SimpleType) element$iv$iv;
            NewKotlinTypeChecker newKotlinTypeChecker = INSTANCE;
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            Iterable list$iv$iv = newKotlinTypeChecker.collectAndFilter(receiver$0, it, constructor);
            CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
        }
        return (List) destination$iv$iv;
    }

    private final List<SimpleType> collectAllSupertypesWithGivenTypeConstructor(@NotNull TypeCheckerContext $receiver, SimpleType baseType, TypeConstructor constructor) {
        TypeCheckerContext.SupertypesPolicy lowerIfFlexibleWithCustomSubstitutor;
        TypeCheckerContext typeCheckerContext = $receiver;
        Object $receiver$iv = constructor.mo381getDeclarationDescriptor();
        Function2 function2 = null;
        ClassDescriptor classDescriptor = (ClassDescriptor) (!($receiver$iv instanceof ClassDescriptor) ? null : $receiver$iv);
        int i = 4;
        boolean z = true;
        if (classDescriptor != null && isCommonFinalClass(classDescriptor)) {
            if (typeCheckerContext.areEqualTypeConstructors(baseType.getConstructor(), constructor)) {
                SimpleType simpleTypeCaptureFromArguments$default = NewCapturedTypeKt.captureFromArguments$default(baseType, CaptureStatus.FOR_SUBTYPING, null, 4, null);
                if (simpleTypeCaptureFromArguments$default == null) {
                    simpleTypeCaptureFromArguments$default = baseType;
                }
                return CollectionsKt.listOf(simpleTypeCaptureFromArguments$default);
            }
            return CollectionsKt.emptyList();
        }
        List result = new SmartList();
        $receiver.initialize();
        ArrayDeque deque$iv = $receiver.supertypesDeque;
        if (deque$iv == null) {
            Intrinsics.throwNpe();
        }
        Set visitedSupertypes$iv = $receiver.supertypesSet;
        if (visitedSupertypes$iv == null) {
            Intrinsics.throwNpe();
        }
        deque$iv.push(baseType);
        while (deque$iv.isEmpty() ^ z) {
            if (visitedSupertypes$iv.size() > 1000) {
                throw new IllegalStateException(("Too many supertypes for type: " + baseType + ". Supertypes = " + CollectionsKt.joinToString$default(visitedSupertypes$iv, null, null, null, 0, null, null, 63, null)).toString());
            }
            SimpleType current = (SimpleType) deque$iv.pop();
            Intrinsics.checkExpressionValueIsNotNull(current, "current");
            if (visitedSupertypes$iv.add(current)) {
                SimpleType current2 = NewCapturedTypeKt.captureFromArguments$default(current, CaptureStatus.FOR_SUBTYPING, function2, i, function2);
                if (current2 == null) {
                    current2 = current;
                }
                if (typeCheckerContext.areEqualTypeConstructors(current2.getConstructor(), constructor)) {
                    result.add(current2);
                    lowerIfFlexibleWithCustomSubstitutor = TypeCheckerContext.SupertypesPolicy.None.INSTANCE;
                } else if (current2.getArguments().isEmpty()) {
                    lowerIfFlexibleWithCustomSubstitutor = TypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE;
                } else {
                    lowerIfFlexibleWithCustomSubstitutor = new TypeCheckerContext.SupertypesPolicy.LowerIfFlexibleWithCustomSubstitutor(TypeConstructorSubstitution.Companion.create(current2).buildSubstitutor());
                }
                TypeCheckerContext.SupertypesPolicy it$iv = lowerIfFlexibleWithCustomSubstitutor;
                z = true;
                if (!(!Intrinsics.areEqual(it$iv, TypeCheckerContext.SupertypesPolicy.None.INSTANCE))) {
                    lowerIfFlexibleWithCustomSubstitutor = null;
                }
                if (lowerIfFlexibleWithCustomSubstitutor == null) {
                    typeCheckerContext = $receiver;
                    i = 4;
                    function2 = null;
                } else {
                    TypeCheckerContext.SupertypesPolicy policy$iv = lowerIfFlexibleWithCustomSubstitutor;
                    for (KotlinType supertype : current.getConstructor().getSupertypes()) {
                        Intrinsics.checkExpressionValueIsNotNull(supertype, "supertype");
                        SimpleType newType$iv = policy$iv.mo385transformType(supertype);
                        deque$iv.add(newType$iv);
                    }
                    typeCheckerContext = $receiver;
                    i = 4;
                    function2 = null;
                }
            }
        }
        $receiver.clear();
        return result;
    }

    private final boolean isCommonFinalClass(@NotNull ClassDescriptor $receiver) {
        return (!ModalityKt.isFinalClass($receiver) || $receiver.getKind() == ClassKind.ENUM_ENTRY || $receiver.getKind() == ClassKind.ANNOTATION_CLASS) ? false : true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final List<SimpleType> selectOnlyPureKotlinSupertypes(List<? extends SimpleType> $receiver$iv) {
        if ($receiver$iv.size() < 2) {
            return $receiver$iv;
        }
        Collection destination$iv$iv = new ArrayList();
        Iterator it = $receiver$iv.iterator();
        while (true) {
            boolean z = true;
            if (!it.hasNext()) {
                break;
            }
            Object element$iv$iv = it.next();
            SimpleType it2 = (SimpleType) element$iv$iv;
            Iterable $receiver$iv2 = it2.getArguments();
            if (!($receiver$iv2 instanceof Collection) || !((Collection) $receiver$iv2).isEmpty()) {
                for (Object element$iv : $receiver$iv2) {
                    TypeProjection it3 = (TypeProjection) element$iv;
                    KotlinType type = it3.getType();
                    Intrinsics.checkExpressionValueIsNotNull(type, "it.type");
                    if (!(!FlexibleTypesKt.isFlexible(type))) {
                        z = false;
                        break;
                    }
                }
            }
            if (z) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        List allPureSupertypes = (List) destination$iv$iv;
        return allPureSupertypes.isEmpty() ^ true ? allPureSupertypes : $receiver$iv;
    }

    @Nullable
    public final Variance effectiveVariance(@NotNull Variance declared, @NotNull Variance useSite) {
        Intrinsics.checkParameterIsNotNull(declared, "declared");
        Intrinsics.checkParameterIsNotNull(useSite, "useSite");
        if (declared == Variance.INVARIANT) {
            return useSite;
        }
        if (useSite == Variance.INVARIANT || declared == useSite) {
            return declared;
        }
        return null;
    }

    private final boolean isSubtypeForSameConstructor(@NotNull TypeCheckerContext $receiver, List<? extends TypeProjection> list, SimpleType superType) {
        boolean zEqualTypes;
        if (list == superType.getArguments()) {
            return true;
        }
        List<TypeParameterDescriptor> parameters = superType.getConstructor().getParameters();
        Intrinsics.checkExpressionValueIsNotNull(parameters, "superType.constructor.parameters");
        int size = parameters.size();
        for (int index = 0; index < size; index++) {
            TypeProjection superProjection = superType.getArguments().get(index);
            if (!superProjection.isStarProjection()) {
                UnwrappedType superArgumentType = superProjection.getType().unwrap();
                TypeProjection it = list.get(index);
                boolean z = it.getProjectionKind() == Variance.INVARIANT;
                if (_Assertions.ENABLED && !z) {
                    throw new AssertionError("Incorrect sub argument: " + it);
                }
                UnwrappedType subArgumentType = it.getType().unwrap();
                TypeParameterDescriptor typeParameterDescriptor = parameters.get(index);
                Intrinsics.checkExpressionValueIsNotNull(typeParameterDescriptor, "parameters[index]");
                Variance variance = typeParameterDescriptor.getVariance();
                Intrinsics.checkExpressionValueIsNotNull(variance, "parameters[index].variance");
                Variance projectionKind = superProjection.getProjectionKind();
                Intrinsics.checkExpressionValueIsNotNull(projectionKind, "superProjection.projectionKind");
                Variance variance2 = effectiveVariance(variance, projectionKind);
                if (variance2 != null) {
                    if ($receiver.argumentsDepth > 100) {
                        throw new IllegalStateException(("Arguments depth is too high. Some related argument: " + subArgumentType).toString());
                    }
                    $receiver.argumentsDepth++;
                    int i = WhenMappings.$EnumSwitchMapping$2[variance2.ordinal()];
                    if (i == 1) {
                        zEqualTypes = INSTANCE.equalTypes($receiver, subArgumentType, superArgumentType);
                    } else if (i == 2) {
                        zEqualTypes = INSTANCE.isSubtypeOf($receiver, subArgumentType, superArgumentType);
                    } else {
                        if (i != 3) {
                            throw new NoWhenBranchMatchedException();
                        }
                        zEqualTypes = INSTANCE.isSubtypeOf($receiver, superArgumentType, subArgumentType);
                    }
                    boolean result$iv = zEqualTypes;
                    $receiver.argumentsDepth--;
                    if (!result$iv) {
                        return false;
                    }
                } else {
                    return $receiver.getErrorTypeEqualsToAnything();
                }
            }
        }
        return true;
    }
}
