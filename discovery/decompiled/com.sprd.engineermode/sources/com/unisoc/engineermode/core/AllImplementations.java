package com.unisoc.engineermode.core;

import com.unisoc.engineermode.core.factory.ImplementationInfo;
import com.unisoc.engineermode.core.factory.PropertyKeys;
import com.unisoc.engineermode.core.intf.IAiIp;
import com.unisoc.engineermode.core.intf.IAntenna;
import com.unisoc.engineermode.core.intf.IAtDiag;
import com.unisoc.engineermode.core.intf.ICabc;
import com.unisoc.engineermode.core.intf.ICharge;
import com.unisoc.engineermode.core.intf.ICoulometerPower;
import com.unisoc.engineermode.core.intf.IGcfTest;
import com.unisoc.engineermode.core.intf.IJeitaControl;
import com.unisoc.engineermode.core.intf.IPhoneInfo;
import com.unisoc.engineermode.core.intf.ISMSCenterNumber;
import com.unisoc.engineermode.core.intf.ISensePll;
import com.unisoc.engineermode.core.intf.ITelephonyApi;
import com.unisoc.engineermode.core.intf.IThermal;
import com.unisoc.engineermode.core.sample.JavaInterface;
import com.unisoc.engineermode.core.sample.KotlinImpl;
import com.unisoc.engineermode.core.sample.KotlinInterface;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class AllImplementations {
    public static List<ImplementationInfo> impls = new ArrayList();

    public static void init() {
        ImplementationInfo implInfo = new ImplementationInfo(IGcfTest.class, "com.unisoc.engineermode.core.impl.debuglog.GcfTestImpl");
        impls.add(implInfo);
        ImplementationInfo implInfo2 = new ImplementationInfo(IAntenna.class, "com.unisoc.engineermode.core.impl.hardware.AntennaImpl");
        impls.add(implInfo2);
        ImplementationInfo implInfo3 = new ImplementationInfo(ITelephonyApi.ICftResult.class, "com.unisoc.engineermode.core.impl.telephony.CftResultImpl");
        impls.add(implInfo3);
        ImplementationInfo implInfo4 = new ImplementationInfo(ITelephonyApi.IControlEvdo.class, "com.unisoc.engineermode.core.impl.telephony.ControlEvdoImpl");
        impls.add(implInfo4);
        ImplementationInfo implInfo5 = new ImplementationInfo(ITelephonyApi.IControlMeid.class, "com.unisoc.engineermode.core.impl.telephony.ControlMeidImpl");
        impls.add(implInfo5);
        ImplementationInfo implInfo6 = new ImplementationInfo(ITelephonyApi.IDnsFilter.class, "com.unisoc.engineermode.core.impl.telephony.DnsFilterImpl");
        impls.add(implInfo6);
        ImplementationInfo implInfo7 = new ImplementationInfo(ITelephonyApi.IManualAssert.class, "com.unisoc.engineermode.core.impl.telephony.ManualAssertImpl");
        impls.add(implInfo7);
        ImplementationInfo implInfo8 = new ImplementationInfo(ITelephonyApi.INetInfoC2k1x.class, "com.unisoc.engineermode.core.impl.telephony.NetInfoC2k1xImpl");
        impls.add(implInfo8);
        ImplementationInfo implInfo9 = new ImplementationInfo(ITelephonyApi.IVamos.class, "com.unisoc.engineermode.core.impl.telephony.VamosImpl");
        impls.add(implInfo9);
        ImplementationInfo implInfo10 = new ImplementationInfo(ITelephonyApi.IVolteDevelopSettings.class, "com.unisoc.engineermode.core.impl.telephony.VolteDevelopSettingsImpl");
        impls.add(implInfo10);
        ImplementationInfo implInfo11 = new ImplementationInfo(ITelephonyApi.IVoltePlmnSettings.class, "com.unisoc.engineermode.core.impl.telephony.VoltePlmnSettingsImpl");
        impls.add(implInfo11);
        ImplementationInfo implInfo12 = new ImplementationInfo(ITelephonyApi.IVolteUeSettings.class, "com.unisoc.engineermode.core.impl.telephony.VolteUESettingsImpl");
        impls.add(implInfo12);
        ImplementationInfo implInfo13 = new ImplementationInfo(ITelephonyApi.IWcdmaPrefer.class, "com.unisoc.engineermode.core.impl.telephony.WcdmaPreferImpl");
        impls.add(implInfo13);
        ImplementationInfo implInfo14 = new ImplementationInfo(JavaInterface.class, "com.unisoc.engineermode.core.sample.JavaImpl");
        implInfo14.addProperty(PropertyKeys.ANDROID_VERSION, new String[]{"9"});
        implInfo14.addProperty("variant", new String[]{"SampleImpl2"});
        impls.add(implInfo14);
        ImplementationInfo implInfo15 = new ImplementationInfo(KotlinInterface.class, "com.unisoc.engineermode.core.sample.KotlinImplDef");
        impls.add(implInfo15);
        ImplementationInfo implInfo16 = new ImplementationInfo(KotlinInterface.class, "com.unisoc.engineermode.core.sample.KotlinImpl");
        implInfo16.addProperty(PropertyKeys.PRODUCT, new String[]{"robolectric", "xxxxxx"});
        implInfo16.addProperty(PropertyKeys.ANDROID_VERSION, new String[]{"9"});
        implInfo16.addProperty("variant", new String[]{KotlinImpl.TAG});
        impls.add(implInfo16);
        ImplementationInfo implInfo17 = new ImplementationInfo(ICabc.class, "com.unisoc.engineermode.core.impl.debuglog.CabcImpl");
        impls.add(implInfo17);
        ImplementationInfo implInfo18 = new ImplementationInfo(ISMSCenterNumber.class, "com.unisoc.engineermode.core.impl.debuglog.SMSCenterNumberImpl");
        impls.add(implInfo18);
        ImplementationInfo implInfo19 = new ImplementationInfo(IAiIp.class, "com.unisoc.engineermode.core.impl.debuglog.AiIpImpl");
        impls.add(implInfo19);
        ImplementationInfo implInfo20 = new ImplementationInfo(IPhoneInfo.class, "com.unisoc.engineermode.core.impl.debuglog.PhoneInfoImpl");
        implInfo20.addProperty(PropertyKeys.ANDROID_VERSION, new String[]{"10"});
        impls.add(implInfo20);
        ImplementationInfo implInfo21 = new ImplementationInfo(IAtDiag.class, "com.unisoc.engineermode.core.impl.debuglog.AtDiagImpl");
        impls.add(implInfo21);
        ImplementationInfo implInfo22 = new ImplementationInfo(IJeitaControl.class, "com.unisoc.engineermode.core.impl.debuglog.JeitaControlImpl");
        impls.add(implInfo22);
        ImplementationInfo implInfo23 = new ImplementationInfo(IThermal.class, "com.unisoc.engineermode.core.impl.hardware.ThermalImpl");
        impls.add(implInfo23);
        ImplementationInfo implInfo24 = new ImplementationInfo(ISensePll.class, "com.unisoc.engineermode.core.impl.hardware.SensePllImpl");
        impls.add(implInfo24);
        ImplementationInfo implInfo25 = new ImplementationInfo(ICharge.class, "com.unisoc.engineermode.core.impl.hardware.ChargeImpl");
        impls.add(implInfo25);
        ImplementationInfo implInfo26 = new ImplementationInfo(ICoulometerPower.class, "com.unisoc.engineermode.core.impl.hardware.CoulometerPowerImpl");
        impls.add(implInfo26);
    }
}
