package com.myspring.pro28.ex01;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class FileUploadController  {
	// 임시 이미지 파일(물리저장소)
	private static final String CURR_IMAGE_REPO_PATH = "c:\\spring\\image_repo";
	// 파일 이미지를 업로드 하기위한 창
	@RequestMapping(value="/form")
	public String form() {
	    return "uploadForm";
	  }
	// 파일 이미지를, 저장소에서 upload 처리 로직
	@RequestMapping(value="/upload",method = RequestMethod.POST)
	// 일반 데이터 + 파일 데이터를 같이 처리하는 MultipartHttpServletRequest 
	public ModelAndView upload(MultipartHttpServletRequest multipartRequest,HttpServletResponse response)
	  throws Exception{
		// 이제, 폼 창에서, 사용자가 입력한 일반데이터 2개와, 이미지 데이터 여러 개를 처리 준비
		// 인코딩 utf-8
		multipartRequest.setCharacterEncoding("utf-8");
		// 일반 데이터와 파일 이미지 데이터를 담을 임시 컬랙션
		Map map = new HashMap();
		// 
		Enumeration enu=multipartRequest.getParameterNames();
		while(enu.hasMoreElements()){
			String name=(String)enu.nextElement();
			String value=multipartRequest.getParameter(name);
			//System.out.println(name+", "+value);
			map.put(name,value);
		}
		
		List fileList= fileProcess(multipartRequest);
		map.put("fileList", fileList);
		ModelAndView mav = new ModelAndView();
		mav.addObject("map", map);
		mav.setViewName("result");
		return mav;
	}
	
	private List<String> fileProcess(MultipartHttpServletRequest multipartRequest) throws Exception{
		List<String> fileList= new ArrayList<String>();
		Iterator<String> fileNames = multipartRequest.getFileNames();
		while(fileNames.hasNext()){
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			String originalFileName=mFile.getOriginalFilename();
			fileList.add(originalFileName);
			File file = new File(CURR_IMAGE_REPO_PATH +"\\"+ fileName);
			if(mFile.getSize()!=0){ //File Null Check
				if(! file.exists()){ //��λ� ������ �������� ���� ���
					if(file.getParentFile().mkdirs()){ //��ο� �ش��ϴ� ���丮���� ����
						file.createNewFile(); //���� ���� ����
					}
				}
				mFile.transferTo(new File(CURR_IMAGE_REPO_PATH +"\\"+ originalFileName)); //�ӽ÷� ����� multipartFile�� ���� ���Ϸ� ����
			}
		}
		return fileList;
	}
}
