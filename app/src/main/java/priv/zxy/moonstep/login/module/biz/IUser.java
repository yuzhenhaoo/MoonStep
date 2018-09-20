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
    public void doLogin(Activity mActivity, Context mContext, String userName, String userPassword, OnLoginListener loginListener);

    /**
     * doRegister也是纯粹的逻辑层，所以我们把它写在biz层中，用来获取网络上的数据
     * @param mActivity 得到调用的当前Activity
     * @param mContext 得到当前的上下文环境
     * @param userName 用户名
     * @param userPassword 用户密码
     * @param gender 性别
     * @param registerListener 注册监听
     */
    public void doRegister(Activity mActivity, Context mContext, String userName, String userPassword, String gender, OnRegisterListener registerListener);
}
