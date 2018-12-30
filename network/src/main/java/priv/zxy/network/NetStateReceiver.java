package priv.zxy.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import priv.zxy.network.bean.MethodManager;
import priv.zxy.network.bean.Network;
import priv.zxy.network.type.NetType;
import priv.zxy.network.utils.Constants;
import priv.zxy.network.utils.LogUtil;
import priv.zxy.network.utils.NetworkUtils;

import static priv.zxy.network.type.NetType.CMNET;
import static priv.zxy.network.type.NetType.CMWAP;
import static priv.zxy.network.type.NetType.WIFI;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/29
 * 描述: 检测网络状态的改变，需要配置
 **/

public class NetStateReceiver extends BroadcastReceiver {

    public boolean hasNetwork;
    private NetType netType;
    /**
     * 用来保存这些带注解的方法，Object是Activity，而List是每个Activity
     *     内部的所有注解方法
     */
    private Map<Object, List<MethodManager>> networkList;

    public NetStateReceiver() {
        netType = NetType.NONE;
        networkList = new HashMap<>();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            LogUtil.d(Constants.LOG_TAG, "NetStateReceiver异常");
            return;
        }
        // 处理广播事件（忽略大小写）
        if (intent.getAction().equalsIgnoreCase(Constants.ANDROID_NET_CHANGE_ACTION) ||
                intent.getAction().equalsIgnoreCase(Constants.CUSTOM_ANDROID_NET_CHANGE_ACTION)) {
            Log.e(Constants.LOG_TAG, "网络发生改变");
            if (!NetworkUtils.isNetworkAvailable()) {
                hasNetwork = false;
                Log.e(Constants.LOG_TAG, "没有网络连接");
            } else {
                hasNetwork = true;
                Log.e(Constants.LOG_TAG, "网络连接成功");
            }
            netType = NetworkUtils.getNetType();
            // 开始向所有的被通知者(Activity)发送通知(网络状态)
            post(netType);
        }
    }

    public boolean hasNetwork(){
        return hasNetwork;
    }
    /**
     * 注册网络观察者
     * @param register Activity
     */
    public void registerObserver(Object register) {
        //获取Activity的所有方法
        List<MethodManager> methodManagers = networkList.get(register);
        // 不为空表示之前注册过
        if (methodManagers == null){
            // register拿到所有的注解方法
            methodManagers = findAnnotationMethod(register);
            // 把所有的注解方法压栈
            networkList.put(register, methodManagers);
        }
    }

    /**
     * 查找对象的注解方法
     * @param register Activity
     * @return 所有的注解Method
     */
    private List<MethodManager> findAnnotationMethod(Object register) {
        List<MethodManager> methodList = new ArrayList<>();
        // 获取类
        Class<?> clazz = register.getClass();
        // 获取所有方法
        Method[] methods = clazz.getMethods();

        while (clazz != null) {
            // 找出系统类，直接跳出，不添加cacheMap（因为不是订阅者）
            String clazzName = clazz.getName();
            if (clazzName.startsWith("java.") || clazzName.startsWith("javax.")
                    || clazzName.startsWith("android.")) {
                break;
            }

            // 循环方法
            for (Method method : methods) {
                Network network = method.getAnnotation(Network.class);
                // 判断注解不为空，切记不能抛异常
                if (network == null) {
                    continue;
                }
                // 严格控制方法格式和规范
                // 方法必须是返回void（一次匹配）
                Type returnType = method.getGenericReturnType();
                if (!"void".equals(returnType.toString())) {
                    throw new RuntimeException(method.getName() + "方法返回必须是void");
                }
                // 方法参数必须有值（二次匹配）
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 1) {
                    throw new RuntimeException(method.getName() + "方法有且只有一个参数");
                }

                // 完全符合要求、规范的方法，保存到方法对象中MethodManager（2个重要成员：方法、参数）
                MethodManager manager = new MethodManager(parameterTypes[0], network.netType(), method);
                methodList.add(manager);
            }

            // 不断循环找出父类含有订阅者（注解方法）的类。直到为空，比如AppCompatActivity
            clazz = clazz.getSuperclass();
        }
        return methodList;
    }

    /**
     * 分发消息
     * @param setter 网络类型
     */
    private void post(final NetType setter) {
        // 订阅者已经登记，从登记表中找出
        Set<Object> set = networkList.keySet();
        // 比如获取MainActivity对象
        for (final Object getter : set) {
            // 获取MainActivity中所有注解的方法
            List<MethodManager> methodList = networkList.get(getter);
            if (methodList != null) {
                // 循环每个方法
                for (final MethodManager method : methodList) {
                    // class1.isAssignableFrom(class2) 判定此 Class 对象所表示的类或接口
                    // 与指定的 Class 参数所表示的类或接口是否相同，或是否是其超类或超接口
                    if (method.getType().isAssignableFrom(setter.getClass())) {
                        switch (method.getNetType()) {
                            case AUTO:
                                invoke(method, getter, setter);
                                break;
                            case WIFI:
                                if (setter == WIFI || setter == NetType.NONE) {
                                    invoke(method, getter, setter);
                                }
                                break;
                            case CMWAP:
                                if (setter == CMWAP || setter == NetType.NONE) {
                                    invoke(method, getter, setter);
                                }
                                break;
                            case CMNET:
                                if (setter == CMNET || setter == NetType.NONE) {
                                    invoke(method, getter, setter);
                                }
                                break;

                            default:
                                break;
                        }
                    }
                }
            }
        }
    }

    /**
     * 找到匹配方法后，通过反射调用MainActivity中所有符合要求的方法
     * @param method 方法
     * @param getter 被执行方法的对象
     * @param setter 传入方法的实参（NetType）
     */
    private void invoke(MethodManager method, Object getter, Object setter) {
        Method execute = method.getMethod();
        try {
            execute.invoke(getter, setter);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    /**
     * 注销网络观察者
     * @param register Activity
     */
    public void unRegisterObserver(Object register) {
        if (!networkList.isEmpty()) {
            networkList.remove(register);
        }
        LogUtil.d(Constants.LOG_TAG, register.getClass().getName() + "网络监听注销成功");
    }

    /**
     * 注销所有的网络观察者
     */
    public void unRegisterAllObserver() {
        if (!networkList.isEmpty()) {
            networkList.clear();
        }
        NetworkManager.getInstance().getApplication().unregisterReceiver(this);
        networkList = null;
        LogUtil.d(Constants.LOG_TAG, "注销所有的网络监听成功");
    }
}
