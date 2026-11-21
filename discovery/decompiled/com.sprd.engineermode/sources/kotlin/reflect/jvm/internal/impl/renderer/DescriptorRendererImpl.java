package kotlin.reflect.jvm.internal.impl.renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorWithSource;
import kotlin.reflect.jvm.internal.impl.descriptors.FieldDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageViewDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PossiblyInnerType;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyAccessorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyGetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertySetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceFile;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterUtilsKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationUseSiteTarget;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.name.SpecialNames;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.constants.AnnotationValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ArrayValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.KClassValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.AbbreviatedType;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.SpecialTypesKt;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.UnresolvedType;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.WrappedType;
import kotlin.text.StringsKt;
import kotlin.text.Typography;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: DescriptorRendererImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class DescriptorRendererImpl extends DescriptorRenderer implements DescriptorRendererOptions {
    static final /* synthetic */ KProperty[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererImpl.class), "functionTypeAnnotationsRenderer", "getFunctionTypeAnnotationsRenderer()Lorg/jetbrains/kotlin/renderer/DescriptorRendererImpl;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererImpl.class), "functionTypeParameterTypesRenderer", "getFunctionTypeParameterTypesRenderer()Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;"))};
    private final Lazy functionTypeAnnotationsRenderer$delegate;
    private final Lazy functionTypeParameterTypesRenderer$delegate;

    @NotNull
    private final DescriptorRendererOptionsImpl options;

    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0 = new int[RenderingFormat.values().length];
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;
        public static final /* synthetic */ int[] $EnumSwitchMapping$3;
        public static final /* synthetic */ int[] $EnumSwitchMapping$4;

        static {
            $EnumSwitchMapping$0[RenderingFormat.PLAIN.ordinal()] = 1;
            $EnumSwitchMapping$0[RenderingFormat.HTML.ordinal()] = 2;
            $EnumSwitchMapping$1 = new int[RenderingFormat.values().length];
            $EnumSwitchMapping$1[RenderingFormat.PLAIN.ordinal()] = 1;
            $EnumSwitchMapping$1[RenderingFormat.HTML.ordinal()] = 2;
            $EnumSwitchMapping$2 = new int[RenderingFormat.values().length];
            $EnumSwitchMapping$2[RenderingFormat.PLAIN.ordinal()] = 1;
            $EnumSwitchMapping$2[RenderingFormat.HTML.ordinal()] = 2;
            $EnumSwitchMapping$3 = new int[RenderingFormat.values().length];
            $EnumSwitchMapping$3[RenderingFormat.PLAIN.ordinal()] = 1;
            $EnumSwitchMapping$3[RenderingFormat.HTML.ordinal()] = 2;
            $EnumSwitchMapping$4 = new int[ParameterNameRenderingPolicy.values().length];
            $EnumSwitchMapping$4[ParameterNameRenderingPolicy.ALL.ordinal()] = 1;
            $EnumSwitchMapping$4[ParameterNameRenderingPolicy.ONLY_NON_SYNTHESIZED.ordinal()] = 2;
            $EnumSwitchMapping$4[ParameterNameRenderingPolicy.NONE.ordinal()] = 3;
        }
    }

    private final DescriptorRendererImpl getFunctionTypeAnnotationsRenderer() {
        Lazy lazy = this.functionTypeAnnotationsRenderer$delegate;
        KProperty kProperty = $$delegatedProperties[0];
        return (DescriptorRendererImpl) lazy.getValue();
    }

    private final DescriptorRenderer getFunctionTypeParameterTypesRenderer() {
        Lazy lazy = this.functionTypeParameterTypesRenderer$delegate;
        KProperty kProperty = $$delegatedProperties[1];
        return (DescriptorRenderer) lazy.getValue();
    }

    public boolean getActualPropertiesInPrimaryConstructor() {
        return this.options.getActualPropertiesInPrimaryConstructor();
    }

    public boolean getAlwaysRenderModifiers() {
        return this.options.getAlwaysRenderModifiers();
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    @NotNull
    public AnnotationArgumentsRenderingPolicy getAnnotationArgumentsRenderingPolicy() {
        return this.options.getAnnotationArgumentsRenderingPolicy();
    }

    @Nullable
    public Function1<AnnotationDescriptor, Boolean> getAnnotationFilter() {
        return this.options.getAnnotationFilter();
    }

    public boolean getBoldOnlyForNamesInHtml() {
        return this.options.getBoldOnlyForNamesInHtml();
    }

    public boolean getClassWithPrimaryConstructor() {
        return this.options.getClassWithPrimaryConstructor();
    }

    @NotNull
    public ClassifierNamePolicy getClassifierNamePolicy() {
        return this.options.getClassifierNamePolicy();
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public boolean getDebugMode() {
        return this.options.getDebugMode();
    }

    @Nullable
    public Function1<ValueParameterDescriptor, String> getDefaultParameterValueRenderer() {
        return this.options.getDefaultParameterValueRenderer();
    }

    public boolean getEachAnnotationOnNewLine() {
        return this.options.getEachAnnotationOnNewLine();
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public boolean getEnhancedTypes() {
        return this.options.getEnhancedTypes();
    }

    @NotNull
    public Set<FqName> getExcludedAnnotationClasses() {
        return this.options.getExcludedAnnotationClasses();
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    @NotNull
    public Set<FqName> getExcludedTypeAnnotationClasses() {
        return this.options.getExcludedTypeAnnotationClasses();
    }

    public boolean getIncludeAdditionalModifiers() {
        return this.options.getIncludeAdditionalModifiers();
    }

    public boolean getIncludeAnnotationArguments() {
        return this.options.getIncludeAnnotationArguments();
    }

    public boolean getIncludeEmptyAnnotationArguments() {
        return this.options.getIncludeEmptyAnnotationArguments();
    }

    public boolean getIncludePropertyConstant() {
        return this.options.getIncludePropertyConstant();
    }

    @NotNull
    public Set<DescriptorRendererModifier> getModifiers() {
        return this.options.getModifiers();
    }

    public boolean getNormalizedVisibilities() {
        return this.options.getNormalizedVisibilities();
    }

    @NotNull
    public OverrideRenderingPolicy getOverrideRenderingPolicy() {
        return this.options.getOverrideRenderingPolicy();
    }

    @NotNull
    public ParameterNameRenderingPolicy getParameterNameRenderingPolicy() {
        return this.options.getParameterNameRenderingPolicy();
    }

    public boolean getParameterNamesInFunctionalTypes() {
        return this.options.getParameterNamesInFunctionalTypes();
    }

    public boolean getPresentableUnresolvedTypes() {
        return this.options.getPresentableUnresolvedTypes();
    }

    @NotNull
    public PropertyAccessorRenderingPolicy getPropertyAccessorRenderingPolicy() {
        return this.options.getPropertyAccessorRenderingPolicy();
    }

    public boolean getReceiverAfterName() {
        return this.options.getReceiverAfterName();
    }

    public boolean getRenderCompanionObjectName() {
        return this.options.getRenderCompanionObjectName();
    }

    public boolean getRenderConstructorDelegation() {
        return this.options.getRenderConstructorDelegation();
    }

    public boolean getRenderConstructorKeyword() {
        return this.options.getRenderConstructorKeyword();
    }

    public boolean getRenderDefaultAnnotationArguments() {
        return this.options.getRenderDefaultAnnotationArguments();
    }

    public boolean getRenderDefaultModality() {
        return this.options.getRenderDefaultModality();
    }

    public boolean getRenderDefaultVisibility() {
        return this.options.getRenderDefaultVisibility();
    }

    public boolean getRenderPrimaryConstructorParametersAsProperties() {
        return this.options.getRenderPrimaryConstructorParametersAsProperties();
    }

    public boolean getRenderTypeExpansions() {
        return this.options.getRenderTypeExpansions();
    }

    public boolean getRenderUnabbreviatedType() {
        return this.options.getRenderUnabbreviatedType();
    }

    public boolean getSecondaryConstructorsAsPrimary() {
        return this.options.getSecondaryConstructorsAsPrimary();
    }

    public boolean getStartFromDeclarationKeyword() {
        return this.options.getStartFromDeclarationKeyword();
    }

    public boolean getStartFromName() {
        return this.options.getStartFromName();
    }

    @NotNull
    public RenderingFormat getTextFormat() {
        return this.options.getTextFormat();
    }

    @NotNull
    public Function1<KotlinType, KotlinType> getTypeNormalizer() {
        return this.options.getTypeNormalizer();
    }

    public boolean getUninferredTypeParameterAsName() {
        return this.options.getUninferredTypeParameterAsName();
    }

    public boolean getUnitReturnType() {
        return this.options.getUnitReturnType();
    }

    @NotNull
    public DescriptorRenderer.ValueParametersHandler getValueParametersHandler() {
        return this.options.getValueParametersHandler();
    }

    public boolean getVerbose() {
        return this.options.getVerbose();
    }

    public boolean getWithDefinedIn() {
        return this.options.getWithDefinedIn();
    }

    public boolean getWithSourceFileForTopLevel() {
        return this.options.getWithSourceFileForTopLevel();
    }

    public boolean getWithoutReturnType() {
        return this.options.getWithoutReturnType();
    }

    public boolean getWithoutSuperTypes() {
        return this.options.getWithoutSuperTypes();
    }

    public boolean getWithoutTypeParameters() {
        return this.options.getWithoutTypeParameters();
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setAnnotationArgumentsRenderingPolicy(@NotNull AnnotationArgumentsRenderingPolicy annotationArgumentsRenderingPolicy) {
        Intrinsics.checkParameterIsNotNull(annotationArgumentsRenderingPolicy, "<set-?>");
        this.options.setAnnotationArgumentsRenderingPolicy(annotationArgumentsRenderingPolicy);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setClassifierNamePolicy(@NotNull ClassifierNamePolicy classifierNamePolicy) {
        Intrinsics.checkParameterIsNotNull(classifierNamePolicy, "<set-?>");
        this.options.setClassifierNamePolicy(classifierNamePolicy);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setDebugMode(boolean z) {
        this.options.setDebugMode(z);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setExcludedTypeAnnotationClasses(@NotNull Set<FqName> set) {
        Intrinsics.checkParameterIsNotNull(set, "<set-?>");
        this.options.setExcludedTypeAnnotationClasses(set);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setModifiers(@NotNull Set<? extends DescriptorRendererModifier> set) {
        Intrinsics.checkParameterIsNotNull(set, "<set-?>");
        this.options.setModifiers(set);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setParameterNameRenderingPolicy(@NotNull ParameterNameRenderingPolicy parameterNameRenderingPolicy) {
        Intrinsics.checkParameterIsNotNull(parameterNameRenderingPolicy, "<set-?>");
        this.options.setParameterNameRenderingPolicy(parameterNameRenderingPolicy);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setReceiverAfterName(boolean z) {
        this.options.setReceiverAfterName(z);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setRenderCompanionObjectName(boolean z) {
        this.options.setRenderCompanionObjectName(z);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setStartFromName(boolean z) {
        this.options.setStartFromName(z);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setTextFormat(@NotNull RenderingFormat renderingFormat) {
        Intrinsics.checkParameterIsNotNull(renderingFormat, "<set-?>");
        this.options.setTextFormat(renderingFormat);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setVerbose(boolean z) {
        this.options.setVerbose(z);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setWithDefinedIn(boolean z) {
        this.options.setWithDefinedIn(z);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setWithoutSuperTypes(boolean z) {
        this.options.setWithoutSuperTypes(z);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions
    public void setWithoutTypeParameters(boolean z) {
        this.options.setWithoutTypeParameters(z);
    }

    public DescriptorRendererImpl(@NotNull DescriptorRendererOptionsImpl options) {
        Intrinsics.checkParameterIsNotNull(options, "options");
        this.options = options;
        boolean zIsLocked = this.options.isLocked();
        if (_Assertions.ENABLED && !zIsLocked) {
            throw new AssertionError("Assertion failed");
        }
        this.functionTypeAnnotationsRenderer$delegate = LazyKt.lazy(new Function0<DescriptorRendererImpl>() { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl$functionTypeAnnotationsRenderer$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final DescriptorRendererImpl invoke() {
                DescriptorRenderer descriptorRendererWithOptions = this.this$0.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl$functionTypeAnnotationsRenderer$2.1
                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
                        invoke2(descriptorRendererOptions);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(@NotNull DescriptorRendererOptions receiver$0) {
                        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
                        receiver$0.setExcludedTypeAnnotationClasses(SetsKt.plus((Set) receiver$0.getExcludedTypeAnnotationClasses(), (Iterable) CollectionsKt.listOf(KotlinBuiltIns.FQ_NAMES.extensionFunctionType)));
                        receiver$0.setAnnotationArgumentsRenderingPolicy(AnnotationArgumentsRenderingPolicy.ALWAYS_PARENTHESIZED);
                    }
                });
                if (descriptorRendererWithOptions != null) {
                    return (DescriptorRendererImpl) descriptorRendererWithOptions;
                }
                throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.renderer.DescriptorRendererImpl");
            }
        });
        this.functionTypeParameterTypesRenderer$delegate = LazyKt.lazy(new Function0<DescriptorRenderer>() { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl$functionTypeParameterTypesRenderer$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final DescriptorRenderer invoke() {
                return this.this$0.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl$functionTypeParameterTypesRenderer$2.1
                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
                        invoke2(descriptorRendererOptions);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(@NotNull DescriptorRendererOptions receiver$0) {
                        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
                        receiver$0.setExcludedTypeAnnotationClasses(SetsKt.plus((Set) receiver$0.getExcludedTypeAnnotationClasses(), (Iterable) CollectionsKt.listOf(KotlinBuiltIns.FQ_NAMES.parameterName)));
                    }
                });
            }
        });
    }

    @NotNull
    public final DescriptorRendererOptionsImpl getOptions() {
        return this.options;
    }

    private final String renderKeyword(String keyword) {
        int i = WhenMappings.$EnumSwitchMapping$0[getTextFormat().ordinal()];
        if (i == 1) {
            return keyword;
        }
        if (i != 2) {
            throw new NoWhenBranchMatchedException();
        }
        if (getBoldOnlyForNamesInHtml()) {
            return keyword;
        }
        return "<b>" + keyword + "</b>";
    }

    private final String renderError(String keyword) {
        int i = WhenMappings.$EnumSwitchMapping$1[getTextFormat().ordinal()];
        if (i == 1) {
            return keyword;
        }
        if (i != 2) {
            throw new NoWhenBranchMatchedException();
        }
        return "<font color=red><b>" + keyword + "</b></font>";
    }

    private final String escape(String string) {
        return getTextFormat().escape(string);
    }

    private final String lt() {
        return escape("<");
    }

    private final String gt() {
        return escape(">");
    }

    private final String arrow() {
        int i = WhenMappings.$EnumSwitchMapping$2[getTextFormat().ordinal()];
        if (i == 1) {
            return escape("->");
        }
        if (i == 2) {
            return "&rarr;";
        }
        throw new NoWhenBranchMatchedException();
    }

    @NotNull
    public String renderMessage(@NotNull String message) {
        Intrinsics.checkParameterIsNotNull(message, "message");
        int i = WhenMappings.$EnumSwitchMapping$3[getTextFormat().ordinal()];
        if (i == 1) {
            return message;
        }
        if (i != 2) {
            throw new NoWhenBranchMatchedException();
        }
        return "<i>" + message + "</i>";
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    @NotNull
    public String renderName(@NotNull Name name, boolean rootRenderedElement) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        String escaped = escape(RenderingUtilsKt.render(name));
        if (getBoldOnlyForNamesInHtml() && getTextFormat() == RenderingFormat.HTML && rootRenderedElement) {
            return "<b>" + escaped + "</b>";
        }
        return escaped;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderName(DeclarationDescriptor descriptor, StringBuilder builder, boolean rootRenderedElement) {
        Name name = descriptor.getName();
        Intrinsics.checkExpressionValueIsNotNull(name, "descriptor.name");
        builder.append(renderName(name, rootRenderedElement));
    }

    private final void renderCompanionObjectName(DeclarationDescriptor descriptor, StringBuilder builder) {
        if (getRenderCompanionObjectName()) {
            if (getStartFromName()) {
                builder.append("companion object");
            }
            renderSpaceIfNeeded(builder);
            DeclarationDescriptor containingDeclaration = descriptor.getContainingDeclaration();
            if (containingDeclaration != null) {
                builder.append("of ");
                Name name = containingDeclaration.getName();
                Intrinsics.checkExpressionValueIsNotNull(name, "containingDeclaration.name");
                builder.append(renderName(name, false));
            }
        }
        if (getVerbose() || (!Intrinsics.areEqual(descriptor.getName(), SpecialNames.DEFAULT_NAME_FOR_COMPANION_OBJECT))) {
            if (!getStartFromName()) {
                renderSpaceIfNeeded(builder);
            }
            Name name2 = descriptor.getName();
            Intrinsics.checkExpressionValueIsNotNull(name2, "descriptor.name");
            builder.append(renderName(name2, true));
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    @NotNull
    public String renderFqName(@NotNull FqNameUnsafe fqName) {
        Intrinsics.checkParameterIsNotNull(fqName, "fqName");
        List<Name> listPathSegments = fqName.pathSegments();
        Intrinsics.checkExpressionValueIsNotNull(listPathSegments, "fqName.pathSegments()");
        return renderFqName(listPathSegments);
    }

    private final String renderFqName(List<Name> list) {
        return escape(RenderingUtilsKt.renderFqName(list));
    }

    @NotNull
    public String renderClassifierName(@NotNull ClassifierDescriptor klass) {
        Intrinsics.checkParameterIsNotNull(klass, "klass");
        if (ErrorUtils.isError(klass)) {
            return klass.getTypeConstructor().toString();
        }
        return getClassifierNamePolicy().renderClassifier(klass, this);
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    @NotNull
    public String renderType(@NotNull KotlinType type) {
        Intrinsics.checkParameterIsNotNull(type, "type");
        StringBuilder $receiver = new StringBuilder();
        renderNormalizedType($receiver, getTypeNormalizer().invoke(type));
        String string = $receiver.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    private final void renderNormalizedType(@NotNull StringBuilder $receiver, KotlinType type) {
        UnwrappedType unwrappedTypeUnwrap = type.unwrap();
        if (!(unwrappedTypeUnwrap instanceof AbbreviatedType)) {
            unwrappedTypeUnwrap = null;
        }
        AbbreviatedType abbreviated = (AbbreviatedType) unwrappedTypeUnwrap;
        if (abbreviated != null) {
            if (getRenderTypeExpansions()) {
                renderNormalizedTypeAsIs($receiver, abbreviated.getExpandedType());
                return;
            }
            renderNormalizedTypeAsIs($receiver, abbreviated.getAbbreviation());
            if (getRenderUnabbreviatedType()) {
                renderAbbreviatedTypeExpansion($receiver, abbreviated);
                return;
            }
            return;
        }
        renderNormalizedTypeAsIs($receiver, type);
    }

    private final void renderAbbreviatedTypeExpansion(@NotNull StringBuilder $receiver, AbbreviatedType abbreviated) {
        if (getTextFormat() == RenderingFormat.HTML) {
            $receiver.append("<font color=\"808080\"><i>");
        }
        $receiver.append(" /* = ");
        renderNormalizedTypeAsIs($receiver, abbreviated.getExpandedType());
        $receiver.append(" */");
        if (getTextFormat() == RenderingFormat.HTML) {
            $receiver.append("</i></font>");
        }
    }

    private final void renderNormalizedTypeAsIs(@NotNull StringBuilder $receiver, KotlinType type) {
        if ((type instanceof WrappedType) && getDebugMode() && !((WrappedType) type).isComputed()) {
            $receiver.append("<Not computed yet>");
            return;
        }
        UnwrappedType unwrappedType = type.unwrap();
        if (!(unwrappedType instanceof FlexibleType)) {
            if (unwrappedType instanceof SimpleType) {
                renderSimpleType($receiver, (SimpleType) unwrappedType);
                return;
            }
            return;
        }
        $receiver.append(((FlexibleType) unwrappedType).render(this, this));
    }

    private final void renderSimpleType(@NotNull StringBuilder $receiver, SimpleType type) {
        if (Intrinsics.areEqual(type, TypeUtils.CANT_INFER_FUNCTION_PARAM_TYPE) || TypeUtils.isDontCarePlaceholder(type)) {
            $receiver.append("???");
            return;
        }
        if (ErrorUtils.isUninferredParameter(type)) {
            if (getUninferredTypeParameterAsName()) {
                TypeConstructor constructor = type.getConstructor();
                if (constructor == null) {
                    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.ErrorUtils.UninferredParameterTypeConstructor");
                }
                TypeParameterDescriptor typeParameterDescriptor = ((ErrorUtils.UninferredParameterTypeConstructor) constructor).getTypeParameterDescriptor();
                Intrinsics.checkExpressionValueIsNotNull(typeParameterDescriptor, "(type.constructor as Uni…).typeParameterDescriptor");
                String string = typeParameterDescriptor.getName().toString();
                Intrinsics.checkExpressionValueIsNotNull(string, "(type.constructor as Uni…escriptor.name.toString()");
                $receiver.append(renderError(string));
                return;
            }
            $receiver.append("???");
            return;
        }
        if (KotlinTypeKt.isError(type)) {
            renderDefaultType($receiver, type);
        } else if (shouldRenderAsPrettyFunctionType(type)) {
            renderFunctionType($receiver, type);
        } else {
            renderDefaultType($receiver, type);
        }
    }

    private final boolean shouldRenderAsPrettyFunctionType(KotlinType type) {
        boolean z;
        if (!FunctionTypesKt.isBuiltinFunctionalType(type)) {
            return false;
        }
        Iterable $receiver$iv = type.getArguments();
        if (($receiver$iv instanceof Collection) && ((Collection) $receiver$iv).isEmpty()) {
            z = true;
        } else {
            for (Object element$iv : $receiver$iv) {
                TypeProjection it = (TypeProjection) element$iv;
                if (it.isStarProjection()) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    @NotNull
    public String renderFlexibleType(@NotNull String lowerRendered, @NotNull String upperRendered, @NotNull KotlinBuiltIns builtIns) {
        Intrinsics.checkParameterIsNotNull(lowerRendered, "lowerRendered");
        Intrinsics.checkParameterIsNotNull(upperRendered, "upperRendered");
        Intrinsics.checkParameterIsNotNull(builtIns, "builtIns");
        if (differsOnlyInNullability(lowerRendered, upperRendered)) {
            if (StringsKt.startsWith$default(upperRendered, "(", false, 2, (Object) null)) {
                return '(' + lowerRendered + ")!";
            }
            return lowerRendered + "!";
        }
        ClassifierNamePolicy classifierNamePolicy = getClassifierNamePolicy();
        ClassDescriptor collection = builtIns.getCollection();
        Intrinsics.checkExpressionValueIsNotNull(collection, "builtIns.collection");
        String kotlinCollectionsPrefix = StringsKt.substringBefore$default(classifierNamePolicy.renderClassifier(collection, this), "Collection", (String) null, 2, (Object) null);
        String simpleCollection = replacePrefixes(lowerRendered, kotlinCollectionsPrefix + "Mutable", upperRendered, kotlinCollectionsPrefix, kotlinCollectionsPrefix + "(Mutable)");
        if (simpleCollection != null) {
            return simpleCollection;
        }
        String mutableEntry = replacePrefixes(lowerRendered, kotlinCollectionsPrefix + "MutableMap.MutableEntry", upperRendered, kotlinCollectionsPrefix + "Map.Entry", kotlinCollectionsPrefix + "(Mutable)Map.(Mutable)Entry");
        if (mutableEntry != null) {
            return mutableEntry;
        }
        ClassifierNamePolicy classifierNamePolicy2 = getClassifierNamePolicy();
        ClassDescriptor array = builtIns.getArray();
        Intrinsics.checkExpressionValueIsNotNull(array, "builtIns.array");
        String kotlinPrefix = StringsKt.substringBefore$default(classifierNamePolicy2.renderClassifier(array, this), "Array", (String) null, 2, (Object) null);
        String array2 = replacePrefixes(lowerRendered, kotlinPrefix + escape("Array<"), upperRendered, kotlinPrefix + escape("Array<out "), kotlinPrefix + escape("Array<(out) "));
        if (array2 != null) {
            return array2;
        }
        return '(' + lowerRendered + ".." + upperRendered + ')';
    }

    @NotNull
    public String renderTypeArguments(@NotNull List<? extends TypeProjection> typeArguments) {
        Intrinsics.checkParameterIsNotNull(typeArguments, "typeArguments");
        if (typeArguments.isEmpty()) {
            return "";
        }
        StringBuilder $receiver = new StringBuilder();
        $receiver.append(lt());
        appendTypeProjections($receiver, typeArguments);
        $receiver.append(gt());
        String string = $receiver.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    private final void renderDefaultType(@NotNull StringBuilder $receiver, KotlinType type) {
        renderAnnotations$default(this, $receiver, type, null, 2, null);
        if (KotlinTypeKt.isError(type)) {
            if ((type instanceof UnresolvedType) && getPresentableUnresolvedTypes()) {
                $receiver.append(((UnresolvedType) type).getPresentableName());
            } else {
                $receiver.append(type.getConstructor().toString());
            }
            $receiver.append(renderTypeArguments(type.getArguments()));
        } else {
            renderTypeConstructorAndArguments$default(this, $receiver, type, null, 2, null);
        }
        if (type.isMarkedNullable()) {
            $receiver.append("?");
        }
        if (SpecialTypesKt.isDefinitelyNotNullType(type)) {
            $receiver.append("!!");
        }
    }

    static /* synthetic */ void renderTypeConstructorAndArguments$default(DescriptorRendererImpl descriptorRendererImpl, StringBuilder sb, KotlinType kotlinType, TypeConstructor typeConstructor, int i, Object obj) {
        if ((i & 2) != 0) {
            typeConstructor = kotlinType.getConstructor();
        }
        descriptorRendererImpl.renderTypeConstructorAndArguments(sb, kotlinType, typeConstructor);
    }

    private final void renderTypeConstructorAndArguments(@NotNull StringBuilder $receiver, KotlinType type, TypeConstructor typeConstructor) {
        PossiblyInnerType possiblyInnerType = TypeParameterUtilsKt.buildPossiblyInnerType(type);
        if (possiblyInnerType == null) {
            $receiver.append(renderTypeConstructor(typeConstructor));
            $receiver.append(renderTypeArguments(type.getArguments()));
        } else {
            renderPossiblyInnerType($receiver, possiblyInnerType);
        }
    }

    private final void renderPossiblyInnerType(@NotNull StringBuilder $receiver, PossiblyInnerType possiblyInnerType) {
        PossiblyInnerType it = possiblyInnerType.getOuterType();
        if (it != null) {
            renderPossiblyInnerType($receiver, it);
            $receiver.append('.');
            Name name = possiblyInnerType.getClassifierDescriptor().getName();
            Intrinsics.checkExpressionValueIsNotNull(name, "possiblyInnerType.classifierDescriptor.name");
            $receiver.append(renderName(name, false));
        } else {
            TypeConstructor typeConstructor = possiblyInnerType.getClassifierDescriptor().getTypeConstructor();
            Intrinsics.checkExpressionValueIsNotNull(typeConstructor, "possiblyInnerType.classi…escriptor.typeConstructor");
            $receiver.append(renderTypeConstructor(typeConstructor));
        }
        $receiver.append(renderTypeArguments(possiblyInnerType.getArguments()));
    }

    @NotNull
    public String renderTypeConstructor(@NotNull TypeConstructor typeConstructor) {
        Intrinsics.checkParameterIsNotNull(typeConstructor, "typeConstructor");
        ClassifierDescriptor cd = typeConstructor.mo381getDeclarationDescriptor();
        if ((cd instanceof TypeParameterDescriptor) || (cd instanceof ClassDescriptor) || (cd instanceof TypeAliasDescriptor)) {
            return renderClassifierName(cd);
        }
        if (cd == null) {
            return typeConstructor.toString();
        }
        throw new IllegalStateException(("Unexpected classifier: " + cd.getClass()).toString());
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    @NotNull
    public String renderTypeProjection(@NotNull TypeProjection typeProjection) {
        Intrinsics.checkParameterIsNotNull(typeProjection, "typeProjection");
        StringBuilder $receiver = new StringBuilder();
        appendTypeProjections($receiver, CollectionsKt.listOf(typeProjection));
        String string = $receiver.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    private final void appendTypeProjections(@NotNull StringBuilder $receiver, List<? extends TypeProjection> list) {
        CollectionsKt.joinTo$default(list, $receiver, ", ", null, null, 0, null, new Function1<TypeProjection, CharSequence>() { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl.appendTypeProjections.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final CharSequence invoke(@NotNull TypeProjection it) {
                String str;
                Intrinsics.checkParameterIsNotNull(it, "it");
                if (it.isStarProjection()) {
                    return "*";
                }
                DescriptorRendererImpl descriptorRendererImpl = DescriptorRendererImpl.this;
                KotlinType type = it.getType();
                Intrinsics.checkExpressionValueIsNotNull(type, "it.type");
                String type2 = descriptorRendererImpl.renderType(type);
                if (it.getProjectionKind() == Variance.INVARIANT) {
                    str = type2;
                } else {
                    str = it.getProjectionKind() + ' ' + type2;
                }
                return str;
            }
        }, 60, null);
    }

    private final void renderFunctionType(@NotNull StringBuilder $receiver, KotlinType type) {
        Name name;
        int lengthBefore = $receiver.length();
        renderAnnotations$default(getFunctionTypeAnnotationsRenderer(), $receiver, type, null, 2, null);
        boolean surroundReceiver = true;
        boolean hasAnnotations = $receiver.length() != lengthBefore;
        boolean isSuspend = FunctionTypesKt.isSuspendFunctionType(type);
        boolean isNullable = type.isMarkedNullable();
        KotlinType receiverType = FunctionTypesKt.getReceiverTypeFromFunctionType(type);
        boolean needParenthesis = isNullable || (hasAnnotations && receiverType != null);
        if (needParenthesis) {
            if (isSuspend) {
                $receiver.insert(lengthBefore, '(');
            } else {
                if (hasAnnotations) {
                    boolean z = StringsKt.last($receiver) == ' ';
                    if (_Assertions.ENABLED && !z) {
                        throw new AssertionError("Assertion failed");
                    }
                    if ($receiver.charAt(StringsKt.getLastIndex($receiver) - 1) != ')') {
                        $receiver.insert(StringsKt.getLastIndex($receiver), "()");
                    }
                }
                $receiver.append("(");
            }
        }
        renderModifier($receiver, isSuspend, "suspend");
        if (receiverType != null) {
            if ((!shouldRenderAsPrettyFunctionType(receiverType) || receiverType.isMarkedNullable()) && !hasModifiersOrAnnotations(receiverType)) {
                surroundReceiver = false;
            }
            if (surroundReceiver) {
                $receiver.append("(");
            }
            renderNormalizedType($receiver, receiverType);
            if (surroundReceiver) {
                $receiver.append(")");
            }
            $receiver.append(".");
        }
        $receiver.append("(");
        int index = 0;
        for (TypeProjection typeProjection : FunctionTypesKt.getValueParameterTypesFromFunctionType(type)) {
            if (index > 0) {
                $receiver.append(", ");
            }
            if (getParameterNamesInFunctionalTypes()) {
                KotlinType type2 = typeProjection.getType();
                Intrinsics.checkExpressionValueIsNotNull(type2, "typeProjection.type");
                name = FunctionTypesKt.extractParameterNameFromFunctionTypeArgument(type2);
            } else {
                name = null;
            }
            if (name != null) {
                $receiver.append(renderName(name, false));
                $receiver.append(": ");
            }
            $receiver.append(getFunctionTypeParameterTypesRenderer().renderTypeProjection(typeProjection));
            index++;
        }
        $receiver.append(") ");
        $receiver.append(arrow());
        $receiver.append(" ");
        renderNormalizedType($receiver, FunctionTypesKt.getReturnTypeFromFunctionType(type));
        if (needParenthesis) {
            $receiver.append(")");
        }
        if (isNullable) {
            $receiver.append("?");
        }
    }

    private final boolean hasModifiersOrAnnotations(@NotNull KotlinType $receiver) {
        return FunctionTypesKt.isSuspendFunctionType($receiver) || !$receiver.getAnnotations().isEmpty();
    }

    private final void appendDefinedIn(@NotNull StringBuilder $receiver, DeclarationDescriptor descriptor) {
        if ((descriptor instanceof PackageFragmentDescriptor) || (descriptor instanceof PackageViewDescriptor)) {
            return;
        }
        if (descriptor instanceof ModuleDescriptor) {
            $receiver.append(" is a module");
            return;
        }
        DeclarationDescriptor containingDeclaration = descriptor.getContainingDeclaration();
        if (containingDeclaration != null && !(containingDeclaration instanceof ModuleDescriptor)) {
            $receiver.append(" ");
            $receiver.append(renderMessage("defined in"));
            $receiver.append(" ");
            FqNameUnsafe fqName = DescriptorUtils.getFqName(containingDeclaration);
            Intrinsics.checkExpressionValueIsNotNull(fqName, "DescriptorUtils.getFqName(containingDeclaration)");
            $receiver.append(fqName.isRoot() ? "root package" : renderFqName(fqName));
            if (getWithSourceFileForTopLevel() && (containingDeclaration instanceof PackageFragmentDescriptor) && (descriptor instanceof DeclarationDescriptorWithSource)) {
                SourceElement source = ((DeclarationDescriptorWithSource) descriptor).getSource();
                Intrinsics.checkExpressionValueIsNotNull(source, "descriptor.source");
                SourceFile containingFile = source.getContainingFile();
                Intrinsics.checkExpressionValueIsNotNull(containingFile, "descriptor.source.containingFile");
                String sourceFileName = containingFile.getName();
                if (sourceFileName != null) {
                    $receiver.append(" ");
                    $receiver.append(renderMessage("in file"));
                    $receiver.append(" ");
                    $receiver.append(sourceFileName);
                }
            }
        }
    }

    static /* synthetic */ void renderAnnotations$default(DescriptorRendererImpl descriptorRendererImpl, StringBuilder sb, Annotated annotated, AnnotationUseSiteTarget annotationUseSiteTarget, int i, Object obj) {
        if ((i & 2) != 0) {
            annotationUseSiteTarget = (AnnotationUseSiteTarget) null;
        }
        descriptorRendererImpl.renderAnnotations(sb, annotated, annotationUseSiteTarget);
    }

    private final void renderAnnotations(@NotNull StringBuilder $receiver, Annotated annotated, AnnotationUseSiteTarget target) {
        if (getModifiers().contains(DescriptorRendererModifier.ANNOTATIONS)) {
            Set<FqName> excludedTypeAnnotationClasses = annotated instanceof KotlinType ? getExcludedTypeAnnotationClasses() : getExcludedAnnotationClasses();
            Function1<AnnotationDescriptor, Boolean> annotationFilter = getAnnotationFilter();
            for (AnnotationDescriptor annotation : annotated.getAnnotations()) {
                if (!CollectionsKt.contains(excludedTypeAnnotationClasses, annotation.getFqName()) && (annotationFilter == null || annotationFilter.invoke(annotation).booleanValue())) {
                    $receiver.append(renderAnnotation(annotation, target));
                    if (getEachAnnotationOnNewLine()) {
                        StringsKt.appendln($receiver);
                    } else {
                        $receiver.append(" ");
                    }
                }
            }
        }
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    @NotNull
    public String renderAnnotation(@NotNull AnnotationDescriptor annotation, @Nullable AnnotationUseSiteTarget target) {
        Intrinsics.checkParameterIsNotNull(annotation, "annotation");
        StringBuilder $receiver = new StringBuilder();
        $receiver.append('@');
        if (target != null) {
            $receiver.append(target.getRenderName() + ":");
        }
        KotlinType annotationType = annotation.getType();
        $receiver.append(renderType(annotationType));
        if (getIncludeAnnotationArguments()) {
            List<String> listRenderAndSortAnnotationArguments = renderAndSortAnnotationArguments(annotation);
            if (getIncludeEmptyAnnotationArguments() || (!listRenderAndSortAnnotationArguments.isEmpty())) {
                CollectionsKt.joinTo$default(listRenderAndSortAnnotationArguments, $receiver, ", ", "(", ")", 0, null, null, 112, null);
            }
        }
        if (getVerbose() && (KotlinTypeKt.isError(annotationType) || (annotationType.getConstructor().mo381getDeclarationDescriptor() instanceof NotFoundClasses.MockClassDescriptor))) {
            $receiver.append(" /* annotation class not found */");
        }
        String string = $receiver.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    private final List<String> renderAndSortAnnotationArguments(AnnotationDescriptor descriptor) {
        List parameterDescriptorsWithDefaultValue;
        ClassConstructorDescriptor classConstructorDescriptorMo373getUnsubstitutedPrimaryConstructor;
        Iterable $receiver$iv;
        Map<Name, ConstantValue<?>> allValueArguments = descriptor.getAllValueArguments();
        ClassDescriptor classDescriptor = getRenderDefaultAnnotationArguments() ? DescriptorUtilsKt.getAnnotationClass(descriptor) : null;
        if (classDescriptor == null || (classConstructorDescriptorMo373getUnsubstitutedPrimaryConstructor = classDescriptor.mo373getUnsubstitutedPrimaryConstructor()) == null || ($receiver$iv = classConstructorDescriptorMo373getUnsubstitutedPrimaryConstructor.getValueParameters()) == null) {
            parameterDescriptorsWithDefaultValue = null;
        } else {
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $receiver$iv) {
                ValueParameterDescriptor it = (ValueParameterDescriptor) element$iv$iv;
                if (it.declaresDefaultValue()) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            Iterable $receiver$iv2 = (List) destination$iv$iv;
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv2, 10));
            for (Object item$iv$iv : $receiver$iv2) {
                ValueParameterDescriptor it2 = (ValueParameterDescriptor) item$iv$iv;
                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                destination$iv$iv2.add(it2.getName());
            }
            parameterDescriptorsWithDefaultValue = (List) destination$iv$iv2;
        }
        if (parameterDescriptorsWithDefaultValue == null) {
            parameterDescriptorsWithDefaultValue = CollectionsKt.emptyList();
        }
        Collection destination$iv$iv3 = new ArrayList();
        for (Object element$iv$iv2 : parameterDescriptorsWithDefaultValue) {
            Name it3 = (Name) element$iv$iv2;
            if (!allValueArguments.containsKey(it3)) {
                destination$iv$iv3.add(element$iv$iv2);
            }
        }
        Iterable $receiver$iv3 = (List) destination$iv$iv3;
        Collection destination$iv$iv4 = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv3, 10));
        for (Object item$iv$iv2 : $receiver$iv3) {
            Name it4 = (Name) item$iv$iv2;
            destination$iv$iv4.add(it4.asString() + " = ...");
        }
        List defaultList = (List) destination$iv$iv4;
        Iterable $receiver$iv4 = allValueArguments.entrySet();
        Collection destination$iv$iv5 = new ArrayList(CollectionsKt.collectionSizeOrDefault($receiver$iv4, 10));
        Name name = null;
        for (Object item$iv$iv3 : $receiver$iv4) {
            Map.Entry $name_value = (Map.Entry) item$iv$iv3;
            Name name2 = (Name) $name_value.getKey();
            ConstantValue<?> constantValue = (ConstantValue) $name_value.getValue();
            StringBuilder sb = new StringBuilder();
            Map<Name, ConstantValue<?>> map = allValueArguments;
            sb.append(name2.asString());
            sb.append(" = ");
            sb.append(!parameterDescriptorsWithDefaultValue.contains(name2) ? renderConstant(constantValue) : "...");
            destination$iv$iv5.add(sb.toString());
            allValueArguments = map;
            name = name2;
        }
        List argumentList = (List) destination$iv$iv5;
        return CollectionsKt.sorted(CollectionsKt.plus((Collection) defaultList, (Iterable) argumentList));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String renderConstant(ConstantValue<?> constantValue) {
        if (constantValue instanceof ArrayValue) {
            return CollectionsKt.joinToString$default(((ArrayValue) constantValue).getValue(), ", ", "{", "}", 0, null, new Function1<ConstantValue<?>, String>() { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl.renderConstant.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                @NotNull
                public final String invoke(@NotNull ConstantValue<?> it) {
                    Intrinsics.checkParameterIsNotNull(it, "it");
                    return DescriptorRendererImpl.this.renderConstant(it);
                }
            }, 24, null);
        }
        if (constantValue instanceof AnnotationValue) {
            return StringsKt.removePrefix(DescriptorRenderer.renderAnnotation$default(this, ((AnnotationValue) constantValue).getValue(), null, 2, null), (CharSequence) "@");
        }
        if (constantValue instanceof KClassValue) {
            String strAsString = ((KClassValue) constantValue).getClassId().asSingleFqName().asString();
            Intrinsics.checkExpressionValueIsNotNull(strAsString, "value.classId.asSingleFqName().asString()");
            int arrayDimensions = ((KClassValue) constantValue).getArrayDimensions();
            for (int i = 0; i < arrayDimensions; i++) {
                strAsString = "kotlin.Array<" + strAsString + Typography.greater;
            }
            return strAsString + "::class";
        }
        return constantValue.toString();
    }

    private final boolean renderVisibility(Visibility visibility, StringBuilder builder) {
        Visibility visibility2 = visibility;
        if (!getModifiers().contains(DescriptorRendererModifier.VISIBILITY)) {
            return false;
        }
        if (getNormalizedVisibilities()) {
            visibility2 = visibility2.normalize();
        }
        if (!getRenderDefaultVisibility() && Intrinsics.areEqual(visibility2, Visibilities.DEFAULT_VISIBILITY)) {
            return false;
        }
        builder.append(renderKeyword(visibility2.getDisplayName()));
        builder.append(" ");
        return true;
    }

    private final void renderModality(Modality modality, StringBuilder builder, Modality defaultModality) {
        if (getRenderDefaultModality() || modality != defaultModality) {
            boolean zContains = getModifiers().contains(DescriptorRendererModifier.MODALITY);
            String strName = modality.name();
            if (strName == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String lowerCase = strName.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
            renderModifier(builder, zContains, lowerCase);
        }
    }

    private final Modality implicitModalityWithoutExtensions(@NotNull MemberDescriptor $receiver) {
        if ($receiver instanceof ClassDescriptor) {
            return ((ClassDescriptor) $receiver).getKind() == ClassKind.INTERFACE ? Modality.ABSTRACT : Modality.FINAL;
        }
        DeclarationDescriptor containingDeclaration = $receiver.getContainingDeclaration();
        if (!(containingDeclaration instanceof ClassDescriptor)) {
            containingDeclaration = null;
        }
        ClassDescriptor containingClassDescriptor = (ClassDescriptor) containingDeclaration;
        if (containingClassDescriptor == null) {
            return Modality.FINAL;
        }
        if (!($receiver instanceof CallableMemberDescriptor)) {
            return Modality.FINAL;
        }
        Collection<? extends CallableMemberDescriptor> overriddenDescriptors = ((CallableMemberDescriptor) $receiver).getOverriddenDescriptors();
        Intrinsics.checkExpressionValueIsNotNull(overriddenDescriptors, "this.overriddenDescriptors");
        if ((!overriddenDescriptors.isEmpty()) && containingClassDescriptor.getModality() != Modality.FINAL) {
            return Modality.OPEN;
        }
        if (containingClassDescriptor.getKind() == ClassKind.INTERFACE && (!Intrinsics.areEqual(((CallableMemberDescriptor) $receiver).getVisibility(), Visibilities.PRIVATE))) {
            return ((CallableMemberDescriptor) $receiver).getModality() == Modality.ABSTRACT ? Modality.ABSTRACT : Modality.OPEN;
        }
        return Modality.FINAL;
    }

    private final void renderModalityForCallable(CallableMemberDescriptor callable, StringBuilder builder) {
        if (!DescriptorUtils.isTopLevelDeclaration(callable) || callable.getModality() != Modality.FINAL) {
            if (getOverrideRenderingPolicy() == OverrideRenderingPolicy.RENDER_OVERRIDE && callable.getModality() == Modality.OPEN && overridesSomething(callable)) {
                return;
            }
            Modality modality = callable.getModality();
            Intrinsics.checkExpressionValueIsNotNull(modality, "callable.modality");
            renderModality(modality, builder, implicitModalityWithoutExtensions(callable));
        }
    }

    private final void renderOverride(CallableMemberDescriptor callableMember, StringBuilder builder) {
        if (getModifiers().contains(DescriptorRendererModifier.OVERRIDE) && overridesSomething(callableMember) && getOverrideRenderingPolicy() != OverrideRenderingPolicy.RENDER_OPEN) {
            renderModifier(builder, true, "override");
            if (getVerbose()) {
                builder.append("/*");
                builder.append(callableMember.getOverriddenDescriptors().size());
                builder.append("*/ ");
            }
        }
    }

    private final void renderMemberKind(CallableMemberDescriptor callableMember, StringBuilder builder) {
        if (getModifiers().contains(DescriptorRendererModifier.MEMBER_KIND) && getVerbose() && callableMember.getKind() != CallableMemberDescriptor.Kind.DECLARATION) {
            builder.append("/*");
            String strName = callableMember.getKind().name();
            if (strName == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String lowerCase = strName.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
            builder.append(lowerCase);
            builder.append("*/ ");
        }
    }

    private final void renderModifier(StringBuilder builder, boolean value, String modifier) {
        if (value) {
            builder.append(renderKeyword(modifier));
            builder.append(" ");
        }
    }

    private final void renderMemberModifiers(MemberDescriptor descriptor, StringBuilder builder) {
        renderModifier(builder, descriptor.isExternal(), "external");
        renderModifier(builder, getModifiers().contains(DescriptorRendererModifier.EXPECT) && descriptor.isExpect(), "expect");
        renderModifier(builder, getModifiers().contains(DescriptorRendererModifier.ACTUAL) && descriptor.isActual(), "actual");
    }

    /* JADX WARN: Code duplicated, block: B:20:0x004d  */
    private final void renderAdditionalModifiers(FunctionDescriptor functionDescriptor, StringBuilder builder) {
        boolean isOperator;
        boolean z;
        boolean z2;
        boolean z3 = false;
        if (functionDescriptor.isOperator()) {
            Iterable overriddenDescriptors = functionDescriptor.getOverriddenDescriptors();
            Intrinsics.checkExpressionValueIsNotNull(overriddenDescriptors, "functionDescriptor.overriddenDescriptors");
            Iterable $receiver$iv = overriddenDescriptors;
            if (!($receiver$iv instanceof Collection) || !((Collection) $receiver$iv).isEmpty()) {
                Iterator it = $receiver$iv.iterator();
                while (true) {
                    if (it.hasNext()) {
                        Object element$iv = it.next();
                        FunctionDescriptor it2 = (FunctionDescriptor) element$iv;
                        Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                        if (it2.isOperator()) {
                            z2 = false;
                            break;
                        }
                    } else {
                        z2 = true;
                        break;
                    }
                }
            } else {
                z2 = true;
            }
            if (z2 || getAlwaysRenderModifiers()) {
                isOperator = true;
            } else {
                isOperator = false;
            }
        } else {
            isOperator = false;
        }
        if (functionDescriptor.isInfix()) {
            Iterable overriddenDescriptors2 = functionDescriptor.getOverriddenDescriptors();
            Intrinsics.checkExpressionValueIsNotNull(overriddenDescriptors2, "functionDescriptor.overriddenDescriptors");
            Iterable $receiver$iv2 = overriddenDescriptors2;
            if (!($receiver$iv2 instanceof Collection) || !((Collection) $receiver$iv2).isEmpty()) {
                Iterator it3 = $receiver$iv2.iterator();
                while (true) {
                    if (it3.hasNext()) {
                        Object element$iv2 = it3.next();
                        FunctionDescriptor it4 = (FunctionDescriptor) element$iv2;
                        Intrinsics.checkExpressionValueIsNotNull(it4, "it");
                        if (it4.isInfix()) {
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
            if (z || getAlwaysRenderModifiers()) {
                z3 = true;
            }
        }
        boolean isInfix = z3;
        renderModifier(builder, functionDescriptor.isTailrec(), "tailrec");
        renderSuspendModifier(functionDescriptor, builder);
        renderModifier(builder, functionDescriptor.isInline(), "inline");
        renderModifier(builder, isInfix, "infix");
        renderModifier(builder, isOperator, "operator");
    }

    private final void renderSuspendModifier(FunctionDescriptor functionDescriptor, StringBuilder builder) {
        renderModifier(builder, functionDescriptor.isSuspend(), "suspend");
    }

    @Override // kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer
    @NotNull
    public String render(@NotNull DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkParameterIsNotNull(declarationDescriptor, "declarationDescriptor");
        StringBuilder $receiver = new StringBuilder();
        declarationDescriptor.accept(new RenderDeclarationDescriptorVisitor(), $receiver);
        if (getWithDefinedIn()) {
            appendDefinedIn($receiver, declarationDescriptor);
        }
        String string = $receiver.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderTypeParameter(TypeParameterDescriptor typeParameter, StringBuilder builder, boolean topLevel) {
        if (topLevel) {
            builder.append(lt());
        }
        if (getVerbose()) {
            builder.append("/*");
            builder.append(typeParameter.getIndex());
            builder.append("*/ ");
        }
        renderModifier(builder, typeParameter.isReified(), "reified");
        String variance = typeParameter.getVariance().getLabel();
        renderModifier(builder, variance.length() > 0, variance);
        renderAnnotations$default(this, builder, typeParameter, null, 2, null);
        renderName(typeParameter, builder, topLevel);
        int upperBoundsCount = typeParameter.getUpperBounds().size();
        if ((upperBoundsCount > 1 && !topLevel) || upperBoundsCount == 1) {
            KotlinType upperBound = typeParameter.getUpperBounds().iterator().next();
            if (!KotlinBuiltIns.isDefaultBound(upperBound)) {
                builder.append(" : ");
                Intrinsics.checkExpressionValueIsNotNull(upperBound, "upperBound");
                builder.append(renderType(upperBound));
            }
        } else if (topLevel) {
            boolean first = true;
            for (KotlinType upperBound2 : typeParameter.getUpperBounds()) {
                if (!KotlinBuiltIns.isDefaultBound(upperBound2)) {
                    if (first) {
                        builder.append(" : ");
                    } else {
                        builder.append(" & ");
                    }
                    Intrinsics.checkExpressionValueIsNotNull(upperBound2, "upperBound");
                    builder.append(renderType(upperBound2));
                    first = false;
                }
            }
        }
        if (topLevel) {
            builder.append(gt());
        }
    }

    private final void renderTypeParameters(List<? extends TypeParameterDescriptor> list, StringBuilder builder, boolean withSpace) {
        if (!getWithoutTypeParameters() && !list.isEmpty()) {
            builder.append(lt());
            renderTypeParameterList(builder, list);
            builder.append(gt());
            if (withSpace) {
                builder.append(" ");
            }
        }
    }

    private final void renderTypeParameterList(StringBuilder builder, List<? extends TypeParameterDescriptor> list) {
        Iterator<? extends TypeParameterDescriptor> it = list.iterator();
        while (it.hasNext()) {
            TypeParameterDescriptor typeParameterDescriptor = it.next();
            renderTypeParameter(typeParameterDescriptor, builder, false);
            if (it.hasNext()) {
                builder.append(", ");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderFunction(FunctionDescriptor function, StringBuilder builder) {
        if (!getStartFromName()) {
            if (!getStartFromDeclarationKeyword()) {
                renderAnnotations$default(this, builder, function, null, 2, null);
                Visibility visibility = function.getVisibility();
                Intrinsics.checkExpressionValueIsNotNull(visibility, "function.visibility");
                renderVisibility(visibility, builder);
                renderModalityForCallable(function, builder);
                if (getIncludeAdditionalModifiers()) {
                    renderMemberModifiers(function, builder);
                }
                renderOverride(function, builder);
                if (getIncludeAdditionalModifiers()) {
                    renderAdditionalModifiers(function, builder);
                } else {
                    renderSuspendModifier(function, builder);
                }
                renderMemberKind(function, builder);
                if (getVerbose()) {
                    if (function.isHiddenToOvercomeSignatureClash()) {
                        builder.append("/*isHiddenToOvercomeSignatureClash*/ ");
                    }
                    if (function.isHiddenForResolutionEverywhereBesideSupercalls()) {
                        builder.append("/*isHiddenForResolutionEverywhereBesideSupercalls*/ ");
                    }
                }
            }
            builder.append(renderKeyword("fun"));
            builder.append(" ");
            List<TypeParameterDescriptor> typeParameters = function.getTypeParameters();
            Intrinsics.checkExpressionValueIsNotNull(typeParameters, "function.typeParameters");
            renderTypeParameters(typeParameters, builder, true);
            renderReceiver(function, builder);
        }
        renderName(function, builder, true);
        List<ValueParameterDescriptor> valueParameters = function.getValueParameters();
        Intrinsics.checkExpressionValueIsNotNull(valueParameters, "function.valueParameters");
        renderValueParameters(valueParameters, function.hasSynthesizedParameterNames(), builder);
        renderReceiverAfterName(function, builder);
        KotlinType returnType = function.getReturnType();
        if (!getWithoutReturnType() && (getUnitReturnType() || returnType == null || !KotlinBuiltIns.isUnit(returnType))) {
            builder.append(": ");
            builder.append(returnType == null ? "[NULL]" : renderType(returnType));
        }
        List<TypeParameterDescriptor> typeParameters2 = function.getTypeParameters();
        Intrinsics.checkExpressionValueIsNotNull(typeParameters2, "function.typeParameters");
        renderWhereSuffix(typeParameters2, builder);
    }

    private final void renderReceiverAfterName(CallableDescriptor callableDescriptor, StringBuilder builder) {
        ReceiverParameterDescriptor receiver;
        if (getReceiverAfterName() && (receiver = callableDescriptor.getExtensionReceiverParameter()) != null) {
            builder.append(" on ");
            KotlinType type = receiver.getType();
            Intrinsics.checkExpressionValueIsNotNull(type, "receiver.type");
            builder.append(renderType(type));
        }
    }

    private final void renderReceiver(CallableDescriptor callableDescriptor, StringBuilder builder) {
        ReceiverParameterDescriptor receiver = callableDescriptor.getExtensionReceiverParameter();
        if (receiver != null) {
            renderAnnotations(builder, receiver, AnnotationUseSiteTarget.RECEIVER);
            KotlinType type = receiver.getType();
            Intrinsics.checkExpressionValueIsNotNull(type, "receiver.type");
            String result = renderType(type);
            if (shouldRenderAsPrettyFunctionType(type) && !TypeUtils.isNullableType(type)) {
                result = '(' + result + ')';
            }
            builder.append(result);
            builder.append(".");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderConstructor(ConstructorDescriptor constructor, StringBuilder builder) {
        ClassConstructorDescriptor primaryConstructor;
        renderAnnotations$default(this, builder, constructor, null, 2, null);
        Visibility visibility = constructor.getVisibility();
        Intrinsics.checkExpressionValueIsNotNull(visibility, "constructor.visibility");
        boolean visibilityRendered = renderVisibility(visibility, builder);
        renderMemberKind(constructor, builder);
        boolean constructorKeywordRendered = getRenderConstructorKeyword() || !constructor.isPrimary() || visibilityRendered;
        if (constructorKeywordRendered) {
            builder.append(renderKeyword("constructor"));
        }
        ClassifierDescriptorWithTypeParameters classDescriptor = constructor.getContainingDeclaration();
        Intrinsics.checkExpressionValueIsNotNull(classDescriptor, "constructor.containingDeclaration");
        if (getSecondaryConstructorsAsPrimary()) {
            if (constructorKeywordRendered) {
                builder.append(" ");
            }
            renderName(classDescriptor, builder, true);
            List<TypeParameterDescriptor> typeParameters = constructor.getTypeParameters();
            Intrinsics.checkExpressionValueIsNotNull(typeParameters, "constructor.typeParameters");
            renderTypeParameters(typeParameters, builder, false);
        }
        List<ValueParameterDescriptor> valueParameters = constructor.getValueParameters();
        Intrinsics.checkExpressionValueIsNotNull(valueParameters, "constructor.valueParameters");
        renderValueParameters(valueParameters, constructor.hasSynthesizedParameterNames(), builder);
        if (getRenderConstructorDelegation() && !constructor.isPrimary() && (classDescriptor instanceof ClassDescriptor) && (primaryConstructor = ((ClassDescriptor) classDescriptor).mo373getUnsubstitutedPrimaryConstructor()) != null) {
            Iterable valueParameters2 = primaryConstructor.getValueParameters();
            Intrinsics.checkExpressionValueIsNotNull(valueParameters2, "primaryConstructor.valueParameters");
            Iterable $receiver$iv = valueParameters2;
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $receiver$iv) {
                ValueParameterDescriptor it = (ValueParameterDescriptor) element$iv$iv;
                if (!it.declaresDefaultValue() && it.getVarargElementType() == null) {
                    destination$iv$iv.add(element$iv$iv);
                }
            }
            List parametersWithoutDefault = (List) destination$iv$iv;
            if (true ^ parametersWithoutDefault.isEmpty()) {
                builder.append(" : ");
                builder.append(renderKeyword("this"));
                builder.append(CollectionsKt.joinToString$default(parametersWithoutDefault, ", ", "(", ")", 0, null, new Function1<ValueParameterDescriptor, String>() { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl.renderConstructor.1
                    @Override // kotlin.jvm.functions.Function1
                    @NotNull
                    public final String invoke(ValueParameterDescriptor it2) {
                        return "";
                    }
                }, 24, null));
            }
        }
        if (getSecondaryConstructorsAsPrimary()) {
            List<TypeParameterDescriptor> typeParameters2 = constructor.getTypeParameters();
            Intrinsics.checkExpressionValueIsNotNull(typeParameters2, "constructor.typeParameters");
            renderWhereSuffix(typeParameters2, builder);
        }
    }

    private final void renderWhereSuffix(List<? extends TypeParameterDescriptor> list, StringBuilder builder) {
        if (getWithoutTypeParameters()) {
            return;
        }
        ArrayList upperBoundStrings = new ArrayList(0);
        for (TypeParameterDescriptor typeParameter : list) {
            List<KotlinType> upperBounds = typeParameter.getUpperBounds();
            Intrinsics.checkExpressionValueIsNotNull(upperBounds, "typeParameter.upperBounds");
            Iterable $receiver$iv = CollectionsKt.drop(upperBounds, 1);
            for (Object item$iv : $receiver$iv) {
                KotlinType it = (KotlinType) item$iv;
                StringBuilder sb = new StringBuilder();
                Name name = typeParameter.getName();
                Intrinsics.checkExpressionValueIsNotNull(name, "typeParameter.name");
                sb.append(renderName(name, false));
                sb.append(" : ");
                Intrinsics.checkExpressionValueIsNotNull(it, "it");
                sb.append(renderType(it));
                upperBoundStrings.add(sb.toString());
            }
        }
        if (!upperBoundStrings.isEmpty()) {
            builder.append(" ");
            builder.append(renderKeyword("where"));
            builder.append(" ");
            CollectionsKt.joinTo$default(upperBoundStrings, builder, ", ", null, null, 0, null, null, 124, null);
        }
    }

    private final void renderValueParameters(Collection<? extends ValueParameterDescriptor> collection, boolean synthesizedParameterNames, StringBuilder builder) {
        boolean includeNames = shouldRenderParameterNames(synthesizedParameterNames);
        int parameterCount = collection.size();
        getValueParametersHandler().appendBeforeValueParameters(parameterCount, builder);
        int index = 0;
        for (ValueParameterDescriptor parameter : collection) {
            getValueParametersHandler().appendBeforeValueParameter(parameter, index, parameterCount, builder);
            renderValueParameter(parameter, includeNames, builder, false);
            getValueParametersHandler().appendAfterValueParameter(parameter, index, parameterCount, builder);
            index++;
        }
        getValueParametersHandler().appendAfterValueParameters(parameterCount, builder);
    }

    private final boolean shouldRenderParameterNames(boolean synthesizedParameterNames) {
        int i = WhenMappings.$EnumSwitchMapping$4[getParameterNameRenderingPolicy().ordinal()];
        if (i == 1) {
            return true;
        }
        if (i == 2) {
            return !synthesizedParameterNames;
        }
        if (i == 3) {
            return false;
        }
        throw new NoWhenBranchMatchedException();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:25:0x008d  */
    public final void renderValueParameter(ValueParameterDescriptor valueParameter, boolean includeName, StringBuilder builder, boolean topLevel) {
        boolean z;
        if (topLevel) {
            builder.append(renderKeyword("value-parameter"));
            builder.append(" ");
        }
        if (getVerbose()) {
            builder.append("/*");
            builder.append(valueParameter.getIndex());
            builder.append("*/ ");
        }
        renderAnnotations$default(this, builder, valueParameter, null, 2, null);
        renderModifier(builder, valueParameter.isCrossinline(), "crossinline");
        renderModifier(builder, valueParameter.isNoinline(), "noinline");
        if (getRenderPrimaryConstructorParametersAsProperties()) {
            CallableDescriptor containingDeclaration = valueParameter.getContainingDeclaration();
            if (!(containingDeclaration instanceof ClassConstructorDescriptor)) {
                containingDeclaration = null;
            }
            ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) containingDeclaration;
            if (classConstructorDescriptor != null && classConstructorDescriptor.isPrimary()) {
                renderModifier(builder, getActualPropertiesInPrimaryConstructor(), "actual");
                renderModifier(builder, true, "val");
            }
        }
        renderVariable(valueParameter, includeName, builder, topLevel);
        if (getDefaultParameterValueRenderer() != null) {
            z = getDebugMode() ? valueParameter.declaresDefaultValue() : DescriptorUtilsKt.declaresOrInheritsDefaultValue(valueParameter);
        }
        boolean withDefaultValue = z;
        if (withDefaultValue) {
            StringBuilder sb = new StringBuilder();
            sb.append(" = ");
            Function1<ValueParameterDescriptor, String> defaultParameterValueRenderer = getDefaultParameterValueRenderer();
            if (defaultParameterValueRenderer == null) {
                Intrinsics.throwNpe();
            }
            sb.append(defaultParameterValueRenderer.invoke(valueParameter));
            builder.append(sb.toString());
        }
    }

    private final void renderValVarPrefix(VariableDescriptor variable, StringBuilder builder) {
        if (!(variable instanceof ValueParameterDescriptor)) {
            builder.append(renderKeyword(variable.isVar() ? "var" : "val"));
            builder.append(" ");
        }
    }

    private final void renderVariable(VariableDescriptor variable, boolean includeName, StringBuilder builder, boolean topLevel) {
        KotlinType realType = variable.getType();
        Intrinsics.checkExpressionValueIsNotNull(realType, "variable.type");
        ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) (!(variable instanceof ValueParameterDescriptor) ? null : variable);
        KotlinType varargElementType = valueParameterDescriptor != null ? valueParameterDescriptor.getVarargElementType() : null;
        KotlinType typeToRender = varargElementType != null ? varargElementType : realType;
        renderModifier(builder, varargElementType != null, "vararg");
        if (topLevel && !getStartFromName()) {
            renderValVarPrefix(variable, builder);
        }
        if (includeName) {
            renderName(variable, builder, topLevel);
            builder.append(": ");
        }
        builder.append(renderType(typeToRender));
        renderInitializer(variable, builder);
        if (getVerbose() && varargElementType != null) {
            builder.append(" /*");
            builder.append(renderType(realType));
            builder.append("*/");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderProperty(PropertyDescriptor property, StringBuilder builder) {
        if (!getStartFromName()) {
            if (!getStartFromDeclarationKeyword()) {
                renderPropertyAnnotations(property, builder);
                Visibility visibility = property.getVisibility();
                Intrinsics.checkExpressionValueIsNotNull(visibility, "property.visibility");
                renderVisibility(visibility, builder);
                boolean z = false;
                renderModifier(builder, getModifiers().contains(DescriptorRendererModifier.CONST) && property.isConst(), "const");
                renderMemberModifiers(property, builder);
                renderModalityForCallable(property, builder);
                renderOverride(property, builder);
                if (getModifiers().contains(DescriptorRendererModifier.LATEINIT) && property.isLateInit()) {
                    z = true;
                }
                renderModifier(builder, z, "lateinit");
                renderMemberKind(property, builder);
            }
            renderValVarPrefix(property, builder);
            List<TypeParameterDescriptor> typeParameters = property.getTypeParameters();
            Intrinsics.checkExpressionValueIsNotNull(typeParameters, "property.typeParameters");
            renderTypeParameters(typeParameters, builder, true);
            renderReceiver(property, builder);
        }
        renderName(property, builder, true);
        builder.append(": ");
        KotlinType type = property.getType();
        Intrinsics.checkExpressionValueIsNotNull(type, "property.type");
        builder.append(renderType(type));
        renderReceiverAfterName(property, builder);
        renderInitializer(property, builder);
        List<TypeParameterDescriptor> typeParameters2 = property.getTypeParameters();
        Intrinsics.checkExpressionValueIsNotNull(typeParameters2, "property.typeParameters");
        renderWhereSuffix(typeParameters2, builder);
    }

    private final void renderPropertyAnnotations(PropertyDescriptor property, StringBuilder builder) {
        if (getModifiers().contains(DescriptorRendererModifier.ANNOTATIONS)) {
            renderAnnotations$default(this, builder, property, null, 2, null);
            FieldDescriptor it = property.getBackingField();
            if (it != null) {
                Intrinsics.checkExpressionValueIsNotNull(it, "it");
                renderAnnotations(builder, it, AnnotationUseSiteTarget.FIELD);
            }
            FieldDescriptor it2 = property.getDelegateField();
            if (it2 != null) {
                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                renderAnnotations(builder, it2, AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD);
            }
            if (getPropertyAccessorRenderingPolicy() == PropertyAccessorRenderingPolicy.NONE) {
                PropertyGetterDescriptor it3 = property.getGetter();
                if (it3 != null) {
                    Intrinsics.checkExpressionValueIsNotNull(it3, "it");
                    renderAnnotations(builder, it3, AnnotationUseSiteTarget.PROPERTY_GETTER);
                }
                PropertySetterDescriptor setter = property.getSetter();
                if (setter != null) {
                    Intrinsics.checkExpressionValueIsNotNull(setter, "it");
                    renderAnnotations(builder, setter, AnnotationUseSiteTarget.PROPERTY_SETTER);
                    Intrinsics.checkExpressionValueIsNotNull(setter, "setter");
                    List<ValueParameterDescriptor> valueParameters = setter.getValueParameters();
                    Intrinsics.checkExpressionValueIsNotNull(valueParameters, "setter.valueParameters");
                    ValueParameterDescriptor it4 = (ValueParameterDescriptor) CollectionsKt.single((List) valueParameters);
                    Intrinsics.checkExpressionValueIsNotNull(it4, "it");
                    renderAnnotations(builder, it4, AnnotationUseSiteTarget.SETTER_PARAMETER);
                }
            }
        }
    }

    private final void renderInitializer(VariableDescriptor variable, StringBuilder builder) {
        ConstantValue<?> constant;
        if (getIncludePropertyConstant() && (constant = variable.mo375getCompileTimeInitializer()) != null) {
            builder.append(" = ");
            Intrinsics.checkExpressionValueIsNotNull(constant, "constant");
            builder.append(escape(renderConstant(constant)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderTypeAlias(TypeAliasDescriptor typeAlias, StringBuilder builder) {
        renderAnnotations$default(this, builder, typeAlias, null, 2, null);
        Visibility visibility = typeAlias.getVisibility();
        Intrinsics.checkExpressionValueIsNotNull(visibility, "typeAlias.visibility");
        renderVisibility(visibility, builder);
        renderMemberModifiers(typeAlias, builder);
        builder.append(renderKeyword("typealias"));
        builder.append(" ");
        renderName(typeAlias, builder, true);
        List<TypeParameterDescriptor> declaredTypeParameters = typeAlias.getDeclaredTypeParameters();
        Intrinsics.checkExpressionValueIsNotNull(declaredTypeParameters, "typeAlias.declaredTypeParameters");
        renderTypeParameters(declaredTypeParameters, builder, false);
        renderCapturedTypeParametersIfRequired(typeAlias, builder);
        builder.append(" = ");
        builder.append(renderType(typeAlias.getUnderlyingType()));
    }

    private final void renderCapturedTypeParametersIfRequired(ClassifierDescriptorWithTypeParameters classifier, StringBuilder builder) {
        List<TypeParameterDescriptor> declaredTypeParameters = classifier.getDeclaredTypeParameters();
        Intrinsics.checkExpressionValueIsNotNull(declaredTypeParameters, "classifier.declaredTypeParameters");
        TypeConstructor typeConstructor = classifier.getTypeConstructor();
        Intrinsics.checkExpressionValueIsNotNull(typeConstructor, "classifier.typeConstructor");
        List<TypeParameterDescriptor> parameters = typeConstructor.getParameters();
        Intrinsics.checkExpressionValueIsNotNull(parameters, "classifier.typeConstructor.parameters");
        if (getVerbose() && classifier.isInner() && parameters.size() > declaredTypeParameters.size()) {
            builder.append(" /*captured type parameters: ");
            renderTypeParameterList(builder, parameters.subList(declaredTypeParameters.size(), parameters.size()));
            builder.append("*/");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderClass(ClassDescriptor klass, StringBuilder builder) {
        ClassConstructorDescriptor primaryConstructor;
        boolean isEnumEntry = klass.getKind() == ClassKind.ENUM_ENTRY;
        if (!getStartFromName()) {
            renderAnnotations$default(this, builder, klass, null, 2, null);
            if (!isEnumEntry) {
                Visibility visibility = klass.getVisibility();
                Intrinsics.checkExpressionValueIsNotNull(visibility, "klass.visibility");
                renderVisibility(visibility, builder);
            }
            if (klass.getKind() != ClassKind.INTERFACE || klass.getModality() != Modality.ABSTRACT) {
                ClassKind kind = klass.getKind();
                Intrinsics.checkExpressionValueIsNotNull(kind, "klass.kind");
                if (!kind.isSingleton() || klass.getModality() != Modality.FINAL) {
                    Modality modality = klass.getModality();
                    Intrinsics.checkExpressionValueIsNotNull(modality, "klass.modality");
                    renderModality(modality, builder, implicitModalityWithoutExtensions(klass));
                }
            }
            renderMemberModifiers(klass, builder);
            renderModifier(builder, getModifiers().contains(DescriptorRendererModifier.INNER) && klass.isInner(), "inner");
            renderModifier(builder, getModifiers().contains(DescriptorRendererModifier.DATA) && klass.isData(), "data");
            renderModifier(builder, getModifiers().contains(DescriptorRendererModifier.INLINE) && klass.isInline(), "inline");
            renderClassKindPrefix(klass, builder);
        }
        if (!DescriptorUtils.isCompanionObject(klass)) {
            if (!getStartFromName()) {
                renderSpaceIfNeeded(builder);
            }
            renderName(klass, builder, true);
        } else {
            renderCompanionObjectName(klass, builder);
        }
        if (isEnumEntry) {
            return;
        }
        List<TypeParameterDescriptor> declaredTypeParameters = klass.getDeclaredTypeParameters();
        Intrinsics.checkExpressionValueIsNotNull(declaredTypeParameters, "klass.declaredTypeParameters");
        renderTypeParameters(declaredTypeParameters, builder, false);
        renderCapturedTypeParametersIfRequired(klass, builder);
        ClassKind kind2 = klass.getKind();
        Intrinsics.checkExpressionValueIsNotNull(kind2, "klass.kind");
        if (!kind2.isSingleton() && getClassWithPrimaryConstructor() && (primaryConstructor = klass.mo373getUnsubstitutedPrimaryConstructor()) != null) {
            builder.append(" ");
            renderAnnotations$default(this, builder, primaryConstructor, null, 2, null);
            Visibility visibility2 = primaryConstructor.getVisibility();
            Intrinsics.checkExpressionValueIsNotNull(visibility2, "primaryConstructor.visibility");
            renderVisibility(visibility2, builder);
            builder.append(renderKeyword("constructor"));
            List<ValueParameterDescriptor> valueParameters = primaryConstructor.getValueParameters();
            Intrinsics.checkExpressionValueIsNotNull(valueParameters, "primaryConstructor.valueParameters");
            renderValueParameters(valueParameters, primaryConstructor.hasSynthesizedParameterNames(), builder);
        }
        renderSuperTypes(klass, builder);
        renderWhereSuffix(declaredTypeParameters, builder);
    }

    private final void renderSuperTypes(ClassDescriptor klass, StringBuilder builder) {
        if (getWithoutSuperTypes() || KotlinBuiltIns.isNothing(klass.getDefaultType())) {
            return;
        }
        TypeConstructor typeConstructor = klass.getTypeConstructor();
        Intrinsics.checkExpressionValueIsNotNull(typeConstructor, "klass.typeConstructor");
        Collection<KotlinType> supertypes = typeConstructor.getSupertypes();
        Intrinsics.checkExpressionValueIsNotNull(supertypes, "klass.typeConstructor.supertypes");
        if (supertypes.isEmpty()) {
            return;
        }
        if (supertypes.size() == 1 && KotlinBuiltIns.isAnyOrNullableAny(supertypes.iterator().next())) {
            return;
        }
        renderSpaceIfNeeded(builder);
        builder.append(": ");
        CollectionsKt.joinTo$default(supertypes, builder, ", ", null, null, 0, null, new Function1<KotlinType, String>() { // from class: kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererImpl.renderSuperTypes.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final String invoke(KotlinType it) {
                DescriptorRendererImpl descriptorRendererImpl = DescriptorRendererImpl.this;
                Intrinsics.checkExpressionValueIsNotNull(it, "it");
                return descriptorRendererImpl.renderType(it);
            }
        }, 60, null);
    }

    private final void renderClassKindPrefix(ClassDescriptor klass, StringBuilder builder) {
        builder.append(renderKeyword(DescriptorRenderer.Companion.getClassifierKindPrefix(klass)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderPackageView(PackageViewDescriptor packageView, StringBuilder builder) {
        renderPackageHeader(packageView.getFqName(), "package", builder);
        if (getDebugMode()) {
            builder.append(" in context of ");
            renderName(packageView.getModule(), builder, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderPackageFragment(PackageFragmentDescriptor fragment, StringBuilder builder) {
        renderPackageHeader(fragment.getFqName(), "package-fragment", builder);
        if (getDebugMode()) {
            builder.append(" in ");
            renderName(fragment.getContainingDeclaration(), builder, false);
        }
    }

    private final void renderPackageHeader(FqName fqName, String fragmentOrView, StringBuilder builder) {
        builder.append(renderKeyword(fragmentOrView));
        FqNameUnsafe unsafe = fqName.toUnsafe();
        Intrinsics.checkExpressionValueIsNotNull(unsafe, "fqName.toUnsafe()");
        String fqNameString = renderFqName(unsafe);
        if (fqNameString.length() > 0) {
            builder.append(" ");
            builder.append(fqNameString);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void renderAccessorModifiers(PropertyAccessorDescriptor descriptor, StringBuilder builder) {
        renderMemberModifiers(descriptor, builder);
    }

    /* JADX INFO: compiled from: DescriptorRendererImpl.kt */
    private final class RenderDeclarationDescriptorVisitor implements DeclarationDescriptorVisitor<Unit, StringBuilder> {

        public final /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0 = new int[PropertyAccessorRenderingPolicy.values().length];

            static {
                $EnumSwitchMapping$0[PropertyAccessorRenderingPolicy.PRETTY.ordinal()] = 1;
                $EnumSwitchMapping$0[PropertyAccessorRenderingPolicy.DEBUG.ordinal()] = 2;
                $EnumSwitchMapping$0[PropertyAccessorRenderingPolicy.NONE.ordinal()] = 3;
            }
        }

        public RenderDeclarationDescriptorVisitor() {
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitClassDescriptor(ClassDescriptor classDescriptor, StringBuilder sb) {
            visitClassDescriptor2(classDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitConstructorDescriptor(ConstructorDescriptor constructorDescriptor, StringBuilder sb) {
            visitConstructorDescriptor2(constructorDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitFunctionDescriptor(FunctionDescriptor functionDescriptor, StringBuilder sb) {
            visitFunctionDescriptor2(functionDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitModuleDeclaration(ModuleDescriptor moduleDescriptor, StringBuilder sb) {
            visitModuleDeclaration2(moduleDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitPackageFragmentDescriptor(PackageFragmentDescriptor packageFragmentDescriptor, StringBuilder sb) {
            visitPackageFragmentDescriptor2(packageFragmentDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitPackageViewDescriptor(PackageViewDescriptor packageViewDescriptor, StringBuilder sb) {
            visitPackageViewDescriptor2(packageViewDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitPropertyDescriptor(PropertyDescriptor propertyDescriptor, StringBuilder sb) {
            visitPropertyDescriptor2(propertyDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitPropertyGetterDescriptor(PropertyGetterDescriptor propertyGetterDescriptor, StringBuilder sb) {
            visitPropertyGetterDescriptor2(propertyGetterDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitPropertySetterDescriptor(PropertySetterDescriptor propertySetterDescriptor, StringBuilder sb) {
            visitPropertySetterDescriptor2(propertySetterDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitReceiverParameterDescriptor(ReceiverParameterDescriptor receiverParameterDescriptor, StringBuilder sb) {
            visitReceiverParameterDescriptor2(receiverParameterDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitTypeAliasDescriptor(TypeAliasDescriptor typeAliasDescriptor, StringBuilder sb) {
            visitTypeAliasDescriptor2(typeAliasDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitTypeParameterDescriptor(TypeParameterDescriptor typeParameterDescriptor, StringBuilder sb) {
            visitTypeParameterDescriptor2(typeParameterDescriptor, sb);
            return Unit.INSTANCE;
        }

        @Override // kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor
        public /* bridge */ /* synthetic */ Unit visitValueParameterDescriptor(ValueParameterDescriptor valueParameterDescriptor, StringBuilder sb) {
            visitValueParameterDescriptor2(valueParameterDescriptor, sb);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: visitValueParameterDescriptor, reason: avoid collision after fix types in other method */
        public void visitValueParameterDescriptor2(@NotNull ValueParameterDescriptor descriptor, @NotNull StringBuilder builder) {
            Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            DescriptorRendererImpl.this.renderValueParameter(descriptor, true, builder, true);
        }

        /* JADX INFO: renamed from: visitPropertyDescriptor, reason: avoid collision after fix types in other method */
        public void visitPropertyDescriptor2(@NotNull PropertyDescriptor descriptor, @NotNull StringBuilder builder) {
            Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            DescriptorRendererImpl.this.renderProperty(descriptor, builder);
        }

        /* JADX INFO: renamed from: visitPropertyGetterDescriptor, reason: avoid collision after fix types in other method */
        public void visitPropertyGetterDescriptor2(@NotNull PropertyGetterDescriptor descriptor, @NotNull StringBuilder builder) {
            Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            visitPropertyAccessorDescriptor(descriptor, builder, "getter");
        }

        /* JADX INFO: renamed from: visitPropertySetterDescriptor, reason: avoid collision after fix types in other method */
        public void visitPropertySetterDescriptor2(@NotNull PropertySetterDescriptor descriptor, @NotNull StringBuilder builder) {
            Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            visitPropertyAccessorDescriptor(descriptor, builder, "setter");
        }

        private final void visitPropertyAccessorDescriptor(PropertyAccessorDescriptor descriptor, StringBuilder builder, String kind) {
            int i = WhenMappings.$EnumSwitchMapping$0[DescriptorRendererImpl.this.getPropertyAccessorRenderingPolicy().ordinal()];
            if (i != 1) {
                if (i == 2) {
                    visitFunctionDescriptor2((FunctionDescriptor) descriptor, builder);
                    return;
                }
                return;
            }
            DescriptorRendererImpl.this.renderAccessorModifiers(descriptor, builder);
            builder.append(kind + " for ");
            DescriptorRendererImpl descriptorRendererImpl = DescriptorRendererImpl.this;
            PropertyDescriptor correspondingProperty = descriptor.getCorrespondingProperty();
            Intrinsics.checkExpressionValueIsNotNull(correspondingProperty, "descriptor.correspondingProperty");
            descriptorRendererImpl.renderProperty(correspondingProperty, builder);
        }

        /* JADX INFO: renamed from: visitFunctionDescriptor, reason: avoid collision after fix types in other method */
        public void visitFunctionDescriptor2(@NotNull FunctionDescriptor descriptor, @NotNull StringBuilder builder) {
            Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            DescriptorRendererImpl.this.renderFunction(descriptor, builder);
        }

        /* JADX INFO: renamed from: visitReceiverParameterDescriptor, reason: avoid collision after fix types in other method */
        public void visitReceiverParameterDescriptor2(@NotNull ReceiverParameterDescriptor descriptor, @NotNull StringBuilder builder) {
            Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            builder.append(descriptor.getName());
        }

        /* JADX INFO: renamed from: visitConstructorDescriptor, reason: avoid collision after fix types in other method */
        public void visitConstructorDescriptor2(@NotNull ConstructorDescriptor constructorDescriptor, @NotNull StringBuilder builder) {
            Intrinsics.checkParameterIsNotNull(constructorDescriptor, "constructorDescriptor");
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            DescriptorRendererImpl.this.renderConstructor(constructorDescriptor, builder);
        }

        /* JADX INFO: renamed from: visitTypeParameterDescriptor, reason: avoid collision after fix types in other method */
        public void visitTypeParameterDescriptor2(@NotNull TypeParameterDescriptor descriptor, @NotNull StringBuilder builder) {
            Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            DescriptorRendererImpl.this.renderTypeParameter(descriptor, builder, true);
        }

        /* JADX INFO: renamed from: visitPackageFragmentDescriptor, reason: avoid collision after fix types in other method */
        public void visitPackageFragmentDescriptor2(@NotNull PackageFragmentDescriptor descriptor, @NotNull StringBuilder builder) {
            Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            DescriptorRendererImpl.this.renderPackageFragment(descriptor, builder);
        }

        /* JADX INFO: renamed from: visitPackageViewDescriptor, reason: avoid collision after fix types in other method */
        public void visitPackageViewDescriptor2(@NotNull PackageViewDescriptor descriptor, @NotNull StringBuilder builder) {
            Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            DescriptorRendererImpl.this.renderPackageView(descriptor, builder);
        }

        /* JADX INFO: renamed from: visitModuleDeclaration, reason: avoid collision after fix types in other method */
        public void visitModuleDeclaration2(@NotNull ModuleDescriptor descriptor, @NotNull StringBuilder builder) {
            Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            DescriptorRendererImpl.this.renderName(descriptor, builder, true);
        }

        /* JADX INFO: renamed from: visitClassDescriptor, reason: avoid collision after fix types in other method */
        public void visitClassDescriptor2(@NotNull ClassDescriptor descriptor, @NotNull StringBuilder builder) {
            Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            DescriptorRendererImpl.this.renderClass(descriptor, builder);
        }

        /* JADX INFO: renamed from: visitTypeAliasDescriptor, reason: avoid collision after fix types in other method */
        public void visitTypeAliasDescriptor2(@NotNull TypeAliasDescriptor descriptor, @NotNull StringBuilder builder) {
            Intrinsics.checkParameterIsNotNull(descriptor, "descriptor");
            Intrinsics.checkParameterIsNotNull(builder, "builder");
            DescriptorRendererImpl.this.renderTypeAlias(descriptor, builder);
        }
    }

    private final void renderSpaceIfNeeded(StringBuilder builder) {
        int length = builder.length();
        if (length == 0 || builder.charAt(length - 1) != ' ') {
            builder.append(' ');
        }
    }

    private final String replacePrefixes(String lowerRendered, String lowerPrefix, String upperRendered, String upperPrefix, String foldedPrefix) {
        if (StringsKt.startsWith$default(lowerRendered, lowerPrefix, false, 2, (Object) null) && StringsKt.startsWith$default(upperRendered, upperPrefix, false, 2, (Object) null)) {
            int length = lowerPrefix.length();
            if (lowerRendered == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String lowerWithoutPrefix = lowerRendered.substring(length);
            Intrinsics.checkExpressionValueIsNotNull(lowerWithoutPrefix, "(this as java.lang.String).substring(startIndex)");
            int length2 = upperPrefix.length();
            if (upperRendered == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String upperWithoutPrefix = upperRendered.substring(length2);
            Intrinsics.checkExpressionValueIsNotNull(upperWithoutPrefix, "(this as java.lang.String).substring(startIndex)");
            String flexibleCollectionName = foldedPrefix + lowerWithoutPrefix;
            if (Intrinsics.areEqual(lowerWithoutPrefix, upperWithoutPrefix)) {
                return flexibleCollectionName;
            }
            if (differsOnlyInNullability(lowerWithoutPrefix, upperWithoutPrefix)) {
                return flexibleCollectionName + "!";
            }
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:12:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:8:0x0034  */
    /* JADX WARN: Instruction removed from duplicated block: B:8:0x0034, please report this as an issue */
    private final boolean differsOnlyInNullability(String lower, String upper) {
        if (!Intrinsics.areEqual(lower, StringsKt.replace$default(upper, "?", "", false, 4, (Object) null))) {
            if (StringsKt.endsWith$default(upper, "?", false, 2, (Object) null)) {
                if (!Intrinsics.areEqual(lower + '?', upper)) {
                    if (Intrinsics.areEqual('(' + lower + ")?", upper)) {
                        return false;
                    }
                }
            } else {
                if (Intrinsics.areEqual('(' + lower + ")?", upper)) {
                    return false;
                }
            }
        }
        return true;
    }

    private final boolean overridesSomething(CallableMemberDescriptor callable) {
        return !callable.getOverriddenDescriptors().isEmpty();
    }
}
