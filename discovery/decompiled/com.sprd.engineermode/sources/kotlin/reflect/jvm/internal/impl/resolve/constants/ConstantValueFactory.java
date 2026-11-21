package kotlin.reflect.jvm.internal.impl.resolve.constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: ConstantValueFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class ConstantValueFactory {
    public static final ConstantValueFactory INSTANCE = new ConstantValueFactory();

    private ConstantValueFactory() {
    }

    @NotNull
    public final ArrayValue createArrayValue(@NotNull List<? extends ConstantValue<?>> value, @NotNull final KotlinType type) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        Intrinsics.checkParameterIsNotNull(type, "type");
        return new ArrayValue(value, new Function1<ModuleDescriptor, KotlinType>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValueFactory.createArrayValue.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final KotlinType invoke(@NotNull ModuleDescriptor it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return type;
            }
        });
    }

    @Nullable
    public final ConstantValue<?> createConstantValue(@Nullable Object value) {
        if (value instanceof Byte) {
            return new ByteValue(((Number) value).byteValue());
        }
        if (value instanceof Short) {
            return new ShortValue(((Number) value).shortValue());
        }
        if (value instanceof Integer) {
            return new IntValue(((Number) value).intValue());
        }
        if (value instanceof Long) {
            return new LongValue(((Number) value).longValue());
        }
        if (value instanceof Character) {
            return new CharValue(((Character) value).charValue());
        }
        if (value instanceof Float) {
            return new FloatValue(((Number) value).floatValue());
        }
        if (value instanceof Double) {
            return new DoubleValue(((Number) value).doubleValue());
        }
        if (value instanceof Boolean) {
            return new BooleanValue(((Boolean) value).booleanValue());
        }
        if (value instanceof String) {
            return new StringValue((String) value);
        }
        if (value instanceof byte[]) {
            return createArrayValue(ArraysKt.toList((byte[]) value), PrimitiveType.BYTE);
        }
        if (value instanceof short[]) {
            return createArrayValue(ArraysKt.toList((short[]) value), PrimitiveType.SHORT);
        }
        if (value instanceof int[]) {
            return createArrayValue(ArraysKt.toList((int[]) value), PrimitiveType.INT);
        }
        if (value instanceof long[]) {
            return createArrayValue(ArraysKt.toList((long[]) value), PrimitiveType.LONG);
        }
        if (value instanceof char[]) {
            return createArrayValue(ArraysKt.toList((char[]) value), PrimitiveType.CHAR);
        }
        if (value instanceof float[]) {
            return createArrayValue(ArraysKt.toList((float[]) value), PrimitiveType.FLOAT);
        }
        if (value instanceof double[]) {
            return createArrayValue(ArraysKt.toList((double[]) value), PrimitiveType.DOUBLE);
        }
        if (value instanceof boolean[]) {
            return createArrayValue(ArraysKt.toList((boolean[]) value), PrimitiveType.BOOLEAN);
        }
        if (value == null) {
            return new NullValue();
        }
        return null;
    }

    private final ArrayValue createArrayValue(List<?> list, final PrimitiveType componentType) {
        Iterable $receiver$iv = CollectionsKt.toList(list);
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv$iv : $receiver$iv) {
            ConstantValue<?> constantValueCreateConstantValue = createConstantValue(element$iv$iv$iv);
            if (constantValueCreateConstantValue != null) {
                destination$iv$iv.add(constantValueCreateConstantValue);
            }
        }
        return new ArrayValue((List) destination$iv$iv, new Function1<ModuleDescriptor, SimpleType>() { // from class: kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValueFactory.createArrayValue.3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final SimpleType invoke(@NotNull ModuleDescriptor module) {
                Intrinsics.checkParameterIsNotNull(module, "module");
                SimpleType primitiveArrayKotlinType = module.getBuiltIns().getPrimitiveArrayKotlinType(componentType);
                Intrinsics.checkExpressionValueIsNotNull(primitiveArrayKotlinType, "module.builtIns.getPrimi…KotlinType(componentType)");
                return primitiveArrayKotlinType;
            }
        });
    }
}
