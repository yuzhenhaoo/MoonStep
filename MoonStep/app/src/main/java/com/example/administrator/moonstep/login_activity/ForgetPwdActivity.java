package com.example.administrator.moonstep.login_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.administrator.moonstep.R;
import com.nightonke.jellytogglebutton.JellyToggleButton;

public class ForgetPwdActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login_forget_pwd_page_);
        initView();
    }

    public void initView(){
//        final Switch button = findViewById(R.id.phone_or_email);
        final JellyToggleButton button = findViewById(R.id.jellytogglebt);
        final TextView phone_num = findViewById(R.id.pho_num);
        final TextView title_name = findViewById(R.id.title_name);
        Button click_check = findViewById(R.id.click_check);
        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //如果switch按钮为true的话，就要开始向邮箱发送验证码
                    title_name.setText("邮箱账号");
                    phone_num.setHint("输入邮箱");
                    Email_Listener();
                }else{
                    title_name.setText("手机账号");
                    phone_num.setHint("输入手机号");
                    Phone_Listener();
                }
            }
        });

        click_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump_to_register_login_page();
            }
        });
    }

    /**
     * 当选中邮箱界面的时候向邮箱发送验证码
     */
    public void Email_Listener(){
        Button bt = findViewById(R.id.send_num);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //这里就是向邮箱发送验证码
            }
        });
    }

    /**
     * 当选中手机界面的时候向手机发送短信
     */
    public void Phone_Listener(){
        Button bt = findViewById(R.id.send_num);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //这里就是向手机发送验证码
            }
        });
    }

    /**
     * 点击进入手机注册页面
     */
    public void jump_to_register_login_page(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}