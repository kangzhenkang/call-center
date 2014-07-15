package com.callcenter.common.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * 模块描述:
 * User: wangyue-ds6
 * Date: 2014/5/5
 * Time: 18:01
 */
public class PropertiesUtils {
    private static final Logger log =  LoggerFactory.getLogger(PropertiesUtils.class);
    InputStream inputStream = null;
    FileOutputStream fileOutputStream = null;
    Properties properties = null;

    public Object getValue(String resourcesFilePath, String key){
        String fileRealPath = this.getClass().getResource(resourcesFilePath).getPath();
        try {
            inputStream = new BufferedInputStream(new FileInputStream(fileRealPath));
            properties = new Properties();
            properties.load(inputStream);

        } catch (Exception e) {
            if(log.isDebugEnabled()) e.printStackTrace();
            log.error("PropertiesUtils-->getValue("+resourcesFilePath+","+key+")-->error!"+e);

        }finally {
            if(null!=inputStream){
                try {
                    inputStream.close();
                } catch (Exception e) {
                    if(log.isDebugEnabled()) e.printStackTrace();
                    log.error("PropertiesUtils-->getValue("+resourcesFilePath+","+key+")-->error!"+e);
                }
            }
        }
        return properties.get(key);

    }

    public boolean setValue(String resourcesFilePath, String key,String value){
        boolean isSuccess = true;
        String fileRealPath = this.getClass().getResource(resourcesFilePath).getPath();
        try {
            inputStream = new BufferedInputStream(new FileInputStream(fileRealPath));
            properties = new Properties();
            properties.load(inputStream);
            properties.setProperty(key,value);
            fileOutputStream =  new FileOutputStream(fileRealPath);
            properties.store(fileOutputStream,null);

        } catch (Exception e) {
            if(log.isDebugEnabled()) e.printStackTrace();
            log.error("PropertiesUtils-->setValue("+resourcesFilePath+","+key+","+value+")-->error!"+e);
            isSuccess = false;

        }finally {
            if(null!=inputStream){
                try {
                    inputStream.close();
                } catch (Exception e) {
                    if(log.isDebugEnabled()) e.printStackTrace();
                    log.error("PropertiesUtils-->setValue("+resourcesFilePath+","+key+","+value+")-->error!"+e);
                    isSuccess = false;
                }
            }

            if(null!=fileOutputStream){
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                    if(log.isDebugEnabled()) e.printStackTrace();
                    log.error("PropertiesUtils-->setValue("+resourcesFilePath+","+key+","+value+")-->error!"+e);
                    isSuccess = false;
                }
            }
        }
        return isSuccess;

    }

}
