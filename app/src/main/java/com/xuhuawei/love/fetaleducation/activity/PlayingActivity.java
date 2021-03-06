package com.xuhuawei.love.fetaleducation.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xhwbaselibrary.base.BaseActivity;

import com.xhwbaselibrary.persistence.MySharedManger;
import com.xhwbaselibrary.utils.DateUtils;
import com.xhwbaselibrary.utils.ToastUtil;
import com.xuhuawei.love.fetaleducation.R;
import com.xuhuawei.love.fetaleducation.bean.MainBean;
import com.xuhuawei.love.fetaleducation.bean.PlayingAudioBean;
import com.xuhuawei.love.fetaleducation.bean.TimerBean;
import com.xuhuawei.love.fetaleducation.config.SingleCacheData;
import com.xuhuawei.love.fetaleducation.dialog.SelectItemMenuDialog;
import com.xuhuawei.love.fetaleducation.dialog.SelectCircleDialog;
import com.xuhuawei.love.fetaleducation.enums.CircleType;
import com.xuhuawei.love.fetaleducation.enums.TimerType;
import com.xuhuawei.love.fetaleducation.player.MyPlayerService;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import static com.xuhuawei.love.fetaleducation.config.EventBusTag.ACTION_ALARM_TIMER_UI_UPDATE;
import static com.xuhuawei.love.fetaleducation.config.EventBusTag.ACTION_VIEWHOLDER_TIMER;
import static com.xuhuawei.love.fetaleducation.config.EventBusTag.TAG_PLAY_SERVICE_PAUSE_OR_START;
import static com.xuhuawei.love.fetaleducation.config.EventBusTag.TAG_PLAY_SERVICE_SEEK;
import static com.xuhuawei.love.fetaleducation.config.EventBusTag.TAG_PLAY_UI_BUFFER;
import static com.xuhuawei.love.fetaleducation.config.EventBusTag.TAG_PLAY_UI_COMPLETION;
import static com.xuhuawei.love.fetaleducation.config.EventBusTag.TAG_PLAY_UI_ERROR;
import static com.xuhuawei.love.fetaleducation.config.EventBusTag.TAG_PLAY_UI_PREPARE;
import static com.xuhuawei.love.fetaleducation.config.EventBusTag.TAG_PLAY_UI_PROGRESS;
import static com.xuhuawei.love.fetaleducation.config.EventBusTag.TAG_PLAY_UI_SEEK_COMPLETION;
import static com.xuhuawei.love.fetaleducation.config.EventBusTag.TAG_PLAY_UI_STARTOR_PAUSE;
import static com.xuhuawei.love.fetaleducation.config.EventBusTag.TAG_PLAY_UI_START_NEW_MUSIC;
import static com.xuhuawei.love.fetaleducation.config.ShareConfig.SHARED_KEY_LAST_AUDIO;

public class PlayingActivity extends BaseActivity {

    private PlayingAudioBean bean;

    private TextView text_title;
    private SeekBar mSeekBar;

    private TextView text_currentTime;
    private TextView text_totalTime;

    //    private FileBean fileBean;
    private String url;

    private ImageView ivLast;
    private ImageView ivNext;
    private ImageView ivPlayOrPause;
    private TextView text_timer;
    private View btn_list;
    private View btn_menu;
    private SelectItemMenuDialog dialog;
    private boolean isDowned = false;

    @Override
    protected void init() {

    }

    @Override
    protected int setContentView() {
        return R.layout.activity_playing;
    }

    @Override
    protected void findViewByIds() {
        ivLast = findMyViewById(R.id.ivLast);
        ivPlayOrPause = findMyViewById(R.id.ivPlayOrPause);
        ivNext = findMyViewById(R.id.ivNext);

        ivLast.setOnClickListener(onClickListener);
        ivPlayOrPause.setOnClickListener(onClickListener);
        ivNext.setOnClickListener(onClickListener);

        mSeekBar = findMyViewById(R.id.musicSeekBar);
        text_title = findMyViewById(R.id.text_title);
        text_timer = findMyViewById(R.id.text_timer);
        text_currentTime = findMyViewById(R.id.text_currentTime);
        text_totalTime = findMyViewById(R.id.text_totalTime);

        View btn_back = findMyViewById(R.id.btn_back);
        btn_back.setOnClickListener(onClickListener);

        View btn_share = findMyViewById(R.id.btn_share);
        btn_share.setOnClickListener(onClickListener);

        btn_list = findMyViewById(R.id.btn_list);
        btn_menu = findMyViewById(R.id.btn_menu);
        btn_list.setOnClickListener(onClickListener);
        btn_menu.setOnClickListener(onClickListener);

        mSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    @Override
    protected void requestService() {
        EventBus.getDefault().register(this);
        boolean isPlaying = SingleCacheData.getInstance().isPlayingAudio();
        if (!isPlaying) {
            ivPlayOrPause.setImageResource(R.drawable.ic_play);
        } else {
            ivPlayOrPause.setImageResource(R.drawable.ic_pause);
        }
        bean = SingleCacheData.getInstance().getCurrentPlayBean();
        text_totalTime.setText(DateUtils.duration2TimeByMicSecond(bean.totalTime));
        text_title.setText(bean.itemId);
    }

    @Override
    protected void onMyDestory() {
        EventBus.getDefault().unregister(this);
    }

    /**
     * 播放下一个音频
     *
     * @param bean
     */
    @Subscriber(tag = TAG_PLAY_UI_START_NEW_MUSIC)
    private void onPlayNewMusic(PlayingAudioBean bean) {
        text_title.setText(bean.itemId);
    }

    /**
     * 定时
     *
     * @param leftTimer
     */
    @Subscriber(tag = ACTION_ALARM_TIMER_UI_UPDATE)
    private void onAlarmTimerUpate(int leftTimer) {
        if (leftTimer <= 0) {
            text_timer.setVisibility(View.GONE);
        } else {
            text_timer.setVisibility(View.VISIBLE);
            text_timer.setText(DateUtils.duration2TimeBySecond(leftTimer));
        }
    }

    /**
     * 暂停状态
     *
     * @param isStart
     */
    @Subscriber(tag = TAG_PLAY_UI_STARTOR_PAUSE)
    private void onUIPauseOrStart(boolean isStart) {
        if (isStart) {
            ivPlayOrPause.setImageResource(R.drawable.ic_play);
        } else {
            ivPlayOrPause.setImageResource(R.drawable.ic_pause);
        }
    }

    /**
     * 更新进度
     *
     * @param currentPosition
     */
    @Subscriber(tag = TAG_PLAY_UI_PROGRESS)
    private void onUpdateProgress(int currentPosition) {
        mSeekBar.setMax(bean.totalTime);
        mSeekBar.setProgress(currentPosition);
        text_currentTime.setText(DateUtils.duration2TimeByMicSecond(currentPosition));
    }

    /**
     * 播放错误
     *
     * @param bean
     */
    @Subscriber(tag = TAG_PLAY_UI_ERROR)
    private void onError(PlayingAudioBean bean) {
        mSeekBar.setMax(bean.totalTime);
        text_totalTime.setText(DateUtils.duration2TimeByMicSecond(bean.totalTime));
        resetStatus();
    }

    /**
     * 开始缓存
     *
     * @param bean
     */
    @Subscriber(tag = TAG_PLAY_UI_BUFFER)
    private void onBufferingUpdate(PlayingAudioBean bean) {
        mSeekBar.setMax(bean.totalTime);
        text_totalTime.setText(DateUtils.duration2TimeByMicSecond(bean.totalTime));
    }

    /**
     * 拖动完成
     *
     * @param bean
     */
    @Subscriber(tag = TAG_PLAY_UI_SEEK_COMPLETION)
    private void onSeekComplete(PlayingAudioBean bean) {
        mSeekBar.setMax(bean.totalTime);
        text_totalTime.setText(DateUtils.duration2TimeByMicSecond(bean.totalTime));
    }

    /**
     * 播放完成
     *
     * @param bean
     */
    @Subscriber(tag = TAG_PLAY_UI_COMPLETION)
    private void onCompletion(PlayingAudioBean bean) {
        mSeekBar.setMax(bean.totalTime);
        text_totalTime.setText(DateUtils.duration2TimeByMicSecond(bean.totalTime));
        resetStatus();
    }

    /**
     * 播放准备好了
     *
     * @param bean
     */
    @Subscriber(tag = TAG_PLAY_UI_PREPARE)
    private void onPrepared(PlayingAudioBean bean) {
//        MyPlayerService.startTimer(TimerType.TIMER_30);
        mSeekBar.setMax(bean.totalTime);
        text_totalTime.setText(DateUtils.duration2TimeByMicSecond(bean.totalTime));
    }

    /**
     * 选择定时器了
     *
     * @param bean
     */
    @Subscriber(tag = ACTION_VIEWHOLDER_TIMER)
    private void onSetTimer(TimerBean bean) {
        if (bean.timerType == TimerType.TIMER_CANCEL) {
            text_timer.setVisibility(View.GONE);
        } else {
            text_timer.setVisibility(View.VISIBLE);
        }
    }


    private void resetStatus() {
        mSeekBar.setProgress(0);
        ivPlayOrPause.setImageResource(R.drawable.ic_play);
        text_currentTime.setText(DateUtils.duration2TimeByMicSecond(0));
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivPlayOrPause:
                    if (MyPlayerService.isRunningPlayingService()) {
                        EventBus.getDefault().post("", TAG_PLAY_SERVICE_PAUSE_OR_START);
                    } else {
                        String lastAudio = MySharedManger.getInstance().getStringValue(SHARED_KEY_LAST_AUDIO);
                        if (!TextUtils.isEmpty(lastAudio)) {
                            MainBean bean = new MainBean(lastAudio);
                            //播放制定的音频
                            MyPlayerService.startPlay(bean);
                            EventBus.getDefault().post(false, TAG_PLAY_UI_STARTOR_PAUSE);
                        }
                    }
                    break;
                case R.id.ivNext:
                    MyPlayerService.startPlayNext();
                    break;
                case R.id.ivLast:
                    MyPlayerService.startPlayLast();
                    break;
                case R.id.btn_list:
                    ToastUtil.showActionResult("暂不支持，后续会支持！", true);
//                    Intent intent = new Intent(PlayingActivity.this, HomePageActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                    finish();
                    break;
                case R.id.btn_menu:
                    if (dialog == null) {
                        dialog = new SelectItemMenuDialog(PlayingActivity.this);
                        dialog.setOnDialogItemClick(dialogItemClick);
                    }
                    dialog.showDialog(isDowned);
                    break;
                case R.id.btn_back:
                    finish();
                    break;
                case R.id.btn_share:

                    break;

            }
        }
    };

    /**
     * 拖动进度条的回调
     */
    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            text_currentTime.setText(DateUtils.duration2TimeByMicSecond(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            EventBus.getDefault().post(seekBar.getProgress(), TAG_PLAY_SERVICE_SEEK);
        }
    };

    private SelectItemMenuDialog.OnDialogItemClick dialogItemClick = new SelectItemMenuDialog.OnDialogItemClick() {
        @Override
        public void onDialogItem(int index) {
            if (index == 2) {
                startActivity(new Intent(PlayingActivity.this, SelectTimerActivity.class));
            } else if (index == 3) {
                SelectCircleDialog dialog = new SelectCircleDialog(PlayingActivity.this);
                dialog.setOnDialogItemClick(selectCircleListener);
                dialog.showDialog();
            } else {
                Toast.makeText(PlayingActivity.this, "暂不支持！", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private SelectCircleDialog.OnDialogItemClick selectCircleListener = new SelectCircleDialog.OnDialogItemClick() {
        @Override
        public void onDialogItem(int index) {
            SingleCacheData.getInstance().setCurrentCircleType(CircleType.getCircleType(index));
        }
    };
}
