package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.VideoDao;
import model.vo.VideoVO;

public class VideoDaoImpl implements VideoDao{
	
	final static String DRIVER 	= "oracle.jdbc.driver.OracleDriver";
	final static String URL 	= "jdbc:oracle:thin:@192.168.0.50:1521:xe";
	final static String USER 	= "project";
	final static String PASS 	= "1234";
	
	public VideoDaoImpl() throws Exception{

		// 1. 드라이버로딩
		Class.forName(DRIVER);
		
	}
	
	public void insertVideo(VideoVO vo, int count) throws Exception{
		
		// 2. Connection 연결객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASS);
		
		// 3. sql 문장 만들기
		String sql = "INSERT INTO video (video_no, genre, title, director, actor, exp) "
				+ " values(project_seq.nextval, ?, ?, ?, ?, ?)";
		
		// 4. sql 전송객체 (PreparedStatement)
		ps = con.prepareStatement(sql);
		ps.setString(1, vo.getGenre());
		ps.setString(2, vo.getVideoName());
		ps.setString(3, vo.getDirector());
		ps.setString(4, vo.getActor());
		ps.setString(5, vo.getExp());

		for(int i=0 ; i<count; i++) {
			// 5. sql 전송
			ps.executeUpdate();
		}
		
		} finally {
			// 6. 닫기
			ps.close();
			con.close();
		}
		
	}


	@Override
	public ArrayList selectVideo(String combo, String text) throws Exception {
		
		ArrayList data = new ArrayList();
		// 2. Connection 연결객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		try {
			
			con = DriverManager.getConnection(URL, USER, PASS);
			
			String sql = null;
			
			if (text.equals("")) {
				sql = "SELECT video_no, title, director, actor FROM video";
				ps = con.prepareStatement(sql);
			} else if (combo.equals("제목") & text.length()>0) {
				sql = "SELECT video_no, title, director, actor FROM video where title = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, text);
			} else if (combo.equals("감독") & text.length()>0) {
				sql = "SELECT video_no, title, director, actor FROM video where director = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, text);
			}

			// 전송
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ArrayList temp = new ArrayList();
				temp.add(rs.getInt("video_no"));
				temp.add(rs.getString("title"));
				temp.add(rs.getString("director"));
				temp.add(rs.getString("actor"));
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
	public void updateVideo(VideoVO vo, String title) throws Exception {
		
		// 2. Connection 연결객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASS);
			
			// 3. sql 문장 만들기
			String sql = "UPDATE video "
					+ " SET genre = ?, director = ?, actor = ?, exp = ?"
					+ " where title = ?";

			// 4. sql 전송객체 (PreparedStatement)
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getGenre());
			ps.setString(2, vo.getDirector());
			ps.setString(3, vo.getActor());
			ps.setString(4, vo.getExp());
			ps.setString(5, title);

			// 5. sql 전송
			ps.executeUpdate();

		} finally {
			// 6. 닫기
			ps.close();
			con.close();
		}
		
	}
	

}
