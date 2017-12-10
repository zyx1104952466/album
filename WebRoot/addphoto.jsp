<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s2" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<LINK rel=stylesheet type=text/css href="css/images/photo.css">
		<script type="text/javascript" src="js/util.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>相片上传</title>
	</head>

	<jsp:include page="top.jsp"></jsp:include>
	<s2:set name="album" value="#request.album" />
	<DIV style="padding-top: 10px; padding-left: 37%;">
		<DIV class=C_9>
			给您的相册：
			<s2:property value="#album.name" />
			上传新的相片
		</DIV>
	</DIV>
	<DIV style="padding-top: 10px; padding-left: 40%;">

		<s2:form method="post" action="uploadPhoto.action"
			enctype="multipart/form-data" name="uploadPhoto">
			<table border="0" cellpadding="0" cellspacing="0" width="200px"
				height="200px">
				<tr>
					<td colspan=2>
						请上传小于2M的
						<font color='red'>jpg</font>或
						<font color='red'>gif</font>图片
					</td>
				</tr>
				<tr>
					<td colspan=2>
						<s2:file name="upload" label="Img_1 " onchange="CheckPic(this.value);"></s2:file>
					</td>
				</tr>
				<tr>
					<td colspan=2>
						<s2:file name="upload" label="Img_2 " onchange="CheckPic(this.value);"></s2:file>
					</td>
				</tr>
				<tr>
					<td colspan=2>
						<s2:file name="upload" label="Img_3 " onchange="CheckPic(this.value);"></s2:file>
					</td>
				</tr>
				
				<tr>
					<td align="left"> 
						<input name="submit" type="submit" value="开始上传" />
					</td>
					<td align="right">
						<input width=50 name="reset"  type="reset" value="清空选框" /> 
					</td>
				</tr>
			</table>
		</s2:form>
	</DIV>
	
<script language="javascript">
	document.getElementsByName("submit")[0].disabled = true;
</script>

	<jsp:include page="foot.jsp"></jsp:include>
	</body>
</html>





