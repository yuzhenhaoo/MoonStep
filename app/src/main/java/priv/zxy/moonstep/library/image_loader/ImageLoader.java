package priv.zxy.moonstep.library.image_loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 张晓翼
 * @createTime 2019/3/8 0008
 * @Describe 图片加载类
 *            单一职责：负责图片的加载
 */

public class ImageLoader {
    /**
     * 设置当前缓存管理器，默认是磁盘+内存双缓存
     */
    private CacheManager mCacheManager = new DoubleCache();

    /**
     * 设置线程数量为cpu核数的线程池
     */
    ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private ImageLoader() {}

    Handler mUiHandler = new Handler(Looper.getMainLooper());

    private void updateImageView(Bitmap bitmap, ImageView view) {
        mUiHandler.post(() -> {
            if (bitmap == null) return;
            view.setImageBitmap(bitmap);
        });
    }

    public static ImageLoader getInstance() {
        return ImageLoaderHolder.instance;
    }

    /**
     * 设置内存缓存器{@link CacheManager}，支持MemoryCache, DickCache, DoubleCache三种
     * @param cm 缓存器
     */
    public void setCacheManager(CacheManager cm) {
        this.mCacheManager = cm;
    }

    public void setCacheManager(CacheLevel cacheLevel) {
        switch (cacheLevel) {
            case MEMORY_CACHE:
                this.mCacheManager = new MemoryCache();
                break;
            case DISK_CACHE:
                this.mCacheManager = new DiskCache();
                break;
            case DOUBLE_CACHE:
                this.mCacheManager = new DoubleCache();
                break;
        }
    }

    /**
     * 将url请求的图片显示在view上
     * @param imageUrl 图片地址
     * @param view 要展示图片的ImageView
     */
    public void showImage(String imageUrl, ImageView view) {
        Bitmap bitmap = mCacheManager.get(imageUrl);
        /*
         * 缓存中没有数据
         */
        if (bitmap == null) {
            submitLoadRequest(imageUrl, view);
            return;
        }
        view.setImageBitmap(bitmap);
    }
    
    private void submitLoadRequest(String imageUrl, ImageView view) {
        mExecutorService.submit(() -> {
            Bitmap bitmap = downloadImage(imageUrl);
            if (bitmap == null) {
                return;
            }
            updateImageView(bitmap, view);
            mCacheManager.put(imageUrl, bitmap);
        });
    }

    /**
     * 根据图片地址下载图片
     * 用的是HttpUrlConnection，方式是同步
     * @param imageUrl 图片地址
     * @return 位图对象
     */
    public Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            final HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 静态内部类实现单例
     */
    private static class ImageLoaderHolder {
        private static final ImageLoader instance = new ImageLoader();
    }

    /**
     * 缓存级别，默认提供三种级别
     * - 内存缓存
     * - 磁盘缓存
     * - 双缓存
     */
    public enum CacheLevel {
        MEMORY_CACHE,
        DISK_CACHE,
        DOUBLE_CACHE
    }
}
