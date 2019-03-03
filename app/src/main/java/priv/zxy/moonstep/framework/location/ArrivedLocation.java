package priv.zxy.moonstep.framework.location;

import java.util.ArrayList;
import java.util.List;

import priv.zxy.moonstep.algorithm.ChooseMapDots.MapDot;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/3
 * 描述: 已到达过的位置
 *       注意，这里已经到达过的位置要存到服务端的数据库中，所以这里的getNumber和setNumber实质上都是网络服务
 **/

public class ArrivedLocation extends AbstractLocation {

    private List<MapDot> mapDots = new ArrayList<>();

    private int number = 0;

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    /**
     * 这里应该是从网络上获得所有已经到达过的map坐标
     */
    @Override
    void setMapDots() {
        /*
         * 这里做相应的网络操作
         */
    }
}
