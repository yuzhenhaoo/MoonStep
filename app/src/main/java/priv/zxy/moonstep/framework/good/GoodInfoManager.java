package priv.zxy.moonstep.framework.good;

import java.util.List;

import priv.zxy.moonstep.DAO.PullUserGoodInfoDAO;
import priv.zxy.moonstep.framework.good.bean.Good;

/**
 * 创建人: Administrator
 * 创建时间: 2019/1/6
 * 描述: 描述: 存储用户物品信息的一个临时类
 *            在加载用户物品信息的时候开始初始化数据
 *           在程序结束的时候，需要清空所有的引用。
 **/

public class GoodInfoManager extends AbstractGood{

    /**
     * 存储当前用户信息的Good对象
     * 注意，这里是引用对象，使用完毕后，要记得清除引用
     */
    private List<Good> goods;

    /**
     * 使用饿汉式是为了提高效率
     */
    private static GoodInfoManager instance = new GoodInfoManager();

    public static GoodInfoManager getInstance() {
        return instance;
    }

    public void add(Good good) {
        if (goods.contains(good)) {
            return;
        }
        goods.add(good);
    }

    public void addAll(List<Good> goods) {
        this.goods = goods;
    }

    public List<Good> getGoods() {
        return goods;
    }

    /**
     * 清理引用，以便JVM调用GC
     */
    public void clear() {
        goods = null;
    }

    @Override
    public void getUserGoods(PullUserGoodInfoDAO.CallBack callBack, String phoneNumber) {
        PullUserGoodInfoDAO.getInstance().getUserGood(callBack, phoneNumber);
    }

    @Override
    public void setUserGood(String phoneNumber, String goodCode, int number) {

    }
}
