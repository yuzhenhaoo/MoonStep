package priv.zxy.moonstep.util.ImageCacheUtil;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 创建人: LYJ
 * 创建时间: 2019/2/8
 * 描述:加密工具类
 **/
public class MD5Encoder {

    public static String encode(String string) throws Exception {
        byte[] hash = MessageDigest.getInstance("MD5").digest(string.getBytes(StandardCharsets.UTF_8));
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

}
