Ext.define('AM.view.analysis.RelativeSite', {
	extend : 'AM.view.analysis.Browser',
	alias : 'widget.analysis_relativeSite',
	config : {
		serviceName : 'relativeSiteUsage'
	},
	initialize : function() {
		this.callParent();
	}
});
