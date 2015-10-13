<%-- 

--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

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
			<script type="text/javascript"></script>		
    </head>
    <body>

        <h1>歡迎~ ${name}</h1>
        <!-- Button trigger modal -->
			<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
			  Launch demo modal
			</button>
			<input id="text" type="text" />
			<input onclick="signDigest(document.getElementById('text').value);" type="button" value="Sign" />

		<!-- Modal -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		        <h4 class="modal-title" id="myModalLabel">Modal title</h4>
		      </div>
		      <div class="modal-body">
		        ...
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		        <button type="button" class="btn btn-primary">Save changes</button>
		      </div>
		    </div>
		  </div>
		</div>
    </body>
</html>