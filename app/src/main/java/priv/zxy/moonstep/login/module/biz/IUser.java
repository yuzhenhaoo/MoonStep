package priv.zxy.moonstep.login.module.biz;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Zxy on 2018/9/20
 */

public interface IUser {
    /**
     * doLogin方法是为了实现登陆的网络操作，而登陆的网络操作是纯粹的逻辑实现，
     * 所以我们把它写在Module层中
     * @Param mContext 上下文 配置上下文的原因是在doLogin的实现中必须要调用ToastUtil实现对用户的提示功能
     * @param userName 用户名
     * @param userPassword 用户密码
     * @param loginListener 登陆监听
     */
    void doLogin(Activity mActivity, Context mContext, String userName, String userPassword, OnLoginListener loginListener) throws InterruptedException;

    /**
     * doRegister也是纯粹的逻辑层，所以我们把它写在biz层中，用来获取网络上的数据
     * @param mActivity 得到调用的当前Activity
     * @param mContext 得到当前的上下文环境
     * @param userName 用户名
     * @param userPassword 用户密码
     * @param gender 性别
     * @param registerListener 注册监听
     */
    void doRegister(Activity mActivity, Context mContext, String userName, String userPassword, String confirmUserPassword,String gender, OnRegisterListener registerListener) throws InterruptedException;

    /**
     * doVerifyPhoneNumber用来处理手机号的验证问题，功能是连接到服务器上的数据库中并对用户的手机输入在数据库中进行查询，检查是否已经在数据库中存在
     * 属于纯粹的逻辑操作，所以把它放在module层
     * @param phoneNumber 手机账号
     * @param mContext 上下文环境
     * @param mActivity 调用当前方法的Activity
     * @param verifyPhoneNumber 验证监听
     */
    void doVerifyPhoneNumber(String phoneNumber, Context mContext, Activity mActivity, OnVerifyPhoneNumber verifyPhoneNumber) throws InterruptedException;

    void submitInfo(String country, String phone, String code, Context mContext, Activity mActivity) throws InterruptedException;

    void checkUserName(String userName, Context mContext, Activity mActivity, OnUserNameCheckListener userNameCheckListener) throws InterruptedException;

    void setChangePassword(Context mContext, Activity mActivity, String phoneNumber, String password, String confirmPassword, OnChangePasswordListener onChangePasswordListener) throws InterruptedException;

    void judgeCanJumpToChangePasswordActivity(String phoneNumber, Context mContext, Activity mActivity, OnPhoneCheckListener onPhoneCheckListener) throws InterruptedException;
}
