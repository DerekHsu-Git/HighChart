package com.gaogao.highchart.chart.bean.pie;

import java.util.ArrayList;

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
public class PieDataSet {
//    private String centerText;
    private ArrayList<PieEntry> pieEntries;
    private ArrayList<Integer> colorList;

    private int valueTextColor;
    private int valueTextSize;
    private boolean drawValue;



    public PieDataSet(ArrayList<PieEntry> pieEntries,ArrayList<Integer> colorList) {
        this.colorList = colorList;
        this.pieEntries = pieEntries;
    }



    public ArrayList<PieEntry> getPieEntries() {
        return pieEntries;
    }

    public void setPieEntries(ArrayList<PieEntry> pieEntries) {
        this.pieEntries = pieEntries;
    }

    public int getValueTextColor() {
        return valueTextColor;
    }

    public void setValueTextColor(int valueTextColor) {
        this.valueTextColor = valueTextColor;
    }

    public int getValueTextSize() {
        return valueTextSize;
    }

    public void setValueTextSize(int valueTextSize) {
        this.valueTextSize = valueTextSize;
    }

    public boolean isDrawValue() {
        return drawValue;
    }

    public void setDrawValue(boolean drawValue) {
        this.drawValue = drawValue;
    }

    public ArrayList<Integer> getColorList() {
        return colorList;
    }

    public void setColorList(ArrayList<Integer> colorList) {
        this.colorList = colorList;
    }

}
