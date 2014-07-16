Ext.define('AM.view.analysis.ConfigList', {
	extend : 'Ext.dataview.List',
	alias : 'widget.analysis_configList',
	config : {

		itemTpl : '<div>{configurationName}</div>',
		onItemDisclosure : true,
		store : 'configListStore',
		items : [{
			xtype : 'toolbar',
			title : 'Analysis',
			docked : 'top',
			items : [ {
				text : 'Back',
				ui : 'back',
				handler : function() {
					this.up('home').showOptionPanel();
				}
			},{
				xtype : 'spacer'
			},{
				text : 'Refresh',
				ui : 'refresh',
				iconCls : 'refresh',
				handler : function(){
					this.up('analysis_configList').loadDataFromServer();
				}
			} ]
		}]
	},
	initialize : function() {
		this.callParent(arguments);
		this.on('painted', this.loadDataFromServer, this);
	},
	loadDataFromServer : function() {

		ajaxService.processRequest("analysis", "getAllConfigs", {
			loadMaskMessage : 'Loading meta data.....',
			loadMaskEl : Ext.getBody(),
			success : function(data){
				Ext.getStore('configListStore').setData(data);
			},
			scope : this
		});
	}
});