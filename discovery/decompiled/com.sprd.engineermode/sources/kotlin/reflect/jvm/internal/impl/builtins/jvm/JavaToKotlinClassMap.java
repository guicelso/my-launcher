package kotlin.reflect.jvm.internal.impl.builtins.jvm;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.CompanionObjectMapping;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.functions.FunctionClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.FqNamesUtilKt;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.name.SpecialNames;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: JavaToKotlinClassMap.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class JavaToKotlinClassMap {
    private static final ClassId FUNCTION_N_CLASS_ID;

    @NotNull
    private static final FqName FUNCTION_N_FQ_NAME;
    public static final JavaToKotlinClassMap INSTANCE;
    private static final ClassId K_FUNCTION_CLASS_ID;
    private static final String NUMBERED_FUNCTION_PREFIX;
    private static final String NUMBERED_K_FUNCTION_PREFIX;
    private static final String NUMBERED_K_SUSPEND_FUNCTION_PREFIX;
    private static final String NUMBERED_SUSPEND_FUNCTION_PREFIX;
    private static final HashMap<FqNameUnsafe, ClassId> javaToKotlin;
    private static final HashMap<FqNameUnsafe, ClassId> kotlinToJava;

    @NotNull
    private static final List<PlatformMutabilityMapping> mutabilityMappings;
    private static final HashMap<FqNameUnsafe, FqName> mutableToReadOnly;
    private static final HashMap<FqNameUnsafe, FqName> readOnlyToMutable;

    static {
        JavaToKotlinClassMap this_$iv = new JavaToKotlinClassMap();
        INSTANCE = this_$iv;
        NUMBERED_FUNCTION_PREFIX = FunctionClassDescriptor.Kind.Function.getPackageFqName().toString() + "." + FunctionClassDescriptor.Kind.Function.getClassNamePrefix();
        NUMBERED_K_FUNCTION_PREFIX = FunctionClassDescriptor.Kind.KFunction.getPackageFqName().toString() + "." + FunctionClassDescriptor.Kind.KFunction.getClassNamePrefix();
        NUMBERED_SUSPEND_FUNCTION_PREFIX = FunctionClassDescriptor.Kind.SuspendFunction.getPackageFqName().toString() + "." + FunctionClassDescriptor.Kind.SuspendFunction.getClassNamePrefix();
        NUMBERED_K_SUSPEND_FUNCTION_PREFIX = FunctionClassDescriptor.Kind.KSuspendFunction.getPackageFqName().toString() + "." + FunctionClassDescriptor.Kind.KSuspendFunction.getClassNamePrefix();
        ClassId classId = ClassId.topLevel(new FqName("kotlin.jvm.functions.FunctionN"));
        Intrinsics.checkExpressionValueIsNotNull(classId, "ClassId.topLevel(FqName(…vm.functions.FunctionN\"))");
        FUNCTION_N_CLASS_ID = classId;
        FqName fqNameAsSingleFqName = FUNCTION_N_CLASS_ID.asSingleFqName();
        Intrinsics.checkExpressionValueIsNotNull(fqNameAsSingleFqName, "FUNCTION_N_CLASS_ID.asSingleFqName()");
        FUNCTION_N_FQ_NAME = fqNameAsSingleFqName;
        ClassId classId2 = ClassId.topLevel(new FqName("kotlin.reflect.KFunction"));
        Intrinsics.checkExpressionValueIsNotNull(classId2, "ClassId.topLevel(FqName(…tlin.reflect.KFunction\"))");
        K_FUNCTION_CLASS_ID = classId2;
        javaToKotlin = new HashMap<>();
        kotlinToJava = new HashMap<>();
        mutableToReadOnly = new HashMap<>();
        readOnlyToMutable = new HashMap<>();
        ClassId kotlinReadOnly$iv = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.iterable);
        Intrinsics.checkExpressionValueIsNotNull(kotlinReadOnly$iv, "ClassId.topLevel(FQ_NAMES.iterable)");
        FqName kotlinMutable$iv = KotlinBuiltIns.FQ_NAMES.mutableIterable;
        Intrinsics.checkExpressionValueIsNotNull(kotlinMutable$iv, "FQ_NAMES.mutableIterable");
        int i = 0;
        FqName packageFqName = kotlinReadOnly$iv.getPackageFqName();
        FqName packageFqName2 = kotlinReadOnly$iv.getPackageFqName();
        Intrinsics.checkExpressionValueIsNotNull(packageFqName2, "kotlinReadOnly.packageFqName");
        ClassId mutableClassId$iv = new ClassId(packageFqName, FqNamesUtilKt.tail(kotlinMutable$iv, packageFqName2), false);
        ClassId kotlinReadOnly$iv2 = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.iterator);
        Intrinsics.checkExpressionValueIsNotNull(kotlinReadOnly$iv2, "ClassId.topLevel(FQ_NAMES.iterator)");
        FqName kotlinMutable$iv2 = KotlinBuiltIns.FQ_NAMES.mutableIterator;
        Intrinsics.checkExpressionValueIsNotNull(kotlinMutable$iv2, "FQ_NAMES.mutableIterator");
        FqName packageFqName3 = kotlinReadOnly$iv2.getPackageFqName();
        FqName packageFqName4 = kotlinReadOnly$iv2.getPackageFqName();
        Intrinsics.checkExpressionValueIsNotNull(packageFqName4, "kotlinReadOnly.packageFqName");
        ClassId mutableClassId$iv2 = new ClassId(packageFqName3, FqNamesUtilKt.tail(kotlinMutable$iv2, packageFqName4), false);
        ClassId kotlinReadOnly$iv3 = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.collection);
        Intrinsics.checkExpressionValueIsNotNull(kotlinReadOnly$iv3, "ClassId.topLevel(FQ_NAMES.collection)");
        FqName kotlinMutable$iv3 = KotlinBuiltIns.FQ_NAMES.mutableCollection;
        Intrinsics.checkExpressionValueIsNotNull(kotlinMutable$iv3, "FQ_NAMES.mutableCollection");
        FqName packageFqName5 = kotlinReadOnly$iv3.getPackageFqName();
        FqName packageFqName6 = kotlinReadOnly$iv3.getPackageFqName();
        Intrinsics.checkExpressionValueIsNotNull(packageFqName6, "kotlinReadOnly.packageFqName");
        ClassId mutableClassId$iv3 = new ClassId(packageFqName5, FqNamesUtilKt.tail(kotlinMutable$iv3, packageFqName6), false);
        ClassId kotlinReadOnly$iv4 = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.list);
        Intrinsics.checkExpressionValueIsNotNull(kotlinReadOnly$iv4, "ClassId.topLevel(FQ_NAMES.list)");
        FqName kotlinMutable$iv4 = KotlinBuiltIns.FQ_NAMES.mutableList;
        Intrinsics.checkExpressionValueIsNotNull(kotlinMutable$iv4, "FQ_NAMES.mutableList");
        FqName packageFqName7 = kotlinReadOnly$iv4.getPackageFqName();
        FqName packageFqName8 = kotlinReadOnly$iv4.getPackageFqName();
        Intrinsics.checkExpressionValueIsNotNull(packageFqName8, "kotlinReadOnly.packageFqName");
        ClassId mutableClassId$iv4 = new ClassId(packageFqName7, FqNamesUtilKt.tail(kotlinMutable$iv4, packageFqName8), false);
        ClassId kotlinReadOnly$iv5 = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.set);
        Intrinsics.checkExpressionValueIsNotNull(kotlinReadOnly$iv5, "ClassId.topLevel(FQ_NAMES.set)");
        FqName kotlinMutable$iv5 = KotlinBuiltIns.FQ_NAMES.mutableSet;
        Intrinsics.checkExpressionValueIsNotNull(kotlinMutable$iv5, "FQ_NAMES.mutableSet");
        FqName packageFqName9 = kotlinReadOnly$iv5.getPackageFqName();
        FqName packageFqName10 = kotlinReadOnly$iv5.getPackageFqName();
        Intrinsics.checkExpressionValueIsNotNull(packageFqName10, "kotlinReadOnly.packageFqName");
        ClassId mutableClassId$iv5 = new ClassId(packageFqName9, FqNamesUtilKt.tail(kotlinMutable$iv5, packageFqName10), false);
        ClassId kotlinReadOnly$iv6 = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.listIterator);
        Intrinsics.checkExpressionValueIsNotNull(kotlinReadOnly$iv6, "ClassId.topLevel(FQ_NAMES.listIterator)");
        FqName kotlinMutable$iv6 = KotlinBuiltIns.FQ_NAMES.mutableListIterator;
        Intrinsics.checkExpressionValueIsNotNull(kotlinMutable$iv6, "FQ_NAMES.mutableListIterator");
        FqName packageFqName11 = kotlinReadOnly$iv6.getPackageFqName();
        FqName packageFqName12 = kotlinReadOnly$iv6.getPackageFqName();
        Intrinsics.checkExpressionValueIsNotNull(packageFqName12, "kotlinReadOnly.packageFqName");
        ClassId mutableClassId$iv6 = new ClassId(packageFqName11, FqNamesUtilKt.tail(kotlinMutable$iv6, packageFqName12), false);
        ClassId kotlinReadOnly$iv7 = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.map);
        Intrinsics.checkExpressionValueIsNotNull(kotlinReadOnly$iv7, "ClassId.topLevel(FQ_NAMES.map)");
        FqName kotlinMutable$iv7 = KotlinBuiltIns.FQ_NAMES.mutableMap;
        Intrinsics.checkExpressionValueIsNotNull(kotlinMutable$iv7, "FQ_NAMES.mutableMap");
        FqName packageFqName13 = kotlinReadOnly$iv7.getPackageFqName();
        FqName packageFqName14 = kotlinReadOnly$iv7.getPackageFqName();
        Intrinsics.checkExpressionValueIsNotNull(packageFqName14, "kotlinReadOnly.packageFqName");
        ClassId mutableClassId$iv7 = new ClassId(packageFqName13, FqNamesUtilKt.tail(kotlinMutable$iv7, packageFqName14), false);
        ClassId kotlinReadOnly$iv8 = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.map).createNestedClassId(KotlinBuiltIns.FQ_NAMES.mapEntry.shortName());
        Intrinsics.checkExpressionValueIsNotNull(kotlinReadOnly$iv8, "ClassId.topLevel(FQ_NAME…MES.mapEntry.shortName())");
        FqName kotlinMutable$iv8 = KotlinBuiltIns.FQ_NAMES.mutableMapEntry;
        Intrinsics.checkExpressionValueIsNotNull(kotlinMutable$iv8, "FQ_NAMES.mutableMapEntry");
        FqName packageFqName15 = kotlinReadOnly$iv8.getPackageFqName();
        FqName packageFqName16 = kotlinReadOnly$iv8.getPackageFqName();
        Intrinsics.checkExpressionValueIsNotNull(packageFqName16, "kotlinReadOnly.packageFqName");
        ClassId mutableClassId$iv8 = new ClassId(packageFqName15, FqNamesUtilKt.tail(kotlinMutable$iv8, packageFqName16), false);
        mutabilityMappings = CollectionsKt.listOf((Object[]) new PlatformMutabilityMapping[]{new PlatformMutabilityMapping(this_$iv.classId(Iterable.class), kotlinReadOnly$iv, mutableClassId$iv), new PlatformMutabilityMapping(this_$iv.classId(Iterator.class), kotlinReadOnly$iv2, mutableClassId$iv2), new PlatformMutabilityMapping(this_$iv.classId(Collection.class), kotlinReadOnly$iv3, mutableClassId$iv3), new PlatformMutabilityMapping(this_$iv.classId(List.class), kotlinReadOnly$iv4, mutableClassId$iv4), new PlatformMutabilityMapping(this_$iv.classId(Set.class), kotlinReadOnly$iv5, mutableClassId$iv5), new PlatformMutabilityMapping(this_$iv.classId(ListIterator.class), kotlinReadOnly$iv6, mutableClassId$iv6), new PlatformMutabilityMapping(this_$iv.classId(Map.class), kotlinReadOnly$iv7, mutableClassId$iv7), new PlatformMutabilityMapping(this_$iv.classId(Map.Entry.class), kotlinReadOnly$iv8, mutableClassId$iv8)});
        FqNameUnsafe fqNameUnsafe = KotlinBuiltIns.FQ_NAMES.any;
        Intrinsics.checkExpressionValueIsNotNull(fqNameUnsafe, "FQ_NAMES.any");
        this_$iv.addTopLevel(Object.class, fqNameUnsafe);
        FqNameUnsafe fqNameUnsafe2 = KotlinBuiltIns.FQ_NAMES.string;
        Intrinsics.checkExpressionValueIsNotNull(fqNameUnsafe2, "FQ_NAMES.string");
        this_$iv.addTopLevel(String.class, fqNameUnsafe2);
        FqNameUnsafe fqNameUnsafe3 = KotlinBuiltIns.FQ_NAMES.charSequence;
        Intrinsics.checkExpressionValueIsNotNull(fqNameUnsafe3, "FQ_NAMES.charSequence");
        this_$iv.addTopLevel(CharSequence.class, fqNameUnsafe3);
        FqName fqName = KotlinBuiltIns.FQ_NAMES.throwable;
        Intrinsics.checkExpressionValueIsNotNull(fqName, "FQ_NAMES.throwable");
        this_$iv.addTopLevel(Throwable.class, fqName);
        FqNameUnsafe fqNameUnsafe4 = KotlinBuiltIns.FQ_NAMES.cloneable;
        Intrinsics.checkExpressionValueIsNotNull(fqNameUnsafe4, "FQ_NAMES.cloneable");
        this_$iv.addTopLevel(Cloneable.class, fqNameUnsafe4);
        FqNameUnsafe fqNameUnsafe5 = KotlinBuiltIns.FQ_NAMES.number;
        Intrinsics.checkExpressionValueIsNotNull(fqNameUnsafe5, "FQ_NAMES.number");
        this_$iv.addTopLevel(Number.class, fqNameUnsafe5);
        FqName fqName2 = KotlinBuiltIns.FQ_NAMES.comparable;
        Intrinsics.checkExpressionValueIsNotNull(fqName2, "FQ_NAMES.comparable");
        this_$iv.addTopLevel(Comparable.class, fqName2);
        FqNameUnsafe fqNameUnsafe6 = KotlinBuiltIns.FQ_NAMES._enum;
        Intrinsics.checkExpressionValueIsNotNull(fqNameUnsafe6, "FQ_NAMES._enum");
        this_$iv.addTopLevel(Enum.class, fqNameUnsafe6);
        FqName fqName3 = KotlinBuiltIns.FQ_NAMES.annotation;
        Intrinsics.checkExpressionValueIsNotNull(fqName3, "FQ_NAMES.annotation");
        this_$iv.addTopLevel(Annotation.class, fqName3);
        for (PlatformMutabilityMapping platformCollection : mutabilityMappings) {
            this_$iv.addMapping(platformCollection);
        }
        for (JvmPrimitiveType jvmType : JvmPrimitiveType.values()) {
            ClassId classId3 = ClassId.topLevel(jvmType.getWrapperFqName());
            Intrinsics.checkExpressionValueIsNotNull(classId3, "ClassId.topLevel(jvmType.wrapperFqName)");
            ClassId classId4 = ClassId.topLevel(KotlinBuiltIns.getPrimitiveFqName(jvmType.getPrimitiveType()));
            Intrinsics.checkExpressionValueIsNotNull(classId4, "ClassId.topLevel(KotlinB…e(jvmType.primitiveType))");
            this_$iv.add(classId3, classId4);
        }
        for (ClassId classId5 : CompanionObjectMapping.INSTANCE.allClassesWithIntrinsicCompanions()) {
            ClassId classId6 = ClassId.topLevel(new FqName("kotlin.jvm.internal." + classId5.getShortClassName().asString() + "CompanionObject"));
            Intrinsics.checkExpressionValueIsNotNull(classId6, "ClassId.topLevel(FqName(…g() + \"CompanionObject\"))");
            ClassId classIdCreateNestedClassId = classId5.createNestedClassId(SpecialNames.DEFAULT_NAME_FOR_COMPANION_OBJECT);
            Intrinsics.checkExpressionValueIsNotNull(classIdCreateNestedClassId, "classId.createNestedClas…AME_FOR_COMPANION_OBJECT)");
            this_$iv.add(classId6, classIdCreateNestedClassId);
        }
        for (int i2 = 0; i2 < 23; i2++) {
            ClassId classId7 = ClassId.topLevel(new FqName("kotlin.jvm.functions.Function" + i2));
            Intrinsics.checkExpressionValueIsNotNull(classId7, "ClassId.topLevel(FqName(…m.functions.Function$i\"))");
            ClassId functionClassId = KotlinBuiltIns.getFunctionClassId(i2);
            Intrinsics.checkExpressionValueIsNotNull(functionClassId, "KotlinBuiltIns.getFunctionClassId(i)");
            this_$iv.add(classId7, functionClassId);
            this_$iv.addKotlinToJava(new FqName(NUMBERED_K_FUNCTION_PREFIX + i2), K_FUNCTION_CLASS_ID);
        }
        while (i < 22) {
            int i3 = i;
            FunctionClassDescriptor.Kind kSuspendFunction = FunctionClassDescriptor.Kind.KSuspendFunction;
            String kSuspendFun = kSuspendFunction.getPackageFqName().toString() + "." + kSuspendFunction.getClassNamePrefix();
            this_$iv.addKotlinToJava(new FqName(kSuspendFun + i3), K_FUNCTION_CLASS_ID);
            i = i3 + 1;
        }
        FqName safe = KotlinBuiltIns.FQ_NAMES.nothing.toSafe();
        Intrinsics.checkExpressionValueIsNotNull(safe, "FQ_NAMES.nothing.toSafe()");
        this_$iv.addKotlinToJava(safe, this_$iv.classId(Void.class));
    }

    private JavaToKotlinClassMap() {
    }

    @NotNull
    public final FqName getFUNCTION_N_FQ_NAME() {
        return FUNCTION_N_FQ_NAME;
    }

    /* JADX INFO: compiled from: JavaToKotlinClassMap.kt */
    public static final class PlatformMutabilityMapping {

        @NotNull
        private final ClassId javaClass;

        @NotNull
        private final ClassId kotlinMutable;

        @NotNull
        private final ClassId kotlinReadOnly;

        @NotNull
        public final ClassId component1() {
            return this.javaClass;
        }

        @NotNull
        public final ClassId component2() {
            return this.kotlinReadOnly;
        }

        @NotNull
        public final ClassId component3() {
            return this.kotlinMutable;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PlatformMutabilityMapping)) {
                return false;
            }
            PlatformMutabilityMapping platformMutabilityMapping = (PlatformMutabilityMapping) obj;
            return Intrinsics.areEqual(this.javaClass, platformMutabilityMapping.javaClass) && Intrinsics.areEqual(this.kotlinReadOnly, platformMutabilityMapping.kotlinReadOnly) && Intrinsics.areEqual(this.kotlinMutable, platformMutabilityMapping.kotlinMutable);
        }

        public int hashCode() {
            ClassId classId = this.javaClass;
            int iHashCode = (classId != null ? classId.hashCode() : 0) * 31;
            ClassId classId2 = this.kotlinReadOnly;
            int iHashCode2 = (iHashCode + (classId2 != null ? classId2.hashCode() : 0)) * 31;
            ClassId classId3 = this.kotlinMutable;
            return iHashCode2 + (classId3 != null ? classId3.hashCode() : 0);
        }

        @NotNull
        public String toString() {
            return "PlatformMutabilityMapping(javaClass=" + this.javaClass + ", kotlinReadOnly=" + this.kotlinReadOnly + ", kotlinMutable=" + this.kotlinMutable + ")";
        }

        public PlatformMutabilityMapping(@NotNull ClassId javaClass, @NotNull ClassId kotlinReadOnly, @NotNull ClassId kotlinMutable) {
            Intrinsics.checkParameterIsNotNull(javaClass, "javaClass");
            Intrinsics.checkParameterIsNotNull(kotlinReadOnly, "kotlinReadOnly");
            Intrinsics.checkParameterIsNotNull(kotlinMutable, "kotlinMutable");
            this.javaClass = javaClass;
            this.kotlinReadOnly = kotlinReadOnly;
            this.kotlinMutable = kotlinMutable;
        }

        @NotNull
        public final ClassId getJavaClass() {
            return this.javaClass;
        }
    }

    @NotNull
    public final List<PlatformMutabilityMapping> getMutabilityMappings() {
        return mutabilityMappings;
    }

    @Nullable
    public final ClassId mapJavaToKotlin(@NotNull FqName fqName) {
        Intrinsics.checkParameterIsNotNull(fqName, "fqName");
        return javaToKotlin.get(fqName.toUnsafe());
    }

    @Nullable
    public static /* synthetic */ ClassDescriptor mapJavaToKotlin$default(JavaToKotlinClassMap javaToKotlinClassMap, FqName fqName, KotlinBuiltIns kotlinBuiltIns, Integer num, int i, Object obj) {
        if ((i & 4) != 0) {
            num = (Integer) null;
        }
        return javaToKotlinClassMap.mapJavaToKotlin(fqName, kotlinBuiltIns, num);
    }

    @Nullable
    public final ClassDescriptor mapJavaToKotlin(@NotNull FqName fqName, @NotNull KotlinBuiltIns builtIns, @Nullable Integer functionTypeArity) {
        Intrinsics.checkParameterIsNotNull(fqName, "fqName");
        Intrinsics.checkParameterIsNotNull(builtIns, "builtIns");
        ClassId kotlinClassId = (functionTypeArity == null || !Intrinsics.areEqual(fqName, FUNCTION_N_FQ_NAME)) ? mapJavaToKotlin(fqName) : KotlinBuiltIns.getFunctionClassId(functionTypeArity.intValue());
        if (kotlinClassId != null) {
            return builtIns.getBuiltInClassByFqName(kotlinClassId.asSingleFqName());
        }
        return null;
    }

    @Nullable
    public final ClassId mapKotlinToJava(@NotNull FqNameUnsafe kotlinFqName) {
        Intrinsics.checkParameterIsNotNull(kotlinFqName, "kotlinFqName");
        if (!isKotlinFunctionWithBigArity(kotlinFqName, NUMBERED_FUNCTION_PREFIX) && !isKotlinFunctionWithBigArity(kotlinFqName, NUMBERED_SUSPEND_FUNCTION_PREFIX)) {
            if (!isKotlinFunctionWithBigArity(kotlinFqName, NUMBERED_K_FUNCTION_PREFIX) && !isKotlinFunctionWithBigArity(kotlinFqName, NUMBERED_K_SUSPEND_FUNCTION_PREFIX)) {
                return kotlinToJava.get(kotlinFqName);
            }
            return K_FUNCTION_CLASS_ID;
        }
        return FUNCTION_N_CLASS_ID;
    }

    private final boolean isKotlinFunctionWithBigArity(FqNameUnsafe kotlinFqName, String prefix) {
        String strAsString = kotlinFqName.asString();
        Intrinsics.checkExpressionValueIsNotNull(strAsString, "kotlinFqName.asString()");
        String arityString = StringsKt.substringAfter(strAsString, prefix, "");
        if (!(arityString.length() > 0) || StringsKt.startsWith$default((CharSequence) arityString, '0', false, 2, (Object) null)) {
            return false;
        }
        Integer arity = StringsKt.toIntOrNull(arityString);
        return arity != null && arity.intValue() >= 23;
    }

    private final void addMapping(PlatformMutabilityMapping platformMutabilityMapping) {
        ClassId javaClassId = platformMutabilityMapping.component1();
        ClassId readOnlyClassId = platformMutabilityMapping.component2();
        ClassId mutableClassId = platformMutabilityMapping.component3();
        add(javaClassId, readOnlyClassId);
        FqName fqNameAsSingleFqName = mutableClassId.asSingleFqName();
        Intrinsics.checkExpressionValueIsNotNull(fqNameAsSingleFqName, "mutableClassId.asSingleFqName()");
        addKotlinToJava(fqNameAsSingleFqName, javaClassId);
        FqName readOnlyFqName = readOnlyClassId.asSingleFqName();
        Intrinsics.checkExpressionValueIsNotNull(readOnlyFqName, "readOnlyClassId.asSingleFqName()");
        FqName mutableFqName = mutableClassId.asSingleFqName();
        Intrinsics.checkExpressionValueIsNotNull(mutableFqName, "mutableClassId.asSingleFqName()");
        mutableToReadOnly.put(mutableClassId.asSingleFqName().toUnsafe(), readOnlyFqName);
        readOnlyToMutable.put(readOnlyFqName.toUnsafe(), mutableFqName);
    }

    private final void add(ClassId javaClassId, ClassId kotlinClassId) {
        addJavaToKotlin(javaClassId, kotlinClassId);
        FqName fqNameAsSingleFqName = kotlinClassId.asSingleFqName();
        Intrinsics.checkExpressionValueIsNotNull(fqNameAsSingleFqName, "kotlinClassId.asSingleFqName()");
        addKotlinToJava(fqNameAsSingleFqName, javaClassId);
    }

    private final void addTopLevel(Class<?> cls, FqNameUnsafe kotlinFqName) {
        FqName safe = kotlinFqName.toSafe();
        Intrinsics.checkExpressionValueIsNotNull(safe, "kotlinFqName.toSafe()");
        addTopLevel(cls, safe);
    }

    private final void addTopLevel(Class<?> cls, FqName kotlinFqName) {
        ClassId classId = classId(cls);
        ClassId classId2 = ClassId.topLevel(kotlinFqName);
        Intrinsics.checkExpressionValueIsNotNull(classId2, "ClassId.topLevel(kotlinFqName)");
        add(classId, classId2);
    }

    private final void addJavaToKotlin(ClassId javaClassId, ClassId kotlinClassId) {
        javaToKotlin.put(javaClassId.asSingleFqName().toUnsafe(), kotlinClassId);
    }

    private final void addKotlinToJava(FqName kotlinFqNameUnsafe, ClassId javaClassId) {
        kotlinToJava.put(kotlinFqNameUnsafe.toUnsafe(), javaClassId);
    }

    @NotNull
    public final Collection<ClassDescriptor> mapPlatformClass(@NotNull FqName fqName, @NotNull KotlinBuiltIns builtIns) {
        Intrinsics.checkParameterIsNotNull(fqName, "fqName");
        Intrinsics.checkParameterIsNotNull(builtIns, "builtIns");
        ClassDescriptor kotlinAnalog = mapJavaToKotlin$default(this, fqName, builtIns, null, 4, null);
        if (kotlinAnalog == null) {
            return SetsKt.emptySet();
        }
        FqName kotlinMutableAnalogFqName = readOnlyToMutable.get(DescriptorUtilsKt.getFqNameUnsafe(kotlinAnalog));
        if (kotlinMutableAnalogFqName == null) {
            return SetsKt.setOf(kotlinAnalog);
        }
        Intrinsics.checkExpressionValueIsNotNull(kotlinMutableAnalogFqName, "readOnlyToMutable[kotlin…eturn setOf(kotlinAnalog)");
        List listAsList = Arrays.asList(kotlinAnalog, builtIns.getBuiltInClassByFqName(kotlinMutableAnalogFqName));
        Intrinsics.checkExpressionValueIsNotNull(listAsList, "Arrays.asList(kotlinAnal…tlinMutableAnalogFqName))");
        return listAsList;
    }

    public final boolean isMutable(@NotNull ClassDescriptor mutable) {
        Intrinsics.checkParameterIsNotNull(mutable, "mutable");
        return mutableToReadOnly.containsKey(DescriptorUtils.getFqName(mutable));
    }

    public final boolean isMutable(@NotNull KotlinType type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        ClassDescriptor classDescriptor = TypeUtils.getClassDescriptor(type);
        return classDescriptor != null && isMutable(classDescriptor);
    }

    public final boolean isReadOnly(@NotNull ClassDescriptor readOnly) {
        Intrinsics.checkParameterIsNotNull(readOnly, "readOnly");
        return readOnlyToMutable.containsKey(DescriptorUtils.getFqName(readOnly));
    }

    public final boolean isReadOnly(@NotNull KotlinType type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        ClassDescriptor classDescriptor = TypeUtils.getClassDescriptor(type);
        return classDescriptor != null && isReadOnly(classDescriptor);
    }

    @NotNull
    public final ClassDescriptor convertMutableToReadOnly(@NotNull ClassDescriptor mutable) {
        Intrinsics.checkParameterIsNotNull(mutable, "mutable");
        return convertToOppositeMutability(mutable, mutableToReadOnly, "mutable");
    }

    @NotNull
    public final ClassDescriptor convertReadOnlyToMutable(@NotNull ClassDescriptor readOnly) {
        Intrinsics.checkParameterIsNotNull(readOnly, "readOnly");
        return convertToOppositeMutability(readOnly, readOnlyToMutable, "read-only");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassId classId(Class<?> cls) {
        boolean z = (cls.isPrimitive() || cls.isArray()) ? false : true;
        if (_Assertions.ENABLED && !z) {
            throw new AssertionError("Invalid class: " + cls);
        }
        Class<?> declaringClass = cls.getDeclaringClass();
        if (declaringClass == null) {
            ClassId classId = ClassId.topLevel(new FqName(cls.getCanonicalName()));
            Intrinsics.checkExpressionValueIsNotNull(classId, "ClassId.topLevel(FqName(clazz.canonicalName))");
            return classId;
        }
        ClassId classIdCreateNestedClassId = classId(declaringClass).createNestedClassId(Name.identifier(cls.getSimpleName()));
        Intrinsics.checkExpressionValueIsNotNull(classIdCreateNestedClassId, "classId(outer).createNes…tifier(clazz.simpleName))");
        return classIdCreateNestedClassId;
    }

    private final ClassDescriptor convertToOppositeMutability(ClassDescriptor descriptor, Map<FqNameUnsafe, FqName> map, String mutabilityKindName) {
        FqName oppositeClassFqName = map.get(DescriptorUtils.getFqName(descriptor));
        if (oppositeClassFqName == null) {
            throw new IllegalArgumentException("Given class " + descriptor + " is not a " + mutabilityKindName + " collection");
        }
        ClassDescriptor builtInClassByFqName = DescriptorUtilsKt.getBuiltIns(descriptor).getBuiltInClassByFqName(oppositeClassFqName);
        Intrinsics.checkExpressionValueIsNotNull(builtInClassByFqName, "descriptor.builtIns.getB…Name(oppositeClassFqName)");
        return builtInClassByFqName;
    }
}
