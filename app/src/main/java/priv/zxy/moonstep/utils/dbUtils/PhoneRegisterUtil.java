package priv.zxy.moonstep.utils.dbUtils;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import priv.zxy.moonstep.db.MoonFriend;
import priv.zxy.moonstep.helper.EMHelper;
import priv.zxy.moonstep.EM.bean.VolleyCallback;
import priv.zxy.moonstep.kernel.Application;
import priv.zxy.moonstep.kernel.bean.ErrorCode;
import priv.zxy.moonstep.kernel.bean.ServiceBase;
import priv.zxy.moonstep.utils.LogUtil;

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
    public void RegisterRequest(final VolleyCallback volleyCallback, final String PhoneNumber, final  String NickName, final String PassWord, final String Gender){
        //请求地址
        String url = ServiceBase.REGISTER_SERVLET_URL;
        String tag = "LogUtilin";
        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(Application.getContext());

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
        final StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");
                        String result = jsonObject.getString("Result");
                        if (result.equals("success")){
                            EMHelper.getInstance(Application.getContext()).registerUser(new VolleyCallback() {
                                @Override
                                public String onSuccess(String result) throws InterruptedException {
                                    volleyCallback.onSuccess();
                                    return null;
                                }

                                @Override
                                public boolean onSuccess() {
                                    return false;
                                }

                                @Override
                                public String onFail(String error) {
                                    return null;
                                }

                                @Override
                                public boolean onFail() {
                                    volleyCallback.getErrorCode(ErrorCode.PasswordFormatISNotRight);
                                    return false;
                                }

                                @Override
                                public void getMoonFriend(MoonFriend moonFriend) {

                                }

                                @Override
                                public void getErrorCode(ErrorCode errorCode) {

                                }
                            }, "moonstep" + PhoneNumber, PassWord, NickName);
                        }
                    } catch (JSONException e) {
                        //做自己的请求异常操作
                        volleyCallback.getErrorCode(ErrorCode.JSONException);
                        LogUtil.e(TAG, e.getMessage());
                    }
                }, error -> {
                    volleyCallback.getErrorCode(ErrorCode.NetNotResponse);
                    LogUtil.i(TAG, error.getMessage());
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("PhoneNumber", PhoneNumber);
                params.put("NickName",NickName);
                params.put("PassWord", PassWord);
                params.put("Gender",Gender);
                return params;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }
}
