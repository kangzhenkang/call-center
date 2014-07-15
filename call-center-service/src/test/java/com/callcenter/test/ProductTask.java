package com.callcenter.test;

import com.callcenter.common.tools.DateUtils;
import com.callcenter.domain.Task;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by wangyue-ds6 on 2014/4/28.
 */
public class ProductTask implements Runnable {
    private final static  boolean isStop = false;
    private static Map<Long,List<Task>> taskMap = null;
    public ProductTask(Map<Long, List<Task>> taskMap){
        this.taskMap =taskMap;
    }

    @Override
    public void run() {

       /* while (!isStop)*/{
            final   int POOL_SIZE  = 10;
            List<Task> taskList = new CopyOnWriteArrayList<Task>();
            for (int i=0;i < POOL_SIZE;i++){
                Task  task = new Task();
                task.setId(i);
                task.setName("task"+i);
                //task.setExpress("0 0/"+i+" * * * ?");
                task.setExpress("0 "+(9+i)+" 10 * * ?");
                Long nextStartTime = null;
                try {
                    nextStartTime = DateUtils.getNextValidTimeAfter(task.getExpress());
                } catch (ParseException e) {
                }
                if (taskMap.containsKey(nextStartTime)) {
                    List tempList = taskMap.get(nextStartTime);
                    tempList .add(task);
                    taskMap.put(nextStartTime,tempList);
                }else {
                    taskList.add(task);
                    taskMap.put(nextStartTime,taskList);
                }

            }

        }

    }
}
