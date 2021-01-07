package com.dapeng.reptile.service.impl;

import com.dapeng.reptile.dao.JobInfoDao;
import com.dapeng.reptile.pojo.JobInfo;
import com.dapeng.reptile.service.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: JobInfoServiceImpl
 * @Description:
 * @author: renzhipeng
 * @date: 2021年01月05日 14:06:22
 */
@Service
public class JobInfoServiceImpl implements JobInfoService {

	@Autowired
	private JobInfoDao jobInfoDao;

	@Override
	public void insertJobInfo(JobInfo jobInfo) {
		jobInfoDao.insert(jobInfo);
	}

	@Override
	public void deleteTable() {
		jobInfoDao.deleteTable();
	}
}
