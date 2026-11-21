package kotlin.reflect.jvm.internal.impl.util;

import java.util.Set;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.text.Regex;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: OperatorNameConventions.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class OperatorNameConventions {

    @JvmField
    @NotNull
    public static final Name AND;

    @JvmField
    @NotNull
    public static final Set<Name> ASSIGNMENT_OPERATIONS;

    @JvmField
    @NotNull
    public static final Set<Name> BINARY_OPERATION_NAMES;

    @JvmField
    @NotNull
    public static final Name COMPARE_TO;

    @JvmField
    @NotNull
    public static final Regex COMPONENT_REGEX;

    @JvmField
    @NotNull
    public static final Name CONTAINS;

    @JvmField
    @NotNull
    public static final Name DEC;

    @JvmField
    @NotNull
    public static final Name DIV;

    @JvmField
    @NotNull
    public static final Name DIV_ASSIGN;

    @JvmField
    @NotNull
    public static final Name EQUALS;

    @JvmField
    @NotNull
    public static final Name GET;

    @JvmField
    @NotNull
    public static final Name GET_VALUE;

    @JvmField
    @NotNull
    public static final Name HAS_NEXT;

    @JvmField
    @NotNull
    public static final Name INC;
    public static final OperatorNameConventions INSTANCE = new OperatorNameConventions();

    @JvmField
    @NotNull
    public static final Name INVOKE;

    @JvmField
    @NotNull
    public static final Name ITERATOR;

    @JvmField
    @NotNull
    public static final Name MINUS;

    @JvmField
    @NotNull
    public static final Name MINUS_ASSIGN;

    @JvmField
    @NotNull
    public static final Name MOD;

    @JvmField
    @NotNull
    public static final Name MOD_ASSIGN;

    @JvmField
    @NotNull
    public static final Name NEXT;

    @JvmField
    @NotNull
    public static final Name NOT;

    @JvmField
    @NotNull
    public static final Name OR;

    @JvmField
    @NotNull
    public static final Name PLUS;

    @JvmField
    @NotNull
    public static final Name PLUS_ASSIGN;

    @JvmField
    @NotNull
    public static final Name PROVIDE_DELEGATE;

    @JvmField
    @NotNull
    public static final Name RANGE_TO;

    @JvmField
    @NotNull
    public static final Name REM;

    @JvmField
    @NotNull
    public static final Name REM_ASSIGN;

    @JvmField
    @NotNull
    public static final Name SET;

    @JvmField
    @NotNull
    public static final Name SET_VALUE;

    @JvmField
    @NotNull
    public static final Set<Name> SIMPLE_UNARY_OPERATION_NAMES;

    @JvmField
    @NotNull
    public static final Name TIMES;

    @JvmField
    @NotNull
    public static final Name TIMES_ASSIGN;

    @JvmField
    @NotNull
    public static final Name UNARY_MINUS;

    @JvmField
    @NotNull
    public static final Set<Name> UNARY_OPERATION_NAMES;

    @JvmField
    @NotNull
    public static final Name UNARY_PLUS;

    static {
        Name nameIdentifier = Name.identifier("getValue");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier, "Name.identifier(\"getValue\")");
        GET_VALUE = nameIdentifier;
        Name nameIdentifier2 = Name.identifier("setValue");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier2, "Name.identifier(\"setValue\")");
        SET_VALUE = nameIdentifier2;
        Name nameIdentifier3 = Name.identifier("provideDelegate");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier3, "Name.identifier(\"provideDelegate\")");
        PROVIDE_DELEGATE = nameIdentifier3;
        Name nameIdentifier4 = Name.identifier("equals");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier4, "Name.identifier(\"equals\")");
        EQUALS = nameIdentifier4;
        Name nameIdentifier5 = Name.identifier("compareTo");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier5, "Name.identifier(\"compareTo\")");
        COMPARE_TO = nameIdentifier5;
        Name nameIdentifier6 = Name.identifier("contains");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier6, "Name.identifier(\"contains\")");
        CONTAINS = nameIdentifier6;
        Name nameIdentifier7 = Name.identifier("invoke");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier7, "Name.identifier(\"invoke\")");
        INVOKE = nameIdentifier7;
        Name nameIdentifier8 = Name.identifier("iterator");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier8, "Name.identifier(\"iterator\")");
        ITERATOR = nameIdentifier8;
        Name nameIdentifier9 = Name.identifier("get");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier9, "Name.identifier(\"get\")");
        GET = nameIdentifier9;
        Name nameIdentifier10 = Name.identifier("set");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier10, "Name.identifier(\"set\")");
        SET = nameIdentifier10;
        Name nameIdentifier11 = Name.identifier("next");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier11, "Name.identifier(\"next\")");
        NEXT = nameIdentifier11;
        Name nameIdentifier12 = Name.identifier("hasNext");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier12, "Name.identifier(\"hasNext\")");
        HAS_NEXT = nameIdentifier12;
        COMPONENT_REGEX = new Regex("component\\d+");
        Name nameIdentifier13 = Name.identifier("and");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier13, "Name.identifier(\"and\")");
        AND = nameIdentifier13;
        Name nameIdentifier14 = Name.identifier("or");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier14, "Name.identifier(\"or\")");
        OR = nameIdentifier14;
        Name nameIdentifier15 = Name.identifier("inc");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier15, "Name.identifier(\"inc\")");
        INC = nameIdentifier15;
        Name nameIdentifier16 = Name.identifier("dec");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier16, "Name.identifier(\"dec\")");
        DEC = nameIdentifier16;
        Name nameIdentifier17 = Name.identifier("plus");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier17, "Name.identifier(\"plus\")");
        PLUS = nameIdentifier17;
        Name nameIdentifier18 = Name.identifier("minus");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier18, "Name.identifier(\"minus\")");
        MINUS = nameIdentifier18;
        Name nameIdentifier19 = Name.identifier("not");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier19, "Name.identifier(\"not\")");
        NOT = nameIdentifier19;
        Name nameIdentifier20 = Name.identifier("unaryMinus");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier20, "Name.identifier(\"unaryMinus\")");
        UNARY_MINUS = nameIdentifier20;
        Name nameIdentifier21 = Name.identifier("unaryPlus");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier21, "Name.identifier(\"unaryPlus\")");
        UNARY_PLUS = nameIdentifier21;
        Name nameIdentifier22 = Name.identifier("times");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier22, "Name.identifier(\"times\")");
        TIMES = nameIdentifier22;
        Name nameIdentifier23 = Name.identifier("div");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier23, "Name.identifier(\"div\")");
        DIV = nameIdentifier23;
        Name nameIdentifier24 = Name.identifier("mod");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier24, "Name.identifier(\"mod\")");
        MOD = nameIdentifier24;
        Name nameIdentifier25 = Name.identifier("rem");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier25, "Name.identifier(\"rem\")");
        REM = nameIdentifier25;
        Name nameIdentifier26 = Name.identifier("rangeTo");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier26, "Name.identifier(\"rangeTo\")");
        RANGE_TO = nameIdentifier26;
        Name nameIdentifier27 = Name.identifier("timesAssign");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier27, "Name.identifier(\"timesAssign\")");
        TIMES_ASSIGN = nameIdentifier27;
        Name nameIdentifier28 = Name.identifier("divAssign");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier28, "Name.identifier(\"divAssign\")");
        DIV_ASSIGN = nameIdentifier28;
        Name nameIdentifier29 = Name.identifier("modAssign");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier29, "Name.identifier(\"modAssign\")");
        MOD_ASSIGN = nameIdentifier29;
        Name nameIdentifier30 = Name.identifier("remAssign");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier30, "Name.identifier(\"remAssign\")");
        REM_ASSIGN = nameIdentifier30;
        Name nameIdentifier31 = Name.identifier("plusAssign");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier31, "Name.identifier(\"plusAssign\")");
        PLUS_ASSIGN = nameIdentifier31;
        Name nameIdentifier32 = Name.identifier("minusAssign");
        Intrinsics.checkExpressionValueIsNotNull(nameIdentifier32, "Name.identifier(\"minusAssign\")");
        MINUS_ASSIGN = nameIdentifier32;
        UNARY_OPERATION_NAMES = SetsKt.setOf((Object[]) new Name[]{INC, DEC, UNARY_PLUS, UNARY_MINUS, NOT});
        SIMPLE_UNARY_OPERATION_NAMES = SetsKt.setOf((Object[]) new Name[]{UNARY_PLUS, UNARY_MINUS, NOT});
        BINARY_OPERATION_NAMES = SetsKt.setOf((Object[]) new Name[]{TIMES, PLUS, MINUS, DIV, MOD, REM, RANGE_TO});
        ASSIGNMENT_OPERATIONS = SetsKt.setOf((Object[]) new Name[]{TIMES_ASSIGN, DIV_ASSIGN, MOD_ASSIGN, REM_ASSIGN, PLUS_ASSIGN, MINUS_ASSIGN});
    }

    private OperatorNameConventions() {
    }
}
