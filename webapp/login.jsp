<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s2" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<LINK rel=stylesheet type=text/css href="css/photo.css">
		<script type="text/javascript" src="js/util.js"></script>
		<script type="text/javascript" src="js/ajax.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>用户登陆</title>
	</head>
	<jsp:include page="top.jsp"></jsp:include>


	<DIV style="padding-top: 10px; padding-left: 37%;">
		<DIV class=C_9>
			用户登陆
		</DIV>
	</DIV>
	<DIV style="padding-top: 10px; padding-left: 40%;">


		<s2:form onsubmit="return checkFormByName('loginForm');" method="post"
			action="login" name="loginForm">
			<table>
				<tr>
					<td align="left">
						<s2:textfield name="username" label="用户名" title="用户名" cssStyle="width:150px"/>
					</td>
				</tr>
				<tr>
					<td align="left">
						<s2:password name="password" label="密码" title="密码" cssStyle="width:150px"/>
					</td>
				</tr>
				<tr>
						<td colspan="2" align="center" style="padding-left:30%;">
							<input class="button" type="submit" value="登录" />
							<input class="button" type="button" value="注册"  onclick="javascript:window.open('register.jsp','_parent','')"/>
						</td>
				</tr>
			</table>
		</s2:form>
	</DIV>

	<jsp:include page="foot.jsp"></jsp:include>
	</body>
</html>