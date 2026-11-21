package android.arch.lifecycle;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: LifecycleRegistry.java */
/* JADX INFO: loaded from: classes.dex */
public class e extends Lifecycle {
    private final WeakReference<d> c;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private android.arch.a.b.a<c, a> f19a = new android.arch.a.b.a<>();
    private int d = 0;
    private boolean e = false;
    private boolean f = false;
    private ArrayList<Lifecycle.State> g = new ArrayList<>();
    private Lifecycle.State b = Lifecycle.State.INITIALIZED;

    public e(@NonNull d dVar) {
        this.c = new WeakReference<>(dVar);
    }

    @MainThread
    public void a(@NonNull Lifecycle.State state) {
        b(state);
    }

    public void a(@NonNull Lifecycle.Event event) {
        b(b(event));
    }

    private void b(Lifecycle.State state) {
        if (this.b == state) {
            return;
        }
        this.b = state;
        if (this.e || this.d != 0) {
            this.f = true;
            return;
        }
        this.e = true;
        d();
        this.e = false;
    }

    private boolean b() {
        if (this.f19a.a() == 0) {
            return true;
        }
        Lifecycle.State state = this.f19a.d().getValue().f21a;
        Lifecycle.State state2 = this.f19a.e().getValue().f21a;
        return state == state2 && this.b == state2;
    }

    private Lifecycle.State c(c cVar) {
        Map.Entry<c, a> entryD = this.f19a.d(cVar);
        return a(a(this.b, entryD != null ? entryD.getValue().f21a : null), this.g.isEmpty() ? null : this.g.get(this.g.size() - 1));
    }

    @Override // android.arch.lifecycle.Lifecycle
    public void a(@NonNull c cVar) {
        d dVar;
        a aVar = new a(cVar, this.b == Lifecycle.State.DESTROYED ? Lifecycle.State.DESTROYED : Lifecycle.State.INITIALIZED);
        if (this.f19a.a(cVar, aVar) == null && (dVar = this.c.get()) != null) {
            boolean z = this.d != 0 || this.e;
            Lifecycle.State stateC = c(cVar);
            this.d++;
            while (aVar.f21a.compareTo(stateC) < 0 && this.f19a.c(cVar)) {
                c(aVar.f21a);
                aVar.a(dVar, e(aVar.f21a));
                c();
                stateC = c(cVar);
            }
            if (!z) {
                d();
            }
            this.d--;
        }
    }

    private void c() {
        this.g.remove(this.g.size() - 1);
    }

    private void c(Lifecycle.State state) {
        this.g.add(state);
    }

    @Override // android.arch.lifecycle.Lifecycle
    public void b(@NonNull c cVar) {
        this.f19a.b(cVar);
    }

    @Override // android.arch.lifecycle.Lifecycle
    @NonNull
    public Lifecycle.State a() {
        return this.b;
    }

    static Lifecycle.State b(Lifecycle.Event event) {
        switch (event) {
            case ON_CREATE:
            case ON_STOP:
                return Lifecycle.State.CREATED;
            case ON_START:
            case ON_PAUSE:
                return Lifecycle.State.STARTED;
            case ON_RESUME:
                return Lifecycle.State.RESUMED;
            case ON_DESTROY:
                return Lifecycle.State.DESTROYED;
            default:
                throw new IllegalArgumentException("Unexpected event value " + event);
        }
    }

    private static Lifecycle.Event d(Lifecycle.State state) {
        switch (state) {
            case INITIALIZED:
                throw new IllegalArgumentException();
            case CREATED:
                return Lifecycle.Event.ON_DESTROY;
            case STARTED:
                return Lifecycle.Event.ON_STOP;
            case RESUMED:
                return Lifecycle.Event.ON_PAUSE;
            case DESTROYED:
                throw new IllegalArgumentException();
            default:
                throw new IllegalArgumentException("Unexpected state value " + state);
        }
    }

    private static Lifecycle.Event e(Lifecycle.State state) {
        switch (state) {
            case INITIALIZED:
            case DESTROYED:
                return Lifecycle.Event.ON_CREATE;
            case CREATED:
                return Lifecycle.Event.ON_START;
            case STARTED:
                return Lifecycle.Event.ON_RESUME;
            case RESUMED:
                throw new IllegalArgumentException();
            default:
                throw new IllegalArgumentException("Unexpected state value " + state);
        }
    }

    private void a(d dVar) {
        android.arch.a.b.b<c, a>.d dVarC = this.f19a.c();
        while (dVarC.hasNext() && !this.f) {
            Map.Entry next = dVarC.next();
            a aVar = (a) next.getValue();
            while (aVar.f21a.compareTo(this.b) < 0 && !this.f && this.f19a.c((c) next.getKey())) {
                c(aVar.f21a);
                aVar.a(dVar, e(aVar.f21a));
                c();
            }
        }
    }

    private void b(d dVar) {
        Iterator<Map.Entry<c, a>> itB = this.f19a.b();
        while (itB.hasNext() && !this.f) {
            Map.Entry<c, a> next = itB.next();
            a value = next.getValue();
            while (value.f21a.compareTo(this.b) > 0 && !this.f && this.f19a.c(next.getKey())) {
                Lifecycle.Event eventD = d(value.f21a);
                c(b(eventD));
                value.a(dVar, eventD);
                c();
            }
        }
    }

    private void d() {
        d dVar = this.c.get();
        if (dVar == null) {
            Log.w("LifecycleRegistry", "LifecycleOwner is garbage collected, you shouldn't try dispatch new events from it.");
            return;
        }
        while (!b()) {
            this.f = false;
            if (this.b.compareTo(this.f19a.d().getValue().f21a) < 0) {
                b(dVar);
            }
            Map.Entry<c, a> entryE = this.f19a.e();
            if (!this.f && entryE != null && this.b.compareTo(entryE.getValue().f21a) > 0) {
                a(dVar);
            }
        }
        this.f = false;
    }

    static Lifecycle.State a(@NonNull Lifecycle.State state, @Nullable Lifecycle.State state2) {
        return (state2 == null || state2.compareTo(state) >= 0) ? state : state2;
    }

    /* JADX INFO: compiled from: LifecycleRegistry.java */
    static class a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        Lifecycle.State f21a;
        GenericLifecycleObserver b;

        a(c cVar, Lifecycle.State state) {
            this.b = g.a(cVar);
            this.f21a = state;
        }

        void a(d dVar, Lifecycle.Event event) {
            Lifecycle.State stateB = e.b(event);
            this.f21a = e.a(this.f21a, stateB);
            this.b.a(dVar, event);
            this.f21a = stateB;
        }
    }
}
