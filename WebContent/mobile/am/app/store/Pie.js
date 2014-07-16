Ext.define('AM.store.Pie', {
        alias: 'store.Pie',
        extend: 'Ext.data.Store',
        config: {
            storeId: 'Pie',
            fields: ['name', 'data'],
           
        },
        constructor: function () {
            this.callParent(arguments);
            return this;
        }
});