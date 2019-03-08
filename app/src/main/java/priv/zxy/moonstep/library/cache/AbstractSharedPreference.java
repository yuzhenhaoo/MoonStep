package priv.zxy.moonstep.library.cache;

import java.util.Map;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/21
 * 描述:
 **/

abstract class AbstractSharedPreference {

    abstract Map<String, ?> read();

    abstract void save(Map<String, String> data);

}
