package com.example.zeashon.a20160728;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zeashon on 2016/8/8.
 */
public class MusicDataBasesHelper  extends SQLiteOpenHelper {

    public SQLiteDatabase mdb = null;
    public String CREATE_TABLE = "CREATE TABLE recentPlay ("
            + "_id integer primary key autoincrement,"
            + "name text not null,"
            + "path text not null"
            +")";

    public MusicDataBasesHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);//创建播放记录
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertnewrecord(MusicInfo music){
        ContentValues cv = new ContentValues();
        cv.put("name",music.getMusicName());
        cv.put("path",music.getPath());
        mdb.insert("recentPlay",null,cv);//插入一条播放记录

    }

    /*查询播放记录*/
    public List<MusicInfo> getRecentMusic(){
        List<MusicInfo> mlist = new ArrayList<MusicInfo>();
        Cursor rst = mdb.query("recentPlay",null,null,null,null,null,"_id desc");  //查询播放记录 结果倒序排列
        if(rst == null || rst.getCount() <= 0){
            return  mlist;
        }
        rst.moveToFirst();
        do {
            MusicInfo mMusic = new MusicInfo();
            mMusic.setMusicName(rst.getString(rst.getColumnIndex("name")));//遍历歌名
            mMusic.setPath(rst.getString(rst.getColumnIndex("path")));//遍历歌曲路径
            mlist.add(mMusic);
        }while (rst.moveToNext());
        return mlist;
    }
}
