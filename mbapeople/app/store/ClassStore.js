

Ext.define('MBAPeople.store.ClassStore', {
    extend: 'Ext.data.Store',

    requires: [
        'MBAPeople.model.ClassModel'
    ],
    config: {
        model: 'MBAPeople.model.ClassModel',
        storeId: 'ClassStore',
        grouper: function(record) {
            return record.get('startyear')[4];
        },
        sorters: [
            {
                property : "startyear",
                direction: "ASC"
            },
            {
                property : "classno",
                direction: "ASC"
            }
        ],
        proxy: {
            type: 'ajax',
            url: 'classno.json',
            reader: {
                type: 'json'
            },
            autoLoad: true
        }

        /*proxy:{
            type: 'searchproxy'
        }*/
    }
});