package com.ad1.invoice.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EntityService {
	public ResponseEntity<Object> jsonResponse(HttpStatus httpStatus, Object data) {
        return ResponseEntity
                .status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(data);
    }
}
