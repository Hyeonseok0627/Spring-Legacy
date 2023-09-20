package com.myspring.pro30.board.service;

import java.util.List;
import java.util.Map;

import com.myspring.pro30.board.vo.ArticleVO;

public interface BoardService {
	public List<ArticleVO> listArticles() throws Exception;
	// 기존, 단일 이미지 글쓰기와 같은 메서드, 
	// 다중 이미지 글쓰기 재사용.
	public int addNewArticle(Map articleMap) throws Exception;
	// 단일 이미지 상세 뷰 보기
//	public ArticleVO viewArticle(int articleNO) throws Exception;
	// 다중 이미지 상세 뷰보기
	public Map viewArticle(int articleNO) throws Exception;
	public void modArticle(Map articleMap) throws Exception;
	//답글작성하기.
	public int addReplyNewArticle(Map articleMap) throws Exception;
	
	public void removeArticle(int articleNO) throws Exception;
}
