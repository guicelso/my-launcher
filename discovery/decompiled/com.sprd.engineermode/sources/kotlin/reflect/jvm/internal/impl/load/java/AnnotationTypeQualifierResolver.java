package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.NullabilityQualifierWithApplicability;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifierWithMigrationStatus;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ArrayValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.EnumValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNullable;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.utils.Jsr305State;
import kotlin.reflect.jvm.internal.impl.utils.ReportLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: AnnotationTypeQualifierResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class AnnotationTypeQualifierResolver {
    private final boolean disabled;
    private final Jsr305State jsr305State;
    private final MemoizedFunctionToNullable<ClassDescriptor, AnnotationDescriptor> resolvedNicknames;

    /* JADX INFO: compiled from: AnnotationTypeQualifierResolver.kt */
    public enum QualifierApplicabilityType {
        METHOD_RETURN_TYPE,
        VALUE_PARAMETER,
        FIELD,
        TYPE_USE
    }

    public AnnotationTypeQualifierResolver(@NotNull StorageManager storageManager, @NotNull Jsr305State jsr305State) {
        Intrinsics.checkParameterIsNotNull(storageManager, "storageManager");
        Intrinsics.checkParameterIsNotNull(jsr305State, "jsr305State");
        this.jsr305State = jsr305State;
        this.resolvedNicknames = storageManager.createMemoizedFunctionWithNullableValues(new AnnotationTypeQualifierResolver$resolvedNicknames$1(this));
        this.disabled = this.jsr305State.getDisabled();
    }

    /* JADX INFO: compiled from: AnnotationTypeQualifierResolver.kt */
    public static final class TypeQualifierWithApplicability {
        private final int applicability;
        private final AnnotationDescriptor typeQualifier;

        public TypeQualifierWithApplicability(@NotNull AnnotationDescriptor typeQualifier, int applicability) {
            Intrinsics.checkParameterIsNotNull(typeQualifier, "typeQualifier");
            this.typeQualifier = typeQualifier;
            this.applicability = applicability;
        }

        @NotNull
        public final AnnotationDescriptor component1() {
            return this.typeQualifier;
        }

        @NotNull
        public final List<QualifierApplicabilityType> component2() {
            QualifierApplicabilityType[] qualifierApplicabilityTypeArrValues = QualifierApplicabilityType.values();
            Collection destination$iv$iv = new ArrayList();
            for (QualifierApplicabilityType qualifierApplicabilityType : qualifierApplicabilityTypeArrValues) {
                if (isApplicableTo(qualifierApplicabilityType)) {
                    destination$iv$iv.add(qualifierApplicabilityType);
                }
            }
            return (List) destination$iv$iv;
        }

        private final boolean isApplicableTo(QualifierApplicabilityType elementType) {
            return isApplicableConsideringMask(QualifierApplicabilityType.TYPE_USE) || isApplicableConsideringMask(elementType);
        }

        private final boolean isApplicableConsideringMask(QualifierApplicabilityType elementType) {
            return (this.applicability & (1 << elementType.ordinal())) != 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final AnnotationDescriptor computeTypeQualifierNickname(ClassDescriptor classDescriptor) {
        if (!classDescriptor.getAnnotations().hasAnnotation(AnnotationTypeQualifierResolverKt.TYPE_QUALIFIER_NICKNAME_FQNAME)) {
            return null;
        }
        Iterable $receiver$iv = classDescriptor.getAnnotations();
        for (Object element$iv : $receiver$iv) {
            AnnotationDescriptor p1 = resolveTypeQualifierAnnotation((AnnotationDescriptor) element$iv);
            if (p1 != null) {
                return p1;
            }
        }
        return null;
    }

    private final AnnotationDescriptor resolveTypeQualifierNickname(ClassDescriptor classDescriptor) {
        if (classDescriptor.getKind() != ClassKind.ANNOTATION_CLASS) {
            return null;
        }
        return this.resolvedNicknames.invoke(classDescriptor);
    }

    @Nullable
    public final AnnotationDescriptor resolveTypeQualifierAnnotation(@NotNull AnnotationDescriptor annotationDescriptor) {
        ClassDescriptor annotationClass;
        Intrinsics.checkParameterIsNotNull(annotationDescriptor, "annotationDescriptor");
        if (this.jsr305State.getDisabled() || (annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor)) == null) {
            return null;
        }
        return AnnotationTypeQualifierResolverKt.isAnnotatedWithTypeQualifier(annotationClass) ? annotationDescriptor : resolveTypeQualifierNickname(annotationClass);
    }

    @Nullable
    public final NullabilityQualifierWithApplicability resolveQualifierBuiltInDefaultAnnotation(@NotNull AnnotationDescriptor annotationDescriptor) {
        Intrinsics.checkParameterIsNotNull(annotationDescriptor, "annotationDescriptor");
        if (this.jsr305State.getDisabled()) {
            return null;
        }
        NullabilityQualifierWithApplicability $qualifier_applicability = (NullabilityQualifierWithApplicability) AnnotationTypeQualifierResolverKt.BUILT_IN_TYPE_QUALIFIER_DEFAULT_ANNOTATIONS.get(annotationDescriptor.getFqName());
        if ($qualifier_applicability == null) {
            return (NullabilityQualifierWithApplicability) null;
        }
        NullabilityQualifierWithMigrationStatus qualifier = $qualifier_applicability.component1();
        Collection<QualifierApplicabilityType> collectionComponent2 = $qualifier_applicability.component2();
        ReportLevel state = resolveJsr305AnnotationState(annotationDescriptor);
        if (!(state != ReportLevel.IGNORE)) {
            state = null;
        }
        if (state != null) {
            return new NullabilityQualifierWithApplicability(NullabilityQualifierWithMigrationStatus.copy$default(qualifier, null, state.isWarning(), 1, null), collectionComponent2);
        }
        return null;
    }

    @Nullable
    public final TypeQualifierWithApplicability resolveTypeQualifierDefaultAnnotation(@NotNull AnnotationDescriptor annotationDescriptor) {
        ClassDescriptor typeQualifierDefaultAnnotatedClass;
        Object element$iv;
        AnnotationDescriptor it;
        Intrinsics.checkParameterIsNotNull(annotationDescriptor, "annotationDescriptor");
        if (!this.jsr305State.getDisabled() && (typeQualifierDefaultAnnotatedClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor)) != null) {
            if (!typeQualifierDefaultAnnotatedClass.getAnnotations().hasAnnotation(AnnotationTypeQualifierResolverKt.TYPE_QUALIFIER_DEFAULT_FQNAME)) {
                typeQualifierDefaultAnnotatedClass = null;
            }
            if (typeQualifierDefaultAnnotatedClass != null) {
                ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor);
                if (annotationClass == null) {
                    Intrinsics.throwNpe();
                }
                AnnotationDescriptor annotationDescriptorMo374findAnnotation = annotationClass.getAnnotations().mo374findAnnotation(AnnotationTypeQualifierResolverKt.TYPE_QUALIFIER_DEFAULT_FQNAME);
                if (annotationDescriptorMo374findAnnotation == null) {
                    Intrinsics.throwNpe();
                }
                Map<Name, ConstantValue<?>> allValueArguments = annotationDescriptorMo374findAnnotation.getAllValueArguments();
                Collection destination$iv$iv = new ArrayList();
                for (Map.Entry<Name, ConstantValue<?>> entry : allValueArguments.entrySet()) {
                    Name parameter = entry.getKey();
                    Iterable list$iv$iv = Intrinsics.areEqual(parameter, JvmAnnotationNames.DEFAULT_ANNOTATION_MEMBER_NAME) ? mapConstantToQualifierApplicabilityTypes(entry.getValue()) : CollectionsKt.emptyList();
                    CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
                }
                Iterable $receiver$iv = (List) destination$iv$iv;
                int accumulator$iv = 0;
                int i = 0;
                for (Object element$iv2 : $receiver$iv) {
                    QualifierApplicabilityType applicabilityType = (QualifierApplicabilityType) element$iv2;
                    int acc = accumulator$iv;
                    accumulator$iv = acc | (1 << applicabilityType.ordinal());
                    i = acc;
                }
                int elementTypesMask = accumulator$iv;
                Iterable $receiver$iv2 = typeQualifierDefaultAnnotatedClass.getAnnotations();
                Iterator<AnnotationDescriptor> it2 = $receiver$iv2.iterator();
                do {
                    if (!it2.hasNext()) {
                        element$iv = null;
                        break;
                    }
                    element$iv = it2.next();
                    it = (AnnotationDescriptor) element$iv;
                } while (!(resolveTypeQualifierAnnotation(it) != null));
                AnnotationDescriptor typeQualifier = (AnnotationDescriptor) element$iv;
                if (typeQualifier != null) {
                    return new TypeQualifierWithApplicability(typeQualifier, elementTypesMask);
                }
                return null;
            }
        }
        return null;
    }

    @NotNull
    public final ReportLevel resolveJsr305AnnotationState(@NotNull AnnotationDescriptor annotationDescriptor) {
        Intrinsics.checkParameterIsNotNull(annotationDescriptor, "annotationDescriptor");
        ReportLevel it = resolveJsr305CustomState(annotationDescriptor);
        return it != null ? it : this.jsr305State.getGlobal();
    }

    @Nullable
    public final ReportLevel resolveJsr305CustomState(@NotNull AnnotationDescriptor annotationDescriptor) {
        Intrinsics.checkParameterIsNotNull(annotationDescriptor, "annotationDescriptor");
        Map<String, ReportLevel> user = this.jsr305State.getUser();
        FqName fqName = annotationDescriptor.getFqName();
        ReportLevel it = user.get(fqName != null ? fqName.asString() : null);
        if (it != null) {
            return it;
        }
        ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor);
        if (annotationClass != null) {
            return migrationAnnotationStatus(annotationClass);
        }
        return null;
    }

    private final ReportLevel migrationAnnotationStatus(@NotNull ClassDescriptor $receiver) {
        AnnotationDescriptor annotationDescriptorMo374findAnnotation = $receiver.getAnnotations().mo374findAnnotation(AnnotationTypeQualifierResolverKt.MIGRATION_ANNOTATION_FQNAME);
        ConstantValue<?> constantValueFirstArgument = annotationDescriptorMo374findAnnotation != null ? DescriptorUtilsKt.firstArgument(annotationDescriptorMo374findAnnotation) : null;
        if (!(constantValueFirstArgument instanceof EnumValue)) {
            constantValueFirstArgument = null;
        }
        EnumValue enumValue = (EnumValue) constantValueFirstArgument;
        if (enumValue == null) {
            return null;
        }
        ReportLevel it = this.jsr305State.getMigration();
        if (it != null) {
            return it;
        }
        String strAsString = enumValue.getEnumEntryName().asString();
        int iHashCode = strAsString.hashCode();
        if (iHashCode == -2137067054) {
            if (strAsString.equals("IGNORE")) {
                return ReportLevel.IGNORE;
            }
            return null;
        }
        if (iHashCode == -1838656823) {
            if (strAsString.equals("STRICT")) {
                return ReportLevel.STRICT;
            }
            return null;
        }
        if (iHashCode == 2656902 && strAsString.equals("WARN")) {
            return ReportLevel.WARN;
        }
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:26:0x0079  */
    private final List<QualifierApplicabilityType> mapConstantToQualifierApplicabilityTypes(@NotNull ConstantValue<?> constantValue) {
        QualifierApplicabilityType qualifierApplicabilityType;
        if (constantValue instanceof ArrayValue) {
            Iterable $receiver$iv = ((ArrayValue) constantValue).getValue();
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $receiver$iv) {
                Iterable list$iv$iv = mapConstantToQualifierApplicabilityTypes((ConstantValue) element$iv$iv);
                CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
            }
            return (List) destination$iv$iv;
        }
        if (!(constantValue instanceof EnumValue)) {
            return CollectionsKt.emptyList();
        }
        switch (((EnumValue) constantValue).getEnumEntryName().getIdentifier()) {
            case "METHOD":
                qualifierApplicabilityType = QualifierApplicabilityType.METHOD_RETURN_TYPE;
                break;
            case "FIELD":
                qualifierApplicabilityType = QualifierApplicabilityType.FIELD;
                break;
            case "TYPE_USE":
                qualifierApplicabilityType = QualifierApplicabilityType.TYPE_USE;
                break;
            case "PARAMETER":
                qualifierApplicabilityType = QualifierApplicabilityType.VALUE_PARAMETER;
                break;
            default:
                qualifierApplicabilityType = null;
                break;
        }
        return CollectionsKt.listOfNotNull(qualifierApplicabilityType);
    }

    public final boolean getDisabled() {
        return this.disabled;
    }
}
