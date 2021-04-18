package net.onest.photographget;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SettingDetail extends AppCompatActivity{
    private customListener listener;
    private ImageView setting_back;
    private LinearLayout personal_data;
    private Button logout;
    private LinearLayout image_quality;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_detail);
        Log.e("ss","ssss");
        init();
        onLisener();
    }
    private void onLisener() {
        listener=new customListener();
        setting_back.setOnClickListener(listener);
        personal_data.setOnClickListener(listener);
        logout.setOnClickListener(listener);
        image_quality.setOnClickListener(listener);
    }
    private void init() {
        setting_back=findViewById(R.id.setting_back);
        personal_data=findViewById(R.id.personal_data);
        logout=findViewById(R.id.logout);
        image_quality=findViewById(R.id.image_quality);
    }
    class customListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.setting_back:
                    Log.e("sd","lll");
                    /*不能直接跳转；需要修改*/
                    Intent intent=new Intent(SettingDetail.this,SettingFragment.class);
                    startActivity(intent);
                    break;
                case R.id.personal_data:
                    Intent intent1=new Intent(SettingDetail.this,SettingPersonal.class);
                    startActivity(intent1);
                    break;
               /* case R.id.logout:
                    SharedPreferences preferences = getContext().getSharedPreferences("login", getContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = p.edit();
                    editor.clear();
                    preferences.edit().putBoolean("isFirstIn", true).commit();
                    editor.commit();
                    int a = p.getInt("user_id",0);
                    Log.e("zy",a+"");
                    Intent intent3 = new Intent(view.getContext(), Login.class);
                    startActivity(intent3);
                    break;*/
              /*  case R.id.image_quality:
                    break;
                    */
            }
        }
    }
}

