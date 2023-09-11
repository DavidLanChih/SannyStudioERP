package com.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bean.CustomerOrder;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Microblading")
public class MicrobladingRestFul {
	@Autowired // 將下行HttpServletRequest物件注入至方法
	private HttpServletRequest request;

	@Autowired
	private DataSource datasource; // 使用預設spring.datasource(application.properties自己設定內)
	@Value("${application.service.MicroUrl}")
	private String MicroURL;
	@PutMapping(path="/OrderForm/Put", produces = "application/json",consumes = "application/json")
	public CustomerOrder insertForm(@RequestBody()CustomerOrder Data,Model model) {
		
//		try(Connection con = datasource.getConnection();)
//		{	
//			if (Data.getName().length()        !=0 &&
//				Data.getSex().length()         !=0 &&
//				Data.getPhone().length()       !=0 &&
//				Data.getServiceItem().length() !=0 &&
//				Data.getSalePrice().length()   !=0 &&
//				Data.getCreateDate().length()  !=0    ) 
//			{
//				System.out.println("para ok&git OK");
//				//取得最新單號
//				int billno=0;
//				String qrySql="select max(s003_Billno) as billno from sal003";
//				Statement qryStmt=con.createStatement();
//				ResultSet qrsRs=qryStmt.executeQuery(qrySql);
//				if(qrsRs.next())
//				{
//					billno=qrsRs.getInt("billno");
//					System.out.println(billno);
//					billno++;
//				}
//				//匯入資料
//				String insertSql="insert into sal003 ( s003_Billno,"
//													+ "s003_Name,"
//													+ "s003_Sex,"
//													+ "s003_Phone,"
//													+ "s003_ServiceItem,"
//													+ "s003_SalePrice,"
//													+ "s003_CreateDate,"
//													+ "s003_Memo) "
//													+ "values (?,?,?,?,?,?,?,?)";
//				PreparedStatement preStmt=con.prepareStatement(insertSql);
//				preStmt.setInt(1,billno);
//				preStmt.setString(2,Data.getName());
//				preStmt.setString(3,Data.getSex());
//				preStmt.setString(4,Data.getPhone());
//				preStmt.setString(5,Data.getServiceItem());
//				int iPrice=Integer.parseInt(Data.getSalePrice());
//				preStmt.setInt(6,iPrice);
//				preStmt.setString(7,Data.getCreateDate());
//				preStmt.setString(8,Data.getMemo());
//				preStmt.executeUpdate();
//				String messageString="資料成功送出";
//				//mod.addAttribute("result", messageString);
//				
//			}
//			
//		}
//		catch(SQLException e)
//		{
//			System.out.println(e);
//			//mod.addAttribute("result", "資料送出失敗，請聯絡負責資訊公司!");
//		}
		System.out.println("Name: " + Data.getName() +
				" Sex: " + Data.getSex() +
				" Phone: " + Data.getPhone() +
				" Servi: " + Data.getServiceItem() +
				" SaleP: " + Data.getSalePrice() +
				" Creat: " + Data.getCreateDate() +
				" Memo: " + Data.getMemo());
		model.addAttribute("Data",Data);
		return Data;
	}

	
}
