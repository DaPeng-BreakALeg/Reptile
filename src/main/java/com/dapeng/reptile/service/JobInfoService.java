package com.dapeng.reptile.service;

import com.dapeng.reptile.pojo.JobInfo;

/**
 * @ClassName: JobInfoService
 * @Description:
 * @author: renzhipeng
 * @date: 2021年01月05日 14:05:38
 */
public interface JobInfoService {

	void insertJobInfo(JobInfo jobInfo);

	void deleteTable();
}
