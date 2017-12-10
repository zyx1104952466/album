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
		<title>用户信息修改</title>
	</head>
	<jsp:include page="top.jsp"></jsp:include>
	<s2:set name="user" value="#session.user" />
	<DIV style="padding-top: 10px; padding-left: 37%;">
		<DIV class=C_9>
			修改用户信息
		</DIV>
	</DIV>
	<DIV style="padding-top: 10px; padding-left: 40%;">

		<form onsubmit="return checkFormByName('UserForm');" method="post"
			action="ChangeUserinfo.action" name="UserForm">
			<table border="0">
				<tr>
					<td align="right">
						用户名：
					</td>
					<td align="left">
						<input value=<s2:property value="#user.id" /> name="id"
							type=hidden>
						<input value=<s2:property value="#user.username" />
							name="username" type="text" title="用户名" disabled>
					</td>
				</tr>
				<tr>
					<td align="right">
						昵称：
					</td>
					<td colspan="2" align="left">
						<input value=<s2:property value="#user.nickName" />
							name="nickName" type="text" style="width: 150px" title="昵称">
					</td>
				</tr>
				<tr>
					<td align="right">
						原始密码：
					</td>
					<td colspan="2" align="left">
						<input name="oldPassword" type="password" style="width: 150px"
							title="原始密码">
					</td>
				</tr>
				<tr>
					<td align="right">
						新的密码：
					</td>
					<td colspan="2" align="left">
						<input name="newPassword" type="password" style="width: 150px"
							title="新的密码">
					</td>
				</tr>
				<tr>
					<td align="right">
						邮箱地址：
					</td>
					<td colspan="2" align="left">
						<input value=<s2:property value="#user.email" /> name="email"
							type="text" style="width: 150px" title="邮箱地址">
					</td>
				</tr>
				<tr>
					<td colspan="3" align="center" style="padding-left: 65px;">
						<input class="button" type="submit" value="修改" />
						<input class="button" type="reset" value="重填" />
					</td>
				</tr>
			</table>
		</form>

		<script></script>

	</DIV>

	<jsp:include page="foot.jsp"></jsp:include>
	</body>
</html>