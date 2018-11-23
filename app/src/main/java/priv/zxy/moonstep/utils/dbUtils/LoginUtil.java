package priv.zxy.moonstep.utils.dbUtils;

import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import priv.zxy.moonstep.kernel.Application;
import priv.zxy.moonstep.kernel.MessageReceiverService;
import priv.zxy.moonstep.kernel.bean.ServiceBase;
import priv.zxy.moonstep.login.module.biz.OnLoginListener;
import priv.zxy.moonstep.utils.LogUtil;
import priv.zxy.moonstep.utils.SharedPreferencesUtil;
import priv.zxy.moonstep.kernel.bean.ErrorCode;

public class LoginUtil {

    private static final String TAG = "LoginUtil";

    private static LoginUtil instance;

    public static LoginUtil getInstance(){
        if (instance == null){
            synchronized (LoginUtil.class){
                if (instance == null){
                    instance = new LoginUtil();
                }
            }
        }
        return instance;
    }

    public void LoginRequest(final OnLoginListener loginListener, final  String phoneNumber, final String inputPassword){
        //请求地址
        String url = ServiceBase.LOGIN_SERVLET_URL;
        String tag = "login";
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
//                                isSuccess = true;

                            EMClient.getInstance().login("moonstep" + phoneNumber,inputPassword,new EMCallBack() {//回调
                                @Override
                                public void onSuccess() {
                                    //保证进入主页面后本地会话和群组都 load 完毕。
                                    new Thread(() -> {
                                        EMClient.getInstance().groupManager().loadAllGroups();
                                        EMClient.getInstance().chatManager().loadAllConversations();
                                    }).start();
                                    loginListener.LoginSuccess();//只有当环信服务器也登陆成功的时候才触发回调方法
                                    LogUtil.d(TAG, "登录聊天服务器成功！");
                                }

                                @Override
                                public void onProgress(int progress, String status) {

                                }

                                @Override
                                public void onError(int code, String message) {
                                    LogUtil.d(TAG, "登录聊天服务器失败！" + code + message);
                                }
                            });

                            //将数据存入
                            SharedPreferencesUtil.getInstance(Application.getContext()).setSuccessLoginInfo(phoneNumber, inputPassword);

                            //我们需要在登录成功的时候再次启动一次MessageReceiverService
                            Application.getContext().startService(new Intent(Application.getContext(), MessageReceiverService.class));
                        } else {
                            loginListener.LoginFail(ErrorCode.PhoneNumberOrPasswordIsWrong);

                            //将mysp文件中的登录成功标记位置为false
                            SharedPreferencesUtil.getInstance(Application.getContext()).setFailLoginInfo();
                        }
                    } catch (JSONException e) {
                        //做自己的请求异常操作
                        loginListener.LoginFail(ErrorCode.JSONException);
                        LogUtil.e(TAG, e.getMessage());
                    }
                }, error -> {
                    //做自己的响应错误操作，如Toast提示（“请稍后重试”等）
            loginListener.LoginFail(ErrorCode.NetNotResponse);
                    LogUtil.e(TAG, error.getMessage());
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("PhoneNumber", phoneNumber);
                params.put("PassWord", inputPassword);
                return params;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }
}
