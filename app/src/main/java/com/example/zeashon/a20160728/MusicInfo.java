package com.example.zeashon.a20160728;

/**
 * Created by Zeashon on 2016/7/30.
 */
public class MusicInfo {
    private String MusicName;
    private String Player;

    public MusicInfo(String MusicName, String Player)
    {
        super();
        this.MusicName = MusicName;
        this.Player = Player;
    }
    public String getMusicName(){
        return MusicName;
    }
    public String getPlayer(){
        return Player;
    }
    public void setMusicName(String MusicName){
        this.MusicName = MusicName;
    }
    public void setPlayer(String Player){ this.Player = Player;}
}
