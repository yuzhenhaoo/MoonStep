package priv.zxy.moonstep.login.view;

import priv.zxy.moonstep.data.bean.ErrorCodeEnum;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/28
 * 描述:为了实现ChangePassword的MVP架构，给activity一个实现接口，就可以完成View和Module的分离
 **/

public interface IChangePasswordView {

    /**
     * 修改密码的逻辑实现
     */
    void changePassword();

    /**
     * 展示成功修改的提示
     */
    void showSuccessTip();

    /**
     * 跳转到登陆Activity
     */
    void toLoginActivity();

    /**
     * 隐藏加载界面
     */
    void hideLoading();

    /**
     * 展示错误提示
     * @param errorCodeEnum 错误码
     */
    void showErrorTip(ErrorCodeEnum errorCodeEnum);
}
