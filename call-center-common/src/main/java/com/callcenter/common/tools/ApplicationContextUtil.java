package com.callcenter.common.tools;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 应用程序上下文 帮助类
 * User: wangyueinfo
 * Date: 14-3-6
 * Time: 下午2:14
 */
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;//声明一个静态变量保存

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        ApplicationContextUtil.context = context;
    }

    public static ApplicationContext getContext() {
        return context;
    }
}