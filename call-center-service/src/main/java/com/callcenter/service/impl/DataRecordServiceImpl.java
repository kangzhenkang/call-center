
package com.callcenter.service.impl;


import com.callcenter.api.DataContentService;
import com.callcenter.common.tools.CommonConstants;
import com.callcenter.dao.DataRecordDao;
import com.callcenter.dao.SyncTaskDao;
import com.callcenter.dao.base.BaseDao;
import com.callcenter.api.domain.DataContent;
import com.callcenter.domain.DataRecord;
import com.callcenter.domain.SyncTask;
import com.callcenter.service.DataRecordService;
import com.callcenter.service.base.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * DataRecordService 实现类
 *
 * @author wangyue-ds6
 * @since 2014-02-28
 */
@Service("dataRecordService")
public class DataRecordServiceImpl extends BaseServiceImpl<DataRecord, Long> implements DataRecordService,DataContentService {
    private static final Log log = LogFactory.getLog(DataRecordServiceImpl.class);

    @Resource
    private DataRecordDao dataRecordDao;

    @Resource
    private SyncTaskDao syncTaskDao;

    public BaseDao<DataRecord, Long> getDao() {
        return dataRecordDao;
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertEntryCreateId(DataRecord dataRecord) {
        return super.insertEntry(dataRecord);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public boolean writeData(DataContent dataContent) {
        boolean isSuccess = false;
        if (null != dataContent && null != dataContent.getBizData()) {
            //报文数据
            String bizData = dataContent.getBizData();
            //获取报文数据总长度
            int bizDatalen = bizData.length();
            // 存在字符数大于数据库最大长度 2000 字节，需要拆记录
            if (bizDatalen > CommonConstants.DATA_MAX_COUNT) {
                int dataCount = bizDatalen / CommonConstants.DATA_MAX_COUNT;              //拆分次数
                int flag = 0;
                while (flag <= dataCount) {
                    String bizDataZipTemp = "";
                    // 最后一次循环，只需要存储剩下不到规定长度的字符串
                    if (flag == dataCount) {
                        bizDataZipTemp = bizData.substring(flag * CommonConstants.DATA_MAX_COUNT, bizDatalen);
                    } else {
                        bizDataZipTemp = bizData.substring(flag * CommonConstants.DATA_MAX_COUNT, (flag + 1) * CommonConstants.DATA_MAX_COUNT);
                    }
                    if (StringUtils.isNotBlank(bizDataZipTemp)) {
                        dataContent.setBizData(bizDataZipTemp);
                        dataContent.setOrderNo(flag + 1);
                        // 新增大数据对象  只执行一次
                        if ("F".equals(dataContent.getBizStatus()) && 0 == flag) {
                            dataContent.setTaskId(insertSyncTask(dataContent));
                        }
                        isSuccess = insertDataRecord(dataContent);
                    }
                    //必须加上
                    flag++;
                }
            } else {
                dataContent.setOrderNo(1);
                if ("F".equals(dataContent.getBizStatus())) {
                    dataContent.setTaskId(insertSyncTask(dataContent));
                }
                isSuccess = insertDataRecord(dataContent);
            }
        } else {
            isSuccess = false;
        }
        return isSuccess;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public boolean writeDataRecord(DataContent dataContent) {
        boolean isSuccess = false;
        if (null != dataContent && null != dataContent.getBizData()) {
            //报文数据
            String bizData = dataContent.getBizData();
            //获取报文数据总长度
            int bizDatalen = bizData.length();
            // 存在字符数大于数据库最大长度 2000 字节，需要拆记录
            if (bizDatalen > CommonConstants.DATA_MAX_COUNT) {
                int dataCount = bizDatalen / CommonConstants.DATA_MAX_COUNT;              //拆分次数
                int flag = 0;
                while (flag <= dataCount) {
                    String bizDataZipTemp = "";
                    // 最后一次循环，只需要存储剩下不到规定长度的字符串
                    if (flag == dataCount) {
                        bizDataZipTemp = bizData.substring(flag * CommonConstants.DATA_MAX_COUNT, bizDatalen);
                    } else {
                        bizDataZipTemp = bizData.substring(flag * CommonConstants.DATA_MAX_COUNT, (flag + 1) * CommonConstants.DATA_MAX_COUNT);
                    }
                    if (StringUtils.isNotBlank(bizDataZipTemp)) {
                        dataContent.setBizData(bizDataZipTemp);
                        dataContent.setOrderNo(flag + 1);
                        // 新增大数据对象  只执行一次
                        if ("F".equals(dataContent.getBizStatus()) && 0 == flag) {
                            dataContent.setTaskId(insertSyncTask(dataContent));
                        }
                        isSuccess = insertDataRecord(dataContent);
                    }
                    //必须加上
                    flag++;
                }
            } else {
                dataContent.setOrderNo(1);
                if ("F".equals(dataContent.getBizStatus())) {
                    dataContent.setTaskId(insertSyncTask(dataContent));
                }
                isSuccess = insertDataRecord(dataContent);
            }
        } else {
            isSuccess = false;
        }
        return isSuccess;
    }

    @Transactional
    @Override
    public boolean updateDataRecord(DataContent dataContent) {
            boolean isSuccess = true;
        SyncTask syncTask = initSyncTask4Update(dataContent);
        try {
            if (syncTaskDao.updateByKey(syncTask) > 0) {
                DataRecord dr4Query = new DataRecord();
                dr4Query.setTaskId(dataContent.getTaskId());
                List<DataRecord> dataRecordList = dataRecordDao.selectEntryList(dr4Query);
                int dataRecordLen = dataRecordList.size();
                if (dataRecordLen > 0) {
                    //报文数据
                    String bizData = dataContent.getBizData();
                    //获取报文数据总长度
                    int bizDatalen = bizData.length();
                    // 存在字符数大于数据库最大长度 2000 字节，需要拆记录
                    if (bizDatalen > CommonConstants.DATA_MAX_COUNT) {
                        int dataCount = bizDatalen / CommonConstants.DATA_MAX_COUNT;              //拆分次数
                        int flag = 0;
                        while (flag <= dataCount) {
                            String bizDataZipTemp = "";
                            // 最后一次循环，只需要存储剩下不到规定长度的字符串
                            if (flag == dataCount) {
                                bizDataZipTemp = bizData.substring(flag * CommonConstants.DATA_MAX_COUNT, bizDatalen);
                            } else {
                                bizDataZipTemp = bizData.substring(flag * CommonConstants.DATA_MAX_COUNT, (flag + 1) * CommonConstants.DATA_MAX_COUNT);
                            }
                            if (StringUtils.isNotBlank(bizDataZipTemp)) {
                                dataContent.setBizData(bizDataZipTemp);
                                // 插入新纪录
                                if (flag == dataCount && dataCount > dataRecordLen) {
                                    dataContent.setOrderNo(dataRecordLen +1);
                                    isSuccess= insertDataRecord(dataContent);
                                } else if (flag == dataCount && dataCount < dataRecordLen) {
                                    //删除纪录 = dataRecordLen-dataCount
                                    while (dataRecordLen!=dataCount) {
                                        isSuccess =  dataRecordDao.deleteByKey(dataRecordList.get(dataRecordLen))>0?true:false;
                                        dataCount++;
                                    }
                                } else {
                                    //正常更新
                                    DataRecord dr4Update = initDataRecord4Update(dataContent);
                                    isSuccess = dataRecordDao.updateByKey(dr4Update)>0?true:false;
                                }
                            }
                            //必须加上
                            flag++;
                        }

                    } else {
                        //正常更新
                        DataRecord dr4Update = initDataRecord4Update(dataContent);
                        isSuccess = dataRecordDao.updateByKey(dr4Update)>0?true:false;
                    }
                }
            }
        } catch (Exception e) {
            log.error("DataRecordServiceImpl->updateSyncTask->syncTaskDao.update", e);
            isSuccess = false;
        }
        return isSuccess;
    }

    private DataRecord initDataRecord4Update(DataContent dataContent) {
        DataRecord dr4Update = new DataRecord();
        dr4Update.setTaskId(dataContent.getTaskId());
        dr4Update.setBizType(dataContent.getBizType());
        dr4Update.setUpdateTime(new Date());
        return dr4Update;
    }


    /**
     * 插入同步任务数据
     *
     * @param dataContent
     * @return
     */
    private Long insertSyncTask(DataContent dataContent) {
        SyncTask syncTask = new SyncTask();
        syncTask.setBizType(dataContent.getBizType());//业务类型
        syncTask.setCallServiceName(dataContent.getCallServiceName()); //调用业务对象名称
        syncTask.setCallMethodName(dataContent.getCallMethodName());   //调用业务方法名称
        syncTask.setSyncStatus(dataContent.getSyncStatus()); //同步状态
        syncTask.setTaskType(dataContent.getTaskType()); //任务类型
        syncTask.setSyncType(dataContent.getSyncType());  //同步类型
        syncTask.setSyncPlan(dataContent.getSyncPlan());  // 执行计划
        syncTask.setSyncCount(0); //执行次数
//        syncTask.setSyncTime(new Date());
        syncTask.setFailCount(0);
        syncTask.setOrderNo(dataContent.getOrderNo());
        syncTask.setMqDestSysId(dataContent.getMqDestSysId());
        syncTask.setMobile(dataContent.getMobile());
        syncTask.setEmail(dataContent.getEmail());
        syncTask.setErrorMsg(dataContent.getErrorMsg());
        syncTask.setCreateTime(new Date());
        syncTask.setUpdateTime(new Date());
        syncTask.setYn(1);
        syncTask.setMqDestSysId(dataContent.getMqDestSysId());
        Long pkId = null;
        try {
            if (syncTaskDao.insertEntry(syncTask) > 0) {
                pkId = syncTask.getId();
            }
        } catch (Exception e) {
            log.error("DataRecordServiceImpl->insertSyncTask->syncTaskDao.insert", e);
        }
        return pkId;
    }


    /**
     * 插入记录数据
     *
     * @param dataContent
     * @return
     */
    private boolean insertDataRecord(DataContent dataContent) {
        try {
            DataRecord dr4Insert = new DataRecord();
            dr4Insert.setTaskId(dataContent.getTaskId());
            dr4Insert.setBizType(dataContent.getBizType());
            dr4Insert.setBizData(dataContent.getBizData());
            dr4Insert.setBizStatus(dataContent.getBizStatus());
            dr4Insert.setOrderNo(dataContent.getOrderNo());
            dr4Insert.setCreateTime(new Date());
            dr4Insert.setUpdateTime(new Date());
            dr4Insert.setYn(CommonConstants.YN_NOT_DELETE);
            return dataRecordDao.insertEntry(dr4Insert) > 0;
        } catch (Exception e) {
            log.error("DataRecordServiceImpl->insertDataRecord->joslDataRecordDao.insert -> return false");
            return false;
        }
    }

    private SyncTask initSyncTask4Update(DataContent dataContent) {
        SyncTask syncTask = new SyncTask();
        syncTask.setId(dataContent.getTaskId());
        syncTask.setBizType(dataContent.getBizType());//业务类型
        syncTask.setCallServiceName(dataContent.getCallServiceName()); //调用业务对象名称
        syncTask.setCallMethodName(dataContent.getCallMethodName());   //调用业务方法名称
        syncTask.setSyncStatus(dataContent.getSyncStatus()); //同步状态
        syncTask.setTaskType(dataContent.getTaskType()); //任务类型
        syncTask.setSyncType(dataContent.getSyncType());  //同步类型
        syncTask.setSyncPlan(dataContent.getSyncPlan());  // 执行计划
        syncTask.setSyncCount(dataContent.getSyncCount()); //执行次数
        syncTask.setFailCount(dataContent.getFailCount());
        syncTask.setOrderNo(dataContent.getOrderNo());
        syncTask.setMqDestSysId(dataContent.getMqDestSysId());
        syncTask.setMobile(dataContent.getMobile());
        syncTask.setEmail(dataContent.getEmail());
        syncTask.setErrorMsg(dataContent.getErrorMsg());
        syncTask.setUpdateTime(new Date());
        return syncTask;
    }

}