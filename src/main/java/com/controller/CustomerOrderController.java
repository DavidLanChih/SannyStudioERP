package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerOrderController {
	@GetMapping(path="/Customer/Order")
	public String CustomerOrderQry(Model model) {
		//將url位置搜尋到的資料渲染到前端
		model.addAttribute("query","http://localhost/Service/Customer/Order?name=");
		return "CustomerOrderqry";
	}
}
