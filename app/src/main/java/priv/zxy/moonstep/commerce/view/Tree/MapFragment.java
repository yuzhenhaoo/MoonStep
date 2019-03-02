package priv.zxy.moonstep.commerce.view.Tree;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import priv.zxy.moonstep.DAO.PushLocationInfoDAO;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.algorithm.ChooseMapDots.MapDot;
import priv.zxy.moonstep.algorithm.MinimumDISTDectation.MinDistanceDetectionContext;
import priv.zxy.moonstep.algorithm.MinimumDISTDectation.MinDistanceTypeEnum;
import priv.zxy.moonstep.constant.SharedConstant;
import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.framework.good.bean.Good;
import priv.zxy.moonstep.framework.stroage.GoodTreasureInfo;
import priv.zxy.moonstep.framework.stroage.MapDotsInfo;
import priv.zxy.moonstep.gps.PackActivity;
import priv.zxy.moonstep.helper.FileHelper;
import priv.zxy.moonstep.util.LogUtil;
import priv.zxy.moonstep.util.SharedPreferencesUtil;
import priv.zxy.moonstep.wheel.animate.AbstractAnimateEffect;
import priv.zxy.moonstep.wheel.animate.ElasticityAnimation;

/**
 * 地图这里可以加上权限功能：
 *     我们利用百度地图的特性，可以显示出来不同类别的地图，把地图的类别和权限相互对应，更高等级的权限可以看到不同视野的地图
 *     不同的地图可以得到的奖励也有所不同。
 */
public class MapFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "MapFragment";
    private static final String TUYA = "tuya.data";
    private static final String NIGHT = "night.data";
    private static final String CUSTOM_MAP_DIR = "customConfigFile";
    /**
     * 设置探索的精度半径为10000m
     */
    private static final int RADIUS = 10000;

    private View view;
    private Button pack;
    private Button radar;

    private MapView map = null;
    /**
     * AMap 类是地图的控制器类，用来操作地图。
     *     它所承载的工作包括：地图图层切换（如卫星图、黑夜地图）、改变地图状态（地图旋转角度、俯仰角、中心点坐标和缩放级别）、
     *     添加点标记（Marker）、绘制几何图形(Polyline、Polygon、Circle)、各类事件监听(点击、手势等)等，AMap 是地图 SDK 最重要的核心类，诸多操作都依赖它完成。
     */
    private AMap aMap = null;
    /**
     * 设置定位属性
     */
    private MyLocationStyle myLocationStyle;
    /**
     * 声明AMapLocationClient类对象
     */
    private AMapLocationClient mLocationClient = null;
    /**
     * 声明AMapLocationClientOption对象
     */
    public AMapLocationClientOption mLocationOption = null;

    /**
     * 用户当前位置
     */
    private MapDot myDot = new MapDot();
    /**
     * 用户地图中的所有“宝藏”地点
     */
    private List<MapDot> allMapDots = null;
    private List<Marker> markers = new LinkedList<>();
    private ArrayList<Good> treasures;

    private AbstractAnimateEffect effect;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fg_main_second_subpage1, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(savedInstanceState);
        initEvent();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 在activity执行onResume时执行map.onResume(),重新绘制加载地图
        map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        // 在activity执行onPause时执行map.onPause()，暂停地图的绘制
        // 停止定位后，本地定位服务并不会被销毁
        mLocationClient.stopLocation();
        map.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 销毁定位客户端，同时销毁本地定位服务。
        mLocationClient.onDestroy();
        map.onDestroy();
        allMapDots = null;
        markers = null;
        treasures = null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // 在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        map.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0){
                    for(int result : grantResults){
                        if (result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(Application.getContext(), "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            this.getActivity().finish();
                            return;
                        }
                    }
                }else{
                    Toast.makeText(Application.getContext(), "发生位置错误", Toast.LENGTH_SHORT).show();
                    this.getActivity().finish();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 动态申请权限
     */
    private void applyForPermissions() {
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(Application.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(Application.getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(Application.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this.getActivity(), permissions, 1);
        }
    }

    private void initView(Bundle savedInstanceState) {
        // 动态的申请权限
        applyForPermissions();
        pack = view.findViewById(R.id.pack);
        radar = view.findViewById(R.id.radar);
        map = view.findViewById(R.id.map);
        // 在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        map.onCreate(savedInstanceState);

        initGoodTreasure();
        initAmapSetting();
        initMapDots();
        initMarkers();
        initLocationStyle();
        initLocationClient();
        initLocationOption();
    }

    /**
     * 初始化宝物信息
     */
    private void initGoodTreasure() {
        treasures = new ArrayList<>(GoodTreasureInfo.getInstance().getTreasures());
    }

    private void initAmapSetting(){
        if (aMap == null){
            aMap = map.getMap();
        }
        // 开启自定义地图
        aMap.setMapCustomEnable(true);
        // 设置自定义地图 tuya
        aMap.setCustomMapStylePath(FileHelper.setMapCustomFile(Application.getContext(), CUSTOM_MAP_DIR, TUYA));
//      aMap.setCustomMapStylePath(FileHelper.setMapCustomFile(Application.getContext(), "customConfigFile", night));//设置自定义地图night
    }

    private void initMapDots(){
        allMapDots = MapDotsInfo.getInstance().getDots();
    }

    private void initEvent(){
        pack.setOnClickListener(this);
        radar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(pack)) {
            ElasticityAnimation.getInstance(pack).show();
            toPackActivity();
        }

        if (v.equals(radar)) {
            // TODO : (标记人:张晓翼，标记时间，2018/12/27)
            /*
             * 一件事就是找到精度半径范围内的寻宝地点
             * 第二件事就是把探索过的地点去除。
             */
            new Thread(() -> {
                List<MapDot> results = new MinDistanceDetectionContext(myDot, allMapDots, RADIUS, MinDistanceTypeEnum.MIN_DIST_TYPE).listResult();
                for (MapDot result : results) {
                    // TODO : (标记人:张晓翼，标记时间，2018/12/27)
                    LogUtil.d(TAG, "result:" + result.getLatitude() + result.getLongitude());
                }
                Log.d(TAG, String.valueOf(allMapDots.size()));
                // 删除所有的范围内的标记点
                deleteGaugePointInMapDots(results);
                // 重新展示界面标记点
                updateDotsInMap();
            }).start();
        }
    }

    /**
     * 从所有点中删除经度范围内的标记点
     * @param gaugePoints 待删除点
     */
    private void deleteGaugePointInMapDots(List<MapDot> gaugePoints){
        Iterator<MapDot> it = allMapDots.iterator();
        while (it.hasNext()){
            MapDot item = it.next();
            if (gaugePoints.contains(item)){
                LogUtil.d(TAG, "eleteGaugePointInMapDots-->" + item.getLongitude() + item.getLongitude());
                it.remove();
                // 从数据库中删除标记点
                item.delete();
            }
        }
    }

    /**
     * 跳转到背包页面
     */
    private void toPackActivity(){
        if (this.getContext() != null){
            Intent intent = new Intent(this.getContext(), PackActivity.class);
            this.getContext().startActivity(intent);
        }
    }

    /**
     * 设置地图风格
     */
    private void initLocationStyle(){
        // 初始化定位蓝点样式类
        myLocationStyle = new MyLocationStyle();
        // 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//       myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        // 设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.interval(3000);
        // 设置显示定位蓝点
        myLocationStyle.showMyLocation(true);
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.location_icon));
        // 设置锚点
        // myLocationStyle.anchor(0.5f, 1.0f);
        // 设置定位精度圆圈的边框颜色
        myLocationStyle.strokeColor(Color.parseColor("#E8DC143C"));
        // 设置定位精度圆圈的填充颜色
        myLocationStyle.radiusFillColor(Color.parseColor("#64FFC800"));
        // 设置定位圆点边框宽度的方法
        myLocationStyle.strokeWidth(1);
        // 设置定位蓝点的Style
        aMap.setMyLocationStyle(myLocationStyle);
        // 设置默认定位按钮是否显示，非必需设置
        // aMap.getUiSettings().setMyLocationButtonEnabled(true);
        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setMyLocationEnabled(true);
    }

    /**
     * 地图客户端设置
     */
    private void initLocationClient(){
        mLocationClient = new AMapLocationClient(Application.getContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
    }

    /**
     * 定位属性
     */
    private void initLocationOption(){
        // 初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        // 设置定位间隔，单位毫秒
        mLocationOption.setInterval(3000);
        // 给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        // 启动定位
        mLocationClient.startLocation();
    }

    /**
     * 控制标记点的刷新
     * 当点击了检测后，如果检测成功，那么标记点刷新
     */
    private void updateDotsInMap(){
        markers.clear();
        initMarkers();
        // 刷新地图
        map.invalidate();
    }

    private void initMarkers(){
        if (allMapDots != null){
            for(MapDot dot : allMapDots){
                LatLng latLng = new LatLng(dot.getLatitude(), dot.getLongitude());
                MarkerOptions options = new MarkerOptions();
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.gauge_point);
                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
                options.title("gold").position(latLng).icon(bitmapDescriptor);
                Marker marker = aMap.addMarker(options);
                // 标记Marker的类型
//                marker.setObject(new MapDot());
                markers.add(marker);
            }
        }
    }

    private AMapLocationListener mLocationListener = location -> {
        if (location != null){
            String address = location.getAddress();//获得地址
            double latitude = location.getLatitude();//获取维度
            double longitude = location.getLongitude();//获取经度
            float accuracy = location.getAccuracy();//获取精度
            String floor = location.getFloor();//获取当前室内定位的楼层
            int status = location.getGpsAccuracyStatus();//获取GPS的当前状态
            // 获取定位时间
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            myDot.setLatitude(latitude);
            myDot.setLongitude(longitude);

            /*
             * 这里出现了一个错误，是由于dots本身不能将context中获得的结果引用过来导致的，但是context.getMapDots的确是获得了结果，只是对于dots而言结果却是not avaliable的
             * 所以只要解决dots=context.getMapDots的问题就好了
             */

            LogUtil.d(TAG, "当前地址为:" + address);
            LogUtil.d(TAG, "当前维度为:" + latitude);
            LogUtil.d(TAG, "当前经度为:" + longitude);

            // 初始化地图坐标
            if (MapDotsInfo.getInstance().initMapDots(latitude, longitude)) {
                allMapDots = MapDotsInfo.getInstance().getDots();
            }

            String phoneNumber = SharedPreferencesUtil.readLoginInfo().get(SharedConstant.PHONE_NUMBER);
            PushLocationInfoDAO.getInstance().LocationServlet(phoneNumber, address, String.valueOf(latitude), String.valueOf(longitude));

        }else{
            /*
             * 同时使用日志处理工具，对错误和异常进行记录
             */
            LogUtil.e("AmapError","location Error, ErrCode:"
                    + location.getErrorCode() + ", errInfo:"
                    + location.getErrorInfo());
        }
    };
}
