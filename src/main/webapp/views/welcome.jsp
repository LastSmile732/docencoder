<%-- 
    Document   : welcome
    Created on : 2013/3/5, 下午 04:04:57
    Author     : yubby
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script type="text/javascript">
</script>


        <h1>歡迎使用Jrsys ${name}</h1>
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
