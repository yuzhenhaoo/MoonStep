package priv.zxy.moonstep.DAO.ImageInfo;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import priv.zxy.moonstep.DAO.constant.UrlBase;
import priv.zxy.moonstep.util.LogUtil;

/**
 * @author 张晓翼
 * @createTime 2019/2/20 0020
 * @Describe 利用图片现有URI上传到远程服务器的类
 *            使用框架:RxJava + Retrofit
 */
public class PullImage2Server {
    private static final String TAG = "PullImage2Server";
    private static final String URL_PATH = UrlBase.PULL_IMAGE_TO_SERVER_URL;

    public static void upLoad2Server(String path, AsyncHttpResponseHandler handler){
        // 手机端要上传的文件，首先要保存你手机上存在该文件
        // String filePath = Environment.getExternalStorageDirectory() +
        // "/1delete/1.jpg";
        // String filePath ="/sdcard/1delete/1.jpg"; ///可以
        // String filePath ="/sdcard/11/软工大作业.ppt";///可以
        // String filePath ="/sdcard/音乐/许嵩-千古.mp3";////别忘了/sdcard开头，，可以
        // /String filePath ="/sdcard/相机/22222.mp4"; ///30M 不可以
        File file = new File(path);
        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.setTimeout(60 * 60 * 1000);
        RequestParams param = new RequestParams();
        try {
            param.put("file", file);
            httpClient.post(URL_PATH, param, handler);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LogUtil.d(TAG, "上传文件不存在");
        }

    }

}
