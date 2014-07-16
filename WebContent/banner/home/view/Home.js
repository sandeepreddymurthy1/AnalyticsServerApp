Ext.define('home.view.Home', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.home',
	//requires : [ 'home.model.Register' ],
	style : 'margin: 50px',
	autoScroll : true,
	defaults : {
		cls : 'field-margin'
	},
	initComponent : function() {
		
		this.template = Ext.XTemplate.from('intro');
		this.templatePanel = new Ext.panel.Panel({height : 800, autoscroll : true, autoScroll : true, html : this.template.html, border : false});
		this.items = [this.templatePanel];
		this.callParent();
		//this.on('render', this.updatePanel,this);
	},
	updatePanel : function(){
		this.template.overwrite(this.templatePanel.getEl(),{});
	}
});