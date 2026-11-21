package kotlin.reflect.jvm.internal;

import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.components.RuntimeModuleData;
import kotlin.reflect.jvm.internal.structure.ReflectClassUtilKt;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: moduleByClassLoader.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\b\u0010\u0005\u001a\u00020\u0006H\u0000\u001a\u0010\u0010\u0007\u001a\u00020\u0004*\u0006\u0012\u0002\b\u00030\bH\u0000\" \u0010\u0000\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"moduleByClassLoader", "Ljava/util/concurrent/ConcurrentMap;", "Lkotlin/reflect/jvm/internal/WeakClassLoaderBox;", "Ljava/lang/ref/WeakReference;", "Lkotlin/reflect/jvm/internal/components/RuntimeModuleData;", "clearModuleByClassLoaderCache", "", "getOrCreateModule", "Ljava/lang/Class;", "kotlin-reflect-api"}, k = 2, mv = {1, 1, 13})
public final class ModuleByClassLoaderKt {
    private static final ConcurrentMap<WeakClassLoaderBox, WeakReference<RuntimeModuleData>> moduleByClassLoader = new ConcurrentHashMap();

    @NotNull
    public static final RuntimeModuleData getOrCreateModule(@NotNull Class<?> receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        ClassLoader classLoader = ReflectClassUtilKt.getSafeClassLoader(receiver$0);
        WeakClassLoaderBox key = new WeakClassLoaderBox(classLoader);
        WeakReference<RuntimeModuleData> weakReference = moduleByClassLoader.get(key);
        if (weakReference != null) {
            RuntimeModuleData it = weakReference.get();
            if (it != null) {
                Intrinsics.checkExpressionValueIsNotNull(it, "it");
                return it;
            }
            moduleByClassLoader.remove(key, weakReference);
        }
        RuntimeModuleData module = RuntimeModuleData.INSTANCE.create(classLoader);
        while (true) {
            try {
                WeakReference<RuntimeModuleData> weakReferencePutIfAbsent = moduleByClassLoader.putIfAbsent(key, new WeakReference<>(module));
                if (weakReferencePutIfAbsent != null) {
                    RuntimeModuleData result = weakReferencePutIfAbsent.get();
                    if (result == null) {
                        moduleByClassLoader.remove(key, weakReferencePutIfAbsent);
                    } else {
                        key.setTemporaryStrongRef((ClassLoader) null);
                        return result;
                    }
                } else {
                    key.setTemporaryStrongRef((ClassLoader) null);
                    return module;
                }
            } catch (Throwable th) {
                key.setTemporaryStrongRef((ClassLoader) null);
                throw th;
            }
        }
    }

    public static final void clearModuleByClassLoaderCache() {
        moduleByClassLoader.clear();
    }
}
