package priv.zxy.moonstep.utils;

import android.app.Activity;
import android.content.Context;

import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.data.bean.ErrorCode;

public class ShowErrorReason {

    private static ShowErrorReason instance;

    private Activity mActivity;

    private Context mContext;

    public ShowErrorReason(Activity mActivity){
        mContext = Application.getContext();
        this.mActivity = mActivity;
    }
    public static ShowErrorReason getInstance(Activity mActivity){
        if (instance == null){
            synchronized(ShowErrorReason.class){
                if (instance == null){
                    instance = new ShowErrorReason(mActivity);
                }
            }
        }
        return instance;
    }

    public void show(ErrorCode errorCode){
        switch (errorCode){
            case PhoneNumberISEmpty:
                ToastUtil.getInstance(mContext, mActivity).showToast("电话号码不能为空哦");
                break;
            case NetNotResponse:
                ToastUtil.getInstance(mContext, mActivity).showToast("网络没有响应，请稍后重试");
                break;
            case ChangePasswordFail:
                ToastUtil.getInstance(mContext, mActivity).showToast("密码修改失败，请稍后重试");
                break;
            case JSONException:
                ToastUtil.getInstance(mContext, mActivity).showToast("JSON数据异常，请稍后重试");
                break;
            case PhoneNumberIsRegistered:
                ToastUtil.getInstance(mContext, mActivity).showToast("电话号码已经注册过了，请换一个重试");
                break;
            case PhoneNumberIsNotRegistered:
                ToastUtil.getInstance(mContext, mActivity).showToast("电话号码还没有注册哦");
                break;
            case PhoneNumberOrPasswordIsWrong:
                ToastUtil.getInstance(mContext, mActivity).showToast("手机号码未注册/密码错误，请重试");
                break;
            case PasswordFormatISNotRight:
                ToastUtil.getInstance(mContext, mActivity).showToast("密码格式不正确，请重新输入");
                break;
            case PasswordIsEmpty:
                ToastUtil.getInstance(mContext, mActivity).showToast("密码不能为空哦");
                break;
            case ConfirmPasswordIsEmpty:
                ToastUtil.getInstance(mContext, mActivity).showToast("验证密码不能为空哦");
                break;
            case PasswordIsNotEqualsConfirmPassword:
                ToastUtil.getInstance(mContext, mActivity).showToast("两次输入密码不一致，请重新输入哦");
                break;
            case ServerIsFault:
                ToastUtil.getInstance(mContext, mActivity).showToast("服务器出错了，请换个姿势重试，或者联系我们的客服吧");
                break;
            case ECRegisterFail:
                ToastUtil.getInstance(mContext, mActivity).showToast("EC注册出错了，请稍后重试");
                break;
            case ConnectChatServiceFail:
                ToastUtil.getInstance(mContext, mActivity).showToast("连接不上chat服务器，请稍后重试");
                break;
            case AccountISRemoverd:
                ToastUtil.getInstance(mContext, mActivity).showToast("您的账号已经被移除");
                break;
            case AccountIsLogUtilinInOtherDevice:
                ToastUtil.getInstance(mContext, mActivity).showToast("账号已经在其它设备上登陆");
                break;
            case ECGetFriendsListFail:
                ToastUtil.getInstance(mContext, mActivity).showToast("EC服务器获取好友列表失败");
                break;
            case MoonFriendUserIsNotExisted:
                ToastUtil.getInstance(mContext, mActivity).showToast("月友账号不存在，可能是服务器端故障，请联系管理员");
                break;
            case PasswordIsWrong:
                ToastUtil.getInstance(mContext, mActivity).showToast("密码错误");
                break;
        }
    }
}
