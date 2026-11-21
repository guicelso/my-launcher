package com.sprd.engineermode.telephony.netinfostatistics;

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
import com.unisoc.engineermode.core.impl.nonpublic.TelephonyManagerProxy;
import com.unisoc.engineermode.core.intf.ITelephonyApi;

/* JADX INFO: loaded from: classes4.dex */
public class AttachTimeFragment extends Fragment {
    private Context mContext;
    private boolean mFragmentDestroyed;
    private HandlerThread mHT;
    private NetinfoHandler mNetinfoHandler;
    private String TAG = "AttachTimeFragment";
    private ITelephonyApi teleApi = CoreApi.getTelephonyApi();
    private Handler mUiThread = new Handler();

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.attachtime, container, false);
    }

    @Override // android.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
        Log.d(this.TAG, "onCreate");
        if (this.mHT == null) {
            this.mHT = new HandlerThread(this.TAG);
            this.mHT.start();
        }
        this.mNetinfoHandler = new NetinfoHandler(this.mHT.getLooper());
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
                AttachTimeFragment.this.parseNetStatistic(simid);
            }
        }
    }

    @Override // android.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private int getResIdByRowCol(String prefix, int row, int col) {
        String resId = prefix + row + col;
        Log.d(this.TAG, String.format("getResIdByRowCol, ResId=%s, row=%d, col=%d", resId, Integer.valueOf(row), Integer.valueOf(col)));
        return getResources().getIdentifier(resId, "id", getActivity().getPackageName());
    }

    @Override // android.app.Fragment
    public void onStart() {
        super.onStart();
        Log.d(this.TAG, "onStart");
        Intent intent = getActivity().getIntent();
        int simindex = intent.getIntExtra(SimSelectHelperActivity.INTENT_SIM_INDEX, -1);
        Log.d(this.TAG, "onResume simindex=" + simindex);
        Message m = this.mNetinfoHandler.obtainMessage(simindex);
        this.mNetinfoHandler.sendMessage(m);
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
        if (this.mNetinfoHandler != null) {
            Log.d(this.TAG, "HandlerThread has quit");
            this.mNetinfoHandler.getLooper().quit();
        }
        HandlerThread handlerThread = this.mHT;
        if (handlerThread != null) {
            ThreadUtils.stopThread(handlerThread);
        }
        this.mFragmentDestroyed = true;
        Log.d(this.TAG, "onDestroy");
    }

    private TextView createTextView(String text) {
        TextView view = new TextView(this.mContext);
        view.setGravity(19);
        view.setLayoutParams(new TableRow.LayoutParams(-2, -2));
        view.setText(text);
        return view;
    }

    private TableRow createRow(ITelephonyApi.INetInfoStat.DataItem item) {
        TableRow tr = new TableRow(this.mContext);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(-2, -2);
        int paddingPx = (int) TypedValue.applyDimension(1, 5.0f, this.mContext.getResources().getDisplayMetrics());
        Log.d(this.TAG, "padding: " + paddingPx);
        tr.setPadding(paddingPx, paddingPx, 0, 0);
        tr.setLayoutParams(lp);
        tr.addView(createTextView(item.desc));
        tr.addView(createTextView(item.data));
        return tr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUi(ITelephonyApi.INetInfoStat.DataItem[] items) {
        TableLayout tl = (TableLayout) getActivity().findViewById(R.id.attachtime_tablelayout);
        for (ITelephonyApi.INetInfoStat.DataItem item : items) {
            Log.d(this.TAG, String.format("%s: %s ", item.desc, item.data));
            tl.addView(createRow(item));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parseNetStatistic(int simindex) {
        if (this.mFragmentDestroyed) {
            Log.d(this.TAG, "mFragmentDestroyed ==" + this.mFragmentDestroyed);
            return;
        }
        try {
            final ITelephonyApi.INetInfoStat.DataItem[] data = this.teleApi.netInfoStat().getAttachTime(simindex);
            this.mUiThread.post(new Runnable() { // from class: com.sprd.engineermode.telephony.netinfostatistics.AttachTimeFragment.1
                @Override // java.lang.Runnable
                public void run() {
                    AttachTimeFragment.this.updateUi(data);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
