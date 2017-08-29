<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="../js/highcharts/highcharts.js"></script>
<script type="text/javascript" src="../js/highcharts/modules/exporting.js"></script>
<link rel="stylesheet" type="text/css" href="../js/highcharts/css/highcharts.css">
<script type="text/javascript">
$(function () {
	//发送ajax请求，获取数据，提供给highcharts插件，进行页面图形展示
	$.post("../subareaAction_showChart.action",function(data){
		//回调函数中调用highcharts提供的API创建图表
		 $('#container').highcharts({
		        chart: {
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false
		        },
		        title: {
		            text: '分区分布图'
		        },
		        tooltip: {
		            headerFormat: '{series.name}<br>',
		            pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
		        },
		        plotOptions: {
		            pie: {
		                allowPointSelect: true,
		                cursor: 'pointer',
		                dataLabels: {
		                    enabled: true,
		                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
		                    style: {
		                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
		                    }
		                }
		            }
		        },
		        series: [{
		            type: 'pie',
		            name: '各省份分区占比',
		            data: data
		        }]
		    });
	},'json');
});

</script>
</head>
<body>
	<div id="container" style="min-width:400px;height:400px"></div>
</body>
</html>