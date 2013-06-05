

Ext.define('MBAPeople.store.UserStore', {
    extend:'Ext.data.Store',

    requires:[
        'MBAPeople.model.UserModel', 'MBAPeople.proxy.ProxySearch'
    ],

    config:{
        model:'MBAPeople.model.UserModel',
        storeId:'UserStore',
        proxy:{
            type: 'searchproxy'
        }
    }



});