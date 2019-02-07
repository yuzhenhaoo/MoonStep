package priv.zxy.moonstep.commerce.presenter;

import priv.zxy.moonstep.commerce.module.biz.IUserDetailBiz;
import priv.zxy.moonstep.commerce.module.biz.UserDetailBiz;
import priv.zxy.moonstep.commerce.view.Me.IUserDetailView;

/**
 * 创建人: LYJ
 * 创建时间: 2019/2/7
 * 描述:
 **/
public class UserDetailPresenter {

    private IUserDetailBiz mIUserDetailBiz;

    private IUserDetailView mIUserDetailView;

    public UserDetailPresenter(IUserDetailView iUserDetailView){
        this.mIUserDetailView = iUserDetailView;
        mIUserDetailBiz = new UserDetailBiz();
    }

    /**
     *  设置数据
     */
    public void setRaceData(){
        mIUserDetailView.initRaceData(mIUserDetailBiz.readRaceData());
    }
}
