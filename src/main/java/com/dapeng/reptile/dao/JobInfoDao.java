package com.dapeng.reptile.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dapeng.reptile.pojo.JobInfo;
import org.apache.ibatis.annotations.Update;

/**
 * @ClassName: JobInfoDao
 * @Description:
 * @author: renzhipeng
 * @date: 2021年01月05日 14:07:24
 */
public interface JobInfoDao extends BaseMapper<JobInfo> {
	@Update("truncate table job_info")
	void deleteTable();
}
