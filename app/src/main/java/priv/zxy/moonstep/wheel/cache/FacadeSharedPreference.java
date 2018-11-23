package priv.zxy.moonstep.wheel.cache;

import android.content.Context;

import java.util.Map;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/21
 * 描述:AbstractSharedPreference子类的外观类
 * 使用外观类，隐藏子系统的细节，只暴露相应的接口给外部客户端
 **/
public class FacadeSharedPreference {

    JudgeConcreteSharedPreference judgeConcreteSharedPreference;

    NumberConcreteSharedPreference numberConcreteSharedPreference;

    ConcreteSharedPreference sharedPreference;

    public FacadeSharedPreference(Context context){
        sharedPreference = new ConcreteSharedPreference(context);
        judgeConcreteSharedPreference = new JudgeConcreteSharedPreference(context);
        numberConcreteSharedPreference = new NumberConcreteSharedPreference(context);
    }

    public void save(String libraryName, Map<String, String> data){
        sharedPreference.setLibrary(libraryName);
        sharedPreference.save(data);
    }

    public Map<String, ?> read(String libraryName){
        sharedPreference.setLibrary(libraryName);
        return sharedPreference.read();
    }

    public void saveNumber(String libraryName, String element, int number, Map<String, String> data){
        numberConcreteSharedPreference.setLibrary(libraryName);
        numberConcreteSharedPreference.setNumber(number);
        numberConcreteSharedPreference.setElement(element);
        numberConcreteSharedPreference.save(data);
    }

    public int readNumber(String libraryName, String element){
        numberConcreteSharedPreference.setLibrary(libraryName);
        numberConcreteSharedPreference.setElement(element);
        return numberConcreteSharedPreference.readNumber();
    }

    public boolean checkElement(String libraryName, String element){
        judgeConcreteSharedPreference.setLibrary(libraryName);
        judgeConcreteSharedPreference.setElement(element);
        return judgeConcreteSharedPreference.check();
    }

    public void saveElement(String library, String element, boolean b){
        judgeConcreteSharedPreference.setLibrary(library);
        judgeConcreteSharedPreference.setElement(element);
        judgeConcreteSharedPreference.setB(b);
        judgeConcreteSharedPreference.save();
    }
}
