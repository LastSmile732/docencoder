<%-- 
/**
 * PDF-Encoder
 * @author Shawn Chen
 * Jrsys International Corp.
 */
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PDF Encryption</title>
        	
            <!-- Bootstrap CSS -->
            <script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.0.min.js" />"></script>
		    <!--<script type='text/javascript' src="<c:url value="/js/bootstrap.min.js" />"></script>-->
		    <script type='text/javascript' src='webjars/bootstrap/3.1.0/js/bootstrap.min.js'></script>
		    <link rel='stylesheet' href='webjars/bootstrap/3.1.0/css/bootstrap.min.css'>
		    <!--<link rel='stylesheet' href="<c:url value="/css/bootstrap.min.css" />">-->
		    <link rel='stylesheet' href="<c:url value="/resources/css/signin.css" />">

    </head>
    <body>
			<form enctype="multipart/form-data" class="form-signin" action="encodePDF" method="POST" target="_blank" onsubmit="return submitTest();">
			<div class="row">
				<div class="col-md-4"><h3>Jrsys PDF Encryption</h3></div>
			</div>
			<div class="row">&nbsp;</div>
			<div class="row">
				<div class="col-md-4"><b>Please select a PDF file:</b></div>
				<div class="col-md-4"><input name="pdf" id="pdf" type="file" size="50" /></div>
			</div>
			<div class="row">&nbsp;</div>
			<div class="row">
				<div class="col-md-4"><b>Please select a certificate file:</b></div>
				<div class="col-md-4"><input name= "certificate" id="certificate" type="file" size="50" /></div>
			</div>
			<div class="row">&nbsp;</div>
			<div class="row">
				<div class="col-md-4"><b>or enter a password without a certificate:</b></div>
				<div class="col-md-4"><input name="password" id="password" type="password" /></div>
			</div>
			<div class="row">
				<div class="col-md-4"><input type="submit" class="btn btn-warning btn-margin" value="Encrypt!"/></div>
			</div>
			<div class="row">&nbsp;</div>
			<div class="row">
				<div class="col-md-8">For testing purpose only, to avoid browser Pop-up blockers, please enable Pop-up window settings.</div>
			</div>
			</form>

    </body>
</html>
