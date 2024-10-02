package com.ad1.invoice.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultStgBranch {
	private List<StaggingBranch> stgBranch;
	private String changeInfo;
}
