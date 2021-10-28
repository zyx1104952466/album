
//Gets the browser specific XmlHttpRequest Object
function getXmlHttpRequestObject() {
	if (window.XMLHttpRequest) {
		return new XMLHttpRequest();
	} else if(window.ActiveXObject) {
		return new ActiveXObject("Microsoft.XMLHTTP");
	} else {
		alert("Your Browser Sucks!\nIt's about time to upgrade don't you think?");
	}
}

function createAjaxObj(){
  var httprequest=false
  if (window.XMLHttpRequest)
  { // if Mozilla, Safari etc
    httprequest=new XMLHttpRequest()
    if (httprequest.overrideMimeType)
      httprequest.overrideMimeType('text/xml')
   }
   else if (window.ActiveXObject)
   { // if IE
     try {
       httprequest=new ActiveXObject("Msxml2.XMLHTTP");
     }
     catch (e){
       try{
          httprequest=new ActiveXObject("Microsoft.XMLHTTP");
       }
       catch (e){}
     }
   }
   return httprequest
}
//Our XmlHttpRequest object to get the auto suggest
var searchReq = createAjaxObj();

//Called from keyup on the search textbox.
//Starts the AJAX request.

function searchSuggest(keyword) {
	if (searchReq.readyState == 4 || searchReq.readyState == 0) {
		if(keyword == '') return;
		var searchType = 1;
		var form = document.forms["search"];
		for (var i = 0; i < 3; i++) {
			if (form.status[i].checked) {
				searchType = form.status[i].value;
				break;
			}
		}
		keyword = allTrim(keyword);
		var args = "keyword="+keyword+"&&searchType="+searchType;
		searchReq.onreadystatechange = handleSearchSuggest;
		searchReq.open("post","searchSuggest.action",true);
		searchReq.setRequestHeader('Content-type','application/x-www-form-urlencoded');
		searchReq.send(args);
	}
}

var str;
//Called when the AJAX response is returned.
function handleSearchSuggest() {
	if (searchReq.readyState == 4) {
		var ss = document.getElementById('search_suggest')
		ss.innerHTML = '';
		ss.style.visibility = "visible";
		str = searchReq.responseText.split("$$");
		positionDiv();
		for(i=0; i < str.length - 1; i++) {
			//Build our element string.  This is cleaner using the DOM, but
			//IE doesn't support dynamically added attributes.
			var suggest = '<div onmouseover="javascript:suggestOver(this);" ';
			suggest += 'onmouseout="javascript:suggestOut(this);" ';
			suggest += 'onclick="javascript:setSearch(this.innerHTML);" ';
			suggest += 'class="suggest_link">' + str[i] + '</div>';
			ss.innerHTML += suggest;
		}
	}
}

function positionDiv()
{
      var DivRef = document.getElementById("search_suggest");
      DivRef.style.position = "absolute";
      var t = document.getElementById("kword");
      DivRef.style.top = getAbsolutePos(t).y;     //相对文本框的TOP高度，方法见下面
      DivRef.style.left = getAbsolutePos(t).x ;   //相对文本框的LEFT宽度
      DivRef.style.height = str.length * 10;      //DIV的高度等于每行20个象素乘行数（也就是数组的长度，体现全局数组的作用，不然要传入数组长度的参数）
}
        
function getAbsolutePos(el)
{
      var SL = 0, ST = 0;
      var is_div = /^div$/i.test(el.tagName);
      if (is_div && el.scrollLeft) SL = el.scrollLeft;
      if (is_div && el.scrollTop)  ST = el.scrollTop;
      var r = { x: el.offsetLeft - SL, y: el.offsetTop - ST };
      if (el.offsetParent)
      {
          var tmp = this.getAbsolutePos(el.offsetParent);
          r.x += tmp.x;
          r.y += tmp.y;
          r.y += 25;
      }
      return r;
}

//Mouse over function
function suggestOver(div_value) {
	div_value.className = 'suggest_link_over';
}
//Mouse out function
function suggestOut(div_value) {
	div_value.className = 'suggest_link';
}
//Click function
function setSearch(value) {
	var t = value.substr(50);
	var tt = t.split('<');
	document.getElementById('kword').value = tt[0];
	document.getElementById("search_suggest").style.visibility = "hidden";
}

function goSearch() {
	var keyword = document.getElementById('kword').value;
	keyword = allTrim(keyword);
	//alert(keyword);
	if(keyword == '') {
		document.getElementById('kword').value = '请输入搜索关键字！';
		return;
	} else {
	var form = document.forms["search"];
		for (var i = 0; i < 3; i++) {
			if (form.status[i].checked) {
				form.searchType.value = form.status[i].value;
				break;
			}
		}
		document.search.submit();
		
	}
}
function Keysubmit(){ 
	if (event.keyCode == 13){ 
		event.returnValue = false; 
		event.cancel = true; 
		document.getElementById('btn_search').click(); 
	} 
} 
