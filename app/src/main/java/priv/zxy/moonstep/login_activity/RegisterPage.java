package priv.zxy.moonstep.login_activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.Utils.PhoneRegisterUtil;
import priv.zxy.moonstep.Utils.UserNameCheckUtil;

public class RegisterPage extends AppCompatActivity {


    private String phoneNumber;
    private String userName;
    private String userGender;
    private String userPassword;
    private String confirmPassword;
    private Button checkUsername;
    private EditText accountName;
    private RadioGroup radioGroup;
    private EditText password;
    private EditText passwordCheck;
    private Button clickRegister;
    private Button returnLoginPage;
    private ImageView backButton;
    private Context mContext;
    private Activity mActivity;
    private Boolean userNameCheckResult = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login_register_page);
        initView();
        initData();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initData() {
        //得到RegisterPhone2传递过来的phone参数，要进行电话的注册
        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");


        checkUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //检测用户名是否已经存在于数据库中，并跳出弹窗对用户进行提示
                UserNameCheckUtil userNameCheckUtil = new UserNameCheckUtil(mContext, mActivity);
                userNameCheckUtil.UserNameCheck(userName);
                if(userNameCheckUtil.checkResult){
                    userNameCheckUtil.SuccessTip();//只有成功的时候才跳出弹窗
                }
            }
        });

        clickRegister.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        clickRegister.setTextSize(16);

                        getData();

                        //进行数据的检查并进行注册
                        checkAndOpeateData();
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
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        returnLoginPage.setTextSize(16);
                        break;
                    case MotionEvent.ACTION_UP:
                        returnLoginPage.setTextSize(14);
                        break;
                }
                return true;
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //回退键
                FinistThisActivity();
            }
        });
    }

    private void getData() {
        userName = accountName.getText().toString();
        userPassword = password.getText().toString();
        confirmPassword = passwordCheck.getText().toString();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.man:
                        userGender = "男";
                        break;
                    case R.id.woman:
                        userGender = "女";
                        break;
                }
            }
        });
    }

    private void checkAndOpeateData() {
        UserNameCheckUtil userNameCheckUtil = new UserNameCheckUtil(mContext, mActivity);
        userNameCheckUtil.UserNameCheck(userName);

        if(userNameCheckUtil.checkResult){ // 在点击提交按钮的时候，我们只检测用户名是否不符合要求，并有相应的弹窗，如果用户名符合要求，就不弹窗
            PhoneRegisterUtil prUtil = new PhoneRegisterUtil(this.getApplicationContext(), this);
            prUtil.RegisterRequest(phoneNumber, userName, userGender, userPassword, confirmPassword);
        }
    }

    private void FinistThisActivity() {
        this.finish();
    }

    private void initView() {
        checkUsername = (Button) findViewById(R.id.check_username);
        accountName = (EditText) findViewById(R.id.accountName);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        password = (EditText) findViewById(R.id.password);
        passwordCheck = (EditText) findViewById(R.id.password_check);
        clickRegister = (Button) findViewById(R.id.click_register);
        returnLoginPage = (Button) findViewById(R.id.return_login_page);
        backButton = (ImageView) findViewById(R.id.back_button);

        mContext = this.getApplicationContext();
        mActivity = this;
    }
}
