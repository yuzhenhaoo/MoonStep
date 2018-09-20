package priv.zxy.moonstep.login_activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mob.MobSDK;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.Utils.LoginUtil;
import priv.zxy.moonstep.Utils.SharedPreferencesUtil;
import priv.zxy.moonstep.Utils.ToastUtil;

public class LoginActivity extends AppCompatActivity {

    private Button loginWeixin;
    private Button loginQQ;
    private Button loginWeibo;
    private EditText account;
    private EditText password;
    private Button clickLogin;
    private Button forgetPassword;
    private Button registerPhone;
    private View deepBackground;
    private View plainBackground;
    private ContentLoadingProgressBar progressBar;
    private String inputAccount;
    private String inputPassword;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 0x01:
                    progressBar.hide();
                    deepBackground.setVisibility(View.GONE);
                    plainBackground.setVisibility(View.GONE);
                    break;
            }
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        initView();

        jumpPage();
    }

    public void initView() {
        loginWeixin = (Button) findViewById(R.id.login_weixin);
        loginQQ = (Button) findViewById(R.id.login_QQ);
        loginWeibo = (Button) findViewById(R.id.login_weibo);
        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        clickLogin = (Button) findViewById(R.id.click_login);
        forgetPassword = (Button) findViewById(R.id.forget_password);
        registerPhone = (Button) findViewById(R.id.register_phone);
        deepBackground = (View) findViewById(R.id.deepBackground);
        plainBackground = (View) findViewById(R.id.plainBackground);
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
        //MobSDK的初始化
        MobSDK.init(this);

        //实现之前成功存下的账户和密码的读取
        SharedPreferencesUtil sp = new SharedPreferencesUtil(this.getApplicationContext());
        account.setText(sp.readLoginInfo().get("UserName"));
        password.setText(sp.readLoginInfo().get("PassWd"));

        //隐藏两个为了刷新而产生的View布局和ProgressBar
        deepBackground.setVisibility(View.GONE);
        plainBackground.setVisibility(View.GONE);
        progressBar.hide();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void jumpPage() {
        registerPhone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        registerPhone.setTextSize(24);
                        jump_to_register_phone_page();

                        break;
                    case MotionEvent.ACTION_UP:
                        registerPhone.setTextSize(20);
                        break;
                }
                return true;
            }
        });

        forgetPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        forgetPassword.setTextSize(22);
                        jump_to_forget_pwd_page();
                        break;
                    case MotionEvent.ACTION_UP:
                        forgetPassword.setTextSize(20);
                        break;
                }
                return true;
            }
        });

        clickLogin.setOnTouchListener(new View.OnTouchListener() {
            //返回值改为true，否则在执行了DOWN以后不再执行UP和MOVE操作。
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        clickLogin.setTextSize(24);
                        getData();
                        try {
                            refreshPage();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.currentThread().sleep(1300);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                handler.obtainMessage(0x01).sendToTarget();
                            }
                        }).start();
                        checkAndOperateData();
                        break;
                    case MotionEvent.ACTION_UP:
                        clickLogin.setTextSize(20);
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 点击进入手机注册页面
     */
    public void jump_to_register_phone_page() {
        Intent intent = new Intent(this, RegisterPhone1.class);
        startActivity(intent);
    }

    /**
     * 点击进入忘记密码页面
     */
    public void jump_to_forget_pwd_page() {
        Intent intent = new Intent(this, ForgetPwdActivity.class);
        startActivity(intent);
    }

    /**
     * 获得用户名和用户密码
     */
    private void getData() {
        inputAccount = account.getText().toString();
        inputPassword = password.getText().toString();
    }

    /**
     * 刷新页面
     */
    private void refreshPage() throws InterruptedException {
        progressBar.show();
        Thread.sleep(200);
        deepBackground.setVisibility(View.VISIBLE);
        plainBackground.setVisibility(View.VISIBLE);
    }

    private void checkAndOperateData() {
        if (inputAccount != null && inputPassword != null) {
            LoginUtil loginUtil = new LoginUtil(this.getApplicationContext(), this);
            loginUtil.LoginRequest(inputAccount, inputPassword);
        } else {
            ToastUtil toastUtil = new ToastUtil(this.getApplicationContext(), this);
            toastUtil.showToast("您的账户/密码不能为空哦！");
        }
    }
}
