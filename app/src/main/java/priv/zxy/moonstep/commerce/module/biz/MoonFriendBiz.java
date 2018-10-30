package priv.zxy.moonstep.commerce.module.biz;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.litepal.LitePal;

import java.util.List;

import priv.zxy.moonstep.EM.bean.VolleyCallback;
import priv.zxy.moonstep.kernel.bean.ErrorCode;
import priv.zxy.moonstep.utils.LogUtil;
import priv.zxy.moonstep.utils.dbUtils.GetMoonFriendUtil;
import priv.zxy.moonstep.db.MoonFriend;

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
    public void checkClientAndDatabase(final List<MoonFriend> lists) throws InterruptedException {
        new Thread(()->{
            try {
                final List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                if (lists.size() != usernames.size()) {
                    for (String username : usernames) {
                        GetMoonFriendUtil.getInstance().returnMoonFriendInfo(new VolleyCallback() {
                            @Override
                            public String onSuccess(String result) {
                                return null;
                            }

                            @Override
                            public boolean onSuccess() {
                                return false;
                            }

                            @Override
                            public String onFail(String error) {
                                return null;
                            }

                            @Override
                            public boolean onFail() {
                                return false;
                            }

                            @Override
                            public void getMoonFriend(MoonFriend moonFriend) {
                                List<MoonFriend> newLists = null;
                                newLists = LitePal.where("phonenumber = ?", moonFriend.getPhoneNumber()).find(MoonFriend.class);
                                if (newLists == null) {//说明该条数据不存在于数据库中
                                    saveUserToMoonFriendDataBase(moonFriend);
                                }
                            }

                            @Override
                            public void getErrorCode(ErrorCode errorCode) {

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
    private void saveUserToMoonFriendDataBase(MoonFriend mf) {
        MoonFriend moonFriend = new MoonFriend();
        moonFriend.setNickName(mf.getNickName());
        moonFriend.setGender(mf.getGender());
        moonFriend.setLevel(mf.getLevel());
        moonFriend.setPet(mf.getPet());
        moonFriend.setPhoneNumber(mf.getPhoneNumber());
        moonFriend.setRace(mf.getRace());
        moonFriend.setSignature(mf.getSignature());
        moonFriend.setHeadPortrait(mf.getHeadPortrait());
        if (moonFriend.save()) {
            LogUtil.d(TAG, "月友信息存储成功");
        } else {
            LogUtil.e(TAG, "月友信息存储失败");
        }
    }
}
