

Ext.define('MBAPeople.model.WpModel', {
    extend: 'Ext.data.Model',

    config: {
        fields: [
            {name: 'id'},
            {name: 'type'},
            {name: 'url'},
            {name: 'status'},
            {name: 'title'},
            {name: 'title_plain'},
            {name: 'content'},
            {name: 'excerpt'},
            {name: 'date'},
            {name: 'modified'},
            {name: 'categories'},
            {name: 'catalog'},
            {name: 'tags'},
            {name: 'author'}
        ]
    }
});