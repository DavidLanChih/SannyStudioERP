package com.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.bean.CustomerOrder;
import com.bean.NowLocalDateTime;

@RestController
@RequestMapping("/Service/Customer")
public class CustomerOrderRestful {
	@Autowired
	private DataSource datasource;

	// @Autowired
	// @Qualifier("sannystudioJDBC")
	// private JdbcTemplate jdbcTemplate;


	@GetMapping(path = "/Order/{name}", produces = { "application/json" })
	public ResponseEntity CustomerQry(@PathVariable("name") String name) {
		// List初始化一定要放這邊，每次重新進來會順便清空舊有資料
		List<CustomerOrder> result = new ArrayList<CustomerOrder>();
		Connection connection;
		ResponseEntity response = null;
		try {
			connection = datasource.getConnection();
			String sql = "select a.m002_Name, b.* ,c.i001_Name"
					   + "  from man002 as a "
					   + " inner join sal003 as b "
					   + "    on a.m002_No = b.s003_NameNo "
					   + " inner join inv001 as c"
					   + "    on b.s003_ServiceItem = c.i001_Code"
					   + " where b.s003_NameNo = ?";
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, name);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				CustomerOrder customer = new CustomerOrder();
				customer.setBillno(rs.getString("s003_Billno"));
				customer.setName(rs.getString("s003_NameNo"));
				customer.setServiceItem(rs.getString("i001_Name"));
				customer.setSalePrice(rs.getString("s003_SalePrice"));
				customer.setCreateDate(rs.getString("s003_CreateDate"));
				customer.setMemo(rs.getString("s003_Memo"));
				result.add(customer);
			}
			// 依照日期排序
			result.sort((CustomerOrder a, CustomerOrder b) -> (a.getCreateDate().compareTo(b.getCreateDate())));
			response = ResponseEntity.ok(result);
//			System.out.println("記錄數:" + result.size());
//			System.out.println(result.iterator().next().getServiceItem());
			// 關閉連接
			rs.close();
			st.close();
			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}

		return response;

		// BeanPropertyRowMapper<Customer>
		// rm=BeanPropertyRowMapper.newInstance(Customer.class);
		// ResponseEntity response = null;
		// String sql = "select s003_serviceItem from sal003 where s003_name = ?";
		// try {
		// //var mapper = BeanPropertyRowMapper.newInstance(Customer.class);
		// result = jdbcTemplate.query(sql,
		// rm,
		// new Object[]{name});
		// //result =jdbcTemplate.queryForList("select s003_serviceItem from sal003
		// where s003_name = ?", new Object[]{name}, mapper);
		// if(result.size()==0) {
		// response=ResponseEntity.badRequest().body("查無 "+name+" 客戶資料");
		// }else {
		// response=ResponseEntity.ok(result);
		// System.out.println(result.iterator().next().getServiceItem());
		// System.out.println(result.size());
		// System.out.println(name);
		// }
		// } catch (Exception e) {
		// // TODO: handle exception
		// String SQL = "insert into errorlog ( e_FormPostion , "
		// + " e_ErrorMessage, "
		// + " e_Date )"
		// + " values (?,?,?)";
		// try {
		//
		// Connection con =datasource.getConnection();
		// PreparedStatement Stmt=con.prepareStatement(SQL);
		// //類別名稱+方法名稱
		// Stmt.setString(1,this.getClass().getName()+"."+Thread.currentThread().getStackTrace()[1].getMethodName()
		// );
		// //例外的完整訊息(包含例外種類)
		// Stmt.setString(2,e.toString());
		// //當下日期(yyyy-mm-dd hh:mm:ss)
		// Stmt.setString(3,NowLocalDateTime.nowDateTime);
		// Stmt.executeUpdate();
		// con.close();
		// Stmt.close();
		// }
		// catch(Exception ex) {
		// System.out.println(ex);
		// }
		// }
		//
		// return response;
	}
}
