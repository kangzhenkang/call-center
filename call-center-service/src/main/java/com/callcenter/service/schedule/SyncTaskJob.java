package com.callcenter.service.schedule;

import com.callcenter.common.tools.CommonConstants;
import com.callcenter.common.tools.DateUtils;
import com.callcenter.dao.DataRecordDao;
import com.callcenter.dao.SyncTaskDao;
import com.callcenter.domain.DataRecord;
import com.callcenter.domain.SyncTask;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 同步任务job
 * User: wangyueinfo
 * Date: 14-2-28
 * Time: 下午11:45
 */
public final class SyncTaskJob {

    private static final Log log = LogFactory.getLog(SyncTaskJob.class);
    @Resource
    private SyncTaskDao syncTaskDao; //同步任务Dao
    @Resource
    private DataRecordDao dataRecordDao; //数据内容Dao1
    @Resource
    private HandleSyncTaskService handleSyncTaskService; //同步任务处理Service
    private static ExecutorService pool = null; //线程池
    private static Map<Long, CopyOnWriteArrayList<SyncTask>> toCheckTaskMap = null; //待任务检测Map
    private final static Map<Long, Boolean> isRunMap = new ConcurrentHashMap<Long, Boolean>();//任务是否运行过Map


    /**
     * @param taskParameter 任务参数
     * @return 任务集合
     * @throws Exception
     */
    public CopyOnWriteArrayList<SyncTask> selectTasks(String taskParameter) throws Exception {

        CopyOnWriteArrayList<SyncTask> toRunTaskList = new CopyOnWriteArrayList<SyncTask>(); //待任务调度的任务集合
        SyncTask st4Query = initSyncTask4Query();  //初始化同步任务作为查询条件
        List<SyncTask> queryTaskList = syncTaskDao.selectEntryList(st4Query); //从数据库中查询出来的任务
        for (SyncTask queryTask : queryTaskList) {
            //只抓取失败次数小于 Constant.MAX_FAIL_COUNT（目前是5）
            if (CommonConstants.MAX_FAIL_COUNT > queryTask.getFailCount()) {
                //更新同步任务状态为同步中
                if (syncTaskDao.updateByKey(initSyncTask4Update(queryTask, CommonConstants.SYNC_STATUS_2,
                        CommonConstants.SYNC_STATUS_1)) > 0) {
                    String syncPlan = queryTask.getSyncPlan();
                    //判断用户定制执行时间
                    if (StringUtils.isNotBlank(syncPlan)) {
                        Long firstMilliseconds = DateUtils.getNextValidTimeAfter(syncPlan);//用户下次执行时间
                        Long secondMilliseconds = System.currentTimeMillis(); //系统时间
                        if (DateUtils.compareMillisecondsAndDeviationTime(firstMilliseconds, secondMilliseconds, CommonConstants.DIFFER_MILLI_SECONDS)
                                && (null == isRunMap.get(firstMilliseconds))) {
                            isRunMap.put(firstMilliseconds, true);
                            packageTaskData(toRunTaskList, queryTask);
                        } else {
                            if (null == toCheckTaskMap) {
                                toCheckTaskMap = new ConcurrentHashMap<Long, CopyOnWriteArrayList<SyncTask>>();
                            }
                            CopyOnWriteArrayList<SyncTask> toCheckTaskList = new CopyOnWriteArrayList<SyncTask>();
                            if (toCheckTaskMap.containsKey(firstMilliseconds)) {
                                CopyOnWriteArrayList<SyncTask> tempList = toCheckTaskMap.get(firstMilliseconds);
                                tempList.add(queryTask);
                                toCheckTaskMap.put(firstMilliseconds, tempList);
                            } else {
                                toCheckTaskList.add(queryTask);
                                toCheckTaskMap.put(firstMilliseconds, toCheckTaskList);
                            }
                            if (null == pool) {
                                pool = Executors.newFixedThreadPool(CommonConstants.POOL_SIZE);
                            }
                            pool.execute(new TaskCheckThread(toRunTaskList, toCheckTaskMap));
                        }
                    } else {
                        packageTaskData(toRunTaskList, queryTask);
                    }
                }
            }
        }
        return toRunTaskList;
    }

    /**
     * 执行方法
     *
     * @return 是否成功
     * @throws Exception
     */
    public boolean execute() throws Exception {
        CopyOnWriteArrayList<SyncTask> toRunTaskList = selectTasks(null);
        for (SyncTask task : toRunTaskList) {
            log.info("执行任务的个数为" + toRunTaskList.size());
            SyncTask st4Update;
            try {
                if (handleSyncTaskService.parseAndCallService(task)) {
                    //如果同步类型-循环 那么需要将状态修改为未同步，让同步任务继续执行
                    if (CommonConstants.SYNC_TYPE_CYCLE.equals(task.getSyncType())) {
                        st4Update = initSyncTask4Update(task, CommonConstants.SYNC_STATUS_1, CommonConstants.SYNC_STATUS_2);
                        syncTaskDao.updateByKey(st4Update);
                    } else {
                        st4Update = initSyncTask4Update(task, CommonConstants.SYNC_STATUS_3, CommonConstants.SYNC_STATUS_2);
                        syncTaskDao.updateByKey(st4Update);
                    }
                } else {
                    st4Update = initSyncTask4Update(task, CommonConstants.SYNC_STATUS_1, CommonConstants.SYNC_STATUS_2);
                    st4Update.setFailCount(task.getFailCount() + 1);
                    syncTaskDao.updateByKey(st4Update);
                }
            } catch (Exception e) {
                st4Update = initSyncTask4Update(task, CommonConstants.SYNC_STATUS_1, CommonConstants.SYNC_STATUS_2);
                st4Update.setFailCount(task.getFailCount() + 1);
                syncTaskDao.updateByKey(st4Update);
                return false;
            }
        }
        return true;
    }

    /**
     * 执行方法
     *
     * @return 是否成功
     */
    public boolean execute(CopyOnWriteArrayList<SyncTask> toRunTaskList) {
        for (SyncTask task : toRunTaskList) {
            log.info("执行任务的个数为" + toRunTaskList.size());
            SyncTask st4Update;
            try {
                if (handleSyncTaskService.parseAndCallService(task)) {
                    //如果同步类型-循环 那么需要将状态修改为未同步，让同步任务继续执行
                    if (CommonConstants.SYNC_TYPE_CYCLE.equals(task.getSyncType())) {
                        st4Update = initSyncTask4Update(task, CommonConstants.SYNC_STATUS_1, CommonConstants.SYNC_STATUS_2);
                        syncTaskDao.updateByKey(st4Update);
                    } else {
                        st4Update = initSyncTask4Update(task, CommonConstants.SYNC_STATUS_3, CommonConstants.SYNC_STATUS_2);
                        syncTaskDao.updateByKey(st4Update);
                    }
                } else {
                    st4Update = initSyncTask4Update(task, CommonConstants.SYNC_STATUS_1, CommonConstants.SYNC_STATUS_2);
                    st4Update.setFailCount(task.getFailCount() + 1);
                    syncTaskDao.updateByKey(st4Update);
                }
            } catch (Exception e) {
                st4Update = initSyncTask4Update(task, CommonConstants.SYNC_STATUS_1, CommonConstants.SYNC_STATUS_2);
                st4Update.setFailCount(task.getFailCount() + 1);
                syncTaskDao.updateByKey(st4Update);
                return false;
            }
        }
        return true;
    }

    /**
     * 初始化同步任务作为查询条件
     *
     * @return SyncTask对象
     */
    private SyncTask initSyncTask4Query() {
        SyncTask syncTask = new SyncTask();
        syncTask.setSyncStatus(CommonConstants.SYNC_STATUS_1);
        syncTask.setTaskType(CommonConstants.TASK_TYPE_JOB);
        syncTask.setYn(CommonConstants.YN_NOT_DELETE);
        return syncTask;
    }

    /**
     * 初始化同步任务作为更新条件
     *
     * @param st SyncTask对象
     * @return SyncTask对象
     */
    private SyncTask initSyncTask4Update(SyncTask st, Integer syncStatus, Integer previousSyncStatus) {
        SyncTask syncTask = new SyncTask();
        syncTask.setId(st.getId());
        syncTask.setSyncStatus(syncStatus);
        syncTask.setPreviousSyncStatus(previousSyncStatus);
        syncTask.setUpdateTime(new Date());
        syncTask.setSyncTime(new Date());
        syncTask.setSyncCount(st.getSyncCount() + 1);
        return syncTask;
    }

    /**
     * 初始化数据记录作为查询条件
     *
     * @param syncTask SyncTask对象
     * @return DataRecord对象
     */
    private DataRecord initDataRecord4Query(SyncTask syncTask) {
        DataRecord dataRecord = new DataRecord();
        dataRecord.setYn(1);
        dataRecord.setTaskId(syncTask.getId());
        dataRecord.setBizStatus("F");
        return dataRecord;
    }

    /**
     * 拼装任务数据
     *
     * @param toRunTaskList 任务集合
     * @param st            SyncTask对象
     */
    private void packageTaskData(CopyOnWriteArrayList<SyncTask> toRunTaskList, SyncTask st) {
        /**
         * 只执行一次的任务，需要从DataRecord表中获取需要执行的数据
         * 而单循环调用，值负责调用对方提供的Dubbo服务即可，不需要封装方法执行数据
         */
        if (CommonConstants.SYNC_TYPE_SINGLE.equals(st.getSyncType())) {
            DataRecord dr4Query = initDataRecord4Query(st); //初始化数据记录查询条件
            List<DataRecord> dataRecordList = dataRecordDao.selectEntryList(dr4Query);
            if (null != dataRecordList && dataRecordList.size() > 0) {
                //存储一条任务数据记录
                if (1 == dataRecordList.size()) {
                    st.setBizDataStr(dataRecordList.get(0).getBizData());
                } else if (dataRecordList.size() > 1) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (DataRecord dr : dataRecordList) {
                        stringBuilder.append(dr.getBizData());
                    }
                    st.setBizDataStr(stringBuilder.toString());
                }
                toRunTaskList.add(st);
            }
        } else {
            toRunTaskList.add(st);
        }
    }

    /**
     * 拼装任务数据
     *
     * @param syncTaskList SyncTask对象集合
     */
    private CopyOnWriteArrayList<SyncTask> packageTaskData(CopyOnWriteArrayList<SyncTask> syncTaskList) {
        CopyOnWriteArrayList<SyncTask> toRunTaskList = new CopyOnWriteArrayList<SyncTask>(); //待任务调度的任务集合
        /**
         * 只执行一次的任务，需要从DataRecord表中获取需要执行的数据
         * 而单循环调用，值负责调用对方提供的Dubbo服务即可，不需要封装方法执行数据
         */
        for (SyncTask st : syncTaskList) {
            if (CommonConstants.SYNC_TYPE_SINGLE.equals(st.getSyncType())) {
                DataRecord dr4Query = initDataRecord4Query(st); //初始化数据记录查询条件
                List<DataRecord> dataRecordList = dataRecordDao.selectEntryList(dr4Query);
                if (null != dataRecordList && dataRecordList.size() > 0) {
                    //存储一条任务数据记录
                    if (1 == dataRecordList.size()) {
                        st.setBizDataStr(dataRecordList.get(0).getBizData());
                    } else if (dataRecordList.size() > 1) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (DataRecord dr : dataRecordList) {
                            stringBuilder.append(dr.getBizData());
                        }
                        st.setBizDataStr(stringBuilder.toString());
                    }
                    toRunTaskList.add(st);
                }
            } else {
                toRunTaskList.add(st);
            }
        }
        return toRunTaskList;
    }

    class TaskCheckThread implements Runnable {
        private final static boolean isStop = false;
        private Map<Long, CopyOnWriteArrayList<SyncTask>> taskMap = null;
        private final Map<Long, Boolean> isRunMap = Collections.synchronizedMap(new HashMap<Long, Boolean>());
        private final CopyOnWriteArrayList<SyncTask> toRunTaskList;

        public TaskCheckThread(CopyOnWriteArrayList<SyncTask> toRunTaskList, Map<Long, CopyOnWriteArrayList<SyncTask>> toCheckTaskMap) {
            this.toRunTaskList = toRunTaskList;
            this.taskMap = toCheckTaskMap;
        }

        @Override
        public void run() {
            while (!isStop) {
                synchronized (taskMap) {
                    for (Map.Entry<Long, CopyOnWriteArrayList<SyncTask>> entry : taskMap.entrySet()) {
                        Long entryKey = entry.getKey();
                        Long firstMilliseconds = entryKey;
                        Long secondMilliseconds = System.currentTimeMillis();
                        if (DateUtils.compareMillisecondsAndDeviationTime(firstMilliseconds, secondMilliseconds, CommonConstants.DIFFER_MILLI_SECONDS) &&
                                (null == isRunMap.get(entryKey))) {
                            isRunMap.put(entryKey, true);
                            taskMap.remove(firstMilliseconds);
                            isRunMap.remove(firstMilliseconds);
                            execute(packageTaskData(entry.getValue()));
                        }
                    }

                }

            }
        }
    }

}
