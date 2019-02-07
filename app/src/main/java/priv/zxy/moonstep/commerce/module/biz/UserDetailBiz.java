package priv.zxy.moonstep.commerce.module.biz;


import priv.zxy.moonstep.framework.race.Race;
import priv.zxy.moonstep.framework.stroage.UserRaceInfo;

/**
 * 创建人: LYJ
 * 创建时间: 2019/2/7
 * 描述:用户具体信息数据处理
 **/
public class UserDetailBiz implements IUserDetailBiz{

    public UserDetailBiz(){}

    /**
     * 读取用户种族信息
     * @return race
     */
    @Override
    public Race readRaceData(){
        return UserRaceInfo.getInstance().getRace();
    }
}
