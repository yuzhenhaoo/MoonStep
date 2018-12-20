package priv.zxy.moonstep.framework.good;

import priv.zxy.moonstep.utils.dbUtils.UserGoodUtil;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/4
 * 描述: 道具类
 **/

public class Props extends AbstractGood {

    @Override
    public void getUserGoods(UserGoodUtil.CallBack callBack, String phoneNumber) {
        UserGoodUtil.getInstance().getUserGood(callBack, phoneNumber);
    }

    @Override
    public void setUserGood(String phoneNumber, String goodCode, int number) {

    }
}
