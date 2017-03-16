package com.gaogao.highchart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.gaogao.highchart.chart.bean.bar.BarChartData;
import com.gaogao.highchart.chart.bean.pie.PieData;
import com.gaogao.highchart.chart.bean.pie.PieDataSet;
import com.gaogao.highchart.chart.bean.pie.PieEntry;
import com.gaogao.highchart.chart.view.barchart.BarChartView;
import com.gaogao.highchart.chart.view.circle.CircleView;
import com.gaogao.highchart.chart.view.linechart.LineChartView;
import com.gaogao.highchart.chart.view.linechart.data.ChartBean;
import com.gaogao.highchart.chart.view.linechart.data.Line;
import com.gaogao.highchart.chart.view.pie.PieChart;

import java.util.ArrayList;
import java.util.List;

//import com.gaogao.highchart.chart.view.linechart.data.Line;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LineChartView lineChartView = (LineChartView) findViewById(R.id.linechart);
        int[] levle = new int[2];
        levle[0] = 20;
        levle[1] = 80;

        List<Line> mLines = new ArrayList<>();

        List<ChartBean> mList = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            ChartBean chartBean = new ChartBean();
            chartBean.setValue((int) (Math.random()*100));
            mList.add(chartBean);
        }
        Line line = new Line(mList);
        mLines.add(line);

        List<ChartBean> mList2 = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            ChartBean chartBean = new ChartBean();
            chartBean.setValue((int) (Math.random()*120));
            mList2.add(chartBean);
        }
        Line line2 = new Line(mList2);
        mLines.add(line2);


        lineChartView.setLineType(false).setShowShade(true).setRealMaxSize(0);
        lineChartView.setMaxYAxisValue(150);//Y轴最大值
        lineChartView.setLevel(levle[0], levle[1]);    //分别设置下限、上限
        lineChartView.setBarChartData(mLines);
        lineChartView.startWithAnim(1500);
        lineChartView.requestLayout();//必须要，否则LineChartRender内pointValuesList报null

        CircleView circleView = (CircleView) findViewById(R.id.circleview);
        circleView.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        circleView.setCicleType(0);
        circleView.setProgressLineWidth(15);
        circleView.setStartAngle(-90);
        circleView.setSweepAngle(360);
        circleView.setTitle("这是标题");
        circleView.setCurrent("当前进度50%");
        circleView.setTarget("目标1000");
        circleView.setProgressWithDuration(50,1500);


        PieChart pieChart = (PieChart) findViewById(R.id.piechart);
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(163,27,27));
        colors.add(Color.rgb(63, 38, 165));
        colors.add(Color.rgb(30,84, 41));
        colors.add(Color.rgb(175, 52, 209));
        colors.add(Color.rgb(103, 126, 30));
        for (int i = 0; i < 5; i++) {
            PieEntry pieEntry = new PieEntry();
            pieEntry.setName("名字" +i);
            pieEntry.setValue(20);
            pieEntries.add(pieEntry);
            Log.e("添加时的颜色：",colors.get(i)+"");
        }
        pieChart.setCenterText("饼状图");
        PieDataSet pieDataSet = new PieDataSet(pieEntries,colors);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setProgressWithDuration(pieData,1500);

        BarChartView barChart = (BarChartView) findViewById(R.id.barChart);
        ArrayList<BarChartData> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            BarChartData barChartData = new BarChartData();
            barChartData.setxValueText(""+i);
            barChartData.setyValue((int) (Math.random()*1000));
            list.add(barChartData);
        }
        barChart.setDataBeen(list);
        barChart.startAnimation(1500);

    }
}
