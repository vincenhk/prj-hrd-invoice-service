package com.ad1.invoice.model.DTO;

import java.util.List;

import com.ad1.invoice.model.FileUpload;
import com.ad1.invoice.model.VendorData;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveStgFinalDto {
	private VendorData vendorData;
	private List<FileUpload> file;
}
