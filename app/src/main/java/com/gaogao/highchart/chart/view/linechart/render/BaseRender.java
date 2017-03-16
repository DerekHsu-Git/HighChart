package com.gaogao.highchart.chart.view.linechart.render;

import android.graphics.Canvas;

import com.gaogao.highchart.chart.view.linechart.data.Line;

import java.util.ArrayList;
import java.util.List;

/**
 * @name highChart
 * @class name：com.gaogao.highchart.chart.view.linechart.render
 * @class describe
 * @anthor xujianbo E-mail: xuarbo@qq.com
 * @time 2017/3/5 22:56
 * @change
 * @chang time
 * @class describe
 */

public abstract class BaseRender implements Render {

    protected List<Integer> startX = new ArrayList<>();
    protected List<Integer> startY= new ArrayList<>();
    protected List<Integer> stopX= new ArrayList<>();
    protected List<Integer> stopY= new ArrayList<>();
    protected List<Integer> left= new ArrayList<>();

//    protected List<ChartBean> chartBeanList;

    protected List<Line> lineList;

//    protected int size;
    protected int spitWidth;

    protected int maxValue;

    public void setRect(List<Integer> sx,List<Integer> sy,List<Integer>ex,List<Integer> ey,List<Integer> left){
        this.startX = sx;
        this.startY = sy;
        this.stopX = ex;
        this.stopY = ey;
        this.left = left;
    }

//    public void setRect(int startX, int startY, int stopX, int stopY, int left) {
//        this.startX.add(0,startX);
//        this.startY.add(0,startY);
//        this.stopX.add(0,stopX);
//        this.stopY.add(0,stopY);
//        this.left.add(0,left);
//
//    }

//    public void setDataBeanList(List<ChartBean> chartBeanList) {
//
//        this.chartBeanList = chartBeanList;
//
//        if (chartBeanList == null || chartBeanList.size() == 0) {
//            return;
//        }
//        size = chartBeanList.size();
//
//        startX += left;
//        if(size==1){
//            spitWidth = (stopX - left - startX);
//
//        }else {
//            spitWidth = (stopX - left - startX) / (size - 1);
//        }
//
//        calculateScale();
//    }

    public void setDataBeanList(List<Line> lines) {

        this.lineList = lines;

        if (lineList == null || lineList.size() == 0) {
            return;
        }
//        size = chartBeanList.size();
        for (int i = 0; i < lineList.size(); i++) {

            startX.set(i,left.get(i));
            if(lines.get(i).getValues().size()==1){
                spitWidth = (stopX.get(i) - left.get(i) - startX.get(i));
            }else {
                spitWidth = (stopX.get(i) - left.get(i) - startX.get(i)) / (lines.get(i).getValues().size() - 1);
            }
            calculateScale();
        }
    }


    protected void calculateScale() {


    }

    @Override
    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }


//	@Override
//	public void setLevel(int minLevel, int maxLevel) {
//
//	}

    @Override
    public void draw(Canvas canvas) {

        drawLineChart(canvas);

        drawBarChart(canvas);

    }

    public void drawLineChart(Canvas canvas) {
    }

    public void drawBarChart(Canvas canvas) {
    }

}
