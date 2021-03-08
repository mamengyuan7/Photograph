package net.onest.photographget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ConvertUtils;
import com.bumptech.glide.Glide;
import com.hitomi.tilibrary.style.index.NumberIndexIndicator;
import com.hitomi.tilibrary.style.progress.ProgressBarIndicator;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.vansz.glideimageloader.GlideImageLoader;
import com.wx.goodview.GoodView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import net.onest.photographget.adapter.AdapterComment;
import net.onest.photographget.model.Comment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DetailedActivity extends BaseActivity {
    private MultiImageView multiImageView;
    private GoodView mGoodView;

    private ImageView comment;
    private TextView hide_down;
    private EditText comment_content;
    private Button comment_send;

    private LinearLayout rl_enroll;
    private RelativeLayout rl_comment;

    private ListView comment_list;
    private List<Comment> data;
    private AdapterComment adapterComment;


    private List<String> imgs = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.enlarge_view;

    }

    @Override
    protected void initView() {
        RecyclerView rvImages = findViewById(R.id.rv_images);
        rvImages.setLayoutManager(new LinearLayoutManager(this));
        rvImages.setAdapter(new FriendsCircleAdapter());
    }

    @Override
    protected void testTransferee() {
    }

    private TransferConfig.Builder getBuilder(int pos) {
        TransferConfig.Builder builder = TransferConfig.build()
                .setProgressIndicator(new ProgressBarIndicator())
                .setIndexIndicator(new NumberIndexIndicator())
                .setImageLoader(GlideImageLoader.with(getApplicationContext()));
        if (pos == 4) {
            builder.enableHideThumb(false);
        } else if (pos == 5) {
            builder.enableJustLoadHitPage(true);
        } else if (pos == 6) {
            builder.enableDragPause(true);
        }
        return builder;
    }

    /**
     * 朋友圈列表数据适配器
     */
    private class FriendsCircleAdapter extends CommonAdapter<Pair<String, List<String>>> {
        private DividerGridItemDecoration divider = new DividerGridItemDecoration(
                Color.TRANSPARENT,
                ConvertUtils.dp2px(8f),
                ConvertUtils.dp2px(8f)
        );

        FriendsCircleAdapter() {
            super(DetailedActivity.this, R.layout.home_detailed, getFriendsCircleList(DetailedActivity.this));
        }

        @Override
        protected void convert(ViewHolder viewHolder, final Pair<String, List<String>> item, final int position) {
            //详情
            viewHolder.setText(R.id.de_type, item.first);
            //题目
            viewHolder.setText(R.id.de_title,"郊游");
            //头像
            Drawable drawable = getResources().getDrawable(R.mipmap.b1);
            BitmapDrawable bd = (BitmapDrawable) drawable;
            final Bitmap bmm = bd.getBitmap();
            viewHolder.setImageBitmap(R.id.de_touxiang,bmm);
            //点赞
            viewHolder.setOnClickListener(R.id.collection, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("iiii","999999999999999999999999999999999999999999999999999999999999");
                    mGoodView = new GoodView(DetailedActivity.this);
                    Drawable drawable = getResources().getDrawable(R.drawable.collection_checked);
                    BitmapDrawable bd = (BitmapDrawable) drawable;
                    final Bitmap bm = bd.getBitmap();
                    viewHolder.setImageBitmap(R.id.collection,bm);
                    mGoodView.setTextInfo("收藏成功", Color.parseColor("#f66467"), 12);
                    mGoodView.show(viewHolder.getConvertView());
                }
            });
            //EXIF
            viewHolder.setOnClickListener(R.id.exif,new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Log.e("iiii","66666666666666666666666666666666666666666666666666666");
                    Intent intent = new Intent(DetailedActivity.this,SignActivity.class);
                    startActivity(intent);

                }
            });
            //评论
            /*viewHolder.setOnClickListener(R.id.comment, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("aaaaa","55555555555555555555555555555555555555555555555555555");
                    // 弹出输入法
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    // 显示评论框
                    rl_enroll = (LinearLayout) viewHolder.getConvertView();
                    //comment_content = (EditText) viewHolder.getConvertView();
                    //comment_send = (Button) viewHolder.getConvertView();
                    //hide_down = (TextView) viewHolder.getConvertView();
                    rl_enroll.setVisibility(View.GONE);
                    //rl_comment = (RelativeLayout) viewHolder.getConvertView();
                    //rl_comment.setVisibility(View.VISIBLE);
                }
            });*/

            final RecyclerView rvPhotos = viewHolder.getView(R.id.rv_photos);
            // 重置 divider
            rvPhotos.removeItemDecoration(divider);
            rvPhotos.addItemDecoration(divider);
            if (rvPhotos.getLayoutManager() == null)
                rvPhotos.setLayoutManager(new GridLayoutManager(DetailedActivity.this, 3));
            PhotosAdapter photosAdapter = new PhotosAdapter(
                    DetailedActivity.this,
                    R.layout.item_picture,
                    item.second
            );
            photosAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int pos) {
                    transferee.apply(getBuilder(position)
                            .setNowThumbnailIndex(pos)
                            .setSourceUrlList(item.second)
                            .bindRecyclerView(((RecyclerView) view.getParent()), R.id.iv_thum)
                    ).show();
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                    return false;
                }
            });
            rvPhotos.setAdapter(photosAdapter);
        }
    }

    /**
     * 单个 item 中照片数据适配器
     */
    private static class PhotosAdapter extends CommonAdapter<String> {

        PhotosAdapter(Context context, int layoutId, List<String> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(final ViewHolder holder, String url, final int position) {
            ImageView imageView = holder.getView(R.id.iv_thum);
            Glide.with(imageView)
                    .load(url)
                    .placeholder(R.mipmap.ic_empty_photo)
                    .into(imageView);
        }
    }
    public static List<Pair<String, List<String>>> getFriendsCircleList(Context context) {
        List<Pair<String, List<String>>> friendsCircleList = new ArrayList<>();

        List<String> imgs = new ArrayList<>();
        imgs.add("https://file06.16sucai.com/2016/0613/8b7ab7ea218d6fbea16d75eda49bd9ca.jpg");
        imgs.add("https://file06.16sucai.com/2016/0328/f6de184de1f109750ed5d316c3bbd324.jpg");
        imgs.add("https://file06.16sucai.com/2016/0613/fcf84cb40747b3602135fb9dd4f0d897.jpg");
        imgs.add("https://file06.16sucai.com/2016/0613/5b4aeec20404962438c8bb791f1979da.jpg");
        imgs.add("https://file06.16sucai.com/2016/0613/892e575cf8ba89579eacde6fae3bcf74.jpg");
        imgs.add("https://file06.16sucai.com/2016/0613/33445716589c16ca0742d1cefc2ac701.jpg");
        imgs.add("https://file06.16sucai.com/2016/0613/732a0311b4d49b20d7d9d904f07a5357.jpg");
        imgs.add("https://file06.16sucai.com/2016/0613/1c5d376864fb8fa320b624b066078658.jpg");
        imgs.add("http://pic1.bbzhi.com/fengjingbizhi/ganshoudaziran-renyuziranzhutisheying/nature_sz197_people_nature_01_8780_11.jpg");
        friendsCircleList.add(new Pair<>("当 enableHideThumb = false 时，表示 transferee 打开或关闭不会干扰原缩略图的显示隐藏，默认开启", imgs));

        return friendsCircleList;
    }

    /*@Override
            protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.home_detailed);

                //图片展示
                multiImageView = findViewById(R.id.patient_detail_table_pictures);

                imgs.add("https://file06.16sucai.com/2016/0613/8b7ab7ea218d6fbea16d75eda49bd9ca.jpg");
                imgs.add("https://file06.16sucai.com/2016/0328/f6de184de1f109750ed5d316c3bbd324.jpg");
                imgs.add("https://file06.16sucai.com/2016/0613/fcf84cb40747b3602135fb9dd4f0d897.jpg");
                imgs.add("https://file06.16sucai.com/2016/0613/5b4aeec20404962438c8bb791f1979da.jpg");
                imgs.add("https://file06.16sucai.com/2016/0613/892e575cf8ba89579eacde6fae3bcf74.jpg");
                imgs.add("https://file06.16sucai.com/2016/0613/33445716589c16ca0742d1cefc2ac701.jpg");
                imgs.add("https://file06.16sucai.com/2016/0613/732a0311b4d49b20d7d9d904f07a5357.jpg");
                imgs.add("https://file06.16sucai.com/2016/0613/1c5d376864fb8fa320b624b066078658.jpg");
                imgs.add("http://pic1.bbzhi.com/fengjingbizhi/ganshoudaziran-renyuziranzhutisheying/nature_sz197_people_nature_01_8780_11.jpg");

            multiImageView.setList(imgs);
            //点赞功能
            mGoodView = new GoodView(this);
            initView1();

        }*/
    //点赞点击事件
    public void collection(View view) {
        ((ImageView) view).setImageResource(R.drawable.collection_checked);
        mGoodView.setTextInfo("收藏成功", Color.parseColor("#f66467"), 12);
        mGoodView.show(view);
    }
    /*private void initView1() {

        // 初始化评论列表
        comment_list = (ListView) findViewById(R.id.comment_list);
        // 初始化数据
        data = new ArrayList<>();
        // 初始化适配器
        adapterComment = new AdapterComment(getApplicationContext(), data);
        // 为评论列表设置适配器
        comment_list.setAdapter(adapterComment);

        comment = (ImageView) findViewById(R.id.comment);
        hide_down = (TextView) findViewById(R.id.hide_down);
        comment_content = (EditText) findViewById(R.id.comment_content);
        comment_send = (Button) findViewById(R.id.comment_send);

        rl_enroll = (LinearLayout) findViewById(R.id.rl_enroll);
        rl_comment = (RelativeLayout) findViewById(R.id.rl_comment);

        setListener();
    }*/

    /**
     * 设置监听
     */
    /*public void setListener(){
        comment.setOnClickListener(this);

        hide_down.setOnClickListener(this);
        comment_send.setOnClickListener(this);
    }*/

    /*@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comment:
                // 弹出输入法
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                // 显示评论框
                rl_enroll.setVisibility(View.GONE);
                rl_comment.setVisibility(View.VISIBLE);
                break;
            case R.id.hide_down:
                // 隐藏评论框
                rl_enroll.setVisibility(View.VISIBLE);
                rl_comment.setVisibility(View.GONE);
                // 隐藏输入法，然后暂存当前输入框的内容，方便下次使用
                InputMethodManager im = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(comment_content.getWindowToken(), 0);
                break;
            case R.id.comment_send:
                sendComment();
                break;
            default:
                break;
        }
    }

    *//**
     * 发送评论
     *//*
    public void sendComment(){
        if(comment_content.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "评论不能为空！", Toast.LENGTH_SHORT).show();
        }else{
            // 生成评论数据
            Comment comment = new Comment();
            comment.setName("评论者"+(data.size()+1)+"：");
            comment.setContent(comment_content.getText().toString());
            adapterComment.addComment(comment);
            // 发送完，清空输入框
            comment_content.setText("");

            Toast.makeText(getApplicationContext(), "评论成功！", Toast.LENGTH_SHORT).show();
        }
    }*/

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
