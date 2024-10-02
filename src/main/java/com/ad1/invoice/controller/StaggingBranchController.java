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
import com.ad1.invoice.model.StaggingBranch;
import com.ad1.invoice.service.StaggingService;

@RestController
@CrossOrigin
@RequestMapping("branch/v1")
public class StaggingBranchController {

	@Autowired
	private StaggingService staggingService;

	private int br = 1;

	@GetMapping("/get/{dtbranch}")
	public List<StaggingBranch> finddtbranchs(@PathVariable Date dtbranch) {
		return staggingService.findDtBranchs(dtbranch);
	}

	@GetMapping("/get")
	@ResponseBody
	public List<StaggingBranch> getBranchs() {
		return staggingService.getBranchs();
	}

	@PostMapping("/save")
	public ResponseEntity<Object> saveBranchs(@RequestBody List<StaggingBranch> staggingBranchs) {
		return staggingService.saveBranchs(staggingBranchs);
	}

	@GetMapping("/download-branch")
	public ResponseEntity<Object> downloadHo(@RequestParam(required = true) String vendor,
			@RequestParam(required = true) String area, @RequestParam(required = true) String periode,
			@RequestParam(required = true) String jabatan) {

		return staggingService.getUploaded(vendor, periode, area, jabatan, br);
	}

	@DeleteMapping("/delete/{dtbranch}")
	public String deleteDateHO(@PathVariable Date dtbranch) {
		return staggingService.deleteBranch(dtbranch);
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
	public ResponseEntity<Object> getParaArea(@RequestParam(required = true) String vendor,
			@RequestParam(required = true) String jabatan) {
		return staggingService.getParaArea(br, vendor, jabatan);
	}
}
