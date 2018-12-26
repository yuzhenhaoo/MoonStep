package priv.zxy.moonstep.DAO;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import priv.zxy.moonstep.data.bean.ErrorCodeEnum;
import priv.zxy.moonstep.DAO.constant.URLBase;
import priv.zxy.moonstep.framework.user.User;
import priv.zxy.moonstep.util.LogUtil;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/24
 * 描述: 从服务器端获得用户的个人信息
 **/

public class PullUserInfoDAO {

    private static final String TAG = "PullUserInfoDAO";

    private static PullUserInfoDAO instance;

    private static final String MOONSTEP = "moonstep";

    private static final String USER_TAG = "user";

    private static final String PARSE_RESULT = "Result";

    private static final String PARSE_PARAMS = "params";

    private static final String PARSE_SUCCESS = "success";

    private static final String PARSE_ERROR = "error";

    private static final String URL = URLBase.CHECK_USER_ID_SERVLET_URL;

    public static PullUserInfoDAO getInstance() {
        if (instance == null){
            synchronized (PullUserInfoDAO.class){
                if (instance == null){
                    instance = new PullUserInfoDAO();
                }
            }
        }
        return instance;
    }
    public void getUserInfo(final Callback callback, final String phoneNumber){
        AndroidNetworking.post(URL)
                .addBodyParameter("UserID", MOONSTEP + phoneNumber)
                .setTag(USER_TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = (JSONObject) new JSONObject(response.toString()).get(PARSE_PARAMS);
                            String result = jsonObject.getString(PARSE_RESULT);
                            if (result.equals(PARSE_SUCCESS)){
                                callback.onSuccess(parseUserInfo(jsonObject));
                            } else if(result.equals(PARSE_ERROR)){
                                callback.onFail(ErrorCodeEnum.USER_IS_NOT_EXISTED);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        LogUtil.d(TAG, anError.toString());
                        callback.onFail(ErrorCodeEnum.JSON_EXCEPTION);
                    }
                });
    }

    private User parseUserInfo(JSONObject jsonObject) throws JSONException{
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
        return moonFriend;
    }

    public interface Callback{
        void onSuccess(User moonFriend);

        void onFail(ErrorCodeEnum errorCode);
    }
}
