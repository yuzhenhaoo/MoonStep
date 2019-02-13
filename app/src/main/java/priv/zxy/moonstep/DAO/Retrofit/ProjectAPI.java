package priv.zxy.moonstep.DAO.Retrofit;

import priv.zxy.moonstep.framework.race.RaceData;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * 创建人: LYJ
 * 创建时间: 2019/2/7
 * 描述:Retrofit请求网络接口
 **/
public interface ProjectAPI {

    @FormUrlEncoded
    @POST
    Call<RaceData> postRaceDataMethod(@Url String url, @Field("RaceCode") String raceCode);
}
