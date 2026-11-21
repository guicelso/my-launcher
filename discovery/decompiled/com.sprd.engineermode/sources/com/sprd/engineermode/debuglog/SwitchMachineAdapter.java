package com.sprd.engineermode.debuglog;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.os.EnvironmentCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.sprd.engineermode.R;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class SwitchMachineAdapter extends BaseAdapter {
    private static final int MAX_UPDATE_ITEM_NUM = 10;
    private List<String> mBootModeArray;
    private LayoutInflater mInflater;
    private int mMode;
    private List<String> mTimeArray;
    private boolean mIsScrolling = false;
    private final String TAG = "SwitchMachineAdapter";
    private List<String> tempTimeArray = new ArrayList();
    private List<String> tempModeArray = new ArrayList();

    public SwitchMachineAdapter(Context mContext, List<String> timelArray, List<String> modeArray, int mode) {
        this.mMode = 1;
        this.mInflater = LayoutInflater.from(mContext);
        this.mTimeArray = timelArray;
        this.mBootModeArray = modeArray;
        this.mMode = mode;
        this.tempTimeArray.clear();
        this.tempModeArray.clear();
        int markSize = this.mTimeArray.size();
        markSize = markSize > modeArray.size() ? modeArray.size() : markSize;
        Log.d("SwitchMachineAdapter", "markSize = " + markSize);
        for (int i = 0; i < markSize; i++) {
            Log.d("SwitchMachineAdapter", "timelArray.get(" + i + ") = " + timelArray.get(i));
            Log.d("SwitchMachineAdapter", "tempModeArray.get(" + i + ") = " + modeArray.get(i));
            this.tempTimeArray.add(0, timelArray.get(i));
            this.tempModeArray.add(0, modeArray.get(i));
        }
    }

    public void setScrollingBlean(boolean scrolling) {
        this.mIsScrolling = scrolling;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        Log.e("SwitchMachineAdapter", "mIsScrolling is :" + this.mIsScrolling);
        if (this.mIsScrolling) {
            int markSize = this.mTimeArray.size();
            if (markSize > this.mBootModeArray.size()) {
                markSize = this.mBootModeArray.size();
            }
            this.tempTimeArray.clear();
            this.tempModeArray.clear();
            for (int i = 0; i < markSize; i++) {
                this.tempTimeArray.add(0, this.mTimeArray.get(i));
                this.tempModeArray.add(0, this.mBootModeArray.get(i));
            }
            return this.mTimeArray.size();
        }
        int markSize2 = this.mTimeArray.size();
        if (markSize2 > this.mBootModeArray.size()) {
            markSize2 = this.mBootModeArray.size();
        }
        if (markSize2 <= 0) {
            return 0;
        }
        if (markSize2 > 10) {
            this.tempTimeArray.clear();
            this.tempModeArray.clear();
            for (int i2 = 0; i2 < 10; i2++) {
                this.tempTimeArray.add(this.mTimeArray.get((markSize2 - i2) - 1));
                this.tempModeArray.add(this.mBootModeArray.get((markSize2 - i2) - 1));
            }
            return this.tempTimeArray.size();
        }
        this.tempTimeArray.clear();
        this.tempModeArray.clear();
        for (int i3 = 0; i3 < markSize2; i3++) {
            this.tempTimeArray.add(0, this.mTimeArray.get(i3));
            this.tempModeArray.add(0, this.mBootModeArray.get(i3));
        }
        return this.tempTimeArray.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        return this.tempTimeArray.get(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        Log.d("SwitchMachineAdapter", "position = " + position + " time = " + this.tempTimeArray.get(position) + " mode = " + this.tempModeArray.get(position));
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = this.mInflater.inflate(R.layout.item, (ViewGroup) null);
            holder.num = (TextView) convertView.findViewById(R.id.num);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.mode = (TextView) convertView.findViewById(R.id.mode);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String bootMode = this.tempModeArray.get(position);
        int i = this.mMode;
        if (i == 1) {
            int modeId = R.string.unknown_power_on;
            if (bootMode == null) {
                modeId = R.string.normal_poweron;
            } else if (bootMode.contains("engtest")) {
                modeId = R.string.engtest;
            } else if (bootMode.contains(NotificationCompat.CATEGORY_ALARM)) {
                modeId = R.string.alarm;
            } else if (bootMode.contains("charger")) {
                modeId = R.string.charger;
            } else if (bootMode.contains(EnvironmentCompat.MEDIA_UNKNOWN)) {
                modeId = R.string.normal_poweron;
            } else if (bootMode.contains("wdgreboot")) {
                modeId = R.string.wdgreboot;
            } else if (bootMode.contains("panic")) {
                modeId = R.string.panic;
            } else if (bootMode.contains("special")) {
                modeId = R.string.special;
            }
            holder.mode.setText(modeId);
        } else if (i == 2) {
            int modeId2 = R.string.unknown_power_off;
            if (bootMode == null) {
                modeId2 = R.string.normal_poweroff;
            } else if (bootMode.contains("timer")) {
                modeId2 = R.string.timer;
            } else if (bootMode.contains("power_shutdown")) {
                modeId2 = R.string.power_shutdown_key;
            } else if (bootMode.contains("power_longpress_reboot")) {
                modeId2 = R.string.power_longpress_reboot_key;
            } else if (bootMode.contains("power_reboot")) {
                modeId2 = R.string.power_reboot_key;
            } else if (bootMode.contains("recovery")) {
                modeId2 = R.string.recovery;
            } else if (bootMode.contains("no_power")) {
                modeId2 = R.string.battery;
            } else if (bootMode.contains("over_temp")) {
                modeId2 = R.string.over_temp;
            }
            holder.mode.setText(modeId2);
        } else if (i == 3) {
            holder.mode.setText(bootMode);
        } else {
            holder.mode.setText(bootMode);
        }
        holder.num.setText(Integer.toString(position + 1));
        holder.time.setText(this.tempTimeArray.get(position));
        return convertView;
    }

    static class ViewHolder {
        public TextView mode;
        public TextView num;
        public TextView time;

        ViewHolder() {
        }
    }
}
