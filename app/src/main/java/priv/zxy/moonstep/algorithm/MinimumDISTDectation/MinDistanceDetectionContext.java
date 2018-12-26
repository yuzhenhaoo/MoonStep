package priv.zxy.moonstep.algorithm.MinimumDISTDectation;

import java.util.List;

import priv.zxy.moonstep.algorithm.ChooseMapDots.MapDot;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/23
 * 描述: 最小距离检测算法策略模式的Context类
 * 目的是根据客户端的需求产生相应的算法子类，使得算法的更变不会动用户端造成影响
 **/

public class MinDistanceDetectionContext {

    private MapDot currentLocation;
    private List<MapDot> srcLocations;
    private int radius;
    private AbstractMinDistanceDetection obj = null;

    public MinDistanceDetectionContext(MapDot currentLocation, List<MapDot> srcLocations, int radius, MinDistanceType minDISTType){
        this.currentLocation = currentLocation;
        this.srcLocations = srcLocations;
        this.radius = radius;
        switch (minDISTType){
            case MIN_DIST_TYPE:
                obj = new MinDistanceDetectionAlgorithm(srcLocations, currentLocation, radius);
                break;
        }
    }

    /**
     * 获得精度半径范围内的半藏列表
     * @return
     */
    public List<MapDot> getResult(){
        return obj.getResult();
    }

    /**
     * 获得精度半径范围内的宝藏数目
     * @return
     */
    public int getLength(){
        return obj.getResultLength();
    }

}
