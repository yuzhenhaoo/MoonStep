package priv.zxy.moonstep.helper;

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

import priv.zxy.moonstep.EM.bean.VolleyCallback;
import priv.zxy.moonstep.kernel.bean.EMBase;

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
     * 注册单个用户
     * @param userId 用户id ("moonstep" + userPhoneNumber)
     * @param passWord 用户密码
     * @param nickName 用户昵称
     */
    public void registerUser(final VolleyCallback volleyCallback, final String userId, final String passWord, final String nickName){
        initToken();
        //请求地址
        String url = EMBase.getInstance().getBaseRequest() + EMBase.getInstance().getOrgName() + "/" + EMBase.getInstance().getAppName() + "/" + "users";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Boolean activated = jsonObject.getJSONArray("entities").getJSONObject(0).getBoolean("activated");
                            if (activated){
                                volleyCallback.onSuccess();
                            }else{
                                volleyCallback.onFail();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "onResponse: ", new RuntimeException());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e(TAG, "onErrorResponse: 注册失败", new RuntimeException());
            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("Authorization", getAuthorization());
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", userId);
                params.put("password", passWord);
                params.put("nickname", nickName);
                return params;
            }
        };
    }

    /**
     * 修改密码
     * @param volleyCallback 回调实例
     * @param userId 用户id
     * @param password 密码
     */
    public void changePassword(final VolleyCallback volleyCallback, String userId, final String password){
        initToken();
        //请求地址
        String url = EMBase.getInstance().getBaseRequest() + EMBase.getInstance().getOrgName() + "/" + EMBase.getInstance().getAppName() + "/" + "users" + "/" + userId + "/" + "password";

        String tag = "changePassword";
        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        Log.d(TAG, "请求的http地址为:" + url);

        StringRequest putRequest = new StringRequest(Request.Method.PUT,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        volleyCallback.onSuccess();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e(TAG, "onErrorResponse: 密码修改失败", new RuntimeException());
            }
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
                map.put("Authorization", getAuthorization());
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
        initToken();
        //请求地址
        String url = EMBase.getInstance().getBaseRequest() + EMBase.getInstance().getOrgName() + "/" + EMBase.getInstance().getAppName() + "/" + "users" + "/" + userId;

        String tag = "changeNickName";
        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        Log.d(TAG, "请求的http地址为:" + url);

        StringRequest putRequest = new StringRequest(Request.Method.PUT,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        volleyCallback.onSuccess();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e(TAG, "onErrorResponse: 昵称修改失败", new RuntimeException());
            }
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
                map.put("Authorization", getAuthorization());
                return map;
            }
        };

        //设置Tag标签
        putRequest.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(putRequest);
    }

    /**
     * 获得token值
     */
    private void initToken() {
        //请求地址
        String url = EMBase.getInstance().getBaseRequest() + EMBase.getInstance().getOrgName() + "/" + EMBase.getInstance().getAppName() + "/token";
        String tag = "token";
        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        Log.d(TAG, "请求的http地址为:" + url);
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = (JSONObject) new JSONObject(response);
                            token = jsonObject.getString("access_token");
                            initAuthorization();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e(TAG, "onErrorResponse: token获取失败", new RuntimeException());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("grant_type", "client_credentials");
                params.put("client_id", EMBase.getInstance().getClient_ID());
                params.put("client_secret", EMBase.getInstance().getClient_Secret());
                return params;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }

    private void initAuthorization() {
        Authorization = "Bearer " + token;
    }

    private String getAuthorization() {
        return Authorization;
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
        String url = EMBase.getInstance().getBaseRequest() + EMBase.getInstance().getOrgName() + "/" + EMBase.getInstance().getOrgName() + "/" + EMBase.getInstance().getAppName() + "/" + "users" + userID + "/" + "status";
        String tag = "userState";
        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        Log.d(TAG, "请求的http地址为:" + url);

        initToken();//进行请求前，必须先获取到Token的值

        StringRequest request = new StringRequest(
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject;
                        try {
                            jsonObject = (JSONObject) new JSONObject(response);
                            String user_state = jsonObject.getJSONObject("data").getString(userID);
                            if (volleyCallback != null) {
                                volleyCallback.onSuccess(user_state);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: 请求用户状态失败", new RuntimeException());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {//设置头信息
                Map<String, String> map = new HashMap<String, String>();
                map.put("Authorization", getAuthorization());
                return map;
            }
        };

        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }
}
