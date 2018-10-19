package priv.zxy.moonstep.login.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.mob.MobSDK;
import com.rengwuxian.materialedittext.MaterialEditText;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.utils.SharedPreferencesUtil;
import priv.zxy.moonstep.utils.ShowErrorReason;
import priv.zxy.moonstep.kernel.bean.ErrorCode;
import priv.zxy.moonstep.login.presenter.UserLoginPresenter;
import priv.zxy.moonstep.main.view.MainActivity;

/**
 *  Created by Zxy on 2018/9/23
 */

public class UserLoginActivity extends AppCompatActivity implements IUserLoginView {

    private Button loginWeixin;
    private Button loginQQ;
    private Button loginWeibo;
    private MaterialEditText account;
    private MaterialEditText password;
    private Button clickLogin;
    private Button forgetPassword;
    private Button registerPhone;
    private View deepBackground;
    private View plainBackground;
    private ContentLoadingProgressBar progressBar;
    private Activity mActivity;
    private Context mContext;
    private UserLoginPresenter userLoginPresenter;
    //实现之前成功存下的账户和密码的读取
    SharedPreferencesUtil sp;
    //加载动画资源文件
    Animation shake;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            hideLoading();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initView();
    }

    /**
     * 关于clickLogin中的逻辑处理，本来showLoading和hideLoading都是要放在presenter中减少activity的冗余度的，但是这里要做网络请求的话，必须要用到
     * handler来处理UI界面的变化，不然可能会引起ANR，然而handler又必须使用在View中，所以showLoading和hideLoading不得不写在activity中。
     */
    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        //MobSDK的初始化
        MobSDK.init(this);
        loginWeixin = (Button) findViewById(R.id.login_weixin);
        loginQQ = (Button) findViewById(R.id.login_QQ);
        loginWeibo = (Button) findViewById(R.id.login_weibo);
        account = (MaterialEditText) findViewById(R.id.account);
        password = (MaterialEditText) findViewById(R.id.password);
        clickLogin = (Button) findViewById(R.id.click_login);
        forgetPassword = (Button) findViewById(R.id.forget_password);
        registerPhone = (Button) findViewById(R.id.register_phone);
        deepBackground = (View) findViewById(R.id.deepBackground);
        plainBackground = (View) findViewById(R.id.plainBackground);
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
        mActivity = this;
        mContext = this.getApplicationContext();
        sp = new SharedPreferencesUtil(mContext);

        shake = AnimationUtils.loadAnimation(this, R.anim.shake);

        userLoginPresenter = new UserLoginPresenter(this, mActivity, mContext);

        userLoginPresenter.initAccountAndPassword(sp);

        account.requestFocus();

        userLoginPresenter.hideLoading();

        clickLogin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        clickLogin.startAnimation(shake);
//                        clickLogin.setClickable(false);//设置不能再次点击，当失败了设置为恢复点击效果
                        break;
                    case MotionEvent.ACTION_UP:
                        userLoginPresenter.showLoading();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    userLoginPresenter.login();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        break;
                }
                return true;
            }
        });

        registerPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPhone.startAnimation(shake);
                userLoginPresenter.toConfirmPhoneActivity();
            }
        });

        forgetPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        forgetPassword.startAnimation(shake);
                        break;
                    case MotionEvent.ACTION_UP:
                        userLoginPresenter.toForgetPasswordActivity();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public String getUserPhoneNumber() {
        return account.getText().toString();
    }

    @Override
    public String getUserPassword() {
        return password.getText().toString();
    }

    @Override
    public void showLoading() {
        progressBar.show();
        deepBackground.setVisibility(View.VISIBLE);
        plainBackground.setVisibility(View.VISIBLE);
        loginQQ.setEnabled(false);
        loginWeibo.setEnabled(false);
        loginWeixin.setEnabled(false);
        clickLogin.setEnabled(false);
        forgetPassword.setEnabled(false);
        registerPhone.setEnabled(false);
        account.setEnabled(false);
        password.setEnabled(false);
    }

    @Override
    public void hideLoading() {
        deepBackground.setVisibility(View.GONE);
        plainBackground.setVisibility(View.GONE);
        progressBar.hide();
        loginQQ.setEnabled(true);
        loginWeibo.setEnabled(true);
        loginWeixin.setEnabled(true);
        clickLogin.setEnabled(true);
        forgetPassword.setEnabled(true);
        registerPhone.setEnabled(true);
        account.setEnabled(true);
        password.setEnabled(true);
    }

    @Override
    public void toMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

    @Override
    public void toConfirmPhoneActivity() {
        Intent intent = new Intent(this, VerifyPhoneActivity.class);
        startActivity(intent);
    }

    @Override
    public void toForgetPasswordActivity() {
        Intent intent = new Intent(this, ForgetPasswordSendMessageActivity.class);
        startActivity(intent);
    }

    @Override
    public void initAccount(SharedPreferencesUtil preference) {
        account.setText(sp.readLoginInfo().get("UserName"));
    }

    @Override
    public void initPassword(SharedPreferencesUtil preference) {
        password.setText(sp.readLoginInfo().get("PassWd"));
    }

    @Override
    public void showSuccessTip() {

    }

    @Override
    public void showErrorTip(ErrorCode errorCode) {
        ShowErrorReason showErrorReason = new ShowErrorReason(mContext, mActivity);
        showErrorReason.show(errorCode);
    }

    @Override
    public void handleSendMessage(){
        mHandler.sendEmptyMessage(0x01);
    }

    @Override
    public void fixLoginPreferences(String username, String password) {
        new SharedPreferencesUtil(mContext).fixSuccessedLoginAccountAndPassword(username, password);
    }
}
