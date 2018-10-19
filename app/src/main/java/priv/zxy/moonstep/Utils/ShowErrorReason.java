package priv.zxy.moonstep.utils;

import android.app.Activity;
import android.content.Context;

import priv.zxy.moonstep.kernel.bean.ErrorCode;

public class ShowErrorReason {
    private Context mContext;
    private Activity mActivity;

    public ShowErrorReason(Context mContext, Activity mActivity){
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    public void show(ErrorCode errorCode){
        ToastUtil toastUtil = new ToastUtil(mContext, mActivity);
        switch (errorCode){
            case PhoneNumberISEmpty:
                toastUtil.showToast("电话号码不能为空哦");
                break;
            case NetNotResponse:
                toastUtil.showToast("网络没有响应，请稍后重试");
                break;
            case ChangePasswordFail:
                toastUtil.showToast("密码修改失败，请稍后重试");
                break;
            case JSONException:
                toastUtil.showToast("JSON数据异常，请稍后重试");
                break;
            case PhoneNumberIsRegistered:
                toastUtil.showToast("电话号码已经注册过了，请换一个重试");
                break;
            case PhoneNumberIsNotRegistered:
                toastUtil.showToast("电话号码还没有注册哦");
                break;
            case PhoneNumberOrPasswordIsWrong:
                toastUtil.showToast("手机号码未注册/密码错误，请重试");
                break;
            case PasswordFormatISNotRight:
                toastUtil.showToast("密码格式不正确，请重新输入");
                break;
            case PasswordIsEmpty:
                toastUtil.showToast("密码不能为空哦");
                break;
            case ConfirmPasswordIsEmpty:
                toastUtil.showToast("验证密码不能为空哦");
                break;
            case PasswordIsNotEqualsConfirmPassword:
                toastUtil.showToast("两次输入密码不一致，请重新输入哦");
                break;
            case ServerIsFault:
                toastUtil.showToast("服务器出错了，请换个姿势重试，或者联系我们的客服吧");
                break;
            case ECRegisterFail:
                toastUtil.showToast("EC注册出错了，请稍后重试");
                break;
            case ConnectChatServiceFail:
                toastUtil.showToast("连接不上chat服务器，请稍后重试");
                break;
            case AccountISRemoverd:
                toastUtil.showToast("您的账号已经被移除");
                break;
            case AccountIsLoginInOtherDevice:
                toastUtil.showToast("账号已经在其它设备上登陆");
                break;
            case ECGetFriendsListFail:
                toastUtil.showToast("EC服务器获取好友列表失败");
                break;
            case MoonFriendUserIsNotExisted:
                toastUtil.showToast("月友账号不存在，可能是服务器端故障，请联系管理员");
                break;
        }
    }
}
