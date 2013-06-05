

Ext.define('MBAPeople.store.AdStore', {
    extend:'Ext.data.Store',

    requires:[
        'MBAPeople.model.AdModel', 'MBAPeople.proxy.ProxyWordPress'
    ],

    config:{
        model:'MBAPeople.model.AdModel',
        storeId:'AdStore',
        proxy:{
            type: 'searchproxy'
        }
    }
});