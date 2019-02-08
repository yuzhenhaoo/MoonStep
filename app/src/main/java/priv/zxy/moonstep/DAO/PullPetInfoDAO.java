package priv.zxy.moonstep.DAO;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import priv.zxy.moonstep.DAO.constant.DaoConstant;
import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.data.bean.ErrorCodeEnum;
import priv.zxy.moonstep.DAO.constant.UrlBase;
import priv.zxy.moonstep.framework.pet.Pet;
import priv.zxy.moonstep.util.JSONUtil;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/7
 * 描述: 下拉用户的宠物信息
 **/
public class PullPetInfoDAO {

    private static PullPetInfoDAO instance = null;

    private static final String PET_TAG = "pet";

    private static final String URL = UrlBase.GET_PET_INFO_SERVLET_URL;

    public static PullPetInfoDAO getInstance() {
        if (instance == null) {
            instance = new PullPetInfoDAO();
        }
        return instance;
    }

    public void getPetInfo(CallBack callBack, String phoneNumber) {
        //获得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(Application.getContext());
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                response -> {
                    try {
                        Pet pet = JSONUtil.handlePetResponse(response);
                        callBack.onSuccess(pet);
                    } catch (Exception e) {
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

        void onSuccess(Pet pet);

        void onFail(ErrorCodeEnum errorCodeEnum);
    }
}
