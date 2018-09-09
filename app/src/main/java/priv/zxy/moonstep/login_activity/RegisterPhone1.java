package priv.zxy.moonstep.login_activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.Utils.PhoneCheckUtil;

public class RegisterPhone1 extends AppCompatActivity {


    private String phoneNumberText;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_phone1);
        initView();
        initData();
    }

    private void initData() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
                checkAndOperatePhoneNumber();
            }
        });

        countryChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 这里应该跳转到一个选择国家并改变手机国家标号的页面，可以用fragment来做
                 */
                jump_to_country_choice_page();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishThis();
            }
        });
    }

    private void getData() {
        phoneNumberText = phoneNumber.getText().toString();
    }

    /**
     * 调用PhoneOrEmailCheckUtil的工具类，通过调用函数phoneOrEmailCheck来对是否进行跳转和可能进行的错误进行判断
     * phoneOrEmail传递的参数为:phoneNumber不能为空，而email必须为空
     */
    private void checkAndOperatePhoneNumber() {
        PhoneCheckUtil peUtil = new PhoneCheckUtil(this.getApplicationContext(), this);
        peUtil.phoneOrEmailCheck(phoneNumberText);
    }

    private void jump_to_country_choice_page() {
        Toast.makeText(this, "国家选择页面还没有写好", Toast.LENGTH_SHORT).show();
    }

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
    }

    private void finishThis(){
        this.finish();
    }
}
