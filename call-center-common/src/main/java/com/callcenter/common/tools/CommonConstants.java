package com.callcenter.common.tools;

/**
 * 公共常量类
 * User: wangyueinfo
 * Date: 14-3-5
 * Time: 上午11:38
 */

public class CommonConstants {

    /**
     * 任务日志同步状态
     */
    public  static  final int SYNC_STATUS_1 = 1;//初始化
    public  static  final int SYNC_STATUS_2 = 2;//未同步
    public  static  final int SYNC_STATUS_3 = 3;//同步中
    public  static  final int SYNC_STATUS_4 = 4;//同步成功
    public  static  final int SYNC_STATUS_5 = 6;//同步失败
    public  static  final int SYNC_STATUS_6 = 6;//已经转历史，转历史就代表该数据已经不存在于该表中


    /**
     * 任务类型
     */
    public static final String TASK_TYPE_JOB = "JOB";  //任务类型-job
    public static final String TASK_TYPE_MQ = "MQ";  //任务类型-mq

    /**
     * 同步类型
     */
    public static final String SYNC_TYPE_CYCLE = "CYCLE";  //同步类型-循环
    public static final String SYNC_TYPE_SINGLE = "SINGLE";  //同步类型-只执行一次

    /**
     * 删除标示
     */
    public static  final  int YN_DELETE=0;   //删除
    public static  final  int YN_NOT_DELETE=1; //不删除，即有效

    /**
     * 最大失败次数
     */
    public static final int MAX_FAIL_COUNT = 5;

    /**
     * 字符串分割
     */
    public static final  String SPLIT_PARAMETER_FALG_1 = "_#_";  //_#_用于分割多个参数
    public static final  String SPLIT_PARAMETER_FALG_2 = "#_#";  //#_#用于分割参数中类名和参数Json串

    public static final int DATA_MAX_COUNT = 1990;
    public static final String ZIP_COMPRESS = "TRUE";
    public static final String ZIP_UNCOMPRESS = "TRUE";

    public static final  int POOL_SIZE  = 10; //线程池大小

    public static final int DIFFER_MILLI_SECONDS = 15; //系统误差时间15秒

    public static  final long HEART_BEAT_TIME = 3*60*1000; //3分钟

    public static final String IS_START_ALARM = "isStartAlarm"; //是否启动报警按钮 Y（是）：启动，N（否）：关闭

    /**
     * 是否已停止
     */
    public static  final String IS_STOP = "0";

    /**
     * 是否已启动
     */
    public  static final String IS_START ="1";

    public  static final String TIMER_TASK_FILE_PATH = "/conf/timer-task.properties";
    /**
     * 动态设置定时任务
     */
    public  static final String DYNAMIC_SETTING_TIMER_TASK = "dynamic setting timer task";

}
