package priv.zxy.moonstep.DAO;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import priv.zxy.moonstep.DAO.constant.DaoConstant;
import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.data.bean.ErrorCodeEnum;
import priv.zxy.moonstep.DAO.constant.UrlBase;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/7
 * 描述:
 **/
public class PetServiceDAO {

    private static PetServiceDAO instance = null;

    private static final String PET_TAG = "pet";

    private static final String URL = UrlBase.GET_PET_INFO_SERVLET_URL;

    public static PetServiceDAO getInstance() {
        if (instance == null) {
            instance = new PetServiceDAO();
        }
        return instance;
    }

    public void getPetInfo(CallBack callBack, String phoneNumber) {

        //获得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(Application.getContext());
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                response -> {
                    try {
                        JSONObject jsonObject = (JSONObject) new JSONObject(response).get(DaoConstant.RESULT);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            callBack.onFail(ErrorCodeEnum.NET_NOT_RESPONSE);
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(DaoConstant.PHONE_NUMBER, phoneNumber);
                return params;
            }
        };
        request.setTag(PET_TAG);

        requestQueue.add(request);
    }

    public interface CallBack {

        void onSuccess();

        void onFail(ErrorCodeEnum errorCodeEnum);
    }
}
