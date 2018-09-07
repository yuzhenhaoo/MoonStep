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

import butterknife.BindView;
import butterknife.ButterKnife;
import priv.zxy.moonstep.R;

public class RegisterPhone1 extends AppCompatActivity {

    @BindView(R.id.input_text)
    TextView inputText;
    @BindView(R.id.spinner2)
    Spinner spinner2;
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
                jumpToTheSecondPage();

            }
        });
    }

    private void jumpToTheSecondPage(){
        Intent intent = new Intent(this, RegisterPhone2.class);
        startActivity(intent);
    }

}
