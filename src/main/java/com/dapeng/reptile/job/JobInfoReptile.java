package com.dapeng.reptile.job;

import com.dapeng.reptile.config.HttpClientDownloader;
import com.dapeng.reptile.pipeline.LaGouPipe;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName: JobInfoReptile
 * @Description: 爬虫逻辑处理类
 * @author: DaPeng
 * @date: 2021年01月04日 下午:30:54
 */
@Component
public class JobInfoReptile implements PageProcessor {

	int flag = 0;
	int sum = 0;
	int sub = 0;
	// 拉勾网采用ajax请求传输的数据，且有反爬机制，需要自己自定义请求头
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).addHeader(":authority", "www.lagou.com")
			.addHeader(":method", "POST")
			.addHeader(":path", "/jobs/positionAjax.json?px=default&city=%E5%8C%97%E4%BA%AC&needAddtionalResult=false")
			.addHeader(":scheme", "https").addHeader("accept", "application/json, text/javascript, */*; q=0.01")
			.addHeader("accept-encoding", "gzip, deflate, br").addHeader("accept-language", "zh-CN,zh;q=0.9")
			// .addHeader("Connection","keep-alive")
			// .addHeader("content-length","23")
			.addHeader("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
			// 如果cookie失效，请自己去搜索cookie
			.addHeader("cookie",
					"user_trace_token=20201130094645-7826ccb8-0575-49ec-ad01-6ae859f843c9; smidV2=202011300946462a53d03ef518a34211b5b33b47c220b5001bee293588ca980; LGUID=20210104163854-4a44df5e-3936-40a3-ac2a-9eb1271bb22f; _ga=GA1.2.1594505211.1609749535; RECOMMEND_TIP=true; index_location_city=%E6%88%90%E9%83%BD; gate_login_token=58d06be1cf7a5156f1e6f5c79425eea230258320d2c85f6fff41e339e4af44ec; LG_LOGIN_USER_ID=ef591b0f211b5fbfdcae3df9cc07afdb4521d4f12e583c58fe9970f0ed985b21; LG_HAS_LOGIN=1; showExpriedIndex=1; showExpriedCompanyHome=1; showExpriedMyPublish=1; hasDeliver=98; privacyPolicyPopup=false; JSESSIONID=ABAAABAABEIABCI19CB56BD9ACBA59A2DB1E50F590E282E; WEBTJ-ID=20210108150605-176e0d10cd25d1-084006470ec366-c791e37-3686400-176e0d10cd3d9a; _putrc=4A767B6ED51B8042123F89F2B170EADC; sensorsdata2015session=%7B%7D; Hm_lvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1609808949,1609815284,1609896756,1610089566; LGSID=20210108150604-0f1f1c8b-e6a9-45aa-b9d8-3059c2862346; login=true; unick=%E4%BB%BB%E5%BF%97%E9%B9%8F; _gid=GA1.2.2129501061.1610089566; X_HTTP_TOKEN=d7a4cf1a6b1f35157101900161bb0805ccae35636e; Hm_lpvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1610091019; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%2218074726%22%2C%22first_id%22%3A%2217616d4b3cbc84-04c85c309b6106-c791e37-3686400-17616d4b3ccca9%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%2C%22%24os%22%3A%22Windows%22%2C%22%24browser%22%3A%22Chrome%22%2C%22%24browser_version%22%3A%2287.0.4280.66%22%2C%22lagou_company_id%22%3A%22%22%7D%2C%22%24device_id%22%3A%2217616d4b3cbc84-04c85c309b6106-c791e37-3686400-17616d4b3ccca9%22%7D; _gat=1; LGRID=20210108153018-5469504a-9c81-4f1a-afe7-23b439f16711; SEARCH_ID=82d23fa3027a4efba85fdb4af6d5dee3")
			// .addHeader("Host", "www.lagou.com")
			.addHeader("origin", "https://www.lagou.com")
			.addHeader("referer", "https://www.lagou.com/jobs/list_Java?px=default")
			.addHeader("sec-fetch-dest", "empty").addHeader("sec-fetch-mode", "cors")
			.addHeader("sec-fetch-site", "same-origin").addHeader("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.66 Safari/537.36")
			.addHeader("x-anit-forge-code", "0").addHeader("x-anit-forge-token", "None")
			.addHeader("x-requested-with", "XMLHttpRequest");

	/**
	 * @Title: process
	 * @Description: 获取Ajax 返回数据里面的数据集
	 * @param: page
	 * @return: void
	 * @author: DaPeng
	 * @date: 2021年01月08日 下午2:33:15
	 */
	@Override
	public void process(Page page) {
		// 开始下载各个地区的页面
		this.processChengDu(page);
		this.processHangZhou(page);
		this.processChongQing(page);
		// 获取网页中ajax信息中的信息
		page.putField("positionName",
				new JsonPathSelector("$.content.positionResult.result[*].positionName").selectList(page.getRawText()));
		page.putField("workYear",
				new JsonPathSelector("$.content.positionResult.result[*].workYear").selectList(page.getRawText()));
		page.putField("salary",
				new JsonPathSelector("$.content.positionResult.result[*].salary").selectList(page.getRawText()));
		page.putField("address",
				new JsonPathSelector("$.content.positionResult.result[*].city").selectList(page.getRawText()));
		page.putField("district",
				new JsonPathSelector("$.content.positionResult.result[*].district").selectList(page.getRawText()));
		page.putField("createTime",
				new JsonPathSelector("$.content.positionResult.result[*].createTime").selectList(page.getRawText()));
		page.putField("companyName", new JsonPathSelector("$.content.positionResult.result[*].companyFullName")
				.selectList(page.getRawText()));
		page.putField("discription",
				new JsonPathSelector("$.content.positionResult.result[*].secondType").selectList(page.getRawText()));
	}

	/**
	 * @Title: getSite
	 * @Description: TODO
	 * @param:
	 * @return: Site
	 * @author: DaPeng
	 * @date: 2021年01月11日 下午:16:56
	 */
	@Override
	public Site getSite() {
		return site;
	}

	/**
	 * @Title: pachong
	 * @Description: 爬虫主程序
	 * @param:
	 * @return: void
	 * @author: DaPeng
	 * @date: 2021年01月08日 下午2:36:15
	 */
	public void pachong() {
		// 如果IP被禁止了，可以自定义代理连接
		// HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
		// httpClientDownloader.setProxyProvider(
		// 		SimpleProxyProvider.from(new Proxy("222.249.238.138", 8080), new Proxy("218.93.119.164", 9002),new Proxy("123.57.210.164",3128)));
		Spider.create(new JobInfoReptile())
				//从"https://www.lagou.com/jobs/list_Java?px=default&city=%E5%8C%97%E4%BA%AC"开始抓
				.addUrl("https://www.lagou.com/jobs/positionAjax.json?px=default&city=%E6%88%90%E9%83%BD&needAddtionalResult=false")
				.setDownloader(new HttpClientDownloader()).addPipeline(new LaGouPipe())
				//开启10个线程抓取
				.thread(10)
				//启动爬虫
				.run();
	}

	/**
	 * @Title: processChengDu
	 * @Description: 下载成都地区Java的进程
	 * @param: page
	 * @return: void
	 * @author: DaPeng
	 * @date: 2021年01月08日 下午2:36:52
	 */
	private void processChengDu(Page page) {
		if (flag == 0) {
			Request[] requests = new Request[9];
			// 查看网站的请求需要的参数信息，举一反三
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < requests.length; i++) {
				requests[i] = new Request(
						"https://www.lagou.com/jobs/positionAjax.json?px=default&city=杭州&needAddtionalResult=false");
				requests[i].setMethod(HttpConstant.Method.POST);
				if (i == 0) {
					map.put("first", "true");
					map.put("pn", i + 1);
					map.put("kd", "java");
					requests[i].setRequestBody(HttpRequestBody.form(map, "utf-8"));
					page.addTargetRequest(requests[i]);
				} else {
					map.put("first", "false");
					map.put("pn", i + 1);
					map.put("kd", "java");
					requests[i].setRequestBody(HttpRequestBody.form(map, "utf-8"));
					page.addTargetRequest(requests[i]);
				}
			}
			flag++;
		}
	}

	/**
	 * @Title: processChengDu
	 * @Description: 下载杭州地区Java网页的进程
	 * @param: page
	 * @return: void
	 * @author: DaPeng
	 * @date: 2021年01月08日 下午2:36:52
	 */
	private void processHangZhou(Page page) {
		if (sum == 0) {
			Request[] requests = new Request[9];
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < requests.length; i++) {
				requests[i] = new Request(
						"https://www.lagou.com/jobs/positionAjax.json?px=default&city=杭州&needAddtionalResult=false");
				requests[i].setMethod(HttpConstant.Method.POST);
				if (i == 0) {
					map.put("first", "true");
					map.put("pn", i + 1);
					map.put("kd", "java");
					requests[i].setRequestBody(HttpRequestBody.form(map, "utf-8"));
					page.addTargetRequest(requests[i]);
				} else {
					map.put("first", "false");
					map.put("pn", i + 1);
					map.put("kd", "java");
					requests[i].setRequestBody(HttpRequestBody.form(map, "utf-8"));
					page.addTargetRequest(requests[i]);
				}
			}
			sum++;
		}
	}

	/**
	 * @Title: processChengDu
	 * @Description: 下载重庆地区Java网页的进程
	 * @param: page
	 * @return: void
	 * @author: DaPeng
	 * @date: 2021年01月08日 下午2:36:52
	 */
	private void processChongQing(Page page) {
		if (sub == 0) {
			Request[] requests = new Request[9];
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < requests.length; i++) {
				requests[i] = new Request(
						"https://www.lagou.com/jobs/positionAjax.json?px=default&city=重庆&needAddtionalResult=false");
				requests[i].setMethod(HttpConstant.Method.POST);
				if (i == 0) {
					map.put("first", "true");
					map.put("pn", i + 1);
					map.put("kd", "java");
					requests[i].setRequestBody(HttpRequestBody.form(map, "utf-8"));
					page.addTargetRequest(requests[i]);
				} else {
					map.put("first", "false");
					map.put("pn", i + 1);
					map.put("kd", "java");
					requests[i].setRequestBody(HttpRequestBody.form(map, "utf-8"));
					page.addTargetRequest(requests[i]);
				}
			}
			sub++;
		}
	}

}
