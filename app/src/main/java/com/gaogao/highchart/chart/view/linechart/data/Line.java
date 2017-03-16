package com.gaogao.highchart.chart.view.linechart.data;

import android.graphics.Color;

import java.util.List;

/**
 * @name highChart
 * @class name：com.gaogao.highchart.chart.view.linechart.data
 * @class describe
 * @anthor xujianbo E-mail: xuarbo@qq.com
 * @time 2017/3/6 22:09
 * @change
 * @chang time
 * @class describe
 */
public class Line extends ChartData{

    private int lineColor = Color.GRAY; //折线颜色
    private int lineWidth = 1; // 折线的宽度dp
    private boolean hasPoints = true; //是否画圆点

    private boolean hasLines = true; // 是否画线条

    private int pointColor = Color.GRAY;//圆点颜色

    private int pointRadius = 1;//圆点半径dp

    private boolean isCubic; //是否是曲线

    private boolean isFill; // 是否填充

    private int fillColr = 0; // 填充色

    public Line(List<ChartBean> values){
        this.values = values;
    }

    public int getLineColor() {
        return lineColor;
    }

    public Line setLineColor(int lineColor) {
        this.lineColor = lineColor;
        return this;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public Line setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
        return this;
    }

    public boolean isHasPoints() {
        return hasPoints;
    }

    public Line setHasPoints(boolean hasPoints) {
        this.hasPoints = hasPoints;
        return this;
    }

    public boolean isHasLines() {
        return hasLines;
    }

    public Line setHasLines(boolean hasLines) {
        this.hasLines = hasLines;
        return this;
    }

    public int getPointColor() {
        return pointColor;
    }

    public Line setPointColor(int pointColor) {
        this.pointColor = pointColor;
        return this;
    }

    public int getPointRadius() {
        return pointRadius;
    }

    public Line setPointRadius(int pointRadius) {
        this.pointRadius = pointRadius;
        return this;
    }

    public boolean isCubic() {
        return isCubic;
    }

    public Line setCubic(boolean cubic) {
        isCubic = cubic;
        return this;
    }

    public boolean isFill() {
        return isFill;
    }

    public Line setFill(boolean fill) {
        isFill = fill;
        return this;
    }

    public int getFillColr() {
        return fillColr;
    }

    public Line setFillColr(int fillColr) {
        this.fillColr = fillColr;
        return this;
    }
}
