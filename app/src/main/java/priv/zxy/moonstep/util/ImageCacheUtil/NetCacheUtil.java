package priv.zxy.moonstep.util.ImageCacheUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 创建人: LYJ
 * 创建时间: 2019/2/8
 * 描述:图片三级缓存之网络缓存
 **/
public class NetCacheUtil {

    private static NetCacheUtil instance = null;

    public static NetCacheUtil getInstance() {
        if (instance == null) {
            instance = new NetCacheUtil();
        }
        return instance;
    }

    /**
     * 网络下载图片
     * @param url 图片下载地址
     * @return 返回图片文件
     */
    public Bitmap downLoadBitmap(String url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                options.inPreferredConfig=Bitmap.Config.ARGB_4444;
                Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream(),null, options);
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert conn != null;
            conn.disconnect();
        }
        return null;
    }
}
