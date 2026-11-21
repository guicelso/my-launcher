package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.Iterator;
import java.util.Set;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedClassDescriptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: ClassDeserializer.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class ClassDeserializer {
    private final Function1<ClassKey, ClassDescriptor> classes;
    private final DeserializationComponents components;
    public static final Companion Companion = new Companion(null);

    @NotNull
    private static final Set<ClassId> BLACK_LIST = SetsKt.setOf(ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.cloneable.toSafe()));

    public ClassDeserializer(@NotNull DeserializationComponents components) {
        Intrinsics.checkParameterIsNotNull(components, "components");
        this.components = components;
        this.classes = this.components.getStorageManager().createMemoizedFunctionWithNullableValues(new Function1<ClassKey, ClassDescriptor>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.ClassDeserializer$classes$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @Nullable
            public final ClassDescriptor invoke(@NotNull ClassDeserializer.ClassKey key) {
                Intrinsics.checkParameterIsNotNull(key, "key");
                return this.this$0.createClass(key);
            }
        });
    }

    @Nullable
    public static /* synthetic */ ClassDescriptor deserializeClass$default(ClassDeserializer classDeserializer, ClassId classId, ClassData classData, int i, Object obj) {
        if ((i & 2) != 0) {
            classData = (ClassData) null;
        }
        return classDeserializer.deserializeClass(classId, classData);
    }

    @Nullable
    public final ClassDescriptor deserializeClass(@NotNull ClassId classId, @Nullable ClassData classData) {
        Intrinsics.checkParameterIsNotNull(classId, "classId");
        return this.classes.invoke(new ClassKey(classId, classData));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:39:0x00bd  */
    public final ClassDescriptor createClass(ClassKey key) {
        Object element$iv;
        DeserializationContext outerContext;
        boolean z;
        ClassId classId = key.getClassId();
        for (ClassDescriptorFactory factory : this.components.getFictitiousClassDescriptorFactories()) {
            ClassDescriptor it = factory.createClass(classId);
            if (it != null) {
                return it;
            }
        }
        if (BLACK_LIST.contains(classId)) {
            return null;
        }
        ClassData classData = key.getClassData();
        if (classData == null) {
            classData = this.components.getClassDataFinder().findClassData(classId);
        }
        if (classData == null) {
            return null;
        }
        NameResolver nameResolver = classData.component1();
        ProtoBuf.Class classProto = classData.component2();
        BinaryVersion metadataVersion = classData.component3();
        SourceElement sourceElement = classData.component4();
        ClassId outerClassId = classId.getOuterClassId();
        if (outerClassId != null) {
            ClassDescriptor classDescriptorDeserializeClass$default = deserializeClass$default(this, outerClassId, null, 2, null);
            if (!(classDescriptorDeserializeClass$default instanceof DeserializedClassDescriptor)) {
                classDescriptorDeserializeClass$default = null;
            }
            DeserializedClassDescriptor outerClass = (DeserializedClassDescriptor) classDescriptorDeserializeClass$default;
            if (outerClass == null) {
                return null;
            }
            Name shortClassName = classId.getShortClassName();
            Intrinsics.checkExpressionValueIsNotNull(shortClassName, "classId.shortClassName");
            if (!outerClass.hasNestedClass$deserialization(shortClassName)) {
                return null;
            }
            outerContext = outerClass.getC();
        } else {
            PackageFragmentProvider packageFragmentProvider = this.components.getPackageFragmentProvider();
            FqName packageFqName = classId.getPackageFqName();
            Intrinsics.checkExpressionValueIsNotNull(packageFqName, "classId.packageFqName");
            Iterable $receiver$iv = packageFragmentProvider.getPackageFragments(packageFqName);
            Iterator it2 = $receiver$iv.iterator();
            do {
                if (it2.hasNext()) {
                    element$iv = it2.next();
                    PackageFragmentDescriptor it3 = (PackageFragmentDescriptor) element$iv;
                    if (it3 instanceof DeserializedPackageFragment) {
                        Name shortClassName2 = classId.getShortClassName();
                        Intrinsics.checkExpressionValueIsNotNull(shortClassName2, "classId.shortClassName");
                        if (((DeserializedPackageFragment) it3).hasTopLevelClass(shortClassName2)) {
                            z = true;
                        } else {
                            z = false;
                        }
                    } else {
                        z = true;
                    }
                } else {
                    element$iv = null;
                    break;
                }
            } while (!z);
            PackageFragmentDescriptor fragment = (PackageFragmentDescriptor) element$iv;
            if (fragment == null) {
                return null;
            }
            DeserializationComponents deserializationComponents = this.components;
            ProtoBuf.TypeTable typeTable = classProto.getTypeTable();
            Intrinsics.checkExpressionValueIsNotNull(typeTable, "classProto.typeTable");
            TypeTable typeTable2 = new TypeTable(typeTable);
            VersionRequirementTable.Companion companion = VersionRequirementTable.Companion;
            ProtoBuf.VersionRequirementTable versionRequirementTable = classProto.getVersionRequirementTable();
            Intrinsics.checkExpressionValueIsNotNull(versionRequirementTable, "classProto.versionRequirementTable");
            outerContext = deserializationComponents.createContext(fragment, nameResolver, typeTable2, companion.create(versionRequirementTable), metadataVersion, null);
        }
        return new DeserializedClassDescriptor(outerContext, classProto, nameResolver, metadataVersion, sourceElement);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: ClassDeserializer.kt */
    static final class ClassKey {

        @Nullable
        private final ClassData classData;

        @NotNull
        private final ClassId classId;

        public ClassKey(@NotNull ClassId classId, @Nullable ClassData classData) {
            Intrinsics.checkParameterIsNotNull(classId, "classId");
            this.classId = classId;
            this.classData = classData;
        }

        @Nullable
        public final ClassData getClassData() {
            return this.classData;
        }

        @NotNull
        public final ClassId getClassId() {
            return this.classId;
        }

        public boolean equals(@Nullable Object other) {
            return (other instanceof ClassKey) && Intrinsics.areEqual(this.classId, ((ClassKey) other).classId);
        }

        public int hashCode() {
            return this.classId.hashCode();
        }
    }

    /* JADX INFO: compiled from: ClassDeserializer.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final Set<ClassId> getBLACK_LIST() {
            return ClassDeserializer.BLACK_LIST;
        }
    }
}
