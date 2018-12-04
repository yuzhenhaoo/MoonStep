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

import priv.zxy.moonstep.data.bean.URLBase;
import priv.zxy.moonstep.framework.user.User;
import priv.zxy.moonstep.helper.EMHelper;
import priv.zxy.moonstep.data.bean.VolleyCallback;
import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.data.bean.ErrorCode;
import priv.zxy.moonstep.utils.LogUtil;

public class ChangePasswordUtil {

    private static final String TAG = "ChangePasswordUtil";

    private static ChangePasswordUtil instance;

    public static ChangePasswordUtil getInstance(){
        if (instance == null){
            synchronized (ChangePasswordUtil.class){
                if (instance == null){
                    instance = new ChangePasswordUtil();
                }
            }
        }
        return instance;
    }

    public void changePassword(final VolleyCallback volleyCallback, final String phoneNumber, final String password) {
        //请求地址
        String url = URLBase.CHANGE_PASSWORD_SERVLET_URL;
        String tag = "Login";
        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(Application.getContext());

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
        final StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        LogUtil.i("TAG", "onResponse");
                        JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");
                        String result = jsonObject.getString("Result");
                        switch (result) {
                            case "success":
                                EMHelper.getInstance(Application.getContext()).changePassword(new VolleyCallback() {
                                    @Override
                                    public String onSuccess(String result) {
                                        return null;
                                    }

                                    @Override
                                    public boolean onSuccess() throws InterruptedException {
                                        //检验成功
                                        volleyCallback.onSuccess();
                                        return false;
                                    }

                                    @Override
                                    public String onFail(String error) {
                                        return null;
                                    }

                                    @Override
                                    public boolean onFail() {
                                        return false;
                                    }

                                    @Override
                                    public void getMoonFriend(User moonFriend) {

                                    }

                                    @Override
                                    public void getErrorCode(ErrorCode errorCode) {

                                    }
                                }, "moonstep" + phoneNumber, password);
                                break;
                            case "error0":
                                //检验失败
                                volleyCallback.getErrorCode(ErrorCode.PhoneNumberIsNotRegistered);
                                break;
                            case "error1":
                                volleyCallback.getErrorCode(ErrorCode.ServerIsFault);
                                break;
                        }
                    } catch (JSONException e) {
                        //做自己的请求异常操作
                        volleyCallback.getErrorCode(ErrorCode.JSONException);
                        LogUtil.e("TAG", e.getMessage());
                    }
                }, error -> {
                    //做自己的响应错误操作，如Toast提示（“请稍后重试”等）
                    volleyCallback.getErrorCode(ErrorCode.JSONException);
                    LogUtil.e("TAG", error.getMessage());
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("PhoneNumber", phoneNumber);
                params.put("PassWord", password);
                return params;
            }
        };
        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }
}
