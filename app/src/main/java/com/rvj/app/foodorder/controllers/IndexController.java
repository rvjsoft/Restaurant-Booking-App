package com.rvj.app.foodorder.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	@GetMapping("/appUi")
	public String index() {
		return "/index.html";
	}
}
