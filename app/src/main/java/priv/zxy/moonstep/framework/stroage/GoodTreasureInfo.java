package priv.zxy.moonstep.framework.stroage;

import org.litepal.LitePal;

import java.util.LinkedList;
import java.util.List;

import priv.zxy.moonstep.DAO.PullGoodTreasureInfoDAO;
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
    private static GoodTreasureInfo instance = new GoodTreasureInfo();
    private LinkedList<Good> treasures = new LinkedList<>();
    /**
     * 宝物个数
     */
    private static final int TREASURE_NUMBER = 30;

    public static GoodTreasureInfo getInstance() {
        return instance;
    }
    // FIXME(张晓翼， 2019/1/25， 这里数据请求报了JSON_EXCEPTION)
    public void initGoodTreasure() {
        long millis = System.currentTimeMillis();
        int days = (int)(millis/1000/60/60);
        if (SharedPreferencesUtil.dataIsInited(SharedConstant.GOOD_TREASURE) || SharedPreferencesUtil.checkMapTime(days)) {
            PullGoodTreasureInfoDAO.getInstance().getTreasures(new PullGoodTreasureInfoDAO.Callback() {
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
