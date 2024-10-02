package com.ad1.invoice.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseMsg {
	private String message;
	private Object data;
}
