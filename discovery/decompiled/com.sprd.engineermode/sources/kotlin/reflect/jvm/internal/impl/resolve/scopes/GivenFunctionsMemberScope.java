package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.NonReportingOverrideStrategy;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: GivenFunctionsMemberScope.kt */
/* JADX INFO: loaded from: classes4.dex */
public abstract class GivenFunctionsMemberScope extends MemberScopeImpl {
    static final /* synthetic */ KProperty[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(GivenFunctionsMemberScope.class), "allDescriptors", "getAllDescriptors()Ljava/util/List;"))};
    private final NotNullLazyValue allDescriptors$delegate;

    @NotNull
    private final ClassDescriptor containingClass;

    private final List<DeclarationDescriptor> getAllDescriptors() {
        return (List) StorageKt.getValue(this.allDescriptors$delegate, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    @NotNull
    protected abstract List<FunctionDescriptor> computeDeclaredFunctions();

    public GivenFunctionsMemberScope(@NotNull StorageManager storageManager, @NotNull ClassDescriptor containingClass) {
        Intrinsics.checkParameterIsNotNull(storageManager, "storageManager");
        Intrinsics.checkParameterIsNotNull(containingClass, "containingClass");
        this.containingClass = containingClass;
        this.allDescriptors$delegate = storageManager.createLazyValue(new Function0<List<? extends DeclarationDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.scopes.GivenFunctionsMemberScope$allDescriptors$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final List<? extends DeclarationDescriptor> invoke() {
                List<FunctionDescriptor> listComputeDeclaredFunctions = this.this$0.computeDeclaredFunctions();
                return CollectionsKt.plus((Collection) listComputeDeclaredFunctions, (Iterable) this.this$0.createFakeOverrides(listComputeDeclaredFunctions));
            }
        });
    }

    @NotNull
    protected final ClassDescriptor getContainingClass() {
        return this.containingClass;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl, kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    @NotNull
    public Collection<DeclarationDescriptor> getContributedDescriptors(@NotNull DescriptorKindFilter kindFilter, @NotNull Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkParameterIsNotNull(kindFilter, "kindFilter");
        Intrinsics.checkParameterIsNotNull(nameFilter, "nameFilter");
        return !kindFilter.acceptsKinds(DescriptorKindFilter.CALLABLES.getKindMask()) ? CollectionsKt.emptyList() : getAllDescriptors();
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl, kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope, kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    @NotNull
    public Collection<SimpleFunctionDescriptor> getContributedFunctions(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(location, "location");
        Iterable $receiver$iv = getAllDescriptors();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $receiver$iv) {
            if (element$iv$iv instanceof SimpleFunctionDescriptor) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $receiver$iv2 = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv2 : $receiver$iv2) {
            SimpleFunctionDescriptor it = (SimpleFunctionDescriptor) element$iv$iv2;
            if (Intrinsics.areEqual(it.getName(), name)) {
                destination$iv$iv2.add(element$iv$iv2);
            }
        }
        Iterable $receiver$iv3 = (List) destination$iv$iv2;
        return (Collection) $receiver$iv3;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl, kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    @NotNull
    public Collection<PropertyDescriptor> getContributedVariables(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(location, "location");
        Iterable $receiver$iv = getAllDescriptors();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $receiver$iv) {
            if (element$iv$iv instanceof PropertyDescriptor) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $receiver$iv2 = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv2 : $receiver$iv2) {
            PropertyDescriptor it = (PropertyDescriptor) element$iv$iv2;
            if (Intrinsics.areEqual(it.getName(), name)) {
                destination$iv$iv2.add(element$iv$iv2);
            }
        }
        Iterable $receiver$iv3 = (List) destination$iv$iv2;
        return (Collection) $receiver$iv3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<DeclarationDescriptor> createFakeOverrides(List<? extends FunctionDescriptor> list) {
        Iterator it;
        int i;
        List listEmptyList;
        Object answer$iv$iv$iv;
        Object answer$iv$iv$iv2;
        final ArrayList result = new ArrayList(3);
        TypeConstructor typeConstructor = this.containingClass.getTypeConstructor();
        Intrinsics.checkExpressionValueIsNotNull(typeConstructor, "containingClass.typeConstructor");
        Iterable supertypes = typeConstructor.getSupertypes();
        Intrinsics.checkExpressionValueIsNotNull(supertypes, "containingClass.typeConstructor.supertypes");
        Iterable<KotlinType> $receiver$iv = supertypes;
        Collection destination$iv$iv = new ArrayList();
        for (KotlinType it2 : $receiver$iv) {
            CollectionsKt.addAll(destination$iv$iv, ResolutionScope.DefaultImpls.getContributedDescriptors$default(it2.getMemberScope(), null, null, 3, null));
        }
        Iterable $receiver$iv2 = (List) destination$iv$iv;
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv : $receiver$iv2) {
            if (element$iv$iv instanceof CallableMemberDescriptor) {
                destination$iv$iv2.add(element$iv$iv);
            }
        }
        List list$iv$iv = (List) destination$iv$iv2;
        List $receiver$iv3 = list$iv$iv;
        Map destination$iv$iv3 = new LinkedHashMap();
        for (Object element$iv$iv2 : $receiver$iv3) {
            CallableMemberDescriptor it3 = (CallableMemberDescriptor) element$iv$iv2;
            Name name = it3.getName();
            Object value$iv$iv$iv = destination$iv$iv3.get(name);
            if (value$iv$iv$iv == null) {
                Object answer$iv$iv$iv3 = new ArrayList();
                answer$iv$iv$iv2 = answer$iv$iv$iv3;
                destination$iv$iv3.put(name, answer$iv$iv$iv2);
            } else {
                answer$iv$iv$iv2 = value$iv$iv$iv;
            }
            ((List) answer$iv$iv$iv2).add(element$iv$iv2);
            list$iv$iv = list$iv$iv;
        }
        Iterator it4 = destination$iv$iv3.entrySet().iterator();
        boolean isFunction = false;
        int $i$f$groupByTo = 0;
        int $i$f$getOrPut = 0;
        while (it4.hasNext()) {
            Map.Entry entry = (Map.Entry) it4.next();
            Name name2 = (Name) entry.getKey();
            Iterable group = (List) entry.getValue();
            Iterable $receiver$iv4 = group;
            Map destination$iv$iv4 = new LinkedHashMap();
            Iterable $receiver$iv$iv = $receiver$iv4;
            for (Object element$iv$iv3 : $receiver$iv$iv) {
                Iterator it5 = it4;
                int $i$f$groupByTo2 = $i$f$groupByTo;
                CallableMemberDescriptor it6 = (CallableMemberDescriptor) element$iv$iv3;
                Boolean boolValueOf = Boolean.valueOf(it6 instanceof FunctionDescriptor);
                int i2 = $i$f$getOrPut;
                Object value$iv$iv$iv2 = destination$iv$iv4.get(boolValueOf);
                if (value$iv$iv$iv2 == null) {
                    Object answer$iv$iv$iv4 = new ArrayList();
                    answer$iv$iv$iv = answer$iv$iv$iv4;
                    destination$iv$iv4.put(boolValueOf, answer$iv$iv$iv);
                } else {
                    answer$iv$iv$iv = value$iv$iv$iv2;
                }
                ((List) answer$iv$iv$iv).add(element$iv$iv3);
                it4 = it5;
                $i$f$groupByTo = $i$f$groupByTo2;
                $i$f$getOrPut = i2;
                isFunction = isFunction;
            }
            Iterator it7 = it4;
            int i3 = $i$f$groupByTo;
            Iterator it8 = destination$iv$iv4.entrySet().iterator();
            while (it8.hasNext()) {
                Map.Entry entry2 = (Map.Entry) it8.next();
                isFunction = ((Boolean) entry2.getKey()).booleanValue();
                $receiver$iv$iv = (List) entry2.getValue();
                List list2 = (Collection) $receiver$iv$iv;
                if (isFunction) {
                    List<? extends FunctionDescriptor> $receiver$iv5 = list;
                    Collection destination$iv$iv5 = new ArrayList();
                    for (Object element$iv$iv4 : $receiver$iv5) {
                        Iterator it9 = it8;
                        FunctionDescriptor it10 = (FunctionDescriptor) element$iv$iv4;
                        int i4 = $i$f$getOrPut;
                        if (Intrinsics.areEqual(it10.getName(), name2)) {
                            destination$iv$iv5.add(element$iv$iv4);
                        }
                        it8 = it9;
                        $i$f$getOrPut = i4;
                    }
                    it = it8;
                    i = $i$f$getOrPut;
                    listEmptyList = (List) destination$iv$iv5;
                } else {
                    it = it8;
                    i = $i$f$getOrPut;
                    listEmptyList = CollectionsKt.emptyList();
                }
                OverridingUtil.generateOverridesInFunctionGroup(name2, list2, listEmptyList, this.containingClass, new NonReportingOverrideStrategy() { // from class: kotlin.reflect.jvm.internal.impl.resolve.scopes.GivenFunctionsMemberScope.createFakeOverrides.4
                    @Override // kotlin.reflect.jvm.internal.impl.resolve.OverridingStrategy
                    public void addFakeOverride(@NotNull CallableMemberDescriptor fakeOverride) {
                        Intrinsics.checkParameterIsNotNull(fakeOverride, "fakeOverride");
                        OverridingUtil.resolveUnknownVisibilityForMember(fakeOverride, null);
                        result.add(fakeOverride);
                    }

                    @Override // kotlin.reflect.jvm.internal.impl.resolve.NonReportingOverrideStrategy
                    protected void conflict(@NotNull CallableMemberDescriptor fromSuper, @NotNull CallableMemberDescriptor fromCurrent) {
                        Intrinsics.checkParameterIsNotNull(fromSuper, "fromSuper");
                        Intrinsics.checkParameterIsNotNull(fromCurrent, "fromCurrent");
                        throw new IllegalStateException(("Conflict in scope of " + GivenFunctionsMemberScope.this.getContainingClass() + ": " + fromSuper + " vs " + fromCurrent).toString());
                    }
                });
                it8 = it;
                $i$f$getOrPut = i;
            }
            it4 = it7;
            $i$f$groupByTo = i3;
        }
        return kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.compact(result);
    }
}
