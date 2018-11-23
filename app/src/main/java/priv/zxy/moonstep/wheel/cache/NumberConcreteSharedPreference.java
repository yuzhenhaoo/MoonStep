package priv.zxy.moonstep.wheel.cache;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/21
 * 描述:
 **/

public class NumberConcreteSharedPreference extends ConcreteSharedPreference{

    private String element = null;
    private int number = 0;
    private Context context;
    private static int mode = 0;

    public NumberConcreteSharedPreference(Context context) {
        super(context);
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void save(Map<String, String> data){
        super.save(data);
        SharedPreferences sp = context.getSharedPreferences(super.getLibrary(), mode);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(element, sp.getInt(element, 0) + number);
        editor.apply();
    }

    public int readNumber(){
        SharedPreferences sp = context.getSharedPreferences("tip", Context.MODE_PRIVATE);
        return sp.getInt(element, 0);
    }
}
