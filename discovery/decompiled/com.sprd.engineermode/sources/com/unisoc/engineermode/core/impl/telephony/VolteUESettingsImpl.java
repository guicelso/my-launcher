package com.unisoc.engineermode.core.impl.telephony;

import android.util.Log;
import com.unisoc.engineermode.core.common.engconstents;
import com.unisoc.engineermode.core.exception.ErrorCode;
import com.unisoc.engineermode.core.exception.OperationFailedException;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.utils.IATUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes4.dex */
public class VolteUESettingsImpl implements ITelephonyApi.IVolteUeSettings {
    private static final int SIM0 = 0;
    private static final String TAG = "VolteUESettingsImpl";
    private Map<ITelephonyApi.IVolteUeSettings.VoiceCodec, Integer> voiceCodecValueMap = new EnumMap(ITelephonyApi.IVolteUeSettings.VoiceCodec.class);
    private Map<ITelephonyApi.IVolteUeSettings.VideoCodec, Integer> videoCodecValueMap = new EnumMap(ITelephonyApi.IVolteUeSettings.VideoCodec.class);
    private Map<ITelephonyApi.IVolteUeSettings.Bandwidth, Integer> bandwidthValueMap = new EnumMap(ITelephonyApi.IVolteUeSettings.Bandwidth.class);
    private Map<ITelephonyApi.IVolteUeSettings.Bitrate, Integer> bitrateValueMap = new EnumMap(ITelephonyApi.IVolteUeSettings.Bitrate.class);
    private Map<ITelephonyApi.IVolteUeSettings.SmsPdu, Integer> smsPduValueMap = new EnumMap(ITelephonyApi.IVolteUeSettings.SmsPdu.class);
    private Pattern pattern = Pattern.compile("\\+SPVOLTEENG: [0-9]+,\"(.*)\"\r\nOK\r\n");

    public VolteUESettingsImpl() {
        this.voiceCodecValueMap.put(ITelephonyApi.IVolteUeSettings.VoiceCodec.EVS, 1);
        this.voiceCodecValueMap.put(ITelephonyApi.IVolteUeSettings.VoiceCodec.AMR_WB, 2);
        this.voiceCodecValueMap.put(ITelephonyApi.IVolteUeSettings.VoiceCodec.AMR_NB, 4);
        this.videoCodecValueMap.put(ITelephonyApi.IVolteUeSettings.VideoCodec.H264, 1);
        this.videoCodecValueMap.put(ITelephonyApi.IVolteUeSettings.VideoCodec.H265, 2);
        int i = 0;
        for (ITelephonyApi.IVolteUeSettings.Bandwidth bw : ITelephonyApi.IVolteUeSettings.Bandwidth.values()) {
            this.bandwidthValueMap.put(bw, Integer.valueOf(i));
            i++;
        }
        int i2 = 0;
        for (ITelephonyApi.IVolteUeSettings.Bitrate br : ITelephonyApi.IVolteUeSettings.Bitrate.values()) {
            this.bitrateValueMap.put(br, Integer.valueOf(i2));
            i2++;
        }
        int i3 = 0;
        for (ITelephonyApi.IVolteUeSettings.SmsPdu sp : ITelephonyApi.IVolteUeSettings.SmsPdu.values()) {
            this.smsPduValueMap.put(sp, Integer.valueOf(i3));
            i3++;
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteUeSettings
    public ITelephonyApi.IVolteUeSettings.VoiceCodec[] getVoiceCodecType() {
        String result = IATUtils.sendATCmd("AT+SPVOLTEENG=6,0", 0);
        if (result.contains("OK")) {
            return (ITelephonyApi.IVolteUeSettings.VoiceCodec[]) getTypes(result, this.voiceCodecValueMap).toArray(new ITelephonyApi.IVolteUeSettings.VoiceCodec[0]);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, result);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteUeSettings
    public void setVoiceCodecType(ITelephonyApi.IVolteUeSettings.VoiceCodec[] voiceCodecs) {
        Log.d(TAG, "setVoiceCodecType, " + Arrays.toString(Arrays.stream(voiceCodecs).map(new Function() { // from class: com.unisoc.engineermode.core.impl.telephony.-$$Lambda$IaTXTxr1U3etuDswdH-yhMQHw-Q
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ITelephonyApi.IVolteUeSettings.VoiceCodec) obj).name();
            }
        }).toArray()));
        int codeValue = getValues(voiceCodecs, this.voiceCodecValueMap);
        String atCmd = String.format("%s6,1,\"%d\"", engconstents.ENG_AT_SPVOLTEENG, Integer.valueOf(codeValue));
        String atRsp = IATUtils.sendATCmd(atCmd, 0);
        if (!atRsp.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRsp);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteUeSettings
    public ITelephonyApi.IVolteUeSettings.VideoCodec[] getVideoCodecType() {
        String result = IATUtils.sendATCmd("AT+SPVOLTEENG=9,0", 0);
        if (result.contains("OK")) {
            return (ITelephonyApi.IVolteUeSettings.VideoCodec[]) getTypes(result, this.videoCodecValueMap).toArray(new ITelephonyApi.IVolteUeSettings.VideoCodec[0]);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, result);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteUeSettings
    public void setVideoCodecType(ITelephonyApi.IVolteUeSettings.VideoCodec[] videoCodecs) {
        Log.d(TAG, "setVideoCodecType, " + Arrays.toString(Arrays.stream(videoCodecs).map(new Function() { // from class: com.unisoc.engineermode.core.impl.telephony.-$$Lambda$bkHQL0ieefWkuAH59tLXyNviB3k
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ITelephonyApi.IVolteUeSettings.VideoCodec) obj).name();
            }
        }).toArray()));
        int codeValue = getValues(videoCodecs, this.videoCodecValueMap);
        String atCmd = String.format("%s9,1,\"%d\"", engconstents.ENG_AT_SPVOLTEENG, Integer.valueOf(codeValue));
        String atRsp = IATUtils.sendATCmd(atCmd, 0);
        if (!atRsp.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, atRsp);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteUeSettings
    public ITelephonyApi.IVolteUeSettings.Bandwidth getMaxBandWidth() {
        String result = IATUtils.sendATCmd("AT+SPVOLTEENG=7,0", 0);
        if (result.contains("OK")) {
            return (ITelephonyApi.IVolteUeSettings.Bandwidth) getType(result, this.bandwidthValueMap);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, result);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteUeSettings
    public void setMaxBandWidth(ITelephonyApi.IVolteUeSettings.Bandwidth bandwidth) {
        int value = getValue(bandwidth, this.bandwidthValueMap);
        String at = "AT+SPVOLTEENG=7,1,\"" + value + "\"";
        String result = IATUtils.sendATCmd(at, 0);
        if (!result.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, result);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteUeSettings
    public ITelephonyApi.IVolteUeSettings.Bitrate getMaxBitRate() {
        String result = IATUtils.sendATCmd("AT+SPVOLTEENG=8,0", 0);
        if (result.contains("OK")) {
            return (ITelephonyApi.IVolteUeSettings.Bitrate) getType(result, this.bitrateValueMap);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, result);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteUeSettings
    public void setMaxBitRate(ITelephonyApi.IVolteUeSettings.Bitrate bitRate) {
        int value = getValue(bitRate, this.bitrateValueMap);
        String at = "AT+SPVOLTEENG=8,1,\"" + value + "\"";
        String result = IATUtils.sendATCmd(at, 0);
        if (!result.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, result);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteUeSettings
    public ITelephonyApi.IVolteUeSettings.SmsPdu getSmsPdu() {
        String result = IATUtils.sendATCmd("AT+SPVOLTEENG=19,0", 0);
        if (result.contains("OK")) {
            return (ITelephonyApi.IVolteUeSettings.SmsPdu) getType(result, this.smsPduValueMap);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, result);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteUeSettings
    public void setSmsPdu(ITelephonyApi.IVolteUeSettings.SmsPdu pdu) {
        int value = getValue(pdu, this.smsPduValueMap);
        String at = "AT+SPVOLTEENG=19,1,\"" + value + "\"";
        String result = IATUtils.sendATCmd(at, 0);
        if (!result.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, result);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteUeSettings
    public String getSprdVolte() {
        String cmd = String.format("%s1,0", engconstents.ENG_AT_SPVOLTEENG);
        String result = IATUtils.sendATCmd(cmd, 0);
        if (result.contains("OK")) {
            return extractValue(result);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, result);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteUeSettings
    public void setSprdVolte(String value) {
        String cmd = String.format("%s1,1,\"%s\"", engconstents.ENG_AT_SPVOLTEENG, value);
        String result = IATUtils.sendATCmd(cmd, 0);
        if (!result.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, result);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteUeSettings
    public boolean getVideoCallState() {
        String result = IATUtils.sendATCmd("AT+SPVOLTEENG=3,0", 0);
        if (result.contains("OK")) {
            return getState(result);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, result);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteUeSettings
    public void setVideoCallState(boolean state) {
        String value = state ? "1" : "0";
        String at = "AT+SPVOLTEENG=3,1,\"" + value + "\"";
        String result = IATUtils.sendATCmd(at, 0);
        if (!result.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, result);
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteUeSettings
    public boolean getVideoConferenceState() {
        String result = IATUtils.sendATCmd("AT+SPVOLTEENG=5,0", 0);
        if (result.contains("OK")) {
            return getState(result);
        }
        throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, result);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi.IVolteUeSettings
    public void setVideoConferenceState(boolean state) {
        String value = state ? "1" : "0";
        String at = "AT+SPVOLTEENG=5,1,\"" + value + "\"";
        String result = IATUtils.sendATCmd(at, 0);
        if (!result.contains("OK")) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, result);
        }
    }

    private String extractValue(String result) {
        Matcher matcher = this.pattern.matcher(result);
        if (!matcher.find() || matcher.group(1) == null) {
            throw new OperationFailedException(ErrorCode.AT_RETURN_ERROR, result);
        }
        return matcher.group(1);
    }

    private boolean getState(String result) {
        String value = extractValue(result);
        if (value.equals("0")) {
            return false;
        }
        if (value.equals("1")) {
            return true;
        }
        throw new OperationFailedException("get state failed," + result);
    }

    private <T extends Enum<T>> List<T> getTypes(String result, Map<T, Integer> typeValueMap) {
        List<T> types = new ArrayList<>();
        try {
            int value = Integer.parseInt(extractValue(result));
            for (Map.Entry<T, Integer> entry : typeValueMap.entrySet()) {
                if ((entry.getValue().intValue() & value) != 0) {
                    types.add(entry.getKey());
                }
            }
            Log.d(TAG, "get codes: " + ((String) types.stream().map(new Function() { // from class: com.unisoc.engineermode.core.impl.telephony.-$$Lambda$88QtgPxQFXNxl4bW9yOd8W80Jg4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((Enum) obj).name();
                }
            }).reduce(new BinaryOperator() { // from class: com.unisoc.engineermode.core.impl.telephony.-$$Lambda$VolteUESettingsImpl$u96jJE2gsUgLRHSmNLVRBN-BEdg
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return VolteUESettingsImpl.lambda$getTypes$0((String) obj, (String) obj2);
                }
            }).orElse("")));
            return types;
        } catch (NumberFormatException e) {
            throw new OperationFailedException("value is not number," + result);
        }
    }

    static /* synthetic */ String lambda$getTypes$0(String t, String u) {
        return t + "," + u;
    }

    private <T extends Enum<T>> T getType(String result, Map<T, Integer> typeValueMap) {
        try {
            int value = Integer.parseInt(extractValue(result));
            for (Map.Entry<T, Integer> entry : typeValueMap.entrySet()) {
                if (value == entry.getValue().intValue()) {
                    Log.d(TAG, "get code: " + entry.getKey().name());
                    return entry.getKey();
                }
            }
            throw new OperationFailedException("get value failed," + result);
        } catch (NumberFormatException e) {
            throw new OperationFailedException("value is not number," + result);
        }
    }

    private <T extends Enum<T>> int getValues(T[] types, Map<T, Integer> typeValueMap) {
        int value = 0;
        for (T t : types) {
            Integer val = typeValueMap.get(t);
            if (val != null) {
                value |= val.intValue();
            }
        }
        return value;
    }

    private <T extends Enum<T>> int getValue(T type, Map<T, Integer> typeValueMap) {
        Integer value = typeValueMap.get(type);
        if (value == null) {
            throw new OperationFailedException("get value failed," + type.name());
        }
        return value.intValue();
    }
}
