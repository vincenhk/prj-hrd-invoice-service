package com.ad1.invoice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetInfoKaryawan {
	
	private String nama_vendor;
	private String total_biaya;
	private String noa_karyawan;
}
