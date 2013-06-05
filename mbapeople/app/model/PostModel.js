

Ext.define('MBAPeople.model.PostModel', {
    extend: 'Ext.data.Model',

    config: {
        fields: [
            {name: 'id'},
            {name: 'author'},
            {name: 'title'},
            {name: 'content'},
            {name: 'status'},
            {name: 'posttime'},
            {name: 'modifytime'},
            {name: 'tag'},
            {name: 'catalog'},
            {name: 'type'},
            {name: 'author_id'}
        ]
    }
});