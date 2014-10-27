<%-- 
/**
 * bill-Encoder
 * @author Shawn Chen
 * Jrsys International Corp.
 */
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>電子帳單加密</title>
            <!-- Bootstrap CSS -->
            <script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.0.min.js" />"></script>
		    <!--<script type='text/javascript' src="<c:url value="/js/bootstrap.min.js" />"></script>-->
		    <script type='text/javascript' src='webjars/bootstrap/3.1.0/js/bootstrap.min.js'></script>
		    <link rel='stylesheet' href='webjars/bootstrap/3.1.0/css/bootstrap.min.css'>
		    <!--<link rel='stylesheet' href="<c:url value="/css/bootstrap.min.css" />">-->
		    <link rel='stylesheet' href="<c:url value="/resources/css/signin.css" />">	    
</head>
<body>
			<form:form enctype="multipart/form-data" modelAttribute="uploadedFile" class="form-signin" method="POST" action="encodePDF" target="_blank">
			<div class="row">
				<div class="col-md-6"><h3>Jrsys 捷而思電子帳單加密</h3></div>
			</div>
			<div class="row">&nbsp;</div>
			<div class="row">
				<div class="col-md-3"><form:label path="pdf"><b>請選擇您要加密的電子帳單PDF檔</b></form:label></div>
				<div class="col-md-3"><form:input path="pdf" type="file"></form:input></div>
			</div>
			<div class="row">&nbsp;</div>
			<div class="row">
				<div class="col-md-3"><form:label path="certificate"><b>請選擇您要用來加密的憑證檔:</b></form:label></div>
				<div class="col-md-3"><form:input path="certificate" type="file" ></form:input></div>
			</div>
			<div class="row">&nbsp;</div>
			<div class="row">
				<div class="col-md-3"><form:label path="password"><b>或是不用憑證, 輸入密碼進行加密:</b></form:label></div>
				<div class="col-md-3"><form:input path="password" type="password" ></form:input></div>
			</div>
			<div class="row">
				<div class="col-md-3"><input type="submit" class="btn btn-warning btn-margin" value="加密!"/></div>
			</div>
			<div class="row">&nbsp;</div>
			<div class="row">
				<div class="col-md-6">僅作測試用途, 為操作順利, 請開放瀏覽器的“彈出視窗”設定, 以避免受到阻擋.</div>
			</div>
			</form:form>
</body>
</html>