package com.ad1.invoice.repository;

import com.ad1.invoice.model.StaggingBranch;
import com.ad1.invoice.model.StaggingHO;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StgBranchRepository extends JpaRepository<StaggingBranch, Long> {

	List<StaggingBranch> findByDtbranch(Date dtbranch);

	String deleteByDtbranch(Date dtbranch);

	@Query(value = "select sb.* from stg_ho ho " + "right join stg_branch sb "
			+ "on ho.str_vendor_name  = sb.str_vendor_name and " + "ho.str_periode  = sb.str_periode and "
			+ "ho.str_kode_area  = sb.str_kode_area and " + "ho.str_branch_code  = sb.str_branch_code and "
			+ "ho.str_nik  = sb.str_nik", nativeQuery = true)
	List<StaggingBranch> getBatch();

	@Query(value = "select sb.* from stg_branch sb " + "inner join stg_ho sh " + "on sb.str_periode = sh.str_periode "
			+ "and sb.str_kode_area = sh.str_kode_area " + "and sb.str_vendor_name = sh.str_vendor_name "
			+ "and sb.str_nik = sh.str_nik " + "where sb.str_periode = :periode " + "and sh.str_kode_area = :area "
			+ "and sh.str_vendor_name = :vendor " + "and sh.str_jabatan = :jabatan", nativeQuery = true)
	List<StaggingBranch> getBranchUploaded(String periode, String area, String vendor, String jabatan);

	@Query(value = "select distinct str_vendor_name nama_vendor " + "from stg_branch sb ", nativeQuery = true)
	List<Object[]> getParaVendorBr(String jabatan);

	@Query(value = "select distinct str_periode periode " + "from stg_branch sb "
			+ "where upper(str_vendor_name) = upper(" + ":vendor" + ")" + "and upper(str_kode_area) = " + "upper("
			+ ":area" + ") " + "and upper(str_jabatan) = upper(" + ":jabatan" + ")", nativeQuery = true)
	List<Object[]> getParaPeriodeBr(String area, String vendor, String jabatan);

	@Query(value = "select distinct str_kode_area kode_area, str_nama_area nama_area " + "from stg_branch sb "
			+ "where upper(str_vendor_name) = upper(" + ":vedor" + ")" + "and upper(str_jabatan) = upper(" + ":jabatan"
			+ ")", nativeQuery = true)
	List<Object[]> getParaAreaBr(String vedor, String jabatan);
}
