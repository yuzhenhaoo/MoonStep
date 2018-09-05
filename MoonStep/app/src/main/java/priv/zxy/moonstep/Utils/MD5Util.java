package priv.zxy.moonstep.Utils;

import android.support.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    @NonNull
    public static String md5(String text){
        MessageDigest myDigest = null;
        try{
            myDigest = MessageDigest.getInstance("MD5");
            //得到text的固定长度的哈希值
            byte[] result = myDigest.digest(text.getBytes("UTF-8"));
            //使用线程安全的StringBuffer
            StringBuffer sb = new StringBuffer();
            //将result的每个字节转换成十六进制的String类型并进行存储
            for (byte b : result){
                int number = b & 0xff;
                String hex = Integer.toHexString(number);
                if(hex.length() == 1){
                    sb.append("0" + hex);
                }else{
                    sb.append(hex);
                }
            }
            return sb.toString();
        }catch (NoSuchAlgorithmException | UnsupportedEncodingException e){
            //这里应该打印到日志文件
            //格式：错误信息+时间戳
            e.printStackTrace();
            return "";
        }
    }
}
