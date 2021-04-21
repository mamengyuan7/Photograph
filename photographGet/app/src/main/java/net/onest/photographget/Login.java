package net.onest.photographget;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.onest.photographget.entity.User;
import net.onest.photographget.utils.ResetPwd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import androidx.appcompat.app.AppCompatActivity;

import static net.onest.photographget.MainActivity.urlAdress;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "ifu25";

    //返回按钮
    private ImageButton mIbNavigationBack;
    private LinearLayout mLlLoginPull;
    private View mLlLoginLayer;
    private LinearLayout mLlLoginOptions;
    //手机号
    private EditText mEtLoginUsername;
    //登录密码
    private EditText mEtLoginPwd;
    //登录用户名layout
    private LinearLayout mLlLoginUsername;
    private ImageView mIvLoginUsernameDel;
    private Button mBtLoginSubmit;
    //登录密码layout
    private LinearLayout mLlLoginPwd;
    private ImageView mIvLoginPwdDel;
    private ImageView mIvLoginLogo;
    private LinearLayout mLayBackBar;
    private TextView mTvLoginForgetPwd;
    private Button mBtLoginRegister;
    private TextView tv_navigation_label;
    //全局Toast
    private Toast mToast;
    private int mLogoHeight;
    private int mLogoWidth;
    //返回按钮
    private ImageView ib_navigation_back;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        SharedPreferences p=getSharedPreferences("user",MODE_PRIVATE);
        tv_navigation_label=findViewById(R.id.tv_navigation_label);
        tv_navigation_label.setText("登录");
        initView();

        //显示服务器返回的数据
        handler= new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String info = (String)msg.obj;
                Log.e("info",info);
                if("输入错误".equals(info)){
                    Toast.makeText(getApplicationContext(),info,Toast.LENGTH_SHORT).show();
                }else{
                    Gson gson=new Gson();
                    User usering = new User();
                    usering = gson.fromJson(info,User.class);
                    int id=usering.getId();
                    SharedPreferences.Editor editor=p.edit();
                    editor.putInt("user_id",id);
                    editor.commit();
                    int a = p.getInt("user_id",0);
                    Log.e("yz",a+"");
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.putExtra("name",usering.getNickName());
                    intent.putExtra("moto",usering.getHead_portrait());
                    intent.putExtra("image",usering.getBackground());
                    intent.putExtra("phonee",usering.getTelephone());
                    intent.putExtra("time",usering.getPers_signature());
                    intent.putExtra("id",usering.getId());
                    startActivity(intent);
                }
            }
        };

        // 监听号码输入框的字数
        mEtLoginUsername.addTextChangedListener(new TextWatcher() {
            CharSequence input;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                input=s;
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()!=0){
                    mIvLoginUsernameDel.setVisibility(View.VISIBLE);
                }else{
                    mIvLoginUsernameDel.setVisibility(View.GONE);
                }
                if (s.toString().trim().length() > 11) {
                    Toast.makeText(getApplicationContext(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    mEtLoginUsername.setTextColor(getResources().getColor(R.color.red));
                }else{
                    mEtLoginUsername.setTextColor(getResources().getColor(R.color.white));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听密码输入框的字数
        mEtLoginPwd.addTextChangedListener(new TextWatcher() {
            CharSequence input;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                input=s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() !=0) {
                    mIvLoginPwdDel.setVisibility(View.VISIBLE);
                }else{
                    mIvLoginPwdDel.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }

    private void initView() {
        //登录层、下拉层、其它登录方式层
/*      mLlLoginLayer = findViewById(R.id.ll_login_layer);
        mLlLoginPull = findViewById(R.id.ll_login_pull);*/
        mLlLoginOptions = findViewById(R.id.ll_login_options);
        //导航栏+返回按钮
        /*mLayBackBar = findViewById(R.id.ly_retrieve_bar);*/
        mIbNavigationBack = findViewById(R.id.ib_navigation_back);
        //logo
        mIvLoginLogo = findViewById(R.id.iv_login_logo);
        //username
        mLlLoginUsername = findViewById(R.id.ll_login_username);
        mEtLoginUsername = findViewById(R.id.et_login_username);

        mIvLoginUsernameDel = findViewById(R.id.iv_login_username_del);

        //passwd
        mLlLoginPwd = findViewById(R.id.ll_login_pwd);
        mEtLoginPwd = findViewById(R.id.et_login_pwd);
        mIvLoginPwdDel = findViewById(R.id.iv_login_pwd_del);

        //提交、注册
        mBtLoginSubmit = findViewById(R.id.bt_login_submit);
        mBtLoginRegister = findViewById(R.id.bt_login_register);

        //忘记密码
        mTvLoginForgetPwd = findViewById(R.id.tv_login_forget_pwd);
        mTvLoginForgetPwd.setOnClickListener(this);

        //注册点击事件
/*        mLlLoginPull.setOnClickListener(this);*/
        mIbNavigationBack.setOnClickListener(this);
        mEtLoginUsername.setOnClickListener(this);
        mIvLoginUsernameDel.setOnClickListener(this);
        mBtLoginSubmit.setOnClickListener(this);
        mBtLoginRegister.setOnClickListener(this);
        mEtLoginPwd.setOnClickListener(this);
        mIvLoginPwdDel.setOnClickListener(this);
        findViewById(R.id.ib_login_weibo).setOnClickListener(this);
        findViewById(R.id.ib_login_qq).setOnClickListener(this);
        findViewById(R.id.ib_login_wx).setOnClickListener(this);
        //注册其它事件
       /* mLayBackBar.getViewTreeObserver().addOnGlobalLayoutListener(this);
        mEtLoginUsername.setOnFocusChangeListener(this);
        mEtLoginUsername.addTextChangedListener(this);
        mEtLoginPwd.setOnFocusChangeListener(this);
        mEtLoginPwd.addTextChangedListener(this);*/
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_navigation_back:
                //返回
                finish();
                break;
            case R.id.et_login_username:
                mEtLoginPwd.clearFocus();
                mEtLoginUsername.setFocusableInTouchMode(true);
                mEtLoginUsername.requestFocus();
                break;
            case R.id.et_login_pwd:
                mEtLoginUsername.clearFocus();
                mEtLoginPwd.setFocusableInTouchMode(true);
                mEtLoginPwd.requestFocus();
                break;
            case R.id.iv_login_username_del:
                //清空用户名
                mEtLoginUsername.setText(null);
                break;
            case R.id.iv_login_pwd_del:
                //清空密码
                mEtLoginPwd.setText(null);
                break;
            case R.id.bt_login_submit:
                //登录
                loginRequest();
                break;
            case R.id.bt_login_register:
                //注册
               startActivity(new Intent(Login.this, Register.class));
                break;
            case R.id.tv_login_forget_pwd:
                //忘记密码
                startActivity(new Intent(Login.this, ResetPwd.class));
                break;
            /*case R.id.ll_login_layer:
            case R.id.ll_login_pull:
                mLlLoginPull.animate().cancel();
                mLlLoginLayer.animate().cancel();

                int height = mLlLoginOptions.getHeight();
                float progress = (mLlLoginLayer.getTag() != null && mLlLoginLayer.getTag() instanceof Float) ? (float) mLlLoginLayer.getTag() : 1;
                int time = (int) (360 * progress);

                if (mLlLoginPull.getTag() != null) {
                    mLlLoginPull.setTag(null);
                    glide(height, progress, time);
                } else {
                    mLlLoginPull.setTag(true);
                    upGlide(height, progress, time);
                }
                break;*/
            case R.id.ib_login_weibo:
                weiboLogin();
                Toast.makeText(Login.this,"zhuanweibo",Toast.LENGTH_SHORT).show();

                break;
            case R.id.ib_login_qq:
                qqLogin();
                break;
            case R.id.ib_login_wx:
                weixinLogin();
                break;
            default:
                break;
        }

    }


    private void weixinLogin() {

    }

    private void qqLogin() {

    }

    private void weiboLogin() {

    }

    private void loginRequest() {
        String num = mEtLoginUsername.getText().toString();
        String pwd = mEtLoginPwd.getText().toString();
        User user = new User(num,pwd);
        Gson gson = new Gson();
        String client = gson.toJson(user);
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(urlAdress+"/PhotographGet/user/ifuser?client=" + client);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
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
    private void wrapperMessage(String info){
        Message msg = Message.obtain();
        msg.obj = info;
        handler.sendMessage(msg);
    }

}
