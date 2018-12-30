package priv.zxy.network.bean;

import java.lang.reflect.Method;

import priv.zxy.network.type.NetType;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/29
 * 描述:保存符合要求的方法的封装类
 **/
public class MethodManager {

    /**
     * 订阅者的回调方法（注解方法）的参数类型
     */
    private Class<?> type;

    /**
     * 订阅者的回调方法（注解方法）的网络模式
     */
    private NetType netType;

    /**
     * 订阅者的回调方法（注解方法）
     */
    private Method method;

    public MethodManager(Class<?> type, NetType netType, Method method) {
        this.type = type;
        this.netType = netType;
        this.method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public void setNetType(NetType netType) {
        this.netType = netType;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public NetType getNetType() {
        return netType;
    }

    public Method getMethod() {
        return method;
    }

    @Override
    public String toString() {
        return "MethodManager{" +
                "type=" + type +
                ", netType=" + netType +
                ", method=" + method +
                '}';
    }
}
