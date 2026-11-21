package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.DeprecationLevel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: VersionRequirement.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class VersionRequirement {
    public static final Companion Companion = new Companion(null);

    @Nullable
    private final Integer errorCode;

    @NotNull
    private final ProtoBuf.VersionRequirement.VersionKind kind;

    @NotNull
    private final DeprecationLevel level;

    @Nullable
    private final String message;

    @NotNull
    private final Version version;

    public VersionRequirement(@NotNull Version version, @NotNull ProtoBuf.VersionRequirement.VersionKind kind, @NotNull DeprecationLevel level, @Nullable Integer errorCode, @Nullable String message) {
        Intrinsics.checkParameterIsNotNull(version, "version");
        Intrinsics.checkParameterIsNotNull(kind, "kind");
        Intrinsics.checkParameterIsNotNull(level, "level");
        this.version = version;
        this.kind = kind;
        this.level = level;
        this.errorCode = errorCode;
        this.message = message;
    }

    @NotNull
    public final Version getVersion() {
        return this.version;
    }

    @NotNull
    public final ProtoBuf.VersionRequirement.VersionKind getKind() {
        return this.kind;
    }

    /* JADX INFO: compiled from: VersionRequirement.kt */
    public static final class Version {
        public static final Companion Companion = new Companion(null);

        @JvmField
        @NotNull
        public static final Version INFINITY = new Version(256, 256, 256);
        private final int major;
        private final int minor;
        private final int patch;

        public boolean equals(@Nullable Object obj) {
            if (this != obj) {
                if (obj instanceof Version) {
                    Version version = (Version) obj;
                    if (this.major == version.major) {
                        if (this.minor == version.minor) {
                            if (this.patch == version.patch) {
                            }
                        }
                    }
                }
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (((this.major * 31) + this.minor) * 31) + this.patch;
        }

        public Version(int major, int minor, int patch) {
            this.major = major;
            this.minor = minor;
            this.patch = patch;
        }

        public /* synthetic */ Version(int i, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, i2, (i4 & 4) != 0 ? 0 : i3);
        }

        @NotNull
        public final String asString() {
            StringBuilder sb;
            int i;
            if (this.patch == 0) {
                sb = new StringBuilder();
                sb.append(this.major);
                sb.append('.');
                i = this.minor;
            } else {
                sb = new StringBuilder();
                sb.append(this.major);
                sb.append('.');
                sb.append(this.minor);
                sb.append('.');
                i = this.patch;
            }
            sb.append(i);
            return sb.toString();
        }

        @NotNull
        public String toString() {
            return asString();
        }

        /* JADX INFO: compiled from: VersionRequirement.kt */
        public static final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
                this();
            }

            @NotNull
            public final Version decode(@Nullable Integer version, @Nullable Integer versionFull) {
                if (versionFull != null) {
                    return new Version(versionFull.intValue() & 255, (versionFull.intValue() >> 8) & 255, (versionFull.intValue() >> 16) & 255);
                }
                if (version != null) {
                    return new Version(version.intValue() & 7, (version.intValue() >> 3) & 15, (version.intValue() >> 7) & 127);
                }
                return Version.INFINITY;
            }
        }
    }

    @NotNull
    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("since ");
        sb.append(this.version);
        sb.append(' ');
        sb.append(this.level);
        String str2 = "";
        if (this.errorCode != null) {
            str = " error " + this.errorCode;
        } else {
            str = "";
        }
        sb.append(str);
        if (this.message != null) {
            str2 = ": " + this.message;
        }
        sb.append(str2);
        return sb.toString();
    }

    /* JADX INFO: compiled from: VersionRequirement.kt */
    public static final class Companion {

        public final /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0 = new int[ProtoBuf.VersionRequirement.Level.values().length];

            static {
                $EnumSwitchMapping$0[ProtoBuf.VersionRequirement.Level.WARNING.ordinal()] = 1;
                $EnumSwitchMapping$0[ProtoBuf.VersionRequirement.Level.ERROR.ordinal()] = 2;
                $EnumSwitchMapping$0[ProtoBuf.VersionRequirement.Level.HIDDEN.ordinal()] = 3;
            }
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final List<VersionRequirement> create(@NotNull MessageLite proto, @NotNull NameResolver nameResolver, @NotNull VersionRequirementTable table) {
            Iterable ids;
            Intrinsics.checkParameterIsNotNull(proto, "proto");
            Intrinsics.checkParameterIsNotNull(nameResolver, "nameResolver");
            Intrinsics.checkParameterIsNotNull(table, "table");
            if (proto instanceof ProtoBuf.Class) {
                ids = ((ProtoBuf.Class) proto).getVersionRequirementList();
            } else if (proto instanceof ProtoBuf.Constructor) {
                ids = ((ProtoBuf.Constructor) proto).getVersionRequirementList();
            } else if (proto instanceof ProtoBuf.Function) {
                ids = ((ProtoBuf.Function) proto).getVersionRequirementList();
            } else if (proto instanceof ProtoBuf.Property) {
                ids = ((ProtoBuf.Property) proto).getVersionRequirementList();
            } else {
                if (!(proto instanceof ProtoBuf.TypeAlias)) {
                    throw new IllegalStateException("Unexpected declaration: " + proto.getClass());
                }
                ids = ((ProtoBuf.TypeAlias) proto).getVersionRequirementList();
            }
            Intrinsics.checkExpressionValueIsNotNull(ids, "ids");
            Iterable $receiver$iv = ids;
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv$iv : $receiver$iv) {
                Integer id = (Integer) element$iv$iv$iv;
                Companion companion = VersionRequirement.Companion;
                Intrinsics.checkExpressionValueIsNotNull(id, "id");
                VersionRequirement versionRequirementCreate = companion.create(id.intValue(), nameResolver, table);
                if (versionRequirementCreate != null) {
                    destination$iv$iv.add(versionRequirementCreate);
                }
            }
            return (List) destination$iv$iv;
        }

        @Nullable
        public final VersionRequirement create(int id, @NotNull NameResolver nameResolver, @NotNull VersionRequirementTable table) {
            DeprecationLevel level;
            Intrinsics.checkParameterIsNotNull(nameResolver, "nameResolver");
            Intrinsics.checkParameterIsNotNull(table, "table");
            ProtoBuf.VersionRequirement info = table.get(id);
            if (info == null) {
                return null;
            }
            Version version = Version.Companion.decode(info.hasVersion() ? Integer.valueOf(info.getVersion()) : null, info.hasVersionFull() ? Integer.valueOf(info.getVersionFull()) : null);
            ProtoBuf.VersionRequirement.Level level2 = info.getLevel();
            if (level2 == null) {
                Intrinsics.throwNpe();
            }
            int i = WhenMappings.$EnumSwitchMapping$0[level2.ordinal()];
            if (i == 1) {
                level = DeprecationLevel.WARNING;
            } else if (i == 2) {
                level = DeprecationLevel.ERROR;
            } else {
                if (i != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                level = DeprecationLevel.HIDDEN;
            }
            Integer errorCode = info.hasErrorCode() ? Integer.valueOf(info.getErrorCode()) : null;
            String message = info.hasMessage() ? nameResolver.getString(info.getMessage()) : null;
            ProtoBuf.VersionRequirement.VersionKind versionKind = info.getVersionKind();
            Intrinsics.checkExpressionValueIsNotNull(versionKind, "info.versionKind");
            return new VersionRequirement(version, versionKind, level, errorCode, message);
        }
    }
}
