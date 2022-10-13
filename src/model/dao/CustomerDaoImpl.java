package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.CustomerDao;
import model.vo.CustomerVO;

public class CustomerDaoImpl implements CustomerDao{

	final static String DRIVER 	= "oracle.jdbc.driver.OracleDriver";
	final static String URL 	= "jdbc:oracle:thin:@192.168.0.50:1521:xe";
	final static String USER 	= "project";
	final static String PASS 	= "1234";
	
	public CustomerDaoImpl() throws Exception{
	 	// 1. 드라이버로딩
		Class.forName(DRIVER);
		System.out.println("드라이버 로딩 성공");
	}
	
	public void insertCustomer(CustomerVO vo) throws Exception{
		// 2. Connection 연결객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASS);
		
		// 3. sql 문장 만들기
		String sql = "INSERT INTO CUSTOMER (custTel1, custName, custTel2, custAddr, custEmail) VALUES (?, ?, ?, ?, ?)";
		
		// 4. sql 전송객체 (PreparedStatement)		
		ps = con.prepareStatement(sql);
		ps.setString(1, vo.getCustTel1());
		ps.setString(2, vo.getCustName());
		ps.setString(3, vo.getCustTel2());
		ps.setString(4, vo.getCustAddr());
		ps.setString(5, vo.getCustEmail());
		
		// 5. sql 전송
		ps.executeUpdate();
		
		} finally {
			// 6. 닫기
			ps.close();
			con.close();
		}

	}
	
	/*
		메소드명	: selectByTel
		인자		: 검색할 전화번호
		리턴값	: 전화번호 검색에 따른 고객정보
		역할		: 사용자가 입력한 전화번호를 받아서 해당하는 고객정보를 리턴
	*/
	public CustomerVO selectByTel(String tel) throws Exception{
		CustomerVO dao = new CustomerVO();
		// 2. 연결 객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASS);
		
		
		// 3. sql 문장
		String sql = "SELECT * FROM CUSTOMER WHERE custTel1 = ?";
		
		// 4. 전송 객체
		ps = con.prepareStatement(sql);
		ps.setString(1, tel);
		
		// 5. 전송 - executeQuery()
		//		결과를 CustomerVO에 담기
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			dao.setCustName(rs.getString("custName"));
			dao.setCustTel1(rs.getString("custTel1"));
			dao.setCustTel2(rs.getString("custTel2"));
			dao.setCustAddr(rs.getString("custAddr"));
			dao.setCustEmail(rs.getString("custEmail"));
		}
		
		} finally {
			// 6. 닫기
			ps.close();
			con.close();
		}
		
		return dao;
		
	}
	
	public void updateCustomer(CustomerVO vo) throws Exception{
		
		// 2. Connection 연결객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASS);

			// 3. sql 문장 만들기
			String sql = "UPDATE customer "
					+ " SET custname = ?, custtel2 = ?, custaddr = ?, custemail = ?"
					+ " where custtel1 = ?";

			// 4. sql 전송객체 (PreparedStatement)
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getCustName());
			ps.setString(2, vo.getCustTel2());
			ps.setString(3, vo.getCustAddr());
			ps.setString(4, vo.getCustEmail());
			ps.setString(5, vo.getCustTel1());

			// 5. sql 전송
			ps.executeUpdate();

		} finally {
			// 6. 닫기
			ps.close();
			con.close();
		}

	}

	@Override
	public CustomerVO selectByName(String name) throws Exception {
		CustomerVO dao = new CustomerVO();
		// 2. 연결 객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASS);
		
		// 3. sql 문장
		String sql = "SELECT * FROM CUSTOMER WHERE custName = ? ";
		
		// 4. 전송 객체
		ps = con.prepareStatement(sql);
		ps.setString(1, name);
		
		// 5. 전송 - executeQuery()
		//		결과를 CustomerVO에 담기
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			dao.setCustName(rs.getString("custName"));
			dao.setCustTel1(rs.getString("custTel1"));
			dao.setCustTel2(rs.getString("custTel2"));
			dao.setCustAddr(rs.getString("custAddr"));
			dao.setCustEmail(rs.getString("custEmail"));
		}
		
		} finally {
			// 6. 닫기
			ps.close();
			con.close();
		}
		
		return dao;
	}
}
