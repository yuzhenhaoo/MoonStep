package priv.zxy.moonstep.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import priv.zxy.moonstep.R;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initView();
    }

    private void initView() {
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
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getUserPassword() {
        return null;
    }

    @Override
    public void clearUserName() {

    }

    @Override
    public void clearUserPassword() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void toMainActivity() {

    }

    @Override
    public void showFailedError(int code) {

    }
}
