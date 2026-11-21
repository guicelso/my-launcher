package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMetadataVersion;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.ModuleMapping;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: ModuleMappingUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class ModuleMappingUtilKt {
    @NotNull
    public static final ModuleMapping loadModuleMapping(@NotNull ModuleMapping.Companion receiver$0, @Nullable byte[] bytes, @NotNull String debugName, @NotNull DeserializationConfiguration configuration, @NotNull Function1<? super JvmMetadataVersion, Unit> reportIncompatibleVersionError) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(debugName, "debugName");
        Intrinsics.checkParameterIsNotNull(configuration, "configuration");
        Intrinsics.checkParameterIsNotNull(reportIncompatibleVersionError, "reportIncompatibleVersionError");
        return receiver$0.loadModuleMapping(bytes, debugName, configuration.getSkipMetadataVersionCheck(), configuration.isJvmPackageNameSupported(), reportIncompatibleVersionError);
    }
}
