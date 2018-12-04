package priv.zxy.moonstep.utils.dbUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hyphenate.exceptions.HyphenateException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import priv.zxy.moonstep.helper.EMHelper;
import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.data.bean.ErrorCode;
import priv.zxy.moonstep.data.bean.URLBase;
import priv.zxy.moonstep.utils.LogUtil;

/**
 * 注册账号
 */
public class PhoneRegisterUtil {

    private static final String TAG = "PhoneRegisterUtil";

    private static PhoneRegisterUtil instance;

    public static PhoneRegisterUtil getInstance() {
        if (instance == null){
            synchronized (PhoneRegisterUtil.class){
                if (instance == null){
                    instance = new PhoneRegisterUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 用来处理手机注册的请求
     *
     * @param PhoneNumber 手机账号
     * @param NickName 用户名
     * @param PassWord 密码
     * @Param Gender 性别
     */
    public void RegisterRequest(final CallBack callBack, final String PhoneNumber, final  String NickName, final String PassWord, final String Gender){
        //请求地址
        String url = URLBase.REGISTER_SERVLET_URL;
        String tag = "Login";
        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(Application.getContext());

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
        final StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");
                        String result = jsonObject.getString("result");
                        if (result.equals("success")){
                            final String raceCode = jsonObject.getString("raceCode");
                            final String raceName = jsonObject.getString("raceName");
                            final String raceDescription = jsonObject.getString("raceDescription");
                            final String raceImage = jsonObject.getString("raceImagePath");
                            final String raceIcon = jsonObject.getString("raceIcon");
                            EMHelper.getInstance(Application.getContext()).registerUser(new EMHelper.Callback() {

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
                                    callBack.onFail(ErrorCode.PasswordFormatISNotRight);
                                }

                            }, "moonstep" + PhoneNumber, PassWord, NickName);
                        }else if (result.equals("error")){
                            callBack.onFail(ErrorCode.PhoneNumberIsRegistered);
                        }
                    } catch (JSONException e) {
                        //做自己的请求异常操作
                        callBack.onFail(ErrorCode.JSONException);
                        LogUtil.e(TAG, e.getMessage());
                    }
                }, error -> callBack.onFail(ErrorCode.NetNotResponse)) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("PhoneNumber", PhoneNumber);
                params.put("NickName",NickName);
                params.put("PassWord", PassWord);
                params.put("Gender",Gender);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Charset", "ISO-8859-1");
                return headers;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }

    public interface CallBack{

        void onSuccess(String raceCode, String raceName, String raceDescription, String raceImage, String raceIcon) throws HyphenateException;

        void onFail(ErrorCode errorCode);
    }
}
