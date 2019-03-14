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

import priv.zxy.moonstep.DAO.constant.DaoConstant;
import priv.zxy.moonstep.constant.SharedConstant;
import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.data.bean.ErrorCodeEnum;
import priv.zxy.moonstep.DAO.constant.UrlBase;
import priv.zxy.moonstep.executor.ExecutorManager;
import priv.zxy.moonstep.framework.user.User;
import priv.zxy.moonstep.helper.EMHelper;
import priv.zxy.moonstep.login.module.biz.OnLoginListener;
import priv.zxy.moonstep.service.MessageReceiverService;
import priv.zxy.moonstep.manager.DataInitManager;
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
    private static final String LOGIN_TAG = "activity_login";
    private static final String CHANGE_PASSWORD_TAG = "change";
    private static final String PARSE_ERROR_0 = "error0";
    private static final String PARSE_ERROR_1 = "error1";

    private static final String URL = UrlBase.LOGIN_SERVLET_URL;

    private static final String URL2 = UrlBase.CHANGE_PASSWORD_SERVLET_URL;

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
                .addBodyParameter(DaoConstant.PHONE_NUMBER, phoneNumber)
                .addBodyParameter(DaoConstant.PASSWORD, inputPassword)
                .setTag(LOGIN_TAG)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = (JSONObject) new JSONObject(response.toString()).get(DaoConstant.PARAMS);
                            String result = jsonObject.getString(DaoConstant.RESULT);
                            if (result.equals(DaoConstant.SUCCESS)) {
                                /*
                                 * 登录环信服务器
                                 */
                                if (Application.startEmServer) {
                                    loginEMServer(loginListener, phoneNumber, inputPassword);
                                } else {
                                    loginListener.LoginSuccess();
                                }
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
                // 只有当环信服务器也登陆成功的时候才触发回调方法
                loginListener.LoginSuccess();
                LogUtil.d(TAG, "登录聊天服务器成功！");
                saveData(phoneNumber, inputPassword);
                getUserInformation(loginListener, phoneNumber);
                // FIXME(张晓翼，2019/3/14，环信服务器登陆无响应，同时设置startEmServer为解耦即时通讯模块做准备)
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
     * 获取用户个人信息
     */
    private void getUserInformation(final OnLoginListener loginListener,String phoneNumber){
        boolean isLoginResult = SharedPreferencesUtil.isSuccessLogin();
        if (isLoginResult){
            PullUserInfoDAO.getInstance().getUserInfo(new PullUserInfoDAO.Callback() {
                @Override
                public void onSuccess(User moonFriend) {
                    // 将用户个人信息存储进入缓存
                    SharedPreferencesUtil.saveMySelfInformation(moonFriend);
                    // 将用户个人信息存储进入UserSelfInfo的实例对象中
                    DataInitManager.initUserSelfInfo(moonFriend);
                    // 只有到这个地方，才会返回login中的UserBiz的回调，才能跳入MainActivity中
                    loginListener.LoginSuccess();
                }

                @Override
                public void onFail(ErrorCodeEnum errorCode) {
                    loginListener.LoginFail(ErrorCodeEnum.USER_DATA_REQUEST_FAIL);
                    LogUtil.d(TAG, "获取个人信息失败");
                }
            }, SharedConstant.MOONSTEP + phoneNumber);
        }
    }


    /**
     * 将数据存储到缓存中，方便存取
     * @param phoneNumber 电话号码
     * @param inputPassword 密码
     */
    private void saveData(final  String phoneNumber, final String inputPassword){
        SharedPreferencesUtil.setSuccessLoginInfo(phoneNumber, inputPassword);
    }

    private void setLoginFailTagToCache(){
        SharedPreferencesUtil.setFailLoginInfo();
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
                            String result = jsonObject.getString(DaoConstant.RESULT);
                            switch (result){
                                case DaoConstant.SUCCESS:
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
        }, MOONSTEP + phoneNumber, password);
    }

    public interface CallBack{

        void onSuccess() throws InterruptedException;

        void onFail(ErrorCodeEnum errorCodeEnum);
    }
}
