package com.xuhuawei.love.fetaleducation.bean;

import java.io.Serializable;

public class PlayingAudioBean implements Serializable{
    public String itemId;
    public String filePath;
    public int totalTime;
    public boolean isPlaying;
    public int buffet_percent;
    public int currentTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayingAudioBean that = (PlayingAudioBean) o;

        return itemId != null ? itemId.equals(that.itemId) : that.itemId == null;
    }

    @Override
    public int hashCode() {
        return itemId != null ? itemId.hashCode() : 0;
    }
}
