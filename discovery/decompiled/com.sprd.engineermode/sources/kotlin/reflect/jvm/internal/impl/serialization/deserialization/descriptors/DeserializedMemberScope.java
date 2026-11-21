package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.Parser;
import kotlin.reflect.jvm.internal.impl.resolve.MemberComparator;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationContext;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.MemberDeserializer;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.NameResolverUtilKt;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNotNull;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNullable;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: DeserializedMemberScope.kt */
/* JADX INFO: loaded from: classes4.dex */
public abstract class DeserializedMemberScope extends MemberScopeImpl {
    static final /* synthetic */ KProperty[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(DeserializedMemberScope.class), "functionNamesLazy", "getFunctionNamesLazy()Ljava/util/Set;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(DeserializedMemberScope.class), "variableNamesLazy", "getVariableNamesLazy()Ljava/util/Set;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(DeserializedMemberScope.class), "classNames", "getClassNames$deserialization()Ljava/util/Set;"))};

    @NotNull
    private final DeserializationContext c;

    @NotNull
    private final NotNullLazyValue classNames$delegate;
    private final NotNullLazyValue functionNamesLazy$delegate;
    private final Map<Name, byte[]> functionProtosBytes;
    private final MemoizedFunctionToNotNull<Name, Collection<SimpleFunctionDescriptor>> functions;
    private final MemoizedFunctionToNotNull<Name, Collection<PropertyDescriptor>> properties;
    private final Map<Name, byte[]> propertyProtosBytes;
    private final MemoizedFunctionToNullable<Name, TypeAliasDescriptor> typeAliasByName;
    private final Map<Name, byte[]> typeAliasBytes;
    private final NotNullLazyValue variableNamesLazy$delegate;

    private final Set<Name> getFunctionNamesLazy() {
        return (Set) StorageKt.getValue(this.functionNamesLazy$delegate, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    private final Set<Name> getVariableNamesLazy() {
        return (Set) StorageKt.getValue(this.variableNamesLazy$delegate, this, (KProperty<?>) $$delegatedProperties[1]);
    }

    protected abstract void addEnumEntryDescriptors(@NotNull Collection<DeclarationDescriptor> collection, @NotNull Function1<? super Name, Boolean> function1);

    @NotNull
    protected abstract ClassId createClassId(@NotNull Name name);

    @NotNull
    public final Set<Name> getClassNames$deserialization() {
        return (Set) StorageKt.getValue(this.classNames$delegate, this, (KProperty<?>) $$delegatedProperties[2]);
    }

    @NotNull
    protected abstract Set<Name> getNonDeclaredFunctionNames();

    @NotNull
    protected abstract Set<Name> getNonDeclaredVariableNames();

    protected DeserializedMemberScope(@NotNull DeserializationContext c, @NotNull Collection<ProtoBuf.Function> functionList, @NotNull Collection<ProtoBuf.Property> propertyList, @NotNull Collection<ProtoBuf.TypeAlias> typeAliasList, @NotNull final Function0<? extends Collection<Name>> classNames) throws IOException {
        Map<Name, byte[]> mapEmptyMap;
        Object answer$iv$iv$iv$iv;
        Object answer$iv$iv$iv$iv2;
        Object answer$iv$iv$iv$iv3;
        Intrinsics.checkParameterIsNotNull(c, "c");
        Intrinsics.checkParameterIsNotNull(functionList, "functionList");
        Intrinsics.checkParameterIsNotNull(propertyList, "propertyList");
        Intrinsics.checkParameterIsNotNull(typeAliasList, "typeAliasList");
        Intrinsics.checkParameterIsNotNull(classNames, "classNames");
        this.c = c;
        DeserializedMemberScope this_$iv = this;
        Collection<ProtoBuf.Function> $receiver$iv$iv = functionList;
        Map destination$iv$iv$iv = new LinkedHashMap();
        for (Object element$iv$iv$iv : $receiver$iv$iv) {
            MessageLite it$iv = (MessageLite) element$iv$iv$iv;
            DeserializedMemberScope this_$iv2 = this_$iv;
            ProtoBuf.Function it = (ProtoBuf.Function) it$iv;
            Name name = NameResolverUtilKt.getName(this_$iv.c.getNameResolver(), it.getName());
            Object value$iv$iv$iv$iv = destination$iv$iv$iv.get(name);
            if (value$iv$iv$iv$iv == null) {
                Object answer$iv$iv$iv$iv4 = new ArrayList();
                answer$iv$iv$iv$iv3 = answer$iv$iv$iv$iv4;
                destination$iv$iv$iv.put(name, answer$iv$iv$iv$iv3);
            } else {
                answer$iv$iv$iv$iv3 = value$iv$iv$iv$iv;
            }
            List list$iv$iv$iv = (List) answer$iv$iv$iv$iv3;
            list$iv$iv$iv.add(element$iv$iv$iv);
            this_$iv = this_$iv2;
        }
        this.functionProtosBytes = packToByteArray(destination$iv$iv$iv);
        DeserializedMemberScope this_$iv3 = this;
        Collection $receiver$iv = propertyList;
        Collection $receiver$iv$iv2 = $receiver$iv;
        Map destination$iv$iv$iv2 = new LinkedHashMap();
        for (Object element$iv$iv$iv2 : $receiver$iv$iv2) {
            MessageLite it$iv2 = (MessageLite) element$iv$iv$iv2;
            ProtoBuf.Property it2 = (ProtoBuf.Property) it$iv2;
            DeserializedMemberScope this_$iv4 = this_$iv3;
            Name name2 = NameResolverUtilKt.getName(this_$iv3.c.getNameResolver(), it2.getName());
            Object value$iv$iv$iv$iv2 = destination$iv$iv$iv2.get(name2);
            if (value$iv$iv$iv$iv2 == null) {
                Object answer$iv$iv$iv$iv5 = new ArrayList();
                answer$iv$iv$iv$iv2 = answer$iv$iv$iv$iv5;
                destination$iv$iv$iv2.put(name2, answer$iv$iv$iv$iv2);
            } else {
                answer$iv$iv$iv$iv2 = value$iv$iv$iv$iv2;
            }
            List list$iv$iv$iv2 = (List) answer$iv$iv$iv$iv2;
            list$iv$iv$iv2.add(element$iv$iv$iv2);
            $receiver$iv = $receiver$iv;
            this_$iv3 = this_$iv4;
        }
        this.propertyProtosBytes = packToByteArray(destination$iv$iv$iv2);
        if (this.c.getComponents().getConfiguration().getTypeAliasesAllowed()) {
            DeserializedMemberScope this_$iv5 = this;
            Collection $receiver$iv2 = typeAliasList;
            Collection $receiver$iv$iv3 = $receiver$iv2;
            Map destination$iv$iv$iv3 = new LinkedHashMap();
            for (Object element$iv$iv$iv3 : $receiver$iv$iv3) {
                MessageLite it$iv3 = (MessageLite) element$iv$iv$iv3;
                ProtoBuf.TypeAlias it3 = (ProtoBuf.TypeAlias) it$iv3;
                DeserializedMemberScope this_$iv6 = this_$iv5;
                Name name3 = NameResolverUtilKt.getName(this_$iv5.c.getNameResolver(), it3.getName());
                Object value$iv$iv$iv$iv3 = destination$iv$iv$iv3.get(name3);
                if (value$iv$iv$iv$iv3 == null) {
                    Object answer$iv$iv$iv$iv6 = new ArrayList();
                    answer$iv$iv$iv$iv = answer$iv$iv$iv$iv6;
                    destination$iv$iv$iv3.put(name3, answer$iv$iv$iv$iv);
                } else {
                    answer$iv$iv$iv$iv = value$iv$iv$iv$iv3;
                }
                List list$iv$iv$iv3 = (List) answer$iv$iv$iv$iv;
                list$iv$iv$iv3.add(element$iv$iv$iv3);
                $receiver$iv2 = $receiver$iv2;
                this_$iv5 = this_$iv6;
            }
            mapEmptyMap = packToByteArray(destination$iv$iv$iv3);
        } else {
            mapEmptyMap = MapsKt.emptyMap();
        }
        this.typeAliasBytes = mapEmptyMap;
        this.functions = this.c.getStorageManager().createMemoizedFunction(new Function1<Name, Collection<? extends SimpleFunctionDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope$functions$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final Collection<SimpleFunctionDescriptor> invoke(@NotNull Name it4) {
                Intrinsics.checkParameterIsNotNull(it4, "it");
                return this.this$0.computeFunctions(it4);
            }
        });
        this.properties = this.c.getStorageManager().createMemoizedFunction(new Function1<Name, Collection<? extends PropertyDescriptor>>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope$properties$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final Collection<PropertyDescriptor> invoke(@NotNull Name it4) {
                Intrinsics.checkParameterIsNotNull(it4, "it");
                return this.this$0.computeProperties(it4);
            }
        });
        this.typeAliasByName = this.c.getStorageManager().createMemoizedFunctionWithNullableValues(new Function1<Name, TypeAliasDescriptor>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope$typeAliasByName$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @Nullable
            public final TypeAliasDescriptor invoke(@NotNull Name it4) {
                Intrinsics.checkParameterIsNotNull(it4, "it");
                return this.this$0.createTypeAlias(it4);
            }
        });
        this.functionNamesLazy$delegate = this.c.getStorageManager().createLazyValue(new Function0<Set<? extends Name>>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope$functionNamesLazy$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Set<? extends Name> invoke() {
                return SetsKt.plus(this.this$0.functionProtosBytes.keySet(), (Iterable) this.this$0.getNonDeclaredFunctionNames());
            }
        });
        this.variableNamesLazy$delegate = this.c.getStorageManager().createLazyValue(new Function0<Set<? extends Name>>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope$variableNamesLazy$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Set<? extends Name> invoke() {
                return SetsKt.plus(this.this$0.propertyProtosBytes.keySet(), (Iterable) this.this$0.getNonDeclaredVariableNames());
            }
        });
        this.classNames$delegate = this.c.getStorageManager().createLazyValue(new Function0<Set<? extends Name>>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope$classNames$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Set<? extends Name> invoke() {
                return CollectionsKt.toSet((Iterable) classNames.invoke());
            }
        });
    }

    @NotNull
    protected final DeserializationContext getC() {
        return this.c;
    }

    private final Map<Name, byte[]> packToByteArray(@NotNull Map<Name, ? extends Collection<? extends AbstractMessageLite>> map) throws IOException {
        Map<Name, ? extends Collection<? extends AbstractMessageLite>> map2 = map;
        boolean z = false;
        int $i$f$mapValues = 0;
        Map destination$iv$iv = new LinkedHashMap(MapsKt.mapCapacity(map2.size()));
        Iterable $receiver$iv$iv$iv = map2.entrySet();
        for (Object element$iv$iv$iv : $receiver$iv$iv$iv) {
            Map.Entry it$iv$iv = (Map.Entry) element$iv$iv$iv;
            Object key = it$iv$iv.getKey();
            Map.Entry entry = (Map.Entry) element$iv$iv$iv;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Map<Name, ? extends Collection<? extends AbstractMessageLite>> map3 = map2;
            Iterable $receiver$iv = (Iterable) entry.getValue();
            boolean z2 = z;
            int $i$f$mapValues2 = $i$f$mapValues;
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv, 10));
            Iterable $receiver$iv$iv = $receiver$iv;
            for (Object item$iv$iv : $receiver$iv$iv) {
                Iterable $receiver$iv2 = $receiver$iv;
                AbstractMessageLite proto = (AbstractMessageLite) item$iv$iv;
                proto.writeDelimitedTo(byteArrayOutputStream);
                destination$iv$iv2.add(Unit.INSTANCE);
                $receiver$iv = $receiver$iv2;
                $receiver$iv$iv = $receiver$iv$iv;
            }
            destination$iv$iv.put(key, byteArrayOutputStream.toByteArray());
            z = z2;
            map2 = map3;
            $i$f$mapValues = $i$f$mapValues2;
        }
        return destination$iv$iv;
    }

    private final Set<Name> getTypeAliasNames() {
        return this.typeAliasBytes.keySet();
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl, kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    @NotNull
    public Set<Name> getFunctionNames() {
        return getFunctionNamesLazy();
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl, kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    @NotNull
    public Set<Name> getVariableNames() {
        return getVariableNamesLazy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:7:0x0037  */
    public final Collection<SimpleFunctionDescriptor> computeFunctions(Name name) {
        Collection protos$iv$iv;
        Map<Name, byte[]> map = this.functionProtosBytes;
        final Parser<ProtoBuf.Function> parser = ProtoBuf.Function.PARSER;
        Intrinsics.checkExpressionValueIsNotNull(parser, "ProtoBuf.Function.PARSER");
        byte[] it$iv = map.get(name);
        if (it$iv != null) {
            final ByteArrayInputStream inputStream$iv = new ByteArrayInputStream(it$iv);
            Collection list = SequencesKt.toList(SequencesKt.generateSequence(new Function0<M>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope$computeDescriptors$$inlined$let$lambda$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                /* JADX WARN: Incorrect return type in method signature: ()TM; */
                @Override // kotlin.jvm.functions.Function0
                public final MessageLite invoke() {
                    return (MessageLite) parser.parseDelimitedFrom(inputStream$iv, this.getC().getComponents().getExtensionRegistryLite());
                }
            }));
            if (list != null) {
                protos$iv$iv = list;
            } else {
                protos$iv$iv = CollectionsKt.emptyList();
            }
        } else {
            protos$iv$iv = CollectionsKt.emptyList();
        }
        Collection $receiver$iv$iv$iv = protos$iv$iv;
        Collection destination$iv$iv$iv = new ArrayList();
        for (Object item$iv$iv$iv : $receiver$iv$iv$iv) {
            ProtoBuf.Function it = (ProtoBuf.Function) item$iv$iv$iv;
            Map<Name, byte[]> map2 = map;
            MemberDeserializer memberDeserializer = this.c.getMemberDeserializer();
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            destination$iv$iv$iv.add(memberDeserializer.loadFunction(it));
            map = map2;
            parser = parser;
        }
        ArrayList descriptors$iv$iv = (ArrayList) destination$iv$iv$iv;
        ArrayList it2 = descriptors$iv$iv;
        computeNonDeclaredFunctions(name, it2);
        Collection it3 = kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.compact(descriptors$iv$iv);
        return it3;
    }

    protected void computeNonDeclaredFunctions(@NotNull Name name, @NotNull Collection<SimpleFunctionDescriptor> functions) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(functions, "functions");
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl, kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope, kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    @NotNull
    public Collection<SimpleFunctionDescriptor> getContributedFunctions(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(location, "location");
        return !getFunctionNames().contains(name) ? CollectionsKt.emptyList() : this.functions.invoke(name);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:7:0x0037  */
    public final Collection<PropertyDescriptor> computeProperties(Name name) {
        Collection protos$iv$iv;
        Map<Name, byte[]> map = this.propertyProtosBytes;
        final Parser<ProtoBuf.Property> parser = ProtoBuf.Property.PARSER;
        Intrinsics.checkExpressionValueIsNotNull(parser, "ProtoBuf.Property.PARSER");
        byte[] it$iv = map.get(name);
        if (it$iv != null) {
            final ByteArrayInputStream inputStream$iv = new ByteArrayInputStream(it$iv);
            Collection list = SequencesKt.toList(SequencesKt.generateSequence(new Function0<M>() { // from class: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope$computeDescriptors$$inlined$let$lambda$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                /* JADX WARN: Incorrect return type in method signature: ()TM; */
                @Override // kotlin.jvm.functions.Function0
                public final MessageLite invoke() {
                    return (MessageLite) parser.parseDelimitedFrom(inputStream$iv, this.getC().getComponents().getExtensionRegistryLite());
                }
            }));
            if (list != null) {
                protos$iv$iv = list;
            } else {
                protos$iv$iv = CollectionsKt.emptyList();
            }
        } else {
            protos$iv$iv = CollectionsKt.emptyList();
        }
        Collection $receiver$iv$iv$iv = protos$iv$iv;
        Collection destination$iv$iv$iv = new ArrayList();
        for (Object item$iv$iv$iv : $receiver$iv$iv$iv) {
            ProtoBuf.Property it = (ProtoBuf.Property) item$iv$iv$iv;
            Map<Name, byte[]> map2 = map;
            MemberDeserializer memberDeserializer = this.c.getMemberDeserializer();
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            destination$iv$iv$iv.add(memberDeserializer.loadProperty(it));
            map = map2;
            parser = parser;
        }
        ArrayList descriptors$iv$iv = (ArrayList) destination$iv$iv$iv;
        ArrayList it2 = descriptors$iv$iv;
        computeNonDeclaredProperties(name, it2);
        Collection it3 = kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.compact(descriptors$iv$iv);
        return it3;
    }

    protected void computeNonDeclaredProperties(@NotNull Name name, @NotNull Collection<PropertyDescriptor> descriptors) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(descriptors, "descriptors");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final TypeAliasDescriptor createTypeAlias(Name name) {
        ProtoBuf.TypeAlias proto;
        byte[] byteArray = this.typeAliasBytes.get(name);
        if (byteArray == null || (proto = ProtoBuf.TypeAlias.parseDelimitedFrom(new ByteArrayInputStream(byteArray), this.c.getComponents().getExtensionRegistryLite())) == null) {
            return null;
        }
        return this.c.getMemberDeserializer().loadTypeAlias(proto);
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl, kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope
    @NotNull
    public Collection<PropertyDescriptor> getContributedVariables(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(location, "location");
        return !getVariableNames().contains(name) ? CollectionsKt.emptyList() : this.properties.invoke(name);
    }

    @NotNull
    protected final Collection<DeclarationDescriptor> computeDescriptors(@NotNull DescriptorKindFilter kindFilter, @NotNull Function1<? super Name, Boolean> nameFilter, @NotNull LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(kindFilter, "kindFilter");
        Intrinsics.checkParameterIsNotNull(nameFilter, "nameFilter");
        Intrinsics.checkParameterIsNotNull(location, "location");
        ArrayList result = new ArrayList(0);
        if (kindFilter.acceptsKinds(DescriptorKindFilter.Companion.getSINGLETON_CLASSIFIERS_MASK())) {
            addEnumEntryDescriptors(result, nameFilter);
        }
        addFunctionsAndProperties(result, kindFilter, nameFilter, location);
        if (kindFilter.acceptsKinds(DescriptorKindFilter.Companion.getCLASSIFIERS_MASK())) {
            for (Name className : getClassNames$deserialization()) {
                if (nameFilter.invoke(className).booleanValue()) {
                    kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.addIfNotNull(result, deserializeClass(className));
                }
            }
        }
        if (kindFilter.acceptsKinds(DescriptorKindFilter.Companion.getTYPE_ALIASES_MASK())) {
            for (Name typeAliasName : getTypeAliasNames()) {
                if (nameFilter.invoke(typeAliasName).booleanValue()) {
                    kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.addIfNotNull(result, this.typeAliasByName.invoke(typeAliasName));
                }
            }
        }
        return kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.compact(result);
    }

    private final void addFunctionsAndProperties(Collection<DeclarationDescriptor> collection, DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> function1, LookupLocation location) {
        if (kindFilter.acceptsKinds(DescriptorKindFilter.Companion.getVARIABLES_MASK())) {
            Collection names$iv = getVariableNames();
            ArrayList subResult$iv = new ArrayList();
            for (Name name$iv : names$iv) {
                if (function1.invoke(name$iv).booleanValue()) {
                    subResult$iv.addAll(getContributedVariables(name$iv, location));
                }
            }
            MemberComparator.NameAndTypeMemberComparator nameAndTypeMemberComparator = MemberComparator.NameAndTypeMemberComparator.INSTANCE;
            Intrinsics.checkExpressionValueIsNotNull(nameAndTypeMemberComparator, "MemberComparator.NameAnd…MemberComparator.INSTANCE");
            CollectionsKt.sortWith(subResult$iv, nameAndTypeMemberComparator);
            collection.addAll(subResult$iv);
        }
        if (kindFilter.acceptsKinds(DescriptorKindFilter.Companion.getFUNCTIONS_MASK())) {
            Collection names$iv2 = getFunctionNames();
            ArrayList subResult$iv2 = new ArrayList();
            for (Name name$iv2 : names$iv2) {
                if (function1.invoke(name$iv2).booleanValue()) {
                    subResult$iv2.addAll(getContributedFunctions(name$iv2, location));
                }
            }
            MemberComparator.NameAndTypeMemberComparator nameAndTypeMemberComparator2 = MemberComparator.NameAndTypeMemberComparator.INSTANCE;
            Intrinsics.checkExpressionValueIsNotNull(nameAndTypeMemberComparator2, "MemberComparator.NameAnd…MemberComparator.INSTANCE");
            CollectionsKt.sortWith(subResult$iv2, nameAndTypeMemberComparator2);
            collection.addAll(subResult$iv2);
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl, kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope
    @Nullable
    /* JADX INFO: renamed from: getContributedClassifier */
    public ClassifierDescriptor mo382getContributedClassifier(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        Intrinsics.checkParameterIsNotNull(location, "location");
        if (hasClass(name)) {
            return deserializeClass(name);
        }
        if (getTypeAliasNames().contains(name)) {
            return this.typeAliasByName.invoke(name);
        }
        return null;
    }

    private final ClassDescriptor deserializeClass(Name name) {
        return this.c.getComponents().deserializeClass(createClassId(name));
    }

    protected boolean hasClass(@NotNull Name name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return getClassNames$deserialization().contains(name);
    }
}
