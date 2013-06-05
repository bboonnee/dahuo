Ext.define('MBAPeople.controller.Ad', {
    extend:'MBAPeople.controller.Contacts',
    config:{
        control:{
            "adlist":{
                itemtap:'onAdlistItemTap'
            }
        }},
    onAdlistItemTap:function (dataview, index, target, record, e, options) {
        var panel = this.getAdpanel();
        if (panel.getItems().get(3).down('#text').getHtml()) {
            panel.setActiveItem(index);
        } else {
            panel.setActiveItem(0);
        }
        Ext.Viewport.setActiveItem(panel);
    }
});