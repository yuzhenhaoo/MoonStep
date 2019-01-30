package priv.zxy.moonstep.DAO;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import priv.zxy.moonstep.DAO.constant.DaoConstant;
import priv.zxy.moonstep.DAO.constant.UrlBase;
import priv.zxy.moonstep.data.bean.ErrorCodeEnum;
import priv.zxy.moonstep.framework.good.bean.Good;

/**
 * http请求类，用来从服务器获得宝物的信息
 */
public class PullGoodTreasureInfoDAO {
    private static PullGoodTreasureInfoDAO instance = null;

    private static final String GOOD_TREASURE = "treasure";

    private static final String URL = UrlBase.GOOD_TREASURE;

    public static PullGoodTreasureInfoDAO getInstance() {
        if (instance == null) {
            instance = new PullGoodTreasureInfoDAO();
        }
        return instance;
    }

    /**
     * 从服务器端获取宝物信息
     * @param callback 回调方法
     * @param phoneNumber 电话号码
     * @param number 宝物数量
     */
    public void getTreasures(Callback callback, String phoneNumber, int number) {
        AndroidNetworking.post(URL)
                .addBodyParameter(DaoConstant.PHONE_NUMBER, phoneNumber)
                .addBodyParameter(DaoConstant.GOOD_NUMBER, String.valueOf(number))
                .setTag(GOOD_TREASURE)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        callback.onSuccess(Good.createItemsformJson(response));
                    }

                    @Override
                    public void onError(ANError anError) {
                        callback.onFail(ErrorCodeEnum.JSON_EXCEPTION);
                    }
                });
    }

    public interface Callback {
        void onSuccess(List<Good> goods);
        void onFail(ErrorCodeEnum code);
    }
}
