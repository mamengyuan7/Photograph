package net.onest.photographget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.baidu.mapapi.SDKInitializer;

import net.onest.photographget.adapter.EXIFAdapter;
import net.onest.photographget.entity.Picture;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EXIFActivity extends AppCompatActivity {
    private List<Picture> pics = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exit_activity);
        initpics();
        EXIFAdapter adapter = new EXIFAdapter(EXIFActivity.this, R.layout.exif_item, pics);
        ListView listView = (ListView) findViewById(R.id.list_exif);
        listView.setAdapter(adapter);
    }

    private void initpics() {
        Picture pic1 = new Picture(1,"E://image//万达广场.jpg",1,"vivo","3炫彩","无",
                "wu","4.7mm","320","2021:02:19 19:01:52",38.02618789666666,114.53675079333333);
        pics.add(pic1);
        Picture pic2 = new Picture(1,"E://image//万达广场.jpg",1,"vivo","3炫彩","无",
                "wu","4.7mm","320","2021:02:19 19:01:52",38.02618789666666,114.53675079333333);
        pics.add(pic2);
    }
}
