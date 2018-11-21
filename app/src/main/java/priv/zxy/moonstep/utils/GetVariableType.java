package priv.zxy.moonstep.utils;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/21
 * 描述: 返回对象的数据类型
 *      This util is used to return type of object
 **/
public class GetVariableType {

    public static String getType(Object o){
        return o.getClass().toString();
    }
    public static String getType(int o){
        return "int";
    }
    public static String getType(byte o){
        return "byte";
    }
    public static String getType(char o){
        return "char";
    }
    public static String getType(double o){
        return "double";
    }
    public static String getType(float o){
        return "float";
    }
    public static String getType(long o){
        return "long";
    }
    public static String getType(boolean o){
        return "boolean";
    }
    public static String getType(short o){
        return "short";
    }
    public static String getType(String o){
        return "String";
    }
}
