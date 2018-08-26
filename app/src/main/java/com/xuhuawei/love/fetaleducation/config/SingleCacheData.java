package com.xuhuawei.love.fetaleducation.config;


import android.text.TextUtils;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.xhwbaselibrary.base.MyEntry;
import com.xhwbaselibrary.persistence.MySharedManger;
import com.xuhuawei.love.fetaleducation.bean.MainBean;
import com.xuhuawei.love.fetaleducation.bean.PlayingAudioBean;
import com.xuhuawei.love.fetaleducation.enums.CircleType;
import com.xuhuawei.love.fetaleducation.enums.TimerType;
import com.xuhuawei.love.fetaleducation.player.MyPlayerApi;
import com.xuhuawei.love.fetaleducation.player.MyPlayerService;

import static com.xuhuawei.love.fetaleducation.config.EventBusTag.TAG_PLAY_UI_START_NEW_MUSIC;
import static com.xuhuawei.love.fetaleducation.config.ShareConfig.SHARED_KEY_LAST_AUDIO;
import static com.xuhuawei.love.fetaleducation.config.ShareConfig.SHARED_KEY_LAST_CIRCLE;

/**
 * Created by lingdian on 17/9/22.
 * 单例的集合
 */

public class SingleCacheData {
    //当前播放的bean
    private PlayingAudioBean currentBean;
    //设置当前的播放列表
    private List<? extends PlayingAudioBean> currentList;
    //定时关闭的类型
    private TimerType currentTimerType;
    //循环的类型
    private CircleType currentCircleType;

    private static SingleCacheData instance = null;

    private SingleCacheData() {
        currentTimerType = TimerType.TIMER_CANCEL;
        int circleType = MySharedManger.getInstance().getIntValue(SHARED_KEY_LAST_CIRCLE);
        currentCircleType= CircleType.getCircleType(circleType);
        currentList = new ArrayList<>();

        String lastAudio = MySharedManger.getInstance().getStringValue(SHARED_KEY_LAST_AUDIO);
        if (!TextUtils.isEmpty(lastAudio)) {
            currentBean = new MainBean(lastAudio);
        }
    }

    public static SingleCacheData getInstance() {
        if (instance == null) {
            instance = new SingleCacheData();
        }
        return instance;
    }

    public boolean isPlayingAudio(){
        return MyPlayerApi.getInstance().isPlaying();
    }
    /**
     * 获取当前定时的类型
     *
     * @return
     */
    public TimerType getCurrentTimerType() {
        return currentTimerType;
    }

    /**
     * 设置定时关闭的类型
     *
     * @param currentTimerType
     */
    public void setCurrentTimerType(TimerType currentTimerType) {
        this.currentTimerType = currentTimerType;
    }
    public void setCurrentCircleType(CircleType currentCircleType) {
        MySharedManger.getInstance().putKeyAndValue(new MyEntry<Integer>(SHARED_KEY_LAST_CIRCLE,currentCircleType.type));
        this.currentCircleType  = currentCircleType;
    }
    /**
     * 获取当前播放的数据bean
     * @return
     */
    public PlayingAudioBean getCurrentPlayBean() {
        return currentBean;
    }

    /**
     * 获取当前的播放列表
     *
     * @return
     */
    public List<? extends PlayingAudioBean> getCurrentList() {
        return currentList;
    }

    /**
     * 清空播放列表
     */
    public void clearCurrentList() {
        if (currentList != null) {
            currentList.clear();
        }
    }

    /**
     * 设置新的播放列表
     *
     * @param currentList
     */
    public void setCurrentList(List<? extends PlayingAudioBean> currentList) {
        this.currentList = currentList;
    }


    /**
     * 播放新的数据bean
     *
     * @param bean
     */
    public void playNewMusic(PlayingAudioBean bean) {
        if (currentBean != null) {
            //如果播放的 id与当前id 不一样 那么就播放
            if (!currentBean.itemId.equals(bean.itemId)||!isPlayingAudio()) {
                //保存更新播放进度
//                PageInfoImple.getInstance().updatePlayProgress();
                currentBean = bean;
                //播放下一个新音乐
                EventBus.getDefault().post(bean, TAG_PLAY_UI_START_NEW_MUSIC);

                String url = bean.filePath;
                //加载数据url
                MyPlayerApi.getInstance().loadUri(bean, url);
            }
        } else {
            currentBean = bean;
            //保存更新播放进度
//            PageInfoImple.getInstance().updatePlayProgress();
            //播放下一个新音乐
            EventBus.getDefault().post(bean, TAG_PLAY_UI_START_NEW_MUSIC);

            String url = bean.filePath;
            //加载数据url
            MyPlayerApi.getInstance().loadUri(bean, url);
        }
    }

    /**
     * 获取下一个 数据bean
     *
     * @return
     */
    public PlayingAudioBean getNextMusic() {
        if (currentList == null || currentList.size() == 0) {
            return null;
        }
        int tempIndex = currentList.indexOf(currentBean);
        int length = currentList.size();

        if (currentCircleType == CircleType.NONE_CIRCLE) {
            return null;
        } else if (currentCircleType == CircleType.SINGLE_CIRCLE) {
            return currentBean;
        } else if (currentCircleType == CircleType.LIST_CIRCLE) {
            int index = 0;
            if (currentBean != null) {
                if (tempIndex == length - 1) {
                    index = 0;
                } else {
                    if (tempIndex < 0) {
                        tempIndex = 0;
                    }
                    tempIndex++;
                    index = tempIndex;
                }
                return (currentList.get(index));
            }
        } else if (currentCircleType == CircleType.LIST_NONE_CIRCLE) {
            int index = 0;
            if (currentBean != null) {
                if (tempIndex == length - 1) {
                    return null;
                } else {
                    if (tempIndex < 0) {
                        tempIndex = 0;
                    }
                    tempIndex++;
                    index = tempIndex;
                }
            }
            return (currentList.get(index));
        } else if (currentCircleType == CircleType.RANDOM_CRICLE) {
            Random random = new Random();
            int index = random.nextInt(length);
            return (currentList.get(index));
        }
        return null;
    }

    /**
     * 获取最后一个数据bean
     *
     * @return
     */
    public PlayingAudioBean getLastMusic() {
        if (currentList != null && currentList.size() > 0) {
            int index = 0;
            if (currentBean != null) {
                int tempIndex = currentList.indexOf(currentBean);
                tempIndex--;
                if (tempIndex > -1) {
                    index = tempIndex;
                }
            }
            return (currentList.get(index));
        }
        return null;
    }
}
