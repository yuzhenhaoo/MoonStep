package priv.zxy.moonstep.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class SharedPreferencesUtil {
    private Context context;

    public SharedPreferencesUtil(){
    }

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
        if(sp.contains("Logging_ability")){
            return false;
        }else{
            return true;
        }
    }

    public void saveSuccessedLoginAccountAndPassword(String username, String passwd){
        SharedPreferences sp = context.getSharedPreferences("mysp",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
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
}