package kotlin.reflect.full;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.reflect.KCallable;
import kotlin.reflect.KFunction;
import kotlin.reflect.KParameter;
import kotlin.reflect.jvm.internal.KCallableImpl;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.UtilKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: KCallables.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\u001a9\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\u00022\u0016\u0010\u0011\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00130\u0012\"\u0004\u0018\u00010\u0013H\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u0014\u001a7\u0010\u0015\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\u00022\u0014\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u00020\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u0016H\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u0017\u001a\u001a\u0010\u0018\u001a\u0004\u0018\u00010\u0001*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0019\u001a\u00020\u001aH\u0007\"$\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"$\u0010\u0007\u001a\u0004\u0018\u00010\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\b\u0010\u0004\u001a\u0004\b\t\u0010\u0006\"(\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004¢\u0006\f\u0012\u0004\b\f\u0010\u0004\u001a\u0004\b\r\u0010\u000e\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001b"}, d2 = {"extensionReceiverParameter", "Lkotlin/reflect/KParameter;", "Lkotlin/reflect/KCallable;", "extensionReceiverParameter$annotations", "(Lkotlin/reflect/KCallable;)V", "getExtensionReceiverParameter", "(Lkotlin/reflect/KCallable;)Lkotlin/reflect/KParameter;", "instanceParameter", "instanceParameter$annotations", "getInstanceParameter", "valueParameters", "", "valueParameters$annotations", "getValueParameters", "(Lkotlin/reflect/KCallable;)Ljava/util/List;", "callSuspend", "R", "args", "", "", "(Lkotlin/reflect/KCallable;[Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "callSuspendBy", "", "(Lkotlin/reflect/KCallable;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "findParameterByName", "name", "", "kotlin-reflect-api"}, k = 2, mv = {1, 1, 13})
@JvmName(name = "KCallables")
public final class KCallables {
    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void extensionReceiverParameter$annotations(KCallable kCallable) {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void instanceParameter$annotations(KCallable kCallable) {
    }

    @SinceKotlin(version = "1.1")
    public static /* synthetic */ void valueParameters$annotations(KCallable kCallable) {
    }

    @Nullable
    public static final KParameter getInstanceParameter(@NotNull KCallable<?> receiver$0) {
        Object obj;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Iterable $receiver$iv = receiver$0.getParameters();
        Object single$iv = null;
        boolean found$iv = false;
        Iterator it = $receiver$iv.iterator();
        while (true) {
            obj = null;
            if (it.hasNext()) {
                Object element$iv = it.next();
                KParameter it2 = (KParameter) element$iv;
                if (it2.getKind() == KParameter.Kind.INSTANCE) {
                    if (found$iv) {
                        break;
                    }
                    single$iv = element$iv;
                    found$iv = true;
                }
            } else {
                if (!found$iv) {
                    break;
                }
                obj = single$iv;
                break;
            }
        }
        return (KParameter) obj;
    }

    @Nullable
    public static final KParameter getExtensionReceiverParameter(@NotNull KCallable<?> receiver$0) {
        Object obj;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Iterable $receiver$iv = receiver$0.getParameters();
        Object single$iv = null;
        boolean found$iv = false;
        Iterator it = $receiver$iv.iterator();
        while (true) {
            obj = null;
            if (it.hasNext()) {
                Object element$iv = it.next();
                KParameter it2 = (KParameter) element$iv;
                if (it2.getKind() == KParameter.Kind.EXTENSION_RECEIVER) {
                    if (found$iv) {
                        break;
                    }
                    single$iv = element$iv;
                    found$iv = true;
                }
            } else {
                if (!found$iv) {
                    break;
                }
                obj = single$iv;
                break;
            }
        }
        return (KParameter) obj;
    }

    @NotNull
    public static final List<KParameter> getValueParameters(@NotNull KCallable<?> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Iterable $receiver$iv = receiver$0.getParameters();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $receiver$iv) {
            KParameter it = (KParameter) element$iv$iv;
            if (it.getKind() == KParameter.Kind.VALUE) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        return (List) destination$iv$iv;
    }

    @SinceKotlin(version = "1.1")
    @Nullable
    public static final KParameter findParameterByName(@NotNull KCallable<?> receiver$0, @NotNull String name) {
        Object obj;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(name, "name");
        Iterable $receiver$iv = receiver$0.getParameters();
        Object single$iv = null;
        boolean found$iv = false;
        Iterator it = $receiver$iv.iterator();
        while (true) {
            obj = null;
            if (it.hasNext()) {
                Object element$iv = it.next();
                KParameter it2 = (KParameter) element$iv;
                if (Intrinsics.areEqual(it2.getName(), name)) {
                    if (found$iv) {
                        break;
                    }
                    single$iv = element$iv;
                    found$iv = true;
                }
            } else {
                if (!found$iv) {
                    break;
                }
                obj = single$iv;
                break;
            }
        }
        return (KParameter) obj;
    }

    @SinceKotlin(version = "1.3")
    @Nullable
    public static final <R> Object callSuspend(@NotNull KCallable<? extends R> kCallable, @NotNull Object[] args, @NotNull Continuation<? super R> continuation) {
        if (!kCallable.isSuspend()) {
            return kCallable.call(Arrays.copyOf(args, args.length));
        }
        if (!(kCallable instanceof KFunction)) {
            throw new IllegalArgumentException("Cannot callSuspend on a property " + kCallable + ": suspend properties are not supported yet");
        }
        SpreadBuilder spreadBuilder = new SpreadBuilder(2);
        spreadBuilder.addSpread(args);
        spreadBuilder.add(continuation);
        R rCall = kCallable.call(spreadBuilder.toArray(new Object[spreadBuilder.size()]));
        if (rCall == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return rCall;
    }

    @SinceKotlin(version = "1.3")
    @Nullable
    public static final <R> Object callSuspendBy(@NotNull KCallable<? extends R> kCallable, @NotNull Map<KParameter, ? extends Object> map, @NotNull Continuation<? super R> continuation) throws IllegalCallableAccessException {
        if (!kCallable.isSuspend()) {
            return kCallable.callBy(map);
        }
        if (!(kCallable instanceof KFunction)) {
            throw new IllegalArgumentException("Cannot callSuspendBy on a property " + kCallable + ": suspend properties are not supported yet");
        }
        KCallableImpl<?> kCallableImplAsKCallableImpl = UtilKt.asKCallableImpl(kCallable);
        if (kCallableImplAsKCallableImpl == null) {
            throw new KotlinReflectionInternalError("This callable does not support a default call: " + kCallable);
        }
        Object objCallDefaultMethod$kotlin_reflect_api = kCallableImplAsKCallableImpl.callDefaultMethod$kotlin_reflect_api(map, continuation);
        if (objCallDefaultMethod$kotlin_reflect_api == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return objCallDefaultMethod$kotlin_reflect_api;
    }
}
