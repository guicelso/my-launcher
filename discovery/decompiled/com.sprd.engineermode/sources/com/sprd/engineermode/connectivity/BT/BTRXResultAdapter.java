package com.sprd.engineermode.connectivity.BT;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.sprd.engineermode.R;
import java.util.ArrayList;
import java.util.Collections;

/* JADX INFO: loaded from: classes4.dex */
public class BTRXResultAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    protected ArrayList<BTRXResult> result;
    private ArrayList<BTRXResult> uiResult = null;

    public BTRXResultAdapter(Context context, ArrayList<BTRXResult> result) {
        this.inflater = null;
        this.result = null;
        this.inflater = LayoutInflater.from(context);
        this.result = result;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        ArrayList<BTRXResult> arrayList = this.result;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    @Override // android.widget.Adapter
    public Object getItem(int p) {
        ArrayList<BTRXResult> arrayList = this.result;
        if (arrayList != null && p >= 0 && p < arrayList.size()) {
            return this.result.get(p);
        }
        return null;
    }

    @Override // android.widget.Adapter
    public long getItemId(int p) {
        return p;
    }

    private static class ViewHolder {
        TextView ber;
        TextView per;
        TextView rssi;

        private ViewHolder() {
        }
    }

    @Override // android.widget.Adapter
    public View getView(int p, View convert, ViewGroup parent) {
        ViewHolder holder;
        this.uiResult = (ArrayList) this.result.clone();
        Collections.reverse(this.uiResult);
        if (convert == null) {
            convert = this.inflater.inflate(R.layout.bt_list_row, (ViewGroup) null);
            holder = new ViewHolder();
            holder.rssi = (TextView) convert.findViewById(R.id.rssi);
            holder.per = (TextView) convert.findViewById(R.id.per);
            holder.ber = (TextView) convert.findViewById(R.id.ber);
            convert.setTag(holder);
        } else {
            holder = (ViewHolder) convert.getTag();
        }
        holder.rssi.setText(this.uiResult.get(p).rssi);
        holder.per.setText(this.uiResult.get(p).per);
        holder.ber.setText(this.uiResult.get(p).ber);
        return convert;
    }
}
