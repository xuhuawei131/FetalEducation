package com.xuhuawei.love.fetaleducation.adapter.viewholder;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xhwbaselibrary.adapters.viewholders.BaseViewHolder;
import com.xhwbaselibrary.image.RoundedCornersTransformation;
import com.xhwbaselibrary.interfaces.AdapterBehavior;
import com.xuhuawei.love.fetaleducation.R;
import com.xuhuawei.love.fetaleducation.activity.ArticleDetailActivity;
import com.xuhuawei.love.fetaleducation.bean.StoryBean;

import static com.xuhuawei.love.fetaleducation.activity.ArticleDetailActivity.PARAM;

public class AskQuestionViewHolder extends BaseViewHolder<StoryBean> {
    private ImageView iv_article_image;
    private TextView tv_article_title;
    private TextView tv_article_from;
    private RoundedCornersTransformation transformation=null;
    public AskQuestionViewHolder(AdapterBehavior behavior, View itemView) {
        super(behavior, itemView);
    }

    @Override
    protected void findViewByIds() {
        iv_article_image = findViewById(R.id.iv_article_image);
        tv_article_title = findViewById(R.id.tv_article_title);
        tv_article_from = findViewById(R.id.tv_article_from);
         }

    @Override
    protected void onBindView(int position, StoryBean bean) {
        transformation= new RoundedCornersTransformation(getContext(), 10,0, RoundedCornersTransformation.CornerType.BOTTOM);


        tv_article_title.setText(bean.title);
        tv_article_from.setText(bean.info);

        itemView.setOnClickListener(onClickListener);
        Glide.with(getContext())
                .load(bean.pic)
                .asBitmap()
                .skipMemoryCache(true)
                .transform(transformation)
                .placeholder(R.drawable.abc_popup_background_mtrl_mult)
                .error(R.drawable.abc_popup_background_mtrl_mult)
                .into(iv_article_image);

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(getContext(), ArticleDetailActivity.class);
            intent.putExtra(PARAM,bean);
            getContext().startActivity(intent);
        }
    };
}
