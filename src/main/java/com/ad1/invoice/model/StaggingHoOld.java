package com.ad1.invoice.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stg_ho_old", schema = "public")
public class StaggingHoOld {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "str_vendor_name")
	private String vendorname;
	@Column(name = "str_branch_name")
	private String branchname;
	@Column(name = "str_branch_code")
	private String branchcode;
	@Column(name = "str_branch_dtl")
	private String branchdtl;
	@Column(name = "cr_umk_ump")
	private Float umkump;
	@Column(name = "dt_join")
	private Date join;
	@Column(name = "dt_out")
	private Date out;
	@Column(name = "cr_gapok")
	private Float gapok;
	@Column(name = "cr_tunj_mkn")
	private Float tunjmkn;
	@Column(name = "cr_tunj_lain")
	private Float tunjlain;
	@Column(name = "cr_seragam")
	private Float seragam;
	@Column(name = "cr_bpjs_tk")
	private Float bpjstk;
	@Column(name = "cr_bpjs_kes")
	private Float bpjskes;
	@Column(name = "cr_bpjs_jht")
	private Float bpjsjht;
	@Column(name = "cr_manaj_fee")
	private Float manajfee;
	@Column(name = "cr_hrg_person")
	private Float hrgperson;
	@Column(name = "noa_jml_mp")
	private int _jmlmp;
	@Column(name = "cr_lembur")
	private Float lembur;
	@Column(name = "cr_thr")
	private Float thr;
	@Column(name = "cr_rapel")
	private Float rapel;
	@Column(name = "cr_tot_manaj_fee")
	private Float totmanajfee;
	@Column(name = "cr_denda_tel_kirim")
	private Float dendatelkirim;
	@Column(name = "cr_sub_total")
	private Float subtotal;
	@Column(name = "cr_ppn")
	private Float ppn;
	@Column(name = "cr_tot_ppn")
	private Float totppn;
	@Column(name = "cr_pph")
	private Float pph;
	@Column(name = "cr_tot_pph")
	private Float totpph;
	@Column(name = "str_nama_plk")
	private String namaplk;
	@Column(name = "str_jabatan")
	private String jabatan;
	@Column(name = "str_nik")
	private String nik;
	@Column(name = "dt_tgl_lahir")
	private Date tgllahir;
	@Column(name = "str_jenis_kel")
	private String jeniskel;
	@Column(name = "str_periode")
	private String periode;
	@Column(name = "str_kode_area")
	private String kodearea;
	@Column(name = "str_nama_area")
	private String namaarea;
	@Column(name = "str_log")
	private String logId;
	@CreationTimestamp
	@Column(name = "dt_insert")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date dtrev;

}
