package priv.zxy.moonstep.main.view;

import android.support.v4.app.Fragment;
import android.view.View;

import java.util.List;

import priv.zxy.moonstep.kernel_data.bean.User;

public interface IMainView {
    void addFragmentToStack(Fragment fragment);

    void toFifthPage();

    void changeMyInformation();

    void toLoginActivity();

    void doEMConnectionListener();

    void bindService();

    void unBindService();

}
