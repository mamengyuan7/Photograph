package net.onest.photographget;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.google.gson.Gson;

import net.onest.photographget.OSS.MyLog;
import net.onest.photographget.OSS.UploadHelper;
import net.onest.photographget.adapter.ImageAdapter;
import net.onest.photographget.entity.Picture;
import net.onest.photographget.entity.PictureDetail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static net.onest.photographget.MainActivity.urlAdress;

public class uploadPic extends AppCompatActivity {
    private static final int REQUEST_CODE = 0x00000011;
    private static final int PERMISSION_WRITE_EXTERNAL_REQUEST_CODE = 0x00000012;

    private RecyclerView rvImage;
    private ImageAdapter mAdapter;
    private ImageView btn;
    private ImageView back;
    private TextView putin;
    private EditText name;
    private EditText introduce;
    private Picture picture;
    private ArrayList<String> images = new ArrayList<>();
    private int n;
    private PictureDetail pictureDetail = new PictureDetail();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 66){
                super.handleMessage(msg);
                String info = (String)msg.obj;
                if(info.equals("添加成功！")){
                    Log.e("456","添加成功！");
                    Toast.makeText(uploadPic.this,"上传图片成功！",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(uploadPic.this,MainActivity.class);
                    startActivity(intent);
                }
            }else if(msg.what == 62){
                super.handleMessage(msg);
                String info = (String)msg.obj;
                if (info.equals("添加成功！")){
                    Log.e("123","添加成功！");
                    Toast.makeText(uploadPic.this,"EXIF信息添加成功！",Toast.LENGTH_LONG).show();
                }
            }else if(msg.what == 26){
                super.handleMessage(msg);
                String info = (String)msg.obj;
                n = Integer.parseInt(info);
            }

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_pic);
        ButterKnife.bind(this);

        back = findViewById(R.id.back_main);
        putin = findViewById(R.id.put);
        name = findViewById(R.id.p_name);
        introduce = findViewById(R.id.p_jianjie);
        findnum();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rvImage = findViewById(R.id.rv_image);
        rvImage.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new ImageAdapter(this);
        rvImage.setAdapter(mAdapter);

        btn = (ImageView) findViewById(R.id.btn_unlimited);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .canPreview(true) //是否点击放大图片查看,，默认为true
                        .setMaxSelectCount(0) // 图片的最大选择数量，小于等于0时，不限数量。
                        .start(uploadPic.this, REQUEST_CODE); // 打开相册
            }
        });

        int hasWriteExternalPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteExternalPermission == PackageManager.PERMISSION_GRANTED) {
            //预加载手机图片。加载图片前，请确保app有读取储存卡权限
            ImageSelector.preload(this);
        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL_REQUEST_CODE);
        }
       /* putin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picture.setImgAddress("https://file06.16sucai.com/2016/0613/8b7ab7ea218d6fbea16d75eda49bd9ca.jpg");
                picture.setIntroduce(introduce.getText().toString());
                picture.setTitle(name.getText().toString());
                SharedPreferences p=getSharedPreferences("user",MODE_PRIVATE);
                int id = p.getInt("user_id",0);
                picture.setUserId(id);
                savePic(picture);
            }
        });*/

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            Log.e("88888",images.get(0));
            boolean isCameraImage = data.getBooleanExtra(ImageSelector.IS_CAMERA_IMAGE, false);
//            Log.d("ImageSelector", "是否是拍照图片：" + isCameraImage);
            mAdapter.refresh(images);
        }
    }
    /**
     * 处理权限申请的回调。
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_WRITE_EXTERNAL_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //预加载手机图片
                ImageSelector.preload(this);
            } else {
                //拒绝权限。
            }
        }
    }
    @OnClick(R.id.put)
    public void uploadTest() {
        UploadHelper uploadHelper = new UploadHelper();
        String path = "";
        if(images.size()==1){
            String uploaduel = uploadHelper.uploadImage(images.get(0));
            path = uploaduel;
        }else {
            for (int i = 0;i<images.size();i++){
                String uploaduel = uploadHelper.uploadImage(images.get(i));
                //这个方法会返回OSS上图片的路径
                MyLog.e("xxxx----testurl:" + uploaduel);
                if(path != ""){
                    path =path+"--"+uploaduel;
                }else {
                    path = uploaduel;
                }
            }
        }
        Log.e("xxx",path);
        String[] pa = path.split("--");
        for (int i = 0;i<images.size();i++){
            pictureDetail.setFlag(i);
            pictureDetail.setAddress(pa[i]);
            getEXIF(pictureDetail,images.get(i));
            savePicDet();
        }
        picture = new Picture();
        picture.setImgAddress(path);
        picture.setIntroduce(introduce.getText().toString());
        picture.setTitle(name.getText().toString());
        SharedPreferences p=getSharedPreferences("user",MODE_PRIVATE);
        int id = p.getInt("user_id",0);
        picture.setUserId(id);
        savePic(picture);
    }

    private void savePic(Picture picture){
        Gson gson = new Gson();
        final String pic = gson.toJson(picture);
        new Thread(){
            @Override
            public void run() {
                try {
                    Log.e("pika","传数据！！！");
                    URL url = new URL(urlAdress+"/PhotographGet/picture/addPic?picture="+pic);
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
    private void savePicDet(){
        Gson gson = new Gson();
        final String picdet = gson.toJson(pictureDetail);
        new Thread(){
            @Override
            public void run() {
                try {
                    Log.e("pika","传数据！！！");
                    URL url = new URL(urlAdress+"/PhotographGet/pictureDetail/add?picDetail="+picdet);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.e("pikaqiu","传过来了呢！");
                    Log.e("xx2",info);
                    wrapperMessage1(info);
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

    private void wrapperMessage1(String info) {
        Message msg = Message.obtain();
        msg.obj = info;
        msg.what = 62;
        handler.sendMessage(msg);
    }
    private void findnum(){
        new Thread(){
            @Override
            public void run() {
                try {
                    Log.e("pika","传数据！！！");
                    URL url = new URL(urlAdress+"/PhotographGet/picture/findnum");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.e("pikaqiu","传过来了呢！");
                    Log.e("xx2",info);
                    wrapperMessage2(info);
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

    private void wrapperMessage2(String info) {
        Message msg = Message.obtain();
        msg.obj = info;
        msg.what = 26;
        handler.sendMessage(msg);
    }
    public void getEXIF(PictureDetail pictureDetail,String path){
        pictureDetail.setPicId(n+1);
        try {
            ExifInterface exifInterface = new ExifInterface(
                    path);
            String FFNumber = exifInterface
                    .getAttribute(ExifInterface.TAG_APERTURE);
            pictureDetail.setPtype(FFNumber);
            String FDateTime = exifInterface
                    .getAttribute(ExifInterface.TAG_DATETIME);
            pictureDetail.setTime(FDateTime);
            String FFocalLength = exifInterface
                    .getAttribute(ExifInterface.TAG_FOCAL_LENGTH);
            pictureDetail.setFocalLength(FFocalLength);
            String FImageLength = exifInterface
                    .getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
            String FImageWidth = exifInterface
                    .getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
            pictureDetail.setCarmeraLen(FImageLength+"*"+FImageWidth);
            String FISOSpeedRatings = exifInterface
                    .getAttribute(ExifInterface.TAG_ISO);
            pictureDetail.setIso(FISOSpeedRatings);
            String FMake = exifInterface
                    .getAttribute(ExifInterface.TAG_MAKE);
            pictureDetail.setBrand(FMake);
            String FModel = exifInterface
                    .getAttribute(ExifInterface.TAG_MODEL);
            pictureDetail.setType(FModel);
            String latitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            String longitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);

            double lat = score2dimensionality(latitude);
            double lon = score2dimensionality(longitude);
            pictureDetail.setLatitude(lat);
            pictureDetail.setLongitude(lon);

            Log.i("aaa", "FFNumber:" + FFNumber);
            Log.i("aaa", "FDateTime:" + FDateTime);
            Log.i("aaa", "FFocalLength:" + FFocalLength);
            Log.i("aaa", "FImageLength:" + FImageLength);
            Log.i("aaa", "FImageWidth:" + FImageWidth);
            Log.i("aaa", "FISOSpeedRatings:" + FISOSpeedRatings);
            Log.i("aaa", "FMake:" + FMake);
            Log.i("aaa", "FModel:" + FModel);
            Log.i("aaa","GPSLat:"+lat);
            Log.i("aaa","GPSLon:"+lon);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * 将 112/1,58/1,390971/10000 格式的经纬度转换成 112.99434397362694格式
     * @param string 度分秒
     * @return 度
     */
    private double score2dimensionality(String string) {
        double dimensionality = 0.0;
        if (null==string){
            return dimensionality;
        }

        //用 ，将数值分成3份
        String[] split = string.split(",");
        for (int i = 0; i < split.length; i++) {

            String[] s = split[i].split("/");
            //用112/1得到度分秒数值
            double v = Double.parseDouble(s[0]) / Double.parseDouble(s[1]);
            //将分秒分别除以60和3600得到度，并将度分秒相加
            dimensionality=dimensionality+v/Math.pow(60,i);
        }
        return dimensionality;
    }
}
