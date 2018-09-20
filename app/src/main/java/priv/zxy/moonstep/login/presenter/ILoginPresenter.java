package priv.zxy.moonstep.login.presenter;

/**
 * Created by Zxy on 2018/9/20
 *
 * ILoginPresenter用来完成Presenter层和View层的交互
 */

public interface ILoginPresenter {
    void clear();       //清空输入框的数据
    void doLogin(String userName, String userPassword);
    void setProgressBarVisiblity(int visibility);
}
