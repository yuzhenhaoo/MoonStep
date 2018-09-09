package priv.zxy.moonstep.login_activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import priv.zxy.moonstep.R;

public class RegisterEmail2 extends AppCompatActivity {

    private TextView header;
    private LinearLayout content1;
    private TextView email;
    private View emailLine;
    private RelativeLayout content2;
    private TextView code;
    private EditText codeNumber;
    private Button sendCode;
    private View passwordLine;
    private ImageView backButton;
    private Button submit;

    private String emailText;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_email2);
        initView();
        initData();
    }

    private void initView() {
        header = (TextView) findViewById(R.id.header);
        content1 = (LinearLayout) findViewById(R.id.content1);
        email = (TextView) findViewById(R.id.phone_number);
        emailLine = (View) findViewById(R.id.email_line);
        content2 = (RelativeLayout) findViewById(R.id.content2);
        code = (TextView) findViewById(R.id.code);
        codeNumber = (EditText) findViewById(R.id.code_number);
        sendCode = (Button) findViewById(R.id.send_code);
        passwordLine = (View) findViewById(R.id.password_line);
        backButton = (ImageView) findViewById(R.id.back_button);
        submit = (Button) findViewById(R.id.submit);
    }

    private void initData(){
        Intent intent = getIntent();
        emailText = intent.getStringExtra("Email");

        email.setText(emailText);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishThis();
            }
        });

        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //向相应的邮箱发送邮件
                sendCode.setEnabled(false);
                timer.start();//使用计时器 设置验证码的时间限制
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //验证成功的话，就进行跳转
                jumpToRegisterPage();
            }
        });
    }

    /**
     * 使用计时器来限定验证码
     * 在发送验证码的过程 不可以再次申请获取验证码 在指定时间之后没有获取到验证码才能重新进行发送
     * 这里限定的时间是60s
     */
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            sendCode.setText((millisUntilFinished / 1000) + "秒");
        }

        @Override
        public void onFinish() {
            sendCode.setEnabled(true);
            sendCode.setText("发送");
        }
    };

    private void jumpToRegisterPage(){
        Intent intent = new Intent(this, RegisterPage.class);
        startActivity(intent);
    }

    private void finishThis(){
        this.finish();
    }
}
