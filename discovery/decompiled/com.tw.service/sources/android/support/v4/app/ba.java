package android.support.v4.app;

import android.graphics.Rect;
import android.os.Build;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class ba {
    private static final int[] Td = {0, 3, 0, 1, 5, 4, 7, 6, 9, 8};
    private static final ka Ud;
    private static final ka Vd;

    static {
        ka kaVar;
        int i = Build.VERSION.SDK_INT;
        Ud = new ga();
        try {
            kaVar = (ka) Class.forName("android.support.transition.FragmentTransitionSupport").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            kaVar = null;
        }
        Vd = kaVar;
    }

    private static ka a(ComponentCallbacksC0013n componentCallbacksC0013n, ComponentCallbacksC0013n componentCallbacksC0013n2) {
        ArrayList arrayList = new ArrayList();
        if (componentCallbacksC0013n != null) {
            Object exitTransition = componentCallbacksC0013n.getExitTransition();
            if (exitTransition != null) {
                arrayList.add(exitTransition);
            }
            Object returnTransition = componentCallbacksC0013n.getReturnTransition();
            if (returnTransition != null) {
                arrayList.add(returnTransition);
            }
            Object sharedElementReturnTransition = componentCallbacksC0013n.getSharedElementReturnTransition();
            if (sharedElementReturnTransition != null) {
                arrayList.add(sharedElementReturnTransition);
            }
        }
        if (componentCallbacksC0013n2 != null) {
            Object enterTransition = componentCallbacksC0013n2.getEnterTransition();
            if (enterTransition != null) {
                arrayList.add(enterTransition);
            }
            Object reenterTransition = componentCallbacksC0013n2.getReenterTransition();
            if (reenterTransition != null) {
                arrayList.add(reenterTransition);
            }
            Object sharedElementEnterTransition = componentCallbacksC0013n2.getSharedElementEnterTransition();
            if (sharedElementEnterTransition != null) {
                arrayList.add(sharedElementEnterTransition);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        ka kaVar = Ud;
        if (kaVar != null && a(kaVar, arrayList)) {
            return Ud;
        }
        ka kaVar2 = Vd;
        if (kaVar2 != null && a(kaVar2, arrayList)) {
            return Vd;
        }
        if (Ud == null && Vd == null) {
            return null;
        }
        throw new IllegalArgumentException("Invalid Transition types");
    }

    private static Object a(ka kaVar, ComponentCallbacksC0013n componentCallbacksC0013n, boolean z) {
        if (componentCallbacksC0013n == null) {
            return null;
        }
        return kaVar.d(z ? componentCallbacksC0013n.getReenterTransition() : componentCallbacksC0013n.getEnterTransition());
    }

    /* JADX WARN: Code duplicated, block: B:46:0x005f  */
    /* JADX WARN: Code duplicated, block: B:59:0x007d  */
    /* JADX WARN: Code duplicated, block: B:60:0x007f  */
    /* JADX WARN: Code duplicated, block: B:62:0x0084 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:63:0x0086  */
    /* JADX WARN: Code duplicated, block: B:64:0x0089  */
    /* JADX WARN: Code duplicated, block: B:68:0x0091  */
    /* JADX WARN: Code duplicated, block: B:69:0x0093  */
    private static void a(C0004e c0004e, C0003d c0003d, SparseArray sparseArray, boolean z, boolean z2) {
        int i;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        View view;
        boolean z7;
        ComponentCallbacksC0013n componentCallbacksC0013n = c0003d.fragment;
        if (componentCallbacksC0013n == null || (i = componentCallbacksC0013n.mContainerId) == 0) {
            return;
        }
        int i2 = z ? Td[c0003d.cmd] : c0003d.cmd;
        boolean z8 = false;
        if (i2 == 1) {
            if (z2) {
                z3 = componentCallbacksC0013n.mIsNewlyAdded;
            } else if (!componentCallbacksC0013n.mAdded || componentCallbacksC0013n.mHidden) {
                z3 = false;
            } else {
                z3 = true;
            }
            z4 = false;
            z5 = false;
            z8 = z3;
            z6 = true;
        } else if (i2 == 3) {
            boolean z9 = componentCallbacksC0013n.mAdded;
            if (z2 ? !z9 || componentCallbacksC0013n.mHidden : z9 || (view = componentCallbacksC0013n.mView) == null || view.getVisibility() != 0 || componentCallbacksC0013n.kd < 0.0f) {
                z7 = false;
            } else {
                z7 = true;
            }
            z5 = z7;
            z6 = false;
            z4 = true;
        } else if (i2 == 4) {
            if (!z2 ? !(!componentCallbacksC0013n.mAdded || componentCallbacksC0013n.mHidden) : componentCallbacksC0013n.mHiddenChanged && componentCallbacksC0013n.mAdded && componentCallbacksC0013n.mHidden) {
                z7 = false;
            } else {
                z7 = true;
            }
            z5 = z7;
            z6 = false;
            z4 = true;
        } else if (i2 == 5) {
            if (!z2) {
                z3 = componentCallbacksC0013n.mHidden;
            } else if (componentCallbacksC0013n.mHiddenChanged && !componentCallbacksC0013n.mHidden && componentCallbacksC0013n.mAdded) {
                z3 = true;
            } else {
                z3 = false;
            }
            z4 = false;
            z5 = false;
            z8 = z3;
            z6 = true;
        } else if (i2 == 6) {
            boolean z10 = componentCallbacksC0013n.mAdded;
            if (z2) {
                z7 = false;
            } else {
                z7 = false;
            }
            z5 = z7;
            z6 = false;
            z4 = true;
        } else if (i2 != 7) {
            z6 = false;
            z4 = false;
            z5 = false;
        } else {
            if (z2) {
                z3 = componentCallbacksC0013n.mIsNewlyAdded;
            } else {
                if (componentCallbacksC0013n.mAdded) {
                }
                z3 = false;
            }
            z4 = false;
            z5 = false;
            z8 = z3;
            z6 = true;
        }
        aa aaVar = (aa) sparseArray.get(i);
        if (z8) {
            if (aaVar == null) {
                aa aaVar2 = new aa();
                sparseArray.put(i, aaVar2);
                aaVar = aaVar2;
            }
            aaVar.lastIn = componentCallbacksC0013n;
            aaVar.lastInIsPop = z;
            aaVar.lastInTransaction = c0004e;
        }
        aa aaVar3 = aaVar;
        if (!z2 && z6) {
            if (aaVar3 != null && aaVar3.firstOut == componentCallbacksC0013n) {
                aaVar3.firstOut = null;
            }
            O o = c0004e.mManager;
            if (componentCallbacksC0013n.mState < 1 && o.mCurState >= 1 && !c0004e.mReorderingAllowed) {
                o.h(componentCallbacksC0013n);
                o.a(componentCallbacksC0013n, 1, 0, 0, false);
            }
        }
        if (z5 && (aaVar3 == null || aaVar3.firstOut == null)) {
            if (aaVar3 == null) {
                aa aaVar4 = new aa();
                sparseArray.put(i, aaVar4);
                aaVar3 = aaVar4;
            }
            aaVar3.firstOut = componentCallbacksC0013n;
            aaVar3.firstOutIsPop = z;
            aaVar3.firstOutTransaction = c0004e;
        }
        if (z2 || !z4 || aaVar3 == null || aaVar3.lastIn != componentCallbacksC0013n) {
            return;
        }
        aaVar3.lastIn = null;
    }

    private static void a(ka kaVar, Object obj, Object obj2, a.b.c.b.b bVar, boolean z, C0004e c0004e) {
        ArrayList arrayList = c0004e.mSharedElementSourceNames;
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        View view = (View) bVar.get((String) (z ? c0004e.mSharedElementTargetNames : c0004e.mSharedElementSourceNames).get(0));
        kaVar.b(obj, view);
        if (obj2 != null) {
            kaVar.b(obj2, view);
        }
    }

    private static void a(ArrayList arrayList, a.b.c.b.b bVar, Collection collection) {
        for (int size = bVar.size() - 1; size >= 0; size--) {
            View view = (View) bVar.valueAt(size);
            if (collection.contains(android.support.v4.view.r.h(view))) {
                arrayList.add(view);
            }
        }
    }

    private static boolean a(ka kaVar, List list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (!kaVar.c(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static a.b.c.b.b b(ka kaVar, a.b.c.b.b bVar, Object obj, aa aaVar) {
        ArrayList arrayList;
        ComponentCallbacksC0013n componentCallbacksC0013n = aaVar.lastIn;
        View view = componentCallbacksC0013n.mView;
        if (bVar.isEmpty() || obj == null || view == null) {
            bVar.clear();
            return null;
        }
        a.b.c.b.b bVar2 = new a.b.c.b.b();
        kaVar.a((Map) bVar2, view);
        C0004e c0004e = aaVar.lastInTransaction;
        if (aaVar.lastInIsPop) {
            C0011l c0011l = componentCallbacksC0013n.mAnimationInfo;
            if (c0011l != null) {
                qa qaVar = c0011l.mExitTransitionCallback;
            }
            arrayList = c0004e.mSharedElementSourceNames;
        } else {
            componentCallbacksC0013n.getEnterTransitionCallback();
            arrayList = c0004e.mSharedElementTargetNames;
        }
        if (arrayList != null) {
            bVar2.retainAll(arrayList);
            bVar2.retainAll(bVar.values());
        }
        int size = bVar.size();
        while (true) {
            size--;
            if (size < 0) {
                return bVar2;
            }
            if (!bVar2.containsKey((String) bVar.valueAt(size))) {
                bVar.removeAt(size);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static View b(a.b.c.b.b bVar, aa aaVar, Object obj, boolean z) {
        ArrayList arrayList;
        C0004e c0004e = aaVar.lastInTransaction;
        if (obj == null || bVar == null || (arrayList = c0004e.mSharedElementSourceNames) == null || arrayList.isEmpty()) {
            return null;
        }
        return (View) bVar.get((String) (z ? c0004e.mSharedElementSourceNames : c0004e.mSharedElementTargetNames).get(0));
    }

    private static Object b(ka kaVar, ComponentCallbacksC0013n componentCallbacksC0013n, boolean z) {
        if (componentCallbacksC0013n == null) {
            return null;
        }
        return kaVar.d(z ? componentCallbacksC0013n.getReturnTransition() : componentCallbacksC0013n.getExitTransition());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(ComponentCallbacksC0013n componentCallbacksC0013n, ComponentCallbacksC0013n componentCallbacksC0013n2, boolean z, a.b.c.b.b bVar, boolean z2) {
        if (z) {
            componentCallbacksC0013n2.getEnterTransitionCallback();
        } else {
            componentCallbacksC0013n.getEnterTransitionCallback();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(ArrayList arrayList, int i) {
        if (arrayList == null) {
            return;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ((View) arrayList.get(size)).setVisibility(i);
        }
    }

    private static a.b.c.b.b c(ka kaVar, a.b.c.b.b bVar, Object obj, aa aaVar) {
        ArrayList arrayList;
        if (bVar.isEmpty() || obj == null) {
            bVar.clear();
            return null;
        }
        ComponentCallbacksC0013n componentCallbacksC0013n = aaVar.firstOut;
        a.b.c.b.b bVar2 = new a.b.c.b.b();
        kaVar.a((Map) bVar2, componentCallbacksC0013n.mView);
        C0004e c0004e = aaVar.firstOutTransaction;
        if (aaVar.firstOutIsPop) {
            componentCallbacksC0013n.getEnterTransitionCallback();
            arrayList = c0004e.mSharedElementTargetNames;
        } else {
            C0011l c0011l = componentCallbacksC0013n.mAnimationInfo;
            if (c0011l != null) {
                qa qaVar = c0011l.mExitTransitionCallback;
            }
            arrayList = c0004e.mSharedElementSourceNames;
        }
        bVar2.retainAll(arrayList);
        bVar.retainAll(bVar2.keySet());
        return bVar2;
    }

    private static Object a(ka kaVar, ComponentCallbacksC0013n componentCallbacksC0013n, ComponentCallbacksC0013n componentCallbacksC0013n2, boolean z) {
        if (componentCallbacksC0013n == null || componentCallbacksC0013n2 == null) {
            return null;
        }
        Object objD = kaVar.d(z ? componentCallbacksC0013n2.getSharedElementReturnTransition() : componentCallbacksC0013n.getSharedElementEnterTransition());
        if (objD == null) {
            return null;
        }
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition((Transition) objD);
        return transitionSet;
    }

    private static Object a(ka kaVar, Object obj, Object obj2, Object obj3, ComponentCallbacksC0013n componentCallbacksC0013n, boolean z) {
        boolean zBooleanValue = true;
        if (obj != null && obj2 != null && componentCallbacksC0013n != null) {
            if (z) {
                C0011l c0011l = componentCallbacksC0013n.mAnimationInfo;
                if (c0011l != null && c0011l.ed != null) {
                    zBooleanValue = componentCallbacksC0013n.mAnimationInfo.ed.booleanValue();
                }
            } else {
                C0011l c0011l2 = componentCallbacksC0013n.mAnimationInfo;
                if (c0011l2 != null && c0011l2.gd != null) {
                    zBooleanValue = componentCallbacksC0013n.mAnimationInfo.gd.booleanValue();
                }
            }
        }
        return zBooleanValue ? kaVar.b(obj2, obj, obj3) : kaVar.a(obj2, obj, obj3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ArrayList b(ka kaVar, Object obj, ComponentCallbacksC0013n componentCallbacksC0013n, ArrayList arrayList, View view) {
        if (obj == null) {
            return null;
        }
        ArrayList arrayList2 = new ArrayList();
        View view2 = componentCallbacksC0013n.mView;
        if (view2 != null) {
            kaVar.a(arrayList2, view2);
        }
        if (arrayList != null) {
            arrayList2.removeAll(arrayList);
        }
        if (arrayList2.isEmpty()) {
            return arrayList2;
        }
        arrayList2.add(view);
        kaVar.a(obj, arrayList2);
        return arrayList2;
    }

    /* JADX WARN: Code duplicated, block: B:103:0x024d A[LOOP:6: B:102:0x024b->B:103:0x024d, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:106:0x0289  */
    /* JADX WARN: Code duplicated, block: B:108:0x0298  */
    /* JADX WARN: Code duplicated, block: B:109:0x029b  */
    /* JADX WARN: Code duplicated, block: B:111:0x02ac  */
    /* JADX WARN: Code duplicated, block: B:114:0x02c2 A[LOOP:8: B:110:0x02aa->B:114:0x02c2, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:151:0x03d1  */
    /* JADX WARN: Code duplicated, block: B:190:0x02b6 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:90:0x01fc  */
    /* JADX WARN: Code duplicated, block: B:92:0x021e  */
    /* JADX WARN: Code duplicated, block: B:93:0x0220 A[ADDED_TO_REGION] */
    static void a(O o, ArrayList arrayList, ArrayList arrayList2, int i, int i2, boolean z) {
        SparseArray sparseArray;
        int i3;
        int i4;
        ComponentCallbacksC0013n componentCallbacksC0013n;
        ComponentCallbacksC0013n componentCallbacksC0013n2;
        ka kaVarA;
        ArrayList arrayList3;
        Object obj;
        ArrayList arrayList4;
        Object obj2;
        Object obj3;
        ComponentCallbacksC0013n componentCallbacksC0013n3;
        ArrayList arrayList5;
        boolean z2;
        ComponentCallbacksC0013n componentCallbacksC0013n4;
        Rect rect;
        ComponentCallbacksC0013n componentCallbacksC0013n5;
        ComponentCallbacksC0013n componentCallbacksC0013n6;
        ka kaVarA2;
        ComponentCallbacksC0013n componentCallbacksC0013n7;
        Object obj4;
        ArrayList arrayListB;
        ArrayList arrayListB2;
        Object objA;
        ArrayList arrayList6;
        int size;
        int i5;
        int size2;
        ArrayList arrayList7;
        int i6;
        View view;
        String strH;
        a.b.c.b.b bVar;
        String str;
        int i7;
        View view2;
        Rect rect2;
        O o2 = o;
        ArrayList arrayList8 = arrayList;
        ArrayList arrayList9 = arrayList2;
        int i8 = i2;
        boolean z3 = z;
        if (o2.mCurState < 1) {
            return;
        }
        SparseArray sparseArray2 = new SparseArray();
        for (int i9 = i; i9 < i8; i9++) {
            C0004e c0004e = (C0004e) arrayList8.get(i9);
            if (((Boolean) arrayList9.get(i9)).booleanValue()) {
                if (c0004e.mManager.mContainer.onHasView()) {
                    for (int size3 = c0004e.mOps.size() - 1; size3 >= 0; size3--) {
                        a(c0004e, (C0003d) c0004e.mOps.get(size3), sparseArray2, true, z3);
                    }
                }
            } else {
                int size4 = c0004e.mOps.size();
                for (int i10 = 0; i10 < size4; i10++) {
                    a(c0004e, (C0003d) c0004e.mOps.get(i10), sparseArray2, false, z3);
                }
            }
        }
        if (sparseArray2.size() != 0) {
            View view3 = new View(o2.mHost.getContext());
            int size5 = sparseArray2.size();
            int i11 = 0;
            while (i11 < size5) {
                int iKeyAt = sparseArray2.keyAt(i11);
                a.b.c.b.b bVar2 = new a.b.c.b.b();
                int i12 = i8 - 1;
                while (i12 >= i) {
                    C0004e c0004e2 = (C0004e) arrayList8.get(i12);
                    if (c0004e2.interactsWith(iKeyAt)) {
                        boolean zBooleanValue = ((Boolean) arrayList9.get(i12)).booleanValue();
                        ArrayList arrayList10 = c0004e2.mSharedElementSourceNames;
                        if (arrayList10 != null) {
                            int size6 = arrayList10.size();
                            ArrayList arrayList11 = c0004e2.mSharedElementSourceNames;
                            ArrayList arrayList12 = c0004e2.mSharedElementTargetNames;
                            if (!zBooleanValue) {
                                arrayList12 = arrayList11;
                                arrayList11 = arrayList12;
                            }
                            int i13 = 0;
                            while (i13 < size6) {
                                String str2 = (String) arrayList12.get(i13);
                                String str3 = (String) arrayList11.get(i13);
                                int i14 = size6;
                                String str4 = (String) bVar2.remove(str3);
                                if (str4 != null) {
                                    bVar2.put(str2, str4);
                                } else {
                                    bVar2.put(str2, str3);
                                }
                                i13++;
                                size6 = i14;
                            }
                        }
                    }
                    i12--;
                    arrayList8 = arrayList;
                    arrayList9 = arrayList2;
                }
                aa aaVar = (aa) sparseArray2.valueAt(i11);
                if (z3) {
                    ViewGroup viewGroup = o2.mContainer.onHasView() ? (ViewGroup) o2.mContainer.onFindViewById(iKeyAt) : null;
                    if (viewGroup == null || (kaVarA2 = a((componentCallbacksC0013n6 = aaVar.firstOut), (componentCallbacksC0013n5 = aaVar.lastIn))) == null) {
                        sparseArray = sparseArray2;
                        i3 = i11;
                        i4 = size5;
                    } else {
                        boolean z4 = aaVar.lastInIsPop;
                        boolean z5 = aaVar.firstOutIsPop;
                        ArrayList arrayList13 = new ArrayList();
                        ArrayList arrayList14 = new ArrayList();
                        sparseArray = sparseArray2;
                        Object objA2 = a(kaVarA2, componentCallbacksC0013n5, z4);
                        Object objB = b(kaVarA2, componentCallbacksC0013n6, z5);
                        ComponentCallbacksC0013n componentCallbacksC0013n8 = aaVar.lastIn;
                        i3 = i11;
                        ComponentCallbacksC0013n componentCallbacksC0013n9 = aaVar.firstOut;
                        i4 = size5;
                        if (componentCallbacksC0013n8 != null) {
                            componentCallbacksC0013n8.mView.setVisibility(0);
                        }
                        if (componentCallbacksC0013n8 == null || componentCallbacksC0013n9 == null) {
                            componentCallbacksC0013n7 = componentCallbacksC0013n5;
                            z4 = z4;
                        } else {
                            boolean z6 = aaVar.lastInIsPop;
                            Object objA3 = bVar2.isEmpty() ? null : a(kaVarA2, componentCallbacksC0013n8, componentCallbacksC0013n9, z6);
                            a.b.c.b.b bVarC = c(kaVarA2, bVar2, objA3, aaVar);
                            componentCallbacksC0013n7 = componentCallbacksC0013n5;
                            a.b.c.b.b bVarB = b(kaVarA2, bVar2, objA3, aaVar);
                            if (bVar2.isEmpty()) {
                                if (bVarC != null) {
                                    bVarC.clear();
                                }
                                if (bVarB != null) {
                                    bVarB.clear();
                                }
                                obj4 = null;
                            } else {
                                a(arrayList14, bVarC, bVar2.keySet());
                                a(arrayList13, bVarB, bVar2.values());
                                obj4 = objA3;
                            }
                            if (objA2 != null || objB != null || obj4 != null) {
                                b(componentCallbacksC0013n8, componentCallbacksC0013n9, z6, bVarC, true);
                                if (obj4 != null) {
                                    arrayList13.add(view3);
                                    kaVarA2.b(obj4, view3, arrayList14);
                                    a(kaVarA2, obj4, objB, bVarC, aaVar.firstOutIsPop, aaVar.firstOutTransaction);
                                    Rect rect3 = new Rect();
                                    View viewB = b(bVarB, aaVar, objA2, z6);
                                    if (viewB != null) {
                                        kaVarA2.a(objA2, rect3);
                                    }
                                    view2 = viewB;
                                    rect2 = rect3;
                                } else {
                                    view2 = null;
                                    rect2 = null;
                                }
                                pa.add(viewGroup, new Y(componentCallbacksC0013n8, componentCallbacksC0013n9, z6, bVarB, view2, kaVarA2, rect2));
                            }
                            if (objA2 == null || obj4 != null || objB != null) {
                                arrayListB = b(kaVarA2, objB, componentCallbacksC0013n6, arrayList14, view3);
                                ComponentCallbacksC0013n componentCallbacksC0013n10 = componentCallbacksC0013n7;
                                arrayListB2 = b(kaVarA2, objA2, componentCallbacksC0013n10, arrayList13, view3);
                                b(arrayListB2, 4);
                                objA = a(kaVarA2, objA2, objB, obj4, componentCallbacksC0013n10, z4);
                                if (objA != null) {
                                    if (componentCallbacksC0013n6 != null && objB != null && componentCallbacksC0013n6.mAdded && componentCallbacksC0013n6.mHidden && componentCallbacksC0013n6.mHiddenChanged) {
                                        componentCallbacksC0013n6.setHideReplaced(true);
                                        kaVarA2.a(objB, componentCallbacksC0013n6.mView, arrayListB);
                                        pa.add(componentCallbacksC0013n6.mContainer, new W(arrayListB));
                                    }
                                    arrayList6 = new ArrayList();
                                    i5 = 0;
                                    for (size = arrayList13.size(); i5 < size; size = size) {
                                        View view4 = (View) arrayList13.get(i5);
                                        arrayList6.add(android.support.v4.view.r.h(view4));
                                        int i15 = Build.VERSION.SDK_INT;
                                        view4.setTransitionName(null);
                                        i5++;
                                    }
                                    kaVarA2.a(objA, objA2, arrayListB2, objB, arrayListB, obj4, arrayList13);
                                    kaVarA2.a(viewGroup, objA);
                                    size2 = arrayList13.size();
                                    arrayList7 = new ArrayList();
                                    i6 = 0;
                                    while (i6 < size2) {
                                        view = (View) arrayList14.get(i6);
                                        strH = android.support.v4.view.r.h(view);
                                        arrayList7.add(strH);
                                        if (strH == null) {
                                            bVar = bVar2;
                                        } else {
                                            int i16 = Build.VERSION.SDK_INT;
                                            view.setTransitionName(null);
                                            bVar = bVar2;
                                            str = (String) bVar.get(strH);
                                            for (i7 = 0; i7 < size2; i7++) {
                                                if (str.equals(arrayList6.get(i7))) {
                                                    View view5 = (View) arrayList13.get(i7);
                                                    int i17 = Build.VERSION.SDK_INT;
                                                    view5.setTransitionName(strH);
                                                    break;
                                                }
                                            }
                                        }
                                        i6++;
                                        bVar2 = bVar;
                                    }
                                    pa.add(viewGroup, new ha(kaVarA2, size2, arrayList13, arrayList6, arrayList14, arrayList7));
                                    b(arrayListB2, 0);
                                    kaVarA2.b(obj4, arrayList14, arrayList13);
                                }
                            }
                        }
                        obj4 = null;
                        if (objA2 == null) {
                            arrayListB = b(kaVarA2, objB, componentCallbacksC0013n6, arrayList14, view3);
                            ComponentCallbacksC0013n componentCallbacksC0013n11 = componentCallbacksC0013n7;
                            arrayListB2 = b(kaVarA2, objA2, componentCallbacksC0013n11, arrayList13, view3);
                            b(arrayListB2, 4);
                            objA = a(kaVarA2, objA2, objB, obj4, componentCallbacksC0013n11, z4);
                            if (objA != null) {
                                if (componentCallbacksC0013n6 != null) {
                                    componentCallbacksC0013n6.setHideReplaced(true);
                                    kaVarA2.a(objB, componentCallbacksC0013n6.mView, arrayListB);
                                    pa.add(componentCallbacksC0013n6.mContainer, new W(arrayListB));
                                }
                                arrayList6 = new ArrayList();
                                i5 = 0;
                                while (i5 < size) {
                                    View view6 = (View) arrayList13.get(i5);
                                    arrayList6.add(android.support.v4.view.r.h(view6));
                                    int i18 = Build.VERSION.SDK_INT;
                                    view6.setTransitionName(null);
                                    i5++;
                                }
                                kaVarA2.a(objA, objA2, arrayListB2, objB, arrayListB, obj4, arrayList13);
                                kaVarA2.a(viewGroup, objA);
                                size2 = arrayList13.size();
                                arrayList7 = new ArrayList();
                                i6 = 0;
                                while (i6 < size2) {
                                    view = (View) arrayList14.get(i6);
                                    strH = android.support.v4.view.r.h(view);
                                    arrayList7.add(strH);
                                    if (strH == null) {
                                        bVar = bVar2;
                                    } else {
                                        int i19 = Build.VERSION.SDK_INT;
                                        view.setTransitionName(null);
                                        bVar = bVar2;
                                        str = (String) bVar.get(strH);
                                        while (i7 < size2) {
                                            if (str.equals(arrayList6.get(i7))) {
                                                View view7 = (View) arrayList13.get(i7);
                                                int i110 = Build.VERSION.SDK_INT;
                                                view7.setTransitionName(strH);
                                                break;
                                                break;
                                            }
                                        }
                                    }
                                    i6++;
                                    bVar2 = bVar;
                                }
                                pa.add(viewGroup, new ha(kaVarA2, size2, arrayList13, arrayList6, arrayList14, arrayList7));
                                b(arrayListB2, 0);
                                kaVarA2.b(obj4, arrayList14, arrayList13);
                            }
                        } else {
                            arrayListB = b(kaVarA2, objB, componentCallbacksC0013n6, arrayList14, view3);
                            ComponentCallbacksC0013n componentCallbacksC0013n12 = componentCallbacksC0013n7;
                            arrayListB2 = b(kaVarA2, objA2, componentCallbacksC0013n12, arrayList13, view3);
                            b(arrayListB2, 4);
                            objA = a(kaVarA2, objA2, objB, obj4, componentCallbacksC0013n12, z4);
                            if (objA != null) {
                                if (componentCallbacksC0013n6 != null) {
                                    componentCallbacksC0013n6.setHideReplaced(true);
                                    kaVarA2.a(objB, componentCallbacksC0013n6.mView, arrayListB);
                                    pa.add(componentCallbacksC0013n6.mContainer, new W(arrayListB));
                                }
                                arrayList6 = new ArrayList();
                                i5 = 0;
                                while (i5 < size) {
                                    View view8 = (View) arrayList13.get(i5);
                                    arrayList6.add(android.support.v4.view.r.h(view8));
                                    int i111 = Build.VERSION.SDK_INT;
                                    view8.setTransitionName(null);
                                    i5++;
                                }
                                kaVarA2.a(objA, objA2, arrayListB2, objB, arrayListB, obj4, arrayList13);
                                kaVarA2.a(viewGroup, objA);
                                size2 = arrayList13.size();
                                arrayList7 = new ArrayList();
                                i6 = 0;
                                while (i6 < size2) {
                                    view = (View) arrayList14.get(i6);
                                    strH = android.support.v4.view.r.h(view);
                                    arrayList7.add(strH);
                                    if (strH == null) {
                                        bVar = bVar2;
                                    } else {
                                        int i112 = Build.VERSION.SDK_INT;
                                        view.setTransitionName(null);
                                        bVar = bVar2;
                                        str = (String) bVar.get(strH);
                                        while (i7 < size2) {
                                            if (str.equals(arrayList6.get(i7))) {
                                                View view9 = (View) arrayList13.get(i7);
                                                int i113 = Build.VERSION.SDK_INT;
                                                view9.setTransitionName(strH);
                                                break;
                                                break;
                                            }
                                        }
                                    }
                                    i6++;
                                    bVar2 = bVar;
                                }
                                pa.add(viewGroup, new ha(kaVarA2, size2, arrayList13, arrayList6, arrayList14, arrayList7));
                                b(arrayListB2, 0);
                                kaVarA2.b(obj4, arrayList14, arrayList13);
                            }
                        }
                    }
                } else {
                    sparseArray = sparseArray2;
                    i3 = i11;
                    i4 = size5;
                    ViewGroup viewGroup2 = o.mContainer.onHasView() ? (ViewGroup) o.mContainer.onFindViewById(iKeyAt) : null;
                    if (viewGroup2 != null && (kaVarA = a((componentCallbacksC0013n2 = aaVar.firstOut), (componentCallbacksC0013n = aaVar.lastIn))) != null) {
                        boolean z7 = aaVar.lastInIsPop;
                        boolean z8 = aaVar.firstOutIsPop;
                        Object objA4 = a(kaVarA, componentCallbacksC0013n, z7);
                        Object objB2 = b(kaVarA, componentCallbacksC0013n2, z8);
                        ArrayList arrayList15 = new ArrayList();
                        ArrayList arrayList16 = new ArrayList();
                        ComponentCallbacksC0013n componentCallbacksC0013n13 = aaVar.lastIn;
                        ComponentCallbacksC0013n componentCallbacksC0013n14 = aaVar.firstOut;
                        if (componentCallbacksC0013n13 == null || componentCallbacksC0013n14 == null) {
                            arrayList3 = arrayList15;
                            obj = null;
                            arrayList4 = arrayList16;
                            obj2 = null;
                        } else {
                            boolean z9 = aaVar.lastInIsPop;
                            Object objA5 = bVar2.isEmpty() ? null : a(kaVarA, componentCallbacksC0013n13, componentCallbacksC0013n14, z9);
                            a.b.c.b.b bVarC2 = c(kaVarA, bVar2, objA5, aaVar);
                            if (bVar2.isEmpty()) {
                                obj3 = null;
                            } else {
                                arrayList15.addAll(bVarC2.values());
                                obj3 = objA5;
                            }
                            if (objA4 == null && objB2 == null && obj3 == null) {
                                arrayList3 = arrayList15;
                                obj = null;
                                arrayList4 = arrayList16;
                                obj2 = null;
                            } else {
                                b(componentCallbacksC0013n13, componentCallbacksC0013n14, z9, bVarC2, true);
                                if (obj3 != null) {
                                    rect = new Rect();
                                    kaVarA.b(obj3, view3, arrayList15);
                                    componentCallbacksC0013n3 = componentCallbacksC0013n13;
                                    arrayList5 = arrayList16;
                                    z2 = z9;
                                    componentCallbacksC0013n4 = componentCallbacksC0013n14;
                                    a(kaVarA, obj3, objB2, bVarC2, aaVar.firstOutIsPop, aaVar.firstOutTransaction);
                                    if (objA4 != null) {
                                        kaVarA.a(objA4, rect);
                                    }
                                } else {
                                    componentCallbacksC0013n3 = componentCallbacksC0013n13;
                                    arrayList5 = arrayList16;
                                    z2 = z9;
                                    componentCallbacksC0013n4 = componentCallbacksC0013n14;
                                    rect = null;
                                }
                                ArrayList arrayList17 = arrayList5;
                                arrayList3 = arrayList15;
                                obj = null;
                                arrayList4 = arrayList17;
                                pa.add(viewGroup2, new Z(kaVarA, bVar2, obj3, aaVar, arrayList17, view3, componentCallbacksC0013n3, componentCallbacksC0013n4, z2, arrayList3, objA4, rect));
                                obj2 = obj3;
                            }
                        }
                        if (objA4 != null || obj2 != null || objB2 != null) {
                            ArrayList arrayListB3 = b(kaVarA, objB2, componentCallbacksC0013n2, arrayList3, view3);
                            if (arrayListB3 != null && !arrayListB3.isEmpty()) {
                                obj = objB2;
                            }
                            if (objA4 != null) {
                                ((Transition) objA4).addTarget(view3);
                            }
                            Object objA6 = a(kaVarA, objA4, obj, obj2, componentCallbacksC0013n, aaVar.lastInIsPop);
                            if (objA6 != null) {
                                ArrayList arrayList18 = new ArrayList();
                                kaVarA.a(objA6, objA4, arrayList18, obj, arrayListB3, obj2, arrayList4);
                                pa.add(viewGroup2, new X(objA4, kaVarA, view3, componentCallbacksC0013n, arrayList4, arrayList18, arrayListB3, obj));
                                ArrayList arrayList19 = arrayList4;
                                pa.add(viewGroup2, new ia(kaVarA, arrayList19, bVar2));
                                kaVarA.a(viewGroup2, objA6);
                                pa.add(viewGroup2, new ja(kaVarA, arrayList19, bVar2));
                            }
                        }
                    }
                    i11 = i3 + 1;
                    o2 = o;
                    arrayList8 = arrayList;
                    arrayList9 = arrayList2;
                    i8 = i2;
                    z3 = z;
                    size5 = i4;
                    sparseArray2 = sparseArray;
                }
                i3 = i3;
                i4 = i4;
                i11 = i3 + 1;
                o2 = o;
                arrayList8 = arrayList;
                arrayList9 = arrayList2;
                i8 = i2;
                z3 = z;
                size5 = i4;
                sparseArray2 = sparseArray;
            }
        }
    }
}
