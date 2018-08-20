package fetaleducation.xuhuawei.com.fetaleducation.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import fetaleducation.xuhuawei.com.fetaleducation.files.SongNameFilter;

public class FileUtils {

    public static File getFileDir() {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        return externalStorageDirectory;
    }

    public static File[] getTencentFile() {
        File tencentFile = new File(getFileDir(), "tencent/qqfile_recv/");
        SongNameFilter filter = new SongNameFilter();

        if (tencentFile.exists()) {
            File fileList[] = tencentFile.listFiles(filter);
            return fileList;
        } else {
            Log.v("xhw", "tencentFile not exists");
        }
        return null;
    }


    public static final String formateDate(long milliseconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.getDefault());
        return sdf.format(new Date(milliseconds));
    }
}
