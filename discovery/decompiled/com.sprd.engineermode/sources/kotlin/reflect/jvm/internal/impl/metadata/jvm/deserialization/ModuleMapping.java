package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolverImpl;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionSpecificBehaviorKt;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmModuleProtoBuf;
import kotlin.reflect.jvm.internal.impl.protobuf.ProtocolStringList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: ModuleMapping.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class ModuleMapping {
    private final String debugName;

    @NotNull
    private final BinaryModuleData moduleData;

    @NotNull
    private final Map<String, PackageParts> packageFqName2Parts;
    public static final Companion Companion = new Companion(null);

    @JvmField
    @NotNull
    public static final ModuleMapping EMPTY = new ModuleMapping(MapsKt.emptyMap(), new BinaryModuleData(CollectionsKt.emptyList()), "EMPTY");

    @JvmField
    @NotNull
    public static final ModuleMapping CORRUPTED = new ModuleMapping(MapsKt.emptyMap(), new BinaryModuleData(CollectionsKt.emptyList()), "CORRUPTED");

    private ModuleMapping(Map<String, PackageParts> map, BinaryModuleData moduleData, String debugName) {
        this.packageFqName2Parts = map;
        this.moduleData = moduleData;
        this.debugName = debugName;
    }

    public /* synthetic */ ModuleMapping(Map packageFqName2Parts, BinaryModuleData moduleData, String debugName, DefaultConstructorMarker $constructor_marker) {
        this(packageFqName2Parts, moduleData, debugName);
    }

    @NotNull
    public final Map<String, PackageParts> getPackageFqName2Parts() {
        return this.packageFqName2Parts;
    }

    @NotNull
    public String toString() {
        return this.debugName;
    }

    /* JADX INFO: compiled from: ModuleMapping.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final ModuleMapping loadModuleMapping(@Nullable byte[] bytes, @NotNull String debugName, boolean skipMetadataVersionCheck, boolean isJvmPackageNameSupported, @NotNull Function1<? super JvmMetadataVersion, Unit> reportIncompatibleVersionError) throws IOException {
            Object answer$iv;
            Object answer$iv2;
            JvmMetadataVersion preVersion;
            String facadeShortName;
            String it;
            Intrinsics.checkParameterIsNotNull(debugName, "debugName");
            Intrinsics.checkParameterIsNotNull(reportIncompatibleVersionError, "reportIncompatibleVersionError");
            if (bytes == null) {
                return ModuleMapping.EMPTY;
            }
            DataInputStream stream = new DataInputStream(new ByteArrayInputStream(bytes));
            try {
                int size$iv = stream.readInt();
                int[] result$iv = new int[size$iv];
                int length = result$iv.length;
                for (int i$iv = 0; i$iv < length; i$iv++) {
                    try {
                        result$iv[i$iv] = stream.readInt();
                    } catch (IOException e) {
                        return ModuleMapping.CORRUPTED;
                    }
                }
                int[] versionNumber = result$iv;
                JvmMetadataVersion preVersion2 = new JvmMetadataVersion(Arrays.copyOf(versionNumber, versionNumber.length));
                if (!skipMetadataVersionCheck && !preVersion2.isCompatible()) {
                    reportIncompatibleVersionError.invoke(preVersion2);
                    return ModuleMapping.EMPTY;
                }
                int flags = VersionSpecificBehaviorKt.isKotlin1Dot4OrLater(preVersion2) ? stream.readInt() : 0;
                JvmMetadataVersion version = new JvmMetadataVersion(versionNumber, (flags & 1) != 0);
                if (!skipMetadataVersionCheck && !version.isCompatible()) {
                    reportIncompatibleVersionError.invoke(version);
                    return ModuleMapping.EMPTY;
                }
                JvmModuleProtoBuf.Module moduleProto = JvmModuleProtoBuf.Module.parseFrom(stream);
                if (moduleProto == null) {
                    return ModuleMapping.EMPTY;
                }
                Map result = new LinkedHashMap();
                Iterator<JvmModuleProtoBuf.PackageParts> it2 = moduleProto.getPackagePartsList().iterator();
                int i = 0;
                while (true) {
                    int[] versionNumber2 = versionNumber;
                    if (!it2.hasNext()) {
                        break;
                    }
                    JvmModuleProtoBuf.PackageParts proto = it2.next();
                    Intrinsics.checkExpressionValueIsNotNull(proto, "proto");
                    String packageFqName = proto.getPackageFqName();
                    Map $receiver$iv = result;
                    Intrinsics.checkExpressionValueIsNotNull(packageFqName, "packageFqName");
                    Object value$iv = $receiver$iv.get(packageFqName);
                    if (value$iv == null) {
                        answer$iv2 = new PackageParts(packageFqName);
                        $receiver$iv.put(packageFqName, answer$iv2);
                    } else {
                        answer$iv2 = value$iv;
                    }
                    PackageParts packageParts = (PackageParts) answer$iv2;
                    ProtocolStringList shortClassNameList = proto.getShortClassNameList();
                    Intrinsics.checkExpressionValueIsNotNull(shortClassNameList, "proto.shortClassNameList");
                    Iterator<String> it3 = shortClassNameList.iterator();
                    int index = 0;
                    while (true) {
                        preVersion = preVersion2;
                        if (!it3.hasNext()) {
                            break;
                        }
                        String partShortName = it3.next();
                        Iterator<String> it4 = it3;
                        List<Integer> multifileFacadeShortNameIdList = proto.getMultifileFacadeShortNameIdList();
                        int flags2 = flags;
                        Intrinsics.checkExpressionValueIsNotNull(multifileFacadeShortNameIdList, "proto.multifileFacadeShortNameIdList");
                        Integer num = (Integer) CollectionsKt.getOrNull(multifileFacadeShortNameIdList, index);
                        Integer multifileFacadeId = num != null ? Integer.valueOf(num.intValue() - 1) : null;
                        if (multifileFacadeId != null) {
                            ProtocolStringList multifileFacadeShortNameList = proto.getMultifileFacadeShortNameList();
                            Intrinsics.checkExpressionValueIsNotNull(multifileFacadeShortNameList, "proto.multifileFacadeShortNameList");
                            int p1 = multifileFacadeId.intValue();
                            facadeShortName = (String) CollectionsKt.getOrNull(multifileFacadeShortNameList, p1);
                        } else {
                            facadeShortName = null;
                        }
                        if (facadeShortName != null) {
                            String it5 = facadeShortName;
                            it = ModuleMappingKt.internalNameOf(packageFqName, it5);
                        } else {
                            it = null;
                        }
                        Intrinsics.checkExpressionValueIsNotNull(partShortName, "partShortName");
                        packageParts.addPart(ModuleMappingKt.internalNameOf(packageFqName, partShortName), it);
                        index++;
                        preVersion2 = preVersion;
                        it3 = it4;
                        flags = flags2;
                        version = version;
                    }
                    int flags3 = flags;
                    JvmMetadataVersion version2 = version;
                    if (isJvmPackageNameSupported) {
                        ProtocolStringList classWithJvmPackageNameShortNameList = proto.getClassWithJvmPackageNameShortNameList();
                        Intrinsics.checkExpressionValueIsNotNull(classWithJvmPackageNameShortNameList, "proto.classWithJvmPackageNameShortNameList");
                        int index2 = 0;
                        for (String partShortName2 : classWithJvmPackageNameShortNameList) {
                            List<Integer> classWithJvmPackageNamePackageIdList = proto.getClassWithJvmPackageNamePackageIdList();
                            Intrinsics.checkExpressionValueIsNotNull(classWithJvmPackageNamePackageIdList, "proto.classWithJvmPackageNamePackageIdList");
                            Integer num2 = (Integer) CollectionsKt.getOrNull(classWithJvmPackageNamePackageIdList, index2);
                            if (num2 == null) {
                                List<Integer> classWithJvmPackageNamePackageIdList2 = proto.getClassWithJvmPackageNamePackageIdList();
                                Intrinsics.checkExpressionValueIsNotNull(classWithJvmPackageNamePackageIdList2, "proto.classWithJvmPackageNamePackageIdList");
                                num2 = (Integer) CollectionsKt.lastOrNull((List) classWithJvmPackageNamePackageIdList2);
                            }
                            if (num2 == null) {
                                proto = proto;
                            } else {
                                int packageId = num2.intValue();
                                ProtocolStringList jvmPackageNameList = moduleProto.getJvmPackageNameList();
                                Intrinsics.checkExpressionValueIsNotNull(jvmPackageNameList, "moduleProto.jvmPackageNameList");
                                String jvmPackageName = (String) CollectionsKt.getOrNull(jvmPackageNameList, packageId);
                                if (jvmPackageName != null) {
                                    Intrinsics.checkExpressionValueIsNotNull(partShortName2, "partShortName");
                                    packageParts.addPart(ModuleMappingKt.internalNameOf(jvmPackageName, partShortName2), null);
                                }
                                i = packageId;
                            }
                            index2++;
                            proto = proto;
                        }
                    }
                    preVersion2 = preVersion;
                    versionNumber = versionNumber2;
                    stream = stream;
                    flags = flags3;
                    version = version2;
                }
                for (JvmModuleProtoBuf.PackageParts proto2 : moduleProto.getMetadataPartsList()) {
                    Map $receiver$iv2 = result;
                    Intrinsics.checkExpressionValueIsNotNull(proto2, "proto");
                    String packageFqName2 = proto2.getPackageFqName();
                    Intrinsics.checkExpressionValueIsNotNull(packageFqName2, "proto.packageFqName");
                    int i2 = i;
                    Object value$iv2 = $receiver$iv2.get(packageFqName2);
                    if (value$iv2 == null) {
                        String packageFqName3 = proto2.getPackageFqName();
                        Intrinsics.checkExpressionValueIsNotNull(packageFqName3, "proto.packageFqName");
                        answer$iv = new PackageParts(packageFqName3);
                        $receiver$iv2.put(packageFqName2, answer$iv);
                    } else {
                        answer$iv = value$iv2;
                    }
                    PackageParts packageParts2 = (PackageParts) answer$iv;
                    Iterable $receiver$iv3 = proto2.getShortClassNameList();
                    Intrinsics.checkExpressionValueIsNotNull($receiver$iv3, "proto.shortClassNameList");
                    i = i2;
                    for (Object element$iv : $receiver$iv3) {
                        String p2 = (String) element$iv;
                        packageParts2.addMetadataPart(p2);
                    }
                }
                ProtoBuf.StringTable stringTable = moduleProto.getStringTable();
                Intrinsics.checkExpressionValueIsNotNull(stringTable, "moduleProto.stringTable");
                ProtoBuf.QualifiedNameTable qualifiedNameTable = moduleProto.getQualifiedNameTable();
                Intrinsics.checkExpressionValueIsNotNull(qualifiedNameTable, "moduleProto.qualifiedNameTable");
                NameResolverImpl nameResolver = new NameResolverImpl(stringTable, qualifiedNameTable);
                Iterable annotationList = moduleProto.getAnnotationList();
                Intrinsics.checkExpressionValueIsNotNull(annotationList, "moduleProto.annotationList");
                Iterable $receiver$iv4 = annotationList;
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv4, 10));
                for (Object item$iv$iv : $receiver$iv4) {
                    ProtoBuf.Annotation proto3 = (ProtoBuf.Annotation) item$iv$iv;
                    Intrinsics.checkExpressionValueIsNotNull(proto3, "proto");
                    destination$iv$iv.add(nameResolver.getQualifiedClassName(proto3.getId()));
                }
                List annotations = (List) destination$iv$iv;
                return new ModuleMapping(result, new BinaryModuleData(annotations), debugName, null);
            } catch (IOException e2) {
            }
        }
    }
}
