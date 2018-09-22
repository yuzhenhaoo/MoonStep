package priv.zxy.moonstep.login.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mob.MobSDK;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.Utils.SharedPreferencesUtil;
import priv.zxy.moonstep.login.presenter.UserLoginPresenter;
import priv.zxy.moonstep.main_page.MainActivity;

public class UserLoginActivity extends AppCompatActivity implements IUserLoginView {

    private LinearLayout header;
    private Button loginWeixin;
    private Button loginQQ;
    private Button loginWeibo;
    private LinearLayout loginContent;
    private EditText account;
    private EditText password;
    private Button clickLogin;
    private Button forgetPassword;
    private View bottomLine;
    private LinearLayout bottom;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        //MobSDK的初始化
        MobSDK.init(this);
        header = (LinearLayout) findViewById(R.id.header);
        loginWeixin = (Button) findViewById(R.id.login_weixin);
        loginQQ = (Button) findViewById(R.id.login_QQ);
        loginWeibo = (Button) findViewById(R.id.login_weibo);
        loginContent = (LinearLayout) findViewById(R.id.login_content);
        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        clickLogin = (Button) findViewById(R.id.click_login);
        forgetPassword = (Button) findViewById(R.id.forget_password);
        bottomLine = (View) findViewById(R.id.bottom_line);
        bottom = (LinearLayout) findViewById(R.id.bottom);
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

        userLoginPresenter.hideLoading();

        clickLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLogin.startAnimation(shake);
                userLoginPresenter.showLoading();
                userLoginPresenter.login();
            }
        });

        registerPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPhone.startAnimation(shake);
                userLoginPresenter.toConfirmPhoneActivity();
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPassword.setAnimation(shake);
                userLoginPresenter.toForgetPasswordActivity();
            }
        });
    }

    @Override
    public String getUserName() {
        return account.getText().toString();
    }

    @Override
    public String getUserPassword() {
        return password.getText().toString();
    }

    @Override
    public void clearUserName() {
        account.setText("");
    }

    @Override
    public void clearUserPassword() {
        password.setText("");
    }

    @Override
    public void showLoading() {
        progressBar.show();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        deepBackground.setVisibility(View.VISIBLE);
        plainBackground.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        deepBackground.setVisibility(View.GONE);
        plainBackground.setVisibility(View.GONE);
        progressBar.hide();
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
        Intent intent = new Intent(this, ForgetPasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public void showFailedError(int code) {

    }

    @Override
    public void initAccount(SharedPreferencesUtil preference) {
        account.setText(sp.readLoginInfo().get("UserName"));
    }

    @Override
    public void initPassword(SharedPreferencesUtil preference) {
        password.setText(sp.readLoginInfo().get("PassWd"));
    }
}
