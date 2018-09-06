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

import com.mob.MobSDK;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.Utils.LoginUtil;
import priv.zxy.moonstep.main_page.MainActivity;

public class LoginActivity extends AppCompatActivity{

    private Button login;
    Button register_phone;
    Button register_email;
    Button forget_password;
    private EditText inputAccount;
    private EditText inputPassword;
    private String account;
    private String passwd;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login_page);

        initView();
        jumpPage();
    }

    public void initView(){
        login = findViewById(R.id.click_login);
        register_phone = findViewById(R.id.register_phone);
        register_email = findViewById(R.id.register_email);
        forget_password = findViewById(R.id.forget_password);
        inputAccount = findViewById(R.id.account);
        inputPassword = findViewById(R.id.password);

        //MobSDK的初始化
        MobSDK.init(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void jumpPage(){
        register_phone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        register_phone.setTextSize(24);
//                        jump_to_register_phone_page();

                        //调用Mob官方的GUI进行手机界面的注册
                        mobGUI();
                        break;
                    case MotionEvent.ACTION_UP:
                        register_phone.setTextSize(20);
                        break;
                }
                return true;
            }
        });

        forget_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        forget_password.setTextSize(22);
                        jump_to_forget_pwd_page();
                        break;
                    case MotionEvent.ACTION_UP:
                        forget_password.setTextSize(20);
                        break;
                }
                return true;
            }
        });

        register_email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        register_email.setTextSize(22);
                        jump_to_register_email_page();
                        break;
                    case MotionEvent.ACTION_UP:
                        register_email.setTextSize(20);
                        break;
                }
                return true;
            }
        });

        login.setOnTouchListener(new View.OnTouchListener() {
            //返回值改为true，否则在执行了DOWN以后不再执行UP和MOVE操作。
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        getData();
                        checkAndOperateData();
                        login.setTextSize(24);
                        break;
                    case MotionEvent.ACTION_UP:
                        login.setTextSize(20);
                        break;
                }
                return true;
            }
        });
    }

    private void mobGUI(){
        RegisterPage registerPage = new RegisterPage();
        //回调函数
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    Toast.makeText(LoginActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
                    jump_to_register_phone_page();
                }else{
                    Toast.makeText(LoginActivity.this, "服务器故障，稍后重试", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerPage.show(LoginActivity.this);
    }
    /**
     * 点击进入手机注册页面
     */
    public void jump_to_register_phone_page(){
        Intent intent = new Intent(this, RegisterPhoneActivity.class);
        startActivity(intent);
    }

    /**
     * 点击进入忘记密码页面
     */
    public void jump_to_forget_pwd_page(){
        Intent intent = new Intent(this, ForgetPwdActivity.class);
        startActivity(intent);
    }

    /**
     * 点击进入邮箱注册页面
     */
    public void jump_to_register_email_page(){
        Intent intent = new Intent(this, RegisterEmailActivity.class);
        startActivity(intent);
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
