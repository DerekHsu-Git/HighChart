package com.gaogao.highchart.chart.view.linechart.axis;

import android.graphics.Canvas;

import com.gaogao.highchart.chart.view.linechart.data.ChartBean;
import com.gaogao.highchart.chart.view.linechart.data.Line;

/**
 * @name highChart
 * @class name：com.gaogao.highchart.chart.view.linechart.axis
 * @class describe
 * @anthor xujianbo E-mail: xuarbo@qq.com
 * @time 2017/3/5 22:55
 * @change
 * @chang time
 * @class describe
 */
public class XAxis extends BaseAxis {


    public XAxis(int statX, int startY, int stopX, int stopY) {
        super();
        this.startX = statX;
        this.startY = startY;
        this.stopX = stopX;
        this.stopY = stopY;
    }

    public void setLineColor(int color) {


    }

    public void setLineWidth(int lineWidth) {

        strokeWidth = lineWidth;
    }

    @Override
    protected void reSetSpit() {
//        if(size==1){
//            spitWidth = (stopX - (textSize * 2) - startX);
//        }else {
//            spitWidth = (stopX - (textSize * 2) - startX) / (size - 1);
//        }


    }

    @Override
    protected void drawText(Canvas canvas) {
        if (lineList!=null && !lineList.isEmpty()){
            for (Line line : lineList) {
                for (int i = 0; i < line.getValues().size(); i++) {
                    int size = line.getValues().size();
                    ChartBean chartBean = line.getValues().get(i);

                    if (chartBean.getxAxisText() == null){
                    }
                    boolean isDraw = chartBean.isShowText();
                    if (!isDraw) {
                        continue;
                    }
                    if(i==0){
                        canvas.drawText(chartBean.getxAxisText(), startX + 4*textSize + i * spitWidth, startY, axisText);
                    } else if(i==(size-1)){
                        canvas.drawText(chartBean.getxAxisText(), startX - 2*textSize + i * spitWidth, startY, axisText);
                    } else {
                        canvas.drawText(chartBean.getxAxisText(), startX + textSize + i * spitWidth, startY, axisText);
                    }

                }
            }
        }

    }

    @Override
    protected void drawLine(Canvas canvas) {
        canvas.drawLine(startX + textSize, startY - textSize - 5,
                stopX - textSize, startY - textSize - 5, paint);
    }

    @Override
    public void init() {

    }


    public int getStart() {
        return startX + textSize;
    }

    public int getTextSize() {
        return textSize;
    }

    public int getSplitWidth() {
        return spitWidth;
    }

    public int getHeight() {
        return textSize + 5;
    }

}
