<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	
<script type="text/javascript">
	function executeError(){
		var errorForm=document.forms["errorForm"];
		var errorMessage=errorForm.errorMessage.value;
		var returnUrl=errorForm.returnUrl.value;
		alert(errorMessage);
		window.open(returnUrl,"_parent","");
	}
</script>

		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>中转页</title>
	</head>
	<body>

		<form name="errorForm">
			<input type="hidden" value="${requestScope.errorMessage }"
				name="errorMessage">
			<input type="hidden" value="${requestScope.returnUrl}"
				name="returnUrl">
		</form>

		<script type="text/javascript">executeError()</script>
		<a target=""></a>
	</body>
</html>