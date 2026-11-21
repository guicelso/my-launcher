package kotlin.reflect.jvm.internal.impl.types;

import java.util.Collection;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: AbstractTypeConstructor.kt */
/* JADX INFO: loaded from: classes4.dex */
final class AbstractTypeConstructor$supertypes$3 extends Lambda implements Function1<AbstractTypeConstructor.Supertypes, Unit> {
    final /* synthetic */ AbstractTypeConstructor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AbstractTypeConstructor$supertypes$3(AbstractTypeConstructor abstractTypeConstructor) {
        super(1);
        this.this$0 = abstractTypeConstructor;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(AbstractTypeConstructor.Supertypes supertypes) {
        invoke2(supertypes);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(@NotNull AbstractTypeConstructor.Supertypes supertypes) {
        Intrinsics.checkParameterIsNotNull(supertypes, "supertypes");
        Collection collectionFindLoopsInSupertypesAndDisconnect = this.this$0.getSupertypeLoopChecker().findLoopsInSupertypesAndDisconnect(this.this$0, supertypes.getAllSupertypes(), new Function1<TypeConstructor, Collection<? extends KotlinType>>() { // from class: kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor$supertypes$3$resultWithoutCycles$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final Collection<KotlinType> invoke(@NotNull TypeConstructor it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return this.this$0.this$0.computeNeighbours(it, false);
            }
        }, new Function1<KotlinType, Unit>() { // from class: kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor$supertypes$3$resultWithoutCycles$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(KotlinType kotlinType) {
                invoke2(kotlinType);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull KotlinType it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                this.this$0.this$0.reportSupertypeLoopError(it);
            }
        });
        if (collectionFindLoopsInSupertypesAndDisconnect.isEmpty()) {
            KotlinType it = this.this$0.defaultSupertypeIfEmpty();
            Collection collectionListOf = it != null ? CollectionsKt.listOf(it) : null;
            if (collectionListOf == null) {
                collectionListOf = CollectionsKt.emptyList();
            }
            Collection resultWithoutCycles = collectionListOf;
            collectionFindLoopsInSupertypesAndDisconnect = resultWithoutCycles;
        }
        this.this$0.getSupertypeLoopChecker().findLoopsInSupertypesAndDisconnect(this.this$0, collectionFindLoopsInSupertypesAndDisconnect, new Function1<TypeConstructor, Collection<? extends KotlinType>>() { // from class: kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor$supertypes$3.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final Collection<KotlinType> invoke(@NotNull TypeConstructor it2) {
                Intrinsics.checkParameterIsNotNull(it2, "it");
                return AbstractTypeConstructor$supertypes$3.this.this$0.computeNeighbours(it2, true);
            }
        }, new Function1<KotlinType, Unit>() { // from class: kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor$supertypes$3.3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(KotlinType kotlinType) {
                invoke2(kotlinType);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull KotlinType it2) {
                Intrinsics.checkParameterIsNotNull(it2, "it");
                AbstractTypeConstructor$supertypes$3.this.this$0.reportScopesLoopError(it2);
            }
        });
        List<? extends KotlinType> list = (List) (collectionFindLoopsInSupertypesAndDisconnect instanceof List ? collectionFindLoopsInSupertypesAndDisconnect : null);
        if (list == null) {
            list = CollectionsKt.toList(collectionFindLoopsInSupertypesAndDisconnect);
        }
        supertypes.setSupertypesWithoutCycles(list);
    }
}
