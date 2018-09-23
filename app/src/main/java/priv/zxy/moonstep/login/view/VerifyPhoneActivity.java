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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.login.presenter.UserVerifyPhoneNumberPresenter;
import priv.zxy.moonstep.login_activity.RegisterPhone2;

/**
 *  Created by Zxy on 2018/9/23
 */

public class VerifyPhoneActivity extends AppCompatActivity implements IVerifyPhoneView {

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

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            hideLoading();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_phone1);
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

        hideLoading();

        userVerifyPhoneNumberPresenter = new UserVerifyPhoneNumberPresenter(this, mActivity, mContext);

        // 回退到LoginActivity
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivitySelf();
            }
        });

        countryChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里使用左滑动画效果的一个包含国家列表的Activity
                userVerifyPhoneNumberPresenter.toCountrySelectedActivity();
            }
        });

        submit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        submit.setAnimation(shake);
                        showLoading();
                        break;
                    case MotionEvent.ACTION_UP:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    userVerifyPhoneNumberPresenter.doVerifyPhoneNumber();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                mHandler.sendEmptyMessage(0x01);
                            }
                        }).start();
                        break;
                }
                return true;
            }
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
    public void toLoginActivity() {
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
    protected void onResume() {
        super.onResume();
        hideLoading();
    }
}
