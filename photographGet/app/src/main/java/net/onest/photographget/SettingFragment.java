package net.onest.photographget;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.sip.SipSession;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.youth.banner.listener.OnBannerListener;

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
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import static net.onest.photographget.MainActivity.urlAdress;

public class SettingFragment extends Fragment implements View.OnClickListener, OnBannerListener {
    private static final String ARG_SHOW_TEXT = "text";
    View view;
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

    private int value;
    private LayoutInflater mInflater;
    private TabLayout sliding_tabs;//小标题
    private ViewPager viewpager;//小页面
    private View setting_protfolio;//个人作品
    private View setting_collection;//个人收藏
    private Handler handler;
    private SharedPreferences p;
    private List<String> mTitleList = new ArrayList<>();
    private List<View> mViewList = new ArrayList<>();
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
        init();
        sendMessage();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String info = (String) msg.obj;
                Gson gson = new Gson();
                User user = new User();
                user = gson.fromJson(info, User.class);
                mCollapsingToolbarLayout.setTitle(user.getNickName());
                setting_nickname.setText(user.getNickName());
                setting_moto.setText(user.getPers_signature());
                Log.e("测试",user.getNickName());
                Log.e("测试",user.getPers_signature());

            }
        };
        //测试user_id为多少
        value = p.getInt("user_id", 0);
        Log.e("value", value + "");

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(view.getContext(),SettingDetail.class);
                startActivity(intent);
            }
        });
        //标题名称
        mCollapsingToolbarLayout.setTitle("可乐加冰");
        Log.e("pp","aaaaa");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
       /* 设置折叠时的标题*/
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        /*设置标题是否显示*/
        mCollapsingToolbarLayout.setTitleEnabled(true);
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
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
                   URL url = new URL(urlAdress+"/Catchtime/UserInfo?userId="+value);
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

    private void init() {

        sliding_tabs=view.findViewById(R.id.sliding_tabs);
        viewpager=view.findViewById(R.id.viewpager);
        mInflater=LayoutInflater.from(getContext());
        setting_protfolio=mInflater.inflate(R.layout.setting_protfolio,null);
        setting_collection=mInflater.inflate(R.layout.setting_collection,null);
        if(mViewList.size()==0&&mTitleList.size()==0){
           /* 判断语句*/
            Log.e("pandaun","panduan");
            mViewList.add(setting_protfolio);
            mViewList.add(setting_collection);
            mTitleList.add("作品");
            mTitleList.add("收藏");
        }
        Log.e("panduan","11");
        sliding_tabs.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        sliding_tabs.addTab(sliding_tabs.newTab().setText(mTitleList.get(0)));//添加选项卡
        sliding_tabs.addTab(sliding_tabs.newTab().setText(mTitleList.get(1)));//添加选项卡
        ViewPagerAdapter mAdapter = new ViewPagerAdapter(mViewList,mTitleList);
        viewpager.setAdapter(mAdapter);//给ViewPager设置适配器
        sliding_tabs.setupWithViewPager(viewpager);  //将TabLayout和ViewPager关联起来。
        sliding_tabs.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器

        toolbar=view.findViewById(R.id.toolbar);
        setting=view.findViewById(R.id.setting);
        mAppBarLayout = view.findViewById(R.id.appbar_layout);
        mCollapsingToolbarLayout=view.findViewById(R.id.collapsing_toolbar_layout);
        text_title=view.findViewById(R.id.text_title);
        share=view.findViewById(R.id.share);
        setting_nickname=view.findViewById(R.id.setting_nickname);
        setting_moto=view.findViewById(R.id.setting_moto);
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
}
