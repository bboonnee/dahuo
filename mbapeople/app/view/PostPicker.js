Ext.define('MBAPeople.view.PostPicker', {
    extend: 'Ext.Panel',
    xtype: 'postpicker',
    requires: ['Ext.dataview.List'],

    config: {
        modal: true,
        hidden: true,
        hideOnMaskTap: true,
        left: 0,
        top: 0,
        width: 220,
        height: 356,
        layout: 'fit',

        items: [{
            xtype: 'list',
            store: 'PostStyles',
            itemTpl: '{name}'
        }]
    }
});
