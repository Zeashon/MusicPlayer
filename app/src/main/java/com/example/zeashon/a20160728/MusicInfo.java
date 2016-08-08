package com.example.zeashon.a20160728;

/**
 * Created by Zeashon on 2016/7/30.
 */
public class MusicInfo {
    private String MusicName;//歌名
    private String Player;//歌手
    private String Path; //音乐文件绝对路径

    //    public MusicInfo(String MusicName, String Player)
//    {
//        super();
//        this.MusicName = MusicName;
//        this.Player = Player;
//    }
    public MusicInfo(String path) {
        this.setPath(path);
    }

    public MusicInfo() {
        super();
    }

    public String getMusicName() {
        return MusicName;
    }

    public String getPlayer() {
        return Player;
    }

    public String getPath() {
        return Path;
    }

    public void setMusicName(String MusicName) {
        this.MusicName = MusicName;
    }

    public void setPlayer(String Player) {
        this.Player = Player;
    }

    public void setPath(String Path) {
        this.Path = Path;
    }
}
