package priv.zxy.moonstep.EM.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import priv.zxy.moonstep.EM.bean.VolleyCallback;
import priv.zxy.moonstep.kernel.Application;
import priv.zxy.moonstep.kernel.bean.ErrorCode;
import priv.zxy.moonstep.utils.LogUtil;
import priv.zxy.moonstep.utils.SharedPreferencesUtil;
import priv.zxy.moonstep.utils.dbUtils.GetMoonFriendUtil;
import priv.zxy.moonstep.db.MoonFriend;

/**
 * 这里创建Service的目的是为了在用户本地没有加载数据库信息的时候开始初始化的加载月友的信息并将其存储在数据库中
 */
public class MoonFriendService extends Service {

    private static final String TAG = "MoonFriendService";

    private static List<MoonFriend> moonFriends = new ArrayList<MoonFriend>();

    //定义onBinder方法所返回地对象
    private MyBinder binder = new MyBinder();

    public class MyBinder extends Binder{

        /**
         * @return 获取当前Service的实例
         */
        public MoonFriendService getService(){
            return MoonFriendService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        if(SharedPreferencesUtil.getInstance(Application.getContext()).isDataInitialized()){
            initMoonFriends();
        }
        super.onCreate();
    }

    /**
     * 这里应该是从登陆就可以开始监听到了
     */
    //必须要将获取好友的函数放在子线程里，不然会发生数据异常
    private static List<String> usernames = new ArrayList<>();

    public void initMoonFriends() {
        new Thread(() -> {
            try {
                usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                for (String username : usernames) {
                    LogUtil.d(TAG, username);
                    GetMoonFriendUtil.getInstance().returnMoonFriendInfo(new VolleyCallback() {
                        @Override
                        public String onSuccess(String result) throws InterruptedException {
                            return null;
                        }

                        @Override
                        public boolean onSuccess() throws InterruptedException {
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
                            List<MoonFriend> newLists = LitePal.where("phonenumber = ?", moonFriend.getPhoneNumber()).find(MoonFriend.class);
                            if(newLists == null || newLists.size() == 0){//说明该条数据不存在于数据库中
                                saveUserToMoonFriendDataBase(moonFriend);
                            }
                        }

                        @Override
                        public void getErrorCode(ErrorCode errorCode) {

                        }
                    }, username);
                }
                LogUtil.d(TAG, "run: EM获取好友列表成功");
                SharedPreferencesUtil.getInstance(Application.getContext()).dataInitialized();
            } catch (HyphenateException e) {
                e.printStackTrace();
                LogUtil.d(TAG, "run: EM获取好友列表失败：" + e.getMessage());
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("MoonFriendService","isDestroyed");
        moonFriends.clear();
    }

    /**
     * 关于使用litepal对于信息的存储，我不知道为什么每次保存都必须重建一个新的对象，不然保存只会对第一次生效，有可能不单单指查询，增删改可能也一样。
     * @param user
     */
    public void saveUserToMoonFriendDataBase(MoonFriend user){
        MoonFriend moonFriend = new MoonFriend();
        moonFriend.setNickName(user.getNickName());
        moonFriend.setPhoneNumber(user.getPhoneNumber());
        moonFriend.setGender(user.getGender());
        moonFriend.setHeadPortraitPath(user.getHeadPortraitPath());
        moonFriend.setSignature(user.getSignature());
        moonFriend.setCurrentTitle(user.getCurrentTitle());
        moonFriend.setRaceName(user.getCurrentTitle());
        moonFriend.setRaceDescription(user.getRaceDescription());
        moonFriend.setLevelName(user.getLevelName());
        moonFriend.setLevelDescription(user.getLevelDescription());
        if (moonFriend.save()){
            LogUtil.d("MoonFriendService","月友信息存储成功");
        }else{
            LogUtil.e("MoonFriendService","月友信息存储失败");
        }
    }
}
