package kotlin.reflect.jvm.internal.impl.types;

/* JADX INFO: loaded from: classes4.dex */
public abstract class TypeProjectionBase implements TypeProjection {
    public String toString() {
        if (isStarProjection()) {
            return "*";
        }
        if (getProjectionKind() == Variance.INVARIANT) {
            return getType().toString();
        }
        return getProjectionKind() + " " + getType();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeProjection)) {
            return false;
        }
        TypeProjection that = (TypeProjection) o;
        return isStarProjection() == that.isStarProjection() && getProjectionKind() == that.getProjectionKind() && getType().equals(that.getType());
    }

    public int hashCode() {
        int result = getProjectionKind().hashCode();
        return (result * 31) + (isStarProjection() ? 17 : getType().hashCode());
    }
}
