package fetaleducation.xuhuawei.com.fetaleducation.bean;

import java.io.File;
import java.io.Serializable;

import fetaleducation.xuhuawei.com.fetaleducation.utils.FileUtils;

public class MainBean implements Serializable {
    public boolean isSelect=false;
    public String title;
    public String updateTime;
    public File file;

    public MainBean(File file) {
        this.title = file.getName();
        this.file = file;
        this.updateTime = FileUtils.formateDate(file.lastModified());
    }
}
