package priv.zxy.moonstep.Exception;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 创建人: zhang376358913
 * 创建时间: 2019/3/1 17:50
 * 类描述:
 * 修改人: zhang376358913
 * 修改时间: zhang376358913
 * 修改备注:
 */
public class ExceptionManager {
    private static ConcurrentHashMap<ExceptionCode, Exception> maps = new ConcurrentHashMap<>();

    public static void handleException(ExceptionCode code) {
        for(Iterator iterator = maps.entrySet().iterator(); iterator.hasNext();) {
            if (iterator.next().equals(code)) {
                maps.get(code).handleWay();
            }
        }
    }

    void add(Exception exception) {
        if (!maps.contains(exception.getCode())) {
            maps.put(exception.getCode(), exception);
        }
    }
}
