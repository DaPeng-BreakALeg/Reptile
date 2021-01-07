package com.dapeng.reptile.schedule;

import com.dapeng.reptile.job.JobInfoReptile;
import com.dapeng.reptile.service.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @ClassName: SaticScheduleTask
 * @Description:
 * @author: renzhipeng
 * @date: 2021年01月06日 13:30:14
 */
@Component
public class JobScheduleTask {

	@Autowired
	private JobInfoReptile jobInfoReptile;
	@Autowired
	private JobInfoService jobInfoService;

	//3.添加定时任务
	@Scheduled(cron = "0 39 15 * * ? ")
	private void jobInfoInsertTasks() {
		System.out.println("工作信息爬虫开始----------------"+ LocalDateTime.now());
		jobInfoReptile.pachong();
		System.out.println("工作信息爬虫结束----------------"+ LocalDateTime.now());
	}

	@Scheduled(cron = "0 8 17 * * ? ")
	private void jobInfoDeleteTasks() {
		System.out.println("开始清空job_info数据库----------------"+ LocalDateTime.now());
		jobInfoService.deleteTable();
		System.out.println("job_info数据库清空完成----------------"+ LocalDateTime.now());
	}

}
