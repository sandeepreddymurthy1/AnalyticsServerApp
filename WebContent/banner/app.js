var rootContext = "/WebAnalyticsWebConsole";
var appFolder = '/WebAnalyticsWebConsole/banner/home';
Ext.Loader.setConfig({
	enabled : true,
	disableCaching : false
});
Ext.Loader.config.disableCaching = false;
Ext.Ajax.disableCaching = false;

Ext.application({
	name : 'home',
	// autoCreateViewport : true,
	appFolder : appFolder,
	// controllers : [ 'AdminController' ],
	requires : [ 'home.view.Register', 'home.view.Login', 'home.view.Home',
			'home.service.BaseService' ],
	launch : function() {
		ajaxService = new home.service.BaseService();
		var tabPanel = Ext.create('Ext.tab.Panel', {
			region : 'center',
			autoScroll : true,
			tabBar : {
				layout : {
					pack : 'center'
				}
			},
			items : [ {
				xtype : 'home',
				title : 'Home',
				autoscroll : true
			}, {
				xtype : 'panel',
				title : 'Demo'
			}, {
				xtype : 'panel',
				title : 'Login/Register',
				layout : {
					type : 'fit'
				},
				defaults : {
					border : false
				},
				autoScroll : true,
				items : [
				         { xtype : 'login_form' },
				         { xtype : 'register_form'}
				         ]
			},{
				xtype : 'panel',
				title : 'Suggest A UseCase'
			}, {
				xtype : 'panel',
				title : 'Contact Info'
			} ]
		});

		Ext.create('Ext.panel.Panel', {
			renderTo : 'bodyPanel',
			layout : {
				type : 'border'
			},
			border : false,
			defaults : {
				border : false
			},
			height : 800,
			items : [ {
				region : 'north',
				xtype : 'panel',
				height : 100,
				html : '<h1> Analytics as Service over Hadoop</h1>'
			}, tabPanel, {
				region : 'south',
				xtype : 'panel',
				height : 20,
				html : 'Developed and maintained by Indrasena'
			} ]
		});
	}
});