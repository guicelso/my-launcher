package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

/* JADX INFO: compiled from: DeserializationConfiguration.kt */
/* JADX INFO: loaded from: classes4.dex */
public interface DeserializationConfiguration {
    boolean getReleaseCoroutines();

    boolean getReportErrorsOnPreReleaseDependencies();

    boolean getSkipMetadataVersionCheck();

    boolean getTypeAliasesAllowed();

    boolean isJvmPackageNameSupported();

    /* JADX INFO: compiled from: DeserializationConfiguration.kt */
    public static final class DefaultImpls {
        public static boolean getSkipMetadataVersionCheck(DeserializationConfiguration $this) {
            return false;
        }

        public static boolean getReportErrorsOnPreReleaseDependencies(DeserializationConfiguration $this) {
            return false;
        }

        public static boolean getTypeAliasesAllowed(DeserializationConfiguration $this) {
            return true;
        }

        public static boolean isJvmPackageNameSupported(DeserializationConfiguration $this) {
            return true;
        }

        public static boolean getReleaseCoroutines(DeserializationConfiguration $this) {
            return false;
        }
    }

    /* JADX INFO: compiled from: DeserializationConfiguration.kt */
    public static final class Default implements DeserializationConfiguration {
        public static final Default INSTANCE = new Default();

        private Default() {
        }

        @Override // kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationConfiguration
        public boolean getReleaseCoroutines() {
            return DefaultImpls.getReleaseCoroutines(this);
        }

        @Override // kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationConfiguration
        public boolean getReportErrorsOnPreReleaseDependencies() {
            return DefaultImpls.getReportErrorsOnPreReleaseDependencies(this);
        }

        @Override // kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationConfiguration
        public boolean getSkipMetadataVersionCheck() {
            return DefaultImpls.getSkipMetadataVersionCheck(this);
        }

        @Override // kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationConfiguration
        public boolean getTypeAliasesAllowed() {
            return DefaultImpls.getTypeAliasesAllowed(this);
        }

        @Override // kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationConfiguration
        public boolean isJvmPackageNameSupported() {
            return DefaultImpls.isJvmPackageNameSupported(this);
        }
    }
}
