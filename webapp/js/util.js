function onitpage() {
	if(document.getElementById("main").style.ScorlHeight) {
	
	}
}
function openLink(link) {
	window.open(link, "", "width=500,height=300,toolbar=0,menubar=0,scrollbars=0");
}
function jumping(pageForm) {
	pageForm.submit();
	return;
}
function gotoPage(pageNum, pageForm) {
	document.forms(pageForm).jumpPage.value = pageNum;
	document.forms(pageForm).submit();
	return;
}
function setButtonDisable() {
	var submit = document.getElementById("submit");
	var reset = document.getElementById("reset");
	submit.disable = true;
	reset.disable = true;
	//button.disable=true;
}


//－－－－－－－－－－－删除确定－－－－－－－－－－－
function isDelete() {
	return confirm("确定要删除吗？");
}

//////////////////////////////////////////////////////////
////通用验证函数
//////////////////////////////////////////////////////////
//---------------去掉左空格函数-----------------------  
function lTrim(instr) {
	var str = instr + "";
	if (str.length == 0) {
		return str;
	}
	var i = 0;
	while ((i < str.length) && (str.substring(i, i + 1) == " ") && (i < 2000)) {
	 	//从左边查询空格并且计数 
		i++;
	}
	 //返回最后一个空格开始的剩余字符串 
	return str.substring(i, str.length);
}  
//--------------去掉右空格函数-----------------------------  
function rTrim(str) {
	var instr = str + "";
	var last_space;
	var ret;
	last_space = instr.length;
	var loop = 0;
	while ((instr.charAt(last_space - 1) == " ") && (last_space > 0) && (loop < 2000)) {
	  	  //从后向前遍历，除掉右边的空格，到达最后一个不是空格的字符
		loop++;
		last_space--;
	}
	if (last_space == 0) {
		return "";
	} else {
		return instr.substring(0, last_space);
	}
}  
//--------------------去掉左右空格函数----------------------------  
function allTrim(str) {
	return rTrim(lTrim(str));
}
////////////////////////////////////////////////
//////通用表单验证，输入域是否为空，接收表单名字，输入控件的title属性为错误提示信息标志
////////////////////////////////////////////////
function checkFormByName(formName) {
	var form = document.forms[formName];
	for (var i = 0; i < form.elements.length; i++) {
		var input = form.elements[i];
		if (allTrim(input.value).length == 0) {
			//去掉左右两边的空格后
			alert(" [ " + input.title + " ] 不能为空！");
			input.focus();
			return false;
		}
		if (input.type == "password") {
			//如果是密码框
			if (allTrim(input.value).length < 6) {
				alert("密码长度最少为6！");
				input.focus();
				return false;
			}
		}
		if (input.name == "rePassword") {
			//如果有确认密码
			if (allTrim(form.elements["password"].value) != allTrim(form.elements["rePassword"].value)) {
				alert("确认密码和新密码不一致！");
				input.focus();
				return false;
			}
		}
		if (input.name == "email") {
			//如果有邮箱地址
			if (!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(allTrim(form.elements["email"].value))) {
				alert("不是有效的email地址！");
				input.focus();
				return false;
			}
		}
		if(input.name == "status") continue;
	}
	return true;
}
///////////////////////////////////////////////////////
////让整个表单变成只读
////////////////////////////////////////////////////////
function disabledForm(formName) {
	var form = document.forms[formName];
	for (var i = 0; i < form.elements.length; i++) {
		form.elements[i].disabled = true;
	}
}

//控制图片大小css的函数
function changeBig(v) {
		//var d=document.getElementById("imgDiv");
		//d.className="divBig";
	var p = document.getElementById(v.id);
	p.className = "imgBig";
}
function changeSmall(v) {
	var p = document.getElementById(v.id);
	p.className = "imgSmall";
}
//得到单选框的值
function getSelectedValue(formName) {
	var flag = false;
	var form = document.forms[formName];
	for (var i = 0; i < 3; i++) {
		if (form.status[i].checked) {
			form.aStatus.value = form.status[i].value;
			flag = true;
			break;
		}
	}
	if (flag == true) {
		//alert(form.aStatus.value);
		return flag;
	} else {
		alert("操作失败，必须要设置相册权限访问权限！");
		return flag;
	}
}

//js增加一行
function addRow() 

  { 

     var root = document.getElementById("tbody") 

     var allRows = root.getElementsByTagName('tr'); 

     var allCells = allRows[0].getElementsByTagName('td'); 

     var newRow = root.insertRow(); 

     var newCell0 = newRow.insertCell(); 

     var newCell1 = newRow.insertCell(); 

     var newCell2 = newRow.insertCell(); 

     var newCell3 = newRow.insertCell(); 

     newCell0.innerHTML = allCells[0].innerHTML; 

     newCell1.innerHTML = allCells[1].innerHTML; 

     newCell2.innerHTML = allCells[2].innerHTML; 

     newCell3.innerHTML = allCells[3].innerHTML; 

  } 

//删除一行
function removeRow(r) { 

    var root = r.parentNode; 

    root.deleteRow(r); 

  } 
  
 //检查上传文件大小
function CheckPic(newPath) {
	  var s = newPath;
      if(s=="") {
      document.getElementsByName("submit")[0].disabled = true;
		return false;
      }
  	  var lowercase = s.toLowerCase(); 
      var ss = lowercase.split(".");
      if(ss[ss.length-1]!="gif" &&  ss[ss.length-1]!="jpg") {
	  	alert("相片格式错误：只能上传jpg和gif格式的相片！");
	  	document.getElementsByName("submit")[0].disabled = true;
		return false;
	  } else {
	  	document.getElementsByName("submit")[0].disabled = false;
	  	return true;
	  }
  }
  
