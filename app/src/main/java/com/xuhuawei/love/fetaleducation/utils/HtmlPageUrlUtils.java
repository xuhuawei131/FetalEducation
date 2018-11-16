package com.xuhuawei.love.fetaleducation.utils;

/**
 * Created by lingdian on 17/9/14.
 */

public class HtmlPageUrlUtils {
    private static final String homeUrl1 = "http://www.qbaobei.com/jiaoyu/tj/tjgs/List_%d.html";
    private static final String homeUrl = "http://www.qbaobei.com/jiaoyu/tj/tjgs/";

    public static String getPageUrlByIndex(int pageIndex) {
        String url = "";
        if (pageIndex <2) {
            url = homeUrl;
        } else {
            url = String.format(homeUrl1, pageIndex);
        }
        return url;
    }
}
