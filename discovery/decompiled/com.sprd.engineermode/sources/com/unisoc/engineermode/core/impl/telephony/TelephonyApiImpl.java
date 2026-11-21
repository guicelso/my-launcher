package com.unisoc.engineermode.core.impl.telephony;

import com.unisoc.engineermode.core.factory.BaseInterface;
import com.unisoc.engineermode.core.factory.ImplementationFactory;
import com.unisoc.engineermode.core.intf.ITelephonyApi;

/* JADX INFO: loaded from: classes4.dex */
public class TelephonyApiImpl implements ITelephonyApi {
    private static final String TAG = "TelephonyAPI";

    public static ITelephonyApi getInstance() {
        return TelephonyApiImplHolder.INSTANCE;
    }

    private static class TelephonyApiImplHolder {
        static final ITelephonyApi INSTANCE = new TelephonyApiImpl();

        private TelephonyApiImplHolder() {
        }
    }

    private static class TelephonyInfoImplHolder {
        static final ITelephonyApi.ITelephonyInfo INSTANCE = new TelephonyInfoImpl();

        private TelephonyInfoImplHolder() {
        }
    }

    private static class NetInfoWcdmaUeCapImplHolder {
        static final ITelephonyApi.INetInfoWcdmaUeCap INSTANCE = new NetInfoWcdmaUeCapImpl();

        private NetInfoWcdmaUeCapImplHolder() {
        }
    }

    private static class NetInfoC2kUeCapImplHolder {
        static final ITelephonyApi.INetInfoC2kUeCap INSTANCE = new NetInfoC2kUeCapImpl();

        private NetInfoC2kUeCapImplHolder() {
        }
    }

    private static class NetInfoGsmUeCapImplHolder {
        static final ITelephonyApi.INetInfoGsmUeCap INSTANCE = new NetInfoGsmUeCapImpl();

        private NetInfoGsmUeCapImplHolder() {
        }
    }

    private static class NetInfoLteImplHolder {
        static final ITelephonyApi.INetInfoLte INSTANCE = new NetInfoLteImpl();

        private NetInfoLteImplHolder() {
        }
    }

    private static class NetInfoWcdmaImplHolder {
        static final ITelephonyApi.INetInfoWcdma INSTANCE = new NetInfoWcdmaImpl();

        private NetInfoWcdmaImplHolder() {
        }
    }

    private static class NetInfoC2kImplHolder {
        static final ITelephonyApi.INetInfoC2k INSTANCE = new NetInfoC2kImpl();

        private NetInfoC2kImplHolder() {
        }
    }

    private static class NetInfoTdscdmaImplHolder {
        static final ITelephonyApi.INetInfoTdscdma INSTANCE = new NetInfoTdscdmaImpl();

        private NetInfoTdscdmaImplHolder() {
        }
    }

    private static class NetInfoGsmImplHolder {
        static final ITelephonyApi.INetInfoGsm INSTANCE = new NetInfoGsmImpl();

        private NetInfoGsmImplHolder() {
        }
    }

    private static class Csfb2GmsDelayImplHolder {
        static final ITelephonyApi.ICsfb2GmsDelay INSTANCE = new Csfb2GmsDelayImpl();

        private Csfb2GmsDelayImplHolder() {
        }
    }

    private static class VamosCpcImplHolder {
        static final ITelephonyApi.IVamosCpc INSTANCE = new VamosCpcImpl();

        private VamosCpcImplHolder() {
        }
    }

    private static class NetInfoStatImplHolder {
        static final ITelephonyApi.INetInfoStat INSTANCE = new NetInfoStatImpl();

        private NetInfoStatImplHolder() {
        }
    }

    private static class GprsSettingImplHolder {
        static final ITelephonyApi.IGprsSetting INSTANCE = new GprsSettingImpl();

        private GprsSettingImplHolder() {
        }
    }

    private static class DataServicePreferImplHolder {
        static final ITelephonyApi.IDataServicePrefer INSTANCE = new DataServicePreferImpl();

        private DataServicePreferImplHolder() {
        }
    }

    private static class FastDormancyImplHolder {
        static final ITelephonyApi.IFastDormancy INSTANCE = new FastDormancyImpl();

        private FastDormancyImplHolder() {
        }
    }

    private static class SimTraceImplHolder {
        static final ITelephonyApi.ISimTrace INSTANCE = new SimTraceImpl();

        private SimTraceImplHolder() {
        }
    }

    private static class C2kConfigImplHolder {
        static final ITelephonyApi.IC2kConfig INSTANCE = new C2kConfigImpl();

        private C2kConfigImplHolder() {
        }
    }

    private static class VideoTypeImplHolder {
        static final ITelephonyApi.IVideoType INSTANCE = new VideoTypeImpl();

        private VideoTypeImplHolder() {
        }
    }

    private static class SimFuncImplHolder {
        static final ITelephonyApi.ISimFunc INSTANCE = new SimFuncImpl();

        private SimFuncImplHolder() {
        }
    }

    private static class NetInfoImplHolder {
        static final ITelephonyApi.INetInfo INSTANCE = new NetInfoImpl();

        private NetInfoImplHolder() {
        }
    }

    private static class RadioFuncImplHolder {
        static final ITelephonyApi.IRadioFunc INSTANCE = new RadioFuncImpl();

        private RadioFuncImplHolder() {
        }
    }

    private static class BandImplHolder {
        static final ITelephonyApi.IBand INSTANCE = new BandImpl();

        private BandImplHolder() {
        }
    }

    private static class NetWorkModeImplHolder {
        static final ITelephonyApi.INetWorkMode INSTANCE = new NetWorkModeImpl();

        private NetWorkModeImplHolder() {
        }
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.ITelephonyInfo telephonyInfo() {
        return TelephonyInfoImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.IEngTestStatus EngTestStatus() {
        return EngTestStatusImpl.EngTestStatusImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.ICallFunc callFunc() {
        return CallFuncImpl.CallFuncImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.ICftResult cftResult() {
        return (ITelephonyApi.ICftResult) ImplementationFactory.create((Class<? extends BaseInterface>) ITelephonyApi.ICftResult.class);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.IControlEvdo controlEvdo() {
        return (ITelephonyApi.IControlEvdo) ImplementationFactory.create((Class<? extends BaseInterface>) ITelephonyApi.IControlEvdo.class);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.IWcdmaPrefer wcdmaPrefer() {
        return (ITelephonyApi.IWcdmaPrefer) ImplementationFactory.create((Class<? extends BaseInterface>) ITelephonyApi.IWcdmaPrefer.class);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.IVolteUeSettings volteUeSettings() {
        return (ITelephonyApi.IVolteUeSettings) ImplementationFactory.create((Class<? extends BaseInterface>) ITelephonyApi.IVolteUeSettings.class);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.IVolteTemporarySettings volteTemporarySettings() {
        return VolteTemporarySettingsImpl.VolteTemporarySettingsImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.IVolteOperatorSettings volteOperatorSettings() {
        return VolteOperatorSettingsImpl.VolteOperatorSettingsImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.IVolteDevelopSettings volteDevelopSettings() {
        return (ITelephonyApi.IVolteDevelopSettings) ImplementationFactory.create((Class<? extends BaseInterface>) ITelephonyApi.IVolteDevelopSettings.class);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.IVoltePlmnSettings voltePlmnSettings() {
        return (ITelephonyApi.IVoltePlmnSettings) ImplementationFactory.create((Class<? extends BaseInterface>) ITelephonyApi.IVoltePlmnSettings.class);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.INetInfoLteUeCap lteUeCapApi() {
        return NetInfoLteUeCapImpl.NetInfoLteUeCapImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.INetInfoWcdmaUeCap wcdmaUeCapApi() {
        return NetInfoWcdmaUeCapImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.INetInfoC2kUeCap c2kUeCapApi() {
        return NetInfoC2kUeCapImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.INetInfoGsmUeCap gsmUeCapApi() {
        return NetInfoGsmUeCapImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.INetInfoLte netInfoLteApi() {
        return NetInfoLteImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.INetInfoWcdma netInfoWcdmaApi() {
        return NetInfoWcdmaImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.INetInfoC2k netInfoC2kApi() {
        return NetInfoC2kImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.INetInfoC2k1x netInfoC2k1xApi() {
        return (ITelephonyApi.INetInfoC2k1x) ImplementationFactory.create((Class<? extends BaseInterface>) ITelephonyApi.INetInfoC2k1x.class);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.INetInfoTdscdma netInfoTdscdmaApi() {
        return NetInfoTdscdmaImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.INetInfoGsm netInfoGsmApi() {
        return NetInfoGsmImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.ICsfb2GmsDelay csfb2GsmDealyApi() {
        return Csfb2GmsDelayImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.IVamosCpc vamosCpcApi() {
        return VamosCpcImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.INetInfoStat netInfoStat() {
        return NetInfoStatImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.IGprsSetting gprsOper() {
        return GprsSettingImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.IDataServicePrefer dataServicePrefer() {
        return DataServicePreferImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.IFastDormancy fastDormancy() {
        return FastDormancyImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.ISimTrace simTrace() {
        return SimTraceImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.IC2kConfig c2kConfig() {
        return C2kConfigImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.IVideoType videoType() {
        return VideoTypeImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.IManualAssert manualAssert() {
        return (ITelephonyApi.IManualAssert) ImplementationFactory.create((Class<? extends BaseInterface>) ITelephonyApi.IManualAssert.class);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.ISimFunc simFunc() {
        return SimFuncImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.INetInfo netInfo() {
        return NetInfoImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.IRadioFunc radioFunc() {
        return RadioFuncImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.IBand band() {
        return BandImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.INetWorkMode netWorkMode() {
        return NetWorkModeImplHolder.INSTANCE;
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.IVamos vamos() {
        return (ITelephonyApi.IVamos) ImplementationFactory.create((Class<? extends BaseInterface>) ITelephonyApi.IVamos.class);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.IDnsFilter dnsFilter() {
        return (ITelephonyApi.IDnsFilter) ImplementationFactory.create((Class<? extends BaseInterface>) ITelephonyApi.IDnsFilter.class);
    }

    @Override // com.unisoc.engineermode.core.intf.ITelephonyApi
    public ITelephonyApi.IControlMeid controlMeidSwitch() {
        return (ITelephonyApi.IControlMeid) ImplementationFactory.create((Class<? extends BaseInterface>) ITelephonyApi.IControlMeid.class);
    }
}
