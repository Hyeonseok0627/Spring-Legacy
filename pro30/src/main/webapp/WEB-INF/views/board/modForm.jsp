<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<%-- 다중 이미지의 상세보기시 사용할 예정.--%> 
<!-- 일반 데이터가 담겨있음 -->
<c:set var="article"  value="${articleMap.article}"  />
<!-- 파일 이미지 이름 목록이 담겨있음 -->
<c:set var="imageFileList"  value="${articleMap.imageFileList}"  />

 
<%
  request.setCharacterEncoding("UTF-8");
%> 

<head>
   <meta charset="UTF-8">
   <title>수정폼 페이지</title>
   <style>
     #tr_file_upload{
       display:none;
     }
     #tr_btn_modify{
       display:none;
     }
     #preview{
      width: 30%
     }
   
   </style>
   <script  src="http://code.jquery.com/jquery-latest.min.js"></script> 
   <script type="text/javascript" >
   
   /* cnt , 파일 추가시 , 입력태그 구분 짓는 변수 */
   var cnt=1;
   /* cnt1 , 이미지 불러오는 태그를 구분 짓는 변수 */
   var cnt1=0; 
   /* cnt2 img 태그를 구분짓는 변수 */
   var cnt2=0;
   
   /* 목록보기 함수 */
     function backToList(obj){
	    obj.action="${contextPath}/board/listArticles.do";
	    obj.submit();
     }
 
	
	 
   /* 이미지를 뷰에 출력하기 위한 함수 */
	/* 글쓰기 부분 재사용 예정 */
	
 </script>
</head>
<body>
<!-- action 부분은 컨트롤러에서 추가할 예정, 처리 부분,
사실은 폼 부분도 필요함. -->
  <form name="frmArticle" method="post"  action="${contextPath}"  enctype="multipart/form-data">
  <table  border=0  align="center">
  <tr>
   <td width=150 align="center" bgcolor=#FF9933>
      글번호
   </td>
   <td >
   <!-- disabled 속성 설정은, 서버에 데이터를 전달 못함 -->
    <input type="text"  value="${article.articleNO }"  readOnly />
    <input type="hidden" name="articleNO" value="${article.articleNO}"  />
   </td>
  </tr>
  <tr>
    <td width="150" align="center" bgcolor="#FF9933">
      작성자 아이디
   </td>
   <td >
    <input type=text value="${article.id }" name="writer"  readOnly />
   </td>
  </tr>
  <tr>
    <td width="150" align="center" bgcolor="#FF9933">
      제목 
   </td>
   <td>
    <input type=text value="${article.title }"  name="title"  id="i_title"  />
   </td>   
  </tr>
  <tr>
    <td width="150" align="center" bgcolor="#FF9933">
      내용
   </td>
   <td>
    <textarea rows="20" cols="60"  name="content"  id="i_content"   />${article.content }</textarea>
   </td>  
  </tr>
 
 	  
  <tr>
	   <td width="150" align="center" bgcolor="#FF9933">
	      등록일자
	   </td>
	   <td>
	    <input type=text value="<fmt:formatDate value="${article.writeDate}" />" readOnly />
	   </td>   
  </tr>
  <tr>
	   	   <td width="150" align="center" bgcolor="#FF9933">
	      <input type=button value="수정반영하기"   onClick="fn_modify_article(frmArticle)"  >
	   </td>
	   <td>
	       
	       <td width="150" align="center" bgcolor="#FF9933">
           <input type=button value="취소"  onClick="backToList(frmArticle)">
	   </td>   
  </tr>
  
    

 </table>
 </form>
</body>
</html>