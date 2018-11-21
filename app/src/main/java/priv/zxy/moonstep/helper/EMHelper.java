package priv.zxy.moonstep.helper;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import priv.zxy.moonstep.EM.bean.VolleyCallback;
import priv.zxy.moonstep.kernel.Application;
import priv.zxy.moonstep.kernel.bean.EMBase;
import priv.zxy.moonstep.utils.LogUtil;

public class EMHelper {

    private static final String TAG = "EMHelper";

    private static EMHelper instance;

    private static String token = null;

    private Context mContext;

    private String Authorization = null;//初始化后的格式为Bearer ${token}

    private EMHelper(Context context) {
        this.mContext = context;
    }

    /**
     * 双重锁定模式
     *
     * @param mContext 上下文
     * @return EMHelper的实例
     */
    public static EMHelper getInstance(Context mContext) {
        if (instance == null) {
            synchronized (EMHelper.class) {
                if (instance == null) {
                    instance = new EMHelper(mContext);
                }
            }
        }
        return instance;
    }

    /**
     * 获得token值
     */
    public void initToken(GetTokenCallback tokenCallback){
        //请求地址
        String url = EMBase.getInstance().getBaseRequest() + EMBase.getInstance().getOrgName() + "/" + EMBase.getInstance().getAppName() + "/token";
        String tag = "token";
        Log.d(TAG, "initToken: " + url);
        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);


//        final String requestBody = "{\"grant_type\": \"client_credentials\", \"client_id\": " + EMBase.getInstance().getClient_ID() + ", \"client_secret\": \"" + EMBase.getInstance().getClient_Secret() + "}";
        final String requestBody = "{" + "\"" + "grant_type" + "\"" + ":" + "\"" + "client_credentials" + "\"" + "," + "\"" + "client_id" + "\"" + ":" + "\"" + EMBase.getInstance().getClient_ID() + "\"" + "," + "\"" + "client_secret" + "\"" + ":" + "\"" + EMBase.getInstance().getClient_Secret() + "\"" + "}";
        LogUtil.d(TAG, "requsetBody = " + requestBody);

        StringRequest request = new StringRequest(Request.Method.POST,
                url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        token = jsonObject.getString("access_token");
                        int timeStamp = jsonObject.getInt("expires_in");
                        tokenCallback.onGetToken(token);
                        LogUtil.d(TAG, "token获取成功:" + token);
                        LogUtil.d(TAG, "token有效期为:" + timeStamp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> {
            error.printStackTrace();
            LogUtil.e(TAG, "onErrorResponse: token获取失败");
        }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s");
                    return null;
                }
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }

    /**
     * 注册单个用户
     * @param userId 用户id ("moonstep" + userPhoneNumber)
     * @param passWord 用户密码
     * @param nickName 用户昵称
     */
    public void registerUser(final Callback callback, final String userId, final String passWord, final String nickName){
        //请求地址
        String url = EMBase.getInstance().getBaseRequest() + EMBase.getInstance().getOrgName() + "/" + EMBase.getInstance().getAppName() + "/" + "users";
        //取得请求队列
        Log.d(TAG, "url: " + url);
        RequestQueue requestQueue = Volley.newRequestQueue(Application.getContext());
        final String requestBody = "{" + "\"" + "username" + "\"" + ":" + "\"" + userId + "\"" + "," + "\"" + "password" + "\"" + ":" + "\"" + passWord + "\"" + "," + "\"" + "nickname" + "\"" + ":" + "\"" + nickName + "\"" + "}";
        Log.d(TAG, "requestBody: " + requestBody);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Boolean activated = jsonObject.getJSONArray("entities").getJSONObject(0).getBoolean("activated");
                        if (activated){
                            callback.onSuccess();
                        }else{
                            callback.onFail();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        LogUtil.e(TAG, "onResponse: ");
                    }
                }, error -> {
                    error.printStackTrace();
                    LogUtil.e(TAG, "onErrorResponse: 注册失败");
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("Authorization", "Bearer "+Application.getToken());
                Log.d(TAG, "getHeaders: " + "Bearer "+Application.getToken());
                return map;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        requestQueue.add(request);
    }

    /**
     * 修改密码
     * @param volleyCallback 回调实例
     * @param userId 用户id
     * @param password 密码
     */
    public void changePassword(final VolleyCallback volleyCallback, String userId, final String password){
        //请求地址
        String url = EMBase.getInstance().getBaseRequest() + EMBase.getInstance().getOrgName() + "/" + EMBase.getInstance().getAppName() + "/" + "users" + "/" + userId + "/" + "password";

        String tag = "changePassword";
        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        LogUtil.d(TAG, "请求的http地址为:" + url);

        StringRequest putRequest = new StringRequest(Request.Method.PUT,
                url,
                response -> {
                    try {
                        volleyCallback.onSuccess();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    error.printStackTrace();
                    LogUtil.e(TAG, "onErrorResponse: 密码修改失败");
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("newpassword", password);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("Authorization", "Bearer " + Application.getToken());
                return map;
            }
        };

        //设置Tag标签
        putRequest.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(putRequest);
    }

    /**
     * 修改昵称
     * @param volleyCallback 回调实例
     * @param userId 用户id
     * @param nickName 昵称
     */
    public void changeNickName(final VolleyCallback volleyCallback, String userId, final String nickName){
        //请求地址
        String url = EMBase.getInstance().getBaseRequest() + EMBase.getInstance().getOrgName() + "/" + EMBase.getInstance().getAppName() + "/" + "users" + "/" + userId;

        String tag = "changeNickName";
        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        LogUtil.d(TAG, "请求的http地址为:" + url);

        StringRequest putRequest = new StringRequest(Request.Method.PUT,
                url,
                response -> {
                    try {
                        volleyCallback.onSuccess();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    error.printStackTrace();
                    LogUtil.e(TAG, "onErrorResponse: 昵称修改失败");
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nickname", nickName);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("Authorization", "Bearer " + Application.getToken());
                return map;
            }
        };

        //设置Tag标签
        putRequest.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(putRequest);
    }

    /**
     * GET请求(Volley)
     * Authorization: Bearer ${token}
     * 获得用户在线状态offline/online
     *
     * @param volleyCallback 回调实例
     * @param userID "moonstep" + userPhoneNumber
     */
    public void getUserState(final VolleyCallback volleyCallback, final String userID) {
        //请求地址
        String url = EMBase.getInstance().getBaseRequest() + EMBase.getInstance().getOrgName() + "/" + EMBase.getInstance().getAppName() + "/" + "users" + "/" + userID + "/" + "status";
        String tag = "userState";
        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        LogUtil.d(TAG, "请求的http地址为:" + url);

        StringRequest request = new StringRequest(
                url,
                response -> {
                    JSONObject jsonObject;
                    try {
                        jsonObject = (JSONObject) new JSONObject(response);
                        String user_state = jsonObject.getJSONObject("data").getString(userID);
                        LogUtil.d(TAG,user_state);
                        if (volleyCallback != null) {
                            volleyCallback.onSuccess(user_state);
                        }
                    } catch (JSONException | InterruptedException e) {
                        e.printStackTrace();
                    }
                },
                error -> LogUtil.e(TAG, "onErrorResponse: 请求用户状态失败")
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {//设置头信息
                Map<String, String> map = new HashMap<String, String>();
                map.put("Authorization", Application.getToken());
                LogUtil.d(TAG, "getHeaders: " + Application.getToken());
                return map;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }

    public interface Callback{

        void onSuccess();

        void onFail();
    }

    public interface GetTokenCallback{
        void onGetToken(String token);
    }
}