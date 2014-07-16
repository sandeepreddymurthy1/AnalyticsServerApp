Ext.define('AM.view.Login', {
	extend : 'Ext.form.Panel',
	alias : 'widget.login',
	config : {
		fullscreen : true,
		items : [ {
			xtype : 'toolbar',
			title : 'Login',
			docked : 'top',
			items : [ {xtype : 'spacer'}]
		}, {
			xtype : 'fieldset',
			items : [ {
				xtype : 'textfield',
				name : 'email',
				label : 'Email*',
				allowBlank : false,
				vtype : 'email'
			}, {
				xtype : 'passwordfield',
				name : 'password',
				label : 'Password*',
				inputType: 'password',
				allowBlank : false
			} ]
		},

		{
			xtype : 'toolbar',
			docked : 'bottom',
			items : [{
				xtype : 'button',
				action:'loginAsDemo',
				text : 'Login As Demo'
			}, {
				xtype : 'button',
				text : 'Register'
			}, {
				xtype : 'spacer'
			}, {
				xtype : 'button',
				text : 'Login'
			} ]
		} ]
	},

	initialize : function() {

		this.callParent(arguments);

	}
});