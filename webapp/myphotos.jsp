<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s2" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<LINK rel=stylesheet type=text/css href="css/images/photo.css">
		<script type="text/javascript" src="js/util.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>我的相片列表</title>

		<style type="text/css">
#bodyL {
	float: left;
	width: 84px;
	margin-right: 2px;
}

a.od {
	width: 80px;
	height: 25px;
	line-height: 25px;
	text-align: center;
	font-weight: bold;
	border: 2px solid #849BCA;
	display: block;
	color: #547BC9;
	float: left;
	text-decoration: none;
	margin-top: 2px;
}

a.od:link {
	background: #EEF1F8;
}

a.od:visited {
	background: #EEF1F8;
}

a.od:hover {
	background: #EEE;
}

a.od:active {
	background: #EEE;
}

#fd {
	width: 300px;
	height: 200px;
	background: #EDF1F8;
	border: 25px solid #849BCA;
	margin-top: 2px;
	margin-left: 2px;
	float: left;
	overflow: hidden;
	position: absolute;
	left: 35%;
	top: 35%;
	cursor: move;
	float: left;
	filter: alpha(opacity = 50);
}

.content {
	padding: 10px;
	text-align: center;
}
</style>


<script language="JavaScript"> 
	function DrawImage(thisp,w,h){
		var a=new Image();   
		a.src=thisp.src; 
		if (a.width<w && a.height<h) {
			thisp.width=a.width;/*使用原始图片大小*/ 
			thisp.height=a.height;   
		}else{
			if(a.width/a.height>w/h){
	  			 thisp.height=w*a.height/a.width; /*不对图片拉伸*/ 
  				 thisp.width=w;
			}else{
   					thisp.width=h*a.width/a.height; 
   					thisp.height=h;
				 }
			}
		}
</script>


	</head>

	<jsp:include page="top.jsp"></jsp:include>

	<DIV class=C_2>
		<!--左侧导航开始-->
		<DIV class=C_3>
			<DIV class=C_4>
				最新评论top10
			</DIV>
			<s2:set name="topComment" value="#request.topComment" />
			<s2:iterator status="offset" value="topComment">
				<DIV class=C_5>
					<SPAN
						style="FLOAT: left; width: 110px; height: 30px; overflow: hidden; text-overflow: ellipsis;">
						<A style="COLOR: #0376c2"
						href="photoView.action?photoId=<s2:property value='photoId' />"><s2:property
								value="content" /> </A> </SPAN>
				</DIV>
			</s2:iterator>
		</DIV>
		<!--左侧导航结束-->
		<div class="C_9">
			我的相片
		</div>

		<DIV class=C_8>
			<DIV class=C_13>

				<div id="fd"
					style="display: none; filter: alpha(opacity = 100); opacity: 1;">
					<center>
						<font style="padding-top:15px; font-size: 20px; color: #849BCA;">
							相片信息修改 </font>
					</center>
					<div class="content">
						<form name="updatePhoto" action="updatePhoto.action" method="post"
							onsubmit="return checkFormByName('updatePhoto');">
							<input id="fd_photoId" name="photoId" type="hidden" value="0" />
							<input id="fd_albumId" name="albumId" type="hidden" value="0" />
							<table style="padding-top: 10px;">
								<tr>
									<td>
										相片名称：
									</td>
									<td>
										<input type="text" name="name" value="name of picture"
											title="相片名称" />
									</td>
								</tr>
								<tr>
									<td>
										相片描述：
									</td>
									<td>
										<textarea name="descr" title="相片描述">descrption of picture</textarea></td>
								</tr>
								<tr height="20px"></tr>
								<tr>
									<td colspan="2" align="center" style="padding-left: 30%">
										<input type="submit" value="提交修改" />
										<input type="button" value="取消修改" onclick="closeed('fd');" />
									</td>
								</tr>

							</table>
						</form>
					</div>
				</div>

				<s2:set name="myPhotos" value="#request.myPhotos" />
				<s2:if test="#myPhotos != null">
					<s2:iterator status="offset" value="myPhotos">

						<s2:if test="#offset.index==0 || #offset.index==4">
							<div class=" Bl_14 ">
						</s2:if>
						<s2:else>
							<div class=" Bl_14 ml68">
						</s2:else>

						
						<div class="Bl_7">
						<table border="0" cellpadding="0" cellspacing="0" class="DBl_7">
							<tr>
								<td align="center" valign="middle" height="115">
									<a href="photoView.action?photoId=<s2:property value='id' />">
									 <img onload="DrawImage(this,120,120);" width="0" height="0"
											title="名字：<s2:property value="name"/>   介绍：<s2:property value="descr"/>"
											src="<s2:property value='url'/>" border="0" /> </a>
								</td>
							</tr>
						</table>
					</div>

						<div class="Bl_8">
							<SPAN
								style="FLOAT: left; width: 100px; height: 20px; overflow: hidden; text-overflow: ellipsis;">
								<s2:property value='name' /> <img title="编辑相片信息"
									src="images/a14.gif" onmousemove="this.src='images/a16.gif'"
									onmouseout="this.src='images/a14.gif'" alt="编辑相片信息"
									onclick="javascript:show('fd',<s2:property value='id'/>,<s2:property value='albumId'/>,'<s2:property value='name'/>','<s2:property value='descr'/>');return false;" /> </SPAN>



							<a onclick="return(confirm('确定删除?'))"
								href="deletePhoto.action?photoId=<s2:property value='id'/>&albumId=<s2:property value='albumId'/>"><img
									title="删除相片" src="images/a15.gif"
									onmousemove="this.src='images/a17.gif'"
									onmouseout="this.src='images/a15.gif'" alt="删除相片" /> </a>
							<br>
							<SPAN
								style="FLOAT: left; width: 120px; height: 20px; overflow: hidden; text-overflow: ellipsis;">
								<a onclick="return(confirm('确定要将此相片设置为封面吗?'))"
								href="setAlbumFace.action?photoId=<s2:property value='id'/>&albumId=<s2:property value='albumId'/>">将此相片设为相册封面</a>
							</SPAN>
						</div>
			</div>
			</s2:iterator>
		</DIV>
		<br>
		<TABLE border=0 cellSpacing=0 cellPadding=0 height=50>
			<TBODY>
				<TR>
					<TD>
						<!-- 分页代码 start -->
						<s2:set name="pageInfo" value="#request.pageInfo" />
						<div>
							<form action="showPhotos.action" name="pageForm" method="post">
								<dl>
									<dd style="text-align: right; font-size: 12px; color: #666633;">
										一共有
										<font color=red><s2:property value="#pageInfo.allNum" />
										</font> 条记录，共
										<font color=red><s2:property value="#pageInfo.pageNum" />
										</font> 页，每页
										<font color=red><s2:property value="#pageInfo.num" />
										</font> 条记录，当前为第
										<font color=red><s2:property
												value="#pageInfo.currentPage+1" /> </font> 页，此页有
										<font color=red><s2:property value="#pageInfo.itemNum" />
										</font> 条记录

										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<!-- First Page -->

										<s2:if test="#pageInfo.currentPage>0">
											<a
												href="showPhotos.action?albumId=<s2:property value="albumId"/>"
												class="nor">首页</a>&nbsp;&nbsp;&nbsp;
									</s2:if>
										<s2:else>
											<span class="nor">首页</span>&nbsp;&nbsp;&nbsp;
									</s2:else>
										<!-- Prep Page -->

										<s2:if test="#pageInfo.currentPage>0">
											<a
												href="showPhotos.action?albumId=<s2:property value="albumId"/>&&currentPage=<s2:property value='#pageInfo.currentPage-1'/>"
												class="nor">上一页</a>&nbsp;
												</s2:if>
										<s2:else>
											<span class="nor">上一页</span>&nbsp;&nbsp;&nbsp;
												</s2:else>

										<!-- Next Page -->
										<s2:if test="#pageInfo.currentPage+1>=#pageInfo.pageNum">
											<span class=nor>下一页</span>&nbsp;&nbsp;&nbsp;
												</s2:if>
										<s2:else>
											<a
												href="showPhotos.action?albumId=<s2:property value="albumId"/>&&currentPage=<s2:property value='#pageInfo.currentPage+1' />"
												class="nor">下一页</a>&nbsp;&nbsp;&nbsp;
											</s2:else>
										<!-- Last Page -->

										<s2:if test="#pageInfo.currentPage+1>=#pageInfo.pageNum">
											<span class=nor>末页</span>
										</s2:if>
										<s2:else>
											<a
												href="showPhotos.action?albumId=<s2:property value="albumId"/>&&currentPage=<s2:property value='#pageInfo.pageNum-1' />"
												class="nor">末页</a>
										</s2:else>
							</form>
						</div>
						<!-- 分页代码 end -->
						</s2:if>
						<s2:else>
							<div
								style="text-align: center; font-size: 20px; color: #ccc; width: 700px; height: 300px; font-weight: bold;">
								此相册为空，您还没有给此相册上传相片！
								<font color=green><a
									href="addPhoto.action?albumId=<s2:property value='albumId' />">点击
										开始上传相片</a> </font>
							</div>
							</DIV>
							<br>
						</s2:else>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<!-- foot -->
	</DIV>


<script type="text/javascript">
var prox;
var proy;
var proxc;
var proyc;
function show(id,photoId,albumId,name,descr){/*--打开--*/
document.getElementById("fd_photoId").value=photoId;
document.getElementById("fd_albumId").value=albumId;
document.updatePhoto.elements[2].value=name;
document.updatePhoto.elements[3].value=descr;
clearInterval(prox);
clearInterval(proy);
clearInterval(proxc);
clearInterval(proyc);
var o = document.getElementById(id);
o.style.display = "block";
o.style.width = "1px";
o.style.height = "1px"; 
prox = setInterval(function(){openx(o,300)},5);           //div宽度

} 
function openx(o,x){/*--打开x--*/
var cx = parseInt(o.style.width);
if(cx < x)
{
   o.style.width = (cx + Math.ceil((x-cx)/5)) +"px";
}
else
{
   clearInterval(prox);
   proy = setInterval(function(){openy(o,200)},5);        //div高度
}
} 
function openy(o,y){/*--打开y--*/ 
var cy = parseInt(o.style.height);
if(cy < y)
{
   o.style.height = (cy + Math.ceil((y-cy)/5)) +"px";
}
else
{
   clearInterval(proy);   
}
} 
function closeed(id){/*--关闭--*/
clearInterval(prox);
clearInterval(proy);
clearInterval(proxc);
clearInterval(proyc); 
var o = document.getElementById(id);
if(o.style.display == "block")
{
   proyc = setInterval(function(){closey(o)},5);   
} 
} 
function closey(o){/*--关闭y--*/ 
var cy = parseInt(o.style.height);
if(cy > 0)
{
   o.style.height = (cy - Math.ceil(cy/5)) +"px";
}
else
{
   clearInterval(proyc);    
   proxc = setInterval(function(){closex(o)},5);
}
} 
function closex(o){/*--关闭x--*/
var cx = parseInt(o.style.width);
if(cx > 0)
{
   o.style.width = (cx - Math.ceil(cx/5)) +"px";
}
else
{
   clearInterval(proxc);
   o.style.display = "none";
}


 

} 

</script>

	<jsp:include page="foot.jsp"></jsp:include>
	</body>
</html>