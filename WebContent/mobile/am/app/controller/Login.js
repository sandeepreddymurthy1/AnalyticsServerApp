Ext.define('AM.controller.Login', {
	extend : 'Ext.app.Controller',
	config : {
		refs : {
			loginView : 'login',
			mainView : 'main',
			dashboard : 'dashboard'
		},
		control : {
			'login button[text=Login]' : {
				tap : 'loginClicked'
			}
		, 
			'login button[text=Register]' : {
				tap : 'registerClicked'
			
		}, 
		'login button[action=loginAsDemo]' : {
				tap : 'demoClicked'
		
	}
		}
	},
	init : function() {
		this.callParent(arguments);
		console.log('Login Controller Initialized');
		
	},

	loginClicked : function() {
		
		var rec = this.getLoginView().getValues();
		ajaxService.processRequest('admin','login',{
            success: function(data) {
            	if( data ){
            		this.getMainView().showHome();
            	}else{
            		 Ext.Msg.alert('Failed', "Email and password didnot match our records, please re-enter or register ");
                     
            	}
            	
            },

            failure : function(data){
           	 Ext.Msg.alert('Failed', "Email and password didnot match our records, please re-enter or register ");
                
            },
            args : rec,
            scope : this,
            loadMaskEl : Ext.getBody(),
            loadMaskMessage : 'Logging in.... Please wait'
        });
	},
	registerClicked : function() {
		var url = document.URL.replace('main.jsp','home.jsp');
		url = url + "?deepLink=Register"
    	window.location = url;
	},
	demoClicked : function(){
		 ajaxService.processRequest('admin','login',{
             success: function(data) {
             	if( data ){
             		this.getMainView().showHome();
             	}else{
             		 Ext.Msg.alert('Failed', "Email and password didnot match our records, please re-enter or register ");
                      
             	}
             	
             },
             failure : function(data){
            	 Ext.Msg.alert('Failed', "Email and password didnot match our records, please re-enter or register ");
                 
             },
             args : { email : 'demo1@demo.com', password : 'demo'},
             scope : this,
             loadMaskEl : Ext.getBody(),
             loadMaskMessage : 'Logging in.... Please wait'
         });
	}

});