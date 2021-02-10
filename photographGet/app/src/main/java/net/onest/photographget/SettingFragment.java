package net.onest.photographget;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.net.sip.SipSession;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class SettingFragment extends Fragment {
    private static final String ARG_SHOW_TEXT = "text";
    View view;
    private String mContentText;
    private ImageView setting;
    Toolbar toolbar;
    AppBarLayout mAppBarLayout;//标题部分
    private LayoutInflater mInflater;
    private TabLayout sliding_tabs;//小标题
    private ViewPager viewpager;//小页面
    private View setting_protfolio;//个人作品
    private View setting_collection;//个人收藏
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
        init();
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
    }
    private int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }

}
