package priv.zxy.moonstep.DAO;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import priv.zxy.moonstep.data.bean.ErrorCodeEnum;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/19
 * 描述:
 **/
public class DownloadFileUtil {

    private static final String TAG = "DownloadFileUtil";

    private static DownloadFileUtil instance;

    public static DownloadFileUtil getInstance(){
        if (instance == null){
            synchronized (DownloadFileUtil.class){
                if (instance == null){
                    instance = new DownloadFileUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 根据某个URL，请求所得数据，由回调方法进行返回。
     * @param onResultListener
     * @param url
     */
    public void downloadFileFromHttp(final OnResultListener onResultListener, String url) {
       new Thread(() -> {
           OkHttpClient client = new OkHttpClient();
           Request request = new Request.Builder()
                   //指定访问的服务器地址
                    .url(url)
                   .build();
           try {
               Response response = client.newCall(request).execute();
               String responseData = response.body().string();
               onResultListener.onSuccess(responseData);
               Log.d(TAG, responseData);
           } catch (IOException e) {
               e.printStackTrace();
           }
       }).start();
    }

    public interface OnResultListener{

        void onSuccess(String content);

        void onFail(ErrorCodeEnum errorCode);
    }
}
