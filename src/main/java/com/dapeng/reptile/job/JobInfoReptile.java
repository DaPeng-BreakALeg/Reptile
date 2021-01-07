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

/**
 * @ClassName: JobInfoReptile
 * @Description:
 * @author: renzhipeng
 * @date: 2021年01月04日 16:30:54
 */
@Component
public class JobInfoReptile implements PageProcessor {

	int flag = 0;
	// 拉勾网采用
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).addHeader(":authority", "www.lagou.com")
			.addHeader(":method", "POST")
			.addHeader(":path", "/jobs/positionAjax.json?px=default&city=%E5%8C%97%E4%BA%AC&needAddtionalResult=false")
			.addHeader(":scheme", "https").addHeader("accept", "application/json, text/javascript, */*; q=0.01")
			.addHeader("accept-encoding", "gzip, deflate, br").addHeader("accept-language", "zh-CN,zh;q=0.9")
			// .addHeader("Connection","keep-alive")
			// .addHeader("content-length","23")
			.addHeader("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
			.addHeader("cookie","user_trace_token=20201130094645-7826ccb8-0575-49ec-ad01-6ae859f843c9; smidV2=202011300946462a53d03ef518a34211b5b33b47c220b5001bee293588ca980; LGUID=20210104163854-4a44df5e-3936-40a3-ac2a-9eb1271bb22f; _ga=GA1.2.1594505211.1609749535; _gid=GA1.2.519885100.1609749535; RECOMMEND_TIP=true; index_location_city=%E6%88%90%E9%83%BD; gate_login_token=58d06be1cf7a5156f1e6f5c79425eea230258320d2c85f6fff41e339e4af44ec; LG_LOGIN_USER_ID=ef591b0f211b5fbfdcae3df9cc07afdb4521d4f12e583c58fe9970f0ed985b21; LG_HAS_LOGIN=1; showExpriedIndex=1; showExpriedCompanyHome=1; showExpriedMyPublish=1; hasDeliver=98; privacyPolicyPopup=false; JSESSIONID=ABAAABAABAGABFADC9AA3F7A86DA237AE37BABE7DDB8E91; WEBTJ-ID=20210106093236-176d55303cd2fe-040fe1b520e459-c791e37-3686400-176d55303ce77c; _putrc=4A767B6ED51B8042123F89F2B170EADC; sensorsdata2015session=%7B%7D; Hm_lvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1609749535,1609808949,1609815284,1609896756; login=true; unick=%E4%BB%BB%E5%BF%97%E9%B9%8F; TG-TRACK-CODE=search_code; X_HTTP_TOKEN=d7a4cf1a6b1f35157752199061bb0805ccae35636e; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%2218074726%22%2C%22first_id%22%3A%2217616d4b3cbc84-04c85c309b6106-c791e37-3686400-17616d4b3ccca9%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%2C%22%24os%22%3A%22Windows%22%2C%22%24browser%22%3A%22Chrome%22%2C%22%24browser_version%22%3A%2287.0.4280.66%22%2C%22lagou_company_id%22%3A%22%22%7D%2C%22%24device_id%22%3A%2217616d4b3cbc84-04c85c309b6106-c791e37-3686400-17616d4b3ccca9%22%7D; _gat=1; PRE_UTM=; PRE_HOST=; PRE_LAND=https%3A%2F%2Fwww.lagou.com%2Fjobs%2Flist%5FJava%3Fpx%3Ddefault; Hm_lpvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1609912578; LGSID=20210106135617-18dde5cd-5fec-4570-8a14-e045fe8bec53; PRE_SITE=https%3A%2F%2Fsec.lagou.com%2F; LGRID=20210106135617-98fc0a19-6093-436a-ab7e-83329f9163b7; SEARCH_ID=5a7edcc9358c4eb799e9d9e906875007")
			// .addHeader("Host", "www.lagou.com")
			.addHeader("origin", "https://www.lagou.com")
			.addHeader("referer", "https://www.lagou.com/jobs/list_Java?px=default")
			.addHeader("sec-fetch-dest", "empty").addHeader("sec-fetch-mode", "cors")
			.addHeader("sec-fetch-site", "same-origin").addHeader("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.66 Safari/537.36")
			.addHeader("x-anit-forge-code", "0").addHeader("x-anit-forge-token", "None")
			.addHeader("x-requested-with", "XMLHttpRequest");

	@Override
	public void process(Page page) {
		this.processChengDu(page);
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

	@Override
	public Site getSite() {
		return site;
	}

	public void pachong() {
		// 自定义代理连接
		// HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
		// httpClientDownloader.setProxyProvider(
		// 		SimpleProxyProvider.from(new Proxy("222.249.238.138", 8080), new Proxy("218.93.119.164", 9002),new Proxy("123.57.210.164",3128)));
		Spider.create(new JobInfoReptile())
				//从"https://www.lagou.com/jobs/list_Java?px=default&city=%E5%8C%97%E4%BA%AC"开始抓
				.addUrl("https://www.lagou.com/jobs/positionAjax.json?px=default&city=%E6%88%90%E9%83%BD&needAddtionalResult=false")
				.setDownloader(new HttpClientDownloader()).addPipeline(new LaGouPipe())
				//开启5个线程抓取
				.thread(10)
				//启动爬虫
				.run();
	}

	private void processChengDu(Page page) {
		if (flag == 0) {
			Request[] requests = new Request[29];
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < requests.length; i++) {
				requests[i] = new Request(
						"https://www.lagou.com/jobs/positionAjax.json?px=default&city=%E6%88%90%E9%83%BD&needAddtionalResult=false");
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
}
