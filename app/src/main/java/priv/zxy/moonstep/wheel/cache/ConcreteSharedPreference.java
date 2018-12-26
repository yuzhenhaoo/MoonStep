package priv.zxy.moonstep.wheel.cache;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

import priv.zxy.moonstep.util.LogUtil;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/21
 * 描述: 装饰模式的Conrete类，是SharedPreference的具体化实现
 *      具体的SharedPreferencesUtil中的内容封装在装饰类的子类之中
 *      该Concrete类所做的具体是数据的存取操作
 **/

public class ConcreteSharedPreference extends AbstractSharedPreference {

    private String library = null;
    private Context context = null;
    private static int mode = 0;
    private static ConcreteSharedPreference instance;

    public ConcreteSharedPreference(Context context){
        this.context = context;
    }

    public static ConcreteSharedPreference getInstance(Context mContext) {
        if (instance == null){
            synchronized (ConcreteSharedPreference.class){
                if (instance == null){
                    instance = new ConcreteSharedPreference(mContext);
                    mode = Context.MODE_PRIVATE;
                }
            }
        }
        return instance;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    public String getLibrary() {
        return library;
    }

    /**
     * 重写read的方法
     * @return 一个泛型为<String, ?>的集合
     */
    @Override
    Map<String, ?> read() {
        Map<String, ?> data;
        SharedPreferences sp = context.getSharedPreferences(library, Context.MODE_PRIVATE);
        data = sp.getAll();
        LogUtil.d("ConcreteSharedPreference", "read" + data.toString());
        return data;
    }

    /**
     * 重写save方法，需要先制定库
     * @param data 传入一个泛型为<String, String>的集合
     */
    @Override
    void save(Map<String, String> data) {
        if (context != null){
            SharedPreferences sp = context.getSharedPreferences(library, mode);
            SharedPreferences.Editor editor = sp.edit();
            for (String key : data.keySet()){
                editor.putString(key, data.get(key));
            }
            editor.apply();
            LogUtil.d("ConcreteSharedPreference", "save:" + data.toString());
        }else{
            /**
             * 这里跳出context为空的异常提示，并终止运行，可以让ContextIsNullException继承于NullPointerException
             * 然后将异常存储到日志信息中
             */
        }

    }
}
