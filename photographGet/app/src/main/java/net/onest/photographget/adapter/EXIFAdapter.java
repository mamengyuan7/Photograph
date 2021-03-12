package net.onest.photographget.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;

import net.onest.photographget.R;
import net.onest.photographget.entity.Picture;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class EXIFAdapter extends ArrayAdapter {
    private int resourceId;

    public EXIFAdapter(Context context, int textViewResourceId, List<Picture> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Picture picture = (Picture)getItem(position);
        View view = LayoutInflater.from (getContext ()).inflate (resourceId, parent, false);
        ImageView pImage = (ImageView) view.findViewById(R.id.exif_img);
        TextView brand = (TextView) view.findViewById(R.id.exif_brand);
        TextView type = (TextView) view.findViewById(R.id.exif_type);
        TextView camera_lens = (TextView) view.findViewById(R.id.exif_camera_lens);
        TextView focal_length = (TextView) view.findViewById(R.id.exif_focal_length);
        TextView iso = (TextView) view.findViewById(R.id.exif_iso);
        TextView time = (TextView) view.findViewById(R.id.exif_time);
        TextView latitude = (TextView) view.findViewById(R.id.exif_latitude);
        TextView longitude = (TextView) view.findViewById(R.id.exif_longitude);

        /*Bitmap bimage=  getBitmapFromURL("https://file06.16sucai.com/2016/0613/8b7ab7ea218d6fbea16d75eda49bd9ca.jpg");
        pImage.setImageBitmap(bimage);*/

        Drawable drawable = LoadImageFromWebOperations("https://p0.ssl.qhimgs1.com/t01497383325fc41f94.jpg");
        pImage.setImageDrawable(drawable);

        brand.setText(picture.getBrand());
        type.setText(picture.getType());
        camera_lens.setText(picture.getCamera_lens());
        focal_length.setText(picture.getFocal_length());
        iso.setText(picture.getIso());
        time.setText(picture.getTime());
        double lat = picture.getLatitude();
        String la = Double.toString(lat);
        String laa = la.substring(0,la.indexOf(".")+3);
        latitude.setText(laa);
        double lon = picture.getLongitude();
        String lo = Double.toString(lon);
        String loo = la.substring(0,lo.indexOf(".")+3);
        longitude.setText(loo);

        return view;
    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }
    private Drawable LoadImageFromWebOperations(String url)
    {
        try
        {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        }catch (Exception e) {
            System.out.println("Exc="+e);
            return null;
        }
    }
}
