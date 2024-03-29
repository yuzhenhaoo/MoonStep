package priv.zxy.moonstep.DAO;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import priv.zxy.moonstep.DAO.constant.DaoConstant;
import priv.zxy.moonstep.constant.SharedConstant;
import priv.zxy.moonstep.data.bean.ErrorCodeEnum;
import priv.zxy.moonstep.DAO.constant.UrlBase;
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

    private static final String USER_TAG = "user";

    private static final String URL = UrlBase.CHECK_USER_ID_SERVLET_URL;

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

    public void getUserInfo(final Callback callback, final String userID){
        AndroidNetworking.post(URL)
                .addBodyParameter(DaoConstant.USER_ID, userID)
                .setTag(USER_TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = (JSONObject) new JSONObject(response.toString()).get(DaoConstant.PARAMS);
                            String result = jsonObject.getString(DaoConstant.RESULT);
                            LogUtil.d(TAG, result);
                            if (result.equals(DaoConstant.SUCCESS)){
                                callback.onSuccess(User.createItemfromJson(jsonObject));
                            } else if(result.equals(DaoConstant.FAIL)){
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

    public interface Callback{
        void onSuccess(User user);

        void onFail(ErrorCodeEnum errorCode);
    }
}
