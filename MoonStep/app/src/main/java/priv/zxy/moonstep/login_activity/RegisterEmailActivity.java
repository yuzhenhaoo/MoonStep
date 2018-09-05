package priv.zxy.moonstep.login_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import priv.zxy.moonstep.R;

public class RegisterEmailActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login_register_email);
        initView();
    }

    public void initView(){
        Button registr_button = findViewById(R.id.click_register);
        Button return_login = findViewById(R.id.return_login_page);
        Button send_message = findViewById(R.id.send_num);
        registr_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check()){
                    jump_to_loginPage();
                }
                else{
                    Toast.makeText(RegisterEmailActivity.this, "注册失败，请重新尝试", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump_to_loginPage();
            }
        });

        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_yanzhengma();
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
     * 用来检验是否可以注册成功，成功的话就返回true,否则就返回false
     * @return
     */
    public Boolean check(){
        //
        return true;
    }

    public void send_yanzhengma(){
        //这里用来发送验证码，并显示是否正确
    }

}
