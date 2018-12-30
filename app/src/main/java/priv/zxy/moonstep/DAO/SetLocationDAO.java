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
 * 描述: 设置位置
 **/

public class SetLocationDAO {

    private static final String TAG = "SetLocationDAO";

    private static SetLocationDAO instance;

    private static final String URL = UrlBase.LOCATION_SERVLET_URL;

    private static final String LOCATION_TAG = "location";

    public static SetLocationDAO getInstance() {
        if (instance == null){
            synchronized (SetLocationDAO.class){
                if (instance == null){
                    instance = new SetLocationDAO();
                }
            }
        }
        return instance;
    }

    public void LocationServlet(String phoneNumber,String address,String latitude,String longitude){
        //请求地址
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
