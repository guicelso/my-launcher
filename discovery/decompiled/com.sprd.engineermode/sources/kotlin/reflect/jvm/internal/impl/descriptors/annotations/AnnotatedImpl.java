package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes4.dex */
public class AnnotatedImpl implements Annotated {
    private final Annotations annotations;

    public AnnotatedImpl(@NotNull Annotations annotations) {
        this.annotations = annotations;
    }

    @Override // kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated
    @NotNull
    public Annotations getAnnotations() {
        return this.annotations;
    }
}
