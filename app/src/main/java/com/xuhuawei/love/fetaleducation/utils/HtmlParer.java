package com.xuhuawei.love.fetaleducation.utils;

import android.text.TextUtils;
import android.util.Log;

import com.lzy.okgo.model.Response;
import com.xuhuawei.love.fetaleducation.bean.ArticleBean;
import com.xuhuawei.love.fetaleducation.bean.StoryBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
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
     *
     * @param response
     * @return
     */
    public static List<StoryBean> dealArticleListResult(Response<String> response) {
        String url = response.getRawCall().request().url().url().toString();
        List<StoryBean> arrayList = new ArrayList<>();
//
        String html = response.body();
        Document documentAll = Jsoup.parse(html);
        Elements elementId = documentAll.getElementsByClass("list-conBox-ul");
//        Element masthead = documentAll.select("div.news-list-ul").first();
        //class等于masthead的div标签
        if (elementId != null && elementId.size() > 0) {
            int length = elementId.size();
            Elements elementsLi1 = elementId.get(0).children();
            int itemSize = elementsLi1.size();
            for (int i = 0; i < itemSize; i++) {
                String link = "";
                String pic = "";
                String title = "";
                String info = "";
                Element elementItem = elementsLi1.get(i);

                Elements itemList = elementItem.children();
                int lengthElements = itemList.size();
                if (lengthElements > 0) {
                    Element aElement = itemList.get(0);
                    link = aElement.attr("href");
                    Elements imageElements = aElement.getElementsByTag("img");
                    if (imageElements != null && imageElements.size() > 0) {
                        Element picElement = imageElements.get(0);
                        pic = picElement.attr("abs:src");
                        title = picElement.attr("alt");
                    }
                    Element infoElement = itemList.get(1);
                    Elements infoList = infoElement.getElementsByClass("info");
                    if (infoList != null && infoList.size() > 0) {
                        info = infoList.get(0).text();
                    }

                    StoryBean bean = new StoryBean(link, pic, title, info);
                    arrayList.add(bean);
                }
            }
        } else {
            Log.e("xhw", "elementId==null" + url);
        }
        return arrayList;
    }



    /**
     * 解析首页的item数据
     *
     * @param response
     * @return
     */
    public static ArticleBean dealArticleDetailResult(Response<String> response) {
        String url = response.getRawCall().request().url().url().toString();
        ArticleBean bean=new ArticleBean();
        String html = response.body();
        Document documentAll = Jsoup.parse(html);

        Elements elementTop = documentAll.getElementsByClass("news-jj-main");
        if (elementTop!=null&&elementTop.size()>0){
            bean.top=elementTop.get(0).text();
        }

        Elements elementArticle = documentAll.getElementsByClass("detail-list item");
        if (elementArticle!=null&&elementArticle.size()>1){
            Element content=elementArticle.get(0);
            Elements elementContent = content.getElementsByClass("detail-box");
            Element contentElement=getListFirstElement(elementContent);
            if (contentElement!=null){
                Elements children=contentElement.children();
                StringBuilder sb=new StringBuilder();
                int length=children.size();
                for (int i = 0; i < length; i++) {
                    Element child =children.get(i);
                    if (length-1==i){
                        Element picElement=getListFirstElement(child.getElementsByTag("img"));
                        String src=picElement.attr("src");
                        bean.contentPic=src;
                    }else{
                        sb.append(child.text());
                    }
                }
                bean.content=sb.toString();
            }

            Element edcation=elementArticle.get(1);
            Element elementSubEducation = getListFirstElement(edcation.getElementsByClass("detail-box"));
            if (elementSubEducation!=null){
                Elements children=elementSubEducation.children();
                StringBuilder sb=new StringBuilder();
                int length=children.size();
                for (int i = 0; i < length; i++) {
                    Element child =children.get(i);
                    Element picElement=getListFirstElement(child.getElementsByTag("img"));
                    if (picElement!=null){
                        String src=picElement.attr("src");
                        bean.educationPic=src;
                    } else{
                        sb.append(child.text());
                    }
                }
                bean.education=sb.toString();
            }
        }

        Log.v("xhw","bean "+bean.toString());
        return bean;
    }

    public static Element getListFirstElement(Elements elements){
        if (elements!=null&&elements.size()>0){
            return elements.get(0);
        }else{
            return null;
        }
    }

}
