package priv.zxy.moonstep.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import priv.zxy.moonstep.constant.SharedConstant;
import priv.zxy.moonstep.framework.user.User;
import priv.zxy.moonstep.helper.EMHelper;
import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.wheel.cache.FacadeSharedPreference;


public class SharedPreferencesUtil {

    private static final String TAG = "SharedPreferencesUtil";

    private Context context;

    private FacadeSharedPreference sp;

    private static SharedPreferencesUtil instance;

    private SharedPreferencesUtil(Context context){
        this.context = context;
        sp = new FacadeSharedPreference(context);
    }

    /**
     * 双重锁定模式
     *
     * @param mContext 上下文
     * @return SharedPreferencesUtil的实例
     */
    public static SharedPreferencesUtil getInstance(Context mContext) {
        if (instance == null) {
            synchronized (EMHelper.class) {
                if (instance == null) {
                    instance = new SharedPreferencesUtil(mContext);
                }
            }
        }
        return instance;
    }

    /**
     * 存储第一次登陆的信息
     */
    public void setFirstLogin(){
        sp.saveElement(SharedConstant.LANDING_LIBRARY,SharedConstant.IS_FIRST_LANDING, true);
    }

    /**
     * 判断是否为第一次登陆
     * @return 是第一次登录的话，返回True
     *          非第一次登录的话，返回False
     */
    public Boolean isFirstLogin(){
        return !sp.checkElement(SharedConstant.LANDING_LIBRARY, SharedConstant.IS_FIRST_LANDING);
    }

    /**
     * 记录好友信息是否已经被加载过（既第一次检测）
     */
    public void dataInitialized(){
        sp.saveElement(SharedConstant.LANDING_LIBRARY, SharedConstant.INIT_DATA, true);
    }

    /**
     * @return 数据库已经进行过初始化的话，就返回false,不然就返回true
     */
    public boolean isDataInitialized(){
        return !sp.checkElement(SharedConstant.LANDING_LIBRARY, SharedConstant.INIT_DATA);
    }

    /**
     * 当登陆成功的时候存储用户的手机号和密码
     * @param username 用户名
     * @param password 用户密码
     */
    public void setSuccessLoginInfo(String username, String password){
        Map<String, String> params = new HashMap<>();
        params.put(SharedConstant.PHONE_NUMBER, username);
        params.put(SharedConstant.PASSWORD, password);
        sp.save(SharedConstant.LANDING_LIBRARY, params);
        sp.saveElement(SharedConstant.LANDING_LIBRARY, SharedConstant.IS_SUCCESS, true);
    }

    /**
     * 当用户登陆失败的时候设置用户的登陆检测字段为false,直到下次登陆成功为止不允许自动登陆
     */
    public void setFailLoginInfo(){
        sp.saveElement(SharedConstant.LANDING_LIBRARY, SharedConstant.IS_SUCCESS, false);
    }

    /**
     * 读取登陆信息
     * @return 返回登陆信息的集合<用户名，密码>
     */
    public Map<String, String> readLoginInfo(){
        Map<String, String> data = new HashMap<>();
        data.put(SharedConstant.PHONE_NUMBER, sp.read(SharedConstant.LANDING_LIBRARY).get(SharedConstant.PHONE_NUMBER).toString());
        data.put(SharedConstant.PASSWORD, sp.read(SharedConstant.LANDING_LIBRARY).get(SharedConstant.PASSWORD).toString());
        return data;
    }

    /**
     * 判断上次登陆是否成功
     * @return 返回上次登陆的结果值
     */
    public boolean isSuccessLogin(){
        return sp.checkElement(SharedConstant.LANDING_LIBRARY, "IS_SUCCESS");
    }

    /**
     * 存储用户的个人信息
     * @param moonFriend 用户信息
     */
    public void saveMySelfInformation(User moonFriend){
        Map<String ,String> params = new HashMap<>();
        params.put(SharedConstant.NICK_NAME, moonFriend.getNickName());
        params.put(SharedConstant.PHONE_NUMBER, moonFriend.getPhoneNumber());
        params.put(SharedConstant.GENDER, moonFriend.getGender());
        params.put(SharedConstant.RACE_CODE, String.valueOf(moonFriend.getRaceCode()));
        params.put(SharedConstant.HEAD_PATH, moonFriend.getHeadPath());
        params.put(SharedConstant.SIGNATURE, moonFriend.getSignature());
        params.put(SharedConstant.ADDRESS, moonFriend.getLocation());
        params.put(SharedConstant.CURRENT_TITLE_CODE, moonFriend.getCurrentTitleCode());
        params.put(SharedConstant.LUCKY_VALUE, String.valueOf(moonFriend.getLuckyValue()));
        sp.save(SharedConstant.PERSONAL_INFO_LIBRARY, params);
        sp.saveElement(SharedConstant.PERSONAL_INFO_LIBRARY, SharedConstant.IS_SAVED, true);
    }

    /**
     * 读取用户的个人信息
     *     必须得加入一个判断，如果已经存入过个人信息的话，才能够进行读取
     * @return 返回个人信息的集合
     */
    public Map<String, String> readMySelfInformation(){
        Map<String, String> data = new HashMap<>();
        data.put(SharedConstant.NICK_NAME, sp.read(SharedConstant.PERSONAL_INFO_LIBRARY).get(SharedConstant.NICK_NAME).toString());
        data.put(SharedConstant.PHONE_NUMBER, sp.read(SharedConstant.PERSONAL_INFO_LIBRARY).get(SharedConstant.PHONE_NUMBER).toString());
        data.put(SharedConstant.GENDER, sp.read(SharedConstant.PERSONAL_INFO_LIBRARY).get(SharedConstant.GENDER).toString());
        data.put(SharedConstant.RACE_CODE, sp.read(SharedConstant.PERSONAL_INFO_LIBRARY).get(SharedConstant.RACE_CODE).toString());
        data.put(SharedConstant.HEAD_PATH, sp.read(SharedConstant.PERSONAL_INFO_LIBRARY).get(SharedConstant.HEAD_PATH).toString());
        data.put(SharedConstant.SIGNATURE, sp.read(SharedConstant.PERSONAL_INFO_LIBRARY).get(SharedConstant.SIGNATURE).toString());
        data.put(SharedConstant.ADDRESS, sp.read(SharedConstant.PERSONAL_INFO_LIBRARY).get(SharedConstant.ADDRESS).toString());
        data.put(SharedConstant.CURRENT_TITLE_CODE, sp.read(SharedConstant.PERSONAL_INFO_LIBRARY).get(SharedConstant.CURRENT_TITLE_CODE).toString());
        data.put(SharedConstant.LUCKY_VALUE, sp.read(SharedConstant.PERSONAL_INFO_LIBRARY).get(SharedConstant.LUCKY_VALUE).toString());
        return data;
    }

    /**
     * 判断是否为第一次读取用户的个人信息
     * @return 返回是否存储了用户个人信息的结果
     */
    public boolean isFirstSaveMySelfInformation(){
        return !sp.checkElement(SharedConstant.PERSONAL_INFO_LIBRARY, "isSaved");
    }

    /**
     * 当有消息来临时，如果页面没有关闭，就先将消息存储到缓存中
     * 为了让FirstMainPageFragmentParent对缓存进行检测并对用户进行提示
     */
    public void saveIsMessageTip(String phoneNumber){
        sp.saveNumber(SharedConstant.MESSAGE_TIP_LIBRARY, phoneNumber, 1, null);
    }

    /**
     * 将每个人发来的消息数目和消息人存入缓存中
     * 当传递进来phoneNumber地时候，获得当前未读的消息数目。
     * @param phoneNumber 电话号码
     * @return 返回当前电话号码的未读消息个数
     */
    public int readMessageNumber(String phoneNumber){
        SharedPreferences sp = context.getSharedPreferences(SharedConstant.MESSAGE_TIP_LIBRARY, Context.MODE_PRIVATE);
        return sp.getInt(phoneNumber, 0);
    }

    /**
     * 当点击进入一个人的聊天页面，说明他的消息已经被看过来，此时应该将他的消息记录从缓存中清除。
     */
    public void handleMessageTip(String phoneNumber){
        SharedPreferences sp = context.getSharedPreferences(SharedConstant.MESSAGE_TIP_LIBRARY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(phoneNumber);
        if (sp.contains("message") && sp.getAll().size() == 1){
            editor.remove("message");
        }
        editor.apply();
    }

    /**
     * 检测当前是否有临时消息
     * @return 当sp中包含的消息只有message地时候，说明这个时候没有任何新来的消息，那么就返回false，
     *              若非这种情况，就返回true
     */
    public boolean isMessageTip(){
        SharedPreferences sp = context.getSharedPreferences(SharedConstant.MESSAGE_TIP_LIBRARY, Context.MODE_PRIVATE);
        return sp.getAll().size() > 1;
    }

    /**
     * 用在地图宝藏刷新的API
     * 用来检索当前时间和数据库中存入的时间（上次存入的时间）是否相差大于3
     * @param days 大于3返回true,否则返回false
     */
    public boolean checkMapTime(int days){
        LogUtil.d(TAG, SharedConstant.DAYS + days);
        SharedPreferences sps = Application.getContext().getSharedPreferences(SharedConstant.MAP_REFRESH_TIME_CHECK, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sps.edit();
        // 第一次的时候，只有过三天，才能重新存一次days
        if (days >= readMapTime() + 3){
            editor.putInt(SharedConstant.DAYS, days);
            editor.apply();
            return true;
        }else if (readMapTime() > days){
            editor.putInt(SharedConstant.DAYS, days);
            editor.apply();
            return false;
        }
        return false;
    }

    private int readMapTime(){
        SharedPreferences sp = context.getSharedPreferences(SharedConstant.MAP_REFRESH_TIME_CHECK, Context.MODE_PRIVATE);
        return sp.getInt(SharedConstant.DAYS, 0);
    }
}
