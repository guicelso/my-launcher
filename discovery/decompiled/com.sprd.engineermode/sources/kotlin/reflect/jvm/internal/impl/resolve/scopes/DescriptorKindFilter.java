package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: MemberScope.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class DescriptorKindFilter {

    @JvmField
    @NotNull
    public static final DescriptorKindFilter ALL;

    @JvmField
    @NotNull
    public static final DescriptorKindFilter CALLABLES;
    private static final int CALLABLES_MASK;

    @JvmField
    @NotNull
    public static final DescriptorKindFilter CLASSIFIERS;
    private static final int CLASSIFIERS_MASK;
    private static final List<Companion.MaskToName> DEBUG_MASK_BIT_NAMES;
    private static final List<Companion.MaskToName> DEBUG_PREDEFINED_FILTERS_MASK_NAMES;

    @JvmField
    @NotNull
    public static final DescriptorKindFilter FUNCTIONS;

    @JvmField
    @NotNull
    public static final DescriptorKindFilter NON_SINGLETON_CLASSIFIERS;

    @JvmField
    @NotNull
    public static final DescriptorKindFilter PACKAGES;

    @JvmField
    @NotNull
    public static final DescriptorKindFilter SINGLETON_CLASSIFIERS;

    @JvmField
    @NotNull
    public static final DescriptorKindFilter TYPE_ALIASES;

    @JvmField
    @NotNull
    public static final DescriptorKindFilter VALUES;
    private static final int VALUES_MASK;

    @JvmField
    @NotNull
    public static final DescriptorKindFilter VARIABLES;

    @NotNull
    private final List<DescriptorKindExclude> excludes;
    private final int kindMask;
    public static final Companion Companion = new Companion(0 == true ? 1 : 0);
    private static int nextMaskValue = 1;
    private static final int NON_SINGLETON_CLASSIFIERS_MASK = Companion.nextMask();
    private static final int SINGLETON_CLASSIFIERS_MASK = Companion.nextMask();
    private static final int TYPE_ALIASES_MASK = Companion.nextMask();
    private static final int PACKAGES_MASK = Companion.nextMask();
    private static final int FUNCTIONS_MASK = Companion.nextMask();
    private static final int VARIABLES_MASK = Companion.nextMask();
    private static final int ALL_KINDS_MASK = Companion.nextMask() - 1;

    /* JADX WARN: Multi-variable type inference failed */
    public DescriptorKindFilter(int kindMask, @NotNull List<? extends DescriptorKindExclude> excludes) {
        Intrinsics.checkParameterIsNotNull(excludes, "excludes");
        this.excludes = excludes;
        int mask = kindMask;
        Iterable $receiver$iv = this.excludes;
        for (Object element$iv : $receiver$iv) {
            DescriptorKindExclude it = (DescriptorKindExclude) element$iv;
            mask &= ~it.getFullyExcludedDescriptorKinds();
        }
        this.kindMask = mask;
    }

    public /* synthetic */ DescriptorKindFilter(int i, List list, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? CollectionsKt.emptyList() : list);
    }

    @NotNull
    public final List<DescriptorKindExclude> getExcludes() {
        return this.excludes;
    }

    public final int getKindMask() {
        return this.kindMask;
    }

    public final boolean acceptsKinds(int kinds) {
        return (this.kindMask & kinds) != 0;
    }

    @Nullable
    public final DescriptorKindFilter restrictedToKindsOrNull(int kinds) {
        int mask = this.kindMask & kinds;
        if (mask == 0) {
            return null;
        }
        return new DescriptorKindFilter(mask, this.excludes);
    }

    @NotNull
    public String toString() {
        Object element$iv;
        String kindString;
        Iterable $receiver$iv = DEBUG_PREDEFINED_FILTERS_MASK_NAMES;
        Iterator it = $receiver$iv.iterator();
        do {
            if (!it.hasNext()) {
                element$iv = null;
                break;
            }
            element$iv = it.next();
        } while (!(((Companion.MaskToName) element$iv).getMask() == this.kindMask));
        Companion.MaskToName maskToName = (Companion.MaskToName) element$iv;
        String predefinedFilterName = maskToName != null ? maskToName.getName() : null;
        if (predefinedFilterName != null) {
            kindString = predefinedFilterName;
        } else {
            Iterable $receiver$iv2 = DEBUG_MASK_BIT_NAMES;
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv$iv : $receiver$iv2) {
                Companion.MaskToName it2 = (Companion.MaskToName) element$iv$iv$iv;
                String name = acceptsKinds(it2.getMask()) ? it2.getName() : null;
                if (name != null) {
                    destination$iv$iv.add(name);
                }
            }
            kindString = CollectionsKt.joinToString$default((List) destination$iv$iv, " | ", null, null, 0, null, null, 62, null);
        }
        return "DescriptorKindFilter(" + kindString + ", " + this.excludes + ')';
    }

    /* JADX INFO: compiled from: MemberScope.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final int nextMask() {
            int i = DescriptorKindFilter.nextMaskValue;
            DescriptorKindFilter.nextMaskValue <<= 1;
            return i;
        }

        public final int getNON_SINGLETON_CLASSIFIERS_MASK() {
            return DescriptorKindFilter.NON_SINGLETON_CLASSIFIERS_MASK;
        }

        public final int getSINGLETON_CLASSIFIERS_MASK() {
            return DescriptorKindFilter.SINGLETON_CLASSIFIERS_MASK;
        }

        public final int getTYPE_ALIASES_MASK() {
            return DescriptorKindFilter.TYPE_ALIASES_MASK;
        }

        public final int getPACKAGES_MASK() {
            return DescriptorKindFilter.PACKAGES_MASK;
        }

        public final int getFUNCTIONS_MASK() {
            return DescriptorKindFilter.FUNCTIONS_MASK;
        }

        public final int getVARIABLES_MASK() {
            return DescriptorKindFilter.VARIABLES_MASK;
        }

        public final int getALL_KINDS_MASK() {
            return DescriptorKindFilter.ALL_KINDS_MASK;
        }

        public final int getCLASSIFIERS_MASK() {
            return DescriptorKindFilter.CLASSIFIERS_MASK;
        }

        /* JADX INFO: compiled from: MemberScope.kt */
        private static final class MaskToName {
            private final int mask;

            @NotNull
            private final String name;

            public MaskToName(int mask, @NotNull String name) {
                Intrinsics.checkParameterIsNotNull(name, "name");
                this.mask = mask;
                this.name = name;
            }

            public final int getMask() {
                return this.mask;
            }

            @NotNull
            public final String getName() {
                return this.name;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static {
        Companion.MaskToName maskToName;
        Companion.MaskToName maskToName2;
        Object obj = null;
        int i = NON_SINGLETON_CLASSIFIERS_MASK;
        int i2 = SINGLETON_CLASSIFIERS_MASK;
        CLASSIFIERS_MASK = i | i2 | TYPE_ALIASES_MASK;
        int i3 = FUNCTIONS_MASK;
        int i4 = VARIABLES_MASK;
        VALUES_MASK = i2 | i3 | i4;
        CALLABLES_MASK = i3 | i4;
        int i5 = 2;
        ALL = new DescriptorKindFilter(ALL_KINDS_MASK, 0 == true ? 1 : 0, i5, 0 == true ? 1 : 0);
        CALLABLES = new DescriptorKindFilter(CALLABLES_MASK, 0 == true ? 1 : 0, i5, 0 == true ? 1 : 0);
        NON_SINGLETON_CLASSIFIERS = new DescriptorKindFilter(NON_SINGLETON_CLASSIFIERS_MASK, 0 == true ? 1 : 0, i5, 0 == true ? 1 : 0);
        SINGLETON_CLASSIFIERS = new DescriptorKindFilter(SINGLETON_CLASSIFIERS_MASK, 0 == true ? 1 : 0, i5, 0 == true ? 1 : 0);
        TYPE_ALIASES = new DescriptorKindFilter(TYPE_ALIASES_MASK, 0 == true ? 1 : 0, i5, 0 == true ? 1 : 0);
        CLASSIFIERS = new DescriptorKindFilter(CLASSIFIERS_MASK, 0 == true ? 1 : 0, i5, 0 == true ? 1 : 0);
        PACKAGES = new DescriptorKindFilter(PACKAGES_MASK, 0 == true ? 1 : 0, i5, 0 == true ? 1 : 0);
        FUNCTIONS = new DescriptorKindFilter(FUNCTIONS_MASK, 0 == true ? 1 : 0, i5, 0 == true ? 1 : 0);
        VARIABLES = new DescriptorKindFilter(VARIABLES_MASK, 0 == true ? 1 : 0, i5, 0 == true ? 1 : 0);
        VALUES = new DescriptorKindFilter(VALUES_MASK, 0 == true ? 1 : 0, i5, 0 == true ? 1 : 0);
        Companion companion = Companion;
        Field[] fields = DescriptorKindFilter.class.getFields();
        Intrinsics.checkExpressionValueIsNotNull(fields, "T::class.java.fields");
        ArrayList arrayList = new ArrayList();
        for (Field it : fields) {
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            if (Modifier.isStatic(it.getModifiers())) {
                arrayList.add(it);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList();
        ArrayList<Field> arrayList4 = arrayList2;
        for (Field field : arrayList4) {
            ArrayList arrayList5 = arrayList2;
            Object obj2 = field.get(obj);
            if (!(obj2 instanceof DescriptorKindFilter)) {
                obj2 = null;
            }
            DescriptorKindFilter descriptorKindFilter = (DescriptorKindFilter) obj2;
            if (descriptorKindFilter != null) {
                int i6 = descriptorKindFilter.kindMask;
                Intrinsics.checkExpressionValueIsNotNull(field, "field");
                String name = field.getName();
                Intrinsics.checkExpressionValueIsNotNull(name, "field.name");
                maskToName2 = new Companion.MaskToName(i6, name);
            } else {
                maskToName2 = null;
            }
            if (maskToName2 != null) {
                arrayList3.add(maskToName2);
            }
            arrayList2 = arrayList5;
            arrayList4 = arrayList4;
            obj = null;
        }
        DEBUG_PREDEFINED_FILTERS_MASK_NAMES = CollectionsKt.toList(arrayList3);
        Companion companion2 = Companion;
        Field[] fields2 = DescriptorKindFilter.class.getFields();
        Intrinsics.checkExpressionValueIsNotNull(fields2, "T::class.java.fields");
        ArrayList arrayList6 = new ArrayList();
        for (Field it2 : fields2) {
            Intrinsics.checkExpressionValueIsNotNull(it2, "it");
            if (Modifier.isStatic(it2.getModifiers())) {
                arrayList6.add(it2);
            }
        }
        boolean z = false;
        ArrayList<Field> arrayList7 = new ArrayList();
        for (Object obj3 : arrayList6) {
            Field it3 = (Field) obj3;
            Intrinsics.checkExpressionValueIsNotNull(it3, "it");
            if (Intrinsics.areEqual(it3.getType(), Integer.TYPE)) {
                arrayList7.add(obj3);
            }
        }
        ArrayList arrayList8 = new ArrayList();
        for (Field field2 : arrayList7) {
            Object obj4 = field2.get(null);
            if (obj4 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.Int");
            }
            int iIntValue = ((Integer) obj4).intValue();
            if (iIntValue == ((-iIntValue) & iIntValue) ? true : z) {
                Intrinsics.checkExpressionValueIsNotNull(field2, "field");
                String name2 = field2.getName();
                Intrinsics.checkExpressionValueIsNotNull(name2, "field.name");
                maskToName = new Companion.MaskToName(iIntValue, name2);
            } else {
                maskToName = null;
            }
            if (maskToName != null) {
                arrayList8.add(maskToName);
            }
            z = false;
        }
        DEBUG_MASK_BIT_NAMES = CollectionsKt.toList(arrayList8);
    }
}
