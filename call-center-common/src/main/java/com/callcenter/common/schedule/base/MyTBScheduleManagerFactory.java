
package com.callcenter.common.schedule.base;

import com.taobao.pamirs.schedule.strategy.TBScheduleManagerFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;


public class MyTBScheduleManagerFactory extends TBScheduleManagerFactory {

    private ApplicationContext applicationcontext;

    private static final Log log = LogFactory
            .getLog(MyTBScheduleManagerFactory.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationcontext)
            throws BeansException {
        super.setApplicationContext(applicationcontext);
        this.applicationcontext = applicationcontext;
    }

    @Override
    public void init() throws Exception {

        super.init();

        while (this.isZookeeperInitialSucess() == false) {
            log.error("ZookeeperInitial fail ...");
            Thread.currentThread().sleep(1000);
        }
        this.stopServer(null);
        String[] names = applicationcontext
                .getBeanNamesForType(InitMyScheduleTask.class);
        for (String name : names) {
            InitMyScheduleTask task = (InitMyScheduleTask) applicationcontext
                    .getBean(name);
            task.invoke(this);
        }

    }

}
