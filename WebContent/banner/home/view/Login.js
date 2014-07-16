Ext.define('home.view.Login', {
	extend : 'Ext.form.Panel',
	alias : 'widget.login_form',
	title : 'Login',
	maxHeight : 150,
	collapsible : true,
	defaults : {
		 margin: '10 0 0 50'
	},
	initComponent : function() {
		this.items = [ {
			xtype : 'textfield',
			fieldLabel : 'Email',
			name : 'email',
			allowBlank : false,
			vtype : 'email'
		}, {
			xtype : 'textfield',
			fieldLabel : 'Password',
			name : 'password',
			inputType: 'password',
			allowBlank : false
		}];
		this.buttons =  [{
		        text: 'Login As Demo',
		        handler: this.handleDemoLogin,
		        scope : this
		    }, {
		        text: 'Login',
		        formBind: true, //only enabled once the form is valid
		        disabled: true,
		        handler: this.handleLogin,
		        scope : this
		    }],
		this.buttonAlign = 'center';

		this.callParent();
	},
	handleDemoLogin : function(){
		this.down('textfield[name=email]').setValue("demo1@demo.com");
		this.down('textfield[name=password]').setValue("demo");
		this.handleLogin();
	},
	handleLogin : function(){
		var form = this.getForm();
        if (form.isValid()) {
            ajaxService.processRequest('admin','login',{
                success: function(form, action) {
                	var url = document.URL.replace('home.jsp','main.jsp');
                	window.location = url;
                },
                failure: function(form, action) {
                    Ext.Msg.alert('Failed', "Email and password didnot match our records, please re-enter or register ");
                    //form.reset();
                },
                args : this.getForm().getValues(),
                scope : this,
                loadMaskEl : this.getEl(),
                loadMaskMessage : 'Logging in.... Please wait'
            });
        }
	}
});