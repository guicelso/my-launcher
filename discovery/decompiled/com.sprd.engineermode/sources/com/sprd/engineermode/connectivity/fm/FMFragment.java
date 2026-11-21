package com.sprd.engineermode.connectivity.fm;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.sprd.engineermode.R;
import java.io.FileNotFoundException;
import java.io.FileReader;

/* JADX INFO: loaded from: classes4.dex */
public class FMFragment extends AbsFMFragment implements AdapterView.OnItemClickListener {
    private static final String HEADSET_STATE_PATH = "/sys/class/switch/h2w/state";
    public static final int SEEK = 1;
    public static final int SET_EARPHONE = 0;
    public static final String TAG = "FMFragment";
    public static final int TURN = 0;
    private ListView fmListView = null;

    @Override // android.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView();
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            replaceFragment(new EarphoneFragment());
        }
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    /* JADX WARN: Code duplicated, block: B:22:0x004f A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Code duplicated, block: B:38:? A[RETURN, SYNTHETIC] */
    private boolean isHeadsetExists() {
        char[] buffer = new char[1024];
        int newState = 0;
        FileReader file = null;
        try {
            try {
                try {
                    file = new FileReader(HEADSET_STATE_PATH);
                    int len = file.read(buffer, 0, 1024);
                    newState = Integer.valueOf(new String(buffer, 0, len).trim()).intValue();
                    file.close();
                } catch (Throwable th) {
                    if (file != null) {
                        try {
                            file.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (FileNotFoundException e2) {
                Log.e("FMTest", "This kernel does not have wired headset support");
                if (file != null) {
                    file.close();
                }
                if (newState != 0) {
                    return true;
                }
                return false;
            } catch (Exception e3) {
                Log.e("FMTest", "", e3);
                if (file != null) {
                    file.close();
                }
                if (newState != 0) {
                    return true;
                }
                return false;
            }
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        if (newState != 0) {
            return true;
        }
        return false;
    }

    public View initView() {
        View view = View.inflate(getActivity(), R.layout.fm_fragment_main, null);
        this.fmListView = (ListView) view.findViewById(R.id.fm_listview);
        this.fmListView.setOnItemClickListener(this);
        String[] adapterData = {getResources().getString(R.string.earphone)};
        ArrayAdapter<String> fmAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, adapterData);
        this.fmListView.setAdapter((ListAdapter) fmAdapter);
        return view;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fm_framelayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override // android.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        FMFragment fmFragment = new FMFragment();
        fragmentTransaction.remove(fmFragment);
        fragmentTransaction.replace(R.id.fm_framelayout, new FMFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
