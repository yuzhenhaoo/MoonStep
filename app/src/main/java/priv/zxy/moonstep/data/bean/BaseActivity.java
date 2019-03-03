package priv.zxy.moonstep.data.bean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.util.LogUtil;
import priv.zxy.network.NetworkManager;
import priv.zxy.network.bean.Network;
import priv.zxy.network.type.NetType;
import priv.zxy.network.utils.Constants;

/**
 * 创建人: Administrator
 * 创建时间: 2018/10/19
 * 描述: 创建BaseActivity使之成为所有Activity的父类
 *       功能如下：1、使每个继承了BaseActivity的Activity运行的时候可以打印出该活动的类名
 *                 2、通过ActivityCollector构建Activity返回栈，使Activity返回栈可见，使对Activity的管理操作更加顺畅
 **/
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏通知栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        LogUtil.d("BaseActivity", getClass().getSimpleName());
        // 把每一个继承了BaseActivity对ActivityCollector做入栈操作
        ActivityCollector.addActivity(this);

        /*
         我们需要注册监听，相当于让观察者(NetStateReceiver接收网络请求)
         添加所有的被观察者对象(Activity, List<AnnotationMethod>)
         */
        NetworkManager.getInstance().registerObserver(this);

        if (NetworkManager.getInstance().hasNetwork()) {
            LogUtil.d(Constants.LOG_TAG, "onCreate() --> hasNetwork() --> true ");
        }
    }

    protected void setView(int viewId) {
        super.setContentView(viewId);
        initView();
        initData();
        initEvent();
    }

    @Network(netType = NetType.AUTO)
    public void network(NetType netType){
        switch (netType) {
            case WIFI:
                // 告知用户已经恢复WIFI网络
                Toast.makeText(this, "您的WIFI已经恢复", Toast.LENGTH_SHORT).show();
                break;
            case CMNET:
            case CMWAP:
                Toast.makeText(this, "您的数据流量已经打开", Toast.LENGTH_SHORT).show();
                LogUtil.d(this.getClass().getName(), "网络已经连接， 网络类型:" + netType.name());
                break;
            case NONE:
                Toast.makeText(this, "您的网络未连接", Toast.LENGTH_SHORT).show();
                LogUtil.d(this.getClass().getName(), "");
            default:
        }
    }

    /**
     * 初始化每个Activity的数据
     */
    abstract protected void initData();

    /**
     * 初始化每个Activity的View
     */
    abstract protected void initView();

    /**
     * 初始化每个Activity的发生时事件
     */
    abstract protected void initEvent();

    /**
     * 跳转到某个Activity中
     * @param activity 当前Activity
     * @param className 要跳转的Activity类名
     */
    protected void toTargetActivity(Activity activity, Class className) {
        Intent intent = new Intent(activity, className);
        startActivity(intent);
    }

    /**
     * 获取某个基于该Activity的Fragment实例
     * @param fragmentId 对应Fragment的id，如:R.id.fragment
     * @return 返回该Fragment的实例（使用时需要强制类型转换）
     */
    protected Fragment findSubFragment(int fragmentId) {
        return getSupportFragmentManager().findFragmentById(fragmentId);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        NetworkManager.getInstance().unRegisterObserver(this);
    }
}
