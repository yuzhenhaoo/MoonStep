package priv.zxy.moonstep.commerce.module.biz;

import priv.zxy.moonstep.framework.race.Race;

/**
 * 创建人: LYJ
 * 创建时间: 2019/2/7
 * 描述:用户具体信息处理接口
 **/
public interface IUserDetailBiz {

    /**
     *  用户种族信息处理
     */
    Race readRaceData();
}
