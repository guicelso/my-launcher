package com.sprd.engineermode.hardware;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.sprd.engineermode.R;
import com.sprd.engineermode.utils.EMFileUtils;
import com.unisoc.engineermode.core.CoreApi;
import com.unisoc.engineermode.core.intf.IHardwareApi;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/* JADX INFO: loaded from: classes4.dex */
public class CoulometerPowerTestActivity extends Activity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String CC_TEST_CMD_PATH = "/sys/class/power_supply/sprdfgu/";
    private static final String TAG = "CoulometerPowerTestActivity";
    private static final int TWINTY_MINUTE = 20;
    private static final int UPDATE_UI_END = 1;
    private AlarmManager am;
    private Calendar c1;
    private Calendar c1_end;
    private Calendar c2;
    private Calendar c2_end;
    private Calendar c3;
    private Calendar c3_end;
    private String curDate;
    private String curDateEnd;
    private EditText dayEditText1;
    private EditText dayEditText2;
    private EditText dayEditText3;
    private EditText dayEditTextEnd1;
    private EditText dayEditTextEnd2;
    private EditText dayEditTextEnd3;
    private String during1;
    private String during2;
    private String during3;
    private SharedPreferences.Editor editor;
    private EditText endTimeEditText1;
    private EditText endTimeEditText2;
    private EditText endTimeEditText3;
    private EditText hourEditText1;
    private EditText hourEditText2;
    private EditText hourEditText3;
    private EditText hourEditTextEnd1;
    private EditText hourEditTextEnd2;
    private EditText hourEditTextEnd3;
    private Intent intent;
    private CheckBox mCheckbox1;
    private CheckBox mCheckbox2;
    private CheckBox mCheckbox3;
    private String mD;
    private String mDEnd;
    private String mH;
    private String mHEnd;
    private String mM;
    private String mMEnd;
    private String mMin;
    private String mMinEnd;
    private String mY;
    private String mYEnd;
    private EditText minuteEditText1;
    private EditText minuteEditText2;
    private EditText minuteEditText3;
    private EditText minuteEditTextEnd1;
    private EditText minuteEditTextEnd2;
    private EditText minuteEditTextEnd3;
    private EditText monthEditText1;
    private EditText monthEditText2;
    private EditText monthEditText3;
    private EditText monthEditTextEnd1;
    private EditText monthEditTextEnd2;
    private EditText monthEditTextEnd3;
    private PendingIntent pi1;
    private PendingIntent pi1_end;
    private PendingIntent pi2;
    private PendingIntent pi2_end;
    private PendingIntent pi3;
    private PendingIntent pi3_end;
    private SharedPreferences preferences;
    private String startDateStr;
    private int t1;
    private int t2;
    private int t3;
    private TextView textStatus1;
    private TextView textStatus2;
    private TextView textStatus3;
    private Button timeBeginBtn1;
    private Button timeBeginBtn2;
    private Button timeBeginBtn3;
    private Button timeResultBtn1;
    private Button timeResultBtn2;
    private Button timeResultBtn3;
    private EditText yearEditText1;
    private EditText yearEditText2;
    private EditText yearEditText3;
    private EditText yearEditTextEnd1;
    private EditText yearEditTextEnd2;
    private EditText yearEditTextEnd3;
    public static String[] mCCValue = {"0", "0", "0"};
    public static String[] mCCValueVol = {"0", "0", "0"};
    public static boolean[] timerStop = {true, true, true};
    public static ArrayList<String> testResults1 = new ArrayList<>();
    public static ArrayList<String> testResults2 = new ArrayList<>();
    public static ArrayList<String> testResults3 = new ArrayList<>();
    public static ArrayList<String> testTimes1 = new ArrayList<>();
    public static ArrayList<String> testTimes2 = new ArrayList<>();
    public static ArrayList<String> testTimes3 = new ArrayList<>();
    private int mYear = 2018;
    private int mMonth = 1;
    private int mDay = 1;
    private int mHour = 1;
    private int mMinute = 0;
    private int mYearEnd = 2018;
    private int mMonthEnd = 1;
    private int mDayEnd = 1;
    private int mHourEnd = 1;
    private int mMinuteEnd = 0;
    private IHardwareApi hwApi = CoreApi.getHardwareApi();

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coulometer_power);
        this.c1 = Calendar.getInstance();
        this.c2 = Calendar.getInstance();
        this.c3 = Calendar.getInstance();
        this.c1_end = Calendar.getInstance();
        this.c2_end = Calendar.getInstance();
        this.c3_end = Calendar.getInstance();
        this.am = (AlarmManager) getSystemService(NotificationCompat.CATEGORY_ALARM);
        initUI();
        this.preferences = getSharedPreferences("cc_status", 0);
        this.preferences.registerOnSharedPreferenceChangeListener(this);
        this.editor = this.preferences.edit();
        refreshStatus();
    }

    private void initUI() {
        this.timeBeginBtn1 = (Button) findViewById(R.id.time1_begin_btn);
        this.timeBeginBtn1.setOnClickListener(this);
        this.timeBeginBtn2 = (Button) findViewById(R.id.time2_begin_btn);
        this.timeBeginBtn2.setOnClickListener(this);
        this.timeBeginBtn3 = (Button) findViewById(R.id.time3_begin_btn);
        this.timeBeginBtn3.setOnClickListener(this);
        this.timeResultBtn1 = (Button) findViewById(R.id.time1_result_btn);
        this.timeResultBtn1.setOnClickListener(this);
        this.timeResultBtn2 = (Button) findViewById(R.id.time2_result_btn);
        this.timeResultBtn2.setOnClickListener(this);
        this.timeResultBtn3 = (Button) findViewById(R.id.time3_result_btn);
        this.timeResultBtn3.setOnClickListener(this);
        this.mCheckbox1 = (CheckBox) findViewById(R.id.checkbox1);
        this.mCheckbox1.setOnClickListener(this);
        this.mCheckbox2 = (CheckBox) findViewById(R.id.checkbox2);
        this.mCheckbox2.setOnClickListener(this);
        this.mCheckbox3 = (CheckBox) findViewById(R.id.checkbox3);
        this.mCheckbox3.setOnClickListener(this);
        this.yearEditText1 = (EditText) findViewById(R.id.time1_year_edit);
        this.yearEditText2 = (EditText) findViewById(R.id.time2_year_edit);
        this.yearEditText3 = (EditText) findViewById(R.id.time3_year_edit);
        this.monthEditText1 = (EditText) findViewById(R.id.time1_month_edit);
        this.monthEditText2 = (EditText) findViewById(R.id.time2_month_edit);
        this.monthEditText3 = (EditText) findViewById(R.id.time3_month_edit);
        this.dayEditText1 = (EditText) findViewById(R.id.time1_day_edit);
        this.dayEditText2 = (EditText) findViewById(R.id.time2_day_edit);
        this.dayEditText3 = (EditText) findViewById(R.id.time3_day_edit);
        this.hourEditText1 = (EditText) findViewById(R.id.time1_hour_edit);
        this.hourEditText2 = (EditText) findViewById(R.id.time2_hour_edit);
        this.hourEditText3 = (EditText) findViewById(R.id.time3_hour_edit);
        this.minuteEditText1 = (EditText) findViewById(R.id.time1_minute_edit);
        this.minuteEditText2 = (EditText) findViewById(R.id.time2_minute_edit);
        this.minuteEditText3 = (EditText) findViewById(R.id.time3_minute_edit);
        this.yearEditTextEnd1 = (EditText) findViewById(R.id.time1_year_edit_end);
        this.yearEditTextEnd2 = (EditText) findViewById(R.id.time2_year_edit_end);
        this.yearEditTextEnd3 = (EditText) findViewById(R.id.time3_year_edit_end);
        this.monthEditTextEnd1 = (EditText) findViewById(R.id.time1_month_edit_end);
        this.monthEditTextEnd2 = (EditText) findViewById(R.id.time2_month_edit_end);
        this.monthEditTextEnd3 = (EditText) findViewById(R.id.time3_month_edit_end);
        this.dayEditTextEnd1 = (EditText) findViewById(R.id.time1_day_edit_end);
        this.dayEditTextEnd2 = (EditText) findViewById(R.id.time2_day_edit_end);
        this.dayEditTextEnd3 = (EditText) findViewById(R.id.time3_day_edit_end);
        this.hourEditTextEnd1 = (EditText) findViewById(R.id.time1_hour_edit_end);
        this.hourEditTextEnd2 = (EditText) findViewById(R.id.time2_hour_edit_end);
        this.hourEditTextEnd3 = (EditText) findViewById(R.id.time3_hour_edit_end);
        this.minuteEditTextEnd1 = (EditText) findViewById(R.id.time1_minute_edit_end);
        this.minuteEditTextEnd2 = (EditText) findViewById(R.id.time2_minute_edit_end);
        this.minuteEditTextEnd3 = (EditText) findViewById(R.id.time3_minute_edit_end);
        this.endTimeEditText1 = (EditText) findViewById(R.id.time1_end_edit);
        this.endTimeEditText2 = (EditText) findViewById(R.id.time2_end_edit);
        this.endTimeEditText3 = (EditText) findViewById(R.id.time3_end_edit);
        this.textStatus1 = (TextView) findViewById(R.id.textStatus1);
        this.textStatus2 = (TextView) findViewById(R.id.textStatus2);
        this.textStatus3 = (TextView) findViewById(R.id.textStatus3);
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
        this.curDate = sDateFormat.format(new Date());
        String[] strings = this.curDate.split(":");
        if (strings.length == 5) {
            this.yearEditText1.setText(strings[0]);
            this.yearEditText2.setText(strings[0]);
            this.yearEditText3.setText(strings[0]);
            this.monthEditText1.setText(strings[1]);
            this.monthEditText2.setText(strings[1]);
            this.monthEditText3.setText(strings[1]);
            this.dayEditText1.setText(strings[2]);
            this.dayEditText2.setText(strings[2]);
            this.dayEditText3.setText(strings[2]);
            this.hourEditText1.setText(strings[3]);
            this.hourEditText2.setText(strings[3]);
            this.hourEditText3.setText(strings[3]);
            this.minuteEditText1.setText(strings[4]);
            this.minuteEditText2.setText(strings[4]);
            this.minuteEditText3.setText(strings[4]);
            this.yearEditTextEnd1.setText(strings[0]);
            this.yearEditTextEnd2.setText(strings[0]);
            this.yearEditTextEnd3.setText(strings[0]);
            this.monthEditTextEnd1.setText(strings[1]);
            this.monthEditTextEnd2.setText(strings[1]);
            this.monthEditTextEnd3.setText(strings[1]);
            this.dayEditTextEnd1.setText(strings[2]);
            this.dayEditTextEnd2.setText(strings[2]);
            this.dayEditTextEnd3.setText(strings[2]);
            this.hourEditTextEnd1.setText(strings[3]);
            this.hourEditTextEnd2.setText(strings[3]);
            this.hourEditTextEnd3.setText(strings[3]);
            this.minuteEditTextEnd1.setText(strings[4]);
            this.minuteEditTextEnd2.setText(strings[4]);
            this.minuteEditTextEnd3.setText(strings[4]);
        }
    }

    private void refreshStatus() {
        boolean timeStop1 = this.preferences.getBoolean("time1_stop", true);
        if (!timeStop1) {
            this.yearEditText1.setText(this.preferences.getString("time1_year", "2018"));
            this.monthEditText1.setText(this.preferences.getString("time1_month", "1"));
            this.dayEditText1.setText(this.preferences.getString("time1_day", "1"));
            this.hourEditText1.setText(this.preferences.getString("time1_hour", "1"));
            this.minuteEditText1.setText(this.preferences.getString("time1_minute", "1"));
            this.yearEditTextEnd1.setText(this.preferences.getString("timeEnd1_year", "2018"));
            this.monthEditTextEnd1.setText(this.preferences.getString("timeEnd1_month", "1"));
            this.dayEditTextEnd1.setText(this.preferences.getString("timeEnd1_day", "1"));
            this.hourEditTextEnd1.setText(this.preferences.getString("timeEnd1_hour", "1"));
            this.minuteEditTextEnd1.setText(this.preferences.getString("timeEnd1_minute", "1"));
            this.endTimeEditText1.setText(this.preferences.getString("time1_during", "1"));
            this.textStatus1.setText(getString(R.string.coulometer_testing));
            this.timeBeginBtn1.setEnabled(false);
        } else {
            this.textStatus1.setText(getString(R.string.coulometer_test_stop));
            this.timeBeginBtn1.setEnabled(true);
        }
        boolean timeStop2 = this.preferences.getBoolean("time2_stop", true);
        if (!timeStop2) {
            this.yearEditText2.setText(this.preferences.getString("time2_year", "2018"));
            this.monthEditText2.setText(this.preferences.getString("time2_month", "1"));
            this.dayEditText2.setText(this.preferences.getString("time2_day", "1"));
            this.hourEditText2.setText(this.preferences.getString("time2_hour", "1"));
            this.minuteEditText2.setText(this.preferences.getString("time2_minute", "1"));
            this.yearEditTextEnd2.setText(this.preferences.getString("timeEnd2_year", "2018"));
            this.monthEditTextEnd2.setText(this.preferences.getString("timeEnd2_month", "1"));
            this.dayEditTextEnd2.setText(this.preferences.getString("timeEnd2_day", "1"));
            this.hourEditTextEnd2.setText(this.preferences.getString("timeEnd2_hour", "1"));
            this.minuteEditTextEnd2.setText(this.preferences.getString("timeEnd2_minute", "1"));
            this.endTimeEditText2.setText(this.preferences.getString("time2_during", "1"));
            this.textStatus2.setText(getString(R.string.coulometer_testing));
            this.timeBeginBtn2.setEnabled(false);
        } else {
            this.textStatus2.setText(getString(R.string.coulometer_test_stop));
            this.timeBeginBtn2.setEnabled(true);
        }
        boolean timeStop3 = this.preferences.getBoolean("time3_stop", true);
        if (!timeStop3) {
            this.yearEditText3.setText(this.preferences.getString("time3_year", "2018"));
            this.monthEditText3.setText(this.preferences.getString("time3_month", "1"));
            this.dayEditText3.setText(this.preferences.getString("time3_day", "1"));
            this.hourEditText3.setText(this.preferences.getString("time3_hour", "1"));
            this.minuteEditText3.setText(this.preferences.getString("time3_minute", "1"));
            this.yearEditTextEnd3.setText(this.preferences.getString("timeEnd3_year", "2018"));
            this.monthEditTextEnd3.setText(this.preferences.getString("timeEnd3_month", "1"));
            this.dayEditTextEnd3.setText(this.preferences.getString("timeEnd3_day", "1"));
            this.hourEditTextEnd3.setText(this.preferences.getString("timeEnd3_hour", "1"));
            this.minuteEditTextEnd3.setText(this.preferences.getString("timeEnd3_minute", "1"));
            this.endTimeEditText3.setText(this.preferences.getString("time3_during", "1"));
            this.textStatus3.setText(getString(R.string.coulometer_testing));
            this.timeBeginBtn3.setEnabled(false);
            return;
        }
        this.textStatus3.setText(getString(R.string.coulometer_test_stop));
        this.timeBeginBtn3.setEnabled(true);
    }

    @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.i(TAG, "instance variable key=" + key);
        if (key.equals("time1_stop") && this.preferences.getBoolean("time1_stop", true)) {
            this.timeBeginBtn1.setEnabled(true);
            this.timeResultBtn1.setEnabled(true);
            this.textStatus1.setText(getString(R.string.coulometer_test_stop));
        } else if (key.equals("time2_stop") && this.preferences.getBoolean("time2_stop", true)) {
            this.timeBeginBtn2.setEnabled(true);
            this.timeResultBtn2.setEnabled(true);
            this.textStatus2.setText(getString(R.string.coulometer_test_stop));
        } else if (key.equals("time3_stop") && this.preferences.getBoolean("time3_stop", true)) {
            this.timeBeginBtn3.setEnabled(true);
            this.timeResultBtn3.setEnabled(true);
            this.textStatus3.setText(getString(R.string.coulometer_test_stop));
        }
    }

    @Override // android.app.Activity
    public void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override // android.app.Activity
    public void onResume() {
        Log.d(TAG, "onResume");
        if (this.preferences.getBoolean("time1_stop", true)) {
            this.timeBeginBtn1.setEnabled(true);
            this.timeResultBtn1.setEnabled(true);
            this.textStatus1.setText(getString(R.string.coulometer_test_stop));
        }
        if (this.preferences.getBoolean("time2_stop", true)) {
            this.timeBeginBtn2.setEnabled(true);
            this.timeResultBtn2.setEnabled(true);
            this.textStatus2.setText(getString(R.string.coulometer_test_stop));
        }
        if (this.preferences.getBoolean("time3_stop", true)) {
            this.timeBeginBtn3.setEnabled(true);
            this.timeResultBtn3.setEnabled(true);
            this.textStatus3.setText(getString(R.string.coulometer_test_stop));
        }
        super.onResume();
    }

    @Override // android.app.Activity
    public void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        boolean[] zArr = timerStop;
        if (!zArr[0]) {
            this.editor.putBoolean("time1_stop", zArr[0]);
            this.editor.putString("time1_year", this.yearEditText1.getText().toString());
            this.editor.putString("time1_month", this.monthEditText1.getText().toString());
            this.editor.putString("time1_day", this.dayEditText1.getText().toString());
            this.editor.putString("time1_hour", this.hourEditText1.getText().toString());
            this.editor.putString("time1_minute", this.minuteEditText1.getText().toString());
            this.editor.putString("time1_during", this.endTimeEditText1.getText().toString());
            this.editor.putString("timeEnd1_year", this.yearEditTextEnd1.getText().toString());
            this.editor.putString("timeEnd1_month", this.monthEditTextEnd1.getText().toString());
            this.editor.putString("timeEnd1_day", this.dayEditTextEnd1.getText().toString());
            this.editor.putString("timeEnd1_hour", this.hourEditTextEnd1.getText().toString());
            this.editor.putString("timeEnd1_minute", this.minuteEditTextEnd1.getText().toString());
            this.editor.commit();
        }
        boolean[] zArr2 = timerStop;
        if (!zArr2[1]) {
            this.editor.putBoolean("time2_stop", zArr2[1]);
            this.editor.putString("time2_year", this.yearEditText2.getText().toString());
            this.editor.putString("time2_month", this.monthEditText2.getText().toString());
            this.editor.putString("time2_day", this.dayEditText2.getText().toString());
            this.editor.putString("time2_hour", this.hourEditText2.getText().toString());
            this.editor.putString("time2_minute", this.minuteEditText2.getText().toString());
            this.editor.putString("time2_during", this.endTimeEditText2.getText().toString());
            this.editor.putString("timeEnd2_year", this.yearEditTextEnd2.getText().toString());
            this.editor.putString("timeEnd2_month", this.monthEditTextEnd2.getText().toString());
            this.editor.putString("timeEnd2_day", this.dayEditTextEnd2.getText().toString());
            this.editor.putString("timeEnd2_hour", this.hourEditTextEnd2.getText().toString());
            this.editor.putString("timeEnd2_minute", this.minuteEditTextEnd2.getText().toString());
            this.editor.commit();
        }
        boolean[] zArr3 = timerStop;
        if (!zArr3[2]) {
            this.editor.putBoolean("time3_stop", zArr3[2]);
            this.editor.putString("time3_year", this.yearEditText3.getText().toString());
            this.editor.putString("time3_month", this.monthEditText3.getText().toString());
            this.editor.putString("time3_day", this.dayEditText3.getText().toString());
            this.editor.putString("time3_hour", this.hourEditText3.getText().toString());
            this.editor.putString("time3_minute", this.minuteEditText3.getText().toString());
            this.editor.putString("time3_during", this.endTimeEditText3.getText().toString());
            this.editor.putString("timeEnd3_year", this.yearEditTextEnd3.getText().toString());
            this.editor.putString("timeEnd3_month", this.monthEditTextEnd3.getText().toString());
            this.editor.putString("timeEnd3_day", this.dayEditTextEnd3.getText().toString());
            this.editor.putString("timeEnd3_hour", this.hourEditTextEnd3.getText().toString());
            this.editor.putString("timeEnd3_minute", this.minuteEditTextEnd3.getText().toString());
            this.editor.commit();
        }
        super.onDestroy();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checkbox1 /* 2131296506 */:
                Log.d(TAG, "onClick checkbox1");
                this.during1 = this.endTimeEditText1.getText().toString();
                if (this.during1.trim().equals("")) {
                    this.during1 = "1.5";
                }
                try {
                    if (this.t1 <= 20) {
                        Toast.makeText(this, getString(R.string.less_than_twinty_toast), 1).show();
                        this.mCheckbox1.setChecked(false);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.checkbox2 /* 2131296507 */:
                Log.d(TAG, "onClick checkbox2");
                this.during2 = this.endTimeEditText2.getText().toString();
                if (this.during2.trim().equals("")) {
                    this.during2 = "1.5";
                }
                try {
                    if (this.t2 <= 20) {
                        Toast.makeText(this, getString(R.string.less_than_twinty_toast), 1).show();
                        this.mCheckbox2.setChecked(false);
                    }
                } catch (NumberFormatException e2) {
                    e2.printStackTrace();
                    return;
                }
                break;
            case R.id.checkbox3 /* 2131296508 */:
                Log.d(TAG, "onClick checkbox3");
                this.during3 = this.endTimeEditText3.getText().toString();
                if (this.during3.trim().equals("")) {
                    this.during3 = "1.5";
                }
                try {
                    if (this.t3 <= 20) {
                        Toast.makeText(this, getString(R.string.less_than_twinty_toast), 1).show();
                        this.mCheckbox3.setChecked(false);
                    }
                } catch (NumberFormatException e3) {
                    e3.printStackTrace();
                    return;
                }
                break;
            case R.id.time1_begin_btn /* 2131297513 */:
                Log.d(TAG, "onClick time1_begin_btn");
                this.mY = this.yearEditText1.getText().toString();
                this.mM = this.monthEditText1.getText().toString();
                this.mD = this.dayEditText1.getText().toString();
                this.mH = this.hourEditText1.getText().toString();
                this.mMin = this.minuteEditText1.getText().toString();
                this.during1 = this.endTimeEditText1.getText().toString();
                if (this.mY.trim().equals("")) {
                    this.mY = "2018";
                }
                if (this.mM.trim().equals("")) {
                    this.mM = "1";
                }
                if (this.mD.trim().equals("")) {
                    this.mD = "1";
                }
                if (this.mH.trim().equals("")) {
                    this.mH = "1";
                }
                if (this.mMin.trim().equals("")) {
                    this.mMin = "0";
                }
                if (this.during1.trim().equals("")) {
                    this.during1 = "1.5";
                }
                if (checkNumberFormatOk(this.mY, this.mM, this.mD, this.mH, this.mMin, this.during1)) {
                    this.mYear = Integer.parseInt(this.mY);
                    this.mMonth = Integer.parseInt(this.mM) - 1;
                    this.mDay = Integer.parseInt(this.mD);
                    this.mHour = Integer.parseInt(this.mH);
                    this.mMinute = Integer.parseInt(this.mMin);
                    this.c1.set(this.mYear, this.mMonth, this.mDay, this.mHour, this.mMinute, 0);
                    this.curDate = this.mY + ":" + this.mM + ":" + this.mD + ":" + this.mH + ":" + this.mMin;
                    SimpleDateFormat sDateFormat1 = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
                    this.startDateStr = sDateFormat1.format(new Date());
                    if (TimeCompare(this.curDate, this.startDateStr)) {
                        this.mYEnd = this.yearEditTextEnd1.getText().toString();
                        this.mMEnd = this.monthEditTextEnd1.getText().toString();
                        this.mDEnd = this.dayEditTextEnd1.getText().toString();
                        this.mHEnd = this.hourEditTextEnd1.getText().toString();
                        this.mMinEnd = this.minuteEditTextEnd1.getText().toString();
                        if (this.mYEnd.trim().equals("")) {
                            this.mYEnd = "2018";
                        }
                        if (this.mMEnd.trim().equals("")) {
                            this.mMEnd = "1";
                        }
                        if (this.mDEnd.trim().equals("")) {
                            this.mDEnd = "1";
                        }
                        if (this.mHEnd.trim().equals("")) {
                            this.mHEnd = "1";
                        }
                        if (this.mMinEnd.trim().equals("")) {
                            this.mMinEnd = "0";
                        }
                        if (checkNumberFormatOk(this.mYEnd, this.mMEnd, this.mDEnd, this.mHEnd, this.mMinEnd, this.during1)) {
                            this.mYearEnd = Integer.parseInt(this.mYEnd);
                            this.mMonthEnd = Integer.parseInt(this.mMEnd) - 1;
                            this.mDayEnd = Integer.parseInt(this.mDEnd);
                            this.mHourEnd = Integer.parseInt(this.mHEnd);
                            this.mMinuteEnd = Integer.parseInt(this.mMinEnd);
                            this.c1_end.set(this.mYearEnd, this.mMonthEnd, this.mDayEnd, this.mHourEnd, this.mMinuteEnd, 10);
                            this.curDateEnd = this.mYEnd + ":" + this.mMEnd + ":" + this.mDEnd + ":" + this.mHEnd + ":" + this.mMinEnd;
                            if (TimeCompare(this.curDateEnd, this.curDate)) {
                                this.t1 = DurationTimeMinutes(this.curDateEnd, this.curDate);
                                createAlarm(this.c1, String.valueOf(this.t1), "time1");
                                createAlarm(this.c1_end, String.valueOf(this.t1), "time1_end");
                                this.editor.putBoolean("time1_stop", false);
                                this.editor.commit();
                            } else {
                                createDialog(getString(R.string.end_time_error));
                            }
                            break;
                        }
                    } else {
                        createDialog(getString(R.string.start_time_error));
                        break;
                    }
                }
                break;
            case R.id.time1_result_btn /* 2131297524 */:
                Log.d(TAG, "onClick time1_result_btn");
                if (!timerStop[0]) {
                    canStopDialog(getString(R.string.notice_testing_show), "time1");
                } else {
                    showTestResult("time1");
                }
                break;
            case R.id.time2_begin_btn /* 2131297527 */:
                Log.d(TAG, "onClick time2_begin_btn");
                this.mY = this.yearEditText2.getText().toString();
                this.mM = this.monthEditText2.getText().toString();
                this.mD = this.dayEditText2.getText().toString();
                this.mH = this.hourEditText2.getText().toString();
                this.mMin = this.minuteEditText2.getText().toString();
                this.during2 = this.endTimeEditText2.getText().toString();
                if (this.mY.trim().equals("")) {
                    this.mY = "2018";
                }
                if (this.mM.trim().equals("")) {
                    this.mM = "1";
                }
                if (this.mD.trim().equals("")) {
                    this.mD = "1";
                }
                if (this.mH.trim().equals("")) {
                    this.mH = "1";
                }
                if (this.mMin.trim().equals("")) {
                    this.mMin = "0";
                }
                if (this.during2.trim().equals("")) {
                    this.during2 = "1.5";
                }
                if (checkNumberFormatOk(this.mY, this.mM, this.mD, this.mH, this.mMin, this.during2)) {
                    this.mYear = Integer.parseInt(this.mY);
                    this.mMonth = Integer.parseInt(this.mM) - 1;
                    this.mDay = Integer.parseInt(this.mD);
                    this.mHour = Integer.parseInt(this.mH);
                    this.mMinute = Integer.parseInt(this.mMin);
                    this.c2.set(this.mYear, this.mMonth, this.mDay, this.mHour, this.mMinute, 0);
                    this.curDate = this.mY + ":" + this.mM + ":" + this.mD + ":" + this.mH + ":" + this.mMin;
                    SimpleDateFormat sDateFormat2 = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
                    this.startDateStr = sDateFormat2.format(new Date());
                    if (TimeCompare(this.curDate, this.startDateStr)) {
                        this.mYEnd = this.yearEditTextEnd2.getText().toString();
                        this.mMEnd = this.monthEditTextEnd2.getText().toString();
                        this.mDEnd = this.dayEditTextEnd2.getText().toString();
                        this.mHEnd = this.hourEditTextEnd2.getText().toString();
                        this.mMinEnd = this.minuteEditTextEnd2.getText().toString();
                        if (this.mYEnd.trim().equals("")) {
                            this.mYEnd = "2018";
                        }
                        if (this.mMEnd.trim().equals("")) {
                            this.mMEnd = "1";
                        }
                        if (this.mDEnd.trim().equals("")) {
                            this.mDEnd = "1";
                        }
                        if (this.mHEnd.trim().equals("")) {
                            this.mHEnd = "1";
                        }
                        if (this.mMinEnd.trim().equals("")) {
                            this.mMinEnd = "0";
                        }
                        if (checkNumberFormatOk(this.mYEnd, this.mMEnd, this.mDEnd, this.mHEnd, this.mMinEnd, this.during2)) {
                            this.mYearEnd = Integer.parseInt(this.mYEnd);
                            this.mMonthEnd = Integer.parseInt(this.mMEnd) - 1;
                            this.mDayEnd = Integer.parseInt(this.mDEnd);
                            this.mHourEnd = Integer.parseInt(this.mHEnd);
                            this.mMinuteEnd = Integer.parseInt(this.mMinEnd);
                            this.c2_end.set(this.mYearEnd, this.mMonthEnd, this.mDayEnd, this.mHourEnd, this.mMinuteEnd, 10);
                            this.curDateEnd = this.mYEnd + ":" + this.mMEnd + ":" + this.mDEnd + ":" + this.mHEnd + ":" + this.mMinEnd;
                            if (TimeCompare(this.curDateEnd, this.curDate)) {
                                this.t2 = DurationTimeMinutes(this.curDateEnd, this.curDate);
                                createAlarm(this.c2, String.valueOf(this.t2), "time2");
                                createAlarm(this.c2_end, String.valueOf(this.t2), "time2_end");
                                this.editor.putBoolean("time2_stop", false);
                                this.editor.commit();
                            } else {
                                createDialog(getString(R.string.end_time_error));
                            }
                            break;
                        }
                    } else {
                        createDialog(getString(R.string.start_time_error));
                        break;
                    }
                }
                break;
            case R.id.time2_result_btn /* 2131297537 */:
                Log.d(TAG, "onClick time2_result_btn");
                if (!timerStop[1]) {
                    canStopDialog(getString(R.string.notice_testing_show), "time2");
                } else {
                    showTestResult("time2");
                }
                break;
            case R.id.time3_begin_btn /* 2131297540 */:
                Log.d(TAG, "onClick time3_begin_btn");
                this.mY = this.yearEditText3.getText().toString();
                this.mM = this.monthEditText3.getText().toString();
                this.mD = this.dayEditText3.getText().toString();
                this.mH = this.hourEditText3.getText().toString();
                this.mMin = this.minuteEditText3.getText().toString();
                this.during3 = this.endTimeEditText3.getText().toString();
                if (this.mY.trim().equals("")) {
                    this.mY = "2018";
                }
                if (this.mM.trim().equals("")) {
                    this.mM = "1";
                }
                if (this.mD.trim().equals("")) {
                    this.mD = "1";
                }
                if (this.mH.trim().equals("")) {
                    this.mH = "1";
                }
                if (this.mMin.trim().equals("")) {
                    this.mMin = "0";
                }
                if (this.during3.trim().equals("")) {
                    this.during3 = "1.5";
                }
                if (checkNumberFormatOk(this.mY, this.mM, this.mD, this.mH, this.mMin, this.during3)) {
                    this.mYear = Integer.parseInt(this.mY);
                    this.mMonth = Integer.parseInt(this.mM) - 1;
                    this.mDay = Integer.parseInt(this.mD);
                    this.mHour = Integer.parseInt(this.mH);
                    this.mMinute = Integer.parseInt(this.mMin);
                    this.c3.set(this.mYear, this.mMonth, this.mDay, this.mHour, this.mMinute, 0);
                    this.curDate = this.mY + ":" + this.mM + ":" + this.mD + ":" + this.mH + ":" + this.mMin;
                    SimpleDateFormat sDateFormat3 = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
                    this.startDateStr = sDateFormat3.format(new Date());
                    if (TimeCompare(this.curDate, this.startDateStr)) {
                        this.mYEnd = this.yearEditTextEnd3.getText().toString();
                        this.mMEnd = this.monthEditTextEnd3.getText().toString();
                        this.mDEnd = this.dayEditTextEnd3.getText().toString();
                        this.mHEnd = this.hourEditTextEnd3.getText().toString();
                        this.mMinEnd = this.minuteEditTextEnd3.getText().toString();
                        if (this.mYEnd.trim().equals("")) {
                            this.mYEnd = "2018";
                        }
                        if (this.mMEnd.trim().equals("")) {
                            this.mMEnd = "1";
                        }
                        if (this.mDEnd.trim().equals("")) {
                            this.mDEnd = "1";
                        }
                        if (this.mHEnd.trim().equals("")) {
                            this.mHEnd = "1";
                        }
                        if (this.mMinEnd.trim().equals("")) {
                            this.mMinEnd = "0";
                        }
                        if (checkNumberFormatOk(this.mYEnd, this.mMEnd, this.mDEnd, this.mHEnd, this.mMinEnd, this.during3)) {
                            this.mYearEnd = Integer.parseInt(this.mYEnd);
                            this.mMonthEnd = Integer.parseInt(this.mMEnd) - 1;
                            this.mDayEnd = Integer.parseInt(this.mDEnd);
                            this.mHourEnd = Integer.parseInt(this.mHEnd);
                            this.mMinuteEnd = Integer.parseInt(this.mMinEnd);
                            this.c3_end.set(this.mYearEnd, this.mMonthEnd, this.mDayEnd, this.mHourEnd, this.mMinuteEnd, 10);
                            this.curDateEnd = this.mYEnd + ":" + this.mMEnd + ":" + this.mDEnd + ":" + this.mHEnd + ":" + this.mMinEnd;
                            if (TimeCompare(this.curDateEnd, this.curDate)) {
                                this.t3 = DurationTimeMinutes(this.curDateEnd, this.curDate);
                                createAlarm(this.c3, String.valueOf(this.t3), "time3");
                                createAlarm(this.c3_end, String.valueOf(this.t3), "time3_end");
                                this.editor.putBoolean("time3_stop", false);
                                this.editor.commit();
                            } else {
                                createDialog(getString(R.string.end_time_error));
                            }
                            break;
                        }
                    } else {
                        createDialog(getString(R.string.start_time_error));
                        break;
                    }
                }
                break;
            case R.id.time3_result_btn /* 2131297550 */:
                Log.d(TAG, "onClick time3_result_btn");
                if (!timerStop[2]) {
                    canStopDialog(getString(R.string.notice_testing_show), "time3");
                } else {
                    showTestResult("time3");
                }
                break;
        }
    }

    private void createAlarm(Calendar calendar, String dur, String timePeriod) {
        Log.d(TAG, "createAlarm timePeriod: " + timePeriod + " dur: " + dur);
        boolean isCCTestCmdExist = EMFileUtils.isFileDirExits(CC_TEST_CMD_PATH);
        this.intent = new Intent(this, (Class<?>) CoulometerPowerService.class);
        this.intent.putExtra("duration", dur);
        this.intent.putExtra("time", timePeriod);
        try {
            if (timePeriod.equals("time1")) {
                this.pi1 = PendingIntent.getService(this, 1, this.intent, 134217728);
                this.am.setExactAndAllowWhileIdle(0, calendar.getTimeInMillis(), this.pi1);
                this.timeBeginBtn1.setEnabled(false);
                this.textStatus1.setText(getString(R.string.coulometer_testing));
                timerStop[0] = false;
                if (!isCCTestCmdExist) {
                    mCCValue[0] = this.hwApi.coulometerPowerApi().getCcResultNewKernel();
                    Log.d(TAG, "time1 startEnergy : " + mCCValue[0]);
                }
            } else if (timePeriod.equals("time1_end")) {
                this.pi1_end = PendingIntent.getService(this, 4, this.intent, 134217728);
                this.am.setExactAndAllowWhileIdle(0, calendar.getTimeInMillis(), this.pi1_end);
            } else if (timePeriod.equals("time2")) {
                this.pi2 = PendingIntent.getService(this, 2, this.intent, 134217728);
                this.am.setExactAndAllowWhileIdle(0, calendar.getTimeInMillis(), this.pi2);
                this.timeBeginBtn2.setEnabled(false);
                this.textStatus2.setText(getString(R.string.coulometer_testing));
                timerStop[1] = false;
                if (!isCCTestCmdExist) {
                    mCCValue[1] = this.hwApi.coulometerPowerApi().getCcResultNewKernel();
                    Log.d(TAG, "time2 startEnergy : " + mCCValue[1]);
                }
            } else if (timePeriod.equals("time2_end")) {
                this.pi2_end = PendingIntent.getService(this, 5, this.intent, 134217728);
                this.am.setExactAndAllowWhileIdle(0, calendar.getTimeInMillis(), this.pi2_end);
            } else if (timePeriod.equals("time3")) {
                this.pi3 = PendingIntent.getService(this, 3, this.intent, 134217728);
                this.am.setExactAndAllowWhileIdle(0, calendar.getTimeInMillis(), this.pi3);
                this.timeBeginBtn3.setEnabled(false);
                this.textStatus3.setText(getString(R.string.coulometer_testing));
                timerStop[2] = false;
                if (!isCCTestCmdExist) {
                    mCCValue[2] = this.hwApi.coulometerPowerApi().getCcResultNewKernel();
                    Log.d(TAG, "time3 startEnergy : " + mCCValue[2]);
                }
            } else if (timePeriod.equals("time3_end")) {
                this.pi3_end = PendingIntent.getService(this, 6, this.intent, 134217728);
                this.am.setExactAndAllowWhileIdle(0, calendar.getTimeInMillis(), this.pi3_end);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int DurationTimeMinutes(String curTime, String startTime) {
        SimpleDateFormat CurrentTime = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
        try {
            long endTime = CurrentTime.parse(curTime).getTime();
            long beginTime = CurrentTime.parse(startTime).getTime();
            int mDuration = (int) (((endTime - beginTime) / 1000) / 60);
            Log.d(TAG, "DurationTimeMinutes mDuration " + mDuration);
            return mDuration;
        } catch (Exception e) {
            Log.d(TAG, "DurationTimeMinutes Exception " + e);
            return 0;
        }
    }

    private boolean TimeCompare(String curTime, String startTime) {
        SimpleDateFormat CurrentTime = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
        try {
            Date beginTime = CurrentTime.parse(curTime);
            Date endTime = CurrentTime.parse(startTime);
            if (endTime.getTime() < beginTime.getTime()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            Log.d(TAG, "TimeCompare Exception " + e);
            return true;
        }
    }

    private void cancelAlarm(String timePeriod) {
        PendingIntent pendingIntent;
        PendingIntent pendingIntent2;
        PendingIntent pendingIntent3;
        if (timePeriod.equals("time1") && (pendingIntent3 = this.pi1) != null) {
            this.am.cancel(pendingIntent3);
            this.am.cancel(this.pi1_end);
        } else if (timePeriod.equals("time2") && (pendingIntent2 = this.pi2) != null) {
            this.am.cancel(pendingIntent2);
            this.am.cancel(this.pi2_end);
        } else if (timePeriod.equals("time3") && (pendingIntent = this.pi3) != null) {
            this.am.cancel(pendingIntent);
            this.am.cancel(this.pi3_end);
        }
    }

    private boolean checkNumberFormatOk(String y, String m, String d, String h, String min, String during) {
        if (y.length() != 4) {
            createDialog(getString(R.string.year_format_err));
            return false;
        }
        if (m.length() > 2) {
            createDialog(getString(R.string.month_format_err));
            return false;
        }
        if (d.length() > 2) {
            createDialog(getString(R.string.day_format_err));
            return false;
        }
        if (h.length() > 2) {
            createDialog(getString(R.string.hour_format_err));
            return false;
        }
        if (min.length() > 2) {
            createDialog(getString(R.string.minute_format_err));
            return false;
        }
        if (during.length() == 0) {
            createDialog(getString(R.string.during_empty_err));
            return false;
        }
        return true;
    }

    private void createDialog(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(getString(R.string.misc_dialog_warn)).setMessage(message).setCancelable(false).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.hardware.CoulometerPowerTestActivity.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create();
        alertDialog.show();
    }

    public void stopTest(String whichTime) {
        if (whichTime.equals("time1")) {
            Log.d(TAG, "stopTest time1");
            timerStop[0] = true;
            cancelAlarm("time1");
            this.timeBeginBtn1.setEnabled(true);
            this.textStatus1.setText(getString(R.string.coulometer_not_test));
            return;
        }
        if (whichTime.equals("time2")) {
            Log.d(TAG, "stopTest time2");
            timerStop[1] = true;
            cancelAlarm("time2");
            this.timeBeginBtn2.setEnabled(true);
            this.textStatus2.setText(getString(R.string.coulometer_not_test));
            return;
        }
        if (whichTime.equals("time3")) {
            Log.d(TAG, "stopTest time3");
            timerStop[2] = true;
            cancelAlarm("time3");
            this.timeBeginBtn3.setEnabled(true);
            this.textStatus3.setText(getString(R.string.coulometer_not_test));
        }
    }

    public void showTestResult(String whichTime) {
        if (whichTime.equals("time1")) {
            if (testResults1.size() < 1) {
                createDialog(getString(R.string.notice_data_empty));
                return;
            }
            Intent mIntent1 = new Intent(this, (Class<?>) CoulometerPowerResultActivity.class);
            mIntent1.putExtra("time", "time1");
            if (this.mCheckbox1.isChecked()) {
                mIntent1.putExtra("showChart", "yes");
            } else {
                mIntent1.putExtra("showChart", "no");
            }
            startActivity(mIntent1);
            return;
        }
        if (whichTime.equals("time2")) {
            if (testResults2.size() < 1) {
                createDialog(getString(R.string.notice_data_empty));
                return;
            }
            Intent mIntent2 = new Intent(this, (Class<?>) CoulometerPowerResultActivity.class);
            mIntent2.putExtra("time", "time2");
            if (this.mCheckbox2.isChecked()) {
                mIntent2.putExtra("showChart", "yes");
            } else {
                mIntent2.putExtra("showChart", "no");
            }
            startActivity(mIntent2);
            return;
        }
        if (whichTime.equals("time3")) {
            if (testResults3.size() < 1) {
                createDialog(getString(R.string.notice_data_empty));
                return;
            }
            Intent mIntent3 = new Intent(this, (Class<?>) CoulometerPowerResultActivity.class);
            mIntent3.putExtra("time", "time3");
            if (this.mCheckbox3.isChecked()) {
                mIntent3.putExtra("showChart", "yes");
            } else {
                mIntent3.putExtra("showChart", "no");
            }
            startActivity(mIntent3);
        }
    }

    private void canStopDialog(String message, final String item) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(getString(R.string.misc_dialog_warn)).setMessage(message).setCancelable(false).setPositiveButton(getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.hardware.CoulometerPowerTestActivity.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                CoulometerPowerTestActivity.this.showTestResult(item);
            }
        }).setNegativeButton(R.string.alertdialog_cancel, new DialogInterface.OnClickListener() { // from class: com.sprd.engineermode.hardware.CoulometerPowerTestActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
            }
        }).create();
        alertDialog.show();
    }
}
