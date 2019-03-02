package priv.zxy.moonstep.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.data.bean.BaseActivity;

/**
 * @author 张晓翼
 * @createTime 2019/1/29 0029
 * @Describe 登录页面
 */
public class LoginSurface extends BaseActivity {

    private ImageView moonImage;
    private Button login;
    private Button signup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_login);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        moonImage = (ImageView) findViewById(R.id.moon_image);
        login = (Button) findViewById(R.id.log_in);
        signup = (Button) findViewById(R.id.sign_up);
    }

    @Override
    protected void initEvent() {
        login.setOnClickListener(v -> toLoginActivity());
        signup.setOnClickListener(v -> toSignUpActivity());
    }

    /**
     * 登陆页面
     */
    private void toLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * 注册页面
     */
    private void toSignUpActivity() {
        Intent intent = new Intent(this, UserSignUpActivity.class);
        startActivity(intent);
    }
}
