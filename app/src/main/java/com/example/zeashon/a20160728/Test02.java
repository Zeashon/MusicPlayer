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
    private Button mSearch;
    private Button mPlay;
    private ListView mListView;
    private MusicAdapter mAdapter;
    private String TAG = "Zeashon";
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
        List<MusicInfo> list = new ArrayList<MusicInfo>();
        for (int i = 0; i < 20; i++) {//设置数据源
            list.add(new MusicInfo("第" + i + "条", i + ""));
        }
        mListView = (ListView) findViewById(R.id.musiclist);
        mSearch = (Button) findViewById(R.id.search_btn);
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearch.setVisibility(View.GONE);
                mListView.setVisibility(View.VISIBLE);
            }
        });

//        设置数据源
//        mAdapter = new MusicAdapter(getApplicationContext(),list);
//        mListView.setAdapter(mAdapter);
//        设置数据项点击监听
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("train","index:"+position);
//                Log.d(TAG, parent.getClass().getSimpleName());
//                MusicInfo musicInfo = (MusicInfo) parent.getAdapter().getItem(position);
//                Toast.makeText(Test02.this,musicInfo.getMusicName(),Toast.LENGTH_SHORT).show();
//            }
//        });

        /*设置数据源*/


        List<File> musicList = scan();
        mAdapter = new MusicAdapter(getApplicationContext(), musicList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("train", "index:" + position);
                Log.d(TAG, parent.getClass().getSimpleName());
                File file = (File) parent.getAdapter().getItem(position);
                Intent intent = new Intent(getApplicationContext(),MusicService.class);
                intent.putExtra("path",file.getAbsolutePath());
                Log.e(TAG,"path:"+file.getAbsolutePath());
                startService(intent);
            }
        });

        mPlay = (Button) findViewById(R.id.musicplay);
        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*stop music play*/
                Intent intent = new Intent(getApplicationContext(),MusicService.class);
                stopService(intent);
                Log.e(TAG,"music stop");
            }
        });
    }//end of onCreate()

    private List<File> scan() {
        List<File> list = new ArrayList<File>();
        for (int i = 0; i < 100; i++) {
            list.add(new File(Environment.getExternalStorageDirectory() + File.separator + "demo.mp3"));
        }
        return list;
    }

}//end of Activity


