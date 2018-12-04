package priv.zxy.moonstep.framework.community;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/3
 * 描述: 社区抽象类
 **/

abstract public class AbstractCommunity {

    public int praiseNumber = 0;

    public double longitude = 0.0;//经度

    public double latitude = 0.0;//维度

    public String address = null;

    public int getPraiseNumber() {
        return praiseNumber;
    }

    public void setPraiseNumber(int praiseNumber) {
        this.praiseNumber = praiseNumber;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
