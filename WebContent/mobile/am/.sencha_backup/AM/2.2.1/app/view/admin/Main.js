Ext.define('AM.view.admin.Main', {
	extend : 'Ext.tab.Panel',
	alias : 'widget.login',
	config : {
		tabBarPosition : 'bottom',
		items : [ {
			xtype : 'toolbar',
			title : 'Admin',
			docked : 'top',
			items : [ {
				xtype : 'button',
				text : 'Back',
				ui : 'back',
				handler : function() {
					this.up('home').showOptionPanel();
				}
			}, {
				xtype : 'spacer'
			} ]
		}, {
			xtype : 'admin_personalInfo'
		}, {
			xtype : 'admin_systemInfo'
		}, {
			xtype : 'admin_systemUser'
		}

		]
	},

	initialize : function() {

		this.callParent(arguments);

	}
});