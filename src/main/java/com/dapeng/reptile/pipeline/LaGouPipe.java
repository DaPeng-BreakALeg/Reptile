package com.dapeng.reptile.pipeline;

import com.dapeng.reptile.pojo.JobInfo;
import com.dapeng.reptile.service.impl.JobInfoServiceImpl;
import com.dapeng.reptile.util.SpringUtil;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName: LaGouPipe
 * @Description:
 * @author: renzhipeng
 * @date: 2021年01月04日 16:59:30
 */
@Component
public class LaGouPipe implements Pipeline {

	private Lock lock = new ReentrantLock();
	private static final Logger log = LoggerFactory.getLogger(JobInfoServiceImpl.class);

	@Override
	public void process(ResultItems resultItems, Task task) {
		String positionnameList = resultItems.get("positionName").toString();
		String workYearList = resultItems.get("workYear").toString();
		String salaryList = resultItems.get("salary").toString();
		String addressList = resultItems.get("address").toString();
		String districtList = resultItems.get("district").toString();
		String createTimeList = resultItems.get("createTime").toString();
		String companyNameList = resultItems.get("companyName").toString();
		String discriptionList = resultItems.get("discription").toString();

		String[] positionnames = this.dataProcessing(positionnameList);
		String[] workYears = this.dataProcessing(workYearList);
		String[] salarys = this.dataProcessing(salaryList);
		String[] addresss = this.dataProcessing(addressList);
		String[] districts = this.dataProcessing(districtList);
		String[] createTimes = this.dataProcessing(createTimeList);
		String[] companyNames = this.dataProcessing(companyNameList);
		String[] discriptions = this.dataProcessing(discriptionList);
		List<JobInfo> jobInfoList = new ArrayList<>();
		ApplicationContext context = SpringUtil.getApplicationContext();
		JobInfoServiceImpl jobInfoService = context.getBean(JobInfoServiceImpl.class);
		JobInfo jobInfo;
		lock.lock();
		for (int i = 0; i < positionnames.length; i++) {
			jobInfo = new JobInfo();
			jobInfo.setPositionName(positionnames[i]);
			jobInfo.setWorkYear(workYears[i]);
			jobInfo.setSalary(salarys[i]);
			jobInfo.setAddress(addresss[i]);
			jobInfo.setDistrict(districts[i]);
			jobInfo.setCreateTime(createTimes[i]);
			jobInfo.setCompanyName(companyNames[i]);
			jobInfo.setDiscription(discriptions[i]);
			jobInfoList.add(jobInfo);
			System.out.println(jobInfo.toString());
			jobInfoService.insertJobInfo(jobInfo);
		}
		lock.unlock();
	}

	private String[] dataProcessing(String str) {
		str = str.substring(1);
		str = str.substring(0, str.length() - 1);
		String[] split = str.split(", ");
		return split;
	}
}
