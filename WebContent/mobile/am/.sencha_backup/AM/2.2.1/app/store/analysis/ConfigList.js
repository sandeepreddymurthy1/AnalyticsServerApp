 Ext.define('AM.store.analysis.ConfigList', {
        alias: 'store.configListStore',
        extend: 'Ext.data.Store',
        config: {
            storeId: 'configListStore',
            fields: ['id', 'configurationName'],
           
        },
        constructor: function () {
            this.callParent(arguments);
            return this;
        }
});