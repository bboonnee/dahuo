

Ext.define('MBAPeople.store.CommentStore', {
    extend:'Ext.data.Store',

    requires:[
        'MBAPeople.model.CommentModel', 'MBAPeople.proxy.ProxySearch'
    ],

    config:{
        model:'MBAPeople.model.CommentModel',
        storeId:'CommentStore',
        proxy:{
            type: 'searchproxy'
        },
        sorters: [
            {
                property : "date",
                direction: "DESC"
            }
        ]
    }



});