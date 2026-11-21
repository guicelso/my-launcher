package com.tw.core.h;

import com.tw.core.utils.B;

/* JADX INFO: loaded from: classes.dex */
class i implements Runnable {
    final /* synthetic */ b js;

    i(b bVar) {
        this.js = bVar;
    }

    /* JADX INFO: Infinite loop detected, blocks: 21, insns: 0 */
    @Override // java.lang.Runnable
    public void run() {
        while (true) {
            B.ci("MqttClientManager", "check emq connect state", new Object[0]);
            try {
                long j = ((long) (this.js.iZ.get() * 5)) * 1000;
                if (j >= 120000) {
                    j = 120000;
                }
                Thread.sleep(j);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!this.js.ja && !this.js.isConnected()) {
                this.js.nQ();
                this.js.iZ.incrementAndGet();
            }
        }
    }
}
