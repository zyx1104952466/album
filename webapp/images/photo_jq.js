// JavaScript Document

//ʱ���
function key_up(){
	document.getElementById("tishi3").innerHTML ="";	
}
function title_select(pid){
	document.getElementById('title_val_'+pid).select();	
}
function title_select2(pid){
	document.getElementById('des_val_'+pid).select();	
}

function UTCTimeDemo(){
	   var now = new Date().getTime();
	   var datestr=escape(now*1000+Math.round(Math.random()*1000));
	   return datestr;
} 

//�޸���Ƭ��Ϣ
function edit_photo(pid,pic_src){
		
	//���������
	var bg_div = '<div id="bg_div" style= "position:absolute;left:0px;top:0px;filter:alpha(opacity=80);opacity:0.8;z-index:98;width:'+$(document).width()+'px;height:'+$(document).height()+'px;background:#000;" align="center"><iframe src="" style="position:absolute; visibility:inherit; top:0px; left:0px; width:'+$(document).width()+'px; height:'+$(document).height()+'px; z-index:-1;filter=\'progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)\';" frameborder="0"></iframe></div>';
	$('body').append(bg_div);
	
	//��ʾ���Ѿ�������Ժ������
	var offset_height = document.documentElement.clientHeight?document.documentElement.clientHeight:document.body.clientHeight;
	var offset_width = document.documentElement.clientWidth?document.documentElement.clientWidth:document.body.clientWidth;
	var offset_top = document.documentElement.scrollTop?document.documentElement.scrollTop:document.body.scrollTop;
	var top_height = offset_top+(offset_height - 290)/2;
	
	var float_div = '<div id="float" style="position:absolute;left:400px;top:'+top_height+'px;filter:alpha(opacity=100);z-index:99;width:499px;height:490px;background:#ffffff;display:none;overflow:hidden;" align="center"></div>';
	$('body').append(float_div);
	
	//ajax�����������
	var url = "/ajax_edit_photo_info.php?pid="+pid+"&pic="+pic_src+"&u="+UTCTimeDemo();
	$('#float').load(url).ajaxSuccess(
		function(){
			$(this).show();
		}
	);
}
//������Ƭ��Ϣ
function strLen(key){
	var l=escape(key),len
	len=l.length-(l.length-l.replace(/\%u/g,"u").length)*4
	l=l.replace(/\%u/g,"uu")
	len=len-(l.length-l.replace(/\%/g,"").length)*2
	return len;
}

function save_photo(pid){
	
	var title = $('#name').val();
	title = trim(title);
	//���ⲻ�Ϸ��Ĵ���
	if(title==''){
		document.getElementById("tishi1").innerHTML ="<span style=font-size:'12px';color='red';>����д��Ƭ���⡣</span>";
		return false;
	}

	var ablum_id = $('#ablum_id').val();
	var des= $('#des').val();
	var status_value='';
	var status= document.getElementsByName("photo_status");
	for(i=0;i<status.length;i++){
		if(status[i].checked){
			status_value=status[i].value;
		}
	}
	
	des = trim(des);
	if( strLen(des)>200 ){
		document.getElementById("tishi2").innerHTML ="<span style=font-size:'12px';color='red';>��Ƭ�������ܳ���200���ַ���</span>";	
		return false;
	}
	var char_num=strLen(title);
	if(char_num>20){
			document.getElementById('title_'+pid).innerHTML=cut_string(title,20,0);;
		}
	
	var tmp_title=title.replace(' ',"%20");
	
	//��ǩ�ж�
	var add_text = document.getElementById('tag_name');
	add_arr = add_text.value.split(" ");
	if(add_arr.length>5){
		document.getElementById("tishi3").innerHTML ="<span style=font-size:'12px';color='red';>��Ƭ��ǩ��಻�ܳ���5��Ŷ��</span>";	
		return false;
	}
	var tmp_tag=add_text.value.replace(' ',"%20");
	
	//ajax�����������
	var url = "/ajax_edit_photo_info.php?action=save&pid="+pid+"&title="+tmp_title+"&ablum_id="+ablum_id+"&des="+des+"&tag="+tmp_tag+"&photo_status="+status_value+"&u="+UTCTimeDemo();
	$('#float').height("120px");
	$('#float').load(url).ajaxSuccess(
		function(){
			$(this).show();
		}
	);
	document.getElementById('title_'+pid).innerHTML=cut_string(title,20,0);
	$('#pic_'+pid+' a').attr('title',title);
	$('#title_'+pid).attr('avalue',title);
	$('#float_'+pid+'').remove();
	setTimeout("remove_div('float')",2000);
}
//����ո�
function trim(str){
    return rtrim(ltrim(str)); 
}
function ltrim(s){ 
	return s.replace(/(^\s*)/g, "");
} 
 //ȥ�ҿո�; 
function rtrim(s){ 
  return s.replace(/(\s*$)/g, "");
} 

//�༭����
function edit_title(pid,type){
	var title = document.getElementById('title_'+pid);
	var title_all = document.getElementById('title_'+pid);
	var left = getPosition(title).x+1+'px';
	var top  = getPosition(title).y+1+'px';
	var div_width;
	if(1==type) div_width=152;
	if(2==type) div_width=230;
	if(3==type) div_width=300;
	var all_value=$('#title_'+pid).attr('avalue'); 
	//var all_value=title.innerHTML;
	var tmp_value=title.innerHTML;
	title.innerHTML='&nbsp;';
	var float_div = '<div id="as_title_'+pid+'" style="position:absolute;filter:alpha(opacity=100);z-index:10;width:'+div_width+'px;background:#FFFFFF;overflow:hidden;top:'+top+';left:'+left+'"><div><input type="text" style="width:'+(div_width-5)+'px;border:1px solid #C0E292;background:#F8FFEC;" value="'+all_value+'" id="title_val_'+pid+'" maxlength="50"></div><div><input type="button" value="����" onclick="save_title('+pid+',0,'+type+')"><input type="button" value="ȡ��" onclick="remove_div3('+pid+',\''+tmp_value+'\',0)"></div></div>';
	$('body').append(float_div);
	$('#title_val_'+pid)[0].focus();
	
}



//�������
function remove_div3(pid,tmp_value,sign){
	var type=sign?'album_':'title_';
	var title = document.getElementById(type+pid);
	title.innerHTML=tmp_value;
	$('#album_'+pid).show();
	$('#as_title_'+pid+'').remove();
}
function remove_div4(pid,tmp_value){
	var title = document.getElementById("des_"+pid);
	title.innerHTML=tmp_value;
	$('#album_'+pid).show();
	$('#float_'+pid+'').remove();
}

//�༭���
function edit_album_title(pid,type){
	var title = document.getElementById('album_'+pid);
	var left = getPosition(title).x+1+'px';
	var top  = getPosition(title).y+1+'px';
	var div_width;	
	if(1==type) div_width=136;
	if(2==type) div_width=230;	
	var all_value=$('#album_'+pid).attr('avalue');
	var tmp_value=title.innerHTML;	 
	title.innerHTML='&nbsp;';
	
	var float_div = '<div id="as_title_'+pid+'" style="position:absolute;filter:alpha(opacity=100);z-index:10;width:'+div_width+'px;background:#FFFFFF;overflow:hidden;top:'+top+';left:'+left+'"><div><input type="text" style="width:'+(div_width-5)+'px;border:1px solid #C0E292;background:#F8FFEC;" value="'+all_value+'" id="title_val_'+pid+'" maxlength="50"></div><div><input type="button" value="����" onclick="save_title('+pid+',1,'+type+')"><input type="button" value="ȡ��" onclick="remove_div3('+pid+',\''+tmp_value+'\',1)"></div></div>';
	
	
	//$('#album_'+pid).hide();
	$('body').append(float_div);
	$('#title_val_'+pid)[0].focus();
}
//�༭���
function edit_album_des(pid){
	var title = document.getElementById('des_'+pid);
	var left = getPosition(title).x+0+'px';
	var top  = getPosition(title).y+1+'px';
	var tmp_value=title.innerHTML;
	title.innerHTML='&nbsp;';
	var float_div = '<div id="float_'+pid+'" style="position:absolute;filter:alpha(opacity=100);z-index:10;width:340px;background:#ffffff;overflow:hidden;top:'+top+';left:'+left+'"><div><textarea name="" cols="" rows="" id="des_val_'+pid+'" maxlength="255" style="background-color:#FFF9E1;width:335px;border:1px solid #C0E292;background:#F8FFEC;height:50px;">'+tmp_value+'</textarea></div><div><div><input type="button" value="����" onclick="save_des('+pid+',1)"><input type="button" value="ȡ��" onclick="remove_div4('+pid+',\''+tmp_value+'\')"></div></div></div>';
	//$('#des_'+pid).hide();
	$('body').append(float_div);
	if('�����������'==title.innerHTML)title_select(pid);
	$('#title_val_'+pid)[0].focus();
	
}

//�༭��Ƭ
function edit_photo_des(pid){
	var title = document.getElementById('des_'+pid);
	var left = getPosition(title).x+1+'px';
	var top  = getPosition(title).y+1+'px';
	
	var float_div = '<div id="float_'+pid+'" style="position:absolute;filter:alpha(opacity=100);z-index:10;width:340px;background:#ffffff;overflow:hidden;top:'+top+';left:'+left+'"><div><textarea name="" cols="" rows="" id="des_val_'+pid+'" maxlength="255" style="background-color:#FFF9E1;width:335px;border:1px solid #C0E292;background:#F8FFEC;height:50px;">'+title.innerHTML+'</textarea></div><div><div><input type="button" value="����" onclick="save_des('+pid+',0)"><input type="button" value="ȡ��" onclick="remove_div2('+pid+')"></div></div></div>';
	//$('#album_'+pid).hide();
	$('body').append(float_div);
	if('�����������'==title.innerHTML)title_select2(pid);
}
//��������
function save_des(pid,tag){
	
	var title = document.getElementById('des_'+pid);	
	var title_val =document.getElementById('des_val_'+pid).value;
	
	//���ⲻ�Ϸ��Ĵ���
	var char_num=strLen(title_val);
	if(char_num>255){
		//���������
		var bg_div = '<div id="bg_div" style= "position:absolute;left:0px;top:0px;filter:alpha(opacity=80);opacity:0.8;z-index:98;width:'+$(document).width()+'px;height:'+$(document).height()+'px;background:#000;" align="center"><iframe src="" style="position:absolute; visibility:inherit; top:0px; left:0px; width:'+$(document).width()+'px; height:'+$(document).height()+'px; z-index:-1;filter=\'progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)\';" frameborder="0"></iframe></div>';
		$('body').append(bg_div);
		
		//��ʾ���Ѿ�������Ժ������
		var offset_height = document.documentElement.clientHeight?document.documentElement.clientHeight:document.body.clientHeight;
		var offset_width = document.documentElement.clientWidth?document.documentElement.clientWidth:document.body.clientWidth;
		var offset_top = document.documentElement.scrollTop?document.documentElement.scrollTop:document.body.scrollTop;
		var top_height = offset_top+(offset_height - 290)/2;
		
		var float_div = '<div id="float" style="position:absolute;left:500px;top:'+top_height+'px;filter:alpha(opacity=100);z-index:99;width:300px;height:120px;background:#ffffff;display:none;overflow:hidden;" align="center"></div>';
		$('body').append(float_div);
		//ajax�����������
		var url = "/ajax_edit_photo_info.php?action=desc_error&tag="+tag+"&title="+title_val+"&u="+UTCTimeDemo();
		$('#float').load(url).ajaxSuccess(
			function(){
				$(this).show();
			}
		);
		return;
	}
	//����HTML��ǩ
	var regEx = /<[^>]*>/g;
    title_val=title_val.replace(regEx,"");
	title.innerHTML=title_val;
	var tmp_title=title_val.replace(' ',"%20");
	if(tag==1){
		var url = "/ajax_edit_photo_info.php?action=album_desc&pid="+pid+"&des="+tmp_title+"&u="+UTCTimeDemo();
	}
	if(tag==0){
		var url = "/ajax_edit_photo_info.php?action=photo_desc&pid="+pid+"&des="+tmp_title+"&u="+UTCTimeDemo();
		//alert(url);
	}
	$('float').load(url).ajaxSuccess(
		function(){
			$(this).show();
		}
	);
	remove_div2(pid);
}


//�������
function save_title(pid,tag,type){
	if(tag==0){
		var title = document.getElementById('title_'+pid);	
	}
	if(tag==1){
		var title = document.getElementById('album_'+pid);	
	}
	var title_val =document.getElementById('title_val_'+pid).value;
	title_val = trim(title_val);
	//���ⲻ�Ϸ��Ĵ���
	var char_num=strLen(title_val);
	if(title_val=='' || char_num>50){
		//���������
		var bg_div = '<div id="bg_div" style= "position:absolute;left:0px;top:0px;filter:alpha(opacity=80);opacity:0.8;z-index:98;width:'+$(document).width()+'px;height:'+$(document).height()+'px;background:#000;" align="center"><iframe src="" style="position:absolute; visibility:inherit; top:0px; left:0px; width:'+$(document).width()+'px; height:'+$(document).height()+'px; z-index:-1;filter=\'progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)\';" frameborder="0"></iframe></div>';
		$('body').append(bg_div);
		
		//��ʾ���Ѿ�������Ժ������
		var offset_height = document.documentElement.clientHeight?document.documentElement.clientHeight:document.body.clientHeight;
		var offset_width = document.documentElement.clientWidth?document.documentElement.clientWidth:document.body.clientWidth;
		var offset_top = document.documentElement.scrollTop?document.documentElement.scrollTop:document.body.scrollTop;
		var top_height = offset_top+(offset_height - 290)/2;
		
		var float_div = '<div id="as_title_'+pid+'" style="position:absolute;left:500px;top:'+top_height+'px;filter:alpha(opacity=100);z-index:99;width:400px;height:120px;background:#ffffff;display:none;overflow:hidden;" align="center"></div>';
		$('body').append(float_div);
		//ajax�����������
		var url = "/ajax_edit_photo_info.php?action=title_error&tag="+tag+"&title="+title_val+"&pid="+pid+"&u="+UTCTimeDemo();
		$('#float').height("120px");
		$('#float').width("400px");
		$('#float').load(url).ajaxSuccess(
			function(){
				$(this).show();
			}
		);
		return;
	}

	//����HTML��ǩ
	var regEx = /<[^>]*>/g;
    title_val=title_val.replace(regEx,"");
	title.innerHTML=cut_string(title_val,20,0);
	var tmp_title=title_val.replace(' ',"%20");
	if(tag==0){
		var url = "/ajax_edit_photo_info.php?action=title&pid="+pid+"&title="+tmp_title+"&u="+UTCTimeDemo();
	}
	if(tag==1){
		var url = "/ajax_edit_photo_info.php?action=album_title&pid="+pid+"&title="+tmp_title+"&u="+UTCTimeDemo();
		if(type==2){
			document.getElementById('album_name').innerHTML=cut_string(title_val,20,0);	
			document.getElementById('album_name_title').innerHTML=cut_string(title_val,20,0);
		}
	}
	$('#pic_'+pid+' a').attr('title',title_val);
	$('#title_'+pid).attr('avalue',title_val);
	$('#album_'+pid).attr('avalue',title_val);
	$('#float').load(url).ajaxSuccess(
		function(){
			$(this).show();
		}
	);
	$('#as_title_'+pid+'').remove();	
	//remove_div2(pid);
}
//��ȡ���
function get_onfocus(pid){
	document.getElementById('title_val_'+pid).focus();
}

//ɾ����Ƭ
function del_photo(pid){
		
	//���������
	var bg_div = '<div id="bg_div" style= "position:absolute;left:0px;top:0px;filter:alpha(opacity=80);opacity:0.8;z-index:98;width:'+$(document).width()+'px;height:'+$(document).height()+'px;background:#000;" align="center"><iframe src="" style="position:absolute; visibility:inherit; top:0px; left:0px; width:'+$(document).width()+'px; height:'+$(document).height()+'px; z-index:-1;filter=\'progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)\';" frameborder="0"></iframe></div>';
	$('body').append(bg_div);
	
	//��ʾ���Ѿ�������Ժ������
	var offset_height = document.documentElement.clientHeight?document.documentElement.clientHeight:document.body.clientHeight;
	var offset_width = document.documentElement.clientWidth?document.documentElement.clientWidth:document.body.clientWidth;
	var offset_top = document.documentElement.scrollTop?document.documentElement.scrollTop:document.body.scrollTop;
	var top_height = offset_top+(offset_height - 290)/2;
	
	var float_div = '<div id="float" style="position:absolute;left:500px;top:'+top_height+'px;filter:alpha(opacity=100);z-index:99;width:300px;height:120px;background:#ffffff;display:none;overflow:hidden;" align="center"></div>';
	$('body').append(float_div);
	
	//ajax�����������
	var url = "/ajax_edit_photo_info.php?action=del&pid="+pid+"&u="+UTCTimeDemo();
	$('#float').height("120px");
	$('#float').width("400px");
	$('#float').load(url).ajaxSuccess(
		function(){
			$(this).show();
		}
	);

}
//ɾ������
function del_comment(pid,pic_id){
		
	//���������
	var bg_div = '<div id="bg_div" style= "position:absolute;left:0px;top:0px;filter:alpha(opacity=80);z-index:98;width:'+$(document).width()+'px;height:'+$(document).height()+'px;background:#000;" align="center"><iframe src="" style="position:absolute; visibility:inherit; top:0px; left:0px; width:'+$(document).width()+'px; height:'+$(document).height()+'px; z-index:-1;filter=\'progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)\';" frameborder="0"></iframe></div>';
	$('body').append(bg_div);
	
	//��ʾ���Ѿ�������Ժ������
	var offset_height = document.documentElement.clientHeight?document.documentElement.clientHeight:document.body.clientHeight;
	var offset_width = document.documentElement.clientWidth?document.documentElement.clientWidth:document.body.clientWidth;
	var offset_top = document.documentElement.scrollTop?document.documentElement.scrollTop:document.body.scrollTop;
	var top_height = offset_top+(offset_height - 290)/2;
	
	var float_div = '<div id="float" style="position:absolute;left:500px;top:'+top_height+'px;filter:alpha(opacity=100);z-index:99;width:400px;height:120px;background:#ffffff;display:none;overflow:hidden;" align="center"></div>';
	$('body').append(float_div);
	
	//ajax�����������
	var url = "/ajax_edit_photo_info.php?action=del_comment&pid="+pid+"&pic_id="+pic_id+"&u="+UTCTimeDemo();
	$('#float').load(url).ajaxSuccess(
		function(){
			$(this).show();
		}
	);

}

//�༭����
function edit_comment(pid,pic_id){
		
	//���������
	var bg_div = '<div id="bg_div" style= "position:absolute;left:0px;top:0px;filter:alpha(opacity=80);z-index:98;width:'+$(document).width()+'px;height:'+$(document).height()+'px;background:#000;" align="center"><iframe src="" style="position:absolute; visibility:inherit; top:0px; left:0px; width:'+$(document).width()+'px; height:'+$(document).height()+'px; z-index:-1;filter=\'progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)\';" frameborder="0"></iframe></div>';
	$('body').append(bg_div);
	
	//��ʾ���Ѿ�������Ժ������
	var offset_height = document.documentElement.clientHeight?document.documentElement.clientHeight:document.body.clientHeight;
	var offset_width = document.documentElement.clientWidth?document.documentElement.clientWidth:document.body.clientWidth;
	var offset_top = document.documentElement.scrollTop?document.documentElement.scrollTop:document.body.scrollTop;
	var top_height = offset_top+(offset_height - 290)/2;
	
	var float_div = '<div id="float" style="position:absolute;left:500px;top:'+top_height+'px;filter:alpha(opacity=100);z-index:99;width:500px;height:200px;background:#ffffff;display:none;overflow:hidden;" align="center"></div>';
	$('body').append(float_div);
	
	//ajax�����������
	var url = "/ajax_edit_photo_info.php?action=edit_comment&pid="+pid+"&pic_id="+pic_id+"&u="+UTCTimeDemo();
	$('#float').load(url).ajaxSuccess(
		function(){
			$(this).show();
		}
	);

}

function save_del_photo(pid,album_id){
	//ajax�����������
	var url = "/ajax_edit_photo_info.php?action=del_save&pid="+pid+"&u="+UTCTimeDemo();
	$('#float').load(url).ajaxSuccess(
		function(){
			$(this).show();
			setTimeout("remove_div('float')",2000);
		}
	);
	if(album_id){
		var userid=readCookie('zol_userid');
		location.href="/photo_"+userid+"_"+album_id+"_1.html";
	}else{
		window.location.reload();
	}
}
function save_del_comment(pid,pic_id){
	//ajax�����������
	var url = "/ajax_edit_photo_info.php?action=del_comment_save&pid="+pid+"&pic_id="+pic_id+"&u="+UTCTimeDemo();
	$('#float').load(url).ajaxSuccess(
		function(){
			$(this).show();
			setTimeout("remove_div('float')",2000);
		}
	);
	window.location.reload();
}

function save_edit_comment(pid,pic_id){
	//ajax�����������
	
	var url = "/ajax_edit_photo_info.php?action=edit_comment_save&pid="+pid+"&pic_id="+pic_id+"&u="+UTCTimeDemo();
	$('#float').load(url).ajaxSuccess(
		function(){
			$(this).show();
			setTimeout("remove_div('float')",2000);
		}
	);
	window.location.reload();
}

function getPosition(e){
  var left = 0;
  var top  = 0;
  while (e.offsetParent){
    left += e.offsetLeft;
    top  += e.offsetTop;
    e     = e.offsetParent;
  }
  left += e.offsetLeft;
  top  += e.offsetTop;
  return {x:left, y:top};
}

function set_page_bg(){
	//���������
	var bg_div = '<div id="bg_div" style= "position:absolute;left:0px;top:0px;filter:alpha(opacity=80);opacity:0.8;z-index:98;width:'+$(document).width()+'px;height:'+$(document).height()+'px;background:#000;" align="center"><iframe src="" style="position:absolute; visibility:inherit; top:0px; left:0px; width:'+$(document).width()+'px; height:'+$(document).height()+'px; z-index:-1;filter=\'progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)\';" frameborder="0"></iframe></div>';
	$('body').append(bg_div);
	
	//��ʾ���Ѿ�������Ժ������
	var offset_height = document.documentElement.clientHeight?document.documentElement.clientHeight:document.body.clientHeight;
	var offset_width = document.documentElement.clientWidth?document.documentElement.clientWidth:document.body.clientWidth;
	var offset_top = document.documentElement.scrollTop?document.documentElement.scrollTop:document.body.scrollTop;
	var top_height = offset_top+(offset_height - 290)/2;
	
	var float_div = '<div id="float" style="position:absolute;left:320px;top:'+top_height+'px;filter:alpha(opacity=100);z-index:99;width:530px;height:300px;background:#ffffff;display:none;overflow:hidden;" align="center"></div>';
	$('body').append(float_div);
}


//�޸����������Ϣ
function edit_ablum(pid){
	
	//���������
	set_page_bg();
	
	//ajax�����������
	var url = "/ajax_edit_ablum_info.php?pid="+pid+"&u="+UTCTimeDemo();
	$('#float').load(url).ajaxSuccess(
		function(){
			$(this).show();
		}
	);
}
//�������
function remove_div2(pid){	
	$('#album_'+pid).show();
	$('#float_'+pid+'').remove();
}
//�������
function remove_div(div_id){
	$('#bg_div').remove();
	$('#'+div_id+'').remove();	
}
function url_encode(unencodedValue)
{   
	// The Javascript escape and unescape functions do not correspond
	// with what browsers actually do...
	var SAFECHARS = "0123456789" +					// Numeric
					"ABCDEFGHIJKLMNOPQRSTUVWXYZ" +	// Alphabetic
					"abcdefghijklmnopqrstuvwxyz" +
					"-_.!~*'()";					// RFC2396 Mark characters
	var HEX = "0123456789ABCDEF";

	var plaintext = unencodedValue;
	var encoded = "";
	for (var i = 0; i < plaintext.length; i++ ) {
		var ch = plaintext.charAt(i);
	    if (ch == " ") {
		    encoded += "+";				// x-www-urlencoded, rather than %20
		} else if (SAFECHARS.indexOf(ch) != -1) {
		    encoded += ch;
		} else {
		    var charCode = ch.charCodeAt(0);
			if (charCode > 255) {
			    /*alert( "Unicode Character '" 
                        + ch 
                        + "' cannot be encoded using standard URL encoding.\n" +
				          "(URL encoding only supports 8-bit characters.)\n" +
						  "A space (+) will be substituted." );*/
				encoded += ch;
			} else {
				encoded += "%";
				encoded += HEX.charAt((charCode >> 4) & 0xF);
				encoded += HEX.charAt(charCode & 0xF);
			}
		}
	} // for

	return encoded;  
}   
//�޸���Ƭ�������Ϣ
function del_ablum(pid){
	//���������
	set_page_bg();
	
	//ajax�����������
	var url = "/ajax_edit_ablum_info.php?action=del&pid="+pid+"&u="+UTCTimeDemo();
	$('#float').load(url).ajaxSuccess(
		function(){
			$(this).show();
		}
	);
}

//���������Ϣ
function save_ablum(pid){
	
	var title = $('#title').val();
	title = trim(title);
	//���ⲻ�Ϸ��Ĵ���
	var char_num=strLen(title);
	if(char_num>50){
		document.getElementById("tishi3").innerHTML ="<span style=font-size:'12px';color='red';>���������Ϊ1��50���ַ���</span>";
		return false;
	}
	var description= $('#description').val();
	des = trim(description);
	if( strLen(description)>200 ){
		document.getElementById("tishi4").innerHTML ="<span style=font-size:'12px';color='red';>����������ܳ���200���ַ���</span>";	
		return false;	
	}
	
	var class_sign = '';
	var  tmp = document.getElementsByName("class_id");
	var sign=false;
	for(i=0;i<tmp.length;i++){ 
		if(tmp[i].checked){
			class_sign = tmp[i].value;
			sign=true;
		}
	}
	if(sign==false){
		document.getElementById("tishi5").innerHTML ="<span style=font-size:'12px';color='red';>׼ȷ�ķ������ø������ѿ���������ƬŶ��</span>";
		return false;
	}

	var class_id = $('#class_id').val();
	var des= $('#description').val();
	var place_province_id = $('#place_province_id').val();
	var place_name= $('#place_name').val();
	
	document.getElementById('album_'+pid).innerHTML=cut_string(title,20,0);
	var tmp_title=title.replace(' ',"%20");
	//ajax�����������
	var url = "/ajax_edit_ablum_info.php?action=save&place_name="+place_name+"&place_province_id="+place_province_id+"&pid="+pid+"&title="+url_encode(tmp_title)+"&class_id="+class_sign+"&des="+url_encode(des)+"&u="+UTCTimeDemo();
	$('#float').height("120px");
	$('#float').load(url).ajaxSuccess(
		function(){
			$(this).show();
		}
	);
	document.getElementById('album_'+pid).innerHTML=cut_string(title,20,0);
	$('#pic_'+pid+' a').attr('title',title);
	$('#album_'+pid).attr('avalue',title);
	$('#float_'+pid+'').remove();
	setTimeout("remove_div('float')",2000);
}
//������Ƭ����
function set_photo(pid,photo_id,tmp_pic){

	/*//ajax�����������
	var url = "/ajax_edit_ablum_info.php?action=set_photo&pid="+pid+"&photoid="+photo_id+"&u="+UTCTimeDemo();
	$('#float').height("120px");
	$('#float').width("400px");
	$('#float').load(url).ajaxSuccess(
		function(){
			$(this).show();
		}
	);*/
	var time =UTCTimeDemo();
	$.post("/ajax_edit_ablum_info.php", {action:"set_photo",pid: pid,photoid:photo_id,u:time}, 
	   function(data){
			alert_error(data,tmp_pic);
	   } 
	 );	
	
}


//ɾ�������Ϣ
function save_del_ablum(pid,type){
	
	var del_obj = document.getElementsByName('del_type');
	var ablum_id = document.getElementById('ablum_id').value;
	for(i=0;i<del_obj.length;i++){ 
		if(del_obj[i].checked){
			var type1 = del_obj[i].value;
		}
	}
	//ajax�����������
	var url = "/ajax_edit_ablum_info.php?action=del_save&pid="+pid+"&del_type="+type1+"&ablum_id="+ablum_id+"&u="+UTCTimeDemo();
	$('#float').load(url).ajaxSuccess(
		function(){
			$(this).show();
			setTimeout("remove_div('float')",2000);
		}
	);
	if(type==2){
		var userid=readCookie('zol_userid');
		location.href="/"+userid+"/album_1.html";
	}else{
		window.location.reload();
	}
}

//cookie functions
function readCookie(name){
  var cookieValue = '';
  var search = name + '=';
  if(document.cookie.length > 0)
  { 
    offset = document.cookie.indexOf(search);
    if (offset != -1)
    { 
      offset += search.length;
      end = document.cookie.indexOf(';', offset);
      if (end == -1) end = document.cookie.length;
      cookieValue = unescape(document.cookie.substring(offset, end))
    }
  }
  return cookieValue;
}

function writeCookie(name, value, hours){
  var expire = '';
  if(hours != null){
    expire = new Date((new Date()).getTime() + hours * 3600000);
    expire = '; expires=' + expire.toGMTString();
  }
  document.cookie = name + "=" + escape(value) + expire + "; path=/; domain=.zol.com.cn;";

}


//����ͼƬ��ַ
function copyurl(userid){
    clipboardData.setData("Text","http://photo2.zol.com.cn/"+userid+"/");
    alert("���Ƴɹ�������ճ����MSN��QQ�и�����������ǣ�") ;                   
}
function copyToClipboard(txt){ 
    if(window.clipboardData) { 
        window.clipboardData.clearData(); 
        window.clipboardData.setData("Text", txt); 
    } 
    else if(navigator.userAgent.indexOf("Opera") != -1)  { 
        window.location = txt; 
    }else if (window.netscape){ 
        try{ 
            netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect"); 
        }catch (e){ 
            alert("����firefox��ȫ�������������м�������������ڵ�ַ�������롰about:config������signed.applets.codebase_principal_support������Ϊ��true��֮������"); 
            return false; 
        } 
        var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard); 
        if (!clip) 
          return; 
        var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable); 
        if (!trans) 
          return; 
        trans.addDataFlavor('text/unicode'); 
        var str = new Object(); 
        var len = new Object(); 
        var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString); 
        var copytext = txt; 
        str.data = copytext; 
        trans.setTransferData("text/unicode",str,copytext.length*2); 
        var clipid = Components.interfaces.nsIClipboard; 
        if (!clip) 
          return false; 
        clip.setData(trans,null,clipid.kGlobalClipboard); 
    } 
	return true;
}
function AddToFavorite(title,url,desc){
     if ((typeof window.sidebar == 'object') && (typeof window.sidebar.addPanel == 'function'))
     {
         window.sidebar.addPanel(title,url,desc);
     }
     else//IE
     {
         window.external.AddFavorite(url,title);
     }
} 
//����С��ʿcookie
function prompt_title (){	

	document.getElementById("tishi").style.display = "none";
}

function cut_string(str, len, hasDot) 
{ 
    var newLength = 0; 
    var newStr = ""; 
    var chineseRegex = /[^\x00-\xff]/g; 
    var singleChar = ""; 
    var strLength = str.replace(chineseRegex,"**").length; 
    for(var i = 0;i < strLength;i++) 
    { 
        singleChar = str.charAt(i).toString(); 
        if(singleChar.match(chineseRegex) != null) 
        { 
            newLength += 2; 
        }     
        else 
        { 
            newLength++; 
        } 
        if(newLength > len) 
        { 
            break; 
        } 
        newStr += singleChar; 
    } 
     
    if(hasDot && strLength > len) 
    { 
        newStr += "..."; 
    } 
    return newStr; 
} 

//������ʾ
function alert_error(type,tmp_pic){
	var msg,title;
	title = '����������';
	if(type == 2){
		msg = "�ܱ�Ǹ!����ƬΪ˽����Ƭ��������Ϊ����!";
		
	}else if(type == 3){
		msg = "�ܱ�Ǹ!����ƬΪ���ѿɼ���Ƭ��������Ϊ����!";
	}else if(type == 4){
		msg = "���������óɹ�!";
	}else{
		msg = "���Ƴɹ�������ճ����MSN��QQ�и�����������ǣ�";
		title = "���Ƴɹ�";
	}

	var bg_div = '<div id="bg_div" style= "position:absolute;left:0px;top:0px;filter:alpha(opacity=80);opacity:0.8;z-index:98;width:'+$(document).width()+'px;height:'+$(document).height()+'px;background:#000;" align="center"><iframe src="" style="position:absolute; visibility:inherit; top:0px; left:0px; width:'+$(document).width()+'px; height:'+$(document).height()+'px; z-index:-1;filter=\'progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)\';" frameborder="0"></iframe></div>';
	$('body').append(bg_div);		
	
	
	var offset_height = document.documentElement.clientHeight?document.documentElement.clientHeight:document.body.clientHeight;
	var offset_width = document.documentElement.clientWidth?document.documentElement.clientWidth:document.body.clientWidth;
	var offset_top = document.documentElement.scrollTop?document.documentElement.scrollTop:document.body.scrollTop;
	var top_height = offset_top+(offset_height - 290)/2;


	var float_div = '<div id="float" style="position:absolute;left:400px;top:'+top_height+'px;filter:alpha(opacity=100);z-index:99;width:400px;height:120px;background:#ffffff;overflow:hidden;" align="center"><table width="400" border="0" align="center" cellpadding="0" cellspacing="0"><tr><td height="25" bgcolor="#709CD2"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0"><tr><td style="color:#FFF;font-size:14px;font-weight:bold;" align="left">'+title+'</td><td align="right"><img src="http://icon.zol.com.cn/photo/jbn1.gif" width="16" height="16" style="float:none;cursor:hand" onClick=remove_div("float")></td></tr></table></td></tr><tr><td  style="padding-left:20px;text-align:left;height:50px;font-size:14px;color:#000">'+msg+'</td></tr></table></div>';
	$('body').append(float_div);
	
	if(type == 4){
		var tmp_pic=tmp_pic.replace('0240','0120');
		var tmp_pic_s= tmp_pic.replace('0120','0120');
		var album_obj = document.getElementById('album_pic');	
		album_obj.src=tmp_pic;
		album_obj.src=tmp_pic_s;
	}
	setTimeout("remove_div('float')",2000);
	return;	
	
}

/* place recommendations start */
var last_place_province = 0;
function change_recommendations(v)
{
    if (v != last_place_province && document.getElementById('placeList'))
    {
        last_place_province = v;
        var url = "http://photo.zol.com.cn/ajax_get_place_recommendations.php?pid=" + v; 
		$('#placeList').load(url);
    }
}


function place_recommendations(o) {
    var p = getXY(o);
    var id = document.getElementById('place_province_id').value;
    var tipNameSpaceURI = "http://www.w3.org/1999/xhtml";
    var tipContainer = document.getElementById('place_recommendations');
    if (o.value == '')
    {
        if (!tipContainer)
        {
            var html = '<div class="innerBox">';
                html += '<span class="title">��ȥ����</span>';
                html += '<a class="close"';
                html += 'onclick="document.getElementById(\'place_recommendations\').style.display=\'none\';" ';
                html += 'href="javascript:void(0);">��</a>';
                html += '<div id="placeList"></div></div>';
            tipContainer = document.createElementNS
                ? document.createElementNS(tipNameSpaceURI, "div")
                : document.createElement("div");
            tipContainer.setAttribute("id", 'place_recommendations');
            tipContainer.innerHTML = html;
            document.getElementsByTagName("body").item(0).appendChild(tipContainer);
        }
		change_recommendations(id);
        tipContainer.style.top = (p.y) + 'px';
        tipContainer.style.left = (p.x) + 'px';
        tipContainer.style.display = 'block';

    }
    else
    {
		if (tipContainer)
		{
			tipContainer.style.display = 'block';
			/*if (id == last_place_province)
			{
				tipContainer.style.display = 'block';
			}
			else
			{
				tipContainer.style.display = 'none';
			}*/
		}
    }
}
function choose_place(p)
{ 
	document.getElementById('place_recommendations').style.display = 'none';
	var obj = document.getElementById('place_name'); 
	obj.value = p; 
}

function getXY(obj)
{
  var curleft = 0;
  var curtop = obj.offsetHeight + 5;
  var border;
  if (obj.offsetParent)
  {
    do
    {
      if (obj.style.position == 'relative')
      {
        if (border = obj.style.borderTopWidth) curtop += parseInt(border);
        if (border = obj.style.borderLeftWidth) curleft += parseInt(border);
      }
      curleft += obj.offsetLeft;
      curtop += obj.offsetTop;
    }
    while (obj = obj.offsetParent)
  }
  else if (obj.x)
  {
    curleft += obj.x;
    curtop += obj.y;
  }
  return {'x': curleft, 'y': curtop};
}
/* place recommendations end */
