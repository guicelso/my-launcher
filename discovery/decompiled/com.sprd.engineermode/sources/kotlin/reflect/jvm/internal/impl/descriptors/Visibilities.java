package kotlin.reflect.jvm.internal.impl.descriptors;

import android.support.v4.os.EnvironmentCompat;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import kotlin.collections.SetsKt;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.TypeAliasConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ReceiverValue;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.SuperCallReceiverValue;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ThisClassReceiver;
import kotlin.reflect.jvm.internal.impl.types.DynamicTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.util.ModuleVisibilityHelper;
import kotlin.reflect.jvm.internal.impl.utils.CollectionsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public class Visibilities {
    public static final ReceiverValue ALWAYS_SUITABLE_RECEIVER;
    public static final Visibility DEFAULT_VISIBILITY;

    @Deprecated
    public static final ReceiverValue FALSE_IF_PROTECTED;

    @NotNull
    public static final Visibility INHERITED;

    @NotNull
    public static final Visibility INTERNAL;

    @NotNull
    public static final Visibility INVISIBLE_FAKE;
    public static final Set<Visibility> INVISIBLE_FROM_OTHER_MODULES;
    private static final ReceiverValue IRRELEVANT_RECEIVER;

    @NotNull
    public static final Visibility LOCAL;

    @NotNull
    private static final ModuleVisibilityHelper MODULE_VISIBILITY_HELPER;
    private static final Map<Visibility, Integer> ORDERED_VISIBILITIES;

    @NotNull
    public static final Visibility PRIVATE;

    @NotNull
    public static final Visibility PRIVATE_TO_THIS;

    @NotNull
    public static final Visibility PROTECTED;

    @NotNull
    public static final Visibility PUBLIC;

    @NotNull
    public static final Visibility UNKNOWN;

    static {
        boolean z = false;
        PRIVATE = new Visibility("private", z) { // from class: kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.1
            private boolean hasContainingSourceFile(@NotNull DeclarationDescriptor descriptor) {
                return DescriptorUtils.getContainingSourceFile(descriptor) != SourceFile.NO_SOURCE_FILE;
            }

            @Override // kotlin.reflect.jvm.internal.impl.descriptors.Visibility
            public boolean isVisible(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
                if (DescriptorUtils.isTopLevelDeclaration(what) && hasContainingSourceFile(from)) {
                    return Visibilities.inSameFile(what, from);
                }
                if (what instanceof ConstructorDescriptor) {
                    ClassifierDescriptorWithTypeParameters classDescriptor = ((ConstructorDescriptor) what).getContainingDeclaration();
                    if (DescriptorUtils.isSealedClass(classDescriptor) && DescriptorUtils.isTopLevelDeclaration(classDescriptor) && (from instanceof ConstructorDescriptor) && DescriptorUtils.isTopLevelDeclaration(from.getContainingDeclaration()) && Visibilities.inSameFile(what, from)) {
                        return true;
                    }
                }
                DeclarationDescriptor parent = what;
                while (parent != null) {
                    parent = parent.getContainingDeclaration();
                    if (((parent instanceof ClassDescriptor) && !DescriptorUtils.isCompanionObject(parent)) || (parent instanceof PackageFragmentDescriptor)) {
                        break;
                    }
                }
                if (parent == null) {
                    return false;
                }
                for (DeclarationDescriptor fromParent = from; fromParent != null; fromParent = fromParent.getContainingDeclaration()) {
                    if (parent == fromParent) {
                        return true;
                    }
                    if (fromParent instanceof PackageFragmentDescriptor) {
                        return (parent instanceof PackageFragmentDescriptor) && ((PackageFragmentDescriptor) parent).getFqName().equals(((PackageFragmentDescriptor) fromParent).getFqName()) && DescriptorUtils.areInSameModule(fromParent, parent);
                    }
                }
                return false;
            }
        };
        PRIVATE_TO_THIS = new Visibility("private_to_this", z) { // from class: kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.2
            @Override // kotlin.reflect.jvm.internal.impl.descriptors.Visibility
            public boolean isVisible(@Nullable ReceiverValue thisObject, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
                DeclarationDescriptor classDescriptor;
                if (Visibilities.PRIVATE.isVisible(thisObject, what, from)) {
                    if (thisObject == Visibilities.ALWAYS_SUITABLE_RECEIVER) {
                        return true;
                    }
                    if (thisObject != Visibilities.IRRELEVANT_RECEIVER && (classDescriptor = DescriptorUtils.getParentOfType(what, ClassDescriptor.class)) != null && (thisObject instanceof ThisClassReceiver)) {
                        return ((ThisClassReceiver) thisObject).getClassDescriptor().getOriginal().equals(classDescriptor.getOriginal());
                    }
                }
                return false;
            }

            @Override // kotlin.reflect.jvm.internal.impl.descriptors.Visibility
            @NotNull
            public String getDisplayName() {
                return "private/*private to this*/";
            }
        };
        boolean z2 = true;
        PROTECTED = new Visibility("protected", z2) { // from class: kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.3
            @Override // kotlin.reflect.jvm.internal.impl.descriptors.Visibility
            public boolean isVisible(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
                ClassDescriptor companionOwner;
                ClassDescriptor givenDescriptorContainingClass = (ClassDescriptor) DescriptorUtils.getParentOfType(what, ClassDescriptor.class);
                ClassDescriptor fromClass = (ClassDescriptor) DescriptorUtils.getParentOfType(from, ClassDescriptor.class, false);
                if (fromClass == null) {
                    return false;
                }
                if (givenDescriptorContainingClass != null && DescriptorUtils.isCompanionObject(givenDescriptorContainingClass) && (companionOwner = (ClassDescriptor) DescriptorUtils.getParentOfType(givenDescriptorContainingClass, ClassDescriptor.class)) != null && DescriptorUtils.isSubclass(fromClass, companionOwner)) {
                    return true;
                }
                DeclarationDescriptorWithVisibility whatDeclaration = DescriptorUtils.unwrapFakeOverrideToAnyDeclaration(what);
                ClassDescriptor classDescriptor = (ClassDescriptor) DescriptorUtils.getParentOfType(whatDeclaration, ClassDescriptor.class);
                if (classDescriptor == null) {
                    return false;
                }
                if (DescriptorUtils.isSubclass(fromClass, classDescriptor) && doesReceiverFitForProtectedVisibility(receiver, whatDeclaration, fromClass)) {
                    return true;
                }
                return isVisible(receiver, what, fromClass.getContainingDeclaration());
            }

            private boolean doesReceiverFitForProtectedVisibility(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility whatDeclaration, @NotNull ClassDescriptor fromClass) {
                if (receiver == Visibilities.FALSE_IF_PROTECTED) {
                    return false;
                }
                if (!(whatDeclaration instanceof CallableMemberDescriptor) || (whatDeclaration instanceof ConstructorDescriptor) || receiver == Visibilities.ALWAYS_SUITABLE_RECEIVER) {
                    return true;
                }
                if (receiver == Visibilities.IRRELEVANT_RECEIVER || receiver == null) {
                    return false;
                }
                KotlinType actualReceiverType = receiver instanceof SuperCallReceiverValue ? ((SuperCallReceiverValue) receiver).getThisType() : receiver.getType();
                return DescriptorUtils.isSubtypeOfClass(actualReceiverType, fromClass) || DynamicTypesKt.isDynamic(actualReceiverType);
            }
        };
        INTERNAL = new Visibility("internal", z) { // from class: kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.4
            @Override // kotlin.reflect.jvm.internal.impl.descriptors.Visibility
            public boolean isVisible(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
                ModuleDescriptor whatModule = DescriptorUtils.getContainingModule(what);
                ModuleDescriptor fromModule = DescriptorUtils.getContainingModule(from);
                if (fromModule.shouldSeeInternalsOf(whatModule)) {
                    return Visibilities.MODULE_VISIBILITY_HELPER.isInFriendModule(what, from);
                }
                return false;
            }
        };
        PUBLIC = new Visibility("public", z2) { // from class: kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.5
            @Override // kotlin.reflect.jvm.internal.impl.descriptors.Visibility
            public boolean isVisible(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
                return true;
            }
        };
        LOCAL = new Visibility("local", z) { // from class: kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.6
            @Override // kotlin.reflect.jvm.internal.impl.descriptors.Visibility
            public boolean isVisible(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
                throw new IllegalStateException("This method shouldn't be invoked for LOCAL visibility");
            }
        };
        INHERITED = new Visibility("inherited", z) { // from class: kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.7
            @Override // kotlin.reflect.jvm.internal.impl.descriptors.Visibility
            public boolean isVisible(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
                throw new IllegalStateException("Visibility is unknown yet");
            }
        };
        INVISIBLE_FAKE = new Visibility("invisible_fake", z) { // from class: kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.8
            @Override // kotlin.reflect.jvm.internal.impl.descriptors.Visibility
            public boolean isVisible(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
                return false;
            }
        };
        UNKNOWN = new Visibility(EnvironmentCompat.MEDIA_UNKNOWN, z) { // from class: kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.9
            @Override // kotlin.reflect.jvm.internal.impl.descriptors.Visibility
            public boolean isVisible(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
                return false;
            }
        };
        INVISIBLE_FROM_OTHER_MODULES = Collections.unmodifiableSet(SetsKt.setOf((Object[]) new Visibility[]{PRIVATE, PRIVATE_TO_THIS, INTERNAL, LOCAL}));
        Map<Visibility, Integer> visibilities = CollectionsKt.newHashMapWithExpectedSize(4);
        visibilities.put(PRIVATE_TO_THIS, 0);
        visibilities.put(PRIVATE, 0);
        visibilities.put(INTERNAL, 1);
        visibilities.put(PROTECTED, 1);
        visibilities.put(PUBLIC, 2);
        ORDERED_VISIBILITIES = Collections.unmodifiableMap(visibilities);
        DEFAULT_VISIBILITY = PUBLIC;
        IRRELEVANT_RECEIVER = new ReceiverValue() { // from class: kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.10
            @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ReceiverValue
            @NotNull
            public KotlinType getType() {
                throw new IllegalStateException("This method should not be called");
            }
        };
        ALWAYS_SUITABLE_RECEIVER = new ReceiverValue() { // from class: kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.11
            @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ReceiverValue
            @NotNull
            public KotlinType getType() {
                throw new IllegalStateException("This method should not be called");
            }
        };
        FALSE_IF_PROTECTED = new ReceiverValue() { // from class: kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.12
            @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ReceiverValue
            @NotNull
            public KotlinType getType() {
                throw new IllegalStateException("This method should not be called");
            }
        };
        Iterator<ModuleVisibilityHelper> iterator = ServiceLoader.load(ModuleVisibilityHelper.class, ModuleVisibilityHelper.class.getClassLoader()).iterator();
        MODULE_VISIBILITY_HELPER = iterator.hasNext() ? iterator.next() : ModuleVisibilityHelper.EMPTY.INSTANCE;
    }

    public static boolean isVisibleIgnoringReceiver(@NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
        return findInvisibleMember(ALWAYS_SUITABLE_RECEIVER, what, from) == null;
    }

    public static boolean inSameFile(@NotNull DeclarationDescriptor what, @NotNull DeclarationDescriptor from) {
        SourceFile fromContainingFile = DescriptorUtils.getContainingSourceFile(from);
        if (fromContainingFile != SourceFile.NO_SOURCE_FILE) {
            return fromContainingFile.equals(DescriptorUtils.getContainingSourceFile(what));
        }
        return false;
    }

    @Nullable
    public static DeclarationDescriptorWithVisibility findInvisibleMember(@Nullable ReceiverValue receiver, @NotNull DeclarationDescriptorWithVisibility what, @NotNull DeclarationDescriptor from) {
        DeclarationDescriptorWithVisibility invisibleUnderlying;
        for (DeclarationDescriptorWithVisibility parent = (DeclarationDescriptorWithVisibility) what.getOriginal(); parent != null && parent.getVisibility() != LOCAL; parent = (DeclarationDescriptorWithVisibility) DescriptorUtils.getParentOfType(parent, DeclarationDescriptorWithVisibility.class)) {
            if (!parent.getVisibility().isVisible(receiver, parent, from)) {
                return parent;
            }
        }
        if (!(what instanceof TypeAliasConstructorDescriptor) || (invisibleUnderlying = findInvisibleMember(receiver, ((TypeAliasConstructorDescriptor) what).getUnderlyingConstructorDescriptor(), from)) == null) {
            return null;
        }
        return invisibleUnderlying;
    }

    @Nullable
    static Integer compareLocal(@NotNull Visibility first, @NotNull Visibility second) {
        if (first == second) {
            return 0;
        }
        Integer firstIndex = ORDERED_VISIBILITIES.get(first);
        Integer secondIndex = ORDERED_VISIBILITIES.get(second);
        if (firstIndex == null || secondIndex == null || firstIndex.equals(secondIndex)) {
            return null;
        }
        return Integer.valueOf(firstIndex.intValue() - secondIndex.intValue());
    }

    @Nullable
    public static Integer compare(@NotNull Visibility first, @NotNull Visibility second) {
        Integer result = first.compareTo(second);
        if (result != null) {
            return result;
        }
        Integer oppositeResult = second.compareTo(first);
        if (oppositeResult != null) {
            return Integer.valueOf(-oppositeResult.intValue());
        }
        return null;
    }

    public static boolean isPrivate(@NotNull Visibility visibility) {
        return visibility == PRIVATE || visibility == PRIVATE_TO_THIS;
    }
}
