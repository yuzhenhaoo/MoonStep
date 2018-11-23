package priv.zxy.moonstep.login.module.biz;

/**
 * Created by Zxy on 2018/9/20
 */

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

//import cn.smssdk.SMSSDK;
import priv.zxy.moonstep.EM.bean.VolleyCallback;
import priv.zxy.moonstep.db.MoonFriend;
import priv.zxy.moonstep.utils.dbUtils.ChangePasswordUtil;
import priv.zxy.moonstep.utils.dbUtils.LoginUtil;
import priv.zxy.moonstep.utils.dbUtils.PhoneCheckUtil;
import priv.zxy.moonstep.utils.dbUtils.PhoneRegisterUtil;
import priv.zxy.moonstep.utils.ToastUtil;
import priv.zxy.moonstep.kernel.bean.ErrorCode;

public class UserBiz implements IUser {

    /**
     * 在做doLogUtilin()之前，你必须先开启刷新页面，否则将会影响用户的体验
     * 当doLogUtilin()方法的调用结束以后，你必须关闭刷新页面，否则将会无法使界面对用户进行响应
     *
     * @param userPhoneNumber 用户名
     * @param userPassword    用户密码
     * @param onLoginListener   登陆监听
     */
    @Override
    public void doLogin(String userPhoneNumber, String userPassword, final OnLoginListener onLoginListener) throws InterruptedException {
        if (userPhoneNumber != null && userPassword != null) {
            LoginUtil.getInstance().LoginRequest(onLoginListener, userPhoneNumber, userPassword);
        } else {
            if(userPhoneNumber == null)
                onLoginListener.LoginFail(ErrorCode.PhoneNumberISEmpty);
            else
                onLoginListener.LoginFail(ErrorCode.PasswordIsEmpty);
        }
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
        if (userPassword.equals(confirmUserPassword)) {
            PhoneRegisterUtil.getInstance().RegisterRequest(new PhoneRegisterUtil.CallBack() {
                @Override
                public void onSuccess(String raceCode, String raceName, String raceDescription, String raceImage, String raceIcon) throws HyphenateException {
                    registerListener.registerSuccess(raceName, raceDescription, raceImage, raceIcon);
                    EMClient.getInstance().createAccount(phoneNumber, userPassword);//同步方法
                }

                @Override
                public void onFail(ErrorCode errorCode) {
                    registerListener.registerFail(errorCode);
                }
            }, phoneNumber, nickName, userPassword, gender);
        } else {
            registerListener.registerFail(ErrorCode.PasswordIsNotEqualsConfirmPassword);
        }
    }

    /**
     * doVerfiyPhoneNumber是在手机注册的第一个页面中来检验手机账号的合法性
     * 具体是进行一个网络操作，并在数据库中进行查询是否有当前手机的一个数据记录
     * 如果数据记录已经存在，那么当前手机的验证失败
     * 如果数据记录不存在的话，手机验证成功，并且可以进入下一页面
     * 因为要对数据库进行查询的请求操作
     * 所以这里要设置一个刷新页面的开启和关闭，但是需要注意的是：modle层本身用来处理逻辑的操作，而对页面的刷新和关闭则是View层的内容，因此我们不在Module层设置页面的刷新机制
     * 但是因为页面刷新机制本身中并不带有线程休眠时间的控制，所以在doVerifyPhoneNumber中应该加入线程休眠的时间
     *
     * @param phoneNumber       用户输入的手机号码
     * @param verifyPhoneNumber 对于phoneNumber的事件监听，这里应该根据实际的状况向verifyPhoneNumber的两个函数传递具体的事件监听的状态码，并根据实际的状态码来处理实际的事件，状态吗可以由bean层中的枚举类来承接
     */
    @Override
    public void doVerifyPhoneNumber(String phoneNumber, final OnVerifyPhoneNumber verifyPhoneNumber) throws InterruptedException {
        if (phoneNumber.equals("")) {
            // 这里应该设置一个具体的状态码：手机号为空
            verifyPhoneNumber.verifyFail(ErrorCode.PhoneNumberISEmpty);
        } else {
            PhoneCheckUtil.getInstance().phoneCheck(new VolleyCallback() {
                @Override
                public String onSuccess(String result) {
                    return null;
                }

                @Override
                public boolean onSuccess() throws InterruptedException {
                    verifyPhoneNumber.verifySuccess();
                    return true;
                }

                @Override
                public String onFail(String error) {
                    return null;
                }

                @Override
                public boolean onFail() {
                    return false;
                }

                @Override
                public void getMoonFriend(MoonFriend moonFriend) {

                }

                @Override
                public void getErrorCode(ErrorCode errorCode) {
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
        ErrorCode errorCode = null;

        if (password.equals("")) {
            errorCode = ErrorCode.PasswordIsEmpty;
        } else if (confirmPassword.equals("")) {
            errorCode = ErrorCode.ConfirmPasswordIsEmpty;
        }

        if (password.equals(confirmPassword)) {

            ChangePasswordUtil.getInstance().changePassword(new VolleyCallback() {
                @Override
                public String onSuccess(String result) {
                    return null;
                }

                @Override
                public boolean onSuccess() throws InterruptedException {
                    changePasswordListener.changePasswordSuccess();
                    return true;
                }

                @Override
                public String onFail(String error) {
                    return null;
                }

                @Override
                public boolean onFail() {
                    return false;
                }

                @Override
                public void getMoonFriend(MoonFriend moonFriend) {

                }

                @Override
                public void getErrorCode(ErrorCode errorCode) {
                    changePasswordListener.changePasswordFail(errorCode);
                }
            }, phoneNumber, password);
        } else {
            errorCode = ErrorCode.PasswordIsNotEqualsConfirmPassword;
        }
        if (errorCode != null) changePasswordListener.changePasswordFail(errorCode);
    }

    @Override
    public void judgeCanJumpToChangePasswordActivity(String phoneNumber, final OnPhoneCheckListener onPhoneCheckListener) throws InterruptedException {
        PhoneCheckUtil.getInstance().phoneCheck(new VolleyCallback() {
            @Override
            public String onSuccess(String result) {
                return null;
            }

            @Override
            public boolean onSuccess() throws InterruptedException {
                onPhoneCheckListener.phoneIsNotExisted();
                return true;
            }

            @Override
            public String onFail(String error) {
                return null;
            }

            @Override
            public boolean onFail() {
                onPhoneCheckListener.phoneIsExisted();
                return true;
            }

            @Override
            public void getMoonFriend(MoonFriend moonFriend) {

            }

            @Override
            public void getErrorCode(ErrorCode errorCode) {
                onPhoneCheckListener.getErrcodeTips(errorCode);
            }
        }, phoneNumber);
    }

    public interface OnRegisterListener {

        void registerSuccess(String raceName, String raceDescription, String raceImage, String raceIcon);

        void registerFail(ErrorCode errorCode);
    }

}
