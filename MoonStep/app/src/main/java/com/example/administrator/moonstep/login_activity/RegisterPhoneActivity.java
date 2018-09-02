package com.example.administrator.moonstep.login_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.moonstep.R;

public class RegisterPhoneActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login_register_phone);

        initView();
    }

    public void initView(){
        Button phone_register = findViewById(R.id.click_register);
        Button return_login_page = findViewById(R.id.return_login_page);
        phone_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //先检查是否可以进行注册，如果注册成功，返回登录界面，进行登录
                if(check()){
                    jump_to_loginPage();
                }
            }
        });

        return_login_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump_to_loginPage();
            }
        });
    }

    public void jump_to_loginPage(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * check函数中判断是否可以注册成功
     * 主要是在验证码输入正确后，检测手机号是否已经存在于数据库中，若是存在于数据库中，那么返回false（注册失败）
     * 如果不存在于数据库中，将账号密码存入数据库中，并返回true
     */
    public Boolean check(){
        if(check_yanzhengma_is_true()){
            return true;
        }
        return false;
    }

    /**
     * 用来检测验证码是否正确，调用时机是在验证码输入完毕后
     */
    public Boolean check_yanzhengma_is_true(){
        return true;
    }
}
