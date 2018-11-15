package com.xuhuawei.love.fetaleducation.utils;

import android.text.TextUtils;
import android.util.Log;

import com.lzy.okgo.model.Response;
import com.xuhuawei.love.fetaleducation.bean.StoryBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lingdian on 17/9/10.
 */

public class HtmlParer {

    /**
     * 获取某个详情页面的下载地址
     *
     * @param html 网页html的所有数据元素
     * @return 获取这个页面 音频的下载地址
     */
    public static String getPageDownFile(String html) {
        String fileUrl = null;
        StringReader readerStr = new StringReader(html);
        BufferedReader reader = new BufferedReader(readerStr);
        String strLine;
        try {
            while ((strLine = reader.readLine()) != null) {
                if (strLine.lastIndexOf(".mp4") != -1) {
                    fileUrl = getDownLoadUrl(strLine);
                    break;
                }
            }
            reader.close();
            return fileUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getDownLoadUrl(String srcStr) {
        if (TextUtils.isEmpty(srcStr)) {
            return null;
        }

        int startIndex = srcStr.indexOf("\'");
        int lastIndex = srcStr.lastIndexOf("\'");

        if (lastIndex > startIndex) {
            return srcStr.substring(startIndex + 1, lastIndex);
        }
        return null;
    }

    /**
     * 解析首页的item数据
     * @param response
     * @return
     */
    public static List<StoryBean> dealFileListResult(Response<String> response) {
        String url=response.raw().request().url().url().toString();
        List<StoryBean> arrayList = new ArrayList<>();
//
        String html = response.body();
        Document documentAll = Jsoup.parse(html);

        Elements elementId = documentAll.getElementsByClass("list-conBox-ul");
        if (elementId != null) {
            Elements elementLiList = elementId;
            int length = elementLiList.size();
            for (int i = 0; i < length; i++) {
                Element elementLi = elementLiList.get(i);

                Elements elementsLi = elementLi.children();

                Element elementA = elementsLi.get(0);
                Element elementSpan = elementsLi.get(1);

                StoryBean bean = new StoryBean();
//
                String link = elementA.attr("href");
                String title = elementA.text();
                String date = elementSpan.text();

                if(TextUtils.isEmpty(title)||!title.contains("_")){
                    continue;
                }


//                bean.link = link;
//                bean.title = title;
//
//                bean.title = title;
//                bean.itemId=MyUtils.getStrMD5(title);
//                bean.link = link;
//                bean.date = date;
//
                arrayList.add(bean);
            }
        } else {
            Log.e("xhw","elementId==null"+url);
        }
        return arrayList;
    }
}
