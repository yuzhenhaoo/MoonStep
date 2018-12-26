package priv.zxy.moonstep.DAO;

import android.content.Intent;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import org.json.JSONException;
import org.json.JSONObject;

import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.data.bean.ErrorCodeEnum;
import priv.zxy.moonstep.constant.URLBase;
import priv.zxy.moonstep.framework.user.User;
import priv.zxy.moonstep.helper.EMHelper;
import priv.zxy.moonstep.login.module.biz.OnLoginListener;
import priv.zxy.moonstep.service.MessageReceiverService;
import priv.zxy.moonstep.util.LogUtil;
import priv.zxy.moonstep.util.SharedPreferencesUtil;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/24
 * 描述:登录模块数据请求的类
 **/

public class LoginDataRequestDAO {

    private static final String TAG = "LoginDataRequestDAO";

    private static LoginDataRequestDAO instance;

    private static final String MOONSTEP = "moonstep";

    private static final String LOGIN_TAG = "login";

    private static final String CHANGE_PASSWORD_TAG = "change";

    private static final String PARSE_RESULT = "Result";

    private static final String PARSE_PARAMS = "params";

    private static final String PARSE_SUCCESS = "success";

    private static final String PARSE_ERROR_0 = "error0";
    private static final String PARSE_ERROR_1 = "error1";

    private static final String URL = URLBase.LOGIN_SERVLET_URL;

    private static final String URL2 = URLBase.CHANGE_PASSWORD_SERVLET_URL;

    public static LoginDataRequestDAO getInstance() {
        if (instance == null){
            synchronized (LoginDataRequestDAO.class){
                if (instance == null){
                    instance = new LoginDataRequestDAO();
                }
            }
        }
        return instance;
    }

    public void login(final OnLoginListener loginListener, final  String phoneNumber, final String inputPassword){
        AndroidNetworking.post(URL)
                .addBodyParameter("phoneNumber", phoneNumber)
                .addBodyParameter("PassWord", inputPassword)
                .setTag(LOGIN_TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = (JSONObject) new JSONObject(response.toString()).get(PARSE_PARAMS);
                            String result = jsonObject.getString(PARSE_RESULT);
                            if (result.equals(PARSE_SUCCESS)) {
                                /**
                                 * 登录环信服务器
                                 */
                                loginEMServer(loginListener, phoneNumber, inputPassword);
                            } else{
                                loginListener.LoginFail(ErrorCodeEnum.PHONE_NUMBER_OR_PASSWORD_IS_WRONG);
                                setLoginFailTagToCache();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            loginListener.LoginFail(ErrorCodeEnum.JSON_EXCEPTION);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        LogUtil.d(TAG, anError.toString());
                        loginListener.LoginFail(ErrorCodeEnum.NET_NOT_RESPONSE);
                    }
                });
    }

    /**
     * 登录环信聊天服务器
     * @param loginListener 登录监听
     * @param phoneNumber 电话号码
     * @param inputPassword 密码
     */
    private void loginEMServer(final OnLoginListener loginListener, final  String phoneNumber, final String inputPassword){
        EMClient.getInstance().login(MOONSTEP + phoneNumber,inputPassword,new EMCallBack() {
            @Override
            public void onSuccess() {
                //保证进入主页面后本地会话和群组都 load 完毕。
                new Thread(() -> {
                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();
                }).start();
                loginListener.LoginSuccess();//只有当环信服务器也登陆成功的时候才触发回调方法
                LogUtil.d(TAG, "登录聊天服务器成功！");
                saveData(phoneNumber, inputPassword);
                startMessageService();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                LogUtil.d(TAG, "登录聊天服务器失败！" + code + message);
            }
        });
    }

    /**
     * 将数据存储到缓存中，方便存取
     * @param phoneNumber 电话号码
     * @param inputPassword 密码
     */
    private void saveData(final  String phoneNumber, final String inputPassword){
        SharedPreferencesUtil.getInstance(Application.getContext()).setSuccessLoginInfo(phoneNumber, inputPassword);
    }

    private void setLoginFailTagToCache(){
        SharedPreferencesUtil.getInstance(Application.getContext()).setFailLoginInfo();
    }

    private void startMessageService(){
        Application.getContext().startService(new Intent(Application.getContext(), MessageReceiverService.class));
    }



    public void changePassword(final CallBack callback, final String phoneNumber, final String password){
        //请求地址
        AndroidNetworking.post(URL2)
                .addBodyParameter("PhoneNumber", phoneNumber)
                .addBodyParameter("PassWord", password)
                .setTag(CHANGE_PASSWORD_TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = (JSONObject) new JSONObject(response.toString()).get("params");
                            String result = jsonObject.getString(PARSE_RESULT);
                            switch (result){
                                case PARSE_SUCCESS:
                                    updateEMUserPassword(callback, phoneNumber, password);
                                    break;
                                case PARSE_ERROR_0:
                                    callback.onFail(ErrorCodeEnum.PHONE_NUMBER_IS_NOT_REGISTERED);
                                    break;
                                case PARSE_ERROR_1:
                                    callback.onFail(ErrorCodeEnum.SERVER_IS_FAULT);
                                    break;
                            }
                        } catch (JSONException e) {
                            callback.onFail(ErrorCodeEnum.JSON_EXCEPTION);
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

    private void updateEMUserPassword(final CallBack callback, String phoneNumber, String password){
        EMHelper.getInstance(Application.getContext()).changePassword(new EMHelper.CallBack() {
            @Override
            public void onSuccess(String obj) {

            }

            @Override
            public void onSuccess() throws InterruptedException {
                callback.onSuccess();
            }

            @Override
            public void onFail() {

            }

            @Override
            public void onFail(ErrorCodeEnum errorCodeEnum) {

            }
        }, "moonstep" + phoneNumber, password);
    }

    public interface CallBack{

        void onSuccess() throws InterruptedException;

        void onFail(ErrorCodeEnum errorCodeEnum);
    }
}
