package net.onest.photographget;

import android.content.Context;
import android.content.Intent;
import android.content.SyncStatusObserver;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup mTabRadioGroup;
    private PopupWindow mPopWindow;
    View popupWindowView;
    private SparseArray<Fragment> mFragmentSparseArray;
<<<<<<< HEAD
    public static String urlAdress="http://192.168.1.102:8080";
=======
    public static String urlAdress="http://192.168.43.65:8080";
>>>>>>> 41fe674853563e93eb3c2eddea70c85f28916519

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        /*initPopupWindow();*/
    }

    private void initView() {
        mTabRadioGroup = findViewById(R.id.tabs);
        mFragmentSparseArray = new SparseArray<>();
        mFragmentSparseArray.append(R.id.shouye_tab,HomeFragment.newInstance());
        mFragmentSparseArray.append(R.id.wode_tab, SettingFragment.newInstance());
        mTabRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 具体的fragment切换逻辑可以根据应用调整，例如使用show()/hide()
                getSupportFragmentManager().beginTransaction().replace(R.id.tabContent,
                        mFragmentSparseArray.get(checkedId)).commit();
            }
        });
        // 默认显示第一个
        getSupportFragmentManager().beginTransaction().add(R.id.tabContent,
                mFragmentSparseArray.get(R.id.shouye_tab)).commit();
        findViewById(R.id.sign_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*startActivity(new Intent(MainActivity.this, DetailedActivity.class));*/
                //点击按钮，使弹出半透明页面并有按钮；
               showPopupWindow();
            }
        });
    }

    //重新加载布局
    public void reLoadFragView(){
        /*现将该fragment从fragmentList移除*/
        mFragmentSparseArray.remove(R.id.wode_tab);
        mFragmentSparseArray.append(R.id.wode_tab, SettingFragment.newInstance());
        getSupportFragmentManager().beginTransaction().add(R.id.tabContent,
                mFragmentSparseArray.get(R.id.wode_tab)).commit();
    }

    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.home_fb, null);
        mPopWindow = new PopupWindow(contentView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.FILL_PARENT);
        mPopWindow.setContentView(contentView);
        //设置各个控件的点击响应
        ImageView conceal=(ImageView)contentView.findViewById(R.id.conceal);
        ImageView image_zp=(ImageView)contentView.findViewById(R.id.image_zp) ;
        ImageView iamge_bw=(ImageView)contentView.findViewById(R.id.image_bw);
        conceal.setOnClickListener(this);
        image_zp.setOnClickListener(this);
        iamge_bw.setOnClickListener(this);
        //.....
        //显示PopupWindow
        View rootview = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.conceal:
                Toast.makeText(this,"clicked conceal",Toast.LENGTH_SHORT).show();
                mPopWindow.dismiss();
                break;
            case R.id.image_zp:
                Toast.makeText(this,"clicked conceal",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,uploadPic.class));
                mPopWindow.dismiss();
                break;
            case R.id.image_bw:
                Toast.makeText(this,"bowen",Toast.LENGTH_SHORT);
                startActivity(new Intent(MainActivity.this,Login.class));
                mPopWindow.dismiss();
                break;
                default:
                    break;
        }

    }
}
