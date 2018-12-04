package priv.zxy.moonstep.algorithm.ChooseMapDotsAlgorithm;

import java.util.List;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/24
 * 描述:定义算法家族，封装
 **/

public abstract class DotChooseAlgorithm {

    public abstract List<MapDot> createDot(double latitude, double longtutide, int number);
}
