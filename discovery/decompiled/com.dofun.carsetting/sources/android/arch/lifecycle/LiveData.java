package android.arch.lifecycle;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class LiveData<T> {
    private static final Object NOT_SET = new Object();
    static final int START_VERSION = -1;
    private boolean mDispatchInvalidated;
    private boolean mDispatchingValue;
    private final Object mDataLock = new Object();
    private android.arch.a.b.b<j<T>, LiveData<T>.b> mObservers = new android.arch.a.b.b<>();
    private int mActiveCount = 0;
    private volatile Object mData = NOT_SET;
    private volatile Object mPendingData = NOT_SET;
    private int mVersion = -1;
    private final Runnable mPostValueRunnable = new Runnable() { // from class: android.arch.lifecycle.LiveData.1
        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.lang.Runnable
        public void run() {
            Object obj;
            synchronized (LiveData.this.mDataLock) {
                obj = LiveData.this.mPendingData;
                LiveData.this.mPendingData = LiveData.NOT_SET;
            }
            LiveData.this.setValue(obj);
        }
    };

    protected void onActive() {
    }

    protected void onInactive() {
    }

    private void considerNotify(LiveData<T>.b bVar) {
        if (bVar.d) {
            if (!bVar.a()) {
                bVar.a(false);
            } else {
                if (bVar.e >= this.mVersion) {
                    return;
                }
                bVar.e = this.mVersion;
                bVar.c.onChanged((T) this.mData);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchingValue(@Nullable LiveData<T>.b bVar) {
        if (this.mDispatchingValue) {
            this.mDispatchInvalidated = true;
            return;
        }
        this.mDispatchingValue = true;
        do {
            this.mDispatchInvalidated = false;
            if (bVar != null) {
                considerNotify(bVar);
                bVar = null;
            } else {
                android.arch.a.b.b<j<T>, LiveData<T>.b>.d dVarC = this.mObservers.c();
                while (dVarC.hasNext()) {
                    considerNotify((b) dVarC.next().getValue());
                    if (this.mDispatchInvalidated) {
                        break;
                    }
                }
            }
        } while (this.mDispatchInvalidated);
        this.mDispatchingValue = false;
    }

    @MainThread
    public void observe(@NonNull d dVar, @NonNull j<T> jVar) {
        if (dVar.getLifecycle().a() == Lifecycle.State.DESTROYED) {
            return;
        }
        LifecycleBoundObserver lifecycleBoundObserver = new LifecycleBoundObserver(dVar, jVar);
        LiveData<T>.b bVarA = this.mObservers.a(jVar, lifecycleBoundObserver);
        if (bVarA != null && !bVarA.a(dVar)) {
            throw new IllegalArgumentException("Cannot add the same observer with different lifecycles");
        }
        if (bVarA != null) {
            return;
        }
        dVar.getLifecycle().a(lifecycleBoundObserver);
    }

    @MainThread
    public void observeForever(@NonNull j<T> jVar) {
        a aVar = new a(jVar);
        LiveData<T>.b bVarA = this.mObservers.a(jVar, aVar);
        if (bVarA != null && (bVarA instanceof LifecycleBoundObserver)) {
            throw new IllegalArgumentException("Cannot add the same observer with different lifecycles");
        }
        if (bVarA != null) {
            return;
        }
        aVar.a(true);
    }

    @MainThread
    public void removeObserver(@NonNull j<T> jVar) {
        assertMainThread("removeObserver");
        LiveData<T>.b bVarB = this.mObservers.b(jVar);
        if (bVarB == null) {
            return;
        }
        bVarB.b();
        bVarB.a(false);
    }

    @MainThread
    public void removeObservers(@NonNull d dVar) {
        assertMainThread("removeObservers");
        for (Map.Entry<j<T>, LiveData<T>.b> entry : this.mObservers) {
            if (entry.getValue().a(dVar)) {
                removeObserver(entry.getKey());
            }
        }
    }

    protected void postValue(T t) {
        boolean z;
        synchronized (this.mDataLock) {
            z = this.mPendingData == NOT_SET;
            this.mPendingData = t;
        }
        if (z) {
            android.arch.a.a.a.a().b(this.mPostValueRunnable);
        }
    }

    @MainThread
    protected void setValue(T t) {
        assertMainThread("setValue");
        this.mVersion++;
        this.mData = t;
        dispatchingValue(null);
    }

    @Nullable
    public T getValue() {
        T t = (T) this.mData;
        if (t != NOT_SET) {
            return t;
        }
        return null;
    }

    int getVersion() {
        return this.mVersion;
    }

    public boolean hasObservers() {
        return this.mObservers.a() > 0;
    }

    public boolean hasActiveObservers() {
        return this.mActiveCount > 0;
    }

    class LifecycleBoundObserver extends LiveData<T>.b implements GenericLifecycleObserver {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        @NonNull
        final d f12a;

        LifecycleBoundObserver(@NonNull d dVar, j<T> jVar) {
            super(jVar);
            this.f12a = dVar;
        }

        @Override // android.arch.lifecycle.LiveData.b
        boolean a() {
            return this.f12a.getLifecycle().a().isAtLeast(Lifecycle.State.STARTED);
        }

        @Override // android.arch.lifecycle.GenericLifecycleObserver
        public void a(d dVar, Lifecycle.Event event) {
            if (this.f12a.getLifecycle().a() == Lifecycle.State.DESTROYED) {
                LiveData.this.removeObserver(this.c);
            } else {
                a(a());
            }
        }

        @Override // android.arch.lifecycle.LiveData.b
        boolean a(d dVar) {
            return this.f12a == dVar;
        }

        @Override // android.arch.lifecycle.LiveData.b
        void b() {
            this.f12a.getLifecycle().b(this);
        }
    }

    private abstract class b {
        final j<T> c;
        boolean d;
        int e = -1;

        abstract boolean a();

        boolean a(d dVar) {
            return false;
        }

        void b() {
        }

        b(j<T> jVar) {
            this.c = jVar;
        }

        void a(boolean z) {
            if (z == this.d) {
                return;
            }
            this.d = z;
            boolean z2 = LiveData.this.mActiveCount == 0;
            LiveData.this.mActiveCount += this.d ? 1 : -1;
            if (z2 && this.d) {
                LiveData.this.onActive();
            }
            if (LiveData.this.mActiveCount == 0 && !this.d) {
                LiveData.this.onInactive();
            }
            if (this.d) {
                LiveData.this.dispatchingValue(this);
            }
        }
    }

    private class a extends LiveData<T>.b {
        @Override // android.arch.lifecycle.LiveData.b
        boolean a() {
            return true;
        }

        a(j<T> jVar) {
            super(jVar);
        }
    }

    private static void assertMainThread(String str) {
        if (android.arch.a.a.a.a().b()) {
            return;
        }
        throw new IllegalStateException("Cannot invoke " + str + " on a background thread");
    }
}
