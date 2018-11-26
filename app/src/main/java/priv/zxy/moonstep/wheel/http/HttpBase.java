package priv.zxy.moonstep.wheel.http;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/20
 * 描述: 本库的调用，需要配置好okhttp3的依赖
 **/
public class HttpBase extends AbstractHttpBase {

    private static String URL = null;

    public static void setURL(String URL) {
        HttpBase.URL = URL;
    }

    public static String getURL() {
        return URL;
    }

    /**
     * 直接封装okhttp的get请求库，方法是异步，满足大部分对Get请求的需要
     * 然后如果获得结果以后，它的结果会直接以okhttp3的Response进行返回，根据情况来进行动态解析，保证了灵活性。
     * @param onHttpResultListener HttpBase监听器
     */
    @Override
    public void doGet(OnHttpResultListener onHttpResultListener) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                /*
                 * 调用日志工具类将异常和错误分别进行错误的存储
                 * 并投出运行时异常，让程序停止
                 */
                throw new RuntimeException();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response){
                onHttpResultListener.onResult(response);
            }
        });
    }

    /**
     * 封装okhttp的post请求，异步方法，没有参数的传递
     * @param onHttpResultListener HttpBase监听器
     */
    @Override
    public void doPost(OnHttpResultListener onHttpResultListener) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .build();

        Request request = new Request.Builder()
                .url(URL)
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                /*
                 * 调用日志工具类将异常和错误分别进行错误的存储
                 * 并投出运行时异常，让程序停止
                 */
                throw new RuntimeException();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                onHttpResultListener.onResult(response);
            }
        });
    }

    /**
     * 重载doPost()，可以附加Map的参数，可以添加请求头（不需要的话传空也可以,headers空值）
     * @param onHttpResultListener HttpBase监听器
     * @param params Map类型的传递参数
     */
    public void doPost(OnHttpResultListener onHttpResultListener, Map<String, String> params, Map<String, String> headers){
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        for (String key: params.keySet()){
            builder.add(key, params.get(key));
        }

        RequestBody formBody = builder.build();

        Request request;
        if (headers != null){
            request = new Request.Builder()
                    .url(URL)
                    .post(formBody)
                    .headers(Headers.of(headers))
                    .build();
        }else{
            request = new Request.Builder()
                    .url(URL)
                    .post(formBody)
                    .build();
        }

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                /*
                 * 调用日志工具类将异常和错误分别进行错误的存储
                 * 并投出运行时异常，让程序停止
                 */
                throw new RuntimeException();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                onHttpResultListener.onResult(response);
            }
        });
    }

    /**
     * 对于json格式的post请求暂且还没有想好，如果要使用okhttp的话，就必然要增加对于Gson的依赖，又要重新导入一个包
     * @param onHttpResultListener HttpBase监听器
     */


    @Override
    void doPut(OnHttpResultListener onHttpResultListener) {

    }

    @Override
    void doDelete(OnHttpResultListener onHttpResultListener) {

    }
}
