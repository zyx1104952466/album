<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s2" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<LINK rel=stylesheet type=text/css href="css/images/photo.css">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>相片列表</title>
		
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

	<s2:set name="myPhotos" value="#request.myPhotos" />
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
								value="content" /> </A></SPAN>
				</DIV>
			</s2:iterator>
		</DIV>
		<!--左侧导航结束-->
		<div class="C_9">
			<s2:if test="#myPhotos == null">
				相片列表
			</s2:if>
			<s2:else>
				<s2:property value="#myPhotos[0].fromUser" />的相册：<s2:property
					value="#myPhotos[0].fromAlbum" />
			</s2:else>

		</div>
		<DIV class=C_8>
			<DIV class=C_13>
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
								<s2:property value='name' /> </SPAN>
							<br>
							浏览：
							<s2:property value="skimNum" />
							次
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
											<a href="showPhotos.action?albumId=<s2:property value="albumId"/>&&" class="nor">首页</a>&nbsp;&nbsp;&nbsp;
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
								相册的主人还没有上传相片，请稍后再来浏览！
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
	<jsp:include page="foot.jsp"></jsp:include>
	</body>
</html>