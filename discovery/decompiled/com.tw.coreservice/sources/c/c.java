package c;

import java.util.Objects;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class c implements d.g.a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ d f13a;

    public c(d dVar) {
        this.f13a = dVar;
    }

    @Override // d.g.a
    public final void a(String str) {
        d.c.c("CoreUpgradeWorker", "upgrade failure %s", str);
    }

    @Override // d.g.a
    public final void b(String str) {
        JSONObject jSONObjectOptJSONObject;
        d dVar = this.f13a;
        Objects.requireNonNull(dVar);
        try {
            d.c.b("CoreUpgradeWorker", "core upgrade response %s", str);
            JSONObject jSONObject = new JSONObject(str);
            if (!"0".equals(jSONObject.optString("code")) || (jSONObjectOptJSONObject = jSONObject.optJSONObject("data")) == null) {
                return;
            }
            dVar.d(jSONObjectOptJSONObject.optString("path"), jSONObjectOptJSONObject.optString("flag"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // d.g.a
    public final void c() {
    }
}
