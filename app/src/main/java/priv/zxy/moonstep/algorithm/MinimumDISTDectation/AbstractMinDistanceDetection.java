package priv.zxy.moonstep.algorithm.MinimumDISTDectation;

import java.util.List;

import priv.zxy.moonstep.algorithm.ChooseMapDots.MapDot;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/23
 * 描述:
 **/

public abstract class AbstractMinDistanceDetection {

    private static final String TAG = "AbstractMinDistanceDetection";

    public abstract List<MapDot> getResult();

    public abstract int getResultLength();
}
