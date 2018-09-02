package com.example.administrator.moonstep.login_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.moonstep.R;

public class LoginActivity extends AppCompatActivity{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login_page);

        initView();
    }

    public void initView(){
        Button register_phone = findViewById(R.id.register_phone);
        final Button register_email = findViewById(R.id.register_email);
        Button login_phone = findViewById(R.id.login_phone);
        Button login_email = findViewById(R.id.login_email);
        final Button forget_password = findViewById(R.id.forget_password);
        register_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump_to_register_phone_page();
            }
        });
        login_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump_to_login_phone_page();
            }
        });
        login_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump_to_login_email_page();
            }
        });
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump_to_forget_pwd_page();
            }
        });
        register_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump_to_register_email_page();
            }
        });
    }

    /**
     * 点击进入手机注册页面
     */
    public void jump_to_register_phone_page(){
        Intent intent = new Intent(this, RegisterPhoneActivity.class);
        startActivity(intent);
    }

    /**
     * 点击进入手机登录页面
     */
    public void jump_to_login_phone_page(){
        Intent intent = new Intent(this, PhoneLoginActivity.class);
        startActivity(intent);
    }

    /**
     * 点击进入邮箱登录页面
     */
    public void jump_to_login_email_page(){
        Intent intent = new Intent(this, EmailLoginActivity.class);
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

}
