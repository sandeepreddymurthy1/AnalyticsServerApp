Ext.define('AM.view.Main', {
    extend: 'Ext.Panel',
    alias: 'widget.main',
    config: {
        layout: 'card',
        //tabBarPosition: 'bottom',
        items: [
            {
                xtype: 'login'
            },
            {
                xtype: 'home'
               // xtype : 'panel'
            }

        ]
    },
    initialize:function(){
        this.callParent(arguments);
    },
    showLoginForm: function () {
        this.setActiveItem(0);
    },
    showHome: function () {
        this.setActiveItem(1);
    }
});