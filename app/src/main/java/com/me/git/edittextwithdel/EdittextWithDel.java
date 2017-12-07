package com.me.git.edittextwithdel;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

/**
 * Created by Administrator on 2017/12/7 0007.
 */

public class EdittextWithDel extends EditText implements View.OnFocusChangeListener, TextWatcher {

    private Drawable mClearDrawable;

    public EdittextWithDel(Context context) {
        this(context,null);
    }

    public EdittextWithDel(Context context, AttributeSet attrs) {
        //这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs,android.R.attr.editTextStyle);
    }

    public EdittextWithDel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null){
            mClearDrawable = getResources().getDrawable(R.drawable.btn_delete);
        }
        mClearDrawable.setBounds(0,0,mClearDrawable.getIntrinsicWidth(),mClearDrawable.getIntrinsicHeight());

        //设置清除按钮是否显示
        setDelBtnVisible(false);

        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mClearDrawable != null && event.getAction() == MotionEvent.ACTION_UP){
            int x = (int) event.getX();
            //判断触摸点是否在水平范围内
            //getTotalPaddingRight():clean的图标左边缘至控件右边缘的距离
            boolean isInnerWidth = (x > (getWidth()-getTotalPaddingRight()))&&(x < (getWidth() - getPaddingRight()));
            //获取删除图标的边界，返回一个Rect对象
            Rect rect = mClearDrawable.getBounds();
            //获取删除图标的高度
            int height = rect.height();
            int y = (int) event.getY();
            //计算图标底部到控件底部的距离
            int distance = (getHeight() - height) / 2;
            //判断触摸点是否在竖直范围内(可能会有点误差)
            //触摸点的纵坐标在distance到（distance+图标自身的高度）之内，则视为点中删除图标
            boolean isInnerHeigh = (y > distance)&&(y < height + distance);
            if (isInnerHeigh && isInnerWidth){
                setText("");
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     */
    private void setDelBtnVisible(boolean b) {
        Drawable right = b?mClearDrawable:null;
        setCompoundDrawables(getCompoundDrawables()[0],getCompoundDrawables()[1],right,getCompoundDrawables()[3]);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
            setDelBtnVisible(getText().length() > 0);
        }else {
            setDelBtnVisible(hasFocus);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (hasFocus()){
            setDelBtnVisible(s.length() > 0);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 设置晃动动画
     */
    public void setShakeAnimation(){
        startAnimation(shakeAnimation(5));
    }

    /**
     * 晃动动画
     *
     * @param count 1秒钟晃动多少下
     * @return
     */
    private Animation shakeAnimation(int count) {
        TranslateAnimation animation = new TranslateAnimation(0,10,0,0);
        animation.setInterpolator(new CycleInterpolator(count));
        animation.setDuration(1000);
        return animation;
    }
}
