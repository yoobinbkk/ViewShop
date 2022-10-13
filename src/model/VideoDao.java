package model;

import java.util.ArrayList;

import model.vo.VideoVO;

public interface VideoDao {
	public int 			insertVideo		(VideoVO vo, int count) 	throws Exception;
	public ArrayList 	selectVideo		(int combo, String text) 	throws Exception;
	public VideoVO		selectByVnum	(int vnum) 					throws Exception;
	public int 			modifyVideo		(VideoVO vo) 				throws Exception;
	public int			deleteVideo		(int video_no) 				throws Exception;
}
