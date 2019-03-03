package priv.zxy.moonstep.framework.user;

import priv.zxy.moonstep.framework.authority.base.AbstractAuthority;
import priv.zxy.moonstep.framework.community.AbstractCommunity;
import priv.zxy.moonstep.framework.good.AbstractGood;
import priv.zxy.moonstep.framework.level.LevelEnum;
import priv.zxy.moonstep.framework.location.AbstractLocation;
import priv.zxy.moonstep.framework.message.Message;
import priv.zxy.moonstep.framework.pet.AbstractPet;
import priv.zxy.moonstep.framework.race.AbstractRace;
import priv.zxy.moonstep.framework.state.UserStateEnum;
import priv.zxy.moonstep.framework.title.AbstractTitle;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/3
 * 描述: 继承了User类的具体User类，什么叫做具体呢？就是说，在User的基础上，
 * 增加了更多广泛的信息，这是一个庞大的系统，基于User存在而已，是整个frameWork层的核心。
 **/

public class ComplateUser extends User {

    /**
     * 抽象权限类
     */
    private AbstractAuthority authority;

    /**
     * 抽象社区类
     */
    private AbstractCommunity community;

    /**
     * 抽象位置
     */
    private AbstractLocation location;

    /**
     * 抽象称号类
     */
    private AbstractTitle title;

    /**
     * 抽象消息类
     */
    private Message message;

    /**
     * 抽象状态类
     */
    private UserStateEnum state;

    /**
     * 抽象种族类
     */
    private AbstractRace race;

    /**
     * 抽象宠物类
     */
    private AbstractPet pet;

    /**
     * 抽象阶别类
     */
    private LevelEnum level;

    /**
     * 抽象物品类
     */
    private AbstractGood good;

}
