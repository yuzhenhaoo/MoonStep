package priv.zxy.moonstep.login_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.main_page.MainActivity;

public class PhoneLoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login_phone_login_page);
        initView();
    }

    public void initView(){
        Button click_login = findViewById(R.id.click_login);
        click_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump_to_mainPage();
            }
        });
    }

    /**
     * 跳转到主页，将之前所有的activity全部清空
     */
    public void jump_to_mainPage(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
