package priv.zxy.moonstep.framework.stroage;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.litepal.LitePal;

import java.util.List;

import priv.zxy.moonstep.DAO.PullUserInfoDAO;
import priv.zxy.moonstep.constant.SharedConstant;
import priv.zxy.moonstep.data.bean.ErrorCodeEnum;
import priv.zxy.moonstep.framework.user.User;
import priv.zxy.moonstep.util.LogUtil;
import priv.zxy.moonstep.util.SharedPreferencesUtil;

public class MoonFriendInfo {

    private static final String TAG = "MoonFriendInfo";

    private static MoonFriendInfo instance = new MoonFriendInfo();

    public static MoonFriendInfo getInstance() {
        return instance;
    }

    /**
     * 初始化所有的好友信息
     */
    public void initMoonFriends() {
        new Thread(() -> {
            try {
                List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                for (String username : usernames) {
                    LogUtil.d(TAG, username);
                    PullUserInfoDAO.getInstance().getUserInfo(new PullUserInfoDAO.Callback() {
                        @Override
                        public void onSuccess(User moonFriend) {
                            List<User> newLists = LitePal.where("phonenumber = ?", moonFriend.getPhoneNumber()).find(User.class);
                            if (newLists == null || newLists.size() == 0) {//说明该条数据不存在于数据库中
                                saveUserToMoonFriendDataBase(moonFriend);
                            }
                        }

                        @Override
                        public void onFail(ErrorCodeEnum errorCode) {

                        }
                    }, username);
                }
                LogUtil.d(TAG, "run: EM获取好友列表成功");
                SharedPreferencesUtil.setDataInited(SharedConstant.MOON_FRIEND);
            } catch (HyphenateException e) {
                e.printStackTrace();
                LogUtil.d(TAG, "run: EM获取好友列表失败：" + e.getMessage());
            }
        }).start();
    }

    private void saveUserToMoonFriendDataBase(User mf) {
        User moonFriend = new User();
        moonFriend.setPhoneNumber(mf.getPhoneNumber());
        moonFriend.setNickName(mf.getNickName());
        moonFriend.setGender(mf.getGender());
        moonFriend.setRaceCode(mf.getRaceCode());
        moonFriend.setHeadPath(mf.getHeadPath());
        moonFriend.setLocation(mf.getLocation());
        moonFriend.setSignature(mf.getSignature());
        moonFriend.setCurrentTitleCode(mf.getCurrentTitleCode());
        moonFriend.setLuckyValue(mf.getLuckyValue());
//        moonFriend.setIsOnline(mf.getIsOnline());
        if (moonFriend.save()) {
            LogUtil.d("MoonFriendService", "月友信息存储成功");
        } else {
            LogUtil.e("MoonFriendService", "月友信息存储失败");
        }
    }
}
