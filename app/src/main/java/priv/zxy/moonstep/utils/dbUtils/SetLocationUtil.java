package priv.zxy.moonstep.utils.dbUtils;

import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import priv.zxy.moonstep.kernel.Application;
import priv.zxy.moonstep.kernel.bean.ErrorCode;
import priv.zxy.moonstep.kernel.bean.ServiceBase;
import priv.zxy.moonstep.utils.LogUtil;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/21
 * 描述:
 **/

public class SetLocationUtil {
    private static final String TAG = "LoginUtil";

    private static SetLocationUtil instance;

    public static SetLocationUtil getInstance(){
        if (instance == null){
            synchronized (SetLocationUtil.class){
                if (instance == null){
                    instance = new SetLocationUtil();
                }
            }
        }
        return instance;
    }

    public void LocationServlet(Callback callback, final String phoneNumber, final String address, final double latitude, final double longitude){
        //请求地址
        String url = ServiceBase.LOCATION_SERVLET_URL;
        String tag = "location";
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
                        if (result.equals("success")) {
                            callback.onSuccess();
                        } else {
                            callback.onFail(ErrorCode.NetNotResponse);

                        }
                    } catch (JSONException e) {
                        //做自己的请求异常操作
                        callback.onFail(ErrorCode.JSONException);
                    }
                }, error -> {
            //做自己的响应错误操作，如Toast提示（“请稍后重试”等）
            callback.onFail(ErrorCode.NetNotResponse);
            LogUtil.e(TAG, error.getMessage());
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phoneNumber", phoneNumber);
                params.put("address", address);
                params.put("latitude", String.valueOf(latitude));
                params.put("longitude", String.valueOf(longitude));
                return params;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }

    public interface Callback{

        void onSuccess();

        void onFail(ErrorCode errorCode);
    }
}
