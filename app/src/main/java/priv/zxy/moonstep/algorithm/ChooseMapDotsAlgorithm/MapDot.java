package priv.zxy.moonstep.algorithm.ChooseMapDotsAlgorithm;

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

}
