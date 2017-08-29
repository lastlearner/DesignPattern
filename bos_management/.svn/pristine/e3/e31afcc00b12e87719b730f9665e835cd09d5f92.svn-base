package com.itheima.bos.web.action.take_delivery;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.take_delivery.Order;
import com.itheima.bos.domain.take_delivery.WayBill;
import com.itheima.bos.service.base.AreaService;
import com.itheima.bos.service.take_delivery.OrderService;
import com.itheima.bos.service.take_delivery.WaybillService;
import com.itheima.bos.utils.FileUtils;
import com.itheima.bos.web.action.common.CommonAction;

/**
 * 运单管理
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class WaybillAction extends CommonAction<WayBill>{
	@Autowired
	private WaybillService service;
	
	//尚盼龙加
	@Autowired
	private OrderService orderService;
	@Autowired
	private AreaService areaService;
	
	private File waybillFile;
	public void setWaybillFile(File waybillFile) {
		this.waybillFile = waybillFile;
	}
	//尚盼龙加
	
	@Action(value="waybillAction_save")
	public String save() throws IOException{
		String f = "1";
		try{
			service.save(getModel());
		}catch(Exception e){
			f = "0";
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(f);
		return NONE;
	}
	
	/**
	 * 下载运单模板
	 * @return
	 * @throws IOException 
	 */
	@Action("waybillAction_exportModel")
	public String exportModel() throws IOException{
		
		//通过输出流将Excel文件写出到浏览器客户端实现下载(一个流,两个头)
		ServletOutputStream outputStream = ServletActionContext.getResponse().getOutputStream();
		
		String filename = "工作单导入模板.zip";
		
		//获得文件的扩展名
		String type = ServletActionContext.getServletContext().getMimeType(filename);
		//获取浏览器的类型,型号
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		//给文件名转码
		filename = FileUtils.encodeDownloadFilename(filename, agent);
		//设置响应头
		ServletActionContext.getResponse().setContentType(type);
		ServletActionContext.getResponse().setHeader("content-disposition","attchment;filename="+filename);
		
		//从本地直接获得文件
		String realPath = ServletActionContext.getServletContext().getRealPath("工作单导入模板.zip");
		FileInputStream fis = new FileInputStream(realPath);
		
		byte[] bytes = new byte[1024];
		int len = 0;
		while((len = fis.read(bytes))!=-1){
			outputStream.write(bytes, 0, len);
		}
		return NONE;
	}
	
	/**
	 * 解析上传的文件,比保存到数据库中
	 * @return
	 */
	@Action("waybill_batchImport")
	public String batchImport(){
		List<WayBill> list = new ArrayList<>();
		String flag = "数据导入失败,请按照模板要求正确填写";
		HSSFWorkbook workbook = null;
		try {
			workbook = new HSSFWorkbook(new FileInputStream(waybillFile));
			//读取第一个标签页面对象
			HSSFSheet sheet = workbook.getSheetAt(0);
			
			int lastRowNum = sheet.getLastRowNum();
			if(lastRowNum < 1){
				flag = "请至少包含一条数据";
				throw new RuntimeException();
			}
			
			//读取每一行的数据
			for (Row row : sheet) {
				//第一行数据不读取,是表头,跳过去
				int rowNum = row.getRowNum();
				if(rowNum == 0){
					continue;
				}
				
				row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
				String wayBillNum = row.getCell(0).getStringCellValue();
				WayBill waybillTemp = service.findByWayBillNum(wayBillNum);
				if(waybillTemp != null){
					flag = "第"+(row.getRowNum()+1)+"行运单号填写错误,已存在";
					throw new RuntimeException();
				}
				
				row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
				String orderNum = row.getCell(1).getStringCellValue();//Order
				Order order = orderService.findByOrderNum(orderNum);
				//如果没有查询到相关订单
				if(order == null || order.getWayBill() !=null){
					flag = "第"+(row.getRowNum()+1)+"行订单号填写错误或已经关联对应的运单";
					throw new RuntimeException();
				}
				String sendName = row.getCell(2).getStringCellValue();
				//设置单元格为字符串
				row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
				String sendMobile = row.getCell(3).getStringCellValue();
				
				String sendCompany = row.getCell(4).getStringCellValue();
				String sendAreaStr = row.getCell(5).getStringCellValue();//sendArea
				String[] sendStrs = sendAreaStr.split("-");
				if(sendStrs.length <2){
					flag = "第"+(row.getRowNum()+1)+"行收件人省市区填写错误";
					throw new RuntimeException();
				}
				Area sendArea = areaService.findByProvinceAndCityAndDistrict(sendStrs[0],sendStrs[1],sendStrs[2]);
				//如果没有查询到相关区域
				if(sendArea == null){
					flag = "第"+(row.getRowNum()+1)+"行发件人省市区填写错误";
					throw new RuntimeException();
				}
				
				String sendAddress = row.getCell(6).getStringCellValue();
				String recName = row.getCell(7).getStringCellValue();
				
				row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
				String recMobile = row.getCell(8).getStringCellValue();
				String recCompany = row.getCell(9).getStringCellValue();
				String recAreaStr = row.getCell(10).getStringCellValue();//recArea
				String[] recStrs = recAreaStr.split("-");
				if(recStrs.length <2){
					flag = "第"+(row.getRowNum()+1)+"行收件人省市区填写错误";
					throw new RuntimeException();
				}
				Area recArea = areaService.findByProvinceAndCityAndDistrict(recStrs[0],recStrs[1],recStrs[2]);
				//如果没有查询到相关区域
				if(recArea == null){
					flag = "第"+(row.getRowNum()+1)+"行收件人省市区填写错误";
					throw new RuntimeException();
				}
				
				String recAddress = row.getCell(11).getStringCellValue();
				String sendProNum = row.getCell(12).getStringCellValue();
				String goodsType = row.getCell(13).getStringCellValue();
				String payTypeNum = row.getCell(14).getStringCellValue();
				Double weight = row.getCell(15).getNumericCellValue();//Double
				String remark = row.getCell(16).getStringCellValue();
				Integer num = (int) row.getCell(17).getNumericCellValue();//Integer
				
				String arriveCity = row.getCell(18).getStringCellValue();
				Integer feeitemnum = (int) row.getCell(19).getNumericCellValue();//Integer
				Double actlweit = row.getCell(20).getNumericCellValue();//Double
				
				row.getCell(22).setCellType(Cell.CELL_TYPE_STRING);
				String vol = row.getCell(21).getStringCellValue();
				
				row.getCell(22).setCellType(Cell.CELL_TYPE_STRING);
				String floadreqr = row.getCell(22).getStringCellValue();
				String wayBillType = row.getCell(23).getStringCellValue();
				Integer signStatus = (int) row.getCell(24).getNumericCellValue();//Integer
				
				row.getCell(25).setCellType(Cell.CELL_TYPE_STRING);
				String delTag = row.getCell(25).getStringCellValue();
				
				WayBill waybill = new WayBill(wayBillNum, order, sendName, sendMobile, sendCompany, sendArea, sendAddress, recName, recMobile, recCompany, recArea, recAddress, sendProNum, goodsType, payTypeNum, weight, remark, num, arriveCity, feeitemnum, actlweit, vol, floadreqr, wayBillType, signStatus, delTag);
				
				//将对象添加到list集合中
				list.add(waybill);
			}	
			service.save(list);
			//如果都没有问题,肯定能走到这一行
			flag = "success";
		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (Exception e){
			flag = "数据导入失败,请按照模板要求正确填写";
			e.printStackTrace();
		}
		
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		try {
			ServletActionContext.getResponse().getWriter().print(flag);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	
	/**
	 * 查找全部
	 * @return
	 */
	@Action("waybillAction_findAll")
	public String findAll(){
		List<WayBill> list = service.findAll();
		list2json(list, new String[]{"order","sendArea","recArea",""});
		
		return NONE;
	}
}
