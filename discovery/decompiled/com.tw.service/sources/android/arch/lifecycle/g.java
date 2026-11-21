package android.arch.lifecycle;

import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class g extends c {
    private final WeakReference ba;
    private a.a.a.b.a aa = new a.a.a.b.a();
    private int ca = 0;
    private boolean da = false;
    private boolean ea = false;
    private ArrayList fa = new ArrayList();
    private Lifecycle$State mState = Lifecycle$State.INITIALIZED;

    public g(e eVar) {
        this.ba = new WeakReference(eVar);
    }

    static Lifecycle$State a(Lifecycle$Event lifecycle$Event) {
        int iOrdinal = lifecycle$Event.ordinal();
        if (iOrdinal != 0) {
            if (iOrdinal != 1) {
                if (iOrdinal == 2) {
                    return Lifecycle$State.RESUMED;
                }
                if (iOrdinal != 3) {
                    if (iOrdinal != 4) {
                        if (iOrdinal == 5) {
                            return Lifecycle$State.DESTROYED;
                        }
                        throw new IllegalArgumentException("Unexpected event value " + lifecycle$Event);
                    }
                }
            }
            return Lifecycle$State.STARTED;
        }
        return Lifecycle$State.CREATED;
    }

    static Lifecycle$State a(Lifecycle$State lifecycle$State, Lifecycle$State lifecycle$State2) {
        return (lifecycle$State2 == null || lifecycle$State2.compareTo(lifecycle$State) >= 0) ? lifecycle$State : lifecycle$State2;
    }

    private void b(Lifecycle$State lifecycle$State) {
        if (this.mState == lifecycle$State) {
            return;
        }
        this.mState = lifecycle$State;
        if (this.da || this.ca != 0) {
            this.ea = true;
            return;
        }
        this.da = true;
        sync();
        this.da = false;
    }

    private static Lifecycle$Event c(Lifecycle$State lifecycle$State) {
        int iOrdinal = lifecycle$State.ordinal();
        if (iOrdinal == 0 || iOrdinal == 1) {
            return Lifecycle$Event.ON_CREATE;
        }
        if (iOrdinal == 2) {
            return Lifecycle$Event.ON_START;
        }
        if (iOrdinal == 3) {
            return Lifecycle$Event.ON_RESUME;
        }
        if (iOrdinal == 4) {
            throw new IllegalArgumentException();
        }
        throw new IllegalArgumentException("Unexpected state value " + lifecycle$State);
    }

    private void sa() {
        ArrayList arrayList = this.fa;
        arrayList.remove(arrayList.size() - 1);
    }

    /* JADX WARN: Code duplicated, block: B:13:0x003f  */
    private void sync() {
        boolean z;
        Lifecycle$Event lifecycle$Event;
        e eVar = (e) this.ba.get();
        if (eVar == null) {
            Log.w("LifecycleRegistry", "LifecycleOwner is garbage collected, you shouldn't try dispatch new events from it.");
            return;
        }
        while (true) {
            if (this.aa.size() == 0) {
                z = true;
            } else {
                Lifecycle$State lifecycle$State = ((f) this.aa.eldest().getValue()).mState;
                Lifecycle$State lifecycle$State2 = ((f) this.aa.g().getValue()).mState;
                if (lifecycle$State == lifecycle$State2 && this.mState == lifecycle$State2) {
                    z = true;
                } else {
                    z = false;
                }
            }
            this.ea = false;
            if (z) {
                return;
            }
            if (this.mState.compareTo(((f) this.aa.eldest().getValue()).mState) < 0) {
                Iterator itDescendingIterator = this.aa.descendingIterator();
                while (itDescendingIterator.hasNext() && !this.ea) {
                    Map.Entry entry = (Map.Entry) itDescendingIterator.next();
                    f fVar = (f) entry.getValue();
                    while (fVar.mState.compareTo(this.mState) > 0 && !this.ea && this.aa.contains(entry.getKey())) {
                        Lifecycle$State lifecycle$State3 = fVar.mState;
                        int iOrdinal = lifecycle$State3.ordinal();
                        if (iOrdinal == 0) {
                            throw new IllegalArgumentException();
                        }
                        if (iOrdinal == 1) {
                            throw new IllegalArgumentException();
                        }
                        if (iOrdinal == 2) {
                            lifecycle$Event = Lifecycle$Event.ON_DESTROY;
                        } else if (iOrdinal == 3) {
                            lifecycle$Event = Lifecycle$Event.ON_STOP;
                        } else {
                            if (iOrdinal != 4) {
                                throw new IllegalArgumentException("Unexpected state value " + lifecycle$State3);
                            }
                            lifecycle$Event = Lifecycle$Event.ON_PAUSE;
                        }
                        this.fa.add(a(lifecycle$Event));
                        fVar.b(eVar, lifecycle$Event);
                        sa();
                    }
                }
            }
            Map.Entry entryG = this.aa.g();
            if (!this.ea && entryG != null && this.mState.compareTo(((f) entryG.getValue()).mState) > 0) {
                a.a.a.b.f fVarF = this.aa.f();
                while (fVarF.hasNext() && !this.ea) {
                    Map.Entry entry2 = (Map.Entry) fVarF.next();
                    f fVar2 = (f) entry2.getValue();
                    while (fVar2.mState.compareTo(this.mState) < 0 && !this.ea && this.aa.contains(entry2.getKey())) {
                        this.fa.add(fVar2.mState);
                        fVar2.b(eVar, c(fVar2.mState));
                        sa();
                    }
                }
            }
        }
    }

    public void a(Lifecycle$State lifecycle$State) {
        b(lifecycle$State);
    }

    @Override // android.arch.lifecycle.c
    public void a(d dVar) {
        this.aa.remove(dVar);
    }

    public void b(Lifecycle$Event lifecycle$Event) {
        b(a(lifecycle$Event));
    }

    @Override // android.arch.lifecycle.c
    public Lifecycle$State getCurrentState() {
        return this.mState;
    }
}
