Ext.define('MBAPeople.view.StylePicker', {
    extend: 'Ext.Panel',
    xtype: 'stylepicker',
    requires: ['Ext.dataview.List'],

    config: {
        hideOnMaskTap: true,
        modal: true,
        hidden: true,
        left: 0,
        top: 0,
        width: 220,
        height: 356,
        layout: 'fit',

        items: [{
            xtype: 'list',
            store: 'Styles',
            itemTpl: '{name}'
        }]
    }
});
