package priv.zxy.moonstep.algorithm;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/24
 * 描述: 封装了正方形范围的选择算法
 **/

public class SquareChooseAlgorithm extends DotChooseAlgorithm {

    @Override
    public List<MapDot> createDot(double latitude, double longtutide, int number) {
        DecimalFormat df = new DecimalFormat( "0.000000");//保留6位小数
        List<MapDot> result = new ArrayList<>();
        int i;
        for (i=0; i<=number; ++i) {
            MapDot dot = new MapDot();
            double lt = ThreadLocalRandom.current().nextDouble(-0.272727,0.272727);//维度
            double lg = ThreadLocalRandom.current().nextDouble(-0.3,0.3);//经度
            dot.setLatitude(Double.valueOf(df.format(lt)) + Double.valueOf(df.format(latitude)));
            dot.setLongitude(Double.valueOf(df.format(lg)) + Double.valueOf(df.format(longtutide)));
            result.add(dot);
        }
        return result;
    }
}
