package priv.zxy.moonstep.data.bean;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

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
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏通知栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        LogUtil.d("BaseActivity", getClass().getSimpleName());
        // 把每一个继承了BaseActivity对ActivityCollector做入栈操作
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
