

Ext.define('MBAPeople.model.PictureModel', {
    extend: 'Ext.data.Model',

    config: {
        fields: [
            {name: 'id'},
            {name: 'image_url'},
            {name: 'title'}
        ]
    }
});