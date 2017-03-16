package com.gaogao.highchart.chart.view.linechart.render;

import android.graphics.Canvas;

import com.gaogao.highchart.LogUtil;
import com.gaogao.highchart.chart.bean.bar.PointBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @name highChart
 * @class name：com.gaogao.highchart.chart.view.linechart.render
 * @class describe
 * @anthor xujianbo E-mail: xuarbo@qq.com
 * @time 2017/3/5 23:01
 * @change
 * @chang time
 * @class describe
 */

public class RealLineRender extends LineChartRender {


    private List<Integer> values = new ArrayList<>();


    public RealLineRender() {
        super();
    }

    @Override
    protected void calculateScale() {
        spitWidth = (stopX.get(0) - startX.get(0) - 2 * left.get(0)) / (10 - 1);
        super.calculateScale();


    }

    @Override
    protected void calculateChartBean() {

        LogUtil.e("calculateChartBean ");
//		super.calculateChartBean();
    }

    @Override
    protected void drawLineAndPoint(Canvas canvas) {

        LogUtil.e("drawLineAndPoint");
        if (pointValuesList == null || pointValuesList.size() == 0) {
            return;
        }

        path.reset();

        float endX = 0;
        for (int i = 0; i < pointValuesList.size(); i++) {

            PointBean pointValue = pointValuesList.get(i);
            canvas.drawCircle(pointValue.getX(), pointValue.getY(), 3, yLinePaint);
            endX = pointValue.getX();
            if (i == 0) {
                path.moveTo(pointValue.getX(), startY.get(0));
                path.lineTo(pointValue.getX(), pointValue.getY());
            } else {
                path.lineTo(pointValue.getX(), pointValue.getY());
            }

        }
        path.lineTo(endX, startY.get(0));

        canvas.drawPath(path, lineShaderPaint);

    }

    //******************************************************************************************//

    public void setMaxSize(int size) {
//        this.size = size;
        calculateScale();
    }


    /**
     * 添加
     *
     * @param value
     */
    public void addValue(int value) {

        if (values == null) {
            values = new ArrayList<>();
        }

        if (values.size() == 10) {
            values.remove(0);
        }
        values.add(value);
        update();
    }


    private void update() {
        if (pointValuesList == null) {
            pointValuesList = new ArrayList<>();
        } else {
            pointValuesList.clear();
        }

        for (int i = 0; i < values.size(); i++) {
            int index = values.size() - 1 - i;
            float scale = (float) values.get(i) / (float) maxValue;
            if (scale > 1) {
                scale = 1;
            }

            float endY = (1 - scale) * startY.get(0);
            float x = stopX.get(0) - left.get(0) - index * spitWidth;

            PointBean pointValue = new PointBean(x, endY);
            pointValuesList.add(pointValue);
        }


    }

    public void setRealValueList(List<Integer> realValueList) {
        this.values.clear();

        if (realValueList.size() > 10) {

            for (int i = 10; i > 0; i--) {
                int index = realValueList.size() - 1 - i;
                this.values.add(realValueList.get(index));
            }
        } else {
            for (int i = 0; i < realValueList.size(); i++) {
                this.values.add(realValueList.get(i));
            }
        }
        update();
    }
}