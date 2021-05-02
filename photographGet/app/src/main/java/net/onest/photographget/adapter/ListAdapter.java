package net.onest.photographget.adapter;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import net.onest.photographget.DetailedActivity;
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

        holder.image_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("picid",picture.getId());
                intent.setClass(context, DetailedActivity.class);
                context.startActivity(intent);


            }
        });


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
