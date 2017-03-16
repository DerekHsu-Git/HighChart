package com.gaogao.highchart.chart.view.linechart.axis;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.gaogao.highchart.chart.view.linechart.data.Line;

import java.util.List;

/**
 * @name highChart
 * @class name：com.gaogao.highchart.chart.view.linechart.axis
 * @class describe
 * @anthor xujianbo E-mail: xuarbo@qq.com
 * @time 2017/3/5 22:54
 * @change
 * @chang time
 * @class describe
 */
public abstract class BaseAxis implements Axis{
    protected int startX;
    protected int startY;
    protected int stopX;
    protected int stopY;


    protected Paint paint;
    protected int strokeWidth = 2;
    protected int color = Color.parseColor("#599de5");

    protected Paint axisText;
    protected int textSize = 18;
    protected int textColor = Color.parseColor("#599de5");
//    protected List<ChartBean> chartBeanList;

    protected List<Line> lineList;

//    protected int size = 0;
    protected int spitWidth;

    public BaseAxis() {

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(color);

        axisText = new Paint(Paint.ANTI_ALIAS_FLAG);
        axisText.setTextAlign(Paint.Align.CENTER);
        axisText.setTextSize(textSize);
        axisText.setColor(textColor);

    }


    public abstract void init();

//    public void setDataBean(List<ChartBean> beanList) {
//
//        chartBeanList = beanList;
//
//        if (chartBeanList == null || chartBeanList.size() == 0) {
//            return;
//        }
//        size = chartBeanList.size();
//        reSetSpit();
//
//    }

    public void setDataBean(List<Line> lines) {

        lineList = lines;

        if (lineList == null || lineList.size() == 0) {
            return;
        }
//        size = chartBeanList.size();
        reSetSpit();

    }

    protected abstract void reSetSpit();

    @Override
    public void draw(Canvas canvas) {

        if(lineList!=null && !lineList.isEmpty()){
            for (int i = 0; i < lineList.size(); i++) {
                drawText(canvas);
                drawLine(canvas);
            }
        }
//        if (chartBeanList != null && size != 0) {
//            drawText(canvas);
//        }

//        drawLine(canvas);
    }

    protected abstract void drawText(Canvas canvas);

    protected abstract void drawLine(Canvas canvas);
}
