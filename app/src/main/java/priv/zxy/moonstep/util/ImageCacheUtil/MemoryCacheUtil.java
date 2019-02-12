package priv.zxy.moonstep.util.ImageCacheUtil;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * 创建人: LYJ
 * 创建时间: 2019/2/8
 * 描述:图片三级缓存之内存缓存
 **/

public class MemoryCacheUtil {

    private static MemoryCacheUtil instance = null;

    private LruCache<String,Bitmap> mMemoryCache;

    public static MemoryCacheUtil getInstance() {
        if (instance == null) {
            instance = new MemoryCacheUtil();
        }
        return instance;
    }

    public MemoryCacheUtil(){
        // 超过指定内存,则开始回收
        long maxMemory = Runtime.getRuntime().maxMemory()/8;
        mMemoryCache = new LruCache<String,Bitmap>((int) maxMemory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int byteCount = value.getByteCount();
                return byteCount;
            }
        };
    }

    /**
     * 从内存中读图片
     * @param url 图片url作为key从内存读取
     */
    public Bitmap getBitmapFromMemory(String url) {
        Bitmap bitmap = mMemoryCache.get(url);
        return bitmap;
    }

    /**
     * 往内存中写图片
     * @param url 图片url作为key从内存读取
     * @param bitmap 图片文件
     */
    public void setBitmapToMemory(String url, Bitmap bitmap) {
        mMemoryCache.put(url,bitmap);
    }

    /**
     * 从内存删除图片
     */
    public void removeBitmapToMemory(String url){
        mMemoryCache.remove(url);
    }
}

