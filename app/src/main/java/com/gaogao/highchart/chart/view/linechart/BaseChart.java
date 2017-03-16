package com.gaogao.highchart.chart.view.linechart;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.gaogao.highchart.chart.view.linechart.axis.XAxis;
import com.gaogao.highchart.chart.view.linechart.data.Line;
import com.gaogao.highchart.chart.view.linechart.render.Render;

import java.util.List;

/**
 * @name highChart
 * @class name：com.gaogao.highchart.chart.view.linechart
 * @class describe
 * @anthor xujianbo E-mail: xuarbo@qq.com
 * @time 2017/3/5 23:08
 * @change
 * @chang time
 * @class describe
 */

public abstract class BaseChart extends BaseView {


    protected XAxis xAxis;
    protected int maxYAxisValue = 100;
    protected int maxLevel = 90;
    protected int minLevel = 40;
//    protected List<ChartBean> chartBeanList;
    protected Render render;

    /**
     * add
     * */

    protected List<Line> lineList;

    public BaseChart(Context context) {
        super(context);
    }

    public BaseChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //********************************************************************************************//


    public void setMaxYAxisValue(int maxYAxisValue) {
        this.maxYAxisValue = maxYAxisValue;
    }

    public void setLevel(int minLevel, int maxLevel) {
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }

//    public void setBarChartData(List<ChartBean> chartData) {
//        int max = 0;
//        for (ChartBean chartbean:chartData) {
//            int newValue = chartbean.getValue();
//            if(newValue>max){
//                max = newValue;
//            }
//        }
//        this.maxYAxisValue = max+50;
////        this.chartBeanList = chartData;
//    }

    public void setBarChartData(List<Line> lineList){
        this.lineList = lineList;
    }

    protected void setRender(Render render) {

        this.render = render;
        this.render.setMaxValue(maxYAxisValue);
        this.render.setLevel(minLevel, maxLevel);
    }

    //********************************************************************************************//

    @Override
    protected void init() {

    }


    @Override
    protected void drawCustomView(Canvas canvas) {

        if (xAxis != null) {
            xAxis.draw(canvas);
        }
        if (render != null) {
            render.draw(canvas);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            selectPoint(event.getX());
        }
        return true;
    }


    protected void selectPoint(float x) {

    }


}
