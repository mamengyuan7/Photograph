package net.onest.photographget;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.onest.photographget.entity.PictureDetail;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LargePic extends AppCompatActivity {
    private FloatingActionButton floating;
    private ImageView pic;
    private int pos;
    private int pId;
    private PictureDetail pictureDetail;
    protected static final int ERROR = 2;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==66){
                super.handleMessage(msg);
                String info = (String)msg.obj;
                Log.e("zz",info);
                Type type=new TypeToken<PictureDetail>(){}.getType();
                Gson gson=new Gson();
                pictureDetail = gson.fromJson(info,type);
                Log.e("pika",pictureDetail.getAddress());
                getPicBitmap(pictureDetail.getAddress());
            }else if(msg.what == 62){
                Bitmap bitmap = (Bitmap) msg.obj;
                pic.setImageBitmap(bitmap);
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
        setContentView(R.layout.large_pic);
        floating = findViewById(R.id.floating);
        pic = findViewById(R.id.pic);
        Intent intent = getIntent();
        pos = intent.getIntExtra("pos",0);
        Log.e("aaaaaaaaaaa",pos+"");
        pId = intent.getIntExtra("pId",0);
        Log.e("aaaaaaaaaaa",pId+"");
        String path = intent.getStringExtra("address");
        getPicBitmap(path);
        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LargePic.this, EXIFActivity.class);
                intent.putExtra("pos",pos);
                intent.putExtra("pId",1);
                startActivity(intent);
            }
        });
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
}
