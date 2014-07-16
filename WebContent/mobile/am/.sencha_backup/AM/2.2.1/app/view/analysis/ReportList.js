Ext.define('AM.view.analysis.ReportList', {
	extend : 'Ext.dataview.List',
	alias : 'widget.analysis_reportList',
	title : 'Report List',
	config : {

		itemTpl : '<div>{reportTitle}</div>',
		onItemDisclosure : true,
		store : {
			fields :['reportType','id','reportTitle'],
			data : []
		},
		items : [{
			xtype : 'toolbar',
			title : 'Reports',
			docked : 'top',
			items : [ {
				text : 'Back',
				ui : 'back',
				handler : function() {
					this.up('analysis_main').showConfigList();
				}
			},{
				xtype : 'spacer'
			},{
				text : 'Refresh',
				ui : 'refresh',
				iconCls : 'refresh',
				handler : function(){
					this.up('analysis_reportList').loadDataFromServer();
				}
			} ]
		}]
	},
	initialize : function() {
		this.callParent(arguments);
	},
	loadDataForReport : function(configId){
		
	}
});