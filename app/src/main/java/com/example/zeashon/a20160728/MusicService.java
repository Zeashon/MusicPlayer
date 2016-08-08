package com.example.zeashon.a20160728;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Zeashon on 2016/8/2.
 */
public class MusicService extends Service {
    private final String TAG = "MusicService";
    private MediaPlayer mMediaPlayer;


    @Override
    public IBinder onBind(Intent intent) {
        //TODO
       return null;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        releaseMediaPlay();
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        //TODO
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"running");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"onStartCommand running");
        String path = intent.getStringExtra("path");
        playMusic(path);
        return super.onStartCommand(intent, flags, startId);
    }

    /*play music*/

    private void playMusic(String path){
        if(mMediaPlayer == null){
            mMediaPlayer = new MediaPlayer();
        }
        mMediaPlayer.stop();//stop play
        mMediaPlayer.reset();// reset
        try{
            mMediaPlayer.setDataSource(path);//set music source
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                //异步启动成功  回调接口
                @Override
                public void onPrepared(MediaPlayer mp) {
                    //MediaPlayer 装载成功
                 /*   发送当前音乐时间长度给UI*/
                    if(!isTaskPlay){
                        isTaskPlay = true;
                        musicTask.start();
                    }
                    mMediaPlayer.start();//paly
                }
            });
            mMediaPlayer.prepareAsync();//异步启动
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*停止并释放MediaPlayer*/
    private void releaseMediaPlay(){
        if (mMediaPlayer != null){
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
        mMediaPlayer = null;
        if(musicTask != null){
            musicTask.interrupt();
        }
        isTaskPlay = false;
    }

    private boolean isTaskPlay;
    private Thread musicTask = new Thread(){
        //进度查询线程 1S 查询一次

        @Override
        public void run() {
            while(!isInterrupted()){
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                    break;
                }
                int progress = mMediaPlayer.getCurrentPosition();//获取当前播放进度
                //通知UI更新
            }
        }
    };
}
