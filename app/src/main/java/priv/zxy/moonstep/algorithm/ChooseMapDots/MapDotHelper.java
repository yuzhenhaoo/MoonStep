package priv.zxy.moonstep.algorithm.ChooseMapDots;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/23
 * 描述:
 **/

public class MapDotHelper {

    private static MapDotHelper instance = null;

    public static MapDotHelper getInstance() {
        if (instance == null){
            synchronized (MapDotHelper.class){
                if (instance == null){
                    instance = new MapDotHelper();
                }
            }
        }
        return instance;
    }

    public float getTwoDotsDistanceCoordinate(MapDot mapDot1, MapDot mapDot2){
        return (float)Math.sqrt(Math.pow(Math.abs(mapDot1.getLatitude() - mapDot2.getLatitude()), 2) + Math.pow(Math.abs(mapDot1.getLongitude() - mapDot2.getLongitude()), 2));
    }

    public int getTwoDotsDistanceMetar(MapDot mapDot1, MapDot mapDot2){
        return coordinateToMetar(getTwoDotsDistanceCoordinate(mapDot1, mapDot2));
    }

    public int coordinateToMetar(float coordinate){
        return (int)(coordinate*100000);
    }
}
