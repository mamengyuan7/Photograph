package net.onest.photographget;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.google.gson.Gson;

import net.onest.photographget.OSS.MyLog;
import net.onest.photographget.OSS.UploadHelper;
import net.onest.photographget.adapter.ImageAdapter;
import net.onest.photographget.entity.Picture;
import net.onest.photographget.entity.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static net.onest.photographget.MainActivity.urlAdress;

public class uploadheadPic extends AppCompatActivity {
    private static final int REQUEST_CODE = 0x00000011;
    private static final int PERMISSION_WRITE_EXTERNAL_REQUEST_CODE = 0x00000012;
    private RecyclerView rvImage;
    private ImageView back;
    private ImageView add;
    private ImageAdapter mAdapter;
    private ArrayList<String> images = new ArrayList<>();
    private SharedPreferences p;
    private int value;
    private String uploaduel;
    private Handler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadhead_pic);
        p=getSharedPreferences("user",MODE_PRIVATE);
        value=p.getInt("user_id",0);
        ButterKnife.bind(this);
        rvImage = findViewById(R.id.heada_image);
        add = findViewById(R.id.touadd);
        rvImage.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new ImageAdapter(this);
        rvImage.setAdapter(mAdapter);
        back = findViewById(R.id.back_head);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(true)  //设置是否单选
                        .canPreview(true) //是否点击放大图片查看,，默认为true
                        .start(uploadheadPic.this, REQUEST_CODE); // 打开相册
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

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String info = (String) msg.obj;
                Gson gson = new Gson();
                User user = new User();
                user = gson.fromJson(info, User.class);
                Log.e("测试",user.getNickName());
                Log.e("测试",user.getPers_signature());
            }
        };
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
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
    @OnClick(R.id.putt)
    public void uploadTest() {
        UploadHelper uploadHelper = new UploadHelper();
        uploaduel = uploadHelper.uploadPortrait(images.get(0));
        //这个方法会返回OSS上图片的路径
        MyLog.e("xxxx----testurl:" + uploaduel);
        String path = uploaduel;
        sendMessage();
        Log.e("xxx",path);
        finish();
    }

    private void sendMessage() {
       User user=new User();
       user.setId(value);
       user.setHead_portrait(uploaduel);
        Gson gson = new Gson();
        String client = gson.toJson(user);
        Log.e("login验证",client);
        new Thread(){
            @Override
            public void run() {
                try {
                    //没改
                    URL url = new URL(urlAdress+"/PhotographGet/user/updatehead?client=" + client);
                    Log.e("发送完数据啦","OK");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.i("检测","得到"+info);
                    //wrapperMessage(info);
                    if(null!=info) {
                        Log.e("ww", info);
                        wrapperMessage(info);
                    }
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
        handler.sendMessage(msg);
    }
}
