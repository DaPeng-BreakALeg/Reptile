package com.dapeng.reptile.export;

import com.dapeng.reptile.util.DateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ExportMapExcel
 * @Description: 导出MAP
 * @author: DaPeng
 * @date: 2021年1月7日 下午5:18:48
 */
@SuppressWarnings("restriction")
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Excel2007XlsxUtil {

	// 样式map
	private static Map<String, XSSFCellStyle> styleMap = null;

	/**
	 * @Title: dealHeaderParam
	 * @Description: 处理表头参数
	 * @param headersNameMap
	 * @param headersName
	 * @param titleFieldMap
	 * @param headersId
	 * @return: void
	 * @author: DaPeng
	 * @date: 2021年1月7日 上午9:58:44
	 */
	private static void dealHeaderParam(Map<Integer, String> headersNameMap, List<String> headersName,
			Map<Integer, String> titleFieldMap, List<String> headersId) {
		/*
		 * （一）表头--标题栏
		 */
		int key = 0;
		for (int i = 0; i < headersName.size(); i++) {
			if (StringUtils.isNotEmpty(headersName.get(i))) {
				headersNameMap.put(key, headersName.get(i));
				key++;
			}
		}
		/*
		 * （二）字段---标题对应的字段
		 */
		int value = 0;
		for (int i = 0; i < headersId.size(); i++) {
			if (StringUtils.isNotEmpty(headersId.get(i))) {
				titleFieldMap.put(value, headersId.get(i));
				value++;
			}
		}
	}

	/**
	 * @Title: setBackgroundStyle
	 * @Description: 设置背景颜色
	 * @param contentStyle
	 * @param colorIndex 背景色下标
	 * @return: void
	 * @author: DaPeng
	 * @date: 2021年1月7日 上午10:24:25
	 */
	private static void setBackgroundStyle(XSSFCellStyle contentStyle, short colorIndex,
			HorizontalAlignment horizontal) {
		contentStyle.setAlignment(horizontal);
		contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		contentStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		contentStyle.setFillForegroundColor(colorIndex);
	}

	/** @Title: getStyleMap
	 * @Description: 获取样式map，所有样式都放在map中
	 * @param wb
	 * @return
	 * @return: Map<String, XSSFCellStyle>
	 * @author: DaPeng
	 * @date: 2021年1月7日 下午4:27:12
	 */
	private static Map<String, XSSFCellStyle> getStyleMap(XSSFWorkbook wb) {

		Map<String, XSSFCellStyle> map = new HashMap<String, XSSFCellStyle>();

		// 设置表头样式
		XSSFCellStyle headerStyle = wb.createCellStyle();
		setBackgroundStyle(headerStyle, IndexedColors.PALE_BLUE.getIndex(), HorizontalAlignment.CENTER);
		map.put(ExcelEnum.HEADER.getKey(), headerStyle);

		// 设置数据水平居中样式
		XSSFCellStyle horizontalCenter = wb.createCellStyle();
		setBackgroundStyle(horizontalCenter, IndexedColors.LEMON_CHIFFON.getIndex(), HorizontalAlignment.CENTER);
		map.put(ExcelEnum.HORIZONTAL_CENTER.getKey(), horizontalCenter);

		// 设置默认(黑色)字体样式
		XSSFCellStyle defaultStyle = wb.createCellStyle();
		setBackgroundStyle(defaultStyle, IndexedColors.LEMON_CHIFFON.getIndex(), HorizontalAlignment.RIGHT);
		map.put(ExcelEnum.DEFAULT.getKey(), defaultStyle);

		// 设置绿色字体样式
		XSSFCellStyle greenStyle = wb.createCellStyle();
		setBackgroundStyle(greenStyle, IndexedColors.LEMON_CHIFFON.getIndex(), HorizontalAlignment.RIGHT);
		XSSFFont greenFont = wb.createFont();
		greenFont.setColor(IndexedColors.SEA_GREEN.getIndex());
		greenStyle.setFont(greenFont);
		map.put(ExcelEnum.GREEN.getKey(), greenStyle);

		// 设置红色字体样式
		XSSFCellStyle redStyle = wb.createCellStyle();
		setBackgroundStyle(redStyle, IndexedColors.LEMON_CHIFFON.getIndex(), HorizontalAlignment.RIGHT);
		XSSFFont redFont = wb.createFont();
		redFont.setColor(IndexedColors.RED.getIndex());
		redStyle.setFont(redFont);
		map.put(ExcelEnum.RED.getKey(), redStyle);

		return map;
	}

	/**
	 * @Title: exportReportExcelToEmail
	 * @Description: 生成excel 邮件发送
	 * @param title
	 * @param headersName
	 * @param headersId
	 * @param dtoList
	 * @param fileName
	 * @return
	 * @return: ByteArrayInputStream
	 * @author: DaPeng
	 * @date: 2021年1月7日 下午1:45:46
	 */
	public static ByteArrayInputStream exportReportExcelToEmail(String title, List<String> headersName,
			List<String> headersId, List<Map<String, Object>> dtoList, String fileName) {
		/*
		 * 文件名
		 */
		// fileName +=".xlsx";
		/*
		 * （一）表头--标题栏
		 */
		Map<Integer, String> headersNameMap = new HashMap<>();
		/*
		 * （二）字段---标题对应的字段
		 */
		Map<Integer, String> titleFieldMap = new HashMap<>();
		// 处理headersName和headersId参数
		dealHeaderParam(headersNameMap, headersName, titleFieldMap, headersId);
		/*
		 * (三）声明一个工作薄：包括构建工作簿、表格、样式
		 */
		XSSFWorkbook wb = new XSSFWorkbook();
		// 获取样式map
		styleMap = getStyleMap(wb);
		// 创建一个sheet
		XSSFSheet sheet = wb.createSheet(title);
		// 设置列宽，设置默认列宽为15个字节
		for (int i = 0; i < headersName.size(); i++) {
			sheet.setColumnWidth(i, 15 * 256);
		}

		sheet.setDefaultColumnWidth(15);

		// 导出图片
		int imgHeight = 0;

		// 合并表头为两倍行高
		for (int i = 0; i < headersNameMap.size(); i++) {
			// first row (0-based),last row (0-based),first column (0-based),
			sheet.addMergedRegion(new CellRangeAddress(imgHeight, imgHeight + 1, i, i));
		}
		XSSFRow row = sheet.createRow(imgHeight);
		XSSFCell cell;
		// 拿到表格所有标题的value的集合
		Collection<String> c = headersNameMap.values();
		// 表格标题的迭代器
		Iterator<String> headersNameIt = c.iterator();
		/*
		 * （四）导出数据：包括导出标题栏以及内容栏
		 */
		// 根据选择的字段生成表头--标题
		XSSFCellStyle headerStyle = styleMap.get(ExcelEnum.HEADER.getKey());
		int size = 0;
		while (headersNameIt.hasNext()) {
			cell = row.createCell(size);
			cell.setCellValue(headersNameIt.next().toString());
			cell.setCellStyle(headerStyle);
			size++;
		}
		// 表格一行的字段的集合，以便拿到迭代器
		Collection<String> headersFieldCo = titleFieldMap.values();
		if (dtoList == null) {
			dtoList = new ArrayList<Map<String, Object>>();
		}
		// 总记录的迭代器
		Iterator<Map<String, Object>> dtoMapIt = dtoList.iterator();
		// 内容栏 真正数据记录的行序号
		int zdRow = imgHeight + 2;
		// 记录的迭代器，遍历总记录
		while (dtoMapIt.hasNext()) {
			// 拿到一条记录
			Map<String, Object> mapTemp = dtoMapIt.next();
			row = sheet.createRow(zdRow);
			zdRow++;
			int zdCell = 0;
			// 一条记录的字段的集合的迭代器
			Iterator<String> headersFieldIt = headersFieldCo.iterator();
			while (headersFieldIt.hasNext()) {
				// 字段的暂存
				String tempField = headersFieldIt.next();
				if (mapTemp.get(tempField) != null) {
					String result = "";
					if (mapTemp.get(tempField) instanceof Date) {
						result = DateUtil.formatDate((Date) mapTemp.get(tempField), DateUtil.DATE_PATTERN_DEFAULT);
					} else {
						result = String.valueOf(mapTemp.get(tempField));
					}
					// 内容样式
					XSSFCellStyle contentStyle = styleMap.get(ExcelEnum.DEFAULT.getKey());
					// 设置水平居中
					contentStyle = styleMap.get(ExcelEnum.HORIZONTAL_CENTER.getKey());

					// 制作单元格
					cell = row.createCell(zdCell);
					// 写进excel对象
					cell.setCellValue(result);
					cell.setCellStyle(contentStyle);
					//					cell.getCellStyle().cloneStyleFrom(contentStyle);
				} else {
					// 字段内容值为null时，进行处理
					cell = row.createCell(zdCell);
					// 写进excel对象
					cell.setCellValue("");
					XSSFCellStyle contentStyle = styleMap.get(ExcelEnum.DEFAULT.getKey());
					cell.setCellStyle(contentStyle);
				}
				zdCell++;
			}
		}
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			// 将wb写入流
			wb.write(byteArrayOutputStream);
			ByteArrayInputStream iss = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
			byteArrayOutputStream.close();
			return iss;
		} catch (FileNotFoundException e) {
			log.error("下载EXCEL文件失败", e);
		} catch (IOException e) {
			log.error("下载EXCEL文件失败", e);
		}
		return null;
	}

}