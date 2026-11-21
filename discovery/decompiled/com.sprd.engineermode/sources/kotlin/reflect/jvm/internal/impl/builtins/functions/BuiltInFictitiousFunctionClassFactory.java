package kotlin.reflect.jvm.internal.impl.builtins.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.BuiltInsPackageFragment;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: BuiltInFictitiousFunctionClassFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class BuiltInFictitiousFunctionClassFactory implements ClassDescriptorFactory {
    public static final Companion Companion = new Companion(null);
    private final ModuleDescriptor module;
    private final StorageManager storageManager;

    public BuiltInFictitiousFunctionClassFactory(@NotNull StorageManager storageManager, @NotNull ModuleDescriptor module) {
        Intrinsics.checkParameterIsNotNull(storageManager, "storageManager");
        Intrinsics.checkParameterIsNotNull(module, "module");
        this.storageManager = storageManager;
        this.module = module;
    }

    /* JADX INFO: compiled from: BuiltInFictitiousFunctionClassFactory.kt */
    private static final class KindWithArity {
        private final int arity;

        @NotNull
        private final FunctionClassDescriptor.Kind kind;

        @NotNull
        public final FunctionClassDescriptor.Kind component1() {
            return this.kind;
        }

        public final int component2() {
            return this.arity;
        }

        public boolean equals(@Nullable Object obj) {
            if (this != obj) {
                if (obj instanceof KindWithArity) {
                    KindWithArity kindWithArity = (KindWithArity) obj;
                    if (Intrinsics.areEqual(this.kind, kindWithArity.kind)) {
                        if (this.arity == kindWithArity.arity) {
                        }
                    }
                }
                return false;
            }
            return true;
        }

        public int hashCode() {
            FunctionClassDescriptor.Kind kind = this.kind;
            return ((kind != null ? kind.hashCode() : 0) * 31) + this.arity;
        }

        @NotNull
        public String toString() {
            return "KindWithArity(kind=" + this.kind + ", arity=" + this.arity + ")";
        }

        public KindWithArity(@NotNull FunctionClassDescriptor.Kind kind, int arity) {
            Intrinsics.checkParameterIsNotNull(kind, "kind");
            this.kind = kind;
            this.arity = arity;
        }

        @NotNull
        public final FunctionClassDescriptor.Kind getKind() {
            return this.kind;
        }
    }

    /* JADX INFO: compiled from: BuiltInFictitiousFunctionClassFactory.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final KindWithArity parseClassName(String className, FqName packageFqName) {
            FunctionClassDescriptor.Kind kind = FunctionClassDescriptor.Kind.Companion.byClassNamePrefix(packageFqName, className);
            if (kind == null) {
                return null;
            }
            String prefix = kind.getClassNamePrefix();
            Companion companion = this;
            int length = prefix.length();
            if (className == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String strSubstring = className.substring(length);
            Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.String).substring(startIndex)");
            Integer num = companion.toInt(strSubstring);
            if (num == null) {
                return null;
            }
            int arity = num.intValue();
            return new KindWithArity(kind, arity);
        }

        @JvmStatic
        @Nullable
        public final FunctionClassDescriptor.Kind getFunctionalClassKind(@NotNull String className, @NotNull FqName packageFqName) {
            Intrinsics.checkParameterIsNotNull(className, "className");
            Intrinsics.checkParameterIsNotNull(packageFqName, "packageFqName");
            KindWithArity className2 = parseClassName(className, packageFqName);
            if (className2 != null) {
                return className2.getKind();
            }
            return null;
        }

        private final Integer toInt(String s) {
            if (s.length() == 0) {
                return null;
            }
            int result = 0;
            int length = s.length();
            for (int i = 0; i < length; i++) {
                char c = s.charAt(i);
                int d = c - '0';
                if (d < 0 || 9 < d) {
                    return null;
                }
                result = (result * 10) + d;
            }
            return Integer.valueOf(result);
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory
    public boolean shouldCreateClass(@NotNull FqName packageFqName, @NotNull Name name) {
        Intrinsics.checkParameterIsNotNull(packageFqName, "packageFqName");
        Intrinsics.checkParameterIsNotNull(name, "name");
        String string = name.asString();
        Intrinsics.checkExpressionValueIsNotNull(string, "name.asString()");
        return (StringsKt.startsWith$default(string, "Function", false, 2, (Object) null) || StringsKt.startsWith$default(string, "KFunction", false, 2, (Object) null) || StringsKt.startsWith$default(string, "SuspendFunction", false, 2, (Object) null) || StringsKt.startsWith$default(string, "KSuspendFunction", false, 2, (Object) null)) && Companion.parseClassName(string, packageFqName) != null;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory
    @Nullable
    public ClassDescriptor createClass(@NotNull ClassId classId) {
        Intrinsics.checkParameterIsNotNull(classId, "classId");
        if (classId.isLocal() || classId.isNestedClass()) {
            return null;
        }
        String className = classId.getRelativeClassName().asString();
        Intrinsics.checkExpressionValueIsNotNull(className, "classId.relativeClassName.asString()");
        if (!StringsKt.contains$default((CharSequence) className, (CharSequence) "Function", false, 2, (Object) null)) {
            return null;
        }
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkExpressionValueIsNotNull(packageFqName, "classId.packageFqName");
        KindWithArity className2 = Companion.parseClassName(className, packageFqName);
        if (className2 == null) {
            return null;
        }
        FunctionClassDescriptor.Kind kind = className2.component1();
        int arity = className2.component2();
        Iterable $receiver$iv = this.module.getPackage(packageFqName).getFragments();
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $receiver$iv) {
            if (element$iv$iv instanceof BuiltInsPackageFragment) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        BuiltInsPackageFragment containingPackageFragment = (BuiltInsPackageFragment) CollectionsKt.first((List) destination$iv$iv);
        return new FunctionClassDescriptor(this.storageManager, containingPackageFragment, kind, arity);
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory
    @NotNull
    public Collection<ClassDescriptor> getAllContributedClassesIfPossible(@NotNull FqName packageFqName) {
        Intrinsics.checkParameterIsNotNull(packageFqName, "packageFqName");
        return SetsKt.emptySet();
    }
}
