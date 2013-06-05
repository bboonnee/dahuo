Ext.define('MBAPeople.view.About', {
    extend:'Ext.form.Panel',
    alias:'widget.about',

    config:{
        style:'border: none; font: 13px Arial black',
        ui:'round',
        items:[
            {
                xtype:'toolbar',
                docked:'top',
                items:[
                    {
                        xtype:'button',
                        itemId:'infoBackBtn',
                        ui:'back',
                        iconCls: 'home',
                        iconMask: true
                    },
                    {
                        xtype:'component',
                        flex:1,
                        cls:[
                            'contact-name'
                        ],
                        style:'font: 24px Arial white;color:white',
                        html:'软件版本',
                        itemId:'nameTxt',
                        centered:true
                    }

                ]
            },
            {
                xtype:'panel',
                itemId:'html',
                listeners:{
                    'initialize':function (panel) {
                        if(!panel.getHtml())
                        {
                            var store = Ext.StoreMgr.lookup('AdReadStore');
                            var p = store.getProxy();
                            p.setExtraParam("json", 'get_post');
                            p.setExtraParam("id", '39');
                            store.load(function (records, operation, success) {
                                panel.setWidth(Ext.getBody().getSize().width);
                                panel.setHtml(records[0].get('content'));
                            })
                        }
                    }
                }
            }
        ]
    }

});