Ext.define('AM.view.common.TimeSelect', {
	extend : 'Ext.field.Select',
	alias : 'widget.common_timeselect',
	
	config : {
        width : '40%',
        align : 'right'
	},
	initialize : function() {
		this.addEvents({
			'complete':true,
			'daily' : true,
			'weekly' : true,
			'monthly' : true,
			'quarter' : true,
			'custom': true
		});
		this.setOptions([
		                  {text: 'Daily',  value: 'getUsageDaily'},
		                  {text: 'Weekly', value: 'getUsageWeekly'},
		                  {text: 'Monthly',  value: 'getUsageMonthly'},
		                  {text: 'Quarter',  value: 'getUsageQuarter'},
		                  {text: 'Yearly',  value: 'getUsageYearly'},
		                  {text: 'Complete',  value: 'getUsageComplete'}
		              ]);
		this.callParent();
	}
});