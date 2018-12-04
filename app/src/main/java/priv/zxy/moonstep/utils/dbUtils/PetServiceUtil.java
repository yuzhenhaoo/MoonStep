package priv.zxy.moonstep.utils.dbUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import priv.zxy.moonstep.data.bean.VolleyCallback;
import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.data.bean.ErrorCode;
import priv.zxy.moonstep.data.bean.URLBase;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/7
 * 描述:
 **/
public class PetServiceUtil {

    private static PetServiceUtil instance = null;

    public static PetServiceUtil getInstance(){
        if (instance == null){
            synchronized (PetServiceUtil.class){
                if (instance == null){
                    instance = new PetServiceUtil();
                }
            }
        }
        return instance;
    }

    public void getPetInfo(VolleyCallback volleyCallback, String phoneNumber){
        String url = URLBase.GET_PET_INFO_SERVLET_URL;
        String tag = "getPetInfo";

        //获得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(Application.getContext());
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonObject = (JSONObject) new JSONObject(response).get("result");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                volleyCallback.getErrorCode(ErrorCode.NetNotResponse);
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("PhoneNumber", phoneNumber);
                return params;
            }
        };
        request.setTag(tag);

        requestQueue.add(request);
    }
}
