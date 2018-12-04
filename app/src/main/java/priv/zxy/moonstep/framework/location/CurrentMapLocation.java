package priv.zxy.moonstep.framework.location;

import java.util.ArrayList;
import java.util.List;

import priv.zxy.moonstep.algorithm.ChooseMapDotsAlgorithm.MapDot;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/4
 * 描述: 当前地图的位置，也就是说，是含有当前所有的地图坐标都存储在这个类当中
 *       要进行的网络操作也全部是存储在这个类当中，如果这里不想要使用缓存的话，可以利用备忘录模式来暂时的存储地图坐标（但是这样应该每次加载都得存，会造成性能上的浪费）
 **/

public class CurrentMapLocation extends AbstractLocation{

    private List<MapDot> mapDots = new ArrayList<>();

    private void add(MapDot dot){
        if (mapDots != null){
            mapDots.add(dot);
        }
    }

    private void addAll(List<MapDot> lists){
        if (mapDots != null){
            mapDots.addAll(lists);
        }
    }

    private void clear(){
        if (mapDots != null){
            mapDots.clear();
        }
    }

    public List<MapDot> getAllMapDots(){
        if (mapDots != null){
            return mapDots;
        }
        return null;
    }

    @Override
    public void setMapDots(){
        /**
         * 这里应该做相应的网络操作
         */
    }
}
