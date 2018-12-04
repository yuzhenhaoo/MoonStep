package priv.zxy.moonstep.commerce.module.biz;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.litepal.LitePal;

import java.util.List;

import priv.zxy.moonstep.data.bean.VolleyCallback;
import priv.zxy.moonstep.data.bean.ErrorCode;
import priv.zxy.moonstep.framework.user.User;
import priv.zxy.moonstep.utils.LogUtil;
import priv.zxy.moonstep.utils.dbUtils.GetUserInformationUtil;

public class MoonFriendBiz implements IMoonFriendBiz {

    private static final String TAG = "MoonFriendBiz";
    /**
     * 用来保存聊天记录地消息记录
     * 通常地做法是显示最近地10条消息，然后要继续显示，就用一个RecyclerView地刷新框，下拉刷新，出现旋转小圆圈，同时开始进行网络请求
     * 当网络请求完毕，小圆圈结束，并且继续显示历史地30条消息
     */
    public void savedChattingMessage() {

    }

    /**
     * @param lists 列表
     * @throws InterruptedException
     */
    @Override
    public void checkClientAndDatabase(final List<User> lists) throws InterruptedException {
        new Thread(()->{
            try {
                final List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                if (lists.size() != usernames.size()) {
                    for (String username : usernames) {
                        GetUserInformationUtil.getInstance().getUserInfo(new GetUserInformationUtil.Callback() {
                            @Override
                            public void onSuccess(User moonFriend) {
                                List<User> newLists = LitePal.where("phonenumber = ?", moonFriend.getPhoneNumber()).find(User.class);
                                LogUtil.d(TAG, "getMoonFriend被调用了");
                                if (newLists == null || newLists.size() == 0) {//说明该条数据不存在于数据库中
                                    saveUserToMoonFriendDataBase(moonFriend);
                                }
                            }

                            @Override
                            public void onFail(ErrorCode errorCode) {

                            }
                        }, username);
                    }
                }
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 关于使用litepal对于信息的存储，我不知道为什么每次保存都必须重建一个新的对象，不然保存智慧对第一次生效，有可能不单单指查询，增删改可能也一样。
     *
     * @param mf 传递进来的好友对象
     */
    private void saveUserToMoonFriendDataBase(User mf) {
        User moonFriend = new User();
        moonFriend.setPhoneNumber(mf.getPhoneNumber());
        moonFriend.setNickName(mf.getNickName());
        moonFriend.setGender(mf.getGender());
        moonFriend.setRaceCode(mf.getRaceCode());
        moonFriend.setHeadPath(mf.getHeadPath());
        moonFriend.setLocation(mf.getLocation());
        moonFriend.setSignature(mf.getSignature());
        moonFriend.setCurrentTitle(mf.getCurrentTitle());
        moonFriend.setLuckyValue(mf.getLuckyValue());
//        moonFriend.setIsOnline(mf.getIsOnline());
        if (moonFriend.save()) {
            LogUtil.d(TAG, "月友信息存储成功");
        } else {
            LogUtil.e(TAG, "月友信息存储失败");
        }
    }
}
