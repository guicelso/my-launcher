package kotlin.reflect.jvm.internal.impl.resolve;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.utils.SmartSet;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: overridingUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class OverridingUtilsKt {
    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public static final <H> Collection<H> selectMostSpecificInEachOverridableGroup(@NotNull Collection<? extends H> receiver$0, @NotNull Function1<? super H, ? extends CallableDescriptor> descriptorByHandle) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(descriptorByHandle, "descriptorByHandle");
        if (receiver$0.size() <= 1) {
            return receiver$0;
        }
        LinkedList queue = new LinkedList(receiver$0);
        SmartSet result = SmartSet.Companion.create();
        while (!queue.isEmpty()) {
            Object nextHandle = CollectionsKt.first((List<? extends Object>) queue);
            final SmartSet conflictedHandles = SmartSet.Companion.create();
            Collection overridableGroup = OverridingUtil.extractMembersOverridableInBothWays(nextHandle, queue, descriptorByHandle, new Function1<H, Unit>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.OverridingUtilsKt$selectMostSpecificInEachOverridableGroup$overridableGroup$1
                {
                    super(1);
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
                    invoke2(obj);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(H it) {
                    SmartSet smartSet = conflictedHandles;
                    Intrinsics.checkExpressionValueIsNotNull(it, "it");
                    smartSet.add(it);
                }
            });
            Intrinsics.checkExpressionValueIsNotNull(overridableGroup, "OverridingUtil.extractMe…nflictedHandles.add(it) }");
            if (overridableGroup.size() == 1 && conflictedHandles.isEmpty()) {
                Object objSingle = CollectionsKt.single(overridableGroup);
                Intrinsics.checkExpressionValueIsNotNull(objSingle, "overridableGroup.single()");
                result.add(objSingle);
            } else {
                Object mostSpecific = OverridingUtil.selectMostSpecificMember(overridableGroup, descriptorByHandle);
                Intrinsics.checkExpressionValueIsNotNull(mostSpecific, "OverridingUtil.selectMos…roup, descriptorByHandle)");
                CallableDescriptor mostSpecificDescriptor = descriptorByHandle.invoke(mostSpecific);
                Collection $receiver$iv = overridableGroup;
                for (Object it : $receiver$iv) {
                    Intrinsics.checkExpressionValueIsNotNull(it, "it");
                    if (!OverridingUtil.isMoreSpecific(mostSpecificDescriptor, descriptorByHandle.invoke(it))) {
                        conflictedHandles.add(it);
                    }
                }
                if (!conflictedHandles.isEmpty()) {
                    result.addAll(conflictedHandles);
                }
                result.add(mostSpecific);
            }
        }
        return result;
    }

    public static final <D extends CallableDescriptor> void retainMostSpecificInEachOverridableGroup(@NotNull Collection<D> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Collection<?> collectionSelectMostSpecificInEachOverridableGroup = selectMostSpecificInEachOverridableGroup(receiver$0, new Function1<D, D>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.OverridingUtilsKt$retainMostSpecificInEachOverridableGroup$newResult$1
            /* JADX WARN: Incorrect return type in method signature: (TD;)TD; */
            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final CallableDescriptor invoke(@NotNull CallableDescriptor receiver$1) {
                Intrinsics.checkParameterIsNotNull(receiver$1, "receiver$0");
                return receiver$1;
            }
        });
        if (receiver$0.size() == collectionSelectMostSpecificInEachOverridableGroup.size()) {
            return;
        }
        receiver$0.retainAll(collectionSelectMostSpecificInEachOverridableGroup);
    }
}
