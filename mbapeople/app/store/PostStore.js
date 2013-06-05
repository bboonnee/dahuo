

Ext.define('MBAPeople.store.PostStore', {
    extend:'Ext.data.Store',

    requires:[
        'MBAPeople.model.PostModel', 'MBAPeople.proxy.ProxySearch'
    ],

    config:{
        model:'MBAPeople.model.PostModel',
        storeId:'PostStore',
        proxy:{
            type: 'searchproxy'
        }
    }
});