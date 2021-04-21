package net.onest.photographget.utils;

import android.util.Log;

import net.onest.photographget.entity.Picture;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QueryInfo {
    private List<Picture> teachers;
    private OkHttpClient okHttpClient=new OkHttpClient();
    private EventBus eventBus;
    //测试用例
    public void getPicInfos(){
        //1. OkHttpClient对象
        //2. Request对象
        final Request request = new Request.Builder()
                .url("http://192.168.43.169:8080/PhotographGet/picture/listall")
                .build();
        //3. Call对象
        Call call = okHttpClient.newCall(request);
        //4. 异步请求
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("lww", "请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result= response.body().string();
//                Log.e("服务端数据",result);
                EventBean eventBean=new EventBean(result,1);
                eventBus=EventBus.getDefault();
                eventBus.post(eventBean);
            }
        });
    }
}
