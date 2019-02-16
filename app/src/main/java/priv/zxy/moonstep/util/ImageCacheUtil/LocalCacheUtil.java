package priv.zxy.moonstep.util.ImageCacheUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 创建人: LYJ
 * 创建时间: 2019/2/8
 * 描述:图片三级缓存之本地缓存
 **/

public class LocalCacheUtil {

    private static LocalCacheUtil instance = null;

    private static final String CACHE_PATH= Environment.getExternalStorageDirectory().getAbsolutePath()+"/cacheImages";

    public static LocalCacheUtil getInstance() {
        if (instance == null) {
            instance = new LocalCacheUtil();
        }
        return instance;
    }

    /**
     * 从本地读取图片
     * @param url 根据图片url读取本地图片文件
     */
    public Bitmap getBitmapFromLocal(String url){
        String fileName;
        try {
            fileName = MD5Encoder.encode(url);
            File file = new File(CACHE_PATH, fileName);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从网络获取图片后,保存至本地缓存
     * @param url 图片url作为文件名
     * @param bitmap 图片文件
     */
    public void setBitmapToLocal(String url, Bitmap bitmap){
        BufferedOutputStream bos = null;
        try {
            String fileName = MD5Encoder.encode(url);
            File file = new File(CACHE_PATH, fileName);
            File parentFile = file.getParentFile();
            if (!parentFile.exists()){
                parentFile.mkdirs();
            }
            bos = new BufferedOutputStream(new FileOutputStream(file));
            if ((fileName.contains("shield.png") || fileName.contains(".PNG"))) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            }
            else {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (bos != null) {
                try {
                    bos.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

