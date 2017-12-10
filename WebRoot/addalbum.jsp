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
		<title>新建相册</title>
	</head>
	<jsp:include page="top.jsp"></jsp:include>
	<s2:set name="user" value="#session.user" />
	<DIV style="padding-top: 10px; padding-left: 37%;">
		<DIV class=C_9>
			创建新的相册
		</DIV>
	</DIV>
	<DIV style="padding-top: 10px; padding-left: 40%;">

		<form onsubmit="return checkFormByName('AlbumForm');" method="post"
			action="addAlbum.action" name="AlbumForm">
			<table border="0">
				<tr>
					<td align="right">
						相册名称：
					</td>
					<td align="left" colspan="2">
						<input value=<s2:property value="#user.id" /> name="userId"
							type=hidden>
						<input name="name" type="text" title="相册名称">
					</td>
				</tr>
				<tr>
					<td align="right">
						相册介绍：
					</td>
					<td colspan="2" align="left">
						<textarea name="desc" rows="3" title="相册介绍">暂无介绍</textarea>
					</td>
				</tr>
				<tr>
					<td>
						相册浏览权限：
					</td>
					<td align="left">
						公开浏览<input type="radio" name="status" value="1" checked="checked" />
					</td>
					<td align="left">
						私有相册<input type="radio" name="status" value="0" />
					</td>
				</tr>
				<tr>
					<td colspan=3>
						<input type="hidden" name="aStatus">
					</td>
				</tr>
				<tr>
					<td colspan="3" align="center" style="padding-left: 90px;">
						<input id="submit" onclick="getSelectedValue('AlbumForm');" type="submit"
									value="创建" />
								<input id="reset" type="reset" value="重置" />
					</td>
				</tr>
			</table>
		</form>

		<script></script>

	</DIV>

	<jsp:include page="foot.jsp"></jsp:include>
	</body>
</html>