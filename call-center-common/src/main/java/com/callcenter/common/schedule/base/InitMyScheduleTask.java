
package com.callcenter.common.schedule.base;

import com.taobao.pamirs.schedule.strategy.TBScheduleManagerFactory;

public interface InitMyScheduleTask {
	
	Object invoke(TBScheduleManagerFactory factory);

}
