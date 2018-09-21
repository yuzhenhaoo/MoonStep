package priv.zxy.moonstep.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import priv.zxy.moonstep.R;

public class ForgetPasswordActivity extends AppCompatActivity implements IForgetPasswordView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login_forget_pwd_page_);
    }
}
