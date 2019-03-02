package priv.zxy.moonstep.login.module.biz;

/**
 * Created by Zxy on 2018/9/20
 */

import android.app.Activity;
import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

//import cn.smssdk.SMSSDK;
import priv.zxy.moonstep.DAO.LoginDataRequestDAO;
import priv.zxy.moonstep.DAO.RegisterDataRequestDAO;
import priv.zxy.moonstep.DAO.PhoneNumberIsInServerDAO;
import priv.zxy.moonstep.util.ToastUtil;
import priv.zxy.moonstep.data.bean.ErrorCodeEnum;

public class LoginBiz implements ILogin {

    private static final String TAG = "LoginBiz";
    /**
     * 在做doLogin()之前，你必须先开启刷新页面，否则将会影响用户的体验
     * 当doLogin()方法的调用结束以后，你必须关闭刷新页面，否则将会无法使界面对用户进行响应
     *
     * @param userPhoneNumber 用户名
     * @param userPassword    用户密码
     * @param onLoginListener   登陆监听
     */
    @Override
    public void doLogin(String userPhoneNumber, String userPassword, final OnLoginListener onLoginListener) throws InterruptedException {
        if (userPhoneNumber == null || userPassword == null) {
            if(userPhoneNumber == null)
                onLoginListener.LoginFail(ErrorCodeEnum.PHONE_NUMBER_IS_EMPTY);
            else
                onLoginListener.LoginFail(ErrorCodeEnum.PASSWORD_IS_EMPTY);
            return;
        }
        LoginDataRequestDAO.getInstance().login(onLoginListener, userPhoneNumber, userPassword);
    }

    /**
     * 在做doRegister之前，你必须先开启页面的刷新，否则将会影响到用户体验
     * 在doRegister调用完成后，你必须关闭页面的刷新机制，否则程序将会无法对用户进行响应
     *
     * @param nickName         用户名
     * @param userPassword     用户密码
     * @param gender           性别
     * @param registerListener 注册监听
     */
    @Override
    public void doRegister(final String phoneNumber, String nickName, final String userPassword, String confirmUserPassword, String gender, final OnRegisterListener registerListener) throws InterruptedException{
        if (phoneNumber == null || nickName == null || userPassword == null || confirmUserPassword == null) {
            registerListener.registerFail(ErrorCodeEnum.REGISTER_DATA_CAN_NOT_NULL);
            return;
        }
        if (!userPassword.equals(confirmUserPassword)) {
            registerListener.registerFail(ErrorCodeEnum.PASSWORD_IS_NOT_EQUALS_CONFIRM_PASSWORD);
            return;
        }
        RegisterDataRequestDAO.getInstance().RegisterRequest(new RegisterDataRequestDAO.CallBack() {
            @Override
            public void onSuccess(String raceCode, String raceName, String raceDescription, String raceImage, String raceIcon) throws HyphenateException {
                registerListener.registerSuccess(raceName, raceDescription, raceImage, raceIcon);
                // 同步方法
                EMClient.getInstance().createAccount(phoneNumber, userPassword);
            }
            @Override
            public void onFail(ErrorCodeEnum errorCode) {
                registerListener.registerFail(errorCode);
            }}, phoneNumber, nickName, userPassword, gender);
    }

    /**
     * doVerfiyPhoneNumber是在手机注册的第一个页面中来检验手机账号的合法性
     * 具体是进行一个网络操作，并在数据库中进行查询是否有当前手机的一个数据记录
     * 如果数据记录已经存在，那么当前手机的验证失败
     * 如果数据记录不存在的话，手机验证成功，并且可以进入下
     * 所以这里要设置一个刷新页面的开启和关闭，但是需要注意的是：modle层本身用来处理逻辑的操作，而对页面的刷新和关闭则是View层的内容，因此我们不在Module层设置页面的刷新机制
     * 但是因为页面刷新机制本身中并不带有线程休眠时间的控制，所以在doVerifyPhoneNumber中应该加入线程休眠的时间
     *
     * @param phoneNumber       用户输入的手机号码一页面
     *      *      * 因为要对数据库进行查询的请求操作
     * @param verifyPhoneNumber 对于phoneNumber的事件监听，这里应该根据实际的状况向verifyPhoneNumber的两个函数传递具体的事件监听的状态码，并根据实际的状态码来处理实际的事件，状态吗可以由bean层中的枚举类来承接
     */
    @Override
    public void doVerifyPhoneNumber(String phoneNumber, final OnVerifyPhoneNumber verifyPhoneNumber) throws InterruptedException {
        if (phoneNumber.equals("")) {
            // 这里应该设置一个具体的状态码：手机号为空
            verifyPhoneNumber.verifyFail(ErrorCodeEnum.PHONE_NUMBER_IS_EMPTY);
        } else {
            PhoneNumberIsInServerDAO.getInstance().check(new PhoneNumberIsInServerDAO.Callback() {
                @Override
                public void onSuccess() {
                    verifyPhoneNumber.verifySuccess();
                }

                @Override
                public void onFail(ErrorCodeEnum errorCode) {
                    verifyPhoneNumber.verifyFail(errorCode);
                }
            }, phoneNumber);
        }
    }

    @Override
    public void submitInfo(String country, String phone, String code, Context mContext, Activity mActivity) throws InterruptedException {
        ToastUtil.getInstance(mContext, mActivity);
        if (phone.equals("")) {
            ToastUtil.getInstance(mContext, mActivity).showToast("手机号不能为空哦，请重新尝试");
        } else {
            if (code.equals("")) {
                ToastUtil.getInstance(mContext, mActivity).showToast("验证码不能为空，请重新尝试!");
            } else {
//                SMSSDK.submitVerificationCode(country, phone, code);//提交验证码  在eventHandler里面查看验证结果
            }
            Thread.sleep(1000);
        }
    }

    @Override
    public void setChangePassword(String phoneNumber, String password, String confirmPassword, final OnChangePasswordListener changePasswordListener) throws InterruptedException {
        ErrorCodeEnum errorCode = null;

        if (password.equals("")) {
            errorCode = ErrorCodeEnum.PASSWORD_IS_EMPTY;
        } else if (confirmPassword.equals("")) {
            errorCode = ErrorCodeEnum.CONFIRM_PASSWORD_IS_EMPTY;
        }

        if (password.equals(confirmPassword)) {
            LoginDataRequestDAO.getInstance().changePassword(new LoginDataRequestDAO.CallBack() {
                @Override
                public void onSuccess() throws InterruptedException {
                    changePasswordListener.changePasswordSuccess();
                }

                @Override
                public void onFail(ErrorCodeEnum errorCodeEnum) {
                    changePasswordListener.changePasswordFail(errorCodeEnum);
                }
            }, phoneNumber, password);
        } else {
            errorCode = ErrorCodeEnum.PASSWORD_IS_NOT_EQUALS_CONFIRM_PASSWORD;
        }
        if (errorCode != null) changePasswordListener.changePasswordFail(errorCode);
    }

    @Override
    public void judgeCanJumpToChangePasswordActivity(String phoneNumber, final OnPhoneCheckListener onPhoneCheckListener) throws InterruptedException {
        PhoneNumberIsInServerDAO.getInstance().check(new PhoneNumberIsInServerDAO.Callback() {
            @Override
            public void onSuccess() {
                onPhoneCheckListener.phoneIsNotExisted();
            }

            @Override
            public void onFail(ErrorCodeEnum errorCode) {
                onPhoneCheckListener.getErrcodeTips(errorCode);
            }
        }, phoneNumber);
    }

    public interface OnRegisterListener {

        void registerSuccess(String raceName, String raceDescription, String raceImage, String raceIcon);

        void registerFail(ErrorCodeEnum errorCode);
    }

}
