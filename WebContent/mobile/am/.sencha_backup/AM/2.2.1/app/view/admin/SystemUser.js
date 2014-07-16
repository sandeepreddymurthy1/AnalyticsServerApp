Ext.define('AM.view.admin.SystemUser', {
	extend : 'Ext.form.Panel',
	alias : 'widget.admin_systemUser',
	config : {
		items : [ {
			xtype : 'fieldset',
			title : 'Personal Preference',
			items : [ {
				xtype : 'field',
				name : 'email',
				label : 'Register Email'
			}, {
				xtype : 'textfield',
				name : 'firstName',
				label : 'First Name'
			}, {
				xtype : 'select',
				name : 'theme',
				label : 'Theme',
				options : [ {
					text : 'Classic',
					value : 'classic'
				}, {
					text : 'Android',
					value : 'android'
				}, {
					text : 'iphone',
					value : 'iphone'
				} ]
			} ]
		}

		 ]
	},

	initialize : function() {

		this.callParent(arguments);

	}
});