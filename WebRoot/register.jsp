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
		<title>用户注册</title>
	</head>
	<jsp:include page="top.jsp"></jsp:include>


<DIV style="padding-top: 10px; padding-left: 37%;">
		<DIV class=C_9>
			欢迎新用户注册
		</DIV>
	</DIV>

		<DIV style="padding-top: 10px; padding-left:40%;">


			<form onsubmit="return checkFormByName('addUserForm');" method="post"
				action="register.action" name="addUserForm">
				<table border="0">
					<tr>
						<td align="right">
							验证码：
						</td>
						<td align="left">
							<input size="6" type="text" name="VerifyCode" title="验证码">
							<span><img width="80" height="20" src="VerifyCode.action"
									onclick="this.src='VerifyCode.action'" alt="如果看不清楚,请点击图片刷新">
							</span>
						</td>
					</tr>
					<tr>
						<td align="right">
							用户名：
						</td>
						<td align="left">
							<input name="username" type="text" title="用户名" style="width: 150px"
								onBlur="sendFieldToServer('addUserForm','username','IsUserExist.action')">
							<span id="usercheck"></span>
						</td>
					</tr>
					<tr>
						<td align="right">
							昵称：
						</td>
						<td colspan="2" align="left">
							<input name="nickName" type="text" style="width: 150px"
								title="昵称">
						</td>
					</tr>
					<tr>
						<td align="right">
							密码：
						</td>
						<td colspan="2" align="left">
							<input name="password" type="password" style="width: 150px"
								title="密码">
						</td>
					</tr>
					<tr>
						<td align="right">
							密码确认：
						</td>
						<td colspan="2" align="left">
							<input name="rePassword" type="password" style="width: 150px"
								title="确认密码">
						</td>
					</tr>
					<tr>
						<td align="right">
							邮箱地址：
						</td>
						<td colspan="2" align="left">
							<input name="email" type="text" style="width: 150px" title="邮箱地址">
						</td>
					</tr>



					<tr>
						<td colspan="3" align="left" style="padding-left:65px;">
							<input class="button" type="submit" value="注册" />
							<input class="button" type="reset" value="重填" />
						</td>
					</tr>
				</table>
			</form>

			<script ></script>

		</DIV>

		<jsp:include page="foot.jsp"></jsp:include>
	</body>
</html>