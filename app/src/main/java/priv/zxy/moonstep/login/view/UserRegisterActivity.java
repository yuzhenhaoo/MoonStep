package priv.zxy.moonstep.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import priv.zxy.moonstep.R;

public class UserRegisterActivity extends AppCompatActivity implements IUserRegisterView {

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
    @BindView(R.id.deepBackground)
    View deepBackground;
    @BindView(R.id.plainBackground)
    View plainBackground;
    @BindView(R.id.progressBar)
    ContentLoadingProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        ButterKnife.bind(this);
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getUserPassWord() {
        return null;
    }

    @Override
    public void clearUserName() {

    }

    @Override
    public void clearUserPassword() {

    }

    @Override
    public void clearUserConfirmPassword() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void toLoginActivity() {

    }

    @Override
    public void toMainActivity() {

    }

    @Override
    public void showFailedError(int code) {

    }
}
