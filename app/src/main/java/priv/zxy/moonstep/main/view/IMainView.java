package priv.zxy.moonstep.main.view;

import android.support.v4.app.Fragment;

public interface IMainView {
    void addFragmentToStack(Fragment fragment);

    void toFifthPage();

    void changeMyInformation();

    void toLoginActivity();

    void doEMConnectionListener();

    void bindService();

    void unBindService();

    void savedChattingMessage(String content, int direction, int type, String phoneNumber);

}
