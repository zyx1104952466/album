if(typeof(pvhitimgview)=="undefined"){
	var pvhitimgview=true;
	function pv_rport(dm,f) {
	  var i = dm.indexOf(f);
	  if (i > 0) {
	    return  dm.substring(0, i);
	  }
	  return dm;
	}
	
   function getRefUrl(refUrl){
	if (refUrl.indexOf('ref0') > -1){
		var regexstr = /(?:\&|\?)ref0=([\s\S]*?)$/i;
		refUrl = refUrl.match(regexstr);
		refUrl = encodeURI(refUrl[1]);
		return refUrl;
		}
	}
   function getDomain()
   {
		hn=location.hostname;
		str=hn.replace(/\.(com|net|org|cn$)\.?.*/,"");
		if(str.lastIndexOf(".") == -1)
			dm = "." + hn;
		else
		{
			str = str.substring(str.lastIndexOf("."));
			dm = hn.substring(hn.lastIndexOf(str));
		}
		if(dm != ".fengniao.com" && dm != ".3qit.com")
			dm=".zol.com.cn";

		return dm;
   }
	
  function readck(name){
	var cookieValue = "";
	var search_s = name + "=";
   if(document.cookie.length > 0)
   { 
    offset = document.cookie.indexOf(search_s);
    if (offset != -1)
    { 
      offset += search_s.length;
      end = document.cookie.indexOf(";", offset);
      if (end == -1) end = document.cookie.length;
      cookieValue = unescape(document.cookie.substring(offset, end))
    }
   }
   return cookieValue;
  }
  function writeck(name, value, hours)
  {
    var expire = "";
    dm = getDomain();
    if(hours != null)
    {
    expire = new Date((new Date()).getTime() + hours * 3600000);
    expire = "; expires=" + expire.toGMTString();
    }
    document.cookie = name + "=" + escape(value) + expire + ";domain=" + dm + ";path=/; ";
  }
  
  function randck(){
    return Math.floor(Math.random()*256);
  }
	
	var imgsrc='';
	function pv_d(){
		var now = new Date().getTime();
		var pv_userid = readck('zol_userid');
	  	var datestr=escape(now*1000+Math.round(Math.random()*1000));
		    datestr+="befrombj";
		
	  	//增加产品线统计
	  	if(typeof(pv_subcatid)=="undefined")
	  		pv_subcatid=0;
	  	
		if(top.location==self.location){
			imgsrc='http://pv.zol.com.cn/images/pvhit0001.gif?t='+datestr+'&subcat='+pv_subcatid+'&vuserid='+pv_userid+'&'+document.referrer;
			var f=location.href;
			if(f.indexOf("xiyuit.com")>0 || f.indexOf("westd.net")>0 || f.indexOf("westd.com")>0 || f.indexOf("ea3w.com")>0 || f.indexOf("fengniao.com")>0 || f.indexOf("idvd.com.cn")>0 || f.indexOf("51flash.com")>0 || f.indexOf("xiaoshuoku.com.cn")>0 || f.indexOf("5down.com")>0){
				imgsrc='http://pvsite.zol.com.cn/images/pvhit0001.gif?t='+datestr+'&'+document.referrer;
			}
		}else {
			var d=document.referrer+'';
			d=d.substr(7);
			d=pv_rport(d,'/');
			d=pv_rport(d,':');
			
			var refer_str = getRefUrl(document.referrer);

			
			refer_str = (refer_str)?refer_str:document.referrer;
			
			if(d.substr(d.length-10)=='zol.com.cn' || d.substr(d.length-10)=='google.com' || d.substr(d.length-9)=='qihoo.com' || d.substr(d.length-9)=='baidu.com'|| d.substr(d.length-8)=='3721.com' || d.substr(d.length-8)=='1sou.com' || d.substr(d.length-10)=='pku.edu.cn' || d.substr(d.length-16)=='sanhaostreet.com' || d.substr(d.length-11)=='ibox.com.cn')
				imgsrc='http://pv.zol.com.cn/images/pvhit0002.gif?t='+datestr+'&subcat='+pv_subcatid+'&vuserid='+pv_userid+'&'+refer_str;
			else if(d.substr(d.length-10)=='xiyuit.com' || d.substr(d.length-9)=='westd.net' || d.substr(d.length-9)=='westd.com'|| d.substr(d.length-8)=='ea3w.com'|| d.substr(d.length-12)=='fengniao.com' || d.substr(d.length-11)=='idvd.com.cn' || d.substr(d.length-11)=='51flash.com' || d.substr(d.length-17)=='xiaoshuoku.com.cn' || d.substr(d.length-9)=='5down.com')
				imgsrc='http://pvsite.zol.com.cn/images/pvhit0002.gif?t='+datestr+'&'+refer_str;
			else
				imgsrc='';
		}

		if(imgsrc!='')
		{
			var dom = getDomain();
			document.write('<script type="text/javascript" src="http://js'+ dom +'/p.ht?h=&t=&c="></script>');
		}
	}

	pv_d();
}
