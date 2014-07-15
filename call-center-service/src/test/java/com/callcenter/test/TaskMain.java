package com.callcenter.test;

import com.callcenter.domain.Task;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wangyue-ds6 on 2014/4/28.
 */
public class TaskMain {
    public static void  main(String args[]){
         Map<Long,List<Task>> taskMap = null;
        ExecutorService pool = null;
        final   int POOL_SIZE  = 10;

        if (null == pool) {
            pool = Executors.newFixedThreadPool(POOL_SIZE);
        }
        if (null == taskMap){
            taskMap = new ConcurrentHashMap<Long, List<Task>>();
        }

        ProductTask productTask = new ProductTask(taskMap);
        ConsumerTask consumerTask = new ConsumerTask(taskMap);
        pool.execute(productTask);
        pool.execute(consumerTask);

    }

}
