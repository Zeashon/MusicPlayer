package com.example.zeashon.a20160728;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Test02 extends AppCompatActivity {
    private List<MusicInfo> musicList;
    private Button mSearch;
    private Button mPlay;
    private ListView mListView;
    private MusicAdapter mAdapter;
    private String TAG = "Test02";
    MusicDataBasesHelper mMusicDataBasesHelper;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test02);
        intent = this.getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name");
            Toast.makeText(this, "hello " + name, Toast.LENGTH_LONG).show();
        }
        mListView = (ListView) findViewById(R.id.musiclist);
        mSearch = (Button) findViewById(R.id.search_btn);

        mMusicDataBasesHelper = new MusicDataBasesHelper(this, "recent", null, 1);
        mMusicDataBasesHelper.mdb = mMusicDataBasesHelper.getWritableDatabase();

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearch.setVisibility(View.GONE);
                mListView.setVisibility(View.VISIBLE);
                musicList = MediaSearchFile.getListofMusic(Test02.this);
            }
        });

        /*设置数据源*/

//        musicList = scan();//获取扫描到的 SD 卡中的歌曲列表
        musicList = MediaSearchFile.getListofMusic(Test02.this);
        mAdapter = new MusicAdapter(getApplicationContext(), musicList);
//        MusicInfo music = new MusicInfo();
//        music.setMusicName("最近播放");//添加一个分割符
//        musicList.add(music);
//        musicList.addAll(getRecent()); //将最近播放列表也加入到 ListView 中
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("train", "index:" + position);
                Log.d(TAG, parent.getClass().getSimpleName());
                MusicInfo file = (MusicInfo) parent.getAdapter().getItem(position);
                //判断是否是 “最近播放” 分割符，并且抛弃掉路径为空的音乐播放
                if (file.getPath() == null || "".equals(file.getPath())) {
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), MusicService.class);
                intent.putExtra("path", file.getPath());
                Log.e(TAG, "path:" + file.getPath());
                startService(intent);
                //更新播放记录
//                mMusicDataBasesHelper.insertnewrecord(file);
                //重新初始化 ListView 的内容
//                musicList.clear();
//                musicList.addAll(scan());
//                MusicInfo music = new MusicInfo();
//                music.setMusicName("最近播放");
//                musicList.add(music);
//                musicList = MediaSearchFile.getListofMusic(Test02.this);
//                musicList.addAll(getRecent());

                mAdapter.notifyDataSetChanged(); //刷新 UI
            }
        });

        mPlay = (Button) findViewById(R.id.musicplay);
        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*stop music play*/
                Intent intent = new Intent(getApplicationContext(), MusicService.class);
                stopService(intent);
                Log.e(TAG, "music stop");
            }
        });
    }//end of onCreate()

    private List<MusicInfo> scan() {
        List<MusicInfo> list = new ArrayList<MusicInfo>();
        for (int i = 0; i < 100; i++) {
            list.add(new MusicInfo(Environment.getExternalStorageDirectory() + File.separator + "demo.mp3"));
        }
        return list;
    }

    /*
* 从数据库中获取播放记录
*/
    private List<MusicInfo> getRecent() {
        return mMusicDataBasesHelper.getRecentMusic();
    }

}//end of Activity


