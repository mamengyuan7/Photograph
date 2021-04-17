package net.onest.photographget;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.onest.photographget.entity.User;

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

public class Register  extends AppCompatActivity implements View.OnClickListener {
    //手机号
    private EditText et_register_username;
    //删除手机号
    private ImageView iv_register_username_del;
    //验证码
   /* private EditText et_register_auth_code;*/
    //服务条款
    private TextView tv_protocol;
    //返回按钮
    private ImageView ib_navigation_back;
    //注册按钮
    private Button bt_register_submit;
    //复选框
    private CheckBox cb_protocol;
    //密码
    private EditText et_register_pwd;
    //删除密码
    private ImageView iv_register_pwd_del;
    private String phone;
    private String password;
    private TextView tv_navigation_label;

    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String info = (String) msg.obj;
                Log.e("ymz", info);
                if (!(info.equals("手机号重复使用"))) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<Integer>(){}.getType();
                    int id = gson.fromJson(info,type);
                    Log.e("yz",id+"");
                    Intent intent = new Intent();
                   /* intent.setClass(getApplicationContext(), DefaultAddress.class);*/
                    intent.putExtra("id",id+"");
                    startActivity(intent);
//                    overridePendingTransition(R.anim.in, R.anim.out);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT).show();
                }
            }
        };
        tv_navigation_label=findViewById(R.id.tv_navigation_label);
        tv_navigation_label.setText("注册");
        findViewById(R.id.ib_navigation_back).setOnClickListener(this);
        initview();
        // 监听号码输入框的字数
        et_register_username.addTextChangedListener(new TextWatcher() {
            CharSequence input;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                input=s;
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()!=0){
                    iv_register_username_del.setVisibility(View.VISIBLE);
                }else{
                    iv_register_username_del.setVisibility(View.GONE);
                    bt_register_submit.setBackground(getResources().getDrawable(R.drawable.bg_login_submit_lock));
                }
                if (s.toString().trim().length() > 11) {
                    Toast.makeText(getApplicationContext(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    et_register_username.setTextColor(getResources().getColor(R.color.red));
                }else{
                    et_register_username.setTextColor(getResources().getColor(R.color.white));
                }
                if(!(et_register_username.getText().toString().trim().equals(""))&&!(et_register_pwd.getText().toString().trim().equals(""))&&cb_protocol.isChecked()){
                    bt_register_submit.setBackground(getResources().getDrawable(R.drawable.bg_login_submit));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //监听密码输入框的字数
        et_register_pwd.addTextChangedListener(new TextWatcher() {
            CharSequence input;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                input=s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() !=0) {
                    iv_register_pwd_del.setVisibility(View.VISIBLE);
                }else{
                    iv_register_pwd_del.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void initview() {
        et_register_username=findViewById(R.id.et_register_username);
       /* et_register_auth_code=findViewById(R.id.et_register_auth_code);*/
        tv_protocol=findViewById(R.id.tv_protocol);
        bt_register_submit=findViewById(R.id.bt_register_submit);
        cb_protocol=findViewById(R.id.cb_protocol);
        iv_register_username_del=findViewById(R.id.iv_register_username_del);
        et_register_pwd=findViewById(R.id.et_register_pwd);
        iv_register_pwd_del=findViewById(R.id.iv_register_pwd_del);
        et_register_username.setOnClickListener(this);
       /* et_register_auth_code.setOnClickListener(this);*/
        tv_protocol.setOnClickListener(this);
        bt_register_submit.setOnClickListener(this);
        cb_protocol.setOnClickListener(this);
        iv_register_username_del.setOnClickListener(this);
        et_register_pwd.setOnClickListener(this);
        iv_register_pwd_del.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_navigation_back:
                finish();
                break;
            case R.id.bt_register_submit:

                if(et_register_username.getText().toString().trim().equals("")||et_register_pwd.getText().toString().trim().equals("")){
                    Toast.makeText(this,"手机号或密码不能为空",Toast.LENGTH_SHORT).show();
                    bt_register_submit.setBackground(getResources().getDrawable(R.drawable.bg_login_submit_lock));
                }
                if(!cb_protocol.isChecked()){
                    Toast.makeText(this,"请勾选已同意服务条款",Toast.LENGTH_SHORT).show();
                    bt_register_submit.setBackground(getResources().getDrawable(R.drawable.bg_login_submit_lock));
                }
                bt_register_submit.setBackground(getResources().getDrawable(R.drawable.bg_login_submit));
                Log.e("username",et_register_username.getText().toString());
                Log.e("code",et_register_pwd.getText().toString());
                phone=et_register_username.getText().toString();
                password=et_register_pwd.getText().toString();
                if(!(et_register_username.getText().toString().trim().equals(""))&&!(et_register_pwd.getText().toString().trim().equals(""))&&cb_protocol.isChecked()){
                    bt_register_submit.setBackground(getResources().getDrawable(R.drawable.bg_login_submit));
                    RegisterUser(phone, password);
                    startActivity(new Intent(Register.this, Login.class));
                }
                break;
            case R.id.iv_register_username_del:
                et_register_username.setText(null);
                break;
            case R.id.iv_register_pwd_del:
                et_register_pwd.setText(null);
                break;
        }
    }

    private void RegisterUser(String phone, String password) {
        User user = new User(phone, password);
        //背景
        /*user.setBackground();*/
        user.setNickName("默认");
        user.setPers_signature("请设置您的个签");
        //头像
        /*user.setHead_portrait();*/
        Gson gson = new Gson();
        String client = gson.toJson(user);
        Log.e("mmy", client);
        new Thread() {
            @Override
            public void run() {
                try {
                    Log.e("mm",client);
                    URL url = new URL("http://192.168.43.169:8080/PhotographGet/user/adduser?user=" + client);
                    Log.e("mm1",client);
                    URLConnection conn = url.openConnection();
                    Log.e("mm2",client);
                    InputStream in = conn.getInputStream();
                    Log.e("mm3",client);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.e("wwq", info);
                    wrapperMessage(info);
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
        msg.obj = info;
        handler.sendMessage(msg);
    }

}
