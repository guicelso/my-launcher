package kotlin.reflect.jvm.internal.pcollections;

/* JADX INFO: loaded from: classes4.dex */
final class IntTree<V> {
    static final IntTree<Object> EMPTYNODE = new IntTree<>();
    private final long key;
    private final IntTree<V> left;
    private final IntTree<V> right;
    private final int size;
    private final V value;

    private IntTree() {
        this.size = 0;
        this.key = 0L;
        this.value = null;
        this.left = null;
        this.right = null;
    }

    private IntTree(long key, V value, IntTree<V> left, IntTree<V> right) {
        this.key = key;
        this.value = value;
        this.left = left;
        this.right = right;
        this.size = left.size + 1 + right.size;
    }

    private IntTree<V> withKey(long newKey) {
        if (this.size == 0 || newKey == this.key) {
            return this;
        }
        return new IntTree<>(newKey, this.value, this.left, this.right);
    }

    V get(long key) {
        if (this.size == 0) {
            return null;
        }
        long j = this.key;
        if (key < j) {
            return this.left.get(key - j);
        }
        if (key > j) {
            return this.right.get(key - j);
        }
        return this.value;
    }

    IntTree<V> plus(long key, V value) {
        if (this.size == 0) {
            return new IntTree<>(key, value, this, this);
        }
        long j = this.key;
        if (key < j) {
            return rebalanced(this.left.plus(key - j, value), this.right);
        }
        if (key > j) {
            return rebalanced(this.left, this.right.plus(key - j, value));
        }
        if (value == this.value) {
            return this;
        }
        return new IntTree<>(key, value, this.left, this.right);
    }

    IntTree<V> minus(long key) {
        if (this.size == 0) {
            return this;
        }
        long j = this.key;
        if (key < j) {
            return rebalanced(this.left.minus(key - j), this.right);
        }
        if (key > j) {
            return rebalanced(this.left, this.right.minus(key - j));
        }
        IntTree<V> intTree = this.left;
        if (intTree.size == 0) {
            IntTree<V> intTree2 = this.right;
            return intTree2.withKey(intTree2.key + j);
        }
        IntTree<V> intTree3 = this.right;
        if (intTree3.size == 0) {
            return intTree.withKey(intTree.key + j);
        }
        long jMinKey = intTree3.minKey();
        long j2 = this.key;
        long newKey = jMinKey + j2;
        V newValue = this.right.get(newKey - j2);
        IntTree<V> newRight = this.right.minus(newKey - this.key);
        IntTree<V> newRight2 = newRight.withKey((newRight.key + this.key) - newKey);
        IntTree<V> intTree4 = this.left;
        IntTree<V> newLeft = intTree4.withKey((intTree4.key + this.key) - newKey);
        return rebalanced(newKey, newValue, newLeft, newRight2);
    }

    private long minKey() {
        IntTree<V> intTree = this.left;
        if (intTree.size == 0) {
            return this.key;
        }
        return intTree.minKey() + this.key;
    }

    private IntTree<V> rebalanced(IntTree<V> newLeft, IntTree<V> newRight) {
        if (newLeft == this.left && newRight == this.right) {
            return this;
        }
        return rebalanced(this.key, this.value, newLeft, newRight);
    }

    private static <V> IntTree<V> rebalanced(long key, V value, IntTree<V> left, IntTree<V> right) {
        int i = ((IntTree) left).size;
        int i2 = ((IntTree) right).size;
        if (i + i2 > 1) {
            if (i >= i2 * 5) {
                IntTree<V> ll = ((IntTree) left).left;
                IntTree<V> lr = ((IntTree) left).right;
                if (((IntTree) lr).size < ((IntTree) ll).size * 2) {
                    long j = ((IntTree) left).key;
                    return new IntTree<>(j + key, ((IntTree) left).value, ll, new IntTree(-j, value, lr.withKey(((IntTree) lr).key + j), right));
                }
                IntTree<V> lrl = ((IntTree) lr).left;
                IntTree<V> lrr = ((IntTree) lr).right;
                long j2 = ((IntTree) lr).key;
                long j3 = ((IntTree) left).key + j2 + key;
                V v = ((IntTree) lr).value;
                IntTree intTree = new IntTree(-j2, ((IntTree) left).value, ll, lrl.withKey(((IntTree) lrl).key + j2));
                long j4 = ((IntTree) left).key;
                long j5 = ((IntTree) lr).key;
                return new IntTree<>(j3, v, intTree, new IntTree((-j4) - j5, value, lrr.withKey(((IntTree) lrr).key + j5 + j4), right));
            }
            if (i2 >= i * 5) {
                IntTree<V> rl = ((IntTree) right).left;
                IntTree<V> rr = ((IntTree) right).right;
                if (((IntTree) rl).size < ((IntTree) rr).size * 2) {
                    long j6 = ((IntTree) right).key;
                    return new IntTree<>(j6 + key, ((IntTree) right).value, new IntTree(-j6, value, left, rl.withKey(((IntTree) rl).key + j6)), rr);
                }
                IntTree<V> rll = ((IntTree) rl).left;
                IntTree<V> rlr = ((IntTree) rl).right;
                long j7 = ((IntTree) rl).key;
                long j8 = ((IntTree) right).key;
                long j9 = j7 + j8 + key;
                V v2 = ((IntTree) rl).value;
                IntTree intTree2 = new IntTree((-j8) - j7, value, left, rll.withKey(((IntTree) rll).key + j7 + j8));
                long j10 = ((IntTree) rl).key;
                return new IntTree<>(j9, v2, intTree2, new IntTree(-j10, ((IntTree) right).value, rlr.withKey(((IntTree) rlr).key + j10), rr));
            }
        }
        return new IntTree<>(key, value, left, right);
    }
}
