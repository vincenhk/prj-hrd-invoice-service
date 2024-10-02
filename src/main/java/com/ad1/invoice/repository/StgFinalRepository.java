package com.ad1.invoice.repository;

import com.ad1.invoice.model.StaggingFinal;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StgFinalRepository extends JpaRepository<StaggingFinal, Long> {

	List<StaggingFinal> findByDtrev(Date dtrev);

	String deleteByDtrev(Date dtrev);

	@Query(value = "select sf.str_vendor_name as nama, str_jabatan as jabatan, count(1) noa, sum(cr_sub_total) total "
			+ "from public.stg_final sf " + "group by str_vendor_name, str_jabatan "
			+ "order by str_vendor_name, str_jabatan ", nativeQuery = true)
	List<Object[]> getAggregate();

	@Query(value = "select * from stg_final sf " + "where sb.str_periode = :periode " + "and sh.str_kode_area = :area "
			+ "and sh.str_vendor_name = :vendor", nativeQuery = true)
	List<StaggingFinal> getAlreadyFinal(String periode, String area, String vendor);

	@Query(value = "select str_periode from stg_final sf " + "where str_jabatan = :jabatan "
			+ "group by str_periode", nativeQuery = true)
	List<Object[]> getDataByPeriode(String jabatan);

	@Query(value = "select str_vendor_name from stg_final sf " + "where str_jabatan = :jabatan "
			+ "group by str_vendor_name", nativeQuery = true)
	List<Object[]> getParaVendor(String jabatan);

	List<StaggingFinal> findByJabatan(String jabatan);

	List<StaggingFinal> findByVendorname(String vendorname);

	List<StaggingFinal> findByPeriode(String periode);

	@Query(value = "select * from stg_final sf " + "where str_vendor_name = :vendor " + "and str_kode_area = :area "
			+ "and str_periode = :periode " + "and str_jabatan = :jabatan", nativeQuery = true)
	List<StaggingFinal> getDataByFile(String periode, String area, String jabatan, String vendor);

	@Query(value = "select distinct str_vendor_name  " + "from stg_final sf "
			+ "where str_jabatan = :jabatan", nativeQuery = true)
	List<Object[]> getVendorFnl(String jabatan);

	@Query(value = "select distinct str_kode_area kode_area, str_nama_area nama_area " + "from stg_final sb "
			+ "where upper(str_vendor_name) = upper(" + ":vendor" + ") " + "and upper(str_jabatan) = upper("
			+ ":jabatan" + ")", nativeQuery = true)
	List<Object[]> getKodeAreaFnl(String jabatan, String vendor);

	@Query(value = "select distinct str_periode periode " + "from stg_final sh " + "where str_vendor_name = :vendor "
			+ "and upper(str_kode_area) = upper( " + ":area" + ") " + "and upper(str_jabatan) = upper( " + ":jabatan"
			+ ")", nativeQuery = true)
	List<Object[]> getPeriodeFnl(String jabatan, String vendor, String area);
}
