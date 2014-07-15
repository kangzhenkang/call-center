package com.callcenter.service;

import com.callcenter.common.Result;

/**
 * 任务调度控制接口
 * User: wangyueinfo
 * DateTime: 12-3-21 上午10:50
 *  Version: 1.0
 */
public interface TimerTaskService {


    /**
     * 启动所有的时间程序
     *
     * @return
     */
    public Result doStartAll();

    /**
     * 停止所有时间程序
     *
     * @return
     */
    public Result doShutDownAll();

    /**
     * 暂停所有时间程序
     *
     * @return
     */
    public Result doPauseAll();

    /**
     * 从暂停中重新启动所有时间程序
     *
     * @return
     */
    public Result doResumeAll();

    /**
     * 判断当前的任务调度器是否启动
     *
     * @return
     */
    public Result isStarted();

    /**
     * 监控心跳检测和报警Job调度
     *
     * @param status 任务启动状态
     * @return Result 对象
     */
    public Result setHeartBeatAndAlarmSchedulerStatus(String status);

    /**
     * 同步任务Job调度
     *
     * @param status 任务启动状态
     * @return Result 对象
     */
    public Result setSyncTaskSchedulerStatus(String status);


}
