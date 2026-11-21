package com.sprd.engineermode.hardware;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import com.sprd.engineermode.R;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class CoulometerPowerResultActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "CoulometerPowerResultActivity";
    private Button clearBtn;
    private LineChartView lineChartView;
    private String time;
    private WindowManager wm;
    private String[] yLabels = {"", "100", "200", "300", "400", "500"};

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        NullPointerException e;
        ArrayIndexOutOfBoundsException e2;
        super.onCreate(bundle);
        setContentView(R.layout.coulometer_power_chart);
        this.wm = (WindowManager) getSystemService("window");
        this.clearBtn = (Button) findViewById(R.id.clear_btn);
        this.clearBtn.setOnClickListener(this);
        int width = this.wm.getDefaultDisplay().getWidth();
        int height = this.wm.getDefaultDisplay().getHeight();
        this.lineChartView = (LineChartView) findViewById(R.id.chart_line);
        Object string = getString(R.string.cc_test_chart);
        Object string2 = getString(R.string.cc_test_chart);
        this.time = getIntent().getStringExtra("time");
        String stringExtra = getIntent().getStringExtra("showChart");
        try {
            try {
                try {
                    if (this.time.equals("time1")) {
                        String str = CoulometerPowerTestActivity.mCCValue[0];
                        String str2 = CoulometerPowerTestActivity.mCCValueVol[0];
                        LineChartView lineChartView = this.lineChartView;
                        ArrayList<String> arrayList = CoulometerPowerTestActivity.testTimes1;
                        lineChartView.SetInfo(arrayList, this.yLabels, CoulometerPowerTestActivity.testResults1, str, str2, width, height, stringExtra);
                        string2 = lineChartView;
                        string = arrayList;
                    } else if (this.time.equals("time2")) {
                        String str3 = CoulometerPowerTestActivity.mCCValue[1];
                        String str4 = CoulometerPowerTestActivity.mCCValueVol[1];
                        LineChartView lineChartView2 = this.lineChartView;
                        ArrayList<String> arrayList2 = CoulometerPowerTestActivity.testTimes2;
                        lineChartView2.SetInfo(arrayList2, this.yLabels, CoulometerPowerTestActivity.testResults2, str3, str4, width, height, stringExtra);
                        string2 = lineChartView2;
                        string = arrayList2;
                    } else {
                        if (this.time.equals("time3")) {
                            String str5 = CoulometerPowerTestActivity.mCCValue[2];
                            String str6 = CoulometerPowerTestActivity.mCCValueVol[2];
                            LineChartView lineChartView3 = this.lineChartView;
                            ArrayList<String> arrayList3 = CoulometerPowerTestActivity.testTimes3;
                            lineChartView3.SetInfo(arrayList3, this.yLabels, CoulometerPowerTestActivity.testResults3, str5, str6, width, height, stringExtra);
                            string2 = lineChartView3;
                            string = arrayList3;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e3) {
                    e2 = e3;
                    Log.d(TAG, "ArrayIndexOutOfBoundsException" + e2);
                } catch (NullPointerException e4) {
                    e = e4;
                    Log.d(TAG, "NullPointerException" + e);
                }
            } catch (ArrayIndexOutOfBoundsException e5) {
                e2 = e5;
                Log.d(TAG, "ArrayIndexOutOfBoundsException" + e2);
            } catch (NullPointerException e6) {
                e = e6;
                Log.d(TAG, "NullPointerException" + e);
            }
        } catch (ArrayIndexOutOfBoundsException e7) {
            e2 = e7;
        } catch (NullPointerException e8) {
            e = e8;
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        if (v.getId() != R.id.clear_btn) {
            return;
        }
        Log.d(TAG, "onClick clear_btn");
        try {
            if (this.time.equals("time1")) {
                this.lineChartView.SetInfo(CoulometerPowerTestActivity.testTimes1, this.yLabels, CoulometerPowerTestActivity.testResults1, "", "", 0, 0, "no");
                CoulometerPowerTestActivity.testTimes1.clear();
                CoulometerPowerTestActivity.testResults1.clear();
            } else if (this.time.equals("time2")) {
                this.lineChartView.SetInfo(CoulometerPowerTestActivity.testTimes2, this.yLabels, CoulometerPowerTestActivity.testResults2, "", "", 0, 0, "no");
                CoulometerPowerTestActivity.testTimes2.clear();
                CoulometerPowerTestActivity.testResults2.clear();
            } else {
                this.lineChartView.SetInfo(CoulometerPowerTestActivity.testTimes3, this.yLabels, CoulometerPowerTestActivity.testResults3, "", "", 0, 0, "no");
                CoulometerPowerTestActivity.testTimes3.clear();
                CoulometerPowerTestActivity.testResults3.clear();
            }
        } catch (NullPointerException e) {
            Log.d(TAG, "onClick NullPointerException" + e);
        } catch (ArrayIndexOutOfBoundsException e2) {
            Log.d(TAG, "onClick ArrayIndexOutOfBoundsException" + e2);
        } finally {
            finish();
        }
    }
}
