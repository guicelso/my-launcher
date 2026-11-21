package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import kotlin.text.StringsKt;

/* JADX INFO: compiled from: ModuleMapping.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class ModuleMappingKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final String internalNameOf(String packageFqName, String className) {
        if (packageFqName.length() == 0) {
            return className;
        }
        return StringsKt.replace$default(packageFqName, '.', '/', false, 4, (Object) null) + "/" + className;
    }
}
