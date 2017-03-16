package com.gaogao.highchart.chart.view.linechart;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.gaogao.highchart.AnimUtil;
import com.gaogao.highchart.LogUtil;
import com.gaogao.highchart.chart.view.linechart.axis.XAxis;
import com.gaogao.highchart.chart.view.linechart.render.LineChartRender;
import com.gaogao.highchart.chart.view.linechart.render.RealLineRender;

import java.util.ArrayList;
import java.util.List;

/**
 * @name highChart
 * @class name：com.gaogao.highchart.chart.view.linechart
 * @class describe
 * @anthor xujianbo E-mail: xuarbo@qq.com
 * @time 2017/3/5 23:06
 * @change
 * @chang time
 * @class describe
 */

public class LineChartView extends BaseChart {

    private boolean isRealLine;                            //是否是是实时曲线
    private LineChartRender lineChartRender;                //折线图的渲染器
    private RealLineRender realLineRender;                    //实时曲线的渲染器
    private int selectIndex;
    private int realMaxSize;
    private boolean drawXGridLine = false;

    private int gradientColor = Color.parseColor("#88599de5");//fff0f0f0

    private int textColor  = Color.parseColor("#599de5");
    private boolean isShowShade = false;

    public LineChartView setShowShade(boolean showShade) {
        isShowShade = showShade;
        return this;
    }


    public LineChartView(Context context) {
        super(context);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LineChartView setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    public LineChartView setGradientColor(int gradientColor) {
        this.gradientColor = gradientColor;
        return this;
    }

    @Override
    protected void reCalculateSize() {
        super.reCalculateSize();

        LogUtil.e("reCalculateSize");


        if (isRealLine) {
            realLineRender = new RealLineRender();
            setRender(realLineRender);
            List<Integer> sx = new ArrayList<>();
            List<Integer> sy = new ArrayList<>();
            List<Integer> ex = new ArrayList<>();
            List<Integer> ey = new ArrayList<>();
            List<Integer> left = new ArrayList<>();

            for (int i = 0; i < lineList.size(); i++) {
                sx.add(0);
                sy.add(viewHeight - xAxis.getHeight());
                ex.add(viewWidth);
                ey.add(0);
                left.add(xAxis.getTextSize());
            }

            realLineRender.setRect(sx, sy, ex, ey, left);
            realLineRender.setMaxSize(realMaxSize);

        } else {

            xAxis = new XAxis(0, viewHeight, viewWidth, viewHeight);

            if(lineList!=null && !lineList.isEmpty()){
                xAxis.setDataBean(lineList);
            }
//
//            if (chartBeanList != null) {
//                xAxis.setDataBean(chartBeanList);
//            }

            List<Integer> sx = new ArrayList<>();
            List<Integer> sy = new ArrayList<>();
            List<Integer> ex = new ArrayList<>();
            List<Integer> ey = new ArrayList<>();
            List<Integer> left = new ArrayList<>();

            for (int i = 0; i < lineList.size(); i++) {
                sx.add(0);
                sy.add(viewHeight - xAxis.getHeight());
                ex.add(viewWidth);
                ey.add(0);
                left.add(xAxis.getTextSize());
            }
            lineChartRender = new LineChartRender();
            lineChartRender.setGradientColor(gradientColor);
            lineChartRender.setShowShade(isShowShade);
            lineChartRender.setTextColor(textColor);
            setRender(lineChartRender);
            lineChartRender.setDrawXGridLine(drawXGridLine);
            lineChartRender.setRect(sx, sy, ex, ey, left);
            if (lineList != null) {
                lineChartRender.setDataBeanList(lineList);
            }
        }
    }


    //********************************************************************************************//

    public void setDrawXGridLine(boolean drawXGridLine) {
        this.drawXGridLine = drawXGridLine;
    }

    /**
     * 是否是实时曲线
     *
     * @param isRealLine
     */
    public LineChartView setLineType(boolean isRealLine) {
        this.isRealLine = isRealLine;
        return this;

    }

    /**
     * 实时曲线最多显示多少个点
     *
     * @param maxSize
     */
    public LineChartView setRealMaxSize(int maxSize) {
        this.realMaxSize = maxSize;
        return this;
    }


    /**
     * 添加实时曲线
     *
     * @param value
     */
    public void addRealValue(int value) {
        if (realLineRender == null) {
            return;
        }
        realLineRender.addValue(value);
        invalidate();
    }

    /**
     * 更新实时曲线
     *
     * @param valueList
     */
    public void setRealValueList(List<Integer> valueList) {
        if (valueList == null) {
            return;
        }
        realLineRender.setRealValueList(valueList);
        invalidate();
    }

    /**
     * 动画时长
     *
     * @param length
     */
    public void startWithAnim(int length) {

        AnimUtil animUtil = new AnimUtil();
        animUtil.addUpdateListener(new AnimUtil.UpdateListener() {
            @Override
            public void progress(float progress) {

                int currentIndex = (int) (progress * lineList.get(0).getValues().size());
                updateRender(currentIndex);
            }
        });
        animUtil.setDuration(length);
        animUtil.startAnimator();

    }


    private void updateRender(int currentIndex) {
        if (lineChartRender == null) {
            return;
        }
        lineChartRender.setCurrentIndex(currentIndex);
        invalidate();
    }

    public void startNoAnim() {
//        updateRender(chartBeanList.size());
    }


    @Override
    protected void selectPoint(float x) {
        super.selectPoint(x);
//        if (lineChartRender == null) {
//            return;
//        }
//        if (chartBeanList == null || chartBeanList.size() == 0) {
//            return;
//        }
//        int split = xAxis.getSplitWidth();
//        float baseW = xAxis.getTextSize() + split / 2.0f;
//        float prevS = 0;    // 上一次比较的开始位置
//        for (int i = 0; i < chartBeanList.size(); i++) {
//            float maxW = baseW + split * i;
//            if (x > prevS && x < maxW) {
//                selectIndex = i;
//                break;
//            }
//            prevS = maxW;
//        }
//
//        lineChartRender.setSelectIndex(selectIndex);
//
//
//        invalidate();

    }
}

