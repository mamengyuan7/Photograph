package net.onest.photographget;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.google.gson.Gson;

import net.onest.photographget.OSS.MyLog;
import net.onest.photographget.OSS.UploadHelper;
import net.onest.photographget.entity.User;
import net.onest.photographget.utils.ImageUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.baidu.mapapi.BMapManager.getContext;
import static net.onest.photographget.MainActivity.urlAdress;

public class SettingPersonal extends AppCompatActivity implements View.OnClickListener{
    private customListener1 listener1;
    private int value;
    private String new_nickname;
    private ImageView setting_back1;
    private PopupWindow mPopWindow;
    private Handler handler;
    private SharedPreferences p;
    //设置头像
    private LinearLayout setphoto;
    //设置背景图
    private LinearLayout set_background;
    //设置昵称
    private LinearLayout set_nickname;
    //设置个签
    private LinearLayout set_signin;
    //上传头像系列--------
    private static final String TAG = "SettingPersonal";
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    private CircleImageView head_image;
    private String imagePath;

    private String imageHead;
    //-------
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_personal);
        p=getSharedPreferences("user",MODE_PRIVATE);
        value=p.getInt("user_id",0);
        Log.e("iddddd",value+"");
        ButterKnife.bind(this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        //测试user_id为多少

        Log.e("settingpersonal", value + "");
        init();
        onLisener();
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
    private void onLisener() {
        listener1=new customListener1();
        setting_back1.setOnClickListener(listener1);
        setphoto.setOnClickListener(listener1);
        set_background.setOnClickListener(listener1);
        set_nickname.setOnClickListener(listener1);
        set_signin.setOnClickListener(listener1);
      /*  head_image.setOnClickListener(listener1)*/;
    }
    private void init() {
        setting_back1=findViewById(R.id.setting_back1);
        setphoto=findViewById(R.id.setphoto);
        set_background=findViewById(R.id.set_background);
        set_nickname=findViewById(R.id.set_nickname);
        set_signin=findViewById(R.id.set_signin);
        //原始头像
        head_image=findViewById(R.id.head_image);

    }
    class customListener1 implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.setting_back1:
                   finish();
                    break;
                    //修改昵称
                case R.id.set_nickname:
                    showPopupWindow();
                    break;
                    //修改背景图
                case R.id.set_background:
                    break;
                    //修改头像
                case R.id.setphoto:
                    //显示修改头像的对话框
//                    showChoosePicDialog();
                    Intent intent = new Intent(SettingPersonal.this,uploadheadPic.class);
                    startActivity(intent);
                    break;
                    //修改头像：如上
                case R.id.head_image:
                    break;
            }
        }
    }
    private void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置头像");
        String[] items = { "选择本地照片", "拍照" };
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        //选择一张照片，查看照片地址
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        takePicture();
                        break;
                }
            }
        });
        builder.create().show();
    }
    //出错
    private void takePicture() {
        //判断是否存在SD卡：
        if (isSdcardExisting()) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            tempUri=getImageUri();
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
            cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            startActivityForResult(cameraIntent,TAKE_PICTURE);
            } else {
                Toast.makeText(getContext(), "请插入sd卡", Toast.LENGTH_LONG).show();
            }
    }
    private boolean isSdcardExisting() {
        final String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
    //获取图片的URI
    private Uri getImageUri() {
        return Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data!=null) {
            Log.e("ceshi1","zoulema");
            Bundle extras = data.getExtras();
            Log.e("ceshi2","zoulema");
            if (extras != null) {
                Log.e("ceshi3","zoulema");
                Bitmap photo = extras.getParcelable("data");
                Log.e("ceshi4","zoulema");
                imagePath = ImageUtils.savePhoto(photo, Environment
                        .getExternalStorageDirectory().getAbsolutePath(), String
                        .valueOf(System.currentTimeMillis()));
                Log.e("imagePath", imagePath+"");;
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        } else {
            // 没有获取 到权限，从新请求，或者关闭app
            Toast.makeText(this, "需要存储权限", Toast.LENGTH_SHORT).show();
        }
    }

    private void showPopupWindow() {
        //获取自定义popupwindow布局文件
        View contentView = LayoutInflater.from(SettingPersonal.this).inflate(R.layout.update_nickname, null);
       //创建popupwindow实例
        mPopWindow = new PopupWindow(contentView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopWindow.setFocusable(true);
        mPopWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setContentView(contentView);
        EditText update_nickname=(EditText)contentView.findViewById(R.id.update_nickname);
        Button update_qd=(Button)contentView.findViewById(R.id.update_qd);
        Button update_qx=(Button)contentView.findViewById(R.id.update_qx);
        update_qd.setOnClickListener(this);
        update_qx.setOnClickListener(this);
        View rootview = LayoutInflater.from(SettingPersonal.this).inflate(R.layout.setting_personal, null);
        mPopWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);

        mPopWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                /**** 如果点击了popupwindow的外部，popupwindow也会消失 ****/
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    mPopWindow.dismiss();
                    return true;
                }
                return false;
            }
        });

        update_qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_nickname=update_nickname.getText().toString();
                Log.e("uuuu",new_nickname+"");
                if(new_nickname==""){
                    Toast.makeText(getApplicationContext(),"用户昵称不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    //更新操作
                    sendMessage();
                    Toast.makeText(SettingPersonal.this,"更改昵称成功",Toast.LENGTH_SHORT).show();
                    mPopWindow.dismiss();// 关闭
                }
            }
        });
    }

    private void sendMessage() {
        User user=new User(value,new_nickname);
        Gson gson = new Gson();
        String client = gson.toJson(user);
        Log.e("login验证",client);
        new Thread(){
            @Override
            public void run() {
                try {
                    //没改
                    URL url = new URL(urlAdress+"/PhotographGet/user/updatenickname?client=" + client);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.update_qx:
                mPopWindow.dismiss();
                break;
            /*case R.id.update_qd:
                *//*new_nickname=mPopWindow.update_nickname.getText().toString();*//*
                Log.e("update_nickname",new_nickname);
                Log.e("y用户昵称",new_nickname);
                if (new_nickname==null){
                    Toast.makeText(this,"用户昵称不能为空!",Toast.LENGTH_LONG).show();
                }else {
                    Log.e("y用户昵称",new_nickname);
                }
                break;*/
        }


    }
}
