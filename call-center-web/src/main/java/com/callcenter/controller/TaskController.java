
package com.callcenter.controller;
import com.callcenter.common.Result;
import com.callcenter.common.tools.CommonConstants;
import com.callcenter.common.tools.PropertiesUtils;
import com.callcenter.domain.Dic;
import com.callcenter.domain.common.Message;
import com.callcenter.domain.common.Page;
import com.callcenter.service.DicService;
import com.callcenter.service.TimerTaskService;
import org.apache.commons.lang.StringUtils;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 *dic controller层
 * @author wangyue-ds6
 * @since 2014-02-28
 */
@Controller
@RequestMapping(value = "/task")
public class TaskController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    /**
     * 任务定时器Service 接口
     */
    @Resource
    private TimerTaskService timerTaskService;

    @Resource
    private Scheduler syncTaskScheduler;

    @Resource
    private Scheduler heartBeatAndAlarmScheduler;
    @Resource
    private DicService dicService;

	/**
	 * 列表展示
	 * @param view 实体对象
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
	public String list(String status,String jobTask,Model view) throws Exception{
		try {
            if ("syncTaskScheduler".equals(jobTask)) {
                // 更新库房数据处理任务调度
                toVm(view,timerTaskService.setSyncTaskSchedulerStatus(status));
                Result result = timerTaskService.isStarted();
                toVm(view,result);
            } else if ("heartBeatAndAlarmScheduler".equals(jobTask)) {
                // 监控各个消息路由服务平台任务调度
                toVm(view,timerTaskService.setHeartBeatAndAlarmSchedulerStatus(status));
                toVm(view,timerTaskService.isStarted());
            } else if ("startAll".equals(jobTask)) {
                // 启动全部任务
                toVm(view,timerTaskService.doStartAll());
            } else if ("stopAll".equals(jobTask)) {
                // 停止全部任务
                toVm(view,timerTaskService.doShutDownAll());
            } else {
                toVm(view,timerTaskService.isStarted());
            }
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			throw e;
		}finally{
		}	
		return "task/list";
	}

    /**
     * 列表展示
     * @param view 实体对象
     * @return
     */
    @RequestMapping(value = "/timerTask",method = {RequestMethod.GET,RequestMethod.POST})
    public String timerTask(Model view) throws Exception{
       /* InputStream inputStream = null;
        Properties properties;*/
        try {

            Dic dic = new Dic();
            dic.setDicType(CommonConstants.DYNAMIC_SETTING_TIMER_TASK);
            dic.setYn(CommonConstants.YN_NOT_DELETE);
            view.addAttribute("settingTaskList", dicService.selectEntryList(dic));
          /*  String timerTaskFilePath = this.getClass().getResource(CommonConstants.TIMER_TASK_FILE_PATH).getPath();
            inputStream = new BufferedInputStream(new FileInputStream(timerTaskFilePath));
            properties = new Properties();
            properties.load(inputStream);
            view.addAttribute("autoExecuteSyncTaskTrigger",properties.get("autoExecuteSyncTaskTrigger"));
            view.addAttribute("autoExecuteHeartBeatAndAlarmJobTrigger",properties.get("autoExecuteHeartBeatAndAlarmJobTrigger"));*/

        } catch (Exception e) {
            LOGGER.error("失败:"+e.getMessage(),e);
            throw e;
        }finally{
            /*if(null!=inputStream){
                inputStream.close();
            }*/

        }
        return "task/timerTask";
    }

    /**
     * 响应新增(修改)页面
     * @param timerTaskName 时间程序名称
     * @return
     */
    @RequestMapping(value="/{timerTaskName}",method=RequestMethod.PUT)
    public String edit(@PathVariable String timerTaskName,Model view) throws Exception{
        try {
            if(timerTaskName != null && timerTaskName.length() > 0) {
                Dic dic = new Dic();
                dic.setDicKey(timerTaskName);
                dic.setDicType(CommonConstants.DYNAMIC_SETTING_TIMER_TASK);
                dic.setYn(CommonConstants.YN_NOT_DELETE);
                List<Dic> settingTaskList =  dicService.selectEntryList(dic);
                if(null!=settingTaskList && settingTaskList.size() >0){
                    view.addAttribute("settingTask",settingTaskList.get(0));
                }

              /*  view.addAttribute("timerTaskName",timerTaskName);
                PropertiesUtils propertiesUtils = new PropertiesUtils();
                view.addAttribute("cronExpression", propertiesUtils.getValue(CommonConstants.TIMER_TASK_FILE_PATH, timerTaskName));*/
            }
        } catch (Exception e) {
            LOGGER.error("失败:"+e.getMessage(),e);
            throw e;
        }finally{

        }
        return "task/editTimerTask";
    }


    /**
     * 保存方法
     * @param timerTaskName 时间程序名称
     * @param cronExpression CronExpression
     * @return
     */
    @RequestMapping(value="/save",method = {RequestMethod.POST,RequestMethod.GET},produces="application/json")
    public @ResponseBody Message save(Long id,String timerTaskName,String cronExpression,Model view) throws Exception{
        Message msg= null;
        try {
            if(id > 0 &&timerTaskName != null && timerTaskName.length() > 0 && cronExpression != null && cronExpression.length() > 0) {

              /*  PropertiesUtils propertiesUtils = new PropertiesUtils();
                propertiesUtils.setValue(CommonConstants.TIMER_TASK_FILE_PATH,timerTaskName,cronExpression);*/
                Dic dic = new Dic();
                dic.setId(id);
                dic.setDicKey(timerTaskName);
                dic.setDicValue(cronExpression);
                dic.setDicType(CommonConstants.DYNAMIC_SETTING_TIMER_TASK);
                dic.setYn(CommonConstants.YN_NOT_DELETE);
                msg =  dicService.saveOrUpdate(dic)>0? Message.success():Message.failure();
            }
        } catch (Exception e) {
            LOGGER.error("失败:"+e.getMessage(),e);
            msg = Message.failure();
        }finally{

        }
        return msg;
    }

    /**
     * 保存方法
     * @param timerTaskName 时间程序名称
     * @param cronExpression CronExpression
     * @return
     */
    @RequestMapping(value="/reScheduleJob",method = {RequestMethod.POST,RequestMethod.GET},produces="application/json")
    public @ResponseBody Message reScheduleJob(String timerTaskName,String cronExpression,Model view) throws Exception{
        Message msg= null;
        try {
            if(timerTaskName != null && timerTaskName.length() > 0 && cronExpression != null && cronExpression.length() > 0) {
                // 运行时可通过动态注入的scheduler得到trigger，注意采用这种注入方式在有的项目中会有问题，如果遇到注入问题，可以采取在运行方法时候，获得bean来避免错误发生。
                if ("autoExecuteSyncTaskTrigger".equals(timerTaskName)){
                    CronTriggerBean trigger = (CronTriggerBean) syncTaskScheduler.getTrigger(timerTaskName, Scheduler.DEFAULT_GROUP);
                    String originConExpression = trigger.getCronExpression();
                    // 如果相等，则表示用户并没有重新设定数据库中的任务时间，这种情况不需要重新rescheduleJob
                    if(!originConExpression.equalsIgnoreCase(cronExpression)){
                        trigger.setCronExpression(cronExpression);
                        syncTaskScheduler.rescheduleJob(timerTaskName, Scheduler.DEFAULT_GROUP, trigger);
                    }
                }else if ("autoExecuteSyncTaskTrigger".equals(timerTaskName)){
                    CronTriggerBean trigger = (CronTriggerBean) heartBeatAndAlarmScheduler.getTrigger(timerTaskName, Scheduler.DEFAULT_GROUP);
                    String originConExpression = trigger.getCronExpression();
                    // 如果相等，则表示用户并没有重新设定数据库中的任务时间，这种情况不需要重新rescheduleJob
                    if(!originConExpression.equalsIgnoreCase(cronExpression)){
                        trigger.setCronExpression(cronExpression);
                        heartBeatAndAlarmScheduler.rescheduleJob(timerTaskName, Scheduler.DEFAULT_GROUP, trigger);
                    }
                }


                msg = Message.success();
            }
        } catch (Exception e) {
            LOGGER.error("失败:"+e.getMessage(),e);
            msg = Message.failure();
        }finally{

        }
        return msg;
    }




    public void toVm(Model view,Result result){
       int startAll = 1;
        if(null != result){
            view.addAttribute("result", result);
            Set<String> set = result.keySet();
            for (String key : set) {
                view.addAttribute(key, result.get(key));
                startAll = (startAll & Integer.parseInt((String)result.get(key)));
            }
        }
        view.addAttribute("startAll",startAll);

    }

}