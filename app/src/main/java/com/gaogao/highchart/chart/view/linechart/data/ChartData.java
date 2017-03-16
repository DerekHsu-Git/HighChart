package com.gaogao.highchart.chart.view.linechart.data;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * @name highChart
 * @class name：com.gaogao.highchart.chart.view.linechart.data
 * @class describe
 * @anthor xujianbo E-mail: xuarbo@qq.com
 * @time 2017/3/6 22:10
 * @change
 * @chang time
 * @class describe  ：不同类型图表相同属性
 */

public class ChartData {

    protected List<ChartBean> values = new ArrayList<>();

    protected boolean hasLabels = false;// 是否画标签

    protected int labelColor = Color.DKGRAY;//标签背景色

    protected float labelRadius = 3; //dp

    public List<ChartBean> getValues() {
        return values;
    }

    public ChartData setValues(List<ChartBean> values) {
        this.values = values;
        return this;
    }

    public boolean isHasLabels() {
        return hasLabels;
    }

    public ChartData setHasLabels(boolean hasLabels) {
        this.hasLabels = hasLabels;
        return this;
    }

    public int getLabelColor() {
        return labelColor;
    }

    public ChartData setLabelColor(int labelColor) {
        this.labelColor = labelColor;
        return this;
    }

    public float getLabelRadius() {
        return labelRadius;
    }

    public ChartData setLabelRadius(float labelRadius) {
        this.labelRadius = labelRadius;
        return this;
    }
}


