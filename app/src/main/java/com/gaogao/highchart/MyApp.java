package com.gaogao.highchart;

import android.app.Application;
import android.content.Context;

/**
 * @name highChart
 * @class name：com.gaogao.highchart
 * @class describe
 * @anthor xujianbo E-mail: xuarbo@qq.com
 * @time 2017/3/5 23:03
 * @change
 * @chang time
 * @class describe
 */
public class MyApp extends Application {

    private static Context context;
    private static MyApp myApp;

    public MyApp() {

    }

    public static MyApp get() {

//		if (myApp == null){
//			myApp = new MyApp();
//		}
        return myApp;
    }

    public static Context getContext() {

        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
        context = this;

    }
}
