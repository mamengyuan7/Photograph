package net.onest.photographget.utils;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.onest.photographget.R;

import androidx.appcompat.app.AppCompatActivity;

public class ResetPwd extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_navigation_label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_pwd);
        tv_navigation_label=findViewById(R.id.tv_navigation_label);
        tv_navigation_label.setText("重置密码");
        findViewById(R.id.ib_navigation_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_navigation_back:
                finish();
                break;
        }

    }
}
