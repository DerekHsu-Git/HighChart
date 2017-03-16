package com.gaogao.highchart.chart.view.linechart.data;

/**
 * @name highChart
 * @class name：com.gaogao.highchart.chart.view.linechart.data
 * @class describe
 * @anthor xujianbo E-mail: xuarbo@qq.com
 * @time 2017/3/5 22:53
 * @change
 * @chang time
 * @class describe
 */
public class ChartBean {

    private int color;
    private int value;
    private String xAxisText;

    private boolean isShowText;

    public ChartBean() {

    }

    public ChartBean(int color, int value, String xAxisText, boolean isShowText) {
        this.color = color;
        this.value = value;
        this.xAxisText = xAxisText;
        this.isShowText = isShowText;
    }


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getxAxisText() {
        return xAxisText;
    }

    public void setxAxisText(String xAxisText) {
        this.xAxisText = xAxisText;
    }

    public boolean isShowText() {
        return isShowText;
    }

    public void setShowText(boolean showText) {
        isShowText = showText;
    }


}

