package priv.zxy.moonstep.util;

import android.app.Activity;
import android.content.Context;

import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.data.bean.ErrorCodeEnum;

public class ShowErrorReasonUtil {

    private static ShowErrorReasonUtil instance;

    private Activity mActivity;

    private Context mContext;

    ShowErrorReasonUtil(Activity mActivity){
        mContext = Application.getContext();
        this.mActivity = mActivity;
    }
    public static ShowErrorReasonUtil getInstance(Activity mActivity){
        if (instance == null){
            synchronized(ShowErrorReasonUtil.class){
                if (instance == null){
                    instance = new ShowErrorReasonUtil(mActivity);
                }
            }
        }
        return instance;
    }

    public void show(ErrorCodeEnum errorCode){
        switch (errorCode){
            case PHONE_NUMBER_IS_EMPTY:
                ToastUtil.getInstance(mContext, mActivity).showToast("电话号码不能为空哦");
                break;
            case NET_NOT_RESPONSE:
                ToastUtil.getInstance(mContext, mActivity).showToast("网络没有响应，请稍后重试");
                break;
            case CHANGE_PASSWORD_FAIL:
                ToastUtil.getInstance(mContext, mActivity).showToast("密码修改失败，请稍后重试");
                break;
            case JSON_EXCEPTION:
                ToastUtil.getInstance(mContext, mActivity).showToast("JSON数据异常，请稍后重试");
                break;
            case PHONE_NUMBER_IS_REGISTERED:
                ToastUtil.getInstance(mContext, mActivity).showToast("电话号码已经注册过了，请换一个重试");
                break;
            case PHONE_NUMBER_IS_NOT_REGISTERED:
                ToastUtil.getInstance(mContext, mActivity).showToast("电话号码还没有注册哦");
                break;
            case PHONE_NUMBER_OR_PASSWORD_IS_WRONG:
                ToastUtil.getInstance(mContext, mActivity).showToast("手机号码未注册/密码错误，请重试");
                break;
            case PASSWORD_FORMAT_IS_NOT_RIGHT:
                ToastUtil.getInstance(mContext, mActivity).showToast("密码格式不正确，请重新输入");
                break;
            case PASSWORD_IS_EMPTY:
                ToastUtil.getInstance(mContext, mActivity).showToast("密码不能为空哦");
                break;
            case CONFIRM_PASSWORD_IS_EMPTY:
                ToastUtil.getInstance(mContext, mActivity).showToast("验证密码不能为空哦");
                break;
            case PASSWORD_IS_NOT_EQUALS_CONFIRM_PASSWORD:
                ToastUtil.getInstance(mContext, mActivity).showToast("两次输入密码不一致，请重新输入哦");
                break;
            case SERVER_IS_FAULT:
                ToastUtil.getInstance(mContext, mActivity).showToast("服务器出错了，请换个姿势重试，或者联系我们的客服吧");
                break;
            case EC_REGISTER_FAIL:
                ToastUtil.getInstance(mContext, mActivity).showToast("EC注册出错了，请稍后重试");
                break;
            case CONNECT_CHAT_SERVICE_FAIL:
                ToastUtil.getInstance(mContext, mActivity).showToast("连接不上chat服务器，请稍后重试");
                break;
            case ACCOUNT_IS_DELETED:
                ToastUtil.getInstance(mContext, mActivity).showToast("您的账号已经被移除");
                break;
            case ACCOUNT_IS_LOGGING_IN_OTHER_DEVICE:
                ToastUtil.getInstance(mContext, mActivity).showToast("账号已经在其它设备上登陆");
                break;
            case EC_GET_FRIENDS_LIST_FAIL:
                ToastUtil.getInstance(mContext, mActivity).showToast("EC服务器获取好友列表失败");
                break;
            case USER_IS_NOT_EXISTED:
                ToastUtil.getInstance(mContext, mActivity).showToast("月友账号不存在，可能是服务器端故障，请联系管理员");
                break;
            case PASSWORD_IS_WRONG:
                ToastUtil.getInstance(mContext, mActivity).showToast("密码错误");
                break;
        }
    }

    public void clear(){
        instance = null;
    }
}
