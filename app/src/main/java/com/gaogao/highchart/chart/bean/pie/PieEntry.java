package com.gaogao.highchart.chart.bean.pie;

/**
 * @name highChart
 * @class name：com.gaogao.highchart.chart.bean.pie
 * @class describe
 * @anthor xujianbo E-mail: xuarbo@qq.com
 * @time 2017/2/12 16:43
 * @change
 * @chang time
 * @class describe
 */
public class PieEntry {
    private int value;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
