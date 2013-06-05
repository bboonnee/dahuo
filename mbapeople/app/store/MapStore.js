

Ext.define('MBAPeople.store.MapStore', {
    extend:'Ext.data.Store',

    requires:[
        'MBAPeople.model.UserModel', 'MBAPeople.proxy.ProxySearch'
    ],

    config:{
        model:'MBAPeople.model.UserModel',
        storeId:'MapStore',
        proxy:{
            type: 'searchproxy'
        }
    }
});