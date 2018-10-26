package priv.zxy.moonstep.kernel.bean;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import priv.zxy.moonstep.kernel.Application;

public class EMBase {

    private static final String TAG = "EMBase";

    private String OrgName = null ;

    private String AppName = null;

    private String Client_ID = null;

    private String Client_Secret = null;

    private static final String baseRequest = "http://a1.easemob.com/";

    private static EMBase instance = null;

    private LocalBroadcastManager localBroadcastManager;

    /**
     * 设计模式：双重锁定(Double-Check Locking)
     * 只有在第一次初始化的时候需要判断instance是不是已经初始化了，如果还没有初始化过，进入同步锁的锁定模式，在同步锁下完成实例的实例化。
     * @return instance
     */
    public static EMBase getInstance() {
        if (instance == null) {
            synchronized (EMBase.class) {
                if (instance == null) {
                    instance = new EMBase();
                }
            }
        }
        return instance;
    }

    public String getOrgName() {
        if (OrgName != null){
            return OrgName;
        }
        Log.d(TAG, "orgName初始化失败", new RuntimeException());
        return null;
    }

    public String getAppName() {
        if (AppName != null){
            return AppName;
        }
        Log.d(TAG, "appName初始化失败", new RuntimeException());
        return null;
    }

    public String getClient_ID() {
        if (Client_ID != null){
            return Client_ID;
        }
        Log.d(TAG, "client_id初始化失败", new RuntimeException());
        return null;
    }

    public String getClient_Secret() {
        if (Client_Secret != null){
            return Client_Secret;
        }
        Log.d(TAG, "client_secret初始化失败", new RuntimeException());
        return null;
    }

    public String getBaseRequest() {
        return baseRequest;
    }


    public void initDataFromDatabase(){
        String url = "http://120.79.154.236:8080/MoonStep/EMServlet";
        String tag = "orgName";

        localBroadcastManager = LocalBroadcastManager.getInstance(Application.mContext);
        final Intent intent = new Intent("priv.zxy.moonstep.kernel.bean.LOCAL_BROADCAST");


        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(Application.mContext);

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        StringRequest request = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response).getJSONObject("result");
                            OrgName = jsonObject.get("orgname").toString();
                            AppName = jsonObject.get("appname").toString();
                            Client_ID = jsonObject.get("client_id").toString();
                            Client_Secret = jsonObject.get("client_secret").toString();
                            localBroadcastManager.sendBroadcastSync(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        request.setTag(tag);

        requestQueue.add(request);
    }

}
