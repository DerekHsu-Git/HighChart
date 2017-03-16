package com.gaogao.highchart;

import android.util.Log;

/**
 * @name highChart
 * @class name：com.gaogao.highchart
 * @class describe
 * @anthor xujianbo E-mail: xuarbo@qq.com
 * @time 2017/2/13 21:32
 * @change
 * @chang time
 * @class describe
 */
public class LogUtil {

    private static String TAG = "HighChart";

    public static void e(String msg) {
        Log.e(TAG, getFormatText(msg));
    }

    public static void e(String tag, String msg) {
        Log.e(tag, getFormatText(msg));
    }

    public static void d(String msg) {
        Log.d(TAG, getFormatText(msg));
    }

    public static void i(String msg) {
        Log.i(TAG, getFormatText(msg));
    }

    private static String getFormatText(String msg) {
        return "{ " + msg + " }";
    }

}
