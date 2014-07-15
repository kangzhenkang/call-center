package com.callcenter.controller;

import com.callcenter.CustomDateEditor;
import com.callcenter.common.tools.CommonConstants;
import com.callcenter.api.domain.DataContent;
import com.callcenter.domain.DataRecord;
import com.callcenter.domain.SyncTask;
import com.callcenter.domain.common.Message;
import com.callcenter.domain.common.Page;
import com.callcenter.service.DataRecordService;
import com.callcenter.service.SyncTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 *SyncTask controller层
 * @author wangyue-ds6
 * @since 2014-02-28
 */
@Controller
@RequestMapping(value = "/syncTask")
public class SyncTaskController{
	private static final Logger LOGGER = LoggerFactory.getLogger(SyncTaskController.class);
	@Resource private SyncTaskService syncTaskService;
	@Resource private DataRecordService dataRecordService;
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(true));
	}
	
	/**
	 * 列表展示
	 * @param syncTask 实体对象
	 * @param page 分页对象
	 * @return
	 */
	
	@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
	public String list(SyncTask syncTask,Page<SyncTask> page,Model view) throws Exception{
		try {
			view.addAttribute("syncTask",syncTask);
			view.addAttribute("page", syncTaskService.selectPage(syncTask,page));
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			throw e;
		}finally{
		}	
		return "syncTask/list";
	}
	
	/**
	 * 响应新增(修改)页面
	 * @param id 对象编号
	 * @return
	 */
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public String edit(@PathVariable Long id,Model view) throws Exception{
		try {
			if(id != null && id > 0) {
				SyncTask syncTask = syncTaskService.selectEntry(id);
				if(syncTask == null) {
//					return toJSON(Message.failure("您要修改的数据不存在或者已被删除!"));
					return null;
				}
                queryDataRecordInfo(syncTask);
				view.addAttribute("syncTask",syncTask);
			}			
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			throw e;
		}finally{
		}

		return "syncTask/edit";
	}
	
	/**
	 * 通过编号删除对象
	 * @param id 对象编号
	 * @return
	 */
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public @ResponseBody Message del(@PathVariable Long id,Model view) throws Exception{
    	Message msg = null;
    	try {
			int res = syncTaskService.deleteSyncTaskAndDataById(id);
			msg  = res > 0 ? Message.success() : Message.failure();
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			msg = Message.failure();
		}finally{
		}

		return msg;
	}
	
	/**
	 * 通过编号查看对象
	 * @param id 对象编号
	 * @return
	 */
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String view(@PathVariable Long id,Model view) throws Exception{
		try {
			SyncTask syncTask = syncTaskService.selectEntry(id);
			if(syncTask == null) {
				return null;
			}
            queryDataRecordInfo(syncTask);
			view.addAttribute("syncTask",syncTask);
		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			throw e;
		}finally{
		}

		return "syncTask/view";
	}

    private void queryDataRecordInfo(SyncTask syncTask) {
        DataRecord dr4Query = new DataRecord();
        dr4Query.setTaskId(syncTask.getId());
        dr4Query.setYn(CommonConstants.YN_NOT_DELETE);
        List<DataRecord> dataRecordList = dataRecordService.selectEntryList(dr4Query);
        if (1 == dataRecordList.size()) {
            syncTask.setBizDataStr(dataRecordList.get(0).getBizData());
        } else if (dataRecordList.size() > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            for (DataRecord dr : dataRecordList) {
                stringBuilder.append(dr.getBizData());
            }
            syncTask.setBizDataStr(stringBuilder.toString());
        }
    }

    /**
	 * 保存方法
	 * @param syncTask 实体对象
	 * @return
	 */
	
	@RequestMapping(value="/save",method = {RequestMethod.POST,RequestMethod.GET},produces="application/json")
	public @ResponseBody Message save(SyncTask syncTask,Model view) throws Exception{
    	Message msg= null;
    	try {
            DataContent dataContent = initDataContent4Insert(syncTask);
            if(null != syncTask.getId() && syncTask.getId() >0 ){
                dataContent.setTaskId(syncTask.getId());
                msg  = dataRecordService.updateDataRecord(dataContent) ? Message.success() : Message.failure();
            }   else {
                msg  = dataRecordService.writeDataRecord(dataContent) ? Message.success() : Message.failure();
            }

		} catch (Exception e) {
			LOGGER.error("失败:"+e.getMessage(),e);
			msg = Message.failure();
		}finally{
		}
		return msg;
	}

    /**
     * 启动或者重新启动所有任务
     * @return
     */

    @RequestMapping(value="/startOrStopAllTask/{optType}",method = {RequestMethod.POST,RequestMethod.GET},produces="application/json")
    public @ResponseBody Message startOrStopAllTask(@PathVariable Integer optType) throws Exception{
        Message msg = null;
        try {
            SyncTask syncTask = new SyncTask();
            if(0 == optType){//停止所有任务
               syncTask.setSyncStatus(CommonConstants.SYNC_STATUS_1);
            }else if(1==optType){ //启动所有任务
                syncTask.setSyncStatus(CommonConstants.SYNC_STATUS_2);
            }
            int res = syncTaskService.updateByKey(syncTask);
            msg  = res > 0 ? Message.success() : Message.failure();
        } catch (Exception e) {
            LOGGER.error("失败:"+e.getMessage(),e);
            msg = Message.failure();
        }finally{
        }

        return msg;
    }
    /**
     * 启动或者重新启动所有任务
     * @return
     */

    @RequestMapping(value="/resetStatusAndFailCountById/{id}",method = {RequestMethod.POST,RequestMethod.GET},produces="application/json")
    public @ResponseBody Message resetStatusAndFailCountById(@PathVariable Long id) throws Exception{
        Message msg = null;
        try {
            int res = syncTaskService.resetStatusAndFailCountById(id);
            msg  = res > 0 ? Message.success() : Message.failure();
        } catch (Exception e) {
            LOGGER.error("失败:"+e.getMessage(),e);
            msg = Message.failure();
        }finally{
        }

        return msg;
    }

    /**
     * 初始化DataContent类作为插入数据
     * @param syncTask
     * @return
     */
    private DataContent initDataContent4Insert(SyncTask syncTask) {
        DataContent dataContent = new DataContent();
        dataContent.setBizType(syncTask.getBizType());
        dataContent.setSyncType(syncTask.getSyncType());
        dataContent.setCallServiceName(syncTask.getCallServiceName());
        dataContent.setCallMethodName(syncTask.getCallMethodName());
        dataContent.setBizStatus(syncTask.getBizStatus());
        dataContent.setTaskType(syncTask.getTaskType());
        dataContent.setSyncPlan(syncTask.getSyncPlan());
        dataContent.setMqDestSysId(syncTask.getMqDestSysId());
        dataContent.setSyncStatus(syncTask.getSyncStatus());
        dataContent.setBizData(syncTask.getBizDataStr());
        dataContent.setFailCount(syncTask.getFailCount());
        dataContent.setSyncCount(syncTask.getSyncCount());
        dataContent.setMobile(syncTask.getMobile());
        dataContent.setEmail(syncTask.getEmail());
        return dataContent;
    }

}