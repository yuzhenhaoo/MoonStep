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
import android.widget.RelativeLayout;
import android.widget.TextView;

import priv.zxy.moonstep.R;

public class SendMessageActivity extends AppCompatActivity implements ISendMessageView {
    private TextView header;
    private LinearLayout content1;
    private TextView phoneNumber;
    private View phoneLine;
    private RelativeLayout content2;
    private TextView code;
    private EditText codeNumber;
    private Button sendCode;
    private View passwordLine;
    private ImageView backButton;
    private LinearLayout voiceMode;
    private TextView voiceCode;
    private Button submit;
    private View deepBackground;
    private View plainBackground;
    private ContentLoadingProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_phone2);
        initView();
    }

    private void initView() {
        header = (TextView) findViewById(R.id.header);
        content1 = (LinearLayout) findViewById(R.id.content1);
        phoneNumber = (TextView) findViewById(R.id.phone_number);
        phoneLine = (View) findViewById(R.id.phone_line);
        content2 = (RelativeLayout) findViewById(R.id.content2);
        code = (TextView) findViewById(R.id.code);
        codeNumber = (EditText) findViewById(R.id.code_number);
        sendCode = (Button) findViewById(R.id.send_code);
        passwordLine = (View) findViewById(R.id.password_line);
        backButton = (ImageView) findViewById(R.id.back_button);
        voiceMode = (LinearLayout) findViewById(R.id.voice_mode);
        voiceCode = (TextView) findViewById(R.id.voice_code);
        submit = (Button) findViewById(R.id.submit);
        deepBackground = (View) findViewById(R.id.deepBackground);
        plainBackground = (View) findViewById(R.id.plainBackground);
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
    }
}
