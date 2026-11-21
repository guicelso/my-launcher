package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorWithVisibility;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public class JavaVisibilities {

    @NotNull
    public static final Visibility PACKAGE_VISIBILITY = new Visibility("package", false) { // from class: kotlin.reflect.jvm.internal.impl.load.java.JavaVisibilities.1
        @Override // kotlin.reflect.jvm.internal.impl.descriptors.Visibility
        public boolean isVisible(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
            return JavaVisibilities.areInSamePackage(what, from);
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.Visibility
        protected Integer compareTo(@NotNull Visibility visibility) {
            if (this == visibility) {
                return 0;
            }
            return Visibilities.isPrivate(visibility) ? 1 : -1;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.Visibility
        @NotNull
        public String getDisplayName() {
            return "public/*package*/";
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.Visibility
        @NotNull
        public Visibility normalize() {
            return Visibilities.PROTECTED;
        }
    };

    @NotNull
    public static final Visibility PROTECTED_AND_PACKAGE;

    @NotNull
    public static final Visibility PROTECTED_STATIC_VISIBILITY;

    static {
        boolean z = true;
        PROTECTED_STATIC_VISIBILITY = new Visibility("protected_static", z) { // from class: kotlin.reflect.jvm.internal.impl.load.java.JavaVisibilities.2
            @Override // kotlin.reflect.jvm.internal.impl.descriptors.Visibility
            public boolean isVisible(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
                return JavaVisibilities.isVisibleForProtectedAndPackage(receiver, what, from);
            }

            @Override // kotlin.reflect.jvm.internal.impl.descriptors.Visibility
            @NotNull
            public String getDisplayName() {
                return "protected/*protected static*/";
            }

            @Override // kotlin.reflect.jvm.internal.impl.descriptors.Visibility
            @NotNull
            public Visibility normalize() {
                return Visibilities.PROTECTED;
            }
        };
        PROTECTED_AND_PACKAGE = new Visibility("protected_and_package", z) { // from class: kotlin.reflect.jvm.internal.impl.load.java.JavaVisibilities.3
            @Override // kotlin.reflect.jvm.internal.impl.descriptors.Visibility
            public boolean isVisible(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
                return JavaVisibilities.isVisibleForProtectedAndPackage(receiver, what, from);
            }

            @Override // kotlin.reflect.jvm.internal.impl.descriptors.Visibility
            protected Integer compareTo(@NotNull Visibility visibility) {
                if (this == visibility) {
                    return 0;
                }
                if (visibility == Visibilities.INTERNAL) {
                    return null;
                }
                return Visibilities.isPrivate(visibility) ? 1 : -1;
            }

            @Override // kotlin.reflect.jvm.internal.impl.descriptors.Visibility
            @NotNull
            public String getDisplayName() {
                return "protected/*protected and package*/";
            }

            @Override // kotlin.reflect.jvm.internal.impl.descriptors.Visibility
            @NotNull
            public Visibility normalize() {
                return Visibilities.PROTECTED;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isVisibleForProtectedAndPackage(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
        if (areInSamePackage(DescriptorUtils.unwrapFakeOverrideToAnyDeclaration(what), from)) {
            return true;
        }
        return Visibilities.PROTECTED.isVisible(receiver, what, from);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean areInSamePackage(@NotNull DeclarationDescriptor first, @NotNull DeclarationDescriptor second) {
        PackageFragmentDescriptor whatPackage = (PackageFragmentDescriptor) DescriptorUtils.getParentOfType(first, PackageFragmentDescriptor.class, false);
        PackageFragmentDescriptor fromPackage = (PackageFragmentDescriptor) DescriptorUtils.getParentOfType(second, PackageFragmentDescriptor.class, false);
        return (fromPackage == null || whatPackage == null || !whatPackage.getFqName().equals(fromPackage.getFqName())) ? false : true;
    }
}
