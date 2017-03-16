package com.gaogao.highchart.chart.view.linechart.render;

import android.graphics.Canvas;

/**
 * @name highChart
 * @class name：com.gaogao.highchart.chart.view.linechart.render
 * @class describe
 * @anthor xujianbo E-mail: xuarbo@qq.com
 * @time 2017/3/5 22:57
 * @change
 * @chang time
 * @class describe
 */
public interface Render {
    void draw(Canvas canvas);
    void setMaxValue(int maxValue);
    void setLevel(int minLevel, int maxLevel);
}
