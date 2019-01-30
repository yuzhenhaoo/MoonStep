package priv.zxy.moonstep.framework.stroage;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import priv.zxy.moonstep.algorithm.ChooseMapDots.ChooseTypeEnum;
import priv.zxy.moonstep.algorithm.ChooseMapDots.DotChooseContext;
import priv.zxy.moonstep.algorithm.ChooseMapDots.MapDot;
import priv.zxy.moonstep.util.SharedPreferencesUtil;

/**
 * 地图寻宝坐标的存储类
 */
public class MapDotsInfo {

    private static final int MAP_DOT_NUMBER = 32;
    private static MapDotsInfo instance = new MapDotsInfo();
    private List<MapDot> dots = new ArrayList<>();

    public static MapDotsInfo getInstance() {
        return instance;
    }

    /**
     * 检测并初始化地图坐标
     */
    public boolean initMapDots(double latitude, double longitude) {
        long millis = System.currentTimeMillis();
        int days = (int)(millis/1000/60/60);
        if (SharedPreferencesUtil.checkMapTime(days)){
            // 这里对32个宝藏位置坐标进行刷新，如果成功的话，就存入sqlite数据库中，并在地图上显示
            DotChooseContext context = new DotChooseContext(ChooseTypeEnum.SQUARE_CHOOSE);
            dots = context.listMapDots(latitude, longitude, MAP_DOT_NUMBER);
            saveMapDots(dots);
            return true;
        } else {
            dots = LitePal.findAll(MapDot.class);
            return false;
        }
    }

    public void initMapDots() {
        dots = LitePal.findAll(MapDot.class);
        if (dots == null) {
            // 进入了这个地方，说明还没有对地图坐标进行存储就进行了读取
            throw new RuntimeException("MapDots还没有被存储进入数据库，请检查逻辑错误");
        }
    }

    /**
     * 获得所有的宝藏坐标
      @return 坐标的集合
     */
    public List<MapDot> getDots() {
        if (dots == null) {
            dots = LitePal.findAll(MapDot.class);
        }
        return dots;
    }

    /**
     * 将32个经纬度坐标存储到LitePal数据库中
     * @param mapDots 地图上的所有寻宝坐标
     */
    private void saveMapDots(List<MapDot> mapDots){
        clearMapDots();
        for(MapDot mapDot : mapDots){
            MapDot dot = new MapDot();
            dot.setLatitude(mapDot.getLatitude());
            dot.setLongitude(mapDot.getLongitude());
            dot.save();
        }
    }

    /**
     * 每次存储之前，需要将当前已有的节点全部清除
     * 删除所有id值大于0的数据
     */
    private void clearMapDots(){
        LitePal.deleteAll(MapDot.class, "id > ?", "0");
    }
}
