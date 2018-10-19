package priv.zxy.moonstep.commerce.view;

import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import priv.zxy.moonstep.db.MoonFriend;

public interface IMoonFriendView {
    void checkClientAndDatabase(List<MoonFriend> lists) throws HyphenateException, InterruptedException;
}
