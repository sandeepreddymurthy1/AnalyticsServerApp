Ext.define('AM.view.Main', {
	extend : 'Ext.Panel',
	alias : 'widget.main',
	config : {
		layout : 'card',
		items : [ {
			xtype : 'login',
			id : 'login'
		}, {
			xtype : 'home',
			id : 'home'
		} ]
	},
	initialize : function() {

		this.callParent(arguments);
		this.on('painted', this.checkLogin, this);
	},
	checkLogin : function() {
		if (loggedInMember != undefined && loggedInMember != ''
				&& loggedInMember.length != 0) {
			this.showHome();
		}
	},
	showLoginForm : function() {
		this.setActiveItem(0, {
			type : 'slide',
			direction : 'top',
			duration : 250
		});
	},
	showHome : function() {
		this.setActiveItem(1, {
			type : 'slide',
			direction : 'left',
			duration : 1000
		});
	}
});