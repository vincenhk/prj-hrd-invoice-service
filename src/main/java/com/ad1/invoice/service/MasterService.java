package com.ad1.invoice.service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ad1.invoice.model.GetInfoKaryawan;
import com.ad1.invoice.model.MasterKaryawan;
import com.ad1.invoice.model.ResponseMsg;
import com.ad1.invoice.repository.MasterRepository;

@Service
public class MasterService {

	@Autowired
	private MasterRepository masterRepository;
	@Autowired
	private EntityService entityService;

	public List<MasterKaryawan> getKaryawans() {
		return masterRepository.findAll();
	}

	public MasterKaryawan getKaryawan(String name) {
		return masterRepository.findByName(name);
	}

	public ResponseEntity<Object> saveKaryawan(MasterKaryawan masterKaryawan) {
		ResponseMsg responseMsg = new ResponseMsg();

		String nik = masterKaryawan.getNik();
		List<MasterKaryawan> cekData = masterRepository.getAlreadyKaryawan(nik);

		if (cekData.size() > 0) {
			responseMsg.setMessage("Data telah tersedia");
			return entityService.jsonResponse(HttpStatus.ALREADY_REPORTED, responseMsg);
		}

		return entityService.jsonResponse(HttpStatus.OK, masterRepository.save(masterKaryawan));
	}

	public ResponseEntity<Object> saveKaryawans(List<MasterKaryawan> masterKaryawans) {
		ResponseMsg responseMsg = new ResponseMsg();
		List<String> dataAlry = new ArrayList<>();

		masterKaryawans.forEach(data -> {
			String nik = data.getNik();
			List<MasterKaryawan> cekData = masterRepository.getAlreadyKaryawan(nik);
			if (cekData.size() > 0) {
				dataAlry.add(nik);
			}
		});
		if (dataAlry.size() > 0) {
			responseMsg.setMessage("Data telah tersedia");
			responseMsg.setData(dataAlry);
			return entityService.jsonResponse(HttpStatus.ALREADY_REPORTED, responseMsg);
		}

		responseMsg.setMessage("Data tersimpan");
		responseMsg.setData(masterRepository.saveAll(masterKaryawans));
		return entityService.jsonResponse(HttpStatus.OK, responseMsg);
	}

	public String deleteMaster(Long id) {
		masterRepository.deleteById(id);
		return "data berhasil dihapus!!!";
	}

	public ResponseEntity<Object> getInfoKaryawan(String jabatan) {
		LocalDate currentDate = LocalDate.now();
		String date_yy = Integer.toString(currentDate.getYear());

		List<GetInfoKaryawan> responseFinal = new ArrayList<>();
		List<Object[]> responseData = masterRepository.getInfoKaryawan(date_yy, jabatan);

		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
		responseData.forEach(data -> {
			GetInfoKaryawan dtoFinal = new GetInfoKaryawan();
			dtoFinal.setNama_vendor(data[0] + "");
			dtoFinal.setTotal_biaya(decimalFormat.format(data[1]) + "");
			dtoFinal.setNoa_karyawan(data[2] + "");
			responseFinal.add(dtoFinal);
		});

		if (responseFinal.isEmpty()) {
			return entityService.jsonResponse(HttpStatus.NOT_FOUND, "Data : " + "Not Found");
		}

		return entityService.jsonResponse(HttpStatus.OK, responseFinal);
	}

	public ResponseEntity<Object> findInfoKaryawanByName(String name, String jabatan) {

		LocalDate currentDate = LocalDate.now();
		String date_yy = Integer.toString(currentDate.getYear());

		List<GetInfoKaryawan> responseFinal = new ArrayList<>();
		List<Object[]> responseData = masterRepository.findInfoKaryawanByName(date_yy, name, jabatan);

		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
		responseData.forEach(data -> {
			GetInfoKaryawan dtoFinal = new GetInfoKaryawan();
			dtoFinal.setNama_vendor(data[0] + "");
			dtoFinal.setTotal_biaya(decimalFormat.format(data[1]) + "");
			dtoFinal.setNoa_karyawan(data[2] + "");
			responseFinal.add(dtoFinal);
		});

		if (responseFinal.isEmpty()) {
			return entityService.jsonResponse(HttpStatus.NOT_FOUND, "Data : " + "Not Found");
		}

		return entityService.jsonResponse(HttpStatus.OK, responseFinal);

	}

	public MasterKaryawan updateKaryawan(MasterKaryawan masterKaryawan) {
		MasterKaryawan existsKaryawan = masterRepository.findById(masterKaryawan.getId()).orElse(null);
		existsKaryawan.setName(masterKaryawan.getName());
		existsKaryawan.setJabatan(masterKaryawan.getJabatan());
		existsKaryawan.setNik(masterKaryawan.getNik());
		existsKaryawan.setTgllahir(masterKaryawan.getTgllahir());
		existsKaryawan.setJeniskel(masterKaryawan.getJeniskel());
		existsKaryawan.setJoin(masterKaryawan.getJoin());
		existsKaryawan.setOut(masterKaryawan.getOut());
		return masterRepository.save(existsKaryawan);
	}
}
