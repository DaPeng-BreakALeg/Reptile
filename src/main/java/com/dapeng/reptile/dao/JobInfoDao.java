package com.dapeng.reptile.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dapeng.reptile.pojo.JobInfo;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @ClassName: JobInfoDao
 * @Description: 工作信息实体类
 * @author: DaPeng
 * @date: 2021年01月05日 下午2:07:24
 */
public interface JobInfoDao extends BaseMapper<JobInfo> {

	/**
	 * @Title: deleteTable
	 * @Description: 删除表中的数据
	 * @param:
	 * @return: void
	 * @author: DaPeng
	 * @date: 2021年01月08日 下午2:07:01
	 */
	@Update("truncate table job_info")
	void deleteTable();

	/**
	 * @Title: selectAllJobInfo
	 * @Description: 查询表中的所有数据
	 * @param:
	 * @return: List<JobInfo>
	 * @author: DaPeng
	 * @date: 2021年01月08日 下午2:07:13
	 */
	@Select("select * from job_info ORDER BY address,create_time DESC")
	List<JobInfo> selectAllJobInfo();

}
