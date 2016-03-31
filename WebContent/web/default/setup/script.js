$(document).ready(function(){

	// Validate
	// http://bassistance.de/jquery-plugins/jquery-plugin-validation/
	// http://docs.jquery.com/Plugins/Validation/
	// http://docs.jquery.com/Plugins/Validation/validate#toptions
	
		$('#contact-form').validate({
	    rules: {
	      name: {
	        minlength: 2,
	        required: true,
	        NoSBCDot:true
	      },
	      
	      username: {
	    	minlength: 2,
		    required: true,
		    NoSBCDot:true
	      },
	      password: {
	      	minlength: 6,
	        required: true
	      },
	      password2: {
	        minlength: 6,
	        equalTo:"#password", 
	        required: true
	      }
	    },
	    messages: {
	    	   password2: {
	    	    equalTo: "The two passwords you typed do not match."
	    	   }
	    }
	  });
		
	
		$("#createprjbtn").bind("click",function(){
		
			if($('#contact-form').valid()){
	           loading(EELang.loading);
	     	   var paras =  $('#contact-form').serialize();
	     	   paras = paras + "&callType=uf&contextServiceName=DO_ORG_ACCOUNT_Insert_md5";
	    	   ////为了防止多次提交,可以加验证码
	     	   $.post(globalURL + "servicecontroller",paras,
	     			function (data){
	     			    window.location= globalURL + "console.pml?isApp=true";
	     	  });
			}
        });
}); // end document.ready