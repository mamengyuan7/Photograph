package net.onest.photographget;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.onest.photographget.entity.Picture;
import net.onest.photographget.entity.PictureDetail;
import net.onest.photographget.entity.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EXIFActivity extends AppCompatActivity {
    private ImageView pImage;
    private TextView brand;
    private TextView type;
    private TextView ptype;
    private TextView camera_lens;
    private TextView focal_length;
    private TextView iso;
    private TextView time;
    private TextView latitude;
    private TextView longitude;
    private TextView aaa;
    private Picture pic;
    private MapView mapView;
    private BaiduMap baiduMap;
    private UiSettings uiSettings;
    private LocationClient locationClient;
    private LocationClientOption locationClientOption;
    private LatLng point;
    private int pos;
    private int pId;
    private PictureDetail pictureDetail;
    protected static final int ERROR = 2;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == 66){
                super.handleMessage(msg);
                String info = (String)msg.obj;
                Log.e("zz",info);
                Type type=new TypeToken<PictureDetail>(){}.getType();
                Gson gson=new Gson();
                pictureDetail = gson.fromJson(info,type);
                Log.e("pika",pictureDetail.getAddress());
                //信息的展示
                init();
                initializeMap();
                hideLogo();
                zoomLevelOp();
                showLocOnMap(pictureDetail.getLatitude(),pictureDetail.getLongitude());
                String a = getAddress(pictureDetail.getLatitude(),pictureDetail.getLongitude());
                aaa.setText(a);
            }else if(msg.what == 62){
                Bitmap bitmap = (Bitmap) msg.obj;
                pImage.setImageBitmap(bitmap);
            }else if (msg.what == ERROR) {
                /*Toast.makeText(MapFindAcitvity.this, "显示图片错误",
                        Toast.LENGTH_SHORT).show();*/
                Log.e("报错了！","llalalalala");
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.exit_activity);
        initView();
        //接受传递过来的图片信息（确定是第几张图片）
        Intent intent = getIntent();
        pos = intent.getIntExtra("pos",0);
        Log.e("aaaaaaaaaaa",pos+"");
        pId = intent.getIntExtra("pId",0);
        Log.e("aaaaaaaaaaa",pId+"");
        getEXIFinformation();
        /*pic = new Picture(1,"E://image//万达广场.jpg",
                1,"vivo","3炫彩","无",
                "wu","4.7mm","320","2021:02:19 19:01:52",
                38.02618789666666,114.53675079333333);
        pImage.setImageResource(R.mipmap.wanda);
        brand.setText("品牌："+pic.getBrand());
        type.setText("型号："+pic.getType());
        ptype.setText("分辨率单位："+pic.getPtype());
        camera_lens.setText("镜头："+pic.getCamera_lens());
        focal_length.setText("焦距："+pic.getFocal_length());
        iso.setText("ISO："+pic.getIso());
        time.setText("拍摄时间："+pic.getTime());
        latitude.setText("经度："+pic.getLatitude());
        longitude.setText("纬度："+pic.getLongitude());*/
        /*showLocOnMap(38.026187152514,114.536750123456);
        String a = getAddress(38.026187,114.536750);
        aaa.setText(a);*/
    }
    private void init(){
        /*Bitmap bitmap = getBitmip(pictureDetail.getAddress());
        pImage.setImageBitmap(bitmap);*/
//        pImage.setImageResource(R.mipmap.wanda);
        getPicBitmap(pictureDetail.getAddress());
        brand.setText("品牌："+pictureDetail.getBrand());
        type.setText("型号："+pictureDetail.getType());
        ptype.setText("分辨率单位："+pictureDetail.getPtype());
        camera_lens.setText("镜头："+pictureDetail.getCarmeraLen());
        focal_length.setText("焦距："+pictureDetail.getFocalLength());
        iso.setText("ISO："+pictureDetail.getIso());
        time.setText("拍摄时间："+pictureDetail.getTime());
        latitude.setText("经度："+pictureDetail.getLatitude());
        longitude.setText("纬度："+pictureDetail.getLongitude());
    }
    public static Bitmap getBitmip(String path){
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if(conn.getResponseCode() == 200){
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //读取网络图片URL
    public void getPicBitmap(String path){
        new Thread() {
            private HttpURLConnection conn;
            private Bitmap bitmap;
            public void run() {
                // 连接服务器 get 请求 获取图片
                try {
                    //创建URL对象
                    Log.e("ppppp",path);
                    URL url = new URL(path);
                    // 根据url 发送 http的请求
                    conn = (HttpURLConnection) url.openConnection();
                    // 设置请求的方式
                    conn.setRequestMethod("GET");
                    //设置超时时间
                    conn.setConnectTimeout(5000);
                    // 得到服务器返回的响应码
                    int code = conn.getResponseCode();
                    //请求网络成功后返回码是200
                    if (code == 200) {
                        //获取输入流
                        InputStream is = conn.getInputStream();
                        //将流转换成Bitmap对象
                        bitmap = BitmapFactory.decodeStream(is);
                        //将更改主界面的消息发送给主线程
                        Message msg = new Message();
                        msg.what = 62;
                        msg.obj = bitmap;
                        handler.sendMessage(msg);
                    } else {
                        //返回码不等于200 请求服务器失败
                        Message msg = new Message();
                        msg.what = ERROR;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = ERROR;
                    handler.sendMessage(msg);
                }
                //关闭连接
                conn.disconnect();
            }
        }.start();
    }
    private void getEXIFinformation(){
        new Thread(){
            @Override
            public void run() {
                String picId = pId+"";
                String flag= String.valueOf(pos);
                String f = "1";
                try {
                    Log.e("数据是：","picId="+picId+",flag="+flag);
                    URL url = new URL("http://192.168.43.169:8080/PhotographGet/pictureDetail/list?picId="+picId+"&flag="+flag);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.e("pikaqiu","传过来了呢！");
                    Log.e("xx2",info);
                    wrapperMessage(info);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private void wrapperMessage(String info) {
        Message msg = Message.obtain();
        msg.obj = info;
        msg.what = 66;
        handler.sendMessage(msg);
    }


    private void showLocOnMap(double lat, double lng) {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.location);
        setMarkPoint(lat,lng);
        MyLocationConfiguration config = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.COMPASS,
                false,
                icon);
        baiduMap.setMyLocationConfiguration(config);
        MyLocationData locData = new MyLocationData
                .Builder()
                .latitude(lat)
                .longitude(lng)
                .build();
        baiduMap.setMyLocationData(locData);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(new LatLng(lat,lng));
        baiduMap.animateMapStatus(msu);
    }
    private void setMarkPoint(double lat,double lng) {
        //定义Maker坐标点
        baiduMap.clear();
        point = new LatLng(lat, lng);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.location);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        baiduMap.addOverlay(option);
    }

    private void initializeMap() {
        baiduMap = mapView.getMap();
        uiSettings = baiduMap.getUiSettings();
    }

    private void hideLogo(){
        mapView.removeViewAt(1);
    }
    private void zoomLevelOp() {
        baiduMap.setMaxAndMinZoomLevel(19, 13);
        MapStatusUpdate msu = MapStatusUpdateFactory
                .zoomTo(16);
        baiduMap.setMapStatus(msu);
    }

    public String getAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder( this, Locale.getDefault());
        try {
            List<android.location.Address> addresses = geocoder.getFromLocation(latitude,
                    longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get( 0);
                String data = address.toString();
                int startCity = data.indexOf( "1:\"") + "1:\"".length();
                int endCity = data.indexOf( "\"", startCity);
                String city = data.substring(startCity, endCity);
                int startPlace = data.indexOf( "feature=") + "feature=".length();
                int endplace = data.indexOf( ",", startPlace);
                String place = data.substring(startPlace, endplace);
                String cou = address.getCountryName()+address.getAdminArea()+address.getLocality()+address.getSubLocality();
                return "地址："+cou+place;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "获取失败";
    }

    private void initView() {
        pImage = (ImageView)findViewById(R.id.exif_img);
        brand = (TextView)findViewById(R.id.exif_brand);
        type = (TextView) findViewById(R.id.exif_type);
        ptype = (TextView)findViewById(R.id.exif_ptype);
        camera_lens = (TextView) findViewById(R.id.exif_camera_lens);
        focal_length = (TextView) findViewById(R.id.exif_focal_length);
        iso = (TextView) findViewById(R.id.exif_iso);
        time = (TextView)findViewById(R.id.exif_time);
        latitude = (TextView) findViewById(R.id.exif_latitude);
        longitude = (TextView) findViewById(R.id.exif_longitude);
        aaa = (TextView)findViewById(R.id.aaa);
        mapView = findViewById(R.id.mapView);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
}
