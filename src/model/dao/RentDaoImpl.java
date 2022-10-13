package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import model.RentDao;


public class RentDaoImpl implements RentDao {
	
	final static String DRIVER 	= "oracle.jdbc.driver.OracleDriver";
	final static String URL 	= "jdbc:oracle:thin:@192.168.0.50:1521:xe";
	final static String USER 	= "project";
	final static String PASS 	= "1234";
	
	// Connection con;
	
	public RentDaoImpl() throws Exception{
		// 1. 드라이버로딩
		Class.forName(DRIVER);
	}

	@Override
	public void rentVideo(String tel, String video_no) throws Exception {
		
		// 2. Connection 연결객체 얻어오기
		Connection con = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		String sql = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASS);
			
			sql = "select return from rent where video_no = ?";
			
			ps1 = con.prepareStatement(sql);
			ps1.setString(1, video_no);
			ResultSet rs = ps1.executeQuery();
			String i = null;
			while(rs.next()) {
				i = rs.getString("return");
			}
			
			if(i.equals("Y")) {
				sql = "INSERT INTO rent(rentno, rentdate, returndate, return, custtel1, video_no) "
						+ " VALUES(rent_seq.nextval, sysdate, ?, 'N', ?, ?)";

				// 4. sql 전송객체 (PreparedStatement)
				ps2 = con.prepareStatement(sql);
				ps2.setString(1, null);
				ps2.setString(2, tel);
				ps2.setString(3, String.valueOf(video_no));

				ps2.executeUpdate();
				ps2.close();
			}
		
		} finally {
			// 6. 닫기
			ps1.close();
			con.close();
		}
		
	}

	@Override
	public void returnVideo(String video_no) throws Exception {
		
		// 2. Connection 연결객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASS);

			String sql = "UPDATE rent SET returndate = sysdate, return = 'Y' WHERE video_no = ? AND return = 'N'";

			// 4. sql 전송객체 (PreparedStatement)
			ps = con.prepareStatement(sql);
			ps.setString(1, video_no);

			ps.executeUpdate();

		} finally {
			// 6. 닫기
			ps.close();
			con.close();
		}

	}

	@Override
	public ArrayList selectList() throws Exception {
		
		ArrayList data = new ArrayList();
		// 2. Connection 연결객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		try {
			
			con = DriverManager.getConnection(URL, USER, PASS);
			
			String sql = "SELECT v.video_no, v.title, c.custname, c.custtel1, r.rentdate, '미납' NNN "
							+ " FROM rent r INNER JOIN video v ON r.video_no = v.video_no "
							   		   + " INNER JOIN customer c ON r.custtel1 = c.custtel1 "
							   		   + " where r.return = 'N'";
			ps = con.prepareStatement(sql);

			// 전송
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ArrayList temp = new ArrayList();
				temp.add(rs.getInt("video_no"));
				temp.add(rs.getString("title"));
				temp.add(rs.getString("custname"));
				temp.add(rs.getString("custtel1"));
				temp.add(rs.getString("rentdate"));
				temp.add(rs.getString("NNN"));
				data.add(temp);
			}
		
		} finally {
			// 6. 닫기
			ps.close();
			con.close();
		}
		
		return data;
		
	}

	@Override
	public String selectNameByTel(String tel) throws Exception {
		
		// 2. Connection 연결객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		String data = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASS);
			
			// sql 문장
			String sql = "select custname from customer where custtel1 = ?";
			
			ps = con.prepareStatement(sql);
			ps.setString(1, tel);
			
			String temp = null;
			
			// 전송
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				temp = rs.getString("custname");
			}
			
			data = temp;
			
		} finally {
			// 6. 닫기
			ps.close();
			con.close();
		}
		
		return data;
			
	}
	
}
