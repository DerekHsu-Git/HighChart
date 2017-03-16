package com.gaogao.highchart.chart.view.pie;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.gaogao.highchart.LogUtil;
import com.gaogao.highchart.R;
import com.gaogao.highchart.chart.bean.pie.Legend;
import com.gaogao.highchart.chart.bean.pie.PieData;
import com.gaogao.highchart.chart.bean.pie.PieEntry;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * @name highChart
 * @class name：com.gaogao.highchart.chart.view.pie
 * @class describe
 * @anthor xujianbo E-mail: xuarbo@qq.com
 * @time 2017/2/12 16:19
 * @change
 * @chang time
 * @class describe
 */
public class PieChart extends View {

    private Context context;
    private PieData pieData;
    //图例
    private Legend legend;

    private int holeColor;
    private int holeRadius;

    //色环半径
    private float radius;

    private int transParentCicleRadius;//半透明圈大小
    private int startRotateAngle = 0;//初始旋转角度，从正下方开始
    private boolean showAsPercentage;//显示百分比

    private String centerText;//中间的文字
    private int centerTextColor = Color.BLACK;
    private int centerTextSize = 20;

    //选中情况下，凸出的长度
    private int selectShift;

    private int mWidth;
    private int mHeight;

    private int mMin;
    private int mCenterX;
    private int mCenterY;

    private Paint mPaint;
    private Paint mTextPaint;
    private Paint mCenterTextPaint;
    private RectF mRectF;

    private float mCurrentMaxAngle;

    float mX = 0,mY = 0;

    private boolean isAnimation;

    /**
     * 数据文本的大小
     */
    private Rect dataTextBound = new Rect();
    /**
     * 中间文本的大小
     */
    private Rect centerTextBound = new Rect();

    private int sum;
    private Bitmap bitmap;
    private int tarIndex;

    private boolean pressed = false;

    public PieChart(Context context) {
        this(context, null);
    }

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PieChart);
        holeColor = typedArray.getColor(R.styleable.PieChart_holeColor, Color.TRANSPARENT);
        holeRadius = typedArray.getDimensionPixelSize(R.styleable.PieChart_holeRadius, dip2px(50));
        transParentCicleRadius = typedArray.getDimensionPixelSize(R.styleable.PieChart_transparentRadius, dip2px(10));
        centerTextColor = typedArray.getColor(R.styleable.PieChart_centerTextColor, Color.BLUE);
        centerTextSize = typedArray.getDimensionPixelSize(R.styleable.PieChart_centerTextSize, dip2px(10));
        typedArray.recycle();
        initPaint();
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
//        initPaint();
        radius = mMin / 4;
        initRecF(radius);
        drawEachArc(canvas);

    }

    /**
     * 初始化 画笔
     */
    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);//空心

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(25);
        mTextPaint.setColor(Color.WHITE);

        mCenterTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCenterTextPaint.setAntiAlias(true);
        mCenterTextPaint.setStyle(Paint.Style.FILL);
        mCenterTextPaint.setTextSize(centerTextSize);
        mCenterTextPaint.setColor(centerTextColor);
    }

    /**
     * 初始化外切矩形
     */
    private void initRecF(float value) {
        //设置半径为宽高最小值的1/4

        float l = mCenterX - value;
        float t = mCenterY - value;
        float r = mCenterX + value;
        float b = mCenterY + value;
        if(mRectF == null){
            mRectF = new RectF(l, t, r, b);
        }else {
            mRectF.set(l,t,r,b);
        }
    }


    private void drawEachArc(Canvas canvas) {
        canvas.save();
        mPaint.setStrokeWidth(mWidth / 2 - holeRadius);
        ArrayList<Integer> colors = pieData.getPieDataSet().getColorList();
        ArrayList<PieEntry> values = pieData.getPieDataSet().getPieEntries();

        //扇形开始度数
        int startAngle = startRotateAngle;
        //所占百分比
        float percent;
        //所占度数
        float angle;
        for (int i = 0; i < colors.size(); i++) {

            percent = values.get(i).getValue() / (float) sum;
            //获取百分比在360中所占度数
            if (i == colors.size() - 1) {//保证所有度数加起来等于360
                angle = mCurrentMaxAngle - startAngle;
            } else {
                angle = (float) Math.ceil(percent * mCurrentMaxAngle);
            }
            mPaint.setColor(colors.get(i));
            //绘制第i段扇形   0.8f是为了让每个扇形之间没有间隙
            float offset ;
            if(pressed && tarIndex == i){
                radius = mMin / 4;
                initRecF(radius+10);
                offset = 2.8f;
                canvas.drawArc(mRectF, startAngle + offset, angle - 2*offset, false, mPaint);
            }else{
                radius = mMin / 4;
                initRecF(radius);
                offset = 0.8f;
                canvas.drawArc(mRectF, startAngle - offset, angle + offset, false, mPaint);
            }
            startAngle += angle;

            if(!isAnimation){
                //当前弧线中心点相对于纵轴的夹角度数,由于扇形的绘制是从三点钟方向开始，所以加90
                float arcCenterDegree = 90 + startAngle - angle / 2;
                drawData(canvas, arcCenterDegree, i, percent);
            }
        }
        if(!isAnimation){
            //绘制中心文本
            canvas.drawText(centerText, mCenterX - centerTextBound.width() / 2, mCenterY + centerTextBound.height() / 2, mCenterTextPaint);
        }
        canvas.restore();
    }

    /**
     * 设置当前进度，带有动画
     * @param duration
     */
    public void setProgressWithDuration(PieData pieData, int duration) {
        this.pieData = pieData;

        ArrayList<PieEntry> values = pieData.getPieDataSet().getPieEntries();
        for (int i = 0; i < values.size(); i++) {
            sum += values.get(i).getValue();
        }
        //计算总和数字的宽高
        mCenterTextPaint.getTextBounds(centerText, 0, centerText.length(), centerTextBound);
        drawWithAnimation(duration);
    }

    private void drawWithAnimation(int duration) {
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,360);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setDuration((long) duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentMaxAngle = (float) animation.getAnimatedValue();
                isAnimation = mCurrentMaxAngle < 360f;
                invalidate();
            }
        });

        valueAnimator.start();
    }


    /**
     * 绘制数据
     *
     * @param canvas  画布
     * @param degree  第i段弧线中心点相对于纵轴的夹角度数
     * @param i       第i段弧线
     * @param percent 数据百分比
     */
    private void drawData(Canvas canvas, float degree, int i, float percent) {
        //弧度中心坐标
        float startX = calculatePosition(degree)[0];
        float startY = calculatePosition(degree)[1];
        String name = pieData.getPieDataSet().getPieEntries().get(i).getName();
        //获取名称文本大小
        mTextPaint.getTextBounds(name, 0, name.length(), dataTextBound);
        //绘制名称数据，20为纵坐标偏移量
        canvas.drawText(name,
                startX - dataTextBound.width() / 2,
                startY + dataTextBound.height() / 2 - 20,
                mTextPaint);

        //拼接百分比并获取文本大小
        DecimalFormat df = new DecimalFormat("0.0");
        String percentString = df.format(percent * 100) + "%";
        mTextPaint.getTextBounds(percentString, 0, percentString.length(), dataTextBound);

        //绘制百分比数据，20为纵坐标偏移量
        canvas.drawText(percentString,
                startX - dataTextBound.width() / 2,
                startY + dataTextBound.height() * 2 - 20,
                mTextPaint);
    }

    /**
     * 计算每段弧度的中心坐标
     *
     * @param degree 当前扇形中心度数
     */
    private float[] calculatePosition(float degree) {
        //由于Math.sin(double a)中参数a不是度数而是弧度，所以需要将度数转化为弧度
        //而Math.toRadians(degree)的作用就是将度数转化为弧度
        //sin 一二正，三四负 sin（180-a）=sin(a)
        //扇形弧线中心点距离圆心的x坐标
        float x = (float) (Math.sin(Math.toRadians(degree)) * radius);
        //cos 一四正，二三负
        //扇形弧线中心点距离圆心的y坐标
        float y = (float) (Math.cos(Math.toRadians(degree)) * radius);

        //每段弧度的中心坐标(扇形弧线中心点相对于view的坐标)
        float startX = mCenterX + x;
        float startY = mCenterY - y;

        float[] position = new float[2];
        position[0] = startX;
        position[1] = startY;
        return position;
    }

    //TODO 添加点击事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                 mX = event.getX();
                 mY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                 mX = event.getX();
                 mY = event.getY();
//                LogUtil.e("["+mX+","+mY+"]");
                break;
            case MotionEvent.ACTION_UP:
                setDrawingCacheEnabled(true);
                bitmap = getDrawingCache();
                if (bitmap == null) {
                    LogUtil.e( "bitmap == null");
                    break;
                }
                int pixel = bitmap.getPixel((int) mX, (int) mY);
                setDrawingCacheEnabled(false);
                //获取颜色RGB
                int redValue = Color.red(pixel);
                int blueValue = Color.blue(pixel);
                int greenValue = Color.green(pixel);
                tarIndex = 0;
                String curColorHex = toHex(redValue, greenValue, blueValue);

                for (int i = 0; i < pieData.getPieDataSet().getColorList().size(); i++) {
                    if(Color.parseColor(curColorHex) == pieData.getPieDataSet().getColorList().get(i)){
                        LogUtil.e("下方 点击了"+i);
                        pressed =! pressed;
                        tarIndex = i;
                        invalidate();
                        break;
                    }
                }
                break;
        }
        return true;
    }

    private String toHex(int redValue, int greenValue, int blueValue) {
        return "#"+toBrowserHextValue(redValue)+toBrowserHextValue(greenValue)+toBrowserHextValue(blueValue);
    }

    private String toBrowserHextValue(int value) {

        StringBuilder builder = new StringBuilder(Integer.toHexString(value & 0xff));
        while (builder.length()<2){
            builder.append("0");
        }
        return builder.toString().toLowerCase();
    }


    public void setPieData(PieData pieData) {
        this.pieData = pieData;
        mCurrentMaxAngle = 360f;
        ArrayList<PieEntry> values = pieData.getPieDataSet().getPieEntries();
        for (int i = 0; i < values.size(); i++) {
            sum += values.get(i).getValue();
        }
        //计算总和数字的宽高
        mCenterTextPaint.getTextBounds(centerText, 0, centerText.length(), centerTextBound);
        invalidate();
    }

    public void setHoleColor(int holeColor) {
        this.holeColor = holeColor;
    }

    public void setHoleRadius(int holeRadius) {
        this.holeRadius = holeRadius;
    }

    public void setTransParentCicleRadius(int transParentCicleRadius) {
        this.transParentCicleRadius = transParentCicleRadius;
    }

    public void setStartRotateAngle(int startRotateAngle) {
        this.startRotateAngle = startRotateAngle;
    }

    public void setCenterText(String centerText) {
        this.centerText = centerText;
    }

    public void setCenterTextColor(int centerTextColor) {
        this.centerTextColor = centerTextColor;
    }

    public void setCenterTextSize(int centerTextSize) {
        this.centerTextSize = centerTextSize;
    }

    public void setSelectShift(int selectShift) {
        this.selectShift = selectShift;
    }

    public int dip2px(float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
