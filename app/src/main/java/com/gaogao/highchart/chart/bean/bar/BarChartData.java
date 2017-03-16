package com.gaogao.highchart.chart.bean.bar;

/**
 * @name highChart
 * @class name：com.gaogao.highchart.chart.bean.bar
 * @class describe
 * @anthor xujianbo E-mail: xuarbo@qq.com
 * @time 2017/2/22 21:24
 * @change
 * @chang time
 * @class describe
 */
public class BarChartData {
    private String xValueText;
    private int yValue;

    public int getyValue() {
        return yValue;
    }

    public void setyValue(int yValue) {
        this.yValue = yValue;
    }

    public String getxValueText() {
        return xValueText;
    }

    public void setxValueText(String xValueText) {
        this.xValueText = xValueText;
    }
}
