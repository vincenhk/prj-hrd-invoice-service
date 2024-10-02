package com.ad1.invoice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ad1.invoice.model.MasterKaryawan;

@Repository
public interface MasterRepository extends JpaRepository<MasterKaryawan, Long> {

	MasterKaryawan findByName(String nama);

	MasterKaryawan findByNik(String nik);

	@Query(value = "select str_vendor_name nama_vendor, sum(cr_tot_ppn) total_ppn, count(1) noa_karyawan"
			+ " from master_karyawan mk" + " inner join stg_final sf on" + " mk.str_nik  = sf.str_nik"
			+ " where  to_char(sf.dt_insert,'yyyy') = :date_yy" + " and UPPER(mk.str_jabatan) = UPPER(:jabatan)"
			+ " group by str_vendor_name", nativeQuery = true)
	List<Object[]> getInfoKaryawan(String date_yy, String jabatan);

	@Query(value = "select str_vendor_name nama_vendor, sum(cr_tot_ppn) total_ppn, count(1) noa_karyawan "
			+ "from master_karyawan mk " + "inner join stg_final sf on " + "mk.str_nik  = sf.str_nik "
			+ "where  to_char(sf.dt_insert,'yyyy') = :date_yy " + "and UPPER(mk.str_jabatan) = UPPER(:jabatan) "
			+ "group by str_vendor_name "
			+ "having upper(str_vendor_name ) like CONCAT('%', upper(:name), '%')", nativeQuery = true)
	List<Object[]> findInfoKaryawanByName(String date_yy, String name, String jabatan);

	@Query(value = "select sb.str_nama_plk, sb.str_nik from stg_branch sb " + "inner join master_karyawan mk "
			+ "on sb.str_nik = mk.str_nik " + "where sb.dt_insert = ( " + "select max(dt_insert) "
			+ "from stg_branch tm " + "where tm.str_nik = mk.str_nik) "
			+ "and sb.str_nama_plk <> mk.str_nama ", nativeQuery = true)
	List<Object[]> getNikChange();

	@Query(value = "select * from master_karyawan mk " + "where str_nik = :nik", nativeQuery = true)
	List<MasterKaryawan> getAlreadyKaryawan(String nik);

}
