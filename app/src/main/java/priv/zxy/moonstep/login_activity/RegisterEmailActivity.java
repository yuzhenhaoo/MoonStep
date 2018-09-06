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

import butterknife.BindView;
import butterknife.ButterKnife;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.Utils.EmailRegisterUtil;

public class RegisterEmailActivity extends AppCompatActivity {

    @BindView(R.id.input_mailBox)
    EditText mailBox;
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.password_check)
    EditText passwordCheck;
    @BindView(R.id.click_register)
    Button clickRegister;
    @BindView(R.id.return_login_page)
    Button returnLoginPage;

    private String emailNumber;
    private String accountNumber;
    private String passwordNumber;
    private String confirmPasswordNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login_register_email);
        ButterKnife.bind(this);
        initData();
    }

    private void getData(){
        emailNumber = mailBox.getText().toString();
        accountNumber = account.getText().toString();
        passwordNumber = password.getText().toString();
        confirmPasswordNumber = passwordCheck.getText().toString();
    }

    private void checkAndOperateData(){
        if(emailNumber == null || accountNumber == null
                || passwordNumber == null || confirmPasswordNumber == null){
            Toast.makeText(this, "以上输入不能为空哦！请重新尝试", Toast.LENGTH_SHORT).show();
        }else{
            EmailRegisterUtil emailRegister = new EmailRegisterUtil(this.getApplicationContext(), this);
            emailRegister.RegisterRequest(emailNumber, accountNumber, passwordNumber, confirmPasswordNumber);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void initData() {
        clickRegister.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
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
                switch (motionEvent.getAction()){
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


}
