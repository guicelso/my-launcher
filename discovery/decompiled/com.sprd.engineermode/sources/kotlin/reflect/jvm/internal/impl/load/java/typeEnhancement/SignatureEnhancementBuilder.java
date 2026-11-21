package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: predefinedEnhancementInfo.kt */
/* JADX INFO: loaded from: classes4.dex */
final class SignatureEnhancementBuilder {
    private final Map<String, PredefinedFunctionEnhancementInfo> signatures = new LinkedHashMap();

    /* JADX INFO: compiled from: predefinedEnhancementInfo.kt */
    public final class ClassEnhancementBuilder {

        @NotNull
        private final String className;
        final /* synthetic */ SignatureEnhancementBuilder this$0;

        public ClassEnhancementBuilder(SignatureEnhancementBuilder $outer, @NotNull String className) {
            Intrinsics.checkParameterIsNotNull(className, "className");
            this.this$0 = $outer;
            this.className = className;
        }

        @NotNull
        public final String getClassName() {
            return this.className;
        }

        public final void function(@NotNull String name, @NotNull Function1<? super FunctionEnhancementBuilder, Unit> block) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(block, "block");
            Map map = this.this$0.signatures;
            FunctionEnhancementBuilder functionEnhancementBuilder = new FunctionEnhancementBuilder(this, name);
            block.invoke(functionEnhancementBuilder);
            Pair<String, PredefinedFunctionEnhancementInfo> pairBuild = functionEnhancementBuilder.build();
            map.put(pairBuild.getFirst(), pairBuild.getSecond());
        }

        /* JADX INFO: compiled from: predefinedEnhancementInfo.kt */
        public final class FunctionEnhancementBuilder {

            @NotNull
            private final String functionName;
            private final List<Pair<String, TypeEnhancementInfo>> parameters;
            private Pair<String, TypeEnhancementInfo> returnType;
            final /* synthetic */ ClassEnhancementBuilder this$0;

            public FunctionEnhancementBuilder(ClassEnhancementBuilder $outer, @NotNull String functionName) {
                Intrinsics.checkParameterIsNotNull(functionName, "functionName");
                this.this$0 = $outer;
                this.functionName = functionName;
                this.parameters = new ArrayList();
                this.returnType = TuplesKt.to("V", null);
            }

            public final void parameter(@NotNull String type, @NotNull JavaTypeQualifiers... qualifiers) {
                TypeEnhancementInfo typeEnhancementInfo;
                Intrinsics.checkParameterIsNotNull(type, "type");
                Intrinsics.checkParameterIsNotNull(qualifiers, "qualifiers");
                List<Pair<String, TypeEnhancementInfo>> list = this.parameters;
                if (qualifiers.length == 0) {
                    typeEnhancementInfo = null;
                } else {
                    Iterable $receiver$iv = ArraysKt.withIndex(qualifiers);
                    int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)), 16);
                    Map destination$iv$iv = new LinkedHashMap(capacity$iv);
                    for (Object element$iv$iv : $receiver$iv) {
                        IndexedValue it = (IndexedValue) element$iv$iv;
                        IndexedValue it2 = (IndexedValue) element$iv$iv;
                        destination$iv$iv.put(Integer.valueOf(it.getIndex()), (JavaTypeQualifiers) it2.getValue());
                    }
                    typeEnhancementInfo = new TypeEnhancementInfo(destination$iv$iv);
                }
                list.add(TuplesKt.to(type, typeEnhancementInfo));
            }

            public final void returns(@NotNull String type, @NotNull JavaTypeQualifiers... qualifiers) {
                Intrinsics.checkParameterIsNotNull(type, "type");
                Intrinsics.checkParameterIsNotNull(qualifiers, "qualifiers");
                Iterable $receiver$iv = ArraysKt.withIndex(qualifiers);
                int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10)), 16);
                Map destination$iv$iv = new LinkedHashMap(capacity$iv);
                for (Object element$iv$iv : $receiver$iv) {
                    IndexedValue it = (IndexedValue) element$iv$iv;
                    IndexedValue it2 = (IndexedValue) element$iv$iv;
                    destination$iv$iv.put(Integer.valueOf(it.getIndex()), (JavaTypeQualifiers) it2.getValue());
                }
                this.returnType = TuplesKt.to(type, new TypeEnhancementInfo(destination$iv$iv));
            }

            public final void returns(@NotNull JvmPrimitiveType type) {
                Intrinsics.checkParameterIsNotNull(type, "type");
                this.returnType = TuplesKt.to(type.getDesc(), null);
            }

            @NotNull
            public final Pair<String, PredefinedFunctionEnhancementInfo> build() {
                SignatureBuildingComponents $receiver = SignatureBuildingComponents.INSTANCE;
                String className = this.this$0.getClassName();
                String str = this.functionName;
                Iterable $receiver$iv = this.parameters;
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
                for (Object item$iv$iv : $receiver$iv) {
                    Pair it = (Pair) item$iv$iv;
                    destination$iv$iv.add((String) it.getFirst());
                }
                String strSignature = $receiver.signature(className, $receiver.jvmDescriptor(str, (List) destination$iv$iv, this.returnType.getFirst()));
                TypeEnhancementInfo second = this.returnType.getSecond();
                Iterable $receiver$iv2 = this.parameters;
                Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv2, 10));
                for (Object item$iv$iv2 : $receiver$iv2) {
                    Pair it2 = (Pair) item$iv$iv2;
                    destination$iv$iv2.add((TypeEnhancementInfo) it2.getSecond());
                }
                return TuplesKt.to(strSignature, new PredefinedFunctionEnhancementInfo(second, (List) destination$iv$iv2));
            }
        }
    }

    @NotNull
    public final Map<String, PredefinedFunctionEnhancementInfo> build() {
        return this.signatures;
    }
}
