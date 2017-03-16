package com.gaogao.highchart.chart.bean.bar;

/**
 * @name highChart
 * @class name：com.gaogao.highchart.chart.bean.bar
 * @class describe
 * @anthor xujianbo E-mail: xuarbo@qq.com
 * @time 2017/2/22 21:39
 * @change
 * @chang time
 * @class describe
 */
public class PointBean {
    private float x;
    private float y;


    public PointBean(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
