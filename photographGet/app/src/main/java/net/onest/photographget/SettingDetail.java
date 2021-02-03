package net.onest.photographget;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SettingDetail extends AppCompatActivity {
    ImageView setting_back;
    LinearLayout personal_data;
    Button logout;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_detail);
        init();
    }

    private void init() {
       /* setting_back=findViewById()*/
    }
}
