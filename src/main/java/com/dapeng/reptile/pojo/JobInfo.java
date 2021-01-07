package com.dapeng.reptile.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: JobInfo
 * @Description: JobInfo
 * @author: renzhipeng
 * @date: 2021年01月04日 16:33:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "job_info")
public class JobInfo {

	@TableField(value = "position_name")
	private String positionName;
	@TableField(value = "work_year")
	private String workYear;
	@TableField(value = "salary")
	private String salary;
	@TableField(value = "address")
	private String address;
	@TableField(value = "district")
	private String district;
	@TableField(value = "create_time")
	private String createTime;
	@TableField(value = "company_name")
	private String companyName;
	@TableField(value = "discription")
	private String discription;
}
