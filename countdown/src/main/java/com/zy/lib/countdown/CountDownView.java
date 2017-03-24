package com.zy.lib.countdown;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
    private int text_size = 18;
    private long time_long;
    private int hh = 0, mm = 0, ss = 0;
    private Drawable setDrawable;
    private Drawable setDrawable_sub;
    private TextView tv_h, tv_s, tv_m, tv_colon, tv_colon2;
    private LinearLayout ll;
    private TextView[] textViews2;
    private TextView[] textViews;

    public CountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initAttributes(attrs);
        CreatView();
    }


    private void initAttributes(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs,
                R.styleable.countdown);
        color_bg = typedArray.getString(R.styleable.countdown_color_bg);
        text_size = typedArray.getInteger(R.styleable.countdown_text_size, text_size);
        color_text = typedArray.getString(R.styleable.countdown_color_text);
        setDrawable = typedArray.getDrawable(R.styleable.countdown_setDrawable);
        setDrawable_sub = typedArray.getDrawable(R.styleable.countdown_setDrawable_sub);
        if (color_bg != null)
            setColor_bg(color_bg);
        if (color_text != null)
            setTextColor(color_text);
        if (text_size != 0)
            setTextSize(text_size);
        if (setDrawable != null)
            setDrawable(setDrawable);
        if (setDrawable_sub != null)
            setDrawable_sub(setDrawable_sub);
        typedArray.recycle();
    }

    public void setStopTime(long time_long) {
        this.time_long = time_long;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getNetTime getNetTime = new getNetTime();
        getNetTime.execute(time_long);
    }

    private void setColor_bg(String color_bg) {
        this.color_bg = color_bg;
    }

    private void setDrawable(Drawable drawable) {
        this.setDrawable = drawable;
    }

    private void setDrawable_sub(Drawable drawable_sub) {
        this.setDrawable_sub = drawable_sub;
    }

    private void setTextSize(int size) {
        this.text_size = size;
    }

    private void setTextColor(String color) {
        this.color_text = color;
    }

    public void setLinearLayoutMargin(int left, int top, int right, int bottom) {
        LayoutParams lp = (LayoutParams) ll.getLayoutParams();
        lp.setMargins(left, top, right, bottom);
        ll.setLayoutParams(lp);
        invalidate();
    }

    public void setLinearLayoutPadding(int left, int top, int right, int bottom) {
        ll.setPadding(left, top, right, bottom);
        invalidate();
    }

    public void setTextViewMargin(int left, int top, int right, int bottom) {
        for (int i = 0; i < textViews2.length; i++) {
            LayoutParams lp = (LayoutParams) textViews2[i].getLayoutParams();
            lp.setMargins(left, top, right, bottom);
            textViews2[i].setLayoutParams(lp);
        }
        invalidate();
    }

    public void setTextViewPadding(int left, int top, int right, int bottom) {
        for (int i = 0; i < textViews2.length; i++) {
            textViews2[i].setPadding(left, top, right, bottom);
        }
        invalidate();
    }

    public void setLinearLayoutDrawable(Drawable d) {
        ll.setBackground(d);
    }

    public void setTextViewDrawable(Drawable d) {
        for (int i = 0; i < textViews2.length; i++) {
            textViews2[i].setBackground(d);
        }
    }

    public void setLinearLayoutBgColor(int color) {
        ll.setBackgroundColor(color);
    }

    public void setTextViewBgColor(int color) {
        for (int i = 0; i < textViews2.length; i++) {
            textViews2[i].setBackgroundColor(getResources().getColor(color));
        }
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


    public void CreatView() {
        ll = new LinearLayout(mContext);
        LayoutParams lp = (LayoutParams) ll.getLayoutParams();
        tv_h = new TextView(mContext);
        tv_m = new TextView(mContext);
        tv_s = new TextView(mContext);
        tv_colon = new TextView(mContext);
        tv_colon2 = new TextView(mContext);
        tv_colon.setText(":");
        tv_colon2.setText(":");
        textViews2 = new TextView[]{tv_s, tv_m, tv_h};
        textViews = new TextView[]{tv_s, tv_m, tv_h, tv_colon, tv_colon2};
        for (int i = 0; i < textViews.length; i++) {
            if (!TextUtils.isEmpty(color_text))
                textViews[i].setTextColor(Color.parseColor(color_text));
            if (text_size != 0)
                textViews[i].setTextSize(text_size);
        }
        for (int i = 0; i < textViews2.length; i++) {
            if (setDrawable_sub != null)
                textViews[i].setBackground(setDrawable_sub);
        }
        if (setDrawable != null)
            ll.setBackground(setDrawable);
        if (!TextUtils.isEmpty(color_bg))
            ll.setBackgroundColor(Color.parseColor(color_bg));
        ll.addView(tv_h);
        ll.addView(tv_colon);
        ll.addView(tv_m);
        ll.addView(tv_colon2);
        ll.addView(tv_s);
        addView(ll);
    }
}
