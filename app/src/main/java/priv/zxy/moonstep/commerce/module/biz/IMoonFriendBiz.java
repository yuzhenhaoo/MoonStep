package priv.zxy.moonstep.commerce.module.biz;

import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import priv.zxy.moonstep.db.MoonFriend;

public interface IMoonFriendBiz {
    void checkClientAndDatabase(List<MoonFriend> lists) throws HyphenateException, InterruptedException;
}
