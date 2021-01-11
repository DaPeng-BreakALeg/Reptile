package com.dapeng.reptile.schedule;

import com.dapeng.reptile.export.Excel2007XlsxUtil;
import com.dapeng.reptile.job.JobInfoReptile;
import com.dapeng.reptile.pojo.JobInfo;
import com.dapeng.reptile.service.EmailService;
import com.dapeng.reptile.service.JobInfoService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: SaticScheduleTask
 * @Description:
 * @author: DaPeng
 * @date: 2021年01月06日 13:30:14
 */
@Slf4j
@Component
public class JobScheduleTask {

	@Autowired
	private EmailService emailService;

	@Autowired
	private JobInfoReptile jobInfoReptile;

	@Autowired
	private JobInfoService jobInfoService;

	@Autowired
	private TemplateEngine templateEngine;

	/**
	 * @Title: jobInfoInsertTasks
	 * @Description: 爬虫定时任务
	 * @param:
	 * @return: void
	 * @author: DaPeng
	 * @date: 2021年01月11日 下午4:22:10
	 */
	@Scheduled(cron = "0 45 15 * * ? ")
	private void jobInfoInsertTasks() {
		System.out.println("开始清空job_info数据库----------------" + LocalDateTime.now());
		jobInfoService.deleteTable();
		System.out.println("job_info数据库清空完成----------------" + LocalDateTime.now());
		System.out.println("工作信息爬虫开始----------------" + LocalDateTime.now());
		jobInfoReptile.pachong();
		System.out.println("工作信息爬虫结束----------------" + LocalDateTime.now());
	}

	/**
	 * @Title: SendEmail
	 * @Description: 邮件发送定时任务
	 * @param:
	 * @return: void
	 * @author: DaPeng
	 * @date: 2021年01月11日 下午4:22:21
	 */
	@Scheduled(cron = "0 46 15 * * ? ")
	private void SendEmail() {
		List<JobInfo> jobInfoList = jobInfoService.selectAllJobInfo();
		List<String> headersName = new ArrayList<>();
		List<String> headersId = new ArrayList<>();
		headersName = Arrays.asList("工作名称", "工作经验", "薪资", "城市地点", "城市地区", "发布时间", "公司名称", "岗位描述");
		headersId = Arrays
				.asList("positionName", "workYear", "salary", "address", "district", "createTime", "companyName",
						"discription");
		String dataTime = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
		String fileName = "拉勾网-" + dataTime + "-Java岗位明细数据.xlsx";

		// 邮箱集合
		Set<String> emails = new HashSet<>();
		// emails.add("3072813329@qq.com");
		emails.add("dapeng_rzp@qq.com");
		doSendEmail(genertorExcelStream(headersName, headersId, jobInfoList, fileName), emails, fileName);
	}

	/**
	 * @Title: genertorExcelStream
	 * @Description: 生成附件Excel
	 * @param: headersName
	 * @param: headersId
	 * @param: dataList
	 * @param: fileName
	 * @return: ByteArrayInputStream
	 * @author: DaPeng
	 * @date: 2021年01月11日 下午4:21:29
	 */
	private synchronized ByteArrayInputStream genertorExcelStream(List<String> headersName, List<String> headersId,
			List<? extends JobInfo> dataList, String fileName) {
		String dataTime = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
		String title = "拉勾网-" + dataTime + "-Java岗位明细数据";
		List<Map<String, Object>> dtoList = this.TtoMap(dataList);
		return Excel2007XlsxUtil.exportReportExcelToEmail(title, headersName, headersId, dtoList, fileName);
	}

	/**
	 * @Title: TtoMap
	 * @Description: 实体类转换成Map
	 * @param: items
	 * @return: List<Map<String,Object>>
	 * @author: DaPeng
	 * @date: 2021年01月11日 下午4:21:42
	 */
	private static List<Map<String, Object>> TtoMap(List items) {
		if (CollectionUtils.isNotEmpty(items)) {
			List<Map<String, Object>> it = new ArrayList<>(items.size());
			for (int j = 0; j < items.size(); j++) {
				it.add(BeanMap.create(items.get(j)));
			}
			return it;
		}
		return items;
	}

	/**
	 * @Title: doSendEmail
	 * @Description: 邮件发送逻辑
	 * @param: bais
	 * @param: emails
	 * @param: fileName
	 * @return: void
	 * @author: DaPeng
	 * @date: 2021年01月11日 下午4:22:46
	 */
	@SneakyThrows
	private void doSendEmail(ByteArrayInputStream bais, Set<String> emails, String fileName) {
		byte[] byteArray = IOUtils.toByteArray(bais);
		String dataTime = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
		ByteArrayResource byteArrayResource = new ByteArrayResource(byteArray);
		Context context = new Context();
		context.setVariable("sendTime", dataTime);
		String emailContent = templateEngine.process("warnEmail", context);
		String[] emailArray = emails.stream().toArray(String[]::new);
		emailService.sendAttachmentsManyMail(emailArray, "拉勾Java职位信息", emailContent, fileName, byteArrayResource);
	}

}
