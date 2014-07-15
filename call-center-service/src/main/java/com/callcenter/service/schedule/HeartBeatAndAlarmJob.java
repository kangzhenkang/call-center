package com.callcenter.service.schedule;

import com.callcenter.common.tools.CommonConstants;
import com.callcenter.common.tools.DateUtils;
import com.callcenter.dao.DicDao;
import com.callcenter.dao.SyncTaskDao;
import com.callcenter.domain.Dic;
import com.callcenter.domain.SyncTask;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 模块描述:心跳检测和报警Job
 * User: wangyue-ds6
 * Date: 2014/5/4
 * Time: 13:30
 */
public class HeartBeatAndAlarmJob {
    private final Logger log = LoggerFactory.getLogger(HeartBeatAndAlarmJob.class);
    @Resource
    private SyncTaskDao syncTaskDao;
    @Resource
    private DicDao dicDao;

    /**
     * 心跳检测和报警执行方法
     * @return
     */
    public boolean execute(){
        boolean isSuccess = true;
        List<SyncTask> queryTaskList =  selectTasks(1); //查询任务状态为同步中的，任务类型是WORKER
        for (SyncTask tempSyncTask:queryTaskList) {
            //重置任务状态为未同步状态，释放3分钟没有执行的任务
            isSuccess = resetTaskStatus(tempSyncTask);
            //报警任务调度失败次数超过5次
            if (CommonConstants.MAX_FAIL_COUNT <= tempSyncTask.getFailCount()){
                isSuccess =  alarmTask(tempSyncTask);
            }
        }
        queryTaskList.clear();
        queryTaskList =  selectTasks(2); //查询满足报警条件，任务类型是WORKER
        for (SyncTask tempSyncTask:queryTaskList) {
            //报警任务调度失败次数超过5次
            isSuccess =  alarmTask(tempSyncTask);
        }
        return isSuccess;
    }

    /**
     * 查询任务类型
     * @param queryType 1：查询满足重置任务状态 2：查询满足报警条件 3：查询所有任务（全表扫描）
     * @return
     */
    private List selectTasks(int queryType){

        SyncTask st4Query = initSyncTask4Query(queryType);  //初始化同步任务作为查询条件
        return syncTaskDao.selectEntryList(st4Query);
    }

    /**
     * 重置任务状态为未同步状态，释放3分钟没有执行的任务
     * @param syncTask 同步任务
     * @return 是否成功
     */
    private boolean resetTaskStatus(SyncTask syncTask){
        boolean isSuccess = true;
        Date lastSyncTime = syncTask.getSyncTime();
        if(null != lastSyncTime) {
            //上一次同步时间大于3分钟以上
            if (DateUtils.getServerTime() - lastSyncTime.getTime() >= CommonConstants.HEART_BEAT_TIME) {
                SyncTask syncTask4Update = initSyncTask4Update(syncTask,CommonConstants.SYNC_STATUS_1,
                        CommonConstants.SYNC_STATUS_2);
                //更新失败，记录日志
                if(syncTaskDao.updateByKey(syncTask4Update) <= 0){
                    StringBuilder sbErrorMsg = new StringBuilder();
                    sbErrorMsg.append("HeartBeatAndWarnJob-->").append("execute()-->").append("syncTaskDao.updateByKey error!!!")
                            .append("parameter:"+syncTask4Update.toString());
                    if(log.isDebugEnabled()){
                        System.out.println(sbErrorMsg.toString());
                    }
                    log.error(sbErrorMsg.toString());
                    isSuccess = false;
                }

            }
        }
        return isSuccess;
    }

    /**
     * 报警任务调度失败次数超过5次
     * @param syncTask 同步任务
     * @return 是否成功
     */
    private boolean alarmTask(SyncTask syncTask){
        boolean isSuccess = true;
        //是否启动报警 ,默认不启动报警功能
        List<Dic>dicList = dicDao.selectEntryList(initDict4Query());
        if (null != dicList && dicList.size()> 0){
            Dic dic = dicList.get(0);
            String dicValue = dic.getDicValue();
            try {
                if (StringUtils.isNotBlank(dicValue) && ("Y".equalsIgnoreCase(dicValue)||"是".equals(dicValue)) ){
                    isSuccess = sendMsg(syncTask.getMobile(),syncTask.getErrorMsg());
                    isSuccess = sendEMail(syncTask.getEmail(),syncTask.getErrorMsg());
                }

            }catch (Exception e){
                StringBuilder sbErrorMsg = new StringBuilder();
                sbErrorMsg.append("HeartBeatAndWarnJob-->").append("alarmTask()--> error!!!")
                        .append(e);
                if(log.isDebugEnabled()){
                    System.out.println(sbErrorMsg.toString());
                }
                log.error(sbErrorMsg.toString());
            }

        }
        return isSuccess;
    }

    /**
     * 发送短信方法
     * @param mobile 手机号码
     * @param msg 短信内容
     * @return 是否成功
     */
    private boolean sendMsg(String mobile,String msg) throws Exception{
        return true;
    }

    /**
     * 发送邮件方法
     * @param email 邮箱地址
     * @param msg 邮件内容
     * @return 是否成功
     */
    private boolean sendEMail(String email,String msg) throws Exception{
        return true;
    }

    /**
     * 初始化同步任务作为查询条件
     * @param queryType 1：查询满足重置任务状态 2：查询满足报警条件 3：查询所有任务（全表扫描）
     * @return SyncTask对象
     */
    private SyncTask initSyncTask4Query(int queryType) {
        SyncTask syncTask = new SyncTask();
        if (1 == queryType){
            syncTask.setSyncStatus(CommonConstants.SYNC_STATUS_2);
            syncTask.setTaskType(CommonConstants.TASK_TYPE_JOB);
        }else if(2 == queryType) {
            syncTask.setFailCount(CommonConstants.MAX_FAIL_COUNT);
            syncTask.setTaskType(CommonConstants.TASK_TYPE_JOB);
        }
        syncTask.setYn(CommonConstants.YN_NOT_DELETE);
        return syncTask;
    }

    /**
     * 初始化同步任务作为更新条件
     *
     * @param st SyncTask对象
     * @return SyncTask对象
     */
    private SyncTask initSyncTask4Update(SyncTask st, Integer syncStatus,Integer previousSyncStatus) {
        SyncTask syncTask = new SyncTask();
        syncTask.setId(st.getId());
        syncTask.setSyncStatus(syncStatus);
        syncTask.setPreviousSyncStatus(previousSyncStatus);
        syncTask.setUpdateTime(new Date());
        syncTask.setSyncTime(new Date());
        return syncTask;
    }

    /**
     * 初始化Dic查询类
     * @return Dic对象
     */
    private Dic initDict4Query(){
        Dic dic = new Dic();
        dic.setDicKey(CommonConstants.IS_START_ALARM);
        dic.setYn(CommonConstants.YN_NOT_DELETE);
        return  dic;
    }
 }
