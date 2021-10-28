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
		<title>相片展示</title>
		
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

	<s2:set name="photo" value="#request.photo" />
	<s2:set name="user" value="#request.user" />
	<s2:set name="album" value="#request.album" />
	<s2:set name="comment" value="#request.comment" />
	<DIV class="bodyW mt7">
	
		<DIV class=P_1>
			<DIV class=P_2>
				相片名称：
				<span id=v_1><s2:property value="#photo.name" /></span>
			</DIV>
			<DIV class=P_3>
				<DIV class=P_4>
					<a style="cursor:hand "
						href="javascript:sayFine()">推荐</a>
					<IMG alt=推荐 src="images/dpbn4.gif" />
				</DIV>

				<DIV style="padding-left: 200px; width: 200px; align: right">
					点击下图切换到下一张相片
				</DIV>
			</DIV>
			<DIV class="P_8 mt7">
				<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
					<TBODY>
						<TR>
							<TD align=center>
								<IMG id=v_3 onclick="nextPhoto(<s2:property value='#album.id'/>);" style="CURSOR: url(images/next.ani)" alt=点击图片切换到下一张相片
									src="<s2:property value='#photo.url'/>" />
							</TD>
						</TR>
					</TBODY>
				</TABLE>
			</DIV>
			<DIV class=P_9>
				<DIV
					style="LINE-HEIGHT: 18px; WIDTH: 600px; OVERFLOW: hidden; WORD-BREAK: break-all"
					id=des_4817701 class=des2></DIV>
			</DIV>
		</DIV>
		<DIV class="P_10 r">
			<DIV class=P_11>
				<DIV
					style="BORDER-BOTTOM: #cccccc 1px solid; BORDER-LEFT: #cccccc 1px solid; BORDER-TOP: #cccccc 1px solid; BORDER-RIGHT: #cccccc 1px solid"
					class=L_3>
					<TABLE border=0 cellSpacing=0 cellPadding=0>
						<TBODY>
							<TR>
								<TD style="WIDTH: 100px; HEIGHT: 100px" vAlign=middle
									align=center>
									<A
										href="showAlbums.action?userId=<s2:property value='#user.id'/>"><IMG
											style="FLOAT: none" alt=""
											src="<s2:property value='#user.url' />"> </A>
								</TD>
							</TR>
						</TBODY>
					</TABLE>
				</DIV>
				

				<DIV class=P_12>
					<DIV class=P_16>
						用户名：
						<s2:property value="#user.username" />
					</DIV>

					<DIV class=P_16>
						昵称：
						<s2:property value="#user.nickName" />
					</DIV>

					<DIV class=P_16>
						E-mail：
						<s2:property value="#user.email" />
					</DIV>
					<DIV class=P_16>
						活跃度：
						<s2:property value="#user.active" />
					</DIV>
					<DIV class=P_14>
						注册时间：
						<s2:property value="#user.addTime" />
					</DIV>
				</DIV>
			</DIV>

			<DIV class="P_17 mt7">
				<DIV class=hui14b>
					<SPAN style="CURSOR: hand"
						title="<s2:property value='#album.name' />">相册名：<s2:property
							value="#album.name" /> </SPAN>
				</DIV>
				<DIV class=r>
					<A class=hui12i
						href="showPhotos.action?albumId=<s2:property value='#album.id' />">返回该相册</A>
				</DIV>
			</DIV>
			<DIV class="P_18 bd8">
				<DIV class=P_19>
					<DIV class=D_5>
						<TABLE border=0 cellSpacing=0 cellPadding=0>
							<TBODY>
								<TR>
									<TD height=120 vAlign=middle width=120 align=center>

										<A
											href="showPhotos.action?albumId=<s2:property value='#album.id' />">
											<img onload=DrawImage(this,120,120)
												title="相册介绍：<s2:property value='#album.introduce' />"
												src="<s2:property value='#album.url' />" border="0" /> </A>
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</DIV>


					<DIV
						style="LINE-HEIGHT: 18px; WIDTH: 120px; OVERFLOW: hidden; WORD-BREAK: break-all">

						<DIV class=P_16>
							浏览次数：
							<s2:property value="#album.skimNum" />
							次
						</DIV>
						<DIV class=P_16 style="padding-top: 5px;">
							共有相片：
							<s2:property value="#album.photoNum" />
							张
						</DIV>

						<s2:if test="#album.opne==0">
							<DIV class=P_16 style="padding-top: 5px;">
								相册属性：
								<font color=red>私密相册</font>
							</DIV>
						</s2:if>
						<s2:else>
							<DIV class=P_16 style="padding-top: 5px;">
								相册属性：公开相册
							</DIV>
						</s2:else>
						<DIV class=P_14 style="padding-top: 5px;">
							创建时间：
							<s2:property value="#album.addTime" />
						</DIV>
						<DIV class=P_16_s style="padding-top: 5px;">
							相册介绍：
							<s2:property value="#album.introduce" />
						</DIV>
					</DIV>


				</DIV>
			</DIV>

			<!--照片详细信息-->
			<DIV class="P_17 mt7">
				<DIV class=hui14b>
					相片信息
				</DIV>
			</DIV>
			<DIV class="P_18 bd8">
				<DIV class=P_23>
					<DIV class=P_27>
						浏览：
						<span id=v_4><s2:property value="#photo.skimNum" /></span>
						次
						<BR>
						被推荐：
						<!-- id = sayFineCount -->	
						<span id=sayFineCount><s2:property value="#photo.fine" /></span>
						次
						<BR>
						上传时间：
						<span id=v_5><s2:property value="#photo.addTime" /></span>
						<BR clear=all>
					</DIV>
					<DIV class=P_28>
						<A href="javascript:sayFine()"><IMG
								style="FLOAT: left" border=0 alt=我要推荐 src="images/dqbn6.gif"
								width=117 height=37> </A>
					</DIV>
				</DIV>
			</DIV>
			<BR clear=all>
		</DIV>


		<form name="pubComment" method="post">
			<INPUT id="photoId" value=<s2:property value="#photo.id" /> type="hidden"
				name="photoId" />
			<DIV style="MARGIN-LEFT: 12px; PADDING-TOP: 20px" class=Z_8>
				<TABLE border=0 cellSpacing=0 cellPadding=0 width=540>
					<TBODY>
						<TR>
							<TD>
								<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
									<TBODY>
										<TR>
											<TD id=comment_str align=right></TD>
										</TR>
									</TBODY>
								</TABLE>
							</TD>
						</TR>
						<TR>
							<TD>
								<TABLE id=login_table>
									<TBODY>
										<TR>
											<TD>
												<s2:if test="#session.user==null">
											您还没有登陆，只能以游客身份发表评论！  <a href="login.jsp">我要登录</a>
													<INPUT value="游客" type="hidden" name="fromuser" />
													<INPUT value=0 type="hidden" name="userId" />
													<INPUT value="images/default/nopic.jpg" type="hidden"
														name="url" />
												</s2:if>
												<s2:else>
													<font color="#0376c2"><s2:property
															value="#session.user.username" />
													</font>  ，欢迎您留下评论
													<INPUT value=<s2:property value="#session.user.username" />
														type=hidden name=fromuser />
													<INPUT value=<s2:property value="#session.user.url" />
														type="hidden" name="url" />
													<INPUT value=<s2:property value="#session.user.id" /> type="hidden"
														name="userId" />
												</s2:else>
											</TD>
										</TR>
									</TBODY>
								</TABLE>
							</TD>
						</TR>
						<TR>
							<TD>
								<TEXTAREA style="WIDTH: 520px; HEIGHT: 69px" id=content
									name=content title="评论内容"></TEXTAREA>
							</TD>
						</TR>
						<TR>
							<TD>
								<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
									<TBODY>
										<TR>
											<TD height=40 width=80>
												<INPUT id=bn value=我要评论 type=button name=bn
													onclick="sendFormToServer('pubComment','pubComment.action')">
											</TD>
											<TD class=Z_9>
												欢迎留下您的评论
											</TD>
											<TD id=submit_ok align=left></TD>
										</TR>
									</TBODY>
								</TABLE>
							</TD>
						</TR>
					</TBODY>
				</TABLE>
			</DIV>
		</form>



		<!-- 评论列表开始 -->
		<DIV style="MARGIN-LEFT: 12px" id=comment_container class=Z_5>
			<s2:iterator  value="comment" >
			
			<TABLE style="BORDER-BOTTOM: #ccc 1px dashed; MARGIN-TOP: 5px"
				onmouseover='this.style.backgroundColor="#F2F5F9"'
				onmouseout='this.style.backgroundColor=""' border=0 width="100%"
				align=center height=103>
				<TBODY>
					<TR>
						<TD vAlign=top rowSpan=3 width=120 align=center>
							<s2:if test="userId!=0">
							<A href="showAlbums.action?userId=<s2:property value='userId'/>"><IMG
									border=0 src="<s2:property value='url'/>"> </A>
							</s2:if>
							<s2:else> 
							<IMG
									border=0 src="<s2:property value='url'/>">
							</s2:else>		
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
								
							</DIV>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			</s2:iterator>
			
		</DIV>






		<!--照片推荐信息-->
		<BR style="CLEAR: both">
		<BR>
	</DIV>

	<jsp:include page="foot.jsp"></jsp:include>
	</body>
</html>