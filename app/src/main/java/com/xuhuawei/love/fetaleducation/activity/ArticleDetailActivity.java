package com.xuhuawei.love.fetaleducation.activity;


import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.xhwbaselibrary.base.BaseActivity;
import com.xuhuawei.love.fetaleducation.R;
import com.xuhuawei.love.fetaleducation.bean.ArticleBean;
import com.xuhuawei.love.fetaleducation.bean.StoryBean;
import com.xuhuawei.love.fetaleducation.parsers.ParserStringCallBack;
import com.xuhuawei.love.fetaleducation.utils.HtmlPageUrlUtils;
import com.xuhuawei.love.fetaleducation.utils.HtmlParer;

import java.util.List;

public class ArticleDetailActivity extends BaseActivity {
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;


    private ImageView image_bg;
    private TextView text_top;
    private TextView text_navi_1;
    private TextView text_navi_2;
    private TextView text_story;
    private ImageView image_content;
    private TextView text_education;
    private ImageView image_education;

    private StoryBean bean;
    public static final String PARAM = "param";

    @Override
    protected void init() {
        bean = this.getIntent().getParcelableExtra(PARAM);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_article_detail;
    }

    @Override
    protected void findViewByIds() {
        image_bg = findViewById(R.id.image_bg);
        text_top = findViewById(R.id.text_top);
        text_navi_1 = findViewById(R.id.text_navi_1);
        text_navi_2 = findViewById(R.id.text_navi_2);

        text_story = findViewById(R.id.text_story);
        image_content = findViewById(R.id.image_content);
        image_education = findViewById(R.id.image_education);
        text_education = findViewById(R.id.text_education);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        collapsingToolbar =
                findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(bean.title);


        text_navi_1.setText(bean.title);
        text_navi_2.setText(bean.title+"故事点评");
    }

    @Override
    protected void requestService() {
        Glide.with(getContext())
                .load(bean.pic)
                .placeholder(R.drawable.abc_popup_background_mtrl_mult)
                .error(R.drawable.abc_popup_background_mtrl_mult)
                .into(image_bg);

        OkGo.<String>get(bean.link)
                .headers("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows 7)")
                .tag(this).execute(new ParserStringCallBack<ArticleBean>() {
            @Override
            public void onStart(Request<String, ? extends Request> request) {
                super.onStart(request);
                showProgressDialog();
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                disProgressDialog();
            }

            @Override
            public ArticleBean parserJson(Response<String> response) {
                ArticleBean bean = HtmlParer.dealArticleDetailResult(response);
                return bean;
            }

            @Override
            public void onResultComing(ArticleBean response) {
                disProgressDialog();
                text_top.setText(response.top);

                text_story.setText(response.content);
                text_education.setText(response.education);

                Glide.with(getContext())
                        .load(response.contentPic)
                        .placeholder(R.drawable.abc_popup_background_mtrl_mult)
                        .error(R.drawable.abc_popup_background_mtrl_mult)
                        .into(image_content);


                Glide.with(getContext())
                        .load(response.educationPic)
                        .placeholder(R.drawable.abc_popup_background_mtrl_mult)
                        .error(R.drawable.abc_popup_background_mtrl_mult)
                        .into(image_education);

            }
        });

    }

    @Override
    protected void onMyDestory() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
