package com.callcenter.test;

import com.callcenter.common.tools.DateUtils;
import com.callcenter.domain.Task;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by wangyue-ds6 on 2014/4/28.
 */
public class ConsumerTask implements Runnable {
    private final static  boolean isStop = false;
    private static Map<Long,List<Task>> taskMap = null;
    public ConsumerTask(Map<Long, List<Task>> taskMap){
        this.taskMap =taskMap;
    }
    private ThreadLocal threadLocal = new ThreadLocal();
    private final  static  Map<Long,Boolean> isRunMap = Collections.synchronizedMap(new HashMap<Long, Boolean>());


    @Override
    public void run() {
        while(!isStop){
            for(Map.Entry<Long, List<Task>> entry:taskMap.entrySet()){
                Long entryKey = entry.getKey();
                Long firstMilliseconds = entryKey;
                Long secondMilliseconds =  System.currentTimeMillis();
                if(DateUtils.compareMilliseconds(firstMilliseconds, secondMilliseconds) &&
                        (((firstMilliseconds - secondMilliseconds) / 1000) <= 10) && (null == isRunMap.get(entryKey))){
                    isRunMap.put(entryKey,true);
                    dealTask(entry.getValue());
                    taskMap.remove(entry.getKey());
                }

            }
        }

    }

    public boolean dealTask(List<Task> taskList){
        for(Task task:taskList){
            System.out.println("Id:" + task.getId() + "  Name:" + task.getName() + "  Date:" + DateUtils.format(new Date()));
        }
        return  true;
    }

    public static void  main(String args[]){
       Boolean isRun = isRunMap.get("abc");
        System.out.println(isRun);
//        System.out.println(isRun.booleanValue());

    }


}
