package priv.zxy.moonstep.commerce.module.biz;

import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.litepal.LitePal;

import java.util.List;

import priv.zxy.moonstep.kernel.Application;
import priv.zxy.moonstep.utils.dbUtils.GetMoonFriendUtil;
import priv.zxy.moonstep.db.MoonFriend;
import priv.zxy.moonstep.kernel.bean.User;

public class MoonFriendBiz implements IMoonFriendBiz {

    /**
     * 用来保存聊天记录地消息记录
     * 通常地做法是显示最近地10条消息，然后要继续显示，就用一个RecyclerView地刷新框，下拉刷新，出现旋转小圆圈，同时开始进行网络请求
     * 当网络请求完毕，小圆圈结束，并且继续显示历史地30条消息
     */
    public void savedChattingMessage() {

    }

    /**
     * 这里存在问题，每次向util工具类输入15809679015的时候，返回的确是18616257996的信息
     * @param lists 列表
     * @throws InterruptedException
     */
    @Override
    public void checkClientAndDatabase(final List<MoonFriend> lists) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    if (lists.size() != usernames.size()) {
                        for (String username : usernames) {
                            GetMoonFriendUtil util = new GetMoonFriendUtil(Application.getEMApplicationContext());
                            util.returnMoonFriendInfo(username);
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            List<MoonFriend> newLists = null;
                            if (util.getMoonFriend() != null && util.isSuccess) {
                                newLists = LitePal.where("phonenumber = ?", util.getMoonFriend().getUserPhoneNumber()).find(MoonFriend.class);
                                if (newLists == null || newLists.size() == 0) {//说明该条数据不存在于数据库中
                                    saveUserToMoonFriendDataBase(util.getMoonFriend());
                                }
                            }
                        }
                    }
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(1000);
    }

    /**
     * 关于使用litepal对于信息的存储，我不知道为什么每次保存都必须重建一个新的对象，不然保存智慧对第一次生效，有可能不单单指查询，增删改可能也一样。
     *
     * @param user
     */
    public void saveUserToMoonFriendDataBase(User user) {
        MoonFriend moonFriend = new MoonFriend();
        moonFriend.setNickName(user.getNickName());
        moonFriend.setGender(user.getUserGender());
        moonFriend.setLevel(user.getUserLevel());
        moonFriend.setPet(user.getUserPet());
        moonFriend.setPhoneNumber(user.getUserPhoneNumber());
        moonFriend.setRace(user.getUserRace());
        moonFriend.setSignature(user.getSignature());
        moonFriend.setHeadPortrait(user.getHeadPortrait());
        if (moonFriend.save()) {
            Log.d("MoonFriendService", "月友信息存储成功");
        } else {
            Log.e("MoonFriendService", "月友信息存储失败");
        }
    }
}
