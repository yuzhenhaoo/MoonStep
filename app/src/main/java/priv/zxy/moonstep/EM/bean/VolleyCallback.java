package priv.zxy.moonstep.EM.bean;

import priv.zxy.moonstep.db.MoonFriend;
import priv.zxy.moonstep.kernel.bean.ErrorCode;

public interface VolleyCallback {

    String onSuccess(String result) throws InterruptedException;

    boolean onSuccess() throws InterruptedException;

    String onFail(String error);

    boolean onFail();

    void getMoonFriend(MoonFriend moonFriend);

    void getErrorCode(ErrorCode errorCode);
}
