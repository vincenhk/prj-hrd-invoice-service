package com.ad1.invoice.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileUpload {
	private String branchName;
	private String branchCode;
	private String branchDetail;
	private Float umkUmp;
	private Date joinDate;
	private Date outDate;
	private Float cr_gapok;
	private Float tunjMakan;
	private Float tunjLain;
	private Float seragam;
	private Float bpjsTK;
	private Float bpjsKesehatan;
	private Float bpjsPensiun;
	private Float managementFee;
	private Float personHarga;
	private int jumMP;
	private Float lembur;
	private Float thr;
	private Float rapel;	
	private Float totMngFee;
	private Float dendaTerlambat;
	private Float subTotalBiaya;
	private Float ppn;
	private Float totPpn;
	private Float pph;
	private Float totPph;
	private String namaPelaksana;
	private String jabatan;
	private String nik;
	private Date lahirDate;
	private String jenisKelamin;
	private String periode;
	private String vendor;
	private String kodeArea;
	private String namaArea;
}
