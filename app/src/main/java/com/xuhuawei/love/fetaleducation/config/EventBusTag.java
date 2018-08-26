package com.xuhuawei.love.fetaleducation.config;

/**
 * Created by lingdian on 17/9/16.
 */

public class EventBusTag {
    public static final String TAG_DOWNLOADING_UPDATE="progress_download";
    public static final String TAG_DOWNLOADING_START="start_download";
    public static final String TAG_DOWNLOADING_DONE="finish_download";
    public static final String TAG_DOWNLOADING_DELETE="delete_download";
    public static final String TAG_DOWNLOADING_ERROR="error_download";
    public static final String TAG_DOWNLOADING_ITEM_CLICK="item_click";
    public static final String TAG_DOWNLOADING_ADD="add_download";
    public static final String TAG_HOME_ITEM_CLICK="home_item";



    /**播放 拖动进度**/
    public static final String TAG_PLAY_SERVICE_SEEK ="play_seek";
    /**播放准备好了**/
    public static final String TAG_PLAY_SERVICE_PAUSE_OR_START ="play_pause_start";

    public static final String TAG_PLAY_SERVICE_TIMER="play_service_timer";



    public static final String TAG_PLAY_UI_STARTOR_PAUSE="play_ui_start_pause";
    /**播放音新的频**/
    public static final String TAG_PLAY_UI_START_NEW_AUDIO="play_ui_new_audio";
    /**播放进度**/
    public static final String TAG_PLAY_UI_PROGRESS ="play_progress";
    /**播放准备好了**/
    public static final String TAG_PLAY_UI_PREPARE ="play_prepare";
    /**播放缓冲**/
    public static final String TAG_PLAY_UI_BUFFER ="play_buffer";

    public static final String TAG_PLAY_UI_ERROR ="play_error";

    public static final String TAG_PLAY_UI_COMPLETION ="play_completion";

    public static final String TAG_PLAY_UI_SEEK_COMPLETION ="play_seek_completion";

    /**播放新的音乐 更新ui**/
    public static final String TAG_PLAY_UI_START_NEW_MUSIC ="play_new_music";




    public static final String ACTION_ALARM_TIMER_UI_UPDATE ="com.xuhuawei.alarm.progress";


    public static final String ACTION_VIEWHOLDER_TIMER="COM.VIEWHOLDER.TIMER";

    public static final String SERVICE_ACTION_TIMER_ALARM ="com.xuhuawei.alarm";

    public static final String SERVICE_ACTION_TIMER_ADD ="action_service_timer";

    public static final String SERVICE_ACTION_PLAYER="action_service_player";

    //----------------------退出的广播-------------------------

    public static final String ACTION_EXIT_ALL_LIFE="exit_all_life";

}
