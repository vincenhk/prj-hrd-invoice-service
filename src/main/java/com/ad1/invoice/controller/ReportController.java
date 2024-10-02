package com.ad1.invoice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ad1.invoice.model.AnnualReport;
import com.ad1.invoice.model.ReportDetail;
import com.ad1.invoice.model.ReportSum;
import com.ad1.invoice.service.ReportService;

@RestController
@CrossOrigin
@RequestMapping("report/v1")
public class ReportController {

	@Autowired
	ReportService reportService;
	
	@GetMapping("/getrpt")
	public ResponseEntity<Object> getRptGrbPeriode(){
		return reportService.getRptGroup();
	}

	@GetMapping("/detail/{dtrev}/{vendor}")
	public List<ReportDetail> getDetailreport(@PathVariable String vendor, @PathVariable String dtrev) {
		System.out.println(vendor);
		System.out.println(dtrev);
		List<ReportDetail> list = new ArrayList<ReportDetail>();
		List<Object[]> reportList = reportService.reportDtl(vendor, dtrev);
		reportList.forEach(report -> {
			ReportDetail dtoFinal = new ReportDetail();
			dtoFinal.setVendor(report[0] + "");
			dtoFinal.setBranch(report[1] + "");
			dtoFinal.setBrcode(report[2] + "");
			dtoFinal.setJoindate(report[3] + "");
			dtoFinal.setTotal(report[4] + "");
			dtoFinal.setJabatan(report[5] + "");
			dtoFinal.setNik(report[6] + "");
			list.add(dtoFinal);
		});
		return list;
	}

	@GetMapping("/detail/{dtrev}")
	public List<ReportDetail> getDetailreportAll(@PathVariable String dtrev) {
		List<ReportDetail> list = new ArrayList<ReportDetail>();
		List<Object[]> reportList = reportService.reportDtlAll(dtrev);
		reportList.forEach(report -> {
			ReportDetail dtoFinal = new ReportDetail();
			dtoFinal.setVendor(report[0] + "");
			dtoFinal.setBranch(report[1] + "");
			dtoFinal.setBrcode(report[2] + "");
			dtoFinal.setJoindate(report[3] + "");
			dtoFinal.setTotal(report[4] + "");
			dtoFinal.setJabatan(report[5] + "");
			dtoFinal.setNik(report[6] + "");
			list.add(dtoFinal);
		});
		return list;
	}

	@GetMapping("/sum/{dtrev}")
	public List<ReportDetail> getSumreport(@PathVariable String dtrev) {
		List<ReportDetail> list = new ArrayList<ReportDetail>();
		List<Object[]> reportList = reportService.reportSum(dtrev);
		reportList.forEach(report -> {
			ReportDetail dtoSum = new ReportDetail();
			dtoSum.setVendor(report[0] + "");
			dtoSum.setBranch(report[1] + "");
			dtoSum.setBrcode(report[2] + "");
			dtoSum.setJoindate(report[3] + "");
			dtoSum.setTotal(report[4] + "");
			dtoSum.setJabatan(report[5] + "");
			dtoSum.setNik(report[6] + "");
			list.add(dtoSum);
		});
		return list;
	}

	@GetMapping("/annualReport")
	@ResponseBody
	public ResponseEntity<Object> getReportAnual() {
		return reportService.anualReport();
	}

}
