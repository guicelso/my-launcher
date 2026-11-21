package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: javaElements.kt */
/* JADX INFO: loaded from: classes4.dex */
public interface JavaMethod extends JavaMember, JavaTypeParameterListOwner {
    @Nullable
    JavaAnnotationArgument getAnnotationParameterDefaultValue();

    @NotNull
    JavaType getReturnType();

    @NotNull
    List<JavaValueParameter> getValueParameters();
}
