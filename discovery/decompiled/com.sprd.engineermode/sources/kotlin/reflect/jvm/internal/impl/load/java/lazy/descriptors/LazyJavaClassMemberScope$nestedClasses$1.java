package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import java.util.Map;
import java.util.Set;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassDescriptorBase;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.EnumEntrySyntheticClassDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.JavaClassFinder;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaAnnotationsKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaField;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: LazyJavaClassMemberScope.kt */
/* JADX INFO: loaded from: classes4.dex */
final class LazyJavaClassMemberScope$nestedClasses$1 extends Lambda implements Function1<Name, ClassDescriptorBase> {
    final /* synthetic */ LazyJavaResolverContext $c;
    final /* synthetic */ LazyJavaClassMemberScope this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    LazyJavaClassMemberScope$nestedClasses$1(LazyJavaClassMemberScope lazyJavaClassMemberScope, LazyJavaResolverContext lazyJavaResolverContext) {
        super(1);
        this.this$0 = lazyJavaClassMemberScope;
        this.$c = lazyJavaResolverContext;
    }

    @Override // kotlin.jvm.functions.Function1
    @Nullable
    public final ClassDescriptorBase invoke(@NotNull Name name) {
        LazyJavaClassDescriptor lazyJavaClassDescriptor;
        Intrinsics.checkParameterIsNotNull(name, "name");
        EnumEntrySyntheticClassDescriptor enumEntrySyntheticClassDescriptorCreate = null;
        if (!((Set) this.this$0.nestedClassIndex.invoke()).contains(name)) {
            JavaField field = (JavaField) ((Map) this.this$0.enumEntryIndex.invoke()).get(name);
            if (field != null) {
                NotNullLazyValue enumMemberNames = this.$c.getStorageManager().createLazyValue(new Function0<Set<? extends Name>>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaClassMemberScope$nestedClasses$1$enumMemberNames$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    @NotNull
                    public final Set<? extends Name> invoke() {
                        return SetsKt.plus((Set) this.this$0.this$0.getFunctionNames(), (Iterable) this.this$0.this$0.getVariableNames());
                    }
                });
                enumEntrySyntheticClassDescriptorCreate = EnumEntrySyntheticClassDescriptor.create(this.$c.getStorageManager(), this.this$0.getOwnerDescriptor(), name, enumMemberNames, LazyJavaAnnotationsKt.resolveAnnotations(this.$c, field), this.$c.getComponents().getSourceElementFactory().source(field));
            }
            return enumEntrySyntheticClassDescriptorCreate;
        }
        JavaClassFinder finder = this.$c.getComponents().getFinder();
        ClassId classId = DescriptorUtilsKt.getClassId(this.this$0.getOwnerDescriptor());
        if (classId == null) {
            Intrinsics.throwNpe();
        }
        ClassId classIdCreateNestedClassId = classId.createNestedClassId(name);
        Intrinsics.checkExpressionValueIsNotNull(classIdCreateNestedClassId, "ownerDescriptor.classId!…createNestedClassId(name)");
        JavaClass it = finder.findClass(new JavaClassFinder.Request(classIdCreateNestedClassId, null, this.this$0.jClass, 2, null));
        if (it != null) {
            lazyJavaClassDescriptor = new LazyJavaClassDescriptor(this.$c, this.this$0.getOwnerDescriptor(), it, null, 8, null);
            LazyJavaClassDescriptor p1 = lazyJavaClassDescriptor;
            this.$c.getComponents().getJavaClassesTracker().reportClass(p1);
        } else {
            lazyJavaClassDescriptor = null;
        }
        return lazyJavaClassDescriptor;
    }
}
