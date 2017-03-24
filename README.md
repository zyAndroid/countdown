# countdown
一个简单的计时器

![image](https://github.com/bigrainbig/countdown/blob/master/app/src/main/res/mipmap-xhdpi/demopic.png)

### 提供了一些方法
```java

  设置margin
  public void setLinearLayoutMargin(int left, int top, int right, int bottom)
  public void setTextViewMargin(int left, int top, int right, int bottom)
  设置Padding
  public void setLinearLayoutPadding(int left, int top, int right, int bottom)
  public void setTextViewPadding(int left, int top, int right, int bottom)
  设置背景
  public void setLinearLayoutDrawable(Drawable d)
  public void setTextViewDrawable(Drawable d)
  设置背景色
  public void setLinearLayoutBgColor(int color)
  public void setTextViewBgColor(int color)
  
  ```
### xml中的一些方法
```java

  countdown:color_bg  //设置背景色
  countdown:color_text  //设置字体颜色
  countdown:text_size  //设置字体大小
  countdown:setDrawable  //设置父容器背景
  countdown:setDrawable_sub  //设置子控件背景
  
```
### 怎么使用
```java
  
  xml中
  
  <com.zy.lib.countdown.CountDownView 
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>
        
  Java中
  csv.setStopTime();//设置一个long类型的停止时间
  
```
