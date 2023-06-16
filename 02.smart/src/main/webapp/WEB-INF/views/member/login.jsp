<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="row justify-content-center h-100 align-items-center">
     <div class="col-md-9 col-lg-7 col-xl-5">
         <div class="card shadow-lg border-0 rounded-lg px-3 py-5">
             <h3 class="text-center"><a href="<c:url value='/'/>">
             	<img src="<c:url value='/img/hanul.logo.png'/>" /></a></h3>
             <div class="card-body">
                 <form method="post" action="smartLogin">
                     <div class="form-floating mb-3">
                         <input class="form-control" type="text" name="userid" required placeholder="아이디">
                         <label>아이디</label>
                     </div>
                     <div class="form-floating mb-3">
                         <input class="form-control" name="userpw" type="password" required placeholder="비밀번호">
                         <label>비밀번호</label>
                     </div>
                     <button class="btn btn-primary form-control py-2">로그인</button>
                    
                     <hr>
                     <div class="d-flex align-items-center justify-content-between mt-4 mb-0">
                         <a class="small" href="index.html">회원가입</a>
                         <a class="small" href="findPassword">비밀번호 찾기</a>
                     </div>
                 </form>
             </div>
             <div class="card-footer text-center py-3">
                 <div class="small"><a href="register.html">Need an account? Sign up!</a></div>
             </div>
         </div>
     </div>
 </div>
 <jsp:include page="/WEB-INF/views/include/modal_alert.jsp"/>
 
 <script>
 $(function(){
	 if( ${not empty fail}){//로그인 실패인 경우
		modalAlert("warning", "로그인 실패", "아이디나 비밀번호가 일치하지 않습니다.!");
	 	new bootstrap.Modal($('#modal-alert')).show();
	 }
 })
 
 $('#modal-alert .btn-ok').click(function(){
	 $('[name=userid]').focus();
 })
 </script>
</body>
</html>