package priv.zxy.moonstep.login.module;

import priv.zxy.moonstep.login.presenter.OnLoginListener;

/**
 *  Created by Zxy on 2018/9/20
 */
public interface IUser {
    public void login(String userName, String userPassword, OnLoginListener loginListener);
}
