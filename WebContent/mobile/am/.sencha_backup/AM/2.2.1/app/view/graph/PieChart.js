/**
 * Demonstrates how use Ext.chart.series.Pie
 */
    //<feature charts>
Ext.define('AM.view.graph.PieChart', {
    extend: 'Ext.Panel',
    alias : 'widget.piechart',
    requires: ['Ext.chart.PolarChart', 'Ext.chart.series.Pie', 'Ext.chart.interactions.Rotate'],
    config: {
        cls: 'card1',
        layout: 'fit',
        style: 'background: white',
        items: [
            {
                xtype: 'toolbar',
                top: 0,
                left: 0,
                zIndex: 50,
                style: {
                    background: 'none'
                },
                items: [
                    {
                        iconCls: 'back',
                        text: 'Back',
                        ui : 'back',
                        handler: function () {
                        	this.up('dashboard_main').showMainPanel();
                        }
                    }
                ]
            },
            {
                xtype: 'polar',
                store: 'Pie',
                colors: AM.view.ColorPatterns.getBaseColors(),
                interactions: ['rotate', 'itemhighlight'],
                legend: {
                    docked: 'bottom',
                    verticalWidth: 100
                },
                innerPadding: 45,
                series: [
                    {
                        type: 'pie',
                        xField: 'data',
                        labelField: 'name',
                        donut: 30,
                        highlightCfg: {
                            margin: 20
                        },
                        style: {
                            stroke: 'white',
                            miterLimit: 10,
                            lineCap: 'miter',
                            lineWidth: 2
                        }
                    }
                ],
                axes: [
                ]
            }
        ]
    },
    initialize: function () {
        this.callParent();
        var graphConfig = this.initialConfig.graphConfig;
		var data = graphConfig.data;
		var recs = [];
		for( var field in data ){
			var rec = {};
			rec.name = field;
			rec.data = data[field];
			recs[recs.length] = rec;
		}
        Ext.getStore('Pie').setData(recs);
    }
});
//</feature>
