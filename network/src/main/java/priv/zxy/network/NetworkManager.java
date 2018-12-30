package priv.zxy.network;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;

import priv.zxy.network.utils.Constants;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/29
 * 描述: 网络管理
 **/

public class NetworkManager {

    /**
     * volatile修饰的变量不允许线程内部缓存和重排序，即直接修改内存
     * 换句话说，volatile修饰的变量，线程在每次使用变量的时候，都会读取变量修改后的最后的值。
     */
    private static volatile NetworkManager instance = null;
    private NetStateReceiver receiver;
    private Application application = null;

    private NetworkManager() {
        receiver = new NetStateReceiver();
    }

    public static NetworkManager getInstance(){
        if (instance == null){
            synchronized (NetworkManager.class){
                if (instance == null){
                    instance = new NetworkManager();
                }
            }
        }
        return instance;
    }

    public void init(Application application){
        this.application = application;

        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ANDROID_NET_CHANGE_ACTION);
        filter.addAction(Constants.CUSTOM_ANDROID_NET_CHANGE_ACTION);
        application.registerReceiver(receiver, filter);
    }

    public Application getApplication() {
        if (application == null){
            throw new RuntimeException("NetworkManager.getgetDefault().init()未初始化");
        }
        return application;
    }

    /**
     * 检查网络状态
     */
    public void checkNetworkState(){
        getApplication();
        Intent intent = new Intent();
        //自定义的BroadCast的Action属性
        intent.setAction(Constants.CUSTOM_ANDROID_NET_CHANGE_ACTION);
        getApplication().sendBroadcast(intent);
    }
    /**
     * 获取当前网络状态
     * @return true为网络连接成功，false为网络连接失败
     */
    public boolean hasNetwork() {
        return receiver.hasNetwork();
    }


    /**
     * 注册观察者
     * 相当于是传递了一个activity给广播接收器
     * 会在广播接收器中通过反射拿到所有的注解方法
     * @param register Activity
     */
    public void registerObserver(Object register) {
        getApplication();
        receiver.registerObserver(register);
    }

    /**
     * 注销Activity的观察者
     * @param register Activity
     */
    public void unRegisterObserver(Object register) {
        receiver.unRegisterObserver(register);
    }

    /**
     * 注销所有的网络观察者
     */
    public void unRegisterAllObserver() {
        receiver.unRegisterAllObserver();
    }
}
