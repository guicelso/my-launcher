package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.SuspendFunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyGetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertySetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassConstructorDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassDescriptorBase;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyGetterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertySetterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.SimpleFunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ValueParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.incremental.UtilsKt;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.load.java.BuiltinMethodsWithDifferentJvmName;
import kotlin.reflect.jvm.internal.impl.load.java.BuiltinMethodsWithSpecialGenericSignature;
import kotlin.reflect.jvm.internal.impl.load.java.BuiltinSpecialProperties;
import kotlin.reflect.jvm.internal.impl.load.java.JavaIncompatibilityRulesOverridabilityCondition;
import kotlin.reflect.jvm.internal.impl.load.java.JavaVisibilities;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAbi;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNames;
import kotlin.reflect.jvm.internal.impl.load.java.PropertiesConventionUtilKt;
import kotlin.reflect.jvm.internal.impl.load.java.SpecialBuiltinMembers;
import kotlin.reflect.jvm.internal.impl.load.java.components.DescriptorResolverUtils;
import kotlin.reflect.jvm.internal.impl.load.java.components.SignaturePropagator;
import kotlin.reflect.jvm.internal.impl.load.java.components.TypeUsage;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaMethodDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaPropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.UtilKt;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.ValueParameterData;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaAnnotationsKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeAttributes;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeResolverKt;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaArrayType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaConstructor;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaField;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMember;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMethod;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameter;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement;
import kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureMappingKt;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorFactory;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ErrorReporter;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNullable;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.utils.SmartSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: LazyJavaClassMemberScope.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class LazyJavaClassMemberScope extends LazyJavaScope {

    @NotNull
    private final NotNullLazyValue<List<ClassConstructorDescriptor>> constructors;
    private final NotNullLazyValue<Map<Name, JavaField>> enumEntryIndex;
    private final JavaClass jClass;
    private final NotNullLazyValue<Set<Name>> nestedClassIndex;
    private final MemoizedFunctionToNullable<Name, ClassDescriptorBase> nestedClasses;

    @NotNull
    private final ClassDescriptor ownerDescriptor;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyJavaClassMemberScope(@NotNull final LazyJavaResolverContext c, @NotNull ClassDescriptor ownerDescriptor, @NotNull JavaClass jClass) {
        super(c);
        Intrinsics.checkParameterIsNotNull(c, "c");
        Intrinsics.checkParameterIsNotNull(ownerDescriptor, "ownerDescriptor");
        Intrinsics.checkParameterIsNotNull(jClass, "jClass");
        this.ownerDescriptor = ownerDescriptor;
        this.jClass = jClass;
        this.constructors = c.getStorageManager().createLazyValue(new Function0<List<? extends ClassConstructorDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaClassMemberScope$constructors$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final List<? extends ClassConstructorDescriptor> invoke() {
                Collection<JavaConstructor> constructors = this.this$0.jClass.getConstructors();
                ArrayList result = new ArrayList(constructors.size());
                for (JavaConstructor constructor : constructors) {
                    JavaClassConstructorDescriptor descriptor = this.this$0.resolveConstructor(constructor);
                    result.add(descriptor);
                }
                SignatureEnhancement signatureEnhancement = c.getComponents().getSignatureEnhancement();
                LazyJavaResolverContext lazyJavaResolverContext = c;
                ArrayList arrayListListOfNotNull = result;
                if (arrayListListOfNotNull.isEmpty()) {
                    arrayListListOfNotNull = CollectionsKt.listOfNotNull(this.this$0.createDefaultConstructor());
                }
                return CollectionsKt.toList(signatureEnhancement.enhanceSignatures(lazyJavaResolverContext, arrayListListOfNotNull));
            }
        });
        this.nestedClassIndex = c.getStorageManager().createLazyValue(new Function0<Set<? extends Name>>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaClassMemberScope$nestedClassIndex$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Set<? extends Name> invoke() {
                return CollectionsKt.toSet(this.this$0.jClass.getInnerClassNames());
            }
        });
        this.enumEntryIndex = c.getStorageManager().createLazyValue(new Function0<Map<Name, ? extends JavaField>>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaClassMemberScope$enumEntryIndex$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Map<Name, ? extends JavaField> invoke() {
                Iterable $receiver$iv = this.this$0.jClass.getFields();
                Collection destination$iv$iv = new ArrayList();
                for (Object element$iv$iv : $receiver$iv) {
                    JavaField it = (JavaField) element$iv$iv;
                    if (it.isEnumEntry()) {
                        destination$iv$iv.add(element$iv$iv);
                    }
                }
                Iterable $receiver$iv2 = (List) destination$iv$iv;
                Iterable $receiver$iv3 = $receiver$iv2;
                int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($receiver$iv3, 10)), 16);
                Map destination$iv$iv2 = new LinkedHashMap(capacity$iv);
                for (Object element$iv$iv2 : $receiver$iv3) {
                    JavaField f = (JavaField) element$iv$iv2;
                    destination$iv$iv2.put(f.getName(), element$iv$iv2);
                }
                return destination$iv$iv2;
            }
        });
        this.nestedClasses = c.getStorageManager().createMemoizedFunctionWithNullableValues(new LazyJavaClassMemberScope$nestedClasses$1(this, c));
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope
    public /* bridge */ /* synthetic */ Set computeFunctionNames(DescriptorKindFilter descriptorKindFilter, Function1 function1) {
        return computeFunctionNames(descriptorKindFilter, (Function1<? super Name, Boolean>) function1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope
    @NotNull
    public ClassDescriptor getOwnerDescriptor() {
        return this.ownerDescriptor;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope
    @NotNull
    public ClassDeclaredMemberIndex computeMemberIndex() {
        return new ClassDeclaredMemberIndex(this.jClass, new Function1<JavaMember, Boolean>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaClassMemberScope.computeMemberIndex.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(JavaMember javaMember) {
                return Boolean.valueOf(invoke2(javaMember));
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final boolean invoke2(@NotNull JavaMember it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return !it.isStatic();
            }
        });
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope
    @NotNull
    protected HashSet<Name> computeFunctionNames(@NotNull DescriptorKindFilter kindFilter, @Nullable Function1<? super Name, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(kindFilter, "kindFilter");
        TypeConstructor typeConstructor = getOwnerDescriptor().getTypeConstructor();
        Intrinsics.checkExpressionValueIsNotNull(typeConstructor, "ownerDescriptor.typeConstructor");
        Iterable supertypes = typeConstructor.getSupertypes();
        Intrinsics.checkExpressionValueIsNotNull(supertypes, "ownerDescriptor.typeConstructor.supertypes");
        Iterable $receiver$iv = supertypes;
        Collection destination$iv = new HashSet();
        for (Object element$iv : $receiver$iv) {
            KotlinType it = (KotlinType) element$iv;
            Iterable list$iv = it.getMemberScope().getFunctionNames();
            CollectionsKt.addAll(destination$iv, list$iv);
        }
        HashSet<Name> hashSet = (HashSet) destination$iv;
        hashSet.addAll(getDeclaredMemberIndex().invoke().getMethodNames());
        hashSet.addAll(computeClassNames(kindFilter, function1));
        return (HashSet) destination$iv;
    }

    @NotNull
    public final NotNullLazyValue<List<ClassConstructorDescriptor>> getConstructors$descriptors_jvm() {
        return this.constructors;
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope
    protected boolean isVisibleAsFunction(@NotNull JavaMethodDescriptor receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        if (this.jClass.isAnnotationType()) {
            return false;
        }
        return isVisibleAsFunctionInCurrentClass(receiver$0);
    }

    private final boolean isVisibleAsFunctionInCurrentClass(final SimpleFunctionDescriptor function) {
        boolean z;
        boolean z2;
        Name name = function.getName();
        Intrinsics.checkExpressionValueIsNotNull(name, "function.name");
        Iterable $receiver$iv = PropertiesConventionUtilKt.getPropertyNamesCandidatesByAccessorName(name);
        if (!($receiver$iv instanceof Collection) || !((Collection) $receiver$iv).isEmpty()) {
            Iterator it = $receiver$iv.iterator();
            while (true) {
                if (it.hasNext()) {
                    Object element$iv = it.next();
                    Name propertyName = (Name) element$iv;
                    Iterable $receiver$iv2 = getPropertiesFromSupertypes(propertyName);
                    if (!($receiver$iv2 instanceof Collection) || !((Collection) $receiver$iv2).isEmpty()) {
                        Iterator it2 = $receiver$iv2.iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                Object element$iv2 = it2.next();
                                PropertyDescriptor property = (PropertyDescriptor) element$iv2;
                                if (doesClassOverridesProperty(property, new Function1<Name, Collection<? extends SimpleFunctionDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaClassMemberScope$isVisibleAsFunctionInCurrentClass$$inlined$any$lambda$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    @NotNull
                                    public final Collection<SimpleFunctionDescriptor> invoke(@NotNull Name accessorName) {
                                        Intrinsics.checkParameterIsNotNull(accessorName, "accessorName");
                                        if (!Intrinsics.areEqual(function.getName(), accessorName)) {
                                            return CollectionsKt.plus(this.this$0.searchMethodsByNameWithoutBuiltinMagic(accessorName), (Iterable) this.this$0.searchMethodsInSupertypesWithoutBuiltinMagic(accessorName));
                                        }
                                        return CollectionsKt.listOf(function);
                                    }
                                }) && (property.isVar() || !JvmAbi.isSetterName(function.getName().asString()))) {
                                    z2 = true;
                                    break;
                                }
                            } else {
                                z2 = false;
                                break;
                            }
                        }
                    } else {
                        z2 = false;
                    }
                    if (z2) {
                        z = true;
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
        } else {
            z = false;
        }
        if (z) {
            return false;
        }
        return (doesOverrideRenamedBuiltins(function) || shouldBeVisibleAsOverrideOfBuiltInWithErasedValueParameters(function) || doesOverrideSuspendFunction(function)) ? false : true;
    }

    private final boolean shouldBeVisibleAsOverrideOfBuiltInWithErasedValueParameters(@NotNull SimpleFunctionDescriptor $receiver) {
        BuiltinMethodsWithSpecialGenericSignature builtinMethodsWithSpecialGenericSignature = BuiltinMethodsWithSpecialGenericSignature.INSTANCE;
        Name name = $receiver.getName();
        Intrinsics.checkExpressionValueIsNotNull(name, "name");
        if (!builtinMethodsWithSpecialGenericSignature.getSameAsBuiltinMethodWithErasedValueParameters(name)) {
            return false;
        }
        Name name2 = $receiver.getName();
        Intrinsics.checkExpressionValueIsNotNull(name2, "name");
        Iterable $receiver$iv = getFunctionsFromSupertypes(name2);
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv$iv : $receiver$iv) {
            SimpleFunctionDescriptor it = (SimpleFunctionDescriptor) element$iv$iv$iv;
            FunctionDescriptor overriddenBuiltinFunctionWithErasedValueParametersInJava = BuiltinMethodsWithSpecialGenericSignature.getOverriddenBuiltinFunctionWithErasedValueParametersInJava(it);
            if (overriddenBuiltinFunctionWithErasedValueParametersInJava != null) {
                destination$iv$iv.add(overriddenBuiltinFunctionWithErasedValueParametersInJava);
            }
        }
        Iterable candidatesToOverride = (List) destination$iv$iv;
        Iterable $receiver$iv2 = candidatesToOverride;
        if (($receiver$iv2 instanceof Collection) && ((Collection) $receiver$iv2).isEmpty()) {
            return false;
        }
        for (Object element$iv : $receiver$iv2) {
            FunctionDescriptor candidate = (FunctionDescriptor) element$iv;
            if (hasSameJvmDescriptorButDoesNotOverride($receiver, candidate)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Collection<SimpleFunctionDescriptor> searchMethodsByNameWithoutBuiltinMagic(Name name) {
        Iterable $receiver$iv = getDeclaredMemberIndex().invoke().findMethodsByName(name);
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        for (Object item$iv$iv : $receiver$iv) {
            JavaMethod it = (JavaMethod) item$iv$iv;
            destination$iv$iv.add(resolveMethodToFunctionDescriptor(it));
        }
        return (Collection) ((List) destination$iv$iv);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Collection<SimpleFunctionDescriptor> searchMethodsInSupertypesWithoutBuiltinMagic(Name name) {
        Iterable $receiver$iv = getFunctionsFromSupertypes(name);
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $receiver$iv) {
            SimpleFunctionDescriptor it = (SimpleFunctionDescriptor) element$iv$iv;
            if (!(SpecialBuiltinMembers.doesOverrideBuiltinWithDifferentJvmName(it) || BuiltinMethodsWithSpecialGenericSignature.getOverriddenBuiltinFunctionWithErasedValueParametersInJava(it) != null)) {
                destination$iv$iv.add(element$iv$iv);
            }
        }
        Iterable $receiver$iv2 = (List) destination$iv$iv;
        return (Collection) $receiver$iv2;
    }

    private final boolean doesOverrideRenamedBuiltins(@NotNull SimpleFunctionDescriptor $receiver) {
        boolean z;
        BuiltinMethodsWithDifferentJvmName builtinMethodsWithDifferentJvmName = BuiltinMethodsWithDifferentJvmName.INSTANCE;
        Name name = $receiver.getName();
        Intrinsics.checkExpressionValueIsNotNull(name, "name");
        Iterable $receiver$iv = builtinMethodsWithDifferentJvmName.getBuiltinFunctionNamesByJvmName(name);
        if (($receiver$iv instanceof Collection) && ((Collection) $receiver$iv).isEmpty()) {
            return false;
        }
        boolean z2 = false;
        for (Object element$iv : $receiver$iv) {
            Name builtinName = (Name) element$iv;
            Iterable $receiver$iv2 = getFunctionsFromSupertypes(builtinName);
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $receiver$iv2) {
                SimpleFunctionDescriptor it = (SimpleFunctionDescriptor) element$iv$iv;
                if (SpecialBuiltinMembers.doesOverrideBuiltinWithDifferentJvmName(it)) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            List builtinSpecialFromSuperTypes = (List) destination$iv$iv;
            if (builtinSpecialFromSuperTypes.isEmpty()) {
                z = false;
            } else {
                SimpleFunctionDescriptor methodDescriptor = createRenamedCopy($receiver, builtinName);
                List $receiver$iv3 = builtinSpecialFromSuperTypes;
                if (!($receiver$iv3 instanceof Collection) || !$receiver$iv3.isEmpty()) {
                    Iterator it2 = $receiver$iv3.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            z = false;
                            break;
                        }
                        Object element$iv2 = it2.next();
                        SimpleFunctionDescriptor it3 = (SimpleFunctionDescriptor) element$iv2;
                        if (doesOverrideRenamedDescriptor(it3, methodDescriptor)) {
                            z = true;
                            break;
                        }
                    }
                } else {
                    z = false;
                }
            }
            if (z) {
                return true;
            }
            z2 = z2;
        }
        return false;
    }

    private final boolean doesOverrideSuspendFunction(@NotNull SimpleFunctionDescriptor $receiver) {
        SimpleFunctionDescriptor suspendView = createSuspendView($receiver);
        if (suspendView == null) {
            return false;
        }
        Name name = $receiver.getName();
        Intrinsics.checkExpressionValueIsNotNull(name, "name");
        Iterable $receiver$iv = getFunctionsFromSupertypes(name);
        if (($receiver$iv instanceof Collection) && ((Collection) $receiver$iv).isEmpty()) {
            return false;
        }
        for (Object element$iv : $receiver$iv) {
            SimpleFunctionDescriptor overriddenCandidate = (SimpleFunctionDescriptor) element$iv;
            if (overriddenCandidate.isSuspend() && doesOverride(suspendView, overriddenCandidate)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x003a  */
    private final SimpleFunctionDescriptor createSuspendView(@NotNull SimpleFunctionDescriptor $receiver) {
        FqName safe;
        FqNameUnsafe it;
        List<ValueParameterDescriptor> valueParameters = $receiver.getValueParameters();
        Intrinsics.checkExpressionValueIsNotNull(valueParameters, "valueParameters");
        ValueParameterDescriptor continuationParameter = (ValueParameterDescriptor) CollectionsKt.lastOrNull((List) valueParameters);
        if (continuationParameter != null) {
            ClassifierDescriptor classifierDescriptorMo381getDeclarationDescriptor = continuationParameter.getType().getConstructor().mo381getDeclarationDescriptor();
            if (classifierDescriptorMo381getDeclarationDescriptor == null || (it = DescriptorUtilsKt.getFqNameUnsafe(classifierDescriptorMo381getDeclarationDescriptor)) == null) {
                safe = null;
            } else {
                if (!it.isSafe()) {
                    it = null;
                }
                if (it != null) {
                    safe = it.toSafe();
                } else {
                    safe = null;
                }
            }
            if (!SuspendFunctionTypesKt.isContinuation(safe, getC().getComponents().getSettings().isReleaseCoroutines())) {
                continuationParameter = null;
            }
            if (continuationParameter != null) {
                FunctionDescriptor.CopyBuilder<? extends SimpleFunctionDescriptor> copyBuilderNewCopyBuilder = $receiver.newCopyBuilder();
                List<ValueParameterDescriptor> valueParameters2 = $receiver.getValueParameters();
                Intrinsics.checkExpressionValueIsNotNull(valueParameters2, "valueParameters");
                SimpleFunctionDescriptor functionDescriptor = (SimpleFunctionDescriptor) copyBuilderNewCopyBuilder.setValueParameters(CollectionsKt.dropLast(valueParameters2, 1)).setReturnType(continuationParameter.getType().getArguments().get(0).getType()).build();
                SimpleFunctionDescriptorImpl simpleFunctionDescriptorImpl = (SimpleFunctionDescriptorImpl) functionDescriptor;
                if (simpleFunctionDescriptorImpl != null) {
                    simpleFunctionDescriptorImpl.setSuspend(true);
                }
                return functionDescriptor;
            }
        }
        return null;
    }

    private final SimpleFunctionDescriptor createRenamedCopy(@NotNull SimpleFunctionDescriptor $receiver, Name builtinName) {
        FunctionDescriptor.CopyBuilder<? extends SimpleFunctionDescriptor> copyBuilderNewCopyBuilder = $receiver.newCopyBuilder();
        copyBuilderNewCopyBuilder.setName(builtinName);
        copyBuilderNewCopyBuilder.setSignatureChange();
        copyBuilderNewCopyBuilder.setPreserveSourceElement();
        FunctionDescriptor functionDescriptorBuild = copyBuilderNewCopyBuilder.build();
        if (functionDescriptorBuild == null) {
            Intrinsics.throwNpe();
        }
        return (SimpleFunctionDescriptor) functionDescriptorBuild;
    }

    private final boolean doesOverrideRenamedDescriptor(SimpleFunctionDescriptor superDescriptor, FunctionDescriptor subDescriptor) {
        FunctionDescriptor subDescriptorToCheck = BuiltinMethodsWithDifferentJvmName.INSTANCE.isRemoveAtByIndex(superDescriptor) ? subDescriptor.getOriginal() : subDescriptor;
        Intrinsics.checkExpressionValueIsNotNull(subDescriptorToCheck, "if (superDescriptor.isRe…iginal else subDescriptor");
        return doesOverride(subDescriptorToCheck, superDescriptor);
    }

    private final boolean doesOverride(@NotNull CallableDescriptor $receiver, CallableDescriptor superDescriptor) {
        OverridingUtil.OverrideCompatibilityInfo overrideCompatibilityInfoIsOverridableByWithoutExternalConditions = OverridingUtil.DEFAULT.isOverridableByWithoutExternalConditions(superDescriptor, $receiver, true);
        Intrinsics.checkExpressionValueIsNotNull(overrideCompatibilityInfoIsOverridableByWithoutExternalConditions, "OverridingUtil.DEFAULT.i…erDescriptor, this, true)");
        OverridingUtil.OverrideCompatibilityInfo.Result commonOverridabilityResult = overrideCompatibilityInfoIsOverridableByWithoutExternalConditions.getResult();
        Intrinsics.checkExpressionValueIsNotNull(commonOverridabilityResult, "OverridingUtil.DEFAULT.i…iptor, this, true).result");
        return commonOverridabilityResult == OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE && !JavaIncompatibilityRulesOverridabilityCondition.Companion.doesJavaOverrideHaveIncompatibleValueParameterKinds(superDescriptor, $receiver);
    }

    private final SimpleFunctionDescriptor findGetterOverride(@NotNull PropertyDescriptor $receiver, Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>> function1) {
        PropertyGetterDescriptor getter = $receiver.getGetter();
        PropertyGetterDescriptor overriddenBuiltinProperty = getter != null ? (PropertyGetterDescriptor) SpecialBuiltinMembers.getOverriddenBuiltinWithDifferentJvmName(getter) : null;
        String specialGetterName = overriddenBuiltinProperty != null ? BuiltinSpecialProperties.INSTANCE.getBuiltinSpecialPropertyGetterName(overriddenBuiltinProperty) : null;
        if (specialGetterName != null && !SpecialBuiltinMembers.hasRealKotlinSuperClassWithOverrideOf(getOwnerDescriptor(), overriddenBuiltinProperty)) {
            return findGetterByName($receiver, specialGetterName, function1);
        }
        String str = JvmAbi.getterName($receiver.getName().asString());
        Intrinsics.checkExpressionValueIsNotNull(str, "JvmAbi.getterName(name.asString())");
        return findGetterByName($receiver, str, function1);
    }

    private final SimpleFunctionDescriptor findGetterByName(@NotNull PropertyDescriptor $receiver, String getterName, Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>> function1) {
        SimpleFunctionDescriptor simpleFunctionDescriptor;
        Name nameIdentifier = Name.identifier(getterName);
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier, "Name.identifier(getterName)");
        Iterable $receiver$iv = function1.invoke(nameIdentifier);
        Iterator it = $receiver$iv.iterator();
        do {
            simpleFunctionDescriptor = null;
            if (!it.hasNext()) {
                break;
            }
            Object element$iv = it.next();
            SimpleFunctionDescriptor descriptor = (SimpleFunctionDescriptor) element$iv;
            if (descriptor.getValueParameters().size() == 0) {
                KotlinTypeChecker kotlinTypeChecker = KotlinTypeChecker.DEFAULT;
                KotlinType returnType = descriptor.getReturnType();
                if (returnType != null ? kotlinTypeChecker.isSubtypeOf(returnType, $receiver.getType()) : false) {
                    simpleFunctionDescriptor = descriptor;
                }
            }
        } while (simpleFunctionDescriptor == null);
        return simpleFunctionDescriptor;
    }

    private final SimpleFunctionDescriptor findSetterOverride(@NotNull PropertyDescriptor $receiver, Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>> function1) {
        SimpleFunctionDescriptor simpleFunctionDescriptor;
        KotlinType returnType;
        Name nameIdentifier = Name.identifier(JvmAbi.setterName($receiver.getName().asString()));
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier, "Name.identifier(JvmAbi.s…terName(name.asString()))");
        Iterable $receiver$iv = function1.invoke(nameIdentifier);
        Iterator it = $receiver$iv.iterator();
        do {
            simpleFunctionDescriptor = null;
            if (!it.hasNext()) {
                break;
            }
            Object element$iv = it.next();
            SimpleFunctionDescriptor descriptor = (SimpleFunctionDescriptor) element$iv;
            if (descriptor.getValueParameters().size() == 1 && (returnType = descriptor.getReturnType()) != null && KotlinBuiltIns.isUnit(returnType)) {
                KotlinTypeChecker kotlinTypeChecker = KotlinTypeChecker.DEFAULT;
                List<ValueParameterDescriptor> valueParameters = descriptor.getValueParameters();
                Intrinsics.checkExpressionValueIsNotNull(valueParameters, "descriptor.valueParameters");
                Object objSingle = CollectionsKt.single((List<? extends Object>) valueParameters);
                Intrinsics.checkExpressionValueIsNotNull(objSingle, "descriptor.valueParameters.single()");
                if (kotlinTypeChecker.equalTypes(((ValueParameterDescriptor) objSingle).getType(), $receiver.getType())) {
                    simpleFunctionDescriptor = descriptor;
                }
            }
        } while (simpleFunctionDescriptor == null);
        return simpleFunctionDescriptor;
    }

    private final boolean doesClassOverridesProperty(PropertyDescriptor property, Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>> function1) {
        if (JavaDescriptorUtilKt.isJavaField(property)) {
            return false;
        }
        SimpleFunctionDescriptor getter = findGetterOverride(property, function1);
        SimpleFunctionDescriptor setter = findSetterOverride(property, function1);
        if (getter == null) {
            return false;
        }
        if (property.isVar()) {
            return setter != null && setter.getModality() == getter.getModality();
        }
        return true;
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope
    protected void computeNonDeclaredFunctions(@NotNull Collection<SimpleFunctionDescriptor> result, @NotNull Name name) {
        boolean z;
        Intrinsics.checkParameterIsNotNull(result, "result");
        Intrinsics.checkParameterIsNotNull(name, "name");
        Iterable functionsFromSupertypes = getFunctionsFromSupertypes(name);
        if (!BuiltinMethodsWithDifferentJvmName.INSTANCE.getSameAsRenamedInJvmBuiltin(name) && !BuiltinMethodsWithSpecialGenericSignature.INSTANCE.getSameAsBuiltinMethodWithErasedValueParameters(name)) {
            Iterable $receiver$iv = functionsFromSupertypes;
            if (!($receiver$iv instanceof Collection) || !((Collection) $receiver$iv).isEmpty()) {
                Iterator it = $receiver$iv.iterator();
                while (true) {
                    if (it.hasNext()) {
                        Object element$iv = it.next();
                        FunctionDescriptor p1 = (FunctionDescriptor) element$iv;
                        if (p1.isSuspend()) {
                            z = false;
                            break;
                        }
                    } else {
                        z = true;
                        break;
                    }
                }
            } else {
                z = true;
            }
            if (z) {
                Iterable $receiver$iv2 = functionsFromSupertypes;
                Collection destination$iv$iv = new ArrayList();
                for (Object element$iv$iv : $receiver$iv2) {
                    SimpleFunctionDescriptor it2 = (SimpleFunctionDescriptor) element$iv$iv;
                    if (isVisibleAsFunctionInCurrentClass(it2)) {
                        destination$iv$iv.add(element$iv$iv);
                    }
                }
                Iterable $receiver$iv3 = (List) destination$iv$iv;
                addFunctionFromSupertypes(result, name, (Collection) $receiver$iv3, false);
                return;
            }
        }
        SmartSet specialBuiltinsFromSuperTypes = SmartSet.Companion.create();
        Collection<? extends SimpleFunctionDescriptor> collectionResolveOverridesForNonStaticMembers = DescriptorResolverUtils.resolveOverridesForNonStaticMembers(name, (Collection) functionsFromSupertypes, CollectionsKt.emptyList(), getOwnerDescriptor(), ErrorReporter.DO_NOTHING);
        Intrinsics.checkExpressionValueIsNotNull(collectionResolveOverridesForNonStaticMembers, "resolveOverridesForNonSt…rter.DO_NOTHING\n        )");
        addOverriddenSpecialMethods(name, result, collectionResolveOverridesForNonStaticMembers, result, new AnonymousClass3(this));
        addOverriddenSpecialMethods(name, result, collectionResolveOverridesForNonStaticMembers, specialBuiltinsFromSuperTypes, new AnonymousClass4(this));
        Iterable $receiver$iv4 = functionsFromSupertypes;
        Collection destination$iv$iv2 = new ArrayList();
        for (Object element$iv$iv2 : $receiver$iv4) {
            SimpleFunctionDescriptor it3 = (SimpleFunctionDescriptor) element$iv$iv2;
            if (isVisibleAsFunctionInCurrentClass(it3)) {
                destination$iv$iv2.add(element$iv$iv2);
            }
        }
        Iterable $receiver$iv5 = (List) destination$iv$iv2;
        List visibleFunctionsFromSupertypes = CollectionsKt.plus((Collection) $receiver$iv5, (Iterable) specialBuiltinsFromSuperTypes);
        addFunctionFromSupertypes(result, name, visibleFunctionsFromSupertypes, true);
    }

    /* JADX INFO: renamed from: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaClassMemberScope$computeNonDeclaredFunctions$3, reason: invalid class name */
    /* JADX INFO: compiled from: LazyJavaClassMemberScope.kt */
    static final class AnonymousClass3 extends FunctionReference implements Function1<Name, Collection<? extends SimpleFunctionDescriptor>> {
        AnonymousClass3(LazyJavaClassMemberScope lazyJavaClassMemberScope) {
            super(1, lazyJavaClassMemberScope);
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "searchMethodsByNameWithoutBuiltinMagic";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(LazyJavaClassMemberScope.class);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "searchMethodsByNameWithoutBuiltinMagic(Lorg/jetbrains/kotlin/name/Name;)Ljava/util/Collection;";
        }

        @Override // kotlin.jvm.functions.Function1
        @NotNull
        public final Collection<SimpleFunctionDescriptor> invoke(@NotNull Name p1) {
            Intrinsics.checkParameterIsNotNull(p1, "p1");
            return ((LazyJavaClassMemberScope) this.receiver).searchMethodsByNameWithoutBuiltinMagic(p1);
        }
    }

    /* JADX INFO: renamed from: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaClassMemberScope$computeNonDeclaredFunctions$4, reason: invalid class name */
    /* JADX INFO: compiled from: LazyJavaClassMemberScope.kt */
    static final class AnonymousClass4 extends FunctionReference implements Function1<Name, Collection<? extends SimpleFunctionDescriptor>> {
        AnonymousClass4(LazyJavaClassMemberScope lazyJavaClassMemberScope) {
            super(1, lazyJavaClassMemberScope);
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "searchMethodsInSupertypesWithoutBuiltinMagic";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(LazyJavaClassMemberScope.class);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "searchMethodsInSupertypesWithoutBuiltinMagic(Lorg/jetbrains/kotlin/name/Name;)Ljava/util/Collection;";
        }

        @Override // kotlin.jvm.functions.Function1
        @NotNull
        public final Collection<SimpleFunctionDescriptor> invoke(@NotNull Name p1) {
            Intrinsics.checkParameterIsNotNull(p1, "p1");
            return ((LazyJavaClassMemberScope) this.receiver).searchMethodsInSupertypesWithoutBuiltinMagic(p1);
        }
    }

    private final void addFunctionFromSupertypes(Collection<SimpleFunctionDescriptor> collection, Name name, Collection<? extends SimpleFunctionDescriptor> collection2, boolean isSpecialBuiltinName) {
        Collection<? extends SimpleFunctionDescriptor> collectionResolveOverridesForNonStaticMembers = DescriptorResolverUtils.resolveOverridesForNonStaticMembers(name, collection2, collection, getOwnerDescriptor(), getC().getComponents().getErrorReporter());
        Intrinsics.checkExpressionValueIsNotNull(collectionResolveOverridesForNonStaticMembers, "resolveOverridesForNonSt…s.errorReporter\n        )");
        if (isSpecialBuiltinName) {
            Collection allDescriptors = CollectionsKt.plus((Collection) collection, (Iterable) collectionResolveOverridesForNonStaticMembers);
            Collection<? extends SimpleFunctionDescriptor> $receiver$iv = collectionResolveOverridesForNonStaticMembers;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
            for (Object item$iv$iv : $receiver$iv) {
                SimpleFunctionDescriptor resolvedOverride = (SimpleFunctionDescriptor) item$iv$iv;
                SimpleFunctionDescriptor overriddenBuiltin = (SimpleFunctionDescriptor) SpecialBuiltinMembers.getOverriddenSpecialBuiltin(resolvedOverride);
                if (overriddenBuiltin != null) {
                    Intrinsics.checkExpressionValueIsNotNull(resolvedOverride, "resolvedOverride");
                    Collection additionalOverrides = allDescriptors;
                    resolvedOverride = createHiddenCopyIfBuiltinAlreadyAccidentallyOverridden(resolvedOverride, overriddenBuiltin, additionalOverrides);
                }
                destination$iv$iv.add(resolvedOverride);
                collectionResolveOverridesForNonStaticMembers = collectionResolveOverridesForNonStaticMembers;
                allDescriptors = allDescriptors;
            }
            collection.addAll((List) destination$iv$iv);
            return;
        }
        collection.addAll(collectionResolveOverridesForNonStaticMembers);
    }

    private final void addOverriddenSpecialMethods(Name name, Collection<? extends SimpleFunctionDescriptor> collection, Collection<? extends SimpleFunctionDescriptor> collection2, Collection<SimpleFunctionDescriptor> collection3, Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>> function1) {
        for (SimpleFunctionDescriptor descriptor : collection2) {
            kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.addIfNotNull(collection3, obtainOverrideForBuiltinWithDifferentJvmName(descriptor, function1, name, collection));
            kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.addIfNotNull(collection3, obtainOverrideForBuiltInWithErasedValueParametersInJava(descriptor, function1, collection));
            kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.addIfNotNull(collection3, obtainOverrideForSuspend(descriptor, function1));
        }
    }

    private final SimpleFunctionDescriptor obtainOverrideForBuiltInWithErasedValueParametersInJava(SimpleFunctionDescriptor descriptor, Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>> function1, Collection<? extends SimpleFunctionDescriptor> collection) {
        SimpleFunctionDescriptor p1;
        FunctionDescriptor overriddenBuiltin = BuiltinMethodsWithSpecialGenericSignature.getOverriddenBuiltinFunctionWithErasedValueParametersInJava(descriptor);
        if (overriddenBuiltin == null || (p1 = createOverrideForBuiltinFunctionWithErasedParameterIfNeeded(overriddenBuiltin, function1)) == null) {
            return null;
        }
        if (!isVisibleAsFunctionInCurrentClass(p1)) {
            p1 = null;
        }
        if (p1 != null) {
            return createHiddenCopyIfBuiltinAlreadyAccidentallyOverridden(p1, overriddenBuiltin, collection);
        }
        return null;
    }

    private final SimpleFunctionDescriptor obtainOverrideForBuiltinWithDifferentJvmName(SimpleFunctionDescriptor descriptor, Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>> function1, Name name, Collection<? extends SimpleFunctionDescriptor> collection) {
        SimpleFunctionDescriptor overriddenBuiltin = (SimpleFunctionDescriptor) SpecialBuiltinMembers.getOverriddenBuiltinWithDifferentJvmName(descriptor);
        if (overriddenBuiltin == null) {
            return null;
        }
        String nameInJava = SpecialBuiltinMembers.getJvmMethodNameIfSpecial(overriddenBuiltin);
        if (nameInJava == null) {
            Intrinsics.throwNpe();
        }
        Name nameIdentifier = Name.identifier(nameInJava);
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier, "Name.identifier(nameInJava)");
        for (SimpleFunctionDescriptor method : function1.invoke(nameIdentifier)) {
            SimpleFunctionDescriptor renamedCopy = createRenamedCopy(method, name);
            if (doesOverrideRenamedDescriptor(overriddenBuiltin, renamedCopy)) {
                return createHiddenCopyIfBuiltinAlreadyAccidentallyOverridden(renamedCopy, overriddenBuiltin, collection);
            }
        }
        return null;
    }

    private final SimpleFunctionDescriptor obtainOverrideForSuspend(SimpleFunctionDescriptor descriptor, Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>> function1) {
        if (!descriptor.isSuspend()) {
            return null;
        }
        Name name = descriptor.getName();
        Intrinsics.checkExpressionValueIsNotNull(name, "descriptor.name");
        Iterable $receiver$iv = function1.invoke(name);
        for (Object element$iv : $receiver$iv) {
            SimpleFunctionDescriptor suspendView = createSuspendView((SimpleFunctionDescriptor) element$iv);
            if (suspendView == null || !doesOverride(suspendView, descriptor)) {
                suspendView = null;
            }
            SimpleFunctionDescriptor overrideCandidate = suspendView;
            if (overrideCandidate != null) {
                return overrideCandidate;
            }
        }
        return null;
    }

    private final SimpleFunctionDescriptor createHiddenCopyIfBuiltinAlreadyAccidentallyOverridden(@NotNull SimpleFunctionDescriptor $receiver, CallableDescriptor specialBuiltin, Collection<? extends SimpleFunctionDescriptor> collection) {
        Collection<? extends SimpleFunctionDescriptor> $receiver$iv = collection;
        boolean z = true;
        if (!($receiver$iv instanceof Collection) || !$receiver$iv.isEmpty()) {
            for (Object element$iv : $receiver$iv) {
                SimpleFunctionDescriptor it = (SimpleFunctionDescriptor) element$iv;
                if ((Intrinsics.areEqual($receiver, it) ^ true) && it.getInitialSignatureDescriptor() == null && doesOverride(it, specialBuiltin)) {
                    z = false;
                    break;
                }
            }
        }
        if (z) {
            return $receiver;
        }
        FunctionDescriptor functionDescriptorBuild = $receiver.newCopyBuilder().setHiddenToOvercomeSignatureClash().build();
        if (functionDescriptorBuild == null) {
            Intrinsics.throwNpe();
        }
        return (SimpleFunctionDescriptor) functionDescriptorBuild;
    }

    private final SimpleFunctionDescriptor createOverrideForBuiltinFunctionWithErasedParameterIfNeeded(FunctionDescriptor overridden, Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>> function1) {
        Object element$iv;
        Name name = overridden.getName();
        Intrinsics.checkExpressionValueIsNotNull(name, "overridden.name");
        Iterator it = function1.invoke(name).iterator();
        do {
            if (!it.hasNext()) {
                element$iv = null;
                break;
            }
            element$iv = it.next();
        } while (!hasSameJvmDescriptorButDoesNotOverride((SimpleFunctionDescriptor) element$iv, overridden));
        SimpleFunctionDescriptor override = (SimpleFunctionDescriptor) element$iv;
        if (override == null) {
            return null;
        }
        FunctionDescriptor.CopyBuilder<? extends SimpleFunctionDescriptor> copyBuilderNewCopyBuilder = override.newCopyBuilder();
        Iterable valueParameters = overridden.getValueParameters();
        Intrinsics.checkExpressionValueIsNotNull(valueParameters, "overridden.valueParameters");
        Iterable $receiver$iv = valueParameters;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        for (Object item$iv$iv : $receiver$iv) {
            ValueParameterDescriptor it2 = (ValueParameterDescriptor) item$iv$iv;
            Intrinsics.checkExpressionValueIsNotNull(it2, "it");
            KotlinType type = it2.getType();
            Intrinsics.checkExpressionValueIsNotNull(type, "it.type");
            destination$iv$iv.add(new ValueParameterData(type, it2.declaresDefaultValue()));
        }
        ArrayList arrayList = (Collection) ((List) destination$iv$iv);
        List<ValueParameterDescriptor> valueParameters2 = override.getValueParameters();
        Intrinsics.checkExpressionValueIsNotNull(valueParameters2, "override.valueParameters");
        copyBuilderNewCopyBuilder.setValueParameters(UtilKt.copyValueParameters(arrayList, valueParameters2, overridden));
        copyBuilderNewCopyBuilder.setSignatureChange();
        copyBuilderNewCopyBuilder.setPreserveSourceElement();
        return (SimpleFunctionDescriptor) copyBuilderNewCopyBuilder.build();
    }

    private final Set<SimpleFunctionDescriptor> getFunctionsFromSupertypes(Name name) {
        TypeConstructor typeConstructor = getOwnerDescriptor().getTypeConstructor();
        Intrinsics.checkExpressionValueIsNotNull(typeConstructor, "ownerDescriptor.typeConstructor");
        Iterable supertypes = typeConstructor.getSupertypes();
        Intrinsics.checkExpressionValueIsNotNull(supertypes, "ownerDescriptor.typeConstructor.supertypes");
        Iterable $receiver$iv = supertypes;
        Collection destination$iv = new LinkedHashSet();
        for (Object element$iv : $receiver$iv) {
            KotlinType it = (KotlinType) element$iv;
            Iterable list$iv = it.getMemberScope().getContributedFunctions(name, NoLookupLocation.WHEN_GET_SUPER_MEMBERS);
            CollectionsKt.addAll(destination$iv, list$iv);
        }
        return (Set) destination$iv;
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope
    protected void computeNonDeclaredProperties(@NotNull Name name, @NotNull Collection<PropertyDescriptor> result) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(result, "result");
        if (this.jClass.isAnnotationType()) {
            computeAnnotationProperties(name, result);
        }
        Set<PropertyDescriptor> propertiesFromSupertypes = getPropertiesFromSupertypes(name);
        if (propertiesFromSupertypes.isEmpty()) {
            return;
        }
        SmartSet propertiesOverridesFromSuperTypes = SmartSet.Companion.create();
        addPropertyOverrideByMethod(propertiesFromSupertypes, result, new Function1<Name, Collection<? extends SimpleFunctionDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaClassMemberScope.computeNonDeclaredProperties.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final Collection<SimpleFunctionDescriptor> invoke(@NotNull Name it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return LazyJavaClassMemberScope.this.searchMethodsByNameWithoutBuiltinMagic(it);
            }
        });
        addPropertyOverrideByMethod(propertiesFromSupertypes, propertiesOverridesFromSuperTypes, new Function1<Name, Collection<? extends SimpleFunctionDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaClassMemberScope.computeNonDeclaredProperties.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final Collection<SimpleFunctionDescriptor> invoke(@NotNull Name it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                return LazyJavaClassMemberScope.this.searchMethodsInSupertypesWithoutBuiltinMagic(it);
            }
        });
        Collection<? extends PropertyDescriptor> collectionResolveOverridesForNonStaticMembers = DescriptorResolverUtils.resolveOverridesForNonStaticMembers(name, SetsKt.plus((Set) propertiesFromSupertypes, (Iterable) propertiesOverridesFromSuperTypes), result, getOwnerDescriptor(), getC().getComponents().getErrorReporter());
        Intrinsics.checkExpressionValueIsNotNull(collectionResolveOverridesForNonStaticMembers, "resolveOverridesForNonSt…rorReporter\n            )");
        result.addAll(collectionResolveOverridesForNonStaticMembers);
    }

    private final void addPropertyOverrideByMethod(Set<? extends PropertyDescriptor> set, Collection<PropertyDescriptor> collection, Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>> function1) {
        for (PropertyDescriptor property : set) {
            JavaPropertyDescriptor newProperty = createPropertyDescriptorByMethods(property, function1);
            if (newProperty != null) {
                collection.add(newProperty);
                return;
            }
        }
    }

    private final void computeAnnotationProperties(Name name, Collection<PropertyDescriptor> collection) {
        JavaMethod method = (JavaMethod) CollectionsKt.singleOrNull(getDeclaredMemberIndex().invoke().findMethodsByName(name));
        if (method == null) {
            return;
        }
        collection.add(createPropertyDescriptorWithDefaultGetter$default(this, method, null, Modality.FINAL, 2, null));
    }

    static /* synthetic */ JavaPropertyDescriptor createPropertyDescriptorWithDefaultGetter$default(LazyJavaClassMemberScope lazyJavaClassMemberScope, JavaMethod javaMethod, KotlinType kotlinType, Modality modality, int i, Object obj) {
        if ((i & 2) != 0) {
            kotlinType = (KotlinType) null;
        }
        return lazyJavaClassMemberScope.createPropertyDescriptorWithDefaultGetter(javaMethod, kotlinType, modality);
    }

    private final JavaPropertyDescriptor createPropertyDescriptorWithDefaultGetter(JavaMethod method, KotlinType givenType, Modality modality) {
        Annotations annotations = LazyJavaAnnotationsKt.resolveAnnotations(getC(), method);
        JavaPropertyDescriptor propertyDescriptor = JavaPropertyDescriptor.create(getOwnerDescriptor(), annotations, modality, method.getVisibility(), false, method.getName(), getC().getComponents().getSourceElementFactory().source(method), false);
        Intrinsics.checkExpressionValueIsNotNull(propertyDescriptor, "JavaPropertyDescriptor.c…inal = */ false\n        )");
        PropertyGetterDescriptorImpl getter = DescriptorFactory.createDefaultGetter(propertyDescriptor, Annotations.Companion.getEMPTY());
        Intrinsics.checkExpressionValueIsNotNull(getter, "DescriptorFactory.create…iptor, Annotations.EMPTY)");
        propertyDescriptor.initialize(getter, null);
        KotlinType returnType = givenType != null ? givenType : computeMethodReturnType(method, ContextKt.childForMethod$default(getC(), propertyDescriptor, method, 0, 4, null));
        propertyDescriptor.setType(returnType, CollectionsKt.emptyList(), mo378getDispatchReceiverParameter(), null);
        getter.initialize(returnType);
        return propertyDescriptor;
    }

    private final JavaPropertyDescriptor createPropertyDescriptorByMethods(PropertyDescriptor overriddenProperty, Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>> function1) {
        SimpleFunctionDescriptor setterMethod;
        boolean z;
        Object obj = null;
        if (!doesClassOverridesProperty(overriddenProperty, function1)) {
            return null;
        }
        SimpleFunctionDescriptor getterMethod = findGetterOverride(overriddenProperty, function1);
        if (getterMethod == null) {
            Intrinsics.throwNpe();
        }
        if (overriddenProperty.isVar()) {
            setterMethod = findSetterOverride(overriddenProperty, function1);
            if (setterMethod == null) {
                Intrinsics.throwNpe();
            }
        } else {
            setterMethod = null;
        }
        if (setterMethod != null) {
            SimpleFunctionDescriptor it = setterMethod;
            z = it.getModality() == getterMethod.getModality();
        } else {
            z = true;
        }
        if (_Assertions.ENABLED && !z) {
            StringBuilder sb = new StringBuilder();
            sb.append("Different accessors modalities when creating overrides for ");
            sb.append(overriddenProperty);
            sb.append(" in ");
            sb.append(getOwnerDescriptor());
            sb.append("for getter is ");
            sb.append(getterMethod.getModality());
            sb.append(", but for setter is ");
            sb.append(setterMethod != null ? setterMethod.getModality() : null);
            throw new AssertionError(sb.toString());
        }
        JavaPropertyDescriptor propertyDescriptor = JavaPropertyDescriptor.create(getOwnerDescriptor(), Annotations.Companion.getEMPTY(), getterMethod.getModality(), getterMethod.getVisibility(), setterMethod != null, overriddenProperty.getName(), getterMethod.getSource(), false);
        Intrinsics.checkExpressionValueIsNotNull(propertyDescriptor, "JavaPropertyDescriptor.c…inal = */ false\n        )");
        KotlinType returnType = getterMethod.getReturnType();
        if (returnType == null) {
            Intrinsics.throwNpe();
        }
        propertyDescriptor.setType(returnType, CollectionsKt.emptyList(), mo378getDispatchReceiverParameter(), null);
        PropertyGetterDescriptorImpl getter = DescriptorFactory.createGetter(propertyDescriptor, getterMethod.getAnnotations(), false, false, false, getterMethod.getSource());
        getter.setInitialSignatureDescriptor(getterMethod);
        getter.initialize(propertyDescriptor.getType());
        Intrinsics.checkExpressionValueIsNotNull(getter, "DescriptorFactory.create…escriptor.type)\n        }");
        if (setterMethod != null) {
            List<ValueParameterDescriptor> valueParameters = setterMethod.getValueParameters();
            Intrinsics.checkExpressionValueIsNotNull(valueParameters, "setterMethod.valueParameters");
            ValueParameterDescriptor parameter = (ValueParameterDescriptor) CollectionsKt.firstOrNull((List) valueParameters);
            if (parameter == null) {
                throw new AssertionError("No parameter found for " + setterMethod);
            }
            PropertySetterDescriptorImpl $receiver = DescriptorFactory.createSetter(propertyDescriptor, setterMethod.getAnnotations(), parameter.getAnnotations(), false, false, false, setterMethod.getVisibility(), setterMethod.getSource());
            $receiver.setInitialSignatureDescriptor(setterMethod);
            obj = $receiver;
        }
        propertyDescriptor.initialize(getter, (PropertySetterDescriptor) obj);
        return propertyDescriptor;
    }

    private final Set<PropertyDescriptor> getPropertiesFromSupertypes(Name name) {
        TypeConstructor typeConstructor = getOwnerDescriptor().getTypeConstructor();
        Intrinsics.checkExpressionValueIsNotNull(typeConstructor, "ownerDescriptor.typeConstructor");
        Iterable supertypes = typeConstructor.getSupertypes();
        Intrinsics.checkExpressionValueIsNotNull(supertypes, "ownerDescriptor.typeConstructor.supertypes");
        Iterable $receiver$iv = supertypes;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv : $receiver$iv) {
            KotlinType it = (KotlinType) element$iv$iv;
            Iterable $receiver$iv2 = it.getMemberScope().getContributedVariables(name, NoLookupLocation.WHEN_GET_SUPER_MEMBERS);
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv2, 10));
            for (Object item$iv$iv : $receiver$iv2) {
                Iterable $receiver$iv3 = $receiver$iv;
                PropertyDescriptor p = (PropertyDescriptor) item$iv$iv;
                destination$iv$iv2.add(p);
                $receiver$iv = $receiver$iv3;
            }
            Iterable $receiver$iv4 = $receiver$iv;
            Iterable list$iv$iv = (List) destination$iv$iv2;
            CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
            $receiver$iv = $receiver$iv4;
        }
        return CollectionsKt.toSet((List) destination$iv$iv);
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope
    @NotNull
    protected LazyJavaScope.MethodSignatureData resolveMethodSignature(@NotNull JavaMethod method, @NotNull List<? extends TypeParameterDescriptor> methodTypeParameters, @NotNull KotlinType returnType, @NotNull List<? extends ValueParameterDescriptor> valueParameters) {
        Intrinsics.checkParameterIsNotNull(method, "method");
        Intrinsics.checkParameterIsNotNull(methodTypeParameters, "methodTypeParameters");
        Intrinsics.checkParameterIsNotNull(returnType, "returnType");
        Intrinsics.checkParameterIsNotNull(valueParameters, "valueParameters");
        SignaturePropagator.PropagatedSignature propagated = getC().getComponents().getSignaturePropagator().resolvePropagatedSignature(method, getOwnerDescriptor(), returnType, null, valueParameters, methodTypeParameters);
        Intrinsics.checkExpressionValueIsNotNull(propagated, "c.components.signaturePr…dTypeParameters\n        )");
        KotlinType returnType2 = propagated.getReturnType();
        Intrinsics.checkExpressionValueIsNotNull(returnType2, "propagated.returnType");
        KotlinType receiverType = propagated.getReceiverType();
        List<ValueParameterDescriptor> valueParameters2 = propagated.getValueParameters();
        Intrinsics.checkExpressionValueIsNotNull(valueParameters2, "propagated.valueParameters");
        List<TypeParameterDescriptor> typeParameters = propagated.getTypeParameters();
        Intrinsics.checkExpressionValueIsNotNull(typeParameters, "propagated.typeParameters");
        boolean zHasStableParameterNames = propagated.hasStableParameterNames();
        List<String> errors = propagated.getErrors();
        Intrinsics.checkExpressionValueIsNotNull(errors, "propagated.errors");
        return new LazyJavaScope.MethodSignatureData(returnType2, receiverType, valueParameters2, typeParameters, zHasStableParameterNames, errors);
    }

    private final boolean hasSameJvmDescriptorButDoesNotOverride(@NotNull SimpleFunctionDescriptor $receiver, FunctionDescriptor builtinWithErasedParameters) {
        String strComputeJvmDescriptor$default = MethodSignatureMappingKt.computeJvmDescriptor$default($receiver, false, false, 2, null);
        FunctionDescriptor original = builtinWithErasedParameters.getOriginal();
        Intrinsics.checkExpressionValueIsNotNull(original, "builtinWithErasedParameters.original");
        return Intrinsics.areEqual(strComputeJvmDescriptor$default, MethodSignatureMappingKt.computeJvmDescriptor$default(original, false, false, 2, null)) && !doesOverride($receiver, builtinWithErasedParameters);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final JavaClassConstructorDescriptor resolveConstructor(JavaConstructor constructor) {
        ClassDescriptor classDescriptor = getOwnerDescriptor();
        JavaClassConstructorDescriptor constructorDescriptor = JavaClassConstructorDescriptor.createJavaConstructor(classDescriptor, LazyJavaAnnotationsKt.resolveAnnotations(getC(), constructor), false, getC().getComponents().getSourceElementFactory().source(constructor));
        Intrinsics.checkExpressionValueIsNotNull(constructorDescriptor, "JavaClassConstructorDesc…ce(constructor)\n        )");
        LazyJavaResolverContext c = ContextKt.childForMethod(getC(), constructorDescriptor, constructor, classDescriptor.getDeclaredTypeParameters().size());
        LazyJavaScope.ResolvedValueParameters valueParameters = resolveValueParameters(c, constructorDescriptor, constructor.getValueParameters());
        List<TypeParameterDescriptor> declaredTypeParameters = classDescriptor.getDeclaredTypeParameters();
        Intrinsics.checkExpressionValueIsNotNull(declaredTypeParameters, "classDescriptor.declaredTypeParameters");
        List<TypeParameterDescriptor> list = declaredTypeParameters;
        Iterable $receiver$iv = constructor.getTypeParameters();
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
        for (Object item$iv$iv : $receiver$iv) {
            JavaTypeParameter p = (JavaTypeParameter) item$iv$iv;
            TypeParameterDescriptor typeParameterDescriptorResolveTypeParameter = c.getTypeParameterResolver().resolveTypeParameter(p);
            if (typeParameterDescriptorResolveTypeParameter == null) {
                Intrinsics.throwNpe();
            }
            destination$iv$iv.add(typeParameterDescriptorResolveTypeParameter);
        }
        constructorDescriptor.initialize(valueParameters.getDescriptors(), constructor.getVisibility(), CollectionsKt.plus((Collection) list, destination$iv$iv));
        constructorDescriptor.setHasStableParameterNames(false);
        constructorDescriptor.setHasSynthesizedParameterNames(valueParameters.getHasSynthesizedNames());
        constructorDescriptor.setReturnType(classDescriptor.getDefaultType());
        c.getComponents().getJavaResolverCache().recordConstructor(constructor, constructorDescriptor);
        return constructorDescriptor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassConstructorDescriptor createDefaultConstructor() {
        boolean isAnnotation = this.jClass.isAnnotationType();
        if (this.jClass.isInterface() && !isAnnotation) {
            return null;
        }
        ClassDescriptor classDescriptor = getOwnerDescriptor();
        JavaClassConstructorDescriptor constructorDescriptor = JavaClassConstructorDescriptor.createJavaConstructor(classDescriptor, Annotations.Companion.getEMPTY(), true, getC().getComponents().getSourceElementFactory().source(this.jClass));
        Intrinsics.checkExpressionValueIsNotNull(constructorDescriptor, "JavaClassConstructorDesc….source(jClass)\n        )");
        List<ValueParameterDescriptor> listCreateAnnotationConstructorParameters = isAnnotation ? createAnnotationConstructorParameters(constructorDescriptor) : Collections.emptyList();
        constructorDescriptor.setHasSynthesizedParameterNames(false);
        constructorDescriptor.initialize(listCreateAnnotationConstructorParameters, getConstructorVisibility(classDescriptor));
        constructorDescriptor.setHasStableParameterNames(true);
        constructorDescriptor.setReturnType(classDescriptor.getDefaultType());
        getC().getComponents().getJavaResolverCache().recordConstructor(this.jClass, constructorDescriptor);
        return constructorDescriptor;
    }

    private final Visibility getConstructorVisibility(ClassDescriptor classDescriptor) {
        Visibility visibility = classDescriptor.getVisibility();
        Intrinsics.checkExpressionValueIsNotNull(visibility, "classDescriptor.visibility");
        if (Intrinsics.areEqual(visibility, JavaVisibilities.PROTECTED_STATIC_VISIBILITY)) {
            Visibility visibility2 = JavaVisibilities.PROTECTED_AND_PACKAGE;
            Intrinsics.checkExpressionValueIsNotNull(visibility2, "JavaVisibilities.PROTECTED_AND_PACKAGE");
            return visibility2;
        }
        return visibility;
    }

    private final List<ValueParameterDescriptor> createAnnotationConstructorParameters(ClassConstructorDescriptorImpl constructor) {
        Pair pair;
        Collection<JavaMethod> methods = this.jClass.getMethods();
        ArrayList result = new ArrayList(methods.size());
        JavaTypeAttributes attr = JavaTypeResolverKt.toAttributes$default(TypeUsage.COMMON, true, null, 2, null);
        Collection<JavaMethod> $receiver$iv = methods;
        ArrayList first$iv = new ArrayList();
        ArrayList second$iv = new ArrayList();
        for (Object element$iv : $receiver$iv) {
            JavaMethod it = (JavaMethod) element$iv;
            if (Intrinsics.areEqual(it.getName(), JvmAnnotationNames.DEFAULT_ANNOTATION_MEMBER_NAME)) {
                first$iv.add(element$iv);
            } else {
                second$iv.add(element$iv);
            }
        }
        Pair pair2 = new Pair(first$iv, second$iv);
        List methodsNamedValue = (List) pair2.component1();
        List<JavaMethod> otherMethods = (List) pair2.component2();
        int index = 0;
        boolean z = methodsNamedValue.size() <= 1;
        if (_Assertions.ENABLED && !z) {
            throw new AssertionError("There can't be more than one method named 'value' in annotation class: " + this.jClass);
        }
        JavaMethod methodNamedValue = (JavaMethod) CollectionsKt.firstOrNull(methodsNamedValue);
        if (methodNamedValue != null) {
            JavaType parameterNamedValueJavaType = methodNamedValue.getReturnType();
            if (parameterNamedValueJavaType instanceof JavaArrayType) {
                pair = new Pair(getC().getTypeResolver().transformArrayType((JavaArrayType) parameterNamedValueJavaType, attr, true), getC().getTypeResolver().transformJavaType(((JavaArrayType) parameterNamedValueJavaType).getComponentType(), attr));
            } else {
                pair = new Pair(getC().getTypeResolver().transformJavaType(parameterNamedValueJavaType, attr), null);
            }
            KotlinType parameterType = (KotlinType) pair.component1();
            KotlinType varargType = (KotlinType) pair.component2();
            addAnnotationValueParameter(result, constructor, 0, methodNamedValue, parameterType, varargType);
        }
        int startIndex = methodNamedValue != null ? 1 : 0;
        for (JavaMethod method : otherMethods) {
            KotlinType parameterType2 = getC().getTypeResolver().transformJavaType(method.getReturnType(), attr);
            addAnnotationValueParameter(result, constructor, index + startIndex, method, parameterType2, null);
            index++;
        }
        return result;
    }

    private final void addAnnotationValueParameter(@NotNull List<ValueParameterDescriptor> list, ConstructorDescriptor constructor, int index, JavaMethod method, KotlinType returnType, KotlinType varargElementType) {
        KotlinType kotlinType;
        ConstructorDescriptor constructorDescriptor = constructor;
        Annotations empty = Annotations.Companion.getEMPTY();
        Name name = method.getName();
        KotlinType kotlinTypeMakeNotNullable = TypeUtils.makeNotNullable(returnType);
        Intrinsics.checkExpressionValueIsNotNull(kotlinTypeMakeNotNullable, "TypeUtils.makeNotNullable(returnType)");
        boolean z = method.getAnnotationParameterDefaultValue() != null;
        if (varargElementType != null) {
            KotlinType it = TypeUtils.makeNotNullable(varargElementType);
            kotlinType = it;
        } else {
            kotlinType = null;
        }
        list.add(new ValueParameterDescriptorImpl(constructorDescriptor, null, index, empty, name, kotlinTypeMakeNotNullable, z, false, false, kotlinType, getC().getComponents().getSourceElementFactory().source(method)));
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope
    @Nullable
    /* JADX INFO: renamed from: getDispatchReceiverParameter */
    protected ReceiverParameterDescriptor mo378getDispatchReceiverParameter() {
        return DescriptorUtils.getDispatchReceiverParameterIfNeeded(getOwnerDescriptor());
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl, kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    @Nullable
    /* JADX INFO: renamed from: getContributedClassifier */
    public ClassifierDescriptor mo382getContributedClassifier(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(location, "location");
        recordLookup(name, location);
        return this.nestedClasses.invoke(name);
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope, kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl, kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope, kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    @NotNull
    public Collection<SimpleFunctionDescriptor> getContributedFunctions(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(location, "location");
        recordLookup(name, location);
        return super.getContributedFunctions(name, location);
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope, kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl, kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    @NotNull
    public Collection<PropertyDescriptor> getContributedVariables(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(location, "location");
        recordLookup(name, location);
        return super.getContributedVariables(name, location);
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope
    @NotNull
    protected Set<Name> computeClassNames(@NotNull DescriptorKindFilter kindFilter, @Nullable Function1<? super Name, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(kindFilter, "kindFilter");
        return SetsKt.plus((Set) this.nestedClassIndex.invoke(), (Iterable) this.enumEntryIndex.invoke().keySet());
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope
    @NotNull
    protected Set<Name> computePropertyNames(@NotNull DescriptorKindFilter kindFilter, @Nullable Function1<? super Name, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(kindFilter, "kindFilter");
        if (this.jClass.isAnnotationType()) {
            return getFunctionNames();
        }
        Iterable result = new LinkedHashSet(getDeclaredMemberIndex().invoke().getFieldNames());
        TypeConstructor typeConstructor = getOwnerDescriptor().getTypeConstructor();
        Intrinsics.checkExpressionValueIsNotNull(typeConstructor, "ownerDescriptor.typeConstructor");
        Iterable supertypes = typeConstructor.getSupertypes();
        Intrinsics.checkExpressionValueIsNotNull(supertypes, "ownerDescriptor.typeConstructor.supertypes");
        Iterable $receiver$iv = supertypes;
        for (Object element$iv : $receiver$iv) {
            KotlinType supertype = (KotlinType) element$iv;
            Iterable list$iv = supertype.getMemberScope().getVariableNames();
            CollectionsKt.addAll((Collection) result, list$iv);
        }
        Iterable $receiver$iv2 = (Collection) result;
        return (Set) $receiver$iv2;
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl
    public void recordLookup(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(location, "location");
        UtilsKt.record(getC().getComponents().getLookupTracker(), location, getOwnerDescriptor(), name);
    }

    @Override // kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope
    @NotNull
    public String toString() {
        return "Lazy Java member scope for " + this.jClass.getFqName();
    }
}
