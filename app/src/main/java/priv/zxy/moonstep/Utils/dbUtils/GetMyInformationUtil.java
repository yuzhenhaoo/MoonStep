package priv.zxy.moonstep.utils.dbUtils;


import android.app.Activity;
import android.content.Context;
import android.util.Log;

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

import priv.zxy.moonstep.kernel.bean.ErrorCode;
import priv.zxy.moonstep.kernel.bean.User;

public class GetMyInformationUtil {
    private static final String TAG = "GetMyInformationUtil";
    private Context mContext;
    public static boolean isSuccess = false;
    public static ErrorCode errorCode = null;
    public static User moonFriend = null;

    public GetMyInformationUtil(Context mContext) {
        this.mContext = mContext;
    }

    public void returnMyInfo(final String userId) {
        //请求地址
        String url = "http://120.79.154.236:8080/MoonStep/CheckUserID";
        final String tag = "myself";
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
                                isSuccess = true;
                                User user = new User();
                                user.setUserPhoneNumber(jsonObject.getString("phoneNumber"));
                                user.setUserGender(jsonObject.getString("gender"));
                                user.setUserRace(jsonObject.getString("race"));
                                user.setUserLevel(jsonObject.getString("level"));
                                user.setNickName(jsonObject.getString("nickName"));
                                user.setSignature(jsonObject.getString("signature"));
//                                user.setHeadPortrait(jsonObject.getString("portrait").getBytes());
                                user.setUserPet(jsonObject.getString("pet"));
                                moonFriend = user;
                                Log.d(tag, "获取个人信息成功");
                            } else if(result.equals("error")){
                                //检验失败
                                errorCode = ErrorCode.MoonFriendUserIsNotExisted;
                            }
                        } catch (JSONException e) {
                            //做自己的请求异常操作
                            errorCode = ErrorCode.JSONException;
                            Log.e(tag, e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //做自己的响应错误操作，如Toast提示（“请稍后重试”等）
                errorCode = ErrorCode.NetNotResponse;
                Log.e(tag, error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("UserID", userId);
                return params;
            }
        };
        //设置Tag标签
        request.setTag(tag);

        //将请求添加到队列中
        requestQueue.add(request);
    }

    public User getMoonFriend(){
        return moonFriend;
    }
}