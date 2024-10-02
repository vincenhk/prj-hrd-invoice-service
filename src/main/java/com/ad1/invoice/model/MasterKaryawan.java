package com.ad1.invoice.model;

import java.util.Date;

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
@Table(name = "master_karyawan", schema = "public")
public class MasterKaryawan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "str_nama")
	private String name;
	@Column(name = "str_jabatan")
	private String jabatan;
	@Column(name = "str_nik")
	private String nik;
	@Column(name = "dt_tgl_lahir")
	private Date tgllahir;
	@Column(name = "str_jenis_kel")
	private String jeniskel;
	@Column(name = "dt_join")
	private Date join;
	@Column(name = "dt_out")
	private Date out;

}
