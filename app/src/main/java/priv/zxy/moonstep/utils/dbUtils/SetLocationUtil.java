package priv.zxy.moonstep.utils.dbUtils;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import priv.zxy.moonstep.data.bean.URLBase;
import priv.zxy.moonstep.utils.LogUtil;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/25
 * 描述: 设置位置
 **/

public class SetLocationUtil {

    private static final String TAG = "SetLocationUtil";
    private static SetLocationUtil instance;

    public static SetLocationUtil getInstance() {
        if (instance == null){
            synchronized (SetLocationUtil.class){
                if (instance == null){
                    instance = new SetLocationUtil();
                }
            }
        }
        return instance;
    }

    public void LocationServlet(String phoneNumber,String address,String latitude,String longitude){
        //请求地址
        String url = URLBase.LOCATION_SERVLET_URL;
        LogUtil.d(TAG, phoneNumber + "  " + address + "  " + latitude + "  " + longitude);
        AndroidNetworking.post(url)
                .addBodyParameter("phoneNumber", phoneNumber)
                .addBodyParameter("address", address)
                .addBodyParameter("latitude", latitude)
                .addBodyParameter("longtitude", longitude)
                .setTag("location")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        LogUtil.d(TAG, response.toString());
                    }

                    @Override
                    public void onError(ANError anError) {
                        LogUtil.d(TAG, anError.toString());
                    }
                });
    }


}
