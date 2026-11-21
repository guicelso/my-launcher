package com.unisoc.engineermode.core.factory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.reflect.KClass;
import kotlin.reflect.KFunction;
import kotlin.reflect.KProperty0;
import kotlin.reflect.full.KClasses;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: ImplementationFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0013\u001a\u00020\b2\u000e\u0010\u0014\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\u0007H\u0007J\u0018\u0010\u0013\u001a\u00020\b2\u000e\u0010\u0014\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\u0015H\u0007J<\u0010\u0016\u001a\u00020\u00042\u000e\u0010\u0014\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\u00072\"\u0010\u0017\u001a\u001e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0006j\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004`\tH\u0002J7\u0010\u0018\u001a\u001e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0006j\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004`\t2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00040\u001aH\u0002¢\u0006\u0002\u0010\u001bJ\u001c\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u001d2\u0006\u0010\u001e\u001a\u00020\u0004H\u0002J\u000e\u0010\u001f\u001a\u00020 2\u0006\u0010\n\u001a\u00020\u000bJ(\u0010!\u001a\u00020\"2\u000e\u0010#\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\u00072\u000e\u0010$\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\u0007H\u0002J\\\u0010%\u001a\u00020\"2.\u0010&\u001a*\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00110\u0006j\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0011`\t2\"\u0010'\u001a\u001e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0006j\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004`\tH\u0002J\u0006\u0010(\u001a\u00020 R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R:\u0010\u0005\u001a.\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\b0\u0007\u0012\u0004\u0012\u00020\b0\u0006j\u0016\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\b0\u0007\u0012\u0004\u0012\u00020\b`\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lcom/unisoc/engineermode/core/factory/ImplementationFactory;", "", "()V", "TAG", "", "cachedInstance", "Ljava/util/HashMap;", "Ljava/lang/Class;", "Lcom/unisoc/engineermode/core/factory/BaseInterface;", "Lkotlin/collections/HashMap;", "context", "Landroid/content/Context;", "getContext", "()Landroid/content/Context;", "setContext", "(Landroid/content/Context;)V", "impls", "", "Lcom/unisoc/engineermode/core/factory/ImplementationInfo;", "create", "interfaceClass", "Lkotlin/reflect/KClass;", "findClassNameBy", "properties", "getProperties", "props", "", "([Ljava/lang/String;)Ljava/util/HashMap;", "getProperty", "Lkotlin/Pair;", "prop", "init", "", "matchInterface", "", "classInterface", "configInterface", "matchProperties", "classProperties", "configProperties", "reset", "name"}, k = 1, mv = {1, 1, 13})
@SuppressLint({"StaticFieldLeak"})
public final class ImplementationFactory {
    private static final String TAG = "IMPLEMENTATIONFACTORY";

    @Nullable
    private static Context context;
    private static final List<ImplementationInfo> impls;
    public static final ImplementationFactory INSTANCE = new ImplementationFactory();
    private static final HashMap<Class<? extends BaseInterface>, BaseInterface> cachedInstance = new HashMap<>();

    static {
        Object next;
        Object next2;
        KProperty0 it;
        KFunction it2;
        Class<?> cls = Class.forName("com.unisoc.engineermode.core.AllImplementations");
        Intrinsics.checkExpressionValueIsNotNull(cls, "Class.forName(clsName)");
        KClass cls2 = JvmClassMappingKt.getKotlinClass(cls);
        Iterator<T> it3 = KClasses.getStaticFunctions(cls2).iterator();
        do {
            if (!it3.hasNext()) {
                next = null;
                break;
            } else {
                next = it3.next();
                it2 = (KFunction) next;
            }
        } while (!Intrinsics.areEqual(it2.getName(), "init"));
        if (next == null) {
            Intrinsics.throwNpe();
        }
        ((KFunction) next).call(new Object[0]);
        Iterator<T> it4 = KClasses.getStaticProperties(cls2).iterator();
        do {
            if (!it4.hasNext()) {
                next2 = null;
                break;
            } else {
                next2 = it4.next();
                it = (KProperty0) next2;
            }
        } while (!Intrinsics.areEqual(it.getName(), "impls"));
        KProperty0 kProperty0 = (KProperty0) next2;
        Object obj = kProperty0 != null ? kProperty0.get() : null;
        if (obj == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.List<com.unisoc.engineermode.core.factory.ImplementationInfo>");
        }
        impls = (List) obj;
    }

    private ImplementationFactory() {
    }

    @Nullable
    public final Context getContext() {
        return context;
    }

    public final void setContext(@Nullable Context context2) {
        context = context2;
    }

    public final void init(@NotNull Context context2) {
        Intrinsics.checkParameterIsNotNull(context2, "context");
        context = context2;
        Config.init$default(Config.INSTANCE, null, 1, null);
    }

    public final void reset() {
        cachedInstance.clear();
    }

    @JvmStatic
    @NotNull
    public static final BaseInterface create(@NotNull KClass<? extends BaseInterface> interfaceClass) {
        Intrinsics.checkParameterIsNotNull(interfaceClass, "interfaceClass");
        return create((Class<? extends BaseInterface>) JvmClassMappingKt.getJavaClass((KClass) interfaceClass));
    }

    @JvmStatic
    @NotNull
    public static final synchronized BaseInterface create(@NotNull Class<? extends BaseInterface> interfaceClass) {
        BaseInterface instance;
        Intrinsics.checkParameterIsNotNull(interfaceClass, "interfaceClass");
        if (cachedInstance.containsKey(interfaceClass)) {
            BaseInterface baseInterface = cachedInstance.get(interfaceClass);
            if (baseInterface == null) {
                Intrinsics.throwNpe();
            }
            return baseInterface;
        }
        String target = INSTANCE.findClassNameBy(interfaceClass, Config.INSTANCE.getData().getProperties());
        Class<?> cls = Class.forName(target);
        Intrinsics.checkExpressionValueIsNotNull(cls, "cls");
        if (JvmClassMappingKt.getKotlinClass(cls).getObjectInstance() != null) {
            Object objectInstance = JvmClassMappingKt.getKotlinClass(cls).getObjectInstance();
            if (objectInstance == null) {
                throw new TypeCastException("null cannot be cast to non-null type com.unisoc.engineermode.core.factory.BaseInterface");
            }
            instance = (BaseInterface) objectInstance;
        } else {
            try {
                Object objInvoke = cls.getMethod("newInstance", new Class[0]).invoke(null, new Object[0]);
                if (objInvoke == null) {
                    throw new TypeCastException("null cannot be cast to non-null type com.unisoc.engineermode.core.factory.BaseInterface");
                }
                instance = (BaseInterface) objInvoke;
            } catch (NoSuchMethodException e) {
                Object objNewInstance = cls.newInstance();
                if (objNewInstance == null) {
                    throw new TypeCastException("null cannot be cast to non-null type com.unisoc.engineermode.core.factory.BaseInterface");
                }
                instance = (BaseInterface) objNewInstance;
            }
        }
        cachedInstance.put(interfaceClass, instance);
        return instance;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [T, com.unisoc.engineermode.core.factory.ImplementationInfo] */
    /* JADX WARN: Type inference failed for: r2v13, types: [T, com.unisoc.engineermode.core.factory.ImplementationInfo] */
    private final String findClassNameBy(final Class<? extends BaseInterface> interfaceClass, final HashMap<String, String> properties) {
        final Ref.ObjectRef target = new Ref.ObjectRef();
        target.element = (ImplementationInfo) 0;
        List<ImplementationInfo> list = impls;
        if (list == null) {
            Intrinsics.throwNpe();
        }
        List findResults = SequencesKt.toList(SequencesKt.filter(SequencesKt.onEach(SequencesKt.filter(SequencesKt.onEach(SequencesKt.filter(CollectionsKt.asSequence(list), new Function1<ImplementationInfo, Boolean>() { // from class: com.unisoc.engineermode.core.factory.ImplementationFactory$findClassNameBy$findResults$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(ImplementationInfo implementationInfo) {
                return Boolean.valueOf(invoke2(implementationInfo));
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final boolean invoke2(@NotNull ImplementationInfo it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return ImplementationFactory.INSTANCE.matchInterface(it.getInterfaceClass(), interfaceClass);
            }
        }), new Function1<ImplementationInfo, Unit>() { // from class: com.unisoc.engineermode.core.factory.ImplementationFactory$findClassNameBy$findResults$2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ImplementationInfo implementationInfo) {
                invoke2(implementationInfo);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull ImplementationInfo it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                Log.d("IMPLEMENTATIONFACTORY", "interfaced matched: " + it.getImplementClassName());
            }
        }), new Function1<ImplementationInfo, Boolean>() { // from class: com.unisoc.engineermode.core.factory.ImplementationFactory$findClassNameBy$findResults$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(ImplementationInfo implementationInfo) {
                return Boolean.valueOf(invoke2(implementationInfo));
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final boolean invoke2(@NotNull ImplementationInfo it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return ImplementationFactory.INSTANCE.matchProperties(it.getProperties(), properties);
            }
        }), new Function1<ImplementationInfo, Unit>() { // from class: com.unisoc.engineermode.core.factory.ImplementationFactory$findClassNameBy$findResults$4
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ImplementationInfo implementationInfo) {
                invoke2(implementationInfo);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull ImplementationInfo it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                Log.d("IMPLEMENTATIONFACTORY", "properties matched: " + it.getImplementClassName());
            }
        }), new Function1<ImplementationInfo, Boolean>() { // from class: com.unisoc.engineermode.core.factory.ImplementationFactory$findClassNameBy$findResults$5
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(ImplementationInfo implementationInfo) {
                return Boolean.valueOf(invoke2(implementationInfo));
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final boolean invoke2(@NotNull ImplementationInfo it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                if (it.getProperties().isEmpty()) {
                    target.element = it;
                    return false;
                }
                return true;
            }
        }));
        if (findResults.size() > 1) {
            Log.e(TAG, "find interface " + interfaceClass + ", get more than one result:");
            if (_Assertions.ENABLED) {
                throw new AssertionError("Assertion failed");
            }
        } else if (findResults.isEmpty()) {
            boolean z = ((ImplementationInfo) target.element) != null;
            if (_Assertions.ENABLED && !z) {
                throw new AssertionError("Assertion failed");
            }
            StringBuilder sb = new StringBuilder();
            sb.append("find interface ");
            sb.append(interfaceClass);
            sb.append(", get none result, use default: ");
            ImplementationInfo implementationInfo = (ImplementationInfo) target.element;
            if (implementationInfo == null) {
                Intrinsics.throwNpe();
            }
            sb.append(implementationInfo.getImplementClassName());
            Log.e(TAG, sb.toString());
        } else {
            target.element = (ImplementationInfo) findResults.get(0);
        }
        ImplementationInfo implementationInfo2 = (ImplementationInfo) target.element;
        if (implementationInfo2 == null) {
            Intrinsics.throwNpe();
        }
        return implementationInfo2.getImplementClassName();
    }

    private final Pair<String, String> getProperty(String prop) {
        List arr = StringsKt.split$default((CharSequence) prop, new String[]{":"}, false, 0, 6, (Object) null);
        boolean z = arr.size() == 2;
        if (_Assertions.ENABLED && !z) {
            throw new AssertionError("Assertion failed");
        }
        String str = (String) arr.get(0);
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        }
        String string = StringsKt.trim((CharSequence) str).toString();
        String str2 = (String) arr.get(1);
        if (str2 != null) {
            return new Pair<>(string, StringsKt.trim((CharSequence) str2).toString());
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
    }

    private final HashMap<String, String> getProperties(String[] props) {
        HashMap<String, String> map = new HashMap<>();
        for (String str : props) {
            Pair<String, String> property = INSTANCE.getProperty(str);
            String k = property.component1();
            String v = property.component2();
            map.put(k, v);
        }
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean matchInterface(Class<? extends BaseInterface> classInterface, Class<? extends BaseInterface> configInterface) {
        return Intrinsics.areEqual(classInterface, configInterface);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean matchProperties(HashMap<String, List<String>> classProperties, HashMap<String, String> configProperties) {
        boolean zContains;
        HashMap<String, List<String>> $receiver$iv = classProperties;
        Map destination$iv$iv = new LinkedHashMap();
        for (Map.Entry<String, List<String>> entry : $receiver$iv.entrySet()) {
            String k = entry.getKey();
            List<String> value = entry.getValue();
            String value2 = configProperties.get(k);
            if (value2 == null) {
                zContains = true;
            } else {
                zContains = value.contains(value2);
            }
            if (!zContains) {
                destination$iv$iv.put(entry.getKey(), entry.getValue());
            }
        }
        return destination$iv$iv.isEmpty();
    }
}
