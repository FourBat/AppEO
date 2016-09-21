package com.liu.easyoffice.MyView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.liu.easyoffice.R;

/**
 * Created by hui on 2016/9/17.
 * 广告轮播
 */
public class MyViewFlipper extends ViewFlipper implements GestureDetector.OnGestureListener {
    private Context context;
    private GestureDetector detector;
    private OnPagerChangeListener mPagerChangeListener;
    public MyViewFlipper(Context context) {
        super(context);
        this.context = context;
        detector = new GestureDetector(getContext(), this);
    }

    public void setOnPageChangeListener(OnPagerChangeListener onPageChangeListener) {
        mPagerChangeListener = onPageChangeListener;
    }

    @Override
    public int getDisplayedChild() {
        int index = super.getDisplayedChild();
        if (mPagerChangeListener != null) {
            mPagerChangeListener.onPageChanged(index);
        }
        return index;
    }

    public MyViewFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        detector = new GestureDetector(getContext(), this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return true;
    }

    @Override
    public void showPrevious() {
        super.showPrevious();
        getDisplayedChild();
    }

    @Override
    public void showNext() {
        super.showNext();
        getDisplayedChild();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        getParent().requestDisallowInterceptTouchEvent(true);//防止与父布局的切换冲突
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;

    }

    @Override
    public void onLongPress(MotionEvent e) {


    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        stopFlipping();
        if (e2.getX() - e1.getX() > 120) {//左进右出
            showPrevious();
            startFlipping();
            return true;
        } else if (e2.getX() - e1.getX() < -120) {//右进左出
            Animation rInAnim = AnimationUtils.loadAnimation(context, R.anim.ad_right_in);
            Animation lOutAnim = AnimationUtils.loadAnimation(context, R.anim.ad_left_out);
            setInAnimation(rInAnim);
            setOutAnimation(lOutAnim);
            showNext();
            startFlipping();
            Animation lInAnim = AnimationUtils.loadAnimation(context, R.anim.ad_left_in);
            Animation rOutAnim = AnimationUtils.loadAnimation(context, R.anim.ad_right_out);
            setInAnimation(lInAnim);
            setOutAnimation(rOutAnim);
            return true;
        }
        return true;
    }

    public interface OnPagerChangeListener {
        void onPageChanged(int index);
    }
}
