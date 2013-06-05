Ext.define('MBAPeople.model.ClassModel', {
    extend: 'Ext.data.Model',

    config: {
        fields: [
            {
                name: 'classno',
                type: 'string'
            },
            {
                name: 'startyear',
                type: 'string'
            }
        ]
    }
});