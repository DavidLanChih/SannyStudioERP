package com.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerOrderController {

	@Autowired
	private DataSource datasource;

	@GetMapping(path = "/Customer/Order")
	public String CustomerOrderQry(Model model) {
		// 將選單渲染至前端
		List<List<String>> nameList = new ArrayList<>();
		try (Connection con = datasource.getConnection();) {

			// 取得所有客戶名稱(不重複)
			String SQL 	= "select distinct s003_NameNo as nameNo,"
						+ "                m002_Name   as name"
						+ "  from sal003 as a"
						+ " inner join man002 as b"
						+ "    on a.s003_NameNo = b.m002_No";
			Statement qryStmt = con.createStatement();
			ResultSet qryRs = qryStmt.executeQuery(SQL);
			String name = "", nameNo ="";
			
			while (qryRs.next()) {
				name   = qryRs.getString("name");
				nameNo = qryRs.getString("nameNo");
				var arr = Arrays.asList(nameNo,name);
				System.out.println(arr);
				nameList.add(arr);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		model.addAttribute("name", nameList);
		// 將url位置搜尋到的資料渲染到前端
		model.addAttribute("query", "../../Service/Customer/Order/");
		return "CustomerOrderqry";
	}
}
