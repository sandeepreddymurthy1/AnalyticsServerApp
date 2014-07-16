Ext.define('AM.view.analysis.Browser', {
	extend : 'Ext.Panel',
	alias : 'widget.analysis_browser',
	requires : [ 'Ext.chart.PolarChart', 'Ext.chart.series.Pie',
			'Ext.chart.interactions.Rotate' ],
	config : {
		layout : 'card',
		serviceName : 'browserUsage',
		style : 'background: white',
		items : [ {
			xtype : 'toolbar',
			top : 0,
			left : 0,
			zIndex : 50,
			style : {
				background : 'none'
			},
			items : [ {
				iconCls : 'back',
				text : 'Back',
				ui : 'back',
				handler : function() {
					this.up('analysis_main').showReportList();
				}
			}, {
				xtype : 'segmentedbutton',
				items : [ {
					text : 'Graph',
					pressed : true,
					handler : function() {
						this.up('analysis_browser').setActiveItem(0);
					}
				}, {
					text : 'List',
					handler : function() {
						this.up('analysis_browser').setActiveItem(1);
					}
				} ]
			}, {
				xtype : 'common_timeselect',
				width : '25%'
			} ]
		}, {
			xtype : 'polar',
			store : 'Pie',
			title : 'Chart',
			layout : 'fit',
			colors : AM.view.ColorPatterns.getBaseColors(),
			interactions : [ 'rotate', 'itemhighlight' ],
			legend : {
				docked : 'bottom',
				verticalWidth : 100
			},
			innerPadding : 45,
			series : [ {
				type : 'pie',
				xField : 'data',

				labelField : 'name',
				donut : 30,
				highlightCfg : {
					margin : 20
				},
				style : {
					stroke : 'white',
					miterLimit : 10,
					lineCap : 'miter',
					lineWidth : 2
				}
			} ]
		}, {
			layout : 'fit',
			items : [ {
				xtype : 'common_grid',
				title : 'Grid',
				store : 'Pie',
				layout : 'fit',
				columns : [ {
					header : 'Name',
					dataIndex : 'name',
					width : '70%'
				}, {
					header : 'Hits',
					dataIndex : 'data',
					width : '30%'
				} ]
			} ]
		} ]
	},
	initialize : function() {
		this.callParent();
		// Register for event on select
		this.down('common_timeselect').on('change', function(field,newValue){
			ajaxService.processRequest(this.getServiceName(),newValue,{
				success : function(data){
					this.loadData(data);
				},
				scope : this
			});
		},this);
	},
	loadData : function(reportConfig) {
		var recs = [];
		var data = reportConfig.data;
		for ( var field in data) {
			var rec = {};
			rec.name = field;
			rec.data = data[field];
			recs[recs.length] = rec;
		}
		this.down('polar').getStore().setData(recs);
		this.down('common_grid').getStore().setData(recs);
	}
});
// </feature>
