package priv.zxy.moonstep.commerce.module.biz;

import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import priv.zxy.moonstep.framework.user.User;

public interface IMoonFriendBiz {
    void checkClientAndDatabase(List<User> lists) throws HyphenateException, InterruptedException;
}
