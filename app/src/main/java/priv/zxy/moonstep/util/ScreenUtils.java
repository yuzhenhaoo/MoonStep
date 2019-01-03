package priv.zxy.moonstep.util;

import android.content.Context;

/**
 * 创建人: Administrator
 * 创建时间: 2019/1/3
 * 描述: 屏幕参数工具类
 **/

public class ScreenUtils {

    /**
     * 获取屏幕高度(px)
     * @param context 句柄
     * @return 高度像素
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕宽度(px)
     * @param context 句柄
     * @return 宽度像素
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
