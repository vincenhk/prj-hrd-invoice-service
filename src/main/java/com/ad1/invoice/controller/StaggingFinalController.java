package com.ad1.invoice.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ad1.invoice.model.StaggingFinal;
import com.ad1.invoice.model.DTO.SaveStgFinalDto;
import com.ad1.invoice.service.StaggingService;

@RestController
@CrossOrigin
@RequestMapping("final/v1")
public class StaggingFinalController {

	@Autowired
	private StaggingService staggingService;

	int br = 3;
	
	@GetMapping("/get/{dtfinal}")
	public List<StaggingFinal> findDtreRevs(@PathVariable Date dtfinal) {
		return staggingService.findDtreRevs(dtfinal);
	}

//	@GetMapping("/get")
//	@ResponseBody
//	public List<StaggingFinal> getRevs() {
//		return staggingService.getRevs();
//	}

//	@PostMapping("/save")
//	public List<StaggingFinal> savereRevs(@RequestBody List<StaggingFinal> staggingHOs) {
//		return staggingService.savereRevs(staggingHOs);
//	}

	@GetMapping("/getparavendor")
	public ResponseEntity<Object> getParaVendorFnl(@RequestParam(required = true) String jabatan) {
		return staggingService.getParaVendorFnl(jabatan);
	}

	@GetMapping("/getparayymm")
	public ResponseEntity<Object> getDataByPeriode(@RequestParam(required = true) String jabatan) {
		return staggingService.getParaPeriode(jabatan);
	}

	@PostMapping("/save")
	@ResponseBody
	public ResponseEntity<Object> saveRev(@RequestBody SaveStgFinalDto data) {
		return staggingService.saveStg(data);
	}

	@DeleteMapping("/delete/{dtfinal}")
	public String deleteDateHO(@PathVariable Date dtfinal) {
		return staggingService.deleteRev(dtfinal);
	}

	@GetMapping("/getfinalrpt")
	public ResponseEntity<Object> getUploadedData(@RequestParam(required = false) String area,
			@RequestParam(required = false) String vendor, @RequestParam(required = false) String jabatan,
			@RequestParam(required = false) String periode) {
		if (vendor == null && area == null && jabatan == null && periode == null) {
			return staggingService.getRevs();
		} else if (area == null && jabatan == null && periode == null && vendor != null) {
			return staggingService.getFinalByVendor(vendor);
		} else if (area == null && vendor == null && periode != null && vendor == null) {
			return staggingService.getFinalByPeriode(periode);
		} else if (jabatan != null) {
			return staggingService.getFinalByJabatan(jabatan);
		}else {
			return staggingService.getUploadFileFnl(periode, area, jabatan, vendor);
		}
	}
	
	@GetMapping("/getvendor")
	public ResponseEntity<Object> getParaVendor(@RequestParam(required = true) String jabatan) {
		return staggingService.getParaVendor(br, jabatan);
	}

	@GetMapping("/getparaperiode")
	public ResponseEntity<Object> getParaPeriode(@RequestParam(required = true) String vendor,
			@RequestParam(required = true) String area, @RequestParam(required = true) String jabatan) {
		return staggingService.getParaPeriode(br, area, vendor, jabatan);
	}

	@GetMapping("/geparaarea")
	public ResponseEntity<Object> getParaArea(@RequestParam(required = true) String vendor,
			@RequestParam(required = true) String jabatan) {
		return staggingService.getParaArea(br, vendor, jabatan);
	}

}
