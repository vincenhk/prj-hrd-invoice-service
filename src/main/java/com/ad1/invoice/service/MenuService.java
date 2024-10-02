package com.ad1.invoice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ad1.invoice.model.Menu;
import com.ad1.invoice.repository.MenuRepository;

@Service
public class MenuService {

	@Autowired
	MenuRepository menuRepository;

	public List<Menu> getMenus() {
		return menuRepository.findAll();
	}

}
