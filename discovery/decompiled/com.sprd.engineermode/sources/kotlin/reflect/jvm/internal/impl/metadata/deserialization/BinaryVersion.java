package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import android.support.v4.os.EnvironmentCompat;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: BinaryVersion.kt */
/* JADX INFO: loaded from: classes4.dex */
public abstract class BinaryVersion {
    public static final Companion Companion = new Companion(null);
    private final int major;
    private final int minor;
    private final int[] numbers;
    private final int patch;

    @NotNull
    private final List<Integer> rest;

    public BinaryVersion(@NotNull int... numbers) {
        Intrinsics.checkParameterIsNotNull(numbers, "numbers");
        this.numbers = numbers;
        Integer orNull = ArraysKt.getOrNull(this.numbers, 0);
        this.major = orNull != null ? orNull.intValue() : -1;
        Integer orNull2 = ArraysKt.getOrNull(this.numbers, 1);
        this.minor = orNull2 != null ? orNull2.intValue() : -1;
        Integer orNull3 = ArraysKt.getOrNull(this.numbers, 2);
        this.patch = orNull3 != null ? orNull3.intValue() : -1;
        int[] iArr = this.numbers;
        this.rest = iArr.length > 3 ? CollectionsKt.toList(ArraysKt.asList(iArr).subList(3, this.numbers.length)) : CollectionsKt.emptyList();
    }

    public final int getMajor() {
        return this.major;
    }

    public final int getMinor() {
        return this.minor;
    }

    @NotNull
    public final int[] toArray() {
        return this.numbers;
    }

    protected final boolean isCompatibleTo(@NotNull BinaryVersion ourVersion) {
        Intrinsics.checkParameterIsNotNull(ourVersion, "ourVersion");
        int i = this.major;
        if (i == 0) {
            return ourVersion.major == 0 && this.minor == ourVersion.minor;
        }
        return i == ourVersion.major && this.minor <= ourVersion.minor;
    }

    public final boolean isAtLeast(@NotNull BinaryVersion version) {
        Intrinsics.checkParameterIsNotNull(version, "version");
        return isAtLeast(version.major, version.minor, version.patch);
    }

    public final boolean isAtLeast(int major, int minor, int patch) {
        int i = this.major;
        if (i > major) {
            return true;
        }
        if (i < major) {
            return false;
        }
        int i2 = this.minor;
        if (i2 > minor) {
            return true;
        }
        return i2 >= minor && this.patch >= patch;
    }

    @NotNull
    public String toString() {
        int[] $receiver$iv = toArray();
        ArrayList list$iv = new ArrayList();
        int length = $receiver$iv.length;
        for (int i = 0; i < length; i++) {
            int item$iv = $receiver$iv[i];
            if (!(item$iv != -1)) {
                break;
            }
            list$iv.add(Integer.valueOf(item$iv));
        }
        ArrayList versions = list$iv;
        return versions.isEmpty() ? EnvironmentCompat.MEDIA_UNKNOWN : CollectionsKt.joinToString$default(versions, ".", null, null, 0, null, null, 62, null);
    }

    public boolean equals(@Nullable Object other) {
        return other != null && Intrinsics.areEqual(getClass(), other.getClass()) && this.major == ((BinaryVersion) other).major && this.minor == ((BinaryVersion) other).minor && this.patch == ((BinaryVersion) other).patch && Intrinsics.areEqual(this.rest, ((BinaryVersion) other).rest);
    }

    public int hashCode() {
        int result = this.major;
        int result2 = result + (result * 31) + this.minor;
        int result3 = result2 + (result2 * 31) + this.patch;
        return result3 + (result3 * 31) + this.rest.hashCode();
    }

    /* JADX INFO: compiled from: BinaryVersion.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
