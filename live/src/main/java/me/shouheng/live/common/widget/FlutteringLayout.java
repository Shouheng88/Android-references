package me.shouheng.live.common.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

import me.shouheng.live.R;

/**
 * Created by wangshouheng on 2017/7/30.
 */
public class FlutteringLayout extends RelativeLayout {

    /**
     * 桃心
     */
    private int[] heartRes = new int[]{
            R.drawable.resource_heart0,R.drawable.resource_heart1,R.drawable.resource_heart2,
            R.drawable.resource_heart3,R.drawable.resource_heart3,R.drawable.resource_heart5,
            R.drawable.resource_heart6,R.drawable.resource_heart7,R.drawable.resource_heart8,
            R.drawable.resource_heart9,R.drawable.resource_heart10
    };

    /**
     * 插补器
     */
    private Interpolator[] interpolators = new Interpolator[]{
            new LinearInterpolator(),new AccelerateInterpolator(),
            new DecelerateInterpolator(),new AccelerateDecelerateInterpolator(),
            new BounceInterpolator(),new OvershootInterpolator()
    };

    private int mWidth,mHeight;

    private Random mRandom;

    /**
     * 进入动画持续时间
     */
    private int mEnterDuration = 300;
    /**
     * 动画持续时间
     */
    private int mDuration = 3000;
    /**
     * 桃心的缩放比例
     */
    private float mScale = 1.0f;

    private LayoutParams mParams;

    /**
     * 是否是相同大小（如果是则只计算一次）
     */
    private boolean mIsSameSize = true;

    private PointF mStartPointF;

    public FlutteringLayout(@NonNull Context context) {
        this(context,null);
    }

    public FlutteringLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlutteringLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){

        mRandom = new Random();

        mStartPointF = new PointF();

        mParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        mParams.addRule(ALIGN_PARENT_BOTTOM,TRUE);
        mParams.addRule(CENTER_HORIZONTAL,TRUE);

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.FlutteringLayout);
        mEnterDuration = a.getInt(R.styleable.FlutteringLayout_enter_duration,mEnterDuration);
        mDuration = a.getInt(R.styleable.FlutteringLayout_duration,mDuration);
        mScale = a.getFloat(R.styleable.FlutteringLayout_scale,mScale);
        mIsSameSize = a.getBoolean(R.styleable.FlutteringLayout_same_size,mIsSameSize);

        a.recycle();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量后的宽高
        mWidth  = getMeasuredWidth();
        mHeight = getMeasuredHeight();

    }

    /**
     * 添加桃心
     */
    public void addHeart() {

        ImageView iv = getHeartView(randomHeartResource());
        addView(iv);
        updateStartPointF(iv);

        Animator animator = getAnimator(iv);
        animator.addListener(new EndAnimatorListener(iv));
        animator.start();

    }

    /**
     * 获取一个桃心
     * @param resId
     * @return
     */
    private ImageView getHeartView(@DrawableRes int resId){
        ImageView iv = new ImageView(getContext());
        iv.setLayoutParams(mParams);
        iv.setImageResource(resId);
        return iv;
    }


    /**
     * 飘心入场动画
     * @param target
     * @return
     */
    private AnimatorSet getEnterAnimator(View target){

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target,View.SCALE_X,0.1f,mScale);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target,View.SCALE_Y,0.1f,mScale);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target,View.ALPHA,0.1f,1.0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX,scaleY,alpha);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.setDuration(mEnterDuration);

        return animatorSet;
    }


    /**
     * 贝塞尔曲线动画
     * @param target
     * @return
     */
    private ValueAnimator getBezierCurveAnimator(final View target){

        //贝塞尔曲线中间的两个点
        final PointF pointf1 = randomPointF(3.0f);
        final PointF pointf2 = randomPointF(1.5f);

        // 传入  起点 和 终点
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new TypeEvaluator<PointF>(){
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {

                //三次方贝塞尔曲线 逻辑 通过四个点确定一条三次方贝塞尔曲线
                float timeLeft = 1.0f - fraction;
                PointF pointF = new PointF();
                pointF.x = (float) (startValue.x * Math.pow(timeLeft,3) + 3 * pointf1.x  * fraction * Math.pow(timeLeft,2)
                        + 3 * pointf2.x * Math.pow(fraction,2) * timeLeft + endValue.x * Math.pow(fraction,3));

                pointF.y = (float) (startValue.y * Math.pow(timeLeft,3) + 3 * pointf1.y * fraction * Math.pow(timeLeft,2)
                        + 3 * pointf2.y * Math.pow(fraction,2) * timeLeft + endValue.y * Math.pow(fraction,3));
                return pointF;
            }
            //起点和终点
        }, mStartPointF , new PointF(mRandom.nextInt(mWidth),0));

        valueAnimator.setInterpolator(randomInterpolator());

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                //更新target的坐标
                target.setX(pointF.x);
                target.setY(pointF.y);
                //透明度 从不透明到完全透明
                target.setAlpha(1.0f - animation.getAnimatedFraction() * animation.getAnimatedFraction());
            }
        });
        valueAnimator.setDuration(mDuration);

        return valueAnimator;
    }


    private Animator getAnimator(View target){

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(getEnterAnimator(target),getBezierCurveAnimator(target));
        return animatorSet;

    }

    /**
     * 测量
     * @param target
     */
    private void makeMeasureSpec(View target){
        int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        target.measure(spec,spec);
    }

    /**
     * 起点
     * @param target
     * @return
     */
    private void updateStartPointF(View target){

        if(mStartPointF.x == 0 || mStartPointF.y == 0 || !mIsSameSize){
            makeMeasureSpec(target);
            int width = target.getMeasuredWidth();
            int height = target.getMeasuredHeight();
            mStartPointF.x = (mWidth + getPaddingLeft() - getPaddingRight() - width)/2;
            mStartPointF.y = mHeight + getPaddingTop() - getPaddingBottom() - height;

        }
    }


    /**
     * 随机贝塞尔曲线中间的点
     * @param scale
     * @return
     */
    private PointF randomPointF(float scale){
        PointF pointF = new PointF();
        pointF.x = mRandom.nextInt(mWidth);
        pointF.y = mRandom.nextInt(mHeight)/scale;

        return pointF;
    }

    /**
     * 随机一个插补器
     * @return
     */
    private Interpolator randomInterpolator(){
        return interpolators[mRandom.nextInt(interpolators.length)];
    }

    /**
     * 随机一个桃心
     * @return
     */
    private int randomHeartResource(){
        return heartRes[mRandom.nextInt(heartRes.length)];
    }

    public int[] getHeartRes() {
        return heartRes;
    }

    public void setHeartRes(int[] heartRes) {
        this.heartRes = heartRes;
    }

    public Interpolator[] getInterpolators() {
        return interpolators;
    }

    public void setInterpolators(Interpolator[] interpolators) {
        this.interpolators = interpolators;
    }

    public int getEnterDuration() {
        return mEnterDuration;
    }

    public void setEnterDuration(int enterDuration) {
        this.mEnterDuration = enterDuration;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public float getScale() {
        return mScale;
    }

    public void setScale(float scale) {
        this.mScale = scale;
    }

    public void setHeartLayoutParams(LayoutParams params){
        this.mParams = params;
    }

    public LayoutParams getHeartLayoutParams(){
        return mParams;
    }

    public boolean isSameSize() {
        return mIsSameSize;
    }

    public void setSameSize(boolean isSameSize) {
        this.mIsSameSize = isSameSize;
    }

    private class EndAnimatorListener extends AnimatorListenerAdapter {

        private View target;

        public EndAnimatorListener(View target){
            this.target = target;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            //动画结束 移除target
            removeView(target);
        }
    }
}

