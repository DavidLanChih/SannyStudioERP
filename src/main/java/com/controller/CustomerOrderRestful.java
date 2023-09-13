package com.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bean.Customer;
import com.bean.NowLocalDateTime;

@RestController
@RequestMapping("/Service/Customer")
public class CustomerOrderRestful {
	@Autowired
	private DataSource datasource;
	
	@Autowired
	@Qualifier("sannystudioJDBC")
	private JdbcTemplate jdbcTemplate;
	List<Customer> result =new ArrayList<Customer>();
	@GetMapping(path="/Order/{name}",produces= {"application/json"})
	public ResponseEntity CustomerQry(@PathVariable("name") String name) {
			//進行查詢
			//1.透過注入連接工廠DataSource 問出一個Connection物件
			Connection connection;
			ResponseEntity response = null;
			try {
				//1.透過注入連接工廠DataSource 問出一個Connection物件(同時開啟連接)
				connection = datasource.getConnection();
					//採用參數架構 防範SQL Injection
				String sql = "select s003_serviceItem from sal003 where s003_name = ?";
				//透過連接拖出來那一個PreparedStatement物件
					PreparedStatement st=connection.prepareStatement(sql);
					//下參數(參數順序從1開始)
					st.setString(1,name);
					//執行查詢 拖出來管理查詢結果的ResutSet物件(線上查詢Fetching)
					ResultSet rs=st.executeQuery();
					//必須保持連線 逐筆截取下來 Fetching--線上讀取資料(逐筆讀取)--產生離線物件模組Model
					//建構集合物件
					//介面 介面多型化<Model-JavaBean> 建構實作該介面類別
					//逐筆讀取 將資料封裝到JavaBean去
					while(rs.next()) {
						//建構Javabean物件
						Customer customer=new Customer();
						//封裝欄位
						customer.setServiceItem(rs.getString("s003_serviceItem"));
						//讓集合參考
						result.add(customer);
					}
					response=ResponseEntity.ok(result);
					//逐筆讀取下來 封裝成前端離線物件模組(集合收集 List<javabean>
					System.out.println("記錄數:"+result.size());
					System.out.println(result.iterator().next().getServiceItem());
					//關閉連接
					connection.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("連接資料錯誤....");
				}
			
			return response;
		
//		BeanPropertyRowMapper<Customer> rm=BeanPropertyRowMapper.newInstance(Customer.class);
//		ResponseEntity response = null;
//		String sql = "select s003_serviceItem from sal003 where s003_name = ?";
//		try {
//			//var mapper = BeanPropertyRowMapper.newInstance(Customer.class);
//			 result = jdbcTemplate.query(sql,
//					rm, 
//					new Object[]{name});
//			//result =jdbcTemplate.queryForList("select s003_serviceItem from sal003 where s003_name = ?", new Object[]{name}, mapper);
//			if(result.size()==0) {
//				response=ResponseEntity.badRequest().body("查無 "+name+" 客戶資料");
//			}else {
//				response=ResponseEntity.ok(result);
//				System.out.println(result.iterator().next().getServiceItem());
//				System.out.println(result.size());
//				System.out.println(name);
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			String SQL = "insert into errorlog (  e_FormPostion , "
//						+ " e_ErrorMessage, "
//						+ "	e_Date )"
//						+ " values (?,?,?)";
//			try {
//			
//				Connection con =datasource.getConnection();
//				PreparedStatement Stmt=con.prepareStatement(SQL);
//				//類別名稱+方法名稱
//				Stmt.setString(1,this.getClass().getName()+"."+Thread.currentThread().getStackTrace()[1].getMethodName() );
//				//例外的完整訊息(包含例外種類)
//				Stmt.setString(2,e.toString());
//				//當下日期(yyyy-mm-dd hh:mm:ss)
//				Stmt.setString(3,NowLocalDateTime.nowDateTime);
//				Stmt.executeUpdate();
//				con.close();
//				Stmt.close();
//				}
//			catch(Exception ex) {
//				System.out.println(ex);
//			}
//		}
//		
//		return response;
	}
}
