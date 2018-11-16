package com.xuhuawei.love.fetaleducation.bean;

import android.os.Parcel;
import android.os.Parcelable;


public class StoryBean implements Parcelable {
    public String link;
    public String pic;
    public String title;
    public String info;

    public StoryBean(String link, String pic, String title, String info) {
        this.link = link;
        this.pic = pic;
        this.title = title;
        this.info = info;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.link);
        dest.writeString(this.pic);
        dest.writeString(this.title);
        dest.writeString(this.info);
    }

    protected StoryBean(Parcel in) {
        this.link = in.readString();
        this.pic = in.readString();
        this.title = in.readString();
        this.info = in.readString();
    }

    public static final Parcelable.Creator<StoryBean> CREATOR = new Parcelable.Creator<StoryBean>() {
        @Override
        public StoryBean createFromParcel(Parcel source) {
            return new StoryBean(source);
        }

        @Override
        public StoryBean[] newArray(int size) {
            return new StoryBean[size];
        }
    };
}
