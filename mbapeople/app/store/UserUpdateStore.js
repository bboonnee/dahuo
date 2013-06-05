

Ext.define('MBAPeople.store.UserUpdateStore', {
    extend:'Ext.data.Store',

    requires:[
        'MBAPeople.model.UserModel', 'MBAPeople.proxy.ProxySearch'
    ],

    config:{
        model:'MBAPeople.model.UserModel',
        storeId:'UserUpdateStore',
        proxy:{
            type: 'searchproxy'
        }
    }



});