
package com.callcenter.common.schedule.base;


import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TaskItemDefine;
import com.taobao.pamirs.schedule.strategy.ScheduleStrategy;
import com.taobao.pamirs.schedule.strategy.TBScheduleManagerFactory;
import com.taobao.pamirs.schedule.taskmanager.ScheduleTaskType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public abstract class BaseSingleTask<T> implements IScheduleTaskDealSingle<T>,
        InitMyScheduleTask {
    
	protected ScheduleTaskType baseTaskType = new ScheduleTaskType();

	protected ScheduleStrategy strategy = new ScheduleStrategy();

	public void setBaseTaskType(ScheduleTaskType baseTaskType) {
		this.baseTaskType = baseTaskType;
	}

	public void setStrategy(ScheduleStrategy strategy) {
		this.strategy = strategy;
	}

	public ScheduleTaskType getBaseTaskType() {
		return baseTaskType;
	}

	public ScheduleStrategy getStrategy() {
		return strategy;
	}

	public Object invoke(TBScheduleManagerFactory factory) {
		try {
			factory.getScheduleDataManager().updateBaseTaskType(baseTaskType);
			factory.getScheduleStrategyManager().updateScheduleStrategy(
					strategy);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将任务类型，转换为对应的任务类型
	 * @param tasks
	 * @return
	 * @author wangyue-ds6
	 * @date 2012-10-29
	 */
	public List<T> convertList(Object [] tasks){
	    List<T> result = new ArrayList<T>();
	    for(Object obj : tasks){
	        result.add((T)obj);
	    }
	    return result;
	}
	
	public Comparator getComparator() {
		return null;
	}

    public boolean isMyTask(Long id, List<TaskItemDefine> taskItemList, int taskCount) {
        if (taskCount == taskItemList.size()) {
            return true;
        }
        Long m = id % taskCount;
        for (TaskItemDefine o : taskItemList) {
            if (m == Long.parseLong(o.getTaskItemId())) {
                return true;
            }
        }
        return false;
    }
}


