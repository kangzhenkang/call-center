
package com.callcenter.common.schedule.base;


import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import com.taobao.pamirs.schedule.strategy.ScheduleStrategy;
import com.taobao.pamirs.schedule.strategy.TBScheduleManagerFactory;
import com.taobao.pamirs.schedule.taskmanager.ScheduleTaskType;

import java.math.BigInteger;
import java.util.*;


public abstract class BaseMultiTask<T> implements IScheduleTaskDealMulti<T>,
        InitMyScheduleTask {
    public final static String SCOPE_START_INDEX = "scopeStartIndex";

    public final static String SCOPE_END_INDEX = "scopeEndIndex";
    public final static int MAX_QUEUE_ID = 1000;



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
	public Comparator getComparator() {
		return null;
	}

    public  List<String> getScopeByQueueCondition(Integer queueNum, List<TaskItemDefine> queryCondition) throws Exception {
        List<String> conditionList = new ArrayList<String>();
        String condition = "";
        if (queryCondition.size() == 0 || queueNum == null || queueNum.intValue() == 0) {
            throw new Exception("没有队列条件或没有队列!");
        }
        if (queryCondition.size() == queueNum.intValue()) {
            condition = "HASH_CODE >= 0 and  HASH_CODE < " + MAX_QUEUE_ID;
            conditionList.add(condition);
            return conditionList;
        } else {
            List<Map<String, Long>> queueList = getDirectiveQueueScope(queueNum,queryCondition);
            for (Map<String, Long> qm: queueList) {
                condition = "HASH_CODE >= " + qm.get(SCOPE_START_INDEX) + " and  HASH_CODE < " + qm.get(SCOPE_END_INDEX);
                conditionList.add(condition);
            }
            return conditionList;
        }
    }

    public static List<Map<String, Long>> getDirectiveQueueScope(Integer queueNum, List<TaskItemDefine> queryCondition) {
        List<Map<String, Long>> rtnConditionList = new ArrayList<Map<String, Long>>();
        if (queryCondition.size() == 0 || queueNum == null || queueNum.intValue() == 0) {
            return null;
        }
        /** 当前机器获取到所有队列数的话就获取全部数据 */
        // if (queryCondition.size() == queueNum.intValue()) {
        // long startIndex = 0;
        // long endIndex = 1000;
        // Map<String, Long> scopeMap = new HashMap<String, Long>();
        // scopeMap.put(SCOPE_START_INDEX,startIndex);
        // scopeMap.put(SCOPE_END_INDEX,endIndex);
        // rtnConditionList.add(scopeMap);
        // return rtnConditionList;
        // } else {
        int baseNum = BigInteger.valueOf(1000).divide(BigInteger.valueOf(queueNum)).intValue();
        for (TaskItemDefine q: queryCondition) {
            long startIndex;
            long endIndex;
            Map<String, Long> scopeMap = new HashMap<String, Long>();
            int num = Integer.parseInt(q.getTaskItemId());
            startIndex = num * baseNum;
            if (num == queueNum - 1) {
                endIndex = 1000;
            } else {
                endIndex = (num + 1) * baseNum;
            }
            scopeMap.put(SCOPE_START_INDEX,startIndex);
            scopeMap.put(SCOPE_END_INDEX,endIndex);
            rtnConditionList.add(scopeMap);
        }
        return rtnConditionList;
        // }
    }



}


