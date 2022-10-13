package model;

import java.util.ArrayList;

public interface RentDao {
	
	// 대여
	public void rentVideo(String tel, String video_no) throws Exception;
	
	// 반납
	public void returnVideo(String video_no) throws Exception;
	
	// 미납 목록 검색
	public ArrayList selectList() throws Exception;
	
	// 이름
	public String selectNameByTel(String tel) throws Exception;
	
}
