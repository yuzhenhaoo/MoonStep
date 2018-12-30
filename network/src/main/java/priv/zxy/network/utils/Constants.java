package priv.zxy.network.utils;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/29
 * 描述: 常量工具类
 **/

public class Constants {

    /**
     * Log日志前缀名
     */
    public static final String LOG_TAG = "network";

    /**
     * 系统网络改变广播
     */
    public static final String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    /**
     * 自定义网络改变广播（加锁+兼容）
     */
    public static final String CUSTOM_ANDROID_NET_CHANGE_ACTION = "network.android.net.conn.CONNECTIVITY_CHANGE";

}
