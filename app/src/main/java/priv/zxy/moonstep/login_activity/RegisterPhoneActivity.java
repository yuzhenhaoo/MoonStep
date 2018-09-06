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

import butterknife.BindView;
import butterknife.ButterKnife;
import priv.zxy.moonstep.R;

public class RegisterPhoneActivity extends AppCompatActivity {

    @BindView(R.id.pho_num)
    EditText phoNum;
    @BindView(R.id.send_num)
    Button sendNum;
    @BindView(R.id.check_message)
    EditText checkMessage;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.password_check)
    EditText passwordCheck;
    @BindView(R.id.click_register)
    Button clickRegister;
    @BindView(R.id.return_login_page)
    Button returnLoginPage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login_register_phone);
        ButterKnife.bind(this);

        initView();
    }
    @SuppressLint("ClickableViewAccessibility")
    public void initView() {
        sendNum.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        sendNum.setTextSize(14);
                        break;
                    case MotionEvent.ACTION_UP:
                        sendNum.setTextSize(12);
                        break;
                }
                return true;
            }
        });
        clickRegister.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        clickRegister.setTextSize(16);
                        //这个地方需要检测注册的成功与否，并进行登陆



                        
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

    public void jump_to_loginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
