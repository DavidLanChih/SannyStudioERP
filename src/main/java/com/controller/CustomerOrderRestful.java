package com.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bean.CustomerOrder;
import com.bean.NowLocalDateTime;

@RestController
@RequestMapping("/Service/Customer")
public class CustomerOrderRestful {
	@Autowired
	private DataSource datasource;
	
	@Autowired
	@Qualifier("sannystudioJDBC")
	private JdbcTemplate jdbcTemplate;
	@GetMapping(path="/Order",produces= {"application/json"})
	public ResponseEntity<Object> CustomerQry(@RequestParam("name")String name) {
		
		List<CustomerOrder> result = null;
		ResponseEntity<Object> response = null;
		try {
			var mapper = BeanPropertyRowMapper.newInstance(CustomerOrder.class);
			result = jdbcTemplate.query("select  s003_name"
											+ ", s003_sex"
											+ ", s003_phone"
											+ ", s003_serviceItem"
											+ ", s003_salePrice"
											+ ", s003_createDate"
											+ ", s003_memo from sal003" 
											+ " where s003_name=?",
											mapper, 
											new Object[] {name});
			if(result.size()==0) {
				response=ResponseEntity.badRequest().body("查無 "+name+" 客戶資料");
			}else {
				response=ResponseEntity.ok(result);
				System.out.println(result.iterator().next().getPhone());
			}
		} catch (Exception e) {
			// TODO: handle exception
			String SQL = "insert into errorlog (  e_FormPostion , "
						+ " e_ErrorMessage, "
						+ "	e_Date )"
						+ " values (?,?,?)";
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
				}
			catch(Exception ex) {
				System.out.println(ex);
			}
		}
		
		return response;
	}
}
