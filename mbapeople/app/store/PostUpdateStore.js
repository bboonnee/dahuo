

Ext.define('MBAPeople.store.PostUpdateStore', {
    extend:'Ext.data.Store',

    requires:[
        'MBAPeople.model.PostModel', 'MBAPeople.proxy.ProxySearch'
    ],

    config:{
        model:'MBAPeople.model.PostModel',
        storeId:'PostUpdateStore',
        proxy:{
            type: 'postproxy'
        }
    }



});