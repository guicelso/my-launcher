package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.util.collectionUtils.ScopeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: ChainedMemberScope.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class ChainedMemberScope implements MemberScope {
    public static final Companion Companion = new Companion(null);

    @NotNull
    private final String debugName;
    private final List<MemberScope> scopes;

    /* JADX WARN: Multi-variable type inference failed */
    public ChainedMemberScope(@NotNull String debugName, @NotNull List<? extends MemberScope> scopes) {
        Intrinsics.checkParameterIsNotNull(debugName, "debugName");
        Intrinsics.checkParameterIsNotNull(scopes, "scopes");
        this.debugName = debugName;
        this.scopes = scopes;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    @Nullable
    /* JADX INFO: renamed from: getContributedClassifier */
    public ClassifierDescriptor mo382getContributedClassifier(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(location, "location");
        ClassifierDescriptor result$iv = (ClassifierDescriptor) null;
        for (Object scope$iv : this.scopes) {
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

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    @NotNull
    public Collection<PropertyDescriptor> getContributedVariables(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(location, "location");
        List<MemberScope> list = this.scopes;
        if (list.isEmpty()) {
            Collection result$iv = SetsKt.emptySet();
            return result$iv;
        }
        Collection<PropertyDescriptor> collectionConcat = (Collection) null;
        for (Object scope$iv : list) {
            MemberScope it = (MemberScope) scope$iv;
            collectionConcat = ScopeUtilsKt.concat(collectionConcat, it.getContributedVariables(name, location));
        }
        if (collectionConcat != null) {
            return collectionConcat;
        }
        Collection result$iv2 = SetsKt.emptySet();
        return result$iv2;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope, kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    @NotNull
    public Collection<SimpleFunctionDescriptor> getContributedFunctions(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(location, "location");
        List<MemberScope> list = this.scopes;
        if (list.isEmpty()) {
            Collection result$iv = SetsKt.emptySet();
            return result$iv;
        }
        Collection<SimpleFunctionDescriptor> collectionConcat = (Collection) null;
        for (Object scope$iv : list) {
            MemberScope it = (MemberScope) scope$iv;
            collectionConcat = ScopeUtilsKt.concat(collectionConcat, it.getContributedFunctions(name, location));
        }
        if (collectionConcat != null) {
            return collectionConcat;
        }
        Collection result$iv2 = SetsKt.emptySet();
        return result$iv2;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    @NotNull
    public Collection<DeclarationDescriptor> getContributedDescriptors(@NotNull DescriptorKindFilter kindFilter, @NotNull Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkParameterIsNotNull(kindFilter, "kindFilter");
        Intrinsics.checkParameterIsNotNull(nameFilter, "nameFilter");
        List<MemberScope> list = this.scopes;
        if (list.isEmpty()) {
            Collection result$iv = SetsKt.emptySet();
            return result$iv;
        }
        Collection<DeclarationDescriptor> collectionConcat = (Collection) null;
        for (Object scope$iv : list) {
            MemberScope it = (MemberScope) scope$iv;
            collectionConcat = ScopeUtilsKt.concat(collectionConcat, it.getContributedDescriptors(kindFilter, nameFilter));
        }
        if (collectionConcat != null) {
            return collectionConcat;
        }
        Collection result$iv2 = SetsKt.emptySet();
        return result$iv2;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    @NotNull
    public Set<Name> getFunctionNames() {
        Iterable $receiver$iv = this.scopes;
        Collection destination$iv = (Set) new LinkedHashSet();
        for (Object element$iv : $receiver$iv) {
            MemberScope it = (MemberScope) element$iv;
            Iterable list$iv = it.getFunctionNames();
            CollectionsKt.addAll(destination$iv, list$iv);
        }
        return (Set) destination$iv;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    @NotNull
    public Set<Name> getVariableNames() {
        Iterable $receiver$iv = this.scopes;
        Collection destination$iv = (Set) new LinkedHashSet();
        for (Object element$iv : $receiver$iv) {
            MemberScope it = (MemberScope) element$iv;
            Iterable list$iv = it.getVariableNames();
            CollectionsKt.addAll(destination$iv, list$iv);
        }
        return (Set) destination$iv;
    }

    @NotNull
    public String toString() {
        return this.debugName;
    }

    /* JADX INFO: compiled from: ChainedMemberScope.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
