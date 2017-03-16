package com.gaogao.highchart.chart.view.barchart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.gaogao.highchart.chart.bean.bar.BarChartData;
import com.gaogao.highchart.chart.bean.bar.PointBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @name highChart
 * @class name：com.gaogao.highchart.chart.view.barchart
 * @class describe
 * @anthor xujianbo E-mail: xuarbo@qq.com
 * @time 2017/2/22 21:26
 * @change
 * @chang time
 * @class describe
 */
public class BarChartView extends View {

    private int width, height;

    //间距
    private int padding = 20;
    //数据个数
    private int dataSize;
    //柱状的
    private float barWidth = 6;
    //文字大小
    private int textSize = 18;
    //y轴最大值
    private int yMaxValue = 1000;//10

    //画笔
    private Paint axisPaint;//坐标
    private Paint barPaint;//柱状
    private Paint textPaint;//文字

    //柱子的颜色
    private int barColor = Color.parseColor("#000000");

    //选中的柱子
    private int selectIndex = -1;
    //间距宽度
    private int split = 0;
    //动画比例
    private float scale = 0f;

    private List<BarChartData> chartDataBeanList = new ArrayList<>();
    private List<PointBean> pointValuesList = new ArrayList<>();


    public BarChartView(Context context) {
        this(context, null);
    }

    public BarChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化画笔
     */
    private void init() {
        axisPaint = new Paint();
        axisPaint.setAntiAlias(true);
        axisPaint.setStrokeCap(Paint.Cap.SQUARE);
        axisPaint.setColor(Color.BLACK);

        barPaint = new Paint();
        barPaint.setAntiAlias(true);
        barPaint.setStrokeCap(Paint.Cap.ROUND);//柱子顶部是圆的
        barPaint.setStrokeWidth(barWidth);
        barPaint.setColor(barColor);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.RIGHT);
        textPaint.setTextSize(textSize);
        textPaint.setColor(Color.BLACK);

    }

    public void setDataBeen(List<BarChartData> chartDataBeenList) {
        dataSize = 0;
        split = 0;
        pointValuesList.clear();
//        autoCountMaxY(chartDataBeenList);
        this.chartDataBeanList = chartDataBeenList;
    }

    public void startAnimation(int duration) {
        setAnimator(duration);
    }

    private void setAnimator(int duration) {
        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);

        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                scale = (float) valueAnimator.getAnimatedValue();

                invalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCoordinate(canvas);
        if (dataSize == 0) {
            dataSize = chartDataBeanList.size();
        }
        if (split == 0) {
            split = (width - 2 * padding) / (dataSize + 1);
        }
        barWidth = split * 0.3f;
        drawXText(canvas);
        drawBar(canvas);
    }

    private void drawBar(Canvas canvas) {
        barPaint.setStrokeWidth(barWidth);
        for (int i = 0; i < dataSize; i++) {
            PointBean pointBean = pointValuesList.get(i);
            int startX = (int) pointBean.getX();
            float y = pointBean.getY();
            float offsite = height - padding - y;
            y = offsite * scale;
            float startY = height - padding - (5 + barWidth / 2);
            canvas.drawLine(startX, startY, startX, startY - y, barPaint);
        }
    }

    private void drawXText(Canvas canvas) {
        int chartH = height - 2 * padding;
        for (int i = 0; i < dataSize; i++) {
            BarChartData barChartData = chartDataBeanList.get(i);
            int startX = padding + (i + 1) * split; //每个x值的横坐标
            //当前值所占view 高度比例
            float mScale = (float) barChartData.getyValue() / (float) (yMaxValue + (yMaxValue + 10));
            if (mScale > 1) {
                mScale = 1;
            }
            float endY = (1 - mScale) * chartH + padding; //柱高

            PointBean pointBean = new PointBean(startX, endY);

            pointValuesList.add(pointBean);

            canvas.drawText(barChartData.getxValueText(), startX, height - (textSize / 4), textPaint);
        }
    }

    private void drawCoordinate(Canvas canvas) {

        canvas.drawLine(padding, height - padding, width - padding, height - padding, axisPaint);
        canvas.drawLine(padding, height - padding, padding, padding, axisPaint);

    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public float getBarWidth() {
        return barWidth;
    }

    public void setBarWidth(float barWidth) {
        this.barWidth = barWidth;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public Paint getAxisPaint() {
        return axisPaint;
    }

    public void setAxisPaint(Paint axisPaint) {
        this.axisPaint = axisPaint;
    }

    public Paint getBarPaint() {
        return barPaint;
    }

    public void setBarPaint(Paint barPaint) {
        this.barPaint = barPaint;
    }

    public Paint getTextPaint() {
        return textPaint;
    }

    public void setTextPaint(Paint textPaint) {
        this.textPaint = textPaint;
    }

    public int getBarColor() {
        return barColor;
    }

    public void setBarColor(int barColor) {
        this.barColor = barColor;
    }

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }


}
