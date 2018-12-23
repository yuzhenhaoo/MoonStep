package priv.zxy.moonstep.algorithm.MinimumDISTDectation;

import java.util.List;

import priv.zxy.moonstep.algorithm.ChooseMapDots.MapDot;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/23
 * 描述: 最小距离检测算法策略模式的Context类
 * 目的是根据客户端的需求产生相应的算法子类，使得算法的更变不会动用户端造成影响
 **/

public class MinDISTDetectionContext<T> {

    private MapDot currentLocation;
    private List<MapDot> srcLocations;
    private int radius;

    public MinDISTDetectionContext(MapDot currentLocation, List<MapDot> srcLocations, int radius){
        this.currentLocation = currentLocation;
        this.srcLocations = srcLocations;
        this.radius = radius;
    }

    public AbstractMinDISTDetection getObj(MinDISTType minDISTType){
        AbstractMinDISTDetection obj = null;
        switch (minDISTType){
            case MIN_DIST_TYPE:
                obj = new MinDISTDetectionAlgorithm(srcLocations, currentLocation, radius);
                break;
        }
        return obj;
    }
}
