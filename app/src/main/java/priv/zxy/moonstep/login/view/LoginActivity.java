package priv.zxy.moonstep.login.view;

import android.support.v7.app.AppCompatActivity;

import priv.zxy.moonstep.login.module.User;

/**
 * Created by Zxy on 2018/9/20
 */
public class LoginActivity extends AppCompatActivity implements ILoginView{
    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void clearUserName() {

    }

    @Override
    public void clearPassword() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void toMainActivity(User user) {

    }

    @Override
    public void showFailedError() {

    }
}
