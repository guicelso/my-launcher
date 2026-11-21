package com.unisoc.engineermode.core.utils;

import java.util.function.Consumer;

/* JADX INFO: renamed from: com.unisoc.engineermode.core.utils.-$$Lambda$SocketUtils$dsoT85lKFERmCxyisDxy4Q_qQUE, reason: invalid class name */
/* JADX INFO: compiled from: lambda */
/* JADX INFO: loaded from: classes4.dex */
public final /* synthetic */ class $$Lambda$SocketUtils$dsoT85lKFERmCxyisDxy4Q_qQUE implements Consumer {
    public static final /* synthetic */ $$Lambda$SocketUtils$dsoT85lKFERmCxyisDxy4Q_qQUE INSTANCE = new $$Lambda$SocketUtils$dsoT85lKFERmCxyisDxy4Q_qQUE();

    private /* synthetic */ $$Lambda$SocketUtils$dsoT85lKFERmCxyisDxy4Q_qQUE() {
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        SocketUtils.timeout(obj);
    }
}
