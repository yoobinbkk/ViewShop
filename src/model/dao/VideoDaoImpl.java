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
	
	public int insertVideo(VideoVO vo, int count) throws Exception{
		
		// 2. Connection 연결객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		int rs = 0;
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
			rs++;
		}
		
		} finally {
			// 6. 닫기
			ps.close();
			con.close();
		}
		
		return rs;
		
	}


	@Override
	public ArrayList selectVideo(int combo, String text) throws Exception {
		
		ArrayList data = new ArrayList();
		// 2. Connection 연결객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		try {
			
			con = DriverManager.getConnection(URL, USER, PASS);
			
			String[] colNames = {"title", "director"};
			String sql = null;
			
			if (text.equals("")) {
				sql = "SELECT video_no, title, director, actor FROM video";
				ps = con.prepareStatement(sql);
			} else if (text.length()>0) {
				sql = "SELECT video_no, title, director, actor FROM video where " + colNames[combo] + " like '%" + text + "%'";
				ps = con.prepareStatement(sql);
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
	public int modifyVideo(VideoVO vo) throws Exception {
		
		// 2. Connection 연결객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		int rs = 0;
		try {
			con = DriverManager.getConnection(URL, USER, PASS);
			
			// 3. sql 문장 만들기
			String sql = "UPDATE video "
					+ " SET title = ?, genre = ?, director = ?, actor = ?, exp = ?"
					+ " where video_no = ?";

			// 4. sql 전송객체 (PreparedStatement)
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getVideoName());
			ps.setString(2, vo.getGenre());
			ps.setString(3, vo.getDirector());
			ps.setString(4, vo.getActor());
			ps.setString(5, vo.getExp());
			ps.setString(6, String.valueOf(vo.getVideoNo()));

			// 5. sql 전송
			rs = ps.executeUpdate();

		} finally {
			// 6. 닫기
			ps.close();
			con.close();
		}
		
		return rs;
		
	}
	
	/*
	 * 메소드명	:	selectByVnum
	 * 인자		:	비디오 번호
	 * 리턴값		:	비디오 정보
	 * 역할		:	비디오 번호를 넘겨받아 해당 비디오 번호의 비디오 정보를 리턴
	 */
	@Override
	public VideoVO selectByVnum(int video_no) throws Exception {
		
		VideoVO vo = new VideoVO();
		
		// 2. Connection 연결객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		try {

			con = DriverManager.getConnection(URL, USER, PASS);
		
		String sql = "Select * from video where video_no = ?";
		
		// 4. 전송 객체
		ps = con.prepareStatement(sql);
		ps.setString(1, String.valueOf(video_no));

		// 5. 전송 - executeQuery()
		//		결과를 CustomerVO에 담기
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			vo.setVideoNo(video_no);
			vo.setGenre(rs.getString("genre"));
			vo.setVideoName(rs.getString("title"));
			vo.setDirector(rs.getString("director"));
			vo.setActor(rs.getString("actor"));
			vo.setExp(rs.getString("exp"));
		}
		
		} finally {
			// 6. 닫기
			ps.close();
			con.close();
		}
		
		return vo;
	}

	/*
	 * 메소드명	:	deleteVideo
	 * 인자		:	비디오 번호
	 * 반환값		:	삭제된 행수
	 * 역할		:	비디오 번호를 넘겨받아 해당 번호의 레코드를 삭제
	 */
	@Override
	public int deleteVideo(int video_no) throws Exception {
		
		// 2. Connection 연결객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;
		int rs = 0;
		try {
			con = DriverManager.getConnection(URL, USER, PASS);

			// 3. sql 문장 만들기
			String sql = "DELETE FROM video WHERE video_no = ?";

			// 4. sql 전송객체 (PreparedStatement)
			ps = con.prepareStatement(sql);
			ps.setString(1, String.valueOf(video_no));

			// 5. sql 전송
			rs = ps.executeUpdate();
		
		} finally {
			// 6. 닫기
			ps.close();
			con.close();
		}
		
		return rs;

	}
	

}
