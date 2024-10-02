package com.ad1.invoice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ad1.invoice.model.MasterKaryawan;
import com.ad1.invoice.service.MasterService;

@RestController
@CrossOrigin
@RequestMapping("/master/v1")
public class MasterController {

	@Autowired
	private MasterService masterService;

	// cari data ob security dari nama
	@GetMapping("/getby/{str_name}")
	@ResponseBody
	public MasterKaryawan getKaryawan(@PathVariable String str_name) {
		return masterService.getKaryawan(str_name);
	}

	// get all data ob security
	@GetMapping("/getall")
	@ResponseBody
	public List<MasterKaryawan> getKaryawans() {
		return masterService.getKaryawans();
	}

	// save data ob security
	@PostMapping("/save")
	public ResponseEntity<Object> addMaster(@RequestBody MasterKaryawan masterKaryawan) {
		return masterService.saveKaryawan(masterKaryawan);
	}

	@GetMapping("/getinfokaryawan")
	@ResponseBody
	public ResponseEntity<Object> getInfoKaryawan(@RequestParam(required = false) String name,
			@RequestParam(required = true) String jabatan) {
		if (name != null) {
			return masterService.findInfoKaryawanByName(name, jabatan);
		} else {
			return masterService.getInfoKaryawan(jabatan);
		}
	}

	// save data ob security all / upload
	@PostMapping("/saveall")
	public ResponseEntity<Object> addMasters(@RequestBody List<MasterKaryawan> masterKaryawans) {
		return masterService.saveKaryawans(masterKaryawans);
	}

	// update data ob security
	@PutMapping("/update")
	public MasterKaryawan updateMaster(@RequestBody MasterKaryawan masterKaryawan) {
		return masterService.updateKaryawan(masterKaryawan);
	}

	// delete data ob security
	@DeleteMapping("/delete/{id}")
	public String deleteMaster(@PathVariable Long id) {
		return masterService.deleteMaster(id);
	}
}
