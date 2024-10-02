package com.ad1.invoice.service;

import java.sql.Date;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ad1.invoice.model.AnnualReport;
import com.ad1.invoice.model.GetReportGroup;
import com.ad1.invoice.model.ResponseMsg;
import com.ad1.invoice.repository.ReportRepository;

@Service
public class ReportService {

	@Autowired
	ReportRepository reportRepository;

	@Autowired
	EntityService entityService;

	public List<Object[]> reportDtl(String vendor, String dtrev) {
		return reportRepository.reportDtl(vendor, dtrev);
	}

	public List<Object[]> reportDtlAll(String dtrev) {
		return reportRepository.reportDtlAll(dtrev);
	}

	public List<Object[]> reportSum(String dtrev) {
		return reportRepository.reportSum(dtrev);
	}

	public ResponseEntity<Object> getRptGroup() {
		System.out.println("Masuk getRptGroup");
		List<GetReportGroup> responseData = new ArrayList<>();
		ResponseMsg responseMsg = new ResponseMsg();

		try {
			List<Object[]> tmpd = reportRepository.getRptGroupPeriod();

			tmpd.forEach(data -> {
				GetReportGroup tmp = new GetReportGroup();
				String periode = data[0] + "";
				int monthNumber = Integer.parseInt(periode.substring(0, 2));
				String month = new DateFormatSymbols().getMonths()[monthNumber - 1];
				String year = "20" + periode.substring(2, 4);

				String fnl = month + "-" + year;
				tmp.setJabatan(data[1] + "");
				tmp.setDate(fnl);
				tmp.setTotalPpn(data[2]+"");
				responseData.add(tmp);
			});

			if (responseData.size() <= 0) {
				responseMsg.setMessage("Data tidak tersedia");
				return entityService.jsonResponse(HttpStatus.NOT_FOUND, responseMsg);
			}
		} catch (Exception e) {
			responseMsg.setMessage(e.getMessage());
			return entityService.jsonResponse(HttpStatus.METHOD_FAILURE, responseMsg);
		}

		responseMsg.setMessage("Data ditemukan");
		responseMsg.setData(responseData);
		return entityService.jsonResponse(HttpStatus.OK, responseMsg);
	}

	public ResponseEntity<Object> anualReport() {
		List<AnnualReport> responseResult = new ArrayList<>();
		List<Object[]> annualList = reportRepository.annualReport();
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
		annualList.forEach(annual -> {
			AnnualReport temp = new AnnualReport();
			temp.setTotalPpn(decimalFormat.format(annual[0]));
			temp.setKaryawan(annual[1] + "");
			temp.setVendor(annual[2] + "");
			responseResult.add(temp);
		});
		if (responseResult.isEmpty()) {
			return entityService.jsonResponse(HttpStatus.NOT_FOUND, "Data tidak ditemukan");
		} else {
			return entityService.jsonResponse(HttpStatus.OK, responseResult);
		}
	}

}
