package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.UtilsKt;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaPackage;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import kotlin.reflect.jvm.internal.impl.util.collectionUtils.ScopeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: JvmPackageScope.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class JvmPackageScope implements MemberScope {
    static final /* synthetic */ KProperty[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmPackageScope.class), "kotlinScopes", "getKotlinScopes()Ljava/util/List;"))};
    private final LazyJavaResolverContext c;

    @NotNull
    private final LazyJavaPackageScope javaScope;
    private final NotNullLazyValue kotlinScopes$delegate;
    private final LazyJavaPackageFragment packageFragment;

    private final List<MemberScope> getKotlinScopes() {
        return (List) StorageKt.getValue(this.kotlinScopes$delegate, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    public JvmPackageScope(@NotNull LazyJavaResolverContext c, @NotNull JavaPackage jPackage, @NotNull LazyJavaPackageFragment packageFragment) {
        Intrinsics.checkParameterIsNotNull(c, "c");
        Intrinsics.checkParameterIsNotNull(jPackage, "jPackage");
        Intrinsics.checkParameterIsNotNull(packageFragment, "packageFragment");
        this.c = c;
        this.packageFragment = packageFragment;
        this.javaScope = new LazyJavaPackageScope(this.c, jPackage, this.packageFragment);
        this.kotlinScopes$delegate = this.c.getStorageManager().createLazyValue(new Function0<List<? extends MemberScope>>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.JvmPackageScope$kotlinScopes$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final List<? extends MemberScope> invoke() {
                Iterable $receiver$iv = this.this$0.packageFragment.getBinaryClasses$descriptors_jvm().values();
                Collection destination$iv$iv = new ArrayList();
                for (Object element$iv$iv$iv : $receiver$iv) {
                    KotlinJvmBinaryClass partClass = (KotlinJvmBinaryClass) element$iv$iv$iv;
                    MemberScope memberScopeCreateKotlinPackagePartScope = this.this$0.c.getComponents().getDeserializedDescriptorResolver().createKotlinPackagePartScope(this.this$0.packageFragment, partClass);
                    if (memberScopeCreateKotlinPackagePartScope != null) {
                        destination$iv$iv.add(memberScopeCreateKotlinPackagePartScope);
                    }
                }
                Iterable $receiver$iv2 = (List) destination$iv$iv;
                return CollectionsKt.toList($receiver$iv2);
            }
        });
    }

    @NotNull
    public final LazyJavaPackageScope getJavaScope$descriptors_jvm() {
        return this.javaScope;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    @Nullable
    /* JADX INFO: renamed from: getContributedClassifier */
    public ClassifierDescriptor mo382getContributedClassifier(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(location, "location");
        recordLookup(name, location);
        ClassDescriptor javaClassifier = this.javaScope.mo382getContributedClassifier(name, location);
        if (javaClassifier != null) {
            return javaClassifier;
        }
        ClassifierDescriptor result$iv = (ClassifierDescriptor) null;
        for (Object scope$iv : getKotlinScopes()) {
            MemberScope it = (MemberScope) scope$iv;
            ClassifierDescriptor newResult$iv = it.mo382getContributedClassifier(name, location);
            if (newResult$iv != null) {
                if ((newResult$iv instanceof ClassifierDescriptorWithTypeParameters) && ((ClassifierDescriptorWithTypeParameters) newResult$iv).isExpect()) {
                    if (result$iv == null) {
                        result$iv = newResult$iv;
                    }
                } else {
                    return newResult$iv;
                }
            }
        }
        return result$iv;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    @NotNull
    public Collection<PropertyDescriptor> getContributedVariables(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(location, "location");
        recordLookup(name, location);
        MemberScope firstScope$iv = this.javaScope;
        List<MemberScope> kotlinScopes = getKotlinScopes();
        MemberScope it = firstScope$iv;
        Collection contributedVariables = it.getContributedVariables(name, location);
        for (Object scope$iv : kotlinScopes) {
            MemberScope it2 = (MemberScope) scope$iv;
            contributedVariables = ScopeUtilsKt.concat(contributedVariables, it2.getContributedVariables(name, location));
        }
        return contributedVariables != null ? contributedVariables : SetsKt.emptySet();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope, kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    @NotNull
    public Collection<SimpleFunctionDescriptor> getContributedFunctions(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(location, "location");
        recordLookup(name, location);
        MemberScope firstScope$iv = this.javaScope;
        List<MemberScope> kotlinScopes = getKotlinScopes();
        MemberScope it = firstScope$iv;
        Collection contributedFunctions = it.getContributedFunctions(name, location);
        for (Object scope$iv : kotlinScopes) {
            MemberScope it2 = (MemberScope) scope$iv;
            contributedFunctions = ScopeUtilsKt.concat(contributedFunctions, it2.getContributedFunctions(name, location));
        }
        return contributedFunctions != null ? contributedFunctions : SetsKt.emptySet();
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    @NotNull
    public Collection<DeclarationDescriptor> getContributedDescriptors(@NotNull DescriptorKindFilter kindFilter, @NotNull Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkParameterIsNotNull(kindFilter, "kindFilter");
        Intrinsics.checkParameterIsNotNull(nameFilter, "nameFilter");
        MemberScope firstScope$iv = this.javaScope;
        List<MemberScope> kotlinScopes = getKotlinScopes();
        MemberScope it = firstScope$iv;
        Collection<DeclarationDescriptor> contributedDescriptors = it.getContributedDescriptors(kindFilter, nameFilter);
        for (Object scope$iv : kotlinScopes) {
            MemberScope it2 = (MemberScope) scope$iv;
            contributedDescriptors = ScopeUtilsKt.concat(contributedDescriptors, it2.getContributedDescriptors(kindFilter, nameFilter));
        }
        return contributedDescriptors != null ? contributedDescriptors : SetsKt.emptySet();
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    @NotNull
    public Set<Name> getFunctionNames() {
        Iterable $receiver$iv = getKotlinScopes();
        Collection destination$iv = (Set) new LinkedHashSet();
        for (Object element$iv : $receiver$iv) {
            MemberScope it = (MemberScope) element$iv;
            Iterable list$iv = it.getFunctionNames();
            CollectionsKt.addAll(destination$iv, list$iv);
        }
        Set $receiver = (Set) destination$iv;
        $receiver.addAll(this.javaScope.getFunctionNames());
        return (Set) destination$iv;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    @NotNull
    public Set<Name> getVariableNames() {
        Iterable $receiver$iv = getKotlinScopes();
        Collection destination$iv = (Set) new LinkedHashSet();
        for (Object element$iv : $receiver$iv) {
            MemberScope it = (MemberScope) element$iv;
            Iterable list$iv = it.getVariableNames();
            CollectionsKt.addAll(destination$iv, list$iv);
        }
        Set $receiver = (Set) destination$iv;
        $receiver.addAll(this.javaScope.getVariableNames());
        return (Set) destination$iv;
    }

    public void recordLookup(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(location, "location");
        UtilsKt.record(this.c.getComponents().getLookupTracker(), location, this.packageFragment, name);
    }
}
