package priv.zxy.moonstep.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import priv.zxy.moonstep.helper.EMHelper;

public class SharedPreferencesUtil {
    private static final String TAG = "SharedPreferencesUtil";

    private Context context;

    private static SharedPreferencesUtil instance;

    private SharedPreferencesUtil(Context context){
        this.context = context;
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
    public void saveFirstLogin(){
        SharedPreferences sp = context.getSharedPreferences("mysp",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Logging_ability","Is_first_Logging");
        editor.apply();
    }

    /**
     * 检测当前是否为第一次登陆
     * @return
     */
    public Boolean isFirstLogin(){
        SharedPreferences sp = context.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        return !sp.contains("Logging_ability");
    }

    /**
     * 当第一登入的时候，需要使用MoonFriendService来初始化好友信息，初始化的时候使用SharedPreferences来存储记录
     */
    public void saveIsInitedDataBase(){
        SharedPreferences sp = context.getSharedPreferences("mysp",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("IsInitedDataBase","true");
        editor.apply();
    }

    /**
     * 数据库已经进行过初始化的话，就返回false,不然就返回true
     * @return
     */
    public boolean readInitDataBase(){
        SharedPreferences sp = context.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        return !sp.contains("IsInitedDataBase");
    }

    public void saveSuccessedLoginAccountAndPassword(String username, String passwd){
        SharedPreferences sp = context.getSharedPreferences("mysp",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("UserName", username);
        editor.putString("PassWd", passwd);
        editor.putBoolean("IsSuccessed", true);
        editor.commit();
    }

    public void fixSuccessedLoginAccountAndPassword(String username, String passwd){
        SharedPreferences sp = context.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("UserName", username);
        editor.putString("PassWd", passwd);
        editor.putBoolean("IsSuccessed", true);
        editor.commit();
    }

    public void fixFailLoginInfo(){
        SharedPreferences sp = context.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("IsSuccessed", false);
        editor.commit();
    }

    public Map<String, String> readLoginInfo(){
        Map<String, String> data = new HashMap<String, String>();
        SharedPreferences sp = context.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        data.put("UserName", sp.getString("UserName", ""));
        data.put("PassWd", sp.getString("PassWd",""));
        return data;
    }

    public boolean isSuccessedLogined(){
        SharedPreferences sp = context.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        return sp.getBoolean("IsSuccessed", false);
    }

    public void saveMySelfInformation(String nickName, String userRaceName, String userRaceDiscription, String headPath, String userSignature, String userCurrentTitle, String userLevel, String userLevelDiscription){
        SharedPreferences sp = context.getSharedPreferences("personalInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("nickName", nickName);
        editor.putString("userRaceName", userRaceName);
        editor.putString("userRaceDiscription", userRaceDiscription);
        editor.putString("userHeadPath", headPath);
        editor.putString("userSignature", userSignature);
        editor.putString("userCurrentTitle", userCurrentTitle);
        editor.putString("userLevel", userLevel);
        editor.putString("userLevelDiscription", userLevelDiscription);
        editor.putString("isSaved", "");
        editor.commit();
    }

    public Map<String, String> readMySelfInformation(){
        Map<String, String> data = new HashMap<String, String>();
        SharedPreferences sp = context.getSharedPreferences("personalInfo", Context.MODE_PRIVATE);
        data.put("nickName", sp.getString("nickName", ""));
        data.put("userLevel", sp.getString("userLevel", ""));
        data.put("userPet", sp.getString("userPet", ""));
        data.put("userRace", sp.getString("userRace", ""));
        data.put("signature", sp.getString("signature", ""));
        return data;
    }

    public boolean isFirstSaveMySelfInformation(){
        SharedPreferences sp = context.getSharedPreferences("personalInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        return !sp.contains("isSaved");
    }

    /**
     * 当有消息来临时，如果页面没有关闭，就先将消息存储到缓存中
     * 为了让FirstMainPageFragmentParent对缓存进行检测并对用户进行提示
     */
    public void saveIsMessageTip(String phoneNumber){
        SharedPreferences sp = context.getSharedPreferences("tip", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("message", "");
        editor.putInt(phoneNumber, sp.getInt(phoneNumber, 0) + 1);
        editor.commit();
    }

    /**
     * 将每个人发来的消息数目和消息人存入缓存中
     * 当传递进来phoneNumber地时候，获得当前未读的消息数目。
     * @param phoneNumber
     * @return
     */
    public int readMessageNumber(String phoneNumber){
        SharedPreferences sp = context.getSharedPreferences("tip", Context.MODE_PRIVATE);
        return sp.getInt(phoneNumber, 0);
    }

    /**
     * 当点击进入一个人的聊天页面，说明他的消息已经被看过来，此时应该将他的消息记录从缓存中清除。
     */
    public void handleMessageTip(String phoneNumber){
        SharedPreferences sp = context.getSharedPreferences("tip", Context.MODE_PRIVATE);
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
        SharedPreferences sp = context.getSharedPreferences("tip", Context.MODE_PRIVATE);
        return sp.getAll().size() > 1;
    }
}
