package priv.zxy.moonstep.main.view;

import android.support.v4.app.Fragment;

public interface IMainView {

    void addFragmentToStack(Fragment fragment);

    void toFifthPage();

    void toSettingActivity();

    void toLoginActivity();

    void exit();

}
