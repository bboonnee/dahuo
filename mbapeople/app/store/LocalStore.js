

Ext.define('MBAPeople.store.LocalStore', {
    extend:'Ext.data.Store',

    requires:[
        'MBAPeople.model.UserModel','Ext.data.proxy.LocalStorage'
    ],

    config:{
        model:'MBAPeople.model.UserModel',
        storeId:'LocalStore',
        proxy:{
            type: 'localstorage'
           // id  : 'mbarenmai'
        },
        autoSync:true,
        autoLoad:true
    }



});