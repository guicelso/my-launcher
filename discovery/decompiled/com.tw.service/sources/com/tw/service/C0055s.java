package com.tw.service;

import android.net.ConnectivityManager;
import android.util.Log;

/* JADX INFO: renamed from: com.tw.service.s, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
class C0055s extends ConnectivityManager.OnStartTetheringCallback {
    C0055s(TWService tWService) {
    }

    public void onTetheringFailed() {
        Log.d("TWService", "onTetheringFailed");
    }

    public void onTetheringStarted() {
        Log.d("TWService", "onTetheringStarted");
    }
}
