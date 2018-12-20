package priv.zxy.moonstep.framework.level;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/8
 * 描述: 将整形或者字符型转换为枚举类型
 **/

public class LevelTransformUtil {

    private static LevelTransformUtil instance = null;

    public static LevelTransformUtil getInstance() {
        if (instance == null){
            synchronized (LevelTransformUtil.class){
                if (instance == null){
                    instance = new LevelTransformUtil();
                }
            }
        }
        return instance;
    }

    public LevelEnum toLevelEnum(char a){
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
        }
        return null;
    }

    public LevelEnum toLevelEnum(int a){
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
        }
        return null;
    }
}
