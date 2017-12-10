<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s2" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<LINK rel=stylesheet type=text/css href="css/photo.css">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>活跃用户列表</title>
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
		<DIV class=C_8>

			<div class="C_9">
			下面是与关键字"
			<font color=red><s2:property value="#request.keyword" />
			</font> "匹配的用户名：
		</div>
		<DIV class=C_8>

			<DIV class=E_6>
				<s2:set name="users" value="#request.users" />
				<s2:set name="users" value="#request.users" />
				<s2:iterator status="offset" value="users" id="user">
<!-- 偶数项 -->
					<s2:if test="#offset.even==true">
						<DIV class="E_7 r">
							<DIV class=E_8>
								<DIV
									style="BORDER-BOTTOM: #cccccc 1px solid; BORDER-LEFT: #cccccc 1px solid; WIDTH: 100px; HEIGHT: 100px; BORDER-TOP: #cccccc 1px solid; BORDER-RIGHT: #cccccc 1px solid">
									<TABLE border=0 cellSpacing=0 cellPadding=0>
										<TBODY>
											<TR>
												<A href="showAlbums.action?userId=<s2:property value='id' />" >
												<TD
													style="background-image:url(<s2:property value='url'/>); background-repeat: no-repeat; background-position: center center;"
													vAlign=middle align=center height=100 width=150>
												</TD>
												</A>
											</TR>
										</TBODY>
									</TABLE>
								</DIV>
								<DIV
									style="PADDING-BOTTOM:0px; PADDING-LEFT:12px; WIDTH:242px; PADDING-RIGHT:0px; BACKGROUND:url(images/no_<s2:property value='#offset.index+1' />.jpg) no-repeat right bottom; HEIGHT: 111px; PADDING-TOP: 0px">
									<A href="showAlbums.action?userId=<s2:property value='id' />"
										><s2:property value="username" /> </A>
									<BR>

								</DIV>
							</DIV>
							<DIV class=E_12>
								昵称：
								<FONT class=hui12b><s2:property value="nickName" /> </FONT>
								<br>
								相册：
								<A href="showAlbums.action?userId=<s2:property value='id' />"><s2:property
										value="albumNum" /> </A>&nbsp;&nbsp;个
								<BR>
								照片：&nbsp;<s2:property value="photoNum" /> &nbsp;&nbsp;张
								<BR>
								活跃度：
								<FONT class=hui12b><s2:property value="active" /> </FONT>
								<BR>
								注册时间：
								<FONT class=hui12b><s2:property value="addTime" /> </FONT>
							</DIV>
							<DIV class=E_13>
								<DIV class=E_14>
									佳作赏析：
								</DIV>
								<DIV class=E_15>
									<UL>
										<s2:iterator value="#user.hotPhotos">
											<LI>
												·
												<A
													href="photoView.action?photoId=<s2:property value='id' />"
													target="_blank"><s2:property value="name" /> </A>
											</LI>
										</s2:iterator>
									</UL>
								</DIV>
							</DIV>
						</DIV>
						<DIV class=E_16></DIV>
					</s2:if>
					<!-- 奇数项 -->
					<s2:else>
						<DIV class=E_7>
							<DIV class=E_8>
								<DIV
									style="BORDER-BOTTOM: #cccccc 1px solid; BORDER-LEFT: #cccccc 1px solid; WIDTH: 100px; HEIGHT: 100px; BORDER-TOP: #cccccc 1px solid; BORDER-RIGHT: #cccccc 1px solid">
									<TABLE border=0 cellSpacing=0 cellPadding=0>
										<TBODY>
											<TR>
												<A href="showAlbums.action?userId=<s2:property value='id'/>" >
												<TD
													style="background-image:url(<s2:property value='url'/>); background-repeat: no-repeat; background-position: center center;"
													vAlign=middle align=center height=100 width=150>
												</TD>
												</A>
											</TR>
										</TBODY>
									</TABLE>
								</DIV>
								<DIV
									style="PADDING-BOTTOM: 0px; PADDING-LEFT: 12px; WIDTH: 242px; PADDING-RIGHT: 0px; BACKGROUND: url(images/no_<s2:property value='#offset.index+1' />.jpg) no-repeat right bottom; HEIGHT: 111px; PADDING-TOP: 0px">
									<A href="showAlbums.action?userId=<s2:property value='id' />"
										><s2:property value="username" /> </A>
									<BR>

								</DIV>
							</DIV>
							<DIV class=E_12>
								昵称：
								<FONT class=hui12b><s2:property value="nickName" /> </FONT>
								<br>
								相册：
								<A href="showAlbums.action?userId=<s2:property value='id' />"
									target=_blank><s2:property value="albumNum" /> </A>&nbsp;&nbsp;个
								<BR>
								照片：&nbsp;<s2:property value="photoNum" /> &nbsp;&nbsp;张
								<BR>
								活跃度：
								<FONT class=hui12b><s2:property value="active" /> </FONT>
								<BR>
								注册时间：
								<FONT class=hui12b><s2:property value="addTime" /> </FONT>
							</DIV>
							<DIV class=E_13>
								<DIV class=E_14>
									佳作赏析：
								</DIV>
								<DIV class=E_15>
									<UL>
										<s2:iterator value="#user.hotPhotos">
											<LI>
												·
												<A
													href="photoView.action?photoId=<s2:property value='id' />"
													target="_blank"><s2:property value="name" /> </A>
											</LI>
										</s2:iterator>
									</UL>
								</DIV>
							</DIV>
						</DIV>
					</s2:else>
				</s2:iterator>


			</DIV>

			<TABLE border=0 cellSpacing=0 cellPadding=0 height=50 align=left>
				<TBODY>
					<TR>
						<TD>
							<!-- 分页代码 start -->

							<s2:set name="pageInfo" value="#request.pageInfo" />
							<div>
								<form action="" name="pageForm" method="post">
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
											<a href="search.action?currentPage=0&&kword=<s2:property value='#request.keyword'/>&&searchType=<s2:property value='#request.searchType'/>" class="nor">首页</a>&nbsp;&nbsp;&nbsp;
									</s2:if>
										<s2:else>
											<span class="nor">首页</span>&nbsp;&nbsp;&nbsp;
									</s2:else>
										<!-- Prep Page -->

										<s2:if test="#pageInfo.currentPage>0">
											<a
												href="search.action?currentPage=<s2:property value='#pageInfo.currentPage-1'/>&&kword=<s2:property value='#request.keyword'/>&&searchType=<s2:property value='#request.searchType'/>"
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
												href="search.action?currentPage=<s2:property value='#pageInfo.currentPage+1'/>&&kword=<s2:property value='#request.keyword'/>&&searchType=<s2:property value='#request.searchType'/>"
												class="nor">下一页</a>&nbsp;&nbsp;&nbsp;
											</s2:else>
										<!-- Last Page -->

										<s2:if test="#pageInfo.currentPage+1>=#pageInfo.pageNum">
											<span class=nor>末页</span>
										</s2:if>
										<s2:else>
											<a href="search.action?currentPage=<s2:property value='#pageInfo.pageNum-1'/>&&kword=<s2:property value='#request.keyword'/>&&searchType=<s2:property value='#request.searchType'/>" class="nor">末页</a>
										</s2:else>
							</form>
							</div>
							</div>
							<!-- 分页代码 end -->

						</TD>
						<TD></TD>
					</TR>
				</TBODY>
			</TABLE>
			<!-- foot -->
		</DIV>
		<jsp:include page="foot.jsp"></jsp:include>
		</body>
</html>