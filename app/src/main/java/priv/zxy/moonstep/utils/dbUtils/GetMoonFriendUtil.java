package priv.zxy.moonstep.utils.dbUtils;

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
import priv.zxy.moonstep.db.MoonFriend;
import priv.zxy.moonstep.kernel.Application;
import priv.zxy.moonstep.kernel.bean.ErrorCode;
import priv.zxy.moonstep.utils.LogUtil;

public class GetMoonFriendUtil {

    private static final String TAG = "GetMoonFriendUtil";

    private static GetMoonFriendUtil instance;

    public static GetMoonFriendUtil getInstance() {
        if (instance == null){
            synchronized (GetMoonFriendUtil.class){
                if (instance == null){
                    instance = new GetMoonFriendUtil();
                }
            }
        }
        return instance;
    }

    public void returnMoonFriendInfo(final VolleyCallback volleyCallback, final String userId) {
        //请求地址
        String url = "http://120.79.154.236:8080/MoonStep/CheckUserID";
        String tag = "moonfriend";
        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(Application.getContext());

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
                                MoonFriend moonFriend = new MoonFriend();
                                moonFriend.setPhoneNumber(jsonObject.getString("phoneNumber"));
                                moonFriend.setGender(jsonObject.getString("gender"));
                                moonFriend.setRace(jsonObject.getString("race"));
                                moonFriend.setLevel(jsonObject.getString("level"));
                                moonFriend.setPet(jsonObject.getString("pet"));
                                moonFriend.setNickName(jsonObject.getString("nickName"));
                                moonFriend.setSignature(jsonObject.getString("signature"));
//                                moonFriend.setHeadPortrait(jsonObject.getString("portrait").getBytes());
                                volleyCallback.getMoonFriend(moonFriend);
                                LogUtil.e(TAG, "获取好友信息成功");
                            } else if(result.equals("error")){
                                volleyCallback.getErrorCode(ErrorCode.MoonFriendUserIsNotExisted);
                            }
                        } catch (JSONException e) {
                            //做自己的请求异常操作
                            volleyCallback.getErrorCode(ErrorCode.JSONException);
                            LogUtil.e(TAG, e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //做自己的响应错误操作，如Toast提示（“请稍后重试”等）
                volleyCallback.getErrorCode(ErrorCode.NetNotResponse);
                LogUtil.e(TAG, error.getMessage());
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
}
