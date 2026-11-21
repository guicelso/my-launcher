package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.List;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: PackagePartProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
public interface PackagePartProvider {
    @NotNull
    List<String> findPackageParts(@NotNull String str);
}
