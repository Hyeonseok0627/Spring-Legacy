package com.myspring.pro30.member.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myspring.pro30.member.service.MemberService;
import com.myspring.pro30.member.vo.MemberVO;



@Controller("memberController")
@EnableAspectJAutoProxy
public class MemberControllerImpl   implements MemberController {
//	private static final Logger logger = LoggerFactory.getLogger(MemberControllerImpl.class);
	@Autowired
	private MemberService memberService;
	@Autowired
	MemberVO memberVO ;
	
	 @RequestMapping(value = "/main", method = RequestMethod.GET)
	  public String home(Locale locale, Model model) {
//	    logger.info("Welcome home! The client locale is {}.", locale);

	    // 나중에, 파일명에서, 중복파일 피하기 위해서 사용하는 네이밍 기법 중 하나인데,
	    // 1) 시간 및 날짜를 이용(현재 쓰는 기법)
	    // 2) UUID, 특정 랜덤한 숫자 및 영문자를 생성해주는 도구를 이용하기도함
	    Date date = new Date();
	    DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, 
	    DateFormat.LONG, locale);
	    String formattedDate = dateFormat.format(date);
	    // 단순 데이터만, 뷰에 전달하는 구조만 잠시 보면 됨.
	    model.addAttribute("serverTime", formattedDate );
	    // 결과 뷰는, 모델&뷰 형식이 아니라, 단순 뷰 리졸버 해당 뷰로 이동함.
//	    return "home";
	    return "main";
	  }
	
	
	@Override
	@RequestMapping(value="/member/listMembers.do" ,method = RequestMethod.GET)
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
//		String viewName = (String)request.getAttribute("viewName");
		//System.out.println("viewName: " +viewName);
//		logger.info("viewName: "+ viewName);
//		logger.debug("viewName: "+ viewName);
		List membersList = memberService.listMembers();
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("membersList", membersList);
		return mav;
	}

	@Override
	@RequestMapping(value="/member/addMember.do" ,method = RequestMethod.POST)
	public ModelAndView addMember(@ModelAttribute("member") MemberVO member, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int result = 0;
		result = memberService.addMember(member);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}
	
	@Override
	@RequestMapping(value="/member/removeMember.do" ,method = RequestMethod.GET)
	public ModelAndView removeMember(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		memberService.removeMember(id);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}
	/*
	@RequestMapping(value = { "/member/loginForm.do", "/member/memberForm.do" }, method =  RequestMethod.GET)
	@RequestMapping(value = "/member/*Form.do", method =  RequestMethod.GET)
	public ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	*/
	
	@Override
	@RequestMapping(value = "/member/login.do", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("member") MemberVO member,
				              RedirectAttributes rAttr,
		                       HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView();
	memberVO = memberService.login(member);
	if(memberVO != null) {
		    HttpSession session = request.getSession();
		    session.setAttribute("member", memberVO);
		    session.setAttribute("isLogOn", true);
		    mav.setViewName("redirect:/member/listMembers.do");
	}else {
		    rAttr.addAttribute("result","loginFailed");
		    mav.setViewName("redirect:/loginForm.do");
	}
	return mav;
	}

	@Override
	@RequestMapping(value = "/member/logout.do", method =  RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute("member");
		session.removeAttribute("isLogOn");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/member/listMembers.do");
		return mav;
	}	

	@RequestMapping(value = "/member/*Form.do", method =  RequestMethod.GET)
	private ModelAndView form(@RequestParam(value= "result", required=false) String result,
						       HttpServletRequest request, 
						       HttpServletResponse response) throws Exception {
		System.out.println("*Form.do 실행 여부 확인===================");
		String viewName = getViewName(request);
//		String viewName = (String)request.getAttribute("viewName");
		System.out.println("*viewName 확인===================" + viewName);
		ModelAndView mav = new ModelAndView();
		System.out.println("*result 확인===================" + result);
		mav.addObject("result",result);
		mav.setViewName(viewName);
		return mav;
	}
	

	private String getViewName(HttpServletRequest request) throws Exception {
		String contextPath = request.getContextPath();
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		if (uri == null || uri.trim().equals("")) {
			uri = request.getRequestURI();
		}

		int begin = 0;
		if (!((contextPath == null) || ("".equals(contextPath)))) {
			begin = contextPath.length();
		}

		int end;
		if (uri.indexOf(";") != -1) {
			end = uri.indexOf(";");
		} else if (uri.indexOf("?") != -1) {
			end = uri.indexOf("?");
		} else {
			end = uri.length();
		}

		String viewName = uri.substring(begin, end);
		if (viewName.indexOf(".") != -1) {
			viewName = viewName.substring(0, viewName.lastIndexOf("."));
		}
		if (viewName.lastIndexOf("/") != -1) {
			viewName = viewName.substring(viewName.lastIndexOf("/", 1), viewName.length());
		}
		return viewName;
	}

	@Override
	@RequestMapping(value = "/member/modMember.do", method = RequestMethod.GET)
	public ModelAndView modMember(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 수정하는 폼에서, id를 get 방식으로 전송해서, 서버측에 받을 수 있음. 
				// id를 가져오는 구조는, 삭제에서 복붙. 재사용.
//				String id=request.getParameter("id");
				
				
				String viewName = getViewName(request);
				System.out.println("viewName(수정폼)이 뭐야? : " + viewName);
				ModelAndView mav = new ModelAndView();
				
				// mav 에 데이터를 넣는 구조, 회원가입에서 복붙. 재사용.
				// 결과 뷰에, 아이디만 전달함. 
				// 만약, 이 아이디에 관련된 모든 정보를 결과 뷰에 재사용할려면
				// 이 아이디로 하나의 회원의 정보를 디비에서 가져고 와서, 
				// 이 하나의 회원의 정보를 결과 뷰에 넣으면됨. 
				mav.addObject("user_id", id);
				
				// 추가, 해당 아이디로, 정보를 가져오기. 
				// 조회된 한 회원의 정보를 담을 임시 인스턴스 : memberOne
				// getOneMember : 서비스에 아직 없는 메서드 임. 임의로 추가. 
				// 외주, 서비스로 동네2번 가기. 인터페이스도 추상메서드 추가. 
				// 구현한 클래스에도 재정의 하기. 
				MemberVO memberOne = memberService.getOneMember(id);
				
				// 디비에서 , 회원 정보를 가져왔으면 뷰에 데이터 전달하기. 
				mav.addObject("member", memberOne);
				
				// 결과 뷰로 가게끔, 설정. 
				mav.setViewName(viewName);
				return mav;
	}

	@Override
	@RequestMapping(value = "/member/updateMember.do", method =  RequestMethod.POST)
	public ModelAndView updateMember(@ModelAttribute("memberVO") MemberVO memberVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
//		MemberVO memberVO = new MemberVO();
//		bind(request, memberVO);
		int result = 0;
		// 실제 업데이트를 반영하는 로직, 외주주기. 동네 2번 보내기 
		// 이름 : updateMember
		result = memberService.updateMember(memberVO);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}



}
