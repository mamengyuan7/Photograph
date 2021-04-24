package net.onest.photographget;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import net.onest.photographget.adapter.ListAdapter;
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
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import static net.onest.photographget.MainActivity.urlAdress;

public class SettingProtfolio extends Fragment {

    List<Picture> newList = new ArrayList<Picture>();
    List<Picture> pictures=new ArrayList<>();
    private ListAdapter listAdapter;
    private SharedPreferences p;
    private int value;
    RecyclerView recyclerView;
    RefreshLayout refreshLayout;
    private Handler handler1=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String object = (String) msg.obj;
            Gson gson = new Gson();
            List<Picture> alist = gson.fromJson(object, new TypeToken<List<Picture>>() {}.getType());
            newList=alist;
            pictures.addAll(pictures.size(),alist);
            listAdapter = new ListAdapter(pictures,getContext()); //创建适配器，并且导入数据list
            recyclerView.setAdapter(listAdapter);//布局导入适配器
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        }

    };
    private Handler handler2;
    public static SettingProtfolio newInstance(){
        SettingProtfolio fragment=new SettingProtfolio();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.setting_protfolio ,container, false);
        Log.e("settingprotfolio","开始执行？");
        p=getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        value=p.getInt("user_id",0);

        /*Gson gson=new Gson();
        List<Picture> alist = gson.fromJson(string, new TypeToken<List<Picture>>() {}.getType());
        newList=alist;
        pictures.addAll(pictures.size(),alist);*/

        refreshLayout = (RefreshLayout)rootView.findViewById(R.id.refreshLayout);
        //刷新数据加载
        setPullRefresher();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        //服务器发消息
        sendMessage();
        return rootView;
    }

    private void sendMessage() {
        Log.e("发数据啦","准备！");
        Log.e("id",p+"");
        new Thread(){
            @Override
            public void run() {
                try {
                    //没改
                    URL url = new URL(urlAdress+"/PhotographGet/user/selectall?id=" + value);
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
//        msg.what=60;
        msg.obj = info;
        handler1.sendMessage(msg);
    }

    private void setPullRefresher() {
        //设置 Header 为 MaterialHeader
        //设置 Header 为 ClassicsFooter 比较经典的样式
       refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        //设置 Footer 为 经典样式
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
               //在这里执行上拉刷新时的具体操作(网络请求、更新UI等)
                //模拟网络请求到的数据
                //ArrayList<Community> newList = new ArrayList<Community>();
                /*myAdapter.refresh(list);*/
                refreshLayout.finishRefresh(2000);
                //不传时间则立即停止刷新    传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //模拟网络请求到的数据
                /*int num=myAdapter.getItemCount();*/
                /*ToServer2(num);*/
                //在这里执行下拉加载时的具体操作(网络请求、更新UI等)
                refreshLayout.finishLoadMore(2000);

            }
        });




    }
}

