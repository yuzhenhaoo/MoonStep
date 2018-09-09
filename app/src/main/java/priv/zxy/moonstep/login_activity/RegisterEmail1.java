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
import android.widget.TextView;

import priv.zxy.moonstep.R;

public class RegisterEmail1 extends AppCompatActivity {

    private TextView inputText;
    private LinearLayout content;
    private EditText email;
    private View phoneLine;
    private Button submit;
    private ImageView backButton;

    private String emailText;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_email1);
        initView();

        initData();
    }

    private void initView() {
        inputText = (TextView) findViewById(R.id.input_text);
        content = (LinearLayout) findViewById(R.id.content);
        email = (EditText) findViewById(R.id.email);
        phoneLine = (View) findViewById(R.id.phone_line);
        submit = (Button) findViewById(R.id.submit);
        backButton = (ImageView) findViewById(R.id.back_button);
    }

    private void initData(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishThis();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
                checkAndOperateData();
            }
        });
    }

    private void getData(){
        emailText = email.getText().toString();
    }

    private void checkAndOperateData(){
        //检验email是否可以进行注册
        jumpToNextPage();
    }

    private void jumpToNextPage(){
        Intent intent = new Intent(this, RegisterEmail2.class);
        intent.putExtra("Email",emailText);
        startActivity(intent);
    }

    private void finishThis(){
        this.finish();
    }
}
