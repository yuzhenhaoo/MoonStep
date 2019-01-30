package priv.zxy.moonstep.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.data.bean.BaseActivity;

/**
 * @author 张晓翼
 * @createTime 2019/1/29 0029
 * @Describe 登录页面
 */
public class LoginActivity extends BaseActivity {

    private ImageView moonImage;
    private Button logIn;
    private Button signUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initEvent();
    }

    private void initView() {
        moonImage = (ImageView) findViewById(R.id.moon_image);
        logIn = (Button) findViewById(R.id.log_in);
        signUp = (Button) findViewById(R.id.sign_up);
    }

    private void initEvent() {
        logIn.setOnClickListener(v -> toLoginActivity1());

        signUp.setOnClickListener(v -> toSignUpActivity());
    }

    private void toLoginActivity1() {
        Intent intent = new Intent(this, LoginActivity1.class);
        startActivity(intent);
    }

    private void toSignUpActivity() {
        Intent intent = new Intent(this, UserSignUpActivity.class);
        startActivity(intent);
    }
}
