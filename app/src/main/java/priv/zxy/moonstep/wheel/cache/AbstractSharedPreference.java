package priv.zxy.moonstep.wheel.cache;

import java.util.Map;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/21
 * 描述: 抽象化SharedPreferences类，使用装饰设计模式和模板方法模式构造SharedPreferences库
 **/

public abstract class AbstractSharedPreference {

    abstract Map<String, ?> read();

    abstract void save(Map<String, String> data);
}
