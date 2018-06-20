package com.example.liukuo.myapplication;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by LiuKuo at 2018/6/20
 */

public class VideoUtils {
    private static WindowManager windowManager = null;
    private static WindowManager.LayoutParams lp;
    private static MediaPlayer mediaPlayer;
    private static View view;

    public static void showVideo(Context mContext) {
        hideVideo();
        view = getView(mContext);

        windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        lp = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                lp.type = WindowManager.LayoutParams.TYPE_PHONE;
            } else {
                lp.type = WindowManager.LayoutParams.TYPE_TOAST;
            }
        } else {
            lp.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        //不设置的话，video获得焦点导致无法返回桌面
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //不设置的话，默认全屏
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        windowManager.addView(view, lp);
    }

    private static View getImage(Context mContext) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.image_layout, null);
        view.setOnTouchListener(myTouchListener);
        return view;
    }

    @NonNull
    private static View getView(Context mContext) {
        View view =  LayoutInflater.from(mContext).inflate(R.layout.video_layout, null);
        final ImageView iv = view.findViewById(R.id.iv);
        view.setOnTouchListener(myTouchListener);
        SurfaceView surface = view.findViewById(R.id.surface);
        SurfaceHolder holder = surface.getHolder();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setLooping(true);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mediaPlayer.setDisplay(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
                iv.setVisibility(View.GONE);
            }
        });
        try {
            mediaPlayer.setDataSource(mContext, Uri.parse("https://raw.githubusercontent.com/dongzhong/ImageAndVideoStore/master/Bruno%20Mars%20-%20Treasure.mp4"));
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

    private static int x, y;
    public static View.OnTouchListener myTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    lp.x = lp.x + movedX;
                    lp.y = lp.y + movedY;

                    // 更新悬浮窗控件布局
                    windowManager.updateViewLayout(view, lp);
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    /**
     * 释放内存
     */
    public static void hideVideo() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            windowManager.removeViewImmediate(view);
        }
    }
}
