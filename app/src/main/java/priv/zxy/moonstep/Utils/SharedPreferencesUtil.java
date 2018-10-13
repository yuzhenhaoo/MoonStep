package priv.zxy.moonstep.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Shader;

import java.util.HashMap;
import java.util.Map;

public class SharedPreferencesUtil {
    private Context context;

    public SharedPreferencesUtil(Context context){
        this.context = context;
    }

    //存储第一次的信息
    public void save_first_login(){
        SharedPreferences sp = context.getSharedPreferences("mysp",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Logging_ability","Is_first_logging");
        editor.apply();
    }

    //第一次登陆就返回true,否则返回false;
    public Boolean is_first_log(){
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
        editor.commit();
    }

    public void fixSuccessedLoginAccountAndPassword(String username, String passwd){
        SharedPreferences sp = context.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("UserName");
        editor.remove("PassWd");
        editor.putString("UserName", username);
        editor.putString("PassWd", passwd);
        editor.commit();
    }

    public Map<String, String> readLoginInfo(){
        Map<String, String> data = new HashMap<String, String>();
        SharedPreferences sp = context.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        data.put("UserName", sp.getString("UserName", ""));
        data.put("PassWd", sp.getString("PassWd",""));
        return data;
    }

    public void saveMySelfInformation(String nickName, String userLevel, String userPet, String userRace, String signature){
        SharedPreferences sp = context.getSharedPreferences("personalInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("nickName", nickName);
        editor.putString("userLevel", userLevel);
        editor.putString("userPet", userPet);
        editor.putString("userRace", userRace);
        editor.putString("signature", signature);
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
}
