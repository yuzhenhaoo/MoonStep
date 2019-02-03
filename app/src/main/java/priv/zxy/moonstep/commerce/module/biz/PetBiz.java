package priv.zxy.moonstep.commerce.module.biz;

import priv.zxy.moonstep.framework.pet.Pet;
import priv.zxy.moonstep.framework.stroage.PetInfo;

/**
 * 创建人: LYJ
 * 创建时间: 2019/2/3
 * 描述:宠物信息处理实现类
 **/

public class PetBiz implements IPetBiz {

    public PetBiz(){}

    /**
     *  读取宠物信息
     */
    @Override
    public Pet readPetData(){
        return PetInfo.getInstance().getPet();
    }
}
