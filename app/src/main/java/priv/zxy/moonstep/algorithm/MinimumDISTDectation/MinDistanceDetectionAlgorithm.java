package priv.zxy.moonstep.algorithm.MinimumDISTDectation;

import java.util.List;

import priv.zxy.moonstep.algorithm.ChooseMapDots.MapDot;
import priv.zxy.moonstep.algorithm.ChooseMapDots.MapDotHelper;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/23
 * 描述:地图上两点最小距离检测算法
 **/

public class MinDistanceDetectionAlgorithm extends AbstractMinDistanceDetection {

    /**
     * 用户当前所在的位置坐标
     */
    private MapDot currentLocation;

    /**
     * 用户周围的地图坐标列表
     */
    private List<MapDot> srcLocations;

    /**
     * 精度半径
     */
    private int radius;

    /**
     * 在精度半径内的结果集
     */
    private List<MapDot> results = null;

    /**
     * 是否已经经过了计算得到了结果，如果没有得到结果，需要重新进行一次计算。
     */
    private static boolean isGetResult = false;

    MinDistanceDetectionAlgorithm(List<MapDot> srcLocations, MapDot currentLocation, int radius) {
        this.currentLocation = currentLocation;
        this.srcLocations = srcLocations;
        this.radius = radius;
    }

    @Override
    public List<MapDot> getResult() {
        isGetResult = true;
        for (MapDot dot:
             srcLocations) {
            if (MapDotHelper.getInstance().getTwoDotsDistanceMetar(dot, currentLocation) <= radius){
                results.add(dot);
            }
        }
        return results;
    }

    @Override
    public int getResultLength() {
        if (isGetResult){
            return results.size();
        }else{
            return getResult().size();
        }
    }
}
