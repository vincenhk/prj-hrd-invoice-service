package com.ad1.invoice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ad1.invoice.model.Menu;
import com.ad1.invoice.service.MenuService;

@RestController
@CrossOrigin
@RequestMapping("/home/v1")
public class MenuController {

	@Autowired
	private MenuService menuService;

	@GetMapping("/getmenu")
	@ResponseBody
	public List<Menu> getMenus() {
		return menuService.getMenus();
	}

}
