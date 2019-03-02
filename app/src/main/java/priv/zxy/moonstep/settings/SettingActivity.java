package priv.zxy.moonstep.settings;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;

import java.lang.ref.WeakReference;

import priv.zxy.moonstep.BuildConfig;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.customview.MyDialog;
import priv.zxy.moonstep.login.view.LoginSurface;
import priv.zxy.moonstep.util.LogUtil;
import priv.zxy.moonstep.wheel.animate.AbstractAnimateEffect;
import priv.zxy.moonstep.wheel.animate.ElasticityAnimation;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/14
 * 描述:
 **/

public class SettingActivity extends AppCompatActivity implements ISettingView{

    private TextView accountSetting;
    private TextView phoneNumber;
    private TextView about;
    private TextView cleanChatSpace;
    private MyDialog myDialog;
    private Button exitLogin;

    private MyHandler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();

        initData();

        initEvent();
    }

    private void initView() {
        accountSetting = (TextView) findViewById(R.id.accountSetting);
        phoneNumber = (TextView) findViewById(R.id.phoneNumber);
        about = (TextView) findViewById(R.id.help);
        cleanChatSpace = (TextView) findViewById(R.id.cleanChatSpace);
        exitLogin = (Button) findViewById(R.id.exitLogin);

        handler = new MyHandler(this);
    }

    private void initData(){

    }

    @Override
    protected void onDestroy() {
        if (myDialog != null){
            myDialog.dismiss();
        }
        super.onDestroy();
    }

    private void initEvent(){
        about.setOnClickListener(v -> toAboutActivity());

        exitLogin.setOnClickListener(v -> {
            ElasticityAnimation.getInstance(exitLogin).show();
            new Thread(()->{
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0x01);
            }).start();
        });
    }

    public void popDialogWindow() {
        myDialog = new MyDialog(this);
        myDialog.setTitle("退出提示!");
        myDialog.setContent("退出登陆后我们会继续保留您的账户数据，记得常回来看看哦！");
        myDialog.setNegativeClickLister("取消", myDialog::dismiss);
        myDialog.setPositiveClickLister("确认", () -> {
            new Thread(() -> {
                EMClient.getInstance().logout(true);//退出EMC的服务，让当前用户不在线
            }).start();
            if (BuildConfig.DEBUG) LogUtil.d("PersonalSurfaceFragment", "用户已经成功下线");
            toLoginActivity();
        });
        myDialog.show();
    }

    public void toLoginActivity() {
        Intent intent = new Intent(this, LoginSurface.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void toAboutActivity() {
        Intent intent = new Intent(this, AboutPage.class);
        startActivity(intent);
    }

    private static class MyHandler extends Handler {

        private WeakReference<SettingActivity> reference;

        MyHandler(SettingActivity activity) {
            reference = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SettingActivity activity = reference.get();
            activity.popDialogWindow();
        }
    }
}
