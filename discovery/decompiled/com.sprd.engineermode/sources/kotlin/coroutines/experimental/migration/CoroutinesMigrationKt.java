package kotlin.coroutines.experimental.migration;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.experimental.Continuation;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.coroutines.experimental.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: CoroutinesMigration.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0007\u001a\f\u0010\u0004\u001a\u00020\u0005*\u00020\u0006H\u0007\u001a\f\u0010\u0007\u001a\u00020\b*\u00020\tH\u0007\u001a\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0007\u001a\f\u0010\u000b\u001a\u00020\u0006*\u00020\u0005H\u0007\u001a\f\u0010\f\u001a\u00020\t*\u00020\bH\u0007\u001a^\u0010\r\u001a\"\u0012\u0004\u0012\u0002H\u000f\u0012\u0004\u0012\u0002H\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u000e\"\u0004\b\u0000\u0010\u000f\"\u0004\b\u0001\u0010\u0010\"\u0004\b\u0002\u0010\u0011*\"\u0012\u0004\u0012\u0002H\u000f\u0012\u0004\u0012\u0002H\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u000eH\u0000\u001aL\u0010\r\u001a\u001c\u0012\u0004\u0012\u0002H\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0013\"\u0004\b\u0000\u0010\u000f\"\u0004\b\u0001\u0010\u0011*\u001c\u0012\u0004\u0012\u0002H\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0013H\u0000\u001a:\u0010\r\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0014\"\u0004\b\u0000\u0010\u0011*\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0014H\u0000¨\u0006\u0015"}, d2 = {"toContinuation", "Lkotlin/coroutines/Continuation;", "T", "Lkotlin/coroutines/experimental/Continuation;", "toContinuationInterceptor", "Lkotlin/coroutines/ContinuationInterceptor;", "Lkotlin/coroutines/experimental/ContinuationInterceptor;", "toCoroutineContext", "Lkotlin/coroutines/CoroutineContext;", "Lkotlin/coroutines/experimental/CoroutineContext;", "toExperimentalContinuation", "toExperimentalContinuationInterceptor", "toExperimentalCoroutineContext", "toExperimentalSuspendFunction", "Lkotlin/Function3;", "T1", "T2", "R", "", "Lkotlin/Function2;", "Lkotlin/Function1;", "kotlin-stdlib_coroutinesExperimental"}, k = 2, mv = {1, 1, 13})
public final class CoroutinesMigrationKt {
    @SinceKotlin(version = "1.3")
    @NotNull
    public static final <T> Continuation<T> toExperimentalContinuation(@NotNull kotlin.coroutines.Continuation<? super T> receiver$0) {
        Continuation<T> continuation;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        ContinuationMigration continuationMigration = (ContinuationMigration) (!(receiver$0 instanceof ContinuationMigration) ? null : receiver$0);
        return (continuationMigration == null || (continuation = continuationMigration.getContinuation()) == null) ? new ExperimentalContinuationMigration(receiver$0) : continuation;
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final <T> kotlin.coroutines.Continuation<T> toContinuation(@NotNull Continuation<? super T> receiver$0) {
        kotlin.coroutines.Continuation<T> continuation;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        ExperimentalContinuationMigration experimentalContinuationMigration = (ExperimentalContinuationMigration) (!(receiver$0 instanceof ExperimentalContinuationMigration) ? null : receiver$0);
        return (experimentalContinuationMigration == null || (continuation = experimentalContinuationMigration.getContinuation()) == null) ? new ContinuationMigration(receiver$0) : continuation;
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final CoroutineContext toExperimentalCoroutineContext(@NotNull kotlin.coroutines.CoroutineContext receiver$0) {
        EmptyCoroutineContext original;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        ContinuationInterceptor interceptor = (ContinuationInterceptor) receiver$0.get(ContinuationInterceptor.INSTANCE);
        ContextMigration migration = (ContextMigration) receiver$0.get(ContextMigration.INSTANCE);
        kotlin.coroutines.CoroutineContext remainder = receiver$0.minusKey(ContinuationInterceptor.INSTANCE).minusKey(ContextMigration.INSTANCE);
        if (migration == null || (original = migration.getContext()) == null) {
            original = EmptyCoroutineContext.INSTANCE;
        }
        CoroutineContext result = remainder == kotlin.coroutines.EmptyCoroutineContext.INSTANCE ? original : original.plus(new ExperimentalContextMigration(remainder));
        return interceptor == null ? result : result.plus(toExperimentalContinuationInterceptor(interceptor));
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final kotlin.coroutines.CoroutineContext toCoroutineContext(@NotNull CoroutineContext receiver$0) {
        kotlin.coroutines.EmptyCoroutineContext original;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        kotlin.coroutines.experimental.ContinuationInterceptor interceptor = (kotlin.coroutines.experimental.ContinuationInterceptor) receiver$0.get(kotlin.coroutines.experimental.ContinuationInterceptor.INSTANCE);
        ExperimentalContextMigration migration = (ExperimentalContextMigration) receiver$0.get(ExperimentalContextMigration.INSTANCE);
        CoroutineContext remainder = receiver$0.minusKey(kotlin.coroutines.experimental.ContinuationInterceptor.INSTANCE).minusKey(ExperimentalContextMigration.INSTANCE);
        if (migration == null || (original = migration.getContext()) == null) {
            original = kotlin.coroutines.EmptyCoroutineContext.INSTANCE;
        }
        kotlin.coroutines.CoroutineContext result = remainder == EmptyCoroutineContext.INSTANCE ? original : original.plus(new ContextMigration(remainder));
        return interceptor == null ? result : result.plus(toContinuationInterceptor(interceptor));
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final kotlin.coroutines.experimental.ContinuationInterceptor toExperimentalContinuationInterceptor(@NotNull ContinuationInterceptor receiver$0) {
        kotlin.coroutines.experimental.ContinuationInterceptor interceptor;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        ContinuationInterceptorMigration continuationInterceptorMigration = (ContinuationInterceptorMigration) (!(receiver$0 instanceof ContinuationInterceptorMigration) ? null : receiver$0);
        return (continuationInterceptorMigration == null || (interceptor = continuationInterceptorMigration.getInterceptor()) == null) ? new ExperimentalContinuationInterceptorMigration(receiver$0) : interceptor;
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final ContinuationInterceptor toContinuationInterceptor(@NotNull kotlin.coroutines.experimental.ContinuationInterceptor receiver$0) {
        ContinuationInterceptor interceptor;
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        ExperimentalContinuationInterceptorMigration experimentalContinuationInterceptorMigration = (ExperimentalContinuationInterceptorMigration) (!(receiver$0 instanceof ExperimentalContinuationInterceptorMigration) ? null : receiver$0);
        return (experimentalContinuationInterceptorMigration == null || (interceptor = experimentalContinuationInterceptorMigration.getInterceptor()) == null) ? new ContinuationInterceptorMigration(receiver$0) : interceptor;
    }

    @NotNull
    public static final <R> Function1<Continuation<? super R>, Object> toExperimentalSuspendFunction(@NotNull Function1<? super kotlin.coroutines.Continuation<? super R>, ? extends Object> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return new ExperimentalSuspendFunction0Migration(receiver$0);
    }

    @NotNull
    public static final <T1, R> Function2<T1, Continuation<? super R>, Object> toExperimentalSuspendFunction(@NotNull Function2<? super T1, ? super kotlin.coroutines.Continuation<? super R>, ? extends Object> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return new ExperimentalSuspendFunction1Migration(receiver$0);
    }

    @NotNull
    public static final <T1, T2, R> Function3<T1, T2, Continuation<? super R>, Object> toExperimentalSuspendFunction(@NotNull Function3<? super T1, ? super T2, ? super kotlin.coroutines.Continuation<? super R>, ? extends Object> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return new ExperimentalSuspendFunction2Migration(receiver$0);
    }
}
