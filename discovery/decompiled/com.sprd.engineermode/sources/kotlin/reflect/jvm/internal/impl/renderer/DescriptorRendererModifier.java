package kotlin.reflect.jvm.internal.impl.renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: DescriptorRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
public enum DescriptorRendererModifier {
    VISIBILITY(true),
    MODALITY(true),
    OVERRIDE(true),
    ANNOTATIONS(false),
    INNER(true),
    MEMBER_KIND(true),
    DATA(true),
    INLINE(true),
    EXPECT(true),
    ACTUAL(true),
    CONST(true),
    LATEINIT(true);


    @JvmField
    @NotNull
    public static final Set<DescriptorRendererModifier> ALL;
    public static final Companion Companion = new Companion(null);

    @JvmField
    @NotNull
    public static final Set<DescriptorRendererModifier> DEFAULTS;
    private final boolean includeByDefault;

    DescriptorRendererModifier(boolean includeByDefault) {
        this.includeByDefault = includeByDefault;
    }

    static {
        DescriptorRendererModifier[] descriptorRendererModifierArrValues = values();
        Collection destination$iv$iv = new ArrayList();
        for (DescriptorRendererModifier descriptorRendererModifier : descriptorRendererModifierArrValues) {
            if (descriptorRendererModifier.includeByDefault) {
                destination$iv$iv.add(descriptorRendererModifier);
            }
        }
        DEFAULTS = CollectionsKt.toSet((List) destination$iv$iv);
        ALL = ArraysKt.toSet(values());
    }

    /* JADX INFO: compiled from: DescriptorRenderer.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
