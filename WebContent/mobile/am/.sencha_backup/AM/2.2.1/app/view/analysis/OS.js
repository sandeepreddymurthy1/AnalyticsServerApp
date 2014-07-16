Ext.define('AM.view.analysis.OS', {
	extend : 'AM.view.analysis.Browser',
	alias : 'widget.analysis_os',
	config : {
		serviceName : 'osUsage'
	},
	initialize : function() {
		this.callParent();
	}
});
