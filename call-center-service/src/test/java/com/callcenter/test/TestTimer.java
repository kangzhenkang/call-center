package com.callcenter.test;

import com.callcenter.common.tools.DateUtils;
import com.callcenter.domain.Task;
import com.taobao.pamirs.schedule.CronExpression;

import java.sql.Time;
import java.text.ParseException;
import java.util.*;

/**
 * Created by wangyue-ds6 on 2014/4/28.
 */
public class TestTimer {

    public static void  main(String args[]){
        List<Task> taskList = new ArrayList<Task>();
        for (int i=0;i < 2;i++){
            Task task = new Task();
            task.setId(i);
            task.setName("task"+i);
            task.setExpress("0 48 14,15 * * ?");
            taskList.add(task);

        }
        Timer timer = new  Timer(Thread.currentThread().getName());
        timer.schedule(new MyTaskTimer(taskList),1000,30000);

    }

    static class MyTaskTimer extends TimerTask {
        private final List<Task> taskList;

        public MyTaskTimer(List taskList ){

            this.taskList = taskList;
        }

        @Override
        public void run() {
            for(Task t:taskList){
                try {
                    CronExpression cronExpression  = new CronExpression(t.getExpress());
                    if  (DateUtils.compareDateWithCronExpression(cronExpression)/* &&
                            DateUtils.differMillisecondsWithCronExpression(cronExpression)<= 60*/){
                        println(t);

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

        }
    }
    public static void println(Task task){
        System.out.println("Id:" + task.getId() + "  Name:" + task.getName() + "  Date:" + DateUtils.format(new Date()));


    }


}
