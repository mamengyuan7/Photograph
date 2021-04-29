package net.onest.photographget.utils;

import android.util.Log;

import net.onest.photographget.entity.Picture;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static net.onest.photographget.MainActivity.urlAdress;

public class QueryInfo {
    private List<Picture> pictures;
    private OkHttpClient okHttpClient=new OkHttpClient();
    private EventBus eventBus;


    //查询所有图片
    public void getPicInfos(){
        //1. OkHttpClient对象
        //2. Request对象
        final Request request = new Request.Builder()
                .url(urlAdress+"/PhotographGet/picture/listall")
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


    //根据typeid查询图片信息
    public void getPicInfosByTypeId(String info){
        FormBody body = new FormBody.Builder()
                .add("typeId", info)
                .build();
        final Request request = new Request.Builder()
                .url(urlAdress+"/PhotographGet/picture/typepic?typeId="+info)
                .get()
                .build();
        //3. Call对象
        Call call = okHttpClient.newCall(request);
        //4. 异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("lww根据id查询图片", "请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result= response.body().string();
//                Log.e("搜索的课题服务端数据",result);
                EventBean eventBean=new EventBean(result,0);
                if(info.equals("1")){
                    eventBean=new EventBean(result,2);
                }else if(info.equals("2")){
                    eventBean=new EventBean(result,3);
                }else if(info.equals("3")){
                    eventBean=new EventBean(result,4);
                }


                eventBus=EventBus.getDefault();
                eventBus.post(eventBean);
            }
        });
    }


}
