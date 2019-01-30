package priv.zxy.moonstep.DAO;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import priv.zxy.moonstep.DAO.constant.DaoConstant;
import priv.zxy.moonstep.DAO.constant.UrlBase;
import priv.zxy.moonstep.util.LogUtil;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/25
 * 描述: 向服务器提交位置信息
 **/

public class PushLocationInfoDAO {

    private static final String TAG = "PushLocationInfoDAO";

    private static PushLocationInfoDAO instance;

    private static final String URL = UrlBase.LOCATION_SERVLET_URL;

    private static final String LOCATION_TAG = "location";

    public static PushLocationInfoDAO getInstance() {
        if (instance == null){
            synchronized (PushLocationInfoDAO.class){
                if (instance == null){
                    instance = new PushLocationInfoDAO();
                }
            }
        }
        return instance;
    }

    public void LocationServlet(String phoneNumber,String address,String latitude,String longitude){
        AndroidNetworking.post(URL)
                .addBodyParameter(DaoConstant.PHONE_NUMBER, phoneNumber)
                .addBodyParameter(DaoConstant.ADDRESS, address)
                .addBodyParameter(DaoConstant.LATITUDE, latitude)
                .addBodyParameter(DaoConstant.LONGTITUDE, longitude)
                .setTag(LOCATION_TAG)
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
