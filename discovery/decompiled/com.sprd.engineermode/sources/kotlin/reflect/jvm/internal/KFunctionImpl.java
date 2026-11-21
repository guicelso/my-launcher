package kotlin.reflect.jvm.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionBase;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KFunction;
import kotlin.reflect.KParameter;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.calls.AnnotationConstructorCaller;
import kotlin.reflect.jvm.internal.calls.Caller;
import kotlin.reflect.jvm.internal.calls.CallerImpl;
import kotlin.reflect.jvm.internal.calls.CallerKt;
import kotlin.reflect.jvm.internal.calls.InlineClassAwareCallerKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.InlineClassManglingRulesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: KFunctionImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0000\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00032\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00042\u00020\u0005B)\b\u0016\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0002¢\u0006\u0002\u0010\fB\u0017\b\u0016\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fB5\b\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0002¢\u0006\u0002\u0010\u0012J&\u00102\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u000304032\n\u00105\u001a\u0006\u0012\u0002\b\u0003042\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u00106\u001a\u0002072\u0006\u00105\u001a\u000208H\u0002J\u0010\u00109\u001a\u0002072\u0006\u00105\u001a\u000208H\u0002J\u0010\u0010:\u001a\u0002072\u0006\u00105\u001a\u000208H\u0002J\u0013\u0010;\u001a\u00020)2\b\u0010<\u001a\u0004\u0018\u00010\u0002H\u0096\u0002J\b\u0010=\u001a\u00020\u0014H\u0016J\b\u0010>\u001a\u00020\tH\u0016R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0016\u0010\u000b\u001a\u0004\u0018\u00010\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u001f\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u001a8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R!\u0010!\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001a8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\u001e\u001a\u0004\b\"\u0010\u001cR\u001b\u0010\r\u001a\u00020\u000e8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b&\u0010'\u001a\u0004\b$\u0010%R\u0014\u0010(\u001a\u00020)8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010*R\u0014\u0010+\u001a\u00020)8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b+\u0010*R\u0014\u0010,\u001a\u00020)8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b,\u0010*R\u0014\u0010-\u001a\u00020)8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b-\u0010*R\u0014\u0010.\u001a\u00020)8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b.\u0010*R\u0014\u0010/\u001a\u00020)8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b/\u0010*R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b0\u00101R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0002X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006?"}, d2 = {"Lkotlin/reflect/jvm/internal/KFunctionImpl;", "Lkotlin/reflect/jvm/internal/KCallableImpl;", "", "Lkotlin/reflect/KFunction;", "Lkotlin/jvm/internal/FunctionBase;", "Lkotlin/reflect/jvm/internal/FunctionWithAllInvokes;", "container", "Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;", "name", "", "signature", "boundReceiver", "(Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V", "descriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/FunctionDescriptor;", "(Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;)V", "descriptorInitialValue", "rawBoundReceiver", "(Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;Ljava/lang/String;Ljava/lang/String;Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Ljava/lang/Object;)V", "arity", "", "getArity", "()I", "getBoundReceiver", "()Ljava/lang/Object;", "caller", "Lkotlin/reflect/jvm/internal/calls/Caller;", "getCaller", "()Lkotlin/reflect/jvm/internal/calls/Caller;", "caller$delegate", "Lkotlin/reflect/jvm/internal/ReflectProperties$LazyVal;", "getContainer", "()Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;", "defaultCaller", "getDefaultCaller", "defaultCaller$delegate", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "descriptor$delegate", "Lkotlin/reflect/jvm/internal/ReflectProperties$LazySoftVal;", "isBound", "", "()Z", "isExternal", "isInfix", "isInline", "isOperator", "isSuspend", "getName", "()Ljava/lang/String;", "createConstructorCaller", "Lkotlin/reflect/jvm/internal/calls/CallerImpl;", "Ljava/lang/reflect/Constructor;", "member", "createInstanceMethodCaller", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method;", "Ljava/lang/reflect/Method;", "createJvmStaticInObjectCaller", "createStaticMethodCaller", "equals", "other", "hashCode", "toString", "kotlin-reflect-api"}, k = 1, mv = {1, 1, 13})
public final class KFunctionImpl extends KCallableImpl<Object> implements FunctionBase<Object>, KFunction<Object>, FunctionWithAllInvokes {
    static final /* synthetic */ KProperty[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(KFunctionImpl.class), "descriptor", "getDescriptor()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(KFunctionImpl.class), "caller", "getCaller()Lkotlin/reflect/jvm/internal/calls/Caller;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(KFunctionImpl.class), "defaultCaller", "getDefaultCaller()Lkotlin/reflect/jvm/internal/calls/Caller;"))};

    /* JADX INFO: renamed from: caller$delegate, reason: from kotlin metadata */
    @NotNull
    private final ReflectProperties.LazyVal caller;

    @NotNull
    private final KDeclarationContainerImpl container;

    /* JADX INFO: renamed from: defaultCaller$delegate, reason: from kotlin metadata */
    @Nullable
    private final ReflectProperties.LazyVal defaultCaller;

    /* JADX INFO: renamed from: descriptor$delegate, reason: from kotlin metadata */
    @NotNull
    private final ReflectProperties.LazySoftVal descriptor;
    private final Object rawBoundReceiver;
    private final String signature;

    @Override // kotlin.reflect.jvm.internal.KCallableImpl
    @NotNull
    public Caller<?> getCaller() {
        return (Caller) this.caller.getValue(this, $$delegatedProperties[1]);
    }

    @Override // kotlin.reflect.jvm.internal.KCallableImpl
    @Nullable
    public Caller<?> getDefaultCaller() {
        return (Caller) this.defaultCaller.getValue(this, $$delegatedProperties[2]);
    }

    @Override // kotlin.reflect.jvm.internal.KCallableImpl
    @NotNull
    public FunctionDescriptor getDescriptor() {
        return (FunctionDescriptor) this.descriptor.getValue(this, $$delegatedProperties[0]);
    }

    private KFunctionImpl(KDeclarationContainerImpl container, final String name, String signature, FunctionDescriptor descriptorInitialValue, Object rawBoundReceiver) {
        this.container = container;
        this.signature = signature;
        this.rawBoundReceiver = rawBoundReceiver;
        this.descriptor = ReflectProperties.lazySoft(descriptorInitialValue, new Function0<FunctionDescriptor>() { // from class: kotlin.reflect.jvm.internal.KFunctionImpl$descriptor$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final FunctionDescriptor invoke() {
                return this.this$0.getContainer().findFunctionDescriptor(name, this.this$0.signature);
            }
        });
        this.caller = ReflectProperties.lazy(new Function0<Caller<? extends Member>>() { // from class: kotlin.reflect.jvm.internal.KFunctionImpl$caller$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Caller<? extends Member> invoke() {
                Member member;
                CallerImpl.Method methodCreateJvmStaticInObjectCaller;
                CallerImpl.Method methodCreateConstructorCaller;
                JvmFunctionSignature jvmSignature = RuntimeTypeMapper.INSTANCE.mapSignature(this.this$0.getDescriptor());
                if (jvmSignature instanceof JvmFunctionSignature.KotlinConstructor) {
                    if (this.this$0.isAnnotationConstructor()) {
                        Class<?> jClass = this.this$0.getContainer().getJClass();
                        Iterable $receiver$iv = this.this$0.getParameters();
                        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
                        for (Object item$iv$iv : $receiver$iv) {
                            String name2 = ((KParameter) item$iv$iv).getName();
                            if (name2 == null) {
                                Intrinsics.throwNpe();
                            }
                            destination$iv$iv.add(name2);
                        }
                        return new AnnotationConstructorCaller(jClass, (List) destination$iv$iv, AnnotationConstructorCaller.CallMode.POSITIONAL_CALL, AnnotationConstructorCaller.Origin.KOTLIN, null, 16, null);
                    }
                    member = this.this$0.getContainer().findConstructorBySignature(((JvmFunctionSignature.KotlinConstructor) jvmSignature).getConstructorDesc());
                } else if (jvmSignature instanceof JvmFunctionSignature.KotlinFunction) {
                    member = this.this$0.getContainer().findMethodBySignature(((JvmFunctionSignature.KotlinFunction) jvmSignature).getMethodName(), ((JvmFunctionSignature.KotlinFunction) jvmSignature).getMethodDesc());
                } else if (jvmSignature instanceof JvmFunctionSignature.JavaMethod) {
                    member = ((JvmFunctionSignature.JavaMethod) jvmSignature).getMethod();
                } else {
                    if (!(jvmSignature instanceof JvmFunctionSignature.JavaConstructor)) {
                        if (!(jvmSignature instanceof JvmFunctionSignature.FakeJavaAnnotationConstructor)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        List<Method> methods = ((JvmFunctionSignature.FakeJavaAnnotationConstructor) jvmSignature).getMethods();
                        Class<?> jClass2 = this.this$0.getContainer().getJClass();
                        List<Method> $receiver$iv2 = methods;
                        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv2, 10));
                        for (Object item$iv$iv2 : $receiver$iv2) {
                            Method it = (Method) item$iv$iv2;
                            Intrinsics.checkExpressionValueIsNotNull(it, "it");
                            destination$iv$iv2.add(it.getName());
                        }
                        return new AnnotationConstructorCaller(jClass2, (List) destination$iv$iv2, AnnotationConstructorCaller.CallMode.POSITIONAL_CALL, AnnotationConstructorCaller.Origin.JAVA, methods);
                    }
                    member = ((JvmFunctionSignature.JavaConstructor) jvmSignature).getConstructor();
                }
                if (member instanceof Constructor) {
                    KFunctionImpl kFunctionImpl = this.this$0;
                    methodCreateConstructorCaller = kFunctionImpl.createConstructorCaller((Constructor) member, kFunctionImpl.getDescriptor());
                } else if (member instanceof Method) {
                    if (!Modifier.isStatic(((Method) member).getModifiers())) {
                        methodCreateJvmStaticInObjectCaller = this.this$0.createInstanceMethodCaller((Method) member);
                    } else {
                        methodCreateJvmStaticInObjectCaller = this.this$0.getDescriptor().getAnnotations().mo374findAnnotation(UtilKt.getJVM_STATIC()) != null ? this.this$0.createJvmStaticInObjectCaller((Method) member) : this.this$0.createStaticMethodCaller((Method) member);
                    }
                    methodCreateConstructorCaller = methodCreateJvmStaticInObjectCaller;
                } else {
                    throw new KotlinReflectionInternalError("Could not compute caller for function: " + this.this$0.getDescriptor() + " (member = " + member + ')');
                }
                return InlineClassAwareCallerKt.createInlineClassAwareCallerIfNeeded$default(methodCreateConstructorCaller, this.this$0.getDescriptor(), false, 2, null);
            }
        });
        this.defaultCaller = ReflectProperties.lazy(new Function0<Caller<? extends Member>>() { // from class: kotlin.reflect.jvm.internal.KFunctionImpl$defaultCaller$2
            {
                super(0);
            }

            /* JADX WARN: Code duplicated, block: B:45:0x0169  */
            @Override // kotlin.jvm.functions.Function0
            @Nullable
            public final Caller<? extends Member> invoke() {
                Constructor<?> member;
                CallerImpl.Method methodCreateConstructorCaller;
                CallerImpl.Method methodCreateStaticMethodCaller;
                JvmFunctionSignature jvmSignature = RuntimeTypeMapper.INSTANCE.mapSignature(this.this$0.getDescriptor());
                if (jvmSignature instanceof JvmFunctionSignature.KotlinFunction) {
                    KDeclarationContainerImpl container2 = this.this$0.getContainer();
                    String methodName = ((JvmFunctionSignature.KotlinFunction) jvmSignature).getMethodName();
                    String methodDesc = ((JvmFunctionSignature.KotlinFunction) jvmSignature).getMethodDesc();
                    Member memberMo371getMember = this.this$0.getCaller().mo371getMember();
                    if (memberMo371getMember == null) {
                        Intrinsics.throwNpe();
                    }
                    member = container2.findDefaultMethod(methodName, methodDesc, !Modifier.isStatic(memberMo371getMember.getModifiers()));
                } else if (jvmSignature instanceof JvmFunctionSignature.KotlinConstructor) {
                    if (this.this$0.isAnnotationConstructor()) {
                        Class<?> jClass = this.this$0.getContainer().getJClass();
                        Iterable $receiver$iv = this.this$0.getParameters();
                        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
                        for (Object item$iv$iv : $receiver$iv) {
                            String name2 = ((KParameter) item$iv$iv).getName();
                            if (name2 == null) {
                                Intrinsics.throwNpe();
                            }
                            destination$iv$iv.add(name2);
                        }
                        return new AnnotationConstructorCaller(jClass, (List) destination$iv$iv, AnnotationConstructorCaller.CallMode.CALL_BY_NAME, AnnotationConstructorCaller.Origin.KOTLIN, null, 16, null);
                    }
                    member = this.this$0.getContainer().findDefaultConstructor(((JvmFunctionSignature.KotlinConstructor) jvmSignature).getConstructorDesc());
                } else {
                    if (jvmSignature instanceof JvmFunctionSignature.FakeJavaAnnotationConstructor) {
                        List<Method> methods = ((JvmFunctionSignature.FakeJavaAnnotationConstructor) jvmSignature).getMethods();
                        Class<?> jClass2 = this.this$0.getContainer().getJClass();
                        List<Method> $receiver$iv2 = methods;
                        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv2, 10));
                        for (Object item$iv$iv2 : $receiver$iv2) {
                            Method it = (Method) item$iv$iv2;
                            Intrinsics.checkExpressionValueIsNotNull(it, "it");
                            destination$iv$iv2.add(it.getName());
                        }
                        return new AnnotationConstructorCaller(jClass2, (List) destination$iv$iv2, AnnotationConstructorCaller.CallMode.CALL_BY_NAME, AnnotationConstructorCaller.Origin.JAVA, methods);
                    }
                    member = null;
                }
                if (member instanceof Constructor) {
                    KFunctionImpl kFunctionImpl = this.this$0;
                    methodCreateConstructorCaller = kFunctionImpl.createConstructorCaller((Constructor) member, kFunctionImpl.getDescriptor());
                } else if (member instanceof Method) {
                    if (this.this$0.getDescriptor().getAnnotations().mo374findAnnotation(UtilKt.getJVM_STATIC()) == null) {
                        methodCreateStaticMethodCaller = this.this$0.createStaticMethodCaller((Method) member);
                    } else {
                        DeclarationDescriptor containingDeclaration = this.this$0.getDescriptor().getContainingDeclaration();
                        if (containingDeclaration == null) {
                            throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                        }
                        if (!((ClassDescriptor) containingDeclaration).isCompanionObject()) {
                            methodCreateStaticMethodCaller = this.this$0.createJvmStaticInObjectCaller((Method) member);
                        } else {
                            methodCreateStaticMethodCaller = this.this$0.createStaticMethodCaller((Method) member);
                        }
                    }
                    methodCreateConstructorCaller = methodCreateStaticMethodCaller;
                } else {
                    methodCreateConstructorCaller = null;
                }
                if (methodCreateConstructorCaller != null) {
                    return InlineClassAwareCallerKt.createInlineClassAwareCallerIfNeeded(methodCreateConstructorCaller, this.this$0.getDescriptor(), true);
                }
                return null;
            }
        });
    }

    @Override // kotlin.jvm.functions.Function0
    @Nullable
    public Object invoke() {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this);
    }

    @Override // kotlin.jvm.functions.Function1
    @Nullable
    public Object invoke(@Nullable Object p1) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1);
    }

    @Override // kotlin.jvm.functions.Function2
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2);
    }

    @Override // kotlin.jvm.functions.Function3
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3);
    }

    @Override // kotlin.jvm.functions.Function4
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4);
    }

    @Override // kotlin.jvm.functions.Function5
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5);
    }

    @Override // kotlin.jvm.functions.Function6
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6);
    }

    @Override // kotlin.jvm.functions.Function7
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7);
    }

    @Override // kotlin.jvm.functions.Function8
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8);
    }

    @Override // kotlin.jvm.functions.Function9
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }

    @Override // kotlin.jvm.functions.Function10
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9, @Nullable Object p10) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
    }

    @Override // kotlin.jvm.functions.Function11
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9, @Nullable Object p10, @Nullable Object p11) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11);
    }

    @Override // kotlin.jvm.functions.Function12
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9, @Nullable Object p10, @Nullable Object p11, @Nullable Object p12) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12);
    }

    @Override // kotlin.jvm.functions.Function13
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9, @Nullable Object p10, @Nullable Object p11, @Nullable Object p12, @Nullable Object p13) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13);
    }

    @Override // kotlin.jvm.functions.Function14
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9, @Nullable Object p10, @Nullable Object p11, @Nullable Object p12, @Nullable Object p13, @Nullable Object p14) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14);
    }

    @Override // kotlin.jvm.functions.Function15
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9, @Nullable Object p10, @Nullable Object p11, @Nullable Object p12, @Nullable Object p13, @Nullable Object p14, @Nullable Object p15) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15);
    }

    @Override // kotlin.jvm.functions.Function16
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9, @Nullable Object p10, @Nullable Object p11, @Nullable Object p12, @Nullable Object p13, @Nullable Object p14, @Nullable Object p15, @Nullable Object p16) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16);
    }

    @Override // kotlin.jvm.functions.Function17
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9, @Nullable Object p10, @Nullable Object p11, @Nullable Object p12, @Nullable Object p13, @Nullable Object p14, @Nullable Object p15, @Nullable Object p16, @Nullable Object p17) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17);
    }

    @Override // kotlin.jvm.functions.Function18
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9, @Nullable Object p10, @Nullable Object p11, @Nullable Object p12, @Nullable Object p13, @Nullable Object p14, @Nullable Object p15, @Nullable Object p16, @Nullable Object p17, @Nullable Object p18) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18);
    }

    @Override // kotlin.jvm.functions.Function19
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9, @Nullable Object p10, @Nullable Object p11, @Nullable Object p12, @Nullable Object p13, @Nullable Object p14, @Nullable Object p15, @Nullable Object p16, @Nullable Object p17, @Nullable Object p18, @Nullable Object p19) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19);
    }

    @Override // kotlin.jvm.functions.Function20
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9, @Nullable Object p10, @Nullable Object p11, @Nullable Object p12, @Nullable Object p13, @Nullable Object p14, @Nullable Object p15, @Nullable Object p16, @Nullable Object p17, @Nullable Object p18, @Nullable Object p19, @Nullable Object p20) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20);
    }

    @Override // kotlin.jvm.functions.Function21
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9, @Nullable Object p10, @Nullable Object p11, @Nullable Object p12, @Nullable Object p13, @Nullable Object p14, @Nullable Object p15, @Nullable Object p16, @Nullable Object p17, @Nullable Object p18, @Nullable Object p19, @Nullable Object p20, @Nullable Object p21) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21);
    }

    @Override // kotlin.jvm.functions.Function22
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9, @Nullable Object p10, @Nullable Object p11, @Nullable Object p12, @Nullable Object p13, @Nullable Object p14, @Nullable Object p15, @Nullable Object p16, @Nullable Object p17, @Nullable Object p18, @Nullable Object p19, @Nullable Object p20, @Nullable Object p21, @Nullable Object p22) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22);
    }

    @Override // kotlin.reflect.jvm.internal.KCallableImpl
    @NotNull
    public KDeclarationContainerImpl getContainer() {
        return this.container;
    }

    /* synthetic */ KFunctionImpl(KDeclarationContainerImpl kDeclarationContainerImpl, String str, String str2, FunctionDescriptor functionDescriptor, Object obj, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(kDeclarationContainerImpl, str, str2, functionDescriptor, (i & 16) != 0 ? CallableReference.NO_RECEIVER : obj);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KFunctionImpl(@NotNull KDeclarationContainerImpl container, @NotNull String name, @NotNull String signature, @Nullable Object boundReceiver) {
        this(container, name, signature, null, boundReceiver);
        Intrinsics.checkParameterIsNotNull(container, "container");
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(signature, "signature");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public KFunctionImpl(@NotNull KDeclarationContainerImpl container, @NotNull FunctionDescriptor descriptor) {
        Intrinsics.checkParameterIsNotNull(container, "container");
        Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
        String strAsString = descriptor.getName().asString();
        Intrinsics.checkExpressionValueIsNotNull(strAsString, "descriptor.name.asString()");
        this(container, strAsString, RuntimeTypeMapper.INSTANCE.mapSignature(descriptor).get_signature(), descriptor, null, 16, null);
    }

    @Override // kotlin.reflect.jvm.internal.KCallableImpl
    public boolean isBound() {
        return !Intrinsics.areEqual(this.rawBoundReceiver, CallableReference.NO_RECEIVER);
    }

    @Override // kotlin.reflect.KCallable
    @NotNull
    public String getName() {
        String strAsString = getDescriptor().getName().asString();
        Intrinsics.checkExpressionValueIsNotNull(strAsString, "descriptor.name.asString()");
        return strAsString;
    }

    private final Object getBoundReceiver() {
        return InlineClassAwareCallerKt.coerceToExpectedReceiverType(this.rawBoundReceiver, getDescriptor());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final CallerImpl.Method createStaticMethodCaller(Method member) {
        return isBound() ? new CallerImpl.Method.BoundStatic(member, getBoundReceiver()) : new CallerImpl.Method.Static(member);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final CallerImpl.Method createJvmStaticInObjectCaller(Method member) {
        return isBound() ? new CallerImpl.Method.BoundJvmStaticInObject(member) : new CallerImpl.Method.JvmStaticInObject(member);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final CallerImpl.Method createInstanceMethodCaller(Method member) {
        return isBound() ? new CallerImpl.Method.BoundInstance(member, getBoundReceiver()) : new CallerImpl.Method.Instance(member);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final CallerImpl<Constructor<?>> createConstructorCaller(Constructor<?> member, FunctionDescriptor descriptor) {
        if (InlineClassManglingRulesKt.shouldHideConstructorDueToInlineClassTypeValueParameters(descriptor)) {
            if (isBound()) {
                return new CallerImpl.AccessorForHiddenBoundConstructor(member, getBoundReceiver());
            }
            return new CallerImpl.AccessorForHiddenConstructor(member);
        }
        if (isBound()) {
            return new CallerImpl.BoundConstructor(member, getBoundReceiver());
        }
        return new CallerImpl.Constructor(member);
    }

    @Override // kotlin.jvm.internal.FunctionBase
    public int getArity() {
        return CallerKt.getArity(getCaller());
    }

    @Override // kotlin.reflect.KFunction
    public boolean isInline() {
        return getDescriptor().isInline();
    }

    @Override // kotlin.reflect.KFunction
    public boolean isExternal() {
        return getDescriptor().isExternal();
    }

    @Override // kotlin.reflect.KFunction
    public boolean isOperator() {
        return getDescriptor().isOperator();
    }

    @Override // kotlin.reflect.KFunction
    public boolean isInfix() {
        return getDescriptor().isInfix();
    }

    @Override // kotlin.reflect.KCallable
    public boolean isSuspend() {
        return getDescriptor().isSuspend();
    }

    public boolean equals(@Nullable Object other) {
        KFunctionImpl that = UtilKt.asKFunctionImpl(other);
        return that != null && Intrinsics.areEqual(getContainer(), that.getContainer()) && Intrinsics.areEqual(getName(), that.getName()) && Intrinsics.areEqual(this.signature, that.signature) && Intrinsics.areEqual(this.rawBoundReceiver, that.rawBoundReceiver);
    }

    public int hashCode() {
        return (((getContainer().hashCode() * 31) + getName().hashCode()) * 31) + this.signature.hashCode();
    }

    @NotNull
    public String toString() {
        return ReflectionObjectRenderer.INSTANCE.renderFunction(getDescriptor());
    }
}
