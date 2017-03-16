package com.gaogao.highchart.chart.bean.pie;

import java.util.ArrayList;

/**
 * @name highChart
 * @class name：com.gaogao.highchart.chart.bean.pie
 * @class describe
 * @anthor xujianbo E-mail: xuarbo@qq.com
 * @time 2017/2/12 17:26
 * @change
 * @chang time
 * @class describe
 */
public class Legend {
    private ArrayList<Integer> colors;
    private ArrayList<String> lables;

    public Legend(ArrayList<Integer> colors, ArrayList<String> lables) {
        this.colors = colors;
        this.lables = lables;
    }

    public ArrayList<Integer> getColors() {
        return colors;
    }

    public void setColors(ArrayList<Integer> colors) {
        this.colors = colors;
    }

    public ArrayList<String> getLables() {
        return lables;
    }

    public void setLables(ArrayList<String> lables) {
        this.lables = lables;
    }
}
