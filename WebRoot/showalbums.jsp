<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s2" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<LINK rel=stylesheet type=text/css href="css/images/photo.css">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>用户相册列表</title>
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
								value="content" /> </A></SPAN>
				</DIV>
			</s2:iterator>
		</DIV>
		<!--左侧导航结束-->
		<div class="C_9">
			相册列表
		</div>

		<DIV class=C_8>
			<DIV class=C_13>
				<s2:set name="myAlbums" value="#request.myAlbums" />
				<s2:if test="#myAlbums != null">
					<s2:iterator status="offset" value="myAlbums">
						<s2:if test="#offset.index==0 || #offset.index==4">
							<DIV class="D_6 r4">
						</s2:if>
						<s2:else>
							<DIV class="D_6 ml50">
						</s2:else>
						<DIV class="D_5">
							<TABLE border=0 cellSpacing=0 cellPadding=0>
								<TR>
									<TD height=120 vAlign="middle" width=120 align="left">
										<s2:if test="open==1">
											<A
												href="showPhotos.action?albumId=<s2:property value='id' />">
												<img class="album"
													title="相册介绍：<s2:property value='introduce'/>"
													src="<s2:property value='url'/>" border="0" /> </A>
										</s2:if>
										<s2:else>
											<img class="album"
												title="相册介绍：<s2:property value='introduce'/>"
												src="<s2:property value='url'/>" border="0" />
										</s2:else>
									</TD>
								</TR>
							</TABLE>
						</DIV>
						<DIV class="D_2">
							<s2:if test="open==1">
								<SPAN
									style="FLOAT: left; width: 100px; height: 20px; overflow: hidden; text-overflow: ellipsis;">
									<A title="相册介绍：<s2:property value='introduce'/>"
									href="showPhotos.action?albumId=<s2:property value='id' />"><s2:property
											value="name" /> </A> </SPAN>
								<SPAN style="COLOR: #0376c2">(<s2:property
										value="photoNum" />张)</SPAN>
							</s2:if>
							<s2:else>
								<SPAN
									style="COLOR: #0376c2; FLOAT: left; width: 100px; height: 20px; overflow: hidden; text-overflow: ellipsis;">
									<s2:property value="name" /> </SPAN>
								<SPAN style="COLOR: #0376c2">(<s2:property
										value="photoNum" />张)</SPAN>
							</s2:else>
							<BR>
							<DIV class=D_4>
								<DIV class=D_3>
									作者：
									<s2:property value="username" />
								</DIV>
							</DIV>
							<BR>
							创建：
							<s2:property value="addTime" />
							<BR>
							<s2:if test="open==1">
								<SPAN style="FLOAT: left">浏览：<s2:property value="skimNum" />次</SPAN>
							</s2:if>
							<s2:else>
								<font color=red><SPAN style="FLOAT: left">属性：私密相册</SPAN>
								</font>
							</s2:else>
						</DIV>
			</DIV>
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
							<form action="goindex.action" name="pageForm" method="post">
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
											<a href="showAlbums.action?currentPage=0&&userId=<s2:property value='#myAlbums[0].userid' />" class="nor">首页</a>&nbsp;&nbsp;&nbsp;
									</s2:if>
										<s2:else>
											<span class="nor">首页</span>&nbsp;&nbsp;&nbsp;
									</s2:else>
										<!-- Prep Page -->

										<s2:if test="#pageInfo.currentPage>0">
											<a
												href="showAlbums.action?currentPage=<s2:property value='#pageInfo.currentPage-1'/>&&userId=<s2:property value='#myAlbums[0].userid' />"
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
												href="showAlbums.action?currentPage=<s2:property value='#pageInfo.currentPage+1'/>&&userId=<s2:property value='#myAlbums[0].userid' />"
												class="nor">下一页</a>&nbsp;&nbsp;&nbsp;
											</s2:else>
										<!-- Last Page -->

										<s2:if test="#pageInfo.currentPage+1>=#pageInfo.pageNum">
											<span class=nor>末页</span>
										</s2:if>
										<s2:else>
											<a
												href="showAlbums.action?currentPage=<s2:property value='#pageInfo.pageNum-1' />&&userId=<s2:property value='#myAlbums[0].userid' />"
												class="nor">末页</a>
										</s2:else>
							</form>
						</div>
						<!-- 分页代码 end -->
						</s2:if>
						<s2:else>
							<div
								style="text-align: center; font-size: 20px; color: #ccc; width: 700px; height: 300px; font-weight: bold;">
								该用户还没有创建相册！
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