package com.callcenter.test;

import com.callcenter.common.tools.DateUtils;
import com.callcenter.domain.Task;
import org.apache.log4j.spi.LoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import java.text.ParseException;
import java.util.Date;
import java.util.Queue;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wangyue-ds6 on 2014/4/28.
 */
public class TestSchedule {
    private final  transient Logger log  = org.slf4j.LoggerFactory.getLogger(TestSchedule.class);
    private final static  int POOL_SIZE  = 10;



/*
    @Test
    public void testA(){
        System.out.println("testA()");

    }*/

    public static void  main(String args[]){

        ExecutorService pool = null;
        Queue<Object> queue = null;
        DealTaskThread thread = null;
        if (null == pool) {
            pool = Executors.newFixedThreadPool(POOL_SIZE);
        }
        if (null == queue){
            queue = new ConcurrentLinkedQueue<Object>();
        }

        for (int i=0;i < POOL_SIZE;i++){
            Task  task = new Task();
            task.setId(i);
            task.setName("task"+i);
            task.setExpress("0 0/1 * * * ?");
            queue.offer(task);

        }
        thread = new DealTaskThread(queue);
        pool.execute(thread);

    }



}
