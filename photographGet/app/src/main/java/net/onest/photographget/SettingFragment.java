package net.onest.photographget;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.AppBarLayout.Behavior;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.youth.banner.listener.OnBannerListener;

import net.onest.photographget.OSS.App;
import net.onest.photographget.adapter.FragmentAdapter;
import net.onest.photographget.entity.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.ref.Reference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;

import static net.onest.photographget.LargePic.ERROR;
import static net.onest.photographget.MainActivity.urlAdress;

public class SettingFragment extends Fragment implements ViewPager.OnPageChangeListener,View.OnClickListener, OnBannerListener{
    private static final String ARG_SHOW_TEXT = "text";
    View view;
    private List<Fragment> list;
    private String mContentText;
    private ImageView setting;
    Toolbar toolbar;
    AppBarLayout mAppBarLayout;//标题部分
    //标题：
    private TextView text_title;
    //分享
    private TextView share;
    //昵称
    private TextView setting_nickname;
    //个签
    private TextView setting_moto;
    //背景图
    private ImageView image_view;
    private int value;
    private LayoutInflater mInflater;
    private TabLayout sliding_tabs;
    private ViewPager viewPager;//小页面
    private Handler handler;
    private SharedPreferences p;
    private String imagePath;
    private CircleImageView headimage;
    private String username;
    CollapsingToolbarLayout mCollapsingToolbarLayout;//折叠式标题栏
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContentText = getArguments().getString(ARG_SHOW_TEXT);
        }

    }
    public SettingFragment() {
        // Required empty public constructor
    }
    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        return fragment;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("test", "初始化设置页");
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        p=getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        //测试user_id为多少
        value = p.getInt("user_id", 0);
        Log.e("value", value + "");
        //添加展示作品页面:只有一个暂时
      /*  viewPager.setAdapter(new );*/
        list=new ArrayList<>();
        init();
        sendMessage();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==60){
                    String info = (String) msg.obj;
                    Gson gson = new Gson();
                    User user = new User();
                    user = gson.fromJson(info, User.class);
                    if(user.getNickName()==null){
                        mCollapsingToolbarLayout.setTitle("null?");
                    }else {
                        mCollapsingToolbarLayout.setTitle(user.getNickName());
                    }
                    setting_nickname.setText(user.getNickName());
                    setting_moto.setText(user.getPers_signature());
                    text_title.setText(user.getNickName());
                    username=user.getNickName();
                    imagePath=user.getHead_portrait();
                    if(imagePath==null){
                        //默认头像：
                        Glide.with(view).load("http://savepicturetab.oss-cn-beijing.aliyuncs.com/image/202104/d44662ca3290ab50329a15266fa09a72.jpg").into(headimage);
                    }
                    Glide.with(view).load(imagePath).into(headimage);
                    /*getPicBitmap(imagePath);*/
                    Log.e("测试",user.getNickName());
                    Log.e("测试",user.getPers_signature());
                }
                if(msg.what==62){
                    Bitmap bitmap = (Bitmap) msg.obj;
                    headimage.setImageBitmap(bitmap);
                }
            }



        };
        //跳转到设置页面
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(view.getContext(),SettingDetail.class);
                startActivity(intent);
            }
        });
        image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击图片，进行刷新
                Toast.makeText(view.getContext(),"点击刷新",Toast.LENGTH_SHORT).show();
                Glide.with(view).load(imagePath).into(headimage);
                setting_nickname.setText(username);
                getFragmentManager();
                Intent intent = new Intent();
                MainActivity activity= (MainActivity) getActivity();
                activity.reLoadFragView();
            }
        });
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
       /* 设置折叠时的标题*/
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        /*设置标题是否显示*/
        mCollapsingToolbarLayout.setTitleEnabled(true);
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        //刷新控件
        return view;
    }
    private void sendMessage() {
        Log.e("发数据啦","准备！");
        Log.e("id",p+"");
        new Thread(){
            @Override
            public void run() {
                try {
                    //没改
                   URL url = new URL(urlAdress+"/PhotographGet/user/getuser?id=" + value);
                    Log.e("发送完数据啦","OK");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.i("检测","得到"+info);
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
        msg.what=60;
        msg.obj = info;
        handler.sendMessage(msg);
    }

    private void init() {
        viewPager=view.findViewById(R.id.viewpager);
        sliding_tabs=view.findViewById(R.id.sliding_tabs);
        /* 判断语句*/
        list.add(SettingProtfolio.newInstance());
        list.add(SettingProtfolio.newInstance());
        //getchildfragment加载子viewpager？
        viewPager.setAdapter(new FragmentAdapter(getChildFragmentManager(),list));
        sliding_tabs.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
        Log.e("panduan","11");
        headimage=view.findViewById(R.id.head_image);
        toolbar=view.findViewById(R.id.toolbar);
        setting=view.findViewById(R.id.setting);
        mAppBarLayout = view.findViewById(R.id.appbar_layout);
        mCollapsingToolbarLayout=view.findViewById(R.id.collapsing_toolbar_layout);
        text_title=view.findViewById(R.id.text_title);
        share=view.findViewById(R.id.share);
        setting_nickname=view.findViewById(R.id.setting_nickname);
        setting_moto=view.findViewById(R.id.setting_moto);
        image_view=view.findViewById(R.id.image_view);
        share.setOnClickListener(this);
    }

    private int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }
    @Override
    public void onClick(View v) {
    }
    @Override
    public void OnBannerClick(int position) {

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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                // button01.setBackgroundColor(Color.parseColor("#FCD9DD"));
                break;
            case 1:
                //button02.setBackgroundColor(Color.parseColor("#B4D1E1"));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


}
