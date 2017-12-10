function ol(){
	/*
	var host=document.domain;
	switch(host){
		case 'zol.com.cn':
		case 'www.zol.com.cn':
		case 'mp3.zol.com.cn':
		case 'stat.zol.com.cn':
		case 'labs.zol.com.cn':
		case 'price.zol.com.cn':
			break;
		default:
			return false;
	}*/
	
	try{
		var len=document.all.ol_stat.length;
	}catch(e){
		return false;
	}
	
	var ad_key_list='';
	for(i=0;i<len;i++){
		if(i>0) ad_key_list+='|';
		ad_key_list+=document.all.ol_stat[i].title+'';
	}
	
	if(isNaN(len)){
		try{
			ad_key_list=document.all.ol_stat.title;
		}catch(e){}
	}
	
	var now = new Date().getTime();
	var datestr=escape(now*1000+Math.round(Math.random()*1000));
	s_url='http://stat.zol.com.cn/ol.php?ad_key_list='+ad_key_list+'&t='+datestr;
	document.write('<scr'+'ipt src="'+s_url+'"></scr'+'ipt>');
}
ol();