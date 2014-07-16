Ext.define('home.view.Register', {
	extend : 'Ext.form.Panel',
	alias : 'widget.register_form',
	title : 'Register',
	autoScroll : true,
	maxHeight : 400,
	collapsible : true,
	defaults : {
		 margin: '10 0 0 50'
	},
	initComponent : function() {
		this.items = [ {
			xtype : 'textfield',
			fieldLabel : 'First Name*',
			name : 'firstName',
			allowBlank : false
		}, {
			xtype : 'textfield',
			fieldLabel : 'Last Name*',
			name : 'lastName',
			allowBlank : false
		}, {
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
		}, {
			xtype : 'textfield',
			fieldLabel : 'Retype Password',
			name : 'retypePassword',
			inputType : 'password',
			allowBlank : false
		}, {
			xtype : 'textfield',
			fieldLabel : 'Website',
			name : 'website',
			vtype : 'url'
		}, {
			xtype : 'textfield',
			fieldLabel : 'Company Name',
			name : 'companyName'
		}, {
			xtype : 'textfield',
			fieldLabel : 'Phone Number',
			name : 'phoneNumber'
		}];
		this.buttons =  [{
		        text: 'Reset',
		        handler: function() {
		            this.up('form').getForm().reset();
		        }
		    }, {
		        text: 'Submit',
		        formBind: true, //only enabled once the form is valid
		        disabled: true,
		        handler: function() {
		            var form = this.up('form').getForm();
		            if (form.isValid()) {
		                ajaxService.processRequest('admin','register',{
		                    success: function(form, action) {
		                       Ext.Msg.alert('Success', 'Registration successfull ! Please login.');
		                    },
		                    failure: function(form, action) {
		                        Ext.Msg.alert('Failed', 'Failed to register');
		                    },
		                    loadMaskEl : this.up('form').getEl(),
		                    loadMaskMessage : 'Creating new member profile.',
		                    scope : this,
		                    args : form.getValues()
		                });
		            }
		        }
		    }],
		this.buttonAlign = 'center';

		this.callParent();
		// this.on('render', this.loadFromBackend,this);
	}
});