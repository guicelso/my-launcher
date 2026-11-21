package com.sprd.engineermode.gcf;

import android.R;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.sprd.engineermode.EngineerModeActivity_2;
import com.unisoc.engineermode.core.utils.IATUtils;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class OperatorActivity extends ListActivity {
    private static final int SET_OPERATOR_AIS = 23;
    private static final int SET_OPERATOR_ALTICE = 31;
    private static final int SET_OPERATOR_ATT = 19;
    private static final int SET_OPERATOR_BEELINE = 21;
    private static final int SET_OPERATOR_CLARO = 11;
    private static final int SET_OPERATOR_CMCC = 1;
    private static final int SET_OPERATOR_CTCC = 44;
    private static final int SET_OPERATOR_CUCC = 2;
    private static final int SET_OPERATOR_DIALOG = 33;
    private static final int SET_OPERATOR_DOCOMO = 12;
    private static final int SET_OPERATOR_DT = 9;
    private static final int SET_OPERATOR_DTAC = 34;
    private static final int SET_OPERATOR_DTAG = 25;
    private static final int SET_OPERATOR_ETISALAT = 26;
    private static final int SET_OPERATOR_FASTLINK = 42;
    private static final int SET_OPERATOR_INWI = 35;
    private static final int SET_OPERATOR_IRANCELL = 36;
    private static final int SET_OPERATOR_MASCOM = 37;
    private static final int SET_OPERATOR_MEGAFON = 15;
    private static final int SET_OPERATOR_MOVISTAR = 7;
    private static final int SET_OPERATOR_MTN = 10;
    private static final int SET_OPERATOR_MTS = 27;
    private static final int SET_OPERATOR_NTEL = 38;
    private static final int SET_OPERATOR_ORANGE = 3;
    private static final int SET_OPERATOR_OYSTERS = 20;
    private static final int SET_OPERATOR_PERU_CLARO = 32;
    private static final int SET_OPERATOR_PRESTIGIO = 28;
    private static final int SET_OPERATOR_RELIANCE = 8;
    private static final int SET_OPERATOR_SEATEL = 29;
    private static final int SET_OPERATOR_SMARFREN = 24;
    private static final int SET_OPERATOR_SMILE = 39;
    private static final int SET_OPERATOR_TELCEL = 6;
    private static final int SET_OPERATOR_TELEFONICA = 13;
    private static final int SET_OPERATOR_TELSTRA = 4;
    private static final int SET_OPERATOR_TIM = 22;
    private static final int SET_OPERATOR_TMOBILE = 16;
    private static final int SET_OPERATOR_TRUE = 17;
    private static final int SET_OPERATOR_UK_EE = 14;
    private static final int SET_OPERATOR_UNITEL = 30;
    private static final int SET_OPERATOR_VEON = 40;
    private static final int SET_OPERATOR_VODACOM = 41;
    private static final int SET_OPERATOR_VODAFONE = 5;
    private static final int SET_OPERATOR_YTLC = 18;
    private static final int SET_OPERATOR_ZONE = 43;
    private static final String TAG = "OperatorActivity";
    private List<String> keyLists = new ArrayList();
    private String mATCmd;
    private String mAtCmd1;
    private Context mContext;
    private OperatorHandler mOperatorHandler;
    private String mStrTmp;
    private String mStrTmp1;
    private static final String KEY_CMCC = "operator_China_CMCC";
    private static final String KEY_CUCC = "operator_China_CUCC";
    private static final String KEY_ORANGE = "operator_France_Orange";
    private static final String KEY_TELSTRA = "operator_Australia_Telstra";
    private static final String KEY_VODAFONE = "operator_Germany_Vodafone";
    private static final String KEY_TELCEL = "operator_Mexico_Telcel";
    private static final String KEY_MOVISTAR = "operator_Mexico_Movistar";
    private static final String KEY_RELIANCE = "operator_India_Reliance";
    private static final String KEY_DT = "operator_dt";
    private static final String KEY_MTN = "operator_SouthAfrica_MTN";
    private static final String KEY_CLARO = "operator_Colombia_Claro";
    private static final String KEY_DOCOMO = "operator_Japan_Docomo";
    private static final String KEY_TELEFONICA = "operator_Spain_Telefonica";
    private static final String KEY_OPERATOR_UK_EE = "operator_UK_EE";
    private static final String KEY_OPERATOR_MEGAFON = "operator_Russia_Megafon";
    private static final String KEY_OPERATOR_TMOBILE = "operator_tmobile";
    private static final String KEY_OPERATOR_TRUE = "operator_Thailand_True";
    private static final String KEY_OPERATOR_YTLC = "operator_Malaysia_YTLC";
    private static final String KEY_OPERATOR_ATT = "operator_att";
    private static final String KEY_OPERATOR_OYSTERS = "operator_oysters";
    private static final String KEY_OPERATOR_BEELINE = "operator_Russia_Beeline";
    private static final String KEY_OPERATOR_TIM = "operator_Brazil_TIM";
    private static final String KEY_OPERATOR_AIS = "operator_Thailand_AIS";
    private static final String KEY_OPERATOR_SMARFREN = "operator_Indonesia_Smartfren";
    private static final String KEY_OPERATOR_DTAG = "operator_Germany_DTAG";
    private static final String KEY_OPERATOR_ETISALAT = "operator_Egypt_Etisalat";
    private static final String KEY_OPERATOR_MTS = "operator_Russia_MTS";
    private static final String KEY_OPERATOR_PRESTIGIO = "operator_Poland_Prestigio";
    private static final String KEY_OPERATOR_SEATEL = "operator_seatel";
    private static final String KEY_OPERATOR_UNITEL = "operator_Angola_UNITEL";
    private static final String KEY_OPERATOR_ALTICE = "operator_Netherlands_Altice";
    private static final String KEY_OPERATOR_PERU_CLARO = "operator_Peru_Claro";
    private static final String KEY_OPERATOR_DIALOG = "operator_Srilanka_Dialog";
    private static final String KEY_OPERATOR_DTAC = "operator_Thailand_DTAC";
    private static final String KEY_OPERATOR_INWI = "operator_Morocco_INWI";
    private static final String KEY_OPERATOR_IRANCELL = "operator_Iran_Irancell";
    private static final String KEY_OPERATOR_MASCOM = "operator_Botswana_Mascom";
    private static final String KEY_OPERATOR_NTEL = "operator_Nigeria_Ntel";
    private static final String KEY_OPERATOR_SMILE = "operator_Nigeria_Smile";
    private static final String KEY_OPERATOR_VEON = "operator_Netherlands_Veon";
    private static final String KEY_OPERATOR_VODACOM = "operator_SouthAfrica_Vodacom";
    private static final String KEY_OPERATOR_FASTLINK = "operator_Iraq_Fastlink";
    private static final String KEY_OPERATOR_ZONE = "operator_Pakistan_Zone";
    private static final String KEY_OPERATOR_CTCC = "operator_China_CTCC";
    private static final String KEY_OPERATOR_I_A_N_T = "operator_Iraq_Allai_Newroz_Telecom";
    private static final String KEY_OPERATOR_F_A = "operator_France_Altice";
    private static final String KEY_OPERATOR_T_A = "operator_Taiwan_APTG";
    private static final String KEY_OPERATOR_M_A_T = "operator_Mexico_AT&T";
    private static final String KEY_OPERATOR_C_A = "operator_Colombia_Avantel";
    private static final String KEY_OPERATOR_S_C = "operator_SouthAfrica_Cell-C";
    private static final String KEY_OPERATOR_M_D = "operator_Malaysia_Digi";
    private static final String KEY_OPERATOR_R_D = "operator_Romania_Digi";
    private static final String KEY_OPERATOR_F_E = "operator_France_EI";
    private static final String KEY_OPERATOR_C_E = "operator_Cuba_ETECSA";
    private static final String KEY_OPERATOR_U_E = "operator_UAE_Etisalat";
    private static final String KEY_OPERATOR_M_I = "operator_Morocco_IAM";
    private static final String KEY_OPERATOR_R_M = "operator_Russia_Motiv";
    private static final String KEY_OPERATOR_M_M = "operator_Myanmar_MPT";
    private static final String KEY_OPERATOR_C_M = "operator_Cameroon_MTN";
    private static final String KEY_OPERATOR_A_M = "operator_Angola_Movicel";
    private static final String KEY_OPERATOR_M_O = "operator_Myanmar_ooredoo";
    private static final String KEY_OPERATOR_E_O = "operator_Egypt_Orange";
    private static final String KEY_OPERATOR_S_E = "operator_Singapore_Era";
    private static final String KEY_OPERATOR_P_S = "operator_Philippines_Smart";
    private static final String KEY_OPERATOR_C_S1 = "operator_Cambodia_Smart";
    private static final String KEY_OPERATOR_C_S_A = "operator_Cambodia_Smart_axiata";
    private static final String KEY_OPERATOR_K_S = "operator_Kenya_Smile";
    private static final String KEY_OPERATOR_U_S = "operator_Uganda_Smile";
    private static final String KEY_OPERATOR_T_S1 = "operator_Tanzania_Smile";
    private static final String KEY_OPERATOR_S_S = "operator_SaudiArabia_STC";
    private static final String KEY_OPERATOR_S_T = "operator_SouthAfrica_Telecom";
    private static final String KEY_OPERATOR_E_T_E = "operator_Egypt_Telecom_Egypt";
    private static final String KEY_OPERATOR_M_T = "operator_Myanmar_Telenor";
    private static final String KEY_OPERATOR_P_T = "operator_Pakistan_Telenor";
    private static final String KEY_OPERATOR_I_T = "operator_Indonesia_Telkomsel";
    private static final String KEY_OPERATOR_C_T = "operator_Colombia_Tigo";
    private static final String KEY_OPERATOR_V_V = "operator_Vietnam_Viettel";
    private static final String KEY_OPERATOR_E_V = "operator_Egypt_Vodacom";
    private static final String KEY_OPERATOR_E_V1 = "operator_Egypt_Vodafone";
    private static final String KEY_OPERATOR_C_V = "operator_Cameroon_Vodafone";
    private static final String KEY_OPERATOR_U_V = "operator_Uganda_Vodafone";
    private static final String KEY_OPERATOR_G_V = "operator_Ghana_Vodafone";
    private static final String KEY_OPERATOR_Z_V = "operator_Zambia_Vodafone";
    private static final String KEY_OPERATOR_I_X = "operator_Indonesia_XL";
    private static final String KEY_OPERATOR_I_A = "operator_India_Airtel";
    private static final String KEY_OPERATOR_Z_A = "operator_Zambia_Afrimax";
    private static final String KEY_OPERATOR_B_C = "operator_Brazil_Claro";
    private static final String KEY_OPERATOR_P_D = "operator_Panama_Digicel";
    private static final String KEY_OPERATOR_U_H = "operator_UK_Hutchison";
    private static final String KEY_OPERATOR_K_J = "operator_Kenya_JTL";
    private static final String KEY_OPERATOR_N_S = "operator_NewZealand_Spark";
    private static final String KEY_OPERATOR_S_S1 = "operator_SierraLleone_Sierratel";
    private static final String KEY_OPERATOR_T_T = "operator_Taiwan_TCC";
    private static final String KEY_OPERATOR_T_T1 = "operator_Tokelau_Teletok";
    private static final String KEY_OPERATOR_I_T2 = "operator_Italy_TIM";
    private static final String KEY_OPERATOR_B_V = "operator_Brazil_VIVO";
    private static final String KEY_OPERATOR_A_C = "operator_Argentina_Claro";
    private static final String KEY_OPERATOR_A_M1 = "operator_Argentina_Movistar";
    private static final String KEY_OPERATOR_C_M1 = "operator_Chile_Movistar";
    private static final String KEY_OPERATOR_C_M2 = "operator_Colombia_Movistar";
    private static final String KEY_OPERATOR_E_M = "operator_Ecuador_Movistar";
    private static final String KEY_OPERATOR_P_M = "operator_Peru_Movistar";
    private static final String KEY_OPERATOR_T_T2 = "operator_Turkey_Turkcell";
    private static final String KEY_OPERATOR_I_W = "operator_Italy_Wind";
    private static final String KEY_OPERATOR_I_I = "operator_India_Idea";
    private static final String KEY_OPERATOR_M_M1 = "operator_Myanmar_MYTEL";
    private static final String KEY_OPERATOR_T_F = "operator_Taiwan_FET";
    private static final String KEY_OPERATOR_T_C = "operator_Taiwan_CHT";
    private static final String KEY_OPERATOR_R_T = "operator_Russia_Tele2";
    private static final String KEY_OPERATOR_S_H = "operator_Sweden_Hutchison";
    private static final String KEY_OPERATOR_D_H = "operator_Denmark_Hutchison";
    private static final String KEY_OPERATOR_A_H = "operator_Austria_Hutchison";
    private static final String KEY_OPERATOR_U_V1 = "operator_USA_Verizon";
    private static final String KEY_OPERATOR_K_S1 = "operator_Korea_SKT";
    private static final String KEY_OPERATOR_U_V2 = "operator_UK_Vodafone";
    private static final String KEY_OPERATOR_I_V = "operator_Italy_Vodafone";
    private static final String KEY_OPERATOR_H_V = "operator_Hungary_Vodafone";
    private static final String KEY_OPERATOR_P_V = "operator_Portugal_Vodafone";
    private static final String KEY_OPERATOR_R_V = "operator_Czech_Republic_Vodafone";
    private static final String KEY_OPERATOR_G_V1 = "operator_Greece_Vodafone";
    private static final String KEY_OPERATOR_C_V1 = "operator_Croatia_Vodafone";
    private static final String KEY_OPERATOR_C_C = "operator_Chile_Claro";
    private static final String KEY_OPERATOR_C_E1 = "operator_Chile_Entel";
    private static final String KEY_OPERATOR_U_M = "operator_Uruguay_Movistar";
    private static final String KEY_OPERATOR_U_C = "operator_Uruguay_Claro";
    private static final String KEY_OPERATOR_U_O = "operator_UK_O2";
    private static final String KEY_OPERATOR_G_O = "operator_Germany_O2";
    private static final String KEY_OPERATOR_D_D = "operator_Dubai_DU";
    private static final String KEY_OPERATOR_I_V1 = "operator_India_Vodafone";
    private static final String KEY_OPERATOR_H_D = "operator_Hungary_Digi";
    private static final String KEY_OPERATOR_A_V = "operator_Albania_Vodafone";
    private static final String KEY_OPERATOR_S_V = "operator_Spain_Vodafone";
    private static final String KEY_OPERATOR_I_V2 = "operator_Ireland_Vodafone";
    private static final String KEY_OPERATOR_A_V1 = "operator_Australia_Vodafone";
    private static final String KEY_OPERATOR_P_E = "operator_Peru_Entel";
    private static final String KEY_OPERATOR_E_C = "operator_Ecuador_Claro";
    private static final String KEY_OPERATOR_T_T3 = "operator_Taiwan_TWM";
    public static String[] keys = {KEY_CMCC, KEY_CUCC, KEY_ORANGE, KEY_TELSTRA, KEY_VODAFONE, KEY_TELCEL, KEY_MOVISTAR, KEY_RELIANCE, KEY_DT, KEY_MTN, KEY_CLARO, KEY_DOCOMO, KEY_TELEFONICA, KEY_OPERATOR_UK_EE, KEY_OPERATOR_MEGAFON, KEY_OPERATOR_TMOBILE, KEY_OPERATOR_TRUE, KEY_OPERATOR_YTLC, KEY_OPERATOR_ATT, KEY_OPERATOR_OYSTERS, KEY_OPERATOR_BEELINE, KEY_OPERATOR_TIM, KEY_OPERATOR_AIS, KEY_OPERATOR_SMARFREN, KEY_OPERATOR_DTAG, KEY_OPERATOR_ETISALAT, KEY_OPERATOR_MTS, KEY_OPERATOR_PRESTIGIO, KEY_OPERATOR_SEATEL, KEY_OPERATOR_UNITEL, KEY_OPERATOR_ALTICE, KEY_OPERATOR_PERU_CLARO, KEY_OPERATOR_DIALOG, KEY_OPERATOR_DTAC, KEY_OPERATOR_INWI, KEY_OPERATOR_IRANCELL, KEY_OPERATOR_MASCOM, KEY_OPERATOR_NTEL, KEY_OPERATOR_SMILE, KEY_OPERATOR_VEON, KEY_OPERATOR_VODACOM, KEY_OPERATOR_FASTLINK, KEY_OPERATOR_ZONE, KEY_OPERATOR_CTCC, KEY_OPERATOR_I_A_N_T, KEY_OPERATOR_F_A, KEY_OPERATOR_T_A, KEY_OPERATOR_M_A_T, KEY_OPERATOR_C_A, KEY_OPERATOR_S_C, KEY_OPERATOR_M_D, KEY_OPERATOR_R_D, KEY_OPERATOR_F_E, KEY_OPERATOR_C_E, KEY_OPERATOR_U_E, KEY_OPERATOR_M_I, KEY_OPERATOR_R_M, KEY_OPERATOR_M_M, KEY_OPERATOR_C_M, KEY_OPERATOR_A_M, KEY_OPERATOR_M_O, KEY_OPERATOR_E_O, KEY_OPERATOR_S_E, KEY_OPERATOR_P_S, KEY_OPERATOR_C_S1, KEY_OPERATOR_C_S_A, KEY_OPERATOR_K_S, KEY_OPERATOR_U_S, KEY_OPERATOR_T_S1, KEY_OPERATOR_S_S, KEY_OPERATOR_S_T, KEY_OPERATOR_E_T_E, KEY_OPERATOR_M_T, KEY_OPERATOR_P_T, KEY_OPERATOR_I_T, KEY_OPERATOR_C_T, KEY_OPERATOR_V_V, KEY_OPERATOR_E_V, KEY_OPERATOR_E_V1, KEY_OPERATOR_C_V, KEY_OPERATOR_U_V, KEY_OPERATOR_G_V, KEY_OPERATOR_Z_V, KEY_OPERATOR_I_X, KEY_OPERATOR_I_A, KEY_OPERATOR_Z_A, KEY_OPERATOR_B_C, KEY_OPERATOR_P_D, KEY_OPERATOR_U_H, KEY_OPERATOR_K_J, KEY_OPERATOR_N_S, KEY_OPERATOR_S_S1, KEY_OPERATOR_T_T, KEY_OPERATOR_T_T1, KEY_OPERATOR_I_T2, KEY_OPERATOR_B_V, KEY_OPERATOR_A_C, KEY_OPERATOR_A_M1, KEY_OPERATOR_C_M1, KEY_OPERATOR_C_M2, KEY_OPERATOR_E_M, KEY_OPERATOR_P_M, KEY_OPERATOR_T_T2, KEY_OPERATOR_I_W, KEY_OPERATOR_I_I, KEY_OPERATOR_M_M1, KEY_OPERATOR_T_F, KEY_OPERATOR_T_C, KEY_OPERATOR_R_T, KEY_OPERATOR_S_H, KEY_OPERATOR_D_H, KEY_OPERATOR_A_H, KEY_OPERATOR_U_V1, KEY_OPERATOR_K_S1, KEY_OPERATOR_U_V2, KEY_OPERATOR_I_V, KEY_OPERATOR_H_V, KEY_OPERATOR_P_V, KEY_OPERATOR_R_V, KEY_OPERATOR_G_V1, KEY_OPERATOR_C_V1, KEY_OPERATOR_C_C, KEY_OPERATOR_C_E1, KEY_OPERATOR_U_M, KEY_OPERATOR_U_C, KEY_OPERATOR_U_O, KEY_OPERATOR_G_O, KEY_OPERATOR_D_D, KEY_OPERATOR_I_V1, KEY_OPERATOR_H_D, KEY_OPERATOR_A_V, KEY_OPERATOR_S_V, KEY_OPERATOR_I_V2, KEY_OPERATOR_A_V1, KEY_OPERATOR_P_E, KEY_OPERATOR_E_C, KEY_OPERATOR_T_T3};

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        this.mOperatorHandler = new OperatorHandler(ht.getLooper());
        int i = 0;
        while (true) {
            String[] strArr = keys;
            if (i < strArr.length) {
                this.keyLists.add(strArr[i]);
                i++;
            } else {
                Log.d(TAG, "keyLists.size = " + this.keyLists.size());
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.simple_list_item_1, keys);
                setListAdapter(adapter);
                this.mContext = this;
                return;
            }
        }
    }

    @Override // android.app.ListActivity
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Log.d(TAG, "position = " + position + " key = " + keys[position]);
        Message mOperator = this.mOperatorHandler.obtainMessage(position, keys[position]);
        this.mOperatorHandler.sendMessage(mOperator);
    }

    class OperatorHandler extends Handler {
        public OperatorHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            String keyName = (String) msg.obj;
            Log.d(OperatorActivity.TAG, "keyName is " + keyName);
            int setValue = msg.what + 30;
            Log.d(OperatorActivity.TAG, "setValue = " + setValue);
            OperatorActivity.this.mATCmd = "AT+SPGCFIOTOPER=1," + setValue;
            OperatorActivity.this.mAtCmd1 = "AT+SPGCFIOTOPER=2," + setValue;
            OperatorActivity operatorActivity = OperatorActivity.this;
            operatorActivity.mStrTmp = IATUtils.sendATCmd(operatorActivity.mATCmd, "atchannel0");
            OperatorActivity operatorActivity2 = OperatorActivity.this;
            operatorActivity2.mStrTmp1 = IATUtils.sendATCmd(operatorActivity2.mAtCmd1, "atchannel0");
            EngineerModeActivity_2.checkAtReturnResult(OperatorActivity.this.mStrTmp, OperatorActivity.this.mStrTmp1, OperatorActivity.this.mContext);
        }
    }
}
