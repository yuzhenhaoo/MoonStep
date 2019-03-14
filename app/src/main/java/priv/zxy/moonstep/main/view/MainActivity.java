package priv.zxy.moonstep.main.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.NetUtils;

import org.litepal.tablemanager.Connector;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.data.bean.BaseActivity;
import priv.zxy.moonstep.login.view.LoginSurface;
import priv.zxy.moonstep.util.ShowErrorReasonUtil;
import priv.zxy.moonstep.data.bean.ErrorCodeEnum;
import priv.zxy.moonstep.commerce.view.FragmentParent;

/**
 * 在MainActivity中获得Moonfriends的获取和MessageQueue的获取
 * 二就是在广播接收到MessageQueue后，传递给MoonFriendFragment改变当前好友列表的消息显示，
 * 再经过判断，通过ChattingActivity加载相应的数据。(问题二：我在得到消息队列后，
 * 给不同好友的消息在没有打开ChattingActivity的时候应该存储在哪里？是本地的数据库吗？直到打开ChattingActivity的时候再重新加载？)
 */

public class MainActivity extends BaseActivity{

    private Activity mActivity;
    /**
     * 定义一个变量，来标识是否退出
     */
    private static boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 实现数据库的创建
        Connector.getDatabase();
        setView(R.layout.activity_main);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, new FragmentParent()).commit();
        mActivity = this;

        View bottomBar = findViewById(R.id.appBar);
    }

    protected void initEvent(){
        // 注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());

    }

    /**
     * 监听返回按键，让返回键必须按两次才可以返回
     */
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void toLoginActivity() {
        Intent intent = new Intent(this, LoginSurface.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * 在ChattingActivity中对为了监听EMClient而设立的SharedPreferences文件而进行销毁，
     * 保证每次进入ChattingActivity只让EMClient的SharedPreferences初始化一次,
     * 但是需要检测只通过Edit清除缓存的话是不是会改变到文件本身的内容，
     * 如果可以改变，那么不需要删除文件，如果不能改变，
     * 那么不但必须清除缓存，也必须删除文件
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit(){
        if (!isExit){
            isExit = true;
            Toast.makeText(Application.getContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            new Thread(()->isExit = false);
        }else{
            finish();
            System.exit(0);
        }
    }

    private void  userRemovedHandler() {
        // 显示帐号已经被移除
        ShowErrorReasonUtil.getInstance(mActivity).show(ErrorCodeEnum.ACCOUNT_IS_DELETED);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 强制退出到登陆页面
        toLoginActivity();
    }

    private void userLoginInAnotherDevice() {
        // 显示帐号在其他设备登录
        ShowErrorReasonUtil.getInstance(mActivity).show(ErrorCodeEnum.ACCOUNT_IS_LOGGING_IN_OTHER_DEVICE);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 强制退出到登陆页面
        toLoginActivity();
    }

    private void userCanNotConnectWithChatServer() {
        if (NetUtils.hasNetwork(MainActivity.this)){
            // 连接不到聊天服务器
            ShowErrorReasonUtil.getInstance(mActivity).show(ErrorCodeEnum.CONNECT_CHAT_SERVICE_FAIL);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 强制退出到登陆页面
            toLoginActivity();
        } else{
            // 当前网络不可用，请检查网络设置
            ShowErrorReasonUtil.getInstance(mActivity).show(ErrorCodeEnum.NET_NOT_RESPONSE);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 强制退出到登陆页面
            toLoginActivity();
        }
    }

    /**
     * 对当前用户登录状态的一个监听情况，可以构建一个专门的MessageManager类来处理这些信息
     * 为了减少后期的维护成本，暂且不动
     */
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
            Looper.prepare();
            Toast.makeText(mActivity, "登陆成功!", Toast.LENGTH_SHORT).show();
            Looper.loop();
        }

        @Override
        public void onDisconnected(final int error){
            runOnUiThread(() -> {
                if (error == EMError.USER_REMOVED) {
                    userRemovedHandler();
                }

                if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    userLoginInAnotherDevice();
                } else {
                    // FIXME(张晓翼，2019/3/14，环信服务器登陆无响应，同时设置startEmServer为解耦即时通讯模块做准备)
                    if (Application.startEmServer) {
                        userCanNotConnectWithChatServer();
                    }
                }
            });
        }
    }
}