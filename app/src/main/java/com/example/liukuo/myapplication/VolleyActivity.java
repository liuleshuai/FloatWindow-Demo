package com.example.liukuo.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class VolleyActivity extends Activity {
    public static final int MALE = 0;
    public static final int FEMALE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Person person = new Person();
        person.setSex(MALE);
        ((TextView) findViewById(R.id.button)).setText(person.getSexDes());
        final ImageView iv = findViewById(R.id.iv);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //①
//        ImageRequest imageRequest = getImageRequest1(iv);
//        requestQueue.add(imageRequest);
        //②
//        imageRequest2(iv, requestQueue);
        //③
        init3(requestQueue);
    }

    private void init3(RequestQueue queue) {
        NetworkImageView netWorkIv = findViewById(R.id.network);
        ImageLoader imageLoader = new ImageLoader(queue, new BitmapCache());
        netWorkIv.setDefaultImageResId(R.mipmap.ic_launcher);
        netWorkIv.setErrorImageResId(R.mipmap.ic_launcher);
        netWorkIv.setImageUrl("http://n.sinaimg.cn/sports/2_img/upload/aecad723/227/w803h1024/20180422/HwWk-fznefkh8846639.jpg",
                imageLoader);
    }

    private void imageRequest2(final ImageView iv, RequestQueue queue) {
        ImageLoader imageLoader = new ImageLoader(queue, new BitmapCache());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(iv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        imageLoader.get("http://n.sinaimg.cn/sports/2_img/vcg/cf0d0fdd/107/w1024h683/20180424/Rui2-fzqvvsa2889060.jpg", listener);
    }

    @NonNull
    private ImageRequest getImageRequest1(final ImageView iv) {
        return new ImageRequest(
                "https://wx2.sinaimg.cn/mw690/73243a6dly1fqixmkcuyrj20rs0ijgmx.jpg",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        iv.setImageBitmap(bitmap);
                    }
                },
                0,
                0,
                ImageView.ScaleType.FIT_CENTER,
                Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(VolleyActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    class Person {
        @SEX
        private int sex;

        public void setSex(@SEX int sex) {
            this.sex = sex;
        }

        @SEX
        public int getSex() {
            return sex;
        }

        public String getSexDes() {
            if (sex == MALE) {
                return "男";
            } else {
                return "女";
            }
        }
    }

    @IntDef({MALE, FEMALE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SEX {
    }
}
