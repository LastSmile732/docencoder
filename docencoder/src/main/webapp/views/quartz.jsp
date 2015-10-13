<%-- 
/**
 * PDF-Encoder
 * @author Shawn Chen
 * HiTrust.
 */
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Quartz Test</title>
            <!-- Bootstrap CSS -->
            <script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.0.min.js" />"></script>
		    <!--<script type='text/javascript' src="<c:url value="/js/bootstrap.min.js" />"></script>-->
		    <script type='text/javascript' src='webjars/bootstrap/3.1.0/js/bootstrap.min.js'></script>
		    <link rel='stylesheet' href='webjars/bootstrap/3.1.0/css/bootstrap.min.css'>
		    <link rel='stylesheet' href="<c:url value="/resources/css/signin.css" />">
		     <style type="text/css">
			 .header{width:100%;
			 		height:50px;
			 		top:0px;
			 		position:fixed;
			 		z-index:2;
			 		background:white}
			 .main{width:100%;
/* 			 		height:1200px; */
			 		margin-top:50px;}
			 .main p{width:99%;
			 		height:300px;}
			 .footer{position:fixed;
			 		left:0px;
			 		bottom:0px;
			 		height:50px;
			 		width:100%;
			 		background:white}
			 </style>
</head>
<body>
	<div class="header">
		<div>
			<p>
				THIS IS THE HEADER
			</p>
		</div>
	</div>
	<div class="main">		
			<form class="quartzTest" action="quartzA" method="POST" target="_self" >
				<div class="row">
					<div class="col-md-4"><h3>Shawn's Quartz Test</h3></div>
				</div>
				<div class="row">
					<div class="col-md-4"><input type="submit" class="btn btn-primary btn-margin" value="JobA TEST!"/></div>
				</div>
				<div class="row">&nbsp;</div>
			</form>
			<form class="quartzRed" action="redquartz" method="POST" target="_self" >
				<div class="row">
					<div class="col-md-4"><input type="submit" class="btn btn-info btn-margin" value="JobB TEST!"/></div>
				</div>
				<div class="row">&nbsp;</div>
			</form>
			
			<form class="quartzBlue" action="bluequartz" method="POST" target="_self" >
				<div class="row">
					<div class="col-md-4"><input type="submit" class="btn btn-warning btn-margin" value="Standby!"/></div>
				</div>
				<div class="row">&nbsp;</div>
			</form>
			<form class="quartzMain" action="quartzMain" method="POST" target="_self" >
				<div class="row">
					<div class="col-md-4"><input type="submit" class="btn btn-danger btn-margin" value="Start/Stop!"/></div>
				</div>
				<div class="row">&nbsp;</div>
			</form>
			
			<form class="quartzCheck" action="checkquartz" method="POST" target="_self" >
				<div class="row">
					<div class="col-md-4">Job Name: <input type="text" name="jobname" id="jobname" placeholder="JobA"/></div>
				</div>
				<div class="row">
					<div class="col-md-4">Group Name: <input type="text" name="groupname" id="groupname" placeholder="FTP"/></div>
				</div>
				<div class="row">
					<div class="col-md-4"><input type="submit" class="btn btn-success btn-margin" value="Restart/Pause!"/></div>
				</div>
				<div class="row">&nbsp;</div>
			</form>
		</div>
		<div class="footer">
		<div>
			<p>
				THIS IS THE FOOTER
			</p>
		</div>
		</div>
</body>
</html>