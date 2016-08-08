package com.example.zeashon.a20160728;

import android.content.Context;
import android.media.MediaRouter;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.List;

/**
 * Created by Zeashon on 2016/7/30.
 */
public class MusicAdapter extends BaseAdapter {
    private Context context;
    private List<MusicInfo> list;
    private String TAG = "MusicAdapter";

    public MusicAdapter(Context context, List<MusicInfo> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {//指定数据源长度
        return list.size();
    }

    @Override
    public Object getItem(int position) {//指定位置的数据
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Hold hold;
        if (convertView == null) {//子项布局为空（当前内存中没有缓冲）
            Log.d("train", "convertView == null");
            convertView = LayoutInflater.from(context).inflate(R.layout.music_list_item, parent, false);
            convertView = View.inflate(context, R.layout.music_list_item, null);//指定子项使用的布局
            hold = new Hold();//建立子项布局中的控件对应关系
            hold.musicNo = (TextView) convertView.findViewById(R.id.music_no);
            hold.name = (TextView) convertView.findViewById(R.id.musicname);
            hold.player = (TextView) convertView.findViewById(R.id.player);

            convertView.setTag(hold);//将“子项布局中的控件对应关系”保存到子项控件中，在复用时直接取出使用
        } else {//当前缓存中存在子项布局（已经出屏的子项布局）
            Log.d("train", "reuse");
            hold = (Hold) convertView.getTag();//取出之前缓存的“子项布局中的控件对应关系”
        }
        /*对子项布局中的控件填充数据*/
        MusicInfo musicInfo = list.get(position);
        hold.musicNo.setText(position + 1 + "");
        hold.name.setText(musicInfo.getMusicName());
        hold.player.setText(musicInfo.getPlayer());
        Log.e(TAG, "set text");
        return convertView;
    }

    /**
     * “子项布局中的控件对应关系”
     */
    private class Hold {
        TextView name;
        TextView player;
        TextView musicNo;
    }
}
