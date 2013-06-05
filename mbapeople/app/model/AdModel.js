

Ext.define('MBAPeople.model.AdModel', {
    extend: 'Ext.data.Model',

    config: {
        fields: [
            {name: 'id'},
            {name: 'guid'},
            {name: 'author'},
            {name: 'post_title'},
            {name: 'post_id'},
            {name: 'brief'},
            {name: 'post_content'},
            {name: 'status'},
            {name: 'post_date'},
            {name: 'post_modified'},
            {name: 'tag'},
            {name: 'catalog'},
            {name: 'type'},
            {name: 'icon'}
        ]
    }
});