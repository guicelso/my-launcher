package kotlin.collections;

import java.util.Arrays;
import java.util.NoSuchElementException;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.internal.InlineOnly;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: _UArrays.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000z\n\u0000\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0016\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0017\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b/\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0011\n\u0002\b\r\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0003\u0010\u0004\u001a\u0017\u0010\u0005\u001a\u00020\u0006*\u00020\u0007H\u0087\bø\u0001\u0000¢\u0006\u0004\b\b\u0010\t\u001a\u0017\u0010\n\u001a\u00020\u000b*\u00020\fH\u0087\bø\u0001\u0000¢\u0006\u0004\b\r\u0010\u000e\u001a\u0017\u0010\u000f\u001a\u00020\u0010*\u00020\u0011H\u0087\bø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\u0013\u001a\u0015\u0010\u0014\u001a\u00020\u0002*\u00020\u0001H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0004\u001a\u0015\u0010\u0015\u001a\u00020\u0007*\u00020\u0006H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\t\u001a\u0015\u0010\u0016\u001a\u00020\f*\u00020\u000bH\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u000e\u001a\u0015\u0010\u0017\u001a\u00020\u0011*\u00020\u0010H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0013\u001a\u001f\u0010\u0018\u001a\u00020\u0019*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0002H\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b\u001b\u0010\u001c\u001a\u001f\u0010\u0018\u001a\u00020\u0019*\u00020\u00072\u0006\u0010\u001a\u001a\u00020\u0007H\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b\u001d\u0010\u001e\u001a\u001f\u0010\u0018\u001a\u00020\u0019*\u00020\f2\u0006\u0010\u001a\u001a\u00020\fH\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b\u001f\u0010 \u001a\u001f\u0010\u0018\u001a\u00020\u0019*\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u0011H\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b!\u0010\"\u001a\u0016\u0010#\u001a\u00020$*\u00020\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b%\u0010&\u001a\u0016\u0010#\u001a\u00020$*\u00020\u0007H\u0007ø\u0001\u0000¢\u0006\u0004\b'\u0010(\u001a\u0016\u0010#\u001a\u00020$*\u00020\fH\u0007ø\u0001\u0000¢\u0006\u0004\b)\u0010*\u001a\u0016\u0010#\u001a\u00020$*\u00020\u0011H\u0007ø\u0001\u0000¢\u0006\u0004\b+\u0010,\u001a\u0016\u0010-\u001a\u00020.*\u00020\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b/\u00100\u001a\u0016\u0010-\u001a\u00020.*\u00020\u0007H\u0007ø\u0001\u0000¢\u0006\u0004\b1\u00102\u001a\u0016\u0010-\u001a\u00020.*\u00020\fH\u0007ø\u0001\u0000¢\u0006\u0004\b3\u00104\u001a\u0016\u0010-\u001a\u00020.*\u00020\u0011H\u0007ø\u0001\u0000¢\u0006\u0004\b5\u00106\u001a=\u00107\u001a\u00020\u0002*\u00020\u00022\u0006\u00108\u001a\u00020\u00022\b\b\u0002\u00109\u001a\u00020$2\b\b\u0002\u0010:\u001a\u00020$2\b\b\u0002\u0010;\u001a\u00020$H\u0087\bø\u0001\u0000¢\u0006\u0004\b<\u0010=\u001a=\u00107\u001a\u00020\u0007*\u00020\u00072\u0006\u00108\u001a\u00020\u00072\b\b\u0002\u00109\u001a\u00020$2\b\b\u0002\u0010:\u001a\u00020$2\b\b\u0002\u0010;\u001a\u00020$H\u0087\bø\u0001\u0000¢\u0006\u0004\b>\u0010?\u001a=\u00107\u001a\u00020\f*\u00020\f2\u0006\u00108\u001a\u00020\f2\b\b\u0002\u00109\u001a\u00020$2\b\b\u0002\u0010:\u001a\u00020$2\b\b\u0002\u0010;\u001a\u00020$H\u0087\bø\u0001\u0000¢\u0006\u0004\b@\u0010A\u001a=\u00107\u001a\u00020\u0011*\u00020\u00112\u0006\u00108\u001a\u00020\u00112\b\b\u0002\u00109\u001a\u00020$2\b\b\u0002\u0010:\u001a\u00020$2\b\b\u0002\u0010;\u001a\u00020$H\u0087\bø\u0001\u0000¢\u0006\u0004\bB\u0010C\u001a\u0017\u0010D\u001a\u00020\u0002*\u00020\u0002H\u0087\bø\u0001\u0000¢\u0006\u0004\bE\u0010\u0004\u001a\u001f\u0010D\u001a\u00020\u0002*\u00020\u00022\u0006\u0010F\u001a\u00020$H\u0087\bø\u0001\u0000¢\u0006\u0004\bG\u0010H\u001a\u0017\u0010D\u001a\u00020\u0007*\u00020\u0007H\u0087\bø\u0001\u0000¢\u0006\u0004\bI\u0010\t\u001a\u001f\u0010D\u001a\u00020\u0007*\u00020\u00072\u0006\u0010F\u001a\u00020$H\u0087\bø\u0001\u0000¢\u0006\u0004\bJ\u0010K\u001a\u0017\u0010D\u001a\u00020\f*\u00020\fH\u0087\bø\u0001\u0000¢\u0006\u0004\bL\u0010\u000e\u001a\u001f\u0010D\u001a\u00020\f*\u00020\f2\u0006\u0010F\u001a\u00020$H\u0087\bø\u0001\u0000¢\u0006\u0004\bM\u0010N\u001a\u0017\u0010D\u001a\u00020\u0011*\u00020\u0011H\u0087\bø\u0001\u0000¢\u0006\u0004\bO\u0010\u0013\u001a\u001f\u0010D\u001a\u00020\u0011*\u00020\u00112\u0006\u0010F\u001a\u00020$H\u0087\bø\u0001\u0000¢\u0006\u0004\bP\u0010Q\u001a'\u0010R\u001a\u00020\u0002*\u00020\u00022\u0006\u0010S\u001a\u00020$2\u0006\u0010T\u001a\u00020$H\u0087\bø\u0001\u0000¢\u0006\u0004\bU\u0010V\u001a'\u0010R\u001a\u00020\u0007*\u00020\u00072\u0006\u0010S\u001a\u00020$2\u0006\u0010T\u001a\u00020$H\u0087\bø\u0001\u0000¢\u0006\u0004\bW\u0010X\u001a'\u0010R\u001a\u00020\f*\u00020\f2\u0006\u0010S\u001a\u00020$2\u0006\u0010T\u001a\u00020$H\u0087\bø\u0001\u0000¢\u0006\u0004\bY\u0010Z\u001a'\u0010R\u001a\u00020\u0011*\u00020\u00112\u0006\u0010S\u001a\u00020$2\u0006\u0010T\u001a\u00020$H\u0087\bø\u0001\u0000¢\u0006\u0004\b[\u0010\\\u001a\u0017\u0010]\u001a\u00020^*\u00020\u0002H\u0087\bø\u0001\u0000¢\u0006\u0004\b_\u0010`\u001a\u001e\u0010]\u001a\u00020^*\u00020\u00022\u0006\u0010]\u001a\u00020aH\u0007ø\u0001\u0000¢\u0006\u0004\bb\u0010c\u001a\u0017\u0010]\u001a\u00020d*\u00020\u0007H\u0087\bø\u0001\u0000¢\u0006\u0004\be\u0010(\u001a\u001e\u0010]\u001a\u00020d*\u00020\u00072\u0006\u0010]\u001a\u00020aH\u0007ø\u0001\u0000¢\u0006\u0004\bf\u0010g\u001a\u0017\u0010]\u001a\u00020h*\u00020\fH\u0087\bø\u0001\u0000¢\u0006\u0004\bi\u0010j\u001a\u001e\u0010]\u001a\u00020h*\u00020\f2\u0006\u0010]\u001a\u00020aH\u0007ø\u0001\u0000¢\u0006\u0004\bk\u0010l\u001a\u0017\u0010]\u001a\u00020m*\u00020\u0011H\u0087\bø\u0001\u0000¢\u0006\u0004\bn\u0010o\u001a\u001e\u0010]\u001a\u00020m*\u00020\u00112\u0006\u0010]\u001a\u00020aH\u0007ø\u0001\u0000¢\u0006\u0004\bp\u0010q\u001a\u0017\u0010r\u001a\u00020\u0001*\u00020\u0002H\u0087\bø\u0001\u0000¢\u0006\u0004\bs\u0010\u0004\u001a\u0017\u0010t\u001a\u00020\u0006*\u00020\u0007H\u0087\bø\u0001\u0000¢\u0006\u0004\bu\u0010\t\u001a\u0017\u0010v\u001a\u00020\u000b*\u00020\fH\u0087\bø\u0001\u0000¢\u0006\u0004\bw\u0010\u000e\u001a\u0017\u0010x\u001a\u00020\u0010*\u00020\u0011H\u0087\bø\u0001\u0000¢\u0006\u0004\by\u0010\u0013\u001a\u001c\u0010z\u001a\b\u0012\u0004\u0012\u00020^0{*\u00020\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b|\u0010}\u001a\u001c\u0010z\u001a\b\u0012\u0004\u0012\u00020d0{*\u00020\u0007H\u0007ø\u0001\u0000¢\u0006\u0004\b~\u0010\u007f\u001a\u001e\u0010z\u001a\b\u0012\u0004\u0012\u00020h0{*\u00020\fH\u0007ø\u0001\u0000¢\u0006\u0006\b\u0080\u0001\u0010\u0081\u0001\u001a\u001e\u0010z\u001a\b\u0012\u0004\u0012\u00020m0{*\u00020\u0011H\u0007ø\u0001\u0000¢\u0006\u0006\b\u0082\u0001\u0010\u0083\u0001\u001a\u0016\u0010\u0084\u0001\u001a\u00020\u0002*\u00020\u0001H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0004\u001a\u0016\u0010\u0085\u0001\u001a\u00020\u0007*\u00020\u0006H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\t\u001a\u0016\u0010\u0086\u0001\u001a\u00020\f*\u00020\u000bH\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u000e\u001a\u0016\u0010\u0087\u0001\u001a\u00020\u0011*\u00020\u0010H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0013\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0088\u0001"}, d2 = {"asByteArray", "", "Lkotlin/UByteArray;", "asByteArray-GBYM_sE", "([B)[B", "asIntArray", "", "Lkotlin/UIntArray;", "asIntArray--ajY-9A", "([I)[I", "asLongArray", "", "Lkotlin/ULongArray;", "asLongArray-QwZRm1k", "([J)[J", "asShortArray", "", "Lkotlin/UShortArray;", "asShortArray-rL5Bavg", "([S)[S", "asUByteArray", "asUIntArray", "asULongArray", "asUShortArray", "contentEquals", "", "other", "contentEquals-kdPth3s", "([B[B)Z", "contentEquals-ctEhBpI", "([I[I)Z", "contentEquals-us8wMrg", "([J[J)Z", "contentEquals-mazbYpA", "([S[S)Z", "contentHashCode", "", "contentHashCode-GBYM_sE", "([B)I", "contentHashCode--ajY-9A", "([I)I", "contentHashCode-QwZRm1k", "([J)I", "contentHashCode-rL5Bavg", "([S)I", "contentToString", "", "contentToString-GBYM_sE", "([B)Ljava/lang/String;", "contentToString--ajY-9A", "([I)Ljava/lang/String;", "contentToString-QwZRm1k", "([J)Ljava/lang/String;", "contentToString-rL5Bavg", "([S)Ljava/lang/String;", "copyInto", "destination", "destinationOffset", "startIndex", "endIndex", "copyInto-FUQE5sA", "([B[BIII)[B", "copyInto-sIZ3KeM", "([I[IIII)[I", "copyInto--B0-L2c", "([J[JIII)[J", "copyInto-9-ak10g", "([S[SIII)[S", "copyOf", "copyOf-GBYM_sE", "newSize", "copyOf-PpDY95g", "([BI)[B", "copyOf--ajY-9A", "copyOf-qFRl0hI", "([II)[I", "copyOf-QwZRm1k", "copyOf-r7IrZao", "([JI)[J", "copyOf-rL5Bavg", "copyOf-nggk6HY", "([SI)[S", "copyOfRange", "fromIndex", "toIndex", "copyOfRange-4UcCI2c", "([BII)[B", "copyOfRange-oBK06Vg", "([III)[I", "copyOfRange--nroSd4", "([JII)[J", "copyOfRange-Aa5vz7o", "([SII)[S", "random", "Lkotlin/UByte;", "random-GBYM_sE", "([B)B", "Lkotlin/random/Random;", "random-oSF2wD8", "([BLkotlin/random/Random;)B", "Lkotlin/UInt;", "random--ajY-9A", "random-2D5oskM", "([ILkotlin/random/Random;)I", "Lkotlin/ULong;", "random-QwZRm1k", "([J)J", "random-JzugnMA", "([JLkotlin/random/Random;)J", "Lkotlin/UShort;", "random-rL5Bavg", "([S)S", "random-s5X_as8", "([SLkotlin/random/Random;)S", "toByteArray", "toByteArray-GBYM_sE", "toIntArray", "toIntArray--ajY-9A", "toLongArray", "toLongArray-QwZRm1k", "toShortArray", "toShortArray-rL5Bavg", "toTypedArray", "", "toTypedArray-GBYM_sE", "([B)[Lkotlin/UByte;", "toTypedArray--ajY-9A", "([I)[Lkotlin/UInt;", "toTypedArray-QwZRm1k", "([J)[Lkotlin/ULong;", "toTypedArray-rL5Bavg", "([S)[Lkotlin/UShort;", "toUByteArray", "toUIntArray", "toULongArray", "toUShortArray", "kotlin-stdlib"}, k = 5, mv = {1, 1, 13}, xi = 1, xs = "kotlin/collections/UArraysKt")
class UArraysKt___UArraysKt {
    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: random--ajY-9A, reason: not valid java name */
    private static final int m328randomajY9A(@NotNull int[] $receiver) {
        return UArraysKt.m329random2D5oskM($receiver, Random.INSTANCE);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: random-QwZRm1k, reason: not valid java name */
    private static final long m332randomQwZRm1k(@NotNull long[] $receiver) {
        return UArraysKt.m331randomJzugnMA($receiver, Random.INSTANCE);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: random-GBYM_sE, reason: not valid java name */
    private static final byte m330randomGBYM_sE(@NotNull byte[] $receiver) {
        return UArraysKt.m333randomoSF2wD8($receiver, Random.INSTANCE);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: random-rL5Bavg, reason: not valid java name */
    private static final short m334randomrL5Bavg(@NotNull short[] $receiver) {
        return UArraysKt.m335randoms5X_as8($receiver, Random.INSTANCE);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: random-2D5oskM, reason: not valid java name */
    public static final int m329random2D5oskM(@NotNull int[] receiver$0, @NotNull Random random) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(random, "random");
        if (UIntArray.m144isEmptyimpl(receiver$0)) {
            throw new NoSuchElementException("Array is empty.");
        }
        return UIntArray.m141getimpl(receiver$0, random.nextInt(UIntArray.m142getSizeimpl(receiver$0)));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: random-JzugnMA, reason: not valid java name */
    public static final long m331randomJzugnMA(@NotNull long[] receiver$0, @NotNull Random random) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(random, "random");
        if (ULongArray.m211isEmptyimpl(receiver$0)) {
            throw new NoSuchElementException("Array is empty.");
        }
        return ULongArray.m208getimpl(receiver$0, random.nextInt(ULongArray.m209getSizeimpl(receiver$0)));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: random-oSF2wD8, reason: not valid java name */
    public static final byte m333randomoSF2wD8(@NotNull byte[] receiver$0, @NotNull Random random) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(random, "random");
        if (UByteArray.m77isEmptyimpl(receiver$0)) {
            throw new NoSuchElementException("Array is empty.");
        }
        return UByteArray.m74getimpl(receiver$0, random.nextInt(UByteArray.m75getSizeimpl(receiver$0)));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: random-s5X_as8, reason: not valid java name */
    public static final short m335randoms5X_as8(@NotNull short[] receiver$0, @NotNull Random random) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(random, "random");
        if (UShortArray.m276isEmptyimpl(receiver$0)) {
            throw new NoSuchElementException("Array is empty.");
        }
        return UShortArray.m273getimpl(receiver$0, random.nextInt(UShortArray.m274getSizeimpl(receiver$0)));
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: asByteArray-GBYM_sE, reason: not valid java name */
    private static final byte[] m292asByteArrayGBYM_sE(@NotNull byte[] $receiver) {
        return $receiver;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: asIntArray--ajY-9A, reason: not valid java name */
    private static final int[] m293asIntArrayajY9A(@NotNull int[] $receiver) {
        return $receiver;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: asLongArray-QwZRm1k, reason: not valid java name */
    private static final long[] m294asLongArrayQwZRm1k(@NotNull long[] $receiver) {
        return $receiver;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: asShortArray-rL5Bavg, reason: not valid java name */
    private static final short[] m295asShortArrayrL5Bavg(@NotNull short[] $receiver) {
        return $receiver;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final byte[] asUByteArray(@NotNull byte[] $receiver) {
        return UByteArray.m69constructorimpl($receiver);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final int[] asUIntArray(@NotNull int[] $receiver) {
        return UIntArray.m136constructorimpl($receiver);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final long[] asULongArray(@NotNull long[] $receiver) {
        return ULongArray.m203constructorimpl($receiver);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final short[] asUShortArray(@NotNull short[] $receiver) {
        return UShortArray.m268constructorimpl($receiver);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: contentEquals-ctEhBpI, reason: not valid java name */
    public static final boolean m296contentEqualsctEhBpI(@NotNull int[] receiver$0, @NotNull int[] other) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return Arrays.equals(receiver$0, other);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: contentEquals-us8wMrg, reason: not valid java name */
    public static final boolean m299contentEqualsus8wMrg(@NotNull long[] receiver$0, @NotNull long[] other) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return Arrays.equals(receiver$0, other);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: contentEquals-kdPth3s, reason: not valid java name */
    public static final boolean m297contentEqualskdPth3s(@NotNull byte[] receiver$0, @NotNull byte[] other) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return Arrays.equals(receiver$0, other);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: contentEquals-mazbYpA, reason: not valid java name */
    public static final boolean m298contentEqualsmazbYpA(@NotNull short[] receiver$0, @NotNull short[] other) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return Arrays.equals(receiver$0, other);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: contentHashCode--ajY-9A, reason: not valid java name */
    public static final int m300contentHashCodeajY9A(@NotNull int[] receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return Arrays.hashCode(receiver$0);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: contentHashCode-QwZRm1k, reason: not valid java name */
    public static final int m302contentHashCodeQwZRm1k(@NotNull long[] receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return Arrays.hashCode(receiver$0);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: contentHashCode-GBYM_sE, reason: not valid java name */
    public static final int m301contentHashCodeGBYM_sE(@NotNull byte[] receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return Arrays.hashCode(receiver$0);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* JADX INFO: renamed from: contentHashCode-rL5Bavg, reason: not valid java name */
    public static final int m303contentHashCoderL5Bavg(@NotNull short[] receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return Arrays.hashCode(receiver$0);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: contentToString--ajY-9A, reason: not valid java name */
    public static final String m304contentToStringajY9A(@NotNull int[] receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return CollectionsKt.joinToString$default(UIntArray.m134boximpl(receiver$0), ", ", "[", "]", 0, null, null, 56, null);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: contentToString-QwZRm1k, reason: not valid java name */
    public static final String m306contentToStringQwZRm1k(@NotNull long[] receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return CollectionsKt.joinToString$default(ULongArray.m201boximpl(receiver$0), ", ", "[", "]", 0, null, null, 56, null);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: contentToString-GBYM_sE, reason: not valid java name */
    public static final String m305contentToStringGBYM_sE(@NotNull byte[] receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return CollectionsKt.joinToString$default(UByteArray.m67boximpl(receiver$0), ", ", "[", "]", 0, null, null, 56, null);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: contentToString-rL5Bavg, reason: not valid java name */
    public static final String m307contentToStringrL5Bavg(@NotNull short[] receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        return CollectionsKt.joinToString$default(UShortArray.m266boximpl(receiver$0), ", ", "[", "]", 0, null, null, 56, null);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: copyInto-sIZ3KeM$default, reason: not valid java name */
    static /* synthetic */ int[] m315copyIntosIZ3KeM$default(int[] $receiver, int[] destination, int destinationOffset, int startIndex, int endIndex, int i, Object obj) {
        if ((i & 2) != 0) {
            destinationOffset = 0;
        }
        if ((i & 4) != 0) {
            startIndex = 0;
        }
        if ((i & 8) != 0) {
            endIndex = UIntArray.m142getSizeimpl($receiver);
        }
        ArraysKt.copyInto($receiver, destination, destinationOffset, startIndex, endIndex);
        return destination;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: copyInto-sIZ3KeM, reason: not valid java name */
    private static final int[] m314copyIntosIZ3KeM(@NotNull int[] $receiver, int[] destination, int destinationOffset, int startIndex, int endIndex) {
        ArraysKt.copyInto($receiver, destination, destinationOffset, startIndex, endIndex);
        return destination;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: copyInto--B0-L2c$default, reason: not valid java name */
    static /* synthetic */ long[] m309copyIntoB0L2c$default(long[] $receiver, long[] destination, int destinationOffset, int startIndex, int endIndex, int i, Object obj) {
        if ((i & 2) != 0) {
            destinationOffset = 0;
        }
        if ((i & 4) != 0) {
            startIndex = 0;
        }
        if ((i & 8) != 0) {
            endIndex = ULongArray.m209getSizeimpl($receiver);
        }
        ArraysKt.copyInto($receiver, destination, destinationOffset, startIndex, endIndex);
        return destination;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: copyInto--B0-L2c, reason: not valid java name */
    private static final long[] m308copyIntoB0L2c(@NotNull long[] $receiver, long[] destination, int destinationOffset, int startIndex, int endIndex) {
        ArraysKt.copyInto($receiver, destination, destinationOffset, startIndex, endIndex);
        return destination;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: copyInto-FUQE5sA$default, reason: not valid java name */
    static /* synthetic */ byte[] m313copyIntoFUQE5sA$default(byte[] $receiver, byte[] destination, int destinationOffset, int startIndex, int endIndex, int i, Object obj) {
        if ((i & 2) != 0) {
            destinationOffset = 0;
        }
        if ((i & 4) != 0) {
            startIndex = 0;
        }
        if ((i & 8) != 0) {
            endIndex = UByteArray.m75getSizeimpl($receiver);
        }
        ArraysKt.copyInto($receiver, destination, destinationOffset, startIndex, endIndex);
        return destination;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: copyInto-FUQE5sA, reason: not valid java name */
    private static final byte[] m312copyIntoFUQE5sA(@NotNull byte[] $receiver, byte[] destination, int destinationOffset, int startIndex, int endIndex) {
        ArraysKt.copyInto($receiver, destination, destinationOffset, startIndex, endIndex);
        return destination;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: copyInto-9-ak10g$default, reason: not valid java name */
    static /* synthetic */ short[] m311copyInto9ak10g$default(short[] $receiver, short[] destination, int destinationOffset, int startIndex, int endIndex, int i, Object obj) {
        if ((i & 2) != 0) {
            destinationOffset = 0;
        }
        if ((i & 4) != 0) {
            startIndex = 0;
        }
        if ((i & 8) != 0) {
            endIndex = UShortArray.m274getSizeimpl($receiver);
        }
        ArraysKt.copyInto($receiver, destination, destinationOffset, startIndex, endIndex);
        return destination;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: copyInto-9-ak10g, reason: not valid java name */
    private static final short[] m310copyInto9ak10g(@NotNull short[] $receiver, short[] destination, int destinationOffset, int startIndex, int endIndex) {
        ArraysKt.copyInto($receiver, destination, destinationOffset, startIndex, endIndex);
        return destination;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: copyOf--ajY-9A, reason: not valid java name */
    private static final int[] m316copyOfajY9A(@NotNull int[] $receiver) {
        int[] iArrCopyOf = Arrays.copyOf($receiver, $receiver.length);
        Intrinsics.checkExpressionValueIsNotNull(iArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        return UIntArray.m136constructorimpl(iArrCopyOf);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: copyOf-QwZRm1k, reason: not valid java name */
    private static final long[] m319copyOfQwZRm1k(@NotNull long[] $receiver) {
        long[] jArrCopyOf = Arrays.copyOf($receiver, $receiver.length);
        Intrinsics.checkExpressionValueIsNotNull(jArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        return ULongArray.m203constructorimpl(jArrCopyOf);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: copyOf-GBYM_sE, reason: not valid java name */
    private static final byte[] m317copyOfGBYM_sE(@NotNull byte[] $receiver) {
        byte[] bArrCopyOf = Arrays.copyOf($receiver, $receiver.length);
        Intrinsics.checkExpressionValueIsNotNull(bArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        return UByteArray.m69constructorimpl(bArrCopyOf);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: copyOf-rL5Bavg, reason: not valid java name */
    private static final short[] m323copyOfrL5Bavg(@NotNull short[] $receiver) {
        short[] sArrCopyOf = Arrays.copyOf($receiver, $receiver.length);
        Intrinsics.checkExpressionValueIsNotNull(sArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        return UShortArray.m268constructorimpl(sArrCopyOf);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: copyOf-qFRl0hI, reason: not valid java name */
    private static final int[] m321copyOfqFRl0hI(@NotNull int[] $receiver, int newSize) {
        int[] iArrCopyOf = Arrays.copyOf($receiver, newSize);
        Intrinsics.checkExpressionValueIsNotNull(iArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
        return UIntArray.m136constructorimpl(iArrCopyOf);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: copyOf-r7IrZao, reason: not valid java name */
    private static final long[] m322copyOfr7IrZao(@NotNull long[] $receiver, int newSize) {
        long[] jArrCopyOf = Arrays.copyOf($receiver, newSize);
        Intrinsics.checkExpressionValueIsNotNull(jArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
        return ULongArray.m203constructorimpl(jArrCopyOf);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: copyOf-PpDY95g, reason: not valid java name */
    private static final byte[] m318copyOfPpDY95g(@NotNull byte[] $receiver, int newSize) {
        byte[] bArrCopyOf = Arrays.copyOf($receiver, newSize);
        Intrinsics.checkExpressionValueIsNotNull(bArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
        return UByteArray.m69constructorimpl(bArrCopyOf);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: copyOf-nggk6HY, reason: not valid java name */
    private static final short[] m320copyOfnggk6HY(@NotNull short[] $receiver, int newSize) {
        short[] sArrCopyOf = Arrays.copyOf($receiver, newSize);
        Intrinsics.checkExpressionValueIsNotNull(sArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
        return UShortArray.m268constructorimpl(sArrCopyOf);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: copyOfRange-oBK06Vg, reason: not valid java name */
    private static final int[] m327copyOfRangeoBK06Vg(@NotNull int[] $receiver, int fromIndex, int toIndex) {
        int[] iArrCopyOfRange;
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            iArrCopyOfRange = ArraysKt.copyOfRange($receiver, fromIndex, toIndex);
        } else {
            if (toIndex > $receiver.length) {
                throw new IndexOutOfBoundsException("toIndex: " + toIndex + ", size: " + $receiver.length);
            }
            iArrCopyOfRange = Arrays.copyOfRange($receiver, fromIndex, toIndex);
            Intrinsics.checkExpressionValueIsNotNull(iArrCopyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
        }
        return UIntArray.m136constructorimpl(iArrCopyOfRange);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: copyOfRange--nroSd4, reason: not valid java name */
    private static final long[] m324copyOfRangenroSd4(@NotNull long[] $receiver, int fromIndex, int toIndex) {
        long[] jArrCopyOfRange;
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            jArrCopyOfRange = ArraysKt.copyOfRange($receiver, fromIndex, toIndex);
        } else {
            if (toIndex > $receiver.length) {
                throw new IndexOutOfBoundsException("toIndex: " + toIndex + ", size: " + $receiver.length);
            }
            jArrCopyOfRange = Arrays.copyOfRange($receiver, fromIndex, toIndex);
            Intrinsics.checkExpressionValueIsNotNull(jArrCopyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
        }
        return ULongArray.m203constructorimpl(jArrCopyOfRange);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: copyOfRange-4UcCI2c, reason: not valid java name */
    private static final byte[] m325copyOfRange4UcCI2c(@NotNull byte[] $receiver, int fromIndex, int toIndex) {
        byte[] bArrCopyOfRange;
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            bArrCopyOfRange = ArraysKt.copyOfRange($receiver, fromIndex, toIndex);
        } else {
            if (toIndex > $receiver.length) {
                throw new IndexOutOfBoundsException("toIndex: " + toIndex + ", size: " + $receiver.length);
            }
            bArrCopyOfRange = Arrays.copyOfRange($receiver, fromIndex, toIndex);
            Intrinsics.checkExpressionValueIsNotNull(bArrCopyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
        }
        return UByteArray.m69constructorimpl(bArrCopyOfRange);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: copyOfRange-Aa5vz7o, reason: not valid java name */
    private static final short[] m326copyOfRangeAa5vz7o(@NotNull short[] $receiver, int fromIndex, int toIndex) {
        short[] sArrCopyOfRange;
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            sArrCopyOfRange = ArraysKt.copyOfRange($receiver, fromIndex, toIndex);
        } else {
            if (toIndex > $receiver.length) {
                throw new IndexOutOfBoundsException("toIndex: " + toIndex + ", size: " + $receiver.length);
            }
            sArrCopyOfRange = Arrays.copyOfRange($receiver, fromIndex, toIndex);
            Intrinsics.checkExpressionValueIsNotNull(sArrCopyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
        }
        return UShortArray.m268constructorimpl(sArrCopyOfRange);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: toByteArray-GBYM_sE, reason: not valid java name */
    private static final byte[] m336toByteArrayGBYM_sE(@NotNull byte[] $receiver) {
        byte[] bArrCopyOf = Arrays.copyOf($receiver, $receiver.length);
        Intrinsics.checkExpressionValueIsNotNull(bArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        return bArrCopyOf;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: toIntArray--ajY-9A, reason: not valid java name */
    private static final int[] m337toIntArrayajY9A(@NotNull int[] $receiver) {
        int[] iArrCopyOf = Arrays.copyOf($receiver, $receiver.length);
        Intrinsics.checkExpressionValueIsNotNull(iArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        return iArrCopyOf;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: toLongArray-QwZRm1k, reason: not valid java name */
    private static final long[] m338toLongArrayQwZRm1k(@NotNull long[] $receiver) {
        long[] jArrCopyOf = Arrays.copyOf($receiver, $receiver.length);
        Intrinsics.checkExpressionValueIsNotNull(jArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        return jArrCopyOf;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* JADX INFO: renamed from: toShortArray-rL5Bavg, reason: not valid java name */
    private static final short[] m339toShortArrayrL5Bavg(@NotNull short[] $receiver) {
        short[] sArrCopyOf = Arrays.copyOf($receiver, $receiver.length);
        Intrinsics.checkExpressionValueIsNotNull(sArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        return sArrCopyOf;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: toTypedArray--ajY-9A, reason: not valid java name */
    public static final UInt[] m340toTypedArrayajY9A(@NotNull int[] receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        int size$iv = UIntArray.m142getSizeimpl(receiver$0);
        UInt[] uIntArr = new UInt[size$iv];
        int length = uIntArr.length;
        for (int i$iv = 0; i$iv < length; i$iv++) {
            int index = i$iv;
            uIntArr[i$iv] = UInt.m86boximpl(UIntArray.m141getimpl(receiver$0, index));
        }
        return uIntArr;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: toTypedArray-QwZRm1k, reason: not valid java name */
    public static final ULong[] m342toTypedArrayQwZRm1k(@NotNull long[] receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        int size$iv = ULongArray.m209getSizeimpl(receiver$0);
        ULong[] uLongArr = new ULong[size$iv];
        int length = uLongArr.length;
        for (int i$iv = 0; i$iv < length; i$iv++) {
            int index = i$iv;
            uLongArr[i$iv] = ULong.m153boximpl(ULongArray.m208getimpl(receiver$0, index));
        }
        return uLongArr;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: toTypedArray-GBYM_sE, reason: not valid java name */
    public static final UByte[] m341toTypedArrayGBYM_sE(@NotNull byte[] receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        int size$iv = UByteArray.m75getSizeimpl(receiver$0);
        UByte[] uByteArr = new UByte[size$iv];
        int length = uByteArr.length;
        for (int i$iv = 0; i$iv < length; i$iv++) {
            int index = i$iv;
            uByteArr[i$iv] = UByte.m21boximpl(UByteArray.m74getimpl(receiver$0, index));
        }
        return uByteArr;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* JADX INFO: renamed from: toTypedArray-rL5Bavg, reason: not valid java name */
    public static final UShort[] m343toTypedArrayrL5Bavg(@NotNull short[] receiver$0) {
        Intrinsics.checkParameterIsNotNull(receiver$0, "receiver$0");
        int size$iv = UShortArray.m274getSizeimpl(receiver$0);
        UShort[] uShortArr = new UShort[size$iv];
        int length = uShortArr.length;
        for (int i$iv = 0; i$iv < length; i$iv++) {
            int index = i$iv;
            uShortArr[i$iv] = UShort.m220boximpl(UShortArray.m273getimpl(receiver$0, index));
        }
        return uShortArr;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final byte[] toUByteArray(@NotNull byte[] $receiver) {
        byte[] bArrCopyOf = Arrays.copyOf($receiver, $receiver.length);
        Intrinsics.checkExpressionValueIsNotNull(bArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        return UByteArray.m69constructorimpl(bArrCopyOf);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final int[] toUIntArray(@NotNull int[] $receiver) {
        int[] iArrCopyOf = Arrays.copyOf($receiver, $receiver.length);
        Intrinsics.checkExpressionValueIsNotNull(iArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        return UIntArray.m136constructorimpl(iArrCopyOf);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final long[] toULongArray(@NotNull long[] $receiver) {
        long[] jArrCopyOf = Arrays.copyOf($receiver, $receiver.length);
        Intrinsics.checkExpressionValueIsNotNull(jArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        return ULongArray.m203constructorimpl(jArrCopyOf);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final short[] toUShortArray(@NotNull short[] $receiver) {
        short[] sArrCopyOf = Arrays.copyOf($receiver, $receiver.length);
        Intrinsics.checkExpressionValueIsNotNull(sArrCopyOf, "java.util.Arrays.copyOf(this, size)");
        return UShortArray.m268constructorimpl(sArrCopyOf);
    }
}
