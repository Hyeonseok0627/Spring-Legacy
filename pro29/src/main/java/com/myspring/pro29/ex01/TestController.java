package com.myspring.pro29.ex01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/test/*")
public class TestController {
  static Logger logger = LoggerFactory.getLogger(TestController.class);
	
  @RequestMapping("/hello")
  public String hello() {
	return "Hello REST!!";
  } 
  
  @RequestMapping("/member")
  public MemberVO member() {
    MemberVO vo = new MemberVO();
    vo.setId("hong");
    vo.setPwd("1234");
    vo.setName("홍길동");
    vo.setEmail("hong@test.com");
    return vo;
  } 	
  
  @RequestMapping("/membersList")
  public List<MemberVO> listMembers () {
    List<MemberVO> list = new ArrayList<MemberVO>();
	for (int i = 0; i < 10; i++) {
	  MemberVO vo = new MemberVO();
	  vo.setId("hong"+i);
	  vo.setPwd("123"+i);
	  vo.setName("홍길동"+i);
	  vo.setEmail("hong"+i+"@test.com");
	  list.add(vo);
	}
    return list; 
  }	
  
  @RequestMapping("/membersMap")
  public Map<Integer, MemberVO> membersMap() {
    Map<Integer, MemberVO> map = new HashMap<Integer, MemberVO>();
    for (int i = 0; i < 10; i++) {
      MemberVO vo = new MemberVO();
      vo.setId("hong" + i);
      vo.setPwd("123"+i);
      vo.setName("홍길동" + i);
      vo.setEmail("hong"+i+"@test.com");
      map.put(i, vo); 
    }
    return map; 
  } 	
  
  // @PathVariable : 서버의 url 주소에서, 특정의 매개변수를 서버에서 가져올 때 사용
  // 예) 게시판에서, 특정 게시글의 상세페이지를 보고 싶다.
  // 해당 게시글의 번호를, 서버에게 전달해야, 해당 게시글 번호의 담긴 내용을 찾을 수 있다.
  // 상세페이지 몇번으로 선택하면 확인하는 방식으로 결과뷰에 데이터 전달할 때 사용
  @RequestMapping(value= "/notice/{num}" , method = RequestMethod.GET)
  public int notice(@PathVariable("num") int num ) throws Exception {
	  System.out.println("클라이언트로 부터 받은 매개변수 서버에서 확인 num : " + num);
	  return num;
  }	

  @RequestMapping(value= "/info", method = RequestMethod.POST)
  public void modify(@RequestBody MemberVO vo ){
    logger.info(vo.toString());
  }
  
  @RequestMapping("/membersList2")
  public  ResponseEntity<List<MemberVO>> listMembers2() {
	List<MemberVO> list = new ArrayList<MemberVO>();
	for (int i = 0; i < 10; i++) {
	  MemberVO vo = new MemberVO();
	  vo.setId("lee" + i);
	  vo.setPwd("123"+i);
	  vo.setName("이현석" + i);
      vo.setEmail("lee"+i+"@test.com");
	  list.add(vo);
	}
	//return new ResponseEntity(list,HttpStatus.INTERNAL_SERVER_ERROR);
    return new ResponseEntity(list,HttpStatus.OK);
  }	
  
  
	@RequestMapping(value = "/res3")
	public ResponseEntity res3() {
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.add("Content-Type", "text/html; charset=utf-8");
	    String message = "<script>";
		message += " alert('res3 테스트 중');";
		message += " location.href='/pro29/test/membersList2'; ";
		message += " </script>";
		return  new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
	}
	
}
