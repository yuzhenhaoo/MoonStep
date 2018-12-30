package priv.zxy.moonstep.DAO;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import priv.zxy.moonstep.DAO.constant.DaoConstant;
import priv.zxy.moonstep.data.bean.ErrorCodeEnum;
import priv.zxy.moonstep.DAO.constant.UrlBase;
import priv.zxy.moonstep.util.LogUtil;

/**
 * 工具类的逻辑尚有问题：
 * 如果要将工具类应用到mvp的设计模式中，对于错误的处理必须有一个相应的规范
 * 在网络相应过程中产生的所有错误必须使用枚举类来进行约束
 * 并在presenter的相应的调用过程中对当前错误码进行判断，并真醉错误码进行输出
 */

public class PhoneNumberIsInServerDAO {

    private static final String TAG = "PhoneNumberIsInServerDAO";

    private static final String CHECK_TAG = "check";

    private static final String URL = UrlBase.CHECK_PHONE_NUMBER_IN_SERVLET_URL;

    private static PhoneNumberIsInServerDAO instance;

    public static PhoneNumberIsInServerDAO getInstance(){
        if (instance == null){
            synchronized(PhoneNumberIsInServerDAO.class){
                if (instance == null){
                    instance = new PhoneNumberIsInServerDAO();
                }
            }
        }
        return instance;
    }

    public void check(final Callback callback, final String phoneNumber){
        AndroidNetworking.post(URL)
                .addBodyParameter("phoneNumber", phoneNumber)
                .setTag(CHECK_TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonObject;
                        try {
                            jsonObject = (JSONObject) new JSONObject(response.toString()).get(DaoConstant.PARAMS);
                            String result = jsonObject.getString(DaoConstant.RESULT);
                            if (result.equals(DaoConstant.SUCCESS)) {
                                //检验成功
                                callback.onSuccess();
                            } else {
                                //检验失败
                                callback.onFail(ErrorCodeEnum.PHONE_NUMBER_IS_REGISTERED);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onFail(ErrorCodeEnum.NET_NOT_RESPONSE);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        LogUtil.d(TAG, anError.toString());
                    }
                });
    }

    public interface Callback{
        void onSuccess();

        void onFail(ErrorCodeEnum errorCode);
    }
}
