<%-- 
    Document   : Error result
    Created on : 2014/05
    Author     : Shawn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PDF Encryption Error!</title>
    </head>
    <body>
		<h3>ERROR! Please check console logs!</h3>
		<div>PDF File Name: ${pdfName}</div>
		<div>certificate File Name:<c:out value="${certName}"/></div>

		<div>Exception name: <c:out value="${exception}"/></div>

    </body>
</html>



