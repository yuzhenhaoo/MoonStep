package priv.zxy.moonstep.framework.level;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/8
 * 描述: 将整形或者字符型转换为枚举类型
 **/

public class LevelTransformUtil {

    public static LevelEnum toLevelEnum(char a){
        switch (a){
            case '1':
                return LevelEnum.FIRST_LEVEL;
            case '2':
                return LevelEnum.SECOND_LEVEL;
            case '3':
                return LevelEnum.THIRD_LEVEL;
            case '4':
                return LevelEnum.FORTH_LEVEL;
            case '5':
                return LevelEnum.FIFTH_LEVEL;
            case '6':
                return LevelEnum.SIXTH_LEVEL;
            case '7':
                return LevelEnum.SEVENTH_LEVEL;
            case '8':
                return LevelEnum.EIGHTH_LEVEL;
            case '9':
                return LevelEnum.NINTH_LEVEL;
            case '0':
                return LevelEnum.TENTH_LEVEL;
            default:
        }
        return null;
    }

    public static LevelEnum toLevelEnum(int a){
        switch (a){
            case 1:
                return LevelEnum.FIRST_LEVEL;
            case 2:
                return LevelEnum.SECOND_LEVEL;
            case 3:
                return LevelEnum.THIRD_LEVEL;
            case 4:
                return LevelEnum.FORTH_LEVEL;
            case 5:
                return LevelEnum.FIFTH_LEVEL;
            case 6:
                return LevelEnum.SIXTH_LEVEL;
            case 7:
                return LevelEnum.SEVENTH_LEVEL;
            case 8:
                return LevelEnum.EIGHTH_LEVEL;
            case 9:
                return LevelEnum.NINTH_LEVEL;
            case 10:
                return LevelEnum.TENTH_LEVEL;
            default:
        }
        return null;
    }

    public static LevelEnum toLevelEnum(String a){
        switch (a){
            case "一阶":
                return LevelEnum.FIRST_LEVEL;
            case "二阶":
                return LevelEnum.SECOND_LEVEL;
            case "三阶":
                return LevelEnum.THIRD_LEVEL;
            case "四阶":
                return LevelEnum.FORTH_LEVEL;
            case "五阶":
                return LevelEnum.FIFTH_LEVEL;
            case "六阶":
                return LevelEnum.SIXTH_LEVEL;
            case "七阶":
                return LevelEnum.SEVENTH_LEVEL;
            case "八阶":
                return LevelEnum.EIGHTH_LEVEL;
            case "九阶":
                return LevelEnum.NINTH_LEVEL;
            case "十阶":
                return LevelEnum.TENTH_LEVEL;
            default:
        }
        return null;
    }
}
