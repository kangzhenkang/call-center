package com.callcenter.service.schedule;

import com.alibaba.fastjson.JSON;

import com.callcenter.common.tools.ApplicationContextUtil;
import com.callcenter.common.tools.CommonConstants;
import com.callcenter.domain.SyncTask;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * 处理任务同步数据
 * User: wangyueinfo
 * Date: 13-12-23
 * Time: 下午6:08
 * version:1.0
 */
@Service
public class HandleSyncTaskService {
    Log log = LogFactory.getLog(HandleSyncTaskService.class);

    /**
     * 解析数据、调用相关业务方法 异步数据同步 - 同步任务对象，用来解决异步处理信息
     *
     * @param syncTask
     * @return true:成功 false:失败
     */
    public boolean parseAndCallService(SyncTask syncTask) {
        boolean isSuccess = true;
        try {
            Object serviceObj = ApplicationContextUtil.getContext().getBean(syncTask.getCallServiceName());   //从Spring上下文获取调用对象实例
            String methodNameStr = syncTask.getCallMethodName(); //获取调用方面名称
            String parametersStr = syncTask.getBizDataStr(); //获取调用参数信息
            //判断是JOB还是MQ
            if (CommonConstants.TASK_TYPE_JOB.equals(syncTask.getTaskType())){
                String parameters[] = parametersStr.split(CommonConstants.SPLIT_PARAMETER_FALG_1);  //用于分割多个参数
                int len = parameters.length; //获取从参数总数
                Class[] clzs = new Class[len];
                Object argsList[] = new Object[len];
                /**
                 * 分割多个参数，遍历具体每个参数的类名和参数Json串，反射具体类实例和解析Json串，设置Method参数类型和值，动态调用指定服务方法
                 */
                Class<?> clz;
                for (int i = 0; i < len; i++) {
                    String parametersInfo[] = parameters[i].split(CommonConstants.SPLIT_PARAMETER_FALG_2);
                    String clzName = parametersInfo[0];
                    String clzParameterInfo = parametersInfo[1];
                    clz = Class.forName(clzName);
                    Object instance;
                    //如果是基本类型或者是lang包或者util包下面的类，直接赋值，不用转成Json
                    if (clz.isPrimitive() ||(clz.getPackage().getName().equals("java.lang")||clz.getPackage().getName().equals("java.util"))){
                        instance = clzParameterInfo;
                    } else {
                        instance   = JSON.parseObject(clzParameterInfo, clz);
                    }
                    clzs[i] = instance.getClass();
                    argsList[i] = instance;
                }
                Method method = serviceObj.getClass().getMethod(methodNameStr, clzs);
                Object returnObj = method.invoke(serviceObj, argsList);
                if (returnObj instanceof Integer) {
                    int returnValue = ((Integer) returnObj).intValue();
                    isSuccess = (returnValue>0 ? true:false);
                } else if (returnObj instanceof Boolean) {
                    isSuccess = (Boolean) returnObj;
                }

            }else if (CommonConstants.TASK_TYPE_JOB.equals(syncTask.getTaskType())){
                 //发送MQ
            }
        } catch (Exception e) {
            log.error("HandleSyncTaskService->parseAndCallService(SyncTask SyncTask)", e);
            isSuccess = false;
        }
        return isSuccess;
    }
    public static void main(String args[]) {
        SyncTask syncTask = new SyncTask();
        syncTask.setCallMethodName("add");
        StringBuilder sb = new StringBuilder();
        sb.append("java.lang.String").append("#_#").append("sit");
        syncTask.setBizDataStr(sb.toString());
        syncTask.setTaskType(CommonConstants.TASK_TYPE_JOB);
        new HandleSyncTaskService().parseAndCallService(syncTask);

    }
}
