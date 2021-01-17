package net.onest.photographget;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.net.sip.SipSession;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class SettingFragment extends Fragment {
    private static final String ARG_SHOW_TEXT = "text";
    View view;
    private String mContentText;
    Toolbar toolbar;
    AppBarLayout mAppBarLayout;//标题部分
    CollapsingToolbarLayout mCollapsingToolbarLayout;//折叠式标题栏
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContentText = getArguments().getString(ARG_SHOW_TEXT);
        }
    }
    public SettingFragment() {
        // Required empty public constructor
    }
    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        return fragment;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("test", "初始化设置页");
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        toolbar=view.findViewById(R.id.toolbar);
        mAppBarLayout = view.findViewById(R.id.appbar_layout);
        mCollapsingToolbarLayout=view.findViewById(R.id.collapsing_toolbar_layout);
        //标题名称
        mCollapsingToolbarLayout.setTitle("可乐加冰");
        Log.e("pp","aaaaa");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
       /* 设置折叠时的标题*/
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        /*设置标题是否显示*/
        mCollapsingToolbarLayout.setTitleEnabled(true);
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);

        return view;
    }

    private int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }

}
