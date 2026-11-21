package kotlin.reflect.jvm.internal.impl.resolve.jvm;

import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.text.Typography;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes4.dex */
public class JvmClassName {
    private FqName fqName;
    private final String internalName;

    @NotNull
    public static JvmClassName byInternalName(@NotNull String internalName) {
        return new JvmClassName(internalName);
    }

    @NotNull
    public static JvmClassName byClassId(@NotNull ClassId classId) {
        FqName packageFqName = classId.getPackageFqName();
        String relativeClassName = classId.getRelativeClassName().asString().replace('.', Typography.dollar);
        if (packageFqName.isRoot()) {
            return new JvmClassName(relativeClassName);
        }
        return new JvmClassName(packageFqName.asString().replace('.', '/') + "/" + relativeClassName);
    }

    @NotNull
    public static JvmClassName byFqNameWithoutInnerClasses(@NotNull FqName fqName) {
        JvmClassName r = new JvmClassName(fqName.asString().replace('.', '/'));
        r.fqName = fqName;
        return r;
    }

    private JvmClassName(@NotNull String internalName) {
        this.internalName = internalName;
    }

    @NotNull
    public FqName getFqNameForTopLevelClassMaybeWithDollars() {
        return new FqName(this.internalName.replace('/', '.'));
    }

    @NotNull
    public FqName getPackageFqName() {
        int lastSlash = this.internalName.lastIndexOf("/");
        return lastSlash == -1 ? FqName.ROOT : new FqName(this.internalName.substring(0, lastSlash).replace('/', '.'));
    }

    @NotNull
    public String getInternalName() {
        return this.internalName;
    }

    public String toString() {
        return this.internalName;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.internalName.equals(((JvmClassName) o).internalName);
    }

    public int hashCode() {
        return this.internalName.hashCode();
    }
}
