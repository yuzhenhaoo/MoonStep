package priv.zxy.moonstep.login.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import priv.zxy.moonstep.kernel.BaseActivity;
import priv.zxy.moonstep.utils.ShowErrorReason;
import priv.zxy.moonstep.utils.ToastUtil;
import priv.zxy.moonstep.kernel.bean.ErrorCode;
import priv.zxy.moonstep.login.presenter.UserForgetPasswordSendMessagePresenter;

import static cn.smssdk.SMSSDK.getVoiceVerifyCode;

/**
 * Created by Zxy on 2018/9/20
 */

public class ForgetPasswordSendMessageActivity extends BaseActivity implements IForgetPasswordSendMessageView{
    private TextView header;
    private LinearLayout content1;
    private EditText phoneNumber;
    private View phoneLine;
    private RelativeLayout content2;
    private TextView code;
    private EditText codeNumber;
    private Button sendCode;
    private View passwordLine;
    private ImageView backButton;
    private LinearLayout voiceMode;
    private TextView voiceCode;
    private Button submit;
    private View deepBackground;
    private View plainBackground;
    private ContentLoadingProgressBar progressBar;
    private Context mContext;
    private Activity mActivity;
    private UserForgetPasswordSendMessagePresenter userForgetPasswordSendMessagePresenter;
    private ToastUtil toastUtil;
    private String phoneNum;
    private String country="86";
    //加载动画资源文件
    Animation shake;

    //Mob短信端的事件触发机制
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
            hideLoading();
        }
    };

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
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
                    //页面跳转
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                userForgetPasswordSendMessagePresenter.toChangePasswordActivity(phoneNumber.getText().toString(), mContext, mActivity);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            handler.sendEmptyMessage(0x01);
                        }
                    }).start();
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //已经验证
                    toastUtil.showToast("验证码已经发送");
                } else {
                    toastUtil.showToast("验证码错误(猜测您点了语音验证码，以最后得到的验证码为准)");
                }
            } else {
                ((Throwable) data).printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_send_message_activity);
        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        //对SMSSDK进行注册，与unregisterEventHandler配套使用
        SMSSDK.registerEventHandler(eventHandler);
        header = (TextView) findViewById(R.id.header);
        content1 = (LinearLayout) findViewById(R.id.content1);
        phoneNumber = (EditText) findViewById(R.id.phone_number);
        phoneLine = (View) findViewById(R.id.phone_line);
        content2 = (RelativeLayout) findViewById(R.id.content2);
        code = (TextView) findViewById(R.id.code);
        codeNumber = (EditText) findViewById(R.id.code_number);
        sendCode = (Button) findViewById(R.id.send_code);
        passwordLine = (View) findViewById(R.id.password_line);
        backButton = (ImageView) findViewById(R.id.back_button);
        voiceMode = (LinearLayout) findViewById(R.id.voice_mode);
        voiceCode = (TextView) findViewById(R.id.voice_code);
        submit = (Button) findViewById(R.id.submit);
        deepBackground = (View) findViewById(R.id.deepBackground);
        plainBackground = (View) findViewById(R.id.plainBackground);
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);

        mContext = this.getApplicationContext();
        mActivity = this;
        toastUtil = ToastUtil.getInstance(mContext, mActivity);

        userForgetPasswordSendMessagePresenter = new UserForgetPasswordSendMessagePresenter(this, mActivity, mContext);
        shake = AnimationUtils.loadAnimation(this, R.anim.shake);

        hideLoading();

        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getPhoneNumber().equals("")) toastUtil.showToast("您的手机号不能为空");
                else{
                    SMSSDK.getVerificationCode(country, getPhoneNumber());//发送短信验证码到手机号
                    sendCode.setEnabled(false);
                    timer.start();//使用计时器 设置验证码的时间限制
                }
            }
        });

        //必须要在满足条件的情况下才能做跳转(验证码发送正确)
        submit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        submit.setAnimation(shake);
                        showLoading();
                        break;
                    case MotionEvent.ACTION_UP:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Looper.prepare();
                                try {
                                    String codeNum = codeNumber.getText().toString();
                                    phoneNum = getPhoneNumber();
                                    userForgetPasswordSendMessagePresenter.submitInfo(country,phoneNum, codeNum, mContext, mActivity);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                handler.sendEmptyMessage(0x01);
                                Looper.loop();
                            }
                        }).start();
                        break;
                }
                return true;
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCode.setAnimation(shake);
                finishActivitySelf();
            }
        });

        //对于语音验证的事件监听
        voiceCode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        submit.setAnimation(shake);
                        break;
                    case MotionEvent.ACTION_UP:
                        sendVoiceCode();
                        break;
                }
                return true;
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

    @Override
    public void toChangePasswordActivity() {
        Intent intent = new Intent(this, UserChangePasswordActivity.class);
        intent.putExtra("phoneNumber", phoneNum);//要将电话号码一起传过去，减小安全问题
        startActivity(intent);
    }

    @Override
    public void finishActivitySelf() {
        this.finish();
    }

    @Override
    public void sendVoiceCode() {
        getVoiceVerifyCode(country, phoneNum);
        toastUtil.showToast("正在向您的手机发送语音信息，请注意接收");
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber.getText().toString();
    }

    @Override
    public void showLoading() {
        progressBar.show();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        deepBackground.setVisibility(View.VISIBLE);
        plainBackground.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        deepBackground.setVisibility(View.GONE);
        plainBackground.setVisibility(View.GONE);
        progressBar.hide();
    }

    @Override
    public void showSuccessTip() {

    }

    @Override
    public void showErrorTip() {
        new ShowErrorReason(mContext, mActivity).show(ErrorCode.PhoneNumberIsNotRegistered);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止使用短信验证 产生内存溢出问题
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
