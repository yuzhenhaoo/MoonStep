package priv.zxy.moonstep.gps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;

import java.util.ArrayList;
import java.util.List;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.helper.FileHelper;
import priv.zxy.moonstep.kernel.Application;
import priv.zxy.moonstep.kernel.bean.ErrorCode;
import priv.zxy.moonstep.utils.SharedPreferencesUtil;
import priv.zxy.moonstep.utils.dbUtils.DownloadFileUtil;
import priv.zxy.moonstep.utils.dbUtils.SetLocationUtil;

/**
 * 地图这里可以加上权限功能：
 * 我们利用百度地图的特性，可以显示出来不同类别的地图，把地图的类别和权限相互对应，更高等级的权限可以看到不同视野的地图
 * 不同的地图可以得到的奖励也有所不同。
 */
public class SecondMainPageFragment1 extends Fragment {

    private static final String TAG = "SecondMainPageFragment1";

    private View view;

    private MapView mapView = null;

    private BaiduMap mBaiduMap;
    private Button bt1;
    private Button bt2;
    private Button bt3;
    private Button bt4;
    private Button bt5;
    private Button bt6;
    private Button bt7;
    private Button loadCustomMap;

    private LocationClient mLocationClient;

    private MyLocationListener myListener = new MyLocationListener();

    private TextView positiontextView;

    private static String PATH = "daiyu_map.json";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        FileHelper.setMapCustomFile(Application.getContext(), PATH);
        mLocationClient = new LocationClient(Application.getContext());//初始化位置信息
        mLocationClient.registerLocationListener(myListener);//注册监听函数
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fg_main_second_subpage1, null);
        //设置开启个性化地图
        MapView.setMapCustomEnable(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        /*
         * 动态的申请权限
         */
        applyForPermissions();
        //获取地图控件的引用
        mapView = view.findViewById(R.id.bmapView);
        bt1 = (Button) view.findViewById(R.id.bt1);
        bt2 = (Button) view.findViewById(R.id.bt2);
        bt3 = (Button) view.findViewById(R.id.bt3);
        bt4 = (Button) view.findViewById(R.id.bt4);
        bt5 = (Button) view.findViewById(R.id.bt5);
        bt6 = (Button) view.findViewById(R.id.bt6);
        bt7 = (Button) view.findViewById(R.id.bt7);
        loadCustomMap = (Button) view.findViewById(R.id.loadCustomMap);

        positiontextView = view.findViewById(R.id.positionTextView);

        mBaiduMap = mapView.getMap();
        //普通地图 ,mBaiduMap是地图控制器对象

        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        bt1.setOnClickListener(v -> mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL));

        bt2.setOnClickListener(v -> mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE));

        bt3.setOnClickListener(v -> mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE));

        bt4.setOnClickListener(v -> mBaiduMap.setTrafficEnabled(true));

        bt5.setOnClickListener(v -> mBaiduMap.setBaiduHeatMapEnabled(true));

        bt6.setOnClickListener(v-> mBaiduMap.setIndoorEnable(true));

        bt7.setOnClickListener(v ->{
        });

        loadCustomMap.setOnClickListener(v-> {
            DownloadFileUtil.getInstance().downloadFileFromHttp(new DownloadFileUtil.OnResultListener() {
                @Override
                public void onSuccess(String responseData) {
//                    FileHelper.getInstance().save("daiyu_map.json", responseData);
                }

                @Override
                public void onFail(ErrorCode errorCode) {

                }
            },"http://120.79.154.236:8080/file/daiyu_map.json");
        });
        initLocationData();
    }

    private void initLocationData(){
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
        //可选，设置返回经纬度坐标类型，默认GCJ02
        //GCJ02：国测局坐标；
        //BD09ll：百度经纬度坐标；
        //BD09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标

        option.setScanSpan(5000);//每隔5s请求一次数据
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
        //可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5*60*1000);
        //可选，V7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        option.setIsNeedAddress(true);
        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true

        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        mLocationClient.start();//发起定位请求
    }

    private void initLocationFunction(BDLocation location) {

        //开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(30)//设置精度范围为30m
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();

        // 设置定位数据
        mBaiduMap.setMyLocationData(locData);

        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.FOLLOWING, true, BitmapDescriptorFactory.fromResource(R.drawable.location_icon),
                R.color.Gold, R.color.Red));

        // 当不需要定位图层时关闭定位图层
//        mBaiduMap.setMyLocationEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
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
        } else {
            requestLocation();
        }
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation(){
        LocationClientOption locationClientOption = new LocationClientOption();
        locationClientOption.setScanSpan(5000);//设置更新的间隔，表示每隔5s更新一下当前的位置
        mLocationClient.setLocOption(locationClientOption);
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
                    requestLocation();
                }else{
                    Toast.makeText(Application.getContext(), "发生位置错误", Toast.LENGTH_SHORT).show();
                    this.getActivity().finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mLocationClient.stop();//在Fragment解绑的时候必须要停止定位，不然程序会在后台不断定位，从而严重消耗手机电量
        mLocationClient.stopIndoorMode();//关闭室内定位模式
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            Log.d(TAG, "onReceiveLocation: " + "运行到了这里");
            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f
            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            SetLocationUtil.getInstance().LoginRequest(new SetLocationUtil.Callback(){
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFail(ErrorCode errorCode) {

                }
            }, SharedPreferencesUtil.getInstance(Application.getContext()).readMySelfInformation().get(""), addr, latitude, longitude);

            Log.d(TAG, "经纬度信息" + latitude + " " + longitude);
            Log.d(TAG, "详细地址信息" + addr);
            Log.d(TAG, "街道" + country + province + city + district + street);
            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            int errorCode = location.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            if (location.getFloor() != null) {
                // 当前支持高精度室内定位
                String buildingID = location.getBuildingID();// 百度内部建筑物ID
                String buildingName = location.getBuildingName();// 百度内部建筑物缩写
                String floor = location.getFloor();// 室内定位的楼层信息，如 f1,f2,b1,b2
                mLocationClient.startIndoorMode();// 开启室内定位模式（重复调用也没问题），开启后，定位SDK会融合各种定位信息（GPS,WI-FI，蓝牙，传感器等）连续平滑的输出定位结果；
            }

            initLocationFunction(location);
        }
    }
}
