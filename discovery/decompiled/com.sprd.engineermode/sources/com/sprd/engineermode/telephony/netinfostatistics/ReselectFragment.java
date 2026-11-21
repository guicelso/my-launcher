package com.sprd.engineermode.telephony.netinfostatistics;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.sprd.engineermode.R;
import com.sprd.engineermode.telephony.SimSelectHelperActivity;
import com.sprd.engineermode.utils.ThreadUtils;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.data.telephony.netinfostat.NetInfoStatData;
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;
import com.unisoc.engineermode.core.intf.ITelephonyApi;

/* JADX INFO: loaded from: classes4.dex */
public class ReselectFragment extends Fragment {
    public int COL;
    public int ROW;
    private Context mContext;
    private boolean mFragmentDestroyed;
    private HandlerThread mht;
    private NetinfoHandler netinfoHandler;
    private String TAG = "ReselectFragment";
    private Handler mUiThread = new Handler();
    private ITelephonyApi teleApi = CoreApi.getTelephonyApi();

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(this.TAG, "onCreateView");
        View view = inflater.inflate(R.layout.cell_reselect_lte, container, false);
        return view;
    }

    @Override // android.app.Fragment
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(this.TAG, "onAttach");
    }

    @Override // android.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this.TAG, "onCreate");
        this.mContext = getActivity();
        if (this.mht == null) {
            this.mht = new HandlerThread(this.TAG);
            this.mht.start();
        }
        this.netinfoHandler = new NetinfoHandler(this.mht.getLooper());
        this.mFragmentDestroyed = false;
    }

    class NetinfoHandler extends Handler {
        public NetinfoHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            int simid = msg.what;
            if (TelephonyManagerProxy.isSimExist(simid)) {
                ReselectFragment.this.parseNetStatisticlte(simid);
            }
        }
    }

    @Override // android.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(this.TAG, "onActivityCreated");
    }

    private int getResIdByRowCol(String prefix, int row, int col) {
        return getResources().getIdentifier(prefix + row + col, "id", this.mContext.getPackageName());
    }

    @Override // android.app.Fragment
    public void onStart() {
        super.onStart();
        Log.d(this.TAG, "onStart");
        Intent intent = getActivity().getIntent();
        int simindex = intent.getIntExtra(SimSelectHelperActivity.INTENT_SIM_INDEX, -1);
        Log.d(this.TAG, "onResume simindex=" + simindex);
        Message m = this.netinfoHandler.obtainMessage(simindex);
        this.netinfoHandler.sendMessage(m);
    }

    @Override // android.app.Fragment
    public void onStop() {
        super.onStop();
        this.mFragmentDestroyed = true;
        Log.d(this.TAG, "onStop");
    }

    @Override // android.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.mFragmentDestroyed = false;
        Log.d(this.TAG, "onDestroyView");
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        if (this.netinfoHandler != null) {
            Log.d(this.TAG, "HandlerThread has quit");
            this.netinfoHandler.getLooper().quit();
        }
        HandlerThread handlerThread = this.mht;
        if (handlerThread != null) {
            ThreadUtils.stopThread(handlerThread);
        }
        this.mFragmentDestroyed = true;
        Log.d(this.TAG, "onDestroy");
    }

    @Override // android.app.Fragment
    public void onDetach() {
        super.onDetach();
        Log.d(this.TAG, "onDetach");
    }

    private TextView createTextView(String text) {
        TextView view = new TextView(this.mContext);
        view.setGravity(19);
        view.setLayoutParams(new TableRow.LayoutParams(-2, -2));
        view.setText(text);
        return view;
    }

    private TableRow createRow(NetInfoStatData.Item item) {
        TableRow tr = new TableRow(this.mContext);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(-2, -2);
        int paddingPx = (int) TypedValue.applyDimension(1, 5.0f, this.mContext.getResources().getDisplayMetrics());
        Log.d(this.TAG, "padding: " + paddingPx);
        tr.setPadding(paddingPx, paddingPx, 0, 0);
        tr.setLayoutParams(lp);
        tr.addView(createTextView(item.getDesc()));
        tr.addView(createTextView(item.getSuccess()));
        tr.addView(createTextView(item.getFail()));
        tr.addView(createTextView(item.getRatio()));
        tr.addView(createTextView(item.getDelay()));
        return tr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUi(NetInfoStatData.Item[] items) {
        TableLayout tl = (TableLayout) getActivity().findViewById(R.id.reselect_lte_tablelayout);
        for (NetInfoStatData.Item item : items) {
            Log.d(this.TAG, String.format("%s: %s  %s  %s %s", item.getDesc(), item.getSuccess(), item.getFail(), item.getRatio(), item.getDelay()));
            tl.addView(createRow(item));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parseNetStatisticlte(int simindex) {
        if (this.mFragmentDestroyed) {
            Log.d(this.TAG, "mFragmentDestroyed ==" + this.mFragmentDestroyed);
            return;
        }
        try {
            final NetInfoStatData data = this.teleApi.netInfoStat().getReselectInfo(simindex);
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.netinfostatistics.ReselectFragment.1
                @Override // java.lang.Runnable
                public void run() {
                    ReselectFragment.this.updateUi(data.getData());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
