package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: descriptorUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class DescriptorUtilKt {
    @Nullable
    public static final ClassDescriptor resolveClassByFqName(@NotNull ModuleDescriptor receiver$0, @NotNull FqName fqName, @NotNull LookupLocation lookupLocation) {
        ClassifierDescriptor contributedClassifier;
        MemberScope unsubstitutedInnerClassesScope;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(fqName, "fqName");
        Intrinsics.checkParameterIsNotNull(lookupLocation, "lookupLocation");
        if (fqName.isRoot()) {
            return null;
        }
        FqName fqNameParent = fqName.parent();
        Intrinsics.checkExpressionValueIsNotNull(fqNameParent, "fqName.parent()");
        MemberScope memberScope = receiver$0.getPackage(fqNameParent).getMemberScope();
        Name nameShortName = fqName.shortName();
        Intrinsics.checkExpressionValueIsNotNull(nameShortName, "fqName.shortName()");
        ClassifierDescriptor contributedClassifier2 = memberScope.mo382getContributedClassifier(nameShortName, lookupLocation);
        if (!(contributedClassifier2 instanceof ClassDescriptor)) {
            contributedClassifier2 = null;
        }
        ClassDescriptor it = (ClassDescriptor) contributedClassifier2;
        if (it != null) {
            return it;
        }
        FqName fqNameParent2 = fqName.parent();
        Intrinsics.checkExpressionValueIsNotNull(fqNameParent2, "fqName.parent()");
        ClassDescriptor classDescriptorResolveClassByFqName = resolveClassByFqName(receiver$0, fqNameParent2, lookupLocation);
        if (classDescriptorResolveClassByFqName == null || (unsubstitutedInnerClassesScope = classDescriptorResolveClassByFqName.getUnsubstitutedInnerClassesScope()) == null) {
            contributedClassifier = null;
        } else {
            Name nameShortName2 = fqName.shortName();
            Intrinsics.checkExpressionValueIsNotNull(nameShortName2, "fqName.shortName()");
            contributedClassifier = unsubstitutedInnerClassesScope.mo382getContributedClassifier(nameShortName2, lookupLocation);
        }
        if (!(contributedClassifier instanceof ClassDescriptor)) {
            contributedClassifier = null;
        }
        return (ClassDescriptor) contributedClassifier;
    }
}
