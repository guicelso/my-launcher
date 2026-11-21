package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: SpecialTypes.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class SpecialTypesKt {
    @Nullable
    public static final AbbreviatedType getAbbreviatedType(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        UnwrappedType unwrappedTypeUnwrap = receiver$0.unwrap();
        if (!(unwrappedTypeUnwrap instanceof AbbreviatedType)) {
            unwrappedTypeUnwrap = null;
        }
        return (AbbreviatedType) unwrappedTypeUnwrap;
    }

    @Nullable
    public static final SimpleType getAbbreviation(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        AbbreviatedType abbreviatedType = getAbbreviatedType(receiver$0);
        if (abbreviatedType != null) {
            return abbreviatedType.getAbbreviation();
        }
        return null;
    }

    @NotNull
    public static final SimpleType withAbbreviation(@NotNull SimpleType receiver$0, @NotNull SimpleType abbreviatedType) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(abbreviatedType, "abbreviatedType");
        return KotlinTypeKt.isError(receiver$0) ? receiver$0 : new AbbreviatedType(receiver$0, abbreviatedType);
    }

    public static final boolean isDefinitelyNotNullType(@NotNull KotlinType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return receiver$0.unwrap() instanceof DefinitelyNotNullType;
    }

    @NotNull
    public static final SimpleType makeSimpleTypeDefinitelyNotNullOrNotNull(@NotNull SimpleType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        DefinitelyNotNullType definitelyNotNullTypeMakeDefinitelyNotNull$descriptors = DefinitelyNotNullType.Companion.makeDefinitelyNotNull$descriptors(receiver$0);
        DefinitelyNotNullType definitelyNotNullTypeMakeIntersectionTypeDefinitelyNotNullOrNotNull = definitelyNotNullTypeMakeDefinitelyNotNull$descriptors != null ? definitelyNotNullTypeMakeDefinitelyNotNull$descriptors : makeIntersectionTypeDefinitelyNotNullOrNotNull(receiver$0);
        return definitelyNotNullTypeMakeIntersectionTypeDefinitelyNotNullOrNotNull != null ? definitelyNotNullTypeMakeIntersectionTypeDefinitelyNotNullOrNotNull : receiver$0.makeNullableAsSpecified(false);
    }

    @NotNull
    public static final UnwrappedType makeDefinitelyNotNullOrNotNull(@NotNull UnwrappedType receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        DefinitelyNotNullType definitelyNotNullTypeMakeDefinitelyNotNull$descriptors = DefinitelyNotNullType.Companion.makeDefinitelyNotNull$descriptors(receiver$0);
        SimpleType simpleTypeMakeIntersectionTypeDefinitelyNotNullOrNotNull = definitelyNotNullTypeMakeDefinitelyNotNull$descriptors != null ? definitelyNotNullTypeMakeDefinitelyNotNull$descriptors : makeIntersectionTypeDefinitelyNotNullOrNotNull(receiver$0);
        return simpleTypeMakeIntersectionTypeDefinitelyNotNullOrNotNull != null ? simpleTypeMakeIntersectionTypeDefinitelyNotNullOrNotNull : receiver$0.makeNullableAsSpecified(false);
    }

    private static final SimpleType makeIntersectionTypeDefinitelyNotNullOrNotNull(@NotNull KotlinType $receiver) {
        IntersectionTypeConstructor definitelyNotNullConstructor;
        TypeConstructor constructor = $receiver.getConstructor();
        if (!(constructor instanceof IntersectionTypeConstructor)) {
            constructor = null;
        }
        IntersectionTypeConstructor typeConstructor = (IntersectionTypeConstructor) constructor;
        if (typeConstructor == null || (definitelyNotNullConstructor = makeDefinitelyNotNullOrNotNull(typeConstructor)) == null) {
            return null;
        }
        return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope($receiver.getAnnotations(), definitelyNotNullConstructor, CollectionsKt.emptyList(), false, definitelyNotNullConstructor.createScopeForKotlinType());
    }

    private static final IntersectionTypeConstructor makeDefinitelyNotNullOrNotNull(@NotNull IntersectionTypeConstructor $receiver) {
        boolean changed$iv = false;
        Iterable $receiver$iv$iv = $receiver.getSupertypes();
        Collection destination$iv$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv$iv, 10));
        for (Object item$iv$iv$iv : $receiver$iv$iv) {
            KotlinType it$iv = (KotlinType) item$iv$iv$iv;
            if (TypeUtils.isNullableType(it$iv)) {
                changed$iv = true;
                KotlinType it = makeDefinitelyNotNullOrNotNull(it$iv.unwrap());
                it$iv = it;
            }
            destination$iv$iv$iv.add(it$iv);
        }
        List newSupertypes$iv = (List) destination$iv$iv$iv;
        if (changed$iv) {
            return new IntersectionTypeConstructor(newSupertypes$iv);
        }
        return null;
    }
}
