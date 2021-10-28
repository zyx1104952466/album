//－－－－－－－－－－－－－－－－－－控制ajax的javascript－－－－－－－－－－－－－
	var xmlHttp;
//－－－－－－－－－－－－－－－创建XMLHttpRequest对象－－－－－－－－－－－－－－－－－
function createXMLHTTP() {
		if(window.XMLHttpRequest)
	{
		xmlHttp = new XMLHttpRequest();     //Mozilla 浏览器
		if (xmlHttp.overrideMimeType)
		{
			xmlHttp.overrideMimeType('text/xml');    //设置MiME类别
		}
	}
	else if (window.ActiveXObject)
	{
		try{xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");}     //IE浏览器
		catch (e)
		{
			try {xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");}
			catch (e) {}
		}
	}
	if (!xmlHttp)
	{
		window.alert("不能创建XMLHttpRequest对象实例.");    //异常，创建对象实例失败
		return false;
	}
}

//-------------------------创建显示返回内容的Div－－－－－－－－－－－－－－－－
function createDiv(content){
	//创建显示返回内容的Div
	var respDiv=document.createElement("div");
	respDiv.className="respDiv";
	//document.body.appendChild(respDiv);
	var main=document.getElementById("mainAdmin");
	main.appendChild(respDiv);
	respDiv.innerHTML=content;
}
///--------------------向服务器发送数据，并接受返回数据库，路径为url，参数为args----------------------
function sendMessageToServer(url,args){
	//alert(url);
    //alert(args);
	//获取XMLHttpRequest对象
	createXMLHTTP();
		xmlHttp.onreadystatechange=function(){
		if(xmlHttp.readyState==4){
			//数据已经接收成功		
					if(xmlHttp.status==200){
						//请求成功
						var respText=xmlHttp.responseText;
						if(document.getElementById("usercheck") != null) {
							document.getElementById("usercheck").innerHTML = respText;
							return;
						}
						if (respText=='sayFineOk'){
							alert('推荐成功，谢谢您的支持！');
							var i = parseInt(document.getElementById("sayFineCount").innerHTML);
							i = i + 1;
							//alert(i);
							document.getElementById("sayFineCount").innerHTML = i;
							return;
						} 
						if (respText=='noRepeat'){
							alert('请不要重复评论，谢谢合作！');
							return;
						} else {
							var json = eval("("+respText+")");
							alert("评论成功！");
							var userId = json.comment.userId;
							//alert(userId);
							var fromUser = json.comment.fromUser;
							var addTime = json.comment.addTime;
							var content = json.comment.content;
							var url = json.comment.url
							var old_comment = document.getElementById("comment_container").innerHTML;
							var new_comment="<TABLE style=\"BORDER-BOTTOM: #ccc 1px dashed; MARGIN-TOP: 5px\"";
							new_comment+="onmouseover=\'this.style.backgroundColor=\"#F2F5F9\"\'";
							new_comment+="onmouseout=\'this.style.backgroundColor=\"\"\' border=0 width=\"100%\"";
							new_comment+="align=center height=103>";
							new_comment+="<TBODY><TR><TD vAlign=top rowSpan=3 width=120 align=middle>";
							if(userId != 0) {
							//	alert(url);
								new_comment+="<IMG border=0 src=\"" + url + "\">";
							} else {
								new_comment+="<IMG border=0 src=\"images/default/nopic.jpg\">";
							}
							new_comment+="</TD><TD colSpan=4></TD></TR><TR><TD style=\"LINE-HEIGHT: 20px; FONT-SIZE: 12px; OVERFLOW: hidden; PADDING-TOP: 10px\"";
							new_comment+="class=wrap vAlign=top colSpan=4 align=left>";
							new_comment+=content;
							new_comment+="</TD></TR><TR><TD height=40 align=left>评论者："+fromUser+"&nbsp;&nbsp;</TD><TD style=\"COLOR: #999999\" width=160 align=left>";
							new_comment+= "发表时间：" + addTime;
							new_comment+="</TD><TD width=20 align=right></TD><TD width=20 align=right><TD width=30 align=right><DIV class=\"z_f_2 r\">";
							//new_comment+="<A href=\"#\">回复</A>";
							new_comment+="</DIV></TD></TR></TBODY></TABLE>";
							new_comment=new_comment+old_comment;
							document.getElementById("comment_container").innerHTML=new_comment;
						}
			     }
		} 
	}	
	xmlHttp.open("post",url,true);
	xmlHttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xmlHttp.send(args);
}
////////-------------------------发送表单数据库到servlet-------------------------------
//////---------------------------先进行表单验证，验证成功后按需要发送----------------------
function sendFormToServer(formName,servletName){
	if(checkFormByName(formName)){
		sendMessageToServer(servletName,changeFormToQueryString(formName));
	}
}
////----------------------------------将表单数据构造成为一个查询字符串--------------------
function changeFormToQueryString(formName){
	var form=document.forms[formName];
	var args="";
	for(var i=0;i<form.elements.length;i++){
		//将表单中的数据构造成为查询字符串
		if(form.elements[i].type!="button"){
			if(i==0){
				args=form.elements[i].name+"="+form.elements[i].value;
			}else{
				args=args+"&&"+form.elements[i].name+"="+form.elements[i].value;
			}
		}
	}
	return args;
}
//--------------------------获取表单的字段名的值，构造成查询字符串-----------------------
function changeFieldToQueryString(formName,fieldName){
	var args="";
	var form=document.forms[formName];
	args = form.elements[fieldName].name+"="+form.elements[fieldName].value;
	return args;
}
//-------------------------将表单上某个字段的值发送到服务器端，并返回结果------------------
function sendFieldToServer(formName,fieldName,servletName){
	sendMessageToServer(servletName,changeFieldToQueryString(formName,fieldName));
}
//---------------------------------获得下一张图片--------------------------------
function getNextPhoto(url,args) {
	args=args+document.getElementById("photo_id").value;
//	alert('传递的参数是：'+args);
	createXMLHTTP();
		xmlHttp.onreadystatechange=function(){
		if(xmlHttp.readyState==4){
			//数据已经接收成功		
					if(xmlHttp.status==200){
						//请求成功
						var respText = xmlHttp.responseText;
						var json = eval("("+respText+")");
					//	alert(json);
						if(json.lastOne != "lastOne") {
							document.getElementById("getMoreComment").href="GetCommentsServlet?photoId="+json.photo_id;
							document.getElementById("photo_id").value=json.photo_id;
							document.getElementById("photo_url").src=json.photo_url;
							document.getElementById("photo_name").value=json.photo_name;
							document.getElementById("photo_addTime").value=json.photo_addTime;
							document.getElementById("photo_categoryName").value=json.photo_categoryName;
							document.getElementById("photo_skimNum").value=json.photo_skimNum+"次";
							if(json.comments!="null"){
							var root = document.getElementById("tbody");
    						var rowNum=root.rows.length;
    						for (i=0;i<rowNum;i++)
    						{
     						    root.deleteRow(i);
     						    rowNum=rowNum-1;
       					        i=i-1;
     				 	    }
							
							for(var comment in json.comments) {
								var newRow_1 = root.insertRow();
								var newRow_2 = root.insertRow(); 
								var newCell_1_1 = newRow_1.insertCell();
								newCell_1_1.style.cssText="text-align:left";
								var newCell_1_2 = newRow_1.insertCell();
								newCell_1_2.style.cssText="text-align:right";
								var newCell_2_1 = newRow_2.insertCell();
								newCell_2_1.style.cssText="text-align:left";
								newCell_2_1.colSpan="2";
								newCell_1_1.innerHTML="作者大名： "+json.comments[comment].author;
								newCell_1_2.innerHTML="发表时间： "+json.comments[comment].pubTime;
								newCell_2_1.innerHTML=json.comments[comment].content;
							}
		
							}
						} else alert("已经到最后一张了！");
			     }
		} 
	}	
	xmlHttp.open("post",url,true);
	xmlHttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xmlHttp.send(args);
}


//---------------------------------获得下一张图片,以及图片的评论信息--------------------------------
function nextPhoto(albumId) {
	var photoId = document.getElementById("photoId").value;
//alert('photoId='+photoId);
//alert('albumId='+albumId);
//alert('photoId='+photoId+'&&albumId='+albumId);
	createXMLHTTP();
	xmlHttp.onreadystatechange=function(){
		if(xmlHttp.readyState==4){
			if(xmlHttp.status==200){
				var respText = xmlHttp.responseText;
				var json = eval("("+respText+")");
				
				if(json.lastOne=='YES') {
					alert("这已经是本相册的最后一张相片了！");
					return;
				} else {
					//alert(json);
					//alert(document.getElementById("v_1").innerHTML);  //图片名称	
					//alert(document.getElementById("photoId").value);  //图片id	 			
					//alert(document.getElementById("v_3").src);  //图片url
					//alert(document.getElementById("v_4").innerHTML);  //浏览次数   
					//alert(document.getElementById("sayFineCount").innerHTML);  //推荐次数
					//alert(document.getElementById("v_5").innerHTML);   //上传时间
					//alert('新的相片Id号='+json.photo.photoId);
					document.getElementById("v_1").innerHTML = json.photo.name;
					document.getElementById("photoId").value = json.photo.photoId;
					document.getElementById("v_3").src = json.photo.url;
					document.getElementById("v_4").innerHTML = json.photo.skimNum;
					//alert('推荐次数:'+json.photo.fine);
					document.getElementById("sayFineCount").innerHTML = json.photo.fine;
					document.getElementById("v_5").innerHTML = json.photo.addTime;
					if(json.comments=='null') {
						alert('评论为空！');
						document.getElementById("comment_container").innerHTML=""; 
						return;
					} else {
						document.getElementById("comment_container").innerHTML=""; 
						var old_comment = document.getElementById("comment_container").innerHTML;
						for(var pos in json.comments) {
							var userId = json.comments[pos].userId;
							var fromUser = json.comments[pos].fromUser;
							var addTime = json.comments[pos].addTime;
							var content = json.comments[pos].content;
							var userFace = json.comments[pos].url
							var new_comment="<TABLE style=\"BORDER-BOTTOM: #ccc 1px dashed; MARGIN-TOP: 5px\"";
							new_comment+="onmouseover=\'this.style.backgroundColor=\"#F2F5F9\"\'";
							new_comment+="onmouseout=\'this.style.backgroundColor=\"\"\' border=0 width=\"100%\"";
							new_comment+="align=center height=103>";
							new_comment+="<TBODY><TR><TD vAlign=top rowSpan=3 width=120 align=middle>";
							if(userId != 0) {
								new_comment+="<a href=\"showAlbums.action?userId="+userId+"\"><IMG border=0 src=\"" + userFace + "\">";
							} else {
								new_comment+="<IMG border=0 src=\"images/default/nopic.jpg\">";
							}
							new_comment+="</TD><TD colSpan=4></TD></TR><TR><TD style=\"LINE-HEIGHT: 20px; FONT-SIZE: 12px; OVERFLOW: hidden; PADDING-TOP: 10px\"";
							new_comment+="class=wrap vAlign=top colSpan=4 align=left>";
							new_comment+=content;
							new_comment+="</TD></TR><TR><TD height=40 align=left>评论者："+fromUser+"&nbsp;&nbsp;</TD><TD style=\"COLOR: #999999\" width=160 align=left>";
							new_comment+= "发表时间：" + addTime;
							new_comment+="</TD><TD width=20 align=right></TD><TD width=20 align=right><TD width=30 align=right><DIV class=\"z_f_2 r\">";
							new_comment+="<A href=\"#\">回复</A>";
							new_comment+="</DIV></TD></TR></TBODY></TABLE>";
							old_comment=old_comment+new_comment;
						}
						document.getElementById("comment_container").innerHTML = old_comment;
					}
				}	
			}
		} 
	}	
	xmlHttp.open("post",'nextPhoto.action',true);
	xmlHttp.setRequestHeader('Content-type','application/x-www-form-urlencoded');
	xmlHttp.send('photoId='+photoId+'&&albumId='+albumId);
}


//-------------推荐---------------
function sayFine(){
	var v = document.getElementById("photoId").value;
	var n = document.getElementById("photoId").name;
	var args = n+"="+v; 
//	alert(args);
	sendMessageToServer('sayFine.action', args);
}