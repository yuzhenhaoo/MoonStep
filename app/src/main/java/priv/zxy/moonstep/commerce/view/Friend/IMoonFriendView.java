package priv.zxy.moonstep.commerce.view.Friend;

import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import priv.zxy.moonstep.framework.user.User;

public interface IMoonFriendView {
    void checkClientAndDatabase(List<User> lists) throws HyphenateException, InterruptedException;
}
