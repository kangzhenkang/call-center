package com.callcenter.test;

import com.callcenter.common.tools.DateUtils;
import com.callcenter.domain.Task;

import java.text.ParseException;
import java.util.Date;
import java.util.Queue;
import java.util.Timer;

/**
 * Created by wangyue-ds6 on 2014/4/28.
 */
public class DealTaskTimer extends java.util.TimerTask{
    private Timer timer =  new Timer();
    private Task task;
    public DealTaskTimer(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        Date currentDate = new Date(DateUtils.getServerTime());
        Date customerDate = null;
        try {
            customerDate = DateUtils.getNextValidTimeAfter(task.getExpress(), currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (currentDate.before(customerDate)) {
            this.timer.schedule(new DealTaskTimer(task), customerDate);
        }

    }

    public void println(Task task){
        System.out.println("Id:" + task.getId() + "  Name:" + task.getName() + "  Date:" + DateUtils.format(new Date()));


    }
}
