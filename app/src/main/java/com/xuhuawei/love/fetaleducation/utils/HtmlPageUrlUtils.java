package com.xuhuawei.love.fetaleducation.utils;

/**
 * Created by lingdian on 17/9/14.
 */

public class HtmlPageUrlUtils {
    private static final String homeUrl1 = "http://www.qbaobei.com/jiaoyu/tj/tjgs/List_%d.html";
    private static final String homeUrl = "http://www.qbaobei.com/jiaoyu/tj/tjgs/";


    private static final String homeCSUrl1 = "http://www.qbaobei.com/jiaoyu/tj/tjcs/List_%d.html";
    private static final String homeCSUrl = "http://www.qbaobei.com/jiaoyu/tj/tjcs/";

    public static String getPageUrlByIndex(int pageIndex) {
        String url = "";
        if (pageIndex <2) {
            url = homeUrl;
        } else {
            url = String.format(homeUrl1, pageIndex);
        }
        return url;
    }

    public static String getCSPageUrlByIndex(int pageIndex) {
        String url = "";
        if (pageIndex <2) {
            url = homeCSUrl;
        } else {
            url = String.format(homeCSUrl1, pageIndex);
        }
        return url;
    }
}
