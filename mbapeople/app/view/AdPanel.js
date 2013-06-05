Ext.define('MBAPeople.view.AdPanel', {
    extend:'Ext.Carousel',
    alias:'widget.adpanel',
    requires:[
        'Ext.carousel.Carousel'
    ],
    config:{
        direction:'horizontal',
        scroll:'vertical',
        masked: {
            xtype: 'loadmask',
            message: '读取中...'
        },
        items:[
            {
                xtype:'toolbar',
                docked:'top',
                title:'校友合作',
                items:[
                    {
                        xtype:'button',
                        itemId:'infoBackBtn',
                        ui:'back',
                        iconCls:'home',
                        iconMask:true
                    }
                ]
            }
        ],
        listeners:{
            'activeitemchange':function (container, value, oldvalue, eopts) {
                if(! value.down('#text').getHtml())
                {
                    container.setMasked(true);
                    //console.dir(value);
                    var index = container.indexOf(value);
                    //console.dir('index'+index);
                    var records = Ext.StoreMgr.lookup('AdStore').getData();
                    var re = records.getAt(index - 3);
                    var store = Ext.StoreMgr.lookup('AdReadStore');
                    var p = store.getProxy();
                    p.setExtraParam("json", 'get_post');
                    //p.setExtraParam("dev", '1');
                    p.setExtraParam("id", re.get('post_id'));
                    store.load(function (records, operation, success) {
                        container.setMasked(false);
                        value.down('#text').setWidth(Ext.getBody().getSize().width);
                        value.down('#text').setHtml(records[0].get('content'));

                    })
                }
            },
            'initialize':function (carousel) {
                var records = Ext.StoreMgr.lookup('AdStore').getData();
                var myitems = [], i;
                for (i = 0; i < records.length; i++) {
                    var record = records.getAt(i);
                    myitems.push(
                        {
                            xtype:'panel',
                            scrollable:{
                                direction:'vertical',
                                directionLock:true
                            },
                            items:{
                                xtype:'panel',
                                //width:'320px',
                                style:'font: 12px Arial black',
                                margin:'10 50 10 15',
                                padding:'10 50 10 15',
                                itemId:'text'
                            }
                        }
                    );
                }
                carousel.setItems(myitems);
                setInterval(function () {
                    if (i < carousel.getItems().length) {
                        i += 1;
                        carousel.setActiveItem(i);
                    }
                    else {
                        carousel.setActiveItem(0);
                        i = 0;
                    }
                }, 100000);

            }
        }
    }
});