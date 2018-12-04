package priv.zxy.moonstep.utils.dbUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import priv.zxy.moonstep.data.bean.URLBase;
import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.data.bean.ErrorCode;
import priv.zxy.moonstep.framework.user.User;
import priv.zxy.moonstep.utils.LogUtil;

/**
 * 注意，为了保障数据的安全性，对于幸运值的获取，我们必须单独设置一个函数来单独的获取，开始的时候加载一次，进行加密后存储进入杂项数据库中，需要用到的时候先检测网络
 * 如果存在网络，就从网络上获取幸运值的内容，如果不存在网络，就从加密的数据中进行解密获取幸运值。
 */
public class GetUserInformationUtil {

    private static final String TAG = "GetUserInformationUtil";

    private static GetUserInformationUtil instance;

    public static GetUserInformationUtil getInstance(){
        if (instance == null){
            synchronized(GetUserInformationUtil.class){
                instance = new GetUserInformationUtil();
            }
        }
        return instance;
    }
    
    public void getUserInfo(final Callback callback, final String userId) {
        //请求地址
        String url = URLBase.CHECK_USERID_SERVLET_URL;
        final String tag = "myself";
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
                            //检验成功
                            User moonFriend = new User();
                            moonFriend.setNickName(jsonObject.getString("nickName"));
                            moonFriend.setPhoneNumber(jsonObject.getString("phoneNumber"));
                            moonFriend.setGender(jsonObject.getString("gender"));
                            moonFriend.setRaceCode(Integer.parseInt(jsonObject.getString("raceCode")));
                            moonFriend.setHeadPath(jsonObject.getString("headPortraitPath"));
                            moonFriend.setSignature(jsonObject.getString("signature"));
                            moonFriend.setLocation(jsonObject.getString("address"));
                            moonFriend.setCurrentTitle(jsonObject.getString("currentTitle"));
                            moonFriend.setLuckyValue(Integer.parseInt(jsonObject.getString("luckyValue")));
                            callback.onSuccess(moonFriend);
                            LogUtil.d(TAG, "获取个人信息成功");
                        } else if(result.equals("error")){
                            //检验失败
                            callback.onFail(ErrorCode.MoonFriendUserIsNotExisted);
                        }
                    } catch (JSONException e) {
                        //做自己的请求异常操作
                        callback.onFail(ErrorCode.JSONException);
                        LogUtil.e(TAG, e.getMessage());
                    }
                }, error -> {
                    //做自己的响应错误操作，如Toast提示（“请稍后重试”等）
                    callback.onFail(ErrorCode.NetNotResponse);
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

    public interface Callback{

        void onSuccess(User moonFriend);

        void onFail(ErrorCode errorCode);
    }
}