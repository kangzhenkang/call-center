package com.jd.synctask.web;


import com.callcenter.api.DataContentService;
import com.callcenter.api.domain.DataContent;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: wangyueinfo
 * Date: 14-3-6
 * Time: 下午4:39
 */

public class DubboTest {
    public static void  main(String args[]){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-config-dubbo-test.xml");
        DataContentService dataContentService = (DataContentService) context.getBean("dataContentService");
        if(null !=dataContentService) {
            DataContent dataContent = new DataContent();
            dataContent.setBizType("test");
            dataContent.setBizStatus("F");
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<3000;i++){
                sb.append("com.jd.sb");
            }
            dataContent.setBizData(sb.toString());
            dataContent.setCallMethodName("1");
            dataContent.setCallServiceName("1");
            dataContent.setMqDestSysId("1");
            dataContent.setSyncPlan("1");
            dataContent.setTaskType("CYCLE");
            if(!dataContentService.writeData(dataContent)){
                System.err.print("调用异常");
            }
        }
    }
    @Test
    public void init(){



    }
}
