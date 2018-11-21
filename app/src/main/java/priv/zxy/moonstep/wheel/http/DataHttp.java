package priv.zxy.moonstep.wheel.http;

import java.util.Map;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/20
 * 描述:
 **/
public class DataHttp extends HttpBase {

    private static String URL;

    public static void setURL(String URL) {
        DataHttp.URL = URL;
    }

    public static String getURL() {
        return URL;
    }

    @Override
    void doPost(OnHttpResultListener onHttpResultListener) {
        super.doPost(onHttpResultListener);
    }

    @Override
    void doPost(OnHttpResultListener onHttpResultListener, Map<String, String> params, Map<String, String> headers) {
        super.doPost(onHttpResultListener, params, headers);
    }
}
