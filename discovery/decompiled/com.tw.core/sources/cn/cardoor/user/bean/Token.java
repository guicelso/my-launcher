package cn.cardoor.user.bean;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class Token implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: cn.cardoor.user.bean.Token.1
        @Override // android.os.Parcelable.Creator
        public Token createFromParcel(Parcel parcel) {
            return new Token(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public Token[] newArray(int i) {
            return new Token[i];
        }
    };
    private int expireTime;
    private String refreshToken;
    private String token;
    private long tokenTime;

    public Token() {
    }

    protected Token(Parcel parcel) {
        this.refreshToken = parcel.readString();
        this.token = parcel.readString();
        this.tokenTime = parcel.readLong();
        this.expireTime = parcel.readInt();
    }

    public static Token json2Bean(JSONObject jSONObject) {
        String strOptString = jSONObject.optString("refreshToken");
        String strOptString2 = jSONObject.optString("token");
        int iOptInt = jSONObject.optInt("expireTime");
        return new Token().setRefreshToken(strOptString).setToken(strOptString2).setExpireTime(iOptInt).setTokenTime(jSONObject.optLong("tokenTime"));
    }

    public JSONObject convert2Json() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("refreshToken", this.refreshToken);
            jSONObject.put("token", this.token);
            jSONObject.put("expireTime", this.expireTime);
            jSONObject.put("tokenTime", this.tokenTime);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getExpireTime() {
        return this.expireTime;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public String getToken() {
        return this.token;
    }

    public long getTokenTime() {
        return this.tokenTime;
    }

    public Token setExpireTime(int i) {
        this.expireTime = i;
        return this;
    }

    public Token setRefreshToken(String str) {
        this.refreshToken = str;
        return this;
    }

    public Token setToken(String str) {
        this.token = str;
        return this;
    }

    public Token setTokenTime(long j) {
        this.tokenTime = j;
        return this;
    }

    public String toString() {
        return "Token{refreshToken='" + this.refreshToken + "', token='" + this.token + "', expireTime=" + this.expireTime + ", tokenTime=" + this.tokenTime + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.refreshToken);
        parcel.writeString(this.token);
        parcel.writeLong(this.tokenTime);
        parcel.writeInt(this.expireTime);
    }
}
