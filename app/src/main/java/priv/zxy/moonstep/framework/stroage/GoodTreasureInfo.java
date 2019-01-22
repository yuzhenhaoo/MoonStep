package priv.zxy.moonstep.framework.stroage;

import org.litepal.LitePal;

import java.util.LinkedList;
import java.util.List;

import priv.zxy.moonstep.DAO.GetGoodTreasureDAO;
import priv.zxy.moonstep.constant.SharedConstant;
import priv.zxy.moonstep.data.bean.ErrorCodeEnum;
import priv.zxy.moonstep.framework.good.bean.Good;
import priv.zxy.moonstep.util.LogUtil;
import priv.zxy.moonstep.util.SharedPreferencesUtil;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/24
 * 描述: 宝物信息的存储类，宝物信息的刷新应该随着地图坐标的刷新而刷新
 **/
public class GoodTreasureInfo {

    private static final String TAG = "GoodTreasureInfo";
    private static GoodTreasureInfo instance;
    private LinkedList<Good> treasures = new LinkedList<>();
    /**
     * 宝物个数
     */
    private static final int TREASURE_NUMBER = 30;

    public static GoodTreasureInfo getInstance() {
        return instance;
    }

    public void initGoodTreasure() {
        if (SharedPreferencesUtil.dataIsInited(SharedConstant.GOOD_TREASURE) || 距离上次刷新的时间大于了3天) {
            GetGoodTreasureDAO.getInstance().getTreasures(new GetGoodTreasureDAO.Callback() {
                @Override
                public void onSuccess(List<Good> goods) {
                    saveGoodTreasures((LinkedList<Good>) goods);
                    SharedPreferencesUtil.setDataInited(SharedConstant.GOOD_TREASURE);
                    treasures = (LinkedList<Good>) goods;
                }

                @Override
                public void onFail(ErrorCodeEnum code) {
                    LogUtil.e(TAG, "错误码" + code);
                }
            }, UserSelfInfo.getInstance().getMySelf().getPhoneNumber(), TREASURE_NUMBER);
        } else {
            treasures = new LinkedList<>(LitePal.findAll(Good.class));
        }
    }

    public LinkedList<Good> getTreasures() {
        if (treasures == null) {
            treasures = new LinkedList<>(LitePal.findAll(Good.class));
        }
        return treasures;
    }

    private void saveGoodTreasures(LinkedList<Good> treasures){
        for(Good treasure : treasures){
            Good good = new Good();
            good.setGoodCode(treasure.getGoodCode());
            good.setGoodGameValue(treasure.getGoodGameValue());
            good.setGoodImagePath(treasure.getGoodImagePath());
            good.setGoodLevel(treasure.getGoodLevel());
            good.setGoodLifeValue(treasure.getGoodLifeValue());
            good.setGoodName(treasure.getGoodName());
            good.setGoodSubscription(treasure.getGoodSubscription());
            good.setNumber(treasure.getNumber());
            good.save();
        }
    }
}
