package com.dofun.carsetting.e.b;

import android.content.Context;
import java.io.File;

/* JADX INFO: compiled from: SystemUpdateT3L.java */
/* JADX INFO: loaded from: classes.dex */
public class c extends a {
    public c() {
        this.b = new String[]{"4125127e-3771-11e6-be69-23ec313d5c09", "423bf380-3771-11e6-96be-774437fe570c", "43136f04-3771-11e6-ba7b-df841fc82b04", "4404658a-dc6c-11e6-99e7-c363ed99c4b9", "ab6475a6-982d-11e4-887a-000c29ba27c0", "bd07a5ee-fbb0-11e4-ae78-000c29ba27c0"};
    }

    @Override // com.dofun.carsetting.e.b.a
    public void a(Context context, com.dofun.carsetting.f.u uVar) {
        try {
            context.deleteDatabase("tecarinfo.db");
        } catch (Exception unused) {
        }
        uVar.write(40730, 1, 0, "newfs_msdos -F 32 -O android -c 8 -L sysrecovery /dev/block/by-name/sysrecovery");
        uVar.write(40730, 1, 0, "mkdir /data/tw/sysrecovery");
        uVar.write(40730, 1, 0, "mount -t vfat /dev/block/by-name/sysrecovery /data/tw/sysrecovery");
        for (File file : this.g) {
            uVar.write(40730, 1, 0, "cp " + file.getAbsolutePath() + " /data/tw/sysrecovery");
        }
        uVar.write(40730, 3, 0, "xcheck /data/tw/sysrecovery/423bf380-3771-11e6-96be-774437fe570c /data/tw/sysrecovery/43136f04-3771-11e6-ba7b-df841fc82b04");
        uVar.write(40730, 1, 0, "umount /data/tw/sysrecovery");
        uVar.write(40730, 1, 0, "rm -r /data/tw/sysrecovery");
        uVar.write(40730, 3, 0, "rm /data/tw/config");
        uVar.write(65282, 0, 0, "xupdate");
    }
}
