<<<<<<< HEAD
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import net.onest.photographget.entity.Huodong;
import net.onest.photographget.entity.Picture;
import net.onest.photographget.entity.User;
import net.onest.photographget.utils.DividerGridItemDecoration;
import net.onest.photographget.utils.EventBean;

import java.sql.SQLTransactionRollbackException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class HomeFragment extends Fragment implements View.OnClickListener,OnBannerListener {
    View view;
    View view1;

    private EventBean eventBean;

    private Banner mBanner;
  //////分类
    private QMUITabSegment tabSegment;
    private ViewPager viewpager_showphoto;
    private Photo_adapter adapter;
    private   List<User> users=new ArrayList<>();

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

    private ListView listView_h;
    private Huodong_adpter aaa;



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("test", "初始化首页");
        view = inflater.inflate(R.layout.fragment_home, container, false);
        view1=inflater.inflate(R.layout.home_1,container,false);

        context=getContext();
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
         //listView_h=view1.findViewById(R.id.listview_item);

        System.out.println("这是viewpage对象："+viewPager);
        initView();
       // Huodong_init();

         return view;
    }

    private void Huodong_init() {

        List<Huodong> huodongs=new ArrayList<>();

        Huodong h1=new Huodong();
        h1.setPic("111");
        h1.setTopic("111");
//
        Huodong h2=new Huodong();
        h2.setPic("222");
        h2.setTopic("222");
//
        huodongs.add(h1);
        huodongs.add(h2);
//
         aaa=new Huodong_adpter(huodongs,R.layout.home_1_item,getContext());
         System.out.println("这是aaa："+aaa);
         listView_h.setAdapter(aaa);
    }


    private void lunbo() {

        System.out.println("这是banner对象："+mBanner);
        //图片资源
        int[] imageResourceID = new int[]{R.mipmap.one_photo,
                R.mipmap.p2,
                R.mipmap.three_photo,
                R.mipmap.pp4};
        List<Integer> imgeList = new ArrayList<>();
        //轮播标题
        String[] mtitle = new String[]{"", "", "", ""};
        List<String> titleList = new ArrayList<>();

        for (int i = 0; i < imageResourceID.length; i++) {
            imgeList.add(imageResourceID[i]);//把图片资源循环放入list里面
            titleList.add(mtitle[i]);//把标题循环设置进列表里面
            //设置图片加载器，通过Glide加载图片
            mBanner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(getActivity()).load(path).into(imageView);
                }
            });
            //设置轮播的动画效果,里面有很多种特效,可以到GitHub上查看文档。
            mBanner.setBannerAnimation(Transformer.Default);
            mBanner.setImages(imgeList);//设置图片资源
            mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);//设置banner显示样式（带标题的样式）
            mBanner.setBannerTitles(titleList); //设置标题集合（当banner样式有显示title时）
            //设置指示器位置（即图片下面的那个小圆点）
            mBanner.setIndicatorGravity(BannerConfig.CENTER);
            mBanner.setDelayTime(2000);//设置轮播时间3秒切换下一图
            mBanner.setOnBannerListener( this);//设置监听
            mBanner.start();//开始进行banner渲染
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mBanner.startAutoPlay();//开始轮播
    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();//结束轮播
    }

    //对轮播图设置点击监听事件
    @Override
    public void OnBannerClick(int position) {
        Toast.makeText(getActivity(), "你点击了第" + (position + 1) + "张轮播图", Toast.LENGTH_SHORT).show();
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

       mBanner=view1.findViewById(R.id.banner);
       lunbo();


       tabSegment=view1.findViewById(R.id.tabSegment);
       viewpager_showphoto=view1.findViewById(R.id.viewpager_showphoto);
        initData();


       photo_kinds();

    }
///////初始化数据
    private List<Picture> initData() {
        User user1 = new User();
        user1.setImg_up("111");
        user1.setName("张三");

        User user2 = new User();
        user2.setImg_up("222");
        user2.setName("李四");

        User user3 = new User();
        user3.setImg_up("333");
        user3.setName("王五 ");
        users.add(user1);
        users.add(user2);
        users.add(user3);

        List<Picture> pictures=new ArrayList<>();
//        eventBus=EventBus.getDefault();
//        if(!eventBus.isRegistered(HomeFragment.this)) {
//            eventBus.register(HomeFragment.this);
//        }
//        new QueryInfo().getTeacherInfos();
        return pictures;
    }


    private void photo_kinds() {
        viewpager_showphoto.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view==object;
            }
            @NonNull
            @Override
            public Object instantiateItem(@NonNull final ViewGroup container, int position) {
                RecyclerView recyclerView = new RecyclerView(getContext());  
if (position==0) {
    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
    gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
    recyclerView.setLayoutManager(gridLayoutManager);
    //设置Adapter
    //teachers=initData();
    adapter = new Photo_adapter(users, R.layout.kinds_item, context);
    recyclerView.setAdapter(adapter);
    //设置分隔线
    recyclerView.addItemDecoration(new DividerGridItemDecoration(getContext()));
}else{
    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
    gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
    recyclerView.setLayoutManager(gridLayoutManager);
    //设置Adapter
    //teachers=initData();

    adapter = new Photo_adapter(users, R.layout.kinds_item, context);
    recyclerView.setAdapter(adapter);
    //设置分隔线
    recyclerView.addItemDecoration(new DividerGridItemDecoration(getContext()));

}
                container.addView(recyclerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return recyclerView;

            }
            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }

        });


        final int normalColor = QMUIResHelper.getAttrColor(getContext(), R.attr.qmui_config_color_blue);
        tabSegment.setDefaultNormalColor(normalColor);

        viewpager_showphoto .setCurrentItem(0, false);
        tabSegment.addTab(new QMUITabSegment.Tab("全部"));
        tabSegment.addTab(new QMUITabSegment.Tab("风景"));
        tabSegment.addTab(new QMUITabSegment.Tab("人像"));
        tabSegment.addTab(new QMUITabSegment.Tab("动物"));

        tabSegment.getTab(0).setTextColor(getResources().getColor(R.color.qmui_config_color_gray_5),getResources().getColor(R.color.white));
        tabSegment.getTab(1).setTextColor(getResources().getColor(R.color.qmui_config_color_gray_5),getResources().getColor(R.color.white));
        tabSegment.getTab(2).setTextColor(getResources().getColor(R.color.qmui_config_color_gray_5),getResources().getColor(R.color.white));
        tabSegment.getTab(3).setTextColor(getResources().getColor(R.color.qmui_config_color_gray_5),getResources().getColor(R.color.white));

        tabSegment.setupWithViewPager(viewpager_showphoto, false);
        tabSegment.setMode(QMUITabSegment.MODE_FIXED);

        int space = QMUIDisplayHelper.dp2px(getContext(), 16);
        tabSegment.setItemSpaceInScrollMode(space);
        tabSegment.arrowScroll(1);
        tabSegment.canScrollHorizontally(1);
        tabSegment.setHasIndicator(false);

        tabSegment.addOnTabSelectedListener(new QMUITabSegment.OnTabSelectedListener() {
            @Override
            public void onTabReselected(int index) {//当某个 Tab 处于被选中状态下再次被点击时会触发
                tabSegment.hideSignCountView(index);//根据 index 在对应的 Tab 上隐藏红点
            }

            @Override
            public void onTabSelected(int index) {
               // tabSegment.getTab(index).setTextColor(normalColor,selectColor);



            }

            @Override
            public void onTabUnselected(int index) {//当某个 Tab 被取消选中时会触发
//                Log.i("bqt", "【onTabUnselected】" + index);
            }

            @Override
            public void onDoubleTap(int index) {//当某个 Tab 被双击时会触发
//                Log.i("bqt", "【onDoubleTap】" + index);
            }
        });
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
=======
package net.onest.photographget;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import net.onest.photographget.entity.Huodong;
import net.onest.photographget.entity.User;
import net.onest.photographget.utils.DividerGridItemDecoration;

import java.sql.SQLTransactionRollbackException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class HomeFragment extends Fragment implements View.OnClickListener,OnBannerListener {
    View view;
    View view1;

    private Banner mBanner;
  //////分类
    private QMUITabSegment tabSegment;
    private ViewPager viewpager_showphoto;
    private Photo_adapter adapter;
    private   List<User> users=new ArrayList<>();

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

    private ListView listView_h;
    private Huodong_adpter aaa;



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("test", "初始化首页");
        view = inflater.inflate(R.layout.fragment_home, container, false);
        view1=inflater.inflate(R.layout.home_1,container,false);

        context=getContext();
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
         //listView_h=view1.findViewById(R.id.listview_item);

        System.out.println("这是viewpage对象："+viewPager);
        initView();
       // Huodong_init();

         return view;
    }

    private void Huodong_init() {

        List<Huodong> huodongs=new ArrayList<>();

        Huodong h1=new Huodong();
        h1.setPic("111");
        h1.setTopic("111");
//
        Huodong h2=new Huodong();
        h2.setPic("222");
        h2.setTopic("222");
//
        huodongs.add(h1);
        huodongs.add(h2);
//
         aaa=new Huodong_adpter(huodongs,R.layout.home_1_item,getContext());
         System.out.println("这是aaa："+aaa);
         listView_h.setAdapter(aaa);
    }


    private void lunbo() {

        System.out.println("这是banner对象："+mBanner);
        //图片资源
        int[] imageResourceID = new int[]{R.mipmap.one_photo,
                R.mipmap.two_photo,
                R.mipmap.three_photo,
                R.mipmap.four_photo};
        List<Integer> imgeList = new ArrayList<>();
        //轮播标题
        String[] mtitle = new String[]{"图片1", "图片2", "图片3", "图片4"};
        List<String> titleList = new ArrayList<>();

        for (int i = 0; i < imageResourceID.length; i++) {
            imgeList.add(imageResourceID[i]);//把图片资源循环放入list里面
            titleList.add(mtitle[i]);//把标题循环设置进列表里面
            //设置图片加载器，通过Glide加载图片
            mBanner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(getActivity()).load(path).into(imageView);
                }
            });
            //设置轮播的动画效果,里面有很多种特效,可以到GitHub上查看文档。
            mBanner.setBannerAnimation(Transformer.Default);
            mBanner.setImages(imgeList);//设置图片资源
            mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);//设置banner显示样式（带标题的样式）
            mBanner.setBannerTitles(titleList); //设置标题集合（当banner样式有显示title时）
            //设置指示器位置（即图片下面的那个小圆点）
            mBanner.setIndicatorGravity(BannerConfig.CENTER);
            mBanner.setDelayTime(2000);//设置轮播时间3秒切换下一图
            mBanner.setOnBannerListener( this);//设置监听
            mBanner.start();//开始进行banner渲染
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mBanner.startAutoPlay();//开始轮播
    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();//结束轮播
    }

    //对轮播图设置点击监听事件
    @Override
    public void OnBannerClick(int position) {
        Toast.makeText(getActivity(), "你点击了第" + (position + 1) + "张轮播图", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(),DetailedActivity.class);
        startActivity(intent);
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

       mBanner=view1.findViewById(R.id.banner);
       lunbo();


       tabSegment=view1.findViewById(R.id.tabSegment);
       viewpager_showphoto=view1.findViewById(R.id.viewpager_showphoto);
        initData();


       photo_kinds();

    }

    private void initData() {
        User user1 = new User();
       /* user1.setImg_up("111");
        user1.setName("名字1");*/

        User user2 = new User();
       /* user2.setImg_up("222");
        user2.setName("名字2");*/

        User user3 = new User();
      /*  user3.setImg_up("333");
        user3.setName("名字3");*/
        users.add(user1);
        users.add(user2);
        users.add(user3);
    }

    private void photo_kinds() {
        viewpager_showphoto.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view==object;
            }
            @NonNull
            @Override
            public Object instantiateItem(@NonNull final ViewGroup container, int position) {
                RecyclerView recyclerView = new RecyclerView(getContext());  
if (position==0) {
    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
    gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
    recyclerView.setLayoutManager(gridLayoutManager);
    //设置Adapter
    //teachers=initData();
    adapter = new Photo_adapter(users, R.layout.kinds_item, context);
    recyclerView.setAdapter(adapter);
    //设置分隔线
    recyclerView.addItemDecoration(new DividerGridItemDecoration(getContext()));
}else{
    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
    gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
    recyclerView.setLayoutManager(gridLayoutManager);
    //设置Adapter
    //teachers=initData();

    adapter = new Photo_adapter(users, R.layout.kinds_item, context);
    recyclerView.setAdapter(adapter);
    //设置分隔线
    recyclerView.addItemDecoration(new DividerGridItemDecoration(getContext()));

}
                container.addView(recyclerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return recyclerView;

            }
            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }

        });


        final int normalColor = QMUIResHelper.getAttrColor(getContext(), R.attr.qmui_config_color_blue);
        tabSegment.setDefaultNormalColor(normalColor);

        viewpager_showphoto .setCurrentItem(0, false);
        tabSegment.addTab(new QMUITabSegment.Tab("1"));
        tabSegment.addTab(new QMUITabSegment.Tab("2"));
        tabSegment.addTab(new QMUITabSegment.Tab("3"));
        tabSegment.addTab(new QMUITabSegment.Tab("4"));

        tabSegment.getTab(0).setTextColor(getResources().getColor(R.color.qmui_config_color_gray_5),getResources().getColor(R.color.white));
        tabSegment.getTab(1).setTextColor(getResources().getColor(R.color.qmui_config_color_gray_5),getResources().getColor(R.color.white));
        tabSegment.getTab(2).setTextColor(getResources().getColor(R.color.qmui_config_color_gray_5),getResources().getColor(R.color.white));
        tabSegment.getTab(3).setTextColor(getResources().getColor(R.color.qmui_config_color_gray_5),getResources().getColor(R.color.white));

        tabSegment.setupWithViewPager(viewpager_showphoto, false);
        tabSegment.setMode(QMUITabSegment.MODE_FIXED);

        int space = QMUIDisplayHelper.dp2px(getContext(), 16);
        tabSegment.setItemSpaceInScrollMode(space);
        tabSegment.arrowScroll(1);
        tabSegment.canScrollHorizontally(1);
        tabSegment.setHasIndicator(false);

        tabSegment.addOnTabSelectedListener(new QMUITabSegment.OnTabSelectedListener() {
            @Override
            public void onTabReselected(int index) {//当某个 Tab 处于被选中状态下再次被点击时会触发
                tabSegment.hideSignCountView(index);//根据 index 在对应的 Tab 上隐藏红点
            }

            @Override
            public void onTabSelected(int index) {
               // tabSegment.getTab(index).setTextColor(normalColor,selectColor);



            }

            @Override
            public void onTabUnselected(int index) {//当某个 Tab 被取消选中时会触发
//                Log.i("bqt", "【onTabUnselected】" + index);
            }

            @Override
            public void onDoubleTap(int index) {//当某个 Tab 被双击时会触发
//                Log.i("bqt", "【onDoubleTap】" + index);
            }
        });
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
>>>>>>> 6465eb4a3a3f2f40bcfa804af5eca4102626d22b
