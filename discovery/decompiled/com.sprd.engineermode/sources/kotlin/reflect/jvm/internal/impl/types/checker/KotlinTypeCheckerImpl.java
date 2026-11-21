package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes4.dex */
public class KotlinTypeCheckerImpl implements KotlinTypeChecker {
    private final TypeCheckingProcedure procedure;

    @NotNull
    public static KotlinTypeChecker withAxioms(@NotNull final KotlinTypeChecker.TypeConstructorEquality equalityAxioms) {
        return new KotlinTypeCheckerImpl(new TypeCheckingProcedure(new TypeCheckerProcedureCallbacksImpl() { // from class: kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeCheckerImpl.1
            @Override // kotlin.reflect.jvm.internal.impl.types.checker.TypeCheckerProcedureCallbacksImpl, kotlin.reflect.jvm.internal.impl.types.checker.TypeCheckingProcedureCallbacks
            public boolean assertEqualTypeConstructors(@NotNull TypeConstructor constructor1, @NotNull TypeConstructor constructor2) {
                return constructor1.equals(constructor2) || equalityAxioms.equals(constructor1, constructor2);
            }
        }));
    }

    protected KotlinTypeCheckerImpl(@NotNull TypeCheckingProcedure procedure) {
        this.procedure = procedure;
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker
    public boolean isSubtypeOf(@NotNull KotlinType subtype, @NotNull KotlinType supertype) {
        return this.procedure.isSubtypeOf(subtype, supertype);
    }

    @Override // kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker
    public boolean equalTypes(@NotNull KotlinType a, @NotNull KotlinType b) {
        return this.procedure.equalTypes(a, b);
    }
}
