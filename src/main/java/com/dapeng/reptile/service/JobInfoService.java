package com.dapeng.reptile.service;

import com.dapeng.reptile.pojo.JobInfo;

import java.util.List;

/**
 * @ClassName: JobInfoService
 * @Description: 工作信息业务层
 * @author: DaPeng
 * @date: 2021年01月05日 下午2:05:38
 */
public interface JobInfoService {

	void insertJobInfo(JobInfo jobInfo);

	void deleteTable();

	List<JobInfo> selectAllJobInfo();

}
