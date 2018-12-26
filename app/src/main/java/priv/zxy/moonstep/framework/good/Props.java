package priv.zxy.moonstep.framework.good;

import priv.zxy.moonstep.DAO.UserGoodDAO;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/4
 * 描述: 道具类
 **/

public class Props extends AbstractGood {

    @Override
    public void getUserGoods(UserGoodDAO.CallBack callBack, String phoneNumber) {
        UserGoodDAO.getInstance().getUserGood(callBack, phoneNumber);
    }

    @Override
    public void setUserGood(String phoneNumber, String goodCode, int number) {

    }
}
