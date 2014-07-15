package com.callcenter.test;

import com.callcenter.common.tools.DateUtils;
import com.callcenter.domain.Task;
import com.taobao.pamirs.schedule.CronExpression;

import java.text.ParseException;
import java.util.Date;
import java.util.Queue;
import java.util.Timer;

/**
 * Created by wangyue-ds6 on 2014/4/28.
 */
public class DealTaskThread implements Runnable{
    private Queue<Object> queue;
    private Timer timer =  new Timer();

    public DealTaskThread(Queue<Object> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        while (!queue.isEmpty()) {
            Task task = (Task)queue.poll();
            Date currentDate = new Date(DateUtils.getServerTime());
            Date customerDate = null;
            try {
                customerDate = DateUtils.getNextValidTimeAfter(task.getExpress(), currentDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (currentDate.before(customerDate)) {
                queue.offer(task);
                this.timer.schedule(new DealTaskTimer(task), customerDate);
            }
        }

    }

    public void println(Task task){
        System.out.println("Id:" + task.getId() + "  Name:" + task.getName() + "  Date:" + DateUtils.format(new Date()));


    }
}
