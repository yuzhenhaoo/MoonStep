package priv.zxy.moonstep.login_activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.Utils.ToastUtil;

import static cn.smssdk.SMSSDK.getVoiceVerifyCode;

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
    private RelativeLayout content2;
    private TextView code;
    private EditText codeNumber;
    private Button sendCode;
    private ImageView backButton;
    private Button submit;
    private TextView voice_code;
    private String country = "86";

    private Context mContext;
    private Activity mActivity;
    private View phoneLine;
    private View passwordLine;
    private LinearLayout voiceMode;
    private TextView voiceCode;
    private View deepBackground;
    private View plainBackground;
    private ContentLoadingProgressBar progressBar;

    EventHandler eventHandler = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            mHandler.sendMessage(msg);
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 0x01:
                    progressBar.hide();
                    deepBackground.setVisibility(View.GONE);
                    plainBackground.setVisibility(View.GONE);
                    break;
            }
        }
    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.currentThread().sleep(1200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.obtainMessage(0x01).sendToTarget();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_phone2);
        SMSSDK.registerEventHandler(eventHandler);
        initView();
        initData();
    }

    private void initView() {
        //对SMSSDK进行注册，与unregisterEventHandler配套使用
        SMSSDK.registerEventHandler(eventHandler);
        header = (TextView) findViewById(R.id.header);
        content1 = (LinearLayout) findViewById(R.id.content1);
        phoneNumber = (TextView) findViewById(R.id.phone_number);
        content2 = (RelativeLayout) findViewById(R.id.content2);
        code = (TextView) findViewById(R.id.code);
        codeNumber = (EditText) findViewById(R.id.code_number);
        sendCode = (Button) findViewById(R.id.send_code);
        backButton = (ImageView) findViewById(R.id.back_button);
        submit = (Button) findViewById(R.id.submit);
        voice_code = (TextView) findViewById(R.id.voice_code);

        mContext = this.getApplicationContext();

        mActivity = this;
        phoneLine = (View) findViewById(R.id.phone_line);
        passwordLine = (View) findViewById(R.id.password_line);
        voiceMode = (LinearLayout) findViewById(R.id.voice_mode);
        voiceCode = (TextView) findViewById(R.id.voice_code);
        deepBackground = (View) findViewById(R.id.deepBackground);
        plainBackground = (View) findViewById(R.id.plainBackground);
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
    }

    @SuppressLint("ClickableViewAccessibility")
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
        sendCode.performClick();//模拟点击

        //必须要在满足条件的情况下才能做跳转(验证码发送正确)
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitInfo(country, phone);
                try {
                    refreshPage();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new Thread(runnable).start();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishThis();
            }
        });

        //对于语音发送案件的监听：
        voice_code.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        voice_code.setTextSize(15);
                        getVoiceVerifyCode(country, phone);
                        ToastUtil toastUtil = new ToastUtil(mContext, mActivity);
                        toastUtil.showToast("正在向您的手机发送语音信息，请注意接收");
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        voice_code.setTextSize(15);
                        break;
                }
                return true;
            }
        });

    }

    /**
     * 验证用户的其他信息
     * 这里验证两次密码是否一致 以及验证码判断
     */
    private void submitInfo(String country, String phone) {
        String code = codeNumber.getText().toString().trim();
        if (code.equals("")) {
            ToastUtil toastUtil = new ToastUtil(this.getApplicationContext(), this);
            toastUtil.showToast("验证码不能为空，请重新尝试!");
        } else {
            SMSSDK.submitVerificationCode(country, phone, code);//提交验证码  在eventHandler里面查看验证结果
        }
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

    //这里需要跳转到最终的注册页面
    private void jumpToTheRegisterPage() {
        Intent intent = new Intent(this, RegisterPage.class);
        intent.putExtra("phoneNumber", phone);
        startActivity(intent);
    }

    /**
     * 刷新页面
     */
    private void refreshPage() throws InterruptedException {
        progressBar.show();
        Thread.sleep(200);
        deepBackground.setVisibility(View.VISIBLE);
        plainBackground.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止使用短信验证 产生内存溢出问题
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {

            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event=" + event);
            if (result == SMSSDK.RESULT_COMPLETE) {
                System.out.println("--------result" + event);
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
                    //提交验证码成功
                    ToastUtil toastUtil = new ToastUtil(mContext, mActivity);
                    toastUtil.showToast("提交验证码成功");
//                    //页面跳转
                    jumpToTheRegisterPage();
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //已经验证
                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
                } else {
                    ToastUtil toastUtil = new ToastUtil(mContext, mActivity);
                    toastUtil.showToast("验证码错误(猜测您点了语音验证码，以最后得到的验证码为准)");
                }
            } else {
                ((Throwable) data).printStackTrace();
            }


        }
    };

    private void finishThis() {
        this.finish();
    }
}
