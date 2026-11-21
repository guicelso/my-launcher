package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: ClassLiteralValue.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class ClassLiteralValue {
    private final int arrayNestedness;

    @NotNull
    private final ClassId classId;

    @NotNull
    public final ClassId component1() {
        return this.classId;
    }

    public final int component2() {
        return this.arrayNestedness;
    }

    public boolean equals(@Nullable Object obj) {
        if (this != obj) {
            if (obj instanceof ClassLiteralValue) {
                ClassLiteralValue classLiteralValue = (ClassLiteralValue) obj;
                if (Intrinsics.areEqual(this.classId, classLiteralValue.classId)) {
                    if (this.arrayNestedness == classLiteralValue.arrayNestedness) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        ClassId classId = this.classId;
        return ((classId != null ? classId.hashCode() : 0) * 31) + this.arrayNestedness;
    }

    public ClassLiteralValue(@NotNull ClassId classId, int arrayNestedness) {
        Intrinsics.checkParameterIsNotNull(classId, "classId");
        this.classId = classId;
        this.arrayNestedness = arrayNestedness;
    }

    public final int getArrayNestedness() {
        return this.arrayNestedness;
    }

    @NotNull
    public final ClassId getClassId() {
        return this.classId;
    }

    @NotNull
    public String toString() {
        StringBuilder $receiver = new StringBuilder();
        int i = this.arrayNestedness;
        for (int i2 = 0; i2 < i; i2++) {
            $receiver.append("kotlin/Array<");
        }
        $receiver.append(this.classId);
        int i3 = this.arrayNestedness;
        for (int i4 = 0; i4 < i3; i4++) {
            $receiver.append(">");
        }
        String string = $receiver.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }
}
