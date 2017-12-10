<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s2" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<LINK rel=stylesheet type=text/css href="css/images/photo.css">
		<script type="text/javascript" src="js/util.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>修改用户头像</title>
	</head>

	<jsp:include page="top.jsp"></jsp:include>
	<DIV style="padding-top: 10px; padding-left: 37%;">
		<DIV class=C_9>
					修改用户头像
				</DIV>
	</DIV>
	<DIV style="padding-top: 10px; padding-left: 40%;">

		<s2:form name="changeFace" method="post" action="changeMypic"
			enctype="multipart/form-data">
			<table border="0" cellpadding="0" cellspacing="0" width="200px"
				height="200px">
				<tr>
					<td align="center" valign="middle" colspan=3>
					<img src="<s2:property value='#session.user.url' />" border="1" />
					</td>
				</tr>
				<tr>
					<td colspan=3>
						请上传小于2M的<font color='red'>jpg</font>或<font color='red'>gif</font>图片
					</td>
				</tr>
				<tr>
					<td colspan=3>
						 <s2:file name="mypic" onchange="CheckPic(this.value);" />
					</td>
				</tr>
				<tr>
					<td colspan=3>
						<s2:submit name="submit" value="修改"></s2:submit>
					</td>
				</tr>
			</table>
		</s2:form>
<script language="javascript">
	document.getElementsByName("submit")[0].disabled = true;
</script>
	</DIV>
	<jsp:include page="foot.jsp"></jsp:include>
	</body>
</html>





