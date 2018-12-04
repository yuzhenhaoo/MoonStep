package priv.zxy.moonstep.data.bean;

import priv.zxy.moonstep.data.bean.ErrorCode;
import priv.zxy.moonstep.framework.user.User;

public interface VolleyCallback {

    String onSuccess(String result) throws InterruptedException;

    boolean onSuccess() throws InterruptedException;

    String onFail(String error);

    boolean onFail();

    void getMoonFriend(User moonFriend);

    void getErrorCode(ErrorCode errorCode);
}
