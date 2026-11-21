package com.unisoc.engineermode.core.impl.debuglog;

import android.annotation.SuppressLint;
import android.support.v4.os.EnvironmentCompat;
import android.telephony.CellIdentityLte;
import android.telephony.CellInfo;
import android.telephony.CellInfoLte;
import android.telephony.CellLocation;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.Log;
import com.unisoc.engineermode.core.common.CommonKt;
import com.unisoc.engineermode.core.impl.nonpublic.DmykAbsTelephonyManagerProxy;
import com.unisoc.engineermode.core.impl.nonpublic.ServiceStateProxy;
import com.unisoc.engineermode.core.impl.nonpublic.StorageManagerProxy;
import com.unisoc.engineermode.core.impl.nonpublic.SubscriptionManagerProxy;
import com.unisoc.engineermode.core.impl.nonpublic.SystemPropertiesProxy;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerExProxy;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;
import com.unisoc.engineermode.core.intf.IPhoneInfo;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.reflect.KProperty;
import kotlin.text.Regex;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: PhoneInfoImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\t\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\nH\u0002J\u000e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00040\u0018H\u0017J\u000e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00040\u0018H\u0016J\u000e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0018H\u0016J\u000e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00040\u0018H\u0016J\u000e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u0018H\u0016J\u000e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\n0\u0018H\u0017J\b\u0010 \u001a\u00020\u0004H\u0016J\b\u0010!\u001a\u00020\u0004H\u0016J\u000e\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00040\u0018H\u0016J\b\u0010#\u001a\u00020\u0004H\u0016J\b\u0010$\u001a\u00020\u0004H\u0017J\b\u0010%\u001a\u00020\u0004H\u0016J\b\u0010&\u001a\u00020\u0004H\u0016J\b\u0010'\u001a\u00020\u0004H\u0016J\u0018\u0010(\u001a\u00020\u00042\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u0015H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u000e\u001a\u0004\b\u0011\u0010\u0012¨\u0006,"}, d2 = {"Lcom/unisoc/engineermode/core/impl/debuglog/PhoneInfoImpl;", "Lcom/unisoc/engineermode/core/intf/IPhoneInfo;", "()V", "HARDWARE_VERSION_PROPERTY_NAME", "", "OPERATION_SYSTEM_VERSION_PROPERTY_NAME", "SOFTWARE_VERSION_PROPERTY_NAME", "SPRD_RAM_SIZE", "TAG", "phoneCount", "", "getPhoneCount", "()I", "phoneCount$delegate", "Lkotlin/Lazy;", "telephoneMgr", "Landroid/telephony/TelephonyManager;", "getTelephoneMgr", "()Landroid/telephony/TelephonyManager;", "telephoneMgr$delegate", "cardExists", "", "slot", "getAllImei", "", "getAllImsi", "getAllOpers", "Lcom/unisoc/engineermode/core/intf/IPhoneInfo$Operator;", "getCdmaImsi", "getCdmaInfo", "Lcom/unisoc/engineermode/core/intf/IPhoneInfo$CdmaInfo;", "getCellId", "getEsn", "getHardwareVersion", "getIccId", "getInternalStorageSize", "getMeid", "getMemorySize", "getOsVersion", "getSoftwareVersion", "humanReadableByteCount", "bytes", "", "si", "name"}, k = 1, mv = {1, 1, 13})
public final class PhoneInfoImpl implements IPhoneInfo {
    private static final String HARDWARE_VERSION_PROPERTY_NAME = "ro.boot.hardware.revision";
    private static final String OPERATION_SYSTEM_VERSION_PROPERTY_NAME = "ro.build.version.release";
    private static final String SOFTWARE_VERSION_PROPERTY_NAME = "ro.version.software";
    private static final String SPRD_RAM_SIZE = "ro.boot.ddrsize";
    private static final String TAG = "PHONEINFO";
    static final /* synthetic */ KProperty[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(PhoneInfoImpl.class), "telephoneMgr", "getTelephoneMgr()Landroid/telephony/TelephonyManager;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(PhoneInfoImpl.class), "phoneCount", "getPhoneCount()I"))};
    public static final PhoneInfoImpl INSTANCE = new PhoneInfoImpl();

    /* JADX INFO: renamed from: telephoneMgr$delegate, reason: from kotlin metadata */
    private static final Lazy telephoneMgr = LazyKt.lazy(new Function0<TelephonyManager>() { // from class: com.unisoc.engineermode.core.impl.debuglog.PhoneInfoImpl$telephoneMgr$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        @NotNull
        public final TelephonyManager invoke() {
            Object systemService = CommonKt.getAppCtx().getSystemService("phone");
            if (systemService != null) {
                return (TelephonyManager) systemService;
            }
            throw new TypeCastException("null cannot be cast to non-null type android.telephony.TelephonyManager");
        }
    });

    /* JADX INFO: renamed from: phoneCount$delegate, reason: from kotlin metadata */
    private static final Lazy phoneCount = LazyKt.lazy(new Function0<Integer>() { // from class: com.unisoc.engineermode.core.impl.debuglog.PhoneInfoImpl$phoneCount$2
        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Integer invoke() {
            return Integer.valueOf(invoke2());
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final int invoke2() {
            return PhoneInfoImpl.INSTANCE.getTelephoneMgr().getPhoneCount();
        }
    });

    private final int getPhoneCount() {
        Lazy lazy = phoneCount;
        KProperty kProperty = $$delegatedProperties[1];
        return ((Number) lazy.getValue()).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final TelephonyManager getTelephoneMgr() {
        Lazy lazy = telephoneMgr;
        KProperty kProperty = $$delegatedProperties[0];
        return (TelephonyManager) lazy.getValue();
    }

    private PhoneInfoImpl() {
    }

    @Override // com.unisoc.engineermode.core.intf.IPhoneInfo
    @NotNull
    public String getMemorySize() throws IllegalAccessException, InvocationTargetException {
        String size = SystemPropertiesProxy.get(SPRD_RAM_SIZE, "");
        if (Intrinsics.areEqual("", size)) {
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        Log.d(TAG, "ram property value is:" + size);
        long j = (long) 1024;
        return humanReadableByteCount(Long.parseLong(new Regex("[^0-9]").replace(size, "")) * j * j, false);
    }

    @Override // com.unisoc.engineermode.core.intf.IPhoneInfo
    @NotNull
    public List<IPhoneInfo.Operator> getAllOpers() throws IllegalAccessException, InvocationTargetException {
        Log.d(TAG, "get mcc and mnc");
        int phoneCount2 = getPhoneCount();
        ArrayList arrayList = new ArrayList(phoneCount2);
        for (int i = 0; i < phoneCount2; i++) {
            arrayList.add(new IPhoneInfo.Operator("0", "0"));
        }
        ArrayList operList = arrayList;
        ArrayList $receiver$iv = operList;
        int i2 = 0;
        for (Object item$iv : $receiver$iv) {
            int index$iv = i2 + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IPhoneInfo.Operator oper = (IPhoneInfo.Operator) item$iv;
            if (!INSTANCE.cardExists(i2)) {
                Log.w(TAG, "not insert sim card i=:" + i2);
            } else {
                String operatorNumberic = TelephonyManagerProxy.getSimOperatorNumericForPhone(i2);
                if (TextUtils.isEmpty(operatorNumberic)) {
                    Log.e(TAG, "get mcc and mnc failed");
                } else {
                    if (operatorNumberic == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    String strSubstring = operatorNumberic.substring(0, 3);
                    Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                    oper.setMcc(strSubstring);
                    String strSubstring2 = operatorNumberic.substring(3);
                    Intrinsics.checkExpressionValueIsNotNull(strSubstring2, "(this as java.lang.String).substring(startIndex)");
                    oper.setMnc(strSubstring2);
                }
            }
            i2 = index$iv;
        }
        Log.d(TAG, CollectionsKt.joinToString$default(operList, null, null, null, 0, null, null, 63, null));
        return operList;
    }

    @Override // com.unisoc.engineermode.core.intf.IPhoneInfo
    @NotNull
    public List<String> getAllImsi() {
        Log.d(TAG, "get all IMSI");
        int phoneCount2 = getPhoneCount();
        ArrayList arrayList = new ArrayList(phoneCount2);
        for (int i = 0; i < phoneCount2; i++) {
            arrayList.add("");
        }
        ArrayList imsiList = arrayList;
        int phoneCount3 = getPhoneCount();
        for (int i2 = 0; i2 < phoneCount3; i2++) {
            if (!cardExists(i2)) {
                Log.d(TAG, "not insert sim card i=" + i2);
            } else {
                int[] subId = SubscriptionManagerProxy.getSubId(i2);
                if (subId == null) {
                    Log.e(TAG, "get sub id error, subId==null");
                } else {
                    imsiList.set(i2, TelephonyManagerProxy.getSubscriberId(subId[0]));
                }
            }
        }
        Log.d(TAG, "get all IMSI " + CollectionsKt.joinToString$default(imsiList, null, null, null, 0, null, null, 63, null));
        return imsiList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.unisoc.engineermode.core.intf.IPhoneInfo
    @SuppressLint({"MissingPermission"})
    @NotNull
    public List<Integer> getCellId() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        int baseStationId;
        Log.d(TAG, "get cell id");
        if (getPhoneCount() == 0) {
            Log.d(TAG, "phone Count=: 0, return!");
            return CollectionsKt.emptyList();
        }
        int phoneCount2 = getPhoneCount();
        ArrayList arrayList = new ArrayList(phoneCount2);
        char c = 0;
        for (int i = 0; i < phoneCount2; i++) {
            arrayList.add(-1);
        }
        ArrayList cellIdList = arrayList;
        int phoneCount3 = getPhoneCount();
        int i2 = 0;
        int $i$f$filter = 0;
        while (i2 < phoneCount3) {
            if (!cardExists(i2)) {
                Log.d(TAG, "slot " + i2 + " empty");
                phoneCount3 = phoneCount3;
            } else {
                int[] subId = SubscriptionManagerProxy.getSubId(i2);
                if (subId == null) {
                    Log.e(TAG, "get sub id error, subId==null");
                    phoneCount3 = phoneCount3;
                } else {
                    int phoneType = TelephonyManagerProxy.getCurrentPhoneType(subId[c]);
                    ServiceState serviceState = TelephonyManagerProxy.getServiceStateForSubscriber(subId[c]);
                    boolean z = ServiceStateProxy.getVoiceRegState(serviceState) == 0 ? 1 : c;
                    boolean z2 = ServiceStateProxy.getDataNetworkType(serviceState) == 13 ? 1 : c;
                    Log.d(TAG, "phoneType=" + phoneType + ",i=" + i2);
                    Log.d(TAG, "csInService=" + z + ",psIn4g=" + z2);
                    if (z2 == 0 || ((phoneType != 1 || z != 0) && phoneType != 2)) {
                        phoneCount3 = phoneCount3;
                        CellLocation cellLocation = TelephonyManagerExProxy.getCellLocationForPhone(i2);
                        if (cellLocation instanceof GsmCellLocation) {
                            baseStationId = ((GsmCellLocation) cellLocation).getCid();
                        } else {
                            baseStationId = cellLocation instanceof CdmaCellLocation ? ((CdmaCellLocation) cellLocation).getBaseStationId() : -1;
                        }
                        cellIdList.set(i2, Integer.valueOf(baseStationId));
                    } else {
                        List<CellInfo> allCellInfo = getTelephoneMgr().getAllCellInfo();
                        if (allCellInfo == null || allCellInfo.size() == 0) {
                            Log.e(TAG, "getAllCellInfo return zero");
                        } else {
                            Log.d(TAG, "cell list.size=:" + allCellInfo.size());
                            List<CellInfo> $receiver$iv = allCellInfo;
                            Collection destination$iv$iv = new ArrayList();
                            for (Object element$iv$iv : $receiver$iv) {
                                int i3 = phoneCount3;
                                CellInfo c2 = (CellInfo) element$iv$iv;
                                int $i$f$filter2 = $i$f$filter;
                                Intrinsics.checkExpressionValueIsNotNull(c2, "c");
                                if (c2.isRegistered()) {
                                    destination$iv$iv.add(element$iv$iv);
                                }
                                phoneCount3 = i3;
                                $i$f$filter = $i$f$filter2;
                            }
                            phoneCount3 = phoneCount3;
                            int i4 = $i$f$filter;
                            if (allCellInfo.size() <= i2) {
                                Log.e(TAG, "registered cell not found, " + i2);
                            } else {
                                CellInfo regCell = allCellInfo.get(i2);
                                if (!(regCell instanceof CellInfoLte)) {
                                    Log.e(TAG, "registered cell not lte, " + i2);
                                } else {
                                    CellIdentityLte cellIdentity = ((CellInfoLte) regCell).getCellIdentity();
                                    Intrinsics.checkExpressionValueIsNotNull(cellIdentity, "regCell.cellIdentity");
                                    cellIdList.set(i2, Integer.valueOf(cellIdentity.getCi()));
                                }
                            }
                            $i$f$filter = i4;
                        }
                    }
                }
            }
            i2++;
            phoneCount3 = phoneCount3;
            c = 0;
        }
        Log.d(TAG, "cell id = " + CollectionsKt.joinToString$default(cellIdList, null, null, null, 0, null, null, 63, null));
        return cellIdList;
    }

    @Override // com.unisoc.engineermode.core.intf.IPhoneInfo
    @NotNull
    public List<IPhoneInfo.CdmaInfo> getCdmaInfo() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Log.d(TAG, "get CDMA Info");
        int phoneCount2 = getPhoneCount();
        ArrayList arrayList = new ArrayList(phoneCount2);
        for (int i = 0; i < phoneCount2; i++) {
            arrayList.add(new IPhoneInfo.CdmaInfo("", -1, -1, -1));
        }
        ArrayList cdmaList = arrayList;
        ArrayList $receiver$iv = cdmaList;
        int i2 = 0;
        for (Object item$iv : $receiver$iv) {
            int index$iv = i2 + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IPhoneInfo.CdmaInfo cdmaInfo = (IPhoneInfo.CdmaInfo) item$iv;
            int phoneType = TelephonyManagerProxy.getCurrentPhoneTypeForSlot(i2);
            if (phoneType == 2) {
                Log.d(TAG, "PHONE_TYPE_CDMA...");
                int[] subId = SubscriptionManagerProxy.getSubId(i2);
                if (subId == null) {
                    Log.d(TAG, "error, subId==null...");
                } else {
                    cdmaInfo.setPrl(TelephonyManagerProxy.getCdmaPrlVersion(subId[0]));
                    CellLocation cellLocationForPhone = TelephonyManagerExProxy.getCellLocationForPhone(i2);
                    if (cellLocationForPhone == null) {
                        throw new TypeCastException("null cannot be cast to non-null type android.telephony.cdma.CdmaCellLocation");
                    }
                    CdmaCellLocation location = (CdmaCellLocation) cellLocationForPhone;
                    cdmaInfo.setSystemId(location.getSystemId());
                    cdmaInfo.setNetworkId(location.getNetworkId());
                    cdmaInfo.setBaseStationId(location.getBaseStationId());
                }
            } else {
                Log.w(TAG, "not cdma, phoneType=" + phoneType + ",i=" + i2);
            }
            i2 = index$iv;
        }
        Log.d(TAG, "CDMA info: " + CollectionsKt.joinToString$default(cdmaList, null, null, null, 0, null, null, 63, null));
        return cdmaList;
    }

    @Override // com.unisoc.engineermode.core.intf.IPhoneInfo
    @NotNull
    public String getEsn() {
        String esn = TelephonyManagerProxy.getEsn();
        return esn != null ? esn : "";
    }

    @Override // com.unisoc.engineermode.core.intf.IPhoneInfo
    @SuppressLint({"MissingPermission"})
    @NotNull
    public String getMeid() {
        String meid = getTelephoneMgr().getMeid();
        return meid != null ? meid : "";
    }

    @Override // com.unisoc.engineermode.core.intf.IPhoneInfo
    @SuppressLint({"MissingPermission"})
    @NotNull
    public List<String> getAllImei() {
        Log.d(TAG, "get all IMEI");
        int phoneCount2 = getPhoneCount();
        ArrayList arrayList = new ArrayList(phoneCount2);
        for (int i = 0; i < phoneCount2; i++) {
            arrayList.add("");
        }
        ArrayList imeiList = arrayList;
        int phoneCount3 = getPhoneCount();
        for (int i2 = 0; i2 < phoneCount3; i2++) {
            String imei = getTelephoneMgr().getImei(i2);
            Intrinsics.checkExpressionValueIsNotNull(imei, "telephoneMgr.getImei(i)");
            imeiList.set(i2, imei);
        }
        Log.d(TAG, "get all IMEI " + CollectionsKt.joinToString$default(imeiList, null, null, null, 0, null, null, 63, null));
        return imeiList;
    }

    @Override // com.unisoc.engineermode.core.intf.IPhoneInfo
    @NotNull
    public String getInternalStorageSize() {
        return humanReadableByteCount(StorageManagerProxy.getPrimaryStorageSize(), true);
    }

    @Override // com.unisoc.engineermode.core.intf.IPhoneInfo
    @NotNull
    public String getSoftwareVersion() {
        return SystemPropertiesProxy.get(SOFTWARE_VERSION_PROPERTY_NAME, "");
    }

    @Override // com.unisoc.engineermode.core.intf.IPhoneInfo
    @NotNull
    public String getHardwareVersion() {
        return SystemPropertiesProxy.get(HARDWARE_VERSION_PROPERTY_NAME, "");
    }

    @Override // com.unisoc.engineermode.core.intf.IPhoneInfo
    @NotNull
    public String getOsVersion() {
        return SystemPropertiesProxy.get(OPERATION_SYSTEM_VERSION_PROPERTY_NAME, "");
    }

    private final String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) {
            return bytes + " B";
        }
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        char cCharAt = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1);
        String pre = String.valueOf(cCharAt) + (si ? "" : "i");
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        Object[] objArr = {Double.valueOf(bytes / Math.pow(unit, exp)), pre};
        String str = String.format("%.1f %sB", Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(str, "java.lang.String.format(format, *args)");
        return str;
    }

    @Override // com.unisoc.engineermode.core.intf.IPhoneInfo
    @NotNull
    public List<String> getCdmaImsi() throws NoSuchMethodException {
        Log.d(TAG, "get all CDMAIMSI");
        int phoneCount2 = getPhoneCount();
        ArrayList arrayList = new ArrayList(phoneCount2);
        for (int i = 0; i < phoneCount2; i++) {
            arrayList.add("");
        }
        ArrayList cdmaImsiList = arrayList;
        int phoneCount3 = getPhoneCount();
        for (int i2 = 0; i2 < phoneCount3; i2++) {
            String cdmaImsi = DmykAbsTelephonyManagerProxy.INSTANCE.getCdmaImsi(i2);
            if (cdmaImsi == null) {
                cdmaImsiList.set(i2, "");
            } else {
                cdmaImsiList.set(i2, cdmaImsi);
            }
        }
        Log.d(TAG, "get all CDMA IMSI " + CollectionsKt.joinToString$default(cdmaImsiList, null, null, null, 0, null, null, 63, null));
        return cdmaImsiList;
    }

    @Override // com.unisoc.engineermode.core.intf.IPhoneInfo
    @NotNull
    public List<String> getIccId() throws NoSuchMethodException {
        Log.d(TAG, "get ICCID");
        int phoneCount2 = getPhoneCount();
        ArrayList arrayList = new ArrayList(phoneCount2);
        for (int i = 0; i < phoneCount2; i++) {
            arrayList.add("");
        }
        ArrayList iccIdList = arrayList;
        int phoneCount3 = getPhoneCount();
        for (int i2 = 0; i2 < phoneCount3; i2++) {
            String iccId = DmykAbsTelephonyManagerProxy.INSTANCE.getIccId(i2);
            if (iccId == null) {
                iccIdList.set(i2, "");
            } else {
                iccIdList.set(i2, iccId);
            }
        }
        Log.d(TAG, "get all ICCID " + CollectionsKt.joinToString$default(iccIdList, null, null, null, 0, null, null, 63, null));
        return iccIdList;
    }

    private final boolean cardExists(int slot) {
        return getTelephoneMgr().getSimState(slot) == 5;
    }
}
