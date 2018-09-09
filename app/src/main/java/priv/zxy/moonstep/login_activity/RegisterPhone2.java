package priv.zxy.moonstep.login_activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.MobSDK;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import priv.zxy.moonstep.R;

/**
 * RegisterPhone2用来调用Mob端口的无GUI接口
 * 实现短信的发送、检验
 * 并处理button按钮点击后60s内不能继续进行点击的事件
 */
public class RegisterPhone2 extends AppCompatActivity {

    private String phone;
    private TextView header;
    private LinearLayout content1;
    private TextView phoneNumber;
    private View phoneLine;
    private RelativeLayout content2;
    private TextView code;
    private EditText codeNumber;
    private Button sendCode;
    private View passwordLine;
    private ImageView backButton;
    private Button submit;
    private String country = "86";

    EventHandler eventHandler=new EventHandler(){

        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    Toast.makeText(RegisterPhone2.this, "提交验证码成功", Toast.LENGTH_SHORT).show();

                    //页面跳转
                    jumpToTheRegisterPage();
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //获取验证码成功
                    Toast.makeText(RegisterPhone2.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    //返回支持发送验证码的国家列表
                    Toast.makeText(RegisterPhone2.this, "返回支持发送验证码的国家列表", Toast.LENGTH_SHORT).show();
                }
            }else{
                ((Throwable)data).printStackTrace();
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_phone2);
        initView();
        initData();
    }

    private void initData() {
        //获取RegisterPhone1中传递的电话号码
        Intent intent = getIntent();
        phone = intent.getStringExtra("phoneNumber");

        //设置ResgisterPhone2的电话号码
        phoneNumber.setText(phone);

        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SMSSDK.getVerificationCode(country, phone);//发送短信验证码到手机号
                sendCode.setEnabled(false);
                timer.start();//使用计时器 设置验证码的时间限制
            }
        });
        //必须要在满足条件的情况下才能做跳转(验证码发送正确)
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitInfo(country, phone);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishThis();
            }
        });
    }

    /**
     * 验证用户的其他信息
     * 这里验证两次密码是否一致 以及验证码判断
     */
    private void submitInfo(String country, String phone) {
        //密码验证
        Log.i("TAG", "提交按钮被点击了");
        String code = codeNumber.getText().toString().trim();
        SMSSDK.submitVerificationCode(country, phone, code);//提交验证码  在eventHandler里面查看验证结果
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
            sendCode.setText("获取验证码");
        }
    };

    //这里需要跳转到最终的注册页面
    private void jumpToTheRegisterPage() {
        Intent intent = new Intent(this, RegisterPage.class);
        intent.putExtra("phoneNumber", phone);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止使用短信验证 产生内存溢出问题
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    private void initView() {
        MobSDK.init(this);

        //对SMSSDK进行注册，与unregisterEventHandler配套使用
        SMSSDK.registerEventHandler(eventHandler);
        header = (TextView) findViewById(R.id.header);
        content1 = (LinearLayout) findViewById(R.id.content1);
        phoneNumber = (TextView) findViewById(R.id.phone_number);
        phoneLine = (View) findViewById(R.id.phone_line);
        content2 = (RelativeLayout) findViewById(R.id.content2);
        code = (TextView) findViewById(R.id.code);
        codeNumber = (EditText) findViewById(R.id.code_number);
        sendCode = (Button) findViewById(R.id.send_code);
        passwordLine = (View) findViewById(R.id.password_line);
        backButton = (ImageView) findViewById(R.id.back_button);
        submit = (Button) findViewById(R.id.submit);
    }

    private void finishThis(){
        this.finish();
    }
}
