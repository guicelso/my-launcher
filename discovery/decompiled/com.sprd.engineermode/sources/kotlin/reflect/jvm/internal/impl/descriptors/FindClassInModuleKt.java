package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: findClassInModule.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class FindClassInModuleKt {
    @Nullable
    public static final ClassDescriptor findClassAcrossModuleDependencies(@NotNull ModuleDescriptor receiver$0, @NotNull ClassId classId) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(classId, "classId");
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkExpressionValueIsNotNull(packageFqName, "classId.packageFqName");
        PackageViewDescriptor packageViewDescriptor = receiver$0.getPackage(packageFqName);
        List<Name> listPathSegments = classId.getRelativeClassName().pathSegments();
        Intrinsics.checkExpressionValueIsNotNull(listPathSegments, "classId.relativeClassName.pathSegments()");
        MemberScope memberScope = packageViewDescriptor.getMemberScope();
        Object objFirst = CollectionsKt.first((List<? extends Object>) listPathSegments);
        Intrinsics.checkExpressionValueIsNotNull(objFirst, "segments.first()");
        ClassifierDescriptor contributedClassifier = memberScope.mo382getContributedClassifier((Name) objFirst, NoLookupLocation.FROM_DESERIALIZATION);
        if (!(contributedClassifier instanceof ClassDescriptor)) {
            contributedClassifier = null;
        }
        ClassDescriptor topLevelClass = (ClassDescriptor) contributedClassifier;
        if (topLevelClass == null) {
            return null;
        }
        ClassDescriptor result = topLevelClass;
        for (Name name : listPathSegments.subList(1, listPathSegments.size())) {
            MemberScope unsubstitutedInnerClassesScope = result.getUnsubstitutedInnerClassesScope();
            Intrinsics.checkExpressionValueIsNotNull(name, "name");
            ClassifierDescriptor contributedClassifier2 = unsubstitutedInnerClassesScope.mo382getContributedClassifier(name, NoLookupLocation.FROM_DESERIALIZATION);
            if (!(contributedClassifier2 instanceof ClassDescriptor)) {
                contributedClassifier2 = null;
            }
            ClassDescriptor classDescriptor = (ClassDescriptor) contributedClassifier2;
            if (classDescriptor == null) {
                return null;
            }
            result = classDescriptor;
        }
        return result;
    }

    @NotNull
    public static final ClassDescriptor findNonGenericClassAcrossDependencies(@NotNull ModuleDescriptor receiver$0, @NotNull ClassId classId, @NotNull NotFoundClasses notFoundClasses) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(classId, "classId");
        Intrinsics.checkParameterIsNotNull(notFoundClasses, "notFoundClasses");
        ClassDescriptor existingClass = findClassAcrossModuleDependencies(receiver$0, classId);
        return existingClass != null ? existingClass : notFoundClasses.getClass(classId, SequencesKt.toList(SequencesKt.map(SequencesKt.generateSequence(classId, FindClassInModuleKt$findNonGenericClassAcrossDependencies$typeParametersCount$1.INSTANCE), new Function1<ClassId, Integer>() { // from class: kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt$findNonGenericClassAcrossDependencies$typeParametersCount$2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Integer invoke(ClassId classId2) {
                return Integer.valueOf(invoke2(classId2));
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final int invoke2(@NotNull ClassId it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return 0;
            }
        })));
    }

    @Nullable
    public static final TypeAliasDescriptor findTypeAliasAcrossModuleDependencies(@NotNull ModuleDescriptor receiver$0, @NotNull ClassId classId) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(classId, "classId");
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkExpressionValueIsNotNull(packageFqName, "classId.packageFqName");
        PackageViewDescriptor packageViewDescriptor = receiver$0.getPackage(packageFqName);
        List<Name> listPathSegments = classId.getRelativeClassName().pathSegments();
        Intrinsics.checkExpressionValueIsNotNull(listPathSegments, "classId.relativeClassName.pathSegments()");
        int lastNameIndex = listPathSegments.size() - 1;
        MemberScope memberScope = packageViewDescriptor.getMemberScope();
        Object objFirst = CollectionsKt.first((List<? extends Object>) listPathSegments);
        Intrinsics.checkExpressionValueIsNotNull(objFirst, "segments.first()");
        ClassifierDescriptor topLevelClassifier = memberScope.mo382getContributedClassifier((Name) objFirst, NoLookupLocation.FROM_DESERIALIZATION);
        if (lastNameIndex == 0) {
            return (TypeAliasDescriptor) (topLevelClassifier instanceof TypeAliasDescriptor ? topLevelClassifier : null);
        }
        ClassDescriptor currentClass = (ClassDescriptor) (!(topLevelClassifier instanceof ClassDescriptor) ? null : topLevelClassifier);
        if (currentClass == null) {
            return null;
        }
        for (Name name : listPathSegments.subList(1, lastNameIndex)) {
            MemberScope unsubstitutedInnerClassesScope = currentClass.getUnsubstitutedInnerClassesScope();
            Intrinsics.checkExpressionValueIsNotNull(name, "name");
            ClassifierDescriptor contributedClassifier = unsubstitutedInnerClassesScope.mo382getContributedClassifier(name, NoLookupLocation.FROM_DESERIALIZATION);
            if (!(contributedClassifier instanceof ClassDescriptor)) {
                contributedClassifier = null;
            }
            ClassDescriptor classDescriptor = (ClassDescriptor) contributedClassifier;
            if (classDescriptor == null) {
                return null;
            }
            currentClass = classDescriptor;
        }
        Name lastName = listPathSegments.get(lastNameIndex);
        MemberScope unsubstitutedMemberScope = currentClass.getUnsubstitutedMemberScope();
        Intrinsics.checkExpressionValueIsNotNull(lastName, "lastName");
        ClassifierDescriptor contributedClassifier2 = unsubstitutedMemberScope.mo382getContributedClassifier(lastName, NoLookupLocation.FROM_DESERIALIZATION);
        return (TypeAliasDescriptor) (contributedClassifier2 instanceof TypeAliasDescriptor ? contributedClassifier2 : null);
    }
}
