package net.onest.photographget;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register  extends AppCompatActivity implements View.OnClickListener {
    //手机号
    private EditText et_register_username;
    //验证码
    private EditText et_register_auth_code;
    //服务条款
    private TextView tv_protocol;
    //注册按钮
    private Button bt_register_submit;
    //复选框
    private CheckBox cb_protocol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        findViewById(R.id.ib_navigation_back).setOnClickListener(this);
        initview();
    }

    public void initview() {
        et_register_username=findViewById(R.id.et_register_username);
        et_register_auth_code=findViewById(R.id.et_register_auth_code);
        tv_protocol=findViewById(R.id.tv_protocol);
        bt_register_submit=findViewById(R.id.bt_register_submit);
        cb_protocol=findViewById(R.id.cb_protocol);
        et_register_username.setOnClickListener(this);
        et_register_auth_code.setOnClickListener(this);
        tv_protocol.setOnClickListener(this);
        bt_register_submit.setOnClickListener(this);
        cb_protocol.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_navigation_back:
                finish();
                break;
            case R.id.bt_register_submit:
                if(et_register_username.getText().toString().trim().equals("")||et_register_auth_code.getText().toString().trim().equals("")){
                    Toast.makeText(this,"手机号或验证码不能为空",Toast.LENGTH_SHORT).show();
                }
                if(!cb_protocol.isChecked()){
                    Toast.makeText(this,"请勾选已同意服务条款",Toast.LENGTH_SHORT).show();
                }
                Log.e("username",et_register_username.getText().toString());
                Log.e("code",et_register_auth_code.getText().toString());

        }
    }
}
