package net.onest.photographget;

import android.content.Context;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.onest.photographget.entity.Picture;
import net.onest.photographget.entity.User;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

public class Photo_adapter extends RecyclerView.Adapter<Photo_adapter.VH> {
    private int  picId;
    String s;
    //② 创建ViewHolder
    public  class VH extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView title;
        private TextView like;
        public VH( View v) {
            super(v);
           img=v.findViewById(R.id.img_p);
           title=v.findViewById(R.id.tv_username);
           like=v.findViewById(R.id.tv_like);
        }
    }

//数据源
    private List<Picture> pictures=new ArrayList<>();
    private int itemLayoutId;
    private Context context;
    private CustomListener listener=new CustomListener();

    public Photo_adapter(List<Picture> pictures, int itemLayoutId, Context context) {
        this.pictures =pictures;
        this.itemLayoutId = itemLayoutId;
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.kinds_item, null, false);
        return  new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH viewHolder, int position) {

            Glide.with(context).load(pictures.get(position).getImgAddress()).into(viewHolder.img);
            /*viewHolder.username.setText(users.get(position).getName());*/
            viewHolder.title.setText(pictures.get(position).getTitle());
            int index=pictures.get(position).getId();
            //点击事件,点击图片，姓名，跳转到详情页面
            viewHolder.img.setOnClickListener(listener);
            viewHolder.title.setOnClickListener(listener);
            viewHolder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent();
                    intent.putExtra("picid",index);
                    intent.setClass(context,DetailedActivity.class);

                    context.startActivity(intent);
                }
            });
    }



    @Override
    public int getItemCount() {
        return pictures.size();
    }

    class  CustomListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(context,DetailedActivity.class);

            context.startActivity(intent);
        }
    }
}
