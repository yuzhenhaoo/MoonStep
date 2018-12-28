package priv.zxy.moonstep.login.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.data.bean.BaseActivity;
import priv.zxy.moonstep.util.ShowErrorReasonUtil;
import priv.zxy.moonstep.util.ToastUtil;
import priv.zxy.moonstep.data.bean.ErrorCodeEnum;
import priv.zxy.moonstep.login.presenter.UserVerifyPhoneNumberPresenter;

/**
 * 创建人: Administrator
 * 创建时间: 2018/09/23
 * 描述: 手机验证页面
 **/

public class VerifyPhoneActivity extends BaseActivity implements IVerifyPhoneView {

    private TextView inputText;
    private LinearLayout content1;
    private TextView countryChoice;
    private View countryLine;
    private LinearLayout content2;
    private TextView phoneHeader;
    private EditText phoneNumber;
    private View phoneLine;
    private Button submit;
    private ImageView backButton;
    private View deepBackground;
    private View plainBackground;
    private ContentLoadingProgressBar progressBar;
    private Context mContext;
    private Activity mActivity;
    private String phoneNum;

    //创建一个桥接对象，通过Presenter完成和Module层的交互
    private UserVerifyPhoneNumberPresenter userVerifyPhoneNumberPresenter;
    //加载动画资源
    private Animation shake;

    private MyHandler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_phonenumber_activity);
        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        inputText = (TextView) findViewById(R.id.input_text);
        content1 = (LinearLayout) findViewById(R.id.content1);
        countryChoice = (TextView) findViewById(R.id.countryChoice);
        countryLine = (View) findViewById(R.id.country_line);
        content2 = (LinearLayout) findViewById(R.id.content2);
        phoneHeader = (TextView) findViewById(R.id.phone_header);
        phoneNumber = (EditText) findViewById(R.id.phone_number);
        phoneLine = (View) findViewById(R.id.phone_line);
        submit = (Button) findViewById(R.id.submit);
        backButton = (ImageView) findViewById(R.id.back_button);
        deepBackground = (View) findViewById(R.id.deepBackground);
        plainBackground = (View) findViewById(R.id.plainBackground);
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
        mContext = this.getApplicationContext();
        mActivity = this;
        mHandler = new MyHandler(this);

        hideLoading();

        shake = AnimationUtils.loadAnimation(this, R.anim.shake);

        userVerifyPhoneNumberPresenter = new UserVerifyPhoneNumberPresenter(this, mActivity, mContext);

        // 回退到LogUtilinActivity
        backButton.setOnClickListener(v -> finishActivitySelf());

        countryChoice.setOnClickListener(v -> {
            //这里使用左滑动画效果的一个包含国家列表的Activity
            userVerifyPhoneNumberPresenter.toCountrySelectedActivity();
        });

        submit.setOnTouchListener((v, event) -> {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    submit.setAnimation(shake);
                    showLoading();
                    break;
                case MotionEvent.ACTION_UP:
                    new Thread(() -> {
                        try {
                            userVerifyPhoneNumberPresenter.doVerifyPhoneNumber();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mHandler.sendEmptyMessage(0x01);
                    }).start();
                    break;
                default:
                    break;
            }
            return true;
        });
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
    public String getPhoneNumber() {
        return phoneNum = phoneNumber.getText().toString();
    }

    @Override
    public void toLogUtilinActivity() {
        Intent intent = new Intent(this, UserLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void toSendMessageActivity() {
        Bundle bundle = new Bundle();
        bundle.putString("phoneNumber", phoneNum);
        Intent intent = new Intent(mActivity, SendMessageActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void finishActivitySelf() {
        this.finish();
    }

    @Override
    public void showSuccessTip() {
        ToastUtil.getInstance(mContext, mActivity).showToast("已经向您的手机发送验证信息，注意查收！");
    }

    @Override
    public void showFailTip(ErrorCodeEnum errorCode) {
        ShowErrorReasonUtil.getInstance(mActivity).show(errorCode);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideLoading();
    }

    private static class MyHandler extends Handler{
        private WeakReference<VerifyPhoneActivity> weakReference;

        MyHandler(VerifyPhoneActivity activity){
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            VerifyPhoneActivity activity = weakReference.get();
            activity.hideLoading();
        }
    }
}
