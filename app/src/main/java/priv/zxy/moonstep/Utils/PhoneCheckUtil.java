package priv.zxy.moonstep.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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

import priv.zxy.moonstep.login_activity.RegisterPhone2;

public class PhoneCheckUtil {
    private Context mContext;
    private Activity mActivity;
    public PhoneCheckUtil(Context context, Activity activity){
        this.mContext = context;
        this.mActivity = activity;
    }

    public void phoneOrEmailCheck(final String phoneNumber){
        //请求地址
        String url = "http://120.79.154.236:8080/MoonStep/CheckPhoneServlet";
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
                            if (result.equals("success")) {
                                //检验成功
                                if( !phoneNumber.equals(""))
                                    jumpToTheSecondPhonePage(mActivity, phoneNumber);
                                else
                                    Warning();
                            } else {
                                //检验失败
                                Fail();
                            }
                        } catch (JSONException e) {
                            //做自己的请求异常操作
                            Error();
                            Log.e("TAG", e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //做自己的响应错误操作，如Toast提示（“请稍后重试”等）
                Error();
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("PhoneNumber", phoneNumber);
                return params;
            }
        };
        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }

    private void Error(){
        Toast.makeText(mContext, "服务器响应错误，稍后重试", Toast.LENGTH_SHORT).show();
    }

    private void Fail(){
        Toast.makeText(mContext, "您的号码已经注册过了，请换一个重试", Toast.LENGTH_SHORT).show();
    }

    private void jumpToTheSecondPhonePage(Activity thisActivity, String phoneNumber){
        Bundle bundle = new Bundle();
        bundle.putString("phoneNumber", phoneNumber);
        Intent intent = new Intent(thisActivity, RegisterPhone2.class);
        intent.putExtras(bundle);
        thisActivity.startActivity(intent);
    }

    private void Warning(){
        Toast.makeText(mContext, "手机账号不能位空", Toast.LENGTH_SHORT).show();
    }
}
