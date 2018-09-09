package priv.zxy.moonstep.login_activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import com.mob.MobSDK;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import priv.zxy.moonstep.R;

/**
 * RegisterPhone2用来调用Mob端口的无GUI接口
 * 实现短信的发送、检验
 * 并处理button按钮点击后60s内不能继续进行点击的事件
 */
public class RegisterPhone2 extends AppCompatActivity {


    @BindView(R.id.header)
    TextView header;
    @BindView(R.id.phone_number)
    TextView phoneNumber;
    @BindView(R.id.content1)
    LinearLayout content1;
    @BindView(R.id.phone_line)
    View phoneLine;
    @BindView(R.id.code)
    TextView code;
    @BindView(R.id.code_number)
    EditText codeNumber;
    @BindView(R.id.send_code)
    Button sendCode;
    @BindView(R.id.content2)
    RelativeLayout content2;
    @BindView(R.id.password_line)
    View passwordLine;
    @BindView(R.id.back_button)
    ImageView backButton;
    @BindView(R.id.submit)
    Button submit;
    private String phone;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.register_phone2);
        initSMSSDK();
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
                SMSSDK.getVerificationCode("86", phone);//发送短信验证码到手机号
                timer.start();//使用计时器 设置验证码的时间限制
            }
        });
        //必须要在满足条件的情况下才能做跳转(验证码发送正确)
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitInfo(phone);
            }
        });
    }

    private void initSMSSDK() {
        //初始化短信验证
        MobSDK.init(this);

        //注册短信回调
        SMSSDK.registerEventHandler(new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                switch (event) {
                    case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            Log.i("TAG","验证成功");
                            //验证成功的话我们就应该做相应的页面跳转了
                            jumpToTheRegisterPage();
                        } else {
                            Log.i("TAG","验证失败");
                        }
                        break;
                    case SMSSDK.EVENT_GET_VERIFICATION_CODE:
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            Log.i("TAG","获取验证成功");
                        } else {
                            Log.i("TAG","获取验证失败");
                        }
                        break;
                }
            }
        });
    }

    /**
     * 验证用户的其他信息
     * 这里验证两次密码是否一致 以及验证码判断
     */
    private void submitInfo(String phone) {
        //密码验证
        Log.i("TAG","提交按钮被点击了");
        String code = codeNumber.getText().toString().trim();
        SMSSDK.submitVerificationCode("86", phone, code);//提交验证码  在eventHandler里面查看验证结果
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
        SMSSDK.unregisterAllEventHandler();
    }
}
