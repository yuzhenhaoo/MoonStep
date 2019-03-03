package priv.zxy.moonstep.commerce.presenter;

import priv.zxy.moonstep.commerce.module.biz.IPetBiz;
import priv.zxy.moonstep.commerce.module.biz.PetBiz;
import priv.zxy.moonstep.commerce.view.Map.IPetView;

/**
 * 创建人: LYJ
 * 创建时间: 2019/2/3
 * 描述:
 **/
public class PetPresenter {

    private IPetBiz mIPetBiz;

    private IPetView mIPetView;

    public PetPresenter(IPetView iPetView){
        this.mIPetView = iPetView;
        mIPetBiz = new PetBiz();
    }

    /**
     *  设置数据
     */
    public void setData(){
        mIPetView.setData(mIPetBiz.readPetData());
    }
}
