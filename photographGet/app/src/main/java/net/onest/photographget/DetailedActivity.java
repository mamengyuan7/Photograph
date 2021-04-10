package net.onest.photographget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
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

import com.wx.goodview.GoodView;

import net.onest.photographget.adapter.AdapterComment;
import net.onest.photographget.model.Comment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailedActivity extends AppCompatActivity implements View.OnClickListener {
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
    private MultiImageView.OnItemClickListener mOnItemClickListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_detailed);

        //图片展示
        multiImageView = findViewById(R.id.multiImage);

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
        multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.e("是谁？",""+position);
                Intent intent = new Intent(DetailedActivity.this,EXIFActivity.class);
                intent.putExtra("pos",position);
                intent.putExtra("pId",1);
                startActivity(intent);
                finish();
            }
        });
        //点赞功能
        mGoodView = new GoodView(this);
        initView1();
        }
    //点赞点击事件
    public void collection(View view) {
        ((ImageView) view).setImageResource(R.drawable.collection_checked);
        mGoodView.setTextInfo("收藏成功", Color.parseColor("#f66467"), 12);
        mGoodView.show(view);
    }
    private void initView1() {

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
    }

    /**
     * 设置监听
     */
    public void setListener(){
        comment.setOnClickListener(this);

        hide_down.setOnClickListener(this);
        comment_send.setOnClickListener(this);
    }

    @Override
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

    //发送评论
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
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
