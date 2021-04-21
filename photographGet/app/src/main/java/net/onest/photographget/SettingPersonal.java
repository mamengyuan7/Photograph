package net.onest.photographget;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SettingPersonal extends AppCompatActivity {
    private customListener1 listener1;
    private ImageView setting_back1;
    private LinearLayout setphoto;
    private LinearLayout set_background;
    private LinearLayout set_nickname;
    private LinearLayout set_signin;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_personal);
        init();
        onLisener();
    }
    private void onLisener() {
        listener1=new customListener1();
        setting_back1.setOnClickListener(listener1);
    }

    private void init() {
        setting_back1=findViewById(R.id.setting_back1);
        setphoto=findViewById(R.id.setphoto);
        set_background=findViewById(R.id.set_background);
        set_nickname=findViewById(R.id.set_nickname);
        set_signin=findViewById(R.id.set_signin);
    }


    class customListener1 implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.setting_back1:
                   finish();
                    break;

            }
        }
    }
}
