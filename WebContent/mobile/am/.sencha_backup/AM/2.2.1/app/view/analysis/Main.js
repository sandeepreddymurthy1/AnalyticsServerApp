Ext.define('AM.view.analysis.Main', {
	extend : 'Ext.Panel',
	alias : 'widget.analysis_main',
	requires : ['AM.view.analysis.ConfigList','AM.view.analysis.ReportList','AM.view.analysis.Browser'],
	config : {
		layout : 'card',
		items : [{
			xtype : 'analysis_configList'
		},{
			xtype : 'analysis_reportList'
		},
		{ 
			xtype : 'analysis_browser'
		},
		{ 
			xtype : 'analysis_os'
		},
		{
			xtype : 'analysis_relativeSite'
		},
		{
			xtype : 'analysis_map'
		}],
	},
	initialize : function() {
		this.callParent(arguments);
	},
	showConfigList : function(){
		this.setActiveItem(0, {
			type : 'slide',
			direction : 'right',
			duration : 1000
		});
	},
	showReportList : function(data){
		this.setActiveItem(1, {
			type : 'slide',
			direction : 'left',
			duration : 1000
		});
	},
	showBrowserPanel : function(data){
		this.setActiveItem(2, {
			type : 'slide',
			direction : 'left',
			duration : 1000
		});
	}
});