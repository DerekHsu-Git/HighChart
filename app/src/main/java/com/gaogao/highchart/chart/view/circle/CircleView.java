package com.gaogao.highchart.chart.view.circle;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.gaogao.highchart.R;

/**
 * @name highChart
 * @class name：com.gaogao.highchart.chart.view.circle
 * @class describe
 * @anthor xujianbo E-mail: xuarbo@qq.com
 * @time 2017/2/12 11:59
 * @change
 * @chang time
 * @class describe
 */
public class CircleView extends View {

    private Context mContext;

    // View的宽高
    private int mWidth, mHeight;
    //圆心坐标
    private int mCenterX, mCenterY;
    private int mMin = 0;
    //圆环的外切矩形
    private RectF mRectF;
    //画笔
    private Paint mPaint;
    private Paint mIndicatorPaint;        //画光标

    //背景圆环的宽度
//    private int mStrokeWidth = 15;
    //前景圆环的宽度
    private int mProgressLineWidth = 16;
    private float mProgress = 0f;
    private float mMaxProgress = 100f;

    private int startAngle = -90;
    private int sweepAngle = 360;



    //圆弧的种类
    private int cicleType = 0;//带光标，实线  1-->不带光标，虚线
    //虚线情况下，环线的宽度、间隔
    private int dashWidth = 1,dashLineWidth = 3;

    //背景环色
    private int mBackLineColor;
    private int mForeLineColor;

    //环内文本
    private String mTitle;
    private String mCurrent;
    private String mTarget;

    //环内文本大小
    private int mTitleTextSize;
    private int mCurrentTextSize;
    private int mTargetTextSize;

    //环内文本颜色
    private int mTitleTextColor;        //标题字体颜色
    private int mCurrentTextColor;        //内容字体颜色
    private int mTargetTextColor;        //目标字体颜色
    private int indicatorShaderRadius = 10; // 阴影大小
    private int indicatorRadius = 5;


    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyCircleView);

        mBackLineColor = typedArray.getColor(R.styleable.MyCircleView_backgroundLineColor, Color.parseColor("#55000000"));
        mForeLineColor = typedArray.getColor(R.styleable.MyCircleView_foregroundLineColor, Color.BLUE);
        mTitleTextSize = typedArray.getDimensionPixelSize(R.styleable.MyCircleView_circleViewtitleTextSize, dip2px(15));
        mCurrentTextSize = typedArray.getDimensionPixelSize(R.styleable.MyCircleView_circleViewcurrentTextSize, dip2px(20));
        mTargetTextSize = typedArray.getDimensionPixelSize(R.styleable.MyCircleView_circleViewtargetTextSize, dip2px(10));
        mTitleTextColor = typedArray.getColor(R.styleable.MyCircleView_circleViewtitleTextColor, Color.parseColor("#000000"));
        mCurrentTextColor = typedArray.getColor(R.styleable.MyCircleView_circleViewcurrentTextColor, Color.parseColor("#000000"));
        mTargetTextColor = typedArray.getColor(R.styleable.MyCircleView_circleViewtargetTextColor, Color.parseColor("#000000"));

        typedArray.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取视图的宽高
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        //取最小值
        if (mWidth != mHeight) {
            mMin = Math.min(mWidth, mHeight);
        } else {
            mMin = mWidth;
        }
        mCenterX = mWidth / 2;
        mCenterY = mHeight / 2;
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint(canvas);
        initRecF();
        drawTwoCircle(canvas);
        if(cicleType==0){
            drawIndicate(canvas);
        }
        drawTitleText(canvas);
        drawCurrentText(canvas);
        drawTargetText(canvas);
    }

    private void drawIndicate(Canvas canvas) {
        canvas.save();
        float mRadius = mMin / 2 - (mProgressLineWidth + indicatorShaderRadius*2);
        float mAngle = (mProgress / mMaxProgress) * sweepAngle;

        // 计算圆弧上X,Y轴坐标
        float mIndicatorX = mRectF.centerX() + (float) (mRadius * Math.cos(Math.toRadians(mAngle + (sweepAngle-Math.abs(startAngle)))));
        float mIndicatorY = mRectF.centerY() + (float) (mRadius * Math.sin(Math.toRadians(mAngle + (sweepAngle-Math.abs(startAngle)))));
        mIndicatorPaint.setShader(new RadialGradient(mIndicatorX, mIndicatorY, dip2px(15),
                new int[]{Color.argb(180, 0, 0, 255), Color.argb(30, 0, 0, 255)}, null, Shader.TileMode.REPEAT));
        canvas.drawCircle(mIndicatorX, mIndicatorY, dip2px(indicatorShaderRadius), mIndicatorPaint);
        mIndicatorPaint.reset();
        // 设置相关属性
        mIndicatorPaint.setAntiAlias(true);
        mIndicatorPaint.setStyle(Paint.Style.FILL);
        mIndicatorPaint.setColor(mForeLineColor);
        canvas.drawCircle(mIndicatorX, mIndicatorY, dip2px(indicatorRadius), mIndicatorPaint);
        canvas.restore();
    }

    private void drawTargetText(Canvas canvas) {
        if (!TextUtils.isEmpty(mTarget)) {
            canvas.save();
            mPaint.setColor(mTargetTextColor);
            mPaint.setTextSize(mTargetTextSize);
            mPaint.setStyle(Paint.Style.FILL);
            int textHeight = 3 * mHeight / 4;//Y轴位于圆环1/4处
            int textWidth = (int) mPaint.measureText(mTarget, 0, mTarget.length());
            canvas.drawText(mTarget, mCenterX - (textWidth / 2), textHeight + mTargetTextSize / 2, mPaint);
            canvas.restore();
        }
    }

    private void drawCurrentText(Canvas canvas) {
        if (!TextUtils.isEmpty(mCurrent)) {
            canvas.save();
            mPaint.setColor(mCurrentTextColor);
            mPaint.setTextSize(mCurrentTextSize);
            mPaint.setStyle(Paint.Style.FILL);
            int textWidth = (int) mPaint.measureText(mCurrent, 0, mCurrent.length());
            canvas.drawText(mCurrent, mCenterX - (textWidth / 2), mCenterY + mCurrentTextSize / 2, mPaint);
            canvas.restore();
        }

    }

    private void drawTitleText(Canvas canvas) {
        if (!TextUtils.isEmpty(mTitle)) {
            canvas.save();
            mPaint.setColor(mTitleTextColor);
            mPaint.setTextSize(mTitleTextSize);
            mPaint.setStyle(Paint.Style.FILL);
            int textHeight = mHeight / 4;//Y轴位于圆环1/4处
            int textWidth = (int) mPaint.measureText(mTitle, 0, mTitle.length());
            canvas.drawText(mTitle, mCenterX - (textWidth / 2), textHeight, mPaint);
            canvas.restore();
        }
    }

    private void drawTwoCircle(Canvas canvas) {
        canvas.save();
        //background circle
        mPaint.setStrokeWidth(mProgressLineWidth);
        mPaint.setColor(mBackLineColor);
        canvas.drawArc(mRectF, startAngle, sweepAngle, false, mPaint);//第三个表示是否连接到圆心

        //foreground cicle
        mPaint.setStrokeWidth(mProgressLineWidth);
        mPaint.setColor(mForeLineColor);
        canvas.drawArc(mRectF, startAngle, (mProgress / mMaxProgress) * sweepAngle, false, mPaint);
        canvas.restore();
    }

    /**
     * 初始化外切矩形
     */
    private void initRecF() {
        int ref = mProgressLineWidth + indicatorShaderRadius*2;

        int l = mCenterX - mMin / 2 + ref;
        int t = mCenterY - mMin / 2 + ref;
        int r = mCenterX + mMin / 2 - ref;
        int b = mCenterY + mMin / 2 - ref;
        mRectF = new RectF(l, t, r, b);
    }

    /**
     * 初始化 画笔
     * @param canvas
     */
    private void initPaint(Canvas canvas) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        canvas.drawColor(Color.TRANSPARENT);
        mPaint.setStyle(Paint.Style.STROKE);//空心

        //关键点：通过DashPathEffect来绘制间隔
        if(cicleType==1){
            mPaint.setPathEffect(new DashPathEffect(new float[]{dip2px(dashWidth), dip2px(dashLineWidth)}, 0));
        }else{
            mIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mIndicatorPaint.setAntiAlias(true);
            mIndicatorPaint.setStyle(Paint.Style.FILL);
        }

    }

    /**
     * 设置当前进度
     *
     * @param mProgress
     */
    public void setProgress(float mProgress) {
        this.mProgress = mProgress;
        invalidate();
    }

    /**
     * 设置当前进度，带有动画
     *
     * @param progress
     * @param duration
     */
    public void setProgressWithDuration(float progress, int duration) {
        setProgressAnimator(progress, duration);
    }

    /**
     * 设置动画
     * @param progress
     * @param duration
     */
    private void setProgressAnimator(float progress, int duration) {
       // float mDuration = ((mMaxProgress / duration) * progress) * duration;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, progress);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setDuration((long) duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setProgress((float) animation.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }

    public void setMaxProgress(float mMaxProgress) {
        this.mMaxProgress = mMaxProgress;
    }

    public void setBackLineColor(int mBackLineColor) {
        this.mBackLineColor = mBackLineColor;
    }

    public void setForeLineColor(int mForeLineColor) {
        this.mForeLineColor = mForeLineColor;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setCurrent(String mCurrent) {
        this.mCurrent = mCurrent;
    }

    public void setTarget(String mTarget) {
        this.mTarget = mTarget;
    }

    public void setTitleTextSize(int mTitleTextSize) {
        this.mTitleTextSize = mTitleTextSize;
    }

    public void setCurrentTextSize(int mCurrentTextSize) {
        this.mCurrentTextSize = mCurrentTextSize;
    }

    public void setTargetTextSize(int mTargetTextSize) {
        this.mTargetTextSize = mTargetTextSize;
    }

    public void setTitleTextColor(int mTitleTextColor) {
        this.mTitleTextColor = mTitleTextColor;
    }

    public void setProgressLineWidth(int mProgressLineWidth) {
        this.mProgressLineWidth = mProgressLineWidth;
    }

    public void setCurrentTextColor(int mCurrentTextColor) {
        this.mCurrentTextColor = mCurrentTextColor;
    }

    public void setTargetTextColor(int mTargetTextColor) {
        this.mTargetTextColor = mTargetTextColor;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }

    public void setSweepAngle(int sweepAngle) {
        this.sweepAngle = sweepAngle;
    }

    public void setCicleType(int cicleType) {
        this.cicleType = cicleType;
    }


    public int dip2px(float dipValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


}
