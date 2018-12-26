package priv.zxy.moonstep.framework.good;

import priv.zxy.moonstep.DAO.UserGoodDAO;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/3
 * 描述: 抽象物品类
 **/

public abstract class AbstractGood {

    abstract void getUserGoods(UserGoodDAO.CallBack callBack, String phoneNumber);

    abstract void setUserGood(String phoneNumber, String goodCode, int number);
}
