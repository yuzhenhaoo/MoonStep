package priv.zxy.moonstep.DAO;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.hyphenate.exceptions.HyphenateException;

import org.json.JSONException;
import org.json.JSONObject;

import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.data.bean.ErrorCodeEnum;
import priv.zxy.moonstep.DAO.constant.URLBase;
import priv.zxy.moonstep.helper.EMHelper;
import priv.zxy.moonstep.util.LogUtil;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/24
 * 描述: 注册请求类
 **/

public class RegisterDataRequestDAO {

    private static final String TAG = "RegisterDataRequestDAO";

    private static RegisterDataRequestDAO instance;

    private static final String MOONSTEP = "moonstep";

    private static final String REGISTER_TAG = "register";

    private static final String PARSE_RESULT = "result";

    private static final String PARSE_PARAMS = "params";

    private static final String PARSE_SUCCESS = "success";

    private static final String PARSE_ERROR = "error";

    private static final String URL = URLBase.REGISTER_SERVLET_URL;

    public static RegisterDataRequestDAO getInstance() {
        if (instance == null){
            synchronized (RegisterDataRequestDAO.class){
                if (instance == null){
                    instance = new RegisterDataRequestDAO();
                }
            }
        }
        return instance;
    }

    public void RegisterRequest(final CallBack callBack, final String phoneNumber,
                                final  String nickName, final String password, final String gender){
        AndroidNetworking.post(URL)
                .addBodyParameter("PhoneNumber", phoneNumber)
                .addBodyParameter("NickName", nickName)
                .addBodyParameter("PassWord", password)
                .addBodyParameter("Gender", gender)
                .addHeaders("Charset", "ISO-8859-1")
                .setTag(REGISTER_TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = (JSONObject) new JSONObject(response.toString()).get(PARSE_PARAMS);
                            String result = jsonObject.getString(PARSE_RESULT);
                            if (result.equals(PARSE_SUCCESS)){
                                final String raceCode = jsonObject.getString("raceCode");
                                final String raceName = jsonObject.getString("raceName");
                                final String raceDescription = jsonObject.getString("raceDescription");
                                final String raceImage = jsonObject.getString("raceImagePath");
                                final String raceIcon = jsonObject.getString("raceIcon");
                                registerEMAccount(callBack, raceCode, raceName, raceDescription, raceImage, raceIcon, phoneNumber,
                                        nickName, password);
                            } else if (result.equals(PARSE_ERROR)){
                                callBack.onFail(ErrorCodeEnum.PHONE_NUMBER_IS_REGISTERED);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callBack.onFail(ErrorCodeEnum.JSON_EXCEPTION);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        callBack.onFail(ErrorCodeEnum.NET_NOT_RESPONSE);
                    }
                });
    }

    private void registerEMAccount(final CallBack callBack, final String raceCode,
                                   final  String raceName, final String raceDescription, final String raceImage, String raceIcon,
                                   final String phoneNumber, final  String nickName, final String password){
        EMHelper.getInstance(Application.getContext()).registerUser(new EMHelper.CallBack() {

            @Override
            public void onSuccess(String obj) {

            }

            @Override
            public void onSuccess() {
                try{
                    callBack.onSuccess(raceCode, raceName, raceDescription, raceImage, raceIcon);
                }catch (HyphenateException e){
                    LogUtil.d(TAG, e.getErrorCode() + e.getMessage());
                }
            }

            @Override
            public void onFail() {
                callBack.onFail(ErrorCodeEnum.PASSWORD_FORMAT_IS_NOT_RIGHT);
            }

            @Override
            public void onFail(ErrorCodeEnum errorCodeEnum) {

            }

        }, MOONSTEP + phoneNumber, password, nickName);
    }

    public interface CallBack{

        void onSuccess(String raceCode, String raceName, String raceDescription, String raceImage, String raceIcon) throws HyphenateException;

        void onFail(ErrorCodeEnum errorCode);
    }

}
