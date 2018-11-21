package priv.zxy.moonstep.wheel.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/20
 * 描述: 专门用来传输文件和接收文件的Http请求类
 **/

public class FileHttp extends HttpBase {

    private static String URL;

    public static void setURL(String URL) {
        FileHttp.URL = URL;
    }

    public static String getURL() {
        return URL;
    }

    /**
     * 继承父亲的doPost方法，得到一个包含文件内容的response，然后自己解析后得到文件的内容
     * @param onHttpResultListener HttpBase监听器
     */
    public void doPostFromHttp(OnHttpResultListener onHttpResultListener){
        super.doPost(onHttpResultListener);
    }

    /**
     * 向给定Http地址提交一个文件
     * @param onHttpResultListener HttpBase监听器
     */
    public void doPostToHttp(OnHttpResultListener onHttpResultListener, String filePath){
        File file = new File(filePath);
            try{
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(read);
                String line_txt = null;
                StringBuilder params = new StringBuilder();
                while ((line_txt = bufferedReader.readLine()) != null){
                    params.append(line_txt);
                }
                byte[] bytes = params.toString().getBytes();

                /*
                * 然后就是文件的网络传输
                 */
            }catch (IOException e){
                /*
                 * 调用日志工具类将异常和错误分别进行错误的存储
                 */
        }
    }
}
