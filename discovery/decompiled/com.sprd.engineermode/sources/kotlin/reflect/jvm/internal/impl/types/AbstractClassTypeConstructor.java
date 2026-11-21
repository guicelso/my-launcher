package kotlin.reflect.jvm.internal.impl.types;

import java.util.Collection;
import java.util.Collections;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.utils.SmartList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractClassTypeConstructor extends AbstractTypeConstructor implements TypeConstructor {
    private int hashCode;

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
    @NotNull
    /* JADX INFO: renamed from: getDeclarationDescriptor */
    public abstract ClassDescriptor mo381getDeclarationDescriptor();

    public AbstractClassTypeConstructor(@NotNull StorageManager storageManager) {
        super(storageManager);
        this.hashCode = 0;
    }

    public final int hashCode() {
        int currentHashCode;
        int currentHashCode2 = this.hashCode;
        if (currentHashCode2 != 0) {
            return currentHashCode2;
        }
        ClassifierDescriptor descriptor = mo381getDeclarationDescriptor();
        if (hasMeaningfulFqName(descriptor)) {
            currentHashCode = DescriptorUtils.getFqName(descriptor).hashCode();
        } else {
            currentHashCode = System.identityHashCode(this);
        }
        this.hashCode = currentHashCode;
        return currentHashCode;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.TypeConstructor
    @NotNull
    public KotlinBuiltIns getBuiltIns() {
        return DescriptorUtilsKt.getBuiltIns(mo381getDeclarationDescriptor());
    }

    public final boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TypeConstructor) || other.hashCode() != hashCode() || ((TypeConstructor) other).getParameters().size() != getParameters().size()) {
            return false;
        }
        ClassifierDescriptor myDescriptor = mo381getDeclarationDescriptor();
        ClassifierDescriptor otherDescriptor = ((TypeConstructor) other).mo381getDeclarationDescriptor();
        if (hasMeaningfulFqName(myDescriptor) && ((otherDescriptor == null || hasMeaningfulFqName(otherDescriptor)) && (otherDescriptor instanceof ClassDescriptor))) {
            return areFqNamesEqual((ClassDescriptor) myDescriptor, (ClassDescriptor) otherDescriptor);
        }
        return false;
    }

    private static boolean areFqNamesEqual(ClassDescriptor first, ClassDescriptor second) {
        if (!first.getName().equals(second.getName())) {
            return false;
        }
        DeclarationDescriptor a = first.getContainingDeclaration();
        for (DeclarationDescriptor b = second.getContainingDeclaration(); a != null && b != null; b = b.getContainingDeclaration()) {
            if (a instanceof ModuleDescriptor) {
                return b instanceof ModuleDescriptor;
            }
            if (b instanceof ModuleDescriptor) {
                return false;
            }
            if (a instanceof PackageFragmentDescriptor) {
                return (b instanceof PackageFragmentDescriptor) && ((PackageFragmentDescriptor) a).getFqName().equals(((PackageFragmentDescriptor) b).getFqName());
            }
            if ((b instanceof PackageFragmentDescriptor) || !a.getName().equals(b.getName())) {
                return false;
            }
            a = a.getContainingDeclaration();
        }
        return true;
    }

    private static boolean hasMeaningfulFqName(@NotNull ClassifierDescriptor descriptor) {
        return (ErrorUtils.isError(descriptor) || DescriptorUtils.isLocal(descriptor)) ? false : true;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor
    @NotNull
    protected Collection<KotlinType> getAdditionalNeighboursInSupertypeGraph(boolean useCompanions) {
        DeclarationDescriptor containingDeclaration = mo381getDeclarationDescriptor().getContainingDeclaration();
        if (!(containingDeclaration instanceof ClassDescriptor)) {
            return Collections.emptyList();
        }
        Collection<KotlinType> additionalNeighbours = new SmartList<>();
        ClassDescriptor containingClassDescriptor = (ClassDescriptor) containingDeclaration;
        additionalNeighbours.add(containingClassDescriptor.getDefaultType());
        ClassDescriptor companion = containingClassDescriptor.mo372getCompanionObjectDescriptor();
        if (useCompanions && companion != null) {
            additionalNeighbours.add(companion.getDefaultType());
        }
        return additionalNeighbours;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor
    @Nullable
    protected KotlinType defaultSupertypeIfEmpty() {
        if (KotlinBuiltIns.isSpecialClassWithNoSupertypes(mo381getDeclarationDescriptor())) {
            return null;
        }
        return getBuiltIns().getAnyType();
    }
}
