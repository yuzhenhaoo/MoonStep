package priv.zxy.moonstep.framework.good;

import priv.zxy.moonstep.DAO.UserGoodDAO;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/3
 * 描述: 抽象物品类
 **/

public abstract class AbstractGood {

    /**
     * 网络请求获得用户物品
     * @param callBack 回调接口
     * @param phoneNumber 用户对象
     */
    abstract void getUserGoods(UserGoodDAO.CallBack callBack, String phoneNumber);

    /**
     * 向网络上传用户物品
     * @param phoneNumber 手机号码
     * @param goodCode 物品编码
     * @param number 数量
     */
    abstract void setUserGood(String phoneNumber, String goodCode, int number);
}
