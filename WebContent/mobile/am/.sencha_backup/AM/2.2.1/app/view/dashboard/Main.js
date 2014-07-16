Ext.define('AM.view.dashboard.Main', {
	extend : 'Ext.Panel',
	alias : 'widget.dashboard_main',
	requires : [ 'AM.view.common.ReportPanel' ],
	config : {
		layout : 'card'
	},
	initialize : function() {
		this.callParent(arguments);
		this.on('painted', this.loadDataFromServer, this);
	},
	loadDataFromServer : function() {

		ajaxService.processRequest("dashboard", "getMetaData", {
			loadMaskMessage : 'Loading meta data.....',
			loadMaskEl : Ext.getBody(),
			success : this.createMainPanel,
			scope : this
		});
	},

	createMainPanel : function(data) {

		this.list = Ext.create('Ext.dataview.List', {
			title : 'Dashboard',
			store : {
				fields : [ 'configName', 'reportName' ],
				data : data.reportPanelsInfo
			},
			itemTpl : '<div>{configName} - {reportName}</div>',
			scope : this,
			onItemDisclosure : true
		});
		this.list.on('itemtap', this.handleClickEvent, this);
		var backButton = {
			text : 'Back',
			ui : 'back',
			handler : function() {
				this.up('home').showOptionPanel();
			},
			scope : this
		};
		var titlebar = {
				xtype : 'toolbar',
				title : 'Home',
				docked : 'top',
				items : [ backButton ]
		};
		this.mainPanel = Ext.create('Ext.Panel', {
			items : [titlebar, this.list ],
			scroll : 'vertical',
			layout : 'fit'
		});
		this.add([ this.mainPanel ]);
		this.setActiveItem(this.mainPanel);

	},
	
	showMainPanel : function(){
		if( this.mainPanel == undefined ){
			this.createMainPanel();
			return;
		}
		this.setActiveItem(this.mainPanel);
	},
	
	handleClickEvent : function(view, idx, item, e) {
		var serviceName = '';
		if( idx%2 == 0 ){
			serviceName = 'browserUsage';
		}else{
			serviceName = 'osUsage';
		}
		ajaxService.processRequest(serviceName, "getUsageComplete",
				{
					loadMaskMessage : 'Loading chart data.....',
					loadMaskEl : this.getEl == undefined ? Ext.getBody() : this
							.getEl(),
					success : this.afterPieDataLoad,
					scope : this
				});
	},
	afterPieDataLoad : function(data) {
		this.graphPanel = Ext.create('AM.view.graph.PieChart', {
			graphConfig : data
		});
		this.add([ this.graphPanel ]);
		this.setActiveItem( this.graphPanel );
	}
});