package priv.zxy.moonstep.login_activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.Utils.LoginUtil;

public class PhoneLoginActivity extends AppCompatActivity {
    private EditText inputAccount;
    private EditText inputPassword;
    private String account;
    private String passwd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login_phone_login_page);
        initView();
    }

    public void initView(){
        Button click_login = findViewById(R.id.click_login);
        inputAccount = findViewById(R.id.account);
        inputPassword = findViewById(R.id.password);
        click_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
                checkAndOperateData();
            }
        });
    }

    private void getData(){
        account = inputAccount.getText().toString();
        passwd = inputPassword.getText().toString();
    }

    private void checkAndOperateData(){
        if(account != null && passwd != null){
            LoginUtil loginUtil = new LoginUtil(this.getApplicationContext(), this);
            loginUtil.LoginRequest(account, passwd);
        }else{
            Toast.makeText(this, "您的账户和密码不能为空哦！", Toast.LENGTH_SHORT).show();
        }
    }
}
