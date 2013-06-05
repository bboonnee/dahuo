Ext.define('MBAPeople.view.PostPanel', {
    extend:'Ext.Carousel',
    alias:'widget.postpanel',
    requires:[
        'Ext.carousel.Carousel', 'MBAPeople.view.CommentList', 'MBAPeople.view.PostHtml'
    ],
    config:{
        direction:'horizontal',
        scroll:'vertical',
        masked:{
            xtype:'loadmask',
            message:'读取中...'
        },
        refs:{
            posthtml:{
                autoCreate:true,
                selector:'posthtml',
                xtype:'posthtml'
            },
        },

        items:[
            {
                xtype:'toolbar',
                docked:'top',
                title:'论坛',
                items:[
                    {
                        xtype:'button',
                        itemId:'infoBackBtn',
                        ui:'back',
                        iconCls:'home',
                        iconMask:true
                    },
                    {xtype:'spacer'},
                    {
                        xtype:'button',
                        itemId:'editpostBtn',
                        hidden:true,
                        text:'修改'
                    },
                ]
            }
        ]
    },
    sendComment:function (textfield,content) {
        var update = Ext.StoreMgr.lookup('UserUpdateStore'),
            postid = textfield.getParent().down('#postid').getValue(),
            //content = textfield.getValue(),
            parent = postid;
        if(content!="")
        {
            var p = update.getProxy();
            p.setExtraParams({'do':'sendComment',
                'account':Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account'),
                'author':Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('name'),
                'content':content,
                'postid':postid,
                'parent':parent,
                'device':Ext.os.name});
            update.load(function (records, operation, success) {
                //读取comment成功则跳转到main
                if (!success) {
                    //Ext.Msg.alert('网络读取数据失败：', '请检查网络连接');
                } else {
                    textfield.getParent().getParent().down('#commentlist').refreshlist(postid);
                    textfield.setValue('');
                    textfield.blur();
                }
            }, this);
        }
    }

});