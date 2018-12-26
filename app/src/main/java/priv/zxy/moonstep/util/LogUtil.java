package priv.zxy.moonstep.util;

import android.util.Log;

/**
 * 创建人: Administrator
 * 创建时间: 2018/10/29
 * 描述: 创建日志工具类，用来控制开发过程中的调试日志
 *          当程序处于开发阶段时，让日志打印出来，当程序上线了之后就把日志屏蔽掉。
 *          主要原因有两个：
 *          ①会降低程序的运行效率
 *          ②会将一些机密性的数据泄露出去
 **/
public class LogUtil {

    private static final int VERBOSE = 1;

    private static final int DEBUG = 2;

    private static final int INFO = 3;

    private static final int WARN = 4;

    private static final int ERROR = 5;

    private static final int NOTHING = 6;

    private static int level = VERBOSE;

    public static void v(String tag, String msg){
        if (level <= VERBOSE){
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg){
        if (level <= DEBUG){
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg){
        if (level <= INFO){
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg){
        if (level <= WARN){
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg){
        if (level <= ERROR){
            Log.e(tag, msg);
        }
    }
}
