package com.ad1.invoice.repository;

import com.ad1.invoice.model.StaggingHO;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StgHORepository extends JpaRepository<StaggingHO, Long> {
	List<StaggingHO> findByDtho(Date dtho);

	String deleteByDtho(Date dtho);

//	@Query(value = "select sh.* from stg_ho sh "
//			+ "where str_kode_area = :periode "
//			+ "and str_vendor_name = :areacode "
//			+ "and str_periode = :vendor")
//	List<StaggingHO> getEqualHo(String periode, String areacode, String vendor);

	@Query(value = "select ho.* from stg_ho ho, stg_branch sb " + "where (ho.str_vendor_name  = sb.str_vendor_name and "
			+ "ho.str_periode  = sb.str_periode and " + "ho.str_kode_area  = sb.str_kode_area and "
			+ "ho.str_branch_code  = sb.str_branch_code and " + "ho.str_nik  = sb.str_nik ) " + "and "
			+ "(ho.str_branch_name  <> sb.str_branch_name or " + "ho.str_nama_area  <> sb.str_nama_area or "
			+ "ho.str_branch_dtl  <> sb.str_branch_dtl or " + "ho.cr_umk_ump  <> sb.cr_umk_ump or "
			+ "ho.dt_join  <> sb.dt_join or " + "ho.dt_out  <> sb.dt_out or " + "ho.cr_gapok  <> sb.cr_gapok or "
			+ "ho.cr_tunj_mkn  <> sb.cr_tunj_mkn or " + "ho.cr_tunj_lain  <> sb.cr_tunj_lain or "
			+ "ho.cr_seragam  <> sb.cr_seragam or " + "ho.cr_bpjs_tk  <> sb.cr_bpjs_tk or "
			+ "ho.cr_bpjs_kes  <> sb.cr_bpjs_kes or " + "ho.cr_bpjs_jht  <> sb.cr_bpjs_jht or "
			+ "ho.cr_manaj_fee  <> sb.cr_manaj_fee or " + "ho.cr_hrg_person  <> sb.cr_hrg_person or "
			+ "ho.noa_jml_mp  <> sb.noa_jml_mp or " + "ho.cr_lembur  <> sb.cr_lembur or "
			+ "ho.cr_thr  <> sb.cr_thr or " + "ho.cr_rapel  <> sb.cr_rapel or "
			+ "ho.cr_tot_manaj_fee  <> sb.cr_tot_manaj_fee or " + "ho.cr_denda_tel_kirim  <> sb.cr_denda_tel_kirim or "
			+ "ho.cr_sub_total  <> sb.cr_sub_total or " + "ho.cr_ppn  <> sb.cr_ppn or "
			+ "ho.cr_tot_ppn  <> sb.cr_tot_ppn or " + "ho.cr_pph  <> sb.cr_pph or "
			+ "ho.cr_tot_pph  <> sb.cr_tot_pph or " + "ho.str_nama_plk  <> sb.str_nama_plk or "
			+ "ho.str_jabatan  <> sb.str_jabatan or " + "ho.dt_tgl_lahir  <> sb.dt_tgl_lahir or "
			+ "ho.str_jenis_kel  <> sb.str_jenis_kel)", nativeQuery = true)
	List<StaggingHO> getChangeInfo();

	@Query(value = "select * from stg_ho sh "
			+ "where sh.str_periode = :periode "
			+ "and sh.str_kode_area = :area "
			+ "and sh.str_vendor_name = :vendor "
			+ "and sh.str_jabatan = :jabatan", nativeQuery = true)
	List<StaggingHO> getHoUploaded(String periode, String area, String vendor, String jabatan);

	@Query(value = "select distinct str_vendor_name nama_vendor "
			+ "from stg_ho sh "
			+ "where str_jabatan = :jabatan", nativeQuery = true)
	List<Object[]> getParaVendor(String jabatan);

	@Query(value = "select distinct str_periode periode "
			+ "from stg_ho sh "
			+ "where str_vendor_name = :vendor "
			+ "and upper(str_kode_area) = upper("+":area" +") "
			+ "and upper(str_jabatan) = upper("+":jabatan"+")", nativeQuery = true)
	List<Object[]> getParaPeriode(String area, String vendor, String jabatan);

	@Query(value = "select distinct str_kode_area kode_area, str_nama_area nama_area "
			+ "from stg_ho sb "
			+ "where upper(str_vendor_name) = upper(" + ":vendor"+") "
			+ "and upper(str_jabatan) = upper("+":jabatan"+")", nativeQuery = true)
	List<Object[]> getParaArea(String vendor, String jabatan);
}
