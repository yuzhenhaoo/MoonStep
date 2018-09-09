package priv.zxy.moonstep.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.EditText;
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

import priv.zxy.moonstep.main_page.MainActivity;

public class LoginUtil {

    private Context mContext;
    private Activity mActivity;
    public LoginUtil(Context context, Activity activity){
        this.mContext = context;
        this.mActivity = activity;
    }

    public void LoginRequest(final  String accountNumber, final String password, final EditText inputAccount, final EditText inputPassword){
        //请求地址
        String url = "http://120.79.154.236:8080/MoonStep/LoginServlet";
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
                                //做自己的登录成功操作，如页面跳转
                                jump_to_mainPage(mActivity);
                                SharedPreferencesUtil sp = new SharedPreferencesUtil(mContext);
                                //将数据存入
                                sp.saveSuccessedLoginAccountAndPassword(accountNumber, password);
                            } else {
                                //做自己的登录失败操作
                                FailToast1();
                            }
                        } catch (JSONException e) {
                            //做自己的请求异常操作
                            FailToast2();
                            Log.e("TAG", e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //做自己的响应错误操作，如Toast提示（“请稍后重试”等）
                FailTast3();
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AccountNumber", accountNumber);
                params.put("Password", password);
                return params;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }

    private void FailToast1(){
        Toast.makeText(mContext, "您的用户名或密码错误，请重新输入！", Toast.LENGTH_SHORT).show();
    }

    private void FailToast2(){
        Toast.makeText(mContext, "请检查您的网络是否连接！", Toast.LENGTH_SHORT).show();
    }

    private void FailTast3(){
        Toast.makeText(mContext, "请稍后重试", Toast.LENGTH_SHORT).show();
    }
    /**
     * 跳转到主页，将之前所有的activity全部清空
     */
    private void jump_to_mainPage(Activity thisActivity){
        Intent intent = new Intent(thisActivity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        thisActivity.startActivity(intent);
    }
}
