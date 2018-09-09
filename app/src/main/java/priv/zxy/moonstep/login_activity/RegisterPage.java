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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.Utils.PhoneRegisterUtil;

public class RegisterPage extends AppCompatActivity {


    @BindView(R.id.check_username)
    Button checkUsername;
    @BindView(R.id.accountName)
    EditText accountName;
    @BindView(R.id.man)
    RadioButton man;
    @BindView(R.id.woman)
    RadioButton woman;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.password_check)
    EditText passwordCheck;
    @BindView(R.id.click_register)
    Button clickRegister;
    @BindView(R.id.return_login_page)
    Button returnLoginPage;
    @BindView(R.id.back_button)
    ImageView backButton;

    private String phoneNumber;
    private String userName;
    private String userGender;
    private String userPassword;
    private String confirmPassword;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login_register_page);
        ButterKnife.bind(this);

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

    private void getData(){
        userName = accountName.getText().toString();
        userPassword = password.getText().toString();
        confirmPassword = passwordCheck.getText().toString();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){
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

    private void checkAndOpeateData(){
        PhoneRegisterUtil prUtil = new PhoneRegisterUtil(this.getApplicationContext(), this);
        prUtil.RegisterRequest(phoneNumber,userName,userGender,userPassword,confirmPassword);
    }

    private void FinistThisActivity() {
        this.finish();
    }

}
