package kotlin.reflect.jvm.internal.impl.name;

import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public final class ClassId {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final boolean local;
    private final FqName packageFqName;
    private final FqName relativeClassName;

    @NotNull
    public static ClassId topLevel(@NotNull FqName topLevelFqName) {
        return new ClassId(topLevelFqName.parent(), topLevelFqName.shortName());
    }

    public ClassId(@NotNull FqName packageFqName, @NotNull FqName relativeClassName, boolean local) {
        this.packageFqName = packageFqName;
        this.relativeClassName = relativeClassName;
        this.local = local;
    }

    public ClassId(@NotNull FqName packageFqName, @NotNull Name topLevelName) {
        this(packageFqName, FqName.topLevel(topLevelName), false);
    }

    @NotNull
    public FqName getPackageFqName() {
        return this.packageFqName;
    }

    @NotNull
    public FqName getRelativeClassName() {
        return this.relativeClassName;
    }

    @NotNull
    public Name getShortClassName() {
        return this.relativeClassName.shortName();
    }

    public boolean isLocal() {
        return this.local;
    }

    @NotNull
    public ClassId createNestedClassId(@NotNull Name name) {
        return new ClassId(getPackageFqName(), this.relativeClassName.child(name), this.local);
    }

    @Nullable
    public ClassId getOuterClassId() {
        FqName parent = this.relativeClassName.parent();
        if (parent.isRoot()) {
            return null;
        }
        return new ClassId(getPackageFqName(), parent, this.local);
    }

    public boolean isNestedClass() {
        return !this.relativeClassName.parent().isRoot();
    }

    @NotNull
    public FqName asSingleFqName() {
        if (this.packageFqName.isRoot()) {
            return this.relativeClassName;
        }
        return new FqName(this.packageFqName.asString() + "." + this.relativeClassName.asString());
    }

    @NotNull
    public static ClassId fromString(@NotNull String string) {
        return fromString(string, false);
    }

    @NotNull
    public static ClassId fromString(@NotNull String string, boolean isLocal) {
        String packageName = StringsKt.substringBeforeLast(string, '/', "").replace('/', '.');
        String className = StringsKt.substringAfterLast(string, '/', string);
        return new ClassId(new FqName(packageName), new FqName(className), isLocal);
    }

    @NotNull
    public String asString() {
        if (this.packageFqName.isRoot()) {
            return this.relativeClassName.asString();
        }
        return this.packageFqName.asString().replace('.', '/') + "/" + this.relativeClassName.asString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClassId id = (ClassId) o;
        if (this.packageFqName.equals(id.packageFqName) && this.relativeClassName.equals(id.relativeClassName) && this.local == id.local) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = this.packageFqName.hashCode();
        return (((result * 31) + this.relativeClassName.hashCode()) * 31) + Boolean.valueOf(this.local).hashCode();
    }

    public String toString() {
        if (!this.packageFqName.isRoot()) {
            return asString();
        }
        return "/" + asString();
    }
}
