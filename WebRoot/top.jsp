<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s2" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<LINK rel=stylesheet type=text/css href="css/photo.css">
		<script type="text/javascript" src="js/util.js"></script>
		<script type="text/javascript" src="js/search.js"></script>
		<title>top</title>
		<meta name=GENERATOR content="MSHTML 8.00.6001.18812">
	</head>
	<body>

		<DIV class=div_body>
			<DIV class=div_head1>
				<DIV class=A_1>
					<DIV class="A_3 r">
						<DIV class=New_topca2>
							<s2:set name="user" value="#session.user" />
							<s2:set name="admin" value="#session.admin" />
							<s2:if test="#admin!=null">
           							 欢迎系统管理员：<s2:property value="#admin.username" />
							</s2:if>
							<s2:elseif test="#user==null">
									游客，您好！
								</s2:elseif>
							<s2:elseif test="#user!=null">
									您已经登录，欢迎用户：<s2:property value="#user.nickName" />
							</s2:elseif>
						</DIV>
					</DIV>
				</DIV>
				<DIV id=top_login class="hui912 r"></DIV>
			</DIV>
			<DIV class=A_4>
				<br>
				<IMG border=0 alt=电子相册 src="images/logo.gif">
				<DIV class=A_5>
					记录、分享你我的美好生活
				</DIV>
				<DIV class="A_6 r1">


					<!-- 系统搜索表单  -->
					<FORM id=sea method=post name=search action="search.action">
						<input type="hidden" name="searchType" />
						<DIV class=A_7>
							<input type="radio" name="status" value="1" checked="checked" />
						</DIV>
						<DIV class=A_8>
							<LABEL for=search_type1>
								相册
							</LABEL>
						</DIV>

						<DIV class=A_7>
							<input type="radio" name="status" value="2" />
						</DIV>
						<DIV class=A_8>
							<LABEL for=search_type2>
								照片
							</LABEL>
						</DIV>

						<DIV class=A_7>
							<input type="radio" name="status" value="3" />
						</DIV>
						<DIV class=A_8>
							<LABEL for=search_type3>
								作者
							</LABEL>
						</DIV>

						<DIV class=A_9>
							<INPUT id=kword class=tf1 onkeyup=searchSuggest(this.value)
								name=kword onkeydown="Keysubmit()" />
						</DIV>
						<INPUT class=r src="images/dabn1.gif" width=60 height=23
							id=btn_search value="搜索" type=button onclick=goSearch() />
						<div id="search_suggest" style="visibility: hidden; height: 1px;">
							&nbsp;
						</div>
					</FORM>


				</DIV>
			</DIV>
		</DIV>

		<DIV class=div_body>
			<s2:if test="#admin!=null">
				<DIV class=A_10>
					<DIV
						style="PADDING-BOTTOM: 0px; PADDING-LEFT: 3px; PADDING-RIGHT: 0px; PADDING-TOP: 5px">
						<SPAN class=A_16><A class=bei14bi href="gethotusers.action">用户管理</A>
						</SPAN><SPAN style="COLOR: #97d2ec"> |</SPAN>
					</DIV>

					<DIV
						style="PADDING-BOTTOM: 0px; PADDING-LEFT: 10px; PADDING-RIGHT: 0px; PADDING-TOP: 5px">
						<SPAN class=A_16><A class=bei14bi href="logout.action">退出系统</A>
						</SPAN>
					</DIV>
				</DIV>
			</s2:if>

			<s2:elseif test="#user==null">
				<DIV class=A_10>
					<DIV
						style="PADDING-BOTTOM: 0px; PADDING-LEFT: 3px; PADDING-RIGHT: 0px; PADDING-TOP: 5px">
						<SPAN class=A_16><A class=bei14bi
							href="gethotphotos.action">最热相片</A> </SPAN><SPAN style="COLOR: #97d2ec">
							|</SPAN>
					</DIV>
					<DIV
						style="PADDING-BOTTOM: 0px; PADDING-LEFT: 3px; PADDING-RIGHT: 0px; PADDING-TOP: 5px">
						<SPAN class=A_16><A class=bei14bi
							href="gethotalbums.action">最热相册</A> </SPAN><SPAN style="COLOR: #97d2ec">
							|</SPAN>
					</DIV>
					<DIV
						style="PADDING-BOTTOM: 0px; PADDING-LEFT: 10px; PADDING-RIGHT: 0px; PADDING-TOP: 5px">
						<SPAN class=A_16><A class=bei14bi href="gethotusers.action">活跃用户</A>
						</SPAN><SPAN style="COLOR: #97d2ec"> |</SPAN>
					</DIV>
					<DIV
						style="PADDING-BOTTOM: 0px; PADDING-LEFT: 10px; PADDING-RIGHT: 0px; PADDING-TOP: 5px">
						<SPAN class=A_16><A class=bei14bi href="login.jsp">用户登录</A>
						</SPAN><SPAN style="COLOR: #97d2ec"> |</SPAN>
					</DIV>
					<DIV
						style="PADDING-BOTTOM: 0px; PADDING-LEFT: 10px; PADDING-RIGHT: 0px; PADDING-TOP: 5px">
						<SPAN class=A_16><A class=bei14bi href="register.jsp">用户注册</A>
						</SPAN><SPAN style="COLOR: #97d2ec"> |</SPAN>
						</SPAN>
					</DIV>
					<DIV
						style="PADDING-BOTTOM: 0px; PADDING-LEFT: 10px; PADDING-RIGHT: 0px; PADDING-TOP: 5px">
						<SPAN class=A_16><A class=bei14bi href="login_admin.jsp">管理员登陆</A>
						</SPAN>
					</DIV>
				</DIV>
			</s2:elseif>

			<s2:elseif test="#user!=null">

				<DIV class=A_10>
					<DIV
						style="PADDING-BOTTOM: 0px; PADDING-LEFT: 3px; PADDING-RIGHT: 0px; PADDING-TOP: 5px">
						<SPAN class=A_16><A class=bei14bi
							href="gethotphotos.action">最热相片</A> </SPAN><SPAN style="COLOR: #97d2ec">
							|</SPAN>
					</DIV>
					<DIV
						style="PADDING-BOTTOM: 0px; PADDING-LEFT: 3px; PADDING-RIGHT: 0px; PADDING-TOP: 5px">
						<SPAN class=A_16><A class=bei14bi
							href="gethotalbums.action">最热相册</A> </SPAN><SPAN style="COLOR: #97d2ec">
							|</SPAN>
					</DIV>
					<DIV
						style="PADDING-BOTTOM: 0px; PADDING-LEFT: 10px; PADDING-RIGHT: 0px; PADDING-TOP: 5px">
						<SPAN class=A_16><A class=bei14bi href="gethotusers.action">活跃用户</A>
						</SPAN><SPAN style="COLOR: #97d2ec"> |</SPAN>
					</DIV>
					<DIV
						style="PADDING-BOTTOM: 0px; PADDING-LEFT: 3px; PADDING-RIGHT: 0px; PADDING-TOP: 5px">
						<SPAN class=A_16><A class=bei14bi href="addalbum.jsp">创建相册</A>
						</SPAN><SPAN style="COLOR: #97d2ec"> |</SPAN>
					</DIV>
					<DIV
						style="PADDING-BOTTOM: 0px; PADDING-LEFT: 3px; PADDING-RIGHT: 0px; PADDING-TOP: 5px">
						<SPAN class=A_16><A class=bei14bi
							href="showAlbums.action?userId=<s2:property value='#user.id' />">相册管理</A>
						</SPAN><SPAN style="COLOR: #97d2ec"> |</SPAN>
					</DIV>
					<DIV
						style="PADDING-BOTTOM: 0px; PADDING-LEFT: 10px; PADDING-RIGHT: 0px; PADDING-TOP: 5px">
						<SPAN class=A_16><A class=bei14bi
							href="comment_getCommentList.action">评论管理</A> </SPAN><SPAN
							style="COLOR: #97d2ec"> |</SPAN>
					</DIV>
					<DIV
						style="PADDING-BOTTOM: 0px; PADDING-LEFT: 10px; PADDING-RIGHT: 0px; PADDING-TOP: 5px">
						<SPAN class=A_16><A class=bei14bi href="changemypic.jsp">头像管理</A>
						</SPAN><SPAN style="COLOR: #97d2ec"> |</SPAN>
					</DIV>
					<DIV
						style="PADDING-BOTTOM: 0px; PADDING-LEFT: 10px; PADDING-RIGHT: 0px; PADDING-TOP: 5px">
						<SPAN class=A_16><A class=bei14bi href="changeuserinfo.jsp">信息修改</A>
						</SPAN><SPAN style="COLOR: #97d2ec"> |</SPAN>
					</DIV>
					<DIV
						style="PADDING-BOTTOM: 0px; PADDING-LEFT: 10px; PADDING-RIGHT: 0px; PADDING-TOP: 5px">
						<SPAN class=A_16><A class=bei14bi href="logout.action">退出系统</A>
						</SPAN>
					</DIV>
				</DIV>
			</s2:elseif>
	</body>
</html>