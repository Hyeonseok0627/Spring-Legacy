package com.myspring.pro29.lhs0918;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/members")
public class MemberController {
	static Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	// 클라이언트 주소 : pro29/boards/all
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	// ResponseEntity : 데이터만 전달하면, 상태 확인도 같이하자. , 덤으로
	// 헤더에 특정의 속성도 같이 추가 가능.
	public ResponseEntity<List<MemberVO>> listArticles() {
		// 로그 기록을 조금 더 기능을 잘 구현한 라이브러리를 이용해서, 기록하자.
		logger.info("listMember 호출");
		// 서버에서 -> 클라이언트에게 데이터 전달하는 샘플 더미 데이터
		// 동네1 ~ 동네4 다 순회 후, 데이터를 직접 전달.
		List<MemberVO> list = new ArrayList<MemberVO>();
		// ArticleVO 타입으로만, 박스에 담아서 전달 해주세요. 강력한 요구사항.
		for (int i = 0; i < 10; i++) {
			MemberVO vo = new MemberVO();
			vo.setId("gustjr"+i);
			vo.setPwd("1234"+i);
			vo.setName("이현석"+i);
			vo.setEmail("gustjr5059@naver.com"+i);
			list.add(vo);
		}
		// 서버 -> 클라이언트, 박스(데이터)(ArticleVO타입의 리스트인 list) 전달. + 상태 코드도 같이 전달.
		return new ResponseEntity(list,HttpStatus.OK);
	}
	
	// 클라이언트 주소: /pro29/boards/{articleNO}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	// @PathVariable("articleNO") Integer articleNO :클라이언트 주소 요청 시,
	// 주소 뒤에 매개변수 전달
	public ResponseEntity<MemberVO> findMember (@PathVariable("id") String id) {
		logger.info("findMember 서버에 잘 호출 되었는지 확인");
		MemberVO vo = new MemberVO();
		vo.setId(id);
		vo.setPwd("1234");
		vo.setName("이현석");
		vo.setEmail("gustjr5059@naver.com");
		// 서버 -> 클라이언트, 더미 데이터+ 상태 코드 같이 전달
		return new ResponseEntity(vo,HttpStatus.OK);
	}	
	
	// POST : 추가(피카추), PUT : (U)업데이트, GET : 조회, PATCH : 부분 수정,
	// DELET : 삭제
	// 추가 테스트
	// 클라이언트 주소 : /pro29/boards/ , POST
	// POST의 확인 방법 2가지
	// 1) 웹, 자바스크립트으로 보내기 -> JSONTest2.jsp
	// 2) 포스트맨으로 보내기
	@RequestMapping(value = "", method = RequestMethod.POST)
	// ResponseEntity : 데이터 + 상태
	// @RequestBody : -> 클라이언트 전달된 데이터를, 서버에서 자동으로 모델 클래스로 매핑
	public ResponseEntity<String> addMember (@RequestBody MemberVO id) {
		// 참조형 변수 타입으로 선언만,
		ResponseEntity<String>  resEntity = null;
		try {
			// log4j 이용해서 출력 확인 -> 서버에 데이터 전달 잘 되었는지 확인 유무로 사용
			logger.info("addMember 호출");
			logger.info(id.toString());
			resEntity =new ResponseEntity("ADD_SUCCEEDED",HttpStatus.OK);
		}catch(Exception e) {
			resEntity = new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
		return resEntity;
	}	
	
	//수정확인.
	// @PathVariable("articleNO") Integer articleNO, : 주소의 매개변수 서버에서 가져오기
	// @RequestBody ArticleVO articleVO : 서버에 전달된 데이터를, 모델 클래스에 자동 매핑
	// 클라이언트 주소 : /pro29/boards/777 , PUT
	// 임의로 전달
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> modMember (@PathVariable("id") String id, @RequestBody MemberVO id1) {
		ResponseEntity<String>  resEntity = null;
		try {
			logger.info("modId 확인222");
			logger.info(id1.toString());
			resEntity =new ResponseEntity("MOD_SUCCEEDED",HttpStatus.OK);
		}catch(Exception e) {
			resEntity = new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
		return resEntity;
	}
	
	//DELETE, 레스트 형식으로, CRUD
	//클라이언트 측에서 -> 서버에게 데이터 전달,
	//수정, 삭제 할 때, 어느 게시글 수정 또는 삭제를 할지를 번호로 알려주면,
	// 서버는, 땡큐하고, 동네 1~4번, 한바퀴 돈다.
	// 클라이언트 주소 : /pro29/boards/777 , DELETE
	// 확인 방법 2가지, 1) 웹, JS 2) 포스트맨 도구로 확인중.
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> removeMember (@PathVariable("id") String id) {
		ResponseEntity<String>  resEntity = null;
		try {
			// 처음에는 반드시, 클라이언트로 부터 전달 받은 데이터, 서버에서 확인하는 연습.
			// 개발 단계에서, 어느 정도 검토 및 확인이 1차로 끝나면
			// 배포 시에는 각 주석을 모두 제거하고 깔끔하게 업로드 한다고
			// 문제가 발생시, 우리는 개발 하는 단계(주석 등 메시지 포함)
			// 버전으로 점검 후, 다시 배포함.
			logger.info("removeMember 호출");
			System.out.println("반드시 클라이언트로부터 넘어온 정보를 확인하는 습관");
			// 데이터 메시지와, 상태 코드를 함께 전달함.
			logger.info(id.toString());
			resEntity =new ResponseEntity("REMOVE_SUCCEEDED",HttpStatus.OK);
		}catch(Exception e) {
			resEntity = new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
		return resEntity;
	}	

}
