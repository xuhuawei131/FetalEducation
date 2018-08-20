package fetaleducation.xuhuawei.com.fetaleducation.player;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;


import java.util.ArrayList;
import java.util.List;

import fetaleducation.xuhuawei.com.fetaleducation.bean.PageInfoDbBean;
import fetaleducation.xuhuawei.com.fetaleducation.config.SwitchConfig;


/**
 * Created by lingdian on 17/9/10.
 * 播放器调用的api
 */

public class MyPlayerApi {
    private static MyPlayerApi myPlayerApi = null;
    /**
     * 目前正在播放的bean
     */
    private PageInfoDbBean currentBean;


    private MediaPlayer player; // 定义多媒体对象
    private MediaCallBack callBack;
    private Context context;
    private List<MediaPlayerCallBack> callBackList;
    private MusicStatus currentStatus = MusicStatus.STOP;

    private MyPlayerApi() {
        player = new MediaPlayer();
        callBackList = new ArrayList<>();
        callBack = new MediaCallBack();
        player.setLooping(SwitchConfig.isLoop);

        player.setOnCompletionListener(callBack);
        player.setOnPreparedListener(callBack);
        player.setOnBufferingUpdateListener(callBack);
        player.setOnErrorListener(callBack);
        player.setOnSeekCompleteListener(callBack);
    }

    public static MyPlayerApi getInstance() {
        if (myPlayerApi == null) {
            myPlayerApi = new MyPlayerApi();
        }
        return myPlayerApi;
    }

    public void init(Context context1) {
        context = context1.getApplicationContext();
    }

    /**
     * 添加播放器监听
     * @param listener
     */
    public void addMediaPlayerListener(MediaPlayerCallBack listener) {
        if (!callBackList.contains(listener)) {
            callBackList.add(listener);
        }
    }

    /**
     * 移除监听
     * @param listener
     */
    public void removeMediaPlayerListener(MediaPlayerCallBack listener) {
        if (callBackList.contains(listener)) {
            callBackList.remove(listener);
        }
    }



    /**
     * 加载音频
     * @param bean
     * @param url
     */
    public void loadUri(PageInfoDbBean bean, String url) {
        this.currentBean =bean;
        try {
            player.reset(); //重置多媒体
            //为多媒体对象设置播放路径
            if (url.startsWith("http")) {
                Uri uri = Uri.parse(url);
                player.setDataSource(context, uri);
            } else {
                player.setDataSource(url);
            }
            player.prepareAsync();//准备播放
            currentStatus = MusicStatus.PLAY;
        } catch (Exception e) {

        }
    }

    /**
     * 暂停与继续播放
     */
    public MusicStatus startOrPause(){
        if (isPlaying()) {
            player.pause();
            currentStatus = MusicStatus.PAUSE;
        } else {
            player.start();
            currentStatus = MusicStatus.PLAY;
        }
        return currentStatus;
    }


    /**
     * 停止播放
     */
    public void stop() {
        if (isPlaying()) {
            player.stop();
            currentStatus= MusicStatus.STOP;
        }
    }

    /**
     * 彻底销毁
     */
    public void destory() {
        stop();
        player.release();
        player = null;
        myPlayerApi = null;
        currentStatus= MusicStatus.STOP;
    }

    /**
     * 是否是播放中
     *
     * @return
     */
    public boolean isPlaying() {
        return player.isPlaying();
    }

    /**
     * 设置是否重复播放
     *
     * @param isLoop 是否循环播放
     */
    public void setLoop(boolean isLoop) {
        player.setLooping(isLoop);
    }

    /**
     *调整进度
     * @param position
     */
    public void seekTo(int position) {
        player.seekTo(position);
    }

    /**
     * 音乐当前的状态：只有播放、暂停、停止三种
     */
    public enum MusicStatus {
        PLAY, PAUSE, STOP
    }

    /**
     * 播放器的状态回调
     */
    private class MediaCallBack implements MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener {
        /**
         * OnCompletionListener
         * 当流媒体播放完毕的时候回调
         *
         * @param mediaPlayer
         */
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            for (MediaPlayerCallBack item : callBackList) {
                item.onCompletion(mediaPlayer, currentBean);
            }
        }

        /**
         * OnBufferingUpdateListener
         *
         * @param mediaPlayer
         * @param percent
         */
        @Override
        public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
            for (MediaPlayerCallBack item : callBackList) {
                item.onBufferingUpdate(mediaPlayer, percent, currentBean);
            }
        }

        /**
         * OnErrorListener
         *
         * @param mediaPlayer
         * @param i
         * @param i1
         * @return
         */
        @Override
        public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
            for (MediaPlayerCallBack item : callBackList) {
                item.onError(mediaPlayer, i, i1, currentBean);
            }
            return false;
        }

        /**
         * OnSeekCompleteListener
         * 当使用seekTo()设置播放位
         *
         * @param mediaPlayer
         */
        @Override
        public void onSeekComplete(MediaPlayer mediaPlayer) {
            for (MediaPlayerCallBack item : callBackList) {
                item.onSeekComplete(mediaPlayer, currentBean);
            }
        }

        /**
         * OnPreparedListener
         * 当装载流媒体完毕的时候回调
         *
         * @param mediaPlayer
         */
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            for (MediaPlayerCallBack item : callBackList) {
                item.onPrepared(mediaPlayer, currentBean);
            }

        }
    }

    /**
     * 开始播放
     */
    public void startPlay(){
        player.start();
    }

    /**
     * 获取当前播放的位置
     * @return
     */
    public int getCurrentPosition(){
        return player.getCurrentPosition();
    }


    public interface MediaPlayerCallBack {
        public void onCompletion(MediaPlayer mediaPlayer, PageInfoDbBean bean);

        public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent, PageInfoDbBean bean);

        public boolean onError(MediaPlayer mediaPlayer, int i, int i1, PageInfoDbBean bean);

        public void onSeekComplete(MediaPlayer mediaPlayer, PageInfoDbBean bean);

        public void onPrepared(MediaPlayer mediaPlayer, PageInfoDbBean bean);
    }

}
