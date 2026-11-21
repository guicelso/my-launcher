package com.unisoc.engineermode.core.sample;

import android.util.Log;
import com.unisoc.engineermode.core.factory.BaseInterface;
import com.unisoc.engineermode.core.factory.ImplementationFactory;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: KotlinTest.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/unisoc/engineermode/core/sample/KotlinTest;", "", "()V", "TAG", "", "kotlinApi", "Lcom/unisoc/engineermode/core/sample/KotlinInterface;", "test", "", "name"}, k = 1, mv = {1, 1, 13})
public final class KotlinTest {
    public static final KotlinTest INSTANCE = new KotlinTest();

    @NotNull
    public static final String TAG = "KOTLINTEST";

    private KotlinTest() {
    }

    @NotNull
    public final KotlinInterface kotlinApi() {
        BaseInterface baseInterfaceCreate = ImplementationFactory.create((KClass<? extends BaseInterface>) Reflection.getOrCreateKotlinClass(KotlinInterface.class));
        if (baseInterfaceCreate != null) {
            return (KotlinInterface) baseInterfaceCreate;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.unisoc.engineermode.core.sample.KotlinInterface");
    }

    public final void test() {
        Log.d(TAG, kotlinApi().self());
    }
}
