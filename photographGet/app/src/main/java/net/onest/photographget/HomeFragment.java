package net.onest.photographget;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


public class HomeFragment extends Fragment implements View.OnClickListener {
    View view;

    private Context context;
    private ViewPager viewPager;
    private ArrayList<View> pageview;
    private ImageView logoLayout;
    private TextView home_1_Layout;
    private TextView home_2_Layout;
    private TextView home_3_Layout;
    // 滚动条图片
    private ImageView scrollbar;
    // 滚动条初始偏移量
    private int offset = 0;
    // 当前页编号
    private int currIndex = 0;
    // 滚动条宽度
    private int bmpW;
    //一倍滚动量
    private int one;

    ////////////////轮播图




    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("test", "初始化首页");
        view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        initView();

        return view;
    }

    private void initView() {
        //查找布局文件用LayoutInflater.inflate
        LayoutInflater inflater =getLayoutInflater();

        View view1 = inflater.inflate(R.layout.home_logo,null);
        View view2 = inflater.inflate(R.layout.home_1,null);
        View view3 = inflater.inflate(R.layout.home_2,null);
        View view4 = inflater.inflate(R.layout.home_3,null);

        logoLayout=view.findViewById(R.id.img_logo);
        home_1_Layout=view.findViewById(R.id.home_1);
        home_2_Layout=view.findViewById(R.id.home_2);
        home_3_Layout=view.findViewById(R.id.home_3);
        scrollbar=view.findViewById(R.id.scrollbar);

        logoLayout.setOnClickListener(this);
        home_1_Layout.setOnClickListener(this);
        home_2_Layout.setOnClickListener(this);
        home_3_Layout.setOnClickListener(this);

        pageview=new ArrayList<View>();
        //添加想要切换的界面
        pageview.add(view1);
        pageview.add(view2);
        pageview.add(view3);
        pageview.add(view4);
        //数据适配器
        PagerAdapter mPagerAdapter=new PagerAdapter() {
            @Override
            //获取当前窗体界面数
            public int getCount() {
                return pageview.size();
            }

            @Override
            //判断是否由对象生成界面
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view==object;
            }
            //使从ViewGroup中移出当前View
            public void destroyItem(View arg0, int arg1, Object arg2) {
                ((ViewPager) arg0).removeView(pageview.get(arg1));
            }
            //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
            public Object instantiateItem(View arg0, int arg1){
                ((ViewPager)arg0).addView(pageview.get(arg1));
                return pageview.get(arg1);
            }
        };
        //绑定适配器
        viewPager.setAdapter(mPagerAdapter);
        //设置viewPager的初始界面为第一个界面
        viewPager.setCurrentItem(0);
        //添加切换界面的监听器
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        // 获取滚动条的宽度
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.scrollbar).getWidth();
        System.out.println("这是滚动条的宽度："+bmpW);
        //为了获取屏幕宽度，新建一个DisplayMetrics对象
        DisplayMetrics displayMetrics = new DisplayMetrics();
        //将当前窗口的一些信息放在DisplayMetrics类中
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //得到屏幕的宽度
        int screenW = displayMetrics.widthPixels;
        System.out.println("这是屏幕宽度："+screenW);
        //计算出滚动条初始的偏移量
        offset = (screenW/ 5 ) -bmpW;
        System.out.println("这是滚动条初始的偏移量："+offset);
        //计算出切换一个界面时，滚动条的位移量
        one = offset + bmpW+10;
        System.out.println("这是切换一个界面时，滚动条的位移量："+one);
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        //将滚动条的初始位置设置成与左边界间隔一个offset
        scrollbar.setImageMatrix(matrix);


    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    /**
                     * TranslateAnimation的四个属性分别为
                     * float fromXDelta 动画开始的点离当前View X坐标上的差值
                     * float toXDelta 动画结束的点离当前View X坐标上的差值
                     * float fromYDelta 动画开始的点离当前View Y坐标上的差值
                     * float toYDelta 动画开始的点离当前View Y坐标上的差值
                     **/
                    animation = new TranslateAnimation(one, 0, 0, 0);
                    break;
                case 1:
                    animation = new TranslateAnimation(offset, one, 0, 0);
                    break;
                case 2:
                    animation = new TranslateAnimation(offset*2, one*2, 0, 0);
                    break;
                case 3:
                    animation = new TranslateAnimation(offset*3, one*3, 0, 0);
                    break;

            }
            //arg0为切换到的页的编码
            currIndex = arg0;
            // 将此属性设置为true可以使得图片停在动画结束时的位置
            animation.setFillAfter(true);
            //动画持续时间，单位为毫秒
            animation.setDuration(200);
            //滚动条开始动画
            scrollbar.startAnimation(animation);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_logo:
                //点击"logo“时切换到第一页
                viewPager.setCurrentItem(0);

                break;
            case R.id.home_1:
                //点击“音乐”时切换的第二页
                viewPager.setCurrentItem(1);
                break;
            case R.id.home_2:
                viewPager.setCurrentItem(2);
                break;
            case R.id.home_3:
                viewPager.setCurrentItem(3);
                break;
        }

    }

    /*private static final String ARG_SHOW_TEXT = "text";

    private String mContentText;


    public HomeFragment() {
        // Required empty public constructor
    }

    *//**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment BlankFragment.
     *//*
    public static HomeFragment newInstance(String param1) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHOW_TEXT, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContentText = getArguments().getString(ARG_SHOW_TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        TextView contentTv = rootView.findViewById(R.id.content_tv);
        contentTv.setText(mContentText);
        return rootView;
    }*/

}
