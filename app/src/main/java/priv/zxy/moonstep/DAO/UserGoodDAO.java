package priv.zxy.moonstep.DAO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import priv.zxy.moonstep.constant.URLBase;
import priv.zxy.moonstep.framework.good.bean.Good;
import priv.zxy.moonstep.util.LogUtil;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/8
 * 描述: 获得用户物品的工具类
 **/

public class UserGoodDAO {

    private static final String TAG = "UserGoodDAO";

    private static final String GOOD_TAG = "good";

    private static UserGoodDAO instance;

    public static UserGoodDAO getInstance() {
        if (instance == null){
            synchronized (UserGoodDAO.class){
                if (instance == null){
                    instance = new UserGoodDAO();
                }
            }
        }
        return instance;
    }

    public void getUserGood(CallBack callBack, String phoneNumber){
        //请求地址
        String url = URLBase.USER_GOOD_URL;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("phoneNumber", phoneNumber)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .tag(GOOD_TAG)
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e(TAG, e.toString());
                /**
                 * 这里调用错误处理的函数进行日志的记录
                 */
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                assert response.body() != null;
                /**
                 * goods的解析可能会出问题，因为Good类型和Json传来的类型是不一致的。
                 */
//                LogUtil.d(TAG, response.body().string());
                List<Good> goods = gson.fromJson(response.body().string(), new TypeToken<List<Good>>(){}.getType());
                callBack.onSuccess(goods);
            }
        });
    }

    /**
     * 上传用户得到的物品数据
     * @param phoneNumber 电话
     * @param goodCode 物品编码
     * @param number 物品数目
     */
    public void setUserGood(String phoneNumber, String goodCode, int number){

    }

    public interface CallBack{
        void onSuccess(List<Good> goods);
    }
}
