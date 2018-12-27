package priv.zxy.moonstep.algorithm.ChooseMapDots;

import org.litepal.crud.LitePalSupport;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/24
 * 描述: 为了便于地图上每个点的表示，创建MapDot作为点的数据结构，用来封装经纬度
 **/

public class MapDot extends LitePalSupport {

    private double latitude = 0.0;

    private double longitude = 0.0;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object obj) {
        MapDot object = (MapDot) obj;
        if (this == object) return true; // 检查this和obj是不是同一个对象
        if (object == null) return false;// 检查obj是不是为空
        if (getClass() != object.getClass()) return false; //判断this与obj是不是同一个类，分为两种情况
        if (!(obj instanceof MapDot)) return false;
        return object.getLatitude() == this.getLatitude() && object.getLongitude() == this.getLongitude();
    }
}
