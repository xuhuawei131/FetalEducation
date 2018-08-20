package fetaleducation.xuhuawei.com.fetaleducation.files;

import java.io.File;
import java.util.Comparator;

public class FileSortCompare implements Comparator<File> {

    @Override
    public int compare(File o1, File o2) {
        return o1.lastModified()>o2.lastModified()?1:-1;
    }
}
