<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"  />
<!DOCTYPE html>
<html>
<head>
<title>JSONTest_lhs0918</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>  
<script>
  $(function() {
      $("#checkJson").click(function() {
      	var article = {id:"gustjr", 
	               pwd:"1234",
	               name:"이현석", 
	               email:"gustjr5059"
	              };
  
  	$.ajax({
   	     type:"POST",
        url:"${contextPath}/members", 
/*          type:"PUT",
        url:"${contextPath}/members/1234", */  
/*          type:"DELETE",
        url:"${contextPath}/members/1234",  
        contentType: "application/json", */
        data :JSON.stringify(article),
      success:function (data,textStatus){
          alert(data);
      },
      error:function(data,textStatus){
        alert("에러가 발생했습니다.");ㅣ
      },
      complete:function(data,textStatus){
      }
   });  //end ajax	

   });
});
</script>
</head>
<body>
  <input type="button" id="checkJson" value="새글 쓰기"/><br><br>
  <div id="output"></div>
</body>
</html>