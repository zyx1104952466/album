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
		<title>评论管理</title>
	</head>
	<jsp:include page="top.jsp"></jsp:include>

<s2:set name="pageInfo" value="#request.pageInfo" />
	
		<!-- 评论列表开始 -->
		<DIV style="MARGIN-LEFT: 0" id=comment_container class=Z_5>
		
			<s2:iterator value="comments" >
			<TABLE style="BORDER-BOTTOM: #ccc 1px dashed; MARGIN-TOP: 5px"
				onmouseover='this.style.backgroundColor="#F2F5F9"'
				onmouseout='this.style.backgroundColor=""' border=0 width="100%"
				align=center height=103>
				<TBODY>
					<TR>
						<TD vAlign=top rowSpan=3 width=120 align=center>
							<IMG border=0 src="<s2:property value='url'/>">
						</TD>
						<TD colSpan=4></TD>
					</TR>
					<TR>
						<TD
							style="LINE-HEIGHT: 20px; FONT-SIZE: 12px; OVERFLOW: hidden; PADDING-TOP: 10px"
							class=wrap vAlign=top colSpan=4 align=left>
							<s2:property value="content"/>
						</TD>
					</TR>
					<TR>
						<TD height=40 align=left>
							评论者：
						<s2:property value="fromUser"/>&nbsp;&nbsp;
						</TD>
						<TD style="COLOR: #999999" width=160 align=left>
							发表时间： <s2:property value="addTime"/>
						</TD>
						<TD width=20 align=right></TD>
						<TD width=20 align=right>
						<TD width=30 align=right>
							<DIV class="z_f_2 r">
								<A href="comment_deleteComment.action?currentPage=<s2:property value='#pageInfo.currentPage'/>&&cid=<s2:property value='id'/>"><font color="red">删除</font></A>
							</DIV>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			</s2:iterator>
			
		</DIV>
		<div style="width:100%">
			<TABLE border=0 cellSpacing=0 cellPadding=0 height=50 align="center">
			<TBODY>
				<TR>
					<TD>
						<!-- 分页代码 start -->
						<div>
							<form action="gethotalbums.action" name="pageForm" method="post">
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
											<a href="comment_getCommentList.action" class="nor">首页</a>&nbsp;&nbsp;&nbsp;
									</s2:if>
										<s2:else>
											<span class="nor">首页</span>&nbsp;&nbsp;&nbsp;
									</s2:else>
										<!-- Prep Page -->

										<s2:if test="#pageInfo.currentPage>0">
											<a
												href="comment_getCommentList.action?currentPage=<s2:property value='#pageInfo.currentPage-1'/>"
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
												href="comment_getCommentList.action?currentPage=<s2:property value='#pageInfo.currentPage+1' />"
												class="nor">下一页</a>&nbsp;&nbsp;&nbsp;
											</s2:else>
										<!-- Last Page -->

										<s2:if test="#pageInfo.currentPage+1>=#pageInfo.pageNum">
											<span class=nor>末页</span>
										</s2:if>
										<s2:else>
											<a
												href="comment_getCommentList.action?currentPage=<s2:property value='#pageInfo.pageNum-1' />"
												class="nor">末页</a>
										</s2:else>
							</form>
						</div>
						<!-- 分页代码 end -->
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<!-- foot -->
</div>
	<jsp:include page="foot.jsp"></jsp:include>
	</body>
</html>