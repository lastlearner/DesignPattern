package com.itheima.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.service.base.AreaService;
import com.itheima.bos.utils.FileUtils;
import com.itheima.bos.utils.PinYin4jUtils;
import com.itheima.bos.web.action.common.CommonAction;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 * 区域管理
 * 
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class AreaAction extends CommonAction<Area> {
	// 通过属性驱动接收上传的文件
	private File areaFile;

	public void setAreaFile(File areaFile) {
		this.areaFile = areaFile;
	}

	@Autowired
	private AreaService service;

	/**
	 * 区域数据批量导入
	 * 
	 * @throws Exception
	 * @throws FileNotFoundException
	 */
	@Action(value = "areaAction_importXls")
	public String importXls() throws FileNotFoundException, Exception {
		List<Area> list = new ArrayList<>();
		// 基于POI解析上传的Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(areaFile));
		// 读取第一个标签页
		HSSFSheet sheet = workbook.getSheetAt(0);
		// 遍历每一行
		for (Row row : sheet) {
			int rowNum = row.getRowNum();
			if (rowNum == 0) {
				continue;
			}
			String id = row.getCell(0).getStringCellValue();
			String province = row.getCell(1).getStringCellValue();
			String city = row.getCell(2).getStringCellValue();
			String district = row.getCell(3).getStringCellValue();
			String postcode = row.getCell(4).getStringCellValue();

			Area area = new Area(id, province, city, district, postcode);

			province = province.substring(0, province.length() - 1);
			city = city.substring(0, city.length() - 1);
			district = district.substring(0, district.length() - 1);

			String[] headByString = PinYin4jUtils.getHeadByString(province + city + district);
			// 简码
			String shortcode = StringUtils.join(headByString);

			// 城市编码---》》shijiazhuang
			String citycode = PinYin4jUtils.hanziToPinyin(city, "");
			area.setShortcode(shortcode);
			area.setCitycode(citycode);
			list.add(area);
		}
		service.saveBatch(list);
		return NONE;
	}

	/**
	 * 分页查询方法
	 * 
	 * @throws IOException
	 */
	@Action(value = "areaAction_pageQuery")
	public String pageQuery() throws IOException {
		// spring data jpa提供的方式，Pageable用于封装查询条件
		Pageable pageable = new PageRequest(page - 1, rows);

		Page<Area> page = service.pageQuery(pageable);

		page2json(page, new String[] { "subareas" });
		return NONE;
	}

	/**
	 * 查询所有区域数据，返回json
	 * 
	 * @throws IOException
	 */
	@Action(value = "areaAction_findAll")
	public String findAll() throws IOException {
		List<Area> list = service.findAll();

		list2json(list, new String[] { "subareas" });

		return NONE;
	}
	
	//注入数据源对象
	@Autowired
	private DataSource dataSource;

	/**
	 * 导出PDF报表
	 * @return
	 * @throws Exception
	 */
	@Action(value = "areaAction_exportPDF")
	public String exportPDF() throws Exception{
		List<Area> list = service.findAll();
		
		// 读取 jrxml 文件
		String jrxml = ServletActionContext.getServletContext().getRealPath("/template/report2.jrxml");
		// 准备需要数据
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("company", "传智播客");
		// 准备需要数据
		JasperReport report = JasperCompileManager.compileReport(jrxml);
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(list));

		HttpServletResponse response = ServletActionContext.getResponse();
		OutputStream ouputStream = response.getOutputStream();
		// 设置相应参数，以附件形式保存PDF
		response.setContentType("application/pdf");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + FileUtils.encodeDownloadFilename("区域数据统计.pdf",
				ServletActionContext.getRequest().getHeader("user-agent")));
		// 使用JRPdfExproter导出器导出pdf
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
		exporter.exportReport();// 导出
		ouputStream.close();// 关闭流
		return null;
	}
	
	@Action(value = "areaAction_exportPDF_bak")
	public String exportPDF_bak() throws Exception{
		List<Area> list = new ArrayList<>();
		
		// 读取 jrxml 文件
		String jrxml = ServletActionContext.getServletContext().getRealPath("/template/report1.jrxml");
		// 准备需要数据
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("company", "传智播客");
		// 准备需要数据
		JasperReport report = JasperCompileManager.compileReport(jrxml);
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource.getConnection());

		HttpServletResponse response = ServletActionContext.getResponse();
		OutputStream ouputStream = response.getOutputStream();
		// 设置相应参数，以附件形式保存PDF
		response.setContentType("application/pdf");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + FileUtils.encodeDownloadFilename("区域数据统计.pdf",
				ServletActionContext.getRequest().getHeader("user-agent")));
		// 使用JRPdfExproter导出器导出pdf
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
		exporter.exportReport();// 导出
		ouputStream.close();// 关闭流
		return null;
	}
}
