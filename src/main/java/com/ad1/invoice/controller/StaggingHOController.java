package com.ad1.invoice.controller;

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

import com.ad1.invoice.model.StaggingHO;
import com.ad1.invoice.service.StaggingService;

@RestController
@CrossOrigin
@RequestMapping("ho/v1")
public class StaggingHOController {

	@Autowired
	private StaggingService staggingService;

	private int br = 2;

	@GetMapping("/get/{dtho}")
	public List<StaggingHO> findDtHos(@PathVariable Date dtho) {
		return staggingService.findDtHos(dtho);
	}

	@GetMapping("/get")
	@ResponseBody
	public List<StaggingHO> getHos() {
		return staggingService.getHos();
	}

	@PostMapping("/save")
	public ResponseEntity<Object> saveHos(@RequestBody List<StaggingHO> staggingHOs) {
		return staggingService.saveHos(staggingHOs);
	}

	@DeleteMapping("/delete/{dtho}")
	public String deleteDateHO(@PathVariable Date dtho) {
		return staggingService.deleteHO(dtho);
	}

	@GetMapping("/download-ho")
	public ResponseEntity<Object> downloadHo(@RequestParam(required = true) String vendor,
			@RequestParam(required = true) String area, @RequestParam(required = true) String periode, @RequestParam(required = true) String jabatan) {

		return staggingService.getUploaded(vendor, periode, area, jabatan, br);
	}

	@GetMapping("/getparavendor")
	public ResponseEntity<Object> getParaVendor(@RequestParam(required = true) String jabatan) {
		return staggingService.getParaVendor(br, jabatan);
	}

	@GetMapping("/getparaperiode")
	public ResponseEntity<Object> getParaPeriode(@RequestParam(required = true) String vendor,
			@RequestParam(required = true) String area, @RequestParam(required = true) String jabatan) {
		return staggingService.getParaPeriode(br, area, vendor, jabatan);
	}

	@GetMapping("/geparaarea")
	public ResponseEntity<Object> getParaArea(@RequestParam(required = true) String vendor, @RequestParam(required = true) String jabatan) {
		return staggingService.getParaArea(br, vendor, jabatan);
	}
}
