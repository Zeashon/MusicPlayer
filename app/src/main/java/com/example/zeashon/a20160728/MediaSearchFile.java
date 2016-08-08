package com.example.zeashon.a20160728;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zeashon on 2016/8/8.
 */

/*
* 从系统媒体服务中获取 SD 卡音乐列表
*/

public class MediaSearchFile {
    public static List<MusicInfo> getListofMusic(Context context) {
        List<MusicInfo> musicList = new ArrayList<MusicInfo>();
        ContentResolver cr = context.getContentResolver();
        Cursor rst = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);//从媒体服务中，通过其提供的ContentProvider，获取内部 SD 卡中的音频信息
        if (rst == null || rst.getCount() <= 0) {
            return musicList;
        }
        rst.moveToFirst();
        do {
            MusicInfo mMusic = new MusicInfo();
            mMusic.setPath(rst.getString(rst.getColumnIndex(MediaStore.Audio.Media.DATA)));//歌曲路径
            String name = rst.getString(rst.getColumnIndex(MediaStore.Audio.Media.ARTIST));//歌曲
            String author = rst.getString(rst.getColumnIndex(MediaStore.Audio.Media.ARTIST));//歌手
            try {
                System.out.println("name byte is :" + new String(name.getBytes("utf-8"), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            mMusic.setMusicName(name.toString().startsWith("?") ?
                    rst.getString(rst.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)).split("[.]")[0] :
                    name);//解决乱码问题，如果 TITLE 获取到的是乱码，则获取 DISPLAY_NAME 作为歌曲名
            mMusic.setPlayer(author.toString().startsWith("?") ? "" : author);
            try {
                System.out.println("发现一首歌曲:" + new
                        String(mMusic.getMusicName().getBytes(), "UTF-8") + "-"
                        + mMusic.getPlayer() + "-" + mMusic.getPath());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            musicList.add(mMusic);
        }
        while (rst.moveToNext());
        return musicList;
        
    }
}
