package com.zy.countdown;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zy.lib.countdown.CountDownView;

/**
 * Created by BigRain on 2017/3/24.
 */
public class DemoActivity extends AppCompatActivity {
    private CountDownView csv, csv1, csv2, csv3, csv4, csv5, csv6, csv7, csv8, csv9, csv10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        csv = (CountDownView) findViewById(R.id.csv);
        csv2 = (CountDownView) findViewById(R.id.csv2);
        csv3 = (CountDownView) findViewById(R.id.csv3);
        csv4 = (CountDownView) findViewById(R.id.csv4);
        csv5 = (CountDownView) findViewById(R.id.csv5);
        csv6 = (CountDownView) findViewById(R.id.csv6);
        csv7 = (CountDownView) findViewById(R.id.csv7);
        csv8 = (CountDownView) findViewById(R.id.csv8);
        csv9 = (CountDownView) findViewById(R.id.csv9);
        csv10 = (CountDownView) findViewById(R.id.csv10);
        csv.setStopTime(Long.valueOf("14903387320000"));
        csv2.setStopTime(Long.valueOf("14903387320000"));
        csv3.setStopTime(Long.valueOf("14903387320000"));
        csv4.setStopTime(Long.valueOf("14903387320000"));
        csv5.setStopTime(Long.valueOf("14903387320000"));
        csv6.setStopTime(Long.valueOf("14903387320000"));
        csv7.setStopTime(Long.valueOf("14903387320000"));
        csv8.setStopTime(Long.valueOf("14903387320000"));
        csv9.setStopTime(Long.valueOf("14903387320000"));
        csv10.setStopTime(Long.valueOf("14903387320000"));

    }
}
