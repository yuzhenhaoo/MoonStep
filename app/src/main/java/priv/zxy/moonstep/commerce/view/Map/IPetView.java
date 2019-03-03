package priv.zxy.moonstep.commerce.view.Map;

import priv.zxy.moonstep.framework.pet.Pet;

/**
 * 创建人: LYJ
 * 创建时间: 2019/2/3
 * 描述:宠物UI接口
 **/
public interface IPetView {

    /**
     *  UI内容设置
     */
    void setData(Pet pet);

    /**
     *  错误提示
     */
    void error(String error_msg);
}
