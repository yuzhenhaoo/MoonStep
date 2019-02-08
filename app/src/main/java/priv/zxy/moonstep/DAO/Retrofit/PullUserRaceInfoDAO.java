package priv.zxy.moonstep.DAO.Retrofit;

import android.support.annotation.NonNull;
import priv.zxy.moonstep.DAO.constant.UrlBase;
import priv.zxy.moonstep.framework.race.RaceData;
import priv.zxy.moonstep.util.LogUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 创建人: LYJ
 * 创建时间: 2019/2/7
 * 描述:向服务器请求用户种族信息
 **/
public class PullUserRaceInfoDAO {

    private static PullUserRaceInfoDAO instance = null;

    private static final String RACE_TAG = "race";

    private static final String BaseURL = UrlBase.GET_PROJECT_SERVLET_URL;

    private static final String URL = UrlBase.GET_RACE_INFO_SERVLET_URL;

    public static PullUserRaceInfoDAO getInstance() {
        if (instance == null) {
            instance = new PullUserRaceInfoDAO();
        }
        return instance;
    }

    public void GetRaceInfo(Callback<RaceData> callback, String raceCode) {

        // 创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // 创建 网络请求接口 的实例
        RaceInfoAPI raceInfoAPI = retrofit.create(RaceInfoAPI.class);

        // 对 发送请求 进行封装(设置请求的种族ID)
        Call<RaceData> call = raceInfoAPI.postMethod(URL, raceCode);

        // 发送网络请求(异步)
        call.enqueue(new Callback<RaceData>() {
            @Override
            public void onResponse(@NonNull Call<RaceData> call, @NonNull Response<RaceData> response) {
                callback.onResponse(call, response);
                LogUtil.d(RACE_TAG, "种族信息请求成功");
            }
            @Override
            public void onFailure(@NonNull Call<RaceData> call, @NonNull Throwable t) {
                callback.onFailure(call, t);
                LogUtil.d(RACE_TAG, "种族信息请求失败");
            }
        });
    }

}
