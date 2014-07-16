Ext.define('AM.view.graph.VerticalBar', {
	extend : 'Ext.chart.AbstractChart',
	alias : 'widget.graph_verticalBar',
	title : 'Bar Chart',
	animate : true,
	shadow : false,
	legend : {
		position : {
			portrait : 'right',
			landscape : 'top'
		},
		labelFont : '17px Arial'
	},

	initialize : function() {
		var recs = [];
		for ( var i = 0; i < this.config.data.data.length; i++) {
			var row = this.config.data.data[i];
			recs[recs.length] = row;
		}
		this.store =  {
			fields : this.config.data.fields,
			data : recs
		};

		this.axes = [ {
			type : 'Numeric',
			position : 'bottom',
			fields : this.config.data.fields,
			label : {
				renderer : function(v) {
					return v.toFixed(0);
				}
			},
			title : 'Number of Hits',
			minimum : 0
		}, {
			type : 'Category',
			position : 'left',
			fields : this.config.data.xField,
			title : 'Month of the Year'
		} ];
		this.series = [ {
			type : 'bar',
			xField : this.config.data.xField,
			yField : this.config.data.yFields[0],
			axis : 'bottom',
			highlight : true,
			showInLegend : true
		} ];
		this.callParent(arguments);
	}
});