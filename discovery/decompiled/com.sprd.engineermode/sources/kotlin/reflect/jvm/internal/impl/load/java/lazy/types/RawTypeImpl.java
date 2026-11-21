package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.RawType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.text.StringsKt;
import kotlin.text.Typography;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: RawType.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class RawTypeImpl extends FlexibleType implements RawType {

    /* JADX INFO: renamed from: kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawTypeImpl$render$1, reason: invalid class name */
    /* JADX INFO: compiled from: RawType.kt */
    static final class AnonymousClass1 extends Lambda implements Function2<String, String, Boolean> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(2);
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Boolean invoke(String str, String str2) {
            return Boolean.valueOf(invoke2(str, str2));
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final boolean invoke2(@NotNull String first, @NotNull String second) {
            Intrinsics.checkParameterIsNotNull(first, "first");
            Intrinsics.checkParameterIsNotNull(second, "second");
            return Intrinsics.areEqual(first, StringsKt.removePrefix(second, (CharSequence) "out ")) || Intrinsics.areEqual(second, "*");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RawTypeImpl(@NotNull SimpleType lowerBound, @NotNull SimpleType upperBound) {
        super(lowerBound, upperBound);
        Intrinsics.checkParameterIsNotNull(lowerBound, "lowerBound");
        Intrinsics.checkParameterIsNotNull(upperBound, "upperBound");
        boolean zIsSubtypeOf = KotlinTypeChecker.DEFAULT.isSubtypeOf(lowerBound, upperBound);
        if (!_Assertions.ENABLED || zIsSubtypeOf) {
            return;
        }
        throw new AssertionError("Lower bound " + lowerBound + " of a flexible type must be a subtype of the upper bound " + upperBound);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.FlexibleType
    @NotNull
    public SimpleType getDelegate() {
        return getLowerBound();
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.FlexibleType, kotlin.reflect.jvm.internal.impl.types.KotlinType
    @NotNull
    public MemberScope getMemberScope() {
        ClassifierDescriptor classifierDescriptorMo381getDeclarationDescriptor = getConstructor().mo381getDeclarationDescriptor();
        if (!(classifierDescriptorMo381getDeclarationDescriptor instanceof ClassDescriptor)) {
            classifierDescriptorMo381getDeclarationDescriptor = null;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) classifierDescriptorMo381getDeclarationDescriptor;
        if (classDescriptor == null) {
            throw new IllegalStateException(("Incorrect classifier: " + getConstructor().mo381getDeclarationDescriptor()).toString());
        }
        MemberScope memberScope = classDescriptor.getMemberScope(RawSubstitution.INSTANCE);
        Intrinsics.checkExpressionValueIsNotNull(memberScope, "classDescriptor.getMemberScope(RawSubstitution)");
        return memberScope;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.UnwrappedType
    @NotNull
    public RawTypeImpl replaceAnnotations(@NotNull Annotations newAnnotations) {
        Intrinsics.checkParameterIsNotNull(newAnnotations, "newAnnotations");
        return new RawTypeImpl(getLowerBound().replaceAnnotations(newAnnotations), getUpperBound().replaceAnnotations(newAnnotations));
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.UnwrappedType
    @NotNull
    public RawTypeImpl makeNullableAsSpecified(boolean newNullability) {
        return new RawTypeImpl(getLowerBound().makeNullableAsSpecified(newNullability), getUpperBound().makeNullableAsSpecified(newNullability));
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.FlexibleType
    @NotNull
    public String render(@NotNull final DescriptorRenderer renderer, @NotNull DescriptorRendererOptions options) {
        boolean z;
        String newUpper;
        Intrinsics.checkParameterIsNotNull(renderer, "renderer");
        Intrinsics.checkParameterIsNotNull(options, "options");
        AnonymousClass1 onlyOutDiffers$ = AnonymousClass1.INSTANCE;
        Function1<KotlinType, List<? extends String>> function1 = new Function1<KotlinType, List<? extends String>>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawTypeImpl.render.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final List<String> invoke(@NotNull KotlinType type) {
                Intrinsics.checkParameterIsNotNull(type, "type");
                Iterable $receiver$iv = type.getArguments();
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
                for (Object item$iv$iv : $receiver$iv) {
                    TypeProjection it = (TypeProjection) item$iv$iv;
                    destination$iv$iv.add(renderer.renderTypeProjection(it));
                }
                return (List) destination$iv$iv;
            }
        };
        AnonymousClass3 replaceArgs$ = AnonymousClass3.INSTANCE;
        String lowerRendered = renderer.renderType(getLowerBound());
        String upperRendered = renderer.renderType(getUpperBound());
        if (options.getDebugMode()) {
            return "raw (" + lowerRendered + ".." + upperRendered + ')';
        }
        if (getUpperBound().getArguments().isEmpty()) {
            return renderer.renderFlexibleType(lowerRendered, upperRendered, TypeUtilsKt.getBuiltIns(this));
        }
        List<String> listInvoke = function1.invoke((KotlinType) getLowerBound());
        List<String> listInvoke2 = function1.invoke((KotlinType) getUpperBound());
        String newArgs = CollectionsKt.joinToString$default(listInvoke, ", ", null, null, 0, null, new Function1<String, String>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawTypeImpl$render$newArgs$1
            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final String invoke(@NotNull String it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return "(raw) " + it;
            }
        }, 30, null);
        Iterable $receiver$iv = CollectionsKt.zip(listInvoke, listInvoke2);
        if (!($receiver$iv instanceof Collection) || !((Collection) $receiver$iv).isEmpty()) {
            Iterator it = $receiver$iv.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = true;
                    break;
                }
                Object element$iv = it.next();
                Pair it2 = (Pair) element$iv;
                AnonymousClass1 onlyOutDiffers$2 = onlyOutDiffers$;
                if (!AnonymousClass1.INSTANCE.invoke2((String) it2.getFirst(), (String) it2.getSecond())) {
                    z = false;
                    break;
                }
                onlyOutDiffers$ = onlyOutDiffers$2;
            }
        } else {
            z = true;
        }
        if (z) {
            newUpper = replaceArgs$.invoke(upperRendered, newArgs);
        } else {
            newUpper = upperRendered;
        }
        String newLower = replaceArgs$.invoke(lowerRendered, newArgs);
        return Intrinsics.areEqual(newLower, newUpper) ? newLower : renderer.renderFlexibleType(newLower, newUpper, TypeUtilsKt.getBuiltIns(this));
    }

    /* JADX INFO: renamed from: kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawTypeImpl$render$3, reason: invalid class name */
    /* JADX INFO: compiled from: RawType.kt */
    static final class AnonymousClass3 extends Lambda implements Function2<String, String, String> {
        public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

        AnonymousClass3() {
            super(2);
        }

        @Override // kotlin.jvm.functions.Function2
        @NotNull
        public final String invoke(@NotNull String receiver$0, @NotNull String newArgs) {
            Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
            Intrinsics.checkParameterIsNotNull(newArgs, "newArgs");
            if (!StringsKt.contains$default((CharSequence) receiver$0, Typography.less, false, 2, (Object) null)) {
                return receiver$0;
            }
            return StringsKt.substringBefore$default(receiver$0, Typography.less, (String) null, 2, (Object) null) + Typography.less + newArgs + Typography.greater + StringsKt.substringAfterLast$default(receiver$0, Typography.greater, (String) null, 2, (Object) null);
        }
    }
}
