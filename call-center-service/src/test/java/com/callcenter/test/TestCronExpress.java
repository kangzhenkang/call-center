package com.callcenter.test;

import com.callcenter.common.tools.DateUtils;
import com.taobao.pamirs.schedule.CronExpression;
import org.junit.Test;

import javax.sound.midi.SysexMessage;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by wangyue-ds6 on 2014/4/25.
 */
public class TestCronExpress {

   // @Test
    public static void  main(String arg[]) throws ParseException {
        String  tmpStr = "0 0/1 * * * ?";

        CronExpression cronExpression = new  CronExpression(tmpStr);
        Long  currentMilliseconds = System.currentTimeMillis();
        Date currentDate = new Date(currentMilliseconds);
        Date customerDate = cronExpression.getNextValidTimeAfter(currentDate);

        Long  customerMilliseconds =customerDate.getTime();
        System.out.println(currentMilliseconds);
        System.out.println(customerMilliseconds);
        System.out.println((customerMilliseconds-currentMilliseconds)/1000);
        if(Math.abs((customerMilliseconds-currentMilliseconds)/1000)<=60){
            //执行任务调度操作
            System.out.println("customerTime==currentTime");
        }
        System.out.println(DateUtils.format(currentDate,"yyyy-MM-dd HH:mm"));
        System.out.println(DateUtils.format(customerDate,"yyyy-MM-dd HH:mm"));

      /*  System.out.println(DateUtils.format(current));
        String expression =cronExpression.getCronExpression();
        System.out.println(expression);
        Date date = cronExpression.getNextValidTimeAfter(current);
        System.out.println(DateUtils.format(date));
        System.out.println(DateUtils.format(cronExpression.getNextInvalidTimeAfter(current)));
        System.out.println( cronExpression.getExpressionSummary());
*/



    }
}
