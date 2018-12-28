package priv.zxy.moonstep.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import priv.zxy.moonstep.helper.EMHelper;
import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.wheel.cache.FacadeSharedPreference;

public class SharedPreferencesUtil {

    private static final String TAG = "SharedPreferencesUtil";
    private static final String LANDING_LIBRARY = "landing";
    private static final String PERSONAL_INFO_LIBRARY = "personalInfo";
    private static final String MESSAGE_TIP_LIBRARY = "tip";
    private static final String MAP_REFRESH_TIME_CHECK = "map";
    private static final String DAYS = "days";
    private static final String IS_SUCCESS = "isSuccess";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String PASSWORD = "passWord";
    private static final String INIT_DATA = "dataIsInitialized";
    private static final String IS_FIRST_LANDING = "firstLand";

    private static final String NICK_NAME = "nickName";
    private static final String GENDER = "gender";
    private static final String RACE_CODE = "raceCode";
    private static final String HEAD_PATH = "headPath";
    private static final String SIGNATURE = "signature";
    private static final String ADDRESS = "address";
    private static final String CURRENT_TITLE_CODE = "currentTitleCode";
    private static final String LUCKY_VALUE = "luckyValue";
    private static final String IS_SAVED = "isSaved";

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
        sp.saveElement(LANDING_LIBRARY,IS_FIRST_LANDING, true);
    }

    /**
     * 判断是否为第一次登陆
     * @return 是第一次登录的话，返回True
     *          非第一次登录的话，返回False
     */
    public Boolean isFirstLogin(){
        return !sp.checkElement(LANDING_LIBRARY, IS_FIRST_LANDING);
    }

    /**
     * 记录好友信息是否已经被加载过（既第一次检测）
     */
    public void dataInitialized(){
        sp.saveElement(LANDING_LIBRARY, INIT_DATA, true);
    }

    /**
     * @return 数据库已经进行过初始化的话，就返回false,不然就返回true
     */
    public boolean isDataInitialized(){
        return !sp.checkElement(LANDING_LIBRARY, INIT_DATA);
    }

    /**
     * 当登陆成功的时候存储用户的手机号和密码
     * @param username 用户名
     * @param password 用户密码
     */
    public void setSuccessLoginInfo(String username, String password){
        Map<String, String> params = new HashMap<>();
        params.put(PHONE_NUMBER, username);
        params.put(PASSWORD, password);
        sp.save(LANDING_LIBRARY, params);
        sp.saveElement(LANDING_LIBRARY, "IS_SUCCESS", true);
    }

    /**
     * 当用户登陆失败的时候设置用户的登陆检测字段为false,直到下次登陆成功为止不允许自动登陆
     */
    public void setFailLoginInfo(){
        sp.saveElement(LANDING_LIBRARY, IS_SUCCESS, false);
    }

    /**
     * 读取登陆信息
     * @return 返回登陆信息的集合<用户名，密码>
     */
    public Map<String, String> readLoginInfo(){
        Map<String, String> data = new HashMap<>();
        data.put(PHONE_NUMBER, sp.read(LANDING_LIBRARY).get(PHONE_NUMBER).toString());
        data.put(PASSWORD, sp.read(LANDING_LIBRARY).get(PASSWORD).toString());
        return data;
    }

    /**
     * 判断上次登陆是否成功
     * @return 返回上次登陆的结果值
     */
    public boolean isSuccessLogin(){
        return sp.checkElement(LANDING_LIBRARY, "IS_SUCCESS");
    }

    /**
     * 存储用户的个人信息
     * @param nickName 昵称
     * @param phoneNumber 电话号码
     * @param gender 性别
     * @param raceCode 种族编码
     * @param headPath 头像路径
     * @param userSignature 用户签名
     * @param location 位置信息
     * @param userCurrentTitle 用户当前称号
     * @param luckyValue 幸运值
     */
    public void saveMySelfInformation(String nickName, String phoneNumber, String gender, int raceCode, String headPath, String userSignature, String location, String userCurrentTitle, int luckyValue){
        Map<String ,String> params = new HashMap<>();
        params.put(NICK_NAME, nickName);
        params.put(PHONE_NUMBER, phoneNumber);
        params.put(GENDER, gender);
        params.put(RACE_CODE, String.valueOf(raceCode));
        params.put(HEAD_PATH, headPath);
        params.put(SIGNATURE, userSignature);
        params.put(ADDRESS, location);
        params.put(CURRENT_TITLE_CODE, userCurrentTitle);
        params.put(LUCKY_VALUE, String.valueOf(luckyValue));
        sp.save(PERSONAL_INFO_LIBRARY, params);
        sp.saveElement(PERSONAL_INFO_LIBRARY, IS_SAVED, true);
    }

    /**
     * 读取用户的个人信息
     *     必须得加入一个判断，如果已经存入过个人信息的话，才能够进行读取
     * @return 返回个人信息的集合
     */
    public Map<String, String> readMySelfInformation(){
        Map<String, String> data = new HashMap<>();
        data.put(NICK_NAME, sp.read(PERSONAL_INFO_LIBRARY).get(NICK_NAME).toString());
        data.put(PHONE_NUMBER, sp.read(PERSONAL_INFO_LIBRARY).get(PHONE_NUMBER).toString());
        data.put(GENDER, sp.read(PERSONAL_INFO_LIBRARY).get(GENDER).toString());
        data.put(RACE_CODE, sp.read(PERSONAL_INFO_LIBRARY).get(RACE_CODE).toString());
        data.put(HEAD_PATH, sp.read(PERSONAL_INFO_LIBRARY).get(HEAD_PATH).toString());
        data.put(SIGNATURE, sp.read(PERSONAL_INFO_LIBRARY).get(SIGNATURE).toString());
        data.put(ADDRESS, sp.read(PERSONAL_INFO_LIBRARY).get(ADDRESS).toString());
        data.put(CURRENT_TITLE_CODE, sp.read(PERSONAL_INFO_LIBRARY).get(CURRENT_TITLE_CODE).toString());
        data.put(LUCKY_VALUE, sp.read(PERSONAL_INFO_LIBRARY).get(LUCKY_VALUE).toString());
        return data;
    }

    /**
     * 判断是否为第一次读取用户的个人信息
     * @return 返回是否存储了用户个人信息的结果
     */
    public boolean isFirstSaveMySelfInformation(){
        return !sp.checkElement(PERSONAL_INFO_LIBRARY, "isSaved");
    }

    /**
     * 当有消息来临时，如果页面没有关闭，就先将消息存储到缓存中
     * 为了让FirstMainPageFragmentParent对缓存进行检测并对用户进行提示
     */
    public void saveIsMessageTip(String phoneNumber){
        sp.saveNumber(MESSAGE_TIP_LIBRARY, phoneNumber, 1, null);
    }

    /**
     * 将每个人发来的消息数目和消息人存入缓存中
     * 当传递进来phoneNumber地时候，获得当前未读的消息数目。
     * @param phoneNumber 电话号码
     * @return 返回当前电话号码的未读消息个数
     */
    public int readMessageNumber(String phoneNumber){
        SharedPreferences sp = context.getSharedPreferences(MESSAGE_TIP_LIBRARY, Context.MODE_PRIVATE);
        return sp.getInt(phoneNumber, 0);
    }

    /**
     * 当点击进入一个人的聊天页面，说明他的消息已经被看过来，此时应该将他的消息记录从缓存中清除。
     */
    public void handleMessageTip(String phoneNumber){
        SharedPreferences sp = context.getSharedPreferences(MESSAGE_TIP_LIBRARY, Context.MODE_PRIVATE);
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
        SharedPreferences sp = context.getSharedPreferences(MESSAGE_TIP_LIBRARY, Context.MODE_PRIVATE);
        return sp.getAll().size() > 1;
    }

    /**
     * 用在地图宝藏刷新的API
     * 用来检索当前时间和数据库中存入的时间（上次存入的时间）是否相差大于3
     * @param days 大于3返回true,否则返回false
     */
    public boolean checkMapTime(int days){
        LogUtil.d(TAG, DAYS + days);
        SharedPreferences sps = Application.getContext().getSharedPreferences(MAP_REFRESH_TIME_CHECK, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sps.edit();
        // 第一次的时候，只有过三天，才能重新存一次days
        if (days >= readMapTime() + 3){
            editor.putInt(DAYS, days);
            editor.apply();
            return true;
        }else if (readMapTime() > days){
            editor.putInt(DAYS, days);
            editor.apply();
            return false;
        }
        return false;
    }

    private int readMapTime(){
        SharedPreferences sp = context.getSharedPreferences(MAP_REFRESH_TIME_CHECK, Context.MODE_PRIVATE);
        return sp.getInt(DAYS, 0);
    }
}
