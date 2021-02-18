package net.onest.photographget;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import net.onest.photographget.entity.Huodong;
import net.onest.photographget.entity.User;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class Huodong_adpter extends BaseAdapter {
    private LayoutInflater inflater;

    //数据源
    private List<Huodong> huodongs;
    private int itemLayoutId;
    private Context context;
    private CustomListener listener=new CustomListener();

    @Override
    public int getCount() {
        return huodongs.size();
    }

    @Override
    public Object getItem(int i) {
        return huodongs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        inflater=LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.home_1_item,null);
        //初始化布局中的元素
        ImageView img = layout.findViewById(R.id.img_item);
        TextView topic = layout.findViewById(R.id.tv_topic);

        //获取数据显示内容(数据绑定，将数据显示到布局中)
       // Glide.with(context).load(huodongs.get(i).getPic()).into(img);
        topic.setText(huodongs.get(i).getTopic());

        img.setOnClickListener(listener);
        topic.setOnClickListener(listener);

        return layout;
    }



    public Huodong_adpter(List<Huodong> huodongs, int itemLayoutId, Context context) {
        this.huodongs =huodongs;
        this.itemLayoutId = itemLayoutId;
        this.context = context;
    }




    class  CustomListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(context,Test_intent.class);

            context.startActivity(intent);
        }
    }
}
