package net.onest.photographget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wx.goodview.GoodView;

import net.onest.photographget.adapter.AdapterComment;
import net.onest.photographget.entity.Comment;
import net.onest.photographget.entity.Picture;
import net.onest.photographget.model.Commentt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static net.onest.photographget.MainActivity.urlAdress;

public class DetailedActivity extends AppCompatActivity implements View.OnClickListener {
    private MultiImageView multiImageView;
    private GoodView mGoodView;
    private ImageView comment;
    private TextView hide_down;
    private EditText comment_content;
    private Button comment_send;
    private ImageView back;
    private TextView title;
    private TextView typee;
    private TextView nickName;
    private LinearLayout rl_enroll;
    private RelativeLayout rl_comment;
    private ListView comment_list;
    private List<Commentt> data;
    private List<Comment> listcomm = new ArrayList<>();
    private AdapterComment adapterComment;
    private List<String> imgs = new ArrayList<>();
    private Picture picture;
    private MultiImageView.OnItemClickListener mOnItemClickListener;
    private Comment comm = new Comment();
    private Commentt commentt = new Commentt();
    private String name;
    private int picId = 7;
    private int userId = 1;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == 66){
                super.handleMessage(msg);
                name = (String)msg.obj;
                nickName.setText(name);
                Log.e("comm",name);
            }else if(msg.what == 26){
                super.handleMessage(msg);
                String info = (String)msg.obj;
                Log.e("comm",info);
            }else if(msg.what == 62){
                super.handleMessage(msg);
                String info = (String)msg.obj;
                Log.e("aaa",info);
                Type type=new TypeToken<Picture>(){}.getType();
                Gson gson=new Gson();
                picture = gson.fromJson(info,type);
                title.setText(picture.getTitle());
                typee.setText(picture.getIntroduce());
                String a = picture.getImgAddress();
                Log.e("aaaa",a);
                String[] path = a.split("--");
                for(int i = 0;i<path.length;i++){
                    imgs.add(path[i]);
                }
                multiImageView.setList(imgs);
                multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.e("是谁？",""+position);
                        Intent intent = new Intent(DetailedActivity.this,LargePic.class);
                        intent.putExtra("pos",position);
                        intent.putExtra("pId",picId);
                        intent.putExtra("address",imgs.get(position));
                        startActivity(intent);
                        finish();
                    }
                });
            }else if(msg.what == 51){
                super.handleMessage(msg);
                String info = (String)msg.obj;
                Log.e("aaa",info);
                Type type=new TypeToken<List<Comment>>(){}.getType();
                Gson gson=new Gson();
                listcomm = gson.fromJson(info,type);
                for(int i = 0;i<listcomm.size();i++){
                    getNickName(listcomm.get(i).getUserId());
                    commentt.setName(name+"：");
                    commentt.setContent(listcomm.get(i).getContent());
                    Log.e("aaaa","123123123");
                }
            }

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_detailed);

        title = findViewById(R.id.de_title);
        typee = findViewById(R.id.de_type);
        nickName = findViewById(R.id.de_nickname);
        back = findViewById(R.id.img_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //图片展示
        multiImageView = findViewById(R.id.multiImage);

        SharedPreferences p=getSharedPreferences("user",MODE_PRIVATE);
        userId = p.getInt("user_id",0);

        listImg();

        /*imgs.add("https://file06.16sucai.com/2016/0613/8b7ab7ea218d6fbea16d75eda49bd9ca.jpg");
        imgs.add("https://file06.16sucai.com/2016/0328/f6de184de1f109750ed5d316c3bbd324.jpg");
        imgs.add("https://file06.16sucai.com/2016/0328/f6de184de1f109750ed5d316c3bbd324.jpg");
        imgs.add("https://file06.16sucai.com/2016/0613/5b4aeec20404962438c8bb791f1979da.jpg");
        imgs.add("https://file06.16sucai.com/2016/0613/892e575cf8ba89579eacde6fae3bcf74.jpg");
        imgs.add("https://file06.16sucai.com/2016/0613/33445716589c16ca0742d1cefc2ac701.jpg");
        imgs.add("https://file06.16sucai.com/2016/0613/732a0311b4d49b20d7d9d904f07a5357.jpg");
        imgs.add("https://file06.16sucai.com/2016/0613/1c5d376864fb8fa320b624b066078658.jpg");
        imgs.add("http://pic1.bbzhi.com/fengjingbizhi/ganshoudaziran-renyuziranzhutisheying/nature_sz197_people_nature_01_8780_11.jpg");

        multiImageView.setList(imgs);
        multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.e("是谁？",""+position);
                Intent intent = new Intent(DetailedActivity.this,LargePic.class);
                intent.putExtra("pos",position);
                intent.putExtra("pId",1);
                intent.putExtra("address",imgs.get(position));
                startActivity(intent);
                finish();
            }
        });*/
        //点赞功能
        mGoodView = new GoodView(this);
        initView1();
        listcomment();
        //获取nickname
        getNickName(userId);
    }
    //点赞点击事件
    public void collection(View view) {
        ((ImageView) view).setImageResource(R.drawable.collection_checked);
        mGoodView.setTextInfo("收藏成功", Color.parseColor("#f66467"), 12);
        mGoodView.show(view);
    }
    private void initView1() {

        // 初始化评论列表
        comment_list = (ListView) findViewById(R.id.comment_list);
        // 初始化数据
        data = new ArrayList<>();
        // 初始化适配器
        adapterComment = new AdapterComment(getApplicationContext(), data);
        // 为评论列表设置适配器
        comment_list.setAdapter(adapterComment);

        comment = (ImageView) findViewById(R.id.comment);
        hide_down = (TextView) findViewById(R.id.hide_down);
        comment_content = (EditText) findViewById(R.id.comment_content);
        comment_send = (Button) findViewById(R.id.comment_send);

        rl_enroll = (LinearLayout) findViewById(R.id.rl_enroll);
        rl_comment = (RelativeLayout) findViewById(R.id.rl_comment);

        setListener();
    }

    /**
     * 设置监听
     */
    public void setListener(){
        comment.setOnClickListener(this);

        hide_down.setOnClickListener(this);
        comment_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comment:
                // 弹出输入法
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                // 显示评论框
                rl_enroll.setVisibility(View.GONE);
                rl_comment.setVisibility(View.VISIBLE);
                break;
            case R.id.hide_down:
                // 隐藏评论框
                rl_enroll.setVisibility(View.VISIBLE);
                rl_comment.setVisibility(View.GONE);
                // 隐藏输入法，然后暂存当前输入框的内容，方便下次使用
                InputMethodManager im = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(comment_content.getWindowToken(), 0);
                break;
            case R.id.comment_send:
                sendComment(name);
                break;
            default:
                break;
        }
    }

    //发送评论
    public void sendComment(String name){
        if(comment_content.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "评论不能为空！", Toast.LENGTH_SHORT).show();
        }else{
            // 生成评论数据
            commentt.setName(name+"：");
            commentt.setContent(comment_content.getText().toString());
            comm.setContent(comment_content.getText().toString());
            comm.setPicId(picId);
            comm.setUserId(userId);
            Log.e("comm",comm.getContent());
            saveComment();
            adapterComment.addComment(commentt);
            // 发送完，清空输入框
            comment_content.setText("");

            Toast.makeText(getApplicationContext(), "评论成功！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private void getNickName(int userId){
        String id = String.valueOf(userId);
        new Thread(){
            @Override
            public void run() {
                try {
                    Log.e("数据是：","");
<<<<<<< HEAD
                    URL url = new URL("http://192.168.43.169:8080/PhotographGet/user/getNickname?userId="+id);
=======
                    URL url = new URL(urlAdress+"/PhotographGet/user/getNickname?userId="+id1);
>>>>>>> 5eca40fe054aa94846824001806f0824af80da8e
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
    private void saveComment(){
        Gson gson = new Gson();
        final String c = gson.toJson(comm);
        new Thread(){
            @Override
            public void run() {
                try {
                    Log.e("数据是：","");
                    URL url = new URL(urlAdress+"/PhotographGet/comment/addcomment?comment="+c);
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
    private void listImg(){
        new Thread(){
            @Override
            public void run() {
                try {
                    Log.e("数据是：","picId="+picId);
                    URL url = new URL(urlAdress+"/PhotographGet/picture/lista?id="+picId);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.e("pikaqiu","传过来了呢！");
                    Log.e("xx2",info);
                    wrapperMessage3(info);
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

    private void listcomment(){
        new Thread(){
            @Override
            public void run() {
                try {
                    Log.e("数据是：","");
                    URL url = new URL("http://192.168.43.169:8080/PhotographGet/comment/listcomment?picId="+picId);
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

    private void wrapperMessage3(String info) {
        Message msg = Message.obtain();
        msg.obj = info;
        msg.what = 62;
        handler.sendMessage(msg);
    }

    private void wrapperMessage(String info) {
        Message msg = Message.obtain();
        msg.obj = info;
        msg.what = 66;
        handler.sendMessage(msg);
    }
    private void wrapperMessage1(String info) {
        Message msg = Message.obtain();
        msg.obj = info;
        msg.what = 26;
        handler.sendMessage(msg);
    }
    private void wrapperMessage2(String info) {
        Message msg = Message.obtain();
        msg.obj = info;
        msg.what = 51;
        handler.sendMessage(msg);
    }
}
