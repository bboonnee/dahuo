

Ext.define('MBAPeople.store.WPReadStore', {
    extend:'Ext.data.Store',

    requires:[
        'MBAPeople.model.WpModel', 'MBAPeople.proxy.ProxySearch'
    ],

    config:{
        model:'MBAPeople.model.WpModel',
        storeId:'AdReadStore',
        proxy:{
            type: 'wpproxy'
        }
    }
});