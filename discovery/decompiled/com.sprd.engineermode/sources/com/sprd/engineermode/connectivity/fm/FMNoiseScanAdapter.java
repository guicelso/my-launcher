package com.sprd.engineermode.connectivity.fm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.sprd.engineermode.R;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class FMNoiseScanAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    protected ArrayList<NoiseScanResult> result;

    public FMNoiseScanAdapter(Context context, ArrayList<NoiseScanResult> result) {
        this.inflater = null;
        this.result = null;
        this.inflater = LayoutInflater.from(context);
        this.result = result;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        ArrayList<NoiseScanResult> arrayList = this.result;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    @Override // android.widget.Adapter
    public Object getItem(int p) {
        ArrayList<NoiseScanResult> arrayList = this.result;
        if (arrayList != null && p >= 0 && p < arrayList.size()) {
            return this.result.get(p);
        }
        return null;
    }

    @Override // android.widget.Adapter
    public long getItemId(int p) {
        return p;
    }

    class ViewHolder {
        TextView freq;
        TextView rssi;
        TextView snr;

        ViewHolder() {
        }
    }

    @Override // android.widget.Adapter
    public View getView(int p, View convert, ViewGroup parent) {
        ViewHolder holder;
        if (convert == null) {
            convert = this.inflater.inflate(R.layout.noise_scan_list_row, parent, false);
            holder = new ViewHolder();
            holder.freq = (TextView) convert.findViewById(R.id.freq);
            holder.rssi = (TextView) convert.findViewById(R.id.rssi);
            holder.snr = (TextView) convert.findViewById(R.id.snr);
            convert.setTag(holder);
        } else {
            holder = (ViewHolder) convert.getTag();
        }
        holder.freq.setText(this.result.get(p).freq);
        holder.rssi.setText(this.result.get(p).rssi);
        holder.snr.setText(this.result.get(p).snr);
        return convert;
    }
}
