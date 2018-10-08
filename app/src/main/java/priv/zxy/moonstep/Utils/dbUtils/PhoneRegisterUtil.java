package priv.zxy.moonstep.Utils.dbUtils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import priv.zxy.moonstep.kernel_data.bean.ErrorCode;

public class PhoneRegisterUtil {

    private Context mContext;
    private Activity mActivity;
    public PhoneRegisterUtil(Context context, Activity activity){
        this.mContext = context;
        this.mActivity = activity;
    }

    public static boolean registeResult = false;
    public static ErrorCode errorCode;
    /**
     * 用来处理手机注册的请求
     *
     * @param PhoneNumber 手机账号
     * @param NickName 用户名
     * @param PassWord 密码
     * @Param Gender 性别
     */
    public void RegisterRequest(final String PhoneNumber, final  String NickName, final String PassWord, final String Gender){
        //请求地址
        String url = "http://120.79.154.236:8080/MoonStep/NewServlet";
        String tag = "Login";
        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
        final StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");
                            String result = jsonObject.getString("Result");
                            if(result.equals("success")){
                                registeResult = true;
                            }else{
                                errorCode = ErrorCode.PasswordFormatISNotRight;
                            }
                        } catch (JSONException e) {
                            //做自己的请求异常操作
                            errorCode = ErrorCode.JSONException;
                            Log.i("TAG", e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //做自己的响应错误操作，如Toast提示（“请稍后重试”等）
                errorCode = ErrorCode.NetNotResponse;
                Log.i("TAG", error.getMessage(), error);
            }
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
