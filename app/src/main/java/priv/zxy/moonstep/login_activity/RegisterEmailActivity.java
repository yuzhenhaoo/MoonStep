package priv.zxy.moonstep.login_activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.Utils.EmailRegisterUtil;

/**
 * 这个类有问题，需要重写
 */
public class RegisterEmailActivity extends AppCompatActivity {

    private String emailNumber;
    private String accountNumber;
    private String passwordNumber;
    private String confirmPasswordNumber;
    private EditText inputMailBox;
    private EditText account;
    private EditText password;
    private EditText passwordCheck;
    private Button clickRegister;
    private Button returnLoginPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login_register_email);
        initData();
        initView();
    }

    private void getData() {
        emailNumber = inputMailBox.getText().toString();
        accountNumber = account.getText().toString();
        passwordNumber = password.getText().toString();
        confirmPasswordNumber = passwordCheck.getText().toString();
    }

    private void checkAndOperateData() {
        if (emailNumber == null || accountNumber == null
                || passwordNumber == null || confirmPasswordNumber == null) {
            Toast.makeText(this, "以上输入不能为空哦！请重新尝试", Toast.LENGTH_SHORT).show();
        } else {
            EmailRegisterUtil emailRegister = new EmailRegisterUtil(this.getApplicationContext(), this);
            emailRegister.RegisterRequest(emailNumber, accountNumber, "男", passwordNumber, confirmPasswordNumber);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void initData() {
        clickRegister.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        clickRegister.setTextSize(16);
                        //这里进行邮箱的注册
                        getData();
                        checkAndOperateData();
                        break;
                    case MotionEvent.ACTION_UP:
                        clickRegister.setTextSize(14);
                        break;
                }
                return true;
            }
        });

        returnLoginPage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        returnLoginPage.setTextSize(16);
                        jump_to_loginPage();
                        break;
                    case MotionEvent.ACTION_UP:
                        returnLoginPage.setTextSize(14);
                        break;
                }
                return true;
            }
        });
    }

    private void jump_to_loginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void initView() {
        inputMailBox = (EditText) findViewById(R.id.input_mailBox);
        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        passwordCheck = (EditText) findViewById(R.id.password_check);
        clickRegister = (Button) findViewById(R.id.click_register);
        returnLoginPage = (Button) findViewById(R.id.return_login_page);
    }
}
