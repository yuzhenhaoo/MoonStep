package priv.zxy.moonstep.login_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.Utils.PhoneOrEmailCheckUtil;

public class RegisterPhone1 extends AppCompatActivity {

    @BindView(R.id.input_text)
    TextView inputText;
    @BindView(R.id.country_choice)
    TextView countryChoice;
    @BindView(R.id.content1)
    LinearLayout content1;
    @BindView(R.id.country_line)
    View countryLine;
    @BindView(R.id.phone_header)
    TextView phoneHeader;
    @BindView(R.id.phone_number)
    EditText phoneNumber;
    @BindView(R.id.content2)
    LinearLayout content2;
    @BindView(R.id.phone_line)
    View phoneLine;
    @BindView(R.id.submit)
    Button submit;

    private String phoneNumberText;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.register_phone1);
        initData();
    }

    private void initData(){
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
    }

    private void getData(){
        phoneNumberText = phoneNumber.getText().toString();
    }

    /**
     * 调用PhoneOrEmailCheckUtil的工具类，通过调用函数phoneOrEmailCheck来对是否进行跳转和可能进行的错误进行判断
     * phoneOrEmail传递的参数为:phoneNumber不能为空，而email必须为空
     */
    private void checkAndOperatePhoneNumber(){
        PhoneOrEmailCheckUtil peUtil = new PhoneOrEmailCheckUtil(this.getApplicationContext(), this);
        peUtil.phoneOrEmailCheck("",phoneNumberText);
    }

    private void jump_to_country_choice_page(){
        Toast.makeText(this, "国家选择页面还没有写好", Toast.LENGTH_SHORT).show();
    }

}
