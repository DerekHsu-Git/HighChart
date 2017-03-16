package com.gaogao.highchart.chart.view.linechart.render;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.DisplayMetrics;

import com.gaogao.highchart.DpUtil;
import com.gaogao.highchart.chart.bean.bar.PointBean;
import com.gaogao.highchart.chart.view.linechart.data.Line;

import java.util.ArrayList;
import java.util.List;

import static com.gaogao.highchart.MyApp.getContext;


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

public class LineChartRender extends BaseRender {

    protected Paint yLinePaint;
    private int yLineWidth = 3;
    private int yLineColor = Color.parseColor("#599de5");//599de5
    private int gradientColor = Color.parseColor("#88599de5");
    private int textColor  = Color.parseColor("#599de5");

    private boolean isShowShade = false;
    private Paint labelPaint;

    public void setShowShade(boolean showShade) {
        lineShaderPaint.setStyle(Paint.Style.FILL);
        linePathPaint.setStyle(Paint.Style.STROKE);
    }

    private Paint levelPaint;

    protected Paint lineShaderPaint;
    protected Paint linePathPaint;
    private Paint chartTextPaint;

    private int minLevel;
    private int maxLevel;

    private int minLevelY;
    private int maxLevelY;

//    protected List<Line> lineList;


    protected List<PointBean> pointValuesList;
    protected Path path = new Path();

    private int currentIndex = 0;
    private int selectIndex = -1;

    private boolean drawXGridLine = false;
    public void setGradientColor(int gradientColor) {
        this.gradientColor = gradientColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public LineChartRender() {

        yLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        yLinePaint.setColor(yLineColor);
        yLinePaint.setStrokeWidth(yLineWidth);
        yLinePaint.setStyle(Paint.Style.STROKE);

        levelPaint = new Paint();
        levelPaint.setAntiAlias(true);
        levelPaint.setColor(yLineColor);
        levelPaint.setStrokeWidth(yLineWidth);
        levelPaint.setStyle(Paint.Style.STROKE);
        int width = getSizeInPixels(10, getContext());
        int space = getSizeInPixels(5, getContext());
        levelPaint.setPathEffect(new DashPathEffect(new float[]{width, space}, 0));//虚线

        lineShaderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lineShaderPaint.setStrokeWidth(yLineWidth);
        lineShaderPaint.setColor(yLineColor);
        lineShaderPaint.setStyle(Paint.Style.FILL);

        lineShaderPaint.setStyle(Paint.Style.STROKE);

        linePathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePathPaint.setStrokeWidth(yLineWidth);
        linePathPaint.setColor(yLineColor);
        linePathPaint.setStyle(Paint.Style.STROKE);


        chartTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        chartTextPaint.setStyle(Paint.Style.STROKE);
        chartTextPaint.setTextAlign(Paint.Align.CENTER);
        chartTextPaint.setColor(textColor);
        chartTextPaint.setTextSize(18);

        labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    }


    public int getSizeInPixels(float dp, Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float pixels = metrics.density * dp;
        return (int) (pixels + 0.5f);
    }

    @Override
    protected void calculateScale() {
        super.calculateScale();

        calculateLevelScale();

        calculateChartBean();

        lineShaderPaint.setStyle(Paint.Style.FILL);
        Shader shader = new LinearGradient(startX.get(0), stopY.get(0), startX.get(0), startY.get(0), new int[]{gradientColor, Color.TRANSPARENT},
                null, Shader.TileMode.CLAMP);
        lineShaderPaint.setShader(shader);

    }

    protected void calculateChartBean() {
        if(lineList!=null && !lineList.isEmpty()){
            for (int j = 0; j < lineList.size(); j++) {
                Line line = lineList.get(j);
                if (pointValuesList == null) {
                    pointValuesList = new ArrayList<>();
                } else {
                    pointValuesList.clear();
                }


                for (int i = 0; i < line.getValues().size(); i++) {
                    float scale = (float) line.getValues().get(i).getValue() / (float) maxValue;
                    if (scale > 1) {
                        scale = 1;
                    }

                    float endY = (1 - scale) * startY.get(j);

                    PointBean pointValue = new PointBean(startX.get(j) + i * spitWidth, endY+8);//目的是解决最大点文字显示不全

                    pointValuesList.add(pointValue);
                }
            }
        }

    }

    private void calculateLevelScale() {
        float scaleMin = (minLevel * 1.0f) / (maxValue * 1.0f);
        minLevelY = 8+(int) ((1 - scaleMin) * (startY.get(0)));		//加8是为了与上面endY+8同步

//		LogUtil.e("scaleMin = " + scaleMin);
        float scaleMax = (maxLevel * 1.0f) / (maxValue * 1.0f);
        maxLevelY = 8+(int) ((1 - scaleMax) * (startY.get(0)));
//		LogUtil.e("scaleMax = " + scaleMax);
    }

    @Override
    public void drawLineChart(Canvas canvas) {
        super.drawLineChart(canvas);

        if (drawXGridLine) {
            drawYLine(canvas);
        }
//        drawLevel(canvas);


        drawLineAndPoint(canvas);
    }

    protected void drawLineAndPoint(Canvas canvas) {

        if (lineList!=null && !lineList.isEmpty()){

            for (int j = 0; j < lineList.size(); j++) {
                Line line = lineList.get(j);
                if (currentIndex == 0) {
                    return;
                }

                chartTextPaint.setColor(textColor);
                yLinePaint.setStyle(Paint.Style.FILL);
                for (int i = 0; i < currentIndex; i++) {

                    PointBean currentPoint = pointValuesList.get(i);
                    canvas.drawCircle(currentPoint.getX(), currentPoint.getY(), 5, yLinePaint);

                    if (selectIndex == -1) {
                        continue;
                    }

                    if (selectIndex == i) {
                        drawLabels(canvas,line.getValues().get(i).getValue()+"",currentPoint.getX(),currentPoint.getY() - 8);
                    }
                }

                path.reset();
                float endX = 0;
                for (int i = 0; i < currentIndex; i++) {
                    PointBean currentPoint = pointValuesList.get(i);
                    if (i == 0) {
                        path.moveTo(startX.get(0), startY.get(j));
                        path.lineTo(currentPoint.getX(), currentPoint.getY());
                    } else {
                        path.lineTo(currentPoint.getX(), currentPoint.getY());
                        endX = currentPoint.getX();
                    }

                }
                path.lineTo(endX, startY.get(j));

                if (path == null) {
                    return;
                }

                path.setFillType(Path.FillType.WINDING);//EVEN_ODD

                canvas.drawPath(path, lineShaderPaint);
                canvas.drawPath(path, linePathPaint);
            }
        }
    }

        /**
     * 画标签
     * @param canvas
     * @param
     */
    protected void drawLabels(Canvas canvas,String label,float x,float y) {

                labelPaint.setTextSize(DpUtil.sp2px(getContext(), 12));

                Paint.FontMetrics fontMetrics = labelPaint.getFontMetrics();

                    Rect bounds = new Rect();
                    int length = label.length();
                    labelPaint.getTextBounds(label, 0, length, bounds);

                    float textW = bounds.width();
                    float textH = bounds.height();
                    float left, top, right, bottom;
                    if(length == 1){
                        left = x - textW * 2.2f;
                        right = x + textW * 2.2f;
                    }  else if(length == 2){
                        left = x - textW * 1.0f;
                        right = x+ textW * 1.0f;
                    } else {
                        left =x - textW * 0.6f;
                        right = x + textW * 0.6f;
                    }
                    top = y - 2.5f*textH;
                    bottom = y - 0.5f*textH;

                    //控制位置
                    if(left < 0){
                        left = 10;
                        right += 10;
                    }
                    if(top < 0){
                        top = 10;
                        bottom += 10;
                    }
                    RectF rectF = new RectF(left, top, right, bottom);
                    float labelRadius = DpUtil.dp2px(getContext(),5);
                    labelPaint.setColor(Color.BLUE);
                    canvas.drawRoundRect(rectF, labelRadius, labelRadius, labelPaint);

                    //drawText
                    labelPaint.setColor(Color.WHITE);
                    float xCoordinate = left + (right - left - textW) / 2;
                    float yCoordinate = bottom - (bottom - top - textH) / 2 ;
                    canvas.drawText(label, xCoordinate, yCoordinate, labelPaint);

    }

//    private void drawLevel(Canvas canvas) {
//        Path path = new Path();
//        path.moveTo(startX - left,minLevelY);
//        path.lineTo(stopX,minLevelY);
//        canvas.drawPath(path,levelPaint);
//
//        path = new Path();
//        path.moveTo(startX - left,maxLevelY);
//        path.lineTo(stopX,maxLevelY);
//        canvas.drawPath(path,levelPaint);
//
////		canvas.drawLine(startX - left, minLevelY, stopX, minLevelY, levelPaint);
////		canvas.drawLine(startX - left, maxLevelY, stopX, maxLevelY, levelPaint);
//    }


    private void drawYLine(Canvas canvas) {
//        for (int i = 0; i < size; i++) {
//            canvas.drawLine(startX + i * spitWidth, startY, startX + i * spitWidth, stopY, yLinePaint);
//        }
    }

    //********************************************************************************************//

    @Override
    public void setLevel(int minLevel, int maxLevel) {

        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }


    public void setCurrentIndex(int index) {
        currentIndex = index;
    }

    public void setSelectIndex(int selectIndex) {

        this.selectIndex = selectIndex;
    }

    public void setDrawXGridLine(boolean drawXGridLine) {
        this.drawXGridLine = drawXGridLine;
    }
}