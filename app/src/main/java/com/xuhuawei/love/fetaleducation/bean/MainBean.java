package com.xuhuawei.love.fetaleducation.bean;

import java.io.File;
import java.io.Serializable;

import com.xuhuawei.love.fetaleducation.utils.FileUtils;

public class MainBean extends PlayingAudioBean implements Serializable {
    public boolean isSelect=false;
    public String title;
    public String updateTime;
    public File file;

    public MainBean(File file) {
        this.title = file.getName();
        this.file = file;
        this.updateTime = FileUtils.formateDate(file.lastModified());
        itemId=title;
        filePath=file.getAbsolutePath();
    }
    public MainBean(String title) {
        this.title = title;
        this.file =new File(FileUtils.getTencentFile(),title) ;
        this.updateTime = FileUtils.formateDate(file.lastModified());
        itemId=title;
        filePath=file.getAbsolutePath();
    }
}
