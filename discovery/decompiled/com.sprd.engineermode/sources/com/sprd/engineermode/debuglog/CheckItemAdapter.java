package com.sprd.engineermode.debuglog;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.sprd.engineermode.R;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class CheckItemAdapter extends BaseAdapter {
    private static final String TAG = "CheckItemAdapter";
    private Context mContext;
    private ArrayList<String> mItemList;

    public static class ViewHolder {
        public TextView itemName;
    }

    public CheckItemAdapter(ArrayList<String> itemList, Context context) {
        this.mItemList = itemList;
        this.mContext = context;
        Log.d(TAG, "itemList size" + this.mItemList.size());
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mItemList.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        if (position < this.mItemList.size()) {
            return this.mItemList.get(position);
        }
        return null;
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder;
        if (convertView == null) {
            vHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.detailed_item, (ViewGroup) null);
            vHolder.itemName = (TextView) convertView.findViewById(R.id.item_name);
            convertView.setTag(R.id.action_save, vHolder);
        } else {
            vHolder = (ViewHolder) convertView.getTag(R.id.action_save);
            vHolder.itemName.requestLayout();
        }
        Log.d(TAG, "title:" + this.mItemList.get(position));
        vHolder.itemName.setText(this.mItemList.get(position));
        return convertView;
    }
}
