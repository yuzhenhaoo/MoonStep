package priv.zxy.moonstep.login_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import priv.zxy.moonstep.R;

public class RegisterPhone2 extends AppCompatActivity {
    @BindView(R.id.header)
    TextView header;
    @BindView(R.id.phone_number)
    EditText phoneNumber;
    @BindView(R.id.content1)
    LinearLayout content1;
    @BindView(R.id.phone_line)
    View phoneLine;
    @BindView(R.id.code)
    TextView code;
    @BindView(R.id.password_number)
    EditText passwordNumber;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.register_phone2);
        initData();
    }

    private void initData(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpToTheRegisterPage();
            }
        });
    }

    //这里需要跳转到最终的注册页面
    private void jumpToTheRegisterPage(){
        Intent intent = new Intent(this, RegisterPage.class);
        startActivity(intent);
    }
}
