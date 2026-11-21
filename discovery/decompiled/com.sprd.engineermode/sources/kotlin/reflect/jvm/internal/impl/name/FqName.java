package kotlin.reflect.jvm.internal.impl.name;

import java.util.List;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes4.dex */
public final class FqName {
    public static final FqName ROOT = new FqName("");

    @NotNull
    private final FqNameUnsafe fqName;
    private transient FqName parent;

    public FqName(@NotNull String fqName) {
        this.fqName = new FqNameUnsafe(fqName, this);
    }

    public FqName(@NotNull FqNameUnsafe fqName) {
        this.fqName = fqName;
    }

    private FqName(@NotNull FqNameUnsafe fqName, FqName parent) {
        this.fqName = fqName;
        this.parent = parent;
    }

    @NotNull
    public String asString() {
        return this.fqName.asString();
    }

    @NotNull
    public FqNameUnsafe toUnsafe() {
        return this.fqName;
    }

    public boolean isRoot() {
        return this.fqName.isRoot();
    }

    @NotNull
    public FqName parent() {
        FqName fqName = this.parent;
        if (fqName != null) {
            return fqName;
        }
        if (isRoot()) {
            throw new IllegalStateException("root");
        }
        this.parent = new FqName(this.fqName.parent());
        return this.parent;
    }

    @NotNull
    public FqName child(@NotNull Name name) {
        return new FqName(this.fqName.child(name), this);
    }

    @NotNull
    public Name shortName() {
        return this.fqName.shortName();
    }

    @NotNull
    public Name shortNameOrSpecial() {
        return this.fqName.shortNameOrSpecial();
    }

    @NotNull
    public List<Name> pathSegments() {
        return this.fqName.pathSegments();
    }

    public boolean startsWith(@NotNull Name segment) {
        return this.fqName.startsWith(segment);
    }

    @NotNull
    public static FqName topLevel(@NotNull Name shortName) {
        return new FqName(FqNameUnsafe.topLevel(shortName));
    }

    public String toString() {
        return this.fqName.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FqName)) {
            return false;
        }
        FqName otherFqName = (FqName) o;
        return this.fqName.equals(otherFqName.fqName);
    }

    public int hashCode() {
        return this.fqName.hashCode();
    }
}
