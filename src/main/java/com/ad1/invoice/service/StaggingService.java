package com.ad1.invoice.service;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ad1.invoice.model.FileUpload;
import com.ad1.invoice.model.GetMonthYear;
import com.ad1.invoice.model.GetParaArea;
import com.ad1.invoice.model.GetParaPeriode;
import com.ad1.invoice.model.GetParaVendor;
import com.ad1.invoice.model.MasterKaryawan;
import com.ad1.invoice.model.ResponseMsg;
import com.ad1.invoice.model.ResultStgBranch;
import com.ad1.invoice.model.StaggingBranch;
import com.ad1.invoice.model.StaggingHO;
import com.ad1.invoice.model.StaggingHoOld;
import com.ad1.invoice.model.VendorData;
import com.ad1.invoice.model.DTO.SaveStgFinalDto;
import com.ad1.invoice.model.StaggingFinal;
import com.ad1.invoice.repository.MasterRepository;
import com.ad1.invoice.repository.StgBranchRepository;
import com.ad1.invoice.repository.StgHORepository;
import com.ad1.invoice.repository.StgHoOldRepository;
import com.ad1.invoice.repository.StgFinalRepository;

import jakarta.transaction.Transactional;

@Service
public class StaggingService {

	@Autowired
	private StgHORepository hoRepository;

	@Autowired
	private StgBranchRepository branchRepository;

	@Autowired
	private StgFinalRepository revRepository;

	@Autowired
	private StgHoOldRepository hoOldRepository;

	@Autowired
	private MasterRepository masterRepository;

	@Autowired
	private EntityService entityService;

	public List<StaggingHO> getHos() {
		return hoRepository.findAll();
	}

	public ResponseEntity<Object> saveHos(List<StaggingHO> staggingHOs) {
		StaggingHO smpl = staggingHOs.get(0);
		String vendor = smpl.getVendorname();
		String area = smpl.getKodearea();
		String periode = smpl.getPeriode();
		String jabatan = smpl.getJabatan();
		List<StaggingHO> cekData = hoRepository.getHoUploaded(periode, area, vendor, jabatan);

		if (cekData.size() > 0) {
			return entityService.jsonResponse(HttpStatus.ALREADY_REPORTED, "Data sudah ada");
		}

		return entityService.jsonResponse(HttpStatus.OK, hoRepository.saveAll(staggingHOs));
	}

	public List<StaggingHO> findDtHos(Date dtho) {
		return hoRepository.findByDtho(dtho);
	}

	public ResponseEntity<Object> getUploaded(String vendor, String periode, String area, String jabatan,
			int kodeDwnld) {
		ResponseMsg responseMsg = new ResponseMsg();
		if (kodeDwnld == 1) {
			List<StaggingHO> responseData = hoRepository.getHoUploaded(periode, area, vendor, jabatan);

			if (responseData.size() <= 0) {
				responseMsg.setMessage("Data tidak tersedia");
				return entityService.jsonResponse(HttpStatus.NOT_FOUND, responseMsg);
			}
			responseMsg.setMessage("Data tersedia");
			responseMsg.setData(responseData);
			return entityService.jsonResponse(HttpStatus.OK, responseMsg);
		} else {
			List<StaggingBranch> responseData = branchRepository.getBranchUploaded(periode, area, vendor, jabatan);
			if (responseData.size() <= 0) {
				responseMsg.setMessage("Data tidak tersedia");
				return entityService.jsonResponse(HttpStatus.NOT_FOUND, responseMsg);
			}
			responseMsg.setMessage("Data tersedia");
			responseMsg.setData(responseData);
			return entityService.jsonResponse(HttpStatus.OK, responseMsg);
		}
	}

	@Transactional
	public String deleteHO(Date dtho) {
		hoRepository.deleteByDtho(dtho);
		return "data deleted!!!";
	}

	public List<StaggingBranch> getBranchs() {
		return branchRepository.findAll();
	}

	public ResponseEntity<Object> getParaVendor(int kode, String jabatan) {
		ResponseMsg responseMsg = new ResponseMsg();

		if (kode == 1) {
			List<Object[]> result = hoRepository.getParaVendor(jabatan);

			List<GetParaVendor> responseData = new ArrayList<>();

			if (result.size() <= 0) {
				responseMsg.setMessage("Data tidak tersedia");
				return entityService.jsonResponse(HttpStatus.NOT_FOUND, responseMsg);
			}
			result.forEach(pcs -> {
				GetParaVendor tmp = new GetParaVendor();
				tmp.setNameVendor(pcs[0] + "");
				responseData.add(tmp);
			});
			responseMsg.setMessage("Data tersedia");
			responseMsg.setData(responseData);
			return entityService.jsonResponse(HttpStatus.OK, responseMsg);
		} else if (kode == 2) {
			List<Object[]> result = branchRepository.getParaVendorBr(jabatan);

			List<GetParaVendor> responseData = new ArrayList<>();

			if (result.size() <= 0) {
				responseMsg.setMessage("Data tidak tersedia");
				return entityService.jsonResponse(HttpStatus.NOT_FOUND, responseMsg);
			}
			result.forEach(pcs -> {
				GetParaVendor tmp = new GetParaVendor();
				tmp.setNameVendor(pcs[0] + "");
				responseData.add(tmp);
			});
			responseMsg.setMessage("Data tersedia");
			responseMsg.setData(responseData);
			return entityService.jsonResponse(HttpStatus.OK, responseMsg);
		}else {
			List<Object[]> result = revRepository.getVendorFnl(jabatan);

			List<GetParaVendor> responseData = new ArrayList<>();

			if (result.size() <= 0) {
				responseMsg.setMessage("Data tidak tersedia");
				return entityService.jsonResponse(HttpStatus.NOT_FOUND, responseMsg);
			}
			result.forEach(pcs -> {
				GetParaVendor tmp = new GetParaVendor();
				tmp.setNameVendor(pcs[0] + "");
				responseData.add(tmp);
			});
			responseMsg.setMessage("Data tersedia");
			responseMsg.setData(responseData);
			return entityService.jsonResponse(HttpStatus.OK, responseMsg);
		}

	}

	public ResponseEntity<Object> getParaArea(int kode, String vendor, String jabatan) {
		ResponseMsg responseMsg = new ResponseMsg();
		if (kode == 1) {
			List<Object[]> result = hoRepository.getParaArea(vendor, jabatan);

			List<GetParaArea> responseData = new ArrayList<>();

			if (result.size() <= 0) {
				responseMsg.setMessage("Data tidak tersedia");
				return entityService.jsonResponse(HttpStatus.NOT_FOUND, responseMsg);
			}
			result.forEach(pcs -> {
				GetParaArea tmp = new GetParaArea();
				tmp.setKodeArea(pcs[0] + "");
				tmp.setNamaArea(pcs[1] + "");
				responseData.add(tmp);
			});
			responseMsg.setMessage("Data tersedia");
			responseMsg.setData(responseData);
			return entityService.jsonResponse(HttpStatus.OK, responseMsg);
		} else if (kode == 2){
			List<Object[]> result = branchRepository.getParaAreaBr(vendor, jabatan);

			List<GetParaArea> responseData = new ArrayList<>();

			if (result.size() <= 0) {
				responseMsg.setMessage("Data tidak tersedia");
				return entityService.jsonResponse(HttpStatus.NOT_FOUND, responseMsg);
			}
			result.forEach(pcs -> {
				GetParaArea tmp = new GetParaArea();
				tmp.setKodeArea(pcs[0] + "");
				tmp.setNamaArea(pcs[1] + "");
				responseData.add(tmp);
			});
			responseMsg.setMessage("Data tersedia");
			responseMsg.setData(responseData);
			return entityService.jsonResponse(HttpStatus.OK, responseMsg);
		}else {
			List<Object[]> result = revRepository.getKodeAreaFnl(jabatan, vendor);

			List<GetParaArea> responseData = new ArrayList<>();

			if (result.size() <= 0) {
				responseMsg.setMessage("Data tidak tersedia");
				return entityService.jsonResponse(HttpStatus.NOT_FOUND, responseMsg);
			}
			result.forEach(pcs -> {
				GetParaArea tmp = new GetParaArea();
				tmp.setKodeArea(pcs[0] + "");
				tmp.setNamaArea(pcs[1] + "");
				responseData.add(tmp);
			});
			responseMsg.setMessage("Data tersedia");
			responseMsg.setData(responseData);
			return entityService.jsonResponse(HttpStatus.OK, responseMsg);
		}

	}

	public ResponseEntity<Object> getParaPeriode(int kode, String area, String vendor, String jabatan) {
		ResponseMsg responseMsg = new ResponseMsg();
		if (kode == 1) {
			List<Object[]> result = hoRepository.getParaPeriode(area, vendor, jabatan);

			List<GetParaPeriode> responseData = new ArrayList<>();

			if (result.size() <= 0) {
				responseMsg.setMessage("Data tidak tersedia");
				return entityService.jsonResponse(HttpStatus.NOT_FOUND, responseMsg);
			}
			result.forEach(pcs -> {
				GetParaPeriode tmp = new GetParaPeriode();
				tmp.setPeriode(pcs[0] + "");
				responseData.add(tmp);
			});
			responseMsg.setMessage("Data tersedia");
			responseMsg.setData(responseData);
			return entityService.jsonResponse(HttpStatus.OK, responseMsg);
		} else if(kode == 2) {
			List<Object[]> result = branchRepository.getParaPeriodeBr(area, vendor, jabatan);

			List<GetParaPeriode> responseData = new ArrayList<>();

			if (result.size() <= 0) {
				responseMsg.setMessage("Data tidak tersedia");
				return entityService.jsonResponse(HttpStatus.NOT_FOUND, responseMsg);
			}
			result.forEach(pcs -> {
				GetParaPeriode tmp = new GetParaPeriode();
				tmp.setPeriode(pcs[0] + "");
				responseData.add(tmp);
			});
			responseMsg.setMessage("Data tersedia");
			responseMsg.setData(responseData);
			return entityService.jsonResponse(HttpStatus.OK, responseMsg);
		}else {
			List<Object[]> result = revRepository.getPeriodeFnl(jabatan, vendor, area);

			List<GetParaPeriode> responseData = new ArrayList<>();

			if (result.size() <= 0) {
				responseMsg.setMessage("Data tidak tersedia");
				return entityService.jsonResponse(HttpStatus.NOT_FOUND, responseMsg);
			}
			result.forEach(pcs -> {
				GetParaPeriode tmp = new GetParaPeriode();
				tmp.setPeriode(pcs[0] + "");
				responseData.add(tmp);
			});
			responseMsg.setMessage("Data tersedia");
			responseMsg.setData(responseData);
			return entityService.jsonResponse(HttpStatus.OK, responseMsg);
		}
	}

	public ResponseEntity<Object> getBranchUploaded(String vendor, String periode, String area, String jabatan) {
		ResponseMsg responseMsg = new ResponseMsg();
		List<StaggingBranch> responseResult = branchRepository.getBranchUploaded(periode, area, vendor, jabatan);

		if (responseResult.size() <= 0) {
			responseMsg.setMessage("Data belum tersedia");
			return entityService.jsonResponse(HttpStatus.NOT_FOUND, responseMsg);
		}

		responseMsg.setMessage("Data tersedia");
		responseMsg.setData(responseResult);
		return entityService.jsonResponse(HttpStatus.OK, responseMsg);
	}

	public ResponseEntity<Object> saveBranchs(List<StaggingBranch> staggingBranchs) {
		ResponseMsg responseMsg = new ResponseMsg();
		ResultStgBranch responseResult = new ResultStgBranch();

		StaggingBranch smpl = staggingBranchs.get(0);
		String periode = smpl.getPeriode();
		String area = smpl.getKodearea();
		String vendor = smpl.getVendorname();
		String jabatan = smpl.getJabatan();
		List<StaggingBranch> cekData = branchRepository.getBranchUploaded(periode, area, vendor, jabatan);

		if (cekData.size() > 0) {
			responseMsg.setMessage("Data telah tersedia");
			return entityService.jsonResponse(HttpStatus.ALREADY_REPORTED, responseMsg);
		}

		List<StaggingBranch> responsSave = branchRepository.saveAll(staggingBranchs);

		List<StaggingHO> cmpndChangeOld = hoRepository.getChangeInfo();
		responseResult.setStgBranch(responsSave);
		responseResult.setChangeInfo(cmpndChangeOld.size() + "");

		List<StaggingHoOld> insOld = new ArrayList<>();

		cmpndChangeOld.forEach(hoItem -> {
			StaggingHoOld old = new StaggingHoOld();
			old.setVendorname(hoItem.getVendorname());
			old.setBranchname(hoItem.getBranchname());
			old.setBranchcode(hoItem.getBranchcode());
			old.setBranchdtl(hoItem.getBranchdtl());
			old.setUmkump(hoItem.getUmkump());
			old.setJoin(hoItem.getJoin());
			old.setOut(hoItem.getOut());
			old.setGapok(hoItem.getGapok());
			old.setTunjmkn(hoItem.getTunjmkn());
			old.setTunjlain(hoItem.getTunjlain());
			old.setSeragam(hoItem.getSeragam());
			old.setBpjstk(hoItem.getBpjstk());
			old.setBpjskes(hoItem.getBpjskes());
			old.setBpjsjht(hoItem.getBpjsjht());
			old.setManajfee(hoItem.getManajfee());
			old.setHrgperson(hoItem.getHrgperson());
			old.set_jmlmp(hoItem.get_jmlmp());
			old.setLembur(hoItem.getLembur());
			old.setThr(hoItem.getThr());
			old.setRapel(hoItem.getRapel());
			old.setTotmanajfee(hoItem.getTotmanajfee());
			old.setDendatelkirim(hoItem.getDendatelkirim());
			old.setSubtotal(hoItem.getSubtotal());
			old.setPpn(hoItem.getPpn());
			old.setTotppn(hoItem.getTotppn());
			old.setPph(hoItem.getPph());
			old.setTotpph(hoItem.getTotpph());
			old.setNamaplk(hoItem.getNamaplk());
			old.setJabatan(hoItem.getJabatan());
			old.setNik(hoItem.getNik());
			old.setTgllahir(hoItem.getTgllahir());
			old.setJeniskel(hoItem.getJeniskel());
			old.setPeriode(hoItem.getPeriode());
			old.setKodearea(hoItem.getKodearea());
			old.setNamaarea(hoItem.getNamaarea());
			old.setLogId("HO-BRANCH BERBEDA, ID HO " + hoItem.getId().toString());
			insOld.add(old);
		});

		hoOldRepository.saveAll(insOld);

		List<Object[]> mst = masterRepository.getNikChange();

		mst.forEach(a -> {
			MasterKaryawan mk = masterRepository.findByNik(a[1] + "");
			mk.setName(a[0] + "");
			masterRepository.save(mk);
		});

		responseMsg.setMessage("Data telah tersedia");
		responseMsg.setData(responseResult);
		return entityService.jsonResponse(HttpStatus.OK, responseMsg);
	}

	public List<StaggingBranch> findDtBranchs(Date dtbranch) {
		return branchRepository.findByDtbranch(dtbranch);
	}

	@Transactional
	public String deleteBranch(Date dtbranch) {
		branchRepository.deleteByDtbranch(dtbranch);
		return "data deleted!!!";
	}

	public ResponseEntity<Object> getRevs() {
		ResponseMsg responseMsg = new ResponseMsg();
		List<Object> data = new ArrayList<>();

		if (data.size() <= 0) {
			responseMsg.setMessage("Data tidak tersedia");
			return entityService.jsonResponse(HttpStatus.NOT_FOUND, responseMsg);
		}
		responseMsg.setMessage("Data tersedia");
		responseMsg.setData(data);
		return entityService.jsonResponse(HttpStatus.OK, responseMsg);
	}

	public List<StaggingFinal> savereRevs(List<StaggingFinal> staggingRevs) {
		return revRepository.saveAll(staggingRevs);
	}

	public ResponseEntity<Object> getParaVendorFnl(String jabatan) {
		ResponseMsg responseMsg = new ResponseMsg();
		List<Object[]> responseData = revRepository.getParaVendor(jabatan);
		List<GetParaVendor> result = new ArrayList<>();
		responseData.forEach(data -> {
			GetParaVendor vendor = new GetParaVendor();
			vendor.setNameVendor(data[0] + "");
			result.add(vendor);
		});

		if (result.size() <= 0) {
			responseMsg.setMessage("Data tidak tersedia");
			return entityService.jsonResponse(HttpStatus.NOT_FOUND, responseMsg);
		}

		responseMsg.setMessage("Data tersedia");
		responseMsg.setData(result);
		return entityService.jsonResponse(HttpStatus.OK, responseMsg);

	}

	public ResponseEntity<Object> getFinalByJabatan(String jabatan) {
		ResponseMsg responseMsg = new ResponseMsg();
		List<StaggingFinal> data = revRepository.findByJabatan(jabatan);

		if (data.size() <= 0) {
			responseMsg.setMessage("Data tidak tersedia");
			return entityService.jsonResponse(HttpStatus.NOT_FOUND, responseMsg);
		}

		responseMsg.setMessage("Data ditemukan");
		responseMsg.setData(data);
		return entityService.jsonResponse(HttpStatus.OK, responseMsg);
	}

	public ResponseEntity<Object> getFinalByVendor(String vendor) {
		ResponseMsg responseMsg = new ResponseMsg();
		List<StaggingFinal> data = revRepository.findByVendorname(vendor);

		if (data.size() <= 0) {
			responseMsg.setMessage("Data tidak tersedia");
			return entityService.jsonResponse(HttpStatus.NOT_FOUND, responseMsg);
		}

		responseMsg.setMessage("Data ditemukan");
		responseMsg.setData(data);
		return entityService.jsonResponse(HttpStatus.OK, responseMsg);
	}

	public ResponseEntity<Object> getFinalByPeriode(String periode) {
		ResponseMsg responseMsg = new ResponseMsg();
		List<StaggingFinal> data = revRepository.findByPeriode(periode);

		if (data.size() <= 0) {
			responseMsg.setMessage("Data tidak tersedia");
			return entityService.jsonResponse(HttpStatus.NOT_FOUND, responseMsg);
		}

		responseMsg.setMessage("Data ditemukan");
		responseMsg.setData(data);
		return entityService.jsonResponse(HttpStatus.OK, responseMsg);
	}

	public ResponseEntity<Object> getUploadFileFnl(String periode, String area, String jabatan, String vendor) {
		ResponseMsg responseMsg = new ResponseMsg();
		List<StaggingFinal> data = revRepository.getDataByFile(periode, area, jabatan, vendor);
		if (data.size() <= 0) {
			responseMsg.setMessage("Data tidak tersedia");
			return entityService.jsonResponse(HttpStatus.NOT_FOUND, responseMsg);
		}

		responseMsg.setMessage("Data ditemukan");
		responseMsg.setData(data);
		return entityService.jsonResponse(HttpStatus.OK, responseMsg);
	}

	public ResponseEntity<Object> getParaPeriode(String jabatan) {
		ResponseMsg responseMsg = new ResponseMsg();
		List<Object[]> resultQ = revRepository.getDataByPeriode(jabatan);

		try {
			List<Object> mma = new ArrayList<>();
			List<Object> yya = new ArrayList<>();

			for (Object[] data : resultQ) {
				String periode = data[0] + "";
				int monthNumber = Integer.parseInt(periode.substring(0, 2));
				String year = "20" + periode.substring(2, 4);
				String month = new DateFormatSymbols().getMonths()[monthNumber - 1];

				if (yya.size() == 0 && mma.size() == 0) {
					yya.add(year);
					mma.add(month);
				} else {
					if (yya.contains(year) == false) {
						yya.add(year);
					}
					if (mma.contains(month) == false) {
						mma.add(month);
					}

				}

			}

			GetMonthYear fnl = new GetMonthYear();
			fnl.setYear(yya);
			fnl.setMonth(mma);

			responseMsg.setMessage("Data ditemukan");
			;
			responseMsg.setData(fnl);
			return entityService.jsonResponse(HttpStatus.OK, responseMsg);
		} catch (Exception e) {
			responseMsg.setMessage(e.getMessage());
			return entityService.jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, responseMsg);
		}

	}

	public ResponseEntity<Object> saveStg(SaveStgFinalDto data) {
		ResponseMsg responseMsg = new ResponseMsg();
		VendorData vendorData = data.getVendorData();
		List<StaggingFinal> responseData = new ArrayList<>();

		Date currentDate = new Date();

		for (FileUpload file : data.getFile()) {
			StaggingFinal dataMaster = new StaggingFinal();
			dataMaster.setVendorname(file.getVendor());
			dataMaster.setBranchname(file.getBranchName());
			dataMaster.setBranchcode(file.getBranchCode());
			dataMaster.setBranchdtl(file.getBranchDetail());
			dataMaster.setUmkump(file.getUmkUmp());
			dataMaster.setJoin(file.getJoinDate());
			dataMaster.setOut(file.getOutDate());
			dataMaster.setGapok(file.getCr_gapok());
			dataMaster.setTunjmkn(file.getTunjMakan());
			dataMaster.setTunjlain(file.getTunjLain());
			dataMaster.setSeragam(file.getSeragam());
			dataMaster.setBpjstk(file.getBpjsTK());
			dataMaster.setBpjskes(file.getBpjsKesehatan());
			dataMaster.setBpjsjht(file.getBpjsPensiun());
			dataMaster.setManajfee(file.getManagementFee());
			dataMaster.setHrgperson(file.getPersonHarga());
			dataMaster.set_jmlmp(file.getJumMP());
			dataMaster.setLembur(file.getLembur());
			dataMaster.setThr(file.getThr());
			dataMaster.setRapel(file.getThr());
			dataMaster.setTotmanajfee(file.getManagementFee());
			dataMaster.setDendatelkirim(file.getDendaTerlambat());
			dataMaster.setSubtotal(file.getSubTotalBiaya());
			dataMaster.setPpn(file.getPpn());
			dataMaster.setTotppn(file.getTotPpn());
			dataMaster.setPph(file.getPph());
			dataMaster.setTotpph(file.getTotPph());
			dataMaster.setNamaplk(file.getNamaPelaksana());
			dataMaster.setJabatan(file.getJabatan());
			dataMaster.setNik(file.getNik());
			dataMaster.setTgllahir(file.getLahirDate());
			dataMaster.setJeniskel(file.getJenisKelamin());
			dataMaster.setDtrev(currentDate);
			dataMaster.setPeriode(file.getPeriode());
			dataMaster.setKodearea(file.getKodeArea());
			dataMaster.setNamaarea(file.getNamaArea());

			responseData.add(dataMaster);
		}
		StaggingFinal smpl = responseData.get(0);
		List<StaggingFinal> cekData = revRepository.getAlreadyFinal(smpl.getPeriode(), smpl.getKodearea(),
				smpl.getVendorname());

		if (cekData.size() > 0) {
			responseMsg.setMessage("Data telah tersedia");
			return entityService.jsonResponse(HttpStatus.OK, responseMsg);
		}
		responseData = revRepository.saveAll(responseData);

		responseMsg.setMessage("Data berhasil disimpan");
		responseMsg.setData(responseData);
		return entityService.jsonResponse(HttpStatus.OK, responseMsg);
	}

	public List<StaggingFinal> findDtreRevs(Date dtrev) {
		return revRepository.findByDtrev(dtrev);
	}

	@Transactional
	public String deleteRev(Date dtrev) {
		revRepository.deleteByDtrev(dtrev);
		return "data deleted!!!";
	}

	public List<Object[]> sumList() {
		return revRepository.getAggregate();
	}

}
