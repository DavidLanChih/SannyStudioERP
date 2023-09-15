package com.controller;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.bean.Customer;
import com.bean.CustomerOrder;
import com.bean.NowLocalDateTime;
import com.bean.Product;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/Microblading")
public class MicrobladingController {

	@Autowired
	private DataSource datasource; // 使用預設spring.datasource(application.properties自己設定內)

	String SQL = "";	
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
	public String postForm(CustomerOrder Data, Model mod) {
		
		// System.out.println("Name: " + Data.getName() +
		// 		" Sex: "   + Data.getSex() +
		// 		" Phone: " + Data.getPhone() +
		// 		" Servi: " + Data.getServiceItem() +
		// 		" SaleP: " + Data.getSalePrice() +
		// 		" Creat: " + Data.getCreateDate() +
		// 		" Memo: "  + Data.getMemo());
		if (Data.getName().length()        !=0 &&
			Data.getSex().length()         !=0 &&
			Data.getPhone().length()       !=0 &&
			Data.getServiceItem().length() !=0 &&
			Data.getSalePrice().length()   !=0 &&
			Data.getCreateDate().length()  !=0    ) 
		{
			try(Connection con = datasource.getConnection();)
			{	
				
					//取得最新單號
					int billno=0;
					SQL="select max(s003_Billno) as billno from sal003";
					Statement qryStmt=con.createStatement();
					ResultSet qryRs=qryStmt.executeQuery(SQL);
					if(qryRs.next())
					{
						billno=qryRs.getInt("billno");
						//System.out.println(billno);
						billno++;
					}
					
					//先確認是否有此客戶資料
					PreparedStatement preStmt=null;
					SQL="select count(*) as num from man002 where m002_name='"+Data.getName()+"'";
					qryRs=qryStmt.executeQuery(SQL);
//					System.out.println(SQL);
					if(qryRs.next())
					{
						int num=qryRs.getInt("num");
						System.out.println(num);
						//若無此資料，則先建立客戶資料
						if (num<1) 
						{
							SQL="insert into man002 (   m002_Name, "
													+ "	m002_Sex, "
													+ "	m002_Phone  ) "
													+ " values (?,?,?)";
							preStmt=con.prepareStatement(SQL);
							preStmt.setString(1,Data.getName());
							preStmt.setString(2,Data.getSex());
							preStmt.setString(3,Data.getPhone());
							preStmt.executeUpdate();
//							System.out.println(SQL);
						}
					}
					//抓取客戶代號
					int nameNo = 0;
					SQL="select m002_No from man002 where m002_name='"+Data.getName()+"'";
					qryRs=qryStmt.executeQuery(SQL);
//					System.out.println(SQL);
					if(qryRs.next())
					{
						nameNo = qryRs.getInt("m002_No");
						System.out.println(nameNo);
					}
					String nameNoStr=Integer.toString(nameNo);
					//匯入訂單資料
					if(Data.getBirthDate().length()!=0) 
					{
						
						SQL="insert into sal003 ( s003_Billno,"
												+ "s003_NameNo,"
												+ "s003_Sex,"
												+ "s003_Phone,"
												+ "s003_BirthDate,"
												+ "s003_ServiceItem,"
												+ "s003_SalePrice,"
												+ "s003_CreateDate,"
												+ "s003_Memo) "
												+ "values (?,?,?,?,?,?,?,?,?)";
						preStmt=con.prepareStatement(SQL);
						preStmt.setInt(1,billno);
						preStmt.setString(2,nameNoStr);
						preStmt.setString(3,Data.getSex());
						preStmt.setString(4,Data.getPhone());
						preStmt.setString(5,Data.getBirthDate());
						preStmt.setString(6,Data.getServiceItem());
						int iPrice=Integer.parseInt(Data.getSalePrice());
						preStmt.setInt(7,iPrice);
						preStmt.setString(8,Data.getCreateDate());
						preStmt.setString(9,Data.getMemo());	
						preStmt.executeUpdate();
//						System.out.println(SQL);
						
						//更新客戶資料
						SQL=" update man002 "
							+ "  set m002_BirthDate='"+Data.getBirthDate()+"'"
							+ "where m002_no="+nameNo;
						preStmt=con.prepareStatement(SQL);
						preStmt.executeUpdate();
//						System.out.println(SQL);
						
					}else {
						SQL="insert into sal003 ( s003_Billno,"
												+ "s003_NameNo,"
												+ "s003_Sex,"
												+ "s003_Phone,"
												+ "s003_ServiceItem,"
												+ "s003_SalePrice,"
												+ "s003_CreateDate,"
												+ "s003_Memo) "
												+ "values (?,?,?,?,?,?,?,?)";
					preStmt=con.prepareStatement(SQL);
					preStmt.setInt(1,billno);
					preStmt.setString(2,nameNoStr);
					preStmt.setString(3,Data.getSex());
					preStmt.setString(4,Data.getPhone());
					preStmt.setString(5,Data.getServiceItem());
					int iPrice=Integer.parseInt(Data.getSalePrice());
					preStmt.setInt(6,iPrice);
					preStmt.setString(7,Data.getCreateDate());
					preStmt.setString(8,Data.getMemo());
					preStmt.executeUpdate();
//					System.out.println(SQL);
							
			}
					qryRs.close();
					qryStmt.close();
					preStmt.close();
					mod.addAttribute("result", "success");
				
				
			}
			catch(Exception e)
			{
				SQL="insert into errorlog (  e_FormPostion , "
										+ "  e_ErrorMessage, "
										+ "	 e_Date )"
										+ "  values (?,?,?)";
				try {

					Connection con =datasource.getConnection();
					PreparedStatement Stmt=con.prepareStatement(SQL);
					//類別名稱+方法名稱
					Stmt.setString(1,this.getClass().getName()+"."+Thread.currentThread().getStackTrace()[1].getMethodName() );
					//例外的完整訊息(包含例外種類)
					Stmt.setString(2,e.toString());
					//當下日期(yyyy-mm-dd hh:mm:ss)
					Stmt.setString(3,NowLocalDateTime.nowDateTime);
					Stmt.executeUpdate();
					con.close();
					Stmt.close();
				}catch(Exception ex) {
					System.out.println(ex);
				}
				finally {				
					mod.addAttribute("result", "error");
				}
			}
		
		}
		else {
			mod.addAttribute("result", "error");
		}
		return "checkForm";
	}
	
	

	@GetMapping("/OrderForm") // 一開始登入表單時走此方法
	public String loginForm(Model mod) {
//		System.out.println("loginForm: (GET) "+NowLocalDateTime.nowDateTime);
		List<Product> result = new ArrayList<Product>();
		List<Customer> customerList = new ArrayList<Customer>();
		
		try(Connection con = datasource.getConnection()) {
			String sql = "select i001_Code, i001_Name from inv001 where i001_kind = 'Microblanding' ";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println(SQL);
			while(rs.next()){
				Product micro = new Product();
				micro.setCode(rs.getString("i001_Code"));
				micro.setName(rs.getString("i001_Name"));
				result.add(micro);
			}
			result.sort((Product a, Product b) -> (a.getCode().compareTo(b.getCode())));
			
			//抓取所有客戶基本資料
			sql="select * from man002";
			rs = stmt.executeQuery(sql);
//			System.out.println(SQL);
			while(rs.next())
			{
				Customer customer = new Customer();
				customer.setName(rs.getString("m002_Name"));
				customer.setSex(rs.getString("m002_Sex"));
				customer.setPhone(rs.getString("m002_Phone"));
				customer.setBirthDate(rs.getString("m002_BirthDate"));
				customerList.add(customer);
			}
			
			rs.close();
			stmt.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		mod.addAttribute("product", result);
		mod.addAttribute("customerList", customerList);
		return "orderForm";
	}
	
	@GetMapping("/test") 
	public String testForm() {
		System.out.println("test");
		return "test";
	}
}
