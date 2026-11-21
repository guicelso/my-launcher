package kotlin.reflect.jvm.internal.impl.resolve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Queue;
import java.util.ServiceLoader;
import java.util.Set;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorWithVisibility;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyAccessorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyAccessorDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeCheckerImpl;
import kotlin.reflect.jvm.internal.impl.utils.SmartSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public class OverridingUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final KotlinTypeChecker.TypeConstructorEquality equalityAxioms;
    private static final List<ExternalOverridabilityCondition> EXTERNAL_CONDITIONS = CollectionsKt.toList(ServiceLoader.load(ExternalOverridabilityCondition.class, ExternalOverridabilityCondition.class.getClassLoader()));
    public static final OverridingUtil DEFAULT = new OverridingUtil(new KotlinTypeChecker.TypeConstructorEquality() { // from class: kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil.1
        @Override // kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker.TypeConstructorEquality
        public boolean equals(@NotNull TypeConstructor a, @NotNull TypeConstructor b) {
            return a.equals(b);
        }
    });

    @NotNull
    public static OverridingUtil createWithEqualityAxioms(@NotNull KotlinTypeChecker.TypeConstructorEquality equalityAxioms) {
        return new OverridingUtil(equalityAxioms);
    }

    private OverridingUtil(KotlinTypeChecker.TypeConstructorEquality axioms) {
        this.equalityAxioms = axioms;
    }

    @NotNull
    public static <D extends CallableDescriptor> Set<D> filterOutOverridden(@NotNull Set<D> candidateSet) {
        return filterOverrides(candidateSet, new Function2<D, D, Pair<CallableDescriptor, CallableDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil.2
            /* JADX WARN: Incorrect types in method signature: (TD;TD;)Lkotlin/Pair<Lkotlin/reflect/jvm/internal/impl/descriptors/CallableDescriptor;Lkotlin/reflect/jvm/internal/impl/descriptors/CallableDescriptor;>; */
            @Override // kotlin.jvm.functions.Function2
            public Pair invoke(CallableDescriptor callableDescriptor, CallableDescriptor callableDescriptor2) {
                return new Pair(callableDescriptor, callableDescriptor2);
            }
        });
    }

    @NotNull
    public static <D> Set<D> filterOverrides(@NotNull Set<D> set, @NotNull Function2<? super D, ? super D, Pair<CallableDescriptor, CallableDescriptor>> function2) {
        if (set.size() <= 1) {
            return set;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (Object obj : set) {
            Iterator it = linkedHashSet.iterator();
            while (true) {
                if (it.hasNext()) {
                    Pair<CallableDescriptor, CallableDescriptor> pairInvoke = function2.invoke(obj, (D) it.next());
                    CallableDescriptor callableDescriptorComponent1 = pairInvoke.component1();
                    CallableDescriptor callableDescriptorComponent2 = pairInvoke.component2();
                    if (overrides(callableDescriptorComponent1, callableDescriptorComponent2)) {
                        it.remove();
                    } else if (overrides(callableDescriptorComponent2, callableDescriptorComponent1)) {
                        break;
                    }
                } else {
                    linkedHashSet.add(obj);
                    break;
                }
            }
        }
        return linkedHashSet;
    }

    public static <D extends CallableDescriptor> boolean overrides(@NotNull D f, @NotNull D g) {
        if (!f.equals(g) && DescriptorEquivalenceForOverrides.INSTANCE.areEquivalent(f.getOriginal(), g.getOriginal())) {
            return true;
        }
        CallableDescriptor originalG = g.getOriginal();
        Iterator i$ = DescriptorUtils.getAllOverriddenDescriptors(f).iterator();
        while (i$.hasNext()) {
            if (DescriptorEquivalenceForOverrides.INSTANCE.areEquivalent(originalG, (CallableDescriptor) i$.next())) {
                return true;
            }
        }
        return false;
    }

    @NotNull
    public static Set<CallableMemberDescriptor> getOverriddenDeclarations(@NotNull CallableMemberDescriptor descriptor) {
        Set<CallableMemberDescriptor> result = new LinkedHashSet<>();
        collectOverriddenDeclarations(descriptor, result);
        return result;
    }

    private static void collectOverriddenDeclarations(@NotNull CallableMemberDescriptor descriptor, @NotNull Set<CallableMemberDescriptor> result) {
        if (descriptor.getKind().isReal()) {
            result.add(descriptor);
            return;
        }
        if (descriptor.getOverriddenDescriptors().isEmpty()) {
            throw new IllegalStateException("No overridden descriptors found for (fake override) " + descriptor);
        }
        for (CallableMemberDescriptor overridden : descriptor.getOverriddenDescriptors()) {
            collectOverriddenDeclarations(overridden, result);
        }
    }

    @NotNull
    public OverrideCompatibilityInfo isOverridableBy(@NotNull CallableDescriptor superDescriptor, @NotNull CallableDescriptor subDescriptor, @Nullable ClassDescriptor subClassDescriptor) {
        return isOverridableBy(superDescriptor, subDescriptor, subClassDescriptor, false);
    }

    @NotNull
    public OverrideCompatibilityInfo isOverridableBy(@NotNull CallableDescriptor superDescriptor, @NotNull CallableDescriptor subDescriptor, @Nullable ClassDescriptor subClassDescriptor, boolean checkReturnType) {
        OverrideCompatibilityInfo basicResult = isOverridableByWithoutExternalConditions(superDescriptor, subDescriptor, checkReturnType);
        boolean wasSuccess = basicResult.getResult() == OverrideCompatibilityInfo.Result.OVERRIDABLE;
        for (ExternalOverridabilityCondition externalCondition : EXTERNAL_CONDITIONS) {
            if (externalCondition.getContract() != ExternalOverridabilityCondition.Contract.CONFLICTS_ONLY && (!wasSuccess || externalCondition.getContract() != ExternalOverridabilityCondition.Contract.SUCCESS_ONLY)) {
                ExternalOverridabilityCondition.Result result = externalCondition.isOverridable(superDescriptor, subDescriptor, subClassDescriptor);
                int i = AnonymousClass9.$SwitchMap$org$jetbrains$kotlin$resolve$ExternalOverridabilityCondition$Result[result.ordinal()];
                if (i == 1) {
                    wasSuccess = true;
                } else {
                    if (i == 2) {
                        return OverrideCompatibilityInfo.conflict("External condition failed");
                    }
                    if (i == 3) {
                        return OverrideCompatibilityInfo.incompatible("External condition");
                    }
                }
            }
        }
        if (!wasSuccess) {
            return basicResult;
        }
        for (ExternalOverridabilityCondition externalCondition2 : EXTERNAL_CONDITIONS) {
            if (externalCondition2.getContract() == ExternalOverridabilityCondition.Contract.CONFLICTS_ONLY) {
                ExternalOverridabilityCondition.Result result2 = externalCondition2.isOverridable(superDescriptor, subDescriptor, subClassDescriptor);
                int i2 = AnonymousClass9.$SwitchMap$org$jetbrains$kotlin$resolve$ExternalOverridabilityCondition$Result[result2.ordinal()];
                if (i2 == 1) {
                    throw new IllegalStateException("Contract violation in " + externalCondition2.getClass().getName() + " condition. It's not supposed to end with success");
                }
                if (i2 == 2) {
                    return OverrideCompatibilityInfo.conflict("External condition failed");
                }
                if (i2 == 3) {
                    return OverrideCompatibilityInfo.incompatible("External condition");
                }
            }
        }
        return OverrideCompatibilityInfo.success();
    }

    @NotNull
    public OverrideCompatibilityInfo isOverridableByWithoutExternalConditions(@NotNull CallableDescriptor superDescriptor, @NotNull CallableDescriptor subDescriptor, boolean checkReturnType) {
        OverrideCompatibilityInfo basicOverridability = getBasicOverridabilityProblem(superDescriptor, subDescriptor);
        if (basicOverridability != null) {
            return basicOverridability;
        }
        List<KotlinType> superValueParameters = compiledValueParameters(superDescriptor);
        List<KotlinType> subValueParameters = compiledValueParameters(subDescriptor);
        List<TypeParameterDescriptor> superTypeParameters = superDescriptor.getTypeParameters();
        List<TypeParameterDescriptor> subTypeParameters = subDescriptor.getTypeParameters();
        if (superTypeParameters.size() != subTypeParameters.size()) {
            for (int i = 0; i < superValueParameters.size(); i++) {
                if (!KotlinTypeChecker.DEFAULT.equalTypes(superValueParameters.get(i), subValueParameters.get(i))) {
                    return OverrideCompatibilityInfo.incompatible("Type parameter number mismatch");
                }
            }
            return OverrideCompatibilityInfo.conflict("Type parameter number mismatch");
        }
        KotlinTypeChecker typeChecker = createTypeChecker(superTypeParameters, subTypeParameters);
        for (int i2 = 0; i2 < superTypeParameters.size(); i2++) {
            if (!areTypeParametersEquivalent(superTypeParameters.get(i2), subTypeParameters.get(i2), typeChecker)) {
                return OverrideCompatibilityInfo.incompatible("Type parameter bounds mismatch");
            }
        }
        for (int i3 = 0; i3 < superValueParameters.size(); i3++) {
            if (!areTypesEquivalent(superValueParameters.get(i3), subValueParameters.get(i3), typeChecker)) {
                return OverrideCompatibilityInfo.incompatible("Value parameter type mismatch");
            }
        }
        if ((superDescriptor instanceof FunctionDescriptor) && (subDescriptor instanceof FunctionDescriptor) && ((FunctionDescriptor) superDescriptor).isSuspend() != ((FunctionDescriptor) subDescriptor).isSuspend()) {
            return OverrideCompatibilityInfo.conflict("Incompatible suspendability");
        }
        if (checkReturnType) {
            KotlinType superReturnType = superDescriptor.getReturnType();
            KotlinType subReturnType = subDescriptor.getReturnType();
            if (superReturnType != null && subReturnType != null) {
                boolean bothErrors = KotlinTypeKt.isError(subReturnType) && KotlinTypeKt.isError(superReturnType);
                if (!bothErrors && !typeChecker.isSubtypeOf(subReturnType, superReturnType)) {
                    return OverrideCompatibilityInfo.conflict("Return type mismatch");
                }
            }
        }
        return OverrideCompatibilityInfo.success();
    }

    @Nullable
    public static OverrideCompatibilityInfo getBasicOverridabilityProblem(@NotNull CallableDescriptor superDescriptor, @NotNull CallableDescriptor subDescriptor) {
        if (((superDescriptor instanceof FunctionDescriptor) && !(subDescriptor instanceof FunctionDescriptor)) || ((superDescriptor instanceof PropertyDescriptor) && !(subDescriptor instanceof PropertyDescriptor))) {
            return OverrideCompatibilityInfo.incompatible("Member kind mismatch");
        }
        if (!(superDescriptor instanceof FunctionDescriptor) && !(superDescriptor instanceof PropertyDescriptor)) {
            throw new IllegalArgumentException("This type of CallableDescriptor cannot be checked for overridability: " + superDescriptor);
        }
        if (!superDescriptor.getName().equals(subDescriptor.getName())) {
            return OverrideCompatibilityInfo.incompatible("Name mismatch");
        }
        OverrideCompatibilityInfo receiverAndParameterResult = checkReceiverAndParameterCount(superDescriptor, subDescriptor);
        if (receiverAndParameterResult != null) {
            return receiverAndParameterResult;
        }
        return null;
    }

    @NotNull
    private KotlinTypeChecker createTypeChecker(@NotNull List<TypeParameterDescriptor> firstParameters, @NotNull List<TypeParameterDescriptor> secondParameters) {
        if (firstParameters.isEmpty()) {
            return KotlinTypeCheckerImpl.withAxioms(this.equalityAxioms);
        }
        final Map<TypeConstructor, TypeConstructor> matchingTypeConstructors = new HashMap<>();
        for (int i = 0; i < firstParameters.size(); i++) {
            matchingTypeConstructors.put(firstParameters.get(i).getTypeConstructor(), secondParameters.get(i).getTypeConstructor());
        }
        return KotlinTypeCheckerImpl.withAxioms(new KotlinTypeChecker.TypeConstructorEquality() { // from class: kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil.3
            @Override // kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker.TypeConstructorEquality
            public boolean equals(@NotNull TypeConstructor a, @NotNull TypeConstructor b) {
                if (OverridingUtil.this.equalityAxioms.equals(a, b)) {
                    return true;
                }
                TypeConstructor img1 = (TypeConstructor) matchingTypeConstructors.get(a);
                TypeConstructor img2 = (TypeConstructor) matchingTypeConstructors.get(b);
                if (img1 == null || !img1.equals(b)) {
                    return img2 != null && img2.equals(a);
                }
                return true;
            }
        });
    }

    @Nullable
    private static OverrideCompatibilityInfo checkReceiverAndParameterCount(CallableDescriptor superDescriptor, CallableDescriptor subDescriptor) {
        if ((superDescriptor.getExtensionReceiverParameter() == null) != (subDescriptor.getExtensionReceiverParameter() == null)) {
            return OverrideCompatibilityInfo.incompatible("Receiver presence mismatch");
        }
        if (superDescriptor.getValueParameters().size() != subDescriptor.getValueParameters().size()) {
            return OverrideCompatibilityInfo.incompatible("Value parameter number mismatch");
        }
        return null;
    }

    private static boolean areTypesEquivalent(@NotNull KotlinType typeInSuper, @NotNull KotlinType typeInSub, @NotNull KotlinTypeChecker typeChecker) {
        boolean bothErrors = KotlinTypeKt.isError(typeInSuper) && KotlinTypeKt.isError(typeInSub);
        return bothErrors || typeChecker.equalTypes(typeInSuper, typeInSub);
    }

    private static boolean areTypeParametersEquivalent(@NotNull TypeParameterDescriptor superTypeParameter, @NotNull TypeParameterDescriptor subTypeParameter, @NotNull KotlinTypeChecker typeChecker) {
        List<KotlinType> superBounds = superTypeParameter.getUpperBounds();
        List<KotlinType> subBounds = new ArrayList<>(subTypeParameter.getUpperBounds());
        if (superBounds.size() != subBounds.size()) {
            return false;
        }
        for (KotlinType superBound : superBounds) {
            ListIterator<KotlinType> it = subBounds.listIterator();
            while (it.hasNext()) {
                KotlinType subBound = it.next();
                if (areTypesEquivalent(superBound, subBound, typeChecker)) {
                    it.remove();
                }
            }
            return false;
        }
        return true;
    }

    private static List<KotlinType> compiledValueParameters(CallableDescriptor callableDescriptor) {
        ReceiverParameterDescriptor receiverParameter = callableDescriptor.getExtensionReceiverParameter();
        List<KotlinType> parameters = new ArrayList<>();
        if (receiverParameter != null) {
            parameters.add(receiverParameter.getType());
        }
        for (ValueParameterDescriptor valueParameterDescriptor : callableDescriptor.getValueParameters()) {
            parameters.add(valueParameterDescriptor.getType());
        }
        return parameters;
    }

    public static void generateOverridesInFunctionGroup(@NotNull Name name, @NotNull Collection<? extends CallableMemberDescriptor> membersFromSupertypes, @NotNull Collection<? extends CallableMemberDescriptor> membersFromCurrent, @NotNull ClassDescriptor current, @NotNull OverridingStrategy strategy) {
        Collection<CallableMemberDescriptor> notOverridden = new LinkedHashSet<>(membersFromSupertypes);
        for (CallableMemberDescriptor fromCurrent : membersFromCurrent) {
            Collection<CallableMemberDescriptor> bound = extractAndBindOverridesForMember(fromCurrent, membersFromSupertypes, current, strategy);
            notOverridden.removeAll(bound);
        }
        createAndBindFakeOverrides(current, notOverridden, strategy);
    }

    public static boolean isVisibleForOverride(@NotNull MemberDescriptor overriding, @NotNull MemberDescriptor fromSuper) {
        return !Visibilities.isPrivate(fromSuper.getVisibility()) && Visibilities.isVisibleIgnoringReceiver(fromSuper, overriding);
    }

    private static Collection<CallableMemberDescriptor> extractAndBindOverridesForMember(@NotNull CallableMemberDescriptor fromCurrent, @NotNull Collection<? extends CallableMemberDescriptor> descriptorsFromSuper, @NotNull ClassDescriptor current, @NotNull OverridingStrategy strategy) {
        Collection<CallableMemberDescriptor> bound = new ArrayList<>(descriptorsFromSuper.size());
        Collection<CallableMemberDescriptor> overridden = SmartSet.create();
        for (CallableMemberDescriptor fromSupertype : descriptorsFromSuper) {
            OverrideCompatibilityInfo.Result result = DEFAULT.isOverridableBy(fromSupertype, fromCurrent, current).getResult();
            boolean isVisibleForOverride = isVisibleForOverride(fromCurrent, fromSupertype);
            int i = AnonymousClass9.$SwitchMap$org$jetbrains$kotlin$resolve$OverridingUtil$OverrideCompatibilityInfo$Result[result.ordinal()];
            if (i == 1) {
                if (isVisibleForOverride) {
                    overridden.add(fromSupertype);
                }
                bound.add(fromSupertype);
            } else if (i == 2) {
                if (isVisibleForOverride) {
                    strategy.overrideConflict(fromSupertype, fromCurrent);
                }
                bound.add(fromSupertype);
            }
        }
        strategy.setOverriddenDescriptors(fromCurrent, overridden);
        return bound;
    }

    private static boolean allHasSameContainingDeclaration(@NotNull Collection<CallableMemberDescriptor> notOverridden) {
        if (notOverridden.size() < 2) {
            return true;
        }
        final DeclarationDescriptor containingDeclaration = notOverridden.iterator().next().getContainingDeclaration();
        return CollectionsKt.all(notOverridden, new Function1<CallableMemberDescriptor, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil.4
            @Override // kotlin.jvm.functions.Function1
            public Boolean invoke(CallableMemberDescriptor descriptor) {
                return Boolean.valueOf(descriptor.getContainingDeclaration() == containingDeclaration);
            }
        });
    }

    private static void createAndBindFakeOverrides(@NotNull ClassDescriptor current, @NotNull Collection<CallableMemberDescriptor> notOverridden, @NotNull OverridingStrategy strategy) {
        if (allHasSameContainingDeclaration(notOverridden)) {
            for (CallableMemberDescriptor descriptor : notOverridden) {
                createAndBindFakeOverride(Collections.singleton(descriptor), current, strategy);
            }
            return;
        }
        Queue<CallableMemberDescriptor> fromSuperQueue = new LinkedList<>(notOverridden);
        while (!fromSuperQueue.isEmpty()) {
            CallableMemberDescriptor notOverriddenFromSuper = VisibilityUtilKt.findMemberWithMaxVisibility(fromSuperQueue);
            Collection<CallableMemberDescriptor> overridables = extractMembersOverridableInBothWays(notOverriddenFromSuper, fromSuperQueue, strategy);
            createAndBindFakeOverride(overridables, current, strategy);
        }
    }

    public static boolean isMoreSpecific(@NotNull CallableDescriptor a, @NotNull CallableDescriptor b) {
        KotlinType aReturnType = a.getReturnType();
        KotlinType bReturnType = b.getReturnType();
        if (!isVisibilityMoreSpecific(a, b)) {
            return false;
        }
        if (a instanceof FunctionDescriptor) {
            return isReturnTypeMoreSpecific(a, aReturnType, b, bReturnType);
        }
        if (a instanceof PropertyDescriptor) {
            PropertyDescriptor pa = (PropertyDescriptor) a;
            PropertyDescriptor pb = (PropertyDescriptor) b;
            if (!isAccessorMoreSpecific(pa.getSetter(), pb.getSetter())) {
                return false;
            }
            if (pa.isVar() && pb.isVar()) {
                return DEFAULT.createTypeChecker(a.getTypeParameters(), b.getTypeParameters()).equalTypes(aReturnType, bReturnType);
            }
            return (pa.isVar() || !pb.isVar()) && isReturnTypeMoreSpecific(a, aReturnType, b, bReturnType);
        }
        throw new IllegalArgumentException("Unexpected callable: " + a.getClass());
    }

    private static boolean isVisibilityMoreSpecific(@NotNull DeclarationDescriptorWithVisibility a, @NotNull DeclarationDescriptorWithVisibility b) {
        Integer result = Visibilities.compare(a.getVisibility(), b.getVisibility());
        return result == null || result.intValue() >= 0;
    }

    private static boolean isAccessorMoreSpecific(@Nullable PropertyAccessorDescriptor a, @Nullable PropertyAccessorDescriptor b) {
        if (a == null || b == null) {
            return true;
        }
        return isVisibilityMoreSpecific(a, b);
    }

    private static boolean isMoreSpecificThenAllOf(@NotNull CallableDescriptor candidate, @NotNull Collection<CallableDescriptor> descriptors) {
        for (CallableDescriptor descriptor : descriptors) {
            if (!isMoreSpecific(candidate, descriptor)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isReturnTypeMoreSpecific(@NotNull CallableDescriptor a, @NotNull KotlinType aReturnType, @NotNull CallableDescriptor b, @NotNull KotlinType bReturnType) {
        KotlinTypeChecker typeChecker = DEFAULT.createTypeChecker(a.getTypeParameters(), b.getTypeParameters());
        return typeChecker.isSubtypeOf(aReturnType, bReturnType);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public static <H> H selectMostSpecificMember(@NotNull Collection<H> collection, @NotNull Function1<H, CallableDescriptor> function1) {
        if (collection.size() == 1) {
            return (H) CollectionsKt.first(collection);
        }
        ArrayList arrayList = new ArrayList(2);
        List map = CollectionsKt.map(collection, function1);
        H h = (H) CollectionsKt.first(collection);
        CallableDescriptor callableDescriptor = (CallableDescriptor) function1.invoke(h);
        for (H h2 : collection) {
            CallableDescriptor callableDescriptor2 = (CallableDescriptor) function1.invoke(h2);
            if (isMoreSpecificThenAllOf(callableDescriptor2, map)) {
                arrayList.add(h2);
            }
            if (isMoreSpecific(callableDescriptor2, callableDescriptor) && !isMoreSpecific(callableDescriptor, callableDescriptor2)) {
                h = h2;
            }
        }
        if (arrayList.isEmpty()) {
            return h;
        }
        if (arrayList.size() == 1) {
            return (H) CollectionsKt.first((Iterable) arrayList);
        }
        H h3 = null;
        for (Object obj : arrayList) {
            if (!FlexibleTypesKt.isFlexible(((CallableDescriptor) function1.invoke(obj)).getReturnType())) {
                h3 = obj;
                break;
            }
        }
        if (h3 != null) {
            return h3;
        }
        return (H) CollectionsKt.first((Iterable) arrayList);
    }

    private static void createAndBindFakeOverride(@NotNull Collection<CallableMemberDescriptor> overridables, @NotNull ClassDescriptor current, @NotNull OverridingStrategy strategy) {
        Collection<CallableMemberDescriptor> visibleOverridables = filterVisibleFakeOverrides(current, overridables);
        boolean allInvisible = visibleOverridables.isEmpty();
        Collection<CallableMemberDescriptor> effectiveOverridden = allInvisible ? overridables : visibleOverridables;
        Modality modality = determineModalityForFakeOverride(effectiveOverridden, current);
        Visibility visibility = allInvisible ? Visibilities.INVISIBLE_FAKE : Visibilities.INHERITED;
        CallableMemberDescriptor mostSpecific = (CallableMemberDescriptor) selectMostSpecificMember(effectiveOverridden, new Function1<CallableMemberDescriptor, CallableDescriptor>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil.5
            @Override // kotlin.jvm.functions.Function1
            public CallableMemberDescriptor invoke(CallableMemberDescriptor descriptor) {
                return descriptor;
            }
        });
        CallableMemberDescriptor fakeOverride = mostSpecific.copy(current, modality, visibility, CallableMemberDescriptor.Kind.FAKE_OVERRIDE, false);
        strategy.setOverriddenDescriptors(fakeOverride, effectiveOverridden);
        strategy.addFakeOverride(fakeOverride);
    }

    @NotNull
    private static Modality determineModalityForFakeOverride(@NotNull Collection<CallableMemberDescriptor> descriptors, @NotNull ClassDescriptor current) {
        boolean hasOpen = false;
        boolean hasAbstract = false;
        Iterator<CallableMemberDescriptor> it = descriptors.iterator();
        while (true) {
            if (it.hasNext()) {
                CallableMemberDescriptor descriptor = it.next();
                int i = AnonymousClass9.$SwitchMap$org$jetbrains$kotlin$descriptors$Modality[descriptor.getModality().ordinal()];
                if (i == 1) {
                    return Modality.FINAL;
                }
                if (i == 2) {
                    throw new IllegalStateException("Member cannot have SEALED modality: " + descriptor);
                }
                if (i == 3) {
                    hasOpen = true;
                } else if (i == 4) {
                    hasAbstract = true;
                }
            } else {
                boolean transformAbstractToClassModality = (!current.isExpect() || current.getModality() == Modality.ABSTRACT || current.getModality() == Modality.SEALED) ? false : true;
                if (hasOpen && !hasAbstract) {
                    return Modality.OPEN;
                }
                if (!hasOpen && hasAbstract) {
                    return transformAbstractToClassModality ? current.getModality() : Modality.ABSTRACT;
                }
                Set<CallableMemberDescriptor> allOverriddenDeclarations = new HashSet<>();
                Iterator<CallableMemberDescriptor> it2 = descriptors.iterator();
                while (it2.hasNext()) {
                    allOverriddenDeclarations.addAll(getOverriddenDeclarations(it2.next()));
                }
                return getMinimalModality(filterOutOverridden(allOverriddenDeclarations), transformAbstractToClassModality, current.getModality());
            }
        }
    }

    /* JADX INFO: renamed from: kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil$9, reason: invalid class name */
    static /* synthetic */ class AnonymousClass9 {
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$descriptors$Modality = new int[Modality.values().length];
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$resolve$ExternalOverridabilityCondition$Result;
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$resolve$OverridingUtil$OverrideCompatibilityInfo$Result;

        static {
            try {
                $SwitchMap$org$jetbrains$kotlin$descriptors$Modality[Modality.FINAL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$descriptors$Modality[Modality.SEALED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$descriptors$Modality[Modality.OPEN.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$descriptors$Modality[Modality.ABSTRACT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            $SwitchMap$org$jetbrains$kotlin$resolve$OverridingUtil$OverrideCompatibilityInfo$Result = new int[OverrideCompatibilityInfo.Result.values().length];
            try {
                $SwitchMap$org$jetbrains$kotlin$resolve$OverridingUtil$OverrideCompatibilityInfo$Result[OverrideCompatibilityInfo.Result.OVERRIDABLE.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$resolve$OverridingUtil$OverrideCompatibilityInfo$Result[OverrideCompatibilityInfo.Result.CONFLICT.ordinal()] = 2;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$resolve$OverridingUtil$OverrideCompatibilityInfo$Result[OverrideCompatibilityInfo.Result.INCOMPATIBLE.ordinal()] = 3;
            } catch (NoSuchFieldError e7) {
            }
            $SwitchMap$org$jetbrains$kotlin$resolve$ExternalOverridabilityCondition$Result = new int[ExternalOverridabilityCondition.Result.values().length];
            try {
                $SwitchMap$org$jetbrains$kotlin$resolve$ExternalOverridabilityCondition$Result[ExternalOverridabilityCondition.Result.OVERRIDABLE.ordinal()] = 1;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$resolve$ExternalOverridabilityCondition$Result[ExternalOverridabilityCondition.Result.CONFLICT.ordinal()] = 2;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$resolve$ExternalOverridabilityCondition$Result[ExternalOverridabilityCondition.Result.INCOMPATIBLE.ordinal()] = 3;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$resolve$ExternalOverridabilityCondition$Result[ExternalOverridabilityCondition.Result.UNKNOWN.ordinal()] = 4;
            } catch (NoSuchFieldError e11) {
            }
        }
    }

    @NotNull
    private static Modality getMinimalModality(@NotNull Collection<CallableMemberDescriptor> descriptors, boolean transformAbstractToClassModality, @NotNull Modality classModality) {
        Modality result = Modality.ABSTRACT;
        for (CallableMemberDescriptor descriptor : descriptors) {
            Modality effectiveModality = (transformAbstractToClassModality && descriptor.getModality() == Modality.ABSTRACT) ? classModality : descriptor.getModality();
            if (effectiveModality.compareTo(result) < 0) {
                result = effectiveModality;
            }
        }
        return result;
    }

    @NotNull
    private static Collection<CallableMemberDescriptor> filterVisibleFakeOverrides(@NotNull final ClassDescriptor current, @NotNull Collection<CallableMemberDescriptor> toFilter) {
        return CollectionsKt.filter(toFilter, new Function1<CallableMemberDescriptor, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil.6
            @Override // kotlin.jvm.functions.Function1
            public Boolean invoke(CallableMemberDescriptor descriptor) {
                return Boolean.valueOf(!Visibilities.isPrivate(descriptor.getVisibility()) && Visibilities.isVisibleIgnoringReceiver(descriptor, current));
            }
        });
    }

    @NotNull
    public static <H> Collection<H> extractMembersOverridableInBothWays(@NotNull H overrider, @NotNull Collection<H> extractFrom, @NotNull Function1<H, CallableDescriptor> descriptorByHandle, @NotNull Function1<H, Unit> onConflict) {
        Collection<H> overridable = new ArrayList<>();
        overridable.add(overrider);
        CallableDescriptor overriderDescriptor = descriptorByHandle.invoke(overrider);
        Iterator<H> iterator = extractFrom.iterator();
        while (iterator.hasNext()) {
            H candidate = iterator.next();
            CallableDescriptor candidateDescriptor = descriptorByHandle.invoke(candidate);
            if (overrider == candidate) {
                iterator.remove();
            } else {
                OverrideCompatibilityInfo.Result finalResult = getBothWaysOverridability(overriderDescriptor, candidateDescriptor);
                if (finalResult == OverrideCompatibilityInfo.Result.OVERRIDABLE) {
                    overridable.add(candidate);
                    iterator.remove();
                } else if (finalResult == OverrideCompatibilityInfo.Result.CONFLICT) {
                    onConflict.invoke(candidate);
                    iterator.remove();
                }
            }
        }
        return overridable;
    }

    @Nullable
    public static OverrideCompatibilityInfo.Result getBothWaysOverridability(CallableDescriptor overriderDescriptor, CallableDescriptor candidateDescriptor) {
        OverrideCompatibilityInfo.Result result1 = DEFAULT.isOverridableBy(candidateDescriptor, overriderDescriptor, null).getResult();
        OverrideCompatibilityInfo.Result result2 = DEFAULT.isOverridableBy(overriderDescriptor, candidateDescriptor, null).getResult();
        if (result1 == OverrideCompatibilityInfo.Result.OVERRIDABLE && result2 == OverrideCompatibilityInfo.Result.OVERRIDABLE) {
            return OverrideCompatibilityInfo.Result.OVERRIDABLE;
        }
        return (result1 == OverrideCompatibilityInfo.Result.CONFLICT || result2 == OverrideCompatibilityInfo.Result.CONFLICT) ? OverrideCompatibilityInfo.Result.CONFLICT : OverrideCompatibilityInfo.Result.INCOMPATIBLE;
    }

    @NotNull
    private static Collection<CallableMemberDescriptor> extractMembersOverridableInBothWays(@NotNull final CallableMemberDescriptor overrider, @NotNull Queue<CallableMemberDescriptor> extractFrom, @NotNull final OverridingStrategy strategy) {
        return extractMembersOverridableInBothWays(overrider, extractFrom, new Function1<CallableMemberDescriptor, CallableDescriptor>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil.7
            @Override // kotlin.jvm.functions.Function1
            public CallableDescriptor invoke(CallableMemberDescriptor descriptor) {
                return descriptor;
            }
        }, new Function1<CallableMemberDescriptor, Unit>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil.8
            @Override // kotlin.jvm.functions.Function1
            public Unit invoke(CallableMemberDescriptor descriptor) {
                strategy.inheritanceConflict(overrider, descriptor);
                return Unit.INSTANCE;
            }
        });
    }

    public static void resolveUnknownVisibilityForMember(@NotNull CallableMemberDescriptor memberDescriptor, @Nullable Function1<CallableMemberDescriptor, Unit> cannotInferVisibility) {
        Visibility visibilityToInherit;
        for (CallableMemberDescriptor descriptor : memberDescriptor.getOverriddenDescriptors()) {
            if (descriptor.getVisibility() == Visibilities.INHERITED) {
                resolveUnknownVisibilityForMember(descriptor, cannotInferVisibility);
            }
        }
        if (memberDescriptor.getVisibility() != Visibilities.INHERITED) {
            return;
        }
        Visibility maxVisibility = computeVisibilityToInherit(memberDescriptor);
        if (maxVisibility == null) {
            if (cannotInferVisibility != null) {
                cannotInferVisibility.invoke(memberDescriptor);
            }
            visibilityToInherit = Visibilities.PUBLIC;
        } else {
            visibilityToInherit = maxVisibility;
        }
        if (memberDescriptor instanceof PropertyDescriptorImpl) {
            ((PropertyDescriptorImpl) memberDescriptor).setVisibility(visibilityToInherit);
            for (PropertyAccessorDescriptor accessor : ((PropertyDescriptor) memberDescriptor).getAccessors()) {
                resolveUnknownVisibilityForMember(accessor, maxVisibility == null ? null : cannotInferVisibility);
            }
            return;
        }
        if (memberDescriptor instanceof FunctionDescriptorImpl) {
            ((FunctionDescriptorImpl) memberDescriptor).setVisibility(visibilityToInherit);
            return;
        }
        PropertyAccessorDescriptorImpl propertyAccessorDescriptor = (PropertyAccessorDescriptorImpl) memberDescriptor;
        propertyAccessorDescriptor.setVisibility(visibilityToInherit);
        if (visibilityToInherit != propertyAccessorDescriptor.getCorrespondingProperty().getVisibility()) {
            propertyAccessorDescriptor.setDefault(false);
        }
    }

    @Nullable
    private static Visibility computeVisibilityToInherit(@NotNull CallableMemberDescriptor memberDescriptor) {
        Collection<? extends CallableMemberDescriptor> overriddenDescriptors = memberDescriptor.getOverriddenDescriptors();
        Visibility maxVisibility = findMaxVisibility(overriddenDescriptors);
        if (maxVisibility == null) {
            return null;
        }
        if (memberDescriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
            for (CallableMemberDescriptor overridden : overriddenDescriptors) {
                if (overridden.getModality() != Modality.ABSTRACT && !overridden.getVisibility().equals(maxVisibility)) {
                    return null;
                }
            }
            return maxVisibility;
        }
        return maxVisibility.normalize();
    }

    @Nullable
    public static Visibility findMaxVisibility(@NotNull Collection<? extends CallableMemberDescriptor> descriptors) {
        if (descriptors.isEmpty()) {
            return Visibilities.DEFAULT_VISIBILITY;
        }
        Visibility maxVisibility = null;
        for (CallableMemberDescriptor descriptor : descriptors) {
            Visibility visibility = descriptor.getVisibility();
            if (maxVisibility == null) {
                maxVisibility = visibility;
            } else {
                Integer compareResult = Visibilities.compare(visibility, maxVisibility);
                if (compareResult == null) {
                    maxVisibility = null;
                } else if (compareResult.intValue() > 0) {
                    maxVisibility = visibility;
                }
            }
        }
        if (maxVisibility == null) {
            return null;
        }
        for (CallableMemberDescriptor descriptor2 : descriptors) {
            Integer compareResult2 = Visibilities.compare(maxVisibility, descriptor2.getVisibility());
            if (compareResult2 == null || compareResult2.intValue() < 0) {
                return null;
            }
        }
        return maxVisibility;
    }

    public static class OverrideCompatibilityInfo {
        private static final OverrideCompatibilityInfo SUCCESS = new OverrideCompatibilityInfo(Result.OVERRIDABLE, "SUCCESS");
        private final String debugMessage;
        private final Result overridable;

        public enum Result {
            OVERRIDABLE,
            INCOMPATIBLE,
            CONFLICT
        }

        @NotNull
        public static OverrideCompatibilityInfo success() {
            return SUCCESS;
        }

        @NotNull
        public static OverrideCompatibilityInfo incompatible(@NotNull String debugMessage) {
            return new OverrideCompatibilityInfo(Result.INCOMPATIBLE, debugMessage);
        }

        @NotNull
        public static OverrideCompatibilityInfo conflict(@NotNull String debugMessage) {
            return new OverrideCompatibilityInfo(Result.CONFLICT, debugMessage);
        }

        public OverrideCompatibilityInfo(@NotNull Result success, @NotNull String debugMessage) {
            this.overridable = success;
            this.debugMessage = debugMessage;
        }

        @NotNull
        public Result getResult() {
            return this.overridable;
        }
    }
}
