package com.dapeng.reptile.export;

import lombok.Getter;

/**
 * @ClassName: ExcelEnum
 * @Description: Excel工具类枚举
 * @author: DaPeng
 * @date: 2021年1月6日 下午3:52:22
 */
public enum ExcelEnum {

	DEFAULT("DEFAULT", "默认样式，黑色字体"), HEADER("HEADER", "表头样式，蓝色背景"), GREEN("GREEN", "绿色字体样式"), RED("RED",
			"红色字体样式"), HORIZONTAL_CENTER("HORIZONTAL_CENTER", "数据水平居中样式");

	// key
	@Getter
	private String key;
	// 数值
	@Getter
	private String value;

	/**
	 * @Title: ExcelEnum
	 * @Description: Excel工具类枚举构造方法
	 * @author: DaPeng
	 * @date: 2021年1月6日 上午9:19:22
	 * @param key
	 * @param value
	 */
	private ExcelEnum(String key, String value) {
		this.key = key;
		this.value = value;
	}

}
