package priv.zxy.moonstep.algorithm.ChooseMapDots;

import java.util.List;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/24
 * 描述:封装策略模式的Context类，用于实现算法的切换，并利用简单工厂封装选择算法的过程
 *
 * 调用方式：
 * 客户端:DotChooseContext context = new DotChooseContext(ChooseTypeEnum.xxx);
 *        List<MapDot> dots = context.getMapDots(xxx,xxx,32);
 **/

public class DotChooseContext {

    private AbstractDotChooseAlgorithm dca;

    public DotChooseContext(ChooseTypeEnum type){
        switch (type){
            case SQUARE_CHOOSE:
                this.dca = new SquareChooseAlgorithmAbstract();
                break;
            case ROUND_CHOOSE:
                this.dca = new RoundChooseAlgorithmAbstract();
                break;
        }
    }

    public List<MapDot> listMapDots(double latitude, double longitude, int number){
        return dca.listDots(latitude, longitude, number);
    }
}
