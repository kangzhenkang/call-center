package com.callcenter.service.impl;


import com.callcenter.common.Result;
import com.callcenter.common.tools.CommonConstants;
import com.callcenter.service.TimerTaskService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 任务调度控制业务类
 * User: wangyueinfo
 * DateTime: 12-3-21 上午10:50
 * Version: 1.0
 */
@Service("timerTaskService")
public class TimerTaskServiceImpl implements TimerTaskService {
    private final static Log log = LogFactory.getLog(TimerTaskServiceImpl.class);

    // 注入Job需要的类

    @Resource
    private Scheduler heartBeatAndAlarmScheduler;           // 同步任务Job调度
    @Resource
    private Scheduler syncTaskScheduler;           // 监控心跳检测和报警Job调度


    /**
     * 启动所有的时间程序
     *
     * @return
     */
    public Result doStartAll() {
        Result result = new Result();
        try {
            heartBeatAndAlarmScheduler.start();                //  启动同步任务Job调度
            syncTaskScheduler.start();                         // 启动监控心跳检测和报警Job调度

            result.addDefaultModel("heartBeatAndAlarmScheduler", CommonConstants.IS_START);      // 记录启动状态
            result.addDefaultModel("syncTaskScheduler", CommonConstants.IS_START);      // 记录启动状态

            result.addDefaultModel("startAll", CommonConstants.IS_START);                     // 记录已全部启动
            result.setSuccess(true);
            result.setResultCode("system.timetask.execute.success");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResultCode("system.timetask.execute.error");
            log.error("Method:TimerTaskServiceImpl-->doStartAll()--->error", e);
        }
        return result;
    }

    /**
     * 停止所有时间程序
     *
     * @return
     */
    public Result doShutDownAll() {
        Result result = new Result();
        try {
            heartBeatAndAlarmScheduler.standby();  // 停止同步任务Job调度
            syncTaskScheduler.standby();           // 停止监控心跳检测和报警Job调度
            result.addDefaultModel("heartBeatAndAlarmScheduler", CommonConstants.IS_STOP);   // 记录停止状态
            result.addDefaultModel("syncTaskScheduler", CommonConstants.IS_STOP);   // 记录停止状态
            result.addDefaultModel("stopAll", CommonConstants.IS_STOP);                     // 记录已全部停止
            result.setSuccess(true);
            result.setResultCode("system.timetask.execute.success");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResultCode("system.timertask.execute.error");
            log.error("Method:TimerTaskServiceImpl-->doShutDownAll()--->error", e);
        }
        return result;
    }

    /**
     * 暂停所有时间程序
     *
     * @return
     */
    public Result doPauseAll() {
        Result result = new Result();
        try {
            heartBeatAndAlarmScheduler.pauseAll();              // 暂停同步任务Job调度
            syncTaskScheduler.pauseAll();              // 暂停监控心跳检测和报警Job调度
            result.addDefaultModel("heartBeatAndAlarmScheduler", CommonConstants.IS_STOP);
            result.addDefaultModel("syncTaskScheduler", CommonConstants.IS_STOP);
            result.addDefaultModel("stopAll", CommonConstants.IS_STOP);                     // 记录已全部停止
            result.setSuccess(true);
            result.setResultCode("system.timetask.execute.success");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResultCode("system.timertask.execute.error");
            log.error("Method:TimeTaskServiceImpl-->doPauseAll()--->error", e);
        }
        return result;
    }

    /**
     * 从暂停中重新启动所有时间程序
     *
     * @return
     */
    public Result doResumeAll() {
        return null;
    }

    /**
     * 判断当前的任务调度器是否启动
     *
     * @return
     */
    public Result isStarted() {
        Result result = new Result();
        try {
            //监控心跳检测和报警Job调度
            if (heartBeatAndAlarmScheduler.isStarted()) {
                result.addDefaultModel("heartBeatAndAlarmScheduler", CommonConstants.IS_START);          // 启动状态记录
                if (heartBeatAndAlarmScheduler.isInStandbyMode()) {
                    result.addDefaultModel("heartBeatAndAlarmScheduler", CommonConstants.IS_STOP);      // 停止状态记录
                }
            } else {
                result.addDefaultModel("heartBeatAndAlarmScheduler", CommonConstants.IS_STOP);          // 停止状态记录
            }
            // 同步任务Job调度
            if (syncTaskScheduler.isStarted()) {
                result.addDefaultModel("syncTaskScheduler", CommonConstants.IS_START);          // 启动状态记录
                if (syncTaskScheduler.isInStandbyMode()) {
                    result.addDefaultModel("syncTaskScheduler", CommonConstants.IS_STOP);      // 停止状态记录
                }
            } else {
                result.addDefaultModel("syncTaskScheduler", CommonConstants.IS_STOP);          // 停止状态记录
            }

        } catch (SchedulerException e) {
            result.setSuccess(false);
            result.setResultCode("system.timetask.execute.error");
            log.error("Method:TimeTaskServiceImpl!isStarted--->error", e);
         }
        return result;
    }

    public Result setHeartBeatAndAlarmSchedulerStatus(String status) {
        Result result = new Result();
        try {
            if (status.equals("start")) {
                heartBeatAndAlarmScheduler.start();
                result.setSuccess(true);
                result.setResultCode("system.timertask.execute.success");
            } else if (status.equals("stop")) {
                heartBeatAndAlarmScheduler.standby();
                result.setSuccess(true);
                result.setResultCode("system.timertask.execute.success");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResultCode("system.timertask.execute.error");
            log.error("Method:TimeTaskServiceImpl-->setHeartBeatAndAlarmSchedulerStatus()-->error", e);
        }
        return result;
    }

    public Result setSyncTaskSchedulerStatus(String status) {

        Result result = new Result();
        try {
            if (status.equals("start")) {
                syncTaskScheduler.start();
                result.setSuccess(true);
                result.setResultCode("system.timertask.execute.success");
            } else if (status.equals("stop")) {
                syncTaskScheduler.standby();
                result.setSuccess(true);
                result.setResultCode("system.timertask.execute.success");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResultCode("system.timertask.execute.error");
            log.error("Method:TimeTaskServiceImpl-->setSyncTaskSchedulerStatus()-->error", e);
        }
        return result;
    }

}