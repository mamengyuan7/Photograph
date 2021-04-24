package net.onest.photographget.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import net.onest.photographget.R;
import net.onest.photographget.entity.Picture;
import net.onest.photographget.entity.User;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Picture> mDataList=new ArrayList<>();
    private Context context;
    private ArrayList<String> imgList=new ArrayList<>();
    private String imagePath;
    private Handler handler;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image_first;
        TextView picture_title;
        TextView picture_introduce;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_first=itemView.findViewById(R.id.image_first);
            picture_title=itemView.findViewById(R.id.picture_title);
            picture_introduce=itemView.findViewById(R.id.picture_introduce);
            image_first.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
    public ListAdapter(List<Picture> pictureList, Context context){
        this.context=context;
        mDataList=pictureList;
    }

    //item的加载

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_item,
                parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        imgList=new ArrayList<>();
        Picture picture=mDataList.get(position);
        Log.e("picture数据",picture.toString());
        holder.picture_title.setText(picture.getTitle());
        holder.picture_introduce.setText(picture.getIntroduce());
        Log.e("tupianquan",picture.getTitle()+picture.getIntroduce()+picture.getImgAddress());
       /* holder.image_first.setImageResource();*/
        String imagejson=picture.getImgAddress();
        List<String> list=new ArrayList<>();
        if(imagejson.contains("--")){
            String[] s=imagejson.split("--");
            for(String ss:s){
                list.add(ss);
            }
        }else {
            list.add(imagejson);

        }
        Log.e("tupianfen",picture.getTitle()+picture.getIntroduce()+list.get(0).toString());
        imagePath=list.get(0);
        Glide.with(context).load(imagePath).into(holder.image_first);

    }
    private void getPicBitmap(String imagePath) {
        new Thread() {
            private HttpURLConnection conn;
            private Bitmap bitmap;
            public void run() {
                // 连接服务器 get 请求 获取图片
                try {
                    //创建URL对象
                    Log.e("ppppp",imagePath);
                    URL url = new URL(imagePath);
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
                        msg.what = 404;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = 404;
                    handler.sendMessage(msg);
                }
                //关闭连接
                conn.disconnect();
            }
        }.start();
    }
    private void wrapperMessage(String info) {
        Message msg = Message.obtain();
        msg.what=60;
        msg.obj = info;
        handler.sendMessage(msg);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
    public void refresh(List<Picture>  newList) {
        //刷新数据
        mDataList.removeAll(mDataList);
        mDataList.addAll(newList);
        notifyDataSetChanged();
    }


}
