package com.dapeng.reptile.controller;

import com.dapeng.reptile.job.JobInfoReptile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @ClassName: pachong
 * @Description:
 * @author: renzhipeng
 * @date: 2021年01月05日 15:53:21
 */
@RestController
public class pachongController {

	@Autowired
	private JobInfoReptile jobInfoReptile;

	@RequestMapping("list")
	public String pachongT() {
		System.err.println("开始"+ LocalDateTime.now());
		jobInfoReptile.pachong();
		System.err.println("结束"+ LocalDateTime.now());
		return "success";
	}
}
