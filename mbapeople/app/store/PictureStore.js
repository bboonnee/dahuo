

Ext.define('MBAPeople.store.PictureStore', {
    extend:'Ext.data.Store',

    requires:[
        'MBAPeople.model.PictureModel'
    ],
    config: {
        model: 'MBAPeople.model.PictureModel',
        storeId: 'PictureStore',
        proxy: {
            type: 'ajax',
            url: 'picture.json',
            reader: {
                type: 'json'
            },
            autoLoad: true
        }
    }



});