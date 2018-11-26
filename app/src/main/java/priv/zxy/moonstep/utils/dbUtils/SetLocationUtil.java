package priv.zxy.moonstep.utils.dbUtils;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.hyphenate.exceptions.HyphenateException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import priv.zxy.moonstep.helper.EMHelper;
import priv.zxy.moonstep.kernel.Application;
import priv.zxy.moonstep.kernel.bean.ErrorCode;
import priv.zxy.moonstep.kernel.bean.ServiceBase;
import priv.zxy.moonstep.utils.LogUtil;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/25
 * 描述:
 **/
public class SetLocationUtil {

    private static final String TAG = "SetLocationUtil";
    private static SetLocationUtil instance;

    public static SetLocationUtil getInstance() {
        if (instance == null){
            synchronized (SetLocationUtil.class){
                if (instance == null){
                    instance = new SetLocationUtil();
                }
            }
        }
        return instance;
    }

    /**
     * response的回应一直为""，到底是怎么回事？
     * @param phoneNumber
     * @param address
     * @param latitude
     * @param longtutide
     */
//    public void LocationServlet(String phoneNumber,String address,String latitude,String longtutide){
//        //请求地址
//        String url = ServiceBase.LOCATION_SERVLET_URL;
//        String tag = "location";
//        //取得请求队列
//        RequestQueue requestQueue = Volley.newRequestQueue(Application.getContext());
//
//        //防止重复请求，所以先取消tag标识的请求队列
//        requestQueue.cancelAll(tag);
//
//        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
//        final StringRequest request = new StringRequest(Request.Method.POST, url,
//                response -> {
//                    try {
//                        LogUtil.d(TAG, "response");
//                        JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");
//                        String result = jsonObject.getString("result");
//                        if (result.equals("success")){
//                            LogUtil.d(TAG, "success");
//                        }else if (result.equals("error")){
//                            LogUtil.d(TAG, "error");
//                        }
//                    } catch (JSONException e) {
//                        //做自己的请求异常操作
//                        LogUtil.d(TAG, "JSONException");
//                    }
//                }, error -> {LogUtil.d(TAG, "error2");}) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("phoneNumber", phoneNumber);
//                params.put("address",address);
//                params.put("latitude", latitude);
//                params.put("latitude",longtutide);
//                return params;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = new HashMap<String, String>();
//                headers.put("Charset", "ISO-8859-1");
//                return headers;
//            }
//        };
//
//        //设置Tag标签
//        request.setTag(tag);
//
//        //将请求添加到队列中
//        requestQueue.add(request);
//    }

    public void LocationServlet(String phoneNumber,String address,String latitude,String longtitude){{
        //请求地址
        String url = ServiceBase.LOCATION_SERVLET_URL;
        LogUtil.d(TAG, phoneNumber + "  " + address + "  " + latitude + "  " + longtitude);
        AndroidNetworking.post(url)
                .addBodyParameter("phoneNumber", phoneNumber)
                .addBodyParameter("address", address)
                .addBodyParameter("latitude", latitude)
                .addBodyParameter("longtitude", longtitude)
                .setTag("location")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        LogUtil.d(TAG, response.toString());
                    }

                    @Override
                    public void onError(ANError anError) {
                        LogUtil.d(TAG, anError.toString());
                    }
                });
    }}
}
