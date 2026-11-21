package kotlin.reflect.jvm.internal.components;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ClassLiteralValue;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import kotlin.reflect.jvm.internal.structure.ReflectClassUtilKt;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: ReflectKotlinClass.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u001c\u0010\t\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u001c\u0010\f\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u001c\u0010\r\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0018\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J \u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0001H\u0002J$\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u00102\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u0006H\u0002J\u001a\u0010\u0018\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\n\u001a\u00020\u000bJ\u0010\u0010\u0019\u001a\u00020\u001a*\u0006\u0012\u0002\b\u00030\u0006H\u0002¨\u0006\u001b"}, d2 = {"Lkotlin/reflect/jvm/internal/components/ReflectClassStructure;", "", "()V", "loadClassAnnotations", "", "klass", "Ljava/lang/Class;", "visitor", "Lkotlin/reflect/jvm/internal/impl/load/kotlin/KotlinJvmBinaryClass$AnnotationVisitor;", "loadConstructorAnnotations", "memberVisitor", "Lkotlin/reflect/jvm/internal/impl/load/kotlin/KotlinJvmBinaryClass$MemberVisitor;", "loadFieldAnnotations", "loadMethodAnnotations", "processAnnotation", "annotation", "", "processAnnotationArgumentValue", "Lkotlin/reflect/jvm/internal/impl/load/kotlin/KotlinJvmBinaryClass$AnnotationArgumentVisitor;", "name", "Lkotlin/reflect/jvm/internal/impl/name/Name;", "value", "processAnnotationArguments", "annotationType", "visitMembers", "classLiteralValue", "Lkotlin/reflect/jvm/internal/impl/resolve/constants/ClassLiteralValue;", "descriptors.runtime"}, k = 1, mv = {1, 1, 13})
final class ReflectClassStructure {
    public static final ReflectClassStructure INSTANCE = new ReflectClassStructure();

    private ReflectClassStructure() {
    }

    public final void loadClassAnnotations(@NotNull Class<?> klass, @NotNull KotlinJvmBinaryClass.AnnotationVisitor visitor) throws InvocationTargetException {
        Intrinsics.checkParameterIsNotNull(klass, "klass");
        Intrinsics.checkParameterIsNotNull(visitor, "visitor");
        for (Annotation annotation : klass.getDeclaredAnnotations()) {
            Intrinsics.checkExpressionValueIsNotNull(annotation, "annotation");
            processAnnotation(visitor, annotation);
        }
        visitor.visitEnd();
    }

    public final void visitMembers(@NotNull Class<?> klass, @NotNull KotlinJvmBinaryClass.MemberVisitor memberVisitor) throws InvocationTargetException {
        Intrinsics.checkParameterIsNotNull(klass, "klass");
        Intrinsics.checkParameterIsNotNull(memberVisitor, "memberVisitor");
        loadMethodAnnotations(klass, memberVisitor);
        loadConstructorAnnotations(klass, memberVisitor);
        loadFieldAnnotations(klass, memberVisitor);
    }

    private final void loadMethodAnnotations(Class<?> klass, KotlinJvmBinaryClass.MemberVisitor memberVisitor) throws InvocationTargetException {
        Method[] methodArr;
        int i;
        Method[] declaredMethods = klass.getDeclaredMethods();
        int length = declaredMethods.length;
        int i2 = 0;
        while (i2 < length) {
            Method method = declaredMethods[i2];
            Intrinsics.checkExpressionValueIsNotNull(method, "method");
            Name nameIdentifier = Name.identifier(method.getName());
            Intrinsics.checkExpressionValueIsNotNull(nameIdentifier, "Name.identifier(method.name)");
            KotlinJvmBinaryClass.MethodAnnotationVisitor visitor = memberVisitor.visitMethod(nameIdentifier, SignatureSerializer.INSTANCE.methodDesc(method));
            if (visitor != null) {
                for (Annotation annotation : method.getDeclaredAnnotations()) {
                    Intrinsics.checkExpressionValueIsNotNull(annotation, "annotation");
                    processAnnotation(visitor, annotation);
                }
                Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                Intrinsics.checkExpressionValueIsNotNull(parameterAnnotations, "method.parameterAnnotations");
                int length2 = parameterAnnotations.length;
                for (int parameterIndex = 0; parameterIndex < length2; parameterIndex++) {
                    Annotation[] annotations = parameterAnnotations[parameterIndex];
                    int length3 = annotations.length;
                    int i3 = 0;
                    while (i3 < length3) {
                        Annotation annotation2 = annotations[i3];
                        Class<?> javaClass = JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(annotation2));
                        Method[] methodArr2 = declaredMethods;
                        ClassId classId = ReflectClassUtilKt.getClassId(javaClass);
                        int i4 = length;
                        Intrinsics.checkExpressionValueIsNotNull(annotation2, "annotation");
                        KotlinJvmBinaryClass.AnnotationArgumentVisitor it = visitor.visitParameterAnnotation(parameterIndex, classId, new ReflectAnnotationSource(annotation2));
                        if (it != null) {
                            INSTANCE.processAnnotationArguments(it, annotation2, javaClass);
                        }
                        i3++;
                        declaredMethods = methodArr2;
                        length = i4;
                    }
                }
                methodArr = declaredMethods;
                i = length;
                visitor.visitEnd();
            } else {
                methodArr = declaredMethods;
                i = length;
            }
            i2++;
            declaredMethods = methodArr;
            length = i;
        }
    }

    private final void loadConstructorAnnotations(Class<?> klass, KotlinJvmBinaryClass.MemberVisitor memberVisitor) throws InvocationTargetException {
        Constructor<?>[] constructorArr;
        int i;
        Constructor<?>[] declaredConstructors = klass.getDeclaredConstructors();
        int length = declaredConstructors.length;
        int i2 = 0;
        while (i2 < length) {
            Constructor<?> constructor = declaredConstructors[i2];
            Name nameSpecial = Name.special("<init>");
            Intrinsics.checkExpressionValueIsNotNull(nameSpecial, "Name.special(\"<init>\")");
            SignatureSerializer signatureSerializer = SignatureSerializer.INSTANCE;
            Intrinsics.checkExpressionValueIsNotNull(constructor, "constructor");
            KotlinJvmBinaryClass.MethodAnnotationVisitor visitor = memberVisitor.visitMethod(nameSpecial, signatureSerializer.constructorDesc(constructor));
            if (visitor != null) {
                for (Annotation annotation : constructor.getDeclaredAnnotations()) {
                    Intrinsics.checkExpressionValueIsNotNull(annotation, "annotation");
                    processAnnotation(visitor, annotation);
                }
                Annotation[][] parameterAnnotations = constructor.getParameterAnnotations();
                Intrinsics.checkExpressionValueIsNotNull(parameterAnnotations, "parameterAnnotations");
                if (!(!(parameterAnnotations.length == 0))) {
                    constructorArr = declaredConstructors;
                    i = length;
                } else {
                    int shift = constructor.getParameterTypes().length - parameterAnnotations.length;
                    int length2 = parameterAnnotations.length;
                    for (int parameterIndex = 0; parameterIndex < length2; parameterIndex++) {
                        Annotation[] annotations = parameterAnnotations[parameterIndex];
                        int length3 = annotations.length;
                        int i3 = 0;
                        while (i3 < length3) {
                            Annotation annotation2 = annotations[i3];
                            Constructor<?>[] constructorArr2 = declaredConstructors;
                            Class<?> javaClass = JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(annotation2));
                            int i4 = length;
                            Constructor<?> constructor2 = constructor;
                            ClassId classId = ReflectClassUtilKt.getClassId(javaClass);
                            Annotation[][] parameterAnnotations2 = parameterAnnotations;
                            Intrinsics.checkExpressionValueIsNotNull(annotation2, "annotation");
                            KotlinJvmBinaryClass.AnnotationArgumentVisitor it = visitor.visitParameterAnnotation(parameterIndex + shift, classId, new ReflectAnnotationSource(annotation2));
                            if (it != null) {
                                INSTANCE.processAnnotationArguments(it, annotation2, javaClass);
                            }
                            i3++;
                            length = i4;
                            declaredConstructors = constructorArr2;
                            constructor = constructor2;
                            parameterAnnotations = parameterAnnotations2;
                        }
                    }
                    constructorArr = declaredConstructors;
                    i = length;
                }
                visitor.visitEnd();
            } else {
                constructorArr = declaredConstructors;
                i = length;
            }
            i2++;
            length = i;
            declaredConstructors = constructorArr;
        }
    }

    private final void loadFieldAnnotations(Class<?> klass, KotlinJvmBinaryClass.MemberVisitor memberVisitor) throws InvocationTargetException {
        for (Field field : klass.getDeclaredFields()) {
            Intrinsics.checkExpressionValueIsNotNull(field, "field");
            Name nameIdentifier = Name.identifier(field.getName());
            Intrinsics.checkExpressionValueIsNotNull(nameIdentifier, "Name.identifier(field.name)");
            KotlinJvmBinaryClass.AnnotationVisitor visitor = memberVisitor.visitField(nameIdentifier, SignatureSerializer.INSTANCE.fieldDesc(field), null);
            if (visitor != null) {
                for (Annotation annotation : field.getDeclaredAnnotations()) {
                    Intrinsics.checkExpressionValueIsNotNull(annotation, "annotation");
                    processAnnotation(visitor, annotation);
                }
                visitor.visitEnd();
            }
        }
    }

    private final void processAnnotation(KotlinJvmBinaryClass.AnnotationVisitor visitor, Annotation annotation) throws InvocationTargetException {
        Class<?> javaClass = JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(annotation));
        KotlinJvmBinaryClass.AnnotationArgumentVisitor it = visitor.visitAnnotation(ReflectClassUtilKt.getClassId(javaClass), new ReflectAnnotationSource(annotation));
        if (it != null) {
            INSTANCE.processAnnotationArguments(it, annotation, javaClass);
        }
    }

    private final void processAnnotationArguments(KotlinJvmBinaryClass.AnnotationArgumentVisitor visitor, Annotation annotation, Class<?> annotationType) throws InvocationTargetException {
        for (Method method : annotationType.getDeclaredMethods()) {
            try {
                Object value = method.invoke(annotation, new Object[0]);
                if (value == null) {
                    Intrinsics.throwNpe();
                }
                Intrinsics.checkExpressionValueIsNotNull(method, "method");
                Name nameIdentifier = Name.identifier(method.getName());
                Intrinsics.checkExpressionValueIsNotNull(nameIdentifier, "Name.identifier(method.name)");
                processAnnotationArgumentValue(visitor, nameIdentifier, value);
            } catch (IllegalAccessException e) {
            }
        }
        visitor.visitEnd();
    }

    private final ClassLiteralValue classLiteralValue(@NotNull Class<?> cls) {
        Class<?> cls2 = cls;
        int dimensions = 0;
        while (cls2.isArray()) {
            dimensions++;
            Class<?> componentType = cls2.getComponentType();
            Intrinsics.checkExpressionValueIsNotNull(componentType, "currentClass.componentType");
            cls2 = componentType;
        }
        if (cls2.isPrimitive()) {
            if (Intrinsics.areEqual(cls2, Void.TYPE)) {
                ClassId classId = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.unit.toSafe());
                Intrinsics.checkExpressionValueIsNotNull(classId, "ClassId.topLevel(KotlinB…s.FQ_NAMES.unit.toSafe())");
                return new ClassLiteralValue(classId, dimensions);
            }
            JvmPrimitiveType jvmPrimitiveType = JvmPrimitiveType.get(cls2.getName());
            Intrinsics.checkExpressionValueIsNotNull(jvmPrimitiveType, "JvmPrimitiveType.get(currentClass.name)");
            PrimitiveType primitiveType = jvmPrimitiveType.getPrimitiveType();
            Intrinsics.checkExpressionValueIsNotNull(primitiveType, "JvmPrimitiveType.get(cur…Class.name).primitiveType");
            if (dimensions > 0) {
                ClassId classId2 = ClassId.topLevel(primitiveType.getArrayTypeFqName());
                Intrinsics.checkExpressionValueIsNotNull(classId2, "ClassId.topLevel(primitiveType.arrayTypeFqName)");
                return new ClassLiteralValue(classId2, dimensions - 1);
            }
            ClassId classId3 = ClassId.topLevel(primitiveType.getTypeFqName());
            Intrinsics.checkExpressionValueIsNotNull(classId3, "ClassId.topLevel(primitiveType.typeFqName)");
            return new ClassLiteralValue(classId3, dimensions);
        }
        ClassId javaClassId = ReflectClassUtilKt.getClassId(cls2);
        JavaToKotlinClassMap javaToKotlinClassMap = JavaToKotlinClassMap.INSTANCE;
        FqName fqNameAsSingleFqName = javaClassId.asSingleFqName();
        Intrinsics.checkExpressionValueIsNotNull(fqNameAsSingleFqName, "javaClassId.asSingleFqName()");
        ClassId kotlinClassId = javaToKotlinClassMap.mapJavaToKotlin(fqNameAsSingleFqName);
        if (kotlinClassId == null) {
            kotlinClassId = javaClassId;
        }
        return new ClassLiteralValue(kotlinClassId, dimensions);
    }

    private final void processAnnotationArgumentValue(KotlinJvmBinaryClass.AnnotationArgumentVisitor visitor, Name name, Object value) throws InvocationTargetException {
        Class<?> cls = value.getClass();
        if (!Intrinsics.areEqual(cls, Class.class)) {
            if (ReflectKotlinClassKt.TYPES_ELIGIBLE_FOR_SIMPLE_VISIT.contains(cls)) {
                visitor.visit(name, value);
                return;
            }
            if (ReflectClassUtilKt.isEnumClassOrSpecializedEnumEntryClass(cls)) {
                Class<?> enclosingClass = cls.isEnum() ? cls : cls.getEnclosingClass();
                Intrinsics.checkExpressionValueIsNotNull(enclosingClass, "(if (clazz.isEnum) clazz…lse clazz.enclosingClass)");
                ClassId classId = ReflectClassUtilKt.getClassId(enclosingClass);
                Name nameIdentifier = Name.identifier(((Enum) value).name());
                Intrinsics.checkExpressionValueIsNotNull(nameIdentifier, "Name.identifier((value as Enum<*>).name)");
                visitor.visitEnum(name, classId, nameIdentifier);
                return;
            }
            if (Annotation.class.isAssignableFrom(cls)) {
                Class<?>[] interfaces = cls.getInterfaces();
                Intrinsics.checkExpressionValueIsNotNull(interfaces, "clazz.interfaces");
                Class<?> annotationClass = (Class) ArraysKt.single(interfaces);
                Intrinsics.checkExpressionValueIsNotNull(annotationClass, "annotationClass");
                KotlinJvmBinaryClass.AnnotationArgumentVisitor v = visitor.visitAnnotation(name, ReflectClassUtilKt.getClassId(annotationClass));
                if (v == null) {
                    return;
                }
                processAnnotationArguments(v, (Annotation) value, annotationClass);
                return;
            }
            if (cls.isArray()) {
                KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor v2 = visitor.visitArray(name);
                if (v2 == null) {
                    return;
                }
                Class<?> componentType = cls.getComponentType();
                Intrinsics.checkExpressionValueIsNotNull(componentType, "componentType");
                int i = 0;
                if (componentType.isEnum()) {
                    ClassId enumClassId = ReflectClassUtilKt.getClassId(componentType);
                    Object[] objArr = (Object[]) value;
                    int length = objArr.length;
                    while (i < length) {
                        Object element = objArr[i];
                        if (element == null) {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.Enum<*>");
                        }
                        Name nameIdentifier2 = Name.identifier(((Enum) element).name());
                        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier2, "Name.identifier((element as Enum<*>).name)");
                        v2.visitEnum(enumClassId, nameIdentifier2);
                        i++;
                    }
                } else if (Intrinsics.areEqual(componentType, Class.class)) {
                    Object[] objArr2 = (Object[]) value;
                    int length2 = objArr2.length;
                    while (i < length2) {
                        Object element2 = objArr2[i];
                        if (element2 == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<*>");
                        }
                        v2.visitClassLiteral(classLiteralValue((Class) element2));
                        i++;
                    }
                } else {
                    Object[] objArr3 = (Object[]) value;
                    int length3 = objArr3.length;
                    while (i < length3) {
                        v2.visit(objArr3[i]);
                        i++;
                    }
                }
                v2.visitEnd();
                return;
            }
            throw new UnsupportedOperationException("Unsupported annotation argument value (" + cls + "): " + value);
        }
        visitor.visitClassLiteral(name, classLiteralValue((Class) value));
    }
}
