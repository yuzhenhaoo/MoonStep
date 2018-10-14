package priv.zxy.moonstep.EM.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

import priv.zxy.moonstep.EM.application.EMApplication;
import priv.zxy.moonstep.EM.bean.OnMoonFriendListener;
import priv.zxy.moonstep.Utils.SharedPreferencesUtil;
import priv.zxy.moonstep.Utils.dbUtils.GetMoonFriendUtil;
import priv.zxy.moonstep.db.MoonFriend;
import priv.zxy.moonstep.kernel_data.bean.User;
import priv.zxy.moonstep.main.view.MainActivity;

/**
 * 这里创建Service的目的是为了在用户本地没有加载数据库信息的时候开始初始化的加载月友的信息并将其存储在数据库中
 */
public class MoonFriendService extends Service {

    private static final String TAG = "MoonFriendService";

    private SharedPreferencesUtil sharedPreferencesUtil;

    private static List<User> moonFriends = new ArrayList<User>();

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
        sharedPreferencesUtil = new SharedPreferencesUtil(EMApplication.getEMApplicationContext());
        if(sharedPreferencesUtil.readInitDataBase()){
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    for (String username : usernames) {
                        GetMoonFriendUtil util = new GetMoonFriendUtil(getApplicationContext());
                        util.returnMoonFriendInfo(username);
                        try {
                            Thread.sleep(150);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
//                        moonFriends.add(util.getMoonFriend());
                        //我们在将当前数据插入数据库的表中时进行相应的判断，如果当前的数据已经存在于表中的话，我们就不进行插入，否则的话，我们就将内容插入数据库中
                        List<MoonFriend> newLists = LitePal.where("phonenumber = ?", util.getMoonFriend().getUserPhoneNumber()).find(MoonFriend.class);
                        if(newLists == null || newLists.size() == 0){//说明该条数据不存在于数据库中
                            saveUserToMoonFriendDataBase(util.getMoonFriend());
                        }
                    }
//                    onMoonFriendListener.getMoonFriends(moonFriends);
                    Log.d(TAG, "run: EM获取好友列表成功");
                    sharedPreferencesUtil.saveIsInitedDataBase();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.d(TAG, "run: EM获取好友列表失败：" + e.getMessage());
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MoonFriendService","isDestroyed");
        moonFriends.clear();
    }

    /**
     * 关于使用litepal对于信息的存储，我不知道为什么每次保存都必须重建一个新的对象，不然保存智慧对第一次生效，有可能不单单指查询，增删改可能也一样。
     * @param user
     */
    public void saveUserToMoonFriendDataBase(User user){
        MoonFriend moonFriend = new MoonFriend();
        moonFriend.setNickName(user.getNickName());
        moonFriend.setGender(user.getUserGender());
        moonFriend.setLevel(user.getUserLevel());
        moonFriend.setPet(user.getUserPet());
        moonFriend.setPhoneNumber(user.getUserPhoneNumber());
        moonFriend.setRace(user.getUserRace());
        moonFriend.setSignature(user.getSignature());
        moonFriend.setHeadPortrait(user.getHeadPortrait());
        if (moonFriend.save()){
            Log.d("MoonFriendService","月友信息存储成功");
        }else{
            Log.e("MoonFriendService","月友信息存储失败");
        }
    }
}
