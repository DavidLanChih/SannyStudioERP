package com.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bean.MicrobladingOrder;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/Microblading")
public class MicrobladingController {
	@Autowired // 將下行HttpServletRequest物件注入至方法
	private HttpServletRequest request;

	@Autowired
	private DataSource datasource; // 使用預設spring.datasource(application.properties自己設定內)

	// 一般表格送出(若需要邊調閱資料庫，邊改寫資料，最後再送出者才用restful)
	// @RequestMapping(path = "/CreateForm", method = { RequestMethod.GET,
	// RequestMethod.POST }) // 網站URL根目錄
	// String Makeform() {
	//
	// String method = request.getMethod();
	// System.out.println("mehtod:" + method); // 取得form是用何種方法
	//
	// String sql = "";
	// if (method.equals("POST")) {
	// try (Connection con = datasource.getConnection();)
	// {
	// sql="select * from users ";
	// PreparedStatement st = con.prepareStatement(sql);
	// ResultSet rs = st.executeQuery();
	// while(rs.next())
	// {
	// System.out.println("name:"+rs.getString("username"));
	// }
	//
	// } catch (Exception e) {
	// // TODO: handle exception
	// System.out.println(e);
	// }

	// }
	// return "applyForm"; // 執行template位置
	// }

	@PostMapping("/OrderForm") // 表單送出時會使用此方法
	public String insertForm(MicrobladingOrder Data) {
		System.out.println("Name: " + Data.getName() +
				" Sex: " + Data.getSex() +
				" Phone: " + Data.getPhone() +
				" Servi: " + Data.getServiceItem() +
				" SaleP: " + Data.getSalePrice() +
				" Creat: " + Data.getCreateDate() +
				" Memo: " + Data.getMemo());
		if (Data.getName().length() != 0 &&
				Data.getSex().length() != 0 &&
				Data.getPhone().length() != 0 &&
				Data.getServiceItem().length() != 0 &&
				Data.getSalePrice().length() != 0 &&
				Data.getCreateDate().length() != 0) {
			System.out.println("ok");

		}
		return "orderForm";
	}

	@GetMapping("/OrderForm") // 一開始登入表單時走此方法
	public String loginForm() {
		System.out.println("GET");
		return "orderForm";
	}
}
