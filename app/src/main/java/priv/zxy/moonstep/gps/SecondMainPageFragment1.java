package priv.zxy.moonstep.gps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import priv.zxy.moonstep.kernel.Application;
import priv.zxy.moonstep.utils.LogUtil;

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

    private LocationClient mLocationClient;

    private TextView positiontextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //初始化位置信息
        mLocationClient = new LocationClient(Application.getContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        view = inflater.inflate(R.layout.fg_main_second_subpage1, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LocationinitPermissions();
        initView();
    }

    private void initView() {
        //获取地图控件的引用
        mapView = view.findViewById(R.id.bmapView);
        bt1 = (Button) view.findViewById(R.id.bt1);
        bt2 = (Button) view.findViewById(R.id.bt2);
        bt3 = (Button) view.findViewById(R.id.bt3);
        bt4 = (Button) view.findViewById(R.id.bt4);
        bt5 = (Button) view.findViewById(R.id.bt5);

        positiontextView = view.findViewById(R.id.positionTextView);

        mBaiduMap = mapView.getMap();
        //普通地图 ,mBaiduMap是地图控制器对象
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        bt1.setOnClickListener(v -> mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL));

        bt2.setOnClickListener(v -> mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE));

        bt3.setOnClickListener(v -> mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE));

        bt4.setOnClickListener(v -> mBaiduMap.setTrafficEnabled(true));

        bt5.setOnClickListener(v -> mBaiduMap.setBaiduHeatMapEnabled(true));

    }

    private void initLocationFunction(BDLocation location) {
        //构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();

        // 设置定位数据
        mBaiduMap.setMyLocationData(locData);

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

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
    private void LocationinitPermissions() {
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
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            initLocationFunction(location);//给地图设置定位数据
            StringBuilder currentPosition = new StringBuilder();
            currentPosition.append("纬度:").append(location.getLatitude()).append("\n");
            currentPosition.append("经线:").append(location.getLongitude()).append("\n");
            currentPosition.append("定位方式:");
            if (location.getLocType() == BDLocation.TypeGpsLocation){
                currentPosition.append("GPS");
            }else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
                currentPosition.append("网络");
            }
            LogUtil.e(TAG, currentPosition.toString());
            positiontextView.setText(currentPosition);
        }
    }
}
