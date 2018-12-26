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
    
    private static String LOGGING_LIBRARY = "logging";

    private static String PERSONAL_INFO_LIBRARY = "personalInfo";

    private static String MESSAGE_TIP_LIBRARY = "tip";

    private static String MAP_REFRESH_TIME_CHECK = "map";

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
        sp.saveElement(LOGGING_LIBRARY,"firstLog", true);
    }

    /**
     * 判断是否为第一次登陆
     * @return 是第一次登录的话，返回True
     *          非第一次登录的话，返回False
     */
    public Boolean isFirstLogin(){
        return !sp.checkElement(LOGGING_LIBRARY, "firstLog");
    }

    /**
     * 记录好友信息是否已经被加载过（既第一次检测）
     */
    public void dataInitialized(){
        sp.saveElement(LOGGING_LIBRARY, "dataIsInitialized", true);
    }

    /**
     * @return 数据库已经进行过初始化的话，就返回false,不然就返回true
     */
    public boolean isDataInitialized(){
        return !sp.checkElement(LOGGING_LIBRARY, "dataIsInitialized");
    }

    /**
     * 当登陆成功的时候存储用户的手机号和密码
     * @param username &
     * @param passwd &
     */
    public void setSuccessLoginInfo(String username, String passwd){
        Map<String, String> params = new HashMap<>();
        params.put("PhoneNumber", username);
        params.put("PassWd", passwd);
        sp.save(LOGGING_LIBRARY, params);
        sp.saveElement(LOGGING_LIBRARY, "IS_SUCCESS", true);
    }

    /**
     * 当用户登陆失败的时候设置用户的登陆检测字段为false,直到下次登陆成功为止不允许自动登陆
     */
    public void setFailLoginInfo(){
        sp.saveElement(LOGGING_LIBRARY, "IS_SUCCESS", false);
    }

    /**
     * 读取登陆信息
     * @return
     */
    public Map<String, String> readLoginInfo(){
        Map<String, String> data = new HashMap<>();
        data.put("PhoneNumber", sp.read(LOGGING_LIBRARY).get("PhoneNumber").toString());
        data.put("PassWd", sp.read(LOGGING_LIBRARY).get("PassWd").toString());
        return data;
    }

    /**
     * 判断上次登陆是否成功
     * @return
     */
    public boolean isSuccessLogin(){
        return sp.checkElement(LOGGING_LIBRARY, "IS_SUCCESS");
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
        params.put("nickName", nickName);
        params.put("phoneNumber", phoneNumber);
        params.put("gender", gender);
        params.put("raceCode", String.valueOf(raceCode));
        params.put("headPath", headPath);
        params.put("signature", userSignature);
        params.put("address", location);
        params.put("currentTitleCode", userCurrentTitle);
        params.put("luckyValue", String.valueOf(luckyValue));
        sp.save(PERSONAL_INFO_LIBRARY, params);
        sp.saveElement(PERSONAL_INFO_LIBRARY, "isSaved", true);
    }

    /**
     * 读取用户的个人信息
     * 必须得加入一个判断，如果已经存入过个人信息的话，才能够进行读取
     */
    public Map<String, String> readMySelfInformation(){
        Map<String, String> data = new HashMap<>();
        data.put("nickName", sp.read(PERSONAL_INFO_LIBRARY).get("nickName").toString());
        data.put("phoneNumber", sp.read(PERSONAL_INFO_LIBRARY).get("phoneNumber").toString());
        data.put("gender", sp.read(PERSONAL_INFO_LIBRARY).get("gender").toString());
        data.put("raceCode", sp.read(PERSONAL_INFO_LIBRARY).get("raceCode").toString());
        data.put("headPath", sp.read(PERSONAL_INFO_LIBRARY).get("headPath").toString());
        data.put("signature", sp.read(PERSONAL_INFO_LIBRARY).get("signature").toString());
        data.put("address", sp.read(PERSONAL_INFO_LIBRARY).get("address").toString());
        data.put("currentTitleCode", sp.read(PERSONAL_INFO_LIBRARY).get("currentTitleCode").toString());
        data.put("luckyValue", sp.read(PERSONAL_INFO_LIBRARY).get("luckyValue").toString());
        return data;
    }

    /**
     * 判断是否为第一次读取用户的个人信息
     * @return
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
     * @param phoneNumber
     * @return
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
        editor.commit();
    }

    /**
     * 检测当前是否有临时消息
     * @return 当sp中包含的消息只有message地时候，说明这个时候没有任何新来的消息，那么就返回false，若非这种情况，就返回true
     */
    public boolean isMessageTip(){
        SharedPreferences sp = context.getSharedPreferences(MESSAGE_TIP_LIBRARY, Context.MODE_PRIVATE);
        return sp.getAll().size() > 1;
    }

    /**
     * 从客户端接收当前时间，如果当前时间没有存入xml中，那么就存入（第一次）
     * 如果当前时间已经存入xml中，就检测此刻时间距离上次存入的时间是否已经经过了3天，如果已经超过了3天，就继续存入，
     * @param days
     */
    public boolean checkMapTime(int days){
        LogUtil.d(TAG, "days" + days);
        LogUtil.d(TAG, "readMapTime()" + readMapTime());
        SharedPreferences sps = Application.getContext().getSharedPreferences(MAP_REFRESH_TIME_CHECK, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sps.edit();

        if (days >= readMapTime() + 3){//第一次的时候，只有过三天，才能重新存一次days
            editor.putInt("days", days);
            editor.apply();
            return true;
        }else if (readMapTime() > days){
            editor.putInt("days", days);
            editor.apply();
            return false;
        }
        return false;
    }

    private int readMapTime(){
        SharedPreferences sp = context.getSharedPreferences(MAP_REFRESH_TIME_CHECK, Context.MODE_PRIVATE);
        return sp.getInt("days", 0);
    }
}
