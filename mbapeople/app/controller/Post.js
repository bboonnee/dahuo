Ext.define('MBAPeople.controller.Post', {
    extend:'MBAPeople.controller.Contacts',
    config:{
        control:{
            "button#postsytlebtn":{
                tap:'onPostsytlebtnTap'
            },
            "button#Postnewbtn":{
                tap:'onPostnewbtnTap'
            },
            "postlist":{
                itemtap:'onPostlistItemTap'
            },
            "commentlist":{
                itemtaphold:'onCommentlistItemtaphold'
            },
            "postpanel":{
                activeitemchange:'onPostActiveitemchange',
                initialize:'onPostPanelinitialize'
            },
            postStyleList:{
                itemtap:'changePostStyle'
            },
            "button#savePostbtn":{
                tap:'onSavePostbtnTap'
            },
            "button#postformBackBtn":{
                tap:'onPostformBackBtn'
            },
            "button#editpostBtn":{
                tap:'onEditpostBtn'
            },
            "pullrefresh":{
                refreshFn:'refreshFn'
            },
            "button#deltePostbtn":{
                tap:'onDeltePostbtn'
            }
        }
    },

    onCommentlistItemtaphold:function (dataview, index, target, record, e, eOpts) {
        var commentid = record.getData().id , postid = record.getData().postid;
        var account = account = Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account');
        if (record.getData().account == account) {
            this.actions = Ext.Viewport.add({
                xtype:'actionsheet',
                items:[
                    {
                        text:'回复',
                        scope:this,
                        hidden:true,
                        handler:function () {
                            this.actions.hide();
                        }
                    },
                    {
                        text:'删除评论',
                        ui:'decline',
                        scope:this,
                        itemId:'deletecomment',
                        handler:function () {
                            var updatestore = Ext.StoreMgr.lookup('PostUpdateStore'),
                                p = updatestore.getProxy();
                            if (!p) {
                            } else {
                                dataview.setMasked(true);
                                p.setExtraParams({'do':'deletecomment',
                                    "commentid":commentid,
                                    'account':account,
                                    'device':Ext.os.name});
                                updatestore.load(function (records, operation, success) {
                                    dataview.setMasked(false);
                                    if (success) {
                                        if (records[0].get('id')) {
                                            dataview.refreshlist(postid);
                                            Ext.Msg.alert('删除评论成功', '', Ext.emptyFn);
                                        } else {
                                            Ext.Msg.alert('删除评论失败', '', Ext.emptyFn);
                                        }
                                    }
                                    else {
                                        Ext.Msg.alert('删除评论失败', '请检查网络', Ext.emptyFn);
                                    }
                                }, this);
                            }
                            this.actions.hide();
                        }
                    },
                    {
                        xtype:'button',
                        text:'取消',
                        scope:this,
                        handler:function () {
                            this.actions.hide();
                        }
                    },
                ]
            });


            this.actions.show();
        }

    },
    refreshFn:function (loaded, arguments) {
        console.dir('refreshFn');
    },
    onPostPanelinitialize:function () {
        var postpanel = this.getPostpanel();
        var records = Ext.StoreMgr.lookup('PostStore').getData();
        var myitems = [], i;
        for (i = 0; i < records.length; i++) {
            var record = records.getAt(i);
            // 构造器函数
            myitems.push(
                {
                    xtype:'panel',
                    scrollable:{
                        direction:'vertical',
                        directionLock:true
                    },
                    items:[
                        {
                            xtype:'panel',
                            style:'font: 15px Arial black',
                            margin:'10 10 10 15',
                            padding:'10 10 10 15',
                            border:1,
                            itemId:'text',
                            html:'<style type="text/css"> .fenge {height:1px; width:100%; background:#00CCFF; overflow:hidden;}.content{text-indent:2em;font-size:13px}</style>'
                                + '<div align="center"><h1 class="page-title">' + [record.get('title')] + '</h1></div>'
                                + '<div align="right"><strong>' + [record.get('author')] + "  " + [record.get('author_id')] + '</strong></div>'
                                + '<div align="right"><strong>' + [record.get('posttime')] + '</strong></div>'
                                + '<div> </div>'
                                + '<div class="fenge">' + [record.get('content')] + '</div>'
                                + '<div class="content">' + [record.get('content')] + '</div>'
                        },
                        {
                            xtype:'commentlist',
                            margin:'10 10 10 15',
                            itemId:'commentlist'
                        },
                        {
                            xtype:'fieldset',
                            border:1,
                            margin:'10 10 10 15',
                            padding:'10 10 10 15',
                            layout:{
                                type:'hbox',
                                align:'stretch'
                            },
                            items:[
                                {
                                    xtype:'textfield',
                                    border:1,
                                    style:'font: 13px Arial',
                                    itemId:'commentinput',
                                    width:'80%',
                                    listeners:{
                                        keyup:function (textfield, e, options) {
                                            var keyCode = e.event.keyCode;
                                            if (keyCode == 13) {
                                                var value = textfield.getValue();
                                                if (value.trim() != "") {
                                                    postpanel.sendComment(textfield,value);
                                                    textfield.setValue('');
                                                    textfield.blur();
                                                } else {
                                                    console.dir('post keyup');
                                                    Ext.Msg.alert('评论为空', '请输入哈~', Ext.emptyFn);
                                                }
                                            }
                                        }
                                    }
                                },
                                {
                                    xtype:'button',
                                    itemId:'commentbtn',
                                    iconCls:'action',
                                    iconMask:true,
                                    handler:function (button) {
                                        var textfield = button.getParent().getParent().down('#commentinput');
                                        if(textfield.getValue().trim()!='')
                                        {
                                            postpanel.sendComment(textfield,textfield.getValue());
                                            textfield.setValue('');
                                            textfield.blur();
                                        }else {
                                            Ext.Msg.alert('评论为空', '请输入哈~', Ext.emptyFn);
                                        }

                                    }
                                },
                                {
                                    xtype:'textfield',
                                    itemId:'postid',
                                    hidden:true,
                                    value:record.get('id')
                                },
                                {
                                    xtype:'textfield',
                                    itemId:'author_id',
                                    hidden:true,
                                    value:record.get('author_id')
                                }
                            ]

                        },

                    ]
                }

            );
        }
        postpanel.setItems(myitems);
    },
    onPostActiveitemchange:function (container, value, oldvalue, eopts) {

        var author_id = value.down('#author_id').getValue();
        if (author_id == Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account')) {
            container.down('#editpostBtn').setHidden(false);
        } else {
            container.down('#editpostBtn').setHidden(true);
        }
        var postid = value.down('#postid').getValue(),
            list = value.down('#commentlist');
        if (postid) {
            if (!list.getStore()) {
                list.refreshlist(postid);
            }
        }
    },
    onEditpostBtn:function () {
        var panel = this.getPostpanel().getActiveItem();
        var postid = panel.down('#postid').getValue();

        var form = this.getPostform();
        var data = Ext.StoreMgr.lookup('PostStore').getData();
        var record = Ext.StoreMgr.lookup('PostStore').getData().get(postid);

        form.clearField();
        form.setHiddenfield(true);
        form.setRecord(record);
        Ext.Viewport.setActiveItem(form);
    },
    onDeltePostbtn:function () {
        var me = this;
        Ext.Msg.confirm(
            "删除帖子",
            "你确定要删除这个帖子？",
            function (buttonId) {
                if (buttonId === 'yes') {
                    me.deletepost();
                }
            }
        );
    },
    deletepost:function () {
        var form = this.getPostform(), me = this,
            updatestore = Ext.StoreMgr.lookup('PostUpdateStore'),
            p = updatestore.getProxy(),
            account = Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account'),
            main = this.getMainview();
        var postid = form.down('#id').getValue();
        if (!p) {
        } else {
            p.setExtraParams({'do':'deletepost',
                "postid":postid,
                'account':account,
                'device':Ext.os.name});
            updatestore.load(function (records, operation, success) {
                if (records[0].get('id')) {
                    me.loadPostdata();
                    Ext.Msg.alert('删除成功', '', Ext.emptyFn);
                    Ext.Viewport.setActiveItem(main);

                } else {
                    Ext.Msg.alert('删除失败', '', Ext.emptyFn);
                    //this.onInfoBackBtnTap();
                }
            }, this);
        }
    },
    loadPostdata:function () {
        var post = Ext.StoreMgr.lookup('PostStore'), me = this,
            p = post.getProxy();
        p.setExtraParams({'do':'getpost', "limit":'10', 'account':Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account'), 'device':Ext.os.name});
        post.load(function (records, operation, success) {
            if (success) {
                var Carousel = this.getPostpanel();
                me.onPostPanelinitialize();
            }
        }, this);
    },
    onPostformBackBtn:function () {
        var main = this.getMainview();
        Ext.Viewport.setActiveItem(main);
    },
    onSavePostbtnTap:function (button, e, options) {
        var form = this.getPostform(),
            main = this.getMainview(), me = this,
            values = form.getValues();

        //如果可以读到model的值，否则新增
        Ext.Ajax.request(
            {
                //url:'http://192.168.1.8/mba/',
                url:'http://54.251.118.17/mba/',
                useDefaultXhrHeader:false,
                method:'post',
                cors:'true',
                params:{
                    'do':"savepost",
                    'data':values,
                    'account':Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account'),
                    'device':Ext.os.name
                },
                success:function (response) {
                    me.loadPostdata();
                    Ext.Msg.alert('保存成功', "", Ext.emptyFn);
                    Ext.Viewport.setActiveItem(main);
                    // process server response here
                },
                failure:function (response) {
                    var text = response.responseText;
                    Ext.Msg.alert('保存失败', text, Ext.emptyFn);
                }
            });
    },
    onPostsytlebtnTap:function (button, e, options) {
        this.getPostStyleBubble().showBy(button);

    },
    onPostnewbtnTap:function (button, e, options) {
        var form = this.getPostform();
        form.setHiddenfield(false);
        //var form = this.getPostnewform();
        form.clearField();
        Ext.Viewport.setActiveItem(form);
    },
    onSavePostbtnTapold:function (button, e, options) {
        var form = this.getPostform(),
            values = form.getValues(),
            updatestore = Ext.StoreMgr.lookup('PostUpdateStore'),
            p = updatestore.getProxy(),
            account = Ext.StoreMgr.lookup('LocalStore').getData().getAt(0).get('account');
        if (!p) {
            //console.log('proxy is null');
        } else {
            p.setExtraParams({'do':'savepost',
                "data":values,
                'account':account,
                'device':Ext.os.name});
            updatestore.load(function (records, operation, success) {
                if (records[0].get('id')) {
                    Ext.Msg.alert('保存成功', '', Ext.emptyFn);
                } else {
                    Ext.Msg.alert('保存失败', '', Ext.emptyFn);
                    //this.onInfoBackBtnTap();
                }
            }, this);
        }
    },
    onPostlistItemTap:function (dataview, index, target, record, e, options) {
        var records = dataview.getStore().getData();
        var panel = this.getPostpanel();
        //panel.setdata(record);
        panel.setMasked(false);
        panel.setActiveItem(index);
        /* if (panel.getItems().get(3).down('#text').getHtml()) {
         panel.setActiveItem(index);
         } else {
         panel.setActiveItem(0);
         }*/
        Ext.Viewport.setActiveItem(panel);
    },


    changePostStyle:function (dataview, index, target, record, e, options) {
        var target = record.data.id,
            classlist = this.getClasslist(),
            poststore = Ext.StoreManager.lookup('PostStore'),
            main = this.getMainview();
        main.setMasked({
            xtype:'loadmask',
            message:'Loading...',
            indicator:false
        });
        poststore.clearFilter();
        switch (target) {
            case 'all':
                //poststore.clearFilter();
                break;
            case 'notice':
                poststore.filter("type", 'notice');
                break;
            case 'help':
                poststore.filter("type", 'help');
                break;
            case 'org':
                poststore.filter("type", 'org');
                break;
            case 'coop':
                poststore.filter("type", 'coop');
                break;
            case 'job':
                poststore.filter("type", 'job');
                break;
            case 'ads':
                poststore.filter("type", 'ads');
                break;
        }
        main.unmask();
        this.getPostStyleBubble().hide();
    },
})
;