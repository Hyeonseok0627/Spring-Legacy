<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    isELIgnored="false"  %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<%
  request.setCharacterEncoding("UTF-8");
%>    


<html>
<head>
<meta charset=UTF-8">
<title>결과창</title>
</head>
<body>
<!-- 컨트롤러에서 테스트 겸, 키이름 변경해서, 수정하기. -->
<%-- <h1>아이디 : ${userID2 }</h1>
<h1>이름   : ${userName2 }</h1>  --%>
<%-- <h1>아이디 : ${userID }</h1>
<h1>이름   : ${userName }</h1> --%>
<h1>아이디 : ${info.userID }</h1>
<h1>이름   : ${info.userName }</h1>
<h1>이메일   : ${info.email }</h1>
</body>
</html>
