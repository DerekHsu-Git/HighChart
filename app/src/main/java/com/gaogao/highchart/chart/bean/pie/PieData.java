package com.gaogao.highchart.chart.bean.pie;

/**
 * @name highChart
 * @class name：com.gaogao.highchart.chart.bean.pie
 * @class describe
 * @anthor xujianbo E-mail: xuarbo@qq.com
 * @time 2017/2/12 16:48
 * @change
 * @chang time
 * @class describe
 */
public class PieData {
    private PieDataSet pieDataSet;
    private int totolCount = 100;

    public PieData(PieDataSet pieDataSet) {
        this.pieDataSet = pieDataSet;
    }

    public PieData(PieDataSet pieDataSet, int totolCount) {
        this.pieDataSet = pieDataSet;
        this.totolCount = totolCount;
    }

    public PieDataSet getPieDataSet() {
        return pieDataSet;
    }

    public void setPieDataSet(PieDataSet pieDataSet) {
        this.pieDataSet = pieDataSet;
    }
}
