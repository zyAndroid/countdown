package com.zy.lib.countdown;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by BigRain on 2017/3/23.
 */
public class CountDownView extends LinearLayout {
    private Context mContext;
    private String color_bg;
    private String color_text;
    private String border_color;
    private String border_sub_color;
    private int text_size = 14;
    private boolean showBorder = false;
    private boolean showBorder_sub = false;
    private long time_long;
    private int hh = 0, mm = 0, ss = 0;
    private TextView tv_h, tv_s, tv_m;
    private LinearLayout ll_linearlayout;

    public CountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        inntView(context);
        initAttributes(attrs);
    }

    public void inntView(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.w_countdown,
                null);
        tv_h = (TextView) v.findViewById(R.id.tv_h);
        tv_s = (TextView) v.findViewById(R.id.tv_s);
        tv_m = (TextView) v.findViewById(R.id.tv_m);
        ll_linearlayout = (LinearLayout) v.findViewById(R.id.ll_linearlayout);
        addView(v);
    }

    private void initAttributes(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs,
                R.styleable.countdown);
        color_bg = typedArray.getString(R.styleable.countdown_color_bg);
        border_color = typedArray.getString(R.styleable.countdown_border_color);
        border_sub_color = typedArray.getString(R.styleable.countdown_border_sub_color);
        text_size = typedArray.getInteger(R.styleable.countdown_text_size, text_size);
        color_text = typedArray.getString(R.styleable.countdown_color_text);
        showBorder = typedArray.getBoolean(R.styleable.countdown_showBorder, false);
        showBorder_sub = typedArray.getBoolean(R.styleable.countdown_showBorder_sub, false);
        setShowBorder(showBorder);
        setShowBorder_sub(showBorder_sub);
        setTextSize(text_size);
        typedArray.recycle();
    }

    public void setStopTime(long time_long) {
        getNetTime getNetTime = new getNetTime();
        getNetTime.execute(time_long);
    }

    public void setTextSize(int size) {
        tv_h.setTextSize(size);
        tv_m.setTextSize(size);
        tv_s.setTextSize(size);
    }

    public void setTextColor(String color) {
        tv_h.setTextColor(Color.parseColor(color));
        tv_m.setTextColor(Color.parseColor(color));
        tv_s.setTextColor(Color.parseColor(color));
    }

    public void setShowBorder_sub(boolean b) {
        if (b) {
            tv_h.setBackground(getResources().getDrawable(R.drawable.boder_yuan));
            tv_m.setBackground(getResources().getDrawable(R.drawable.boder_yuan));
            tv_s.setBackground(getResources().getDrawable(R.drawable.boder_yuan));
        }

    }

    public void setShowBorder(boolean b) {
        if (b)
            ll_linearlayout.setBackground(getResources().getDrawable(R.drawable.boder_yuan));
    }

    //这里开始到最后是倒计时。
    class getNetTime extends AsyncTask<Long, Void, Long> {

        long time_net = 0;

        @Override
        protected Long doInBackground(Long... longs) {
            URL url = null;//取得资源对象
            try {
                url = new URL("http://www.bjtime.cn");
                URLConnection uc = url.openConnection();
                uc.connect();
                time_net = uc.getDate();
                if (time_net < longs[0]) {

                    return longs[0] - time_net;
                } else {
                    return (long) 0;
                }
            } catch (Exception e) {
                Log.d("countdown", "Exception: " + e);
            }
            return (long) 0;
        }

        @Override
        protected void onPostExecute(Long lon) {
            super.onPostExecute(lon);
            if (lon != 0) {
                Date date = new Date(lon);
                hh = date.getHours();
                mm = date.getMinutes();
                ss = date.getSeconds();
                timer.schedule(task, 0, 1000);
            }

        }
    }

    private void clearTimer() {
        if (task != null) {
            task.cancel();
        }
        if (timer != null) {
            timer.cancel();
        }
    }

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            hander.sendEmptyMessage(0);
        }
    };
    Handler hander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (valueDown()) {
                tv_h.setText(forMatString(hh));
                tv_m.setText(forMatString(mm));
                tv_s.setText(forMatString(ss));

            } else {
                clearTimer();
            }
        }
    };

    public boolean valueDown() {
        if (ss != 0) {
            ss = ss - 1;
            return true;
        } else if (ss == 0 && mm != 0) {
            mm = mm - 1;
            ss = 59;
            return true;
        } else if (ss == 0 && mm == 0 && hh != 0) {
            hh = hh - 1;
            mm = 59;
            ss = 59;
            return true;
        } else {
            return false;
        }
    }

    public String forMatString(int i) {
        String s = "00";
        if (String.valueOf(i).length() == 1) {
            s = "0" + String.valueOf(i);
        } else {
            s = String.valueOf(i);
        }
        return s;
    }
}
