package com.ad1.invoice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ad1.invoice.model.StaggingFinal;

@Repository
public interface ReportRepository extends JpaRepository<StaggingFinal, Long> {

	@Query(value = "select sf.str_vendor_name vendor, sf.str_branch_name branch, sf.str_branch_code brcode, sf.dt_join joindate, sf.cr_sub_total, sf.str_jabatan jabatan, sf.str_nik nik "
			+ "from public.stg_final sf where upper(sf.str_vendor_name) = upper(:vendor) "
			+ "and concat(to_char(sf.dt_insert,'yyyy'), '-', to_char(sf.dt_insert, 'mm')) = :dtrev ", nativeQuery = true)
	List<Object[]> reportDtl(String vendor, String dtrev); // report detail pilih vendor

	@Query(value = "select sf.str_vendor_name vendor, sf.str_branch_name branch, sf.str_branch_code brcode, sf.dt_join joindate, sf.cr_sub_total, sf.str_jabatan jabatan, sf.str_nik nik "
			+ "from public.stg_final sf where concat(to_char(sf.dt_insert,'yyyy'), '-', to_char(sf.dt_insert, 'mm')) = :dtrev ", nativeQuery = true)
	List<Object[]> reportDtlAll(String dtrev); // report detail all

	@Query(value = "select sf.str_vendor_name vendor, sf.str_branch_name branch, sf.str_branch_code brcode, sf.dt_join joindate, sf.cr_sub_total, sf.str_jabatan jabatan, sf.str_nik nik "
			+ "from public.stg_final sf where to_char(sf.dt_insert,'yyyy') = :dtrev ", nativeQuery = true)
	List<Object[]> reportSum(String dtrev); // report tahunan pilih tahun

	@Query(value = "SELECT sum(cr_tot_ppn) tot_ppn, count(str_nama_plk) karyawan, count(distinct str_vendor_name) vendor "
			+ "FROM stg_final sf "
			+ "where sf.dt_insert >= now() + interval '-1 year' "
			+ "and sf.dt_insert <= now()", nativeQuery = true)
	List<Object[]> annualReport(); // invoice final, untuk dashboard data setahun
	

	@Query(value = "select str_periode, str_jabatan, sum(cr_tot_ppn) sumppn from stg_final sf "
			+ "group by str_periode, str_jabatan", nativeQuery = true)
	List<Object[]> getRptGroupPeriod();
}
