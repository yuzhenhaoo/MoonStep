package priv.zxy.moonstep.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import priv.zxy.moonstep.R;

public class ConfirmPhoneActivity extends AppCompatActivity implements IConfirmPhoneView {

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_phone1);
        initView();
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
        deepBackground = (View) findViewById(R.id.deepBackground);
        plainBackground = (View) findViewById(R.id.plainBackground);
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
    }
}
